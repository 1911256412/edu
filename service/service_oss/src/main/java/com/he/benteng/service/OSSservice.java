package com.he.benteng.service;

import org.springframework.web.multipart.MultipartFile;

public interface OSSservice {
    String upload(MultipartFile file);
}
