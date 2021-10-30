package com.test.wirebarley.controller;

import com.test.wirebarley.domain.Exchange;
import com.test.wirebarley.service.ExchangeService;
import com.test.wirebarley.utils.ExchangeUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

  private final ExchangeService exchangeService;

  public MainController(ExchangeService exchangeService) {
    this.exchangeService = exchangeService;
  }

  @GetMapping("/exchange/{country}")
  @ResponseBody
  public double getExchange(@PathVariable String country) {
    double exchange = 0.0;
    Exchange getJson = exchangeService.getExchangeInfo("exchange");
    exchange = getJson.getQuotes().get(country);
    return ExchangeUtil.doubleFormatter(exchange);
  }

  @GetMapping("/finalPrice/{country}/{price}")
  @ResponseBody
  public ResponseEntity finalPrice(@PathVariable String country, @PathVariable int price) {

    if (price < 1 || price > 100000) {
      return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
    Exchange getJson = exchangeService.getExchangeInfo("exchange");
    Map<String, Object> data = exchangeService.finalPrice(getJson, country, price);

    return new ResponseEntity(data, HttpStatus.OK);
  }

  @RequestMapping(value = {"/", "/index"})
  public ModelAndView main() {
    ModelAndView mv = new ModelAndView();
    double exchange = 0.0;
    Exchange getJson = exchangeService.getExchangeInfo("exchange");
    exchange = getJson.getQuotes().get("USDKRW");
    mv.setViewName("index");
    mv.addObject("initExchange", ExchangeUtil.doubleFormatter(exchange));
    return mv;
  }
}
