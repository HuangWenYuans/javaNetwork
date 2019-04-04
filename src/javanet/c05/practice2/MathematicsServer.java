/**
 * Copyright (C), 2019
 * FileName: MathematicsServer
 * Author:   huangwenyuan
 * Date:     2019/04/04 上午 09:12
 * Description:
 */

package javanet.c05.practice2;

import sun.security.tools.keytool.Main;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/04
 * @since 1.0.0
 */
public class MathematicsServer {
    public static void main(String[] args) throws IOException {
        URL url = new URL("http://localhost:8888/practice/calculator.html?num1=1&num2=2");
        System.out.println(url.getQuery());

    }
}

    