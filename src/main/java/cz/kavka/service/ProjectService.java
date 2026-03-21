package cz.kavka.service;

import cz.kavka.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    ProjectDto createProject(ProjectDto projectDto);

    ProjectDto getProject(Long id);

    List<ProjectDto> getAllProjects();

    void editProject(ProjectDto source, Long id);

    void deleteProject(Long id);


}
