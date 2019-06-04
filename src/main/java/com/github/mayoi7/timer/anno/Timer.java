package com.github.mayoi7.timer.anno;

import com.github.mayoi7.timer.cons.ResultPosition;
import com.github.mayoi7.timer.utils.IdUtil;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * 计时器注解<br/>
 * 在方法上使用，当方法运行完毕后可以直接输出结果
 * @author Kth
 * @date 2019/5/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Timer {

    /**
     * 输出的方式，通过指定的匹配规则
     * @return 指定的匹配规则
     */
    String value() default "";

    /**
     * 计时器的名称，可以不设置，默认生成一个随机id
     * @return 计时器名称
     */
    String name() default "";

    /**
     * 时间单位
     * @return TimeUnit对象，默认为毫秒
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

    /**
     * 设置自定义的格式化器路径
     * @return 格式化器类的地址，默认为空字符串，当为空字符串时，输出工具类会自动选择默认的格式化器
     */
    String formatter() default "";

    /**
     * 结果输出的位置
     * @return 运行时间输出的位置，默认为控制台
     */
    ResultPosition position() default ResultPosition.CONSOLE;
}
