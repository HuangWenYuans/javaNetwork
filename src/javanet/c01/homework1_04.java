/**
 * Copyright (C), 2019
 * FileName: homework1_04
 * Author:   huangwenyuan
 * Date:     2019/3/7 22:44
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
public class homework1_04 {
    public static void main(String[] args) throws IOException {
        double num = Math.random();
        List<Shape> list = new ArrayList<>();
        if (num > 0.5) {
            //创建一个半径为2.0的圆
            Shape shape = new Circle("圆1", 2.0);
            list.add(shape);
        } else {
            //创建一个长为3宽为2的长方形
            Shape shape = new Rectangle("长方形", 2, 3);
            list.add(shape);
        }
        //打印出形状的相关信息
        for (int i = 0; i < list.size(); i++) {
            list.get(i).printInfo();
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("shapes.data"));
        //将shapeList写入文件
        oos.writeObject(list);

    }
}

abstract class Shape implements Serializable {
    public abstract double getArea();

    public void printInfo() {
        System.out.println(getClass().getSimpleName() +
                " has area " + getArea());
    }
}

class Circle extends Shape {
    /***
     * 名称
     */
    private String name;
    /***
     * 半径
     */
    private double r;

    public Circle() {
    }

    public Circle(String name, double r) {
        this.name = name;
        this.r = r;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Override
    public double getArea() {
        return Math.PI * r * r;
    }
}

class Rectangle extends Shape {
    /***
     * 名称
     */
    private String name;
    /***
     * 宽
     */
    private double width;
    /***
     * 高
     */
    private double heigh;

    public Rectangle() {
    }

    public Rectangle(String name, double width, double heigh) {
        this.name = name;
        this.width = width;
        this.heigh = heigh;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getArea() {
        return width * heigh;
    }
}
    