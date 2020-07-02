package com.lcl.file.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileService {

    void upload(MultipartFile [] files, String filePath);

    void download(String id, HttpServletRequest request, HttpServletResponse response);

    void downloadZip(String id, String filePath, HttpServletRequest request, HttpServletResponse response);


}
