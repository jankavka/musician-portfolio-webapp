package cz.kavka.dto.mapper;

import cz.kavka.dto.AlbumDto;
import cz.kavka.entity.AlbumEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    AlbumDto toDto(AlbumEntity albumEntity);

    AlbumEntity toEntity(AlbumDto albumDto);

}
