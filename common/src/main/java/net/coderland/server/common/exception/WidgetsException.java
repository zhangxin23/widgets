package net.coderland.server.common.exception;

/**
 * Created by zhangxin on 15/12/23.
 */
public class WidgetsException extends RuntimeException {

    public WidgetsException() {
        super();
    }

    public WidgetsException(String message) {
        super(message);
    }

    public WidgetsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WidgetsException(Throwable cause) {
        super(cause);
    }
}
