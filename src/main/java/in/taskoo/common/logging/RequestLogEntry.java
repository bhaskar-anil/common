package in.taskoo.common.logging;

import lombok.Data;

@Data
public class RequestLogEntry {
    private String httpMethod;

    private String uri;

    private String sessionId;

    private Integer responseCode;

    private Long executionTime;
}
