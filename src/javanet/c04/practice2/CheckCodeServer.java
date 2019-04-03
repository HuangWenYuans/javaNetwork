/**
 * Copyright (C), 2019
 * FileName: CheckCodeServer
 * Author:   huangwenyuan
 * Date:     2019/04/03 下午 11:15
 * Description:
 */

package javanet.c04.practice2;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/03
 * @since 1.0.0
 */
public class CheckCodeServer {
    public static void main(String[] args) throws IOException {
        byte[] inBuff = new byte[2048];
        //接受数据的对象
        DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
        //发送数据的对象
        DatagramPacket outPacket;
        //随机生成的校验码
        String code = verifyCode();
        DatagramSocket socket = new DatagramSocket(25000);
        //接受客户端发送来的数据
        socket.receive(inPacket);
        //将校验码转换成byte数组
        byte[] sendData = code.getBytes();
        outPacket = new DatagramPacket(sendData, sendData.length, inPacket.getSocketAddress());
        //发送数据
        socket.send(outPacket);
    }

    /***
     * 生成随机校验码的方法
     */
    private static String verifyCode() {
        String str = "";
        for (int i = 0; i < 6; i++) {
            //随机选择是生成大写字母、小写字母还是数字
            int flag = new Random().nextInt(3);

            switch (flag) {
                case 0:
                    //随机生成数字
                    int num = new Random().nextInt(10);
                    str += num;
                    break;
                case 1:
                    //随机生成大写字母
                    char capital = (char) (new Random().nextInt(26) + 65);
                    str += capital;
                    break;
                default:
                    //随机生成小写字母
                    char lowerCase = (char) (new Random().nextInt(26) + 97);
                    str += lowerCase;
                    break;
            }
        }
        System.out.println(str);
        return str;
    }
}

    