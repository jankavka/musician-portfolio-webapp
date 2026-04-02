package cz.kavka.controller;

import cz.kavka.constant.ConstantNameResolver;
import cz.kavka.dto.ProjectDto;
import cz.kavka.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static cz.kavka.constant.ConstantNameResolver.*;

@Controller
@RequiredArgsConstructor
public class ProjectsController {

    private final ProjectService projectService;

    @GetMapping("/projekty")
    public String renderProjects(Model model) {
        var projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return PROJECT_INDEX_PUBLIC_TEMPLATE;
    }

    @GetMapping("/admin/projekty")
    public String renderAdminProjects(Model model) {
        var projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);
        return PROJECT_INDEX_ADMIN_TEMPLATE;
    }

    @GetMapping("/admin/projekty/novy")
    public String renderCreateForm(ProjectDto projectDto) {
        return PROJECT_CREATE_ADMIN_TEMPLATE;
    }

    @PostMapping("/admin/projekty/novy")
    public String createProject(@Valid ProjectDto projectDto, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return PROJECT_CREATE_ADMIN_TEMPLATE;
        }
        projectService.createProject(projectDto);
        attributes.addFlashAttribute(ConstantNameResolver.SUCCESS, "Projekt vytvořen");
        return REDIRECT + PROJECTS_HOME_ADMIN;
    }

    @GetMapping("/admin/projekty/upravit/{id}")
    public String renderEditForm(Model model, @PathVariable Long id) {
        var projectDto = projectService.getProject(id);
        model.addAttribute("projectDto", projectDto);
        return PROJECT_EDIT_ADMIN_TEMPLATE;
    }

    @PostMapping("/admin/projekty/upravit/{id}")
    public String updateProject(
            @Valid ProjectDto projectDto,
            BindingResult result,
            RedirectAttributes attributes,
            @PathVariable Long id) {
        if (result.hasErrors()) {
            Model model = new BindingAwareModelMap();
            model.addAttribute("projectDto", projectDto);
            return renderEditForm(model, id);
        }
        attributes.addFlashAttribute(
                SUCCESS, "Projekt s názvem " + projectDto.name() + " upraven");
        projectService.editProject(projectDto, id);
        return REDIRECT + PROJECTS_HOME_ADMIN;
    }

    @GetMapping("/admin/projekty/vymazat/{id}")
    public String deleteProject(@PathVariable Long id, RedirectAttributes attributes) {
        var projectName = projectService.getProject(id).name();
        projectService.deleteProject(id);
        attributes.addFlashAttribute(
                SUCCESS, "Projekt s názvem " + projectName + " úspěšně vymazán");
        return REDIRECT + PROJECTS_HOME_ADMIN;
    }

}
