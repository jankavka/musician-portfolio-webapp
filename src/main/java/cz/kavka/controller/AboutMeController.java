package cz.kavka.controller;

import cz.kavka.dto.AboutMeDto;
import cz.kavka.service.AboutMeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static cz.kavka.constant.ConstantNameResolver.*;

@Controller
@RequiredArgsConstructor
public class AboutMeController {

    private final AboutMeService aboutMeService;

    @GetMapping("/o-me")
    public String renderAboutMe(Model model) {
        model.addAttribute(ABOUT_ME, aboutMeService.getInfo());
        return ABOUT_INDEX_PUBLIC_TEMPLATE;
    }

    @GetMapping("/admin/o-me")
    public String renderAdminAboutMe(Model model) {
        AboutMeDto aboutMeDto = aboutMeService.getInfo();
        model.addAttribute(ABOUT_ME, aboutMeDto);
        return ABOUT_INDEX_ADMIN_TEMPLATE;
    }

    @PostMapping("/admin/o-me/upravit")
    public String updateAboutMe(AboutMeDto aboutMeDto) {
        aboutMeService.editInfo(aboutMeDto);

        return REDIRECT + ABOUT_INDEX_ADMIN;
    }

    @GetMapping("/admin/o-me/upravit")
    public String renderEditForm(Model model) {
        model.addAttribute(ABOUT_ME, aboutMeService.getInfo());
        return ABOUT_EDIT_ADMIN_TEMPLATE;
    }
}
