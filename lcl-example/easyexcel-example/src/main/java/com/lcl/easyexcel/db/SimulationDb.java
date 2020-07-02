package com.lcl.easyexcel.db;

import com.lcl.easyexcel.pojo.FirstSheet;
import com.lcl.easyexcel.pojo.SecondSheet;
import com.lcl.easyexcel.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SimulationDb
 * @Description: 模拟数据库操作
 * @Author: Chunliang.Li
 * @Date: 2020/6/29 16:51
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class SimulationDb {

    private static Map<String, List<FirstSheet>> firstDb = new HashMap<>();
    private static Map<String, List<SecondSheet>> secondDb = new HashMap<>();

    static {
        List<FirstSheet> firstSheetList = new ArrayList<>();
        firstSheetList.add(new FirstSheet("1", "username", "password"));
        firstDb.put(Constant.FIRST_DB, firstSheetList);

        List<SecondSheet> secondSheetList = new ArrayList<>();
        secondSheetList.add(new SecondSheet("1", "为人处世要低调"));
        secondDb.put(Constant.SECOND_DB, secondSheetList);
    }

    public static List<FirstSheet> selectFirst() {
        return firstDb.get(Constant.FIRST_DB);
    }

    public static List<SecondSheet> selectSecond() {
        return secondDb.get(Constant.SECOND_DB);
    }

    public static void batchInsertFirst(List<FirstSheet> list) {
        firstDb.get(Constant.FIRST_DB).addAll(list);
    }

    public static void batchInsertSecond(List<SecondSheet> list) {
        secondDb.get(Constant.SECOND_DB).addAll(list);
    }

    public static void deleteFirst(List<String> ids) {
        List<FirstSheet> fList = firstDb.get(Constant.FIRST_DB);
        List<String> dbList = new ArrayList<>();
        fList.forEach(f -> dbList.add(f.getId()));
        ids.forEach(id -> fList.remove(dbList.indexOf(id)));
    }

    public static void deleteSecond(List<String> ids) {
        List<SecondSheet> sList = secondDb.get(Constant.SECOND_DB);
        List<String> dbList = new ArrayList<>();
        sList.forEach(s -> dbList.add(s.getId()));
        ids.forEach(id -> sList.remove(dbList.indexOf(id)));
    }


}
