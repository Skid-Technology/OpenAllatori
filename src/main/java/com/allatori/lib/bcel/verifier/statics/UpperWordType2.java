package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.generic.Type;

public final class UpperWordType2 extends Type {

  public static UpperWordType2 upperWordType2 = new UpperWordType2();

  public static UpperWordType2 getUpperWordType2() {
    return upperWordType2;
  }

  public UpperWordType2() {
    super((byte) 15, "Long_Upper");
  }
}
