package com.allatori.util.random;

import java.util.Date;

public abstract class SeededRandomGenerator extends RandomGenerator {

  public static long calculateSeed(Date date) {
    return date.getTime();
  }

  public static long calculateSeed() {
    return calculateSeed(new Date());
  }
}
