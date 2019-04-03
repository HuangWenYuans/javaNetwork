/**
 * Copyright (C), 2019
 * FileName: UDPNTPClient
 * Author:   huangwenyuan
 * Date:     2019/04/03 下午 10:23
 * Description:
 */

package javanet.c04.practice1;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 * 通过使用Apache的jar包实现通过UDP协议与ntp服务进行通信
 *
 * @author huangwenyuan
 * @create 2019/04/03
 * @since 1.0.0
 */
public class UDPNTPClient {
    public static void main(String[] args) {
        try {
            NTPUDPClient client = new NTPUDPClient();
            InetAddress timeServer = InetAddress.getByName("ntp1.aliyun.com");
            //从指定服务器中获取时间信息
            TimeInfo timeInfo = client.getTime(timeServer);
            //从时间信息中获取时间戳
            TimeStamp timeStamp = timeInfo.getMessage().getTransmitTimeStamp();
            //将时间戳对象转换成时间对象
            Date date = timeStamp.getDate();
            //将日期格式化
            String formatDate = new SimpleDateFormat("yyyy年M月dd日 HH时mm分ss秒").format(date);
            System.out.println(formatDate);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    