package com.two.vote.service.impl;

import com.two.vote.service.UploadSerivce;
import com.two.vote.utils.CreateIdUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
public class UploadServiceImpl implements UploadSerivce {

    @Override
    public String upload(MultipartFile file) {
        File dir = new File("src/main/resources/static/image/img");
        System.out.println(dir.getAbsolutePath());
        if (file.isEmpty()) {
            return null;
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = file.getInputStream();
            String filename = CreateIdUtil.randomFileName(file.getOriginalFilename());
            File targetFile = new File(dir + filename);
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdir();
            }

            outputStream = new FileOutputStream(targetFile);
            FileCopyUtils.copy(inputStream, outputStream);
            return targetFile.getName();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
