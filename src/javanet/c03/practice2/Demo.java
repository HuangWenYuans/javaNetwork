/**
 * Copyright (C), 2019
 * FileName: Demo
 * Author:   huangwenyuan
 * Date:     2019/03/26 下午 02:36
 * Description:
 */

package javanet.c03.practice2;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/03/26
 * @since 1.0.0
 */
public class Demo {
    byte[] inBuff = new byte[4096];
    private DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
    private DatagramPacket outPacket = null;

    public void init() throws IOException {
        DatagramSocket socket = new DatagramSocket();
        outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName("time.nist.gov"), 37);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            byte[] buff = scanner.nextLine().getBytes();
            outPacket.setData(buff);
            socket.send(outPacket);
            socket.receive(inPacket);
            System.out.println(new String(inBuff, 0, inPacket.getLength()));
        }
    }

    public static void main(String[] args) throws IOException {
        new Demo().init();
    }
}

    