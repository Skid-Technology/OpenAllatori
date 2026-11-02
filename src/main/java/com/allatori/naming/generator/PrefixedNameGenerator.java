package com.allatori.naming.generator;

public class PrefixedNameGenerator implements NameGenerator {

  public String text;

  @Override
  public void reset() {}

  @Override
  public String nextName() {
    return this.text;
  }

  public PrefixedNameGenerator(String text) {
    super();
    this.text = text;
  }
}
