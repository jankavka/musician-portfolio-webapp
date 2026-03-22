package cz.kavka.service;

import cz.kavka.dto.ProjectDto;
import cz.kavka.dto.mapper.ProjectMapper;
import cz.kavka.entity.repository.ProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cz.kavka.service.exception.message.ExceptionMessage.entityNotFoundExceptionMessage;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    private final ProjectRepository projectRepository;

    private static final String SERVICE_NAME = "projekt";

    @Override
    @Transactional
    public ProjectDto createProject(ProjectDto projectDto) {
        //TODO: photo setting
        var entityToSave = projectMapper.toEntity(projectDto);
        var savedEntity = projectRepository.save(entityToSave);
        return projectMapper.toDto(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDto getProject(Long id) {
        return projectMapper.toDto(projectRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id))));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectDto> getAllProjects() {
        return projectRepository
                .findAll()
                .stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public void editProject(ProjectDto projectDto, Long id) {
        var entityToEdit = projectRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));

        projectMapper.updateEntity(entityToEdit, projectDto);

    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id));
        }
    }
}
