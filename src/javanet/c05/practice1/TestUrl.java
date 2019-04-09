/**
 * Copyright (C), 2019
 * FileName: TestUrl
 * Author:   huangwenyuan
 * Date:     2019/04/08 下午 12:01
 * Description:
 */

package javanet.c05.practice1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * 功能描述: 测试关于URL的一系列方法
 *
 * @author huangwenyuan
 * @create 2019/04/08
 * @since 1.0.0
 */
public class TestUrl {
    public static void main(String[] args) {
        System.out.println("请输入一个URL:");
        //获取用户输入
        Scanner scanner = new Scanner(System.in);
        String urlStr = scanner.nextLine();
        try {
            //根据给定的URL创建一个URL对象
            URL url = new URL(urlStr);
            //协议
            String protocol = url.getProtocol();
            System.out.println("协议：" + protocol);
            //主机号
            String host = url.getHost();
            System.out.println("主机号：" + host);
            //端口号
            int port = url.getPort();
            System.out.println("端口号：" + port);
            //路径
            String path = url.getPath();
            System.out.println("路径：" + path);
            //查询部分
            String query = url.getQuery();
            System.out.println("查询部分：" + query);
            // url的锚点
            String ref = url.getRef();
            System.out.println("定位位置：" + ref);
            //url的用户信息
            String userInfo = url.getUserInfo();
            System.out.println("用户信息：" + userInfo);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

    