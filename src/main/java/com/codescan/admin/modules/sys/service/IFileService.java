package com.codescan.admin.modules.sys.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String uploadFile(MultipartFile file);

    String uploadFileRtPath(MultipartFile file);
}
