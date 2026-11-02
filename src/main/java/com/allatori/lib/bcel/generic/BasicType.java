package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.Constants;

public final class BasicType extends Type {

  public BasicType(byte value) {
    super(value, Constants.TEXTS3[value]);
    if (value < 4 || value > 12) {
      throw new ClassGenException(("Invalid type: " + value));
    }
  }

  public static final BasicType getType(byte value) {
    switch (value) {
      case 4:
        return BOOLEAN;
      case 5:
        return CHAR;
      case 6:
        return FLOAT;
      case 7:
        return DOUBLE;
      case 8:
        return BYTE;
      case 9:
        return SHORT;
      case 10:
        return INT;
      case 11:
        return LONG;
      case 12:
        return VOID;
      default:
        throw new ClassGenException(("Invalid type: " + value));
    }
  }

  @Override
  public int hashCode() {
    return this.type;
  }

  @Override
  public boolean equals(Object result) {
    return result instanceof BasicType ? ((BasicType) result).type == this.type : false;
  }
}
