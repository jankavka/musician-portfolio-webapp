package cz.kavka.service.exception.handler;

import cz.kavka.service.exception.MultipartFilesEmptyException;
import cz.kavka.service.exception.WrongContentTypeException;
import cz.kavka.service.exception.WrongVideoSourceException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static cz.kavka.constant.ConstantNameResolver.*;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartFilesEmptyException.class)
    public String handleMultipartFilesEmpty(
            MultipartFilesEmptyException e,
            RedirectAttributes redirectAttributes
    ) {

        log.error(ERROR_MESSAGE_TEMPLATE, e.getMessage(), e.getClass());
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        return REDIRECT + PHOTO_CREATE_ADMIN;
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(
            NullPointerException e,
            RedirectAttributes attributes,
            HttpServletRequest req
    ) {

        log.error(ERROR_MESSAGE_TEMPLATE, e.getMessage(), e.getClass());
        if (req.getRequestURI().equals(PHOTO_CREATE_ADMIN) || req.getRequestURI().equals(PROJECT_CREATE_ADMIN)) {
            attributes.addFlashAttribute(MISSING_FILE, e.getMessage());
            return REDIRECT + req.getRequestURI();
        }
        attributes.addFlashAttribute(ERROR, SOMETHING_WRONG);
        return REDIRECT + PHOTO_INDEX_ADMIN;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e,
            RedirectAttributes attributes,
            HttpServletRequest req
    ) {

        log.error(ERROR_MESSAGE_TEMPLATE, e.getMessage(), e.getClass());
        if (req.getRequestURI().equals(PHOTO_CREATE_ADMIN)) {

            attributes.addFlashAttribute(ERROR, "Album musí být vybráno");
            return REDIRECT + req.getRequestURI();
        }
        attributes.addFlashAttribute(ERROR, SOMETHING_WRONG);
        return PHOTO_INDEX_ADMIN;
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public String handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException e,
            RedirectAttributes attributes,
            HttpServletRequest req
    ) {

        log.error(ERROR_MESSAGE_TEMPLATE, e.getMessage(), e.getClass());
        if (req.getRequestURI().equals(ALBUM_CREATE_ADMIN) || req.getRequestURI().equals(PHOTO_CREATE_ADMIN)) {
            attributes.addFlashAttribute(ERROR, "Maximální velikost pro nahrávání souborů je 10 MB");

            return REDIRECT + req.getRequestURI();

        }
        attributes.addFlashAttribute(ERROR, SOMETHING_WRONG);
        return PHOTO_INDEX_ADMIN;

    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(
            IllegalStateException e,
            RedirectAttributes attributes,
            HttpServletRequest req
    ) {

        log.error(ERROR_MESSAGE_TEMPLATE, e.getMessage(), e.getClass());
        if (req.getRequestURI().equals(PHOTO_CREATE_ADMIN)) {
            attributes.addFlashAttribute(MISSING_FILE, "Soubor musí být vybrán");
            return REDIRECT + req.getRequestURI();
        }

        attributes.addFlashAttribute(ERROR, SOMETHING_WRONG);
        return PHOTO_INDEX_ADMIN;

    }

    @ExceptionHandler({InternalAuthenticationServiceException.class, UsernameNotFoundException.class})
    public String handleInternalAthServiceException(AuthenticationException e, RedirectAttributes attributes) {
        log.error("Auth error");
        attributes.addFlashAttribute("error", e.getMessage());
        return REDIRECT + USER_LOGIN;
    }

    @ExceptionHandler(MailException.class)
    public String handleMailException(MailException e, RedirectAttributes attributes) {
        log.error(e.getMessage());
        attributes.addFlashAttribute(
                "error", "Nepodařilo se odeslat mail s novým heslem. Zkuste to znovu");
        return REDIRECT + USER_LOGIN;
    }


    @ExceptionHandler(WrongVideoSourceException.class)
    public String handleWrongVideoSourceException(
            WrongVideoSourceException e,
            RedirectAttributes attributes
    ) {

        log.error(ERROR_MESSAGE_TEMPLATE, e.getMessage(), e.getClass());
        attributes.addFlashAttribute(ERROR, e.getMessage());
        return REDIRECT + VIDEO_ADMIN_CREATE;

    }

    @ExceptionHandler(WrongContentTypeException.class)
    public String handleWrongContentTypeException(
            WrongContentTypeException e,
            HttpServletRequest req,
            RedirectAttributes attributes
    ) {

        log.error("{}, {}", e.getMessage(), e.getClass());
        attributes.addFlashAttribute("missingFile", e.getMessage());
        return REDIRECT + req.getRequestURI();

    }


}
