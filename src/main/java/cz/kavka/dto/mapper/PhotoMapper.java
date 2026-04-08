package cz.kavka.dto.mapper;

import cz.kavka.dto.PhotoDto;
import cz.kavka.entity.PhotoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhotoMapper {

    PhotoEntity toEntity(PhotoDto photoDto);

    PhotoDto toDto(PhotoEntity photoEntity);
}
