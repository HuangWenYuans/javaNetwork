/**
 * Copyright (C), 2019
 * FileName: homework1_03
 * Author:   huangwenyuan
 * Date:     2019/3/9 21:07
 * Description:
 */

package javanet.c01;

import java.io.*;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/9
 * @since 1.0.0
 */
public class homework1_03 {
    public static void main(String[] args) {
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream("random.txt"));
            BufferedReader br = new BufferedReader(reader);
            FileWriter fw = new FileWriter("Test.txt");

            String line = null;
            while ((line = br.readLine()) != null) {
                String str = line.substring(((line.trim()).indexOf(":")) + 1, line.length());
                str = str.trim();
                if (Integer.parseInt(str) > 50) {
                    //如果读取到的为大于50的数字,追加到test.txt中
                    fw.write(line + "\n");
                }
            }
            fw.flush();
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    