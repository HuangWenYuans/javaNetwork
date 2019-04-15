package javanet.c05.practice2.no2.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
    private BufferedWriter bw;
    /***
     * 正文内容
     */
    private StringBuilder content;
    /***
     * 协议头（状态行与请求头 回车）信息
     */
    private StringBuilder headInfo;
    /***
     * 正文的长度
     */
    private int len;
    /***
     * 空格
     */
    private final String BLANK = " ";
    /***
     * 回车符
     */
    private final String CRLF = "\r\n";

    private Response() {
        content = new StringBuilder();
        headInfo = new StringBuilder();
        len = 0;
    }

    public Response(Socket client) {
        this();

        try {
            bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            headInfo = null;
        }
    }

    public Response(OutputStream os) {
        this();
        bw = new BufferedWriter(new OutputStreamWriter(os));
    }

    /***
     * 动态添加内容的方法
     * @param info
     * @return
     */
    public Response print(String info) {
        content.append(info);
        len += info.getBytes().length;
        return this;
    }

    /***
     * 构建正文+换行的方法
     * @param info
     * @return
     */
    public Response println(String info) {
        content.append(info).append(CRLF);
        len += (info + CRLF).getBytes().length;
        return this;
    }

    /***
     *  推送响应信息给浏览器的方法
     * @param code
     * @throws IOException
     */
    public void pushToBrowser(int code) throws IOException {
        if (null == headInfo) {
            code = 505;
        }
        createHeadInfo(code);
        bw.append(headInfo);
        bw.append(content);
        bw.flush();
    }

    /***
     * 构建响应头信息的方法
     * @param code
     */
    private void createHeadInfo(int code) {
        //1、响应行: HTTP/1.1 200 OK
        headInfo.append("HTTP/1.1").append(BLANK);
        headInfo.append(code).append(BLANK);
        //不同状态码的提示信息
        switch (code) {
            case 200:
                headInfo.append("OK").append(CRLF);
                break;
            case 404:
                headInfo.append("NOT FOUND").append(CRLF);
                break;
            case 505:
                headInfo.append("SERVER ERROR").append(CRLF);
                break;
        }
        //2、响应头(最后一行存在空行):
        headInfo.append("Date:").append(new Date()).append(CRLF);
        headInfo.append("Server:").append("Server/0.0.1;charset=UTF-8").append(CRLF);
        headInfo.append("Content-type:text/html").append(CRLF);
        headInfo.append("Content-length:").append(len).append(CRLF);
        headInfo.append(CRLF);
    }

}
