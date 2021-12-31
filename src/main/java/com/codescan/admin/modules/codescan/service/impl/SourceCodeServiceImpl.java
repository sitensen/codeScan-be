package com.codescan.admin.modules.codescan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codescan.admin.common.exception.ApiException;
import com.codescan.admin.modules.codescan.mapper.SourceCodeMapper;
import com.codescan.admin.modules.codescan.model.SourceCodeVo;
import com.codescan.admin.modules.codescan.service.ISourceCodeService;
import com.codescan.admin.modules.sys.model.SysFile;
import com.codescan.admin.modules.sys.service.IFileService;
import com.codescan.admin.utils.MinioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.transform.Source;
import java.util.Objects;

@Service
public class SourceCodeServiceImpl extends ServiceImpl<SourceCodeMapper, SourceCodeVo> implements ISourceCodeService {
    @Autowired
    private IFileService fileService;
    @Override
    public boolean saveSourceCode(SourceCodeVo sourceCodeVo) {
        boolean flag = this.save(sourceCodeVo);
        String path = sourceCodeVo.getCodePath();
        SysFile sysFile = fileService.findByMinioUrl(path);
        if(Objects.isNull(sysFile)){
            throw new ApiException("获取文件信息失败!");
        }
        new Thread(()->analysisSourceCode(sourceCodeVo,sysFile)).start();
        return flag;
    }

    public void analysisSourceCode(SourceCodeVo sourceCodeVo,SysFile sysFile){
        String localPath = sysFile.getLocalUrl();


    }
}
