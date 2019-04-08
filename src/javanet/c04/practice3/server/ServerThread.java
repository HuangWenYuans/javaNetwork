/**
 * Copyright (C), 2019
 * FileName: ServerThread
 * Author:   huangwenyuan
 * Date:     2019/04/08 上午 10:19
 * Description:
 */

package javanet.c04.practice3.server;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/08
 * @since 1.0.0
 */

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ServerThread extends Thread {
    Socket connectToClient;
    DataOutputStream outToClient;
    DataInputStream inFromClient;
    DataOutputStream outFromClient;
    DataInputStream inToClient;
    ReadTestFile readTestFile;
    String selectedAnswer = "";
    String kaohao = "", name = "";

    public ServerThread(Socket socket) {
        connectToClient = socket;
        try {
            readTestFile = new ReadTestFile();
            inFromClient = new DataInputStream(connectToClient.getInputStream());
            outFromClient = new DataOutputStream(connectToClient.getOutputStream());
        } catch (IOException e) {
        }
        start();
    }

    private void socketClosing() {
        try {
            inFromClient.close();
            outFromClient.close();
            connectToClient.close();
        } catch (Exception e) {
            System.out.println("关闭socket异常！");
        }
    }

    @Override
    public void run() {
        String inStr = "";
        String outStr = "";
        while (true) {
            try {
                inStr = inFromClient.readUTF();
                if (inStr.startsWith("考生验证")) {
                    int start, end;
                    start = inStr.indexOf("@") + 1;
                    end = inStr.indexOf("@", start);
                    kaohao = inStr.substring(start, end);
                    name = inStr.substring(end + 1);

                    try {
                        Connection con = DBConnection.getConnection();
                        String cx = "select count(*) from student where 考号 = '" + kaohao + "'and 姓名 ='" + name + "'";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(cx);
                        rs.next();
                        if (rs.getInt(1) == 1) {
                            outToClient.writeUTF("验证通过");
                        } else {
                            outToClient.writeUTF("验证失败");
                        }
                    } catch (Exception e) {
                        System.out.println("数据库连接出错!");
                    }
                }
                if (inStr.startsWith("开始考试")) {
                    int time = readTestFile.getTestTime();
                    outToClient.writeUTF("考试时间@" + time);
                    System.out.println(inStr);
                }
                if (inStr.startsWith("下一题")) {
                    outStr = readTestFile.getTestQuestion();
                    outToClient.writeUTF("下一题@" + outStr);
                } else if (inStr.startsWith("提交答案")) {
                    inStr = inStr.substring(inStr.indexOf("@") + 1);
                    selectedAnswer += inStr;
                } else if (inStr.startsWith("成绩")) {
                    int score = getTestScore();
                    if (score >= 60) {
                        outStr = "成绩:" + score + "\n祝贺你通过考试！";

                    } else {
                        outStr = "成绩:" + score + "\n你没有通过考试！";
                    }
                    outToClient.writeUTF(outStr);
                    try {
                        Connection con = DBConnection.getConnection();
                        String cx = "update student set 成绩 =" + score + "where 考号='" + kaohao + "'and 姓名 ='" + name + "'";
                        Statement st = con.createStatement();
                        st.executeUpdate(cx);
                    } catch (Exception e) {
                        System.out.println("数据库连接出错！");
                    }
                }
            } catch (IOException e) {
                socketClosing();
                System.out.println("与客户的连接中断");
                break;
            }
        }
    }

    private int getTestScore() {
        String correctAnswer = readTestFile.getCorrectAnswer();
        int n = 0, testScore = 0;
        int length1 = correctAnswer.length();
        int length2 = selectedAnswer.length();
        int min = Math.min(length1, length2);
        for (int i = 0; i < min; i++) {
            if (selectedAnswer.charAt(i) == correctAnswer.charAt(i)) {
                n++;
            }
        }
        testScore = (int) (100.0 * n / length1);
        return testScore;
    }

}
    