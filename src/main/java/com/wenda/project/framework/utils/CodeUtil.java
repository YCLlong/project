package com.wenda.project.framework.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

public class CodeUtil {
    /**
     * 刷新随机码
     */
    public static final String REFRESH_CODES = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String genId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static SecureRandom rand = new SecureRandom();


    /**
     * 使用指定源生成验证码
     *
     * @param size    验证码长度
     * @param sources 验证码字符源
     * @return
     */
    public static String generateCode(int size, String sources) {
        if (sources == null || sources.length() == 0) {
            sources = REFRESH_CODES;
        }
        int codesLen = sources.length();
        StringBuffer verifyCode = new StringBuffer(size);
        try {
            for (int i = 0; i < size; i++) {
                verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
            }
        } catch (Exception e) {
            Random rand = new Random();
            for (int i = 0; i < size; i++) {
                verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
            }
        }
        return verifyCode.toString();
    }
}
