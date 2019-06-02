package com.github.mayoi7.timer.simulate;

import com.github.mayoi7.timer.format.AbstractFormatter;
import com.github.mayoi7.timer.props.TimerOutputSource;
import com.github.mayoi7.timer.props.TimerProperties;

/**
 * @author Kth
 * @date 2019/6/1
 */
public class UserFormatter extends AbstractFormatter {

    /**
     * 通过配置和属性源来初始化格式化器
     * @param properties 配置项，包括输出模式和规则
     * @param source     属性源，包含所有需要输出的属性
     */
    public UserFormatter(TimerProperties properties, TimerOutputSource source) {
        super(properties, source);
    }

    @Override
    public InfoReceiver getInfoReceiver() {
        return new UserReceiver();
    }

}
