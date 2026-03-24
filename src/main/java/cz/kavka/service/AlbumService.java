package cz.kavka.service;

import cz.kavka.dto.AlbumDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AlbumService {

    void createAlbum(AlbumDto albumDto, MultipartFile[] files);

    AlbumDto getAlbum(Long id);

    List<AlbumDto> getAllAlbums();
}
