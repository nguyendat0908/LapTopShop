package com.DatLeo.LapTopShop.controller.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ErrorController {
    
    @GetMapping("/error/401")
    public String getError401Page(Model model) {
        return "error/401";
    }

    @GetMapping("/error/404")
    public String getError404Page(Model model) {
        return "error/404";
    }

    @GetMapping("/error/500")
    public String getError500Page(Model model) {
        return "error/500";
    }
    
}
