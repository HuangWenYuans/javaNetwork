package javanet.l03.folder3;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;


/***
 * 时钟服务器类
 */
class DaytimeServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(2007);
            while (true) {
                Socket s = ss.accept();
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                writeTime(out);
                out.close();
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeTime(DataOutputStream out) throws IOException {
        Calendar current = Calendar.getInstance();
        out.writeInt(current.get(Calendar.YEAR));
        out.writeByte(current.get(Calendar.MONTH) + 1);
        out.writeByte(current.get(Calendar.DAY_OF_MONTH));
        out.writeByte(current.get(Calendar.HOUR_OF_DAY));
        out.writeByte(current.get(Calendar.MINUTE));
        out.writeByte(current.get(Calendar.SECOND));
    }
}

/***
 * 时钟客户端类
 */
class DaytimeClient extends JFrame {
    /***
     * 画时钟的面板
     */
    private PaintPanel clock = new PaintPanel();
    /***
     * 定时器
     */
    private java.util.Timer timer = new java.util.Timer();
    /***
     * 显示时钟的label
     */
    JLabel messageLabel = new JLabel("", SwingConstants.CENTER);

    public DaytimeClient() {
        setTitle("时钟");
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);

        add(clock);
        messageLabel.setForeground(Color.RED);
        messageLabel.setFont(new Font("Courier", Font.BOLD, 18));
        add(messageLabel, BorderLayout.SOUTH);

        // 设置每过一秒则从服务器获取时间
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DaytimeClient.getTime();// 从服务器上获取当前时间
                messageLabel.setText(DaytimeClient.getTime().get(3) + "/" + DaytimeClient.getTime().get(4) + "/" + DaytimeClient.getTime().get(5) + "   " + DaytimeClient.getTime().get(0)
                        + ":" + DaytimeClient.getTime().get(1) + ":" + DaytimeClient.getTime().get(2) + '\n');

                repaint();
            }
        }, 0, 1000);
    }

    /***
     * 从服务器获取时间的方法
     */
    static List<Object> getTime() {
        List<Object> list = null;
        int year = 0;
        byte month = 0;
        byte day = 0;
        byte hour = 0;
        byte minute = 0;
        byte second = 0;
        try {
            Socket s = new Socket("localhost", 2007);
            //获取socket对应的数据输入流
            DataInputStream dis = new DataInputStream(s.getInputStream());
            list = new ArrayList<>();
            //年
            year = dis.readInt();
            //月
            month = dis.readByte();
            //日
            day = dis.readByte();
            //时
            hour = dis.readByte();
            //分
            minute = dis.readByte();
            //秒
            second = dis.readByte();
        } catch (IOException e) {
            e.printStackTrace();
        }
        list.add(hour);
        list.add(minute);
        list.add(second);
        list.add(year);
        list.add(month);
        list.add(day);
        return list;
    }

    public static void main(String[] args) throws IOException {
        //创建时钟客户端对象
        new DaytimeClient();
    }
}

/***
 * 时钟面板类
 */
class PaintPanel extends JPanel {
    private java.util.Timer timer = new java.util.Timer();
    private byte hour;
    private byte minute;
    private byte second;

    public PaintPanel() {
        //通过计时器从服务器实时获取时间用以修改时钟面板
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                hour = (byte) DaytimeClient.getTime().get(0);
                minute = (byte) DaytimeClient.getTime().get(1);
                second = (byte) DaytimeClient.getTime().get(2);
            }
        }, 0, 1000);
    }

    public byte getHour() {
        return hour;
    }

    public void setHour(byte hour) {
        this.hour = hour;
    }

    public byte getMinute() {
        return minute;
    }

    public void setMinute(byte minute) {
        this.minute = minute;
    }

    public byte getSecond() {
        return second;
    }

    public void setSecond(byte second) {
        this.second = second;
    }

    /***
     * 绘制组件的方法
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        int xCenter = getWidth() / 2;
        int yCenter = getHeight() / 2;

        // 计算半径
        int radius = (int) (Math.min(this.getWidth(), this.getHeight()) * 0.8 * 0.5);
        // 画圆
        g.drawOval(xCenter - radius, yCenter - radius, radius * 2, radius * 2);

        // 画钟面上显示的数字
        g.drawString("12", xCenter - 6, yCenter - radius + 12);
        g.drawString("3", xCenter + radius - 12, yCenter + 4);
        g.drawString("6", xCenter - 4, yCenter + radius - 8);
        g.drawString("9", xCenter - radius + 4, yCenter + 6);

        // 画时针、分针、秒针
        g.drawLine(xCenter, yCenter, (int) (xCenter + radius * 0.8 * Math.sin(second * 2 * Math.PI / 60)), (int) (yCenter - radius * 0.8 * Math.cos(second * 2 * Math.PI / 60)));
        g.drawLine(xCenter, yCenter, (int) (xCenter + radius * 0.6 * Math.sin(minute * 2 * Math.PI / 60)), (int) (yCenter - radius * 0.6 * Math.cos(minute * 2 * Math.PI / 60)));
        g.drawLine(xCenter, yCenter, (int) (xCenter + radius * 0.4 * Math.sin((hour + minute / 60.0) * 2 * Math.PI / 12)), (int) (yCenter - radius * 0.4 * Math.cos((hour + minute / 60.0) * 2 * Math.PI / 12)));
    }
}

