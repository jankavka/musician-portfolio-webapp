package cz.kavka.controller;

import cz.kavka.dto.AboutMeDto;
import cz.kavka.service.AboutMeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class AboutMeController {

    private final AboutMeService aboutMeService;

    private static final String ABOUT_ME = "aboutMeDto";

    @GetMapping("/o-me")
    public String renderAboutMe(Model model) {
        model.addAttribute(ABOUT_ME, aboutMeService.getInfo());
        return "public/pages/about-me";
    }

    @GetMapping("/admin/o-me")
    public String renderAdminAboutMe(Model model) {
        AboutMeDto aboutMeDto = aboutMeService.getInfo();
        model.addAttribute(ABOUT_ME, aboutMeDto);
        return "admin/about-me/index";
    }

    @PostMapping("/admin/o-me/upravit")
    public String updateAboutMe(AboutMeDto aboutMeDto) {
        aboutMeService.editInfo(aboutMeDto);

        return "redirect:/o-me/admin";
    }

    @GetMapping("/admin/o-me/upravit")
    public String renderEditForm(Model model) {
        model.addAttribute(ABOUT_ME, aboutMeService.getInfo());
        return "admin/about-me/edit";
    }
}
