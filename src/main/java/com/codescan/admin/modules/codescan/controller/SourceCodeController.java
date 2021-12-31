package com.codescan.admin.modules.codescan.controller;

import com.codescan.admin.common.api.CommonResult;
import com.codescan.admin.modules.codescan.model.SourceCodeVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sourceCode")
@RestController
public class SourceCodeController {

    @PostMapping("/save")
    public CommonResult<?> save(@RequestBody SourceCodeVo sourceCodeVo){

        return CommonResult.forbidden(sourceCodeVo);
    }
}
