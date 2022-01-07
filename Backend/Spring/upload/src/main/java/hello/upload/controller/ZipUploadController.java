package hello.upload.controller;

import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@RestController
@RequestMapping("/file/v1")
@RequiredArgsConstructor
public class UploadController {

    public final FileStore fileStore;

    @GetMapping("/{fileName}")
    public Resource getFile(@PathVariable("fileName") String fileName) throws MalformedURLException {
        return new UrlResource("file:" + fileStore.getFullPath(fileName));
    }

    @PostMapping()
    public UploadFile uploadFile(@RequestBody MultipartFile multipartFile) throws IOException {
        return fileStore.storeFile(multipartFile);
    }

}
