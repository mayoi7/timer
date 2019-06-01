package com.github.mayoi7.timer.props;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 包含了属性输出模式和排版格式
 * @author Kth
 * @date 2019/6/1
 */
@Component
@Data
public class TimerProperties {

    /** 属性输出模式 */
    private final TimerModeProperties timerMode;

    /** 输出格式 */
    private final TimerFormatProperties timerFormat;

    public TimerProperties(TimerModeProperties timerMode, TimerFormatProperties timerFormat) {
        this.timerMode = timerMode;
        this.timerFormat = timerFormat;
    }
}
