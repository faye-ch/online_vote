package com.two.vote.controller;

import com.two.vote.service.UploadSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@Controller
public class UploadController {

    @Autowired
    private UploadSerivce uploadSerivce;

    @PostMapping("upload")
    public ResponseEntity<String> upload(MultipartFile file){
        String fileName = uploadSerivce.upload(file);
        return ResponseEntity.ok("ok");
    }
}
