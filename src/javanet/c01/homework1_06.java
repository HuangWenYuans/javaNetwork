/**
 * Copyright (C), 2019
 * FileName: homework1_06
 * Author:   huangwenyuan
 * Date:     2019/3/7 18:06
 * Description:
 */

package javanet.c01;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/7
 * @since 1.0.0
 */
public class homework1_06 {
    public static void main(String[] args) throws IOException {
        //创建随机存储文件
        RandomAccessFile raf = new RandomAccessFile(new File("score.data"), "rw");
        //增加学生成绩
        insert(raf, 29, "学号：16201213；姓名：黄文源；成绩：90\n");
        ////删除学生成绩
        //delete(raf, "16201213");
        ////修改学生成绩
        //update(raf, "16201213", "30");
        //查询学生成绩
        //System.out.println(select(raf, "16201213"));
        //清空文件中所有内容
        //clear(raf);
    }

    /***
     * 学生成绩增加的方法
     * @param raf
     * @param pos
     * @param insertContent
     * @throws IOException
     */
    private static void insert(RandomAccessFile raf, long pos, String insertContent) throws IOException {
        //创建临时文件
        File tmp = File.createTempFile("tmp", null);
        tmp.deleteOnExit();
        //以读写的方式创建RandomAccessFile对象
        FileOutputStream tmpOut = new FileOutputStream(tmp);
        FileInputStream tmpIn = new FileInputStream(tmp);
        raf.seek(pos);

        //    将插入点后的内容读入临时文件中保存
        byte[] bbuff = new byte[64];
        //    用户保存实际读取的数据
        int hasRead = 0;
        //使用循环的方式读取插入点后的数据
        while ((hasRead = raf.read(bbuff)) > 0) {
            //将读取的数据写入临时文件
            tmpOut.write(bbuff, 0, hasRead);
        }

        //  往文件中插入内容
        //将指针重新定位到pos位置
        raf.seek(pos);
        raf.write(insertContent.getBytes());
        //追加临时文件中的内容
        while ((hasRead = tmpIn.read(bbuff)) > 0) {
            raf.write(bbuff, 0, hasRead);
        }
    }


    /***
     * 学生成绩删除的方法
     * @param raf
     * @param studentNo
     * @throws IOException
     */
    private static void delete(RandomAccessFile raf, String studentNo) throws IOException {
        String line = null;
        while ((line = raf.readLine()) != null) {
            //如果当前行包含了需要删除学号
            if (line.contains(studentNo)) {
                //保存指针所在位置
                long pointer = raf.getFilePointer();
                //回到行首
                raf.seek(0);
                byte[] bbuf = new byte[line.length()];
                //用和原来行一样长的空白字符串代替原来行
                String str = new String(bbuf);
                raf.writeBytes(str);
            }
        }

    }

    /***
     * 学生成绩修改的方法
     * @param raf
     * @param studentNo
     * @param newGrade
     */
    private static void update(RandomAccessFile raf, String studentNo, String newGrade) throws IOException {
        String line = null;
        while ((line = raf.readLine()) != null) {
            //如果当前行包含了需要修改的学号
            if (line.contains(studentNo)) {
                //保存指针所在位置
                long pointer = raf.getFilePointer();
                raf.seek(pointer - 3);
                //更新成绩
                String str = new String(newGrade);
                raf.writeBytes(str + "\n");
            }
        }
    }

    /***
     * 学生成绩查询
     * @param raf
     * @param studentNo
     * @return
     */
    public static List<String> select(RandomAccessFile raf, String studentNo) throws IOException {
        String line = null;
        List<String> list = new ArrayList<>();
        while ((line = raf.readLine()) != null) {
            //如果当前行包含了需要查询的学号
            if (line.contains(studentNo)) {
                //截取成绩
                String str = line.substring(line.length() - 3, line.length());
                list.add("学号" + studentNo + "的成绩:" + str);
            }
        }
        return list;
    }

    /***
     * 清空文件中所有内容
     * @param raf
     * @throws IOException
     */
    private static void clear(RandomAccessFile raf) throws IOException {
        raf.setLength(0);
        raf.close();
    }

}

    