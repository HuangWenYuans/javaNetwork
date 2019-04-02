/**
 * Copyright (C), 2019
 * FileName: TemperatureClient
 * Author:   huangwenyuan
 * Date:     2019/04/02 下午 07:10
 * Description:
 */

package javanet.l04.folder1;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 功能描述: 温度曲线客户端程序
 *
 * @author huangwenyuan
 * @create 2019/04/02
 * @since 1.0.0
 */
public class TemperatureClient {
    public static void main(String[] args) {
        try {
            DatagramSocket datagramSocket = new DatagramSocket();
            DatagramPacket outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName("localhost"), 20000);
            Random random = new Random();
            List<Integer> list = new ArrayList<>();

            for (int i = 0; i < 24; i++) {
                //生成一个0到75之间的随机数
                int num = random.nextInt(75);
                //温度为-30到45之间的数字
                int temp = num - 30;
                //将生成的随机温度加入到列表中
                list.add(temp);
                //将数字列表转换成字节数组
                byte[] buff = list.get(i).toString().getBytes();
                //设置数据
                outPacket.setData(buff);
                //每隔一秒发送一次
                Thread.sleep(1000);
                //发送数据
                datagramSocket.send(outPacket);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

    