package com.lcl.file.controller;

import com.lcl.file.service.FileService;
import com.lcl.file.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: FileController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/6/30 20:26
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    @Autowired
    private FileService fileService;
    @Value("${file.path}")
    private String filepath;

    @PostMapping("/upload")
    public ApiResult upload(@RequestParam MultipartFile[] files) {
        try {
            fileService.upload(files, filepath);
            return ApiResult.of(ApiResult.ResultCode.OK, "上传成功", null);
        } catch (Exception e) {
            log.error("", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "上传失败", null);
        }
    }

    @GetMapping("/download")
    public void download(@RequestParam String id, HttpServletResponse response, HttpServletRequest request) {
        try {
            fileService.download(id, request, response);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    @GetMapping("/downloadZip")
    public void downloadZip(@RequestParam String id, HttpServletResponse response, HttpServletRequest request) {
        try {
            fileService.downloadZip(id, filepath, request, response);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
