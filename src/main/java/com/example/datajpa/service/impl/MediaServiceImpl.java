package com.example.datajpa.service.impl;

import com.example.datajpa.domain.Media;
import com.example.datajpa.dto.MediaResponse;
import com.example.datajpa.repository.MediaRepository;
import com.example.datajpa.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService {

    private final MediaRepository mediaRepository;

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Value("${upload.dir}")
    private String uploadDir;

    @Override
    public MediaResponse upload(MultipartFile file) {

        String name = UUID.randomUUID().toString();
        int lastIndex = Objects.requireNonNull(file.getOriginalFilename())
                .lastIndexOf(".");
        String extension = file.getOriginalFilename()
                .substring(lastIndex + 1);
        Path path = Paths.get(serverPath + String.format("%s.%s",
                name, extension));

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File upload failed");
        }

        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setMimeTypeFile(file.getContentType());
        media.setIsDeleted(false);

        media = mediaRepository.save(media);

        return MediaResponse.builder()
                .name(name)
                .extension(extension)
                .mimeTypeFile(file.getContentType())
                .uri(baseUri + String.format("%s.%s", name, extension))
                .size(file.getSize())
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files) {
        return files.stream().map(this::upload).collect(Collectors.toList());
    }

//    @Override
//    public Resource downloadByName(String filename) {
//        Path path = Paths.get(uploadDir, filename);
//        if (!Files.exists(path)) {
//            throw new RuntimeException("File not found: " + filename);
//        }
//        return new FileSystemResource(path.toFile());
//    }
//    @Override
//    public Resource downloadByName(String filename) {
//        try {
//            Path file = Paths.get("/Users/momrotha/Documents/file_upload/").resolve(filename).normalize();
//            Resource resource = new UrlResource(file.toUri());
//            if (resource.exists()) {
//                return resource;
//            } else {
//                throw new RuntimeException("File not found: " + filename);
//            }
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Error: " + e.getMessage());
//        }
//    }

    @Override
    public ResponseEntity<Resource> downloadByName(String filename) {
        Media media = mediaRepository.findByName(filename)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found"));

        String fullFileName = media.getName() + "." + media.getExtension();

        Path filePath = Paths.get(serverPath).resolve(fullFileName).normalize();
        Path serverRoot = Paths.get(serverPath).toAbsolutePath().normalize();

        if (!filePath.toAbsolutePath().startsWith(serverRoot)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file path");
        }

        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading file");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fullFileName + "\"")
                .body(resource);
    }


    @Override
    public boolean deleteByName(String filename) {
        Path path = Paths.get(serverPath).resolve(filename).normalize();
        try {
            if (Files.exists(path)) {
                Files.delete(path);
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file");
        }
    }
}
