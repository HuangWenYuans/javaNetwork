/**
 * Copyright (C), 2019
 * FileName: Servlet
 * Author:   huangwenyuan
 * Date:     2019/04/14 下午 11:57
 * Description:
 */

package javanet.c05.practice2.no2.server;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/14
 * @since 1.0.0
 */
public abstract class Servlet {
    public void service(Request request, Response response) {
        try {
            this.doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.doPost(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void doGet(Request request, Response response) throws Exception;

    public abstract void doPost(Request request, Response response) throws Exception;


}


    