/**
 * Copyright (C), 2019
 * FileName: TemperatureServer
 * Author:   huangwenyuan
 * Date:     2019/04/02 下午 07:35
 * Description: 温度曲线服务端程序
 */

package javanet.l04.folder2;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述: 温度曲线服务端程序
 *
 * @author huangwenyuan
 * @create 2019/04/02
 * @since 1.0.0
 */
public class TemperatureServer {
    public static void main(String[] args) {
        byte[] inBuff = new byte[1024];
        DatagramPacket inPacket = new DatagramPacket(inBuff, inBuff.length);
        List<Integer> list = null;
        try {
            //创建DatagramSocket对象
            DatagramSocket datagramSocket = new DatagramSocket(20000);
            //循环接受客户端发送的数据
            list = new ArrayList<>();

            for (int i = 0; i < 24; i++) {
                datagramSocket.receive(inPacket);
                //将接受到的内容转换成字符串输出
                String numStr = new String(inBuff, 0, inPacket.getLength());
                //System.out.print(numStr+" ");
                //将接收到的所有温度都加入到一个list中
                list.add(Integer.parseInt(numStr));
                System.out.println(list);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PaintChart chart = new PaintChart("温度曲线服务器程序", "温度曲线图",list);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}

/***
 * 依赖第三方jar包的绘画类
 */
class PaintChart extends ApplicationFrame {
    /***
     * 初始构造方法
     * @param applicationTitle
     * @param chartTitle
     */
    public PaintChart(String applicationTitle, String chartTitle,List<Integer> list) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "时间",
                "温度",
                createDataset(list),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(xylineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        final XYPlot plot = xylineChart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(1.0f));
        plot.setRenderer(renderer);
        setContentPane(chartPanel);
    }

    /***
     *  给坐标赋值的方法
     * @return
     */
    XYDataset createDataset(List<Integer> list) {
        final XYSeries firefox = new XYSeries("温度");
        for (int i = 0; i <list.size() ; i++) {
            firefox.add(i,list.get(i));
        }
        //final XYSeries chrome = new XYSeries("Chrome");
        //chrome.add(1.0, 4.0);
        //chrome.add(2.0, 5.0);
        //chrome.add(3.0, 6.0);
        //final XYSeries iexplorer = new XYSeries("InternetExplorer");
        //iexplorer.add(3.0, 4.0);
        //iexplorer.add(4.0, 5.0);
        //iexplorer.add(5.0, 4.0);
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(firefox);
        return dataset;
    }

    //public static void main(String[] args) {
    //    PaintChart chart = new PaintChart("温度曲线服务器程序", "温度曲线图");
    //    chart.pack();
    //    RefineryUtilities.centerFrameOnScreen(chart);
    //    chart.setVisible(true);
    //}
}
