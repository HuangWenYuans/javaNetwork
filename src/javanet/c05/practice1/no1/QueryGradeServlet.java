/**
 * Copyright (C), 2019
 * FileName: QueryGradeServlet
 * Author:   huangwenyuan
 * Date:     2019/04/13 下午 09:13
 * Description:
 */

package javanet.c05.practice1.no1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/13
 * @since 1.0.0
 */
@WebServlet("/queryGrade")
public class QueryGradeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String name = request.getParameter("name");
        System.out.println(name);
        if ("hwy".equals(name)) {
            response.getWriter().write("90");
        } else if ("xm".equals(name)) {
            response.getWriter().write("80");
        }else if("xh".equals(name)){
            response.getWriter().write("80");
        }else{
            response.getWriter().write("0");
        }


    }
}

    