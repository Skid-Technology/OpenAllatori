package com.allatori.lib.bcel.util.comparator;

import com.allatori.lib.bcel.generic.FieldGen;
import com.allatori.lib.bcel.util.BCELComparator;

public class FieldGenComparator implements BCELComparator {

  @Override
  public boolean equals(Object result, Object result2) {
    FieldGen fieldGen = (FieldGen) result;
    FieldGen fieldGen2 = (FieldGen) result2;
    return fieldGen.getName().equals(fieldGen2.getName())
        && fieldGen.getText().equals(fieldGen2.getText());
  }

  @Override
  public int hashCode(Object result) {
    FieldGen fieldGen;
    return (fieldGen = (FieldGen) result).getText().hashCode() ^ fieldGen.getName().hashCode();
  }
}
