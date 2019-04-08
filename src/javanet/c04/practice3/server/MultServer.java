/**
 * Copyright (C), 2019
 * FileName: MultServer
 * Author:   huangwenyuan
 * Date:     2019/04/08 上午 10:16
 * Description:
 */

package javanet.c04.practice3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/08
 * @since 1.0.0
 */
public class MultServer {
    public static void main(String args[]) throws IOException {
        System.out.println("建立并等待连接。。。。");
        ServerSocket serverSocket = new ServerSocket(5500);
        Socket connectToClient = null;
        while (true) {
            connectToClient = serverSocket.accept();
            new ServerThread(connectToClient);
        }
    }
}

    