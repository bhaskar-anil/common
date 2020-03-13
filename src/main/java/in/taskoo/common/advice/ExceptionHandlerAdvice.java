package in.taskoo.common.advice;

import static net.logstash.logback.marker.Markers.appendFields;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import in.taskoo.common.error.TkCommonErrorType;
import in.taskoo.common.error.dto.ErrorMessageField;
import in.taskoo.common.error.dto.TkError;
import in.taskoo.common.error.dto.TkErrorResponse;
import in.taskoo.common.exception.DataNotFoundException;
import in.taskoo.common.exception.InvalidRequestException;
import in.taskoo.common.exception.NotAcceptableException;
import in.taskoo.common.logging.ErrorLogEntry;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "taskoo.common")
@Component
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ HttpMessageNotReadableException.class, HttpMessageConversionException.class,
                        ConstraintViolationException.class, MethodArgumentTypeMismatchException.class })
    public TkErrorResponse handleBadRequestException(RuntimeException exception) {
        return new TkErrorResponse(TkCommonErrorType.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public TkErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return prepareCmpErrorResponse(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    public TkErrorResponse handleBInvalidRequestException(InvalidRequestException exception) {
        return exception.getErrorResponse();
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public TkErrorResponse handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception) {
        return new TkErrorResponse(TkCommonErrorType.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(NotAcceptableException.class)
    public TkErrorResponse handleNotAcceptable(NotAcceptableException exception) {
        return exception.getErrorResponse();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public TkErrorResponse handleDataNotFound(DataNotFoundException exception) {
        return exception.getErrorResponse();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ Exception.class, IllegalStateException.class })
    public TkErrorResponse handleSystemErrorRequestException(Exception exception) {
        logError(exception);
        return new TkErrorResponse(TkCommonErrorType.INTERNAL_SERVER_ERROR);
    }

    private static void logError(Exception exception) {
        ErrorLogEntry errorLogEntry = new ErrorLogEntry()
            .setExceptionClass(exception.getClass())
            .setMessage(exception.getMessage())
            .setStackTraceElements(exception.getStackTrace());

        log.error(appendFields(errorLogEntry), errorLogEntry.toString());
    }

    private TkErrorResponse prepareCmpErrorResponse(MethodArgumentNotValidException exception) {
        TkErrorResponse errors = new TkErrorResponse();

        exception.getBindingResult().getAllErrors().stream().map(objectError -> (FieldError) objectError)
                .forEach(fieldError -> {
                    String placeholderMessage = "[{objectName}] [{field}] " + fieldError.getDefaultMessage();
                    errors.addError(new TkError(fieldError.getCode(), placeholderMessage, 
                        new ErrorMessageField("objectName", fieldError.getObjectName()),
                        new ErrorMessageField("field", fieldError.getField())));
                });
        return errors;
    }
}