package com.github.mayoi7.timer.util;

import org.junit.Test;

import java.util.UUID;

/**
 * @author Kth
 * @date 2019/6/4
 */
public class UtilTest {

    @Test
    public void testUUID() {
        System.out.println(UUID.randomUUID().toString().substring(0, 8));
    }
}
