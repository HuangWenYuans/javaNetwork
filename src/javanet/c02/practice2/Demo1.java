/**
 * Copyright (C), 2019
 * FileName: InetAddressTset
 * Author:   huangwenyuan
 * Date:     2019/3/12 14:53
 * Description:
 */

package javanet.c02.practice2;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/12
 * @since 1.0.0
 */
public class Demo1 {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        for (int i = 0; i < 100; i++) {
            FansThread ft = new FansThread(ticket);
            ft.setName(i + "号粉丝");
            ft.start();
        }

    }
}

class Ticket {
    private int count;

    public Ticket() {
        this.count = 20;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public synchronized void sell(String name) {
        if (this.count > 0) {
            count--;
            System.out.println(name + "购票成功，剩余票数：" + count + "张");
        } else {
            System.out.println("抱歉，票已售完！");
            System.exit(1);
        }
    }
}

class FansThread extends Thread {
    private Ticket ticket;

    public FansThread() {
    }

    public FansThread(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public void run() {
        while (true) {
            //循环调用门票销售的方法,进行抢购
            ticket.sell(this.getName());
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

    