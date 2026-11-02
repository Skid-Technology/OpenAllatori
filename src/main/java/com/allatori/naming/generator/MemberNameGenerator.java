package com.allatori.naming.generator;

public class MemberNameGenerator implements NameGenerator {

  public long seed;

  public String text;

  @Override
  public String nextName() {
    return "" + this.text + (this.seed++);
  }

  @Override
  public void reset() {
    this.seed = 0L;
  }

  public MemberNameGenerator(String text) {
    super();
    this.seed = 0L;
    this.text = text;
  }
}
