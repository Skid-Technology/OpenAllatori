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

public class ClassConstantPoolTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  public ConstantPoolEditor constantPoolEditor;

  public ClassConstantPoolTransformer(ClassStorage classStorage2) {
    super();
    this.constantPoolEditor = new ConstantPoolEditor(null);
    this.classStorage = classStorage2;
  }

  @Override
  public void finish() {
    DistributedStringEncryptionTransformer.entries6 = null;
    DistributedStringEncryptionTransformer.values = null;
  }

  @Override
  public void transform(ClassGen classGen) {
    if (DistributedStringEncryptionTransformer.values.contains(classGen)) {
      try {
        this.transform2(classGen);
      } catch (Exception exception) {
        this.constantPoolEditor.processClassGen(classGen);
        this.transform2(classGen);
      }
    }
  }

  public void transform2(ClassGen classGen) {
    InstructionHandle instructionHandle;
    Method[] methods = classGen.getMethods();
    int value;
    for (int value2 = value = 0; value2 < methods.length; value2 = ++value) {
      Method method;
      if ((method = methods[value]).getCode() != null) {
        InstructionHandle instructionHandle2;
        MethodGen methodGen;
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
                String text3;
                if ((text3 =
                            ((ConstantUtf8)
                                    classGen
                                        .getConstantPool()
                                        .getConstant(((ConstantString) constant).getStringIndex()))
                                .getBytes())
                        != null
                    && text3.startsWith(DistributedStringEncryptionTransformer.text2)) {
                  String text4 =
                      text3.substring(DistributedStringEncryptionTransformer.text2.length());
                  String text5;
                  String text6 =
                      text4.substring(
                          (text5 =
                                      text4.substring(
                                          0,
                                          text4.indexOf(
                                              DistributedStringEncryptionTransformer.text2)))
                                  .length()
                              + DistributedStringEncryptionTransformer.text2.length());
                  instructionHandle = instructionHandle2;
                  lDC.setIndex(
                      classGen
                          .getConstantPool()
                          .addString(
                              DistributedStringEncryptionTransformer.createText(
                                  text6, classGen.getClassName(), methodGen.getName(), text5)));
                  break label38;
                }
                if (text3 != null && text3.startsWith(CustomStringEncryption.text)) {
                  lDC.setIndex(
                      classGen
                          .getConstantPool()
                          .addString(
                              CustomStringEncryption.createText(
                                  text3,
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
}
