package com.github.mayoi7.timer.aspect;

import com.github.mayoi7.timer.cons.ResultPosition;
import com.github.mayoi7.timer.anno.Timer;
import com.github.mayoi7.timer.format.AbstractFormatter;
import com.github.mayoi7.timer.props.TimerOutputSource;
import com.github.mayoi7.timer.props.TimerProperties;
import com.github.mayoi7.timer.utils.TimerStreamOutputer;
import com.github.mayoi7.timer.utils.TimerUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 通过aop代理方法
 * @author Kth
 * @date 2019/5/30
 */
@Component
@Aspect
public class TimerAspect {

    /** 配置的格式化规则对象 */
    private final TimerProperties properties;

    public TimerAspect(TimerProperties properties) {
        this.properties = properties;
    }

    @Pointcut("@annotation(com.github.mayoi7.timer.anno.Timer)")
    private void pointcut() {}

    /**
     * 计算方法用时，并输出结果
     * @param joinPoint 连接点对象，包含连接点的信息
     * @param timer 注解对象，用于获取配置信息
     * @return 原方法返回的对象
     */
    @Around("pointcut() && @annotation(timer)")
    public Object advice(ProceedingJoinPoint joinPoint, Timer timer) {
        long startTime = System.currentTimeMillis();
        Object result = null;
        try {
             result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            // 无论如何都会计算方法的停止时间并输出结果
            long endTime = System.currentTimeMillis();

            // 时间单位
            TimeUnit unit = timer.unit();

            // 如果在注解处声明了其他时间单位，则覆盖配置文件中设置的时间单位
            if(unit != TimeUnit.MILLISECONDS) {
                properties.getTimerMode().setUnit(unit);
            } else {
                // 否则使用配置文件处的时间单位
                unit = properties.getTimerMode().getUnit();
            }

            // 时间花费
            long duration = TimerUtil.timeConversion(endTime - startTime, unit);

            Class clazz = joinPoint.getSignature().getDeclaringType();

            Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();

            // 构建数据源对象
            TimerOutputSource source
                    = new TimerOutputSource(new Date(), duration, method, clazz);

            // 构建结果输出工具对象
            TimerStreamOutputer outputer = null;

            // 格式化器的路径
            String formatterPath = null;

            /*
             * 如果timer中的配置的自定义格式化器路径不为空，或配置文件配置的格式化器路径不为空，
             * 就使用配置的自定义格式化器，
             * 否则会使用默认的格式化器，且timer配置的优先级大于配置文件中
             */
            if(!"".equals(formatterPath = timer.formatter()) ||
                    !"".equals(formatterPath =
                            properties.getTimerFormat().getFormatterPath())) {
                try {
                    Class<?> formatterClass = Class.forName(formatterPath);
                    // 获取类构造器
                    Constructor constructor = formatterClass
                            .getConstructor(TimerProperties.class, TimerOutputSource.class);

                    // 创建实例
                    AbstractFormatter formatter = (AbstractFormatter)constructor
                            .newInstance(properties, source);
                    // 通过自定义格式化器来初始化输出流工具类
                    outputer = new TimerStreamOutputer(formatter);
                } catch (ClassNotFoundException |
                        NoSuchMethodException |
                        IllegalAccessException |
                        InstantiationException |
                        InvocationTargetException e) {
                    e.printStackTrace();
                    // 如果报错，会使用默认的格式化器，但是会打印出错信息
                    outputer = new TimerStreamOutputer(properties, source);
                }
            } else {
                // 如果构造器路径为空，则默认会使用配置文件中的初始化器
                outputer = new TimerStreamOutputer(properties, source);
            }

            // 根据设定，选择不同的信息输出位置
            if(ResultPosition.CONSOLE == timer.position()) {
                outputer.output2Console();
            } else if(ResultPosition.LOG == timer.position()) {
                outputer.output2Log();
            }
        }

        return result;
    }
}
