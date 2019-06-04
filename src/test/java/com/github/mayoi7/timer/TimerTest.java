package com.github.mayoi7.timer;

import com.github.mayoi7.timer.demo.TimerDemo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

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
        demo.longTimeMethod(5);
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().substring(0, 8));
    }

}
