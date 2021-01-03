package com.utils;


import org.codehaus.plexus.util.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtil {

    private static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAICFsEbgyBBoLgBtwx737DhmuYGJkfTJZPgdKXPboWpCXDyreHOZjfgTUl2C+3RhH9t9uQVsGaRGhZZ4DtnlF3AWq9TozxmU/9zlJwE0eedm+0IrCqcMDvFjbCc249ACOuUGTvKQoj4Xp1WopoDT+xGdXnFS9BysP2aSMzlGvpf3AgMBAAECgYAVyZQWj+iaj26s9+6KA3FyJkfwY8fwfWu2fAsY7vSwNMu6JT9oKXHdyji+TYQfOI/T/74r4yV9qc7fsT6jRajxQp8rxf6Q/fHHFa2oYtMbYPWByaM4C2zgjVvZNt43QpuohHtfDkR/l6MnEO9TxlgWEv7c9eJVDnWzEsEc7nNO8QJBAOCEEOikkDWN2syEwaaBESpFpIKupByjRl/OjNUrJOdvdI8yJ9DLGc6XAwZmbrbUsOjWTRYR+kdBXgCIqPOlPQsCQQCSi4eaW4B3e9i5i0vwsmo2mxmZUAlrFyY9KoQ+njZ92v1vU6SxGoVKVkNU6UbvNO5MW+a+Zbr2XzDKWJYkLexFAkBecE7ofOhIDp5dbu5CCoJc5KR5b9qKTUe53xkwRZClZSWDYlaLJhN56d2U4JtrfnXXyuTsBq3fMSjIUrO2oOfXAkBfVIdFIasjjxxAL16aWOYBWcpAUrGdybRla+gVeASIwNHG/wmO8GqvHgi1Dv/HEo7Ge65SQ4lpRilOKGybYohlAkBNHNUpb5UXK7q6Afirl0D2zW/ZBFRDXyui3hxkh6Yu6JO0lfMRmZTjmguB9H7+u2CM6rQwmu4HtSq/GuFVWfU5";
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAhbBG4MgQaC4AbcMe9+w4ZrmBiZH0yWT4HSlz26FqQlw8q3hzmY34E1Jdgvt0YR/bfbkFbBmkRoWWeA7Z5RdwFqvU6M8ZlP/c5ScBNHnnZvtCKwqnDA7xY2wnNuPQAjrlBk7ykKI+F6dVqKaA0/sRnV5xUvQcrD9mkjM5Rr6X9wIDAQAB";
    private static String A = "RSA";
    String B = "MD5withRSA";

    /**
     * Base64编码
     * @param source
     * @return
     * @throws Exception
     */
    static String encodeBase64(byte[] source) throws Exception{
        return new String(Base64.encodeBase64(source), "UTF-8");
    }

    /**
     * Base64解码
     *
     * @param target
     * @return
     * @throws Exception
     */
    static byte[] decodeBase64(String target) throws Exception {
        return Base64.decodeBase64(target.getBytes("UTF-8"));
    }

    /**
     * 获取私钥
     * @return
     * @throws Exception
     */
    static PrivateKey getPrivateKey() throws Exception {
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decodeBase64(privateKey));
        KeyFactory keyFactory = KeyFactory.getInstance(A);
        return keyFactory.generatePrivate(privateKeySpec);
    }

    /**
     * 获取公钥
     * @return
     * @throws Exception
     */
    static PublicKey getPublicKey() throws Exception {
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodeBase64(publicKey));
        KeyFactory keyFactory = KeyFactory.getInstance(A);
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * 私钥解密
     *
     * @param target
     * @throws Exception
     */
    public static String decryptionByPrivateKey(String target) throws Exception {
        Cipher cipher = Cipher.getInstance(getPrivateKey().getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        cipher.update(decodeBase64(target));
        String source = new String(cipher.doFinal(), "UTF-8");
        return source;
    }

    /**
     * 公钥加密
     * @param source
     * @return
     * @throws Exception
     */
    public static String encryptionByPublicKey(String source) throws Exception{
        PublicKey publicKey = getPublicKey();
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipher.update(source.getBytes("UTF-8"));
        String target = encodeBase64(cipher.doFinal());
        System.out.println("公钥加密后的数据：\r\n" + target);
        return target;
    }

    public static void main(String[] args) throws Exception {
        //加密
        encryptionByPublicKey("5555");
        //解密
        String target = "bJPjNGt+2KzEIohj7ZJB2KpSDhID9UFc5NL7j68KVZKhGNSVkQPnznkK2ixrgohAH0sMYJHDGaYPRBsDTa94dUYHIFJ5R25w6yuKlpHev/MJ0KxMgmylegEj8vmdN8guD4Y6UvE/59U1VL06fSsXzopu9yWWBfwV0vPVPuzDMwU=";
        String password = decryptionByPrivateKey(target);
        System.out.println(encryptionByPublicKey("123456"));
    }
}
