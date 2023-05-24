package com.backend.common.utils;

import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;

public class CertificateTest {
    public void encode() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        char[] password = "123456".toCharArray();
        String alias = "www.jianggujin.com";
        String certificatePath = "test.cer";
        String keyStorePath = "test.pfx";
        byte[] data = "jianggujin".getBytes();

        KeyStore keyStore = JCertificate.getKeyStore(getClass().getResourceAsStream(keyStorePath), password,
                JCertificate.JKeyStore.JKS);
        X509Certificate certificate = (X509Certificate) JCertificate
                .getCertificate(getClass().getResourceAsStream(certificatePath));
        PrivateKey privateKey = JCertificate.getPrivateKey(keyStore, alias, password);
        PublicKey publicKey = JCertificate.getPublicKey(certificate);

        System.out.println("是否有效：" + JCertificate.verifyCertificate(certificate));
        System.out.println("使用者：" + certificate.getSubjectDN().getName());
        System.out.println("版本：" + certificate.getVersion());
        System.out.println("序列号：" + certificate.getSerialNumber().toString(16));
        System.out.println("签名算法：" + certificate.getSigAlgName());
        System.out.println("证书类型：" + certificate.getType());
        System.out.println("颁发者：" + certificate.getIssuerDN().getName());
        System.out.println(
                "有效期：" + format.format(certificate.getNotBefore()) + "到" + format.format(certificate.getNotAfter()));

        byte[] signResult = JCertificate.sign(data, keyStore, alias, password);
        //System.out.println("签名：" + JBase64.getEncoder().encodeToString(signResult, "UTF-8"));
        System.out.println("签名：" + Base64.getEncoder().encodeToString(signResult));
        System.out.println("证书验签：" + JCertificate.verify(data, signResult, certificate));
        System.out.println("密钥库验签：" + JCertificate.verify(data, signResult, keyStore));

        byte[] result = JCertificate.encrypt(data, privateKey);
        //System.out.println("私钥加密：" + JBase64.getEncoder().encodeToString(result, "UTF-8"));
        System.out.println("私钥加密：" + Base64.getEncoder().encodeToString(result));
        System.out.println("公钥解密：" + new String(JCertificate.decrypt(result, publicKey)));

        result = JCertificate.encrypt(data, publicKey);
        //System.out.println("公钥加密：" + JBase64.getEncoder().encodeToString(result, "UTF-8"));
        System.out.println("公钥加密：" + Base64.getEncoder().encodeToString(result));
        System.out.println("私钥解密：" + new String(JCertificate.decrypt(result, privateKey)));
    }

    public void convert() throws Exception {
        char[] password = "123456".toCharArray();
        String keyStorePath = "test.keystore";

        KeyStore keyStore = JCertificate.getKeyStore(getClass().getResourceAsStream(keyStorePath), password,
                JCertificate.JKeyStore.JKS);
        KeyStore target = JCertificate.convert(keyStore, JCertificate.JKeyStore.PKCS12, password);
        for (String alias : JCertificate.listAlias(target)) {
            System.out.println(alias);
        }
        target.store(new FileOutputStream("test.pfx"), password);
    }
}
