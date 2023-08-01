package com.web.app.flourishandblotts.controllers;

import com.web.app.flourishandblotts.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("file")
public class FileController {


    private final StorageService storageService;

    private final HttpServletRequest request;


    @PostMapping("uploads")
    public Map<String, String > uploadFile(@RequestParam("file") MultipartFile multipartFile){
        String path = this.storageService.store(multipartFile);
        String host = this.request.getRequestURL().toString().replace(this.request.getRequestURL(), "");
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/file/")
                .path(path)
                .toUriString();
        return Map.of("url", url);
    }

    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = this.storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }


}
