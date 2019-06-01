package com.github.mayoi7.timer.utils;

import com.github.mayoi7.timer.exception.TimerException;
import com.github.mayoi7.timer.format.TimerFormatable;
import com.github.mayoi7.timer.format.impl.SimpleFormatter;
import com.github.mayoi7.timer.props.TimerOutputSource;
import com.github.mayoi7.timer.props.TimerProperties;
import lombok.Data;

import java.io.*;

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
    public TimerStreamOutputer(TimerProperties properties, TimerOutputSource source) {
        this(new SimpleFormatter(properties, source));
    }

    public TimerStreamOutputer(TimerFormatable formatter) {
        this.formatter = formatter;
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
