package com.allatori.transform;

import com.allatori.bytecode.MethodFactory;
import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.InstructionFactory;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InstructionList;
import com.allatori.lib.bcel.generic.InstructionTargeter;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.model.ClassStorage;

public class FieldAccessTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  @Override
  public void finish() {}

  @Override
  public void transform(ClassGen classGen) {
    String text2 = Configuration.getDebugInfoOptions().getText2();
    String text = Configuration.getDebugInfoOptions().getText();
    if (text2 != null && text != null) {
      if (Configuration.getDebugInfoOptions().matchesClassGen(this.classStorage, classGen)) {
        try {
          Method[] methods = classGen.getMethods();
          for (int index = 0; index < methods.length; index++) {
            Method method;
            if ((method = methods[index]).getCode() != null) {
              MethodGen methodGen =
                  MethodFactory.createMethodGen(
                      method,
                      classGen.getClassName(),
                      classGen.getConstantPool(),
                      classGen.getConstantPool().getConstantPool());
              if (Configuration.getDebugInfoOptions()
                  .matchesMethodGen(this.classStorage, classGen, methodGen)) {
                InstructionFactory instructionFactory = new InstructionFactory(classGen);
                InstructionList instructionList;
                InstructionHandle end = (instructionList = methodGen.getInstructionList()).getEnd();
                InstructionHandle instructionHandle;
                InstructionHandle instructionHandle2;
                if (Configuration.getDebugInfoOptions().isEnabled()) {
                  InstructionHandle instructionHandle3 =
                      instructionList.createInstructionHandle(
                          end,
                          instructionFactory.createInvoke(
                              text2, text, Type.VOID, new Type[] {Type.OBJECT}, (short) 184));
                  instructionHandle =
                      instructionList.createInstructionHandle(
                          instructionHandle3, InstructionFactory.createThis());
                  instructionHandle2 = end;
                } else {
                  instructionHandle =
                      instructionList.createInstructionHandle(
                          end,
                          instructionFactory.createInvoke(
                              text2, text, Type.VOID, Type.NO_ARGS, (short) 184));
                  instructionHandle2 = end;
                }
                InstructionTargeter[] instructionTargeters;
                int value;
                for (int value2 =
                        value =
                            (instructionTargeters = instructionHandle2.getTargeters()).length - 1;
                    value2 >= 0;
                    value2 = value) {
                  InstructionTargeter instructionTargeter = instructionTargeters[value];
                  value--;
                  instructionTargeter.updateTarget(end, instructionHandle);
                }
                methodGen.update5();
                classGen.replaceMethod(method, methodGen.getMethod());
              }
            }
          }
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    }
  }

  public FieldAccessTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }
}
