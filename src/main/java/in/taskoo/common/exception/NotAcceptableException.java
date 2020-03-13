package in.taskoo.common.exception;

import in.taskoo.common.error.TkCommonErrorType;
import in.taskoo.common.error.dto.TkErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class NotAcceptableException extends TkBaseException {

    private static final long serialVersionUID = 4963071580292482682L;

    public NotAcceptableException() {
        super(new TkErrorResponse(TkCommonErrorType.NOT_ACCEPTABLE));
    }
}