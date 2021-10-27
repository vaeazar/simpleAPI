package com.test.wirebarley.controller;

import com.test.wirebarley.domain.Exchange;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

  private String jsonUrl = "http://api.currencylayer.com/live?access_key=052b94f87cd81cbaeca109ddea492194&currencies=KRW,JPY,PHP&format=1";

  private final RestTemplate longRestTemplate;

  public MainController(RestTemplate longRestTemplate) {
    this.longRestTemplate = longRestTemplate;
  }

  @GetMapping("/exchange/{country}")
  @ResponseBody
  public double getExchange(@PathVariable String country) {
    double exchange = 0.0;
    Exchange getJson = longRestTemplate.getForObject(jsonUrl, Exchange.class);
    exchange = getJson.getQuotes().get(country);
    return Math.floor(exchange*100)/100;
  }
  @GetMapping("/finalPrice/{country}/{price}")
  @ResponseBody
  public ResponseEntity finalPrice(@PathVariable String country, @PathVariable int price) {
    double exchange = 0.0;
    double finalPrice = 0.0;
    Map<String, Object> data = new HashMap<>();
    Exchange getJson = longRestTemplate.getForObject(jsonUrl, Exchange.class);
    exchange = getJson.getQuotes().get(country);
    finalPrice = exchange * price;
    data.put("exchange", Math.floor(exchange*100)/100);
    data.put("finalPrice", Math.floor(finalPrice*100)/100);

    return new ResponseEntity(data, HttpStatus.OK);
  }

  @RequestMapping(value = {"/", "/index"})
  public ModelAndView main() {
    ModelAndView mv = new ModelAndView();
    double exchange = 0.0;
    Exchange getJson = longRestTemplate.getForObject(jsonUrl, Exchange.class);
    exchange = getJson.getQuotes().get("USDKRW");
    mv.setViewName("index");
    mv.addObject("initExchange",Math.floor(exchange*100)/100);
    return mv;
  }
}
