package in.taskoo.common.exception;

import in.taskoo.common.error.TkCommonErrorType;
import in.taskoo.common.error.dto.ErrorMessageField;
import in.taskoo.common.error.dto.TkErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class InvalidRequestException extends TkBaseException {

    private static final long serialVersionUID = 4679899190183277980L;

    public InvalidRequestException(final TkErrorResponse cmpErrorResponse) {
        super(cmpErrorResponse);
    }

    // thrown when invalid enums
    public InvalidRequestException(final String type, final Object value) {
        super(new TkErrorResponse(TkCommonErrorType.INVALID_ENUM, new ErrorMessageField("type", type), new ErrorMessageField("value", value)));
    }
}
