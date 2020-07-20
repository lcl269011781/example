package com.lcl.mailexample.controller;

import com.lcl.mailexample.pojo.MailTemplate;
import com.lcl.mailexample.service.MailService;
import com.lcl.mailexample.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: MailController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/14 9:16
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@Slf4j
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;
    @Value("${spring.mail.username}")
    private String from;

    @PostMapping("/send")
    public ApiResult send(MailTemplate mailTemplate,
                          @RequestParam(value = "files", required = false) MultipartFile[] files) {

        try {
            mailService.send(mailTemplate, from, files);
            return ApiResult.of(ApiResult.ResultCode.OK, "发送成功", null);
        } catch (Exception e) {
            log.error("", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "发送失败", null);
        }
    }

}
