package ea.kolokolov.exception.handler;

import ea.kolokolov.exception.OperationForbiddenException;
import org.eclipse.jetty.http.HttpStatus;

import javax.inject.Singleton;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Singleton
public class OperationForbiddenHandler implements ExceptionMapper<OperationForbiddenException> {

    @Override
    public Response toResponse(OperationForbiddenException e) {
        return Response
                .status(HttpStatus.FORBIDDEN_403)
                .entity(e.getMessage())
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }
}
