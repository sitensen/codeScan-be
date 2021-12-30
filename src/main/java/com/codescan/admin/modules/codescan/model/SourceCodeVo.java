package com.codescan.admin.modules.codescan.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *
 */
@TableName(value = "tb_source_code")
@Data
public class SourceCodeVo {
    private long id;
    private String codePath;
    private String codeType;
}
