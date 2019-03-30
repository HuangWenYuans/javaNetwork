/**
 * Copyright (C), 2019
 * FileName: DaytimeServer
 * Author:   huangwenyuan
 * Date:     2019/03/26 下午 02:05
 * Description:
 */

package javanet.l03.folder1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/03/26
 * @since 1.0.0
 */
public class DaytimeServer {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(2007);
            while (true) {
                Socket s = ss.accept();
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                writeTime(out);
                out.close();
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeTime(DataOutputStream out) throws IOException {
        Calendar current = Calendar.getInstance();
        out.writeInt(current.get(Calendar.YEAR));
        out.writeByte(current.get(Calendar.MONTH) + 1);
        out.writeByte(current.get(Calendar.DAY_OF_MONTH));
        out.writeByte(current.get(Calendar.HOUR_OF_DAY));
        out.writeByte(current.get(Calendar.MINUTE));
        out.writeByte(current.get(Calendar.SECOND));
    }
}


class DaytimeClient {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 2007);
        //获取socket对应的数据输入流
        DataInputStream dis = new DataInputStream(s.getInputStream());
        //年
        int year = dis.readInt();
        //月
        byte month = dis.readByte();
        //日
        byte day = dis.readByte();
        //时
        byte hour = dis.readByte();
        //分
        byte minute = dis.readByte();
        //秒
        byte second = dis.readByte();
        System.out.println(year + "/" + month + "/" + day + " " + hour + ":" + minute + ":" + second);
    }
}

    