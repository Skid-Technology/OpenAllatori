package com.allatori.naming.generator;

public class MixedConfusableNameGenerator implements NameGenerator {

  public static int count = 10;

  public static String text = "IiiIiIiiII";

  public long seed = 0L;

  public static void processCount(int value) {
    if (value > count) {
      count = value;
    }
  }

  public static String nextName2() {
    return text;
  }

  @Override
  public void reset() {
    this.seed = 0L;
  }

  @Override
  public String nextName() {
    String text;
    String text2 = text = Long.toBinaryString(this.seed++);
    while (text2.length() < count) {
      text2 = text = '0' + text;
    }
    StringBuffer stringBuffer = new StringBuffer(text.length());
    int value;
    for (int value2 = value = 0; value2 < text.length(); value2 = ++value) {
      if (value % 2 == 0) {
        stringBuffer.append((char) (text.charAt(value) == '0' ? 'i' : 'I'));
      } else {
        stringBuffer.insert(0, (char) (text.charAt(value) == '0' ? 'i' : 'I'));
      }
    }
    return stringBuffer.toString();
  }

  public static void setText(String text2) {
    text = text2;
  }
}
