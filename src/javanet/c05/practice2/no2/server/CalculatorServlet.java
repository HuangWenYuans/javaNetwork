package javanet.c05.practice2.no2.server;

public class CalculatorServlet extends Servlet {

    @Override
    public void doGet(Request request, Response response) throws Exception {
        //将字符串转换为数字类型
        int num1 = Integer.parseInt(request.getParameter("num1"));
        int num2 = Integer.parseInt(request.getParameter("num2"));
        System.out.println("第一个数字：" + num1);
        System.out.println("第二个数字：" + num2);
        //打印结果到网页
        response.println("<h2>" + num1 + "x" + num2 + "=" + num1 * num2 + "</h2>");
    }

    @Override
    public void doPost(Request request, Response response) throws Exception {
    }

}
