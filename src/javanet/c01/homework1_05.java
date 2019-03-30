/**
 * Copyright (C), 2019
 * FileName: homework1_05
 * Author:   huangwenyuan
 * Date:     2019/3/9 21:57
 * Description:
 */

package javanet.c01;

import java.io.*;
import java.util.List;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/9
 * @since 1.0.0
 */
public class homework1_05 {
    public static void main(String[] args) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("shapes.data"));
            //从输入流中读取对象
            List<Shape> list = (List<Shape>) ois.readObject();
            //    调用打印形状信息的方法
            for (int i = 0; i < list.size(); i++) {
                list.get(i).printInfo();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

    