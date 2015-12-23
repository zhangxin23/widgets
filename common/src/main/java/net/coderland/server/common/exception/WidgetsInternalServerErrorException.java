package net.coderland.server.common.exception;

/**
 * Created by zhangxin on 15/12/23.
 */
public class WidgetsInternalServerErrorException extends RuntimeException {
    public WidgetsInternalServerErrorException() {
        super();
    }

    public WidgetsInternalServerErrorException(String message) {
        super(message);
    }

    public WidgetsInternalServerErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public WidgetsInternalServerErrorException(Throwable cause) {
        super(cause);
    }
}
