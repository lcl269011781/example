package com.lcl.file.util;

import java.io.*;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @ClassName: ZipCompressor
 * @Description: Zip压缩工具
 * @Author: Chunliang.Li
 * @Date: 2019/9/26 9:40
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class ZipCompressor {

    private static final int BUFFER = 8192;

    private static File zipFile;

    public ZipCompressor(String pathName) {
        zipFile = new File(pathName);
    }

    /**
     * 压缩多个文件路径
     *
     * @param pathNameList
     */
    public void compress(List<String> pathNameList) {
        ZipOutputStream out = null;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new CRC32());
            out = new ZipOutputStream(checkedOutputStream);
            String basedir = "";
            for (String s : pathNameList) {
                compress(new File(s), out, basedir);
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 压缩一个文件路径
     *
     * @param srcPathName
     */
    public void compress(String srcPathName) {
        File file = new File(srcPathName);
        if (!file.exists()) {
            throw new RuntimeException(srcPathName + "不存在！");
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new CRC32());
            ZipOutputStream out = new ZipOutputStream(checkedOutputStream);
            String basedir = "";
            compress(file, out, basedir);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * 判断是文件还是文件夹
     *
     * @param file
     * @param out
     * @param basedir
     */
    private void compress(File file, ZipOutputStream out, String basedir) {
        if (file.isDirectory()) {
            this.compressDirectory(file, out, basedir);
        } else {
            this.compressFile(file, out, basedir);
        }
    }

    /**
     * 压缩一个文件夹
     *
     * @param file
     * @param zipOutputStream
     * @param baseDir
     */
    private void compressDirectory(File file, ZipOutputStream zipOutputStream, String baseDir) {
        if (!file.exists()) {
            return;
        }
        File[] files = file.listFiles();
        for (File value : files) {
            compress(value, zipOutputStream, baseDir + file.getName() + "/");
        }
    }

    /**
     * 压缩一个文件
     *
     * @param file
     * @param out
     * @param basedir
     */
    private void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            ZipEntry zipEntry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(zipEntry);
            int count;
            byte[] bytes = new byte[BUFFER];
            while ((count = bis.read(bytes, 0, BUFFER)) != -1) {
                out.write(bytes, 0, count);
            }
            bis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
