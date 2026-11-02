package com.allatori.naming.generator;

public class RandomizedNameGenerator implements NameGenerator {

  public NameGenerator nameGenerator;

  @Override
  public String nextName() {
    String text = this.nameGenerator.nextName();
    return !"r".equals(text) && !"R".equals(text) ? text : this.nextName();
  }

  @Override
  public void reset() {
    this.nameGenerator.reset();
  }

  public RandomizedNameGenerator(NameGenerator nameGenerator2) {
    super();
    this.nameGenerator = nameGenerator2;
  }
}
