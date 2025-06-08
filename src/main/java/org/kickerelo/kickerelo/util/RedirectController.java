package org.kickerelo.kickerelo.util;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
    @GetMapping("/")
    public String redirectToApp() {
        return "redirect:/app";
    }
}
