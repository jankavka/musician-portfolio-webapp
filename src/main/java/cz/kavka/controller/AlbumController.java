package cz.kavka.controller;

import cz.kavka.dto.AlbumDto;
import cz.kavka.service.AlbumService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping("/novy")
    public String renderCreateAlbumForm(AlbumDto albumDto){
        return "admin/photos/create-album";
    }

    @PostMapping("/novy")
    public String createNewAlbum(
            @Valid AlbumDto albumDto,
            @Nullable @RequestParam("files")MultipartFile[] files,
            BindingResult result,
            RedirectAttributes attributes){
        if(result.hasErrors()){
            return renderCreateAlbumForm(albumDto);
        }
        attributes.addFlashAttribute("success","Album vytvořeno");
        albumService.createAlbum(albumDto,files);
        return "redirect:/foto/admin";
    }
}
