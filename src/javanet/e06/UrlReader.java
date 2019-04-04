/**
 * Copyright (C), 2019
 * FileName: UrlReader
 * Author:   huangwenyuan
 * Date:     2019/04/02 下午 02:45
 * Description:
 */

package javanet.e06;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 功能描述: 通过第三方jar包抓取input中的name属性值
 *
 * @author huangwenyuan
 * @create 2019/04/02
 * @since 1.0.0
 */
public class UrlReader {
    public static void main(String[] args) throws IOException {
        //需要抓取的网页的URL
        URL url = new URL("http://localhost:8888/practice1/NewFile.html");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        String htmlText = "";
        //读取网页对应的html文本
        while ((line = br.readLine()) != null) {
            htmlText += "\n" + line;
        }
        //将html文本解析成Documen对象
        Document document = Jsoup.parse(htmlText);
        //获取input框
        Elements rows = document.select("input");
        //获取input框中的name属性值
        String e = rows.attr("name");
        System.out.println(e);

    }
}

    