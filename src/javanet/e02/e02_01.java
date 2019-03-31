
package javanet.e02;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/3/7
 * @since 1.0.0
 */
public class e02_01 implements Runnable {

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            System.out.println("主线程:" + i);
        }
        e02_01 thread = new e02_01();
        new Thread(thread, "子线程:").start();
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName() + "" + i);
        }
    }
}

    