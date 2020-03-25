package in.taskoo.common.exception;

import in.taskoo.common.error.dto.TkErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public abstract class TkBaseException extends RuntimeException {
    private static final long serialVersionUID = 7057981566943771324L;
    private TkErrorResponse errorResponse;

  public TkBaseException(TkErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}