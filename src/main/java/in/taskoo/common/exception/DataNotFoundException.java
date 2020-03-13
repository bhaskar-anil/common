package in.taskoo.common.exception;

import in.taskoo.common.error.TkCommonErrorType;
import in.taskoo.common.error.dto.ErrorMessageField;
import in.taskoo.common.error.dto.TkErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataNotFoundException extends TkBaseException {
  private static final long serialVersionUID = 3952536567734942673L;

  public DataNotFoundException(final Object source, final Object identifier) {
      super(new TkErrorResponse(TkCommonErrorType.NOT_FOUND, new ErrorMessageField("source", source), new ErrorMessageField("identifier", identifier)));
  }
}
