package com.akira.timer.simulate;

import com.akira.timer.format.TimerFormatable;

/**
 * @author Kth
 * @date 2019/6/1
 */
public class UserReceiver extends TimerFormatable.InfoReceiver {

    @Override
    public String output() {
        return "\nuser:\n" + super.output();
    }
}
