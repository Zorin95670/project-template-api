package com.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.api.model.authentication.UserRoleType;
import com.api.model.common.Resource;
import com.api.model.dto.alert.AlertDTO;
import com.api.service.UserService;

import io.swagger.v3.oas.annotations.servers.Server;

/**
 * Alerts controller.
 */
@Path("/alerts")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class AlertsController {

    /** Logger. **/
    private static final Logger LOGGER = LoggerFactory.getLogger(AlertsController.class);

    /**
     * Project service.
     */
    @Autowired
    private UserService service;

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
    @RolesAllowed(UserRoleType.ADMIN)
    public final Response getAlerts() {
        List<AlertDTO> alerts = new ArrayList<>();
        
        if (!service.isSecureAdministrator()) {
            AlertDTO alert = new AlertDTO();
            alert.setSeverity("critical");
            alert.setType("security");
            alert.setMessage("Administrator's password is not secure, please change it.");
            alerts.add(alert);
        }
        
        if (alerts.size() == 0) {
            return Response.noContent().build();
        }
        return Response.ok(new Resource<AlertDTO>(Long.valueOf(alerts.size()), alerts, 0, alerts.size())).build();
    }

}
