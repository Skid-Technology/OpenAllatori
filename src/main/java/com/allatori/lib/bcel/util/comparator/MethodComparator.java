package com.allatori.lib.bcel.util.comparator;

import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.util.BCELComparator;

public class MethodComparator implements BCELComparator {

  public static MethodComparator methodComparator = new MethodComparator();

  @Override
  public boolean equals(Object result, Object result2) {
    Method method = (Method) result;
    Method method2 = (Method) result2;
    return method.getText().equals(method2.getText())
        && method.getText2().equals(method2.getText2());
  }

  @Override
  public int hashCode(Object result) {
    Method method;
    return (method = (Method) result).getText2().hashCode() ^ method.getText().hashCode();
  }

  public static MethodComparator getMethodComparator() {
    return methodComparator;
  }
}
