package com.lcl.easyexcel.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface EasyExcelService {

    void ep(HttpServletResponse response, HttpServletRequest request,String filename) throws InterruptedException, ExecutionException, TimeoutException, IOException;

    void im(MultipartFile [] files) throws IOException, InterruptedException;

}
