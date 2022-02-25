package com.he.benteng.service;

import org.springframework.web.multipart.MultipartFile;

public interface uploadService {
    String upload(MultipartFile file);

    void removeVideo(String videoId);
}
