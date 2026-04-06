package cz.kavka.service;

import cz.kavka.dto.ProjectDto;
import cz.kavka.dto.mapper.ProjectMapper;
import cz.kavka.entity.repository.ProjectRepository;
import cz.kavka.service.files.MyFilesUtils;
import cz.kavka.service.normalize.StringNormalizer;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;

import static cz.kavka.service.exception.message.ExceptionMessage.entityNotFoundExceptionMessage;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;

    private final ProjectRepository projectRepository;

    private final MyFilesUtils filesUtils;

    private static final String SERVICE_NAME = "projekt";

    private final String dirUrl;

    public ProjectServiceImpl(
            ProjectMapper projectMapper,
            ProjectRepository projectRepository,
            MyFilesUtils filesUtils,
            @Value("${projects.path}")
            String dirUrl
    ) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.filesUtils = filesUtils;
        this.dirUrl = dirUrl;
    }

    @Override
    @Transactional
    public void createProject(ProjectDto projectDto, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            var fileName = StringNormalizer.getNormalizedString(
                    (Objects.requireNonNull(file.getOriginalFilename())), true);
            var suffix = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

            File photo = new File(dirUrl + File.separator + fileName + "." + suffix);

            filesUtils.savePhotoFile(file, photo);

            var entityToSave = projectMapper.toEntity(projectDto);
            entityToSave.setPhotoUrl(photo.getPath());
            projectRepository.save(entityToSave);

        } else {
            throw new NullPointerException("Soubory nesmí být prázdné");
        }


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
    public void editProject(ProjectDto projectDto, MultipartFile file, Long id) {
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
