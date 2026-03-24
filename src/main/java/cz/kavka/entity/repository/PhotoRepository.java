package cz.kavka.entity.repository;

import cz.kavka.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity,Long> {

    List<PhotoEntity> getAllPhotosByAlbumId(Long albumId);
}
