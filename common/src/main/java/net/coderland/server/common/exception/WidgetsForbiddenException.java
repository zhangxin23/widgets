package net.coderland.server.common.exception;

/**
 * Created by zhangxin on 15/12/23.
 */
public class WidgetsForbiddenException extends RuntimeException {
    public WidgetsForbiddenException() {
        super();
    }

    public WidgetsForbiddenException(String message) {
        super(message);
    }

    public WidgetsForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public WidgetsForbiddenException(Throwable cause) {
        super(cause);
    }
}
