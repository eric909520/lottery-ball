package com.backend.common.utils;

import io.sentry.Sentry;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.util.*;



public class JCertificate {

    public final static String X509 = "X.509";

    /**
     * 密钥库枚举
     *
     * @author jianggujin
     *
     */
    public static enum JKeyStore {

        JCEKS, JKS, DKS, PKCS11, PKCS12;

        public String getName() {
            return this.name();
        }
    }

    /**
     * 获得{@link KeyStore}
     *
     * @param in
     * @param password
     * @param keyStore
     * @return
     */
    public static KeyStore getKeyStore(InputStream in, char[] password, JKeyStore keyStore) {
        return getKeyStore(in, password, keyStore.getName());
    }

    /**
     * 获得{@link KeyStore}
     *
     * @param in
     * @param password
     * @param keyStore
     * @return
     * @throws Exception
     */
    public static KeyStore getKeyStore(InputStream in, char[] password, String keyStore) {
        try {
            KeyStore ks = KeyStore.getInstance(keyStore);
            ks.load(in, password);
            return ks;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 列出别名
     *
     * @param keyStore
     * @return
     */
    public static List<String> listAlias(KeyStore keyStore) {
        try {
            Enumeration<String> aliasEnum = keyStore.aliases();
            List<String> aliases = new ArrayList<String>();
            while (aliasEnum.hasMoreElements()) {
                aliases.add(aliasEnum.nextElement());
            }
            return aliases;
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得私钥
     *
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias, char[] password) {
        try {
            PrivateKey key = (PrivateKey) keyStore.getKey(alias, password);
            return key;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得私钥
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static PrivateKey getPrivateKey(InputStream in, String alias, char[] password, JKeyStore keyStore) {
        return getPrivateKey(in, alias, password, keyStore.getName());
    }

    /**
     * 获得私钥
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(InputStream in, String alias, char[] password, String keyStore) {
        try {
            KeyStore ks = getKeyStore(in, password, keyStore);
            PrivateKey key = (PrivateKey) ks.getKey(alias, password);
            return key;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得{@link Certificate}
     *
     * @param in
     * @return
     */
    public static Certificate getCertificate(InputStream in) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
            Certificate certificate = certificateFactory.generateCertificate(in);
            return certificate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得{@link Certificate}
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static Certificate getCertificate(InputStream in, String alias, char[] password, JKeyStore keyStore) {
        return getCertificate(in, alias, password, keyStore.getName());
    }

    /**
     * 获得{@link Certificate}
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static Certificate getCertificate(InputStream in, String alias, char[] password, String keyStore) {
        KeyStore ks = getKeyStore(in, password, keyStore);
        return getCertificate(ks, alias);
    }

    /**
     * 获得{@link Certificate}
     *
     * @param keyStore
     * @param alias
     * @return
     */
    public static Certificate getCertificate(KeyStore keyStore, String alias) {
        try {
            Certificate certificate = keyStore.getCertificate(alias);
            return certificate;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 获得证书链
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static Certificate[] getCertificateChain(InputStream in, String alias, char[] password, JKeyStore keyStore) {
        return getCertificateChain(in, alias, password, keyStore.getName());
    }

    /**
     * 获得证书链
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static Certificate[] getCertificateChain(InputStream in, String alias, char[] password, String keyStore) {
        KeyStore ks = getKeyStore(in, password, keyStore);
        return getCertificateChain(ks, alias);
    }

    /**
     * 获得证书链
     *
     * @param keyStore
     * @param alias
     * @return
     */
    public static Certificate[] getCertificateChain(KeyStore keyStore, String alias) {
        try {
            Certificate[] certificateChain = keyStore.getCertificateChain(alias);
            return certificateChain;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得公钥
     *
     * @param certificate
     * @return
     */
    public static PublicKey getPublicKey(Certificate certificate) {
        PublicKey key = certificate.getPublicKey();
        return key;
    }

    /**
     * 获得公钥
     *
     * @param in
     * @return
     */
    public static PublicKey getPublicKey(InputStream in) {
        Certificate certificate = getCertificate(in);
        return getPublicKey(certificate);
    }

    /**
     * 获得公钥
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static PublicKey getPublicKey(InputStream in, String alias, char[] password, JKeyStore keyStore) {
        return getPublicKey(in, alias, password, keyStore.getName());
    }

    /**
     * 获得公钥
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static PublicKey getPublicKey(InputStream in, String alias, char[] password, String keyStore) {
        Certificate certificate = getCertificate(in, alias, password, keyStore);
        return getPublicKey(certificate);
    }

    /**
     * 获得公钥
     *
     * @param keyStore
     * @param alias
     * @return
     */
    public static PublicKey getPublicKey(KeyStore keyStore, String alias) {
        Certificate certificate = getCertificate(keyStore, alias);
        return getPublicKey(certificate);
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param date
     * @param certificate
     * @return
     */
    public static boolean verifyCertificate(Date date, Certificate certificate) {
        X509Certificate x509Certificate = (X509Certificate) certificate;
        try {
            x509Certificate.checkValidity(date);
            return true;
        } catch (CertificateExpiredException e1) {
            Sentry.captureException(e1);
            return false;
        } catch (CertificateNotYetValidException e1) {
            Sentry.captureException(e1);
            return false;
        }
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param certificate
     * @return
     */
    public static boolean verifyCertificate(Certificate certificate) {
        return verifyCertificate(new Date(), certificate);
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param in
     * @return
     */
    public static boolean verifyCertificate(InputStream in) {
        Certificate certificate = getCertificate(in);
        return verifyCertificate(certificate);
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param date
     * @param in
     * @return
     */
    public static boolean verifyCertificate(Date date, InputStream in) {
        Certificate certificate = getCertificate(in);
        return verifyCertificate(date, certificate);
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static boolean verifyCertificate(InputStream in, String alias, char[] password, JKeyStore keyStore) {
        return verifyCertificate(in, alias, password, keyStore.getName());
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static boolean verifyCertificate(InputStream in, String alias, char[] password, String keyStore) {
        Certificate certificate = getCertificate(in, alias, password, keyStore);
        return verifyCertificate(certificate);
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param date
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static boolean verifyCertificate(Date date, InputStream in, String alias, char[] password,
                                            JKeyStore keyStore) {
        return verifyCertificate(date, in, alias, password, keyStore.getName());
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param date
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static boolean verifyCertificate(Date date, InputStream in, String alias, char[] password, String keyStore) {
        Certificate certificate = getCertificate(in, alias, password, keyStore);
        return verifyCertificate(date, certificate);
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param keyStore
     * @param alias
     * @return
     */
    public static boolean verifyCertificate(KeyStore keyStore, String alias) {
        Certificate certificate = getCertificate(keyStore, alias);
        return verifyCertificate(certificate);
    }

    /**
     * 验证{@link Certificate}是否过期或无效
     *
     * @param date
     * @param keyStore
     * @param alias
     * @return
     */
    public static boolean verifyCertificate(Date date, KeyStore keyStore, String alias) {
        Certificate certificate = getCertificate(keyStore, alias);
        return verifyCertificate(date, certificate);
    }

    /**
     * 签名
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] sign(byte[] data, InputStream in, String alias, char[] password, JKeyStore keyStore) {
        return sign(data, in, alias, password, keyStore.getName());
    }

    /**
     * 签名
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] sign(byte[] data, InputStream in, String alias, char[] password, String keyStore) {
        // 获得证书
        Certificate certificate = getCertificate(in, alias, password, keyStore);
        // 取得私钥
        PrivateKey privateKey = getPrivateKey(in, alias, password, keyStore);
        return sign(data, certificate, privateKey);
    }

    /**
     * 签名
     *
     * @param data
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static byte[] sign(byte[] data, KeyStore keyStore, String alias, char[] password) {
        // 获得证书
        Certificate certificate = getCertificate(keyStore, alias);
        // 取得私钥
        PrivateKey privateKey = getPrivateKey(keyStore, alias, password);
        return sign(data, certificate, privateKey);
    }

    /**
     * 签名
     *
     * @param data
     * @param certificate
     * @param privateKey
     * @return
     */
    public static byte[] sign(byte[] data, Certificate certificate, PrivateKey privateKey) {
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate) certificate;
        return sign(data, privateKey, x509Certificate.getSigAlgName());
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
        return JCodecUtils.sign(data, privateKey, signatureAlgorithm);
    }

    /**
     * 验签
     *
     * @param data
     * @param sign
     * @param in
     * @return
     */
    public static boolean verify(byte[] data, byte[] sign, InputStream in) {
        // 获得证书
        Certificate certificate = getCertificate(in);

        return verify(data, sign, certificate);
    }

    /**
     * 验签
     *
     * @param data
     * @param sign
     * @param certificate
     * @return
     */
    public static boolean verify(byte[] data, byte[] sign, Certificate certificate) {
        // 获得证书
        X509Certificate x509Certificate = (X509Certificate) certificate;
        // 获得公钥
        PublicKey publicKey = x509Certificate.getPublicKey();
        return verify(data, sign, publicKey, x509Certificate.getSigAlgName());
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
        return JCodecUtils.verify(data, sign, publicKey, signatureAlgorithm);
    }

    /**
     * 验签
     *
     * @param data
     * @param sign
     * @param keyStore
     * @param alias
     * @return
     */
    public static boolean verify(byte[] data, byte[] sign, KeyStore keyStore, String alias) {
        Certificate certificate = getCertificate(keyStore, alias);
        return verify(data, sign, certificate);
    }

    /**
     * 验签，遍历密钥库中的所有公钥
     *
     * @param data
     * @param sign
     * @param keyStore
     * @return
     */
    public static boolean verify(byte[] data, byte[] sign, KeyStore keyStore) {
        try {
            Enumeration<String> aliasEnum = keyStore.aliases();
            while (aliasEnum.hasMoreElements()) {
                if (verify(data, sign, keyStore.getCertificate(aliasEnum.nextElement())))
                    return true;
            }
        } catch (KeyStoreException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] encryptByPrivate(byte[] data, InputStream in, String alias, char[] password,
                                          JKeyStore keyStore) {
        return encryptByPrivate(data, in, alias, password, keyStore.getName());
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] encryptByPrivate(byte[] data, InputStream in, String alias, char[] password, String keyStore) {
        PrivateKey privateKey = getPrivateKey(in, alias, password, keyStore);
        return encrypt(data, privateKey);
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static byte[] encryptByPrivate(byte[] data, KeyStore keyStore, String alias, char[] password) {
        PrivateKey privateKey = getPrivateKey(keyStore, alias, password);
        return encrypt(data, privateKey);
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     */
    public static byte[] encrypt(byte[] data, PrivateKey privateKey) {
        Cipher cipher = getCipher(privateKey, Cipher.ENCRYPT_MODE);
        return JCodecUtils.doFinal(data, cipher);
    }

    public static OutputStream wrapByPrivate(OutputStream out, InputStream in, String alias, char[] password,
                                             JKeyStore keyStore) {
        return wrapByPrivate(out, in, alias, password, keyStore.getName());
    }

    public static OutputStream wrapByPrivate(OutputStream out, InputStream in, String alias, char[] password,
                                             String keyStore) {
        PrivateKey privateKey = getPrivateKey(in, alias, password, keyStore);
        return wrap(out, privateKey);
    }

    public static OutputStream wrapByPrivate(OutputStream out, KeyStore keyStore, String alias, char[] password) {
        PrivateKey privateKey = getPrivateKey(keyStore, alias, password);
        return wrap(out, privateKey);
    }

    public static OutputStream wrap(OutputStream out, PrivateKey privateKey) {
        Cipher cipher = getCipher(privateKey, Cipher.ENCRYPT_MODE);
        return new CipherOutputStream(out, cipher);
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param certificate
     * @return
     */
    public static byte[] encrypt(byte[] data, Certificate certificate) {
        PublicKey publicKey = certificate.getPublicKey();
        return encrypt(data, publicKey);
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param in
     * @return
     */
    public static byte[] encryptByPublic(byte[] data, InputStream in) {
        PublicKey publicKey = getPublicKey(in);
        return encrypt(data, publicKey);
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] encryptByPublic(byte[] data, InputStream in, String alias, char[] password,
                                         JKeyStore keyStore) {
        return encryptByPublic(data, in, alias, password, keyStore.getName());
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] encryptByPublic(byte[] data, InputStream in, String alias, char[] password, String keyStore) {
        PublicKey publicKey = getPublicKey(in, alias, password, keyStore);
        return encrypt(data, publicKey);
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param keyStore
     * @param alias
     * @return
     */
    public static byte[] encryptByPublic(byte[] data, KeyStore keyStore, String alias) {
        PublicKey publicKey = getPublicKey(keyStore, alias);
        return encrypt(data, publicKey);
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static byte[] encrypt(byte[] data, PublicKey publicKey) {
        Cipher cipher = getCipher(publicKey, Cipher.ENCRYPT_MODE);
        return JCodecUtils.doFinal(data, cipher);
    }

    public static OutputStream wrap(OutputStream out, Certificate certificate) {
        PublicKey publicKey = certificate.getPublicKey();
        return wrap(out, publicKey);
    }

    public static OutputStream wrapByPublic(OutputStream out, InputStream in) {
        PublicKey publicKey = getPublicKey(in);
        return wrap(out, publicKey);
    }

    public static OutputStream wrapByPublic(OutputStream out, InputStream in, String alias, char[] password,
                                            JKeyStore keyStore) {
        return wrapByPublic(out, in, alias, password, keyStore.getName());
    }

    public static OutputStream wrapByPublic(OutputStream out, InputStream in, String alias, char[] password,
                                            String keyStore) {
        PublicKey publicKey = getPublicKey(in, alias, password, keyStore);
        return wrap(out, publicKey);
    }

    public static OutputStream wrapByPublic(OutputStream out, KeyStore keyStore, String alias) {
        PublicKey publicKey = getPublicKey(keyStore, alias);
        return wrap(out, publicKey);
    }

    public static OutputStream wrap(OutputStream out, PublicKey publicKey) {
        Cipher cipher = getCipher(publicKey, Cipher.ENCRYPT_MODE);
        //
        return new CipherOutputStream(out, cipher);
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] decryptByPrivate(byte[] data, InputStream in, String alias, char[] password,
                                          JKeyStore keyStore) {
        return decryptByPrivate(data, in, alias, password, keyStore.getName());
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] decryptByPrivate(byte[] data, InputStream in, String alias, char[] password, String keyStore) {
        PrivateKey privateKey = getPrivateKey(in, alias, password, keyStore);
        return decrypt(data, privateKey);
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static byte[] decryptByPrivate(byte[] data, KeyStore keyStore, String alias, char[] password) {
        // 取得私钥
        PrivateKey privateKey = getPrivateKey(keyStore, alias, password);
        return decrypt(data, privateKey);
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */
    public static byte[] decrypt(byte[] data, PrivateKey privateKey) {
        Cipher cipher = getCipher(privateKey, Cipher.DECRYPT_MODE);
        return JCodecUtils.doFinal(data, cipher);
    }

    public static InputStream wrapByPrivate(InputStream sIn, InputStream in, String alias, char[] password,
                                            JKeyStore keyStore) {
        return wrapByPrivate(sIn, in, alias, password, keyStore.getName());
    }

    public static InputStream wrapByPrivate(InputStream sIn, InputStream in, String alias, char[] password,
                                            String keyStore) {
        PrivateKey privateKey = getPrivateKey(in, alias, password, keyStore);
        return wrap(sIn, privateKey);
    }

    public static InputStream wrapByPrivate(InputStream sIn, KeyStore keyStore, String alias, char[] password) {
        PrivateKey privateKey = getPrivateKey(keyStore, alias, password);
        return wrap(sIn, privateKey);
    }

    public static InputStream wrap(InputStream sIn, PrivateKey privateKey) {
        Cipher cipher = getCipher(privateKey, Cipher.ENCRYPT_MODE);
        return new CipherInputStream(sIn, cipher);
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param certificate
     * @return
     */
    public static byte[] decrypt(byte[] data, Certificate certificate) {
        PublicKey publicKey = certificate.getPublicKey();
        return decrypt(data, publicKey);
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param in
     * @return
     */
    public static byte[] decryptByPublic(byte[] data, InputStream in) {
        PublicKey publicKey = getPublicKey(in);
        return decrypt(data, publicKey);
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] decryptByPublic(byte[] data, InputStream in, String alias, char[] password,
                                         JKeyStore keyStore) {
        return decryptByPublic(data, in, alias, password, keyStore.getName());
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param in
     * @param alias
     * @param password
     * @param keyStore
     * @return
     */
    public static byte[] decryptByPublic(byte[] data, InputStream in, String alias, char[] password, String keyStore) {
        PublicKey publicKey = getPublicKey(in, alias, password, keyStore);
        return decrypt(data, publicKey);
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param keyStore
     * @param alias
     * @return
     */
    public static byte[] decryptByPublic(byte[] data, KeyStore keyStore, String alias) {
        PublicKey publicKey = getPublicKey(keyStore, alias);
        return decrypt(data, publicKey);
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static byte[] decrypt(byte[] data, PublicKey publicKey) {
        Cipher cipher = getCipher(publicKey, Cipher.DECRYPT_MODE);
        return JCodecUtils.doFinal(data, cipher);
    }

    public static InputStream wrap(InputStream sIn, Certificate certificate) {
        PublicKey publicKey = certificate.getPublicKey();
        return wrap(sIn, publicKey);
    }

    public static InputStream wrapByPublic(InputStream sIn, InputStream in) {
        PublicKey publicKey = getPublicKey(in);
        return wrap(sIn, publicKey);
    }

    public static InputStream wrapByPublic(InputStream sIn, InputStream in, String alias, char[] password,
                                           JKeyStore keyStore) {
        return wrapByPublic(sIn, in, alias, password, keyStore.getName());
    }

    public static InputStream wrapByPublic(InputStream sIn, InputStream in, String alias, char[] password,
                                           String keyStore) {
        PublicKey publicKey = getPublicKey(in, alias, password, keyStore);
        return wrap(sIn, publicKey);
    }

    public static InputStream wrapByPublic(InputStream sIn, KeyStore keyStore, String alias) {
        PublicKey publicKey = getPublicKey(keyStore, alias);
        return wrap(sIn, publicKey);
    }

    public static InputStream wrap(InputStream sIn, PublicKey publicKey) {
        Cipher cipher = getCipher(publicKey, Cipher.DECRYPT_MODE);
        return new CipherInputStream(sIn, cipher);
    }

    public static Cipher getCipher(Key key, int opmode) {
        JCodecUtils.checkOpMode(opmode);
        try {
            Cipher cipher = Cipher.getInstance(key.getAlgorithm());
            cipher.init(opmode, key);
            return cipher;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 导出公钥证书
     *
     * @param out
     * @param certificate
     * @param rfc
     */
    public static void export(OutputStream out, Certificate certificate, boolean rfc) {
        try {
            byte[] encoded = certificate.getEncoded();
            if (rfc) {
                out.write("-----BEGIN CERTIFICATE-----\r\n".getBytes());
                out.write(Base64.getMimeEncoder().encode(encoded));
                out.write("\r\n-----END CERTIFICATE-----\r\n".getBytes());
            } else out.write(encoded);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将密钥库转换为指定类型的密钥库
     *
     * @param srcKeyStore
     * @param target
     * @param password
     * @param alias
     *           导出指定别名的证书
     * @return
     */
    public static KeyStore convert(KeyStore srcKeyStore, JKeyStore target, char[] password, String... alias) {
        return convert(srcKeyStore, target.getName(), password, alias);
    }

    /**
     * 将密钥库转换为指定类型的密钥库
     *
     * @param srcKeyStore
     * @param target
     * @param password
     * @param alias
     *           导出指定别名的证书
     * @return
     */
    public static KeyStore convert(KeyStore srcKeyStore, String target, char[] password, String... alias) {
        try {
            KeyStore outputKeyStore = KeyStore.getInstance(target);
            outputKeyStore.load(null, password);
            if (alias.length == 0) {
                Enumeration<String> enums = srcKeyStore.aliases();
                while (enums.hasMoreElements()) {
                    String keyAlias = enums.nextElement();
                    copyKeyEntry(srcKeyStore, outputKeyStore, keyAlias, password);
                }
            } else {
                for (String keyAlias : alias) {
                    copyKeyEntry(srcKeyStore, outputKeyStore, keyAlias, password);
                }
            }
            return outputKeyStore;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 复制
     *
     * @param src
     * @param target
     * @param alias
     * @param password
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     */
    public static void copyKeyEntry(KeyStore src, KeyStore target, String alias, char[] password)
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException {
        if (src.isKeyEntry(alias)) {
            Key key = src.getKey(alias, password);
            Certificate[] certChain = src.getCertificateChain(alias);
            target.setKeyEntry(alias, key, password, certChain);
        }
    }

}
