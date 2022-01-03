package com.codescan.admin.modules.codescan.controller;

import com.codescan.admin.common.api.CommonResult;
import com.codescan.admin.modules.codescan.model.SourceCodeVo;
import com.codescan.admin.modules.codescan.service.ISourceCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping("/sourceCode")
@RestController
public class SourceCodeController {
    @Autowired
    private ISourceCodeService sourceCodeService;

    @PostMapping("/save")
    public CommonResult<?> save(@RequestBody SourceCodeVo sourceCodeVo){
        if(Objects.isNull(sourceCodeVo.getCodePath())){
            return CommonResult.failed("源码文件不能为空!");
        }
        String suffiixName = sourceCodeVo.getCodePath().substring(sourceCodeVo.getCodePath().lastIndexOf("."));
        if(!suffiixName.equalsIgnoreCase(".zip")){
            // || sourceCodeVo.getCodePath().equalsIgnoreCase(".py")
            return CommonResult.failed("源码文件格式错误,请上传zip压缩包!");
        }
        sourceCodeService.saveSourceCode(sourceCodeVo);
        return CommonResult.success();
    }
}
