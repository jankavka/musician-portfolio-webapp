package cz.kavka.controller;

import cz.kavka.service.AlbumService;
import cz.kavka.service.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    private final AlbumService albumService;

    @GetMapping("/foto")
    public String renderPhotos(Model model) {
        var allPhotos = photoService.getAllPhotos();
        model.addAttribute("allPhotos", allPhotos);
        return "public/pages/photos";
    }

    @GetMapping("/admin/foto")
    public String renderAdminPhotos(Model model) {
        var allAlbums = albumService.getAllAlbums();
        model.addAttribute("albums", allAlbums);
        return "admin/photos/index";
    }

    @GetMapping("/admin/foto/novy")
    public String renderCreatePhotoForm(
            Model model) {
        var allAlbums = albumService.getAllAlbums();
        model.addAttribute("albums", allAlbums);
        return "admin/photos/create-photo";
    }

    @PostMapping("/admin/foto/novy")
    public String createPhoto(
            @RequestParam("id") Long albumId,
            @RequestPart("files") MultipartFile[] multipartFiles,
            RedirectAttributes attributes
    ) {
        attributes.addFlashAttribute("success", "Foto úspěšně uloženo");
        photoService.savePhoto(multipartFiles, albumId);
        return "redirect:/admin/foto";

    }

    @GetMapping("/admin/foto/vymazat/{id}")
    public String deletePhoto(@PathVariable Long id, RedirectAttributes attributes, HttpServletRequest req) {
        var albumId = photoService.getPhoto(id).album().id();
        photoService.deletePhoto(id);
        attributes.addFlashAttribute("success", "Fotka úspěšně vymazána");
        return "redirect:/admin/album/" + albumId;
    }
}
