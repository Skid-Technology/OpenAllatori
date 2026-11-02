package com.allatori.stacktrace;

public class StackTraceMappingEntry {

  public int count;

  public int count2;

  public String text;

  public StackTraceMappingEntry(
      String text, int value, int value2, MappingConstructorToken mappingConstructorToken) {
    this(text, value, value2);
  }

  public StackTraceMappingEntry(String text, int count2, int count) {
    super();
    this.text = text;
    this.count2 = count2;
    this.count = count;
  }

  public static int calculateCount(StackTraceMappingEntry stackTraceMappingEntry) {
    return stackTraceMappingEntry.count2;
  }

  public static String getText(StackTraceMappingEntry stackTraceMappingEntry) {
    return stackTraceMappingEntry.text;
  }

  public static int calculateCount2(StackTraceMappingEntry stackTraceMappingEntry) {
    return stackTraceMappingEntry.count;
  }
}
