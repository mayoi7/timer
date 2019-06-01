package com.akira.timer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Kth
 * @date 2019/5/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TimerTest {

    @Autowired
    TimerDemo demo;

    @Test
    public void longTimeMethodTest() {
        demo.longTimeMethod();
    }

    public static void main(String[] args) {

    }

}
