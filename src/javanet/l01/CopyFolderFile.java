/**
 * Copyright (C), 2019
 * FileName: CopyFolderFile
 * Author:   huangwenyuan
 * Date:     2019/3/11 22:50
 * Description: 拷贝文件夹下的文件
 */

package javanet.l01;

import java.io.*;

/**
 * 功能描述: 拷贝文件夹下的文件
 *
 * @author huangwenyuan
 * @create 2019/3/11
 * @since 1.0.0
 */
public class CopyFolderFile {
    public static void main(String[] args) {
        //a文件夹的路径
        String PathA = "D:\\IDEA\\javaNetwork\\src\\javanet\\l01\\a";
        //b文件夹的路径
        String PathB = "D:\\IDEA\\javaNetwork\\src\\javanet\\l01\\b";
        File fileA = new File(PathA);
        File fileB = new File(PathB);
        //返回a文件夹下的所有文件名
        String[] fileListA = fileA.list();
        //返回b文件夹下的所有文件名
        String[] fileListB = fileB.list();

        if (fileB.list().length == 0) {
            //如果b文件夹为空的，则将a文件夹中所有文件复制到b中
            for (String fileNameA : fileListA) {
                copyFile(PathA + fileNameA, PathB + fileNameA);
            }
        } else {
            for (String fileNameA : fileListA) {
                for (String fileNameB : fileListB) {
                    //判断b文件夹是否已存在a文件夹的文件
                    if (fileNameB == null || "".equals(fileNameB) || !fileNameB.equals(fileNameA)) {
                        //b文件夹不存在a文件夹中的文件，进行拷贝
                        copyFile(PathA + fileNameA, PathB + fileNameA);
                    }
                }
            }
        }
    }

    /***
     * 文件拷贝的方法
     * @param inputPath
     * @param outputPath
     */
    private static void copyFile(String inputPath, String outputPath) {
        try {
            FileInputStream fis = new FileInputStream(inputPath);
            FileOutputStream fos = new FileOutputStream(outputPath);
            byte[] bbuf = new byte[1024];
            int hasRead = 0;

            while ((hasRead = fis.read(bbuf)) > 0) {
                //每读取一次，即写入文件输出流，读了多少，就写多少
                fos.write(bbuf, 0, hasRead);
            }
            fis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

    