package com.codescan.admin.modules.codescan.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "tb_source_report")
@Data
public class SourceReportVo {
    private long id;
    private String reportName;
    private String filePath;
}
