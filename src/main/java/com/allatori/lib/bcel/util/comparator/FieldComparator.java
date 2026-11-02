package com.allatori.lib.bcel.util.comparator;

import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.util.BCELComparator;

public class FieldComparator implements BCELComparator {

  @Override
  public boolean equals(Object result, Object result2) {
    Field field = (Field) result;
    Field field2 = (Field) result2;
    return field.getText().equals(field2.getText()) && field.getText2().equals(field2.getText2());
  }

  @Override
  public int hashCode(Object result) {
    Field field;
    return (field = (Field) result).getText2().hashCode() ^ field.getText().hashCode();
  }
}
