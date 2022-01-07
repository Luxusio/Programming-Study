package hello.upload.file;

import hello.upload.domain.UploadFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.*;

@Slf4j
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                UploadFile uploadFile = storeFile(multipartFile);
                storeFileResult.add(uploadFile);
            }
        }
        return storeFileResult;
    }

    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        // image.png

        // 서버에 저장하는 파일명?
        String storeFileName = createStoreZipFileName(originalFilename);

        storeFileAsZip(Path.of(getFullPath(storeFileName)), multipartFile);

        return new UploadFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String fileName) {
        int idx = fileName.lastIndexOf('.');
        return fileName.substring(idx + 1);
    }

    private String createStoreZipFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        return uuid + ".zip";
    }

    private final String PATH_LEVELS = "levels";



    public void saveLevelFile(Long levelId, MultipartFile multipartFile) throws IOException {
        storeFileAsZip(Path.of(fileDir, PATH_LEVELS, levelId + ".zip"), multipartFile);
    }

    private void storeFileAsZip(Path path, MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            throw new IllegalArgumentException("given multipartFile is empty!");
        }

        if (isZip(multipartFile.getInputStream())) {
            multipartFile.transferTo(path.toFile());
        }
        else {
            try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(path.toFile()))) {
                String zipEntryFileName = Optional.
                        ofNullable(multipartFile.getOriginalFilename())
                        .orElse(path.getFileName().toString());

                zipOutputStream.putNextEntry(new ZipEntry(zipEntryFileName));
                StreamUtils.copy(multipartFile.getInputStream(), zipOutputStream);
            }
        }
    }

    /**
     * Check the inputStream with zip or not
     *
     * @param inputStream stream to be wrapped
     * @return is given stream zip or not
     * @throws IOException when thrown IOException by inputStream
     * @see <a href="https://stackoverflow.com/a/22401183">...</a>
     */
    private boolean isZip(InputStream inputStream) throws IOException {
        return new ZipInputStream(inputStream).getNextEntry() != null;
    }

}
