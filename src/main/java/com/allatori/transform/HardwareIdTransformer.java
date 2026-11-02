package com.allatori.transform;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;

public class HardwareIdTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  @Override
  public void transform(ClassGen classGen) {
    try {
      int value;
      ClassGen classGen2;
      if ((classGen2 = this.classStorage.getClassGen("com.license4j.HardwareID")) != null
          && (value = classGen2.getConstantPool().lookupUtf8("resources/d.bfi")) != -1) {
        ((ConstantUtf8) classGen2.getConstantPool().getConstant(value))
            .setBytes(Configuration.getLogOptions().getComLicense4jSupport());
        return;
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public HardwareIdTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }

  @Override
  public void finish() {}
}
