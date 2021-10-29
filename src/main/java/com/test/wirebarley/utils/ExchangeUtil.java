package com.test.wirebarley.utils;

public class ExchangeUtil {

  public static double doubleFormatter(double target) {
    double formatTarget = target;
    formatTarget = Math.floor(formatTarget*100)/100;
    return formatTarget;
  }
}
