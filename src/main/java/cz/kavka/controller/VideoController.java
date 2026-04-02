package cz.kavka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VideoController {

    @GetMapping("/video")
    public String renderVideo(){
        return "public/pages/videos";
    }

    @GetMapping("/admin/video")
    public String renderAdminVideo(){
        return "admin/videos/index";
    }

    @GetMapping("/admin/video/novy")
    public String renderCreateForm(){
        return "admin/videos/create";
    }

    @GetMapping("/admin/video/upravit")
    public String renderEditForm(){
        return "admin/videos/edit";
    }
}
