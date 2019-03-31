/**
 * Copyright (C), 2019
 * FileName: homework1_02
 * Author:   huangwenyuan
 * Date:     2019/3/6 23:58
 * Description:
 */

package javanet.e01;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/6
 * @since 1.0.0
 */
public class e01_02 {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        //创建字符输出流
        FileWriter fw = new FileWriter("random.txt");
        for (int i = 0; i < 10; i++) {
            //创建1-100范围内的随机数
            int num = 1 + random.nextInt(100);
            try {
                fw.write("Number " + i + ": " + num + "\n");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fw.flush();
        fw.close();
    }
}

    