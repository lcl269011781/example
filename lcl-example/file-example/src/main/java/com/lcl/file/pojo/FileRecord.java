package com.lcl.file.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName: FileRecord
 * @Description: 上传文件记录
 * @Author: Chunliang.Li
 * @Date: 2020/6/30 20:19
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FileRecord {

    private String id;
    private String fileName;
    /**
     * 存储路径
     */
    private String filePath;
    /**
     * 后缀名
     */
    private String suffix;
    private Date createDate;

}
