/**
 * Copyright Rakuten, Inc. All Rights Reserved.
 *
 * This program is the information assets which are handled
 * as "Strictly Confidential".
 * Permission of Use is only admitted in Rakuten Inc.
 * Development Department.
 * If you don't have permission , MUST not be published,
 * broadcast, rewritten for broadcast or publication
 * or redistributed directly or indirectly in any medium.
 */

package in.taskoo.common.util;

import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class CommonApiLoggingInterceptor implements ClientHttpRequestInterceptor {
  private static final Logger logger = LoggerFactory.getLogger(CommonApiLoggingInterceptor.class);

  @Override
  public ClientHttpResponse intercept(HttpRequest req, byte[] body, ClientHttpRequestExecution exec)
      throws IOException {
    logRequest(req, body);
    ClientHttpResponse response = exec.execute(req, body);
    logResponse(response);
    return response;
  }

  private void logRequest(HttpRequest request, byte[] body) throws IOException {
    if (logger.isInfoEnabled()) {
      logger.info("URI         : {} \n" +
                  "Method      : {} \n" +
                  "Headers     : {} \n" +
                  "API Request : {} \n",
      request.getURI(), request.getMethod(), request.getHeaders(), new String(body, "UTF-8"));
    }
  }

  private void logResponse(ClientHttpResponse response) throws IOException {
    if (logger.isInfoEnabled()) {
      logger.info("Status code  : {} \n" +
                  "Status text  : {} \n" +
                  "Headers      : {} \n" +
                  "API Response : {}",
      response.getStatusCode(), response.getStatusText(), response.getHeaders(), copyToString(response.getBody(), Charset.defaultCharset()));
    }
  }
}
