package com.allatori.lib.bcel.util.comparator;

import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.util.BCELComparator;

public class MethodGenComparator implements BCELComparator {

  @Override
  public int hashCode(Object result) {
    MethodGen methodGen;
    return (methodGen = (MethodGen) result).getText().hashCode() ^ methodGen.getName().hashCode();
  }

  @Override
  public boolean equals(Object result, Object result2) {
    MethodGen methodGen = (MethodGen) result;
    MethodGen methodGen2 = (MethodGen) result2;
    return methodGen.getName().equals(methodGen2.getName())
        && methodGen.getText().equals(methodGen2.getText());
  }
}
