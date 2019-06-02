package com.github.mayoi7.timer.utils;

import com.github.mayoi7.timer.cons.ResultPosition;
import com.github.mayoi7.timer.exception.TimerException;
import com.github.mayoi7.timer.format.AbstractFormatter;
import com.github.mayoi7.timer.format.TimerFormatable;
import com.github.mayoi7.timer.format.impl.SimpleFormatter;
import com.github.mayoi7.timer.props.TimerOutputSource;
import com.github.mayoi7.timer.props.TimerProperties;
import lombok.Data;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 用于输出最终的结果
 * @author Kth
 * @date 2019/5/31
 */
@Data
public class TimerStreamOutputer {

    /** 格式化器 */
    private TimerFormatable formatter;

    /**
     * 如果不使用格式化器，则会默认使用SimpleFormatter对象
     * @param properties 配置的格式化模式和规则
     * @param source 属性源对象，保存着待格式化的属性集合
     */
    private TimerStreamOutputer(TimerProperties properties, TimerOutputSource source) {
        this(new SimpleFormatter(properties, source));
    }

    private TimerStreamOutputer(TimerFormatable formatter) {
        this.formatter = formatter;
    }

    /**
     * 输出结果<br/>
     * 根据设定，选择不同的信息输出位置
     * @param pos 设置的输出位置
     */
    public void output(ResultPosition pos) {
        if(ResultPosition.CONSOLE == pos) {
            output2Console();
        } else if(ResultPosition.LOG == pos) {
            output2Log();
        }
    }

    /**
     * 将结果输出到控制台，
     * 如果PropertyFormatable的实现类没有重写format()方法，则会抛出一个运行时异常
     */
    public void output2Console() {
        TimerFormatable.InfoReceiver receiver = formatter.format();

        System.out.println(receiver.output());
    }

    /**
     * 将结果输出到执行日志文件中，只能使用追加方式<br/>
     * 如果文件路径不存在，则会在指定目录下创建一个"timer-yyyy-mm-dd.log"文件
     */
    public void output2Log() {
        // 获取用户配置的属性
        TimerProperties properties = formatter.getTimerProperties();

        // 获取路径和文件名
        String path = properties.getTimerFormat().getLogPath();
        String fileFormat = properties.getTimerFormat().getFileFormat();

        String fullPath = checkAndGetFullLogPath(path, fileFormat);
        File file = new File(fullPath);

        // 输出的内容
        String content = formatter.format().output();

        try(FileWriter writer = new FileWriter(file, true)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>
     * 构造并获取结果输出器
     * </p>
     * <p>
     * 如果timer中的配置的自定义格式化器路径不为空，或配置文件配置的格式化器路径不为空，
     * 就使用配置的自定义格式化器,否则会使用默认的格式化器
     * <p/>
     * 且timer配置的优先级大于配置文件
     * @param path 拥有最高优先级的自定义格式化器路径，即@Timer注解中配置的formatter的值
     * @param properties 配置文件属性对象，其中也可以配置拥有次优先级的格式化器路径
     * @param source 数据源对象，封装待展示的数据
     * @return 结果流输出器，通过调用outputXXX()方法输出结果
     */
    public static TimerStreamOutputer getInstance(String path,
                                                  TimerProperties properties,
                                                  TimerOutputSource source) {
        String formatterPath = null;
        TimerStreamOutputer outputer = null;
        if(!"".equals(formatterPath = path) ||
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

        return outputer;
    }

    /**
     * 通过文件路径和文件名格式获取完整的文件路径
     * @param path 日志文件的输出路径
     * @param fileFormat 文件名格式
     * @return 日志的完整输出路径
     */
    private static String checkAndGetFullLogPath(String path, String fileFormat) {
        if(path == null || fileFormat == null) {
            throw new TimerException("path or fileFormat is null");
        } else if("".equals(fileFormat)) {
            throw new TimerException("fileFormat should have some words");
        }

        // 如果最后一个字符不是分隔符，就手动添加
        if(path.charAt(path.length() - 1) != '/') {
            path += '/';
        }

        return path + TimerFormatParser.parseFileName(fileFormat);
    }
}
