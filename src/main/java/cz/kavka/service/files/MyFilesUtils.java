package cz.kavka.service.files;

import cz.kavka.entity.AlbumEntity;
import cz.kavka.service.normalize.StringNormalizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Helper class with methods for working with files
 */
@Slf4j
@Component("filesUtils")
@RequiredArgsConstructor
public class MyFilesUtils {


    /**
     * Helper method for saving file to suggested destination represented by
     * photo object
     *
     * @param file  MultipartFile instance with data of file
     * @param photo File.class instance which represents the exact place in file system
     *              for saving the photo
     */
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


    /**
     * Helper method which tries to delete file on given url. If IOException is thrown than
     * NullPointerException is rethrown and later caught by specific method in GlobalExceptionHandler
     *
     * @param url String which represents url of file to delete
     */
    public void deletePhotoFile(String url) {
        try {
            Files.deleteIfExists(Path.of(url));

        } catch (IOException e) {
            throw new NullPointerException("Soubor nenalezen");
        }
    }


    /**
     * Creates file name from given MultipartFile instance
     *
     * @param file MultipartFile instance for extracting original file name from
     * @return String representation of normalized fileName
     */
    public String getFileName(MultipartFile file) {
        return StringNormalizer.getNormalizedString(
                (Objects.requireNonNull(file.getOriginalFilename())), true);
    }

    /**
     * Extracts file suffix from given MultipartFile instance
     *
     * @param file MultipartFile instance for extracting suffix
     * @return file suffix
     */
    public String getSuffix(MultipartFile file) {
        return Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
    }


    /**
     * Deletes all files on related albumPath contained on AlbumEntity object. After all files
     * are deleted also album as a root directory is deleted.
     *
     * @param album AlbumEntity objet which contains albumPath attribute
     */
    public void deleteAlbumDir(AlbumEntity album) {
        File file = new File(album.getAlbumPath());

        try {
            var allFiles = file.listFiles();
            if (allFiles != null) {
                for (File f : allFiles) {
                    if (f != null) {
                        Files.delete(f.toPath());
                    }
                }
            }
            Files.delete(file.toPath());
        } catch (IOException e) {
            log.warn("Chyba při mazání alba");
            log.warn(e.getMessage());
        }


    }


}
