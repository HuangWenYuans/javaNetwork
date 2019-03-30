/**
 * Copyright (C), 2019
 * FileName: DeadLock
 * Author:   huangwenyuan
 * Date:     2019/3/14 8:57
 * Description:
 */

package javanet.c02.practice2;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/14
 * @since 1.0.0
 */
public class Demo2 implements Runnable {
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
        Demo2 d1 = new Demo2();
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