package com.github.mayoi7.timer.cons;

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
    PARAM
}
