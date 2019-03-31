/**
 * Copyright (C), 2019
 * FileName: e01_01
 * Author:   huangwenyuan
 * Date:     2019/3/5 22:17
 * Description:
 */

package javanet.e01;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/5
 * @since 1.0.0
 */
public class e01_01 {
    public static void main(String[] args) {
        File file = new File("Test.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                List<Object> list = new ArrayList<>();
                list.add(line);
                for (Object obj : list) {
                    if (((String) obj).contains("test")) {
                        System.out.println(obj);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    