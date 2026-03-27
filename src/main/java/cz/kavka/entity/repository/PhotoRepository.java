package cz.kavka.entity.repository;

import cz.kavka.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {

    List<PhotoEntity> getAllPhotosByAlbumId(Long albumId);

    @Modifying
    @Query("DELETE FROM PhotoEntity p WHERE p.id = :id")
    void deleteById(@Param("id") Long id);
}
