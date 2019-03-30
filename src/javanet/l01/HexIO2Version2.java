/**
 * Copyright (C), 2019
 * FileName: HexIO2Version2
 * Author:   huangwenyuan
 * Date:     2019/3/12 16:34
 * Description:
 */

package javanet.l01;

import java.io.*;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/12
 * @since 1.0.0
 */
public class HexIO2Version2 {
    public static void main(String[] args) {
        try {
            //将字节流转换成字符流
            InputStreamReader reader = new InputStreamReader(new FileInputStream("Test.txt"));
            //将普通的reader包装成BufferedReader
            BufferedReader br = new BufferedReader(reader);
            FileWriter fw = new FileWriter("TestOut1.txt");
            int n = 0;
            int hasRead = 0;
            while ((hasRead = br.read()) > 0) {
                fw.write(" " + Integer.toHexString(hasRead));
                if (((++n) % 10) == 0) {
                    fw.write("\n");
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    