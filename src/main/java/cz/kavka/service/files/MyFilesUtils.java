package cz.kavka.service.files;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Component("filesUtils")
@RequiredArgsConstructor
public class MyFilesUtils {


    public void savePhotoFile(MultipartFile file, File photo) {
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = new FileOutputStream(photo)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);

            }
            outputStream.flush();

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    public void deletePhotoFile(String url) {
        try {
            Files.deleteIfExists(Path.of(url));

        } catch (IOException e) {
            throw new NullPointerException("Soubor nenalezen");
        }
    }



}
