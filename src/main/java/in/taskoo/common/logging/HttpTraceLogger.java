package in.taskoo.common.logging;

import static net.logstash.logback.marker.Markers.appendFields;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.stereotype.Repository;

/**
 * Customized HttpTraceRepository to log requests in log files
 * No HttpTrace is actually saved
 * <p>
 * This class is originally used to perform HTTP traces
 * However, this implementation overrides the original functionality to log HTTP requests instead
 * As such, this is NOT a repository to store information in a database
 */
@Slf4j(topic = "taskoo.common.requestLogger")
@Repository
public class HttpTraceLogger implements HttpTraceRepository {

    // Not used, so an empty list is returned
    @Override
    public List<HttpTrace> findAll() {
        return Collections.emptyList();
    }

    @Override
    public void add(HttpTrace trace) {
        // TODO: Verify that session ID logging works after Spring Security is set up
        // The session is currently always null
        // Do note that calling HttpServletRequest::getSession automatically creates a session if the session is currently null
        // Thus, trying to obtain the session ID through the HttpServletRequest in RequestContextHolder.getRequestAttributes()
        // might unintentionally create a session

        RequestLogEntry requestLogEntry = new RequestLogEntry()
            .setHttpMethod(trace.getRequest().getMethod())
            .setUri(trace.getRequest().getUri().getPath())
            .setSessionId(Objects.nonNull(trace.getSession()) ? trace.getSession().getId() : null)
            .setResponseCode(trace.getResponse().getStatus())
            .setExecutionTime(trace.getTimeTaken());

        log.info(appendFields(requestLogEntry), requestLogEntry.toString());
    }
}