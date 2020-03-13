package in.taskoo.common.error.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.Data;

@Data
public class TkError implements Serializable {

	private static final long serialVersionUID = 7139849309703469547L;

	private String errorCode;

    private String message;

    private String placeholderMessage;

    private Map<String, String> messageFieldsMap;

    public TkError(final String code, final String placeholderMessage, final ErrorMessageField... messageFields) {
        this.errorCode = code;
        this.message = getMessage(placeholderMessage, messageFields);
        this.placeholderMessage = placeholderMessage;
        this.messageFieldsMap = buildFieldsMap(messageFields);
    }

    private Map<String, String> buildFieldsMap(final ErrorMessageField... messageFields) {
        if (Objects.nonNull(messageFields)) {
            this.messageFieldsMap = new HashMap<>();
            for (ErrorMessageField messageField : messageFields) {
                this.messageFieldsMap.put(messageField.getField(), messageField.getValue().toString());
            }
        }
        return this.messageFieldsMap;
    }

    public String getMessage(String placeholderMessage, final ErrorMessageField... messageFields) {
        if (Objects.isNull(messageFields)) {
            return placeholderMessage;
        }
        for (final ErrorMessageField messageField : messageFields) {
            final String placeholderString = "{" + messageField.getField() + "}";
            placeholderMessage = placeholderMessage.replace(placeholderString,
                Objects.isNull(messageField.getValue()) ? "null" : messageField.getValue().toString());
        }
        return placeholderMessage;
    }

    @Override
    public String toString() {
        return "Error [" + errorCode + " : " + message + "]";
    }
}