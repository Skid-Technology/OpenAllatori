package com.allatori.optimizer;

import com.allatori.bytecode.MethodFactory;
import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ACONST_NULL;
import com.allatori.lib.bcel.generic.ALOAD;
import com.allatori.lib.bcel.generic.ANEWARRAY;
import com.allatori.lib.bcel.generic.ASTORE;
import com.allatori.lib.bcel.generic.ATHROW;
import com.allatori.lib.bcel.generic.ArithmeticInstruction;
import com.allatori.lib.bcel.generic.ArrayInstruction;
import com.allatori.lib.bcel.generic.ArrayType;
import com.allatori.lib.bcel.generic.BIPUSH;
import com.allatori.lib.bcel.generic.BranchInstruction;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.ClassGenException;
import com.allatori.lib.bcel.generic.ClassObserver;
import com.allatori.lib.bcel.generic.CodeExceptionGen;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.ConstantPushInstruction;
import com.allatori.lib.bcel.generic.ConversionInstruction;
import com.allatori.lib.bcel.generic.DCONST;
import com.allatori.lib.bcel.generic.DLOAD;
import com.allatori.lib.bcel.generic.DSTORE;
import com.allatori.lib.bcel.generic.DUP;
import com.allatori.lib.bcel.generic.FCONST;
import com.allatori.lib.bcel.generic.FLOAD;
import com.allatori.lib.bcel.generic.FSTORE;
import com.allatori.lib.bcel.generic.FieldInstruction;
import com.allatori.lib.bcel.generic.FieldOrMethod;
import com.allatori.lib.bcel.generic.GETFIELD;
import com.allatori.lib.bcel.generic.GETSTATIC;
import com.allatori.lib.bcel.generic.GOTO;
import com.allatori.lib.bcel.generic.GotoInstruction;
import com.allatori.lib.bcel.generic.ICONST;
import com.allatori.lib.bcel.generic.IFNE;
import com.allatori.lib.bcel.generic.IINC;
import com.allatori.lib.bcel.generic.ILOAD;
import com.allatori.lib.bcel.generic.INVOKESPECIAL;
import com.allatori.lib.bcel.generic.INVOKEVIRTUAL;
import com.allatori.lib.bcel.generic.ISTORE;
import com.allatori.lib.bcel.generic.IfInstruction;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionConstants;
import com.allatori.lib.bcel.generic.InstructionFactory;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InstructionList;
import com.allatori.lib.bcel.generic.InstructionTargeter;
import com.allatori.lib.bcel.generic.InvokeInstruction;
import com.allatori.lib.bcel.generic.LCONST;
import com.allatori.lib.bcel.generic.LDC;
import com.allatori.lib.bcel.generic.LDC2_W;
import com.allatori.lib.bcel.generic.LLOAD;
import com.allatori.lib.bcel.generic.LSTORE;
import com.allatori.lib.bcel.generic.LineNumberGen;
import com.allatori.lib.bcel.generic.LoadInstruction;
import com.allatori.lib.bcel.generic.LocalVariableGen;
import com.allatori.lib.bcel.generic.LocalVariableInstruction;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.NEW;
import com.allatori.lib.bcel.generic.NEWARRAY;
import com.allatori.lib.bcel.generic.NOP;
import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.POP;
import com.allatori.lib.bcel.generic.POP2;
import com.allatori.lib.bcel.generic.PUSH;
import com.allatori.lib.bcel.generic.PopInstruction;
import com.allatori.lib.bcel.generic.PushInstruction;
import com.allatori.lib.bcel.generic.RET;
import com.allatori.lib.bcel.generic.ReturnInstruction;
import com.allatori.lib.bcel.generic.Select;
import com.allatori.lib.bcel.generic.StoreInstruction;
import com.allatori.lib.bcel.generic.TargetLostException;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;
import com.allatori.util.random.RandomUtils;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

public class MethodOptimizer implements ClassObserver {

  public ClassStorage classStorage;

