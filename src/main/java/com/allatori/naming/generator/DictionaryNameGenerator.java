package com.allatori.naming.generator;

public class DictionaryNameGenerator implements NameGenerator {

  public AsciiNameGenerator asciiNameGenerator;

  public int count;

  public String[] texts;

  @Override
  public String nextName() {
    return this.count < this.texts.length
        ? this.texts[this.count++]
        : this.asciiNameGenerator.nextName();
  }

  public DictionaryNameGenerator(String[] texts2) {
    super();
    this.asciiNameGenerator = new AsciiNameGenerator();
    this.count = 0;
    this.texts = texts2;
  }

  @Override
  public void reset() {
    this.count = 0;
    this.asciiNameGenerator.reset();
  }
}
