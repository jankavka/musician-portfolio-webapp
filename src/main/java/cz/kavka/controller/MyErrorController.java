package cz.kavka.controller;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class MyErrorController implements ErrorController {

    @GetMapping
    public String gerErrorPage(){
        return "public/pages/error";
    }
}
