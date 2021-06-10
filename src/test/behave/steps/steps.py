#!/usr/bin/python3
""" Module that contains all default steps. """

import requests
import json
import jinja2
import time
import urllib
import websockets
from urllib.parse import urljoin

from behave import when, then
from behave.api.async_step import (
    async_run_until_complete,
    use_or_create_async_context,
    AsyncContext,
)


########################################################################################################################
#####  Utilities functions                                                                                         #####
########################################################################################################################
def str_to_bool(value):
    """ Convert a string representing a boolean to a real boolean """
    if str(value).lower() in ["true", "yes", "o"]:
        return True
    elif str(value).lower() in ["false", "no", "n"]:
        return False
    else:
        raise ValueError("Not accepted boolean value: {}".format(value))


def str_to_array_of_int(value):
    """ Convert a string representing an array of int into a real array of int """
    return [int(v) for v in value.split(",")]


def check_special_value(expected, value):
    """Check if value equals to Null, Not null, empty or expected value."""
    if expected == "NULL":
        return value is None
    elif expected == "NOT_NULL":
        return value is not None
    elif expected == "EMPTY":
        return value == ""
    elif expected == "NOT_EMPTY":
        return value is not None and len(value) > 0
    else:
        return value == expected


def convert_special_value(value):
    """Convert to special value."""
    if value == "NULL":
        return None
    elif value == "EMPTY":
        return ""
    else:
        return value


def convert_value_to(value, value_type):
    """ Convert {value} to specified {value_type}. """
    if value is None or len(str(value)) == 0:
        return None
    convert_map = {
        "string": str,
        "json": json.loads,
        "integer": int,
        "float": float,
        "boolean": str_to_bool,
        "arrayInteger": str_to_array_of_int,
    }
    try:
        return convert_map[value_type](value)
    except KeyError:
        raise ValueError("Unexpected type " + str(type))


########################################################################################################################
#####  Steps: Requests on api                                                                                      #####
########################################################################################################################
@when('I request "{endpoint}"')
@when('I request "{endpoint}" on "{server}"')
@when('I request "{endpoint}" with method "{method}"')
def request_endpoint(context, endpoint, method="GET", body=None):
    """Make a request to api. Set response in context.
    By default it will send the request on api.
    """
    url = "{}{}".format(
        context.BASE_URL, jinja2.Template(endpoint).render(**context.scenario_storage)
    )
    print("{} on {}".format(method, url))
    print("With body {}".format(body))
    context.response = requests.request(method, url, data=body, headers=context.HEADERS)
    print("Status code: {}".format(context.response.status_code))
    print("Response Body: {}".format(context.response.content))


@when('I request "{endpoint}" with query param')
def request_endpoint_with_query(context, endpoint):
    """ Make a request to api. Set response in context. """
    print(context.table)
    request_endpoint(
        context,
        "{}?{}".format(
            endpoint,
            urllib.parse.urlencode(
                dict(
                    [
                        (
                            row[0],
                            jinja2.Template(row[1]).render(**context.scenario_storage),
                        )
                        for row in context.table
                    ]
                )
            ),
        ),
    )


@when('I request "{endpoint}" with method "{method}" with body')
def request_endpoint_with_body(context, endpoint, method):
    """ Convert table to json and request {endpoint}. """
    body = {}
    for content in context.table:
        content_type = content[2] if (len(content) == 3) else "string"
        body[content[0]] = convert_value_to(
            convert_special_value(
                jinja2.Template(content[1]).render(**context.scenario_storage)
            ),
            content_type,
        )
    request_endpoint(context, endpoint, method, json.dumps(body))


@when('I request "{endpoint}" with method "{method}" with array body')
def request_endpoint_with_body_as_array(context, endpoint, method):
    """ Convert array body to json and request {endpoint}. """
    body = []
    for content in context.table:
        content_type = content['type']
        content_key = content['key']
        length = 0

        for key in context.table.headings:
            if key in ['type', 'key']:
                continue
            length += 1

            if len(body) < length:
                body.append({})

            body[length - 1][content_key] = convert_value_to(
                convert_special_value(
                    jinja2.Template(content[key]).render(**context.scenario_storage)
                ),
                content_type,
            )
    print(body)
    request_endpoint(context, endpoint, method, json.dumps(body))


@when('I request "{endpoint}" with method "{method}" with query param and body')
def request_endpoint_with_query_and_body(context, endpoint, method):
    """ Request {endpoint} with given params and body. """
    body_content = [content for content in context.table if content[3] == "body"]
    query_content = [content for content in context.table if content[3] == "param"]
    body = {}
    for content in body_content:
        content_type = content[2] if (len(content) == 3) else "string"
        if content[1] == "null":
            body[content[0]] = None
        elif content[1] == "empty":
            body[content[0]] = ""
        else:
            body[content[0]] = convert_value_to(
                jinja2.Template(content[1]).render(**context.scenario_storage),
                content_type,
            )
    query_params = urllib.parse.urlencode(
        dict(
            [
                (
                    param[0],
                    jinja2.Template(param[1]).render(**context.scenario_storage),
                )
                for param in query_content
            ]
        )
    )
    request_endpoint(
        context,
        "{}?{}".format(endpoint, query_params),
        method,
        json.dumps(body),
    )


@then('I set response "{key}" to "{value}"')
def store_response_value_to_context(context, key, value):
    """ Store response value in scenario storage. """
    data = context.response.json()
    if key not in dict(data):
        raise ValueError("Attribute {} not exists in response".format(key))
    print("Set {} in context storage with value: {}".format(value, data[key]))
    context.scenario_storage[value] = data[key]


