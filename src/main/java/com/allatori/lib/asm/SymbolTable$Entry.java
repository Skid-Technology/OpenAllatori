package com.allatori.lib.asm;

public final class SymbolTable$Entry extends Symbol {

  public SymbolTable$Entry next;

  public final int hashCode;

  public SymbolTable$Entry(int value, int value2, String text, long value3, int hashCode) {
    super(value, value2, null, null, text, value3);
    this.hashCode = hashCode;
  }

  public SymbolTable$Entry(int value, int value2, long value3, int hashCode) {
    super(value, value2, null, null, null, value3);
    this.hashCode = hashCode;
  }

  public SymbolTable$Entry(int value, int value2, String text, String text2, int hashCode) {
    super(value, value2, null, text, text2, 0L);
    this.hashCode = hashCode;
  }

  public SymbolTable$Entry(int value, int value2, String text, int hashCode) {
    super(value, value2, null, null, text, 0L);
    this.hashCode = hashCode;
  }

  public SymbolTable$Entry(
      int value, int value2, String text, String text2, String text3, long value3, int hashCode) {
    super(value, value2, text, text2, text3, value3);
    this.hashCode = hashCode;
  }
}
