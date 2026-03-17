package com.lexho.user_service.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String path = request.getRequestURI();

        // 🔥 public APIs allow
        if (path.startsWith("/auth")) {
            return true;
        }

        Object phone = request.getAttribute("phone");

        if (phone == null) {
            response.setStatus(401);
            response.getWriter().write("Unauthorized");
            return false;
        }

        return true;
    }
}