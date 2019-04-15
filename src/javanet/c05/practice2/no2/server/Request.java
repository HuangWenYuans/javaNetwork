package javanet.c05.practice2.no2.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Request {
    /***
     * 请求方式
     */
    private String method;
    /***
     * 请求url
     */
    private String url;
    //请求参数
    private Map<String, List<String>> parameterMapValues;
    private final String CRLF = "\r\n";
    private InputStream is;
    /***
     * 请求信息
     */
    private String requestInfo;


    public Request() {
        method = "";
        url = "";
        requestInfo = "";
        parameterMapValues = new HashMap<>();
    }

    public Request(InputStream is){
        this();
        this.is = is;
        try {
            byte[] data = new byte[40960];
            int len = is.read(data);
            requestInfo = new String(data, 0, len);
            //System.out.println(requestInfo);
        } catch (IOException e) {
            return;
        }
        //分析请求信息
        parseRequestInfo();
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, List<String>> getParameterMapValues() {
        return parameterMapValues;
    }

    public void setParameterMapValues(Map<String, List<String>> parameterMapValues) {
        this.parameterMapValues = parameterMapValues;
    }

    public String getCRLF() {
        return CRLF;
    }

    public InputStream getIs() {
        return is;
    }

    public void setIs(InputStream is) {
        this.is = is;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    /***
     * 分析请求信息的方法
     */
    private void parseRequestInfo() {
        if (requestInfo == null || "".equals(requestInfo = requestInfo.trim())) {
            // 如果请求信息为空
            return;
        }
        //接受请求参数
        String paramString = "";
        //获取请求方式
        String firstLine = requestInfo.substring(0, requestInfo.indexOf(CRLF)).trim();
        //"/"的位置
        int index = requestInfo.indexOf("/");
        this.method = firstLine.substring(0, index).trim();
        //获取url字符串
        String urlStr = firstLine.substring(index, firstLine.indexOf("HTTP/")).trim();
        if (this.method.equalsIgnoreCase("post")) {
            this.url = urlStr;
            paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
        } else if (this.method.equalsIgnoreCase("get")) {
            if (urlStr.contains("?")) {
                //获取是否存在参数
                String[] urlArray = urlStr.split("\\?");
                this.url = urlArray[0];
                //获取请求参数
                paramString = urlArray[1];
            } else {
                //不存在参数
                this.url = urlStr;
            }
        }

        if (paramString.equals("")) {
            //如果不存在请求参数
            return;
        }
        //否则将请求参数封装到map中
        parseParams(paramString);

    }

    /***
     * 将请求参数封装到map中的方法
     * @param paramString
     */
    private void parseParams(String paramString) {
        //分割数组
        StringTokenizer token = new StringTokenizer(paramString, "&");
        while (token.hasMoreTokens()) {
            String keyValue = token.nextToken();
            String[] keyValues = keyValue.split("=");
            if (keyValues.length == 1) {
                keyValues = Arrays.copyOf(keyValues, 2);
                keyValues[1] = null;
            }
            //map的键
            String key = keyValues[0].trim();
            //map的值
            String value = null == keyValues[1] ? null : decode(keyValues[1].trim(), "utf-8");

            //    转化成map
            if (!parameterMapValues.containsKey(key)) {
                parameterMapValues.put(key, new ArrayList<>());
            }
            List<String> values = parameterMapValues.get(key);
            values.add(value);
        }

    }

    /**
     * 处理中文的方法
     *
     * @return
     */
    private String decode(String value, String enc) {
        try {
            //进行解码
            return java.net.URLDecoder.decode(value, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过name获取对应的多个值
     *
     * @param name
     * @return
     */
    public String[] getParameterValues(String name) {
        List<String> values;
        if ((values = parameterMapValues.get(name)) == null) {
            return null;
        }
        return values.toArray(new String[0]);
    }

    /**
     * 通过name获取对应的一个值
     *
     * @param name
     * @return
     */
    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        return values == null ? null : values[0];
    }

}
