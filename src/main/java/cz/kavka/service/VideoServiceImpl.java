package cz.kavka.service;

import cz.kavka.dto.VideoDto;
import cz.kavka.entity.VideoEntity;
import cz.kavka.entity.repository.VideoRepository;
import cz.kavka.service.exception.WrongVideoSourceException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cz.kavka.service.exception.message.ExceptionMessage.*;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    private static final String SERVICE_NAME = "video";

    @Transactional
    @Override
    public void saveVideoUrl(VideoDto videoDto) {
        if (!videoDto.url().contains("youtu")){
            throw new WrongVideoSourceException("Špatný zdroj videa. Nahrajte video z YouTube");
        }
        var entityToSave = new VideoEntity();
        entityToSave.setUrl(videoDto.url());
        entityToSave.setVideoYoutubeId(getVideoYoutubeId(videoDto.url()));
        videoRepository.save(entityToSave);
    }

    @Transactional(readOnly = true)
    @Override
    public VideoDto getVideo(Long id) {
        var videoEntity = videoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));
        return new VideoDto(videoEntity.getId(), videoEntity.getUrl(), videoEntity.getVideoYoutubeId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<VideoDto> getAllVideos() {
        return videoRepository
                .findAll()
                .stream()
                .map(videoEntity -> new VideoDto(
                        videoEntity.getId(),
                        videoEntity.getUrl(),
                        videoEntity.getVideoYoutubeId()))
                .toList();
    }

    @Transactional
    @Override
    public void deleteVideoUrl(Long id) {
        var entityToDelete = videoRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(entityNotFoundExceptionMessage(SERVICE_NAME, id)));

        videoRepository.delete(entityToDelete);

    }

    private String getVideoYoutubeId(String url) {
        String result;
        var beginIndexOfId = url.indexOf("v=") + 2;
        result = url.substring(beginIndexOfId);
        if (result.contains("&")) {
            var endIndexOfId = result.indexOf('&');
            result = result.substring(0, endIndexOfId);
        }
        return result;

    }
}
