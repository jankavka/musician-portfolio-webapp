package cz.kavka.dto.mapper;

import cz.kavka.dto.PhotoDto;
import cz.kavka.entity.PhotoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoEntity toEntity(PhotoDto photoDto);

    PhotoDto toDto(PhotoEntity photoEntity);

    void updateEntity(@MappingTarget PhotoEntity target, PhotoDto source);
}
