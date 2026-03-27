package cz.kavka.service;

import cz.kavka.dto.AlbumDto;
import cz.kavka.dto.mapper.AlbumMapper;
import cz.kavka.entity.repository.AlbumRepository;
import cz.kavka.service.normalize.StringNormalizer;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Override
    public AlbumDto getAlbum(Long id) {
        return albumMapper
                .toDto(albumRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id))));
    }

    @Override
    public List<AlbumDto> getAllAlbums() {
        return albumRepository
                .findAll()
                .stream()
                .map(albumMapper::toDto)
                .toList();
    }
}
