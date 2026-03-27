package cz.kavka.service.exception.handler;

import cz.kavka.service.exception.MultipartFilesEmptyException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String ERROR = "error";

    private static final String MISSING_FILE = "missingFile";

    private static final String REDIRECT_PHOTO_ADMIN = "redirect:/admin/foto";

    private static final String ADMIN_PHOTO_NEW = "/admin/foto/novy";

    private static final String ADMIN_ALBUM_NEW = "/admin/album/novy";

    private static final String REDIRECT = "redirect:";

    @ExceptionHandler(MultipartFilesEmptyException.class)
    public String handleMultipartFilesEmpty(
            MultipartFilesEmptyException e, RedirectAttributes redirectAttributes) {

        log.error(e.getMessage());
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        return "redirect:/foto/novy";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(
            NullPointerException e, RedirectAttributes attributes, HttpServletRequest req) {

        log.error(e.getMessage());
        if (req.getRequestURI().equals(ADMIN_PHOTO_NEW)) {
            attributes.addFlashAttribute(MISSING_FILE, e.getMessage());
            return REDIRECT + req.getRequestURI();
        }
        attributes.addFlashAttribute(ERROR, "Něco se pokazilo");
        return REDIRECT_PHOTO_ADMIN;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e, RedirectAttributes attributes, HttpServletRequest req) {

        log.error(e.getMessage());
        if (req.getRequestURI().equals(ADMIN_PHOTO_NEW)) {

            attributes.addFlashAttribute(ERROR, "Album musí být vybráno");
            return REDIRECT + req.getRequestURI();
        }
        attributes.addFlashAttribute(ERROR, "Něco se pokazilo");
        return REDIRECT_PHOTO_ADMIN;
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public String handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException e, RedirectAttributes attributes, HttpServletRequest req) {

        log.error(e.getMessage());
        if (req.getRequestURI().equals(ADMIN_ALBUM_NEW) || req.getRequestURI().equals(ADMIN_PHOTO_NEW)) {
            attributes.addFlashAttribute(ERROR, "Maximální velikost pro nahrávání souborů je 10 MB");

            return REDIRECT + req.getRequestURI();

        }
        attributes.addFlashAttribute(ERROR, "Internal Server Error: Upload size exceeded");
        return REDIRECT_PHOTO_ADMIN;

    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(
            IllegalStateException e, RedirectAttributes attributes, HttpServletRequest req) {
        log.error(e.getMessage());
        if (req.getRequestURI().equals(ADMIN_PHOTO_NEW)) {
            attributes.addFlashAttribute(MISSING_FILE, "Soubor musí být vybrán");
            return REDIRECT + req.getRequestURI();
        }

        attributes.addFlashAttribute(ERROR, "Něco se pokazilo");
        return REDIRECT_PHOTO_ADMIN;

    }


}
