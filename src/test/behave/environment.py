#!/usr/bin/python3
""" Module to manage behave environment. """

import os
import re
import requests


def filter_version_tag(context, itm):
    """Skip {itm} that doesn't match version limits
    {itm} can be either a feature or a scenario.
    """
    # minimum and maximum version check
    for tag in itm.tags:
        minimum_version = re.match(r"^v(\d+)$", tag)
        maximum_version = re.match(r"^V(\d+)$", tag)
        if (
            minimum_version
            and int(minimum_version.groups()[0]) > int(context.VERSION.split(".")[0])
        ) or (
            maximum_version
            and int(maximum_version.groups()[0]) < int(context.VERSION.split(".")[0])
        ):
            itm.skip()


def before_all(context):
    """ Set behave context before all. """
    context.BASE_URL = "{}/api/".format(
        context.config.userdata.get(
            "URL",
            os.environ.get(
                "URL",
                "http://localhost:8080/PROJECT_NAME",
            ),
        )
    )
    context.BASE_SERVER = "{}/".format(
        context.config.userdata.get(
            "URL",
            os.environ.get(
                "URL",
                "http://localhost:8080/PROJECT_NAME",
            ),
        )
    )

    context.HEADERS = {"Accept": "application/json", "Content-type": "application/json"}
    try:
        url = "{}{}".format(context.BASE_URL, "version")
        context.VERSION = requests.get(url).json()["version"]
    except:
        context.VERSION = None


def before_feature(context, feature):
    """ Apply filter by tag before feature. """
    context.global_directory = False
    context.outputs = list()
    filter_version_tag(context, feature)


def before_scenario(context, scenario):
    """ Apply filter by tag before scenario. """
    if context.VERSION is None:
        raise ValueError("Unable to get API version")
    context.scenario_storage = {}
    filter_version_tag(context, scenario)
