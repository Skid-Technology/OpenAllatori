package com.allatori.config;

public class LineNumberOptions {

  public static final int COUNT = 3;

  public final RenamingOptions renamingOptions;

  public int lineNumbersMode;

  public static final int COUNT2 = 2;

  public static final int COUNT3 = 1;

  public void setLineNumbersMode(int lineNumbersMode) {
    this.lineNumbersMode = lineNumbersMode;
  }

  public int getLineNumbersMode() {
    return this.lineNumbersMode;
  }

  public LineNumberOptions(RenamingOptions renamingOptions2) {
    super();
    this.renamingOptions = renamingOptions2;
    this.lineNumbersMode = 1;
  }
}
