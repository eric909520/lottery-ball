package com.backend.common.utils;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 加解密工具
 *
 * @author jianggujin
 *
 */

public class JCodecUtils {
    /**
     * 获得私钥
     *
     * @param privateKey
     * @param algorithm
     * @return
     */
    public static PrivateKey getPrivateKey(byte[] privateKey, String algorithm) {
        try {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得公钥
     *
     * @param publicKey
     * @param algorithm
     * @return
     */
    public static PublicKey getPublicKey(byte[] publicKey, String algorithm) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 检查加解密操作模式
     *
     * @param opmode
     */
    public static void checkOpMode(int opmode) {
        if (opmode != Cipher.ENCRYPT_MODE && opmode != Cipher.DECRYPT_MODE)
            throw new IllegalArgumentException("opmode invalid");
    }

    /**
     * 签名
     *
     * @param data
     * @param privateKey
     * @param signatureAlgorithm
     * @return
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey, String signatureAlgorithm) {
        try {
            Signature signature = Signature.getInstance(signatureAlgorithm);
            signature.initSign(privateKey);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验签
     *
     * @param data
     * @param sign
     * @param publicKey
     * @param signatureAlgorithm
     * @return
     */
    public static boolean verify(byte[] data, byte[] sign, PublicKey publicKey, String signatureAlgorithm) {
        try {
            Signature signature = Signature.getInstance(signatureAlgorithm);
            signature.initVerify(publicKey);
            signature.update(data);

            return signature.verify(sign);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按单部分操作加密或解密数据，或者结束一个多部分操作
     *
     * @param data
     * @param cipher
     * @return
     */
    public static byte[] doFinal(byte[] data, Cipher cipher) {
        try {
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化密钥
     *
     * @param algorithm
     * @param keySize
     * @return
     */
    public static KeyPair initKey(String algorithm, int keySize) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
            keyPairGen.initialize(keySize);
            return keyPairGen.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化密钥
     *
     * @param algorithm
     * @return
     */
    public static SecretKey initKey(String algorithm) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
