package javanet.c05.practice2.no2.server;

import java.io.IOException;
import java.net.Socket;

/***
 * 处理请求的线程类
 */
public class Dispatcher implements Runnable {
    private Socket client;
    private Request request;
    private Response response;
    private int code = 200;

    public Dispatcher(Socket client) {
        this.client = client;
        try {
            //获取请求协议
            request = new Request(client.getInputStream());
            //获取响应协议
            response = new Response(client.getOutputStream());
        } catch (IOException e) {
            code = 500;
            return;
        }
    }

    @Override
    public void run() {
        try {
            //根据url获取servlet
            Servlet servlet = WebApp.getServlet(request.getUrl());
            if (servlet == null) {
                //找不到对应的处理
                this.code = 404;
            } else {
                servlet.service(request, response);
            }
            response.pushToBrowser(code);
        } catch (IOException e) {
            e.printStackTrace();
            this.code = 500;
        }
        //释放资源
        release();
    }

    /***
     * 释放资源的方法
     */
    private void release() {
        try {
            client.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
