/**
 * Copyright (C), 2019
 * FileName: FirstThreadDemo
 * Author:   huangwenyuan
 * Date:     2019/3/19 0019 下午 07:52
 * Description:
 */

package javanet.l02;


import java.util.Date;
import java.util.Scanner;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/19 0019
 * @since 1.0.0
 */
public class FirstThreadDemo extends Thread {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("请输入需要创建的线程数:");
        Scanner scanner = new Scanner(System.in);
        //获取用户输入的次数
        int n = scanner.nextInt();
        Thread[] threads = new FirstThreadDemo[n];
        for (int i = 0; i < n; i++) {
            threads[i] = new FirstThreadDemo();
            threads[i].start();
            Thread.sleep(1000);
        }
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始运行时间" + new Date(System.currentTimeMillis()));
    }
}

    