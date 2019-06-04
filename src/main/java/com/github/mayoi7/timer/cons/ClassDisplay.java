package com.github.mayoi7.timer.cons;

import com.github.mayoi7.timer.exception.TimerException;
import com.github.mayoi7.timer.format.AbstractFormatter;

/**
 * 输出类显示的格式，有全路径和简单名称两种格式
 * @author Kth
 * @date 2019/5/31
 */
public enum ClassDisplay {

    /**
     * 显示全路径，类似com.akira.timer.xxx
     */
    FULL,

    /**
     * 显示简单名称，即仅显示类名
     */
    SIMPLE;

    /**
     * 类格式化器，如果需要实现自定义的格式化规则，需要重写其中的接口方法
     */
    public static abstract class AbstractClassFormatter {

        /**
         * 将类对象按指定的规则格式化为字符串
         * @param clazz 待格式化的类
         * @param mode 模式
         * @return 格式化后的字符串
         */
        public final String format(Class clazz, ClassDisplay mode) {
            switch (mode) {
                case SIMPLE:
                    return formatInSimple(clazz);
                case FULL:
                    return formatInFull(clazz);
                default:
                    return "[illegal-class-name]";
            }
        }

        /**
         * 以SIMPLE模式格式化类对象
         * @param clazz 类对象
         * @return 格式化后的字符串
         */
        public abstract String formatInSimple(Class clazz);

        /**
         * 以FULL模式格式化类对象
         * @param clazz 类对象
         * @return 格式化后的字符串
         */
        public abstract String formatInFull(Class clazz);

    }
}
