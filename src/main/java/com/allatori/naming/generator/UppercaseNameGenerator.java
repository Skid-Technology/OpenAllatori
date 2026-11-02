package com.allatori.naming.generator;

public class UppercaseNameGenerator implements NameGenerator {

  public static final int COUNT = 26;

  public static String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public long seed = 0L;

  @Override
  public void reset() {
    this.seed = 0L;
  }

  @Override
  public String nextName() {
    String text2 = "";
    long value = this.seed;
    int value2 = 1;
    long value3 = 26L;
    for (long value4 = value; value4 >= value3; value4 = value) {
      long value5 = value;
      value2++;
      value = value5 - value3;
      value3 *= 26L;
    }
    int value6;
    for (int value7 = value6 = 0; value7 < value2; value7 = value6) {
      text2 = "" + text2 + text.charAt((int) (value % 26L));
      long value8 = value;
      value6++;
      value = value8 / 26L;
    }
    this.seed++;
    return text2;
  }
}
