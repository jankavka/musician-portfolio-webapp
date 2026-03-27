package cz.kavka.dto.mapper;

import cz.kavka.dto.ConcertDto;
import cz.kavka.entity.ConcertEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ConcertMapper {

    ConcertDto toDto(ConcertEntity concertEntity);

    ConcertEntity toEntity(ConcertDto concertDto);

    void updateEntity(@MappingTarget ConcertEntity target, ConcertDto concertDto);
}
