package com.github.mayoi7.timer.utils;

import java.util.UUID;

/**
 * 随机id生成器
 * @author Kth
 * @date 2019/6/4
 */
public class IdUtil {

    /** id的长度 */
    private static final int ID_LENGTH = 8;

    public static String getRandomId() {
        return UUID.randomUUID().toString().substring(0, ID_LENGTH);
    }
}
