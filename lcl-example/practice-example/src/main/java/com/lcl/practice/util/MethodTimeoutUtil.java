package com.lcl.practice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * MethodTimeoutUtils
 * <p>
 * 提高方法调用的成功率和获取执行最快方法的结果的工具类
 */
public class MethodTimeoutUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodTimeoutUtil.class);

    private static final boolean useCommomPoll =
            (ForkJoinPool.getCommonPoolParallelism() > 1);

    /**
     * Default executor --  ForkJoinPool.commonPool() unless it cannot
     * support parallelism
     */
    private static final Executor asyncPool = useCommomPoll ? ForkJoinPool.commonPool() : new ThreadPerTaskExecutor();

    /**
     * Fallback if ForkJoinPool.commonPool() cannot support parallelism
     */
    static final class ThreadPerTaskExecutor implements Executor {
        @Override
        public void execute(Runnable r) {
            new Thread().run();
        }
    }

    /**
     * Null-checks executor argument, and translates uses of
     * commonPool to asyncPool is case parallelism disabled.
     */
    static Executor screenExecutor(Executor e) {
        if (!useCommomPoll && e == ForkJoinPool.commonPool()) {
            return asyncPool;
        }
        if (e == null) {
            throw new NullPointerException();
        }
        return e;
    }

    /**
     * 通过线程获取操作返回的结果
     *
     * @param callable Callable<T>
     * @param timeout  超时时间
     * @param unit     TimeUnit
     * @return 线程返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T simpleInvoke(Callable<T> callable, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return simpleInvoke(asyncPool, callable, timeout, unit);
    }

    /**
     * 通过线程池获取操作返回的结果
     *
     * @param executor 线程池
     * @param callable Callable<T>
     * @param timeout  超时时间
     * @param unit     TimeUnit
     * @return 线程返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T simpleInvoke(Executor executor, Callable<T> callable, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        T result = null;

        List<Thread> ts = new CopyOnWriteArrayList<>();

        Callable<T> cb = () -> {
            Thread thread = Thread.currentThread();
            ts.add(thread);
            T t = null;
            try {
                t = callable.call();
                ts.remove(thread);
            } catch (Exception e) {
                ts.remove(thread);
                throw e;
            }
            return t;
        };

        FutureTask<T> futureTask = new FutureTask<T>(cb);
        executor.execute(futureTask);
        try {
            result = futureTask.get(timeout, unit);
        } catch (ExecutionException e) {
            throw e;
        } catch (InterruptedException | TimeoutException e) {
            if (ts.size() > 0) {
                Thread t = ts.get(0);
                if (t.isAlive()) {
                    t.interrupt();
                }
            }
            futureTask.cancel(true);
            throw e;
        }
        return result;
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param callable Callable<T>
     * @param times    重试次数
     * @param timeout  超时时间
     * @param unit     TimeUnit
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T simpleInvoke(Callable<T> callable, int times, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return simpleInvoke(asyncPool, callable, times, timeout, unit);
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param executor 线程池
     * @param callable Callable<T>
     * @param times    重试次数
     * @param timeout  超时时间
     * @param unit     TimeUnit
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T simpleInvoke(Executor executor, Callable<T> callable, int times, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        T result = null;

        int i = 1;
        List<Thread> ts = new CopyOnWriteArrayList<>();

        Callable<T> cb = () -> {
            Thread thread = Thread.currentThread();
            ts.add(thread);
            T t = null;
            try {
                t = callable.call();
                ts.remove(thread);
            } catch (Exception e) {
                ts.remove(thread);
                throw e;
            }
            return t;
        };

        for (; i < times; i++) {
            if (i != 1) {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }
            }

            FutureTask<T> futureTask = new FutureTask<T>(cb);
            executor.execute(futureTask);
            try {
                result = futureTask.get(timeout, unit);
                break;
            } catch (ExecutionException e) {
                if (i == times) {
                    throw e;
                }
            } catch (InterruptedException e) {
                if (ts.size() > 0) {
                    Thread t = ts.get(0);
                    if (t.isAlive()) {
                        t.interrupt();
                    }
                }
                futureTask.cancel(true);
                throw e;
            } catch (TimeoutException e) {
                if (ts.size() > 0) {
                    Thread t = ts.get(0);
                    if (t.isAlive()) {
                        t.interrupt();
                    }
                }
                futureTask.cancel(true);
                if (i == times) {
                    throw e;
                }
            }

        }

        return result;
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param callable Callable<T>
     * @param times    重试次数
     * @param timeout  超时时间
     * @param unit     TimeUnit
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T multipleInvoke(Callable<T> callable, int times, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return multipleInvoke(asyncPool, callable, times, timeout, unit, null, null);
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param callable    Callable<T>
     * @param times       重试次数
     * @param timeout     超时时间
     * @param unit        TimeUnit
     * @param lastTimeout 最终超时时间
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T multipleInvoke(Callable<T> callable, int times, long timeout, TimeUnit unit, Long lastTimeout) throws InterruptedException, ExecutionException, TimeoutException {
        return multipleInvoke(asyncPool, callable, times, timeout, unit, null, lastTimeout);
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param callable  Callable<T>
     * @param times     重试次数
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param lastTimes 最终尝试次数
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T multipleInvoke(Callable<T> callable, int times, long timeout, TimeUnit unit, Integer lastTimes) throws InterruptedException, ExecutionException, TimeoutException {
        return multipleInvoke(asyncPool, callable, times, timeout, unit, lastTimes, null);
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param callable    Callable<T>
     * @param times       重试次数
     * @param timeout     超时时间
     * @param unit        TimeUnit
     * @param lastTimes   最终尝试次数
     * @param lastTimeout 最终超时时间
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T multipleInvoke(Callable<T> callable, int times, long timeout, TimeUnit unit, Integer lastTimes, Long lastTimeout) throws InterruptedException, ExecutionException, TimeoutException {
        return multipleInvoke(asyncPool, callable, times, timeout, unit, lastTimes, lastTimeout);
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param executor 线程池
     * @param callable Callable<T>
     * @param times    重试次数
     * @param timeout  超时时间
     * @param unit     TimeUnit
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T multipleInvoke(Executor executor, Callable<T> callable, int times, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return multipleInvoke(screenExecutor(executor), callable, times, timeout, unit, null, null);
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param executor    线程池
     * @param callable    Callable<T>
     * @param times       重试次数
     * @param timeout     超时时间
     * @param unit        TimeUnit
     * @param lastTimeout 最终超时时间
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T multipleInvoke(Executor executor, Callable<T> callable, int times, long timeout, TimeUnit unit, Long lastTimeout) throws InterruptedException, ExecutionException, TimeoutException {
        return multipleInvoke(screenExecutor(executor), callable, times, timeout, unit, null, lastTimeout);
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param executor  线程池
     * @param callable  Callable<T>
     * @param times     重试次数
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param lastTimes 最终尝试次数
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T multipleInvoke(Executor executor, Callable<T> callable, int times, long timeout, TimeUnit unit, Integer lastTimes) throws InterruptedException, ExecutionException, TimeoutException {
        return multipleInvoke(screenExecutor(executor), callable, times, timeout, unit, lastTimes, null);
    }

    /**
     * 通过线程池循环多次获取操作返回的结果, 只返回其中一次返回的结果
     *
     * @param executor    线程池
     * @param callable    Callable<T>
     * @param times       重试次数
     * @param timeout     超时时间
     * @param unit        TimeUnit
     * @param lastTimes   最终尝试次数
     * @param lastTimeout 最终超时时间
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T multipleInvoke(Executor executor, Callable<T> callable, int times, long timeout, TimeUnit unit, Integer lastTimes, Long lastTimeout) throws InterruptedException, ExecutionException, TimeoutException {
        T result = null;

        int i = 1;
        List<Thread> ts = new CopyOnWriteArrayList<>();

        Callable<T> cb = () -> {
            Thread thread = Thread.currentThread();
            ts.add(thread);
            T t = null;
            try {
                t = callable.call();
                ts.remove(thread);
            } catch (Exception e) {
                ts.remove(thread);
                throw e;
            }
            return t;
        };

        for (; i < times; i++) {
            if (i < times) {
                if (i != 1) {
                    if (Thread.interrupted()) {
                        throw new InterruptedException();
                    }
                }

                FutureTask<T> futureTask = new FutureTask<T>(cb);
                executor.execute(futureTask);
                try {
                    result = futureTask.get(timeout, unit);
                    break;
                } catch (ExecutionException e) {
                    if (i == times) {
                        throw e;
                    }
                } catch (InterruptedException e) {
                    if (ts.size() > 0) {
                        Thread t = ts.get(0);
                        if (t.isAlive()) {
                            t.interrupt();
                        }
                    }
                    futureTask.cancel(true);
                    throw e;
                } catch (TimeoutException e) {
                    if (ts.size() > 0) {
                        Thread t = ts.get(0);
                        if (t.isAlive()) {
                            t.interrupt();
                        }
                    }
                    futureTask.cancel(true);
                    if (i == times) {
                        throw e;
                    }
                }

            } else {
                if (Thread.interrupted()) {
                    throw new InterruptedException();
                }

                if (lastTimeout != null && lastTimeout > 0) {
                    timeout = lastTimeout;
                }
                if (lastTimes != null && lastTimes > 0) {
                    FutureTask<T> futureTask = new FutureTask<T>(cb);
                    executor.execute(futureTask);
                    try {
                        result = futureTask.get(timeout, unit);
                        break;
                    } catch (ExecutionException e) {
                        if (i == times) {
                            throw e;
                        }
                    } catch (InterruptedException e) {
                        if (ts.size() > 0) {
                            Thread t = ts.get(0);
                            if (t.isAlive()) {
                                t.interrupt();
                            }
                        }
                        futureTask.cancel(true);
                        throw e;
                    } catch (TimeoutException e) {
                        if (ts.size() > 0) {
                            Thread t = ts.get(0);
                            if (t.isAlive()) {
                                t.interrupt();
                            }
                        }
                        futureTask.cancel(true);
                        if (i == times) {
                            throw e;
                        }
                    }
                }

                Callable<T>[] callables = (Callable<T>[]) new Callable<?>[lastTimes];
                for (int j = 0; j < lastTimes; j++) {
                    callables[j] = callable;
                }
                // result = invokeMultiple(executor, timeout, unit, callables);
            }
        }
        return result;
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果,
     *
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param functions Function<? super Object, ? extends T>...
     * @return 返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T invokeMultiple(long timeout, TimeUnit unit, Function<? super Object, ? extends T>... functions) throws InterruptedException, ExecutionException, TimeoutException {
        return invokeMultiple(asyncPool, timeout, unit, functions);
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     *
     * @param executor  线程池
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param functions Function<? super Object, ? extends T>...
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T invokeMultiple(Executor executor, long timeout, TimeUnit unit, Function<? super Object, ? extends T>... functions) throws InterruptedException, ExecutionException, TimeoutException {
        T result = null;

        int lastTimes = functions.length;
        List<Thread> ts = new CopyOnWriteArrayList<>();
        CompletableFuture<?>[] futures = new CompletableFuture<?>[lastTimes];

        for (int j = 0; j < lastTimes; j++) {
            Function<? super Object, ? extends T> ifn = functions[j];
            Function<? super Object, ? extends T> fn = x -> {
                Thread thread = Thread.currentThread();
                ts.add(thread);
                T t = ifn.apply(null);
                ts.remove(thread);
                return t;
            };
            CompletableFuture<T> future = CompletableFuture.completedFuture(null).thenApplyAsync(fn, executor);
            futures[j] = future;
        }
        CompletableFuture<?> anyOf = CompletableFuture.anyOf(futures)
                .whenCompleteAsync((res, th) -> {
                    /*if (th == null) {
                        System.out.println("res:" + res);
                    } else {
                        System.out.println("err:" + th.getMessage());
                    }*/
                }, executor);

        try {
            result = (T) anyOf.get(timeout, unit);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            if (ts.size() > 0) {
                for (Thread t : ts) {
                    if (t.isAlive()) {
                        t.interrupt();
                    }
                }
            }

            for (CompletableFuture<?> completableFuture : futures) {
                if (!completableFuture.isDone()) {
                    completableFuture.cancel(true);
                }
            }
            throw e;
        }

        if (ts.size() > 0) {
            for (Thread t : ts) {
                if (t.isAlive()) {
                    t.interrupt();
                }
            }
        }
        for (CompletableFuture<?> completableFuture : futures) {
            if (!completableFuture.isDone()) {
                completableFuture.cancel(true);
            }
        }
        return result;
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     *
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param suppliers Supplier<T>...
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T supplyInvokeMultiple(long timeout, TimeUnit unit, Supplier<T>... suppliers) throws InterruptedException, ExecutionException, TimeoutException {
        return supplyInvokeMultiple(asyncPool, timeout, unit, suppliers);
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     *
     * @param executor  线程池
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param suppliers Supplier<T>...
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T supplyInvokeMultiple(Executor executor, long timeout, TimeUnit unit, Supplier<T>... suppliers) throws InterruptedException, ExecutionException, TimeoutException {
        T result = null;

        int lastTimes = suppliers.length;
        List<Thread> ts = new CopyOnWriteArrayList<>();
        CompletableFuture<?>[] futures = new CompletableFuture<?>[lastTimes];

        for (int j = 0; j < lastTimes; j++) {
            Supplier<T> isp = suppliers[j];
            Supplier<T> sp = () -> {
                Thread thread = Thread.currentThread();
                ts.add(thread);
                T t = isp.get();
                ts.remove(thread);
                return t;
            };
            CompletableFuture<T> future = CompletableFuture.supplyAsync(sp, executor);
            futures[j] = future;
        }
        CompletableFuture<?> anyOf = CompletableFuture.anyOf(futures)
                .whenCompleteAsync((res, th) -> {
                    /*if (th == null) {
                        System.out.println("res:" + res);
                    } else {
                        System.out.println("err:" + th.getMessage());
                    }*/
                }, executor);

        try {
            result = (T) anyOf.get(timeout, unit);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            if (ts.size() > 0) {
                for (Thread t : ts) {
                    if (t.isAlive()) {
                        t.interrupt();
                    }
                }
            }

            for (CompletableFuture<?> completableFuture : futures) {
                if (!completableFuture.isDone()) {
                    completableFuture.cancel(true);
                }
            }
            throw e;
        }

        if (ts.size() > 0) {
            for (Thread t : ts) {
                if (t.isAlive()) {
                    t.interrupt();
                }
            }
        }
        for (CompletableFuture<?> completableFuture : futures) {
            if (!completableFuture.isDone()) {
                completableFuture.cancel(true);
            }
        }
        return result;
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     *
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param callables Callable<T>...
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T invokeMultiple(long timeout, TimeUnit unit, Callable<T>... callables) throws InterruptedException, ExecutionException, TimeoutException {
        return invokeMultiple(asyncPool, timeout, unit, callables);
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     *
     * @param executor  线程池
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param callables Callable<T>...
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static <T> T invokeMultiple(Executor executor, long timeout, TimeUnit unit, Callable<T>... callables) throws InterruptedException, ExecutionException, TimeoutException {
        T result = null;

        int lastTimes = callables.length;
        List<Exception> es = new CopyOnWriteArrayList<>();
        List<Thread> ts = new CopyOnWriteArrayList<>();
        CompletableFuture<?>[] futures = new CompletableFuture<?>[lastTimes];

        for (int j = 0; j < lastTimes; j++) {
            Callable<T> callable = callables[j];
            Supplier<T> sp = () -> {
                Thread thread = Thread.currentThread();
                ts.add(thread);
                T t = null;
                try {
                    t = callable.call();
                    ts.remove(thread);
                    es.clear();
                } catch (InterruptedException e) {
                    if (ts.contains(thread)) {
                        es.add(e);
                        if (es.size() < lastTimes) {
                            long millis = unit.toMillis(timeout);
                            try {
                                Thread.interrupted();
                                thread.join(millis);
                                thread.interrupt();
                            } catch (InterruptedException e1) {
                                ts.remove(thread);
                            }
                        } else {
                            ts.remove(thread);
                        }
                    }
                } catch (Exception e) {
                    if (ts.contains(thread)) {
                        es.add(e);
                        if (es.size() < lastTimes) {
                            long millis = unit.toMillis(timeout);
                            try {
                                Thread.interrupted();
                                thread.join(millis);
                                thread.interrupt();
                            } catch (InterruptedException e1) {
                                ts.remove(thread);
                            }
                        } else {
                            ts.remove(thread);
                        }
                    }
                }
                return t;
            };
            //勿用,会导致多个任务不能同时并行执行
            //CompletableFuture<T> future = CompletableFuture.supplyAsync(sp,executor);
            CompletableFuture<T> future = CompletableFuture.supplyAsync(sp);
            futures[j] = future;
        }
        CompletableFuture<?> anyOf = CompletableFuture.anyOf(futures)
                .whenCompleteAsync((res, th) -> {
                    /*if (th == null) {
                        System.out.println("res:" + res);
                    } else {
                        System.out.println("err:" + th.getMessage());
                    }*/
                }, executor);

        try {
            result = (T) anyOf.get(timeout, unit);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            if (ts.size() > 0) {
                Iterator<Thread> tsi = ts.iterator();
                while (tsi.hasNext()) {
                    Thread t = tsi.next();
                    ts.remove(t);
                    if (t.isAlive()) {
                        t.interrupt();
                    }
                }
            }
            for (CompletableFuture<?> completableFuture : futures) {
                if (!completableFuture.isDone()) {
                    completableFuture.cancel(true);
                }
            }
            throw e;
        }

        if (ts.size() > 0) {
            Iterator<Thread> tsi = ts.iterator();
            while (tsi.hasNext()) {
                Thread t = tsi.next();
                ts.remove(t);
                if (t.isAlive()) {
                    t.interrupt();
                }
            }
        }
        for (CompletableFuture<?> completableFuture : futures) {
            if (!completableFuture.isDone()) {
                completableFuture.cancel(true);
            }
        }
        if (result == null && es.size() > 0) {
            Exception e = es.get(es.size() - 1);
            if (e instanceof InterruptedException) {
                throw (InterruptedException) e;
            } else if (e instanceof TimeoutException) {
                throw (TimeoutException) e;
            } else {
                throw new ExecutionException(e);
            }
        }
        return result;
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     *
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param callables Callable<T>...
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static List<Object> invokeMultipleList(long timeout, TimeUnit unit, Callable<Object>... callables) throws InterruptedException, ExecutionException, TimeoutException {
        return invokeMultipleList(asyncPool, timeout, unit, true, null, callables);
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     *
     * @param executor  线程池
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param callables Callable<T>...
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static List<Object> invokeMultipleList(Executor executor, long timeout, TimeUnit unit, Callable<Object>... callables) throws InterruptedException, ExecutionException, TimeoutException {
        return invokeMultipleList(executor, timeout, unit, true, null, callables);
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     * *如果allowEmpty为true,则等待所有操作完成,返回所有操作的结果
     * *如果allowEmpty为false,则判断emptyFilter是否包含所有完成操作的任一结果,如果都不包含,则返回所有操作的结果,否则抛出异常
     *
     * @param timeout     超时时间
     * @param unit        TimeUnit
     * @param allowEmpty  是否允许为空
     * @param callables   Callable<T>...
     * @param emptyFilter Function<Object, Boolean>
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static List<Object> invokeMultipleList(long timeout, TimeUnit unit, boolean allowEmpty, Function<Object, Boolean> emptyFilter, Callable<Object>... callables) throws InterruptedException, ExecutionException, TimeoutException {
        return invokeMultipleList(asyncPool, timeout, unit, allowEmpty, emptyFilter, callables);
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     * *如果allowEmpty为true,则等待所有操作完成,返回所有操作的结果
     * *如果allowEmpty为false,则判断emptyFilter是否包含所有完成操作的任一结果,如果都不包含,则返回所有操作的结果,否则抛出异常
     *
     * @param executor    线程池
     * @param timeout     超时时间
     * @param unit        TimeUnit
     * @param allowEmpty  是否允许为空
     * @param callables   Callable<T>...
     * @param emptyFilter Function<Object, Boolean>
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static List<Object> invokeMultipleList(Executor executor, long timeout, TimeUnit unit, boolean allowEmpty, Function<Object, Boolean> emptyFilter, Callable<Object>... callables) throws InterruptedException, ExecutionException, TimeoutException {
        long startTime = System.currentTimeMillis();

        int lastTimes = callables.length;
        List<Object> list = new ArrayList<>();
        List<Thread> ts = new CopyOnWriteArrayList<>();
        List<FutureTask<Object>> futureTaskList = new ArrayList<>(lastTimes);

        for (Callable<Object> callable : callables) {
            Callable<Object> cb = () -> {
                Thread thread = Thread.currentThread();
                ts.add(thread);
                Object t = null;
                try {
                    t = callable.call();
                    ts.remove(thread);
                } catch (Exception e) {
                    ts.remove(thread);
                    throw e;
                }
                return t;
            };
            FutureTask<Object> futureTask = new FutureTask<Object>(cb);
            executor.execute(futureTask);
            futureTaskList.add(futureTask);
        }

        FutureTask<Object> futureTask;
        long ntimeout;
        Object o;
        for (int i = 0; i < futureTaskList.size(); i++) {
            futureTask = futureTaskList.get(i);

            if (i == 0) {
                ntimeout = timeout;
            } else {
                if (Thread.interrupted()) {
                    if (ts.size() > 0) {
                        for (Thread t : ts) {
                            if (t.isAlive()) {
                                t.interrupt();
                            }
                        }
                    }
                    for (i = i + 1; i < futureTaskList.size(); i++) {
                        futureTask = futureTaskList.get(i);
                        if (!futureTask.isDone()) {
                            futureTask.cancel(true);
                        }
                    }
                    throw new InterruptedException();
                }

                long stopTime = System.currentTimeMillis();
                long time = stopTime - startTime;
                time = unit.convert(time, TimeUnit.MILLISECONDS);
                if (time >= timeout) {
                    if (ts.size() > 0) {
                        for (Thread t : ts) {
                            if (t.isAlive()) {
                                t.interrupt();
                            }
                        }
                    }
                    for (i = i + 1; i < futureTaskList.size(); i++) {
                        futureTask = futureTaskList.get(i);
                        if (!futureTask.isDone()) {
                            futureTask.cancel(true);
                        }
                    }
                    throw new InterruptedException();
                }

                ntimeout = timeout - time;
            }

            try {
                o = futureTask.get(ntimeout, unit);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                if (ts.size() > 0) {
                    for (Thread t : ts) {
                        if (t.isAlive()) {
                            t.interrupt();
                        }
                    }
                }
                futureTask.cancel(true);
                for (i = i + 1; i < futureTaskList.size(); i++) {
                    futureTask = futureTaskList.get(i);
                    if (!futureTask.isDone()) {
                        futureTask.cancel(true);
                    }
                }
                throw e;

            }
            if (allowEmpty || !emptyFilter.apply(o)) {
                list.add(o);
            } else {
                if (ts.size() > 0) {
                    for (Thread t : ts) {
                        if (t.isAlive()) {
                            t.interrupt();
                        }
                    }
                }

                for (i = i + 1; i < futureTaskList.size(); i++) {
                    futureTask = futureTaskList.get(i);
                    if (!futureTask.isDone()) {
                        futureTask.cancel(true);
                    }
                }
                throw new ExecutionException(new RuntimeException("filter empty value failed, value:" + o));
            }
        }
        return list;
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     * *如果callable1操作成功,并且callable2或者callable3至少有一个操作成功,则返回callable1操作和callable2或者callable3其中一个操作成功的结果,否则抛出异常
     *
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param callable1 callable1
     * @param callable2 callable2
     * @param callable3 callable3
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static List<Object> invokeMultipleListLast(long timeout, TimeUnit unit, Callable<Object> callable1, Callable<Object> callable2, Callable<Object> callable3) throws InterruptedException, ExecutionException, TimeoutException {
        return invokeMultipleListLast(asyncPool, timeout, unit, true, null, callable1, callable2, callable3);
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     * *如果callable1操作成功,并且callable2或者callable3至少有一个操作成功,则返回callable1操作和callable2或者callable3其中一个操作成功的结果,否则抛出异常
     *
     * @param executor  线程池
     * @param timeout   超时时间
     * @param unit      TimeUnit
     * @param callable1 callable1
     * @param callable2 callable2
     * @param callable3 callable3
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static List<Object> invokeMultipleListLast(Executor executor, long timeout, TimeUnit unit, Callable<Object> callable1, Callable<Object> callable2, Callable<Object> callable3) throws InterruptedException, ExecutionException, TimeoutException {
        return invokeMultipleListLast(executor, timeout, unit, true, null, callable1, callable2, callable3);
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     * *如果callable1操作成功,并且callable2或者callable3至少有一个操作成功,则返回callable1操作和callable2或者callable3其中一个操作成功的结果,否则抛出异常
     *
     * @param timeout     超时时间
     * @param unit        TimeUnit
     * @param allowEmpty  是否允许为空
     * @param callable1   callable1
     * @param callable2   callable2
     * @param callable3   callable3
     * @param emptyFilter Function<Object, Boolean>
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static List<Object> invokeMultipleListLast(long timeout, TimeUnit unit, boolean allowEmpty, Function<Object, Boolean> emptyFilter, Callable<Object> callable1, Callable<Object> callable2, Callable<Object> callable3) throws InterruptedException, ExecutionException, TimeoutException {
        return invokeMultipleListLast(asyncPool, timeout, unit, allowEmpty, emptyFilter, callable1, callable2, callable3);
    }

    /**
     * 通过线程池同时获取多个不同操作返回的结果
     * *如果callable1操作成功,并且callable2或者callable3至少有一个操作成功,则返回callable1操作和callable2或者callable3其中一个操作成功的结果,否则抛出异常
     *
     * @param executor    线程池
     * @param timeout     超时时间
     * @param unit        TimeUnit
     * @param allowEmpty  是否允许为空
     * @param callable1   callable1
     * @param callable2   callable2
     * @param callable3   callable3
     * @param emptyFilter Function<Object, Boolean>
     * @return 线程第一次返回的结果
     * @throws InterruptedException 线程终端异常
     * @throws ExecutionException   线程池异常
     * @throws TimeoutException     超时异常
     */
    public static List<Object> invokeMultipleListLast(Executor executor, long timeout, TimeUnit unit, boolean allowEmpty, Function<Object, Boolean> emptyFilter, Callable<Object> callable1, Callable<Object> callable2, Callable<Object> callable3) throws InterruptedException, ExecutionException, TimeoutException {
        long startTime = System.currentTimeMillis();

        List<Object> list = new ArrayList<>();
        Object o1 = null;
        Object o2 = null;

        List<Thread> ts = new CopyOnWriteArrayList<>();

        Callable<Object> cb1 = () -> {
            Thread thread = Thread.currentThread();
            ts.add(thread);
            Object t = null;
            try {
                t = callable1.call();
                ts.remove(thread);
            } catch (Exception e) {
                ts.remove(thread);
                throw e;
            }
            return t;
        };
        Callable<Object> cb2 = () -> {
            Thread thread = Thread.currentThread();
            ts.add(thread);
            Object t = null;
            try {
                t = callable2.call();
                ts.remove(thread);
            } catch (Exception e) {
                ts.remove(thread);
                throw e;
            }
            return t;
        };
        Callable<Object> cb3 = () -> {
            Thread thread = Thread.currentThread();
            ts.add(thread);
            Object t = null;
            try {
                t = callable3.call();
                ts.remove(thread);
            } catch (Exception e) {
                ts.remove(thread);
                throw e;
            }
            return t;
        };

        FutureTask<Object> futureTask1 = new FutureTask<Object>(cb1);
        FutureTask<Object> futureTask2 = new FutureTask<Object>(cb2);
        executor.execute(futureTask1);
        executor.execute(futureTask2);
        try {
            o1 = futureTask1.get(timeout, unit);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            if (ts.size() > 0) {
                for (Thread t : ts) {
                    if (t.isAlive()) {
                        t.interrupt();
                    }
                }
            }
            futureTask1.cancel(true);
            if (!futureTask2.isDone()) {
                futureTask2.cancel(true);
            }
            throw e;
        }

        if (allowEmpty || !emptyFilter.apply(o1)) {
            if (Thread.interrupted()) {
                if (ts.size() > 0) {
                    for (Thread t : ts) {
                        if (t.isAlive()) {
                            t.interrupt();
                        }
                    }
                }
                if (!futureTask2.isDone()) {
                    futureTask2.cancel(true);
                }
                throw new InterruptedException();
            }

            long stopTime = System.currentTimeMillis();
            long time = stopTime - startTime;
            time = unit.convert(time, TimeUnit.MILLISECONDS);
            if (time >= timeout) {
                if (ts.size() > 0) {
                    for (Thread t : ts) {
                        if (t.isAlive()) {
                            t.interrupt();
                        }
                    }
                }
                if (!futureTask2.isDone()) {
                    futureTask2.cancel(true);
                }
                throw new InterruptedException();
            }

            if (futureTask2.isDone()) {
                try {
                    o2 = futureTask2.get();
                } catch (InterruptedException | CancellationException e) {
                    if (ts.size() > 0) {
                        for (Thread t : ts) {
                            if (t.isAlive()) {
                                t.interrupt();
                            }
                        }
                    }

                    if (!futureTask2.isDone()) {
                        futureTask2.cancel(true);
                    }
                    throw e;
                } catch (ExecutionException e) {
                    if (ts.size() > 0) {
                        for (Thread t : ts) {
                            if (t.isAlive()) {
                                t.interrupt();
                            }
                        }
                    }
                    if (!futureTask2.isDone()) {
                        futureTask2.cancel(true);
                    }

                    long ntimeout = timeout - time;
                    FutureTask<Object> futureTask3 = new FutureTask<Object>(cb3);
                    executor.execute(futureTask3);
                    try {
                        o2 = futureTask3.get(timeout, unit);
                    } catch (InterruptedException | ExecutionException | TimeoutException e1) {
                        if (ts.size() > 0) {
                            for (Thread t : ts) {
                                if (t.isAlive()) {
                                    t.interrupt();
                                }
                            }
                        }
                        if (!futureTask3.isDone()) {
                            futureTask3.cancel(true);
                        }
                        throw e1;
                    }
                }
            } else {
                try {
                    long ntimeout = timeout - time;
                    o2 = invokeMultiple(executor, ntimeout, unit, () -> futureTask2.get(ntimeout, unit), cb3);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    if (ts.size() > 0) {
                        for (Thread t : ts) {
                            if (t.isAlive()) {
                                t.interrupt();
                            }
                        }
                    }
                    if (!futureTask2.isDone()) {
                        futureTask2.cancel(true);
                    }
                    throw e;
                }
            }
        } else {
            if (!futureTask2.isDone()) {
                futureTask2.cancel(true);
            }
            throw new ExecutionException(new RuntimeException("filter empty value failed, value:" + o1));
        }
        if (ts.size() > 0) {
            for (Thread t : ts) {
                if (t.isAlive()) {
                    t.interrupt();
                }
            }
        }
        list.add(o1);
        list.add(o2);
        return list;
    }

    /**
     * 判断参数是否为空
     *
     * @param o Object 参数
     * @return boolean 是否为空
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return false;
        }

        if (o instanceof String) {
            return "".equals(o);
        } else if (o instanceof Byte) {
            return ((Byte) o) <= 0;
        } else if (o instanceof Short) {
            return ((Short) o) <= 0;
        } else if (o instanceof Integer) {
            return ((Integer) o) <= 0;
        } else if (o instanceof Long) {
            return ((Long) o) <= 0;
        } else if (o instanceof Float) {
            return ((Float) o) <= 0;
        } else if (o instanceof Double) {
            return ((Double) o) <= 0;
        } else if (o instanceof BigDecimal) {
            return ((BigDecimal) o).compareTo(BigDecimal.ZERO) <= 0;
        } else if (o instanceof Collection) {
            return ((Collection) o).size() == 0;
        } else if (o instanceof Map) {
            return ((Map) o).size() == 0;
        }
        return false;
    }

    //@Resource(name = "logThreadPool")
    //private  static ThreadPoolExecutor threadPoolExecutor;
    //总时间
    private static long totalTime = 120;
    //时间单位
    private static TimeUnit unit = TimeUnit.SECONDS;

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor((int) (30 * 0.8),
                30,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new ThreadPoolExecutor.CallerRunsPolicy());
        List<Object> list = null;
        String firstResult = "";
        String secondResult = "";
        try {
            list = invokeMultipleList(threadPoolExecutor, totalTime, unit, false, MethodTimeoutUtil::isEmpty,
                    () -> "first list result",
                    () -> "second list result");
        } catch (Exception e) {
            LOGGER.error("MethodTimeoutUtils|invokeMultipleList|error|", e);
        }

        if (list != null && list.size() > 1) {
            firstResult = (String) list.get(0);
            secondResult = (String) list.get(1);
        }

        System.out.println("firstResult:" + firstResult);
        System.out.println("secondResult:" + secondResult);
    }
}
