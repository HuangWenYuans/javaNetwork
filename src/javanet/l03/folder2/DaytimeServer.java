/**
 * Copyright (C), 2019
 * FileName: DaytimeServer
 * Author:   huangwenyuan
 * Date:     2019/03/26 下午 07:38
 * Description:
 */

package javanet.l03.folder2;

import java.io.DataOutputStream;
import java.io.IOException;
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
                new Thread(new DaytimeServerThread(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


class DaytimeServerThread extends Thread {
    Socket s;
    DataOutputStream out;

    public DaytimeServerThread(Socket s) throws IOException {
        this.s = s;
        out = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {
        try {
            writeTime(out);
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

    