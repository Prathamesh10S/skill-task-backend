package com.skill.taskassigner.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AdminAuth {

    @Value("${app.admin.key}")
    private String adminKey;

    public void checkAdmin(String key) {
        if (key == null || !adminKey.equals(key)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Admin access required"
            );
        }
    }
}
