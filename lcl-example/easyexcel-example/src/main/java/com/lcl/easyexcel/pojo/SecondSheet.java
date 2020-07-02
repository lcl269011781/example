package com.lcl.easyexcel.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName: FirstSheet
 * @Description: 第2个sheet
 * @Author: Chunliang.Li
 * @Date: 2020/6/29 16:43
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SecondSheet {
    /**
     * 无视该字段生成excel表头
     */
    @ExcelIgnore
    private String id;

    @ExcelProperty(value = "为人处事要低调", index = 0)
    private String remark;


}
