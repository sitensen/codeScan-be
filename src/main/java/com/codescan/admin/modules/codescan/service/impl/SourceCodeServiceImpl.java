package com.codescan.admin.modules.codescan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codescan.admin.modules.codescan.mapper.SourceCodeMapper;
import com.codescan.admin.modules.codescan.model.SourceCodeVo;
import com.codescan.admin.modules.codescan.service.ISourceCodeService;
import org.springframework.stereotype.Service;

@Service
public class SourceCodeServiceImpl extends ServiceImpl<SourceCodeMapper, SourceCodeVo> implements ISourceCodeService {
}
