package com.allatori.lib.asm;

public final class SymbolTable$LabelEntry {

  public final int index;

  public final Label label;

  public SymbolTable$LabelEntry next;

  public SymbolTable$LabelEntry(int index, Label label2) {
    super();
    this.index = index;
    this.label = label2;
  }
}
