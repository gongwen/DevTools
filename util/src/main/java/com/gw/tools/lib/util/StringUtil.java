package com.gw.tools.lib.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by GongWen on 17/10/10.
 * 程序员开发工具箱(https://1024tools.com/)
 */

public class StringUtil {
    //##Hash算法
    public static final String MD5 = "MD5";
    public static final String SHA_1 = "SHA-1";
    public static final String SHA_256 = "SHA-256";
    public static final String SHA_512 = "SHA-512";

    public static final String DES = "DES";
    public static final String RSA = "RSA";
    public static final String MD5withRSA = "MD5withRSA";
    public static final String RSA_PUBLIC_KEY = "RSA_PUBLIC_KEY";
    public static final String RSA_PRIVATE_KEY = "RSA_PRIVATE_KEY";
    //<editor-fold>  字符串编码
    //java.nio.charset.StandardCharsets
    /**
     * 7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块
     */
    public static final String US_ASCII = "US-ASCII";
    /**
     * ISO拉丁字母表 No.1，也叫做ISO-LATIN-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";
    /**
     * 8 位 UCS 转换格式
     */
    public static final String UTF_8 = "UTF-8";
    /**
     * 16 位 UCS 转换格式，Big Endian(最低地址存放高位字节）字节顺序
     */
    public static final String UTF_16BE = "UTF-16BE";
    /**
     * 16 位 UCS 转换格式，Litter Endian（最高地址存放地位字节）字节顺序
     */
    public static final String UTF_16LE = "UTF-16LE";
    /**
     * 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
     */
    public static final String UTF_16 = "UTF-16";
    /**
     * 中文超大字符集
     **/
    public static final String GBK = "GBK";
    public static final String GB2312 = "GB2312";

    //</editor-fold>

    private StringUtil() {
    }

    public static int length(CharSequence charSequence) {
        return charSequence == null ? 0 : charSequence.length();
    }

    public static String toString(Object obj) {
        return toString(obj, "null");
    }

    public static String toString(Object obj, String resultOfEmpty) {
        if (obj == null) {
            return resultOfEmpty;
        }
        return obj instanceof String ? (String) obj : obj.toString();
    }

    //#########编解码相关#################################################################################
    //<editor-fold>  String,byte[]互转
    public static byte[] string2Bytes(String txt) {
        if (null == txt) {
            return null;
        }
        return txt.getBytes();
    }

    public static byte[] string2Bytes(String txt, String charsetName) throws UnsupportedEncodingException {
        if (null == txt) {
            return null;
        }
        return txt.getBytes(charsetName);
    }

    public static byte[] string2Bytes(String txt, Charset charset) {
        if (null == txt) {
            return null;
        }
        return txt.getBytes(charset);
    }

    public static String bytes2String(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }

    public static String bytes2String(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, charsetName);
    }

    public static String bytes2String(byte[] bytes, Charset charset) {
        if (bytes == null) {
            return null;
        }
        return new String(bytes, charset);
    }
    //</editor-fold>

    //<editor-fold>  Base64 编解码

    /**
     * Base64 编码
     *
     * @param input
     * @param flags Base64.DEFAULT等
     * @return
     */
    public static String encodeToString4Base64(byte[] input, int flags) {
        if (input == null) {
            return null;
        }
        return Base64.encodeToString(input, flags);
    }

    /**
     * Base64 编码
     *
     * @param input
     * @param flags Base64.DEFAULT等
     * @return
     */
    public static byte[] encode4Base64(byte[] input, int flags) {
        if (input == null) {
            return null;
        }
        return Base64.encode(input, flags);
    }

    /**
     * Base64 解码
     *
     * @param input
     * @param flags
     * @return
     */
    public static byte[] decode4Base64(byte[] input, int flags) {
        if (input == null) {
            return null;
        }
        return Base64.decode(input, flags);
    }

    /**
     * Base64 解码
     *
     * @param str
     * @param flags
     * @return
     */
    public static byte[] decode4Base64(final String str, int flags) {
        if (str == null) {
            return null;
        }
        return Base64.decode(str, flags);
    }

    /**
     * Base64 编码
     *
     * @param str
     * @param flags Base64.DEFAULT等
     * @return
     */
    public static String encodeToString4Base64(final String str, int flags) {
        if (str == null) {
            return null;
        }
        return Base64.encodeToString(str.getBytes(), flags);
    }

    /**
     * Base64 解码
     *
     * @param str
     * @param flags
     * @return
     */
    //有可能抛出IllegalArgumentException异常
    public static String decodeToString4Base64(final String str, int flags) {
        if (str == null) {
            return null;
        }
        return bytes2String(Base64.decode(str, flags));
    }
    //</editor-fold>

    //<editor-fold>  Hash计算,算法如下：Md5,SHA-1,SHA-256,SHA-512

    /**
     * MD5加密
     * http://hello-nick-xu.iteye.com/blog/2103775
     *
     * @param plainText
     * @return
     */
    public static String md5Hash(final String plainText) {
        return toHash(plainText, MD5);
    }

    public static String sha1Hash(final String plainText) {
        return toHash(plainText, SHA_1);
    }

    public static String sha256Hash(final String plainText) {
        return toHash(plainText, SHA_256);
    }

    public static String sha512Hash(final String plainText) {
        return toHash(plainText, SHA_512);
    }

    public static byte[] sha1(final String plainText) {
        return getMessageDigest(plainText, SHA_1);
    }

    public static byte[] sha256(final String plainText) {
        return getMessageDigest(plainText, SHA_256);
    }

    public static String toHash(final String plainText, String algorithm) {
        return bytes2Hex(getMessageDigest(plainText, algorithm));
    }

    public static byte[] getMessageDigest(final String toDigest, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            return messageDigest.digest(toDigest.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String bytes2Hex(final byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (byte cipher : bytes) {
            String toHexStr = Integer.toHexString(cipher & 0xff);
            builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
        }
        return builder.toString();
    }
    //</editor-fold>

    //<editor-fold>  字符串编码转换

    /**
     * 将字符编码转换成US-ASCII码
     */
    public static String toASCII(final String str) {
        return changeCharset(str, US_ASCII);
    }

    /**
     * 将字符编码转换成ISO-8859-1
     */
    public static String toISO_8859_1(final String str) {
        return changeCharset(str, ISO_8859_1);
    }

    /**
     * 将字符编码转换成UTF-8
     */
    public static String toUTF_8(final String str) {
        return changeCharset(str, UTF_8);
    }

    /**
     * 将字符编码转换成UTF-16BE
     */
    public static String toUTF_16BE(final String str) {
        return changeCharset(str, UTF_16BE);
    }

    /**
     * 将字符编码转换成UTF-16LE
     */
    public static String toUTF_16LE(final String str) {
        return changeCharset(str, UTF_16LE);
    }

    /**
     * 将字符编码转换成UTF-16
     */
    public static String toUTF_16(final String str) {
        return changeCharset(str, UTF_16);
    }

    /**
     * 将字符编码转换成GBK
     */
    public static String toGBK(final String str) {
        return changeCharset(str, GBK);
    }

    /**
     * 将字符编码转换成GB2312
     */
    public static String toGB2312(final String str) {
        return changeCharset(str, GB2312);
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str            待转换的字符串
     * @param newCharsetName 目标编码
     */
    public static String changeCharset(final String str, String newCharsetName) {
        if (str != null) {
            try {
                // 用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
                byte[] bs = str.getBytes();
                return new String(bs, newCharsetName); // 用新的字符编码生成字符串
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 字符串编码转换的实现方法
     *
     * @param str            待转换的字符串
     * @param oldCharsetName 源字符集
     * @param newCharsetName 目标字符集
     */
    public static String changeCharset(final String str, String oldCharsetName, String newCharsetName) {
        if (str != null) {
            try {
                // 用源字符编码解码字符串
                byte[] bytes = str.getBytes(oldCharsetName);
                return new String(bytes, newCharsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    //</editor-fold>

    //<editor-fold>  URL编解码 #http://blog.csdn.net/junhuahouse/article/details/23087755
    public static String urlEncode(final String str, String enc) {
        try {
            return URLEncoder.encode(str, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String urlDecode(final String str, String enc) {
        try {
            return URLDecoder.decode(str, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String urlEncode(final String str) {
        return urlEncode(str, UTF_8);
    }

    /**
     * @param str
     * @return
     */
    //有可能抛出IllegalArgumentException异常
    public static String urlDecode(final String str) {
        return urlDecode(str, UTF_8);
    }
    //</editor-fold>
    //#########编解码相关#################################################################################
    //#########加密/解密#################################################################################
    //DES对称加密/解密

    /**
     * DES 对称加密
     *
     * @param plainText
     * @param key（长度至少为8个字节）
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    //注意：encodeToString4Base64(encryption4DES(plainText,key))查看结果
    public static byte[] encryption4DES(final String plainText, String key)
            throws InvalidKeyException, NoSuchAlgorithmException
            , InvalidKeySpecException, NoSuchPaddingException
            , BadPaddingException, IllegalBlockSizeException {
        if (plainText == null) {
            return null;
        }
        SecureRandom random = new SecureRandom();
        DESKeySpec keySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
        return cipher.doFinal(plainText.getBytes());
    }

    /**
     * DES 对称解密
     *
     * @param cipherData
     * @param key（长度至少为8个字节）
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String decryption4DES(byte[] cipherData, String key)
            throws InvalidKeyException, NoSuchAlgorithmException
            , InvalidKeySpecException, NoSuchPaddingException
            , BadPaddingException, IllegalBlockSizeException {
        SecureRandom random = new SecureRandom();
        DESKeySpec keySpec = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(DES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, random);
        return StringUtil.bytes2String(cipher.doFinal(cipherData));
    }

    //RSA非对称加密/解密

    /**
     * 创建RSA密钥对,获取公钥/私钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, Key> generateRSAKeyPair() throws NoSuchAlgorithmException {
        Map<String, Key> keyMap = new HashMap<>();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        keyMap.put(RSA_PUBLIC_KEY, keyPair.getPublic());
        keyMap.put(RSA_PRIVATE_KEY, keyPair.getPrivate());
        return keyMap;
    }

    /**
     * 服务器数据使用私钥加密
     *
     * @param plainText
     * @param privateKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] encryption4RSA(String plainText, PrivateKey privateKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException
            , InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey, new SecureRandom());
        return cipher.doFinal(plainText.getBytes());
    }

    /**
     * 用户使用公钥解密
     *
     * @param cipherData
     * @param publicKey
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String decryption4RSA(byte[] cipherData, PublicKey publicKey)
            throws NoSuchPaddingException, NoSuchAlgorithmException
            , InvalidKeyException, BadPaddingException
            , IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, publicKey, new SecureRandom());
        return new String(cipher.doFinal(cipherData));
    }

    /**
     * 服务器根据私钥和加密数据生成数字签名
     *
     * @param cipherData
     * @param privateKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static byte[] generateRSASign(byte[] cipherData, PrivateKey privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException
            , SignatureException {
        Signature signature = Signature.getInstance(MD5withRSA);
        signature.initSign(privateKey);
        signature.update(cipherData);
        return signature.sign();
    }

    /**
     * 用户根据公钥、加密数据验证数据是否被修改过
     *
     * @param cipherData
     * @param signData
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifyRsaData(byte[] cipherData, byte[] signData, PublicKey publicKey)
            throws NoSuchAlgorithmException, InvalidKeyException
            , SignatureException {
        Signature signature = Signature.getInstance(MD5withRSA);
        signature.initVerify(publicKey);
        signature.update(cipherData);
        return signature.verify(signData);
    }
    /*
    //RSA非对称加密/解密 示例代码
    try {
        String plainText = "～｀我有一个测试数据1＝";
        Map<String, Key> keyMap = StringUtil.generateRSAKeyPair();
        PrivateKey privateKey = (PrivateKey) keyMap.get(StringUtil.RSA_PRIVATE_KEY);
        PublicKey publicKey = (PublicKey) keyMap.get(StringUtil.RSA_PUBLIC_KEY);
        //服务器私钥加密数据
        byte[] encryptionBytes = StringUtil.encryption4RSA(plainText, privateKey);
        //Log.i("TAG", StringUtil.encodeToString4Base64(encryptionBytes, Base64.DEFAULT));
        //服务器根据私钥和加密数据生成数字签名
        byte[] signBytes = StringUtil.generateRSASign(encryptionBytes, privateKey);
        //Log.i("TAG", StringUtil.encodeToString4Base64(signBytes, Base64.DEFAULT));

        //用户根据公钥、加密数据验证数据是否被修改过
        boolean isVerifySuccessful = StringUtil.verifyRsaData(encryptionBytes, signBytes, publicKey);
        //Log.i("TAG", String.valueOf(isVerifySuccessful));
        //用户公钥解密数据
        if (isVerifySuccessful) {
            String decryption = StringUtil.decryption4RSA(encryptionBytes, publicKey);
            Log.i("TAG", decryption);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }*/
    //#########加密/解密#################################################################################
}