@then('I set "{key}", which is in the first resource of the response, to "{value}"')
def store_first_response_value_to_context(context, key, value):
    """ Store response value in scenario storage. """
    data = context.response.json()
    if key not in dict(data["resources"][0]):
        raise ValueError(
            "Attribute {} not exists in the first resource of the response.".format(key)
        )
    print(
        "Set {} in context storage with the value: {}".format(
            value, data["resources"][0].get(key)
        )
    )
    context.scenario_storage[value] = data["resources"][0].get(key)


@then('I expect response contains these attributes "{keys}"')
def check_response_attribute(context, keys):
    """Check if response contains attributes.
    This step includes replacement of {{parameters}} with corresponding value in scenario storage."""
    data = context.response.json()
    list = jinja2.Template(keys).render(**context.scenario_storage).split(",")
    assert len(list) == len(data)

    for item in data:
        assert item in list


@then('I expect response body is "{value}"')
@then('I expect response body is "{value}" as "{type}"')
def check_response_body(context, value, type="string"):
    """Check if response body equals to value.
    This step includes replacement of {{parameters}} with corresponding value in scenario storage."""
    data = context.response.json()
    print(
        "Expected {0}, got {1}".format(
            convert_value_to(
                jinja2.Template(value).render(**context.scenario_storage), type
            ),
            data,
        )
    )
    assert check_special_value(
        convert_value_to(
            jinja2.Template(value).render(**context.scenario_storage), type
        ),
        data,
    )


@then('I expect response attribute "{key}" is "{value}"')
@then('I expect response attribute "{key}" is "{value}" as "{type}"')
def check_response_value(context, key, value, type="string"):
    """Check if response contains attribute.
    This step includes replacement of {{parameters}} with corresponding value in scenario storage."""
    data = context.response.json()
    print(
        "Expected {0}, got {1}".format(
            convert_value_to(
                jinja2.Template(value).render(**context.scenario_storage), type
            ),
            data[key],
        )
    )
    assert check_special_value(
        convert_value_to(
            jinja2.Template(value).render(**context.scenario_storage), type
        ),
        data[key],
    )


@then('I expect response json attribute "{path}" is "{value}"')
@then('I expect response json attribute "{path}" is "{value}" as "{type}"')
def check_response_json_path(context, path, value, type="string"):
    """ Check if the response contains the given value for the given json path. """
    data = context.response.json()
    value = data
    for key in path.split("/"):
        try:
            value = value[int(key)]
        except ValueError:
            value = value[key]
    print(
        "Expected {0}, got {1}".format(
            convert_value_to(
                jinja2.Template(str(value)).render(**context.scenario_storage), type
            ),
            value,
        )
    )
    assert check_special_value(
        convert_value_to(
            jinja2.Template(str(value)).render(**context.scenario_storage), type
        ),
        value,
    )


@then('I expect one resource of response contains an attribute "{key}" with "{value}"')
@then(
    'I expect one resource of response contains an attribute "{key}" with "{value}" as "{type}"'
)
def check_response_resources_attribute(context, key, value, type="string"):
    """Check if minimum one resource response contains attribute.
    This step includes replacement of {{parameters}} with corresponding value in scenario storage."""
    data = context.response.json()
    value_output = convert_value_to(
        jinja2.Template(value).render(**context.scenario_storage), type
    )
    is_present = False
    for item in data["resources"]:
        if check_special_value(
            value_output,
            str(item[key]),
        ):
            is_present = True
            break
    print(
        "Response does not contain attribute {} with value {}".format(key, value_output)
    )
    assert is_present


@then(
    'I expect all resources of response to have the attribute "{key}" to be in "{values}"'
)
@then(
    'I expect all resources of response to have the attribute "{key}" to be in "{values}" as "{my_type}"'
)
def check_all_response_resources_attribute(context, key, values, my_type="string"):
    """Check if all resources response contains attribute.
    This step includes replacement of {{parameters}} with corresponding value in scenario storage."""
    data = context.response.json()
    templated_values = jinja2.Template(values).render(**context.scenario_storage)
    templated_values = [
        convert_value_to(value, my_type) for value in templated_values.split(",")
    ]
    for resource in data["resources"]:
        check = key in dict(resource) and True in [
            check_special_value(val, resource[key]) for val in templated_values
        ]
        if not check:
            print(
                "Response does not contain attribute {} and so its value ({}) is not in {}".format(
                    key, resource[key], templated_values
                )
            )

        assert check


@then('I expect "{status}" as a status code')
def expect_status(context, status):
    """ Check if {status} is equal to response status. """
    print("Expected {} but it is {}".format(status, context.response.status_code))
    assert context.response.status_code in [int(i) for i in status.split(",")]


@then('I expect "{variable_name}" is "{value}"')
@then('I expect "{variable_name}" is "{value}" as "{my_type}')
def check_value(context, variable_name, value, my_type="string"):
    """Check if {value} is equal to '{variable_name}' attribute in response json."""
    current_value = convert_value_to(context.response.json()[variable_name], my_type)
    expected_value = convert_value_to(value, my_type)
    print("Expected {} but it is {}".format(value, current_value))
    assert check_special_value(expected_value, current_value)


@then('I wait "{timer}" seconds')
def wait_seconds(context, timer):
    """ Wait x seconds. """
    time.sleep(int(timer))
