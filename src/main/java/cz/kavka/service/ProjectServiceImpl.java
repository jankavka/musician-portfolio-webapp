package cz.kavka.service;

import cz.kavka.dto.ProjectDto;
import cz.kavka.dto.mapper.ProjectMapper;
import cz.kavka.entity.repository.ProjectRepository;
import cz.kavka.service.exception.WrongContentTypeException;
import cz.kavka.service.files.MyFilesUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

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

    /**
     * creates a new project based on params mentioned lower. If file is empty or is null NullPointerException
     * is thrown.
     *
     * @param projectDto object with info about project such as name and description
     * @param file       MultipartFile instance with photo related to project
     */
    @Override
    @Transactional
    public void createProject(ProjectDto projectDto, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            var contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new WrongContentTypeException("Povoleny jsou pouze obrázky");
            }
            var fileName = filesUtils.getFileName(file);
            var suffix = filesUtils.getSuffix(file);

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

    /**
     * Edits information about project including photo represented and MultipartFile. If multipart is null or is empty
     * the photo remains the same as before editing.
     *
     * @param projectDto object with updated info about project
     * @param file       MultipartFile instance with photo related to project
     * @param id         primary key of project entity
     */
    @Override
    @Transactional
    public void editProject(ProjectDto projectDto, MultipartFile file, Long id) {

        var entityToEdit = projectRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));

        if (file != null && !file.isEmpty()) {
            var contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new WrongContentTypeException("Povoleny jsou pouze obrázky");
            }

            var fileName = filesUtils.getFileName(file);
            var suffix = filesUtils.getSuffix(file);

            File photo = new File(dirUrl + File.separator + fileName + "." + suffix);

            filesUtils.savePhotoFile(file, photo);

            projectMapper.updateEntity(entityToEdit, projectDto);
            entityToEdit.setPhotoUrl(photo.getPath());


        } else {
            entityToEdit.setName(projectDto.name());
            entityToEdit.setDescription(projectDto.description());

        }

    }

    @Override
    @Transactional
    public void deleteProject(Long id) {
        var project = projectRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));
        projectRepository.deleteById(id);

        filesUtils.deletePhotoFile(project.getPhotoUrl());

    }


}
