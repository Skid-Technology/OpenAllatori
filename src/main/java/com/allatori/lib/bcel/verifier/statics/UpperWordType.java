package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.generic.Type;

public final class UpperWordType extends Type {

  public static UpperWordType upperWordType = new UpperWordType();

  public UpperWordType() {
    super((byte) 15, "Long_Upper");
  }

  public static UpperWordType getUpperWordType() {
    return upperWordType;
  }
}
