package com.allatori.naming.generator;

public class AsciiNameGenerator implements NameGenerator {

  public long seed = 0L;

  public static String text = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";

  public static int count = text.length();

  @Override
  public String nextName() {
    String text2 = "";
    long value = this.seed;
    int value2 = 1;
    long value3 = count;
    for (long value4 = value; value4 >= value3; value4 = value) {
      long value5 = value;
      value2++;
      value = value5 - value3;
      value3 *= count;
    }
    int value6;
    for (int value7 = value6 = 0; value7 < value2; value7 = value6) {
      text2 = "" + text2 + text.charAt((int) (value % count));
      int value8 = count;
      long value9 = value;
      value6++;
      value = value9 / value8;
    }
    this.seed++;
    return text2;
  }

  @Override
  public void reset() {
    this.seed = 0L;
  }
}
