package com.api.service;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.api.model.error.ErrorType;
import com.api.model.error.GlobalException;

/**
 * HTTP service to make HTTP call.
 */
@Service
public class HttpService implements IHttpService {

    /**
     * Standard HTTP client.
     */
    private final Client client = ClientBuilder.newClient();

    @Override
    public final Response request(final HttpMethod method, final String uri, final Entity<?> body,
            final MultivaluedHashMap<String, Object> queryParameters, final MultivaluedHashMap<String, Object> headers,
            final HttpAuthenticationFeature authentication) {
        if (method == null) {
            throw new GlobalException(ErrorType.WRONG_HTTP_METHOD, "method");
        }
        WebTarget target = this.client.target(uri);

        target = this.setQueryParametersToTarget(target, queryParameters);
        this.setAuthentication(target, authentication);

        final Builder builder = target.request();

        this.setHeaders(builder, headers);

        if (HttpMethod.GET.equals(method)) {
            return builder.get();
        } else if (HttpMethod.DELETE.equals(method)) {
            return builder.delete();
        } else if (HttpMethod.POST.equals(method)) {
            return builder.post(body);
        } else if (HttpMethod.PUT.equals(method)) {
            return builder.put(body);
        }

        throw new UnsupportedOperationException(String.format("Method %s is not yet supported.", method.toString()));
    }

    @Override
    public final Response get(final String uri, final MultivaluedHashMap<String, Object> queryParameters,
            final MultivaluedHashMap<String, Object> headers, final HttpAuthenticationFeature authentication) {
        return this.request(HttpMethod.GET, uri, null, queryParameters, headers, authentication);
    }

    @Override
    public final Response delete(final String uri) {
        return this.request(HttpMethod.DELETE, uri, null, null, null, null);
    }

    @Override
    public final Response post(final String uri, final Entity<?> body) {
        return this.request(HttpMethod.POST, uri, body, null, null, null);
    }

    @Override
    public final Response put(final String uri, final Entity<?> body) {
        return this.request(HttpMethod.PUT, uri, body, null, null, null);
    }

    /**
     * Set query parameters to target.
     *
     * @param target
     *            Target.
     * @param queryParameters
     *            Query parameters, can be null so no parameters are set.
     * @return Updated target.
     */
    private WebTarget setQueryParametersToTarget(final WebTarget target,
            final MultivaluedHashMap<String, Object> queryParameters) {
        if (queryParameters == null) {
            return target;
        }

        WebTarget finalTarget = target;

        for (final Entry<String, List<Object>> set : queryParameters.entrySet()) {
            finalTarget = finalTarget.queryParam(set.getKey(),
                    set.getValue().toArray(new Object[set.getValue().size()]));
        }
        return finalTarget;
    }

    /**
     * Set authentication to target.
     *
     * @param target
     *            Target.
     * @param authentication
     *            Authentication feature, can be null so no authentication is set.
     */
    private void setAuthentication(final WebTarget target, final HttpAuthenticationFeature authentication) {
        if (authentication == null) {
            return;
        }

        target.register(authentication);
    }

    /**
     * Set headers to request builder.
     *
     * @param builder
     *            Request builder.
     * @param headers
     *            Headers, can be null so no headers are set.
     */
    private void setHeaders(final Builder builder, final MultivaluedHashMap<String, Object> headers) {
        if (headers == null) {
            return;
        }

        builder.headers(headers);
    }
}
