package com.allatori.lib.bcel.generic;

import com.allatori.bytecode.ClassHierarchy;
import java.util.Comparator;

public class MethodGen$2 implements Comparator {

  public final ClassHierarchy classHierarchy;

  @Override
  public int compare(Object result, Object result2) {
    boolean enabled5 = ((ClassGen) result).isEnabled5();
    boolean enabled52 = ((ClassGen) result2).isEnabled5();
    if (enabled5 == enabled52) {
      return 0;
    } else {
      return enabled5 ? -1 : 1;
    }
  }

  public MethodGen$2(ClassHierarchy classHierarchy2) {
    super();
    this.classHierarchy = classHierarchy2;
  }
}
