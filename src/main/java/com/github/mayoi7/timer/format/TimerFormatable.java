package com.github.mayoi7.timer.format;

import com.github.mayoi7.timer.props.TimerOutputSource;
import com.github.mayoi7.timer.props.TimerProperties;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 专门负责将DisplayProperty的对象格式化为字符串进行输出
 * @author Kth
 * @date 2019/5/31
 */
public interface TimerFormatable extends Formatable {

    /**
     * 用于接收格式化后属性的对象
     */
    @Data
    class InfoReceiver {
        /** 日期 */
        protected String date;

        /** 执行时间 */
        protected String duration;

        /** 类信息 */
        protected String classInfo;

        /** 方法信息 */
        protected String methodInfo;

        /**
         * 将所有信息拼接输出<br/>
         * 继承该类的对象可以根据设定的格式，对该方法进行重写
         * @return 拼接后的输出结果
         */
        public String output() {
            return date + "\n" + duration + "\n" + classInfo + "\n" + methodInfo;
        }
    }

    /**
     * 获取实类的方法，子类可以通过重写该方法，实现自定义的InfoReceiver
     * @return InfoReceiver对象
     */
    default InfoReceiver getInfoReceiver() {
        return new InfoReceiver();
    }

    /**
     * 根据大部分的实现，实现类中可以保存数据源source对象，
     * 所以可以自行调用具体的方法
     * @return 格式化后的输出结果对象，调用其output即可输出结果
     */
    default InfoReceiver format() {
        throw new RuntimeException("no-data-source");
    }

    /**
     * 对属性进行格式化，输出最终格式化后的结果
     * @param source 被格式化的对象，需要传入DisplayProperty对象的属性，否则会返回null
     * @return 格式化后的对象，当传入参数类型不正确时会返回null
     */
    @Override
    default InfoReceiver format(Object source) {
        // 先判断类型
        if(!(source instanceof TimerOutputSource)) {
            return null;
        }
        TimerOutputSource property = (TimerOutputSource)source;
        InfoReceiver receiver = getInfoReceiver();

        receiver.setDate(formatDate(property.getDate()));
        receiver.setDuration(formatTime(property.getDuration()));
        receiver.setClassInfo(formatClass(property.getClazz()));
        receiver.setMethodInfo(formatMethod(property.getMethod()));

        return receiver;
    }

    /**
     * 格式化日期
     * @param date 日期
     * @return 日期格式化后的字符串
     */
    String formatDate(Date date);

    /**
     * 对方法用时进行格式化
     * @param duration 方法用时
     * @return 格式化后的输出结果
     */
    String formatTime(long duration);

    /**
     * 对类进行格式化
     * @param clazz 类
     * @return 格式化后的类信息
     */
    String formatClass(Class clazz);

    /**
     * 对方法进行格式化
     * @param method 方法名
     * @return 格式化后的方法信息
     */
    String formatMethod(Method method);

    /**
     * 获取属性对象
     * @return 属性对象，保存有用户的配置属性
     */
    default TimerProperties getTimerProperties() {
        throw new RuntimeException("no-properties in TimerFormatable");
    }
}
