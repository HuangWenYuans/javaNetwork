/**
 * Copyright (C), 2019
 * FileName: VehicleLocationServer
 * Author:   huangwenyuan
 * Date:     2019/04/07 下午 09:25
 * Description:
 */

package javanet.c04.practice2;


import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

/**
 * 功能描述: 车辆定位程序服务端
 *
 * @author huangwenyuan
 * @create 2019/04/07
 * @since 1.0.0
 */
public class VehicleLocationServer {
    public static void main(String[] args) {
        byte[] inBuff = new byte[1024];
        DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
        try {
            //创建DatagramSocket对象
            DatagramSocket socket = new DatagramSocket(20001);
            //定时器对象
            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    List<Integer> list = new ArrayList<>();
                    for (int i = 0; i < 2; i++) {
                        //接受服务器发送的车辆坐标
                        try {
                            socket.receive(inPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //将接受到的内容转换成字符串
                        String numStr = new String(inBuff, 0, inPacket.getLength());
                        list.add(Integer.valueOf(numStr));
                    }
                    //设中心点的位置为(0,0)，计算距离
                    double distance = Math.sqrt(Math.pow(list.get(0) - 0, 2) + Math.pow(list.get(1) - 0, 2));
                    String str = null;
                    DatagramPacket outPacket = null;

                    //如果距离超过限定距离，则发出警报
                    if (distance > 35) {
                        str = "车辆离中心范围超出限制！";
                        byte[] sendData = str.getBytes();
                        outPacket = new DatagramPacket(sendData, sendData.length, inPacket.getSocketAddress());
                        //发送数据
                        try {
                            socket.send(outPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 0, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class VehicleLocationClient {
    public static void main(String[] args) {
        try {
            byte[] inBuff = new byte[1024];
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
            DatagramPacket outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName("localhost"), 20001);
            List<Integer> list = new ArrayList<>();

            final int[] x = {40};
            final int[] y = {40};

            Timer timer = new Timer();
            //每隔1秒发送当前位置
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    //先将列表中的元素清空
                    list.clear();
                    list.add(x[0]);
                    list.add(y[0]);

                    System.out.println(x[0]);
                    System.out.println(y[0]);
                    for (int i = 0; i < 2; i++) {
                        //将车辆所在位置发送给服务器
                        byte[] buff = list.get(i).toString().getBytes();
                        //设置数据
                        outPacket.setData(buff);
                        //发送数据
                        try {
                            socket.send(outPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //读取socket中的数据
                    try {
                        socket.receive(inPacket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String str = new String(inBuff, 0, inBuff.length);
                    //打印提示信息
                    System.out.println(str);
                    //如果车辆距离中心超出范围，则往中心行驶
                    if (str.contains("超出限制")) {
                        x[0] = x[0] - 5;
                        y[0] = y[0] - 5;
                    }
                }
            }, 0, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



    