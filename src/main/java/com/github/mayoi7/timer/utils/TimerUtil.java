package com.github.mayoi7.timer.utils;


import java.util.concurrent.TimeUnit;

/**
 * 用于输出结果的工具类
 * @author Kth
 * @date 2019/5/30
 */
public class TimerUtil {

    /** 系统默认时间单位，毫秒 */
    private static final TimeUnit SYSTEM_TIME_UNIT = TimeUnit.MILLISECONDS;

    /**
     * 对进制时间进行转换
     * @param duration 时间数值
     * @param unit 时间单位，TimeUnit对象
     * @return 转换后的时间数值，单位为unit
     */
    public static long timeConversion(long duration, TimeUnit unit) {

        switch (unit) {
            case NANOSECONDS:
                return SYSTEM_TIME_UNIT.toNanos(duration);
            case MICROSECONDS:
                return SYSTEM_TIME_UNIT.toMicros(duration);
            case MILLISECONDS:
                return SYSTEM_TIME_UNIT.toMillis(duration);
            case SECONDS:
                return SYSTEM_TIME_UNIT.toSeconds(duration);
            case MINUTES:
                return SYSTEM_TIME_UNIT.toMinutes(duration);
            case HOURS:
                return SYSTEM_TIME_UNIT.toHours(duration);
            case DAYS:
                return SYSTEM_TIME_UNIT.toDays(duration);
            default:
                return duration;
        }
    }

    /**
     * 将时间数值转换为字符串输出
     * @param duration 时间数值
     * @param unit 时间单位
     * @return 输出的字符串结果，形如"200 ms"
     */
    public static String valueOfTime(long duration, TimeUnit unit) {
        String time = "" + duration;

        switch (unit) {
            case NANOSECONDS:
                return time + " ns";
            case MICROSECONDS:
                return time + " us";
            case MILLISECONDS:
                return time + " ms";
            case SECONDS:
                return time + " s";
            case MINUTES:
                return time + " min";
            case HOURS:
                return time + " hour";
            case DAYS:
                return time + " day";
            default:
                return time;
        }
    }
}
