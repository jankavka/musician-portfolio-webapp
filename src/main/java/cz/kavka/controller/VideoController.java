package cz.kavka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/video")
public class VideoController {

    @GetMapping
    public String renderVideo(){
        return "public/pages/videos";
    }

    @GetMapping("/admin")
    public String renderAdminVideo(){
        return "admin/videos/index";
    }

    @GetMapping("/admin/novy")
    public String renderCreateForm(){
        return "admin/videos/create";
    }

    @GetMapping("/admin/upravit")
    public String renderEditForm(){
        return "admin/videos/edit";
    }
}
