package com.api.service;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.http.HttpMethod;

/**
 * Interface for HttpService.
 */
public interface IHttpService {

    /**
     * Invoke HTTP request with defined method.
     *
     * @param method
     *            HTTP method to use.
     * @param uri
     *            URI web resource URI. May contain template parameters. Must not be null.
     * @param body
     *            Request entity.
     * @param queryParameters
     *            Query parameters to set at the request.
     * @param headers
     *            Headers to set at the request.
     * @param authentication
     *            Authentication feature to use.
     * @return Invocation response.
     */
    Response request(HttpMethod method, String uri, Entity<?> body, MultivaluedHashMap<String, Object> queryParameters,
            MultivaluedHashMap<String, Object> headers, HttpAuthenticationFeature authentication);

    /**
     * Invoke HTTP GET method.
     *
     * @param uri
     *            URI web resource URI. May contain template parameters. Must not be null.
     * @param queryParameters
     *            Query parameters to set at the request.
     * @param headers
     *            Headers to set at the request.
     * @param authentication
     *            Authentication feature to use.
     * @return Invocation response.
     */
    Response get(String uri, MultivaluedHashMap<String, Object> queryParameters,
            MultivaluedHashMap<String, Object> headers, HttpAuthenticationFeature authentication);

    /**
     * Invoke HTTP POST method.
     *
     * @param uri
     *            URI web resource URI. May contain template parameters. Must not be null.
     * @param body
     *            Request entity.
     * @return Invocation response.
     */
    Response post(String uri, Entity<?> body);

    /**
     * Invoke HTTP DELETE method.
     *
     * @param uri
     *            URI web resource URI. May contain template parameters. Must not be null.
     * @return Invocation response.
     */
    Response delete(String uri);

    /**
     * Invoke HTTP PUT method.
     *
     * @param uri
     *            URI web resource URI. May contain template parameters. Must not be null.
     * @param body
     *            Request entity.
     * @return Invocation response.
     */
    Response put(String uri, Entity<?> body);
}
