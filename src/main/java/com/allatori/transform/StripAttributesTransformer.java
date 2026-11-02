package com.allatori.transform;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.InnerClass;
import com.allatori.lib.bcel.classfile.InnerClasses;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import java.util.Iterator;
import java.util.Vector;

public class StripAttributesTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  public StripAttributesTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }

  @Override
  public void finish() {}

  @Override
  public void transform(ClassGen classGen) {
    String text;
    if ((text = Configuration.getMethodRemovalOptions().getTestString()) != null) {
      ClassGen classGen2;
      if ((classGen2 = this.classStorage.getClassGen(text)) != null) {
        Vector vector = new Vector();
        Iterator classesByName2 = this.classStorage.getClassesByName2();
        String text2 = "" + text + "$";
        while (classesByName2.hasNext()) {
          ClassGen classGen3;
          if ((classGen3 = (ClassGen) classesByName2.next()).getClassName().startsWith(text2)) {
            InnerClass innerClass =
                new InnerClass(
                    classGen2.getConstantPool().addClass(classGen3.getClassName()), 0, 0, 0);
            vector.add(innerClass);
          }
        }
        classGen2.processAttribute(
            new InnerClasses(
                classGen2.getConstantPool().addUtf8("InnerClasses"),
                vector.size() * 8 + 2,
                (InnerClass[]) vector.toArray(new InnerClass[0]),
                classGen2.getConstantPool().getConstantPool()));
      }
    }
  }
}
