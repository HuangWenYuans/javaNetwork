/**
 * Copyright (C), 2019
 * FileName: BusLineQueryClient
 * Author:   huangwenyuan
 * Date:     2019/04/09 下午 07:30
 * Description:
 */

package javanet.l05;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.IndexRange;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;


/**
 * 功能描述:
 * 公交车查询客户端程序
 *
 * @author huangwenyuan
 * @create 2019/04/09
 * @since 1.0.0
 */
public class BusLineQueryClient extends Application {
    public static void main(String[] args) throws IOException {
        //启动面板
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //绘制面板
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("公交车查询");
        stage.setResizable(true);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);
        scene.setRoot(grid);

        //创建只允许输入数字的文本输入框
        TextField input = new TextField() {
            @Override
            public void replaceText(int start, int end, String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override
            public void replaceSelection(String text) {
                if (!text.matches("[a-z]")) {
                    super.replaceSelection(text);
                }
            }
        };

        input.setPrefColumnCount(10);
        //添加提示性文字
        grid.add(new Label("请输入需要查询的线路(只能为数字):"), 0, 0);
        //添加文本输入框
        grid.add(input, 1, 0);
        //让输入框始终处于面板的上部中央
        grid.setAlignment(Pos.TOP_CENTER);

        //如果按下回车键,则与服务器端开始通信
        input.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Socket socket = null;
                try {
                    socket = new Socket("localhost", 25000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //获取socket对应的输出流
                PrintStream ps = null;
                try {
                    ps = new PrintStream(socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //将需要查询的公交车号发送给服务器
                ps.println(input.getText());
                //启动客户端子线程
                try {
                    new Thread(new ClientThread(socket)).start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        stage.show();

    }
}

/***
 * 客户端子线程
 */
class ClientThread implements Runnable {
    private Socket s;
    BufferedReader br;

    public ClientThread(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    @Override
    public void run() {
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                //读取服务器发送的站点信息打印出来
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

