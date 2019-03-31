/**
 * Copyright (C), 2019
 * FileName: Thread02
 * Author:   huangwenyuan
 * Date:     2019/3/7 8:03
 * Description:
 */

package javanet.e02;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/7
 * @since 1.0.0
 */
public class e02_02 extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("子线程:" + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("主线程" + i);
        }
        //启动子线程
        new e02_02().start();
    }
}

    