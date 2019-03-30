/**
 * Copyright (C), 2019
 * FileName: MyClient
 * Author:
 * Date:     2019/03/22 下午 10:29
 * Description:
 */

package javanet.c03.practice3;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 功能描述:
 *
 * @author
 * @create 2019/03/22
 * @since 1.0.0
 */
public class MyClient {

    public static void main(String[] args) throws IOException {
        for (int j = 0; j < 3; j++) {
            String tmp = "第" + (j + 1) + "次计算:请输入需要计算的数字(以空格隔开)";
            String str = JOptionPane.showInputDialog(tmp);
            String[] strs = str.split(" ");
            Socket s = new Socket("localhost", 30000);
            //客户端启动ClientThread线程不断读取来自服务器的数据
            Thread thread = new Thread(new ClientThread(s));
            thread.start();
            //获取socket对应的输出流
            PrintStream ps = new PrintStream(s.getOutputStream());
            //先将用户输入的数字个数发送给服务器端
            ps.println(strs.length);
            for (int i = 0; i < strs.length; i++) {
                //将用户输入的数字写入Socket输出流中
                ps.println(Double.parseDouble(strs[i]));
            }
        }
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
                //不断读取Socket输入流中的内容，并将这些内容以界面的形式显示出来
                JOptionPane.showMessageDialog(null, content);
            }
        } catch (IOException e) {
            System.out.println("服务器已经关闭，无法连接服务器！");
        }
    }
}

/***
 * 服务器端
 */
class MyServer {
    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(30000);
        //while (true) {
        for (int i = 0; i < 3; i++) {
            Socket s = ss.accept();
            //启动服务器线程
            new Thread(new ServerThread(s)).start();
        }
    }
}

/***
 * 服务器端子线程
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
            //先获取到客户端用户键盘输入的数字的个数
            int n = Integer.valueOf(br.readLine());
            double[] nums = new double[n];
            //和
            double sum = 0;
            //平均数
            double average = 0;
            //方差
            double variance = 0;
            //计算输入数字的和
            for (int i = 0; i < n; i++) {
                //遍历读取用户输入的数字
                nums[i] = Double.valueOf(br.readLine());
                sum += nums[i];
            }
            //计算输入数字的平均数
            average = sum / n;
            for (int i = 0; i < n; i++) {
                variance += Math.pow((nums[i] - average), 2);
            }
            //获取Socket对应的输出流
            PrintStream ps = new PrintStream(s.getOutputStream());
            //将计算结果写入到输出流中
            ps.println("输入数字的和为:" + sum);
            ps.println("输入数字的平均数为:" + average);
            ps.println("输入数字的方差为:" + variance / n);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