  public void processInstructionList(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    int value;
    int value2;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!this.matchesInstruction(instructionHandle.getInstruction())) {
        instructionHandle2 = instructionHandle;
      } else {
        int value3 = 1;
        InstructionHandle instructionHandle3;
        instructionHandle2 = instructionHandle3 = instructionHandle.getInstructionHandle2();
        while (true) {
          if (instructionHandle2 == null) {
            value2 = value3;
            break;
          }
          if (!this.matchesInstruction(instructionHandle3.getInstruction())) {
            value2 = value3;
            break;
          }
          if (!this.matchesConstantPoolGen(
              instructionHandle3.getInstruction(),
              instructionHandle.getInstruction(),
              classGen.getConstantPool())) {
            value2 = value3;
            break;
          }
          InstructionHandle instructionHandle4 = instructionHandle3;
          value3++;
          instructionHandle3 = instructionHandle4.getInstructionHandle2();
          instructionHandle2 = instructionHandle3;
        }
        if (value2 < 2) {
          instructionHandle2 = instructionHandle;
        } else {
          byte value4 = 1;
          InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
          int value5;
          int value6 = value5 = 1;
          while (true) {
            if (value6 >= value3) {
              value = value4;
              break;
            }
            if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
              value = 0;
              break;
            }
            InstructionHandle instructionHandle5 = instructionHandle22;
            value5++;
            instructionHandle22 = instructionHandle5.getInstructionHandle2();
            value6 = value5;
          }
          if (value == 0) {
            instructionHandle2 = instructionHandle;
          } else {
            OptimizationStatistics.count5++;
            InstructionHandle instructionHandle23 = instructionHandle.getInstructionHandle2();
            int value7;
            for (int value8 = value7 = 1; value8 < value3; value8 = value7) {
              this.processInstructionHandle2(instructionHandle23, instructionHandle);
              label68:
              {
                try {
                  instructionList.processInstructionHandle2(instructionHandle23);
                } catch (TargetLostException targetLostException) {
                  instructionHandle2 = instructionHandle;
                  break label68;
                }
                instructionHandle2 = instructionHandle;
              }
              InstructionHandle instructionHandle24 = instructionHandle2.getInstructionHandle2();
              value7++;
              instructionHandle23 = instructionHandle24;
            }
            int value9;
            for (int value10 = value9 = 1; value10 < value3; value10 = ++value9) {
              if (instructionHandle.getInstruction().produceStack(classGen.getConstantPool())
                  == 1) {
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.DUP);
              } else if (instructionHandle.getInstruction().produceStack(classGen.getConstantPool())
                  == 2) {
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.DUP2);
              }
            }
            instructionHandle2 = instructionHandle;
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processEnabled(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList, boolean matches) {
    int value;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof LoadInstruction)) {
        instructionHandle2 = instructionHandle;
      } else if (!this.matchesInstructionTargeters(instructionHandle.getTargeters())) {
        instructionHandle2 = instructionHandle;
      } else {
        ConstantPoolGen constantPool = classGen.getConstantPool();
        int index = ((LoadInstruction) instructionHandle.getInstruction()).getIndex();
        int value2 = 0;
        InstructionHandle instructionHandle3 = null;
        InstructionHandle instructionHandle4 = null;
        InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
        int value3 = 0;
        while (true) {
          if (instructionHandle22 != null) {
            Instruction instruction;
            if (!((instruction = instructionHandle22.getInstruction())
                    instanceof ArithmeticInstruction)
                && !(instruction instanceof ArrayInstruction)
                && !(instruction instanceof ConversionInstruction)
                && !(instruction instanceof ConstantPushInstruction)
                && !(instruction instanceof FieldOrMethod)
                && !(instruction instanceof LocalVariableInstruction)
                && !(instruction instanceof PushInstruction)
                && !(instruction instanceof PopInstruction)
                && !(instruction instanceof NEW)
                && !(instruction instanceof NEWARRAY)
                && !(instruction instanceof NOP)) {
              instructionHandle2 = instructionHandle4;
              break;
            }
            if ((instruction instanceof StoreInstruction || instruction instanceof IINC)
                && ((LocalVariableInstruction) instruction).getIndex() == index) {
              instructionHandle2 = instructionHandle4;
              break;
            }
            if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
              instructionHandle2 = instructionHandle4;
              break;
            }
            if (value2 == 1) {
              Instruction instruction2;
              if ((instruction2 = instructionHandle22.getInstruction()) instanceof LoadInstruction
                  && ((LoadInstruction) instruction2).getIndex() == index) {
                instructionHandle3 = instructionHandle22;
              }
            } else {
              Instruction instruction3;
              if (value2 == 2
                  && (instruction3 = instructionHandle22.getInstruction())
                      instanceof LoadInstruction
                  && ((LoadInstruction) instruction3).getIndex() == index) {
                instructionHandle2 = instructionHandle4 = instructionHandle22;
                break;
              }
            }
            int value4 = instructionHandle22.getInstruction().produceStack(constantPool);
            int value5 = instructionHandle22.getInstruction().consumeStack(constantPool);
            if (value4 != -2) {
              if (value5 == -2) {
                instructionHandle2 = instructionHandle4;
                break;
              }
              int value6;
              if ((value6 = value2 - value5) < 0) {
                instructionHandle2 = instructionHandle4;
                break;
              }
              value2 = value6 + value4;
              instructionHandle22 = instructionHandle22.getInstructionHandle2();
              int value7 = value3++;
              if (value7 <= (short) 777) {
                continue;
              }
              instructionHandle2 = instructionHandle4;
              break;
            }
          }
          instructionHandle2 = instructionHandle4;
          break;
        }
        InstructionHandle instructionHandle5 =
            instructionHandle2 != null ? instructionHandle4 : instructionHandle3;
        int value8 = instructionHandle4 != null ? 2 : 1;
        if (instructionHandle5 == null) {
          instructionHandle2 = instructionHandle;
        } else {
          label172:
          {
            if (!matches) {
              InstructionHandle instructionHandle23 = instructionHandle.getInstructionHandle2();
              byte value9 = 0;
              instructionHandle2 = instructionHandle23;
              while (true) {
                if (instructionHandle2 != null && instructionHandle23 != instructionHandle5) {
                  if (instructionHandle23.getInstruction() instanceof LoadInstruction
                      && ((LoadInstruction) instructionHandle23.getInstruction()).getIndex()
                          == index) {
                    instructionHandle2 =
                        instructionHandle23 = instructionHandle23.getInstructionHandle2();
                    continue;
                  }
                  value = 1;
                  break;
                }
                value = value9;
                break;
              }
              if (value == 0) {
                instructionHandle2 = instructionHandle;
                break label172;
              }
            }
            OptimizationStatistics.count++;
            InstructionHandle instructionHandle6;
            InstructionHandle instructionHandle7 =
                instructionHandle6 = instructionHandle.getInstructionHandle2();
            this.processInstructionHandle(
                instructionHandle6.getInstructionHandle(), instructionHandle6);
            this.processInstructionHandle2(
                instructionHandle7.getInstructionHandle(), instructionHandle5);
            label106:
            {
              try {
                instructionList.processInstructionHandle2(
                    instructionHandle6.getInstructionHandle());
              } catch (TargetLostException targetLostException) {
                instructionHandle2 = instructionHandle5;
                break label106;
              }
              instructionHandle2 = instructionHandle5;
            }
            if (instructionHandle2.getInstruction().produceStack(constantPool) == 1) {
              if (value8 == 2) {
                instructionHandle2 = instructionHandle6;
                instructionList.createInstructionHandle2(
                    instructionHandle5, InstructionConstants.DUP_X2);
              } else {
                instructionList.createInstructionHandle2(
                    instructionHandle5, InstructionConstants.DUP_X1);
                instructionHandle2 = instructionHandle6;
              }
            } else {
              label173:
              {
                if (instructionHandle5.getInstruction().produceStack(constantPool) == 2) {
                  if (value8 == 2) {
                    instructionHandle2 = instructionHandle6;
                    instructionList.createInstructionHandle2(
                        instructionHandle5, InstructionConstants.DUP2_X2);
                    break label173;
                  }
                  instructionList.createInstructionHandle2(
                      instructionHandle5, InstructionConstants.DUP2_X1);
                }
                instructionHandle2 = instructionHandle6;
              }
            }
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList2(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    int value;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof ALOAD)) {
        instructionHandle2 = instructionHandle;
      } else if (instructionHandle.getInstructionHandle2() == null) {
        instructionHandle2 = instructionHandle;
      } else if (instructionHandle.getInstructionHandle2().getInstructionHandle2() == null) {
        instructionHandle2 = instructionHandle;
      } else if (instructionHandle
              .getInstructionHandle2()
              .getInstructionHandle2()
              .getInstructionHandle2()
          == null) {
        instructionHandle2 = instructionHandle;
      } else if (!(instructionHandle.getInstructionHandle2().getInstruction()
          instanceof GETFIELD)) {
        instructionHandle2 = instructionHandle;
      } else if (!(instructionHandle
              .getInstructionHandle2()
              .getInstructionHandle2()
              .getInstruction()
          instanceof ALOAD)) {
        instructionHandle2 = instructionHandle;
      } else if (!(instructionHandle
              .getInstructionHandle2()
              .getInstructionHandle2()
              .getInstructionHandle2()
              .getInstruction()
          instanceof GETFIELD)) {
        instructionHandle2 = instructionHandle;
      } else if (((ALOAD) instructionHandle.getInstruction()).getIndex()
          != ((ALOAD)
                  instructionHandle
                      .getInstructionHandle2()
                      .getInstructionHandle2()
                      .getInstruction())
              .getIndex()) {
        instructionHandle2 = instructionHandle;
      } else if (instructionHandle
              .getInstructionHandle2()
              .getInstruction()
              .produceStack(classGen.getConstantPool())
          != 1) {
        instructionHandle2 = instructionHandle;
      } else {
        byte value2 = 1;
        InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
        int value3;
        int value4 = value3 = 1;
        while (true) {
          if (value4 >= 4) {
            value = value2;
            break;
          }
          if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
            value = 0;
            break;
          }
          InstructionHandle instructionHandle3 = instructionHandle22;
          value3++;
          instructionHandle22 = instructionHandle3.getInstructionHandle2();
          value4 = value3;
        }
        if (value == 0) {
          instructionHandle2 = instructionHandle;
        } else {
          OptimizationStatistics.count11++;
          instructionHandle2 = instructionHandle;
          this.processInstructionHandle(
              instructionHandle.getInstructionHandle2().getInstructionHandle2(),
              instructionHandle
                  .getInstructionHandle2()
                  .getInstructionHandle2()
                  .getInstructionHandle2());
          instructionList.processInstructionHandle3(
              instructionHandle.getInstructionHandle2(),
              instructionHandle.getInstructionHandle2().getInstructionHandle2());
          instructionList.createInstructionHandle2(
              instructionHandle.getInstructionHandle2().getInstructionHandle2(),
              InstructionConstants.SWAP);
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList3(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    byte value;
    int value2;
    FieldInstruction fieldInstruction;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof FieldInstruction)) {
        instructionHandle2 = instructionHandle;
      } else {
        FieldInstruction fieldInstruction2 = (FieldInstruction) instructionHandle.getInstruction();
        if (!Configuration.getFieldRemovalOptions()
            .matchesText(
                ""
                    + fieldInstruction2.getText2(classGen.getConstantPool())
                    + "."
                    + fieldInstruction2.getFieldName(classGen.getConstantPool())
                    + fieldInstruction2.getText3(classGen.getConstantPool()))) {
          instructionHandle2 = instructionHandle;
        } else {
          label238:
          {
            InstructionHandle instructionHandle3;
            ConstantPoolGen constantPoolGen;
            label220:
            {
              constantPoolGen = classGen.getConstantPool();
              int value3 = fieldInstruction2.produceStack(constantPoolGen);
              instructionHandle3 = null;
              if (value3 > 0) {
                if (instructionHandle.getInstructionHandle2() != null
                    && (instructionHandle.getInstructionHandle2().getInstruction() instanceof POP
                        || instructionHandle.getInstructionHandle2().getInstruction()
                            instanceof POP2)
                    && instructionHandle
                            .getInstructionHandle2()
                            .getInstruction()
                            .consumeStack(constantPoolGen)
                        == value3) {
                  instructionHandle3 = instructionHandle.getInstructionHandle2();
                }
                if (instructionHandle3 == null) {
                  instructionHandle3 = instructionHandle;
                  if (Type.INT.equals(fieldInstruction2.getType2(constantPoolGen))
                      || Type.SHORT.equals(fieldInstruction2.getType2(constantPoolGen))
                      || Type.BYTE.equals(fieldInstruction2.getType2(constantPoolGen))
                      || Type.BOOLEAN.equals(fieldInstruction2.getType2(constantPoolGen))
                      || Type.CHAR.equals(fieldInstruction2.getType2(constantPoolGen))) {
                    instructionList.createInstructionHandle2(instructionHandle3, new ICONST(0));
                  } else if (Type.LONG.equals(fieldInstruction2.getType2(constantPoolGen))) {
                    instructionList.createInstructionHandle2(instructionHandle3, new LCONST(0L));
                  } else if (Type.FLOAT.equals(fieldInstruction2.getType2(constantPoolGen))) {
                    instructionList.createInstructionHandle2(instructionHandle3, new FCONST(0.0F));
                  } else if (Type.DOUBLE.equals(fieldInstruction2.getType2(constantPoolGen))) {
                    instructionList.createInstructionHandle2(instructionHandle3, new DCONST(0.0));
                  } else if (fieldInstruction2.getType2(constantPoolGen) instanceof ArrayType) {
                    instructionList.createInstructionHandle2(instructionHandle3, new ACONST_NULL());
                  } else {
                    if (!(fieldInstruction2.getType2(constantPoolGen) instanceof ObjectType)) {
                      Log.warn(
                          "[remove-fields] Cannot remove field usage ("
                              + fieldInstruction2.getText2(constantPoolGen)
                              + "."
                              + fieldInstruction2.getFieldName(constantPoolGen)
                              + ") in "
                              + classGen.getClassName()
                              + "."
                              + methodGen.getName()
                              + " - the value is used");
                      instructionHandle2 = instructionHandle;
                      break label238;
                    }
                    instructionList.createInstructionHandle2(instructionHandle3, new ACONST_NULL());
                  }
                  Log.warn(
                      "[remove-fields] the value of "
                          + fieldInstruction2.getText2(constantPoolGen)
                          + "."
                          + fieldInstruction2.getFieldName(constantPoolGen)
                          + " in "
                          + classGen.getClassName()
                          + "."
                          + methodGen.getName()
                          + " was replaced with null(0)");
                  fieldInstruction = fieldInstruction2;
                  break label220;
                }
              } else {
                instructionHandle3 = instructionHandle;
              }
              fieldInstruction = fieldInstruction2;
            }
            int value4 = fieldInstruction.consumeStack(constantPoolGen);
            InstructionHandle instructionHandle4;
            instructionHandle2 = instructionHandle4 = instructionHandle;
            while (true) {
              if (instructionHandle2.getInstructionHandle() == null) {
                value2 = value4;
                break;
              }
              if (value4 == 0) {
                value2 = value4;
                break;
              }
              instructionHandle4 = instructionHandle4.getInstructionHandle();
              value4 -=
                  instructionHandle4.getInstruction().produceStack(constantPoolGen)
                      - instructionHandle4.getInstruction().consumeStack(constantPoolGen);
              instructionHandle2 = instructionHandle4;
            }
            if (value2 != 0) {
              Log.warn(
                  "[remove-fields] Cannot remove field usage ("
                      + fieldInstruction2.getText2(constantPoolGen)
                      + "."
                      + fieldInstruction2.getFieldName(constantPoolGen)
                      + ") in "
                      + classGen.getClassName()
                      + "."
                      + methodGen.getName()
                      + " - object not found");
              instructionHandle2 = instructionHandle;
            } else {
              InstructionHandle instructionHandle5 = instructionHandle4;
              InstructionHandle instructionHandle6 = instructionHandle4;
              int value5 = 0;
              label191:
              while (true) {
                instructionHandle2 = instructionHandle6;
                while (instructionHandle2 != instructionHandle3
                    && instructionHandle6.getInstructionHandle2() != null) {
                  instructionHandle6 = instructionHandle6.getInstructionHandle2();
                  if (this.matchesInstructionTargeters(instructionHandle6.getTargeters())) {
                    continue label191;
                  }
                  InstructionHandle instructionHandle7 = instructionHandle6;
                  value5++;
                  instructionHandle2 = instructionHandle7;
                }
                if (value5 == 1) {
                  byte value6 = 0;
                  InstructionTargeter[] instructionTargeters;
                  if ((instructionTargeters = instructionHandle3.getTargeters()) != null) {
                    InstructionTargeter[] instructionTargeters2 = instructionTargeters;
                    int value7 = instructionTargeters.length;
                    int value8;
                    for (int value9 = value8 = 0; value9 < value7; value9 = ++value8) {
                      InstructionTargeter instructionTargeter;
                      if (!((instructionTargeter = instructionTargeters2[value8])
                              instanceof LineNumberGen)
                          && !(instructionTargeter instanceof LocalVariableGen)
                          && instructionTargeter instanceof CodeExceptionGen) {
                        value6 = 1;
                      }
                    }
                  }
                  if (value6 != 0 && instructionHandle5 != instructionHandle3) {
                    instructionHandle3.setInstruction(InstructionConstants.NOP);
                    instructionHandle3 = instructionHandle3.getInstructionHandle();
                    value5 = 0;
                  }
                }
                if (value5 > 0) {
                  Log.warn(
                      "[remove-fields] Cannot remove field usage ("
                          + fieldInstruction2.getText2(constantPoolGen)
                          + "."
                          + fieldInstruction2.getFieldName(constantPoolGen)
                          + ") in "
                          + classGen.getClassName()
                          + "."
                          + methodGen.getName()
                          + " - targeters inside block");
                  instructionHandle2 = instructionHandle;
                } else {
                  InstructionHandle instructionHandle8;
                  if ((instructionHandle8 = instructionHandle3.getInstructionHandle2()) == null) {
                    Log.warn(
                        "[remove-fields] Cannot remove field usage ("
                            + fieldInstruction2.getText2(constantPoolGen)
                            + "."
                            + fieldInstruction2.getFieldName(constantPoolGen)
                            + ") in "
                            + classGen.getClassName()
                            + "."
                            + methodGen.getName()
                            + " - unexpected end of block");
                    instructionHandle2 = instructionHandle;
                  } else {
                    InstructionTargeter[] instructionTargeters3;
                    if ((instructionTargeters3 = instructionHandle5.getTargeters()) != null) {
                      InstructionTargeter[] instructionTargeters4 = instructionTargeters3;
                      int value10 = instructionTargeters3.length;
                      int value11;
                      for (int value12 = value11 = 0; value12 < value10; value12 = ++value11) {
                        InstructionTargeter instructionTargeter2;
                        if (!((instructionTargeter2 = instructionTargeters4[value11])
                                instanceof LineNumberGen)
                            && !(instructionTargeter2 instanceof LocalVariableGen)) {
                          instructionTargeter2.updateTarget(instructionHandle5, instructionHandle8);
                        }
                      }
                    }
                    InstructionHandle instructionHandle9 = instructionHandle8;
                    do {
                      instructionHandle9 = instructionHandle9.getInstructionHandle();
                      this.processInstructionHandle(instructionHandle9, instructionHandle8);
                      this.processInstructionHandle2(instructionHandle9, instructionHandle8);
                    } while (instructionHandle9 != instructionHandle5
                        && instructionHandle9.getInstructionHandle() != null);
                    byte value13 = 0;
                    InstructionHandle instructionHandle10;
                    instructionHandle2 = instructionHandle10 = instructionHandle3;
                    while (true) {
                      if (instructionHandle2 == instructionHandle5) {
                        value = value13;
                        break;
                      }
                      if (instructionHandle10.getInstruction() instanceof IfInstruction) {
                        byte value14 = 1;
                        instructionHandle5 = instructionHandle10.getInstructionHandle2();
                        value = value14;
                        break;
                      }
                      instructionHandle2 =
                          instructionHandle10 = instructionHandle10.getInstructionHandle();
                    }
                    label145:
                    if (value == 0) {
                      try {
                        instructionList.processInstructionHandle(
                            instructionHandle5, instructionHandle3);
                      } catch (TargetLostException targetLostException) {
                        instructionHandle2 = instructionHandle8;
                        break label145;
                      }
                      instructionHandle2 = instructionHandle8;
                    } else {
                      label236:
                      {
                        int value15 =
                            instructionHandle3.getInstruction().consumeStack(constantPoolGen);
                        InstructionHandle instructionHandle11;
                        for (InstructionHandle instructionHandle12 =
                                instructionHandle11 = instructionHandle5;
                            instructionHandle12 != instructionHandle3;
                            instructionHandle12 =
                                instructionHandle11 = instructionHandle11.getInstructionHandle2()) {
                          value15 -=
                              instructionHandle11.getInstruction().produceStack(constantPoolGen)
                                  - instructionHandle11
                                      .getInstruction()
                                      .consumeStack(constantPoolGen);
                        }
                        int value16;
                        for (int value17 = value16 = 0; value17 < value15; value17 = value16) {
                          value16++;
                          instructionList.createInstructionHandle2(instructionHandle3, new POP());
                        }
                        try {
                          instructionList.processInstructionHandle(
                              instructionHandle5, instructionHandle3);
                        } catch (TargetLostException targetLostException2) {
                          instructionHandle2 = instructionHandle8;
                          break label236;
                        }
                        instructionHandle2 = instructionHandle8;
                      }
                    }
                    if (instructionHandle2.getInstructionHandle() != null) {
                      instructionHandle2 = instructionHandle8.getInstructionHandle();
                    } else {
                      InstructionHandle instructionHandle13 = instructionHandle8;
                      instructionHandle2 = instructionHandle13;
                    }
                  }
                }
                break;
              }
            }
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList4(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    int value;
    for (InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
        instructionHandle2 != null;
        instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2()) {
      for (int value2 = value = 0; value2 < 1 + RandomUtils.calculateCount(3); value2 = value) {
        instructionList.createInstructionHandle(
            instructionHandle, new BIPUSH((byte) (RandomUtils.calculateCount(120) - 120)));
        value++;
        instructionList.createInstructionHandle(instructionHandle, InstructionConstants.POP);
      }
    }
  }

  public void processInstructionList5(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    LocalVariableGen[] localVariableGens;
    if ((localVariableGens = methodGen.getLocalVariables()) != null) {
      if (localVariableGens.length != 0) {
        boolean[] matchess = new boolean[methodGen.getCount()];
        int value = methodGen.getArgumentNames().length;
        if (!methodGen.isEnabled11()) {
          value++;
        }
        Type[] types;
        int value2 = (types = methodGen.getArgumentTypes()).length;
        int value3;
        for (int value4 = value3 = 0; value4 < value2; value4 = ++value3) {
          Type type;
          if ((type = types[value3]) == Type.LONG || type == Type.DOUBLE) {
            value++;
          }
        }
        InstructionHandle instructionHandle;
        InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
        while (instructionHandle2 != null) {
          if (instructionHandle.getInstruction() instanceof LoadInstruction) {
            LoadInstruction loadInstruction = (LoadInstruction) instructionHandle.getInstruction();
            instructionHandle2 = instructionHandle;
            matchess[loadInstruction.getIndex()] = true;
          } else {
            if (instructionHandle.getInstruction() instanceof RET) {
              RET rET = (RET) instructionHandle.getInstruction();
              matchess[rET.getIndex()] = true;
            }
            instructionHandle2 = instructionHandle;
          }
          instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
        }
        InstructionHandle instructionHandle3;
        instructionHandle2 = instructionHandle3 = instructionList.getStart();
        while (instructionHandle2 != null) {
          if (instructionHandle3.getInstruction() instanceof StoreInstruction) {
            StoreInstruction storeInstruction =
                (StoreInstruction) instructionHandle3.getInstruction();
            if (!matchess[storeInstruction.getIndex()] && storeInstruction.getIndex() >= value) {
              if (storeInstruction instanceof ASTORE
                  || storeInstruction instanceof ISTORE
                  || storeInstruction instanceof FSTORE) {
                instructionHandle3.setInstruction(InstructionConstants.POP);
              } else if (storeInstruction instanceof LSTORE || storeInstruction instanceof DSTORE) {
                instructionHandle3.setInstruction(InstructionConstants.POP2);
              }
            }
          } else if (instructionHandle3.getInstruction() instanceof IINC) {
            IINC iNC = (IINC) instructionHandle3.getInstruction();
            if (!matchess[iNC.getIndex()] && iNC.getIndex() >= value) {
              InstructionHandle instructionHandle4 = instructionHandle3;
              instructionHandle3 = instructionHandle3.getInstructionHandle2();
              if (this.matchesInstructionTargeters(instructionHandle4.getTargeters())) {
                try {
                  instructionList.processInstructionHandle2(instructionHandle4);
                } catch (TargetLostException targetLostException) {
                  instructionHandle2 = instructionHandle3;
                  continue;
                }
                instructionHandle2 = instructionHandle3;
                continue;
              }
              instructionHandle4.setInstruction(InstructionConstants.NOP);
              instructionHandle2 = instructionHandle3;
              continue;
            }
          }
          instructionHandle2 = instructionHandle3 = instructionHandle3.getInstructionHandle2();
        }
        LocalVariableGen[] localVariableGens2 = localVariableGens;
        int value5 = localVariableGens.length;
        int value6;
        for (int value7 = value6 = 0; value7 < value5; value7 = ++value6) {
          LocalVariableGen localVariableGen;
          if ((localVariableGen = localVariableGens2[value6]).getIndex() >= value
              && !matchess[localVariableGen.getIndex()]) {
            methodGen.removeLocalVariable(localVariableGen);
            OptimizationStatistics.count16++;
          }
        }
      }
    }
  }

  public boolean matchesInstructionHandle(
      InstructionList instructionList,
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2) {
    int index = ((LocalVariableInstruction) instructionHandle.getInstruction()).getIndex();
    InstructionHandle instructionHandle3;
    for (InstructionHandle instructionHandle4 = instructionHandle3 = instructionList.getStart();
        instructionHandle4 != null;
        instructionHandle4 = instructionHandle3 = instructionHandle3.getInstructionHandle2()) {
      Instruction instruction;
      if ((instruction = instructionHandle3.getInstruction()) instanceof LocalVariableInstruction
          && ((LocalVariableInstruction) instruction).getIndex() == index
          && instructionHandle3 != instructionHandle
          && instructionHandle3 != instructionHandle2) {
        return false;
      }
    }
    return true;
  }

  public void processInstructionList6(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    int value;
    int value2;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof IINC)) {
        instructionHandle2 = instructionHandle;
      } else if (!this.matchesInstructionTargeters(instructionHandle.getTargeters())) {
        instructionHandle2 = instructionHandle;
      } else {
        IINC iNC = (IINC) instructionHandle.getInstruction();
        int value3 = 0;
        byte value4 = 0;
        InstructionHandle instructionHandle3 = instructionHandle.getInstructionHandle();
        label141:
        while (true) {
          instructionHandle2 = instructionHandle3;
          while (instructionHandle2 != null && value3 < 3) {
            if (!this.matchesInstructionTargeters(instructionHandle3.getTargeters())) {
              value2 = value4;
              break label141;
            }
            if (instructionHandle3.getInstruction() instanceof BranchInstruction) {
              value2 = value4;
              break label141;
            }
            if (instructionHandle3.getInstruction() instanceof LocalVariableInstruction
                && ((LocalVariableInstruction) instructionHandle3.getInstruction()).getIndex()
                    == iNC.getIndex()) {
              value2 = value4;
              break label141;
            }
            if (instructionHandle3.getInstruction() instanceof ArrayInstruction) {
              value2 = 1;
              break label141;
            }
            if ((instructionHandle3 = instructionHandle3.getInstructionHandle()) == null) {
              continue label141;
            }
            InstructionHandle instructionHandle4 = instructionHandle3;
            value3++;
            instructionHandle2 = instructionHandle4;
          }
          value2 = value4;
          break;
        }
        if (value2 != 0) {
          InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
          instructionHandle2 = instructionHandle22;
          this.processInstructionHandle(
              instructionHandle22.getInstructionHandle(), instructionHandle22);
          instructionList.processInstructionHandle3(
              instructionHandle22.getInstructionHandle(),
              instructionHandle3.getInstructionHandle());
          OptimizationStatistics.count27++;
          OptimizationStatistics.count20++;
        } else {
          label155:
          {
            int value5 = 0;
            if (value3 < 3) {
              byte value6 = 0;
              InstructionHandle instructionHandle23 = instructionHandle.getInstructionHandle2();
              label115:
              while (true) {
                instructionHandle2 = instructionHandle23;
                while (instructionHandle2 != null && value5 < 3) {
                  if (!this.matchesInstructionTargeters(instructionHandle23.getTargeters())) {
                    value = value6;
                    break label115;
                  }
                  if (instructionHandle23.getInstruction() instanceof BranchInstruction) {
                    value = value6;
                    break label115;
                  }
                  if (instructionHandle23.getInstruction() instanceof LocalVariableInstruction
                      && ((LocalVariableInstruction) instructionHandle23.getInstruction())
                              .getIndex()
                          == iNC.getIndex()) {
                    value = value6;
                    break label115;
                  }
                  if (value5 > 0
                      && instructionHandle23.getInstruction() instanceof ArrayInstruction) {
                    value = 1;
                    break label115;
                  }
                  if ((instructionHandle23 = instructionHandle23.getInstructionHandle2()) == null) {
                    continue label115;
                  }
                  InstructionHandle instructionHandle5 = instructionHandle23;
                  value5++;
                  instructionHandle2 = instructionHandle5;
                }
                value = value6;
                break;
              }
              if (value != 0) {
                this.processInstructionHandle(
                    instructionHandle, instructionHandle.getInstructionHandle2());
                instructionList.processInstructionHandle3(
                    instructionHandle, instructionHandle23.getInstructionHandle());
                InstructionHandle instructionHandle6 = instructionHandle23;
                OptimizationStatistics.count27++;
                OptimizationStatistics.count20++;
                instructionHandle2 = instructionHandle6;
                break label155;
              }
            }
            if (value3 == 0 && value5 == 0) {
              instructionHandle2 = instructionHandle;
            } else {
              OptimizationStatistics.count27++;
              this.processInstructionHandle(
                  instructionHandle, instructionHandle.getInstructionHandle2());
              if (value3 > 3) {
                value3 = 3;
              }
              if (value5 > 3) {
                value5 = 3;
              }
              if (value3 == 3) {
                InstructionHandle instructionHandle24 = instructionHandle.getInstructionHandle2();
                InstructionHandle instructionHandle7 = instructionHandle24.getInstructionHandle();
                instructionHandle2 = instructionHandle24;
                instructionList.processInstructionHandle3(
                    instructionHandle7,
                    instructionHandle24
                        .getInstructionHandle()
                        .getInstructionHandle()
                        .getInstructionHandle()
                        .getInstructionHandle()
                        .getInstructionHandle());
              } else if (value5 == 3) {
                instructionHandle2 = instructionHandle;
                instructionList.processInstructionHandle3(
                    instructionHandle,
                    instructionHandle
                        .getInstructionHandle2()
                        .getInstructionHandle2()
                        .getInstructionHandle2());
              } else if (value3 == 2) {
                InstructionHandle instructionHandle25 = instructionHandle.getInstructionHandle2();
                InstructionHandle instructionHandle8 = instructionHandle25.getInstructionHandle();
                instructionHandle2 = instructionHandle25;
                instructionList.processInstructionHandle3(
                    instructionHandle8,
                    instructionHandle25
                        .getInstructionHandle()
                        .getInstructionHandle()
                        .getInstructionHandle()
                        .getInstructionHandle());
              } else if (value5 == 2) {
                instructionHandle2 = instructionHandle;
                instructionList.processInstructionHandle3(
                    instructionHandle,
                    instructionHandle.getInstructionHandle2().getInstructionHandle2());
              } else if (value3 == 1) {
                InstructionHandle instructionHandle26 = instructionHandle.getInstructionHandle2();
                InstructionHandle instructionHandle9 = instructionHandle26.getInstructionHandle();
                instructionHandle2 = instructionHandle26;
                instructionList.processInstructionHandle3(
                    instructionHandle9,
                    instructionHandle26
                        .getInstructionHandle()
                        .getInstructionHandle()
                        .getInstructionHandle());
              } else {
                if (value5 == 1) {
                  instructionList.processInstructionHandle3(
                      instructionHandle, instructionHandle.getInstructionHandle2());
                }
                instructionHandle2 = instructionHandle;
              }
            }
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList7(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    if (classGen.getMajor() <= 50) {
      InstructionHandle instructionHandle = null;
      InstructionHandle instructionHandle2 = null;
      InstructionHandle instructionHandle3;
      InstructionHandle instructionHandle4 = instructionHandle3 = instructionList.getStart();
      while (true) {
        if (instructionHandle4 == null) {
          instructionHandle4 = instructionHandle;
          break;
        }
        if (!(instructionHandle3.getInstruction() instanceof GOTO)
            && !(instructionHandle3.getInstruction() instanceof ReturnInstruction)) {
          instructionHandle4 = instructionHandle2;
        } else if (instructionHandle3.getInstructionHandle2() == null) {
          instructionHandle4 = instructionHandle2;
        } else {
          label46:
          {
            if (instructionHandle3.getInstruction() instanceof GOTO) {
              GOTO gOTO;
              if ((gOTO = (GOTO) instructionHandle3.getInstruction()).getTarget() == null) {
                instructionHandle4 = instructionHandle2;
                break label46;
              }
              if (Math.abs(instructionHandle3.getPosition() - gOTO.getTarget().getPosition())
                  > 32000) {
                instructionHandle4 = instructionHandle2;
                break label46;
              }
            }
            if (instructionHandle == null) {
              instructionHandle = instructionHandle3;
              instructionHandle4 = instructionHandle2;
            } else {
              instructionHandle2 = instructionHandle3;
              instructionHandle4 = instructionHandle2;
            }
          }
        }
        if (instructionHandle4 != null) {
          instructionHandle4 = instructionHandle;
          break;
        }
        instructionHandle4 = instructionHandle3 = instructionHandle3.getInstructionHandle2();
      }
      if (instructionHandle4 != null) {
        instructionList.createInstructionHandle2(instructionHandle, InstructionConstants.POP);
        OptimizationStatistics.count32++;
        if (instructionHandle2 != null) {
          instructionList.createInstructionHandle2(
              instructionHandle2, InstructionConstants.ICONST_0);
        }
      }
    }
  }

  public void processEnabled2(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList, boolean matches) {
    Vector vector;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof ALOAD)) {
        instructionHandle2 = instructionHandle;
      } else {
        int index = ((ALOAD) instructionHandle.getInstruction()).getIndex();
        if (!matches && !this.matchesInstructionTargeters(instructionHandle.getTargeters())) {
          instructionHandle2 = instructionHandle;
        } else {
          ConstantPoolGen constantPool = classGen.getConstantPool();
          int value = instructionHandle.getInstruction().produceStack(constantPool);
          Vector vector2;
          (vector2 = new Vector()).add(instructionHandle);
          InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
          int value2 = 0;
          while (true) {
            if (instructionHandle22 != null) {
              Instruction instruction;
              if (!((instruction = instructionHandle22.getInstruction())
                      instanceof ArithmeticInstruction)
                  && !(instruction instanceof ArrayInstruction)
                  && !(instruction instanceof ConversionInstruction)
                  && !(instruction instanceof ConstantPushInstruction)
                  && !(instruction instanceof FieldOrMethod)
                  && !(instruction instanceof LocalVariableInstruction)
                  && !(instruction instanceof PushInstruction)
                  && !(instruction instanceof PopInstruction)
                  && !(instruction instanceof NEW)
                  && !(instruction instanceof NEWARRAY)
                  && !(instruction instanceof NOP)) {
                vector = vector2;
                break;
              }
              if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
                vector = vector2;
                break;
              }
              label121:
              {
                if (value == 0 && instructionHandle22.getInstruction() instanceof ALOAD) {
                  if (!matches) {
                    instructionHandle2 = instructionHandle22;
                    vector2.add(instructionHandle22);
                    break label121;
                  }
                  if (((ALOAD) instructionHandle22.getInstruction()).getIndex() == index) {
                    vector2.add(instructionHandle22);
                  }
                }
                instructionHandle2 = instructionHandle22;
              }
              if (instructionHandle2.getInstruction() instanceof ASTORE) {
                if (matches
                    && ((ASTORE) instructionHandle22.getInstruction()).getIndex() == index) {
                  vector = vector2;
                  break;
                }
                if (!matches) {
                  vector = vector2;
                  break;
                }
              }
              int value3 = instructionHandle22.getInstruction().produceStack(constantPool);
              int value4 = instructionHandle22.getInstruction().consumeStack(constantPool);
              if (value3 != -2) {
                if (value4 == -2) {
                  vector = vector2;
                  break;
                }
                int value5;
                if ((value5 = value - value4) < 0) {
                  vector = vector2;
                  break;
                }
                value = value5 + value3;
                instructionHandle22 = instructionHandle22.getInstructionHandle2();
                int value6 = value2++;
                if (value6 <= (short) 777) {
                  continue;
                }
                vector = vector2;
                break;
              }
            }
            vector = vector2;
            break;
          }
          if (vector.size() < 2) {
            instructionHandle2 = instructionHandle;
          } else {
            if (vector2.size() > 1) {
              OptimizationStatistics.count14++;
            }
            if (vector2.size() > 2) {
              OptimizationStatistics.count33++;
            }
            int value7;
            int value8;
            if (matches) {
              for (int value9 = value8 = vector2.size() - 1; value9 >= 1; value9 = value8) {
                this.processInstructionHandle(
                    (InstructionHandle) vector2.get(value8),
                    ((InstructionHandle) vector2.get(value8)).getInstructionHandle2());
                InstructionHandle instructionHandle3 = (InstructionHandle) vector2.get(value8);
                Vector vector3 = vector2;
                value8--;
                instructionList.processInstructionHandle3(
                    instructionHandle3, (InstructionHandle) vector3.get((byte) 0));
              }
            } else {
              for (int value10 = value7 = vector2.size() - 1; value10 >= 1; value10 = value7) {
                this.processInstructionHandle(
                    (InstructionHandle) vector2.get(value7),
                    ((InstructionHandle) vector2.get(value7)).getInstructionHandle2());
                InstructionHandle instructionHandle4 = (InstructionHandle) vector2.get(value7);
                Object result = vector2.get(0);
                value7--;
                instructionList.processInstructionHandle3(
                    instructionHandle4, ((InstructionHandle) result).getInstructionHandle());
              }
            }
            instructionHandle2 = instructionHandle;
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList8(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    OptimizationStatistics.count30++;
    LocalVariableGen[] localVariableGens;
    if ((localVariableGens = methodGen.getLocalVariables()) != null) {
      if (localVariableGens.length != 0) {
        boolean[] matchess = new boolean[methodGen.getCount()];
        int value = methodGen.getArgumentNames().length;
        if (!methodGen.isEnabled11()) {
          value++;
        }
        Type[] types;
        int value2 = (types = methodGen.getArgumentTypes()).length;
        int value3;
        for (int value4 = value3 = 0; value4 < value2; value4 = ++value3) {
          Type type;
          if ((type = types[value3]) == Type.LONG || type == Type.DOUBLE) {
            value++;
          }
        }
        LocalVariableGen[] localVariableGens2 = localVariableGens;
        int value5 = localVariableGens.length;
        int value6;
        for (int value7 = value6 = 0; value7 < value5; value7 = ++value6) {
          LocalVariableGen localVariableGen;
          if ((localVariableGen = localVariableGens2[value6]).getType() == Type.BOOLEAN
              || localVariableGen.getType() == Type.BYTE
              || localVariableGen.getType() == Type.SHORT
              || localVariableGen.getType() == Type.CHAR) {
            localVariableGen.setType(Type.INT);
          }
        }
        InstructionHandle instructionHandle;
        for (InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
            instructionHandle2 != null;
            instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2()) {
          if (instructionHandle.getInstruction() instanceof LocalVariableInstruction) {
            LocalVariableInstruction localVariableInstruction =
                (LocalVariableInstruction) instructionHandle.getInstruction();
            matchess[localVariableInstruction.getIndex()] = true;
          }
        }
        LocalVariableGen[] localVariableGens3 = localVariableGens;
        int value8 = localVariableGens.length;
        int value9;
        for (int value10 = value9 = 0; value10 < value8; value10 = ++value9) {
          LocalVariableGen localVariableGen2;
          if ((localVariableGen2 = localVariableGens3[value9]).getIndex() >= value
              && !matchess[localVariableGen2.getIndex()]) {
            methodGen.removeLocalVariable(localVariableGen2);
            OptimizationStatistics.count6++;
          }
        }
        instructionList.update();
        LocalVariableGen[] localVariableGens4 = localVariableGens;
        int value11 = localVariableGens.length;
        int value12;
        for (int value13 = value12 = 0; value13 < value11; value13 = ++value12) {
          LocalVariableGen localVariableGen3;
          if ((localVariableGen3 = localVariableGens4[value12]).getInstructionHandle() != null
              && localVariableGen3.getInstructionHandle2() != null
              && localVariableGen3.getInstructionHandle().getPosition()
                  > localVariableGen3.getInstructionHandle2().getPosition()) {
            InstructionHandle instructionHandle3 = localVariableGen3.getInstructionHandle();
            localVariableGen3.processInstructionHandle2(localVariableGen3.getInstructionHandle2());
            localVariableGen3.processInstructionHandle(instructionHandle3);
          }
        }
      }
    }
  }

  public void processCount(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList, int value) {
    InstructionList instructionList2;
    InstructionList instructionList3;
    InstructionList instructionList4;
    int value2 = 0;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (value2 >= value) {
        instructionHandle2 = instructionHandle;
      } else if (!this.matchesInstructionTargeters(instructionHandle.getTargeters())) {
        instructionHandle2 = instructionHandle;
      } else {
        if (instructionHandle.getInstruction() instanceof ConstantPushInstruction) {
          ConstantPoolGen constantPool = classGen.getConstantPool();
          ConstantPushInstruction constantPushInstruction;
          if ((constantPushInstruction =
                          (ConstantPushInstruction) instructionHandle.getInstruction())
                      .getType(constantPool)
                  == Type.LONG
              && constantPushInstruction instanceof LCONST) {
            LCONST lCONST;
            if ((lCONST = (LCONST) constantPushInstruction).getValue().longValue() == 0L) {
              InstructionHandle instructionHandle3 =
                  instructionList.createInstructionHandle2(
                      instructionHandle, InstructionConstants.I2L);
              InstructionHandle instructionHandle4 =
                  instructionList.createInstructionHandle2(
                      instructionHandle, InstructionConstants.ICONST_0);
              this.processInstructionHandle(instructionHandle, instructionHandle4);
              label113:
              {
                label94:
                {
                  try {
                    this.processInstructionHandle2(instructionHandle, instructionHandle3);
                  } catch (ClassGenException classGenException) {
                    {
                      instructionList4 = instructionList;
                      break label94;
                    }
                  }
                  instructionList4 = instructionList;
                }
                try {
                  instructionList4.processInstructionHandle2(instructionHandle);
                } catch (TargetLostException targetLostException) {
                  instructionHandle2 = instructionHandle3;
                  break label113;
                }
                instructionHandle2 = instructionHandle3;
              }
              instructionHandle = instructionHandle2;
              value2++;
            } else if (lCONST.getValue().longValue() == 1L) {
              InstructionHandle instructionHandle5 =
                  instructionList.createInstructionHandle2(
                      instructionHandle, InstructionConstants.I2L);
              InstructionHandle instructionHandle6 =
                  instructionList.createInstructionHandle2(
                      instructionHandle, InstructionConstants.ICONST_1);
              this.processInstructionHandle(instructionHandle, instructionHandle6);
              label115:
              {
                label82:
                {
                  try {
                    this.processInstructionHandle2(instructionHandle, instructionHandle5);
                  } catch (ClassGenException classGenException2) {
                    {
                      instructionList3 = instructionList;
                      break label82;
                    }
                  }
                  instructionList3 = instructionList;
                }
                try {
                  instructionList3.processInstructionHandle2(instructionHandle);
                } catch (TargetLostException targetLostException2) {
                  instructionHandle2 = instructionHandle5;
                  break label115;
                }
                instructionHandle2 = instructionHandle5;
              }
              instructionHandle = instructionHandle2;
              value2++;
            }
          }
        } else if (instructionHandle.getInstruction() instanceof LDC2_W) {
          LDC2_W lDC2_W = (LDC2_W) instructionHandle.getInstruction();
          ConstantPoolGen constantPool2 = classGen.getConstantPool();
          if (lDC2_W.getType(constantPool2) == Type.LONG) {
            long value3;
            long value4 = value3 = lDC2_W.getValue(constantPool2).longValue();
            if (value4 == (short) value4) {
              InstructionHandle instructionHandle7 =
                  instructionList.createInstructionHandle2(
                      instructionHandle, InstructionConstants.I2L);
              InstructionHandle instructionHandle22 =
                  instructionList.getInstructionHandle2(
                      instructionHandle, new PUSH(classGen.getConstantPool(), (short) value3));
              this.processInstructionHandle(instructionHandle, instructionHandle22);
              label117:
              {
                label68:
                {
                  try {
                    this.processInstructionHandle2(instructionHandle, instructionHandle7);
                  } catch (ClassGenException classGenException3) {
                    {
                      instructionList2 = instructionList;
                      break label68;
                    }
                  }
                  instructionList2 = instructionList;
                }
                try {
                  instructionList2.processInstructionHandle2(instructionHandle);
                } catch (TargetLostException targetLostException3) {
                  instructionHandle2 = instructionHandle7;
                  break label117;
                }
                instructionHandle2 = instructionHandle7;
              }
              instructionHandle = instructionHandle2;
              value2++;
            }
          }
        }
        instructionHandle2 = instructionHandle;
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public static int[] getCounts(int value) {
    int value2 = 0;
    int value3 = 0;
    int value4;
    for (int value5 = value4 = 0; value5 < 32; value5 = ++value4) {
      if ((value >> value4) % 2 == 1) {
        if (RandomUtils.calculateValue() < 0.5) {
          value2 |= 1 << value4;
        } else {
          value3 |= 1 << value4;
        }
      }
    }
    return new int[] {value2, value3};
  }

  public void processInstructionList9(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    IfInstruction ifInstruction;
    int value;
    instructionList.update();
    InstructionHandle instructionHandle = null;
    InstructionHandle instructionHandle2;
    InstructionHandle instructionHandle3 = instructionHandle2 = instructionList.getStart();
    while (instructionHandle3 != null) {
      if (instructionHandle2.getInstruction() instanceof IfInstruction) {
        instructionHandle = instructionHandle2;
      }
      if (!(instructionHandle2.getInstruction() instanceof GOTO)) {
        instructionHandle3 = instructionHandle2;
      } else {
        label176:
        {
          InstructionHandle instructionHandle4 = instructionHandle2;
          GOTO gOTO = (GOTO) instructionHandle2.getInstruction();
          if (instructionHandle4.getInstructionHandle2() != null) {
            if (gOTO.getTarget() == null) {
              instructionHandle3 = instructionHandle2;
              break label176;
            }
            if (instructionHandle4.getInstructionHandle2() == gOTO.getTarget()) {
              instructionHandle3 = instructionHandle2;
              break label176;
            }
            if (instructionHandle4.getInstructionHandle2().getInstructionHandle2()
                == gOTO.getTarget()) {
              instructionHandle3 = instructionHandle2;
              break label176;
            }
            if (instructionHandle4.getPosition() > gOTO.getTarget().getPosition()) {
              instructionHandle3 = instructionHandle2;
              break label176;
            }
            IfInstruction ifInstruction2 = null;
            InstructionHandle instructionHandle5;
            if ((instructionHandle5 = instructionHandle4.getInstructionHandle2()).getTargeters()
                == null) {
              instructionHandle3 = instructionHandle2;
              break label176;
            }
            InstructionTargeter[] instructionTargeters;
            int value2 = (instructionTargeters = instructionHandle5.getTargeters()).length;
            int value3;
            for (int value4 = value3 = 0; value4 < value2; value4 = ++value3) {
              InstructionTargeter instructionTargeter;
              if ((instructionTargeter = instructionTargeters[value3]) instanceof IfInstruction) {
                ifInstruction2 = (IfInstruction) instructionTargeter;
              }
            }
            if (instructionHandle == null) {
              instructionHandle3 = instructionHandle2;
              break label176;
            }
            if (instructionHandle.getInstruction() != ifInstruction2) {
              instructionHandle3 = instructionHandle2;
              break label176;
            }
            if (instructionHandle.getInstructionHandle2() != null) {
              if (instructionHandle.getInstructionHandle2().getInstructionHandle2() == null) {
                instructionHandle3 = instructionHandle2;
                break label176;
              }
              label150:
              if (instructionHandle.getInstructionHandle2() != instructionHandle4) {
                if (instructionHandle.getInstructionHandle2().getInstructionHandle2()
                    == instructionHandle4) {
                  instructionHandle3 = instructionHandle2;
                  break label176;
                }
                InstructionHandle instructionHandle6 = instructionHandle;
                byte value5 = 1;
                instructionHandle3 = instructionHandle6;
                while (true) {
                  if (instructionHandle3 != null) {
                    if (instructionHandle6 != instructionHandle5) {
                      if (!this.matchesInstructionTargeters(instructionHandle6.getTargeters())) {
                        value = 0;
                        break;
                      }
                      instructionHandle3 =
                          instructionHandle6 = instructionHandle6.getInstructionHandle2();
                      continue;
                    }
                    InstructionTargeter[] instructionTargeters2;
                    int value6 = (instructionTargeters2 = instructionHandle5.getTargeters()).length;
                    int value7;
                    for (int value8 = value7 = 0; value8 < value6; value8 = ++value7) {
                      InstructionTargeter instructionTargeter2;
                      if (!((instructionTargeter2 = instructionTargeters2[value7])
                              instanceof LineNumberGen)
                          && !(instructionTargeter2 instanceof LocalVariableGen)
                          && instructionTargeter2 != ifInstruction2) {
                        value5 = 0;
                        break;
                      }
                    }
                  }
                  value = value5;
                  break;
                }
                if (value == 0) {
                  instructionHandle3 = instructionHandle2;
                  break label176;
                }
                Instruction instruction;
                if ((!((instruction = instructionHandle5.getInstruction())
                            instanceof LoadInstruction)
                        || !instruction.equals(
                            instructionHandle.getInstructionHandle2().getInstruction()))
                    && (!this.matchesInstruction(instruction)
                        || !this.matchesConstantPoolGen(
                            instruction,
                            instructionHandle.getInstructionHandle2().getInstruction(),
                            classGen.getConstantPool()))) {
                  if (!(instruction instanceof NEW)) {
                    break label150;
                  }
                  if (!instruction.equals(
                      instructionHandle.getInstructionHandle2().getInstruction())) {
                    instructionHandle3 = instructionHandle2;
                    break label176;
                  }
                }
                if (instruction.produceStack(classGen.getConstantPool()) != 1) {
                  instructionHandle3 = instructionHandle2;
                  break label176;
                }
                if (ifInstruction2.consumeStack(classGen.getConstantPool()) != 1) {
                  instructionHandle3 = instructionHandle2;
                  break label176;
                }
                OptimizationStatistics.count12++;
                instructionList.createInstructionHandle(instructionHandle, instruction.copy());
                instructionList.createInstructionHandle(
                    instructionHandle, InstructionConstants.SWAP);
                this.processInstructionHandle(
                    instructionHandle.getInstructionHandle2(),
                    instructionHandle.getInstructionHandle2().getInstructionHandle2());
                this.processInstructionHandle2(
                    instructionHandle.getInstructionHandle2(),
                    instructionHandle.getInstructionHandle2().getInstructionHandle2());
                label106:
                {
                  try {
                    instructionList.processInstructionHandle2(
                        instructionHandle.getInstructionHandle2());
                  } catch (TargetLostException targetLostException) {
                    ifInstruction = ifInstruction2;
                    break label106;
                  }
                  ifInstruction = ifInstruction2;
                }
                ifInstruction.setTarget(instructionHandle5.getInstructionHandle2());
                this.processInstructionHandle(
                    instructionHandle5, instructionHandle5.getInstructionHandle2());
                this.processInstructionHandle2(
                    instructionHandle5, instructionHandle5.getInstructionHandle2());
                try {
                  instructionList.processInstructionHandle2(instructionHandle5);
                } catch (TargetLostException targetLostException2) {
                  break label150;
                }
                instructionHandle3 = instructionHandle2;
                break label176;
              }
            }
          }
          instructionHandle3 = instructionHandle2;
        }
      }
      instructionHandle3 = instructionHandle2 = instructionHandle3.getInstructionHandle2();
    }
  }

  public void processInstructionList10(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof Select)) {
        instructionHandle2 = instructionHandle;
      } else {
        label80:
        {
          Select select;
          InstructionHandle[] instructionHandles;
          if ((instructionHandles =
                  (select = (Select) instructionHandle.getInstruction()).getTargets())
              != null) {
            if (instructionHandles.length == 0) {
              instructionHandle2 = instructionHandle;
              break label80;
            }
            InstructionHandle instructionHandle3 = null;
            InstructionHandle instructionHandle4 = null;
            InstructionHandle[] instructionHandles2 = instructionHandles;
            int value = instructionHandles.length;
            int value2;
            for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
              InstructionHandle instructionHandle5;
              if ((instructionHandle5 = instructionHandles2[value2]) != null
                  && instructionHandle3 == null
                  && !(instructionHandle5.getInstruction() instanceof BranchInstruction)
                  && !(instructionHandle5.getInstruction() instanceof NEW)
                  && instructionHandle5 != select.getTarget()) {
                instructionHandle3 = instructionHandle5;
              }
              if (instructionHandle5 != null
                  && instructionHandle5.getInstructionHandle() != null
                  && (instructionHandle5.getInstructionHandle().getInstruction() instanceof GOTO
                      || instructionHandle5.getInstructionHandle().getInstruction()
                          instanceof ReturnInstruction)) {
                instructionHandle4 = instructionHandle5.getInstructionHandle();
              }
            }
            if (instructionHandle3 != null && instructionHandle4 != null) {
              instructionList.update();
              if (Math.abs(instructionHandle3.getPosition() - instructionHandle4.getPosition())
                  > 32000) {
                instructionHandle4 = null;
              }
            }
            if (instructionHandle3 != null && instructionHandle4 != null) {
              OptimizationStatistics.count29++;
              BranchInstruction branchInstruction =
                  InstructionFactory.createBranchInstruction((short) 154, null);
              instructionList.createBranchHandle(instructionHandle3, branchInstruction);
              InstructionHandle instructionHandle6 =
                  instructionList.createInstructionHandle2(instructionHandle3, new ICONST(0));
              branchInstruction.setTarget(
                  instructionList.createBranchHandle(
                      instructionHandle4, new GOTO(instructionHandle6)));
              instructionHandle2 = instructionHandle;
              break label80;
            }
            if (instructionHandle3 != null) {
              OptimizationStatistics.count29++;
              InstructionHandle instructionHandle7 =
                  instructionList.createInstructionHandle2(instructionHandle3, new ICONST(0));
              instructionList.createBranchHandle(instructionHandle7, new IFNE(instructionHandle7));
            }
          }
          instructionHandle2 = instructionHandle;
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public static long[] getSeeds(long value, int value2) {
    long value3 = value;
    long value4 = value;
    int value5;
    for (int value6 = value5 = 0; value6 < value2; value6 = ++value5) {
      if ((value >> value5) % 2L == 0L) {
        if (RandomUtils.calculateValue() < 0.5) {
          value3 |= 1L << value5;
        } else {
          value4 |= 1L << value5;
        }
      }
    }
    return new long[] {value3, value4};
  }

  public void processInstructionList11(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    LocalVariableGen[] localVariableGens;
    if ((localVariableGens = methodGen.getLocalVariables()) != null) {
      if (localVariableGens.length != 0) {
        int value;
        if ((value = methodGen.getCount()) >= 4) {
          int value2 = methodGen.getArgumentNames().length;
          if (!methodGen.isEnabled11()) {
            value2++;
          }
          Type[] types;
          int value3 = (types = methodGen.getArgumentTypes()).length;
          int value4;
          for (int value5 = value4 = 0; value5 < value3; value5 = ++value4) {
            Type type;
            if ((type = types[value4]) == Type.LONG || type == Type.DOUBLE) {
              value2++;
            }
          }
          if (value2 < 4) {
            int[] values = new int[value];
            boolean[] matchess = new boolean[value];
            InstructionHandle instructionHandle2;
            for (InstructionHandle instructionHandle3 =
                    instructionHandle2 = instructionList.getStart();
                instructionHandle3 != null;
                instructionHandle3 =
                    instructionHandle2 = instructionHandle2.getInstructionHandle2()) {
              if (instructionHandle2.getInstruction() instanceof LoadInstruction
                  || instructionHandle2.getInstruction() instanceof StoreInstruction) {
                LocalVariableInstruction localVariableInstruction =
                    (LocalVariableInstruction) instructionHandle2.getInstruction();
                int value6;
                values[value6 = localVariableInstruction.getIndex()]++;
                if ((localVariableInstruction.produceStack(classGen.getConstantPool()) == 2
                        || localVariableInstruction.consumeStack(classGen.getConstantPool()) == 2)
                    && value6 + 1 < value) {
                  matchess[value6] = true;
                  matchess[value6 + 1] = true;
                }
              }
            }
            int value7;
            for (int value8 = value7 = 0; value8 < values.length; value8 = value7) {
              int value9 = values[value7] << 16 | value7;
              int value10 = value7;
              value7++;
              values[value10] = value9;
            }
            Arrays.sort(values, 4, value);
            int value11;
            for (int value12 = value11 = value2; value12 < 4; value12 = ++value11) {
              if (!matchess[value11]) {
                int value13 = values[value11] >> 16;
                int value14;
                for (int value15 = value14 = value - 1; value15 >= 4; value15 = --value14) {
                  int value16 = values[value14] & 65535;
                  int value17 = values[value14] >> 16;
                  if (value16 > 3 && !matchess[value16] && value17 > value13) {
                    OptimizationStatistics.count24++;
                    values[value14] = 0;
                    InstructionHandle instructionHandle4;
                    InstructionHandle instructionHandle5 =
                        instructionHandle4 = instructionList.getStart();
                    while (instructionHandle5 != null) {
                      label84:
                      {
                        if (instructionHandle4.getInstruction()
                            instanceof LocalVariableInstruction) {
                          LocalVariableInstruction localVariableInstruction2;
                          if ((localVariableInstruction2 =
                                      (LocalVariableInstruction)
                                          instructionHandle4.getInstruction())
                                  .getIndex()
                              == value11) {
                            localVariableInstruction2.setIndex(value16);
                          } else if (localVariableInstruction2.getIndex() == value16) {
                            localVariableInstruction2.setIndex(value11);
                          }
                        } else if (instructionHandle4.getInstruction() instanceof RET) {
                          RET rET;
                          if ((rET = (RET) instructionHandle4.getInstruction()).getIndex()
                              == value11) {
                            instructionHandle = instructionHandle4;
                            rET.setIndex(value16);
                            break label84;
                          }
                          if (rET.getIndex() == value16) {
                            rET.setIndex(value11);
                          }
                        }
                        instructionHandle = instructionHandle4;
                      }
                      instructionHandle5 =
                          instructionHandle4 = instructionHandle.getInstructionHandle2();
                    }
                    break;
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  public void processInstructionList12(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    byte value;
    int value2;
    InvokeInstruction invokeInstruction;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof InvokeInstruction)) {
        instructionHandle2 = instructionHandle;
      } else {
        InvokeInstruction invokeInstruction2 =
            (InvokeInstruction) instructionHandle.getInstruction();
        if (!Configuration.getMethodCallRemovalOptions()
            .matchesText(
                this.classStorage,
                classGen,
                methodGen,
                invokeInstruction2.getText2(classGen.getConstantPool()),
                invokeInstruction2.getText4(classGen.getConstantPool()),
                invokeInstruction2.getText3(classGen.getConstantPool()))) {
          instructionHandle2 = instructionHandle;
        } else {
          label238:
          {
            InstructionHandle instructionHandle3;
            ConstantPoolGen constantPoolGen;
            label220:
            {
              constantPoolGen = classGen.getConstantPool();
              int value3 = invokeInstruction2.produceStack(constantPoolGen);
              instructionHandle3 = null;
              if (value3 > 0) {
                if (instructionHandle.getInstructionHandle2() != null
                    && (instructionHandle.getInstructionHandle2().getInstruction() instanceof POP
                        || instructionHandle.getInstructionHandle2().getInstruction()
                            instanceof POP2)
                    && instructionHandle
                            .getInstructionHandle2()
                            .getInstruction()
                            .consumeStack(constantPoolGen)
                        == value3) {
                  instructionHandle3 = instructionHandle.getInstructionHandle2();
                }
                if (instructionHandle3 == null) {
                  instructionHandle3 = instructionHandle;
                  if (Type.INT.equals(invokeInstruction2.getType2(classGen.getConstantPool()))
                      || Type.SHORT.equals(invokeInstruction2.getType2(classGen.getConstantPool()))
                      || Type.BYTE.equals(invokeInstruction2.getType2(classGen.getConstantPool()))
                      || Type.BOOLEAN.equals(
                          invokeInstruction2.getType2(classGen.getConstantPool()))
                      || Type.CHAR.equals(
                          invokeInstruction2.getType2(classGen.getConstantPool()))) {
                    instructionList.createInstructionHandle2(instructionHandle3, new ICONST(0));
                  } else if (Type.LONG.equals(
                      invokeInstruction2.getType2(classGen.getConstantPool()))) {
                    instructionList.createInstructionHandle2(instructionHandle3, new LCONST(0L));
                  } else if (Type.FLOAT.equals(
                      invokeInstruction2.getType2(classGen.getConstantPool()))) {
                    instructionList.createInstructionHandle2(instructionHandle3, new FCONST(0.0F));
                  } else if (Type.DOUBLE.equals(
                      invokeInstruction2.getType2(classGen.getConstantPool()))) {
                    instructionList.createInstructionHandle2(instructionHandle3, new DCONST(0.0));
                  } else if (invokeInstruction2.getType2(classGen.getConstantPool())
                      instanceof ArrayType) {
                    instructionList.createInstructionHandle2(instructionHandle3, new ACONST_NULL());
                  } else {
                    if (!(invokeInstruction2.getType2(classGen.getConstantPool())
                        instanceof ObjectType)) {
                      Log.warn(
                          "[remove-calls] Cannot remove call ("
                              + invokeInstruction2.getText2(constantPoolGen)
                              + "."
                              + invokeInstruction2.getText4(constantPoolGen)
                              + ") in "
                              + classGen.getClassName()
                              + "."
                              + methodGen.getName()
                              + " - return value is used");
                      instructionHandle2 = instructionHandle;
                      break label238;
                    }
                    instructionList.createInstructionHandle2(instructionHandle3, new ACONST_NULL());
                  }
                  Log.warn(
                      "[remove-calls] return value of "
                          + invokeInstruction2.getText2(constantPoolGen)
                          + "."
                          + invokeInstruction2.getText4(constantPoolGen)
                          + " in "
                          + classGen.getClassName()
                          + "."
                          + methodGen.getName()
                          + " was replaced with null(0)");
                  invokeInstruction = invokeInstruction2;
                  break label220;
                }
              } else {
                instructionHandle3 = instructionHandle;
              }
              invokeInstruction = invokeInstruction2;
            }
            int value4 = invokeInstruction.consumeStack(constantPoolGen);
            InstructionHandle instructionHandle4;
            instructionHandle2 = instructionHandle4 = instructionHandle;
            while (true) {
              if (instructionHandle2.getInstructionHandle() == null) {
                value2 = value4;
                break;
              }
              if (value4 == 0) {
                value2 = value4;
                break;
              }
              instructionHandle4 = instructionHandle4.getInstructionHandle();
              value4 -=
                  instructionHandle4.getInstruction().produceStack(constantPoolGen)
                      - instructionHandle4.getInstruction().consumeStack(constantPoolGen);
              instructionHandle2 = instructionHandle4;
            }
            if (value2 != 0) {
              Log.warn(
                  "[remove-calls] Cannot remove call ("
                      + invokeInstruction2.getText2(constantPoolGen)
                      + "."
                      + invokeInstruction2.getText4(constantPoolGen)
                      + ") in "
                      + classGen.getClassName()
                      + "."
                      + methodGen.getName()
                      + " - parameters not found");
              instructionHandle2 = instructionHandle;
            } else {
              InstructionHandle instructionHandle5 = instructionHandle4;
              InstructionHandle instructionHandle6 = instructionHandle4;
              int value5 = 0;
              label191:
              while (true) {
                instructionHandle2 = instructionHandle6;
                while (instructionHandle2 != instructionHandle3
                    && instructionHandle6.getInstructionHandle2() != null) {
                  instructionHandle6 = instructionHandle6.getInstructionHandle2();
                  if (this.matchesInstructionTargeters(instructionHandle6.getTargeters())) {
                    continue label191;
                  }
                  InstructionHandle instructionHandle7 = instructionHandle6;
                  value5++;
                  instructionHandle2 = instructionHandle7;
                }
                if (value5 == 1) {
                  byte value6 = 0;
                  InstructionTargeter[] instructionTargeters;
                  if ((instructionTargeters = instructionHandle3.getTargeters()) != null) {
                    InstructionTargeter[] instructionTargeters2 = instructionTargeters;
                    int value7 = instructionTargeters.length;
                    int value8;
                    for (int value9 = value8 = 0; value9 < value7; value9 = ++value8) {
                      InstructionTargeter instructionTargeter;
                      if (!((instructionTargeter = instructionTargeters2[value8])
                              instanceof LineNumberGen)
                          && !(instructionTargeter instanceof LocalVariableGen)
                          && instructionTargeter instanceof CodeExceptionGen) {
                        value6 = 1;
                      }
                    }
                  }
                  if (value6 != 0 && instructionHandle5 != instructionHandle3) {
                    instructionHandle3.setInstruction(InstructionConstants.NOP);
                    instructionHandle3 = instructionHandle3.getInstructionHandle();
                    value5 = 0;
                  }
                }
                if (value5 > 0) {
                  Log.warn(
                      "[remove-calls] Cannot remove call ("
                          + invokeInstruction2.getText2(constantPoolGen)
                          + "."
                          + invokeInstruction2.getText4(constantPoolGen)
                          + ") in "
                          + classGen.getClassName()
                          + "."
                          + methodGen.getName()
                          + " - targeters inside block");
                  instructionHandle2 = instructionHandle;
                } else {
                  InstructionHandle instructionHandle8;
                  if ((instructionHandle8 = instructionHandle3.getInstructionHandle2()) == null) {
                    Log.warn(
                        "[remove-calls] Cannot remove call ("
                            + invokeInstruction2.getText2(constantPoolGen)
                            + "."
                            + invokeInstruction2.getText4(constantPoolGen)
                            + ") in "
                            + classGen.getClassName()
                            + "."
                            + methodGen.getName()
                            + " - unexpected end of block");
                    instructionHandle2 = instructionHandle;
                  } else {
                    InstructionTargeter[] instructionTargeters3;
                    if ((instructionTargeters3 = instructionHandle5.getTargeters()) != null) {
                      InstructionTargeter[] instructionTargeters4 = instructionTargeters3;
                      int value10 = instructionTargeters3.length;
                      int value11;
                      for (int value12 = value11 = 0; value12 < value10; value12 = ++value11) {
                        InstructionTargeter instructionTargeter2;
                        if (!((instructionTargeter2 = instructionTargeters4[value11])
                                instanceof LineNumberGen)
                            && !(instructionTargeter2 instanceof LocalVariableGen)) {
                          instructionTargeter2.updateTarget(instructionHandle5, instructionHandle8);
                        }
                      }
                    }
                    InstructionHandle instructionHandle9 = instructionHandle8;
                    do {
                      instructionHandle9 = instructionHandle9.getInstructionHandle();
                      this.processInstructionHandle(instructionHandle9, instructionHandle8);
                      this.processInstructionHandle2(instructionHandle9, instructionHandle8);
                    } while (instructionHandle9 != instructionHandle5
                        && instructionHandle9.getInstructionHandle() != null);
                    byte value13 = 0;
                    InstructionHandle instructionHandle10;
                    instructionHandle2 = instructionHandle10 = instructionHandle3;
                    while (true) {
                      if (instructionHandle2 == instructionHandle5) {
                        value = value13;
                        break;
                      }
                      if (instructionHandle10.getInstruction() instanceof IfInstruction) {
                        byte value14 = 1;
                        instructionHandle5 = instructionHandle10.getInstructionHandle2();
                        value = value14;
                        break;
                      }
                      instructionHandle2 =
                          instructionHandle10 = instructionHandle10.getInstructionHandle();
                    }
                    label145:
                    if (value == 0) {
                      try {
                        instructionList.processInstructionHandle(
                            instructionHandle5, instructionHandle3);
                      } catch (TargetLostException targetLostException) {
                        instructionHandle2 = instructionHandle8;
                        break label145;
                      }
                      instructionHandle2 = instructionHandle8;
                    } else {
                      label236:
                      {
                        int value15 =
                            instructionHandle3.getInstruction().consumeStack(constantPoolGen);
                        InstructionHandle instructionHandle11;
                        for (InstructionHandle instructionHandle12 =
                                instructionHandle11 = instructionHandle5;
                            instructionHandle12 != instructionHandle3;
                            instructionHandle12 =
                                instructionHandle11 = instructionHandle11.getInstructionHandle2()) {
                          value15 -=
                              instructionHandle11.getInstruction().produceStack(constantPoolGen)
                                  - instructionHandle11
                                      .getInstruction()
                                      .consumeStack(constantPoolGen);
                        }
                        int value16;
                        for (int value17 = value16 = 0; value17 < value15; value17 = value16) {
                          value16++;
                          instructionList.createInstructionHandle2(instructionHandle3, new POP());
                        }
                        try {
                          instructionList.processInstructionHandle(
                              instructionHandle5, instructionHandle3);
                        } catch (TargetLostException targetLostException2) {
                          instructionHandle2 = instructionHandle8;
                          break label236;
                        }
                        instructionHandle2 = instructionHandle8;
                      }
                    }
                    if (instructionHandle2.getInstructionHandle() != null) {
                      instructionHandle2 = instructionHandle8.getInstructionHandle();
                    } else {
                      InstructionHandle instructionHandle13 = instructionHandle8;
                      instructionHandle2 = instructionHandle13;
                    }
                  }
                }
                break;
              }
            }
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList13(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionList instructionList2;
    int value;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof POP)
          && !(instructionHandle.getInstruction() instanceof POP2)) {
        instructionHandle2 = instructionHandle;
      } else if (!this.matchesInstructionTargeters(instructionHandle.getTargeters())) {
        instructionHandle2 = instructionHandle;
      } else {
        boolean matches = instructionHandle.getInstruction() instanceof POP;
        ConstantPoolGen constantPool = classGen.getConstantPool();
        int value2 = 0;
        int value3 = 0;
        InstructionHandle instructionHandle3 = null;
        InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
        while (true) {
          if (instructionHandle22 != null) {
            Instruction instruction;
            if (!((instruction = instructionHandle22.getInstruction())
                    instanceof ArithmeticInstruction)
                && !(instruction instanceof ArrayInstruction)
                && !(instruction instanceof ConversionInstruction)
                && !(instruction instanceof ConstantPushInstruction)
                && !(instruction instanceof FieldOrMethod)
                && !(instruction instanceof LocalVariableInstruction)
                && !(instruction instanceof PushInstruction)
                && !(instruction instanceof PopInstruction)
                && !(instruction instanceof NEW)
                && !(instruction instanceof NEWARRAY)
                && !(instruction instanceof NOP)) {
              value = value3;
              break;
            }
            if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
              value = value3;
              break;
            }
            int value4 = instructionHandle22.getInstruction().produceStack(constantPool);
            int value5 = instructionHandle22.getInstruction().consumeStack(constantPool);
            if (value4 != -2) {
              if (value5 == -2) {
                value = value3;
                break;
              }
              int value6;
              if ((value6 = value2 - value5) < 0) {
                value = value3;
                break;
              }
              if ((value2 = value6 + value4) == 0) {
                instructionHandle3 = instructionHandle22;
                if (matches && instructionHandle3.getInstruction() instanceof POP) {
                  value = value3;
                  break;
                }
              }
              value3++;
              instructionHandle22 = instructionHandle22.getInstructionHandle2();
              if (value3 <= 777) {
                continue;
              }
              value = value3;
              break;
            }
          }
          value = value3;
          break;
        }
        if (value == 0) {
          instructionHandle2 = instructionHandle;
        } else if (instructionHandle3 == null) {
          instructionHandle2 = instructionHandle;
        } else {
          label77:
          if (matches && instructionHandle3.getInstruction() instanceof POP) {
            OptimizationStatistics.count9++;
            InstructionHandle instructionHandle23 = instructionHandle.getInstructionHandle2();
            this.processInstructionHandle(
                instructionHandle23.getInstructionHandle().getTargeters(),
                instructionHandle23.getInstructionHandle(),
                instructionHandle23);
            label73:
            {
              try {
                instructionList.processInstructionHandle2(
                    instructionHandle23.getInstructionHandle());
              } catch (TargetLostException targetLostException) {
                instructionList2 = instructionList;
                this.processInstructionHandle(targetLostException, instructionHandle23);
                break label73;
              }
              instructionList2 = instructionList;
            }
            instructionList2.createInstructionHandle2(
                instructionHandle3, InstructionConstants.POP2);
            InstructionHandle instructionHandle24 = instructionHandle3.getInstructionHandle2();
            this.processInstructionHandle(
                instructionHandle24.getInstructionHandle().getTargeters(),
                instructionHandle24.getInstructionHandle(),
                instructionHandle24);
            try {
              instructionList.processInstructionHandle2(instructionHandle24.getInstructionHandle());
            } catch (TargetLostException targetLostException2) {
              instructionHandle2 = instructionHandle23;
              this.processInstructionHandle(targetLostException2, instructionHandle24);
              break label77;
            }
            instructionHandle2 = instructionHandle23;
          } else {
            OptimizationStatistics.count8++;
            InstructionHandle instructionHandle25 = instructionHandle.getInstructionHandle2();
            instructionHandle2 = instructionHandle25;
            instructionList.processInstructionHandle3(
                instructionHandle25.getInstructionHandle(), instructionHandle3);
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList14(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    instructionList.update();
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      label107:
      {
        if (instructionHandle.getInstruction() instanceof GotoInstruction) {
          InstructionHandle instructionHandle3;
          if ((instructionHandle3 =
                  ((GotoInstruction) instructionHandle.getInstruction()).getTarget())
              == null) {
            instructionHandle2 = instructionHandle;
            break label107;
          }
          if (instructionHandle3 == instructionHandle) {
            instructionHandle2 = instructionHandle;
            break label107;
          }
          if (!(instructionHandle3.getInstruction() instanceof GotoInstruction)
              && !(instructionHandle3.getInstruction() instanceof ReturnInstruction)) {
            instructionHandle2 = instructionHandle;
            break label107;
          }
          if (instructionHandle3.getInstruction() instanceof GotoInstruction) {
            int value = 0;
            for (InstructionHandle instructionHandle4 = instructionHandle3;
                instructionHandle4 != null
                    && instructionHandle3.getInstruction() instanceof GotoInstruction
                    && value < 100;
                instructionHandle4 = instructionHandle3) {
              Instruction instruction = instructionHandle3.getInstruction();
              value++;
              instructionHandle3 = ((GotoInstruction) instruction).getTarget();
            }
            if (value == 100) {
              instructionHandle2 = instructionHandle;
              break label107;
            }
          }
          if (instructionHandle3 == null) {
            instructionHandle2 = instructionHandle;
            break label107;
          }
          if (instructionHandle3.getInstruction() instanceof ReturnInstruction) {
            OptimizationStatistics.count19++;
            instructionList.createInstructionHandle2(
                instructionHandle, instructionHandle3.getInstruction());
            instructionHandle = instructionHandle.getInstructionHandle2();
            this.processInstructionHandle(
                instructionHandle.getInstructionHandle().getTargeters(),
                instructionHandle.getInstructionHandle(),
                instructionHandle);
            try {
              instructionList.processInstructionHandle2(instructionHandle.getInstructionHandle());
            } catch (TargetLostException targetLostException) {
              this.processInstructionHandle(targetLostException, instructionHandle);
            }
          } else {
            if (Math.abs(instructionHandle.getPosition() - instructionHandle3.getPosition())
                > 32000) {
              instructionHandle2 = instructionHandle;
              break label107;
            }
            OptimizationStatistics.count13++;
            ((GotoInstruction) instructionHandle.getInstruction()).setTarget(instructionHandle3);
          }
        } else if (instructionHandle.getInstruction() instanceof IfInstruction) {
          InstructionHandle instructionHandle5;
          if ((instructionHandle5 =
                  ((IfInstruction) instructionHandle.getInstruction()).getTarget())
              == null) {
            instructionHandle2 = instructionHandle;
            break label107;
          }
          if (!(instructionHandle5.getInstruction() instanceof GotoInstruction)) {
            instructionHandle2 = instructionHandle;
            break label107;
          }
          int value2 = 0;
          for (InstructionHandle instructionHandle6 = instructionHandle5;
              instructionHandle6 != null
                  && instructionHandle5.getInstruction() instanceof GotoInstruction
                  && value2 < 100;
              instructionHandle6 = instructionHandle5) {
            Instruction instruction2 = instructionHandle5.getInstruction();
            value2++;
            instructionHandle5 = ((GotoInstruction) instruction2).getTarget();
          }
          if (value2 == 100) {
            instructionHandle2 = instructionHandle;
            break label107;
          }
          if (instructionHandle5 == null) {
            instructionHandle2 = instructionHandle;
            break label107;
          }
          if (Math.abs(instructionHandle.getPosition() - instructionHandle5.getPosition())
              > 32000) {
            instructionHandle2 = instructionHandle;
            break label107;
          }
          OptimizationStatistics.count25++;
          ((IfInstruction) instructionHandle.getInstruction()).setTarget(instructionHandle5);
        }
        instructionHandle2 = instructionHandle;
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList15(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof ALOAD)) {
        instructionHandle2 = instructionHandle;
      } else if (!this.matchesInstructionTargeters(instructionHandle.getTargeters())) {
        instructionHandle2 = instructionHandle;
      } else {
        ConstantPoolGen constantPool = classGen.getConstantPool();
        int value = instructionHandle.getInstruction().produceStack(constantPool);
        InstructionHandle instructionHandle3 = null;
        InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
        int value2 = 0;
        while (true) {
          if (instructionHandle22 != null) {
            Instruction instruction;
            if (!((instruction = instructionHandle22.getInstruction())
                    instanceof ArithmeticInstruction)
                && !(instruction instanceof ArrayInstruction)
                && !(instruction instanceof ConversionInstruction)
                && !(instruction instanceof ConstantPushInstruction)
                && !(instruction instanceof FieldOrMethod)
                && !(instruction instanceof LoadInstruction)
                && !(instruction instanceof PushInstruction)
                && !(instruction instanceof NEW)
                && !(instruction instanceof NEWARRAY)
                && !(instruction instanceof NOP)
                && !(instruction instanceof POP)
                && !(instruction instanceof POP2)) {
              instructionHandle2 = instructionHandle3;
              break;
            }
            if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
              instructionHandle2 = instructionHandle3;
              break;
            }
            Instruction instruction2;
            if (value == 0
                && ((instruction2 = instructionHandle22.getInstruction()) instanceof LoadInstruction
                    || instruction2 instanceof ConstantPushInstruction
                    || instruction2 instanceof LDC
                    || instruction2 instanceof LDC2_W
                    || instruction2 instanceof ACONST_NULL)) {
              instructionHandle2 = instructionHandle3 = instructionHandle22;
              break;
            }
            int value3 = instructionHandle22.getInstruction().produceStack(constantPool);
            int value4 = instructionHandle22.getInstruction().consumeStack(constantPool);
            if (value3 != -2) {
              if (value4 == -2) {
                instructionHandle2 = instructionHandle3;
                break;
              }
              int value5;
              if ((value5 = value - value4) < 0) {
                instructionHandle2 = instructionHandle3;
                break;
              }
              value = value5 + value3;
              instructionHandle22 = instructionHandle22.getInstructionHandle2();
              int value6 = value2++;
              if (value6 <= (short) 777) {
                continue;
              }
              instructionHandle2 = instructionHandle3;
              break;
            }
          }
          instructionHandle2 = instructionHandle3;
          break;
        }
        if (instructionHandle2 == null) {
          instructionHandle2 = instructionHandle;
        } else {
          OptimizationStatistics.count18++;
          this.processInstructionHandle(
              instructionHandle3, instructionHandle3.getInstructionHandle2());
          instructionHandle2 = instructionHandle;
          instructionList.processInstructionHandle3(
              instructionHandle3, instructionHandle.getInstructionHandle());
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList16(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!this.matchesInstruction(instructionHandle.getInstruction())) {
        instructionHandle2 = instructionHandle;
      } else if (!this.matchesInstructionTargeters(instructionHandle.getTargeters())) {
        instructionHandle2 = instructionHandle;
      } else {
        ConstantPoolGen constantPool = classGen.getConstantPool();
        Instruction instruction = instructionHandle.getInstruction();
        int value = 0;
        InstructionHandle instructionHandle3 = null;
        InstructionHandle instructionHandle4 = null;
        InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
        int value2 = 0;
        while (true) {
          if (instructionHandle22 != null) {
            Instruction instruction2;
            if (!((instruction2 = instructionHandle22.getInstruction())
                    instanceof ArithmeticInstruction)
                && !(instruction2 instanceof ArrayInstruction)
                && !(instruction2 instanceof ConversionInstruction)
                && !(instruction2 instanceof ConstantPushInstruction)
                && !(instruction2 instanceof FieldOrMethod)
                && !(instruction2 instanceof LocalVariableInstruction)
                && !(instruction2 instanceof PushInstruction)
                && !(instruction2 instanceof PopInstruction)
                && !(instruction2 instanceof NEW)
                && !(instruction2 instanceof NEWARRAY)
                && !(instruction2 instanceof NOP)) {
              instructionHandle2 = instructionHandle4;
              break;
            }
            if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
              instructionHandle2 = instructionHandle4;
              break;
            }
            if (value == 1) {
              Instruction instruction3 = instructionHandle22.getInstruction();
              if (this.matchesInstruction(instruction3)
                  && this.matchesConstantPoolGen(instruction, instruction3, constantPool)) {
                instructionHandle3 = instructionHandle22;
              }
            } else if (value == 2) {
              Instruction instruction4 = instructionHandle22.getInstruction();
              if (this.matchesInstruction(instruction4)
                  && this.matchesConstantPoolGen(instruction, instruction4, constantPool)) {
                instructionHandle2 = instructionHandle4 = instructionHandle22;
                break;
              }
            }
            int value3 = instructionHandle22.getInstruction().produceStack(constantPool);
            int value4 = instructionHandle22.getInstruction().consumeStack(constantPool);
            if (value3 != -2) {
              if (value4 == -2) {
                instructionHandle2 = instructionHandle4;
                break;
              }
              int value5;
              if ((value5 = value - value4) < 0) {
                instructionHandle2 = instructionHandle4;
                break;
              }
              value = value5 + value3;
              instructionHandle22 = instructionHandle22.getInstructionHandle2();
              int value6 = value2++;
              if (value6 <= (short) 777) {
                continue;
              }
              instructionHandle2 = instructionHandle4;
              break;
            }
          }
          instructionHandle2 = instructionHandle4;
          break;
        }
        InstructionHandle instructionHandle5 =
            instructionHandle2 != null ? instructionHandle4 : instructionHandle3;
        int value7 = instructionHandle4 != null ? 2 : 1;
        if (instructionHandle5 == null) {
          instructionHandle2 = instructionHandle;
        } else {
          OptimizationStatistics.count17++;
          InstructionHandle instructionHandle6;
          InstructionHandle instructionHandle7 =
              instructionHandle6 = instructionHandle.getInstructionHandle2();
          this.processInstructionHandle(
              instructionHandle6.getInstructionHandle(), instructionHandle6);
          this.processInstructionHandle2(
              instructionHandle7.getInstructionHandle(), instructionHandle5);
          label88:
          {
            try {
              instructionList.processInstructionHandle2(instructionHandle6.getInstructionHandle());
            } catch (TargetLostException targetLostException) {
              instructionHandle2 = instructionHandle5;
              break label88;
            }
            instructionHandle2 = instructionHandle5;
          }
          if (instructionHandle2.getInstruction().produceStack(constantPool) == 1) {
            if (value7 == 2) {
              instructionHandle2 = instructionHandle6;
              instructionList.createInstructionHandle2(
                  instructionHandle5, InstructionConstants.DUP_X2);
            } else {
              instructionList.createInstructionHandle2(
                  instructionHandle5, InstructionConstants.DUP_X1);
              instructionHandle2 = instructionHandle6;
            }
          } else {
            label137:
            {
              if (instructionHandle5.getInstruction().produceStack(constantPool) == 2) {
                if (value7 == 2) {
                  instructionHandle2 = instructionHandle6;
                  instructionList.createInstructionHandle2(
                      instructionHandle5, InstructionConstants.DUP2_X2);
                  break label137;
                }
                instructionList.createInstructionHandle2(
                    instructionHandle5, InstructionConstants.DUP2_X1);
              }
              instructionHandle2 = instructionHandle6;
            }
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList17(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    MethodOptimizer methodOptimizer;
    int value;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof LoadInstruction)) {
        instructionHandle2 = instructionHandle;
      } else if (instructionHandle.getInstructionHandle2() == null) {
        instructionHandle2 = instructionHandle;
      } else {
        InstructionHandle instructionHandle3;
        if ((instructionHandle3 = instructionHandle.getInstructionHandle2()).getInstructionHandle2()
            == null) {
          instructionHandle2 = instructionHandle;
        } else {
          InstructionHandle instructionHandle4;
          if ((instructionHandle4 = instructionHandle3.getInstructionHandle2())
                  .getInstructionHandle2()
              == null) {
            instructionHandle2 = instructionHandle;
          } else {
            InstructionHandle instructionHandle5;
            if ((instructionHandle5 = instructionHandle4.getInstructionHandle2())
                    .getInstructionHandle2()
                == null) {
              instructionHandle2 = instructionHandle;
            } else {
              InstructionHandle instructionHandle6;
              if ((instructionHandle6 = instructionHandle5.getInstructionHandle2())
                      .getInstructionHandle2()
                  == null) {
                instructionHandle2 = instructionHandle;
              } else {
                InstructionHandle instructionHandle22 = instructionHandle6.getInstructionHandle2();
                if (!(instructionHandle3.getInstruction() instanceof StoreInstruction)) {
                  instructionHandle2 = instructionHandle;
                } else if (!(instructionHandle4.getInstruction() instanceof LoadInstruction)) {
                  instructionHandle2 = instructionHandle;
                } else if (!(instructionHandle5.getInstruction() instanceof StoreInstruction)) {
                  instructionHandle2 = instructionHandle;
                } else if (!(instructionHandle6.getInstruction() instanceof LoadInstruction)) {
                  instructionHandle2 = instructionHandle;
                } else if (!(instructionHandle22.getInstruction() instanceof StoreInstruction)) {
                  instructionHandle2 = instructionHandle;
                } else if (((LoadInstruction) instructionHandle.getInstruction()).getIndex()
                    != ((StoreInstruction) instructionHandle5.getInstruction()).getIndex()) {
                  instructionHandle2 = instructionHandle;
                } else if (((StoreInstruction) instructionHandle3.getInstruction()).getIndex()
                    != ((LoadInstruction) instructionHandle6.getInstruction()).getIndex()) {
                  instructionHandle2 = instructionHandle;
                } else if (((LoadInstruction) instructionHandle4.getInstruction()).getIndex()
                    != ((StoreInstruction) instructionHandle22.getInstruction()).getIndex()) {
                  instructionHandle2 = instructionHandle;
                } else {
                  byte value2 = 1;
                  InstructionHandle instructionHandle23 = instructionHandle.getInstructionHandle2();
                  int value3;
                  int value4 = value3 = 1;
                  while (true) {
                    if (value4 >= 6) {
                      value = value2;
                      break;
                    }
                    if (!this.matchesInstructionTargeters(instructionHandle23.getTargeters())) {
                      value = 0;
                      break;
                    }
                    InstructionHandle instructionHandle7 = instructionHandle23;
                    value3++;
                    instructionHandle23 = instructionHandle7.getInstructionHandle2();
                    value4 = value3;
                  }
                  if (value == 0) {
                    instructionHandle2 = instructionHandle;
                  } else {
                    label122:
                    {
                      OptimizationStatistics.count28++;
                      this.processInstructionHandle(instructionHandle3, instructionHandle);
                      this.processInstructionHandle2(instructionHandle3, instructionHandle);
                      label91:
                      {
                        try {
                          instructionList.processInstructionHandle2(instructionHandle3);
                        } catch (TargetLostException targetLostException) {
                          methodOptimizer = this;
                          break label91;
                        }
                        methodOptimizer = this;
                      }
                      methodOptimizer.processInstructionHandle(
                          instructionHandle6, instructionHandle22);
                      this.processInstructionHandle2(instructionHandle6, instructionHandle22);
                      try {
                        instructionList.processInstructionHandle2(instructionHandle6);
                      } catch (TargetLostException targetLostException2) {
                        instructionHandle2 = instructionHandle;
                        break label122;
                      }
                      instructionHandle2 = instructionHandle;
                    }
                  }
                }
              }
            }
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public static int[] getCounts(int value, int value2) {
    int value3 = value;
    int value4 = value;
    int value5;
    for (int value6 = value5 = 0; value6 < value2; value6 = ++value5) {
      if ((value >> value5) % 2 == 0) {
        if (RandomUtils.calculateValue() < 0.5) {
          value3 |= 1 << value5;
        } else {
          value4 |= 1 << value5;
        }
      }
    }
    return new int[] {value3, value4};
  }

  public boolean matchesConstantPoolGen(
      Instruction instruction, Instruction instruction2, ConstantPoolGen constantPoolGen) {
    if (instruction instanceof ConstantPushInstruction
        && instruction2 instanceof ConstantPushInstruction) {
      if (((ConstantPushInstruction) instruction).getType(constantPoolGen)
              == ((ConstantPushInstruction) instruction2).getType(constantPoolGen)
          && ((ConstantPushInstruction) instruction)
              .getValue()
              .equals(((ConstantPushInstruction) instruction2).getValue())) {
        return true;
      }
    } else {
      if (instruction instanceof ACONST_NULL && instruction2 instanceof ACONST_NULL) {
        return true;
      }
      if (instruction instanceof LDC && instruction2 instanceof LDC) {
        if (((LDC) instruction).getIndex() == ((LDC) instruction2).getIndex()) {
          return true;
        }
      } else if (instruction instanceof LDC2_W && instruction2 instanceof LDC2_W) {
        if (((LDC2_W) instruction).getIndex() == ((LDC2_W) instruction2).getIndex()) {
          return true;
        }
      } else if (instruction instanceof GETSTATIC
          && instruction2 instanceof GETSTATIC
          && ((GETSTATIC) instruction).getIndex() == ((GETSTATIC) instruction2).getIndex()) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void notify(ClassGen classGen) {
    Method[] methods = classGen.getMethods();
    int value;
    for (int value2 = value = 0; value2 < methods.length; value2 = ++value) {
      Method method;
      if ((method = methods[value]).getCode() != null) {
        MethodGen methodGen =
            MethodFactory.createMethodGen(
                method,
                classGen.getClassName(),
                classGen.getConstantPool(),
                classGen.getConstantPool().getConstantPool());
        if (!Configuration.getObfuscationOptions()
            .matchesMethodGen(this.classStorage, classGen, methodGen)) {
          if (Configuration.getMethodCallRemovalOptions().isEnabled()) {
            InstructionList instructionList = methodGen.getInstructionList();
            instructionList.update();
            this.processInstructionList12(classGen, methodGen, instructionList);
            methodGen.update5();
            methodGen.update4();
            classGen.replaceMethod(method, methodGen.getMethod());
          }
          if (Configuration.getFieldRemovalOptions().isValues()) {
            InstructionList instructionList2 = methodGen.getInstructionList();
            instructionList2.update();
            this.processInstructionList3(classGen, methodGen, instructionList2);
            methodGen.update5();
            methodGen.update4();
            classGen.replaceMethod(method, methodGen.getMethod());
          }
        } else {
          InstructionList instructionList3;
          (instructionList3 = methodGen.getInstructionList()).update();
          if (Configuration.getMethodCallRemovalOptions().isEnabled()) {
            this.processInstructionList12(classGen, methodGen, instructionList3);
          }
          if (Configuration.getFieldRemovalOptions().isValues()) {
            this.processInstructionList3(classGen, methodGen, instructionList3);
          }
          int value3;
          value2 =
              value3 =
                  Configuration.getExtensiveFlowOptions()
                      .calculateCount(this.classStorage, classGen, methodGen);
          Configuration.getExtensiveFlowOptions();
          if (value2 != 0 && !method.getText().startsWith("Allatori")) {
            this.processInstructionList7(classGen, methodGen, instructionList3);
          }
          this.processInstructionList20(classGen, methodGen, instructionList3);
          this.processInstructionList18(classGen, methodGen, instructionList3);
          this.processInstructionList25(classGen, methodGen, instructionList3);
          this.processInstructionList17(classGen, methodGen, instructionList3);
          this.processInstructionList13(classGen, methodGen, instructionList3);
          this.processInstructionList9(classGen, methodGen, instructionList3);
          this.processEnabled2(classGen, methodGen, instructionList3, true);
          this.processEnabled2(classGen, methodGen, instructionList3, false);
          this.processInstructionList15(classGen, methodGen, instructionList3);
          this.processEnabled(classGen, methodGen, instructionList3, false);
          this.processInstructionList2(classGen, methodGen, instructionList3);
          this.processInstructionList27(classGen, methodGen, instructionList3);
          this.processEnabled(classGen, methodGen, instructionList3, true);
          this.processInstructionList16(classGen, methodGen, instructionList3);
          this.processInstructionList16(classGen, methodGen, instructionList3);
          this.processInstructionList26(classGen, methodGen, instructionList3);
          this.processInstructionList(classGen, methodGen, instructionList3);
          this.processInstructionList19(classGen, methodGen, instructionList3);
          this.processInstructionList6(classGen, methodGen, instructionList3);
          this.processInstructionList14(classGen, methodGen, instructionList3);
          this.processInstructionList8(classGen, methodGen, instructionList3);
          this.processInstructionList5(classGen, methodGen, instructionList3);
          this.processInstructionList21(classGen, methodGen, instructionList3);
          this.processInstructionList11(classGen, methodGen, instructionList3);
          Configuration.getExtensiveFlowOptions();
          if (value3 != 0) {
            this.processInstructionList23(classGen, methodGen, instructionList3);
          }
          this.processInstructionList10(classGen, methodGen, instructionList3);
          Configuration.getExtensiveFlowOptions();
          if (value3 == 1) {
            this.processCount(
                classGen, methodGen, instructionList3, 20, 16, 16, 20, 16, 16, 8, 4, 4);
          } else {
            Configuration.getExtensiveFlowOptions();
            if (value3 == 2) {
              this.processCount(
                  classGen, methodGen, instructionList3, 64, 32, 32, 64, 32, 64, 32, 8, 8);
            } else if (Configuration.getObfuscationOptions().isEnabled3()
                && Configuration.getObfuscationOptions().isMethodsNamingEnabled()) {
              this.processCount(classGen, methodGen, instructionList3, 8, 4, 4, 8, 4, 4, 4, 2, 2);
            }
          }
          if (Configuration.getMethodRemovalOptions()
              .matchesMethod(this.classStorage, classGen, method)) {
            this.processInstructionList4(classGen, methodGen, instructionList3);
          }
          methodGen.update5();
          methodGen.update4();
          classGen.replaceMethod(method, methodGen.getMethod());
        }
      }
    }
  }

  public void processInstructionList18(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof GotoInstruction)) {
        instructionHandle2 = instructionHandle;
      } else if (!this.matchesInstructionTargeters(instructionHandle.getTargeters())) {
        instructionHandle2 = instructionHandle;
      } else {
        label27:
        {
          GotoInstruction gotoInstruction;
          InstructionHandle instructionHandle3;
          if ((instructionHandle3 =
                  (gotoInstruction = (GotoInstruction) instructionHandle.getInstruction())
                      .getTarget())
              != null) {
            if (instructionHandle3.getInstructionHandle2() == null) {
              instructionHandle2 = instructionHandle;
              break label27;
            }
            if (!(instructionHandle3.getInstruction() instanceof LoadInstruction)) {
              instructionHandle2 = instructionHandle;
              break label27;
            }
            gotoInstruction.setTarget(instructionHandle3.getInstructionHandle2());
            instructionList.createInstructionHandle2(
                gotoInstruction, instructionHandle3.getInstruction().copy());
            OptimizationStatistics.count26++;
          }
          instructionHandle2 = instructionHandle;
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processCount(
      ClassGen classGen,
      MethodGen methodGen,
      InstructionList instructionList,
      int value,
      int value2,
      int value3,
      int value4,
      int value5,
      int value6,
      int value7,
      int value8,
      int value9) {
    InstructionList instructionList2;
    InstructionList instructionList3;
    InstructionList instructionList4;
    InstructionList instructionList5;
    InstructionList instructionList6;
    InstructionList instructionList7;
    InstructionList instructionList8;
    InstructionList instructionList9;
    InstructionList instructionList10;
    InstructionList instructionList11;
    InstructionList instructionList12;
    InstructionList instructionList13;
    InstructionList instructionList14;
    InstructionList instructionList15;
    InstructionList instructionList16;
    this.processCount(classGen, methodGen, instructionList, value4);
    int value10 = 0;
    int value11 = 0;
    int value12 = 0;
    int value13 = 0;
    int value14 = 0;
    int value15 = 0;
    int value16 = 0;
    int value17 = 0;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null
        && (value10 < value || value11 < value2 || value12 < value3)) {
      if (!this.matchesInstructionTargeters(instructionHandle.getTargeters())) {
        instructionHandle2 = instructionHandle;
      } else if (instructionHandle.getInstruction() == InstructionConstants.ICONST_0) {
        if (value10 >= value) {
          instructionHandle2 = instructionHandle;
        } else {
          int value18;
          InstructionHandle instructionHandle3;
          InstructionHandle instructionHandle4;
          MethodOptimizer methodOptimizer;
          if ((value18 = RandomUtils.calculateCount(5)) == 0) {
            instructionHandle3 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.IAND);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_5);
            instructionHandle4 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_2);
            methodOptimizer = this;
          } else if (value18 == 1) {
            instructionHandle3 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ISHR);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_2);
            instructionHandle4 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_3);
            methodOptimizer = this;
          } else if (value18 == 2) {
            instructionHandle3 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ISHR);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_3);
            instructionHandle4 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_5);
            methodOptimizer = this;
          } else if (value18 == 3) {
            instructionHandle3 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.IAND);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_4);
            instructionHandle4 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_3);
            methodOptimizer = this;
          } else {
            instructionHandle3 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.IXOR);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_3);
            instructionHandle4 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_3);
            methodOptimizer = this;
          }
          methodOptimizer.processInstructionHandle(instructionHandle, instructionHandle4);
          label580:
          {
            label565:
            {
              try {
                this.processInstructionHandle2(instructionHandle, instructionHandle3);
              } catch (ClassGenException classGenException) {
                {
                  instructionList16 = instructionList;
                  break label565;
                }
              }
              instructionList16 = instructionList;
            }
            try {
              instructionList16.processInstructionHandle2(instructionHandle);
            } catch (TargetLostException targetLostException) {
              instructionHandle2 = instructionHandle3;
              break label580;
            }
            instructionHandle2 = instructionHandle3;
          }
          InstructionHandle instructionHandle5 = instructionHandle2;
          value10++;
          instructionHandle2 = instructionHandle5;
        }
      } else if (instructionHandle.getInstruction() == InstructionConstants.ICONST_1) {
        if (value10 >= value) {
          instructionHandle2 = instructionHandle;
        } else if (instructionHandle.getInstructionHandle2() != null
            && instructionHandle.getInstructionHandle2().getInstructionHandle2() != null
            && instructionHandle.getInstructionHandle2().getInstruction()
                == InstructionConstants.DUP
            && instructionHandle.getInstructionHandle2().getInstructionHandle2().getInstruction()
                == InstructionConstants.POP2) {
          instructionHandle2 = instructionHandle;
        } else {
          int value19;
          InstructionHandle instructionHandle6;
          InstructionHandle instructionHandle7;
          MethodOptimizer methodOptimizer2;
          if ((value19 = RandomUtils.calculateCount(7)) == 0) {
            instructionHandle6 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.IAND);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_5);
            instructionHandle7 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_3);
            methodOptimizer2 = this;
          } else if (value19 == 1) {
            instructionHandle6 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ISHR);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_1);
            instructionHandle7 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_3);
            methodOptimizer2 = this;
          } else if (value19 == 2) {
            instructionHandle6 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ISHR);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_2);
            instructionHandle7 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_5);
            methodOptimizer2 = this;
          } else if (value19 == 3) {
            instructionHandle6 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.IXOR);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_3);
            instructionHandle7 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_2);
            methodOptimizer2 = this;
          } else if (value19 != 4 && value19 != 5) {
            instructionHandle6 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.IXOR);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_5);
            instructionHandle7 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_4);
            methodOptimizer2 = this;
          } else {
            instructionHandle6 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.INEG);
            instructionHandle7 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_M1);
            methodOptimizer2 = this;
          }
          methodOptimizer2.processInstructionHandle(instructionHandle, instructionHandle7);
          label583:
          {
            label533:
            {
              try {
                this.processInstructionHandle2(instructionHandle, instructionHandle6);
              } catch (ClassGenException classGenException2) {
                {
                  instructionList15 = instructionList;
                  break label533;
                }
              }
              instructionList15 = instructionList;
            }
            try {
              instructionList15.processInstructionHandle2(instructionHandle);
            } catch (TargetLostException targetLostException2) {
              instructionHandle2 = instructionHandle6;
              break label583;
            }
            instructionHandle2 = instructionHandle6;
          }
          InstructionHandle instructionHandle8 = instructionHandle2;
          value10++;
          instructionHandle2 = instructionHandle8;
        }
      } else if (instructionHandle.getInstruction() == InstructionConstants.ICONST_2) {
        if (value11 >= value2) {
          instructionHandle2 = instructionHandle;
        } else {
          InstructionHandle instructionHandle9;
          InstructionHandle instructionHandle10;
          int value20;
          MethodOptimizer methodOptimizer3;
          if ((value20 = RandomUtils.calculateCount(3)) == 0) {
            instructionHandle9 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ISHR);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_1);
            instructionHandle10 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_5);
            methodOptimizer3 = this;
          } else if (value20 == 1) {
            instructionHandle9 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.INEG);
            instructionHandle10 =
                instructionList.getInstructionHandle2(
                    instructionHandle, new PUSH(classGen.getConstantPool(), -2));
            methodOptimizer3 = this;
          } else {
            instructionHandle9 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.IXOR);
            instructionList.createInstructionHandle2(
                instructionHandle, InstructionConstants.ICONST_3);
            instructionHandle10 =
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.ICONST_1);
            methodOptimizer3 = this;
          }
          methodOptimizer3.processInstructionHandle(instructionHandle, instructionHandle10);
          label585:
          {
            label520:
            {
              try {
                this.processInstructionHandle2(instructionHandle, instructionHandle9);
              } catch (ClassGenException classGenException3) {
                {
                  instructionList14 = instructionList;
                  break label520;
                }
              }
              instructionList14 = instructionList;
            }
            try {
              instructionList14.processInstructionHandle2(instructionHandle);
            } catch (TargetLostException targetLostException3) {
              instructionHandle2 = instructionHandle9;
              break label585;
            }
            instructionHandle2 = instructionHandle9;
          }
          InstructionHandle instructionHandle11 = instructionHandle2;
          value11++;
          instructionHandle2 = instructionHandle11;
        }
      } else if (instructionHandle.getInstruction() == InstructionConstants.ICONST_3) {
        if (value11 >= value2) {
          instructionHandle2 = instructionHandle;
        } else {
          InstructionHandle instructionHandle12 =
              instructionList.createInstructionHandle2(
                  instructionHandle, InstructionConstants.INEG);
          InstructionHandle instructionHandle22 =
              instructionList.getInstructionHandle2(
                  instructionHandle, new PUSH(classGen.getConstantPool(), -3));
          this.processInstructionHandle(instructionHandle, instructionHandle22);
          label587:
          {
            label507:
            {
              try {
                this.processInstructionHandle2(instructionHandle, instructionHandle12);
              } catch (ClassGenException classGenException4) {
                {
                  instructionList13 = instructionList;
                  break label507;
                }
              }
              instructionList13 = instructionList;
            }
            try {
              instructionList13.processInstructionHandle2(instructionHandle);
            } catch (TargetLostException targetLostException4) {
              instructionHandle2 = instructionHandle12;
              break label587;
            }
            instructionHandle2 = instructionHandle12;
          }
          InstructionHandle instructionHandle13 = instructionHandle2;
          value11++;
          instructionHandle2 = instructionHandle13;
        }
      } else if (instructionHandle.getInstruction() == InstructionConstants.ICONST_4) {
        if (value11 >= value2) {
          instructionHandle2 = instructionHandle;
        } else {
          InstructionHandle instructionHandle14 =
              instructionList.createInstructionHandle2(
                  instructionHandle, InstructionConstants.INEG);
          InstructionHandle instructionHandle23 =
              instructionList.getInstructionHandle2(
                  instructionHandle, new PUSH(classGen.getConstantPool(), -4));
          this.processInstructionHandle(instructionHandle, instructionHandle23);
          label589:
          {
            label494:
            {
              try {
                this.processInstructionHandle2(instructionHandle, instructionHandle14);
              } catch (ClassGenException classGenException5) {
                {
                  instructionList12 = instructionList;
                  break label494;
                }
              }
              instructionList12 = instructionList;
            }
            try {
              instructionList12.processInstructionHandle2(instructionHandle);
            } catch (TargetLostException targetLostException5) {
              instructionHandle2 = instructionHandle14;
              break label589;
            }
            instructionHandle2 = instructionHandle14;
          }
          InstructionHandle instructionHandle15 = instructionHandle2;
          value11++;
          instructionHandle2 = instructionHandle15;
        }
      } else if (instructionHandle.getInstruction() == InstructionConstants.ICONST_5) {
        if (value11 >= value2) {
          instructionHandle2 = instructionHandle;
        } else {
          InstructionHandle instructionHandle16 =
              instructionList.createInstructionHandle2(
                  instructionHandle, InstructionConstants.INEG);
          InstructionHandle instructionHandle24 =
              instructionList.getInstructionHandle2(
                  instructionHandle, new PUSH(classGen.getConstantPool(), -5));
          this.processInstructionHandle(instructionHandle, instructionHandle24);
          label591:
          {
            label481:
            {
              try {
                this.processInstructionHandle2(instructionHandle, instructionHandle16);
              } catch (ClassGenException classGenException6) {
                {
                  instructionList11 = instructionList;
                  break label481;
                }
              }
              instructionList11 = instructionList;
            }
            try {
              instructionList11.processInstructionHandle2(instructionHandle);
            } catch (TargetLostException targetLostException6) {
              instructionHandle2 = instructionHandle16;
              break label591;
            }
            instructionHandle2 = instructionHandle16;
          }
          InstructionHandle instructionHandle17 = instructionHandle2;
          value11++;
          instructionHandle2 = instructionHandle17;
        }
      } else if (instructionHandle.getInstruction() == InstructionConstants.ICONST_M1) {
        if (value11 >= value2) {
          instructionHandle2 = instructionHandle;
        } else {
          InstructionHandle instructionHandle18 =
              instructionList.createInstructionHandle2(
                  instructionHandle, InstructionConstants.ISHR);
          instructionList.createInstructionHandle2(
              instructionHandle, InstructionConstants.ICONST_2);
          InstructionHandle instructionHandle25 =
              instructionList.getInstructionHandle2(
                  instructionHandle, new PUSH(classGen.getConstantPool(), -4));
          this.processInstructionHandle(instructionHandle, instructionHandle25);
          label593:
          {
            label468:
            {
              try {
                this.processInstructionHandle2(instructionHandle, instructionHandle18);
              } catch (ClassGenException classGenException7) {
                {
                  instructionList10 = instructionList;
                  break label468;
                }
              }
              instructionList10 = instructionList;
            }
            try {
              instructionList10.processInstructionHandle2(instructionHandle);
            } catch (TargetLostException targetLostException7) {
              instructionHandle2 = instructionHandle18;
              break label593;
            }
            instructionHandle2 = instructionHandle18;
          }
          InstructionHandle instructionHandle19 = instructionHandle2;
          value11++;
          instructionHandle2 = instructionHandle19;
        }
      } else {
        label460:
        {
          if (instructionHandle.getInstruction() instanceof ConstantPushInstruction) {
            if (value12 >= value3) {
              instructionHandle2 = instructionHandle;
              break label460;
            }
            ConstantPoolGen constantPool = classGen.getConstantPool();
            ConstantPushInstruction constantPushInstruction;
            if ((constantPushInstruction =
                            (ConstantPushInstruction) instructionHandle.getInstruction())
                        .getType(constantPool)
                    != Type.BYTE
                && constantPushInstruction.getType(constantPool) != Type.SHORT) {
              if (constantPushInstruction.getType(constantPool) != Type.CHAR
                  && constantPushInstruction.getType(constantPool) != Type.INT
                  && constantPushInstruction.getType(constantPool) == Type.LONG) {}
            } else {
              byte value21 = 7;
              if (constantPushInstruction.getType(constantPool) == Type.SHORT) {
                value21 = 15;
              }
              int value22;
              int[] counts =
                  getCounts(value22 = constantPushInstruction.getValue().intValue(), value21);
              int value23 = counts[0];
              int value24 = counts[1];
              if ((value23 & value24) != value22) {
                instructionHandle2 = instructionHandle;
                break label460;
              }
              InstructionHandle instructionHandle20 =
                  instructionList.createInstructionHandle2(
                      instructionHandle, InstructionConstants.IAND);
              instructionList.getInstructionHandle2(
                  instructionHandle, new PUSH(classGen.getConstantPool(), value23));
              InstructionHandle instructionHandle26 =
                  instructionList.getInstructionHandle2(
                      instructionHandle, new PUSH(classGen.getConstantPool(), value24));
              this.processInstructionHandle(instructionHandle, instructionHandle26);
              label595:
              {
                label447:
                {
                  try {
                    this.processInstructionHandle2(instructionHandle, instructionHandle20);
                  } catch (ClassGenException classGenException8) {
                    {
                      instructionList9 = instructionList;
                      break label447;
                    }
                  }
                  instructionList9 = instructionList;
                }
                try {
                  instructionList9.processInstructionHandle2(instructionHandle);
                } catch (TargetLostException targetLostException8) {
                  instructionHandle2 = instructionHandle20;
                  break label595;
                }
                instructionHandle2 = instructionHandle20;
              }
              instructionHandle = instructionHandle2;
              value12++;
            }
          } else if (instructionHandle.getInstruction() instanceof LDC) {
            LDC lDC = (LDC) instructionHandle.getInstruction();
            ConstantPoolGen constantPool2 = classGen.getConstantPool();
            if (lDC.getType(constantPool2) == Type.INT) {
              int value25 = (Integer) lDC.getValue(constantPool2);
              int value26 = 0;
              int value27 = value25;
              while (value27 > 0 && (value25 & 1) == 0) {
                int value28 = value25 >> 1;
                value26++;
                value27 = value25 = value28;
              }
              if (value25 == (short) value25 && value26 > 0 && value13 < value5) {
                InstructionHandle instructionHandle21 =
                    instructionList.createInstructionHandle2(
                        instructionHandle, InstructionConstants.ISHL);
                instructionList.getInstructionHandle2(
                    instructionHandle, new PUSH(classGen.getConstantPool(), value26));
                InstructionHandle instructionHandle27 =
                    instructionList.getInstructionHandle2(
                        instructionHandle, new PUSH(classGen.getConstantPool(), value25));
                this.processInstructionHandle(instructionHandle, instructionHandle27);
                label599:
                {
                  label421:
                  {
                    try {
                      this.processInstructionHandle2(instructionHandle, instructionHandle21);
                    } catch (ClassGenException classGenException9) {
                      {
                        instructionList8 = instructionList;
                        break label421;
                      }
                    }
                    instructionList8 = instructionList;
                  }
                  try {
                    instructionList8.processInstructionHandle2(instructionHandle);
                  } catch (TargetLostException targetLostException9) {
                    instructionHandle2 = instructionHandle21;
                    break label599;
                  }
                  instructionHandle2 = instructionHandle21;
                }
                instructionHandle = instructionHandle2;
                value13++;
              } else if (value26 > 0 && value16 < value8) {
                InstructionHandle instructionHandle28 =
                    instructionList.createInstructionHandle2(
                        instructionHandle, InstructionConstants.ISHL);
                instructionList.getInstructionHandle2(
                    instructionHandle, new PUSH(classGen.getConstantPool(), value26));
                InstructionHandle instructionHandle29 =
                    instructionList.getInstructionHandle2(
                        instructionHandle, new PUSH(classGen.getConstantPool(), value25));
                this.processInstructionHandle(instructionHandle, instructionHandle29);
                label601:
                {
                  label406:
                  {
                    try {
                      this.processInstructionHandle2(instructionHandle, instructionHandle28);
                    } catch (ClassGenException classGenException10) {
                      {
                        instructionList7 = instructionList;
                        break label406;
                      }
                    }
                    instructionList7 = instructionList;
                  }
                  try {
                    instructionList7.processInstructionHandle2(instructionHandle);
                  } catch (TargetLostException targetLostException10) {
                    instructionHandle2 = instructionHandle28;
                    break label601;
                  }
                  instructionHandle2 = instructionHandle28;
                }
                instructionHandle = instructionHandle2;
                value16++;
              } else if (value26 == 0 && value16 < value8) {
                int value29 = RandomUtils.calculateCount(32000) + 555;
                int value30;
                int value31;
                if (((value31 = (value30 = (Integer) lDC.getValue(constantPool2)) ^ value29)
                        ^ value29)
                    != value30) {
                  instructionHandle2 = instructionHandle;
                  break label460;
                }
                InstructionHandle instructionHandle30 =
                    instructionList.createInstructionHandle2(
                        instructionHandle, InstructionConstants.IXOR);
                instructionList.getInstructionHandle2(
                    instructionHandle, new PUSH(classGen.getConstantPool(), value29));
                InstructionHandle instructionHandle210 =
                    instructionList.getInstructionHandle2(
                        instructionHandle, new PUSH(classGen.getConstantPool(), value31));
                this.processInstructionHandle(instructionHandle, instructionHandle210);
                label603:
                {
                  label391:
                  {
                    try {
                      this.processInstructionHandle2(instructionHandle, instructionHandle30);
                    } catch (ClassGenException classGenException11) {
                      {
                        instructionList6 = instructionList;
                        break label391;
                      }
                    }
                    instructionList6 = instructionList;
                  }
                  try {
                    instructionList6.processInstructionHandle2(instructionHandle);
                  } catch (TargetLostException targetLostException11) {
                    instructionHandle2 = instructionHandle30;
                    break label603;
                  }
                  instructionHandle2 = instructionHandle30;
                }
                instructionHandle = instructionHandle2;
                value16++;
              }
            }
          } else if (instructionHandle.getInstruction() instanceof LDC2_W) {
            LDC2_W lDC2_W = (LDC2_W) instructionHandle.getInstruction();
            ConstantPoolGen constantPool3 = classGen.getConstantPool();
            if (lDC2_W.getType(constantPool3) == Type.LONG) {
              long value32 = lDC2_W.getValue(constantPool3).longValue();
              int value33 = 0;
              long value34 = value32;
              while (value34 > 0L && (value32 & 1L) == 0L) {
                long value35 = value32 >> 1;
                value33++;
                value34 = value32 = value35;
              }
              if (value32 == (short) value32 && value33 > 0 && value14 < value6) {
                InstructionHandle instructionHandle31 =
                    instructionList.createInstructionHandle2(
                        instructionHandle, InstructionConstants.LSHL);
                instructionList.getInstructionHandle2(
                    instructionHandle, new PUSH(classGen.getConstantPool(), value33));
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.I2L);
                InstructionHandle instructionHandle211 =
                    instructionList.getInstructionHandle2(
                        instructionHandle, new PUSH(classGen.getConstantPool(), (int) value32));
                this.processInstructionHandle(instructionHandle, instructionHandle211);
                label607:
                {
                  label342:
                  {
                    try {
                      this.processInstructionHandle2(instructionHandle, instructionHandle31);
                    } catch (ClassGenException classGenException12) {
                      {
                        instructionList5 = instructionList;
                        break label342;
                      }
                    }
                    instructionList5 = instructionList;
                  }
                  try {
                    instructionList5.processInstructionHandle2(instructionHandle);
                  } catch (TargetLostException targetLostException12) {
                    instructionHandle2 = instructionHandle31;
                    break label607;
                  }
                  instructionHandle2 = instructionHandle31;
                }
                InstructionHandle instructionHandle32 = instructionHandle2;
                value14 += 1;
                instructionHandle2 = instructionHandle32;
                break label460;
              }
              if (value32 == (int) value32 && value33 > 0 && value15 < value7) {
                InstructionHandle instructionHandle33 =
                    instructionList.createInstructionHandle2(
                        instructionHandle, InstructionConstants.LSHL);
                instructionList.getInstructionHandle2(
                    instructionHandle, new PUSH(classGen.getConstantPool(), value33));
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.I2L);
                InstructionHandle instructionHandle212 =
                    instructionList.getInstructionHandle2(
                        instructionHandle, new PUSH(classGen.getConstantPool(), (int) value32));
                this.processInstructionHandle(instructionHandle, instructionHandle212);
                label609:
                {
                  label331:
                  {
                    try {
                      this.processInstructionHandle2(instructionHandle, instructionHandle33);
                    } catch (ClassGenException classGenException13) {
                      {
                        instructionList4 = instructionList;
                        break label331;
                      }
                    }
                    instructionList4 = instructionList;
                  }
                  try {
                    instructionList4.processInstructionHandle2(instructionHandle);
                  } catch (TargetLostException targetLostException13) {
                    instructionHandle2 = instructionHandle33;
                    break label609;
                  }
                  instructionHandle2 = instructionHandle33;
                }
                InstructionHandle instructionHandle34 = instructionHandle2;
                value15++;
                instructionHandle2 = instructionHandle34;
                break label460;
              }
              if (value33 > 0 && value17 < value9) {
                InstructionHandle instructionHandle35 =
                    instructionList.createInstructionHandle2(
                        instructionHandle, InstructionConstants.LSHL);
                instructionList.getInstructionHandle2(
                    instructionHandle, new PUSH(classGen.getConstantPool(), value33));
                InstructionHandle instructionHandle213 =
                    instructionList.getInstructionHandle2(
                        instructionHandle, new PUSH(classGen.getConstantPool(), value32));
                this.processInstructionHandle(instructionHandle, instructionHandle213);
                label611:
                {
                  label320:
                  {
                    try {
                      this.processInstructionHandle2(instructionHandle, instructionHandle35);
                    } catch (ClassGenException classGenException14) {
                      {
                        instructionList3 = instructionList;
                        break label320;
                      }
                    }
                    instructionList3 = instructionList;
                  }
                  try {
                    instructionList3.processInstructionHandle2(instructionHandle);
                  } catch (TargetLostException targetLostException14) {
                    instructionHandle2 = instructionHandle35;
                    break label611;
                  }
                  instructionHandle2 = instructionHandle35;
                }
                InstructionHandle instructionHandle36 = instructionHandle2;
                value17++;
                instructionHandle2 = instructionHandle36;
                break label460;
              }
              if (value33 == 0 && value17 < value9) {
                int value36 = RandomUtils.calculateCount(32000) + 555;
                long value37;
                long value38;
                if (((value37 = (value38 = lDC2_W.getValue(constantPool3).longValue()) ^ value36)
                        ^ value36)
                    != value38) {
                  instructionHandle2 = instructionHandle;
                  break label460;
                }
                InstructionHandle instructionHandle37 =
                    instructionList.createInstructionHandle2(
                        instructionHandle, InstructionConstants.LXOR);
                instructionList.createInstructionHandle2(
                    instructionHandle, InstructionConstants.I2L);
                instructionList.getInstructionHandle2(
                    instructionHandle, new PUSH(classGen.getConstantPool(), value36));
                InstructionHandle instructionHandle214 =
                    instructionList.getInstructionHandle2(
                        instructionHandle, new PUSH(classGen.getConstantPool(), value37));
                this.processInstructionHandle(instructionHandle, instructionHandle214);
                label613:
                {
                  label362:
                  {
                    try {
                      this.processInstructionHandle2(instructionHandle, instructionHandle37);
                    } catch (ClassGenException classGenException15) {
                      {
                        instructionList2 = instructionList;
                        break label362;
                      }
                    }
                    instructionList2 = instructionList;
                  }
                  try {
                    instructionList2.processInstructionHandle2(instructionHandle);
                  } catch (TargetLostException targetLostException15) {
                    instructionHandle2 = instructionHandle37;
                    break label613;
                  }
                  instructionHandle2 = instructionHandle37;
                }
                instructionHandle = instructionHandle2;
                value17++;
              }
            }
          }
          instructionHandle2 = instructionHandle;
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
    OptimizationStatistics.count22 += value10;
    OptimizationStatistics.count15 += value11;
    OptimizationStatistics.count21 += value12;
  }

  public void processInstructionList19(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionList instructionList2;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof StoreInstruction)) {
        instructionHandle2 = instructionHandle;
      } else if (instructionHandle.getInstructionHandle2() == null) {
        instructionHandle2 = instructionHandle;
      } else if (!(instructionHandle.getInstructionHandle2().getInstruction()
          instanceof LoadInstruction)) {
        instructionHandle2 = instructionHandle;
      } else {
        InstructionHandle instructionHandle3 = instructionHandle;
        InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
        StoreInstruction storeInstruction = (StoreInstruction) instructionHandle3.getInstruction();
        LoadInstruction loadInstruction = (LoadInstruction) instructionHandle22.getInstruction();
        if (storeInstruction.getIndex() != loadInstruction.getIndex()) {
          instructionHandle2 = instructionHandle;
        } else if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
          instructionHandle2 = instructionHandle;
        } else if (!this.matchesInstructionTargeters(instructionHandle3.getTargeters())) {
          instructionHandle2 = instructionHandle;
        } else {
          InstructionHandle instructionHandle4;
          if ((instructionHandle4 = instructionHandle22.getInstructionHandle2()) == null) {
            instructionHandle2 = instructionHandle;
          } else {
            InstructionHandle instructionHandle5 = instructionHandle4;
            label67:
            if (this.matchesInstructionHandle(
                instructionList, instructionHandle3, instructionHandle22)) {
              OptimizationStatistics.count3++;
              this.processInstructionHandle(instructionHandle22, instructionHandle4);
              this.processInstructionHandle(instructionHandle3, instructionHandle4);
              this.processInstructionHandle2(instructionHandle22, instructionHandle4);
              this.processInstructionHandle2(instructionHandle3, instructionHandle4);
              label64:
              {
                try {
                  instructionList.processInstructionHandle2(instructionHandle3);
                } catch (TargetLostException targetLostException) {
                  {
                    instructionList2 = instructionList;
                    break label64;
                  }
                }
                instructionList2 = instructionList;
              }
              try {
                instructionList2.processInstructionHandle2(instructionHandle22);
              } catch (TargetLostException targetLostException2) {
                instructionHandle2 = instructionHandle5;
                break label67;
              }
              instructionHandle2 = instructionHandle5;
            } else {
              label104:
              {
                OptimizationStatistics.count2++;
                MethodOptimizer methodOptimizer;
                if (!(storeInstruction instanceof ASTORE)
                    && !(storeInstruction instanceof FSTORE)
                    && !(storeInstruction instanceof ISTORE)) {
                  if (storeInstruction instanceof DSTORE || storeInstruction instanceof LSTORE) {
                    instructionList.createInstructionHandle(
                        instructionHandle3, InstructionConstants.DUP2);
                  }
                  methodOptimizer = this;
                } else {
                  instructionList.createInstructionHandle(
                      instructionHandle3, InstructionConstants.DUP);
                  methodOptimizer = this;
                }
                methodOptimizer.processInstructionHandle(instructionHandle22, instructionHandle4);
                this.processInstructionHandle2(instructionHandle22, instructionHandle3);
                try {
                  instructionList.processInstructionHandle2(instructionHandle22);
                } catch (TargetLostException targetLostException3) {
                  instructionHandle2 = instructionHandle5;
                  break label104;
                }
                instructionHandle2 = instructionHandle5;
              }
            }
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public MethodOptimizer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }

  public void processInstructionHandle(
      TargetLostException targetLostException, InstructionHandle instructionHandle) {
    InstructionHandle[] targets = targetLostException.getTargets();
    int value;
    for (int value2 = value = 0; value2 < targets.length; value2 = ++value) {
      int value3;
      InstructionTargeter[] instructionTargeters;
      if ((instructionTargeters = targets[value].getTargeters()) != null) {
        for (int value4 = value3 = 0; value4 < instructionTargeters.length; value4 = value3) {
          InstructionTargeter instructionTargeter = instructionTargeters[value3];
          int value5 = value;
          value3++;
          instructionTargeter.updateTarget(targets[value5], instructionHandle);
        }
      }
    }
  }

  public void processInstructionHandle(
      InstructionHandle instructionHandle, InstructionHandle instructionHandle2) {
    if (instructionHandle2 != null) {
      if (instructionHandle.getTargeters() != null) {
        if (instructionHandle2.getTargeters() != null) {
          InstructionTargeter[] instructionTargeters;
          int value = (instructionTargeters = instructionHandle2.getTargeters()).length;
          int value2;
          for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
            if (instructionTargeters[value2] instanceof LineNumberGen) {
              return;
            }
          }
        }
        InstructionTargeter[] instructionTargeters2;
        int value4 = (instructionTargeters2 = instructionHandle.getTargeters()).length;
        int value5;
        for (int value6 = value5 = 0; value6 < value4; value6 = ++value5) {
          InstructionTargeter instructionTargeter;
          if ((instructionTargeter = instructionTargeters2[value5]) instanceof LineNumberGen) {
            instructionTargeter.updateTarget(instructionHandle, instructionHandle2);
          }
        }
      }
    }
  }

  public void processInstructionList20(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof NEW)) {
        instructionHandle2 = instructionHandle;
      } else {
        String className =
            ((NEW) instructionHandle.getInstruction())
                .getLoadClassType(classGen.getConstantPool())
                .getClassName();
        if (!"java.lang.StringBuffer".equals(className)
            && !"java.lang.StringBuilder".equals(className)) {
          instructionHandle2 = instructionHandle;
        } else {
          String text = "L" + className.replace('.', '/') + ";";
          if (instructionHandle.getInstructionHandle2() == null) {
            instructionHandle2 = instructionHandle;
          } else if (!(instructionHandle.getInstructionHandle2().getInstruction() instanceof DUP)) {
            instructionHandle2 = instructionHandle;
          } else if (instructionHandle.getInstructionHandle2().getInstructionHandle2() == null) {
            instructionHandle2 = instructionHandle;
          } else if (!(instructionHandle
                  .getInstructionHandle2()
                  .getInstructionHandle2()
                  .getInstruction()
              instanceof INVOKESPECIAL)) {
            instructionHandle2 = instructionHandle;
          } else {
            INVOKESPECIAL nVOKESPECIAL =
                (INVOKESPECIAL)
                    instructionHandle
                        .getInstructionHandle2()
                        .getInstructionHandle2()
                        .getInstruction();
            ConstantPoolGen constantPool = classGen.getConstantPool();
            if (!className.equals(nVOKESPECIAL.getText2(constantPool))) {
              instructionHandle2 = instructionHandle;
            } else if (!"<init>".equals(nVOKESPECIAL.getText4(constantPool))) {
              instructionHandle2 = instructionHandle;
            } else if (!"()V".equals(nVOKESPECIAL.getText3(constantPool))) {
              instructionHandle2 = instructionHandle;
            } else {
              int value = 0;
              InstructionHandle instructionHandle3 = null;
              InstructionHandle instructionHandle4;
              instructionHandle2 =
                  instructionHandle4 =
                      instructionHandle
                          .getInstructionHandle2()
                          .getInstructionHandle2()
                          .getInstructionHandle2();
              while (true) {
                label109:
                if (instructionHandle2 != null) {
                  Instruction instruction;
                  if (!((instruction = instructionHandle4.getInstruction())
                          instanceof ArithmeticInstruction)
                      && !(instruction instanceof ArrayInstruction)
                      && !(instruction instanceof ConversionInstruction)
                      && !(instruction instanceof ConstantPushInstruction)
                      && !(instruction instanceof FieldOrMethod)
                      && !(instruction instanceof LocalVariableInstruction)
                      && !(instruction instanceof PushInstruction)
                      && !(instruction instanceof PopInstruction)
                      && !(instruction instanceof NEW)
                      && !(instruction instanceof NEWARRAY)
                      && !(instruction instanceof NOP)) {
                    instructionHandle2 = instructionHandle3;
                    break;
                  }
                  if (!this.matchesInstructionTargeters(instructionHandle4.getTargeters())) {
                    instructionHandle2 = instructionHandle3;
                    break;
                  }
                  if (instructionHandle4.getInstruction() instanceof INVOKEVIRTUAL) {
                    INVOKEVIRTUAL nVOKEVIRTUAL =
                        (INVOKEVIRTUAL) instructionHandle4.getInstruction();
                    if (className.equals(nVOKEVIRTUAL.getText2(constantPool))
                        && "append".equals(nVOKEVIRTUAL.getText4(constantPool))
                        && ("(Ljava/lang/String;)" + text)
                            .equals(nVOKEVIRTUAL.getText3(constantPool))) {
                      if (value == 1) {
                        instructionHandle2 = instructionHandle3 = instructionHandle4;
                        break;
                      }
                      break label109;
                    }
                  }
                  int value2 = instructionHandle4.getInstruction().produceStack(constantPool);
                  int value3 = instructionHandle4.getInstruction().consumeStack(constantPool);
                  if (value2 != -2) {
                    if (value3 == -2) {
                      instructionHandle2 = instructionHandle3;
                      break;
                    }
                    int value4;
                    if ((value4 = value - value3) < 0) {
                      instructionHandle2 = instructionHandle3;
                      break;
                    }
                    value = value4 + value2;
                    instructionHandle2 =
                        instructionHandle4 = instructionHandle4.getInstructionHandle2();
                    continue;
                  }
                }
                instructionHandle2 = instructionHandle3;
                break;
              }
              if (instructionHandle2 == null) {
                instructionHandle2 = instructionHandle;
              } else {
                OptimizationStatistics.count31++;
                instructionList.createInstructionHandle2(
                    instructionHandle.getInstructionHandle2().getInstructionHandle2(),
                    InstructionConstants.ICONST_0);
                ((INVOKEVIRTUAL) instructionHandle3.getInstruction())
                    .setIndex(
                        constantPool.addMethodref(
                            className, "insert", "(ILjava/lang/String;)" + text));
                instructionHandle2 = instructionHandle;
              }
            }
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionHandle(
      InstructionTargeter[] instructionTargeters,
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2) {
    if (instructionTargeters != null) {
      int value;
      for (int value2 = value = 0; value2 < instructionTargeters.length; value2 = value) {
        InstructionTargeter instructionTargeter = instructionTargeters[value];
        value++;
        instructionTargeter.updateTarget(instructionHandle, instructionHandle2);
      }
    }
  }

  public static int[] getCounts() {
    int value = 0;
    int value2 = 0;
    int value3;
    for (int value4 = value3 = 0; value4 < 32; value4 = ++value3) {
      if (RandomUtils.calculateValue() < 0.5) {
        value |= 1 << value3;
      } else {
        value2 |= 1 << value3;
      }
    }
    return new int[] {value, value2};
  }

  public void processInstructionHandle2(
      InstructionHandle instructionHandle, InstructionHandle instructionHandle2) {
    if (instructionHandle2 != null) {
      if (instructionHandle.getTargeters() != null) {
        InstructionTargeter[] instructionTargeters;
        int value = (instructionTargeters = instructionHandle.getTargeters()).length;
        int value2;
        for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
          InstructionTargeter instructionTargeter;
          if ((instructionTargeter = instructionTargeters[value2]) instanceof LocalVariableGen) {
            instructionTargeter.updateTarget(instructionHandle, instructionHandle2);
          }
        }
      }
    }
  }

  public void processInstructionList21(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    boolean matches;
    instructionList.update();
    LocalVariableGen[] localVariableGens;
    if ((localVariableGens = methodGen.getLocalVariables()) != null) {
      int value = methodGen.getArgumentNames().length;
      if (!methodGen.isEnabled11()) {
        value++;
      }
      Type[] types;
      int value2 = (types = methodGen.getArgumentTypes()).length;
      int value3;
      for (int value4 = value3 = 0; value4 < value2; value4 = ++value3) {
        Type type;
        if ((type = types[value3]) == Type.LONG || type == Type.DOUBLE) {
          value++;
        }
      }
      LocalVariableGen[] localVariableGens2 = localVariableGens;
      int value5 = localVariableGens.length;
      int value6;
      for (int value7 = value6 = 0; value7 < value5; value7 = ++value6) {
        LocalVariableGen localVariableGen;
        if ((localVariableGen = localVariableGens2[value6]).getType() == Type.BOOLEAN
            || localVariableGen.getType() == Type.BYTE
            || localVariableGen.getType() == Type.SHORT
            || localVariableGen.getType() == Type.CHAR) {
          localVariableGen.setType(Type.INT);
        }
      }
      int value8;
      InstructionHandle[] instructionHandles = new InstructionHandle[value8 = methodGen.getCount()];
      InstructionHandle[] instructionHandles2 = new InstructionHandle[value8];
      boolean[] matchess = new boolean[value8];
      Hashtable hashtable = new Hashtable();
      InstructionHandle instructionHandle;
      for (InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
          instructionHandle2 != null;
          instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2()) {
        if (instructionHandle.getInstruction() instanceof LocalVariableInstruction) {
          LocalVariableInstruction localVariableInstruction =
              (LocalVariableInstruction) instructionHandle.getInstruction();
          int value9;
          LocalVariableInstruction localVariableInstruction2;
          if (instructionHandles[value9 = localVariableInstruction.getIndex()] == null) {
            if (value9 < value) {
              localVariableInstruction2 = localVariableInstruction;
              instructionHandles[value9] = instructionList.getStart();
              instructionHandles2[value9] = instructionHandle;
            } else {
              instructionHandles[value9] = instructionHandles2[value9] = instructionHandle;
              localVariableInstruction2 = localVariableInstruction;
            }
          } else {
            instructionHandles2[value9] = instructionHandle;
            localVariableInstruction2 = localVariableInstruction;
          }
          boolean matches2 =
              localVariableInstruction2.produceStack(classGen.getConstantPool()) == 2
                  || localVariableInstruction.consumeStack(classGen.getConstantPool()) == 2;
          if (matches2 && value9 + 1 < value8) {
            matchess[value9] = true;
            if (instructionHandles[value9 + 1] == null) {
              if (value9 + 1 < value) {
                instructionHandles[value9 + 1] = instructionList.getStart();
                instructionHandles2[value9 + 1] = instructionHandle;
              } else {
                instructionHandles[value9 + 1] =
                    instructionHandles2[value9 + 1] = instructionHandle;
              }
            } else {
              instructionHandles2[value9 + 1] = instructionHandle;
            }
          }
        } else if (instructionHandle.getInstruction() instanceof RET) {
          RET rET = (RET) instructionHandle.getInstruction();
          int value10;
          if (instructionHandles[value10 = rET.getIndex()] == null) {
            if (value10 < value) {
              instructionHandles[value10] = instructionList.getStart();
              instructionHandles2[value10] = instructionHandle;
            } else {
              instructionHandles[value10] = instructionHandles2[value10] = instructionHandle;
            }
          } else {
            instructionHandles2[value10] = instructionHandle;
          }
        } else if (instructionHandle.getInstruction() instanceof BranchInstruction) {
          hashtable.put((BranchInstruction) instructionHandle.getInstruction(), instructionHandle);
        }
      }
      InstructionHandle instructionHandle3;
      for (InstructionHandle instructionHandle4 = instructionHandle3 = instructionList.getStart();
          instructionHandle4 != null;
          instructionHandle4 = instructionHandle3 = instructionHandle3.getInstructionHandle2()) {
        if (instructionHandle3.getInstructionHandle2() != null
            && instructionHandle3.getInstruction() instanceof ALOAD
            && instructionHandle3.getInstructionHandle2().getInstruction() instanceof ATHROW) {
          int index = ((ALOAD) instructionHandle3.getInstruction()).getIndex();
          instructionHandles[index] = instructionList.getStart();
          instructionHandles2[index] = instructionList.getEnd();
        }
      }
      int value11;
      for (int value12 = value11 = 0; value12 < value8; value12 = ++value11) {
        if (instructionHandles[value11] != null) {
          while (true) {
            boolean matches3 = false;
            InstructionHandle instructionHandle5;
            InstructionHandle instructionHandle6 = instructionHandle5 = instructionHandles[value11];
            while (true) {
              if (instructionHandle6 == null) {
                matches = matches3;
                break;
              }
              InstructionTargeter[] instructionTargeters;
              if ((instructionTargeters = instructionHandle5.getTargeters()) != null) {
                InstructionTargeter[] instructionTargeters2 = instructionTargeters;
                int value13 = instructionTargeters.length;
                int value14;
                for (int value15 = value14 = 0; value15 < value13; value15 = ++value14) {
                  InstructionHandle instructionHandle7;
                  InstructionTargeter instructionTargeter;
                  if ((instructionTargeter = instructionTargeters2[value14])
                          instanceof BranchInstruction
                      && (instructionHandle7 =
                              (InstructionHandle) hashtable.get(instructionTargeter))
                          != null) {
                    if (instructionHandle7.getPosition()
                        < instructionHandles[value11].getPosition()) {
                      instructionHandles[value11] = instructionHandle7;
                      if (matchess[value11] && value11 + 1 < value8) {
                        instructionHandles[value11 + 1] = instructionHandle7;
                      }
                      matches3 = true;
                    }
                    if (instructionHandle7.getPosition()
                        > instructionHandles2[value11].getPosition()) {
                      instructionHandles2[value11] = instructionHandle7;
                      if (matchess[value11] && value11 + 1 < value8) {
                        instructionHandles2[value11 + 1] = instructionHandle7;
                      }
                      matches3 = true;
                    }
                  }
                }
              }
              if (instructionHandle5 == instructionHandles2[value11]) {
                matches = matches3;
                break;
              }
              instructionHandle6 = instructionHandle5 = instructionHandle5.getInstructionHandle2();
            }
            if (!matches) {
              break;
            }
          }
        }
      }
      int[] values = new int[value8];
      byte value16 = 1;
      int value17 = 1;
      for (int value18 = value17; value18 < value8; value18 = ++value17) {
        int value19;
        if (matchess[value17]
            && values[value17] == 0
            && value17 + 1 < value8
            && values[value17 + 1] == 0
            && !matchess[value17 - 1]) {
          for (int value20 = value19 = Math.max(value17 + 2, value);
              value20 < value8;
              value20 = ++value19) {
            if (matchess[value19]
                && values[value19] == 0
                && !matchess[value19 - 1]
                && instructionHandles2[value17] != null
                && instructionHandles[value19] != null
                && instructionHandles2[value17].getPosition()
                    < instructionHandles[value19].getPosition()
                && instructionHandles2[value17 + 1] != null
                && instructionHandles2[value17 + 1].getPosition()
                    < instructionHandles[value19].getPosition()) {
              instructionHandles2[value17] = instructionHandles2[value19];
              instructionHandles2[value17 + 1] = instructionHandles2[value19];
              values[value19] = value17;
              OptimizationStatistics.count4 += 2;
            }
          }
        }
      }
      int value21;
      for (int value22 = value21 = value16; value22 < value8; value22 = ++value21) {
        int value23;
        if (!matchess[value21] && !matchess[value21 - 1] && values[value21] == 0) {
          for (int value24 = value23 = Math.max(value21 + 1, value);
              value24 < value8;
              value24 = ++value23) {
            if (!matchess[value23]
                && !matchess[value23 - 1]
                && values[value23] == 0
                && (instructionHandles2[value21] != null
                        && instructionHandles[value23] != null
                        && instructionHandles2[value21].getPosition()
                            < instructionHandles[value23].getPosition()
                    || instructionHandles2[value21] == null
                        && instructionHandles[value23] != null)) {
              instructionHandles2[value21] = instructionHandles2[value23];
              values[value23] = value21;
              OptimizationStatistics.count4++;
            }
          }
        }
      }
      int value25;
      for (int value26 = value25 = value16; value26 < value8; value26 = ++value25) {
        int value27;
        if ((matchess[value25] || matchess[value25 - 1]) && values[value25] == 0) {
          for (int value28 = value27 = Math.max(value25 + 2, value);
              value28 < value8;
              value28 = ++value27) {
            if (!matchess[value27]
                && !matchess[value27 - 1]
                && values[value27] == 0
                && instructionHandles2[value25] != null
                && instructionHandles[value27] != null
                && instructionHandles2[value25].getPosition()
                    < instructionHandles[value27].getPosition()) {
              instructionHandles2[value25] = instructionHandles2[value27];
              values[value27] = value25;
              OptimizationStatistics.count4++;
            }
          }
        }
      }
      InstructionHandle instructionHandle8;
      for (InstructionHandle instructionHandle9 = instructionHandle8 = instructionList.getStart();
          instructionHandle9 != null;
          instructionHandle9 = instructionHandle8 = instructionHandle8.getInstructionHandle2()) {
        if (instructionHandle8.getInstruction() instanceof LocalVariableInstruction) {
          LocalVariableInstruction localVariableInstruction3 =
              (LocalVariableInstruction) instructionHandle8.getInstruction();
          if (values[localVariableInstruction3.getIndex()] != 0) {
            localVariableInstruction3.setIndex(values[localVariableInstruction3.getIndex()]);
          }
        } else if (instructionHandle8.getInstruction() instanceof RET) {
          RET rET2 = (RET) instructionHandle8.getInstruction();
          if (values[rET2.getIndex()] != 0) {
            rET2.setIndex(values[rET2.getIndex()]);
          }
        }
      }
      Arrays.fill(matchess, false);
      InstructionHandle instructionHandle10;
      for (InstructionHandle instructionHandle11 = instructionHandle10 = instructionList.getStart();
          instructionHandle11 != null;
          instructionHandle11 = instructionHandle10 = instructionHandle10.getInstructionHandle2()) {
        if (instructionHandle10.getInstruction() instanceof LocalVariableInstruction) {
          LocalVariableInstruction localVariableInstruction4 =
              (LocalVariableInstruction) instructionHandle10.getInstruction();
          int value29;
          matchess[value29 = localVariableInstruction4.getIndex()] = true;
          boolean matches4 =
              localVariableInstruction4.produceStack(classGen.getConstantPool()) == 2
                  || localVariableInstruction4.consumeStack(classGen.getConstantPool()) == 2;
          if (matches4 && value29 + 1 < value8) {
            matchess[value29 + 1] = true;
          }
        } else if (instructionHandle10.getInstruction() instanceof RET) {
          RET rET3 = (RET) instructionHandle10.getInstruction();
          matchess[rET3.getIndex()] = true;
        }
      }
      int value30;
      for (int value31 = value30 = value; value31 < value8; value31 = value30) {
        int value32 = value30;
        int value33 = value30;
        value30++;
        values[value33] = value32;
      }
      int value34;
      for (int value35 = value34 = value; value35 < value8; value35 = ++value34) {
        int value36;
        if (!matchess[value34]) {
          for (int value37 = value36 = value34 + 1; value37 < value8; value37 = value36) {
            int value38 = values[value36] - 1;
            int value39 = value36;
            value36++;
            values[value39] = value38;
          }
        }
      }
      InstructionHandle instructionHandle12;
      for (InstructionHandle instructionHandle13 = instructionHandle12 = instructionList.getStart();
          instructionHandle13 != null;
          instructionHandle13 = instructionHandle12 = instructionHandle12.getInstructionHandle2()) {
        if (instructionHandle12.getInstruction() instanceof LocalVariableInstruction) {
          LocalVariableInstruction localVariableInstruction5;
          if ((localVariableInstruction5 =
                      (LocalVariableInstruction) instructionHandle12.getInstruction())
                  .getIndex()
              >= value) {
            localVariableInstruction5.setIndex(values[localVariableInstruction5.getIndex()]);
          }
        } else {
          RET rET4;
          if (instructionHandle12.getInstruction() instanceof RET
              && (rET4 = (RET) instructionHandle12.getInstruction()).getIndex() >= value) {
            rET4.setIndex(values[rET4.getIndex()]);
          }
        }
      }
      LocalVariableGen[] localVariableGens3 = localVariableGens;
      int value40 = localVariableGens.length;
      int value41;
      for (int value42 = value41 = 0; value42 < value40; value42 = ++value41) {
        LocalVariableGen localVariableGen2;
        if ((localVariableGen2 = localVariableGens3[value41]).getIndex() < value) {
          localVariableGen2.processInstructionHandle2(instructionList.getStart());
          localVariableGen2.processInstructionHandle(instructionList.getEnd());
        } else {
          methodGen.removeLocalVariable(localVariableGen2);
        }
      }
    }
  }

  public boolean matchesInstruction(Instruction instruction) {
    return instruction instanceof ConstantPushInstruction
        || instruction instanceof ACONST_NULL
        || instruction instanceof LDC
        || instruction instanceof LDC2_W;
  }

  public void processInstructionList22(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {}

  public void processInstructionList23(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    LocalVariableGen[] localVariableGens;
    if ((localVariableGens = methodGen.getLocalVariables()) != null) {
      if (localVariableGens.length != 0) {
        int value;
        if ((value = methodGen.getCount()) >= 2) {
          int[] values = new int[value];
          boolean[] matchess = new boolean[value];
          Type[] types = new Type[value];
          int value2 = 0;
          if (!methodGen.isEnabled11()) {
            types[0] = Type.OBJECT;
            value2 = 1;
          }
          int value3 = methodGen.getArgumentNames().length;
          if (!methodGen.isEnabled11()) {
            value3++;
          }
          Type[] types2;
          int value4 = (types2 = methodGen.getArgumentTypes()).length;
          int value5;
          for (int value6 = value5 = 0; value6 < value4; value6 = ++value5) {
            Type type = types2[value5];
            Type type2;
            Type type3 = type2 = type;
            int value7 = value2;
            value2++;
            types[value7] = type2;
            if (type3 == Type.LONG || type == Type.DOUBLE) {
              int value8 = value2 - 1;
              boolean matches = matchess[value2] = true;
              value3++;
              matchess[value8] = matches;
              int value9 = value2;
              value2++;
              types[value9] = type;
            }
          }
          InstructionHandle instructionHandle2;
          for (InstructionHandle instructionHandle3 =
                  instructionHandle2 = instructionList.getStart();
              instructionHandle3 != null;
              instructionHandle3 =
                  instructionHandle2 = instructionHandle2.getInstructionHandle2()) {
            if (instructionHandle2.getInstruction() instanceof LoadInstruction
                || instructionHandle2.getInstruction() instanceof StoreInstruction) {
              LocalVariableInstruction localVariableInstruction =
                  (LocalVariableInstruction) instructionHandle2.getInstruction();
              int value10;
              values[value10 = localVariableInstruction.getIndex()]++;
              if (types[value10] == null) {
                types[value10] = localVariableInstruction.getType(classGen.getConstantPool());
              }
              if ((localVariableInstruction.produceStack(classGen.getConstantPool()) == 2
                      || localVariableInstruction.consumeStack(classGen.getConstantPool()) == 2)
                  && value10 + 1 < value) {
                matchess[value10] = true;
                matchess[value10 + 1] = true;
              }
            }
          }
          int value11;
          for (int value12 = value11 = 0; value12 < values.length; value12 = value11) {
            int value13 = values[value11] << 16 | value11;
            int value14 = value11;
            value11++;
            values[value14] = value13;
          }
          Arrays.sort(values);
          byte value15 = 0;
          int value16;
          for (int value17 = value16 = 0; value17 < value3 && value15 == 0; value17 = ++value16) {
            int value18;
            if (types[value16] != null) {
              for (int value19 = value18 = value - 1; value19 >= 0; value19 = --value18) {
                int value20;
                if ((value20 = values[value18] & 65535) > value16
                    && !matchess[value16]
                    && !matchess[value20]
                    && types[value20] != null) {
                  OptimizationStatistics.count7++;
                  InstructionHandle instructionHandle4;
                  InstructionHandle instructionHandle5 =
                      instructionHandle4 = instructionList.getStart();
                  while (instructionHandle5 != null) {
                    label95:
                    {
                      if (instructionHandle4.getInstruction() instanceof LocalVariableInstruction) {
                        LocalVariableInstruction localVariableInstruction2;
                        if ((localVariableInstruction2 =
                                    (LocalVariableInstruction) instructionHandle4.getInstruction())
                                .getIndex()
                            == value16) {
                          localVariableInstruction2.setIndex(value20);
                        } else if (localVariableInstruction2.getIndex() == value20) {
                          localVariableInstruction2.setIndex(value16);
                        }
                      } else if (instructionHandle4.getInstruction() instanceof RET) {
                        RET rET;
                        if ((rET = (RET) instructionHandle4.getInstruction()).getIndex()
                            == value16) {
                          instructionHandle = instructionHandle4;
                          rET.setIndex(value20);
                          break label95;
                        }
                        if (rET.getIndex() == value20) {
                          rET.setIndex(value16);
                        }
                      }
                      instructionHandle = instructionHandle4;
                    }
                    instructionHandle5 =
                        instructionHandle4 = instructionHandle.getInstructionHandle2();
                  }
                  InstructionHandle start = instructionList.getStart();
                  if (value20 < value3) {
                    instructionList.createInstructionHandle(
                        instructionList.createInstructionHandle(
                            instructionList.createInstructionHandle(
                                instructionList.createInstructionHandle(
                                    start,
                                    InstructionFactory.createLocalVariableInstruction(
                                        types[value16], value20)),
                                InstructionFactory.createLocalVariableInstruction(
                                    types[value20], value16)),
                            InstructionFactory.createLocalVariableInstruction2(
                                types[value20], value20)),
                        InstructionFactory.createLocalVariableInstruction2(
                            types[value16], value16));
                  } else {
                    instructionList.createInstructionHandle(
                        instructionList.createInstructionHandle(
                            start,
                            InstructionFactory.createLocalVariableInstruction(
                                types[value16], value20)),
                        InstructionFactory.createLocalVariableInstruction2(
                            types[value16], value16));
                  }
                  value15 = 1;
                  break;
                }
              }
            }
          }
          if (value15 != 0) {}
        }
      }
    }
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

  public void processInstructionList24(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    instructionList.update();
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      label72:
      {
        if (instructionHandle.getInstruction() instanceof IfInstruction) {
          byte value = 1;
          byte value2 = 1;
          InstructionHandle instructionHandle3 = instructionHandle;
          InstructionHandle instructionHandle4;
          if ((instructionHandle4 =
                      ((IfInstruction) instructionHandle.getInstruction()).getTarget())
                  .getPosition()
              >= instructionHandle3.getPosition()) {
            value = 0;
          }
          if (instructionHandle4.getPosition() <= instructionHandle3.getPosition()) {
            value2 = 0;
          }
          if (value == 0 && value2 == 0) {
            instructionHandle2 = instructionHandle;
            break label72;
          }
          InstructionHandle instructionHandle5;
          if ((instructionHandle5 = instructionHandle4.getInstructionHandle()) == null) {
            instructionHandle2 = instructionHandle;
            break label72;
          }
          if (!(instructionHandle5.getInstruction() instanceof GotoInstruction)) {
            instructionHandle2 = instructionHandle;
            break label72;
          }
          GotoInstruction gotoInstruction;
          InstructionHandle target =
              (gotoInstruction = (GotoInstruction) instructionHandle5.getInstruction()).getTarget();
          if (value != 0) {
            if (target.getPosition() >= instructionHandle3.getPosition()) {
              instructionHandle2 = instructionHandle;
              break label72;
            }
            if (instructionHandle4.getPosition() >= target.getPosition()) {
              instructionHandle2 = instructionHandle;
              break label72;
            }
          } else if (value2 != 0 && target.getPosition() >= instructionHandle3.getPosition()) {
            instructionHandle2 = instructionHandle;
            break label72;
          }
          if (target.getInstruction() instanceof BranchInstruction) {
            instructionHandle2 = instructionHandle;
            break label72;
          }
          if (target.getInstruction() instanceof NEW) {
            instructionHandle2 = instructionHandle;
            break label72;
          }
          if (target.getInstruction() instanceof NEWARRAY) {
            instructionHandle2 = instructionHandle;
            break label72;
          }
          if (target.getInstruction() instanceof ANEWARRAY) {
            instructionHandle2 = instructionHandle;
            break label72;
          }
          if (!this.matchesInstructionTargeters(
              instructionHandle4.getInstructionHandle().getTargeters())) {
            instructionHandle2 = instructionHandle;
            break label72;
          }
          OptimizationStatistics.count26++;
          gotoInstruction.setTarget(target.getInstructionHandle2());
          instructionList.createInstructionHandle(
              instructionHandle4.getInstructionHandle(), target.getInstruction());
        }
        instructionHandle2 = instructionHandle;
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList25(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    InstructionList instructionList2;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof StoreInstruction)) {
        instructionHandle2 = instructionHandle;
      } else if (instructionHandle.getInstructionHandle2() == null) {
        instructionHandle2 = instructionHandle;
      } else if (!(instructionHandle.getInstructionHandle2().getInstruction()
          instanceof LoadInstruction)) {
        instructionHandle2 = instructionHandle;
      } else {
        InstructionHandle instructionHandle3 = instructionHandle;
        InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
        StoreInstruction storeInstruction = (StoreInstruction) instructionHandle3.getInstruction();
        LoadInstruction loadInstruction = (LoadInstruction) instructionHandle22.getInstruction();
        if (storeInstruction.getIndex() != loadInstruction.getIndex()) {
          instructionHandle2 = instructionHandle;
        } else if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
          instructionHandle2 = instructionHandle;
        } else if (!this.matchesInstructionTargeters(instructionHandle3.getTargeters())) {
          instructionHandle2 = instructionHandle;
        } else {
          InstructionHandle instructionHandle4;
          if ((instructionHandle4 = instructionHandle22.getInstructionHandle2()) == null) {
            instructionHandle2 = instructionHandle;
          } else {
            label78:
            {
              InstructionHandle instructionHandle5;
              instructionHandle5 = instructionHandle4;
              label55:
              if (this.matchesInstructionHandle(
                  instructionList, instructionHandle3, instructionHandle22)) {
                OptimizationStatistics.count3++;
                this.processInstructionHandle(instructionHandle22, instructionHandle4);
                this.processInstructionHandle(instructionHandle3, instructionHandle4);
                this.processInstructionHandle2(instructionHandle22, instructionHandle4);
                this.processInstructionHandle2(instructionHandle3, instructionHandle4);
                label52:
                {
                  try {
                    instructionList.processInstructionHandle2(instructionHandle3);
                  } catch (TargetLostException targetLostException) {
                    {
                      instructionList2 = instructionList;
                      break label52;
                    }
                  }
                  instructionList2 = instructionList;
                }
                try {
                  instructionList2.processInstructionHandle2(instructionHandle22);
                } catch (TargetLostException targetLostException2) {
                  break label55;
                }
                instructionHandle2 = instructionHandle5;
                break label78;
              }
              instructionHandle2 = instructionHandle5;
            }
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }

  public void processInstructionList26(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {}

  public void processInstructionList27(
      ClassGen classGen, MethodGen methodGen, InstructionList instructionList) {
    int value;
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle2 = instructionHandle = instructionList.getStart();
    while (instructionHandle2 != null) {
      if (!(instructionHandle.getInstruction() instanceof LoadInstruction)) {
        instructionHandle2 = instructionHandle;
      } else if (instructionHandle.getInstructionHandle2() == null) {
        instructionHandle2 = instructionHandle;
      } else if (!(instructionHandle.getInstructionHandle2().getInstruction()
          instanceof LoadInstruction)) {
        instructionHandle2 = instructionHandle;
      } else {
        InstructionHandle instructionHandle3 = instructionHandle;
        LoadInstruction loadInstruction = (LoadInstruction) instructionHandle.getInstruction();
        int index = loadInstruction.getIndex();
        int value2 = 1;
        InstructionHandle instructionHandle4;
        instructionHandle2 = instructionHandle4 = instructionHandle3.getInstructionHandle2();
        while (instructionHandle2 != null
            && instructionHandle4.getInstruction() instanceof LoadInstruction
            && ((LoadInstruction) instructionHandle4.getInstruction()).getIndex() == index) {
          InstructionHandle instructionHandle5;
          InstructionHandle instructionHandle6 =
              instructionHandle5 = instructionHandle4.getInstructionHandle2();
          value2++;
          instructionHandle2 = instructionHandle6;
          instructionHandle4 = instructionHandle5;
        }
        if (value2 == 1) {
          instructionHandle2 = instructionHandle;
        } else if (instructionHandle4 == null) {
          instructionHandle2 = instructionHandle;
        } else {
          byte value3 = 1;
          InstructionHandle instructionHandle22 = instructionHandle3.getInstructionHandle2();
          int value4;
          int value5 = value4 = 1;
          while (true) {
            if (value5 >= value2) {
              value = value3;
              break;
            }
            if (!this.matchesInstructionTargeters(instructionHandle22.getTargeters())) {
              value = 0;
              break;
            }
            InstructionHandle instructionHandle7 = instructionHandle22;
            value4++;
            instructionHandle22 = instructionHandle7.getInstructionHandle2();
            value5 = value4;
          }
          if (value == 0) {
            instructionHandle2 = instructionHandle;
          } else {
            OptimizationStatistics.count10++;
            if (value2 > 2) {
              OptimizationStatistics.count23++;
            }
            InstructionHandle instructionHandle23 = instructionHandle3.getInstructionHandle2();
            int value6;
            for (int value7 = value6 = 1; value7 < value2; value7 = value6) {
              this.processInstructionHandle2(instructionHandle23, instructionHandle3);
              label111:
              {
                try {
                  instructionList.processInstructionHandle2(instructionHandle23);
                } catch (TargetLostException targetLostException) {
                  instructionHandle2 = instructionHandle3;
                  break label111;
                }
                instructionHandle2 = instructionHandle3;
              }
              InstructionHandle instructionHandle24 = instructionHandle2.getInstructionHandle2();
              value6++;
              instructionHandle23 = instructionHandle24;
            }
            if (!(loadInstruction instanceof ALOAD)
                && !(loadInstruction instanceof FLOAD)
                && !(loadInstruction instanceof ILOAD)) {
              int value8;
              if (loadInstruction instanceof DLOAD || loadInstruction instanceof LLOAD) {
                for (int value9 = value8 = 1; value9 < value2; value9 = value8) {
                  value8++;
                  instructionList.createInstructionHandle2(
                      instructionHandle3, InstructionConstants.DUP2);
                }
              }
            } else {
              int value10 = 1;
              label98:
              while (true) {
                int value11 = value10;
                while (true) {
                  if (value11 >= value2) {
                    break label98;
                  }
                  if (value2 - value10 > 2) {
                    value10 += 2;
                    instructionList.createInstructionHandle2(
                        instructionHandle3, InstructionConstants.DUP2);
                  }
                  if (value2 - value10 == 2) {
                    value10++;
                    instructionList.createInstructionHandle2(
                        instructionHandle3, InstructionConstants.DUP_X1);
                  }
                  if (value2 - value10 != 1) {
                    break;
                  }
                  value10++;
                  instructionList.createInstructionHandle2(
                      instructionHandle3, InstructionConstants.DUP);
                  value11 = value10;
                }
              }
            }
            instructionHandle2 = instructionHandle;
          }
        }
      }
      instructionHandle2 = instructionHandle = instructionHandle2.getInstructionHandle2();
    }
  }
}
