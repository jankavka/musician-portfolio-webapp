package cz.kavka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "concerts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConcertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String band;

    @Column
    private LocalDateTime startDateTime;

    @Column
    private String linkToEvent;
}
