package cz.kavka.controller;

import cz.kavka.dto.ContactDto;
import cz.kavka.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static cz.kavka.constant.ConstantNameResolver.*;

@Controller
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/kontakty")
    public String renderContact(Model model) {
        var contacts = contactService.getContacts();
        model.addAttribute(CONTACT, contacts);
        return CONTACT_INDEX_PUBLIC_TEMPLATE;
    }

    @GetMapping("/admin/kontakty")
    public String renderAdminContact(Model model) {
        var contacts = contactService.getContacts();
        model.addAttribute(CONTACT, contacts);
        return CONTACT_INDEX_ADMIN_TEMPLATE;
    }

    @GetMapping("/admin/kontakty/upravit")
    public String renderEditForm(Model model) {
        var contacts = contactService.getContacts();
        model.addAttribute(CONTACT, contacts);
        return CONTACT_EDIT_ADMIN_TEMPLATE;
    }

    @PostMapping("/admin/kontakty/upravit")
    public String updateContacts(@Valid ContactDto contactDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            Model model = new BindingAwareModelMap();
            model.addAttribute(CONTACT, contactDto);
            return renderEditForm(model);
        }
        contactService.updateContacts(contactDto);
        attributes.addFlashAttribute(SUCCESS, "Contact úspěšně aktualizován");
        return REDIRECT + CONTACT_INDEX_ADMIN;
    }

}
