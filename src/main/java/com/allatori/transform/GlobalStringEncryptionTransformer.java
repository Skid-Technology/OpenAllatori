package com.allatori.transform;

import com.allatori.bytecode.ConstantPoolEditor;
import com.allatori.bytecode.MethodFactory;
import com.allatori.config.Configuration;
import com.allatori.crypto.CustomStringEncryption;
import com.allatori.crypto.StringEncryptionUtils;
import com.allatori.internal.NameSignatureInstructionSupport;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ArrayType;
import com.allatori.lib.bcel.generic.BranchHandle;
import com.allatori.lib.bcel.generic.BranchInstruction;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.IINC;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionConstants;
import com.allatori.lib.bcel.generic.InstructionFactory;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InstructionList;
import com.allatori.lib.bcel.generic.LDC;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.PUSH;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public class GlobalStringEncryptionTransformer implements ClassTransformer {

  public static String text2 = "com_allatori_string_decryptor";

  public static String text5 = "DecryptString";

  public static String text = "DecryptString2";

  public static String text4 = "allatori-12321-32123:";

  public static char character = 'U';

  public static boolean enabled = false;

  public static String text3 = "makeConcatWithConstants";

  public Collection collection;

  public ClassStorage classStorage;

  public static Vector values;

  public boolean enabled2 = false;

  @Override
  public void finish() {
    if (this.enabled2) {
      this.classStorage.processClassGen(this.createClassGen());
    }
  }

  public void transform2(ClassGen classGen) {
    InstructionFactory instructionFactory = new InstructionFactory(classGen);
    ConstantPoolGen constantPool = classGen.getConstantPool();
    InstructionList instructionList = new InstructionList();
    MethodGen methodGen =
        new MethodGen(
            9,
            Type.STRING,
            new Type[] {Type.STRING},
            new String[] {"s"},
            text5,
            classGen.getClassName(),
            instructionList,
            constantPool);
    instructionList.getInstructionHandle(instructionFactory.createNew("java.lang.String"));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(instructionFactory.createNewArray(Type.CHAR, (short) 1));
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(InstructionConstants.POP2);
    Iterator iterator2 = StringEncryptionUtils.createValues(character).iterator();
    Iterator iterator3 = iterator2;
    while (iterator3.hasNext()) {
      Instruction instruction = (Instruction) iterator2.next();
      iterator3 = iterator2;
      instructionList.getInstructionHandle(instruction);
    }
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(InstructionConstants.ARRAYLENGTH);
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.ISUB);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 3));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.OBJECT, 1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
    BranchInstruction branchInstruction =
        InstructionFactory.createBranchInstruction((short) 155, null);
    BranchHandle branchHandle = instructionList.getBranchHandle(branchInstruction);
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
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 2));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(new PUSH(constantPool, 63));
    instructionList.getInstructionHandle(InstructionConstants.IAND);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
    instructionList.getInstructionHandle(InstructionConstants.CASTORE);
    instructionList.getInstructionHandle(new IINC(3, -1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    BranchInstruction branchInstruction2 =
        InstructionFactory.createBranchInstruction((short) 155, null);
    instructionList.getBranchHandle(branchInstruction2);
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
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 2));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(new PUSH(constantPool, 63));
    instructionList.getInstructionHandle(InstructionConstants.IAND);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
    instructionList.getInstructionHandle(InstructionConstants.CASTORE);
    instructionList.getInstructionHandle(new IINC(3, -1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    instructionList.getBranchHandle(
        InstructionFactory.createBranchInstruction((short) 167, branchHandle));
    InstructionHandle instructionHandle =
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
    branchInstruction.setTarget(instructionHandle);
    branchInstruction2.setTarget(instructionHandle);
    methodGen.update5();
    methodGen.update4();
    classGen.processMethod2(methodGen.getMethod());
    instructionList.update3();
  }

  @Override
  public void transform(ClassGen classGen) {
    InstructionHandle instructionHandle;
    int value = Configuration.getObfuscationOptions().calculateCount(this.classStorage, classGen);
    Configuration.getObfuscationOptions();
    if (value != 0) {
      InstructionFactory instructionFactory = new InstructionFactory(classGen);
      Method[] methods = classGen.getMethods();
      int value2;
      for (value = value2 = 0; value < methods.length; value = ++value2) {
        Method method;
        if ((method = methods[value2]).getCode() != null) {
          if (classGen.getConstantPool().getSize() > 20000) {
            new ConstantPoolEditor(this.classStorage).processClassGen(classGen);
            instructionFactory = new InstructionFactory(classGen);
            method = (methods = classGen.getMethods())[value2];
          }
          InstructionHandle instructionHandle2;
          InstructionList instructionList;
          MethodGen methodGen;
          InstructionHandle instructionHandle3 =
              instructionHandle2 =
                  (instructionList =
                          (methodGen =
                                  MethodFactory.createMethodGen(
                                      method,
                                      classGen.getClassName(),
                                      classGen.getConstantPool(),
                                      classGen.getConstantPool().getConstantPool()))
                              .getInstructionList())
                      .getStart();
          while (instructionHandle3 != null) {
            label111:
            {
              if (!enabled
                  && instructionHandle2.getInstruction()
                      instanceof NameSignatureInstructionSupport) {
                NameSignatureInstructionSupport nameSignatureInstructionSupport =
                    (NameSignatureInstructionSupport) instructionHandle2.getInstruction();
                if (text3.equals(
                    nameSignatureInstructionSupport.getText3(classGen.getConstantPool()))) {
                  Log.warn(
                      "javac Java compiler should be run with -XDstringConcat=inline flag to"
                          + " encrypt all strings.");
                  enabled = true;
                }
              } else if (instructionHandle2.getInstruction() instanceof LDC) {
                LDC lDC = (LDC) instructionHandle2.getInstruction();
                Constant constant;
                if ((constant = classGen.getConstantPool().getConstant(lDC.getIndex()))
                    instanceof ConstantString) {
                  String bytes =
                      ((ConstantUtf8)
                              classGen
                                  .getConstantPool()
                                  .getConstant(((ConstantString) constant).getStringIndex()))
                          .getBytes();
                  if (this.classStorage.getClassGen(bytes) != null
                      || this.collection.contains(bytes)
                      || StringEncryptionUtils.matchesText(bytes)) {
                    instructionHandle3 =
                        instructionHandle2 = instructionHandle2.getInstructionHandle2();
                    continue;
                  }
                  int value3;
                  label85:
                  {
                    value3 =
                        Configuration.getObfuscationOptions()
                            .calculateCount(this.classStorage, classGen, methodGen);
                    String text6;
                    if ((text6 = classGen.getClassName()).startsWith((String) "com.allatori")) {
                      if (text6.startsWith("com.allatori.obfuscate.opt")) {
                        Configuration.getObfuscationOptions();
                        value3 = 0;
                      }
                      if (text6.startsWith("com.allatori.obfuscate.ren")) {
                        Configuration.getObfuscationOptions();
                        value3 = 0;
                      }
                      if (text6.startsWith("com.allatori.io.re")) {
                        Configuration.getObfuscationOptions();
                        value3 = 0;
                        value = value3;
                        break label85;
                      }
                    } else if (text6.startsWith((String) "org.apache.bcel")) {
                      Configuration.getObfuscationOptions();
                      value3 = 0;
                    }
                    value = value3;
                  }
                  Configuration.getObfuscationOptions();
                  if (value == 1) {
                    lDC.setIndex(classGen.getConstantPool().addString("" + text4 + bytes));
                    instructionList.createInstructionHandle2(
                        instructionHandle2,
                        instructionFactory.createInvoke(
                            text2, text, Type.STRING, new Type[] {Type.STRING}, (short) 184));
                    if (!values.contains(classGen)) {
                      values.add(classGen);
                    }
                    this.enabled2 = true;
                    instructionHandle = instructionHandle2;
                    break label111;
                  }
                  Configuration.getObfuscationOptions();
                  if (value3 >= 1000) {
                    String text7;
                    if ((text7 =
                                CustomStringEncryption.createText(
                                    bytes, value3, this.classStorage.getClassLoader()))
                            .startsWith(CustomStringEncryption.text)
                        && !values.contains(classGen)) {
                      values.add(classGen);
                    }
                    lDC.setIndex(classGen.getConstantPool().addString(text7));
                    instructionHandle = instructionHandle2;
                    instructionList.createInstructionHandle2(
                        instructionHandle2,
                        CustomStringEncryption.getInvokeInstruction(instructionFactory, value3));
                    break label111;
                  }
                  lDC.setIndex(classGen.getConstantPool().addString(this.createText(bytes)));
                  instructionList.createInstructionHandle2(
                      instructionHandle2,
                      instructionFactory.createInvoke(
                          text2, text5, Type.STRING, new Type[] {Type.STRING}, (short) 184));
                  this.enabled2 = true;
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

  public GlobalStringEncryptionTransformer(ClassStorage classStorage2) {
    this.collection = new TreeSet();
    this.classStorage = classStorage2;
    character = Configuration.getObfuscationOptions().getCharacter();
    values = new Vector();
    StringEncryptionUtils.update();
    this.collection.add("");
    Iterator classesByName2 = classStorage2.getClassesByName2();
    label27:
    while (true) {
      for (Iterator iterator = classesByName2; iterator.hasNext(); iterator = classesByName2) {
        ClassGen classGen = (ClassGen) classesByName2.next();
        if (!Configuration.getIgnoreClassesOptions().matchesClassGen(classStorage2, classGen)) {
          int value;
          int value2 =
              value = Configuration.getObfuscationOptions().calculateCount(classStorage2, classGen);
          Configuration.getObfuscationOptions();
          if (value2 == 1) {
            StringEncryptionUtils.processCollection(classGen, this.collection);
          } else {
            Configuration.getObfuscationOptions();
            if (value == 2) {
              StringEncryptionUtils.processClassGen2(classGen);
            } else {
              Configuration.getObfuscationOptions();
              if (value == 3) {
                StringEncryptionUtils.processClassGen2(classGen);
                StringEncryptionUtils.processClassGen(classGen);
              }
            }
          }
          continue label27;
        }
      }
      return;
    }
  }

  public String createText(String text) {
    char[] values = new char[text.length()];
    char value = character;
    int value2;
    for (int value3 = value2 = values.length - 1; value3 >= 0; value3 = value2) {
      values[value2] = (char) (text.charAt(value2) ^ value);
      char value4 = (char) (value ^ value2);
      value2--;
      value = (char) (value4 & (byte) 63);
    }
    return new String(values);
  }

  public void transform3(ClassGen classGen) {
    InstructionFactory instructionFactory = new InstructionFactory(classGen);
    ConstantPoolGen constantPool = classGen.getConstantPool();
    InstructionList instructionList = new InstructionList();
    MethodGen methodGen =
        new MethodGen(
            9,
            Type.STRING,
            new Type[] {Type.STRING},
            new String[] {"arg0"},
            text,
            classGen.getClassName(),
            instructionList,
            constantPool);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    String text2 = "java.lang.RuntimeException";
    instructionList.getInstructionHandle(instructionFactory.createNew(text2));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(text2, "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            text2,
            "getStackTrace",
            new ArrayType(new ObjectType("java.lang.StackTraceElement"), 1),
            Type.NO_ARGS,
            (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.AALOAD);
    instructionList.getInstructionHandle(instructionFactory.createNew("java.lang.StringBuffer"));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.StringBuffer", "<init>", Type.VOID, Type.NO_ARGS, (short) 183));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.StackTraceElement", "getClassName", Type.STRING, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.StackTraceElement",
            "getMethodName",
            Type.STRING,
            Type.NO_ARGS,
            (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    instructionList.getInstructionHandle(InstructionConstants.POP);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.StringBuffer",
            "append",
            Type.STRINGBUFFER,
            new Type[] {Type.STRING},
            (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.StringBuffer",
            "append",
            Type.STRINGBUFFER,
            new Type[] {Type.STRING},
            (short) 182));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.StringBuffer", "toString", Type.STRING, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.ISUB);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 3));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.OBJECT, 2));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 4));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(instructionFactory.createNewArray(Type.CHAR, (short) 1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.OBJECT, 5));
    Iterator iterator2 = StringEncryptionUtils.createValues(character).iterator();
    Iterator iterator3 = iterator2;
    while (iterator3.hasNext()) {
      Instruction instruction = (Instruction) iterator2.next();
      iterator3 = iterator2;
      instructionList.getInstructionHandle(instruction);
    }
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 6));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.ISUB);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 7));
    BranchInstruction branchInstruction =
        InstructionFactory.createBranchInstruction((short) 167, null);
    instructionList.getBranchHandle(branchInstruction);
    InstructionHandle instructionHandle =
        instructionList.getInstructionHandle(new PUSH(constantPool, 63));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    instructionList.getInstructionHandle(InstructionConstants.POP);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(InstructionConstants.POP);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 6));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    instructionList.getInstructionHandle(InstructionConstants.POP);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 2));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 4));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "charAt", Type.CHAR, new Type[] {Type.INT}, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 7));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "charAt", Type.CHAR, new Type[] {Type.INT}, (short) 182));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 6));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(InstructionConstants.CASTORE);
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.IAND);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 6));
    instructionList.getInstructionHandle(new IINC(4, -1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 4));
    BranchInstruction branchInstruction2 =
        InstructionFactory.createBranchInstruction((short) 156, null);
    instructionList.getBranchHandle(branchInstruction2);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 4));
    InstructionHandle instructionHandle2 = instructionList.getInstructionHandle(new IINC(7, -1));
    InstructionHandle instructionHandle3 =
        instructionList.getInstructionHandle(
            InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 5));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 7));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    BranchInstruction branchInstruction3 =
        InstructionFactory.createBranchInstruction((short) 156, instructionHandle);
    instructionList.getBranchHandle(branchInstruction3);
    instructionList.getInstructionHandle(InstructionConstants.POP2);
    instructionList.getInstructionHandle(instructionFactory.createNew("java.lang.String"));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 5));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String",
            "<init>",
            Type.VOID,
            new Type[] {new ArrayType(Type.CHAR, 1)},
            (short) 183));
    instructionList.getInstructionHandle(InstructionFactory.createReturn(Type.OBJECT));
    branchInstruction.setTarget(instructionHandle3);
    branchInstruction2.setTarget(instructionHandle2);
    methodGen.update5();
    methodGen.update4();
    classGen.processMethod2(methodGen.getMethod());
    instructionList.update3();
  }

  public void finish2() {}

  public static String createText(String text, String text2) {
    int value = text2.length() - 1;
    int value2 = text2.length() - 1;
    char[] values = new char[text.length()];
    char value3 = character;
    int value4;
    for (int value5 = value4 = values.length - 1; value5 >= 0; value5 = --value4) {
      values[value4] = (char) (text.charAt(value4) ^ value3 ^ text2.charAt(value2));
      char value6 = (char) (value3 ^ value4 ^ text2.charAt(value2));
      value2--;
      value3 = (char) (value6 & (byte) 63);
      if (value2 < 0) {
        value2 = value;
      }
    }
    return new String(values);
  }

  public ClassGen createClassGen() {
    ClassGen classGen = new ClassGen(text2, "java.lang.Object", "", 33, new String[0]);
    if (Configuration.getObfuscationOptions().isEnabled()) {
      StringEncryptionUtils.processCharacter(classGen, text5, character);
      return classGen;
    } else {
      this.transform2(classGen);
      this.transform3(classGen);
      return classGen;
    }
  }
}
