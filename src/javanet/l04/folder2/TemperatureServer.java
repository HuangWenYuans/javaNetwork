/**
 * Copyright (C), 2019
 * FileName: TemperatureServer
 * Author:   huangwenyuan
 * Date:     2019/04/02 下午 07:35
 * Description: 温度曲线服务端程序
 */

package javanet.l04.folder2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 功能描述: 温度曲线服务端程序
 * @author huangwenyuan
 * @create 2019/04/02
 * @since 1.0.0
 */
public class TemperatureServer {
    public static void main(String[] args) {
        byte[] inBuff = new byte[1024];
        DatagramPacket inPacket = new DatagramPacket(inBuff,inBuff.length);

        try {
            //创建DatagramSocket对象
            DatagramSocket datagramSocket = new DatagramSocket(20000);
            //循环接受客户端发送的数据
            for (int i = 0; i <1000 ; i++) {
                datagramSocket.receive(inPacket);
                //将接受到的内容转换成字符串输出
                System.out.println(new String(inBuff,0,inPacket.getLength()));
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    