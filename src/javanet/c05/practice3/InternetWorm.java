/**
 * Copyright (C), 2019
 * FileName: InternetWorm
 * Author:   huangwenyuan
 * Date:     2019/04/13 下午 10:49
 * Description:
 */

package javanet.c05.practice3;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/13
 * @since 1.0.0
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class InternetWorm {
    /***
     * 用户名
     */
    private static String userName = "18270631410";
    /***
     * 密码
     */
    private static String password = "hwy79868058";
    /***
     * 需要访问的URL
     */
    private static String redirectURL = "http://home.renren.com/970416750";
    /***
     * 登陆请求发送的URL
     */
    private static String renRenLoginURL = "http://www.renren.com/PLogin.do";
    private HttpResponse response;
    private DefaultHttpClient httpclient = new DefaultHttpClient();

    /***
     * 登陆的方法
     * @return
     */
    private boolean login() {
        HttpPost httpost = new HttpPost(renRenLoginURL);
        //建立一个NameValuePair数组，用于存储欲传送的参数，添加相关参数，见上图中的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("origURL", redirectURL));
        nvps.add(new BasicNameValuePair("domain", "renren.com"));
        nvps.add(new BasicNameValuePair("isplogin", "true"));
        nvps.add(new BasicNameValuePair("email", userName));
        nvps.add(new BasicNameValuePair("password", password));
        try {
            //登陆成功，获取返回的数据，即html文件
            httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            response = httpclient.execute(httpost);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            httpost.abort();
        }
        return true;
    }

    private String getRedirectLocation() {
        //获取响应头的URL
        Header locationHeader = response.getFirstHeader("Location");
        if (locationHeader == null) {
            return null;
        }
        return locationHeader.getValue();
    }

    /***
     * 获取HTML文本的方法
     * @param redirectLocation
     * @return
     */
    private String getText(String redirectLocation) {
        HttpGet httpget = new HttpGet(redirectLocation);
        // Create a response handler
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody;
        try {
            responseBody = httpclient.execute(httpget, responseHandler);
        } catch (Exception e) {
            e.printStackTrace();
            responseBody = null;
        } finally {
            httpget.abort();
            httpclient.getConnectionManager().shutdown();
        }
        return responseBody;
    }

    /***
     * 返回页面内容的方法
     * @return
     */
    public String returnText() {
        //如果登陆成功则打印登陆后页面的内容
        if (login()) {
            String redirectLocation = getRedirectLocation();
            if (redirectLocation != null) {
                return getText(redirectLocation);
            }
        }
        return null;
    }

    public static void main(String[] args) {
        //创建一个爬虫对象
        InternetWorm internetWorm = new InternetWorm();
        System.out.println(internetWorm.returnText());
        //获取给定网页中包含的元素
        getPageElements("http://www.renren.com/PLogin.do");
    }

    /***
     * 获取指定页面中元素的方法
     * @param url
     */
    public static void getPageElements(String url) {
        print("正在获取URL:%s中的指定元素。。。。。", url);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取页面中的超链接
        Elements links = doc.select("a[href]");
        //获取页面js文件
        Elements media = doc.select("[src]");
        //获取连接中的css文件
        Elements imports = doc.select("link[href]");

        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img")) {
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            } else {
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
            }
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    /***
     * 打印的方法
     * @param msg
     * @param args
     */
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    /***
     * 去除空格的方法
     * @param s
     * @param width
     * @return
     */
    private static String trim(String s, int width) {
        if (s.length() > width) {
            return s.substring(0, width - 1) + ".";
        } else {
            return s;
        }
    }
}
    