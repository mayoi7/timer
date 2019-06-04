package com.github.mayoi7.timer.format;

import com.github.mayoi7.timer.cons.ClassDisplay;
import com.github.mayoi7.timer.cons.MethodDisplay;
import com.github.mayoi7.timer.props.TimerOutputSource;
import com.github.mayoi7.timer.props.TimerProperties;
import com.github.mayoi7.timer.utils.TimerUtil;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Kth
 * @date 2019/6/1
 */
public abstract class AbstractFormatter implements TimerFormatable {

    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal;

    /** 包含了输出模式和格式，定义了输出规则 */
    private final TimerProperties properties;

    /** 待格式化的属性 */
    private TimerOutputSource source;

    /**
     * 通过配置和属性源来初始化格式化器
     * @param properties 配置项，包括输出模式和规则
     * @param source 属性源，包含所有需要输出的属性
     */
    public AbstractFormatter(TimerProperties properties, TimerOutputSource source) {
        this.properties = properties;

        this.source = source;
        dateFormatThreadLocal = ThreadLocal.withInitial(
                () -> new SimpleDateFormat(properties.getTimerFormat().getDateFormat()));
    }


    /**
     * 返回类中自定义的格式化结果
     * @return SimpleFormatterReceiver实例对象，调用output即可得到结果
     */
    @Override
    public InfoReceiver format() {
        return format(source);
    }

    @Override
    public String formatDate(Date date) {
        dateFormatThreadLocal.remove();
        return dateFormatThreadLocal.get().format(date);
    }

    @Override
    public String formatTime(long duration) {
        // TODO: 2019/5/31 根据displayMode设定的值对输出结果再进行调整
        return TimerUtil.valueOfTime(duration, properties.getTimerMode().getUnit());
    }

    @Override
    public String formatClass(Class clazz) {
        switch (properties.getTimerMode().getClassMode()) {
            case FULL:
                return clazz.getName();
            case SIMPLE:
                return clazz.getSimpleName();
            default:
                return "[illegal-class-name]";
        }
    }

    @Override
    public String formatMethod(Method method) {
        switch (properties.getTimerMode().getMethodMode()) {
            case SIMPLE:
                return method.getName();
            case PARAM:
                return method.getName() + Arrays.toString(method.getParameters());
            default:
                return "[illegal-method-name/params]";
        }
    }

    @Override
    public TimerProperties getTimerProperties() {
        return properties;
    }
}
