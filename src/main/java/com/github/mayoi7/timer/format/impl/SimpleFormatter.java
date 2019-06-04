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

        // 注意，如果没有这一步，则类格式化器不会生效
        classFormatter = new SimpleClassFormatter();
    }

    private class SimpleInfoReceiver extends InfoReceiver {

        @Override
        public String output() {
            return "\n[" + super.output().substring(28) + "]";
        }
    }

    private static class SimpleClassFormatter extends ClassFormatter {

        @Override
        public String formatInFull(Class clazz) {
            return super.formatInFull(clazz);
        }

        @Override
        public String formatInSimple(Class clazz) {
            return super.formatInSimple(clazz);
        }
    }

    @Override
    public InfoReceiver getInfoReceiver() {
        return new SimpleInfoReceiver();
    }
}
