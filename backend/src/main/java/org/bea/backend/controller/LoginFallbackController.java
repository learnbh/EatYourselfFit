package org.bea.backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginFallbackController {

    @GetMapping("/login/success")
    public String loginSuccess(HttpServletRequest request) {
        // Forward to index.html so the SPA router can handle the client-side path
        return "forward:/index.html";
    }

}
