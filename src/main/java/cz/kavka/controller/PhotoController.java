package cz.kavka.controller;

import cz.kavka.service.AlbumService;
import cz.kavka.service.PhotoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static cz.kavka.constant.ConstantNameResolver.*;

@Controller
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    private final AlbumService albumService;

    @GetMapping("/foto")
    public String renderPhotos(Model model) {
        var allAlbums = albumService.getAllAlbums();
        model.addAttribute("allAlbums", allAlbums);
        return PHOTO_INDEX_PUBLIC_TEMPLATE;
    }

    @GetMapping("/admin/foto")
    public String renderAdminPhotos(Model model) {
        var allAlbums = albumService.getAllAlbums();
        model.addAttribute("albums", allAlbums);
        return PHOTO_INDEX_ADMIN_TEMPLATE;
    }

    @GetMapping("/admin/foto/novy")
    public String renderCreatePhotoForm(
            Model model) {
        var allAlbums = albumService.getAllAlbums();
        model.addAttribute("albums", allAlbums);
        return PHOTO_CREATE_ADMIN_TEMPLATE;
    }

    @PostMapping("/admin/foto/novy")
    public String createPhoto(
            @RequestParam("id") Long albumId,
            @RequestPart("files") MultipartFile[] multipartFiles,
            RedirectAttributes attributes
    ) {
        attributes.addFlashAttribute(SUCCESS, "Foto úspěšně uloženo");
        photoService.savePhoto(multipartFiles, albumId);
        return REDIRECT + PHOTO_INDEX_ADMIN;

    }

    @GetMapping("/admin/foto/vymazat/{id}")
    public String deletePhoto(@PathVariable Long id, RedirectAttributes attributes) {
        var albumId = photoService.getPhoto(id).album().id();
        photoService.deletePhoto(id);
        attributes.addFlashAttribute(SUCCESS, "Fotka úspěšně vymazána");
        return REDIRECT + ALBUM_DETAIL_ADMIN + albumId;
    }
}
