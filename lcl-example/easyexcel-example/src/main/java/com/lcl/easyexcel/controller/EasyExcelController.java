package com.lcl.easyexcel.controller;

import com.lcl.easyexcel.exception.ExcelException;
import com.lcl.easyexcel.service.EasyExcelService;
import com.lcl.easyexcel.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName: EasyExcelController
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/6/29 17:35
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@RestController
@RequestMapping("/easyexcel")
@Slf4j
public class EasyExcelController {
    @Autowired
    private EasyExcelService easyExcelService;

    /**
     * 简易导出==>单个文件，多个sheet各自不同。
     *
     * @param request
     * @param response
     * @param filename 文件名
     */
    @GetMapping("/ep/{filename}")
    public void ep(HttpServletRequest request, HttpServletResponse response, @PathVariable String filename) {
        try {
            easyExcelService.ep(response, request, filename);
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 导入多个文件
     *
     * @param files 多文件
     */
    @PostMapping("/im")
    public ApiResult im(MultipartFile[] files) {
        try {
            easyExcelService.im(files);
            return ApiResult.of(ApiResult.ResultCode.OK, "导入成功", null);
        } catch (ExcelException e) {
            log.error("", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            log.error("", e);
            return ApiResult.of(ApiResult.ResultCode.INTERNAL_SERVER_ERROR, "导入失败", null);
        }
    }
}
