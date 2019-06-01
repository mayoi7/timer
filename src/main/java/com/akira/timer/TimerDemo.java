package com.akira.timer;

import com.akira.timer.anno.Timer;
import com.akira.timer.cons.ResultPosition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 包含各种用于测试的方法
 * @author Kth
 * @date 2019/5/30
 */
@Component
public class TimerDemo {

    public static final int BIG_TEST_TIMES = 1000000000;

    public static final int MID_BIG_TEST_TIMES = 10000000;

    public static final int MEDIUM_TEST_TIMES = 1000000;

    public static final int SMALL_TEST_TIMES = 10000;

    @Timer(position = ResultPosition.LOG, unit = TimeUnit.SECONDS)
    public void longTimeMethod() {
        System.out.println("longTimeMethod begin");
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < MID_BIG_TEST_TIMES; i++) {
            list.add(i);
        }
        System.out.println("longTimeMethod over");
    }
}
