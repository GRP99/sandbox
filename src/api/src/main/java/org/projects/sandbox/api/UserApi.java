package org.projects.sandbox.api;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.projects.sandbox.model.UserCreate;
import org.projects.sandbox.model.UserUpdate;

@Path("/user")
public interface UserApi {
    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getUser(@PathParam("username") String username);

    @PATCH
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateUser(@PathParam("username") String username,
                        UserUpdate userUpdate);

    @DELETE
    @Path("/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response deleteUser(@PathParam("username") String username);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response getAllUsers(@QueryParam("offset") int offset,
                         @QueryParam("limit") int limit);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createUser(UserCreate userCreate);
}
