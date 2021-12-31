package com.codescan.admin.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codescan.admin.modules.sys.model.SysFile;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService extends IService<SysFile> {
    String uploadFile(MultipartFile file);

    String uploadFileRtPath(MultipartFile file);

    SysFile findByMinioUrl(String minioUrl);
}
