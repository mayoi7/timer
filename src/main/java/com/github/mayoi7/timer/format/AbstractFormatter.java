package com.github.mayoi7.timer.format;

import com.github.mayoi7.timer.cons.ClassDisplay.AbstractClassFormatter;
import com.github.mayoi7.timer.cons.MethodDisplay.AbstractMethodFormatter;
import com.github.mayoi7.timer.props.TimerOutputSource;
import com.github.mayoi7.timer.props.TimerProperties;
import com.github.mayoi7.timer.utils.TimerUtil;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 抽象格式化器，提供了TimerFormatable中大部分方法的实现<br/>
 * 本类用于自定义格式化器<br/>
 * <br/>
 * <p>
 *     如果需要对输出属性的格式进行自定义，则需要实现{@link InfoReceiver}类，
 *     重写其output方法。然后需要重写{@link TimerFormatable}中的{@code getInstance()}方法，返回
 *     {@link InfoReceiver}对象，推荐将该类放在你自定义的格式化器的内部<br/>
 * </p>
 * <br/>
 * <p>
 *     如果需要对不同属性模式的输出方式做修改（如类的FULL模式下想要输出更详细的内容），
 *     就要实现AbstractClassFormatter或AbstractMethodFormatter（根据你自己的需要），
 *     重写其接口方法，并在你格式化器的构造方法中为
 *     {@code classFormatter}或{@code methodFormatter}
 *     赋予你实现的对应类的实例，这一步很重要，否则框架将会使用默认的实现方式。
 *     同样地，也推荐将该类定义在你格式化器的内部，
 *     并声明为private或protected，以避免外部的调用
 * </p>
 * @author Kth
 * @date 2019/6/1
 */
public abstract class AbstractFormatter implements TimerFormatable {

    /** 日期格式化器，线程本地变量 */
    private static ThreadLocal<SimpleDateFormat> dateFormatThreadLocal;

    /** 包含了输出模式和格式，定义了输出规则 */
    private final TimerProperties properties;

    /** 待格式化的属性 */
    private TimerOutputSource source;

    /**
     * 类对象格式化器类，调用format方法进行格式化<br/>
     * 本类为单例模式，但是由于本类实例仅在当前类中创建，
     * 所以构造函数声明为private没有意义，就此省略
     */
    protected static class ClassFormatter extends AbstractClassFormatter {

        private static class Internal {
            private static ClassFormatter INSTANCE = new ClassFormatter();
        }

        /**
         * 返回单例
         * @return ClassFormatter对象
         */
        private static ClassFormatter getInstance() {
            return Internal.INSTANCE;
        }

        @Override
        public String formatInSimple(Class clazz) {
            return clazz.getSimpleName();
        }

        @Override
        public String formatInFull(Class clazz) {
            return clazz.getName();
        }
    }

    /**
     * 方法对象格式化器类，调用format方法进行格式化<br/>
     * 本类为单例模式，但是由于本类实例仅在当前类中创建，
     * 所以构造函数声明为private没有意义，就此省略
     */
    protected static class MethodFormatter extends AbstractMethodFormatter {

        private static class Internal {
            private static MethodFormatter INSTANCE = new MethodFormatter();
        }

        /**
         * 返回单例
         * @return ClassFormatter对象
         */
        private static MethodFormatter getInstance() {
            return Internal.INSTANCE;
        }
        @Override
        public String formatInSimple(Method method) {
            return method.getName();
        }

        @Override
        public String formatInParam(Method method) {
            return method.getName() + Arrays.toString(method.getParameters());
        }
    }

    /** 类格式化器 */
    protected AbstractClassFormatter classFormatter = ClassFormatter.getInstance();

    /** 方法格式化器 */
    protected AbstractMethodFormatter methodFormatter = MethodFormatter.getInstance();

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
    public final String formatClass(Class clazz) {
        return classFormatter
                .format(clazz, properties.getTimerMode().getClassMode());
    }

    @Override
    public final String formatMethod(Method method) {
        return methodFormatter
                .format(method, properties.getTimerMode().getMethodMode());
    }

    @Override
    public TimerProperties getTimerProperties() {
        return properties;
    }

}
