/**
 * Copyright (C), 2019
 * FileName: Demo1
 * Date:     2019/03/21 下午 09:57
 * Description:
 */

package javanet.c03.practice2.no1;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 功能描述:
 *
 * @author
 * @create 2019/03/21
 * @since 1.0.0
 */
public class Demo1 {
    public static void main(String[] args) throws IOException {
        //创建一个ServerSocket，用来监听客户端Socket的连接请求
        ServerSocket ss = new ServerSocket(30000);
        //生成随机数
        Random random = new Random();
        int num = random.nextInt();
        while (true) {
            Socket socket = ss.accept();
            PrintStream ps = new PrintStream(socket.getOutputStream());
            ps.println(num);
            ps.flush();
            ps.close();
            socket.close();
        }
    }
}

    