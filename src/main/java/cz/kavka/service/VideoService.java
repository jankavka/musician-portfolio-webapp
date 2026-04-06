package cz.kavka.service;

import cz.kavka.dto.VideoDto;

import java.util.List;

public interface VideoService {

    void saveVideoUrl(VideoDto videoDto);

    VideoDto getVideo(Long id);

    List<VideoDto> getAllVideos();

    void deleteVideoUrl(Long id);
}
