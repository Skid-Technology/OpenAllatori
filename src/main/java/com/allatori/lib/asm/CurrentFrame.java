package com.allatori.lib.asm;

public final class CurrentFrame extends Frame {

  public CurrentFrame(Label label) {
    super(label);
  }

  @Override
  public void execute(int value, int value2, Symbol symbol, SymbolTable symbolTable)
      throws ClassNotFoundException {
    super.execute(value, value2, symbol, symbolTable);
    Frame frame = new Frame(null);
    this.merge(symbolTable, frame, 0);
    this.copyFrom(frame);
  }
}
