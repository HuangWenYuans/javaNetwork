/**
 * Copyright (C), 2019
 * FileName: ClientFrame
 * Author:   huangwenyuan
 * Date:     2019/04/08 上午 10:13
 * Description:
 */

package javanet.c04.practice3.client;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/08
 * @since 1.0.0
 */
public class ClientFrame extends JFrame implements ActionListener {
    TestPanel myPanel;
    JPanel jPanel;
    JLabel jXh, jXm, jUserCheckOk;
    JTextField tXh, tXm;
    JButton ok;
    Socket socket;
    DataOutputStream outToServer;

    public ClientFrame(String s) {
        super(s);
        //创建连接到本机服务器、5500端口的套接字对象，并初始化面板
        try {
            socket = new Socket("127.0.0.1", 5500);
            outToServer = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "数据流建立错误");
        }
        myPanel = new TestPanel(this, socket);
        myPanel.startButton.setEnabled(false);
        add(myPanel, BorderLayout.CENTER);
        jPanel = new JPanel();
        jXh = new JLabel("学号:");
        jXm = new JLabel("姓名");
        ok = new JButton("确认");
        tXh = new JTextField(8);
        tXm = new JTextField(8);
        jPanel.add(jXh);
        jPanel.add(tXh);
        jPanel.add(jXm);
        jPanel.add(tXm);
        jPanel.add(ok);
        ok.addActionListener(this);
        add(jPanel, "North");
        setSize(500, 380);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String args[]) {
        ClientFrame frame = new ClientFrame("C/S考试系统");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            outToServer.writeUTF("考生验证@" + tXh.getText() + "@" + tXm.getText());
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "向服务器连接失败");
        }
    }
}
    