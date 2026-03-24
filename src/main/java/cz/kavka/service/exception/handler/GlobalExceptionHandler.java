package cz.kavka.service.exception.handler;

import cz.kavka.service.exception.MultipartFilesEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartFilesEmptyException.class)
    public String handleMultipartFilesEmpty(
            MultipartFilesEmptyException exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", exception.getMessage());
        return "redirect:/foto/novy";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException exception, RedirectAttributes attributes) {
        log.error("{} {}", exception.getMessage(), exception.getLocalizedMessage());
        attributes.addFlashAttribute("error", "Internal server Errror: Něco se pokazilo :(");
        return "redirect:/foto/admin";
    }
}
