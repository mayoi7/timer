package com.github.mayoi7.timer.cons;

import java.lang.reflect.Method;

/**
 * 方法名输出方式，目前仅有简单方法名一种方式
 * @author Kth
 * @date 2019/5/31
 */
public enum MethodDisplay {

    /**
     * 简单名称显示，仅包含方法名
     */
    SIMPLE,

    /**
     * 会在方法名后追加参数列表，
     * 格式为{@code method[param1, param2, ...]}
     */
    PARAM;

    /**
     * 方法格式化器，如果需要实现自定义的格式化规则，需要重写其中的接口方法
     */
    public static abstract class AbstractMethodFormatter {

        /**
         * 将方法对象按指定的规则格式化为字符串
         * @param method 待格式化的方法
         * @param mode 模式
         * @return 格式化后的字符串
         */
        public final String format(Method method, MethodDisplay mode) {
            switch (mode) {
                case SIMPLE:
                    return formatInSimple(method);
                case PARAM:
                    return formatInParam(method);
                default:
                    return "[illegal-method-name/params]";
            }
        }

        /**
         * 以SIMPLE模式格式化方法对象
         * @param method 方法对象
         * @return 格式化后的字符串
         */
        public abstract String formatInSimple(Method method);

        /**
         * 以PARAM模式格式化方法对象
         * @param method 方法对象
         * @return 格式化后的字符串
         */
        public abstract String formatInParam(Method method);

    }
}
