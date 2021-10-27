package com.test.wirebarley.domain;

import java.util.HashMap;
import lombok.Data;

@Data
public class Exchange {
  boolean success;
  String terms;
  String privacy;
  Long timestamp;
  String source;
  HashMap<String, Double> quotes;
}
