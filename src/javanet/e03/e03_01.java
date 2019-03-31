/**
 * Copyright (C), 2019
 * FileName: e03_01
 * Author:   huangwenyuan
 * Date:     2019/03/31 下午 10:39
 * Description:
 */

package javanet.e03;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/03/31
 * @since 1.0.0
 */

public class e03_01 implements Runnable {
    A a = new A();
    B b = new B();

    public void init() {
        Thread.currentThread().setName("主线程");
        a.foo(b);
        System.out.println("进入主线程之后");
    }

    @Override
    public void run() {
        Thread.currentThread().setName("副线程");
        b.bar(a);
        System.out.println("进入副线程之后");
    }

    public static void main(String[] args) {
        e03_01 d1 = new e03_01();
        new Thread(d1).start();
        d1.init();
    }

}

class A {
    public synchronized void foo(B b) {
        System.out.println("当前线程名:" + Thread.currentThread().getName() + "进入A实例的foo方法");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程名:" + Thread.currentThread().getName() + "企图调用B实例的last()方法");
        b.last();
    }

    public synchronized void last() {
        System.out.println("进入A类的last()内部");
    }

}

class B {
    public synchronized void bar(A a) {
        System.out.println("当前线程名" + Thread.currentThread().getName() + "进入B实例的bar()方法");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程名：" + Thread.currentThread().getName() + "企图调用A实例的last()方法");
        a.last();
    }

    public synchronized void last() {
        System.out.println("进入B类的last()方法内部");
    }
}
    