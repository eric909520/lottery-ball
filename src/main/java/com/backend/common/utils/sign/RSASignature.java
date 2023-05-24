package com.backend.common.utils.sign;

import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA签名验签类
 */
@Slf4j
public class RSASignature {

    public static String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALGOgi7+l7nHouIWD6idTIFxBy6nZmAhT7ca2uHHGvqpAs1Vi4rbBPssPy3BQ9+SHRMkGFsJ9CE9bLj0j4pJOl9SGoACVfNX7TtzZ3WMccFjY545nuav4v5hh0rwuFnZzsvlDgwMru6JMAcBZVn14kvEQ72oa6fZ2rgH6yw1k4BPAgMBAAECgYAZh4MCXMrWBHVQCpn/xXYtmmHM/HbE1aVPj4XvNqCTb9+eRHGG6ozhzG/dFU+8Apc5nFQAKwM2EE3kng1Rdcv1kVdLwfhwap4HPvS4xboTaQQ4x+Rzu6/kQPLavcoDlRFNK9u/26aqt1XDC9fAuJD2RWcZylDwGpoiGzV4zTBz0QJBANlstAEozTjXZcM5NlPvTxW9Z9eauI+w3dNErJcAUgyaLQConZCbkNbj4ec42DGb0rKVE0MVadmts5srq0KlIMkCQQDRDwbliqYwFIHZeeqABMry1oyOKETVhbkv/F8kJqyELqobqD6M2def3WZJuPmfEHfdIU64nOTEiV4mZUC09HxXAkAtqPFoaOwoKrv5MpOhLtl55EvhcGGf83GyQ3LHR07zzJD02XQ1gSX6quKvDzX/vQyTalEgPmf7Sco1JQeTOGBZAkBFR2uLqdTE3ibhwOsbHXt72emeOm4FLFlgM4v72O0WqLgqjiicWJyCF8aL68adFcIC/8SsnSGal8MhijBVRyflAkBVfcMrF/Wnfox57QznnIVrXISi2TLtO1EBFQiAZlft843SSCcoqJaM4VFV0YaTpNTUQHXgxnpQBH06Z4eQQKV3";

    public static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCxjoIu/pe5x6LiFg+onUyBcQcup2ZgIU+3Gtrhxxr6qQLNVYuK2wT7LD8twUPfkh0TJBhbCfQhPWy49I+KSTpfUhqAAlXzV+07c2d1jHHBY2OeOZ7mr+L+YYdK8LhZ2c7L5Q4MDK7uiTAHAWVZ9eJLxEO9qGun2dq4B+ssNZOATwIDAQAB";

    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA256WithRSA";

    /*public static void main(String[] args) {
        String sign = sign("s4HGrK9vo8sXZAdqlMz/Jz/sCNTFuxHn/wYaWXVomr0=", privateKey);
        System.out.println(sign);
        boolean check = doCheck("s4HGrK9vo8sXZAdqlMz/Jz/sCNTFuxHn/wYaWXVomr0=", "ndbSB314FY7gsx57hdkOtdPx6aGOdGS0sSwNaTjoP5QWGx3GkE6rf858Q+qJWj1bVIoVYw3yGCzNn1w1sfrwePyBovPL/qCn/kDVybwgI58VsTjE1vx7bklSYcdyP5RzsKf/JYbHkZONhrBPBG5Kv4Ac18WH206S0ieCv6VFASU=", publicKey);
        System.out.println(check);
    }*/

    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @param encode     字符集编码
     * @return 签名值
     */
    public static String sign(String content, String privateKey, String encode) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(encode));
            byte[] signed = signature.sign();
            return new BASE64Encoder().encode(signed);
        } catch (Exception e) {
            Sentry.captureException(e);
            log.info("签名失败----->>>>" + e.getMessage());
        }

        return null;
    }

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(privateKey));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes());
            byte[] signed = signature.sign();
            return new BASE64Encoder().encode(signed);
        } catch (Exception e) {
            Sentry.captureException(e);
            log.info("签名失败----->>>>" + e.getMessage());
        }
        return null;
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @param encode    字符集编码
     * @return 布尔值
     */
    public static boolean doCheck(String content, String sign, String publicKey, String encode) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = new BASE64Decoder().decodeBuffer(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(encode));

            boolean bverify = signature.verify(new BASE64Decoder().decodeBuffer(sign));
            return bverify;
        } catch (Exception e) {
            Sentry.captureException(e);
            log.info("验签失败----->>>>" + e.getMessage());
        }
        return false;
    }

    public static boolean doCheck(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = new BASE64Decoder().decodeBuffer(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes());

            boolean bverify = signature.verify(new BASE64Decoder().decodeBuffer(sign));
            return bverify;
        } catch (Exception e) {
            Sentry.captureException(e);
            log.info("验签失败----->>>>" + e.getMessage());
        }
        return false;
    }

}
