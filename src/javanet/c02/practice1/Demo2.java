/**
 * Copyright (C), 2019
 * FileName: SecondThreadDemo
 * Author:   huangwenyuan
 * Date:     2019/3/12 14:11
 * Description:
 */

package javanet.c02.practice1;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/12
 * @since 1.0.0
 */
public class Demo2 extends Thread {
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
        Random random = new Random();
        int n = random.nextInt(10);
        for (int i = 0; i < n; i++) {
            double num = Math.random();
            if (num > 0.5) {
                a++;
            } else {
                b++;
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Demo2();
        Thread t2 = new Demo2();
        Thread t3 = new Demo2();
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
        System.out.println("正面出现的次数" + a);
        //保留两位小数
        DecimalFormat df = new DecimalFormat("#0.00");
        System.out.println("正面出现的概率" + df.format(a / (a * 1.0 + b)));
    }
}

    