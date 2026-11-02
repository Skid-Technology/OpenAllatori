package com.allatori.naming.generator;

public class MethodNameGenerator implements NameGenerator {

  public NameGenerator nameGenerator;

  public String text;

  public int count;

  @Override
  public String nextName() {
    return this.count++ == 0 ? this.text : this.nameGenerator.nextName();
  }

  public MethodNameGenerator(String text, NameGenerator nameGenerator2) {
    super();
    this.count = 0;
    this.text = text;
    this.nameGenerator = nameGenerator2;
  }

  @Override
  public void reset() {
    this.count = 0;
    this.nameGenerator.reset();
  }
}
