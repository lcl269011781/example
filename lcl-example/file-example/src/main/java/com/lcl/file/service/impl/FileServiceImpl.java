package com.lcl.file.service.impl;

import com.lcl.file.db.SimulationDb;
import com.lcl.file.exception.FileException;
import com.lcl.file.pojo.FileRecord;
import com.lcl.file.service.FileService;
import com.lcl.file.util.ZipCompressor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: FileServiceImpl
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/6/30 20:23
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Override
    public void upload(MultipartFile[] files, String filePath) {
        List<File> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = file.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf("."));
            String id = UUID.randomUUID().toString().replace("-", "");

            File targetFile = new File(filePath+id+suffix);
            //文件夹是否存在，不存在直接创建
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdir();
            }
            try {
                //上传
                file.transferTo(targetFile);
                list.add(targetFile);
                SimulationDb.insert(new FileRecord(id,filename,filePath,suffix,new Date()));
            } catch (IOException e) {
                log.error("",e);
                //事务操作，这里处理成一个上传失败，之前成功的也删除
                remove(list);
                throw new FileException("上传文件失败");
            }

        }
    }

    @Override
    public void download(String id, HttpServletRequest request, HttpServletResponse response) {
        FileRecord fileRecord = SimulationDb.selectById(id);
        //响应头设置
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" +encodeFilename(request,fileRecord.getFileName()));
        ServletOutputStream outputStream = null;
        try {
            File file = new File(fileRecord.getFilePath()+fileRecord.getId()+fileRecord.getSuffix());
            outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(file));
            outputStream.flush();
            response.flushBuffer();
        } catch (IOException e) {
            log.error("下载失败，原因:{}", e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("下载输出流关闭失败！原因:{}", e.getMessage());
                }
            }
        }
    }

    @Override
    public void downloadZip(String id, String filePath, HttpServletRequest request, HttpServletResponse response) {
        //假装查询了多条数据是个list
        List<FileRecord> fileRecordList = new ArrayList<>();
        FileRecord fileRecord = SimulationDb.selectById(id);
        fileRecordList.add(fileRecord);

        //存放Zip文件路径
        String zipName = System.currentTimeMillis() + "-文件.zip";
        String zipPath = filePath + zipName;
        //多文件路径地址集合
        List<String> pathList = new ArrayList<>();
        fileRecordList.forEach(f -> {
            //文件路径
            String downloadFilePath =f.getFilePath()+f.getId()+f.getSuffix();
            File file = new File(downloadFilePath);
            if (!file.exists()) {
                throw new FileException(f.getFileName()+"文件已被删除");
            }
            pathList.add(downloadFilePath);
        });
        try {
            //开始压缩并压缩到本地服务器
            ZipCompressor zipCompressor = new ZipCompressor(zipPath);
            zipCompressor.compress(pathList);
            File zipFile = new File(zipPath);
            //浏览器下载
            downloadCommon(response,request, zipFile, zipName) ;
            //下载成功删除本地压缩包
            FileUtils.forceDelete(zipFile);
        } catch (IOException e) {
            log.error("",e);
        }
    }

    private void downloadCommon(HttpServletResponse response,HttpServletRequest request, File zipFile, String zipName) {
        //响应头设置
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" +encodeFilename(request,zipName));
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(FileUtils.readFileToByteArray(zipFile));
            outputStream.flush();
            response.flushBuffer();
        } catch (IOException e) {
            log.error("下载失败，原因:{}", e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("下载输出流关闭失败！原因:{}", e.getMessage());
                }
            }
        }
    }


    private void remove(List<File> list){
        list.forEach(file->{
            if (file.exists()){
                file.delete();
            }
        });
    }
    /**
     * 处理导出名称乱码问题
     */
    private String encodeFilename(HttpServletRequest request, String name) {
        try {
            //火狐浏览器导出名称乱码问题
            if (request.getHeader("User-Agent").toLowerCase().contains("firefox")) {
                return new String(name.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }
            return URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }
}
