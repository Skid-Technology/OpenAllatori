package com.allatori.transform;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Field;
import java.util.Comparator;

public class FieldOrderComparator implements Comparator {

  public final int count;

  public final MemberReorderTransformer memberReorderTransformer;

  @Override
  public int compare(Object result, Object result2) {
    return this.calculateCount((Field) result, (Field) result2);
  }

  public int calculateCount(Field field, Field field2) {
    int value = this.count;
    Configuration.getObfuscationOptions();
    return value == 100
        ? field2.getText().compareTo(field.getText())
        : field.getText().compareTo(field2.getText());
  }

  public FieldOrderComparator(MemberReorderTransformer memberReorderTransformer2, int count) {
    super();
    this.memberReorderTransformer = memberReorderTransformer2;
    this.count = count;
  }
}
