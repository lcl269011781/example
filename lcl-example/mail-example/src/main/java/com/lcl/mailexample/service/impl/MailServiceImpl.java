package com.lcl.mailexample.service.impl;

import com.lcl.mailexample.pojo.MailTemplate;
import com.lcl.mailexample.service.MailService;
import com.lcl.mailexample.util.MultipartFileToFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @ClassName: MailServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/14 9:17
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void send(MailTemplate mailTemplate, String from, MultipartFile[] files) {
        //发送方设置
        mailTemplate.setFrom(from);
        //附件设置,多文件压缩
        if (files != null && files.length > 0) {
            mailTemplate.setFile(new ArrayList<>());
            for (MultipartFile file : files) {
                mailTemplate.getFile().add(MultipartFileToFile.multipartFileToFile(file));
            }
        }
        this.send(mailTemplate);
    }

    @Override
    public void send(MailTemplate mailTemplate) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress(mailTemplate.getFrom()));
            message.setSubject(mailTemplate.getTitle());
            //多个收件人
            setTo(mailTemplate, message);
            //多个抄送
            setCc(mailTemplate, message);
            //文件正文+多个附件
            message.setContent(setContent(mailTemplate));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            log.error("", e);
        }finally {
            if (mailTemplate.getFile()!=null){
                mailTemplate.getFile().forEach(MultipartFileToFile::delteTempFile);
            }
        }


    }

    private MimeMultipart setContent(MailTemplate mailTemplate) throws MessagingException {
        MimeMultipart mp = new MimeMultipart();
        AtomicReference<MimeBodyPart> mbp = new AtomicReference<>(new MimeBodyPart());
        mbp.get().setContent(mailTemplate.getContent(), "text/html;charset=utf-8");
        mp.addBodyPart(mbp.get());
        //多个附件处理
        if (mailTemplate.getFile() != null) {
            mailTemplate.getFile().forEach(file -> {
                try {
                    mbp.set(new MimeBodyPart());
                    mbp.get().setDataHandler(new DataHandler(new FileDataSource(file)));
                    mbp.get().setFileName(MimeUtility.encodeText(file.getName(), "UTF-8", "B"));
                    mp.addBodyPart(mbp.get());
                } catch (MessagingException | UnsupportedEncodingException e) {
                    log.error("", e);
                }
            });
        }
        return mp;
    }

    private void setCc(MailTemplate mailTemplate, MimeMessage message) throws MessagingException {
        if (!CollectionUtils.isEmpty(mailTemplate.getCc())) {
            InternetAddress[] ccAddress = new InternetAddress[mailTemplate.getCc().size()];
            List<String> ccList = mailTemplate.getCc();
            for (int i = 0; i < ccList.size(); i++) {
                ccAddress[i] = new InternetAddress(ccList.get(i));
            }
            message.setRecipients(Message.RecipientType.CC, ccAddress);
        }
    }

    private void setTo(MailTemplate mailTemplate, MimeMessage message) throws MessagingException {
        InternetAddress[] toAddress = new InternetAddress[mailTemplate.getTo().size()];
        List<String> toList = mailTemplate.getTo();
        for (int i = 0; i < toList.size(); i++) {
            toAddress[i] = new InternetAddress(toList.get(i));
        }
        message.setRecipients(Message.RecipientType.TO, toAddress);
    }

}
