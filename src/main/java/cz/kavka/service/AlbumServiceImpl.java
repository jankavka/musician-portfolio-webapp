package cz.kavka.service;

import cz.kavka.dto.AlbumDto;
import cz.kavka.dto.mapper.AlbumMapper;
import cz.kavka.entity.AlbumEntity;
import cz.kavka.entity.PhotoEntity;
import cz.kavka.entity.repository.AlbumRepository;
import cz.kavka.service.normalize.StringNormalizer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;

import static cz.kavka.service.exception.message.ExceptionMessage.entityNotFoundExceptionMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlbumServiceImpl implements AlbumService {

    private final PhotoService photoService;

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    private static final String SERVICE_NAME = "album";

    @Value("${photos.path}")
    private String photosPath;


    @Transactional
    @Override
    public void createAlbum(AlbumDto albumDto, MultipartFile[] files) {
        var albumName = albumDto.name();
        var normalizedAlbumName = StringNormalizer.getNormalizedString(albumName, false);
        var albumPath = photosPath + normalizedAlbumName;
        try {
            Files.createDirectory(Path.of(albumPath));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        var albumEntityToSave = albumMapper.toEntity(albumDto);
        albumEntityToSave.setAlbumPath(albumPath);

        var savedAlbum = albumRepository.save(albumEntityToSave);


        if (!files[0].isEmpty()) {
            photoService.savePhoto(files, savedAlbum.getId());
        }


    }

    @Transactional(readOnly = true)
    @Override
    public AlbumDto getAlbum(Long id) {
        return albumMapper
                .toDto(albumRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id))));
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlbumDto> getAllAlbums() {
        return albumRepository
                .findAll()
                .stream()
                .map(albumMapper::toDto)
                .toList();
    }

    @Transactional
    @Override
    public void editAlbumInfo(AlbumDto albumDto, Long id) {

        var albumToEdit = albumRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));
        var relatedPhotos = photoService.getAllPhotosByAlbum(id);
        var originalDirName = albumToEdit.getAlbumPath().split("/")[1];
        var normalizedAlbumName = StringNormalizer.getNormalizedString(albumDto.name(), false);

        setTargetEntity(albumToEdit, albumDto, normalizedAlbumName);

        for (PhotoEntity photo : relatedPhotos) {
            var fileName = photo.getUrl().split("/")[2];
            photo.setUrl(albumToEdit.getAlbumPath() + File.separator + fileName);
        }

        File originalDir = new File(photosPath + originalDirName);
        File newDir = new File(photosPath + normalizedAlbumName);

        if (!originalDir.renameTo(newDir)) {
            log.warn("Chyba během přejmenování alba");
        }
    }

    @Transactional
    @Override
    public void deleteAlbum(Long albumId) {

        var album = albumRepository
                .findById(albumId)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, albumId)));

        albumRepository.deleteById(albumId);
        deleteAlbumDir(album);

    }

    private void setTargetEntity(AlbumEntity albumToEdit, AlbumDto albumDto, String normalizedAlbumName) {
        albumToEdit.setName(albumDto.name());
        albumToEdit.setDescription(albumDto.description());
        albumToEdit.setAlbumPath(photosPath + normalizedAlbumName);
    }

    private void deleteAlbumDir(AlbumEntity album) {
        File file = new File(album.getAlbumPath());

        try {
            var allFiles = file.listFiles();
            if (allFiles != null) {
                for (File f : allFiles) {
                    if (f != null) {
                        Files.delete(f.toPath());
                    }
                }
            }
            Files.delete(file.toPath());
        } catch (IOException e) {
            log.warn("Chyba při mazání alba");
            log.warn(e.getMessage());
        }


    }
}
