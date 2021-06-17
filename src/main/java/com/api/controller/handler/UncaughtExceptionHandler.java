package com.api.controller.handler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.api.model.error.ErrorDTO;
import com.api.model.error.ErrorType;

/**
 * Handle all uncaught exception and send appropriate response.
 */
@Provider
public class UncaughtExceptionHandler implements ExceptionMapper<Throwable> {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UncaughtExceptionHandler.class);

    @Override
    public final Response toResponse(final Throwable exception) {
        LOGGER.error("Uncaught error", exception);
        final ErrorDTO error = new ErrorDTO(ErrorType.INTERNAL_ERROR.getMessage(), null, null, exception);

        return Response.status(ErrorType.INTERNAL_ERROR.getStatus()).entity(error).type(MediaType.APPLICATION_JSON)
                .build();
    }

}
