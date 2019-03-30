/**
 * Copyright (C), 2019
 * FileName: Demo1
 * Author:
 * Date:     2019/3/21 08:22
 * Description:
 */

package javanet.c03.practice1;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 功能描述:
 *
 * @author
 * @create 2019/3/21
 * @since 1.0.0
 */
public class Demo1 {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
        System.out.println("百度的ip地址为：" + inetAddress.getHostAddress());
    }
}

    