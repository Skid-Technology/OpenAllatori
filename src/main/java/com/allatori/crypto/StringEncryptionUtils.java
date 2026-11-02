package com.allatori.crypto;

import com.allatori.bytecode.MethodFactory;
import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ConstantValue;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.SourceFile;
import com.allatori.lib.bcel.classfile.StackMap;
import com.allatori.lib.bcel.classfile.StackMapEntry;
import com.allatori.lib.bcel.classfile.StackMapType;
import com.allatori.lib.bcel.generic.ArrayType;
import com.allatori.lib.bcel.generic.BranchInstruction;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.FieldGen;
import com.allatori.lib.bcel.generic.FieldInstruction;
import com.allatori.lib.bcel.generic.GETFIELD;
import com.allatori.lib.bcel.generic.GETSTATIC;
import com.allatori.lib.bcel.generic.IF_ACMPEQ;
import com.allatori.lib.bcel.generic.IF_ACMPNE;
import com.allatori.lib.bcel.generic.IINC;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionConstants;
import com.allatori.lib.bcel.generic.InstructionFactory;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InstructionList;
import com.allatori.lib.bcel.generic.InstructionTargeter;
import com.allatori.lib.bcel.generic.LDC;
import com.allatori.lib.bcel.generic.LineNumberGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.PUSH;
import com.allatori.lib.bcel.generic.PUTFIELD;
import com.allatori.lib.bcel.generic.PUTSTATIC;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.util.Log;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Vector;
import java.util.regex.Pattern;

public class StringEncryptionUtils {

  public static Vector values = new Vector();

  public static boolean matchesText(Vector vector, String text) {
    if ("000".equals(text)) {
      return false;
    }
    if ("001".equals(text)) {
      vector.add(InstructionConstants.ICONST_1);
    } else if ("010".equals(text)) {
      vector.add(InstructionConstants.ICONST_2);
    } else if ("011".equals(text)) {
      vector.add(InstructionConstants.ICONST_3);
    } else if ("100".equals(text)) {
      vector.add(InstructionConstants.ICONST_4);
    } else if ("101".equals(text)) {
      vector.add(InstructionConstants.ICONST_5);
    } else if ("110".equals(text)) {
      vector.add(InstructionConstants.ICONST_3);
      vector.add(InstructionConstants.ICONST_5);
      vector.add(InstructionConstants.IXOR);
    } else if ("111".equals(text)) {
      vector.add(InstructionConstants.ICONST_2);
      vector.add(InstructionConstants.ICONST_5);
      vector.add(InstructionConstants.IXOR);
    }
    return true;
  }

