package com.allatori.lib.bcel.util.comparator;

import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.util.BCELComparator;

public class ConstantComparator implements BCELComparator {

  @Override
  public boolean equals(Object result, Object result2) {
    Constant constant = (Constant) result;
    Constant constant2 = (Constant) result2;
    return constant.toString().equals(constant2.toString());
  }

  @Override
  public int hashCode(Object result) {
    return ((Constant) result).toString().hashCode();
  }
}
