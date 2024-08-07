package org.mude.security
        ;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpServletResponse implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN, "Forbidden");
    }
}