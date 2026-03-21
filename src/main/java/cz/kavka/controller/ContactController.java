package cz.kavka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kontakty")
public class ContactController {

    @GetMapping
    public String renderContact(){
        return "public/pages/contacts";
    }

    @GetMapping("/admin")
    public String renderAdminContact(){
        return "admin/contacts/index";
    }

    @GetMapping("/admin/upravit")
    public String renderEditForm(){
        return "admin/contacts/edit";
    }

}
