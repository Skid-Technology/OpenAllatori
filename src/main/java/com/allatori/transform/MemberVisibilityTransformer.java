package com.allatori.transform;

import com.allatori.bytecode.MethodFactory;
import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.EnclosingMethod;
import com.allatori.lib.bcel.classfile.ExceptionTable;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.InnerClasses;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.MethodParameters;
import com.allatori.lib.bcel.classfile.RuntimeVisibleParameterAnnotations;
import com.allatori.lib.bcel.classfile.Signature;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.FieldGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.ClassStorage;

public class MemberVisibilityTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  public void processFieldGen(ClassGen classGen, MethodGen methodGen, FieldGen fieldGen) {
    Attribute[] attributes = null;
    if (classGen != null && methodGen == null && fieldGen == null) {
      attributes = classGen.getAttributes();
    }
    if (methodGen != null) {
      attributes = methodGen.getAttributes();
    }
    if (fieldGen != null) {
      attributes = fieldGen.getAttributes();
    }
    if (attributes != null) {
      int value;
      for (int value2 = value = attributes.length - 1; value2 >= 0; value2 = --value) {
        if (attributes[value] instanceof Signature) {
          if (methodGen != null) {
            methodGen.removeAttribute(attributes[value]);
          } else if (fieldGen != null) {
            fieldGen.removeAttribute(attributes[value]);
          } else {
            classGen.processAttribute2(attributes[value]);
          }
        }
      }
    }
  }

  @Override
  public void finish() {}

  @Override
  public void transform(ClassGen classGen) {
    int value;
    if (Configuration.getObfuscationOptions().matchesClassGen(this.classStorage, classGen)) {
      this.processFieldGen(classGen, null, null);
      int value2;
      Method[] methods;
      for (int value3 = value2 = (methods = classGen.getMethods()).length - 1;
          value3 >= 0;
          value3 = --value2) {
        Attribute[] attributes;
        int value4;
        Method method;
        if ((attributes = (method = methods[value2]).getAttributes()) != null) {
          for (int value5 = value4 = attributes.length - 1; value5 >= 0; value5 = --value4) {
            if (attributes[value4] instanceof Signature) {
              MethodGen methodGen =
                  MethodFactory.createMethodGen(
                      method,
                      classGen.getClassName(),
                      classGen.getConstantPool(),
                      classGen.getConstantPool().getConstantPool());
              this.processFieldGen(null, methodGen, null);
              classGen.replaceMethod(method, methodGen.getMethod());
            }
          }
        }
      }
      int value6;
      Field[] fields;
      for (int value7 = value6 = (fields = classGen.getFields()).length - 1;
          value7 >= 0;
          value7 = --value6) {
        int value8;
        Attribute[] attributes2;
        Field field;
        if ((attributes2 = (field = fields[value6]).getAttributes()) != null) {
          for (int value9 = value8 = attributes2.length - 1; value9 >= 0; value9 = --value8) {
            if (attributes2[value8] instanceof Signature) {
              FieldGen fieldGen = new FieldGen(field, classGen.getConstantPool());
              this.processFieldGen(null, null, fieldGen);
              classGen.replaceField(field, fieldGen.getField());
            }
          }
        }
      }
    }
    if (Configuration.getObfuscationOptions().matchesClassGen2(this.classStorage, classGen)) {
      this.transform2(classGen);
    }
    int value10;
    Method[] methods2;
    for (int value11 = value10 = (methods2 = classGen.getMethods()).length - 1;
        value11 >= 0;
        value11 = --value10) {
      Method method2 = methods2[value10];
      int value12;
      Attribute[] attributes3;
      if (Configuration.getObfuscationOptions().matchesMethod(this.classStorage, classGen, method2)
          && (attributes3 = method2.getAttributes()) != null) {
        for (int value13 = value12 = attributes3.length - 1; value13 >= 0; value13 = --value12) {
          if (attributes3[value12] instanceof ExceptionTable) {
            MethodGen methodGen2 =
                MethodFactory.createMethodGen(
                    method2,
                    classGen.getClassName(),
                    classGen.getConstantPool(),
                    classGen.getConstantPool().getConstantPool());
            methodGen2.removeExceptions();
            classGen.replaceMethod(method2, methodGen2.getMethod());
            break;
          }
        }
      }
    }
    int value14;
    Method[] methods3;
    for (int value15 = value14 = (methods3 = classGen.getMethods()).length - 1;
        value15 >= 0;
        value15 = --value14) {
      Method method3 = methods3[value14];
      int value16;
      value15 =
          value16 =
              Configuration.getRenamingOptions()
                  .getLocalVariableOptions()
                  .calculateCount(this.classStorage, classGen, method3);
      Configuration.getRenamingOptions().getLocalVariableOptions();
      if (value15 != 3) {
        Configuration.getRenamingOptions().getLocalVariableOptions();
        Attribute[] attributes4;
        if (value16 != 5 && (attributes4 = method3.getAttributes()) != null) {
          byte value17 = 0;
          int value18;
          value15 = value18 = attributes4.length - 1;
          while (true) {
            if (value15 < 0) {
              value = value17;
              break;
            }
            if (attributes4[value18] instanceof RuntimeVisibleParameterAnnotations) {
              value = 1;
              break;
            }
            value15 = --value18;
          }
          int value19;
          if (value == 0) {
            for (int value20 = value19 = attributes4.length - 1;
                value20 >= 0;
                value20 = --value19) {
              if (attributes4[value19] instanceof MethodParameters) {
                MethodGen methodGen3 =
                    MethodFactory.createMethodGen(
                        method3,
                        classGen.getClassName(),
                        classGen.getConstantPool(),
                        classGen.getConstantPool().getConstantPool());
                methodGen3.removeAttribute(attributes4[value19]);
                classGen.replaceMethod(method3, methodGen3.getMethod());
                break;
              }
            }
          }
        }
      }
    }
  }

  public MemberVisibilityTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }

  public void transform2(ClassGen classGen) {
    Attribute[] attributes = null;
    if (classGen != null) {
      attributes = classGen.getAttributes();
    }
    if (attributes != null) {
      int value;
      for (int value2 = value = attributes.length - 1; value2 >= 0; value2 = --value) {
        if (attributes[value] instanceof InnerClasses
            || attributes[value] instanceof EnclosingMethod) {
          classGen.processAttribute2(attributes[value]);
        }
      }
    }
  }
}
