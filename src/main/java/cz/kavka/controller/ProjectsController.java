package cz.kavka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projekty")
public class ProjectsController {

    @GetMapping
    public String renderProjects(){
        return "public/pages/projects";
    }

    @GetMapping("/admin")
    public String renderAdminProjects(){
        return "admin/projects/index";
    }

    @GetMapping("/admin/novy")
    public String renderCreateForm(){
        return "admin/projects/create";
    }

    @GetMapping("/admin/upravit")
    public String renderEditForm(){
        return "admin/projects/edit";
    }
}
