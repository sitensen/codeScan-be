package com.codescan.admin.modules.sys.service.impl;

import com.codescan.admin.modules.sys.service.IFileService;
import com.codescan.admin.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class FileServiceImpl implements IFileService {
    @Value("${minio.server}")
    private String server;
    @Value("${minio.accessKey}")
    private String username;
    @Value("${minio.secretKey}")
    private String password;

    public String uploadFile(MultipartFile file) {
        try {
            MinioUtils minioClient = new MinioUtils(server, username, password);
            String url = minioClient.putObject(file, file.getContentType());
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<String> uploadFiles(MultipartFile[] files) {
        List<String> result = new ArrayList<>();
        try {
            MinioUtils minioClient = new MinioUtils(server, username, password);
            String host = "xxxx";
            for (MultipartFile file : files) {
                String fileName = minioClient.putObject(file, file.getContentType());
                String url = (host + "/api/file/getFile?fileName=" + fileName);
                result.add(url);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream getFile(String fileName) {
        try {
            MinioUtils minioClient = new MinioUtils(server, username, password);
            InputStream is = minioClient.getObject(fileName);
            return is;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String uploadFileRtPath(MultipartFile file) {
        try {
            MinioUtils minioClient = new MinioUtils(server, username, password);
            String url = minioClient.putObject(file, file.getContentType());
            return minioClient.getBasisUrl() + url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String uploadImage(BufferedImage file, String filename) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(file, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            MinioUtils minioClient = new MinioUtils(server, username, password);
            String url = minioClient.putObject(filename, input);
            return minioClient.getBasisUrl() + url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String uploadFileRtPath(String fileName) {
        try {
            MinioUtils minioClient = new MinioUtils(server, username, password);
            String url = minioClient.putObject(fileName);
            return minioClient.getBasisUrl() + url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



    public List<String> uploadFilesRtPath(MultipartFile[] files) {
        List<String> result = new LinkedList<>();
        try {
            MinioUtils minioClient = new MinioUtils(server, username, password);
            for (MultipartFile file : files) {
                String url = minioClient.putObject(file, file.getContentType());
                result.add(minioClient.getBasisUrl() + url);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
