package com.lcl.mailexample.service;

import com.lcl.mailexample.pojo.MailTemplate;
import org.springframework.web.multipart.MultipartFile;

public interface MailService {

    void send(MailTemplate mailTemplate, String from,MultipartFile[]files);

    void send(MailTemplate mailTemplate);
}
