/**
 * Copyright (C), 2019
 * FileName: SecondThreadDemo
 * Author:   huangwenyuan
 * Date:     2019/3/19 0019 下午 08:15
 * Description:
 */

package javanet.l02;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/19 0019
 * @since 1.0.0
 */
public class SecondThreadDemo extends Thread {
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new SecondThreadDemo[3];
        for (int i = 0; i < 3; i++) {
            threads[i] = new SecondThreadDemo();
            threads[i].start();
            threads[i].join();
        }
    }

    @Override
    public void run() {
        System.out.print(Thread.currentThread().getName() + ":");
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}

    