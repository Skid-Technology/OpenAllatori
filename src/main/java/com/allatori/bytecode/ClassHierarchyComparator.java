package com.allatori.bytecode;

import com.allatori.lib.bcel.generic.ClassGen;
import java.util.Comparator;

public class ClassHierarchyComparator implements Comparator {

  public final ClassHierarchy classHierarchy;

  @Override
  public int compare(Object result, Object result2) {
    String className = ((ClassGen) result).getClassName();
    String className2 = ((ClassGen) result2).getClassName();
    return className.compareTo(className2);
  }

  public ClassHierarchyComparator(ClassHierarchy classHierarchy2) {
    super();
    this.classHierarchy = classHierarchy2;
  }
}
