package com.api.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.api.model.dto.version.Version;
import com.api.utils.Configuration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller to manage project version.
 */
@Path("/version")
@Produces(MediaType.APPLICATION_JSON)
@Controller
public class VersionController {

    /**
     * Configuration class.
     */
    @Autowired
    private Configuration configuration;

    /**
     * Endpoint to return version of project.
     *
     * @return Project version.
     */
    @GET
    @Operation(summary = "Get version of project.")
    @Tag(name = "Version")
    @ApiResponse(responseCode = "200", description = "Version of project.",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Version.class)))
    public final Version getVersion() {
        return new Version(this.configuration.getVersion());
    }
}
