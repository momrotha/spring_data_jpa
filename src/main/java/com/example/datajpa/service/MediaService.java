package com.example.datajpa.service;

import com.example.datajpa.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    MediaResponse upload(MultipartFile file);
    List<MediaResponse> uploadMultiple(List<MultipartFile> files);
//    Resource downloadByName(String filename);
    boolean deleteByName(String filename);
    ResponseEntity<Resource> downloadByName(String fileName);

}
