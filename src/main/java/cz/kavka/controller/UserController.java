package cz.kavka.controller;

import cz.kavka.dto.ForgottenPasswordDto;
import cz.kavka.dto.PasswordChangeDto;
import cz.kavka.dto.UserDto;
import cz.kavka.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static cz.kavka.constant.ConstantNameResolver.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @GetMapping("/login")
    public String renderLoginPage() {
        return USER_LOGIN_TEMPLATE;
    }


    @PostMapping("/admin/logout")
    public String logoutUser() {
        userService.handleLogout();
        return REDIRECT + USER_LOGIN;
    }

    /**
     * This method handles unsupported get request od logout url to avoid error
     * on client site
     *
     * @return default admin page
     */
    @GetMapping("/admin/logout")
    public String getAdminPage() {
        return REDIRECT + USER_ADMIN;
    }


    @GetMapping("/registration")
    public String renderRegistrationForm(UserDto userDto) {
        return USER_REGISTRATION_TEMPLATE;
    }

    @PostMapping("/registration")
    public String registerUser(@Valid UserDto userDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            log.error("{}", result);
            return renderRegistrationForm(userDto);
        }
        if (!userDto.password().trim().equals(userDto.confirmPassword().trim())) {
            result.rejectValue("confirmPassword","error", "Hesla se neshodují");
            return renderRegistrationForm(userDto);
        }
        attributes.addFlashAttribute(SUCCESS, "uživatel vytvořen");
        userService.createUser(userDto);
        return REDIRECT + USER_LOGIN;
    }

    @GetMapping("/zapomenute-heslo")
    public String renderForgottenPasswordForm(ForgottenPasswordDto forgottenPasswordDto) {
        return USER_FORGOTTEN_PASSWORD_TEMP;


    }

    @PostMapping("/zapomenute-heslo")
    public String handleForgottenPassword(
            @Valid ForgottenPasswordDto forgottenPasswordDto,
            BindingResult result,
            RedirectAttributes attributes) {

        if (result.hasErrors()) {
            return renderForgottenPasswordForm(forgottenPasswordDto);
        }
        userService.handleForgottenPassword(forgottenPasswordDto.username());
        attributes.addFlashAttribute("success", "Heslo úspěšně resetováno");
        return REDIRECT + USER_LOGIN;
    }

    @GetMapping("/admin/profil")
    public String renderUserProfile(Model model) {
        var currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        return USER_ADMIN_PROFILE_TEMP;
    }

    @GetMapping("/admin/zmena-hesla")
    public String renderPasswordChangeForm(PasswordChangeDto passwordChangeDto) {
        return USER_PASSWORD_CHANGE_TEMP;

    }

    @PostMapping("/admin/zmena-hesla")
    public String handlePasswordChange(
            @Valid PasswordChangeDto passwordChangeDto,
            BindingResult result,
            RedirectAttributes attributes
    ) {
        if (result.hasErrors()) {
            return renderPasswordChangeForm(passwordChangeDto);
        }

        if (!passwordChangeDto.password().trim().equals(passwordChangeDto.confirmPassword().trim())) {
            result.rejectValue("confirmPassword","error", "Hesla se neshodují");
            return renderPasswordChangeForm(passwordChangeDto);
        }

        attributes.addAttribute("success", "Změna hesla proběhla úspěšně");
        return REDIRECT + USER_PROFILE;

    }
}
