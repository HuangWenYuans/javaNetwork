/**
 * Copyright (C), 2019
 * FileName: InetAddressTset
 * Author:   huangwenyuan
 * Date:     2019/3/12 13:36
 * Description:
 */

package javanet.c02.practice1;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/12
 * @since 1.0.0
 */
public class Demo1 implements Runnable {
    /***
     * 正面出现的次数
     */
    private static int a;
    /***
     * 反面出现的次数
     */
    private static int b;

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            double num = Math.random();
            if (num > 0.5) {
                //正面
                a++;
            } else {
                //反面
                b++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Demo1 st = new Demo1();
        Thread t1 = new Thread(st, "子线程1");
        Thread t2 = new Thread(st, "子线程2");
        Thread t3 = new Thread(st, "子线程3");
        //三个掷硬币子线程结束后，再启动主线程
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
        if (a > 3) {
            System.out.println("正面");
        }
    }
}

    