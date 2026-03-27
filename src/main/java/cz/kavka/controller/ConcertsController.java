package cz.kavka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConcertsController {

    @GetMapping("/koncerty")
    public String renderConcerts(){
        return "public/pages/concerts";
    }

    @GetMapping("/admin/koncerty")
    public String renderAdminConcerts(){
        return "admin/concerts/index";
    }

    @GetMapping("/admin/koncerty/novy")
    public String renderCreateForm(){
        return "admin/concerts/create";
    }

    @GetMapping("/admin/koncerty/upravit")
    public String renderEditForm(){
        return "admin/concerts/edit";
    }
}
