/**
 * Copyright (C), 2019
 * FileName: SecondThreadDemo1
 * Author:   huangwenyuan
 * Date:     2019/3/19 0019 下午 08:22
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
public class SecondThreadDemo1 implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        SecondThreadDemo1 thread = new SecondThreadDemo1();
        for (int i = 0; i <= 2; i++) {
            Thread t = new Thread(thread, "子线程" + i);
            t.start();
            t.join();
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

    