package com.asyraf.hospital.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.asyraf.hospital.entity.User;
import com.asyraf.hospital.repository.UserRepository;

@Component
public class TokenValidationInterceptor implements HandlerInterceptor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("token");

        if (!isValid(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid!");
            return false;
        }
        return true;
    }

    private boolean isValid(String token) {
        Optional<User> user = userRepository.findByToken(token);
        return user.isPresent();
    }

}
