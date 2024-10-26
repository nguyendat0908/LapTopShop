package com.DatLeo.LapTopShop.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UploadFileService {

    private final ServletContext servletContext;

    public String handleSaveUploadFile(MultipartFile file, String targetFolder){

        // Khong upload file
        if (file.isEmpty()) {
            return "";
        }

        String fileName = "";
        try {

            byte[] bytes;
            bytes = file.getBytes();

            // Get path
            String rootPath = this.servletContext.getRealPath("/resources/images");

            // Location save file
            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Create the file on server
            fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

            stream.write(bytes);
            stream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileName;
    }
    
}
