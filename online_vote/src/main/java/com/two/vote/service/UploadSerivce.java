package com.two.vote.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadSerivce {
    String upload(MultipartFile file);
}
