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
        while (true) {
            Socket s = ss.accept();
            //启动子线程
            new Thread(new ServerThread(s)).start();
        }
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
            //获取用户输入的公交车编号
            String busNum = br.readLine();

            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream("D:\\IDEA\\javaNetwork\\src\\javanet\\l05\\route.txt")));
            String line = null;
            int begin = 0;
            int end = 0;

            //获取所查询公交车途径站点的开始行号和结束行号
            while ((line = reader.readLine()) != null) {
                if (line.contains("<" + busNum + ">")) {
                    begin = reader.getLineNumber() + 1;
                }
                if (line.contains(busNum + "--END")) {
                    end = reader.getLineNumber();
                }
            }
            reader.close();

            PrintStream ps = new PrintStream(s.getOutputStream());
            //获取开始后到结束行的内容
            LineNumberReader lr = new LineNumberReader(new InputStreamReader(new FileInputStream("D:\\IDEA\\javaNetwork\\src\\javanet\\l05\\route.txt")));
            while ((line = lr.readLine()) != null) {
                for (int i = begin; i < end; i++) {
                    if (lr.getLineNumber() == i) {
                        //将站点信息写入socket流中
                        ps.println(line);
                    }
                }
            }
            lr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    