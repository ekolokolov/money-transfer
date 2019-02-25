package ea.kolokolov.endpoint;

import ea.kolokolov.model.User;
import ea.kolokolov.service.UserInfoService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.status;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserEndpoint {

    private UserInfoService userInfoService;

    @Inject
    public UserEndpoint(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GET
    @Path("/{login}")
    public Response get(@PathParam("login") String login) {
//        if (login.isEmpty()) throw new BadRequestException();
        return ok(userInfoService.getUser(login)).build();
    }

    @GET
    public Response getUsers() {
        return ok(userInfoService.getUsers()).build();
    }

    /**
     * Unsupported operation
     *
     * @return Http Status 403 (Forbidden)
     */
    @POST
    public Response createUser(User user) {
        return status(FORBIDDEN).build();
    }

    /**
     * Unsupported operation
     *
     * @return Http Status 403 (Forbidden)
     */
    @PUT
    public Response changeUser(User user) {
        return status(FORBIDDEN).build();
    }

    /**
     * Unsupported operation
     *
     * @return Http Status 403 (Forbidden)
     */
    @DELETE
    public Response deleteUser(User user) {
        return status(FORBIDDEN).build();
    }
}
