package com.heima.user.utils.common;

import java.util.Base64;
public class Base64Utils {

    /**
     * 解码Base64字符串为字节数组
     * @param base64 Base64编码的字符串
     * @return 解码后的字节数组，如果解码失败则返回null
     */
    public static byte[] decode(String base64) {
        try {
            return Base64.getDecoder().decode(base64);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 编码字节数组为Base64字符串
     * @param data 输入字节数组
     * @return Base64编码的字符串
     */
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }
}