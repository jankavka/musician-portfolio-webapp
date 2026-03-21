package cz.kavka.entity.repository;

import cz.kavka.entity.ConcertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<ConcertEntity, Long> {
}
