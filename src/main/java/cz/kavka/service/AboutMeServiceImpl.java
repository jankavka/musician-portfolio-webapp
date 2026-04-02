package cz.kavka.service;

import cz.kavka.dto.AboutMeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;

@Slf4j
@Service
public class AboutMeServiceImpl implements AboutMeService {

    private final ObjectMapper objectMapper;

    private final File file;

    public AboutMeServiceImpl(
            ObjectMapper objectMapper,
            @Value("${about-me.path}")
            String aboutMeUrl,
            @Value("${about-me.json-file}")
            String aboutMeJson

    ) {
        this.objectMapper = objectMapper;
        this.file = new File("./" + aboutMeUrl + aboutMeJson);
    }

    @Override
    public AboutMeDto getInfo() {
        if (Files.notExists(file.toPath())) {
            try {
                Files.createFile(file.toPath());
                return new AboutMeDto(null);
            } catch (IOException e) {
                log.warn(e.getMessage());
            }
        } else if (file.length() == 0) {
            return new AboutMeDto(null);
        }

        return objectMapper.readValue(file, new TypeReference<>() {
        });
    }

    @Override
    public void editInfo(AboutMeDto source) {
        try (OutputStream os = new FileOutputStream(file)) {
            objectMapper.writeValue(os, source);

        } catch (IOException e) {
            log.error("Internal server error: {} ", e.getMessage());
        }
    }
}
