package net.coderland.server.common.exception;

/**
 * Created by zhangxin on 15/12/23.
 */
public class WidgetsBadRequestException extends RuntimeException {
    public WidgetsBadRequestException() {
        super();
    }

    public WidgetsBadRequestException(String message) {
        super(message);
    }

    public WidgetsBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public WidgetsBadRequestException(Throwable cause) {
        super(cause);
    }
}
