/**
 * Copyright (C), 2019
 * FileName: Encryption
 * Author:   huangwenyuan
 * Date:     2019/04/23 下午 09:32
 * Description:
 */

package javanet.c06.practice2;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;


/***
 * A主机
 */
class A {
    /***
     * 公钥
     */
    private static String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiwH7z7ie9SSGpcGLBY2Da/929jSHH9jR\n" +
            "m7KTWIQzJQdDOWwMjKimySw+SV7B0z4CfSCaTr1T1DFGUxAcznFCIHVTGC9pXpRkUg0LHYmD8+IM\n" +
            "b/RpzhFotSb6OImtWSBh78ENd0750MD24cQPx7iPjbWdb/cdfFvguIkbRB9EBcHqaViLkxRb4mzw\n" +
            "bUYiBo43Mz9gWiL/SQ85yUZUwHvqsBx+nLcpQ/FwxbEYeyRWLhsMTckki3fWAYovtwhq/HItaubV\n" +
            "ZbWpcmHxo4hLwD9VJ1Q2hVc48t5pQ6GNQxeYSMJnKrWuXZFyZFm3sX7kBz/SY8E+iv0U4A93OGBv\n" +
            "xkD3MwIDAQAB";
    private static String randomPwd;

    public static void main(String[] args) {
        Socket s = null;
        try {
            //   生成随机密码
            randomPwd = generateRandomPwd();
            s = new Socket("localhost", 30000);
            new Thread(new ClientThread(s, randomPwd)).start();
            //获取socket对应的输出流
            PrintStream ps = new PrintStream(s.getOutputStream());
            System.out.println("A主机生成的随机密码为：" + randomPwd);
            //将Base64编码后的公钥转换成PublicKey对象
            PublicKey publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            //用公钥加密生成的随机密码
            byte[] publicEncrypt = RSAUtil.publicEncrypt(randomPwd.getBytes(), publicKey);
            //加密后的内容Base64编码
            String byte2Base64 = RSAUtil.byte2Base64(publicEncrypt);
            System.out.println("公钥加密并Base64编码的结果：" + byte2Base64);
            //发送给B主机
            ps.println(byte2Base64);
            //发送完数据后，关闭输出流以保证B主机在读取数据时不会阻塞
            s.shutdownOutput();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 生成20位随机密码的方法
     */
    private static String generateRandomPwd() {
        String str = "";
        for (int i = 0; i < 20; i++) {
            //随机选择是生成大写字母、小写字母还是数字
            int flag = new Random().nextInt(3);

            switch (flag) {
                case 0:
                    //随机生成数字
                    int num = new Random().nextInt(10);
                    str += num;
                    break;
                case 1:
                    //随机生成大写字母
                    char capital = (char) (new Random().nextInt(26) + 65);
                    str += capital;
                    break;
                default:
                    //随机生成小写字母
                    char lowerCase = (char) (new Random().nextInt(26) + 97);
                    str += lowerCase;
                    break;
            }
        }
        return str;
    }
}

/***
 * 客户端线程
 */
class ClientThread implements Runnable {
    private Socket s;
    BufferedReader br;
    String randomPwd = "";

    public ClientThread(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    public ClientThread(Socket s, String randomPwd) throws IOException {
        this.s = s;
        this.randomPwd = randomPwd;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));

    }

    public void run() {
        try {
            //获取B主机发送的Base64编码后的秘钥
            String line = null;
            String content = "";
            while ((line = br.readLine()) != null) {
                content += line;
            }
            int index = content.indexOf("内容");
            System.out.println("content" + content);
            String base64Str = content.substring(0, index);
            System.out.println("B主机发来的Base64编码后的秘钥" + base64Str);
            //获取B主机发送的Base64加密后的内容
            String bytesBase64 = content.substring(index + 2, content.length());
            System.out.println("B主机发送的Base64加密后的内容" + bytesBase64);
            //将Base64编码的字符串，转换成AES秘钥
            SecretKey aesKey2 = AESUtil.loadKeyAES(base64Str);
            //加密后的内容Base64解码
            byte[] base642Byte = AESUtil.base642Byte(bytesBase64);
            //解密
            byte[] decryptAES = AESUtil.decryptAES(base642Byte, aesKey2);
            //解密后的明文
            String decrpt = new String(decryptAES);
            System.out.println("B发送内容解密后为 " + decrpt);
            System.out.println(randomPwd);
            String password = decrpt.substring(0, randomPwd.length());
            System.out.println("B主机发来的A生成的随机密码为：" + password);
            String digest = new String(decryptAES).substring(randomPwd.length() + 1, decrpt.length());
            System.out.println("B主机发来的消息概要为" + digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

/***
 * B主机
 */
class B {
    /***
     * 私钥
     */
    private static String privateKeyStr = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCLAfvPuJ71JIalwYsFjYNr/3b2\n" +
            "NIcf2NGbspNYhDMlB0M5bAyMqKbJLD5JXsHTPgJ9IJpOvVPUMUZTEBzOcUIgdVMYL2lelGRSDQsd\n" +
            "iYPz4gxv9GnOEWi1Jvo4ia1ZIGHvwQ13TvnQwPbhxA/HuI+NtZ1v9x18W+C4iRtEH0QFweppWIuT\n" +
            "FFvibPBtRiIGjjczP2BaIv9JDznJRlTAe+qwHH6ctylD8XDFsRh7JFYuGwxNySSLd9YBii+3CGr8\n" +
            "ci1q5tVltalyYfGjiEvAP1UnVDaFVzjy3mlDoY1DF5hIwmcqta5dkXJkWbexfuQHP9JjwT6K/RTg\n" +
            "D3c4YG/GQPczAgMBAAECggEAewGpW3nKp7DHuZvXsnU0S3gHQ/zdaZAKg5V1D4oEXAreMMztGfcP\n" +
            "h8sv3usL5yrjNuiHw4+Dw6xKx6vlYcUVN/W6LxedTUVQtM63/E44xrJMQTFuAxyipFK2bxtdmxWt\n" +
            "tGQHz4DCTTjD/qnpMNmfpk2E4JQysl33MDSbPR/RqKlEXzbDpFgzs/GoBYiBRdjLeCAim+VbargL\n" +
            "wlsp6WfgN5Bz8//ehjPwui3jrpeXJtABECNMDsoQkymj+DUPkJiq3hnueR5HOxK1DPytFy4JX5e7\n" +
            "vLzkEwV2YEY6gtL/hXfiKUFtBBAMwKHjNHMahINZzUmaB177/9NfwLzkFWiuyQKBgQDzLc4JfE9V\n" +
            "/KHPBEivsYvVLQY8F3h6OiW+VmR/QkDD53sj5go7uffULi+cULCLVAGibmpr0+1nCQMTCXkK4jKv\n" +
            "XyU7yR7MndF0RPD761QXZ32yzKvXzVjTBXEj6pN87bbi8aLNIloJu8Awuhe39ChbfyOg/3qnF/N+\n" +
            "x2rKfrkpDwKBgQCSVi0uXcm5XaZQr8xDVwtEfimFQHjqIxwl0oKSVd8vYqJjsdn3BmiOp79vsFZt\n" +
            "7trQhu1p7N6jubThEU026NilNfnKuIh4aoYUh9XXJiseVG/agCHlaLN5+YjrcKxj9d3p6kekntay\n" +
            "OGcCqKQpH4J+JtrzeW7JsXUsMJ0OKHannQKBgDXJy+kNNf8vAhtoNyuleeiNLPPdvShL9E1FjrUX\n" +
            "UnwCl5o5J8pOOCMWlDFfJ62pxzLGeYOgPMKtm6B6V+uwXMWtpe6zbbyUq9+r4CHYy6Dho0wJOXli\n" +
            "UmNasMvg9v6LFgjcqcX18zRz8u7UOqrVtpeGZ7sUx2xuP82rDlP787jBAoGAQAsrFuHBCTktMp7d\n" +
            "nJMZwU4dyc2fnYjkJsFsl7KxTMG0JEGQDQZm+Bf+NllMWixw41ZJzVP3VFxQBzhVPLTHK/blbY05\n" +
            "XMnyCEYylkiEEnANy+jL7OTXt4g4HiJfsBfImTkJRcimWbVJj+ZSghTiPWev52K709WwXDiiY7Hd\n" +
            "zBkCgYEAnpDBjA18/71STWObzbcBg+s5HSUqTARUp0ITs1az+YY+SmkURldXOsbrTF/fCl/uBas+\n" +
            "uIHliEKc43MAy70Y0ZxFK12/Vl+4u9ZykDCcE5ie0YNCW0CJdbuNlnd+w+Z7BIdZp5BrO1P016u0\n" +
            "SO65g2gYomHYoaynnP6VUBQj8aM=";

    public B(String privateKeyStr) {
        this.privateKeyStr = privateKeyStr;
    }

    public String getPrivateKey() {
        return privateKeyStr;
    }

    public void setPrivateKey(String privateKeyStr) {
        this.privateKeyStr = privateKeyStr;
    }

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(30000);
            while (true) {
                Socket s = ss.accept();
                //启动服务器线程
                new Thread(new ServerThread(s)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ServerThread implements Runnable {
        Socket s;
        BufferedReader br;
        private String privateKeyStr = B.privateKeyStr;

        public ServerThread(Socket s) throws IOException {
            this.s = s;
            //获取Socket输入流并包装成缓冲字符流
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }

        @Override
        public void run() {
            try {
                //获取主机A发送的随机密码
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String byte2Base64 = new String(sb);
                System.out.println("B获取到的公钥加密并Base64编码的结果：" + byte2Base64);
                //计算所获取到的密码的消息概要
                String digest = RSAUtil.getDigest("MD5", byte2Base64.getBytes());
                System.out.println("A发送的密码对应的摘要为：" + digest);

                //将Base64编码后的私钥转换成PrivateKey对象
                PrivateKey privateKey = RSAUtil.string2PrivateKey(privateKeyStr);
                //加密后的内容Base64解码
                byte[] base642Byte = RSAUtil.base642Byte(byte2Base64);
                //用私钥解密
                byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
                //解密A主机发送的密码
                String randomPwd = new String(privateDecrypt);
                System.out.println("A发送的随机密码为：" + randomPwd);
                //将消息摘要与密码一起发送给A主机
                String str = randomPwd + digest;

                //生成AES秘钥，并Base64编码
                String base64Str = AESUtil.genKeyAES();
                System.out.println("AES秘钥Base64编码:" + base64Str);
                //将Base64编码的字符串，转换成AES秘钥
                SecretKey aesKey = AESUtil.loadKeyAES(base64Str);
                //加密消息摘要与密码
                byte[] encryptAES = AESUtil.encryptAES(str.getBytes(), aesKey);
                //加密后的内容Base64编码
                String bytesBase64 = AESUtil.byte2Base64(encryptAES);
                System.out.println("内容加密并Base64编码的结果：" + bytesBase64);

                PrintStream ps = new PrintStream(s.getOutputStream());
                //将Base64编码后的秘钥发送给主机A
                ps.println(base64Str);
                //将Base64编码后的内容发送给主机A
                ps.println("内容" + bytesBase64);
                //关闭输出流
                s.shutdownOutput();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}


/***
 * RSA加密算法实现类
 */
class RSAUtil {
    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair) {
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair) {
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception {
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception {
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }

    /***
     * 获取消息摘要的方法
     * @param algorithm
     * @param content
     * @return
     * @throws Exception
     */
    public static String getDigest(String algorithm, byte[] content) throws Exception {
        MessageDigest instance = MessageDigest.getInstance(algorithm);
        instance.update(content);
        //当所有数据已被更新,调用digest()方法完成哈希计算,返回字节数组
        byte[] digest = instance.digest();
        return DatatypeConverter.printHexBinary(digest);
    }
}


/***
 * AES加密算法实现类
 */
class AESUtil {

    /***
     * 生成AES秘钥并进行Base64编码的方法
     * @return
     * @throws Exception
     */
    public static String genKeyAES() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        SecretKey key = keyGen.generateKey();
        String base64Str = byte2Base64(key.getEncoded());
        return base64Str;
    }

    /***
     * 将编码后的秘钥转换成SecretKey对象的方法
     * @param base64Key
     * @return
     * @throws Exception
     */
    public static SecretKey loadKeyAES(String base64Key) throws Exception {
        byte[] bytes = base642Byte(base64Key);
        SecretKeySpec key = new SecretKeySpec(bytes, "AES");
        return key;
    }

    /***
     * 将字节数组转换成Base64编码
     * @param bytes
     * @return
     */
    public static String byte2Base64(byte[] bytes) {
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }


    /***
     * Base64编码转换成字节数组
     * @param base64Key
     * @return
     * @throws IOException
     */
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }

    /***
     * 加密的方法
     * @param source
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptAES(byte[] source, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(source);
    }

    /***
     * 解密的方法
     * @param source
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptAES(byte[] source, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(source);
    }
}

