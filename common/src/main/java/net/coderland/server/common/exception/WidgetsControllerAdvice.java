package net.coderland.server.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * Created by zhangxin on 15/12/23.
 */
@ControllerAdvice
public class WidgetsControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WidgetsBadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(WidgetsBadRequestException ex) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Bad Request Exception");
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WidgetsForbiddenException.class)
    public ResponseEntity<?> handleForbiddenException(WidgetsForbiddenException ex) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Forbidden Exception");
        errorDetail.setStatus(HttpStatus.FORBIDDEN.value());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(WidgetsInternalServerErrorException.class)
    public ResponseEntity<?> handleInternalServerErrorException(WidgetsInternalServerErrorException ex) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Forbidden Exception");
        errorDetail.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WidgetsNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(WidgetsNotFoundException ex) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Forbidden Exception");
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(RuntimeException ex) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTitle("Bad Request Exception");
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setDeveloperMessage(stackTraceToString(ex.getStackTrace()));

        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }

    private String stackTraceToString(StackTraceElement[] stackTraceElements) {
        StringBuilder builder = new StringBuilder();
        for(StackTraceElement item: stackTraceElements) {
            builder.append(item.getClassName())
                    .append("(")
                    .append(item.getFileName()).append(": ").append(item.getLineNumber())
                    .append(")")
                    .append("    ");
        }
        return builder.toString();
    }
}
