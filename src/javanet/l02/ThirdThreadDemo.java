/**
 * Copyright (C), 2019
 * FileName: ThirdThreadDemo
 * Author:   huangwenyuan
 * Date:     2019/3/19 0019 下午 08:33
 * Description:
 */

package javanet.l02;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/19 0019
 * @since 1.0.0
 */
public class ThirdThreadDemo implements Runnable {
    public static void main(String[] args) {
        ThirdThreadDemo thread = new ThirdThreadDemo();
        //创建三个子线程对文件进行复制
        new Thread(thread, "folder1").start();
        new Thread(thread, "folder2").start();
        new Thread(thread, "folder3").start();
    }

    @Override
    public void run() {
        try {
            //创建字节输入流
            FileInputStream fis = new FileInputStream("D:\\IDEA\\IDEAWorkSpace\\TwoHundredCases\\src\\javaNetWorkLab\\l02\\input.txt");
            //创建字节输出流
            FileOutputStream fos = new FileOutputStream("D:\\IDEA\\IDEAWorkSpace\\TwoHundredCases\\src\\javaNetWorkLab\\l02\\" + Thread.currentThread().getName() + "/output.txt");
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

    