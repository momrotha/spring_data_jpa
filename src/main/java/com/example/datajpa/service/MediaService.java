package com.example.datajpa.service;

import com.example.datajpa.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    MediaResponse upload(MultipartFile file);
}
