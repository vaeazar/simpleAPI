package com.test.wirebarley.exchangeTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.test.wirebarley.controller.MainController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class ExchangeTest {

  Logger log = (Logger) LoggerFactory.getLogger(ExchangeTest.class);

  @Autowired
  MainController controller;

  @Autowired
  MockMvc mvc;

  @BeforeAll
  void setup() throws Exception {
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  private MvcResult result;

  @Test
  @DisplayName("Exchange API working test")
  void T1() {
    try {
      result = mvc.perform(get("/exchange/USDKRW"))
          .andExpect(status().isOk())
          .andReturn();
      String data = result.getResponse().getContentAsString();
      log.info("API result ::: {}", data);
      assertThat(data).isNotBlank();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
