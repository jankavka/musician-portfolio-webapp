package cz.kavka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String renderHome(){
        return "public/pages/home";
    }

    @GetMapping("/admin")
    public String renderAdminHome(){
        return "admin/admin-home";
    }
}
