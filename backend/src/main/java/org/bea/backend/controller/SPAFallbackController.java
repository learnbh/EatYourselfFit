package org.bea.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SPAFallbackController {
    
    @GetMapping({
        "/login/success",
        "/recipe"
    })

    public String forwardToIndex() {
        // Forward to index.html so the SPA router can handle the route
        return "forward:/index.html";
    }
    
}
