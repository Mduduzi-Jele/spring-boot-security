package tutorial.app.security.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//The @Component annotation tells Spring to scan for this class and register it as a bean. This means that Spring will automatically create
//an instance of this class and make it available for injection into other classes.
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

//    The AuthEntryPointJwt class implements the AuthenticationEntryPoint interface. This interface defines a single method, commence(), which
//    is responsible for handling unauthorized access to protected resources.

//    The commence() method takes three parameters:
//    - HttpServletRequest: The HTTP request that was denied access to the protected resource.
//    - HttpServletResponse: The HTTP response that will be sent back to the client.
//    - AuthenticationException: The Exception that was thrown when the client attempted to access the protected resource without valid
//      authentication credentials.
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", authenticationException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authenticationException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
