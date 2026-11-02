package com.allatori.naming.generator;

import java.util.Vector;

public class MainNameGenerator implements NameGenerator {

  public boolean enabled;

  public boolean enabled2;

  public static Vector values = new Vector();

  public NameGenerator nameGenerator;

  public static Vector values2 = new Vector();

  public int count;

  public static void processText(String text) {
    if (text.charAt(0) != '<' && !"main".equals(text)) {
      if (!values2.contains(text)) {
        values2.add(text);
      }
    }
  }

  @Override
  public void reset() {
    this.count = 0;
    this.nameGenerator.reset();
  }

  public static void reset2() {
    values = new Vector();
    values2 = new Vector();
  }

  public static void processText2(String text) {
    if (!values.contains(text)) {
      values.add(text);
    }
  }

  public MainNameGenerator(NameGenerator nameGenerator2, boolean enabled2, boolean enabled) {
    super();
    this.count = 0;
    this.nameGenerator = nameGenerator2;
    this.enabled2 = enabled2;
    this.enabled = enabled;
  }

  @Override
  public String nextName() {
    if (this.enabled2 && this.count < values.size()) {
      return (String) values.get(this.count++);
    } else {
      return this.enabled && this.count < values2.size()
          ? (String) values2.get(this.count++)
          : this.nameGenerator.nextName();
    }
  }
}
