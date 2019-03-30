/**
 * Copyright (C), 2019
 * FileName: Demo4
 * Author:   huangwenyuan
 * Date:     2019/3/18 0018 下午 04:27
 * Description:
 */

package javanet.c02.practice2;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/18 0018
 * @since 1.0.0
 */


public class Demo4 {
    public static void main(String[] args) {
        List<Goods> goods = new ArrayList<>(20);
        for (int i = 0; i < 20; i++) {
            Goods goodz = new Goods((int) (Math.random() * 81 + 20), (i + 1));
            goods.add(goodz);
        }
        Mall mall = new Mall(goods);
        System.out.println(mall.getList().size());
        for (int i = 0; i < 50; ++i) {
            new Thread(new Customer(new Cart(), mall), i + "号顾客").start();
        }
    }
}

class Goods {
    private int price;
    private int no;


    public Goods(int price, int no) {
        this.no = no;
        this.price = price;
    }

    public int getNo() {
        return no;
    }

    public int getPrice() {
        return price;
    }
}

class Mall {
    final int MAX_GOODS = 20;
    List<Goods> list;

    volatile boolean flag = true;

    public Mall(List<Goods> list) {
        this.list = list;
    }

    public List<Goods> getList() {
        return list;
    }

    public synchronized Goods buy() {
        Goods goods = null;
        if (list.size() > 0) {
            int index = (int) (Math.random() * list.size());
            goods = list.get(index);
            list.remove(index);
            System.out.println(Thread.currentThread().getName() + "抢到商品" + goods.getNo() + "，价格为：" + goods.getPrice());
        }
        if (list.size() == 0) {
            flag = false;
        }
        return goods;
    }

    public synchronized void back(List<Goods> goods) {
        for (Goods goodz : goods) {
            list.add(goodz);
        }
        flag = true;
    }

}

class Cart {
    final static int MAX_TOTAL = 3;
    final static int MAX_MONEY = 100;
    List<Goods> list = new ArrayList<>();
    int totalAmount;

    public int getCount() {
        return list.size();
    }

    public void add(Goods goods) {

        setTotalAmount(getTotalAmount() + goods.getPrice());
        list.add(goods);
    }

    public void delete(int index) {
        setTotalAmount(getTotalAmount() - list.get(index).getPrice());
        list.remove(index);
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalAmount() {
        if (totalAmount == 0) {
            for (Goods goods : list) {
                totalAmount += goods.getPrice();
            }
        }
        return totalAmount;
    }

    public int getMaxIndex() {
        int index = 0;
        int max = 0;
        for (int i = 0; i < list.size(); ++i) {
            Goods m = list.get(i);
            if (max < m.getPrice()) {
                max = m.getPrice();
                index = i;
            }
        }
        return index;
    }

    public List<Goods> cost() {
        List<Goods> mylist = new ArrayList<>();
        while (getTotalAmount() > MAX_MONEY) {
            mylist.add(list.get(getMaxIndex()));
            delete(getMaxIndex());
            System.out.println(Thread.currentThread().getName() + "退还了一件商品");
        }
        System.out.println(Thread.currentThread().getName() + "一共抢购到 "
                + getCount() + " 件商品，总价格为：" + getTotalAmount());
        list.clear();
        return mylist;
    }


}

class Customer implements Runnable {
    Cart cart;
    Mall mall;

    public Customer(Cart cart, Mall mall) {
        this.cart = cart;
        this.mall = mall;
    }


    @Override
    public void run() {
        while (mall.flag) {
            if (cart.getCount() < Cart.MAX_TOTAL) {
                Goods rush = mall.buy();
                if (rush != null) {
                    cart.add(rush);
                }

            }
        }
        List<Goods> clear = cart.cost();
        if (clear.size() != 0) {
            mall.back(clear);
        }
    }
}


    