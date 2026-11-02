package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;

public class ATHROW extends Instruction implements UnconditionalBranch, ExceptionThrower {

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE10};
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitUnconditionalBranch(this);
    visitor.visitExceptionThrower(this);
    visitor.visitATHROW(this);
  }

  public ATHROW() {
    super((short) 191, (short) 1);
  }
}
