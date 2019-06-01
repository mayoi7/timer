package com.github.mayoi7.timer.format.impl;

import com.github.mayoi7.timer.format.AbstractFormatter;
import com.github.mayoi7.timer.props.TimerOutputSource;
import com.github.mayoi7.timer.props.TimerProperties;

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
