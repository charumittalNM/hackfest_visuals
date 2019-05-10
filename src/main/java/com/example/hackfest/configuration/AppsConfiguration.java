package com.example.hackfest.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppsConfiguration {


  @Value("${httpClient.connection.pool.size}")
  private String poolMaxTotal;

  @Value("${httpClientFactory.connection.timeout}")
  private String connectionTimeOut;

  @Value("${httpClientFactory.read.timeout}")
  private String readTimeOut;


  @Bean
  public RestTemplate restTemplate() {
    return restTemplate(Integer.parseInt(connectionTimeOut), Integer.parseInt(readTimeOut),
        Integer.parseInt(poolMaxTotal));
  }

  public HttpClient httpClient(int noOfConnections) {
    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(noOfConnections);
    return HttpClientBuilder.create().setConnectionManager(connectionManager).build();
  }

  public ClientHttpRequestFactory httpRequestFactory(int connectionTimeout,
      int readTimeout,
      int maxConnections) {
    HttpComponentsClientHttpRequestFactory factory =
        new HttpComponentsClientHttpRequestFactory(httpClient(maxConnections));
    factory.setConnectTimeout(connectionTimeout);
    factory.setReadTimeout(readTimeout);
    return factory;
  }

  public RestTemplate restTemplate(int connectionTimeout, int readTimeout, int maxConnections) {

    PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setDefaultMaxPerRoute(maxConnections);
    connectionManager.setMaxTotal(maxConnections);
    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder.create().setConnectionManager(connectionManager).build());
    requestFactory.setConnectionRequestTimeout(connectionTimeout);
    requestFactory.setConnectTimeout(connectionTimeout);
    requestFactory.setReadTimeout(readTimeout);
    return new RestTemplate(requestFactory);

  }

}
