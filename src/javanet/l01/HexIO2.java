/**
 * Copyright (C), 2019
 * FileName: HexIO2
 * Author:   huangwenyuan
 * Date:     2019/3/11 22:37
 * Description:
 */

package javanet.l01;

import java.io.*;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/11
 * @since 1.0.0
 */
public class HexIO2 {
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("Test.txt");
            FileWriter fw = new FileWriter("TestOut.txt");
            int b, n = 0;
            while ((b = fr.read()) != -1) {
                fw.write(" " + Integer.toHexString(b));
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

    