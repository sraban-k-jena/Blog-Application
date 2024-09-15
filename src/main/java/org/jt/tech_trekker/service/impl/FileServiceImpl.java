package org.jt.tech_trekker.service.impl;

import org.jt.tech_trekker.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Value("${upload.file.profile}")
    private String profileDir;
    @Value("${upload.file.blog.banner}")
    private String bannerDir;

    @Override
    public void createProfileDirectory() {
        createDirectory(profileDir);

    }

    @Override
    public void createBlogDirectory() {
        createDirectory(bannerDir);

    }

    private void createDirectory(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdir();
            log.info(dir + "  created");
            return;
        }
        log.info(dir + "  already existed .");
    }

    @Override
    public String uploadProfilePicture(MultipartFile file) {
        return uploadImage(file, profileDir);
    }

    @Override
    public String uploadBlogBanner(MultipartFile file) {
        return uploadImage(file, bannerDir);
    }

    @Override
    public byte[] getProfilePicture(String fileName) {
        return getImage(profileDir, fileName);

    }

    @Override
    public byte[] getBlogBanner(String fileName) {
        return getImage(bannerDir, fileName);
    }

    private String getCustomerName(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String customName = UUID.randomUUID().toString();

        return customName + extension;
    }

    private String uploadImage(MultipartFile file, String dir) {
        if (!file.isEmpty()) {
            String originalName = file.getOriginalFilename();
            String customName = getCustomerName(originalName);
            try {
                var fos = new FileOutputStream(dir + File.separator + customName);
                fos.write(file.getBytes());
                fos.close();
                return customName;
            } catch (Exception ee) {
                throw new RuntimeException(ee);
            }
        }
        throw new RuntimeException("Image not Found .");

    }

    private byte[] getImage(String dir, String fileName) {
        String location = dir + File.separator + fileName;
        try (var fis = new FileInputStream(location)) {
            return fis.readAllBytes();

        } catch (Exception e) {

            throw new RuntimeException(e);
        }
    }
}
