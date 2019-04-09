/**
 * Copyright (C), 2019
 * FileName: BusLineQueryServer
 * Author:   huangwenyuan
 * Date:     2019/04/09 下午 07:31
 * Description:
 */

package javanet.l05;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 功能描述:
 * 公交车查询程序服务器端
 *
 * @author huangwenyuan
 * @create 2019/04/09
 * @since 1.0.0
 */
public class BusLineQueryServer {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(25000);
        Socket s = ss.accept();
        //启动子线程
        new Thread(new ServerThread(s)).start();
        Scanner scanner = new Scanner(System.in);

    }
}

/***
 * 服务器子线程
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
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\IDEA\\javaNetwork\\src\\javanet\\l05\\route.txt")));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            //将数据写入socket流中
            PrintStream ps = new PrintStream(s.getOutputStream());
            ps.println("ssss");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    