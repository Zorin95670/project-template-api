package com.api.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.api.model.authentication.UserRoleType;

/**
 * User controller.
 */
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class UserController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * Endpoint to return a specific project.
     *
     * @param id
     *            Deployment's id.
     * @return Deployment.
     * @throws OctoException
     *             On all database error.
     */
    @GET
    @Path("/me")
    @RolesAllowed(UserRoleType.ALL)
    public final Response getMyInformations() {
        return Response.ok("{ \"roles\": [\"ADMIN\"], \"user\": { \"login\": \"admin\", \"firstname\": \"Vincent\", \"lastname\": \"Moittie\", \"email\": \"test@gmail.com\" } }").build();
    }
}
