package cz.kavka.service;

import cz.kavka.dto.ContactDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {

    private final ObjectMapper objectMapper;

    private final File file;

    public ContactServiceImpl(
            ObjectMapper objectMapper,
            @Value("${contacts.path}")
            String contactsUrl,
            @Value("${contacts.json-file}")
            String jsonFile

    ) {
        this.objectMapper = objectMapper;
        this.file = new File( contactsUrl + jsonFile);

    }


    @Override
    public void updateContacts(ContactDto contactDto) {
        try (OutputStream os = new FileOutputStream(file)) {
            objectMapper.writeValue(os, contactDto);

        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    @Override
    public ContactDto getContacts() {
        if (Files.notExists(file.toPath())) {
            try {
                Files.createFile(file.toPath());
                return new ContactDto(null, null);
            } catch (IOException e) {
                log.warn(e.getMessage());

            }

        } else if (file.length() == 0) {
            return new ContactDto(null, null);

        }
        return objectMapper.readValue(file, new TypeReference<>() {
        });
    }
}
