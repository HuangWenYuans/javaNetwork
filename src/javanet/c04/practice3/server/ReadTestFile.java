/**
 * Copyright (C), 2019
 * FileName: ReadTestFile
 * Author:   huangwenyuan
 * Date:     2019/04/08 上午 10:18
 * Description:
 */

package javanet.c04.practice3.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/08
 * @since 1.0.0
 */
public class ReadTestFile {
    private BufferedReader bufReader;
    public int testTime;          //试题规定考试时间
    private String correctAnswer;

    public ReadTestFile() throws IOException {
        bufReader = new BufferedReader(new FileReader("test.txt"));
        //从文件第一行提取规定用时
        String s = bufReader.readLine();
        int i1 = s.indexOf('@');        //得到字符@的索引值
        int i2 = s.indexOf("分钟");     //得到字符串“分钟”的索引值
        s = s.substring(i1 + 1, i2);        //得到用分钟表示的考试规定用时
        testTime = Integer.parseInt(s) * 60 * 1000; //将考试规定用时转化为ms从文件第2行提取标准答案
        //
        s = bufReader.readLine().trim();
        correctAnswer = s.substring(s.indexOf("@") + 1);
    }

    public int getTestTime() {
        return testTime;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    /*读取试题中的每一题并返回，读到文件最后*/

    public String getTestQuestion() {
        String testQuestion = "";
        try {
            StringBuffer temp = new StringBuffer();
            String s = "";
            if (bufReader != null) {
                while ((s = bufReader.readLine()) != null) {
                    //读第3行的试题内容
                    if (s.startsWith("*")) {
                        break;
                    }
                    temp.append("\n" + s);
                    if (s.startsWith("试题结束")) {
                        bufReader.close();
                    }
                }
                testQuestion = temp.toString();
            }
        } catch (Exception e) {
            testQuestion = "试题结束";
        }
        return testQuestion;
    }
}


    