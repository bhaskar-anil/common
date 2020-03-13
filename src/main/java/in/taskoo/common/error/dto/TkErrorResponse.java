package in.taskoo.common.error.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.taskoo.common.error.TkBaseErrorType;


public class TkErrorResponse implements Serializable {

	private static final long serialVersionUID = 6739806771781272183L;
	
	private final List<TkError> errors = new ArrayList<>();

    public TkErrorResponse() {
    }

    public TkErrorResponse(TkError error) {
        errors.add(error);
    }

    public TkErrorResponse(final TkBaseErrorType message, final ErrorMessageField... messageFields) {
        errors.add(new TkError(message.getErrorCode(), message.getMessage(), messageFields));
    }

    public TkErrorResponse(final TkBaseErrorType message) {
        errors.add(new TkError(message.getErrorCode(), message.getMessage()));
    }

    public void addError(final TkBaseErrorType message, final ErrorMessageField... messageFields) {
        errors.add(new TkError(message.getErrorCode(), message.getMessage(), messageFields));
    }

    public void addError(final TkBaseErrorType message) {
        errors.add(new TkError(message.getErrorCode(), message.getMessage()));
    }

    public void addError(final TkError error) {
        errors.add(error);
    }

    public void addAllErrors(final TkErrorResponse errorz) {
        if (Objects.isNull(errorz) || !errorz.hasErrors()) {
            return;
        }
        errors.addAll(errorz.getErrors());
    }

    public void clear() {
        errors.clear();
    }

    public List<TkError> getErrors() {
        return errors;
    }

    public Boolean hasErrors() {
        return !errors.isEmpty();
    }

    @Override
    public String toString() {
        return "Errors [errorMessages=" + errors + "]";
    }
}