package com.yzchat.socket.utils;

import android.util.Base64;
import android.util.Log;

import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;

import javax.crypto.Cipher;



@SuppressWarnings({"unused", "WeakerAccess"})
public class EncryptUtils {

    @SuppressWarnings({"unused", "WeakerAccess"})
    public static class ConfigureEncryptAndDecrypt {
        public static final String CHAR_ENCODING = "UTF-8";
        public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
        public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
    }

    public static String privateKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCT+cf8PMhEW52pPrbwYPTO8ANOL0JAqxmw6iFt/lXSnDP3hWxFb64sWtOYAeIOGBfqAY7V2DHcJO6O8nmFhxWGj9xP9Xi5Bmo3kt2Qi5gKfz61y3v8Rt54DT5zzGpGJ/pdJOj8uuFSSonldFKwowmMfJYdAMZj+WHI3h4zOd+/nQIDAQAB";

    @SuppressWarnings("FieldCanBeLocal")
    private static String SEED_TABLE = "QWERPOIUTYHGFJKDLASMZNBCVqwertyuioplkjhgfdfsazxcvbnm";

    public static String getRandomString(int length) {
        SecureRandom random = new SecureRandom();
        int index;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            index = random.nextInt(48);
            builder.append(SEED_TABLE.charAt(index));
        }
        return builder.toString();
    }

    public static String sortSign(String nonce, String charset, String mobile) {
        ArrayList<String> sortFields = new ArrayList<>();
        sortFields.add("characterEncoding=" + charset);
        sortFields.add("nonceStr=" + nonce);
        sortFields.add("mobile=" + mobile);

        Collections.sort(sortFields);

        StringBuilder builder = new StringBuilder();
        for (String key : sortFields) {
            builder.append(key).append("&");
        }

        String result = builder.toString();
        result = result.substring(0, result.length() - 1);
         Log.d("sortSign", result);
        return result;
    }

    //写一个md5加密的方法
    public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }


    /**
     * 加密方法 source： 源数据
     */
    public static String encryptByPublicKey(String source, String publicKey)
            throws Exception {
        Key key = getPublicKey(publicKey);
        /* 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ConfigureEncryptAndDecrypt.RSA_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /* 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        return new String(Base64.encode(b1, Base64.DEFAULT),
                ConfigureEncryptAndDecrypt.CHAR_ENCODING);
    }


    public static String signByPrivateKey(String content, String privateKey) {
        return RSA2.sign(content, privateKey, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                Base64.decode(key.getBytes(), Base64.DEFAULT));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                Base64.decode(key.getBytes(), Base64.DEFAULT));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }
}
