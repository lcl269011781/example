package com.lcl.jdk.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: StreamApiTest
 * @Description:
 * @Author: Chunliang.Li
 * @Date: 2020/7/10 10:16
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class StreamApiTest {

    public static void main(String[] args) throws InterruptedException {

        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        list.add("789");

//        list.stream().filter(s->!"123".equals(s)).map(s->s="123").forEach(System.out::println);
//        list.stream().forEach(System.out::println);
//        List<String> collect = list.stream().filter(s -> !"123".equals(s)).collect(Collectors.toList());
//        collect.parallelStream().forEach(System.out::println);
//        System.out.println("------------");
//        list.parallelStream().forEach(System.out::println);

        for (int i = 0; i < 500; i++) {
            list.add(String.valueOf((int) (Math.random() * 500)));
        }
        long start = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (Integer.parseInt(list.get(i)) % 5 == 0) {
                sum++;
            }
            Thread.sleep(100);
        }
        System.out.println(sum);
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "毫秒");


        System.out.println("----------------");
        long s = System.currentTimeMillis();
        AtomicInteger n= new AtomicInteger();
        list.parallelStream().forEach(i->{
            if (Integer.parseInt(i) % 5 == 0) {
                n.getAndIncrement();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long e = System.currentTimeMillis();
        System.out.println(n);
        System.out.println((e - s) + "毫秒");

    }

}
