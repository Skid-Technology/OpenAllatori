package com.allatori.lib.bcel.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class InstructionList$1 implements Iterator {

  public final InstructionList this$0;

  public InstructionHandle ih;

  @Override
  public boolean hasNext() {
    return this.ih != null;
  }

  public InstructionList$1(InstructionList instructionList) {
    super();
    this.this$0 = instructionList;
    this.ih = InstructionList.access$000(this.this$0);
  }

  @Override
  public Object next() {
    if (this.ih == null) {
      throw new NoSuchElementException();
    }
    InstructionHandle instructionHandle = this.ih;
    this.ih = this.ih.instructionHandle2;
    return instructionHandle;
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
