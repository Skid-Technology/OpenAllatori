package com.allatori.transform;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;

public class RemoveMethodsTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  @Override
  public void transform(ClassGen classGen) {
    String text = "toString";
    String text2 = "()Ljava/lang/String;";
    int value;
    Method[] methods;
    for (int value2 = value = (methods = classGen.getMethods()).length - 1;
        value2 >= 0;
        value2 = --value) {
      Method method = methods[value];
      if (Configuration.getOptimizationOptions().matchesClassGen(this.classStorage, classGen)
          && text.equals(method.getText())
          && text2.equals(method.getText2())) {
        classGen.processMethod(method);
        Log.info(" [remove-methods] removed " + classGen.getClassName() + "." + text + text2);
      }
      if (Configuration.getOptimizationOptions()
          .matchesMethod(this.classStorage, classGen, method)) {
        classGen.processMethod(method);
        String text3 = "" + classGen.getClassName() + "." + method.getText() + method.getText2();
        Configuration.getOptimizationOptions().processText(text3);
        Log.info(" [remove-methods] removed " + text3);
      }
    }
  }

  @Override
  public void finish() {}

  public RemoveMethodsTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }
}
