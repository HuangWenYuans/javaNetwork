/**
 * Copyright (C), 2019
 * FileName: CopyFile
 * Author:   huangwenyuan
 * Date:     2019/3/11 22:41
 * Description: 实现文件复制
 */

package javanet.l01;

import java.io.*;

/**
 * 功能描述: 实现文件复制
 *
 * @author huangwenyuan
 * @create 2019/3/11
 * @since 1.0.0
 */
public class CopyFile {
    public static void main(String[] args) {
        try {
            //创建字节输入流
            FileInputStream fis = new FileInputStream("input.txt");
            //创建字节输出流
            FileOutputStream fos = new FileOutputStream("output.txt");
            byte[] bbuf = new byte[1024];
            //保存实际读取的字节数
            int hasRead = 0;
            while ((hasRead = fis.read(bbuf)) > 0) {
                //每读取一次，即写入文件输出流，读了多少，就写多少
                fos.write(bbuf, 0, hasRead);
            }
            fis.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

    