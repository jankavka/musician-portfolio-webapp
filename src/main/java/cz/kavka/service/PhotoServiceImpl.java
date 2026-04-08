package cz.kavka.service;

import cz.kavka.dto.PhotoDto;
import cz.kavka.dto.mapper.PhotoMapper;
import cz.kavka.entity.PhotoEntity;
import cz.kavka.entity.repository.AlbumRepository;
import cz.kavka.entity.repository.PhotoRepository;
import cz.kavka.service.exception.WrongContentTypeException;
import cz.kavka.service.files.MyFilesUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;

import static cz.kavka.service.exception.message.ExceptionMessage.entityNotFoundExceptionMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private final AlbumRepository albumRepository;

    private final PhotoMapper photoMapper;

    private final MyFilesUtils filesUtils;

    private static final String SERVICE_NAME = "Fotka";

    @Transactional
    @Override
    public void savePhoto(MultipartFile[] files, Long albumId) {
        for (MultipartFile file : files) {
            if (file != null && !file.isEmpty()) {

                var contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new WrongContentTypeException("Povoleny jsou pouze obrázky");
                }

                var fileName = filesUtils.getFileName(file);
                var suffix = filesUtils.getSuffix(file);

                var relatedAlbum = albumRepository.findById(albumId).orElseThrow();

                File photo = new File(relatedAlbum.getAlbumPath() + File.separator + fileName + "." + suffix);

                filesUtils.savePhotoFile(file, photo);

                var entityToSave = new PhotoEntity();
                entityToSave.setUrl(photo.getPath());
                entityToSave.setName(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0]);
                entityToSave.setAlbum(relatedAlbum);

                photoRepository.save(entityToSave);
            } else {
                throw new NullPointerException("Soubory nesmí být prázdné");
            }
        }

    }

    @Transactional(readOnly = true)
    @Override
    public PhotoDto getPhoto(Long id) {
        return photoMapper
                .toDto(photoRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id))));
    }

    @Transactional(readOnly = true)
    @Override
    public List<PhotoEntity> getAllPhotosByAlbum(Long albumId) {
        return photoRepository
                .getAllPhotosByAlbumId(albumId)
                .stream()
                .toList();
    }

    @Override
    @Transactional
    public void deletePhoto(Long id) {
        var photoEntity = photoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));

        var url = photoEntity.getUrl();
        filesUtils.deletePhotoFile(url);
        photoRepository.deleteById(id);

    }

}
