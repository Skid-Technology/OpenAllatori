package com.allatori.transform;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;

public class MethodAccessTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  @Override
  public void transform(ClassGen classGen) {
    try {
      int value;
      Field[] fields;
      for (int value2 = value = (fields = classGen.getFields()).length - 1;
          value2 >= 0;
          value2 = --value) {
        Field field = fields[value];
        if (Configuration.getSyntheticOptions().matchesField(this.classStorage, classGen, field)) {
          field.processEnabled9(true);
        }
      }
      Method[] methods;
      for (int value3 = (methods = classGen.getMethods()).length - 1; value3 >= 0; value3--) {
        Method method = methods[value3];
        if (Configuration.getSyntheticOptions()
            .matchesMethod(this.classStorage, classGen, method)) {
          method.processEnabled9(true);
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public MethodAccessTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }

  @Override
  public void finish() {}
}