  public static void processCollection(ClassGen classGen, Collection values) {
    InstructionHandle instructionHandle;
    int value;
    Field[] fields;
    for (int value2 = value = (fields = classGen.getFields()).length - 1;
        value2 >= 0;
        value2 = --value) {
      Constant constant;
      ConstantValue constantValue;
      if ((constantValue = fields[value].getConstantValue()) != null
          && (constant =
                  classGen.getConstantPool().getConstant(constantValue.getConstantValueIndex()))
              instanceof ConstantString) {
        String bytes =
            ((ConstantUtf8)
                    classGen
                        .getConstantPool()
                        .getConstant(((ConstantString) constant).getStringIndex()))
                .getBytes();
        values.add(bytes);
      }
    }
    Method[] methods;
    int value3;
    for (int value4 = value3 = (methods = classGen.getMethods()).length - 1;
        value4 >= 0;
        value4 = --value3) {
      Method method;
      if ((method = methods[value3]).getCode() != null) {
        InstructionList instructionList;
        InstructionHandle instructionHandle2;
        InstructionHandle instructionHandle3 =
            instructionHandle2 =
                (instructionList =
                        MethodFactory.createMethodGen(
                                method,
                                classGen.getClassName(),
                                classGen.getConstantPool(),
                                classGen.getConstantPool().getConstantPool())
                            .getInstructionList())
                    .getStart();
        while (instructionHandle3 != null) {
          label119:
          {
            if (instructionHandle2.getInstruction() instanceof PUTSTATIC
                || instructionHandle2.getInstruction() instanceof PUTFIELD) {
              if (instructionHandle2.getInstructionHandle() == null) {
                instructionHandle = instructionHandle2;
                break label119;
              }
              InstructionHandle instructionHandle4;
              if ((instructionHandle4 = instructionHandle2.getInstructionHandle()).getInstruction()
                  instanceof LDC) {
                LDC lDC = (LDC) instructionHandle4.getInstruction();
                Constant constant2;
                if ((constant2 = classGen.getConstantPool().getConstant(lDC.getIndex()))
                    instanceof ConstantString) {
                  String bytes2 =
                      ((ConstantUtf8)
                              classGen
                                  .getConstantPool()
                                  .getConstant(((ConstantString) constant2).getStringIndex()))
                          .getBytes();
                  values.add(bytes2);
                }
              }
            }
            instructionHandle = instructionHandle2;
          }
          instructionHandle3 = instructionHandle2 = instructionHandle.getInstructionHandle2();
        }
        InstructionHandle instructionHandle5;
        InstructionHandle instructionHandle6 = instructionHandle5 = instructionList.getStart();
        while (instructionHandle6 != null) {
          InstructionHandle instructionHandle7;
          if (!(instructionHandle5.getInstruction() instanceof LDC)) {
            instructionHandle7 = instructionHandle5;
          } else {
            LDC lDC2 = (LDC) instructionHandle5.getInstruction();
            Constant constant3;
            if (!((constant3 = classGen.getConstantPool().getConstant(lDC2.getIndex()))
                instanceof ConstantString)) {
              instructionHandle7 = instructionHandle5;
            } else {
              ConstantPoolGen constantPool = classGen.getConstantPool();
              int value5 = lDC2.produceStack(constantPool);
              InstructionHandle instructionHandle22 = instructionHandle5.getInstructionHandle2();
              int value6 = 0;
              while (true) {
                if (instructionHandle22 != null) {
                  int value7 = instructionHandle22.getInstruction().produceStack(constantPool);
                  int value8 = instructionHandle22.getInstruction().consumeStack(constantPool);
                  if (value7 != -2) {
                    if (value8 == -2) {
                      instructionHandle7 = instructionHandle5;
                      break;
                    }
                    int value9;
                    if ((value9 = value5 - value8) > 0) {
                      value5 = value9 + value7;
                      instructionHandle22 = instructionHandle22.getInstructionHandle2();
                      int value10 = value6++;
                      if (value10 > (byte) 33) {
                        instructionHandle7 = instructionHandle5;
                        break;
                      }
                      continue;
                    }
                    Instruction instruction;
                    if ((instruction = instructionHandle22.getInstruction()) instanceof IF_ACMPEQ
                        || instruction instanceof IF_ACMPNE) {
                      String bytes3 =
                          ((ConstantUtf8)
                                  classGen
                                      .getConstantPool()
                                      .getConstant(((ConstantString) constant3).getStringIndex()))
                              .getBytes();
                      instructionHandle7 = instructionHandle5;
                      values.add(bytes3);
                      break;
                    }
                  }
                }
                instructionHandle7 = instructionHandle5;
                break;
              }
            }
          }
          instructionHandle6 = instructionHandle5 = instructionHandle7.getInstructionHandle2();
        }
      }
    }
  }

