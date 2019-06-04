package com.github.mayoi7.timer.props;

import com.github.mayoi7.timer.utils.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 封装用于输出的属性<br/>
 * 目前仅支持 执行结束时间、执行用时、方法所在类、执行的具体方法 这四个参数
 * @author Kth
 * @date 2019/5/31
 */
@Data
@AllArgsConstructor
public class TimerOutputSource {

    /** 计时器的名称 */
    private String name;

    /** 输出结果的时间 */
    private Date date;

    /** 方法执行时间 */
    private long duration;

    /** 执行的具体方法对象 */
    private Method method;

    /** 方法所在的类 */
    private Class clazz;

    public TimerOutputSource(Date date, long duration, Method method, Class clazz) {
        this(IdUtil.getRandomId(), date, duration, method, clazz);
    }

}
