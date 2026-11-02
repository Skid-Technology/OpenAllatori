package com.allatori.naming.generator;

public class TypeNameGenerator implements NameGenerator {

  public String text;

  public NameGenerator nameGenerator;

  public TypeNameGenerator(String text, NameGenerator nameGenerator2) {
    super();
    this.nameGenerator = nameGenerator2;
    this.text = text;
  }

  @Override
  public String nextName() {
    return "" + this.text + this.nameGenerator.nextName();
  }

  @Override
  public void reset() {
    this.nameGenerator.reset();
  }
}
