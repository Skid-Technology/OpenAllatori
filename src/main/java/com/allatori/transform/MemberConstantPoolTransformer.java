package com.allatori.transform;

import com.allatori.bytecode.ConstantPoolEditor;
import com.allatori.bytecode.MethodFactory;
import com.allatori.crypto.CustomStringEncryption;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.LDC;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.ClassStorage;

public class MemberConstantPoolTransformer implements ClassTransformer {

  public ConstantPoolEditor constantPoolEditor;

  public ClassStorage classStorage;

  public void transform2(ClassGen classGen) {
    InstructionHandle instructionHandle;
    Method[] methods = classGen.getMethods();
    int value;
    for (int value2 = value = 0; value2 < methods.length; value2 = ++value) {
      Method method;
      if ((method = methods[value]).getCode() != null) {
        MethodGen methodGen;
        InstructionHandle instructionHandle2;
        InstructionHandle instructionHandle3 =
            instructionHandle2 =
                (methodGen =
                        MethodFactory.createMethodGen(
                            method,
                            classGen.getClassName(),
                            classGen.getConstantPool(),
                            classGen.getConstantPool().getConstantPool()))
                    .getInstructionList()
                    .getStart();
        while (instructionHandle3 != null) {
          label38:
          {
            if (instructionHandle2.getInstruction() instanceof LDC) {
              LDC lDC = (LDC) instructionHandle2.getInstruction();
              Constant constant;
              if ((constant = classGen.getConstantPool().getConstant(lDC.getIndex()))
                  instanceof ConstantString) {
                String text2;
                if ((text2 =
                            ((ConstantUtf8)
                                    classGen
                                        .getConstantPool()
                                        .getConstant(((ConstantString) constant).getStringIndex()))
                                .getBytes())
                        != null
                    && text2.startsWith(GlobalStringEncryptionTransformer.text4)) {
                  String text3 = text2.substring(GlobalStringEncryptionTransformer.text4.length());
                  String text5 = "" + classGen.getClassName() + methodGen.getName();
                  instructionHandle = instructionHandle2;
                  lDC.setIndex(
                      classGen
                          .getConstantPool()
                          .addString(GlobalStringEncryptionTransformer.createText(text3, text5)));
                  break label38;
                }
                if (text2 != null && text2.startsWith(CustomStringEncryption.text)) {
                  lDC.setIndex(
                      classGen
                          .getConstantPool()
                          .addString(
                              CustomStringEncryption.createText(
                                  text2,
                                  this.classStorage.getClassLoader(),
                                  classGen.getClassName(),
                                  methodGen.getName())));
                }
              }
            }
            instructionHandle = instructionHandle2;
          }
          instructionHandle3 = instructionHandle2 = instructionHandle.getInstructionHandle2();
        }
        methodGen.update5();
        classGen.replaceMethod(method, methodGen.getMethod());
      }
    }
  }

  @Override
  public void transform(ClassGen classGen) {
    if (GlobalStringEncryptionTransformer.values.contains(classGen)) {
      try {
        this.transform2(classGen);
      } catch (Exception exception) {
        this.constantPoolEditor.processClassGen(classGen);
        this.transform2(classGen);
      }
    }
  }

  @Override
  public void finish() {
    GlobalStringEncryptionTransformer.values = null;
  }

  public MemberConstantPoolTransformer(ClassStorage classStorage2) {
    super();
    this.constantPoolEditor = new ConstantPoolEditor(null);
    this.classStorage = classStorage2;
  }
}
