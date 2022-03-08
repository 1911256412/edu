package com.he.benteng.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface uploadService {
    String upload(MultipartFile file);

    void removeVideo(String videoId);

    void deleteBatch(List videoList);
}
