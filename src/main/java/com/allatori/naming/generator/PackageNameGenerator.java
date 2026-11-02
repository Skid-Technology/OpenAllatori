package com.allatori.naming.generator;

public class PackageNameGenerator implements NameGenerator {

  public String[] texts;

  public int count;

  public long seed;

  @Override
  public String nextName() {
    String text = "";
    long value = this.seed;
    int value2 = 1;
    long value3 = this.count;
    for (long value4 = value; value4 >= value3; value4 = value) {
      long value5 = value;
      value2++;
      value = value5 - value3;
      value3 *= this.count;
    }
    int value6;
    for (int value7 = value6 = 0; value7 < value2; value7 = value6) {
      text = "" + text + this.texts[(int) (value % this.count)];
      int value8 = this.count;
      long value9 = value;
      value6++;
      value = value9 / value8;
    }
    this.seed++;
    return text;
  }

  public PackageNameGenerator(String[] texts2) {
    super();
    this.seed = 0L;
    this.texts = texts2;
    this.count = texts2.length;
  }

  @Override
  public void reset() {
    this.seed = 0L;
  }
}
