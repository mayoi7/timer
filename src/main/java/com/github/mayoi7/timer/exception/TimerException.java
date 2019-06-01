package com.github.mayoi7.timer.exception;

/**
 * @author Kth
 * @date 2019/6/1
 */
public class TimerException extends RuntimeException {

    public TimerException() {
        super();
    }

    public TimerException(String message) {
        super(message);
    }

    public TimerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimerException(Throwable cause) {
        super(cause);
    }

    protected TimerException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
