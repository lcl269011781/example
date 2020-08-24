package com.lcl.practice.stream;

import com.lcl.practice.pojo.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description： 流式编程测试
 * Author: Chunliang.Li
 * Date: 2020/8/24 13:49
 **/
public class Java8StreamTest {

    public static void main(String[] args) {

        User user1 = new User(1, "zhangsan", "123456");
        User user2 = new User(2, "lisi", "123456");
        User user3 = new User(3, "wangwu", "123456");

        List<User> list = Arrays.asList(user1, user2, user3);
        //filter 过滤
//        list.stream().filter(user -> user.getId() % 2 == 0).forEach(System.out::println);
        //map 映射,返回一个新的stream， collect 将stream转化成list以及其他类型
//        list = list.stream().map(user -> user.setId(user.getId() * 2)).collect(Collectors.toList());
        //sorted 排序
//        list = list.stream().sorted((o1, o2) -> o2.getId() - o1.getId()).collect(Collectors.toList());

        list = list.stream().limit(1).collect(Collectors.toList());

        System.out.println(list);

    }

}
