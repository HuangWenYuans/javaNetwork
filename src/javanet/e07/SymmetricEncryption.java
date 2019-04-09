/**
 * Copyright (C), 2019
 * FileName: SymmetricEncryption
 * Author:   huangwenyuan
 * Date:     2019/04/09 下午 02:30
 * Description:
 */

package javanet.e07;

import java.util.Scanner;


/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/09
 * @since 1.0.0
 */
public class SymmetricEncryption {
    public static void main(String[] args) {
        System.out.println("请输入需要加密的字符串：");
        // 获取用户输入
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        int n = str.length();
        char[] charArray = new char[n];
        for (int i = 0; i < n; i++) {
            charArray[i] = str.charAt(i);
        }
        //加密
        encryption(charArray, 235);
        //解密
        Decrypttion(charArray, 235);
    }

    /***
     * 加密方法
     * @param charArray
     * @param n
     */
    private static void encryption(char[] charArray, int n) {
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (charArray[i] ^ n);
        }
        System.out.print("加密完成,密文为：");
        print(charArray);

    }

    /***
     * 解密方法
     * @param charArray
     * @param n
     */
    private static void Decrypttion(char[] charArray, int n) {
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (charArray[i] ^ n);
        }
        System.out.print("解密完成，明文为：");
        print(charArray);
    }

    /***
     * 打印的方法
     * @param arr
     */
    private static void print(char[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
    }
}

    