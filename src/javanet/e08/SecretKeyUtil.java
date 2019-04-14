/**
 * Copyright (C), 2019
 * FileName: SecretKeyUtil
 * Author:   huangwenyuan
 * Date:     2019/04/11 上午 09:23
 * Description:
 */

package javanet.e08;


import sun.misc.BASE64Encoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/11
 * @since 1.0.0
 */
public class SecretKeyUtil {
    private static final String ALGORITHM = "RSA";
    private static final int KEY_SIZE = 1024;
    private static String PUBLIC_KEY_FILE = "publicKey";
    private static String PRIVATE_KEY_FILE = "privateKey";

    public static void main(String[] args) throws NoSuchAlgorithmException {
        generateKeyPair();
        genKeyPair();

    }

    private static void genKeyPair() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        // 为RSA算法创建一个KeyPairGenerator对象 
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        // 利用上面的随机数据源初始化这个KeyPairGenerator对象
        keyPairGenerator.initialize(KEY_SIZE, secureRandom);

        // 生成密匙对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();
        byte[] publicKeyBytes = publicKey.getEncoded();
        byte[] privateKeyBytes = privateKey.getEncoded();
        String publicKeyBase64 = new BASE64Encoder().encode(publicKeyBytes);
        String privateKeyBase64 = new BASE64Encoder().encode(privateKeyBytes);
        System.out.println("publicKeyBase64.length():" + publicKeyBase64.length());
        System.out.println("publicKeyBase64:" + publicKeyBase64);
        System.out.println("privateKeyBase64.length():" + privateKeyBase64.length());
        System.out.println("privateKeyBase64:" + privateKeyBase64);
    }

    /***
     * 生成秘钥对的方法
     */
    private static void generateKeyPair() throws NoSuchAlgorithmException {
        //生成指定算法的秘钥对的KeyPairGenerator对象
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        //生成一个密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获取公钥
        Key publicKey = keyPair.getPublic();
        //获取私钥
        Key privateKey = keyPair.getPrivate();
        ObjectOutputStream oos1 = null;
        ObjectOutputStream oos2 = null;
        try {
            //将对象流生成的密钥写入文件
            oos1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
            oos2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
            oos1.writeObject(privateKey);
            oos2.writeObject(publicKey);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos1.close();
                oos2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

