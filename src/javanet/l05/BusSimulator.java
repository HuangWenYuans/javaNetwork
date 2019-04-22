/**
 * Copyright (C), 2019
 * FileName: BusSimulator
 * Author:   huangwenyuan
 * Date:     2019/04/09 下午 07:33
 * Description:
 */

package javanet.l05;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 功能描述: 公交车模拟程序
 *
 * @author huangwenyuan
 * @create 2019/04/09
 * @since 1.0.0
 */
public class BusSimulator {
    public static void main(String[] args) {
        byte[] inBuff = new byte[1024];
        DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
        DatagramPacket outPacket = null;
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(20001);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                //创建DatagramSocket对象
                //接受客户端发送的数据
                socket.receive(inPacket);
                //将接受到的内容转换成字符串输出
                String numStr = new String(inBuff, 0, inPacket.getLength());
                //获取所查询公交车共有几站
                int index = getStationInfo(numStr);
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    Random random = new Random();
                    //随机生成一个在该范围内的数字
                    int num = 1 + random.nextInt(index == 0 ? 1 : index);
                    list.add(num);
                    //将获取到的站点信息发送给客户端
                    byte[] sendData = list.get(i).toString().getBytes();
                    outPacket = new DatagramPacket(sendData, sendData.length, inPacket.getSocketAddress());
                    //发送数据
                    socket.send(outPacket);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static int getStationInfo(String busNum) {
        int begin = 0;
        int end = 0;

        try {
            //获取用户输入的公交车编号
            LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream("D:\\IDEA\\javaNetwork\\src\\javanet\\l05\\route.txt")));
            String line = null;
            //获取所查询公交车途径站点的开始行号和结束行号
            while ((line = reader.readLine()) != null) {
                if (line.equals("<" + busNum + ">")) {
                    begin = reader.getLineNumber() + 1;
                }
                if (line.equals(busNum + "--END")) {
                    end = reader.getLineNumber();
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回所查公交车的站台数
        return (end - begin) / 2;
    }
}

    