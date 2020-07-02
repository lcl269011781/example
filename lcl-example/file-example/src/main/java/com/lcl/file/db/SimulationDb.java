package com.lcl.file.db;

import com.lcl.file.pojo.FileRecord;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SimulationDb
 * @Description: 模拟数据库操作
 * @Author: Chunliang.Li
 * @Date: 2020/6/29 16:51
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class SimulationDb {

    private static Map<String, FileRecord> db = new HashMap<>();

    public static FileRecord selectById(String id){
        return db.get(id);
    }

    public static void insert(FileRecord fileRecord){
        db.put(fileRecord.getId(),fileRecord);
    }

}
