package cz.kavka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/foto")
public class PhotosController {

    @GetMapping
    public String renderPhotos(){
        return "public/pages/photos";
    }

    @GetMapping("/admin")
    public String renderAdminPhotos(){
        return "admin/photos/index";
    }

    @GetMapping("/admin/create")
    public String renderCreateForm(){
        return "admin/photos/create";
    }
}
