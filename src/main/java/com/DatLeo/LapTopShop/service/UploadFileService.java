package com.DatLeo.LapTopShop.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UploadFileService {

    private final ServletContext servletContext;

    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {

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

    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                System.out.println("Deleting file: " + filePath);
                Files.delete(path);
                System.out.println("File deleted successfully: " + filePath);
            } else {
                System.out.println("File not found" + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error deleting file: " + filePath);
            e.printStackTrace();
        }
    }

    // Method return path cho file
    public String getFullPathFile(String fileName, String targetFolder) {
        return servletContext.getRealPath("/resources/images/" + targetFolder + "/" + fileName);
    }

}
