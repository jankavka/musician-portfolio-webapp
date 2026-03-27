package cz.kavka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactController {

    @GetMapping("/kontakty")
    public String renderContact(){
        return "public/pages/contacts";
    }

    @GetMapping("/admin/kontakty")
    public String renderAdminContact(){
        return "admin/contacts/index";
    }

    @GetMapping("/admin/kontakty/upravit")
    public String renderEditForm(){
        return "admin/contacts/edit";
    }

}
