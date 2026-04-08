package cz.kavka.service;

import cz.kavka.dto.PhotoDto;
import cz.kavka.entity.PhotoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PhotoService {

    void savePhoto(MultipartFile[] files, Long id);

    PhotoDto getPhoto(Long id);

    List<PhotoEntity> getAllPhotosByAlbum(Long albumId);

    void deletePhoto(Long id);
}
