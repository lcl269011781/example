package com.lcl.mailexample.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.List;

/**
 * @ClassName: MailTemplate
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/14 9:18
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MailTemplate {

    private String id;
    private String title;
    private String content;
    /**
     * 收贱人邮箱
     */
    private List<String> to;
    /**
     * 抄送
     */
    private List<String> cc;
    /**
     * 发件人邮箱
     */
    private String from;
    /**
     * 附件
     */
    private List<File> file;

}
