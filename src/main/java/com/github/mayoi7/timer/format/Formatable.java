package com.github.mayoi7.timer.format;

/**
 * 格式化接口，实现此接口的对象需要提供格式化方法<br/>
 * 例如，将一个对象按照某种格式，格式化为字符串进行输出
 * @author Kth
 * @date 2019/5/30
 */
public interface Formatable {


    /**
     * 对属性进行格式化，输出最终格式化后的结果
     * @param source 被格式化的对象
     * @return 格式化后的对象
     */
    Object format(Object source);
}
