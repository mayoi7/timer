package com.akira.timer.format.impl;

import com.akira.timer.format.AbstractFormatter;
import com.akira.timer.props.TimerOutputSource;
import com.akira.timer.props.TimerProperties;

/**
 * 简单初始化器，执行默认的输出格式<br/>
 * 格式为：
 * <p>
 *     [ <br/>
 *       date <br/>
 *       time <br/>
 *       class <br/>
 *       method <br/>
 *     ]
 * </p>
 * @author Kth
 * @date 2019/5/31
 */
public class SimpleFormatter extends AbstractFormatter {


    public SimpleFormatter(TimerProperties properties, TimerOutputSource source) {
        super(properties, source);
    }

    public class SimpleInfoReceiver extends InfoReceiver {

        @Override
        public String output() {
            return "\n[\n" + super.output() + "\n]";
        }
    }

    @Override
    public InfoReceiver getInfoReceiver() {
        return new SimpleInfoReceiver();
    }
}
