/**
 * Copyright (C), 2019
 * FileName: BusLineQueryClient
 * Author:   huangwenyuan
 * Date:     2019/04/09 下午 07:30
 * Description:
 */

package javanet.l05;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 功能描述:
 * 公交车查询客户端程序
 *
 * @author huangwenyuan
 * @create 2019/04/09
 * @since 1.0.0
 */
public class BusLineQueryClient extends Application {
    private ArrayList<String> list = null;
    private ArrayList<Integer> stationList = null;

    public static void main(String[] args) {
        //启动面板
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        //绘制面板
        Group group = new Group();
        Scene scene = new Scene(group, 800, 600);
        stage.setTitle("公交车查询");

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
        //绘制搜索框
        drawSearchBox(group, input);
        //如果按下回车键,则与服务器端开始通信
        input.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                //清空之前的界面
                group.getChildren().clear();
                //绘制搜索框
                drawSearchBox(group, input);

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
                if (ps != null) {
                    ps.println(input.getText());
                }
                //启动客户端子线程
                try {
                    //与服务器通信
                    new Thread(new ClientThread(socket)).start();
                    //与公交车模拟程序通信
                    new Thread(new UDPThread(input.getText())).start();
                    //保证数据传输完毕
                    Thread.sleep(500);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

                if (list.size() == 0) {
                    // 如果没有所查询的路线,给出提示信息
                    Text promptText = new Text(250, 300, "对不起，没有对应的路线信息!");
                    promptText.setStyle("-fx-font-weight: bold");
                    promptText.setFont(Font.font(18));
                    promptText.setFill(Color.RED);
                    group.getChildren().add(promptText);
                } else {
                    //有所查询的路线信息

                    //根据站点计算间距
                    int x = (int) Math.ceil(750 / list.size());
                    // 绘制车站线
                    Rectangle r1 = new Rectangle(30, 120, list.size() * x + 10, 30);
                    r1.setFill(Color.AQUAMARINE);
                    Rectangle r2 = new Rectangle(30, 130, list.size() * x + 10, 10);
                    r2.setFill(Color.YELLOW);
                    group.getChildren().add(r1);
                    group.getChildren().add(r2);

                    //输入公交车号且按下回车键后打印信息
                    for (int i = 0; i < list.size(); i++) {
                        //绘制车站编号以及车站名
                        Text numStr = new Text();
                        Text busText = new Text();
                        Circle circle = new Circle();
                        if (i % 2 == 0) {
                            //对应车站编号
                            numStr = new Text(50 + i * x, 100, list.get(i));
                            circle = new Circle(50 + i * x, 135, 10);
                            circle.setFill(Color.YELLOW);

                        } else {
                            //对应车站名
                            busText = new Text(45 + (i - 1) * x, 200, list.get(i));
                        }
                        //设置文本样式
                        numStr.setStyle("-fx-font-weight: bold");
                        busText.setFont(Font.font(12));
                        busText.setStyle("-fx-font-weight: bold");
                        busText.setFill(Color.BLUE);
                        //使车站名竖直摆放
                        busText.setWrappingWidth(10);
                        busText.setTextAlignment(TextAlignment.CENTER);

                        group.getChildren().add(busText);
                        group.getChildren().add(numStr);
                        group.getChildren().add(circle);
                    }
                    //如果所查线路有站点信息
                    if (stationList.size() != 0) {
                        for (int i = 0; i < 3; i++) {
                            //绘制矩形
                            Rectangle rect = new Rectangle((stationList.get(i) - 1) * 2 * x + 45, 50, 20, 20);
                            final int[] pos = {(stationList.get(i) - 1) * 2 * x + 45};
                            //设置矩形为红色
                            rect.setFill((Color.rgb(255, 0, 0)));

                            Random random = new Random();
                            int n = random.nextInt(6) + 10;

                            Timeline tl = new Timeline();
                            tl.getKeyFrames().add(new KeyFrame(Duration.millis(n * 100),
                                    actionEvent -> {
                                        //公交车未到达终点,则移动
                                        if (rect.getX() != (list.size() / 2 - 1) * 2 * x + 45) {
                                            pos[0] += 2 * x;
                                            rect.setX(pos[0]);
                                        }
                                    }));
                            tl.setCycleCount(Animation.INDEFINITE);
                            tl.play();
                            group.getChildren().add(rect);
                        }
                    }
                }
            }
        });

        stage.setScene(scene);
        stage.show();

    }

    /***
     * TCP客户端子线程
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
            list = new ArrayList<>();
            String line = null;
            try {
                list.clear();
                while ((line = br.readLine()) != null) {
                    //将获取到的站点信息存入list中
                    list.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * UDP子线程
     */
    class UDPThread implements Runnable {
        private String input;

        public UDPThread(String input) {
            this.input = input;
        }

        public String getInput() {
            return input;
        }

        public void setInput(String input) {
            this.input = input;
        }

        @Override
        public void run() {
            stationList = new ArrayList<>();
            try {
                byte[] inBuff = new byte[1024];
                DatagramSocket socket = new DatagramSocket();
                DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
                DatagramPacket outPacket = new DatagramPacket(new byte[0], 0, InetAddress.getByName("localhost"), 20001);

                byte[] buff = input.getBytes();
                //设置数据
                outPacket.setData(buff);
                //发送数据
                socket.send(outPacket);

                List<Integer> list = new ArrayList<>();
                stationList.clear();
                //读取socket中的数据
                for (int i = 0; i < 3; i++) {
                    socket.receive(inPacket);
                    String numStr = new String(inBuff, 0, inPacket.getLength());
                    stationList.add(Integer.parseInt(numStr));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * 绘制搜索框的方法
     * @param group
     * @param input
     */
    private void drawSearchBox(Group group, TextField input) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        input.setPrefColumnCount(5);
        //添加提示性文字
        grid.add(new Label("请输入需要查询的线路(只能为数字):"), 30, 0);
        //添加文本输入框
        grid.add(input, 31, 0);
        //让输入框始终处于面板的上部中央
        grid.setAlignment(Pos.TOP_CENTER);
        group.getChildren().add(grid);
    }
}

