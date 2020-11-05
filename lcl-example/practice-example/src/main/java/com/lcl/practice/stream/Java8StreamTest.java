package com.lcl.practice.stream;

import com.lcl.practice.pojo.User;

import java.util.ArrayList;

/**
 * Description： 流式编程测试
 * Author: Chunliang.Li
 * Date: 2020/8/24 13:49
 **/
public class Java8StreamTest {

    public static void main(String[] args) {

        boolean flag = true;

        User user1 = new User(1, "1", "1", false);
        User user2 = new User(2, "2", "2", false);
        User user3 = new User(3, "3", "3", false);
        User user4 = new User(4, "4", "4", false);

        ArrayList<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        if (list.parallelStream().anyMatch(user -> {
            System.out.println(user);
            return flag == user.getBoo();
        })) {
            System.out.println("==");
        }else{
            System.out.println("==2");
        }

    }

}
