package cz.kavka.service;

import cz.kavka.dto.ProjectDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {

    void createProject(ProjectDto projectDto, MultipartFile file);

    ProjectDto getProject(Long id);

    List<ProjectDto> getAllProjects();

    void editProject(ProjectDto source, MultipartFile file, Long id);

    void deleteProject(Long id);


}
