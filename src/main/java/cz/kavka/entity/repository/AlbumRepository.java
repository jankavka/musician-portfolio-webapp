package cz.kavka.entity.repository;

import cz.kavka.entity.AlbumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<AlbumEntity,Long> {
}
