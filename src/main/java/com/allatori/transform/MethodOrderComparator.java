package com.allatori.transform;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Method;
import java.util.Comparator;

public class MethodOrderComparator implements Comparator {

  public final int count;

  public final MemberReorderTransformer memberReorderTransformer;

  @Override
  public int compare(Object result, Object result2) {
    return this.calculateCount((Method) result, (Method) result2);
  }

  public MethodOrderComparator(MemberReorderTransformer memberReorderTransformer2, int count) {
    super();
    this.memberReorderTransformer = memberReorderTransformer2;
    this.count = count;
  }

  public int calculateCount(Method method, Method method2) {
    int value = this.count;
    Configuration.getObfuscationOptions();
    return value == 100
        ? method2.getText().compareTo(method.getText())
        : method.getText().compareTo(method2.getText());
  }
}
