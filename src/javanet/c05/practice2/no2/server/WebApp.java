package javanet.c05.practice2.no2.server;


import java.util.Map;

public class WebApp {
    private static ServletContext context;

    static {
        context = new ServletContext();
        Map<String, String> mapping = context.getMapping();
        mapping.put("/cal", "cal");

        Map<String, Servlet> servlet = context.getServlet();
        servlet.put("cal", new CalculatorServlet());
    }

    /***
     * 获取servlet的方法
     * @param url
     * @return
     */
    public static Servlet getServlet(String url) {
        if (url == null || "".equals(url = url.trim())) {
            return null;
        }
        //返回url对应的servlet
        return context.getServlet().get(context.getMapping().get(url));
    }
}