package com.allatori.transform;

import com.allatori.bytecode.MethodFactory;
import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ACONST_NULL;
import com.allatori.lib.bcel.generic.ANEWARRAY;
import com.allatori.lib.bcel.generic.BIPUSH;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.DLOAD;
import com.allatori.lib.bcel.generic.ICONST;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionConstants;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InstructionList;
import com.allatori.lib.bcel.generic.InstructionTargeter;
import com.allatori.lib.bcel.generic.LLOAD;
import com.allatori.lib.bcel.generic.LineNumberGen;
import com.allatori.lib.bcel.generic.LoadInstruction;
import com.allatori.lib.bcel.generic.LocalVariableGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.NEW;
import com.allatori.lib.bcel.generic.NEWARRAY;
import com.allatori.lib.bcel.generic.PUSH;
import com.allatori.lib.bcel.generic.SIPUSH;
import com.allatori.model.ClassStorage;

public class ControlFlowTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  @Override
  public void finish() {}

  @Override
  public void transform(ClassGen classGen) {
    InstructionHandle instructionHandle;
    try {
      Method[] methods = classGen.getMethods();
      int value = methods.length;
      for (int index = 0; index < value; index++) {
        Method method;
        if ((method = methods[index]).getCode() != null) {
          MethodGen methodGen =
              MethodFactory.createMethodGen(
                  method,
                  classGen.getClassName(),
                  classGen.getConstantPool(),
                  classGen.getConstantPool().getConstantPool());
          if (Configuration.getObfuscationOptions()
              .matchesMethodGen(this.classStorage, classGen, methodGen)) {
            int value2;
            int value3 =
                value2 =
                    Configuration.getExtensiveFlowOptions()
                        .calculateCount(this.classStorage, classGen, methodGen);
            Configuration.getExtensiveFlowOptions();
            if (value3 != 0) {
              InstructionList instructionList;
              InstructionHandle start =
                  (instructionList = methodGen.getInstructionList()).getStart();
              byte value4 = 0;
              InstructionHandle instructionHandle2 = start;
              while (instructionHandle2 != null) {
                if (!(start.getInstruction() instanceof NEWARRAY)
                    && !(start.getInstruction() instanceof ANEWARRAY)) {
                  label115:
                  {
                    label114:
                    if (start.getInstruction() instanceof NEW) {
                      if (value4 != 0) {
                        Configuration.getExtensiveFlowOptions();
                        if (value2 != 2) {
                          break label114;
                        }
                      }
                      if (start.getInstructionHandle() == null) {
                        instructionHandle = start;
                        break label115;
                      }
                      if (!this.matchesInstructionTargeters(start.getTargeters())) {
                        instructionHandle = start;
                        break label115;
                      }
                      if (!this.matchesInstructionTargeters(
                          start.getInstructionHandle().getTargeters())) {
                        instructionHandle = start;
                        break label115;
                      }
                      if (!(start.getInstructionHandle().getInstruction() instanceof ICONST)
                          && !(start.getInstructionHandle().getInstruction()
                              instanceof ACONST_NULL)) {
                        instructionHandle = start;
                        break label115;
                      }
                      Instruction instruction = start.getInstructionHandle().getInstruction();
                      instructionList.createInstructionHandle2(
                          instructionList.createInstructionHandle2(
                              instructionList.createInstructionHandle2(start, instruction),
                              InstructionConstants.DUP),
                          InstructionConstants.POP2);
                      value4 = 1;
                    }
                    instructionHandle = start;
                  }
                  label98:
                  {
                    label97:
                    if (instructionHandle.getInstruction() instanceof NEW) {
                      if (value4 != 0) {
                        Configuration.getExtensiveFlowOptions();
                        if (value2 != 2) {
                          break label97;
                        }
                      }
                      if (start.getInstructionHandle() == null) {
                        value3 = value2;
                        break label98;
                      }
                      if (!this.matchesInstructionTargeters(start.getTargeters())) {
                        value3 = value2;
                        break label98;
                      }
                      if (!this.matchesInstructionTargeters(
                          start.getInstructionHandle().getTargeters())) {
                        value3 = value2;
                        break label98;
                      }
                      if (!(start.getInstructionHandle().getInstruction()
                          instanceof LoadInstruction)) {
                        value3 = value2;
                        break label98;
                      }
                      LoadInstruction loadInstruction;
                      if (!((loadInstruction =
                              (LoadInstruction) start.getInstructionHandle().getInstruction())
                          instanceof LLOAD)) {
                        if (loadInstruction instanceof DLOAD) {
                          value3 = value2;
                          break label98;
                        }
                        instructionList.createInstructionHandle2(
                            instructionList.createInstructionHandle2(
                                instructionList.createInstructionHandle2(
                                    start, loadInstruction.copy()),
                                InstructionConstants.DUP),
                            InstructionConstants.POP2);
                        value4 = 1;
                      }
                    }
                    value3 = value2;
                  }
                  Configuration.getExtensiveFlowOptions();
                  if (value3 != 2
                      || !(start.getInstruction() instanceof BIPUSH)
                          && !(start.getInstruction() instanceof SIPUSH)
                          && !(start.getInstruction() instanceof ICONST)) {
                    instructionHandle2 = start = start.getInstructionHandle2();
                  } else {
                    instructionList.createInstructionHandle2(
                        instructionList.createInstructionHandle2(
                            instructionList.getInstructionHandle2(
                                start, new PUSH(classGen.getConstantPool(), 1)),
                            InstructionConstants.DUP),
                        InstructionConstants.POP2);
                    start =
                        start
                            .getInstructionHandle2()
                            .getInstructionHandle2()
                            .getInstructionHandle2()
                            .getInstructionHandle2();
                    instructionHandle2 = start;
                  }
                } else {
                  instructionList.createInstructionHandle2(
                      instructionList.createInstructionHandle2(
                          instructionList.getInstructionHandle2(
                              start, new PUSH(classGen.getConstantPool(), 1)),
                          InstructionConstants.DUP),
                      InstructionConstants.POP2);
                  start =
                      start
                          .getInstructionHandle2()
                          .getInstructionHandle2()
                          .getInstructionHandle2()
                          .getInstructionHandle2();
                  instructionHandle2 = start;
                }
              }
              methodGen.update5();
              methodGen.update4();
              classGen.replaceMethod(method, methodGen.getMethod());
            }
          }
        }
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  public ControlFlowTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }

  public boolean matchesInstructionTargeters(InstructionTargeter[] instructionTargeters) {
    if (instructionTargeters == null) {
      return true;
    }
    int value;
    for (int value2 = value = 0; value2 < instructionTargeters.length; value2 = ++value) {
      InstructionTargeter instructionTargeter;
      if (!((instructionTargeter = instructionTargeters[value]) instanceof LineNumberGen)
          && !(instructionTargeter instanceof LocalVariableGen)) {
        return false;
      }
    }
    return true;
  }
}
