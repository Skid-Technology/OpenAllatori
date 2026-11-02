package com.allatori.naming.generator;

public class NumericNameGenerator implements NameGenerator {

  public static String text = "0123456789";

  public long seed = 0L;

  public static final int COUNT = 10;

  @Override
  public void reset() {
    this.seed = 0L;
  }

  @Override
  public String nextName() {
    String text2 = "";
    long value = this.seed;
    int value2 = 1;
    long value3 = 10L;
    for (long value4 = value; value4 >= value3; value4 = value) {
      long value5 = value;
      value2++;
      value = value5 - value3;
      value3 *= 10L;
    }
    int value6;
    for (int value7 = value6 = 0; value7 < value2; value7 = value6) {
      text2 = "" + text2 + text.charAt((int) (value % 10L));
      long value8 = value;
      value6++;
      value = value8 / 10L;
    }
    this.seed++;
    return text2;
  }
}
