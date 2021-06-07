package com.api.controller.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.model.error.GlobalException;

/**
 * Handle exception and send appropriate response.
 */
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<GlobalException> {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    public final Response toResponse(final GlobalException exception) {
        if (exception.needToBeLogged()) {
            LOGGER.error("General error (field: '{}', value: '{}')", exception.getError().getField(),
                    exception.getError().getValue(), exception);
        } else {
            LOGGER.error("General error (field: '{}', value: '{}', message: '{}')", exception.getError().getField(),
                    exception.getError().getValue(), exception.getMessage());
        }
        return Response.status(exception.getStatus()).entity(exception.getError()).type(MediaType.APPLICATION_JSON)
                .build();
    }

}
