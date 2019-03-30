/**
 * Copyright (C), 2019
 * FileName: Demo2
 * Author:
 * Date:     2019/3/21  08:23
 * Description:
 */

package javanet.c03.practice1;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * 功能描述:
 *
 * @author
 * @create 2019/3/21
 * @since 1.0.0
 */
public class Demo2 {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        //获取本地主机实例
        InetAddress local = InetAddress.getLocalHost();
        System.out.println("本机的ip地址为:" + local.getHostAddress());
        //调用获取本机MAC地址的方法
        getLocalMac(local);
    }

    /***
     * 通过IP地址获取对应的MAC地址
     * @param local
     * @throws SocketException
     */
    private static void getLocalMac(InetAddress local) throws SocketException {
        //根据ip地址获取对应的mac地址
        byte[] mac = NetworkInterface.getByInetAddress(local).getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            //    将字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        System.out.println("本机的MAC地址为" + sb.toString().toUpperCase());
    }
}

    