package com.test.wirebarley.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomRestTemplate {

  @Bean
  public RestTemplate longRestTemplate() {
    HttpClient httpClient = HttpClients.custom()
        .setMaxConnTotal(10)
        .setMaxConnPerRoute(5)
        .build();

    HttpComponentsClientHttpRequestFactory factory
        = new HttpComponentsClientHttpRequestFactory(httpClient);
    factory.setConnectTimeout(10*1000);
    factory.setReadTimeout(10*1000);
    return new RestTemplate(factory);
  }
}