  public static void update() {
    values = new Vector();
    if (Configuration.getObfuscationOptions().getStringEncryptionIgnoredStrings() != null) {
      BufferedReader bufferedReader;
      try {
        for (String text =
                (bufferedReader =
                        new BufferedReader(
                            new FileReader(
                                Configuration.getObfuscationOptions()
                                    .getStringEncryptionIgnoredStrings())))
                    .readLine();
            text != null;
            text = bufferedReader.readLine()) {
          if (text.length() > 0) {
            String text2;
            if (text.startsWith("regex:")) {
              text2 = text.substring(6);
            } else {
              text2 = text.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
            }
            values.add(Pattern.compile(text2, 32));
          }
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  public static void processCharacter(ClassGen classGen, String text, char value) {
    InstructionFactory instructionFactory = new InstructionFactory(classGen);
    ConstantPoolGen constantPool = classGen.getConstantPool();
    InstructionList instructionList = new InstructionList();
    MethodGen methodGen =
        new MethodGen(
            9,
            Type.STRING,
            new Type[] {Type.STRING},
            new String[] {"s"},
            text,
            classGen.getClassName(),
            instructionList,
            constantPool);
    int value2 = constantPool.addClass("java.lang.String");
    int value3 = constantPool.addClass("[C");
    int value4 = constantPool.addUtf8("StackMap");
    ConstantPool constantPool2 = constantPool.getConstantPool();
    StackMapType[] stackMapTypes = new StackMapType[4];
    stackMapTypes[0] = new StackMapType((byte) 7, value2, constantPool2);
    stackMapTypes[1] = new StackMapType((byte) 7, value3, constantPool2);
    stackMapTypes[2] = new StackMapType((byte) 1, 0, constantPool2);
    stackMapTypes[3] = new StackMapType((byte) 1, 0, constantPool2);
    StackMapEntry stackMapEntry =
        new StackMapEntry(27, 4, stackMapTypes, 0, new StackMapType[0], constantPool2);
    StackMapEntry stackMapEntry2 =
        new StackMapEntry(
            58,
            4,
            new StackMapType[] {
              new StackMapType((byte) 7, value2, constantPool2),
              new StackMapType((byte) 7, value3, constantPool2),
              new StackMapType((byte) 1, 0, constantPool2),
              new StackMapType((byte) 1, 0, constantPool2)
            },
            0,
            new StackMapType[0],
            constantPool2);
    StackMap stackMap =
        new StackMap(
            value4, 30, new StackMapEntry[] {stackMapEntry, stackMapEntry2}, constantPool2);
    methodGen.addCodeAttribute(stackMap);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(instructionFactory.createNewArray(Type.CHAR, (short) 1));
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(InstructionConstants.POP2);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.OBJECT, 1));
    instructionList.getInstructionHandle(new PUSH(constantPool, value));
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(InstructionConstants.POP2);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 1));
    instructionList.getInstructionHandle(InstructionConstants.ARRAYLENGTH);
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(InstructionConstants.POP2);
    instructionList.getInstructionHandle(InstructionConstants.ISUB);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 3));
    BranchInstruction branchInstruction =
        InstructionFactory.createBranchInstruction((short) 167, null);
    instructionList.getBranchHandle(branchInstruction);
    InstructionHandle instructionHandle =
        instructionList.getInstructionHandle(
            InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "charAt", Type.CHAR, new Type[] {Type.INT}, (short) 182));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 2));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(InstructionConstants.POP2);
    instructionList.getInstructionHandle(InstructionConstants.CASTORE);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 2));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 2));
    instructionList.getInstructionHandle(new PUSH(constantPool, 63));
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(InstructionConstants.POP2);
    instructionList.getInstructionHandle(InstructionConstants.IAND);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
    instructionList.getInstructionHandle(new IINC(3, -1));
    InstructionHandle instructionHandle2 =
        instructionList.getInstructionHandle(
            InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    instructionList.getBranchHandle(
        InstructionFactory.createBranchInstruction((short) 156, instructionHandle));
    instructionList.getInstructionHandle(instructionFactory.createNew("java.lang.String"));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 1));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String",
            "<init>",
            Type.VOID,
            new Type[] {new ArrayType(Type.CHAR, 1)},
            (short) 183));
    instructionList.getInstructionHandle(InstructionFactory.createReturn(Type.OBJECT));
    branchInstruction.setTarget(instructionHandle2);
    methodGen.update5();
    methodGen.update4();
    classGen.processMethod2(methodGen.getMethod());
    instructionList.update3();
  }

  public static boolean matchesText(String text) {
    int value;
    for (int value2 = value = values.size() - 1; value2 >= 0; value2 = --value) {
      if (((Pattern) values.get(value)).matcher(text).matches()) {
        return true;
      }
    }
    return false;
  }

  public static boolean matchesText2(Vector vector, String text) {
    if ("00".equals(text)) {
      return false;
    }
    if ("01".equals(text)) {
      vector.add(InstructionConstants.ICONST_1);
    } else if ("10".equals(text)) {
      vector.add(InstructionConstants.ICONST_2);
    } else if ("11".equals(text)) {
      vector.add(InstructionConstants.ICONST_3);
    }
    return true;
  }

  public static Vector createValues(int value) {
    Vector vector = new Vector();
    String text;
    String text2 = text = Integer.toBinaryString(value);
    while (text2.length() < 7) {
      text2 = text = "0" + text;
    }
    if (text.startsWith("0")) {
      boolean matches;
      if (matches = matchesText(vector, text.substring(1, 4))) {
        vector.add(InstructionConstants.ICONST_3);
        vector.add(InstructionConstants.ISHL);
      }
      if (matchesText(vector, text.substring(4, 7)) && matches) {
        vector.add(InstructionConstants.IXOR);
      }
      return vector;
    } else if (text.endsWith("0")) {
      boolean matches2;
      if (matches2 = matchesText(vector, text.substring(0, 3))) {
        vector.add(InstructionConstants.ICONST_4);
        vector.add(InstructionConstants.ISHL);
      }
      if (matchesText(vector, text.substring(3, 6)) && matches2) {
        vector.add(InstructionConstants.ICONST_1);
        vector.add(InstructionConstants.ISHL);
        vector.add(InstructionConstants.IXOR);
      }
      return vector;
    } else if (text.charAt(3) == '0') {
      boolean matches3;
      if (matches3 = matchesText(vector, text.substring(0, 3))) {
        vector.add(InstructionConstants.ICONST_4);
        vector.add(InstructionConstants.ISHL);
      }
      if (matchesText(vector, text.substring(4, 7)) && matches3) {
        vector.add(InstructionConstants.IXOR);
      }
      return vector;
    } else {
      matchesText(vector, text.substring(0, 3));
      vector.add(InstructionConstants.ICONST_4);
      vector.add(InstructionConstants.ISHL);
      boolean matches4 = matchesText2(vector, text.substring(3, 5));
      if (matches4) {
        vector.add(InstructionConstants.ICONST_2);
        vector.add(InstructionConstants.ISHL);
      }
      if (matchesText2(vector, text.substring(5, 7))) {
        vector.add(InstructionConstants.IXOR);
      }
      if (matches4) {
        vector.add(InstructionConstants.IXOR);
      }
      return vector;
    }
  }

  public static String getText(ClassGen classGen) {
    Attribute[] attributes;
    if ((attributes = classGen.getAttributes()) == null) {
      return "Unknown Source";
    }
    int value;
    for (int value2 = value = attributes.length - 1; value2 >= 0; value2 = --value) {
      if (attributes[value] instanceof SourceFile) {
        return ((SourceFile) attributes[value]).getText();
      }
    }
    return "Unknown Source";
  }

  public static void processClassGen(ClassGen classGen) {
    Method[] methods;
    int value;
    for (int value2 = value = (methods = classGen.getMethods()).length - 1;
        value2 >= 0;
        value2 = --value) {
      Method method;
      if ((method = methods[value]).getCode() != null) {
        InstructionHandle instructionHandle;
        InstructionList instructionList;
        InstructionHandle instructionHandle2 =
            instructionHandle =
                (instructionList =
                        MethodFactory.createMethodGen(
                                method,
                                classGen.getClassName(),
                                classGen.getConstantPool(),
                                classGen.getConstantPool().getConstantPool())
                            .getInstructionList())
                    .getStart();
        while (instructionHandle2 != null) {
          InstructionHandle instructionHandle3;
          if (!(instructionHandle.getInstruction() instanceof LDC)) {
            instructionHandle3 = instructionHandle;
          } else {
            LDC lDC = (LDC) instructionHandle.getInstruction();
            if (!(classGen.getConstantPool().getConstant(lDC.getIndex())
                instanceof ConstantString)) {
              instructionHandle3 = instructionHandle;
            } else {
              label145:
              {
                ConstantPoolGen constantPool = classGen.getConstantPool();
                int value3 = lDC.produceStack(constantPool);
                InstructionHandle instructionHandle22 = instructionHandle.getInstructionHandle2();
                int value4 = 0;
                while (instructionHandle22 != null) {
                  int value5 = instructionHandle22.getInstruction().produceStack(constantPool);
                  int value6 = instructionHandle22.getInstruction().consumeStack(constantPool);
                  if (value5 == -2) {
                    break;
                  }
                  if (value6 == -2) {
                    instructionHandle3 = instructionHandle;
                    break label145;
                  }
                  int value7;
                  if ((value7 = value3 - value6) <= 0) {
                    Instruction instruction;
                    if (!((instruction = instructionHandle22.getInstruction()) instanceof IF_ACMPEQ)
                        && !(instruction instanceof IF_ACMPNE)) {
                      break;
                    }
                    int value8;
                    do {
                      value8 = calculateCount(instructionHandle22);
                    } while ((instructionHandle22 = instructionHandle22.getInstructionHandle())
                            != null
                        && value8 == -1);
                    String text =
                        ""
                            + classGen.getClassName()
                            + "."
                            + method.getText()
                            + "("
                            + getText(classGen);
                    if (value8 != -1) {
                      text = "" + text + ":" + value8;
                    }
                    String text2 = "" + text + ")";
                    Log.warn("Comparing strings using '==' operator at " + text2);
                    instructionHandle3 = instructionHandle;
                    break label145;
                  }
                  value3 = value7 + value5;
                  instructionHandle22 = instructionHandle22.getInstructionHandle2();
                  int value9 = value4++;
                  if (value9 > (byte) 33) {
                    instructionHandle3 = instructionHandle;
                    break label145;
                  }
                }
                instructionHandle3 = instructionHandle;
              }
            }
          }
          instructionHandle2 = instructionHandle = instructionHandle3.getInstructionHandle2();
        }
        InstructionHandle instructionHandle4;
        InstructionHandle instructionHandle5 = instructionHandle4 = instructionList.getStart();
        while (instructionHandle5 != null) {
          InstructionHandle instructionHandle6;
          if (!(instructionHandle4.getInstruction() instanceof GETSTATIC)
              && !(instructionHandle4.getInstruction() instanceof GETFIELD)) {
            instructionHandle6 = instructionHandle4;
          } else {
            FieldInstruction fieldInstruction =
                (FieldInstruction) instructionHandle4.getInstruction();
            ConstantPoolGen constantPool2 = classGen.getConstantPool();
            if (!((String) "Ljava/lang/String;").equals(fieldInstruction.getText3(constantPool2))) {
              instructionHandle6 = instructionHandle4;
            } else {
              label142:
              {
                int value10 = fieldInstruction.produceStack(constantPool2);
                InstructionHandle instructionHandle23 = instructionHandle4.getInstructionHandle2();
                int value11 = 0;
                while (instructionHandle23 != null) {
                  int value12 = instructionHandle23.getInstruction().produceStack(constantPool2);
                  int value13 = instructionHandle23.getInstruction().consumeStack(constantPool2);
                  if (value12 == -2) {
                    break;
                  }
                  if (value13 == -2) {
                    instructionHandle6 = instructionHandle4;
                    break label142;
                  }
                  int value14;
                  if ((value14 = value10 - value13) <= 0) {
                    Instruction instruction2;
                    if (!((instruction2 = instructionHandle23.getInstruction())
                            instanceof IF_ACMPEQ)
                        && !(instruction2 instanceof IF_ACMPNE)) {
                      break;
                    }
                    int value15;
                    do {
                      value15 = calculateCount(instructionHandle23);
                    } while ((instructionHandle23 = instructionHandle23.getInstructionHandle())
                            != null
                        && value15 == -1);
                    String text3 =
                        ""
                            + classGen.getClassName()
                            + "."
                            + method.getText()
                            + "("
                            + getText(classGen);
                    if (value15 != -1) {
                      text3 = "" + text3 + ":" + value15;
                    }
                    String text4 = "" + text3 + ")";
                    Log.warn("Comparing strings using '==' operator at " + text4);
                    instructionHandle6 = instructionHandle4;
                    break label142;
                  }
                  value10 = value14 + value12;
                  instructionHandle23 = instructionHandle23.getInstructionHandle2();
                  int value16 = value11++;
                  if (value16 > (byte) 33) {
                    instructionHandle6 = instructionHandle4;
                    break label142;
                  }
                }
                instructionHandle6 = instructionHandle4;
              }
            }
          }
          instructionHandle5 = instructionHandle4 = instructionHandle6.getInstructionHandle2();
        }
      }
    }
  }

  public static int calculateCount(InstructionHandle instructionHandle) {
    InstructionTargeter[] instructionTargeters;
    if ((instructionTargeters = instructionHandle.getTargeters()) == null) {
      return -1;
    }
    int value;
    for (int value2 = value = instructionTargeters.length - 1; value2 >= 0; value2 = --value) {
      if (instructionTargeters[value] instanceof LineNumberGen) {
        return ((LineNumberGen) instructionTargeters[value]).getSourceLine();
      }
    }
    return -1;
  }

  public static void processClassGen2(ClassGen classGen) {
    int value;
    Field[] fields;
    for (int value2 = value = (fields = classGen.getFields()).length - 1;
        value2 >= 0;
        value2 = --value) {
      Field field;
      ConstantValue constantValue;
      Constant constant;
      if ((constantValue = (field = fields[value]).getConstantValue()) != null
          && (constant =
                  classGen.getConstantPool().getConstant(constantValue.getConstantValueIndex()))
              instanceof ConstantString
          && field.isEnabled3()) {
        if (!field.isEnabled11()) {
          FieldGen fieldGen = new FieldGen(field, classGen.getConstantPool());
          fieldGen.cancelInitValue();
          classGen.replaceField(field, fieldGen.getField());
        } else {
          Method method = null;
          int value3;
          Method[] methods;
          for (int value4 = value3 = (methods = classGen.getMethods()).length - 1;
              value4 >= 0;
              value4 = --value3) {
            Method method2 = methods[value3];
            if ("<clinit>".equals(method2.getText())) {
              method = method2;
              break;
            }
          }
          InstructionList instructionList = new InstructionList();
          instructionList.getInstructionHandle4(InstructionFactory.createReturn(Type.VOID));
          MethodGen methodGen;
          ClassGen classGen2;
          if (method != null) {
            instructionList =
                (methodGen =
                        MethodFactory.createMethodGen(
                            method,
                            classGen.getClassName(),
                            classGen.getConstantPool(),
                            classGen.getConstantPool().getConstantPool()))
                    .getInstructionList();
            classGen2 = classGen;
          } else {
            methodGen =
                new MethodGen(
                    8,
                    Type.VOID,
                    Type.NO_ARGS,
                    new String[0],
                    "<clinit>",
                    classGen.getClassName(),
                    instructionList,
                    classGen.getConstantPool());
            classGen2 = classGen;
          }
          String bytes =
              ((ConstantUtf8)
                      classGen2
                          .getConstantPool()
                          .getConstant(((ConstantString) constant).getStringIndex()))
                  .getBytes();
          InstructionFactory instructionFactory = new InstructionFactory(classGen);
          instructionList.getInstructionHandle4(
              instructionFactory.createFieldAccess(
                  classGen.getClassName(), field.getText(), Type.STRING, (short) 179));
          instructionList.getInstructionHandle2(new PUSH(classGen.getConstantPool(), bytes));
          methodGen.update5();
          methodGen.update4();
          InstructionList instructionList2;
          if (method != null) {
            classGen.replaceMethod(method, methodGen.getMethod());
            instructionList2 = instructionList;
          } else {
            classGen.processMethod2(methodGen.getMethod());
            instructionList2 = instructionList;
          }
          instructionList2.update3();
          FieldGen fieldGen2 = new FieldGen(field, classGen.getConstantPool());
          fieldGen2.cancelInitValue();
          classGen.replaceField(field, fieldGen2.getField());
        }
      }
    }
  }
}
