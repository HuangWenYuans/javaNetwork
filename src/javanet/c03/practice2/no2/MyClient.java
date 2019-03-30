/**
 * Copyright (C), 2019
 * FileName: Demo2
 * Author:
 * Date:     2019/03/22 下午 09:51
 * Description:
 */

package javanet.c03.practice2.no2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 功能描述:
 *
 * @author
 * @create 2019/03/22
 * @since 1.0.0
 */
public class MyClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 30000);
        //客户端启动ClientThread线程不断读取来自服务器的数据
        new Thread(new ClientThread(s)).start();
        //获取socket对应的输出流
        PrintStream ps = new PrintStream(s.getOutputStream());
        System.out.println("请输入两个数字:");
        //获取用户键盘输入
        Scanner scanner = new Scanner(System.in);
        double num1 = scanner.nextDouble();
        double num2 = scanner.nextDouble();
        //将用户输入的数字写入Socket对应的输出流
        ps.println(num1);
        ps.println(num2);
    }
}

/***
 * 客户端线程
 */
class ClientThread implements Runnable {
    private Socket s;
    BufferedReader br;

    public ClientThread(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String content = null;
            while ((content = br.readLine()) != null) {
                //不断读取Socket输入流中的内容，并将这些内容打印出来
                System.out.println(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/***
 * 服务器端
 */
class MyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(30000);
        while (true) {
            Socket s = ss.accept();
            //启动服务器线程
            new Thread(new ServerThread(s)).start();
        }
    }
}

/***
 * 服务器端线程
 */
class ServerThread implements Runnable {
    Socket s;
    BufferedReader br;

    public ServerThread(Socket s) throws IOException {
        this.s = s;
        //获取Socket输入流并包装成缓冲字符流
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        try {
            //获取用户输入的两个数的值
            double num1 = Double.valueOf(br.readLine());
            double num2 = Double.valueOf(br.readLine());
            double sum = num1 + num2;
            //相加后写入到Socket输出流中
            PrintStream ps = new PrintStream(s.getOutputStream());
            ps.println("两个数的和为:" + sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


    