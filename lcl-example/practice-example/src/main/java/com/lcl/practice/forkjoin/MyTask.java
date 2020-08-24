package com.lcl.practice.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * Description：
 * Author: Chunliang.Li
 * Date: 2020/8/24 14:28
 **/
public class MyTask extends RecursiveTask<Integer> {

    private static final Integer LIMIT = 10;

    private int begin;
    private int end;
    private int result;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - begin) <= LIMIT) {
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
        } else {
            int m = (begin + end) / 2;
            MyTask myTask1 = new MyTask(begin, m);
            MyTask myTask2 = new MyTask(m + 1, end);

            myTask1.fork();
            myTask2.fork();

            result = myTask1.join() + myTask2.join();
        }

        return result;
    }
}
