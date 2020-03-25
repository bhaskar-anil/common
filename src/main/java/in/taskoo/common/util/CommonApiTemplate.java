package in.taskoo.common.util;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

public class CommonApiTemplate extends RestTemplate {
  @Autowired
  private CommonApiErrorHandler commonApiErrorHandler;
  @Autowired
  private CommonApiLoggingInterceptor commonApiLoggingInterceptor;

  private String proxySchme;

  private String proxyHost;

  private String proxyPort;

  private String connectTimeout;

  private String readTimeout;

  @PostConstruct
  public void init() {
    setRequestFactory(httpRequestFactory());

    ObjectMapper mapper = new ObjectMapper()
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule());
    mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
    setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter(mapper)));

    setErrorHandler(commonApiErrorHandler);

    setInterceptors(Collections.singletonList(commonApiLoggingInterceptor));
  }

  private ClientHttpRequestFactory httpRequestFactory() {
    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient());

    if (StringUtils.isNotEmpty(connectTimeout)) {
      factory.setConnectTimeout(Integer.parseInt(connectTimeout));
    }

    if (StringUtils.isNotEmpty(readTimeout)) {
      factory.setReadTimeout(Integer.parseInt(readTimeout));
    }
    return new BufferingClientHttpRequestFactory(factory);
  }

  private CloseableHttpClient httpClient() {
    HttpClientBuilder builder = HttpClientBuilder.create();
    builder.setConnectionManager(new PoolingHttpClientConnectionManager());

    if (StringUtils.isNotEmpty(proxyHost) && StringUtils.isNotEmpty(proxyPort) && StringUtils.isNotEmpty(proxySchme)) {
      builder.setProxy(new HttpHost(proxyHost, Integer.parseInt(proxyPort), proxySchme));
    }

    return builder.build();
  }
}
