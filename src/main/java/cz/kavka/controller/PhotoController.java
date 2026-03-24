package cz.kavka.controller;

import cz.kavka.service.AlbumService;
import cz.kavka.service.PhotoService;
import cz.kavka.service.exception.MultipartFilesEmptyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/foto")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    private final AlbumService albumService;

    @GetMapping
    public String renderPhotos(Model model) {
        var allPhotos = photoService.getAllPhotos();
        model.addAttribute("allPhotos", allPhotos);
        return "public/pages/photos";
    }

    @GetMapping("/admin")
    public String renderAdminPhotos(Model model) {
        var allPhotos = photoService.getAllPhotos();
        model.addAttribute("allPhotos", allPhotos);
        return "admin/photos/index";
    }

    @GetMapping("/novy")
    public String renderCreatePhotoForm(MultipartFile[] files, Model model) {
        var allAlbums = albumService.getAllAlbums();
        model.addAttribute("albums", allAlbums);
        return "admin/photos/create-photo";
    }

    @PostMapping("/novy")
    public String createPhoto(
            @RequestParam("files") MultipartFile[] multipartFiles,
            @RequestParam("id") Long albumId,
            RedirectAttributes attributes) {
        try {
            attributes.addFlashAttribute("success", "Foto úspěšně uloženo");
            photoService.savePhoto(multipartFiles, albumId);
            return "redirect:/foto/admin";
        } catch (NullPointerException e) {
            throw new MultipartFilesEmptyException("Soubory nesmí být prázdné");
        }
    }
}
