package dmit2015.restclient;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

/**
 * This response mapper handles http status 400 (Bad Request) returned from Firebase Authentication
 * when the email/password is invalid by returning a custom error message.
 *
 */
public class FirebaseAuthenticationResponseMapper implements ResponseExceptionMapper<RuntimeException> {
    @Override
    public RuntimeException toThrowable(Response response) {
        if (response.getStatus() == 400) {
            return new RuntimeException("Invalid login credentials.");
        }
        String message = String.format("Unknown error, status code %d", response.getStatus());
        return new RuntimeException(message);
    }

    @Override
    public int getPriority() {
        return ResponseExceptionMapper.super.getPriority();
    }

    @Override
    public boolean handles(int status, MultivaluedMap headers) {
        // Only handle status code 400 (Bad Request) when login fails.
        return status == 400;
    }
}