package net.coderland.server.common.exception;

/**
 * Created by zhangxin on 15/12/23.
 */
public class WidgetsNotFoundException extends RuntimeException {
    public WidgetsNotFoundException() {
        super();
    }

    public WidgetsNotFoundException(String message) {
        super(message);
    }

    public WidgetsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WidgetsNotFoundException(Throwable cause) {
        super(cause);
    }
}
