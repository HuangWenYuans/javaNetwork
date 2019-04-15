/**
 * Copyright (C), 2019
 * FileName: ServletContext
 * Author:   huangwenyuan
 * Date:     2019/04/14 下午 11:50
 * Description:
 */

package javanet.c05.practice2.no2.server;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/14
 * @since 1.0.0
 */
public class ServletContext {
    private Map<String, Servlet> servlet;
    private Map<String, String> mapping;

    public ServletContext() {
        servlet = new HashMap<>();
        mapping = new HashMap<>();
    }

    public Map<String, Servlet> getServlet() {
        return servlet;
    }

    public void setServlet(Map<String, Servlet> servlet) {
        this.servlet = servlet;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }
}
    