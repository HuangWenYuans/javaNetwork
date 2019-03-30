/**
 * Copyright (C), 2019
 * FileName: Demo4
 * Author:
 * Date:     2019/3/21  09:11
 * Description:
 */

package javanet.c03.practice1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述:
 *
 * @author
 * @create 2019/3/21
 * @since 1.0.0
 */
public class Demo4 {
    public static void main(String[] args) throws IOException, ParseException {
        Socket socket = new Socket("localhost", 13);
        //创建缓冲字符流
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line;
        while ((line = br.readLine()) != null) {
            //将获取到的时间信息进行格式化
            Date date = new SimpleDateFormat("HH:mm:ss yyyy/M/dd").parse(line);
            String formatDate = new SimpleDateFormat("yyyy年M月dd日 HH时mm分").format(date);
            System.out.println(formatDate);
        }
    }
}

    