/**
 * Copyright (C), 2019
 * FileName: Demo3
 * Author:
 * Date:     2019/03/21 下午 10:15
 * Description:
 */

package javanet.c03.practice1;

import java.io.IOException;
import java.net.Socket;

/**
 * 功能描述:
 *
 * @author
 * @create 2019/03/21
 * @since 1.0.0
 */
public class Demo3 {
    public static void main(String[] args) {
        try {
            //echo服务
            Socket socket1 = new Socket("localhost", 7);
        } catch (IOException e) {
            System.out.println("echo服务未开启");
        } finally {
            System.out.println("echo服务已处于运行状态");

        }
        try {
            //daytime服务
            Socket socket2 = new Socket("localhost", 13);
        } catch (IOException e) {
            System.out.println("daytime服务未开启");
        } finally {
            System.out.println("daytime服务已处于运行状态");
        }
        try {
            //web服务端口
            Socket socket3 = new Socket("localhost", 80);
        } catch (IOException e) {
            System.out.println("web服务未开启");
        } finally {
            System.out.println("web服务已处于运行状态");
        }
    }
}

    