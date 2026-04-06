package cz.kavka.controller;

import cz.kavka.dto.VideoDto;
import cz.kavka.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static cz.kavka.constant.ConstantNameResolver.*;

@Controller
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/video/{id}")
    public String renderVideo(@PathVariable Long id, Model model) {
        var video = videoService.getVideo(id);
        model.addAttribute("video", video);
        return VIDEO_INDEX_TEMPLATE;
    }

    @GetMapping("/admin/video/{id}")
    public String renderAdminVideo(@PathVariable Long id, Model model) {
        var video = videoService.getVideo(id);
        model.addAttribute("video", video);
        return VIDEO_ADMIN_DETAIL_TEMPLATE;
    }

    @PostMapping("/admin/video/novy")
    public String createVideo(@Valid VideoDto videoDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            renderCreateForm(videoDto);
            return renderCreateForm(videoDto);
        }
        videoService.saveVideoUrl(videoDto);
        attributes.addFlashAttribute(SUCCESS, "Vytvořeno nové video");
        return REDIRECT + VIDEO_ADMIN_INDEX;
    }

    @GetMapping("/admin/video/novy")
    public String renderCreateForm(VideoDto videoDto) {
        return VIDEO_ADMIN_CREATE_TEMPLATE;
    }

    @GetMapping("/admin/video")
    public String showAllAdminVideos(Model model) {
        var allVideos = videoService.getAllVideos();
        model.addAttribute("videos", allVideos);
        return VIDEO_ADMIN_INDEX_TEMPLATE;
    }

    @GetMapping("/video")
    public String showAllVideos(Model model) {
        var allVideos = videoService.getAllVideos();
        model.addAttribute("videos", allVideos);
        return VIDEO_INDEX_TEMPLATE;
    }

    @GetMapping("/admin/video/vymazat/{id}")
    public String deleteVideo(@PathVariable Long id, RedirectAttributes attributes) {
        videoService.deleteVideoUrl(id);
        attributes.addFlashAttribute(SUCCESS, "Video úspěšně vymazáno");
        return REDIRECT + VIDEO_ADMIN_INDEX;
    }
}
