/**
 * Copyright (C), 2019
 * FileName: InetAddressTset
 * Author:   huangwenyuan
 * Date:     2019/3/18 0018 下午 04:25
 * Description:
 */

package javanet.c02.practice3;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/18 0018
 * @since 1.0.0
 */

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Demo1 extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Collision balls");
        Pane root = new Pane();
        Scene scene = new Scene(root, 500, 500, Color.ALICEBLUE);
        for (int i = 0; i < 10; i++) {
            Circle circle = new Circle();
            circle.setRadius((double) (Math.random() * 30) + 20);
            circle.setCenterX((double) (Math.random() * 300) + 100);
            circle.setCenterY((double) (Math.random() * 300) + 100);
            circle.setFill(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            root.getChildren().add(circle);
            Ball b = new Ball(circle, root);
            ChangeColor cc = new ChangeColor(circle);
            ChangeOpacity co = new ChangeOpacity(circle);
            Thread t1 = new Thread(b);
            Thread t2 = new Thread(cc);
            Thread t3 = new Thread(co);
            t1.start();
            t2.start();
            t3.start();
        }
        stage.setScene(scene);
        stage.show();
    }

    class Ball implements Runnable {
        private Circle c;
        private Pane p;
        private double x = (int) (Math.random() * 5 + 1), y = (Math.random() * 5 + 1);

        public Ball(Circle circle, Pane pane) {
            this.c = circle;
            this.p = pane;
        }

        public synchronized void move() {
            if (c.getCenterX() < c.getRadius() || c.getCenterX() > p.getWidth() - c.getRadius()) {
                x *= -1;
            }
            if (c.getCenterY() < c.getRadius() || c.getCenterY() > p.getHeight() - c.getRadius()) {
                y *= -1;
            }
            c.setCenterX(c.getCenterX() + x * 3);
            c.setCenterY(c.getCenterY() + y * 3);
        }

        @Override
        public void run() {
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(50), e -> move()));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
        }
    }

    class ChangeColor implements Runnable {
        private Circle c;

        public ChangeColor(Circle circle) {
            this.c = circle;
        }

        public synchronized void change() {
            c.setFill(Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
        }

        @Override
        public void run() {
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), e -> change()));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
        }

    }

    class ChangeOpacity implements Runnable {
        private Circle c;

        public ChangeOpacity(Circle circle) {
            this.c = circle;
        }

        public synchronized void change() {
            c.setOpacity((double) Math.random());
        }

        @Override
        public void run() {
            Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), e -> change()));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
        }

    }
}

    