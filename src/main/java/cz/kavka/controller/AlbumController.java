package cz.kavka.controller;

import cz.kavka.dto.AlbumDto;
import cz.kavka.service.AlbumService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/admin/album/novy")
    public String renderCreateAlbumForm(AlbumDto albumDto) {
        return "admin/photos/create-album";
    }

    @GetMapping("/admin/album/{id}")
    public String getAdminAlbumDetail(Model model, @PathVariable Long id) {
        var album = albumService.getAlbum(id);
        model.addAttribute("album", album);

        return "admin/photos/album-detail";
    }


    @PostMapping("/admin/album/novy")
    public String createNewAlbum(
            @Valid AlbumDto albumDto,
            BindingResult result,
            @Nullable @RequestParam("files") MultipartFile[] files,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return renderCreateAlbumForm(albumDto);
        }
        attributes.addFlashAttribute("success", "Album vytvořeno");
        albumService.createAlbum(albumDto, files);
        return "redirect:/admin/foto";
    }
}
