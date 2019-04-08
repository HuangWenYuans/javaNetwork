/**
 * Copyright (C), 2019
 * FileName: TestPanel
 * Author:   huangwenyuan
 * Date:     2019/04/08 上午 10:13
 * Description:
 */

package javanet.c04.practice3.client;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/08
 * @since 1.0.0
 */

public class TestPanel extends JPanel implements ActionListener, Runnable {
    //声明套接字通信用到的类对象和变量、计时器、线程对象
    //套接字对象
    Socket connectToServer;
    DataInputStream inFromSerevr;
    DataOutputStream outToServer;
    Thread thread;
    Timer testTimer;
    int testTime;
    JButton startButton;
    JLabel timeLabel;
    JTextArea questionArea;
    JRadioButton radioButton[] = new JRadioButton[5];
    /*5个单选按钮，目的是设置前4个答案选项都不选中，而第5个选项选中，而第5个按钮设置为不可见*/
    ButtonGroup buttonGroup = new ButtonGroup();
    JButton answerButton;
    JButton questionButton;
    JButton scoreButton;
    ClientFrame frame;

    public TestPanel(ClientFrame frame, Socket socket) {
        this.frame = frame;
        initPanelGUI();
        //初始化套接字和接受、发送数据的数据流
        try {
            connectToServer = socket;
            inFromSerevr = new DataInputStream(
                    connectToServer.getInputStream());
            outToServer = new DataOutputStream(
                    connectToServer.getOutputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "服务器连接错误");
            startButton.setEnabled(false);
        }
        testTimer = new Timer(1000, this);
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startButtonPerformed();
        }
        //计时器启动后，每隔1s更新一次考试剩余时间
        if (e.getSource() == testTimer) {
            testTimerPerformed();
        }
        if (e.getSource() == questionButton) {
            radioButton[radioButton.length - 1].setSelected(true);
            questionButtonPerformed();
        }
        if (e.getSource() == answerButton) {
            answerButtonPerformed();
        }
        if (e.getSource() == scoreButton) {
            scoreButtonPerformed();
        }
    }

    /*线性启动后执行run方法，接收服务器发送回来的信息并作出相应的处理*/
    @Override
    public void run() {
        String inStr = "";
        while (true) {
            try {
                inStr = inFromSerevr.readUTF();   //从服务器套接字读取数据
                if (inStr.equals("验证失败")) {
                    JOptionPane.showMessageDialog(null, "验证失败，请检查考号与姓名！");
                }
                if (inStr.equals("验证通过")) {
                    frame.remove(frame.jPanel);
                    startButton.setEnabled(true);
                    frame.validate();
                }
                if (inStr.startsWith("考试时间")) {
                    inStr = inStr.substring(inStr.indexOf("@") + 1);//提取考试时间
                    testTime = Integer.parseInt(inStr);           //得到考试用时
                    timeLabel.setText(convertTime(testTime));  //显示考试用时
                    testTimer.start();
                }
                if (inStr.startsWith("下一题")) {
                    inStr = inStr.substring(inStr.indexOf("@") + 1);
                    //提取考试内容
                    questionArea.setText(inStr);
                    if (inStr.startsWith("试题结束")) {
                        testTimer.stop();                           //停止计时
                        questionButton.setEnabled(false);
                        answerButton.setEnabled(false);
                        scoreButton.setEnabled(true);
                    }
                }
                if (inStr.startsWith("成绩")) {
                    JOptionPane.showMessageDialog(null, inStr, "成绩显示",
                            JOptionPane.INFORMATION_MESSAGE);
                    socketClosing();
                }
            } catch (Exception e) {
                socketClosing();
                questionArea.setText("服务器连接终止");
                break;
            }
        }
    }

    /*初始化面板中的图形组建*/
    private void initPanelGUI() {
        setLayout(new BorderLayout());
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(2, 1));
        startButton = new JButton("开始考试");
        startButton.addActionListener(this);
        timeLabel = new JLabel("考试剩于时间");
        northPanel.add(startButton);
        northPanel.add(timeLabel);
        add(northPanel, BorderLayout.NORTH);
        questionArea = new JTextArea(30, 10);
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setFont(new Font("幼圆", Font.PLAIN, 16));
        int vScroll = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        int hScroll = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
        add(new JScrollPane(questionArea, vScroll, hScroll), BorderLayout.CENTER);
        JPanel southPanel = new JPanel();
        JPanel radioPanel = new JPanel();            //放5个单选按钮的面板
        String s[] = {"A", "B", "C", "D"};
        for (int i = 0; i < radioButton.length; i++) {
            radioButton[i] = new JRadioButton(s[i], false);
            buttonGroup.add(radioButton[i]);
            radioPanel.add(radioButton[i]);
        }
        radioButton[radioButton.length - 1].setVisible(false);        //第5个单选按钮不可见
        answerButton = new JButton("提交答案");
        answerButton.setEnabled(false);
        answerButton.addActionListener(this);
        questionButton = new JButton("下一题");
        questionButton.setEnabled(false);
        questionButton.addActionListener(this);
        scoreButton = new JButton("成绩");
        scoreButton.setEnabled(false);
        scoreButton.addActionListener(this);
        southPanel.add(radioPanel);
        southPanel.add(answerButton);
        southPanel.add(questionButton);
        southPanel.add(scoreButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    /*把毫秒表示的时间转化为时、分、秒的字符串表示*/
    private String convertTime(int time) {
        int leftTime = time / 1000;
        int leftHour = leftTime / 3600;
        int leftMinute = (leftTime - leftHour * 3600) / 60;
        int leftSecond = leftTime % 60;
        String timeStr = "剩余时间：" + leftHour + "小时" + leftMinute + "分" + leftSecond + "秒";
        return timeStr;
    }

    /*单击"开始考试"按钮后要执行的任务*/
    private void startButtonPerformed() {
        startButton.setEnabled(false);
        questionButton.setEnabled(true);
        try {
            outToServer.writeUTF("开始考试");
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "向服务器写\"开始考试\"失败");
        }
    }

    /*计时器倒计时*/
    private void testTimerPerformed() {
        testTime -= 1000;
        timeLabel.setText(convertTime(testTime));
        if (testTime <= 0) {
            testTimer.stop();
            questionButton.setEnabled(false);
            answerButton.setEnabled(false);
        }
    }

    /*单击"下一题"按钮后要执行的任务*/
    private void questionButtonPerformed() {
        questionButton.setEnabled(false);
        answerButton.setEnabled(true);
        try {
            outToServer.writeUTF("下一题");
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "向服务器写\"开始考试\"失败");
        }
    }

    /*单击"提交答案"按钮后要执行的任务*/
    private void answerButtonPerformed() {
        String answer = "";
        questionButton.setEnabled(true);
        answerButton.setEnabled(false);
        for (int i = 0; i < radioButton.length; i++) {
            if (radioButton[i].isSelected()) {
                answer = radioButton[i].getLabel();
                break;
            }
        }
        try {
            outToServer.writeUTF("提交答案@" + answer);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "向服务器\"提交答案\"失败");
        }
    }

    /*单击"成绩"按钮后要执行的任务*/
    private void scoreButtonPerformed() {
        try {
            scoreButton.setEnabled(false);
            outToServer.writeUTF("成绩");
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "要求服务器发送\"成绩\"失败");
        }
    }

    /*关闭所有链接*/
    private void socketClosing() {
        try {
            inFromSerevr.close();
            outToServer.close();
            connectToServer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "关闭socket异常！");
        }
    }
}

    