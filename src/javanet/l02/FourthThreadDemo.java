/**
 * Copyright (C), 2019
 * FileName: FourthThreadDemo
 * Author:   huangwenyuan
 * Date:     2019/3/19 0019 下午 09:21
 * Description:
 */

package javanet.l02;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/19 0019
 * @since 1.0.0
 */
public class FourthThreadDemo extends Thread {
    private Counter counter;

    public FourthThreadDemo() {
    }

    public FourthThreadDemo(Counter counter) {
        this.counter = counter;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        //创建数组存放300个子线程
        FourthThreadDemo[] threads = new FourthThreadDemo[300];
        for (int i = 0; i < 300; i++) {
            threads[i] = new FourthThreadDemo(counter);
            threads[i].start();
        }
    }

    @Override
    public void run() {
        counter.decrement();
    }

}

class Counter {
    private int c = 30;

    public synchronized void decrement() {
        if (c > 0) {
            c--;
        }
        System.out.println(Thread.currentThread() + "=" + c);
    }
}


    