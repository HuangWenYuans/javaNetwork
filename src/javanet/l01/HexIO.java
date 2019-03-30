/**
 * Copyright (C), 2019
 * FileName: HexIO
 * Author:   huangwenyuan
 * Date:     2019/3/11 22:30
 * Description:
 */

package javanet.l01;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/11
 * @since 1.0.0
 */
public class HexIO {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("Test.txt");
            int n = 0;
            int hasRead = 0;
            while ((hasRead = fis.read()) > 0) {
                System.out.print(" " + Integer.toHexString(hasRead));
                if (((++n) % 10) == 0) {
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    