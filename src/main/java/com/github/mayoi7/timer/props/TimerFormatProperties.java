package com.github.mayoi7.timer.props;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 一些需要设定的输出格式，如日期输出方式、排版格式，以及日志路径等
 * @author Kth
 * @date 2019/6/1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "timer.format")
public class TimerFormatProperties {

    /** 日期输出格式 */
    private String dateFormat = "yyyy-MM-dd HH:mm:ss";

    /**
     * 定义排版格式<br/>
     * 已弃用，如果需要修改输出格式，请实现一个子类继承InfoReceiver类，
     * 并重写其output方法，同时重写TimerFormattable中的getInfoReceiver方法返回这个类
     */
    @Deprecated
    private String typesetFormat;

    /** 日志文件名格式，后缀名默认为.log */
    private String fileFormat = "timer";

    /** 日志输出路径，如使用控制台输出方式则此项可不设置 */
    private String logPath = "/";

    /** 自定义的格式化器的路径 */
    private String formatterPath = "";
}
