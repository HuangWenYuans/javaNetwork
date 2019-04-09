/**
 * Copyright (C), 2019
 * FileName: BusLineQueryClient
 * Author:   huangwenyuan
 * Date:     2019/04/09 下午 07:30
 * Description:
 */

package javanet.l05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 功能描述:
 * 公交车查询客户端程序
 *
 * @author huangwenyuan
 * @create 2019/04/09
 * @since 1.0.0
 */
public class BusLineQueryClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 25000);
        System.out.println("请输入需要查询的公交车:");
        Scanner scanner = new Scanner(System.in);
        String busNum = scanner.nextLine();

        //获取socket对应的输出流
        PrintStream ps = new PrintStream(socket.getOutputStream());
        //将需要查询的公交车号发送给服务器
        ps.println(busNum);
        //启动客户端子线程
        new Thread(new ClientThread(socket)).start();

    }
}

/***
 * 客户端子线程
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


    