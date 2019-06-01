package com.github.mayoi7.timer.props;

import com.github.mayoi7.timer.cons.ClassDisplay;
import com.github.mayoi7.timer.cons.MethodDisplay;
import com.github.mayoi7.timer.cons.TimeDisplay;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 定义属性输出的模式，从预设模式中选择
 * @author Kth
 * @date 2019/5/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "timer.mode")
public class TimerModeProperties {

    /** 时间输出方式 */
    private TimeDisplay timeMode = TimeDisplay.SIMPLE;

    /** 时间单位 */
    private TimeUnit unit = TimeUnit.MILLISECONDS;

    /** 方法名输出方式 */
    private MethodDisplay methodMode = MethodDisplay.SIMPLE;

    /** 类名输出方式 */
    private ClassDisplay classMode = ClassDisplay.FULL;

}
