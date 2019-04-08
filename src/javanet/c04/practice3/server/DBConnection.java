/**
 * Copyright (C), 2019
 * FileName: DBConnection
 * Author:   huangwenyuan
 * Date:     2019/04/08 上午 10:16
 * Description:
 */

package javanet.c04.practice3.server;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/08
 * @since 1.0.0
 */
public class DBConnection {
    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            con = DriverManager.getConnection("jdbc:odbc:student", "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

    