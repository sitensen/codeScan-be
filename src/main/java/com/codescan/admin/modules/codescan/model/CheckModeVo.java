package com.codescan.admin.modules.codescan.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 检查模式
 */
@TableName(value = "tb_check_mode")
@Data
public class CheckModeVo {
    private long id;
    private String modeName;
}
