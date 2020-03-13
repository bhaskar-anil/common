package in.taskoo.common.error.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageField {
    private String field;
    private Object value;
}