package com.lcl.practice.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * Description：ForkJoin
 * Author: Chunliang.Li
 * Date: 2020/8/24 14:28
 **/
public class ForkJoinTest {

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(new MyTask(0, 100));
        try {
            System.out.println(forkJoinTask.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

}
