package cz.kavka.dto.mapper;

import cz.kavka.dto.AlbumDto;
import cz.kavka.entity.AlbumEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumDto toDto(AlbumEntity albumEntity);

    AlbumEntity toEntity(AlbumDto albumDto);

    void updateEntity(@MappingTarget AlbumEntity target, AlbumDto source);
}
