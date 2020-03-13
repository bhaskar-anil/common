package in.taskoo.common.logging;

import lombok.Data;

@Data
public class ErrorLogEntry {
    private String message;

    private Class exceptionClass;

    private StackTraceElement[] stackTraceElements;
}
