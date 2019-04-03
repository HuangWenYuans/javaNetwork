/**
 * Copyright (C), 2019
 * FileName: e05_01
 * Author:   huangwenyuan
 * Date:     2019/04/03 下午 07:55
 * Description:
 */

package javanet.e05;


import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/03
 * @since 1.0.0
 */
public class e05_01 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress host;
        if (args.length > 0) {
            host = InetAddress.getByName(args[0]);
        } else {
            host = InetAddress.getByName("time.nist.gov");
        }
        UDPPoke poker = new UDPPoke(host, 37);
        byte[] response = poker.poke();
        if (response == null) {
            System.out.println("在分配的时间内无响应");
            return;
        } else if (response.length != 4) {
            System.out.println("无法识别的响应格式");
            return;
        }
        //    将time协议的时间与Date类的时间进行转换
        long translator = 2208988800L;
        long secondSince1900 = 0;
        for (int i = 0; i < 4; i++) {
            secondSince1900 = (secondSince1900 << 8) | (response[i] & 0x000000FF);
        }
        long secondSince1970 = secondSince1900 - translator;
        long msSince1970 = secondSince1970 * 1000;
        //将时间转换为Date格式
        Date time = new Date(msSince1970);
        //将日期转换为指定格式
        String formatDate = new SimpleDateFormat("yyyy年M月dd日 HH时mm分ss秒").format(time);
        System.out.println(formatDate);
    }

}

class UDPPoke {
    /***
     * 缓冲区
     */
    private int buffSize;
    /***
     * 超时时间
     */
    private int timeout;
    /***
     * 地址
     */
    private InetAddress host;
    /***
     * 端口号
     */
    private int port;

    public UDPPoke() {
    }

    public UDPPoke(int buffSize, int timeout, InetAddress host, int port) {
        this.buffSize = buffSize;
        this.timeout = timeout;
        this.host = host;
        this.port = port;
    }

    public UDPPoke(InetAddress host, int port) {
        this.host = host;
        this.port = port;
        this.buffSize = 8192;
        this.timeout = 30000;
    }

    public byte[] poke() {
        try (DatagramSocket socket = new DatagramSocket(0)) {
            DatagramPacket outPacket = new DatagramPacket(new byte[1], 1, host, port);
            socket.connect(host, port);
            socket.setSoTimeout(timeout);
            socket.send(outPacket);
            DatagramPacket inPacket = new DatagramPacket(new byte[buffSize], buffSize);
            //阻塞，直到接收到响应
            socket.receive(inPacket);
            //接收到的字节数
            int numBytes = inPacket.getLength();
            byte[] response = new byte[numBytes];
            System.arraycopy(inPacket.getData(), 0, response, 0, numBytes);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}


    