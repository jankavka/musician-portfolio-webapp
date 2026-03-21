package cz.kavka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/koncerty")
public class ConcertsController {

    @GetMapping
    public String renderConcerts(){
        return "public/pages/concerts";
    }

    @GetMapping("/admin")
    public String renderAdminConcerts(){
        return "admin/concerts/index";
    }

    @GetMapping("/admin/novy")
    public String renderCreateForm(){
        return "admin/concerts/create";
    }

    @GetMapping("/admin/upravit")
    public String renderEditForm(){
        return "admin/concerts/edit";
    }
}
