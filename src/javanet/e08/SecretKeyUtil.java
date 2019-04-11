/**
 * Copyright (C), 2019
 * FileName: SecretKeyUtil
 * Author:   huangwenyuan
 * Date:     2019/04/11 上午 09:23
 * Description:
 */

package javanet.e08;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

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

