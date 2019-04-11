/**
 * Copyright (C), 2019
 * FileName: RSAUtil
 * Author:   huangwenyuan
 * Date:     2019/04/11 上午 08:49
 * Description:
 */

package javanet.e08;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * 功能描述:
 *
 * @author huangwenyuan
 * @create 2019/04/11
 * @since 1.0.0
 */
public class RSAUtil {
    /***
     * 指定加密算法为RSA
     */
    private static final String ALGORITHM = "RSA";
    /***
     * 指定公钥文件名
     */
    private static String PUBLIC_KEY_FILE = "publicKey";
    /***
     * 指定私钥文件名
     */
    private static String PRIVATE_KEY_FILE = "privateKey";

    public static void main(String[] args) throws Exception {
        String source = "黄文源";
        System.out.println("需要加密的字符串为：" + source);

        //生成密文
        String cryptograph = encrypt(source);
        System.out.println("公钥加密后的结果：" + cryptograph);
        System.out.println();

        //解密密文
        String target = decrypt(cryptograph);
        System.out.println("私钥解密后的字符串" + target);
        System.out.println();
    }

    /***
     * 加密方法
     * @param source
     * @return
     */
    public static String encrypt(String source) throws Exception {
        Key publicKey = getKey(PUBLIC_KEY_FILE);
        //    对数据进行RSA加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        //将需要加密的字符串转换为byte数组
        byte[] bytes = source.getBytes();
        //加密数据
        byte[] bytes1 = cipher.doFinal(bytes);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(bytes);
    }

    /***
     * 解密方法
     * @param cryptograph
     * @return
     */
    public static String decrypt(String cryptograph) {
        try {
            Key privateKey = getKey(PRIVATE_KEY_FILE);
            //对已用公钥加密的数据进行RSA解密
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes = decoder.decodeBuffer(cryptograph);
            //执行解密操作
            byte[] bytes1 = cipher.doFinal(bytes);
            return new String(bytes1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }

        return null;
    }

    /***
     * 获取私钥的方法
     * @param fileName
     * @return
     */
    private static Key getKey(String fileName) throws IOException {
        Key key = null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName));
            key = (Key) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            ois.close();
        }
        return key;
    }
}

    