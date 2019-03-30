/**
 * Copyright (C), 2019
 * FileName: ThirdThreadDemo
 * Author:   huangwenyuan
 * Date:     2019/3/17 0017 下午 10:48
 * Description:
 */

package javanet.c02.practice2;


import java.util.Random;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/17 0017
 * @since 1.0.0
 */
public class Demo3 {
    public static void main(String[] args) throws InterruptedException {
        Stock stock = new Stock("帕拉梅拉", 20);
        Random random = new Random();
        //生成不大于库存量的随机销售数量
        int sellAmount = random.nextInt(stock.getStock());
        //生成不小于50的随机进货数量
        int replenishAmount = random.nextInt(10) + 50;
        new SellThread("销售员1", stock, sellAmount).start();
        Thread.sleep(random.nextInt(2000));
        new SellThread("销售员2", stock, sellAmount).start();
        new ReplenishThread("进货员1", stock, replenishAmount).start();
    }
}

class Stock {
    /***
     * 货物名称
     */
    private String name;
    /***
     * 货物库存量
     */
    private int stock;

    /***
     * 标记货物库存，小于10为true,大于10为false
     */
    private boolean flag = false;

    public Stock() {
    }

    public Stock(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /***
     * 销售方法
     * @param sellAmount
     */
    public synchronized void sell(double sellAmount) {
        try {
            //货物库存量小于10，销售方法阻塞
            if (flag) {
                wait();
            } else {
                //货物库存量大于10
                //执行销售操作
                System.out.println(Thread.currentThread().getName() + ",销售货物：" + getName() + ",销售数量:" + sellAmount);
                stock -= sellAmount;
                System.out.println("剩余库存量为:" + stock);
                //如果库存小于10,将标志置为true
                if (stock < 10) {
                    flag = true;
                }
                // 唤醒进货线程
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /***
     * 进货的方法
     * @param replenishAmount
     */
    public synchronized void replenish(double replenishAmount) {
        //如果库存量大于10，则不需要进货,进货方法阻塞
        try {
            if (!flag) {
                wait();
            } else {
                //执行进货操作
                System.out.println(Thread.currentThread().getName() + ",购进货物：" + getName() + ",进货数量:" + replenishAmount);
                stock += replenishAmount;
                System.out.println("剩余库存为:" + stock);
                //库存量大于10，将标志置为false
                if (stock > 10) {
                    flag = false;
                }
                //    唤醒销售线程
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SellThread extends Thread {
    /***
     * 库存对象
     */
    private Stock stock;
    /***
     * 销售数量
     */
    private int sellAmount;

    public SellThread(String name, Stock stock, int sellAmount) {
        super(name);
        this.stock = stock;
        this.sellAmount = sellAmount;
    }

    @Override
    public void run() {
        //调用stock对象中的销售方法
        stock.sell(sellAmount);
    }
}

class ReplenishThread extends Thread {
    /***
     * 库存对象
     */
    private Stock stock;
    /***
     * 进货数量
     */
    private int replenishAmount;

    public ReplenishThread(String name, Stock stock, int replenishAmount) {
        super(name);
        this.stock = stock;
        this.replenishAmount = replenishAmount;
    }

    @Override
    public void run() {
        //调用stock对象中的进货方法
        stock.replenish(replenishAmount);
    }
}

    