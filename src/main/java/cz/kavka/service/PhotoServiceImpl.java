package cz.kavka.service;

import cz.kavka.dto.PhotoDto;
import cz.kavka.dto.mapper.PhotoMapper;
import cz.kavka.entity.PhotoEntity;
import cz.kavka.entity.repository.AlbumRepository;
import cz.kavka.entity.repository.PhotoRepository;
import cz.kavka.service.normalize.StringNormalizer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Objects;

import static cz.kavka.service.exception.message.ExceptionMessage.entityNotFoundExceptionMessage;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final PhotoRepository photoRepository;

    private final AlbumRepository albumRepository;

    private final PhotoMapper photoMapper;

    private static final String SERVICE_NAME = "Fotka";

    @Value("${photos.path}")
    private String photosPath;

    @Transactional
    @Override
    public void savePhoto(MultipartFile[] files, Long albumId) {
        for (MultipartFile file : files) {
            if (file != null) {
                var fileName = StringNormalizer.getNormalizedString(
                        (Objects.requireNonNull(file.getOriginalFilename())), true);
                var suffix = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

                var relatedAlbum = albumRepository.findById(albumId).orElseThrow();

                File photo = new File(relatedAlbum.getAlbumPath() + File.separator + fileName + "." + suffix);

                savePhotoFile(file, photo);

                var entityToSave = new PhotoEntity();
                entityToSave.setUrl(photo.getPath());
                entityToSave.setName(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0]);
                entityToSave.setAlbum(relatedAlbum);

                photoRepository.save(entityToSave);
            } else {
                throw new NullPointerException();
            }
        }

    }

    @Override
    public PhotoDto getPhoto(Long id) {
        return photoMapper
                .toDto(photoRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id))));
    }

    @Override
    public List<PhotoDto> getAllPhotos() {
        return photoRepository
                .findAll()
                .stream()
                .map(photoMapper::toDto).toList();
    }

    @Override
    public List<PhotoDto> getAllPhotosByAlbum(Long albumId) {
        return photoRepository
                .getAllPhotosByAlbumId(albumId)
                .stream()
                .map(photoMapper::toDto)
                .toList();
    }

    @Override
    public void editPhoto(PhotoDto photoDto, Long id) {
        var entityToUpdate = photoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));
        photoMapper.updateEntity(entityToUpdate, photoDto);
    }

    @Override
    public void deletePhoto(Long id) {
        if (photoRepository.existsById(id)) {
            photoRepository.deleteById(id);
        }
    }

    private void savePhotoFile(MultipartFile file, File photo) {
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(photo)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);

            }
            outputStream.flush();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
