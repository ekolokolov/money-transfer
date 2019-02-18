package ea.kolokolov.exception.handler;

import ea.kolokolov.exception.UserNotFoundException;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class UserNotFoundHandler implements ExceptionMapper<UserNotFoundException> {

    @Override
    public Response toResponse(UserNotFoundException e) {
        return Response
                .status(HttpStatus.NOT_FOUND_404)
                .entity(e.getMessage())
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }
}
