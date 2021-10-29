package com.test.wirebarley.service;

import com.test.wirebarley.domain.Exchange;
import com.test.wirebarley.utils.ExchangeUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {

  private String jsonUrl = "http://api.currencylayer.com/live?access_key=052b94f87cd81cbaeca109ddea492194&currencies=KRW,JPY,PHP&format=1";
  private final RestTemplate longRestTemplate;

  public ExchangeService(RestTemplate longRestTemplate) {
    this.longRestTemplate = longRestTemplate;
  }

  @Cacheable(cacheNames = "getExchangeInfo", key = "#cacheKey", unless = "#result == null")
  public Exchange getExchangeInfo(String cacheKey) {

    Exchange getJson = longRestTemplate.getForObject(jsonUrl, Exchange.class);

    return getJson;
  }

  public Map<String, Object> finalPrice(Exchange getJson, String country, int price) {
    Map<String, Object> data = new HashMap<>();
    double exchange = 0.0;
    double finalPrice = 0.0;
    exchange = getJson.getQuotes().get(country);
    finalPrice = exchange * price;
    data.put("exchange", ExchangeUtil.doubleFormatter(exchange));
    data.put("finalPrice", ExchangeUtil.doubleFormatter(finalPrice));

    return data;
  }
}
