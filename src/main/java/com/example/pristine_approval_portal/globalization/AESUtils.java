package com.example.pristine_approval_portal.globalization;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

    private static final byte[] keyValue =
       new byte[]{'c', 'o', 'd', 'i', 'n', 'g', 'a', 'f', 'f', 'a', 'i', 'r', 's', 'c', 'o', 'm'};

 public static String encrypt(String cleartext) throws Exception{
     byte[]rawKey = getRawKey();
     byte[] result = encrypt(rawKey,cleartext.getBytes());
 return toHex(result);
 }

    private static byte[] encrypt(byte[] raw, byte[] clean) throws Exception {
     SecretKey skeySpec = new SecretKeySpec(raw,"AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(cipher.DECRYPT_MODE,skeySpec);
        byte[] encrypted = cipher.doFinal(clean);
        return encrypted;
    }
    public static String decrypt(String encrypted)
            throws Exception {

        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(enc);
        return new String(result);
    }
    private static byte[] decrypt(byte[] encrypted)
            throws Exception {
        SecretKey skeySpec = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
    private static byte[] getRawKey() throws Exception{
        SecretKey Key = new SecretKeySpec(keyValue,"AES");
        byte[] raw = Key.getEncoded();
        return raw;
    }
    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private final static String HEX = "0123456789ABCDEF";

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
}
