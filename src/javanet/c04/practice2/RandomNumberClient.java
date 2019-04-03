/**
 * Copyright (C), 2019
 * FileName: RandomNumberServer
 * Author:   huangwenyuan
 * Date:     2019/04/04 上午 12:21
 * Description:
 */

package javanet.c04.practice2;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/04
 * @since 1.0.0
 */
public class RandomNumberClient {
    public static void main(String[] args) {
        try {
            byte[] inBuff = new byte[1024];
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
            DatagramPacket outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName("localhost"), 20000);
            Random random = new Random();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i <= 5; i++) {
                int num = random.nextInt(1000);
                list.add(num);
                byte[] buff = list.get(i).toString().getBytes();
                //设置数据
                outPacket.setData(buff);
                //发送数据
                socket.send(outPacket);
                //读取socket中的数据
                socket.receive(inPacket);
            }
            System.out.println("随机数的和为：" + new String(inBuff, 0, inBuff.length));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

/***
 * 服务器类
 */
class RandomNumberServer {
    public static void main(String[] args) {
        byte[] inBuff = new byte[1024];
        DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
        DatagramPacket outPacket = null;
        List<Integer> list = null;
        try {
            //创建DatagramSocket对象
            DatagramSocket socket = new DatagramSocket(20000);
            //循环接受客户端发送的数据
            list = new ArrayList<>();
            int sum = 0;
            for (int i = 0; i <= 5; i++) {
                socket.receive(inPacket);
                //将接受到的内容转换成字符串输出
                String numStr = new String(inBuff, 0, inPacket.getLength());
                //将获取到的数字累加
                sum += Integer.parseInt(numStr);
                //存入到list中
                list.add(Integer.parseInt(numStr));
                //将计算的结果发送回客户端
                byte[] sendData = String.valueOf(sum).getBytes();
                outPacket = new DatagramPacket(sendData, sendData.length, inPacket.getSocketAddress());
                //发送数据
                socket.send(outPacket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    