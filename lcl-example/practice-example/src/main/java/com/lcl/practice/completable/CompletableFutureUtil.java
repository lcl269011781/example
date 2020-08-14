package com.lcl.practice.completable;


import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @ClassName: CompletableFutureUtil
 * @Description: 异步编排
 * @Author: Chunliang.Li
 * @Date: 2020/7/29 14:42
 * 本人学识渊博、经验丰富，代码风骚、效率恐怖、无所不精、无所不通
 **/
public class CompletableFutureUtil {

    private static Executor executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws ExecutionException, InterruptedException {


        CompletableFuture<String> async = CompletableFuture.supplyAsync(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return "3";
        });
        CompletableFuture<String> async2 = CompletableFuture.supplyAsync(() -> "4");
        //anyof  任意一个任务完成就返回
        CompletableFuture<Object> any = CompletableFuture.anyOf(async,async2);
        Object o = any.get();
        System.out.println(o);


    }

    private static void test4() {
        List<CompletableFuture<String>> futureList = Arrays.asList(CompletableFuture.supplyAsync(() -> "1"),
                CompletableFuture.supplyAsync(() -> "2"),
                CompletableFuture.supplyAsync(() -> "3"),
                CompletableFuture.supplyAsync(() -> "4"),
                CompletableFuture.supplyAsync(() -> "5"));
        //所有任务完成才返回
        CompletableFuture<Void> all = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{}));
        all.thenRun(() -> futureList.forEach(future -> {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }));
    }

    public void test3() throws ExecutionException, InterruptedException {
        CompletableFuture<String> xxxx = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync:" + Thread.currentThread().getName());
            return "Hello";
        }, executor).thenApply(o -> {
            System.out.println("thenApply:" + Thread.currentThread().getName() + "===" + o);
            return o + "World";
        }).thenApply(o -> {
            System.out.println("thenApply:" + Thread.currentThread().getName() + "===" + o);
            return o + "-----";
        });

        xxxx.thenCombine(xxxx, (s, s2) -> {
            System.out.println("thenCombine:" + s + s2);
            return s + s2;
        });
        System.out.println(xxxx.get());

    }

    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> async = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync:" + Thread.currentThread().getName());
            return 10 / 0;
        }, executor).whenCompleteAsync((integer, throwable) -> System.out.println("whenCompleteAsync：" + integer + "---：" + Thread.currentThread().getName()))
                .exceptionally(throwable -> {
                    System.out.println("exceptionally:" + throwable.getMessage() + "---：" + Thread.currentThread().getName());
                    return 0;
                }).handleAsync((integer, throwable) -> 1);
        Integer integer = async.get();
        System.out.println(integer);
    }

    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync:" + Thread.currentThread().getName());
            return 1024;
        }, executor).thenApply(o -> {
            System.out.println("thenApply:" + Thread.currentThread().getName() + "===" + o);
            return o * 2;
        }).whenComplete((integer, throwable) -> System.out.println("whenComplete:" + Thread.currentThread().getName() + "===" + integer)).exceptionally(throwable -> {
            System.out.println("exceptionally:" + Thread.currentThread().getName() + "===" + throwable.getMessage());
            return 0;
        }).handle((integer, throwable) -> {
            System.out.println("handle:" + Thread.currentThread().getName() + "===" + integer);
            return 666;
        });
        Integer i = future.get();
        System.out.println(i);
    }

}
