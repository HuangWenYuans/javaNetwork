/**
 * Copyright (C), 2019
 * FileName: ThirdThreadDemo
 * Author:   huangwenyuan
 * Date:     2019/3/17 0017 下午 08:03
 * Description:
 */

package javanet.c02.practice1;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/17 0017
 * @since 1.0.0
 */

public class Demo3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("请输入需要计算阶乘的数字");
        Scanner scanner = new Scanner(System.in);
        //需要计算阶乘的数字
        int n = scanner.nextInt();
        System.out.println("需要分解给多少个子线程");
        //分解给多少个线程执行
        int m = scanner.nextInt();
        //创建一个通用池
        ForkJoinPool pool = ForkJoinPool.commonPool();
        //提交可分解的CalTask任务
        Future<Long> future = pool.submit(new CalTask(1, n));
        System.out.println(n+"的阶乘为:"+future.get());
        //    关闭线程池
        pool.shutdown();
    }
}

class CalTask extends RecursiveTask<Long> {
    /***
     * 临界值，即子任务分配的最大任务数
     */
    private int threshold = 200;
    private int start;
    private int end;
    /***
     * 阶乘计算的结果
     */
    private long sum;

    public CalTask() {
    }

    public CalTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        sum = 1;
        if (end == 0) {
            sum = 0;
            return sum;
        }
        if (end - start < threshold) {
            for (int i = start; i <= end; i++) {
                sum *= i;
            }
            return sum;
        } else {
            //    当end与start之间的差大于threshold时,将大任务分解成小任务
            int mid = (start + end) / 2;
            CalTask left = new CalTask(start, end);
            CalTask right = new CalTask(mid, end);
            //    并行执行两个小任务
            left.fork();
            right.fork();
            //    将两个小任务的结果合并
            return left.join() + right.join();
        }
    }
}

    