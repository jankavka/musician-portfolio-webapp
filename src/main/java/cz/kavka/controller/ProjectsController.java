package cz.kavka.controller;

import cz.kavka.dto.ProjectDto;
import cz.kavka.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/projekty")
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;

    private static final String RED_HOME_ADMIN_PATH = "redirect:/projekty/admin";

    private static final String SUCCESS = "success";

    @GetMapping
    public String renderProjects(Model model) {
        var projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "public/pages/projects";
    }

    @GetMapping("/admin")
    public String renderAdminProjects(Model model) {
        var projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return "admin/projects/index";
    }

    @GetMapping("/novy")
    public String renderCreateForm(ProjectDto projectDto) {
        return "admin/projects/create";
    }

    @PostMapping("/novy")
    public String createProject(@Valid ProjectDto projectDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "admin/projects/create";
        }
        projectService.createProject(projectDto);
        attributes.addFlashAttribute(SUCCESS, "Projekt vytvořen");
        return RED_HOME_ADMIN_PATH;
    }

    @GetMapping("/upravit/{id}")
    public String renderEditForm(Model model, @PathVariable Long id) {
        var projectDto = projectService.getProject(id);
        model.addAttribute("projectDto", projectDto);
        return "admin/projects/edit";
    }

    @PostMapping("/upravit/{id}")
    public String updateProject(
            @Valid ProjectDto projectDto, BindingResult result, RedirectAttributes attributes, @PathVariable Long id) {
        if (result.hasErrors()) {
            return "admin/projects/edit";
        }
        attributes.addFlashAttribute(
                SUCCESS, "Projekt s názvem " + projectDto.name() + " upraven");
        projectService.editProject(projectDto, id);
        return RED_HOME_ADMIN_PATH;
    }

    @GetMapping("/vymazat/{id}")
    public String deleteProject(@PathVariable Long id, RedirectAttributes attributes) {
        var projectName = projectService.getProject(id).name();
        projectService.deleteProject(id);
        attributes.addFlashAttribute(
                SUCCESS, "Projekt s názvem " + projectName + " úspěšně vymazán");
        return RED_HOME_ADMIN_PATH;
    }

}
