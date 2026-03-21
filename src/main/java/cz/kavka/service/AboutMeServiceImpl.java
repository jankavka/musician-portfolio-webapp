package cz.kavka.service;

import cz.kavka.dto.AboutMeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class AboutMeServiceImpl implements AboutMeService {

    private static final File file = new File("./about-me/about-me.json");

    private final ObjectMapper objectMapper;

    @Override
    public AboutMeDto getInfo() {

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
