package cz.kavka.controller;

import cz.kavka.dto.ConcertDto;
import cz.kavka.service.ConcertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static cz.kavka.constant.ConstantNameResolver.*;

@Controller
@RequiredArgsConstructor
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping("/koncerty")
    public String renderConcerts(Model model) {
        var allConcerts = concertService.getAllConcerts();
        model.addAttribute(CONCERTS, allConcerts);
        return CONCERT_INDEX_TEMPLATE;
    }

    @GetMapping("/admin/koncerty")
    public String renderAdminConcerts(Model model) {
        var allConcerts = concertService.getAllConcerts();
        model.addAttribute(CONCERTS, allConcerts);
        return CONCERT_ADMIN_INDEX_TEMPLATE;
    }

    @GetMapping("/admin/koncerty/{id}")
    public String renderConcertDetail(@PathVariable Long id, Model model) {
        var concert = concertService.getConcert(id);
        model.addAttribute(CONCERT, concert);
        return CONCERT_ADMIN_DETAIL_TEMPLATE;
    }

    @GetMapping("/admin/koncerty/novy")
    public String renderCreateForm(ConcertDto concertDto) {

        return "admin/concerts/create";
    }

    @PostMapping("/admin/koncerty/novy")
    public String createConcert(@Valid ConcertDto concertDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return renderCreateForm(concertDto);
        }
        concertService.createConcert(concertDto);
        attributes.addFlashAttribute(
                SUCCESS, "Koncert s názvem " + concertDto.name() + " úspěšně vytvořen");
        return REDIRECT + CONCERT_ADMIN_INDEX;
    }

    @GetMapping("/admin/koncerty/upravit/{id}")
    public String renderEditForm(@PathVariable Long id, Model model) {
        var concert = concertService.getConcert(id);
        model.addAttribute("concertDto", concert);
        return CONCERT_ADMIN_EDIT_TEMPLATE;
    }

    @PostMapping("/admin/koncerty/upravit/{id}")
    public String editConcert(
            @PathVariable Long id,
            @Valid ConcertDto concertDto,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            Model model = new BindingAwareModelMap();
            model.addAttribute("concertDto", concertDto);
            return renderEditForm(id, model);
        }
        concertService.editConcert(concertDto, id);
        attributes.addFlashAttribute(
                SUCCESS, "Koncert s názvem" + concertDto.name() + " úspěšně upraven");
        return REDIRECT + CONCERT_ADMIN_INDEX;

    }


    @GetMapping("/admin/koncerty/vymazat/{id}")
    public String deleteConcert(@PathVariable Long id, RedirectAttributes attributes) {
        concertService.deleteConcert(id);
        attributes.addFlashAttribute(SUCCESS, "Koncert úspěšně vymazán");
        return REDIRECT + CONCERT_ADMIN_INDEX;
    }
}
