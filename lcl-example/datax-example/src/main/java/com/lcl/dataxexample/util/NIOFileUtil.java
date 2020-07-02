package com.lcl.dataxexample.util;

/**
 * @ClassName: NioFileUtil
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 10:33
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

/**
 * @ClassName: DataxSupportDbType
 * @Description: NIO工具类
 * @Author: Chunliang.Li
 * @Date: 2020/7/2 13:00
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class NIOFileUtil {

    public static void read(File file) throws IOException {
        read(file, 2048);
    }

    /**
     * NIO读取文件
     *
     * @param allocate 字节大小
     */
    public static void read(File file, int allocate) throws IOException {
        RandomAccessFile access = null;
        FileChannel channel = null;
        try {
            //随机流
            access = new RandomAccessFile(file, "r");
            //获取文件通道
            channel = access.getChannel();
            //字节缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(allocate);
            System.out.println("限制是：" + byteBuffer.limit() + ",容量是：" + byteBuffer.capacity() + " ,位置是：" + byteBuffer.position());
            int length = -1;
            while ((length = channel.read(byteBuffer)) != -1) {
                byteBuffer.clear();
                byte[] bytes = byteBuffer.array();
//                String str = new String(bytes, 0, length);
//                System.out.println(str);
            }
        }  finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (access != null) {
                try {
                    access.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void write(File file, String context) throws IOException {
        write(file, context, 2048);
    }

    /**
     * NIO写文件
     *
     * @param context
     * @param allocate
     * @throws IOException
     */
    public static void write(File file, String context, int allocate) throws IOException {
        FileOutputStream outputStream = null;
        FileChannel channel = null;
        try {
            //文件内容追加模式--推荐
            outputStream = new FileOutputStream(file, true);
            channel = outputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(allocate);
            //清理缓冲区
            byteBuffer.clear();
            byteBuffer.put(context.getBytes("UTF-8"));
            System.out.println("限制是：" + byteBuffer.limit() + ",容量是：" + byteBuffer.capacity() + " ,位置是：" + byteBuffer.position());
            int length = 0;
            byteBuffer.flip();
            while ((length = channel.write(byteBuffer)) != 0) {
                /*
                 * 注意，这里不需要clear，将缓冲中的数据写入到通道中后 第二次接着上一次的顺序往下读
                 */
                System.out.println("写入长度:" + length);
            }

            channel.write(byteBuffer);
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void copy(File sourceFile, File targetFile) throws IOException {
        copy(sourceFile, targetFile, 2048);
    }

    /**
     * 拷贝文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @param capacity   字节大小
     */
    public static void copy(File sourceFile, File targetFile, int capacity) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        FileChannel readChannel = null;
        FileChannel writeChannel = null;

        try {
            fis = new FileInputStream(sourceFile);
            readChannel = fis.getChannel();

            ByteBuffer bf = ByteBuffer.allocate(capacity);
            System.out.println("限制是：" + bf.limit() + "容量是：" + bf.capacity() + "位置是：" + bf.position());
            int length = -1;

            fos = new FileOutputStream(targetFile, true);
            writeChannel = fos.getChannel();

            while ((length = readChannel.read(bf)) != -1) {
                //将当前位置置为limit，然后设置当前位置为0，也就是从0到limit这块，都写入到同道中
                bf.flip();
                int outlength = 0;
                while ((outlength = writeChannel.write(bf)) != 0) {
//                    System.out.println("读，" + length + "写," + outlength);
                }
                //将当前位置置为0，然后设置limit为容量，也就是从0到limit（容量）这块，
                //都可以利用，通道读取的数据存储到
                //0到limit这块
                bf.clear();
            }


        }  finally {
            try {
                if (writeChannel != null) {
                    writeChannel.close();
                }
                if (readChannel != null) {
                    readChannel.close();
                }
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}