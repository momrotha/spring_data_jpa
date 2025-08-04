package com.example.datajpa.controller;

import com.example.datajpa.dto.MediaResponse;
import com.example.datajpa.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medias")
public class MediaController {

    private final MediaService mediaService;

    //Upload single file
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile file) {
        return mediaService.upload(file);
    }

    //Upload multiple files
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/multiple")
    public List<MediaResponse> uploadMultiple(@RequestPart List<MultipartFile> files) {
        return mediaService.uploadMultiple(files);
    }

    //Download file by name
    @GetMapping("/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename) {
        return mediaService.downloadByName(filename);
    }


    //Delete file by name
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{filename:.+}")
    public String delete(@PathVariable String filename) {
        boolean deleted = mediaService.deleteByName(filename);
        if (deleted) return "Deleted: " + filename;
        return "File not found: " + filename;
    }
}
