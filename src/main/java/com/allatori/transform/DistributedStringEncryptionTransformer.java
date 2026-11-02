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
import com.allatori.util.random.RandomUtils;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

public class DistributedStringEncryptionTransformer implements ClassTransformer {

  public static String text4 = "AllatoriDecryptString";

  public static String text = "AllatoriDecryptString2";

  public static String text2 = "allatori-12321-32123:";

  public static char character = 'U';

  public static Hashtable entries6 = new Hashtable();

  public static Type type = Type.STRING;

  public static boolean enabled2 = false;

  public static String text5 = "makeConcatWithConstants";

  public Hashtable entries;

  public String text3;

  public static Vector values;

  public ClassStorage classStorage;

  public Hashtable entries2;

  public Collection collection;

  public Hashtable entries3;

  public Hashtable entries4;

  public String text6;

  public Hashtable entries5;

  public boolean enabled;

  public Hashtable entries7;

  public Hashtable entries8;

  public static String createText(String text, String text2, String text3, String text4) {
    int value = ((int[]) entries6.get(text4))[2] == 0 ? 1 : 0;
    String text5 = value != 0 ? ("" + text2 + text3) : ("" + text3 + text2);
    int value2 = text5.length() - 1;
    int value3 = text5.length() - 1;
    char[] values = new char[text.length()];
    char value4 = (char) ((int[]) entries6.get(text4))[0];
    char value5 = (char) ((int[]) entries6.get(text4))[1];
    int value6;
    for (int value7 = value6 = values.length - 1; value7 >= 0; value7 = --value6) {
      char value8 = (char) (text.charAt(value6) ^ value4 ^ text5.charAt(value3));
      int value9 = value6;
      value6--;
      values[value9] = value8;
      if (value6 < 0) {
        break;
      }
      char value10 = (char) (text.charAt(value6) ^ value5 ^ text5.charAt(value3));
      int value11 = value6;
      value3--;
      values[value11] = value10;
      if (value3 < 0) {
        value3 = value2;
      }
    }
    return new String(values);
  }

  public void processValues(Vector vector, Vector vector2) {
    if (vector == null) {
      vector = new Vector();
    }
    if (vector2 == null) {
      vector2 = new Vector();
    }
    if (Configuration.getObfuscationOptions().isEnabled()) {
      Iterator iterator2;
      Iterator iterator3 = iterator2 = vector.iterator();
      while (iterator3.hasNext()) {
        ClassGen classGen = (ClassGen) iterator2.next();
        iterator3 = iterator2;
        StringEncryptionUtils.processCharacter(classGen, text4, character);
      }
    } else {
      Iterator iterator4;
      Iterator iterator5 = iterator4 = vector.iterator();
      while (iterator5.hasNext()) {
        ClassGen classGen2 = (ClassGen) iterator4.next();
        int value = RandomUtils.calculateCount(125) + 1;
        int value2 = RandomUtils.calculateCount(125) + 1;
        iterator5 = iterator4;
        this.entries.put(classGen2.getClassName(), new int[] {value, value2});
        this.transform2(classGen2);
      }
      Iterator iterator6;
      for (Iterator iterator7 = iterator6 = vector2.iterator();
          iterator7.hasNext();
          iterator7 = iterator6) {
        ClassGen classGen3 = (ClassGen) iterator6.next();
        int value3 = RandomUtils.calculateCount(125) + 1;
        int value4 = RandomUtils.calculateCount(125) + 1;
        int value5 = RandomUtils.calculateValue() <= 0.5 ? 0 : 1;
        entries6.put(classGen3.getClassName(), new int[] {value3, value4, value5});
        this.transform3(classGen3);
      }
    }
  }

  public DistributedStringEncryptionTransformer(ClassStorage classStorage2) {
    super();
    this.collection = new TreeSet();
    this.enabled = true;
    this.entries7 = new Hashtable();
    this.entries4 = new Hashtable();
    this.entries3 = new Hashtable();
    this.entries5 = new Hashtable();
    this.entries8 = new Hashtable();
    this.entries2 = new Hashtable();
    this.entries = new Hashtable();
    this.text3 = ".R";
    this.text6 = "org.springframework.boot.";
    this.classStorage = classStorage2;
    character = Configuration.getObfuscationOptions().getCharacter();
    values = new Vector();
    entries6 = new Hashtable();
    StringEncryptionUtils.update();
    this.collection.add("");
    this.finish2();
  }

  public void processValues(Vector vector) {
    DistributedStringEncryptionTransformer distributedStringEncryptionTransformer;
    Vector vector2;
    if (vector.size() != 0) {
      String text;
      if ((text = Configuration.getObfuscationOptions().getDecryptorIncludePattern()).length()
          > 0) {
        String[] texts = text.split(";");
        int value;
        for (int value2 = value = vector.size() - 1; value2 >= 0; value2 = --value) {
          ClassGen classGen = (ClassGen) vector.get(value);
          int value3;
          for (int value4 = value3 = texts.length - 1; value4 >= 0; value4 = --value3) {
            if (texts[value3].length() > 0 && !classGen.getClassName().startsWith(texts[value3])) {
              vector.removeElement(classGen);
            }
          }
        }
      }
      String text2;
      if ((text2 = Configuration.getObfuscationOptions().getDecryptorExcludePattern()).length()
          > 0) {
        String[] texts2 = text2.split(";");
        int value5;
        for (int value6 = value5 = vector.size() - 1; value6 >= 0; value6 = --value5) {
          ClassGen classGen2 = (ClassGen) vector.get(value5);
          int value7;
          for (int value8 = value7 = texts2.length - 1; value8 >= 0; value8 = --value7) {
            if (texts2[value7].length() > 0
                && classGen2.getClassName().startsWith(texts2[value7])) {
              vector.removeElement(classGen2);
            }
          }
        }
      }
      ClassGen classGen3 = null;
      ClassGen classGen4 = null;
      ClassGen classGen5 = null;
      String text3 = "java.";
      int value9;
      int value10 = value9 = vector.size() - 1;
      while (true) {
        if (value10 < 0) {
          vector2 = vector;
          break;
        }
        if (vector.size() == 1) {
          vector2 = vector;
          break;
        }
        ClassGen classGen6 = (ClassGen) vector.get(value9);
        byte value11 = 1;
        if (classGen6.containsMethod((String) "<clinit>", (String) "()V") != null) {
          value11 = 0;
        }
        byte value12 = 1;
        int value13;
        if (!classGen6.getSuperclassName().startsWith(text3)) {
          value12 = 0;
          value13 = value11;
        } else {
          String[] texts3;
          int value14 = (texts3 = classGen6.getInterfaceNames()).length;
          int value15;
          value13 = value15 = 0;
          while (true) {
            if (value13 >= value14) {
              value13 = value11;
              break;
            }
            if (!texts3[value15].startsWith(text3)) {
              value12 = 0;
              value13 = value11;
              break;
            }
            value13 = ++value15;
          }
        }
        if (value13 != 0 && classGen3 == null) {
          classGen3 = classGen6;
        }
        if (value12 != 0 && classGen4 == null) {
          classGen4 = classGen6;
        }
        if (value11 != 0 && value12 != 0 && classGen5 == null) {
          classGen5 = classGen6;
        }
        if (value11 == 0 || value12 == 0) {
          vector.remove(classGen6);
        }
        value10 = --value9;
      }
      label93:
      {
        if (vector2.size() == 1) {
          if (classGen5 != null) {
            distributedStringEncryptionTransformer = this;
            vector.clear();
            vector.add(classGen5);
            break label93;
          }
          if (classGen4 != null) {
            distributedStringEncryptionTransformer = this;
            vector.clear();
            vector.add(classGen4);
            break label93;
          }
          if (classGen3 != null) {
            vector.clear();
            vector.add(classGen3);
          }
        }
        distributedStringEncryptionTransformer = this;
      }
      ClassGen classGen7;
      if ((classGen7 =
              distributedStringEncryptionTransformer.classStorage.getClassGen(
                  "com.allatori.ant.ObfuscatorTask"))
          != null) {
        vector.removeElement(classGen7);
      }
      ClassGen classGen8;
      if ((classGen8 = this.classStorage.getClassGen("com.allatori.wtk.WTKObfuscator")) != null) {
        vector.removeElement(classGen8);
      }
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
            new Type[] {type},
            new String[] {"s"},
            text4,
            classGen.getClassName(),
            instructionList,
            constantPool);
    int value = RandomUtils.calculateValue() <= 0.5 ? 1 : 0;
    Vector vector;
    Vector vector2;
    if (value != 0) {
      vector2 =
          vector =
              StringEncryptionUtils.createValues(
                  ((int[]) this.entries.get(classGen.getClassName()))[0]);
      vector2.addAll(StringEncryptionUtils.createValues(RandomUtils.calculateCount(125) + 1));
    } else {
      (vector = StringEncryptionUtils.createValues(RandomUtils.calculateCount(125) + 1))
          .addAll(
              StringEncryptionUtils.createValues(
                  ((int[]) this.entries.get(classGen.getClassName()))[0]));
      vector2 = vector;
    }
    vector2.addAll(
        StringEncryptionUtils.createValues(((int[]) this.entries.get(classGen.getClassName()))[1]));
    Iterator iterator2 = vector.iterator();
    Iterator iterator3 = iterator2;
    while (iterator3.hasNext()) {
      Instruction instruction = (Instruction) iterator2.next();
      iterator3 = iterator2;
      instructionList.getInstructionHandle(instruction);
    }
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(instructionFactory.createCheckCast(Type.STRING));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.OBJECT, 0));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(instructionFactory.createNewArray(Type.CHAR, (short) 1));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.ISUB);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 3));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.OBJECT, 1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 4));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    if (value != 0) {
      instructionList.getInstructionHandle(InstructionFactory.POP2);
      instructionList.getInstructionHandle(
          InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
    } else {
      instructionList.getInstructionHandle(InstructionFactory.POP);
      instructionList.getInstructionHandle(
          InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
      instructionList.getInstructionHandle(InstructionFactory.POP);
    }
    BranchInstruction branchInstruction =
        InstructionFactory.createBranchInstruction((short) 155, null);
    BranchHandle branchHandle = instructionList.getBranchHandle(branchInstruction);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "charAt", Type.CHAR, new Type[] {Type.INT}, (short) 182));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 2));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(new IINC(3, -1));
    instructionList.getInstructionHandle(InstructionConstants.CASTORE);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    BranchInstruction branchInstruction2 =
        InstructionFactory.createBranchInstruction((short) 155, null);
    instructionList.getBranchHandle(branchInstruction2);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X1);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "charAt", Type.CHAR, new Type[] {Type.INT}, (short) 182));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 4));
    instructionList.getInstructionHandle(new IINC(3, -1));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(InstructionConstants.CASTORE);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 3));
    instructionList.getBranchHandle(
        InstructionFactory.createBranchInstruction((short) 167, branchHandle));
    InstructionHandle instructionHandle =
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
    branchInstruction.setTarget(instructionHandle);
    branchInstruction2.setTarget(instructionHandle);
    methodGen.update5();
    methodGen.update4();
    classGen.processMethod2(methodGen.getMethod());
    instructionList.update3();
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public void finish() {}

  public boolean matchesClassGen(ClassGen classGen) {
    return Configuration.getObfuscationOptions().isPackageStringEncryptionEnabled()
        ? !classGen.isEnabled9()
            && !classGen.isEnabled2()
            && !classGen.isEnabled10()
            && !classGen.isEnabled5()
            && !classGen.isEnabled8()
            && !classGen.getClassName().contains("$")
            && !classGen.getClassName().endsWith(this.text3)
        : classGen.isEnabled6()
            && !classGen.isEnabled2()
            && !classGen.isEnabled10()
            && !classGen.isEnabled5()
            && !classGen.isEnabled8()
            && !classGen.getClassName().contains("$")
            && !classGen.getClassName().endsWith(this.text3);
  }

  @Override
  public void transform(ClassGen classGen) {
    InstructionHandle instructionHandle;
    byte value = 0;
    Iterator iterator2 = this.entries7.values().iterator();
    int value2;
    while (true) {
      if (iterator2.hasNext()) {
        if (!((Vector) iterator2.next()).contains(classGen)) {
          continue;
        }
        value2 = 1;
        break;
      }
      value2 = value;
      break;
    }
    if (value2 != 0) {
      InstructionFactory instructionFactory = new InstructionFactory(classGen);
      byte value3 = 2;
      String[] texts = new String[2];
      int value4;
      if (((Vector) this.entries3.get(this.getText(classGen))).size() > 0) {
        for (int value5 = value4 = 0; value5 < value3; value5 = value4) {
          String className =
              ((ClassGen)
                      ((Vector) this.entries3.get(this.getText(classGen)))
                          .get(
                              RandomUtils.calculateCount(
                                  ((Vector) this.entries3.get(this.getText(classGen))).size())))
                  .getClassName();
          int value6 = value4;
          value4++;
          texts[value6] = className;
        }
      }
      int value7 = 0;
      String[] texts2 = new String[value3];
      int value8;
      if (((Vector) this.entries5.get(this.getText(classGen))).size() > 0) {
        for (int value9 = value8 = 0; value9 < value3; value9 = value8) {
          String className2 =
              ((ClassGen)
                      ((Vector) this.entries5.get(this.getText(classGen)))
                          .get(
                              RandomUtils.calculateCount(
                                  ((Vector) this.entries5.get(this.getText(classGen))).size())))
                  .getClassName();
          int value10 = value8;
          value8++;
          texts2[value10] = className2;
        }
      }
      int value11 = 0;
      Method[] methods = classGen.getMethods();
      int value12;
      for (value2 = value12 = 0; value2 < methods.length; value2 = ++value12) {
        Method method;
        if ((method = methods[value12]).getCode() != null) {
          if (classGen.getConstantPool().getSize() > 20000) {
            new ConstantPoolEditor(this.classStorage).processClassGen(classGen);
            instructionFactory = new InstructionFactory(classGen);
            method = (methods = classGen.getMethods())[value12];
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
            label156:
            {
              if (!enabled2
                  && instructionHandle2.getInstruction()
                      instanceof NameSignatureInstructionSupport) {
                NameSignatureInstructionSupport nameSignatureInstructionSupport =
                    (NameSignatureInstructionSupport) instructionHandle2.getInstruction();
                if (text5.equals(
                    nameSignatureInstructionSupport.getText3(classGen.getConstantPool()))) {
                  Log.warn(
                      "javac Java compiler should be run with -XDstringConcat=inline flag to"
                          + " encrypt all strings.");
                  enabled2 = true;
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
                  int value13;
                  label107:
                  {
                    value13 =
                        Configuration.getObfuscationOptions()
                            .calculateCount(this.classStorage, classGen, methodGen);
                    String text3;
                    if ((text3 = classGen.getClassName()).startsWith((String) "com.allatori")) {
                      if (text3.startsWith("com.allatori.obfuscate.opt")) {
                        Configuration.getObfuscationOptions();
                        value13 = 0;
                      }
                      if (text3.startsWith("com.allatori.obfuscate.ren")) {
                        Configuration.getObfuscationOptions();
                        value13 = 0;
                      }
                      if (text3.startsWith("com.allatori.io.re")) {
                        Configuration.getObfuscationOptions();
                        value13 = 0;
                        value2 = value13;
                        break label107;
                      }
                    } else if (text3.startsWith((String) "org.apache.bcel")) {
                      Configuration.getObfuscationOptions();
                      value13 = 0;
                    }
                    value2 = value13;
                  }
                  Configuration.getObfuscationOptions();
                  if (value2 == 1) {
                    String text6 = texts2[value11];
                    lDC.setIndex(
                        classGen.getConstantPool().addString("" + text2 + text6 + text2 + bytes));
                    String text7 = text;
                    ObjectType objectType = Type.STRING;
                    Type[] types = new Type[] {type};
                    InstructionFactory instructionFactory2 = instructionFactory;
                    InstructionHandle instructionHandle4 = instructionHandle2;
                    InstructionList instructionList2 = instructionList;
                    value11++;
                    instructionList2.createInstructionHandle2(
                        instructionHandle4,
                        instructionFactory2.createInvoke(
                            text6, text7, objectType, types, (short) 184));
                    if (value11 >= value3) {
                      value11 = 0;
                    }
                    if (!values.contains(classGen)) {
                      values.add(classGen);
                    }
                  } else {
                    Configuration.getObfuscationOptions();
                    if (value13 >= 1000) {
                      String text8;
                      if ((text8 =
                                  CustomStringEncryption.createText(
                                      bytes, value13, this.classStorage.getClassLoader()))
                              .startsWith(CustomStringEncryption.text)
                          && !values.contains(classGen)) {
                        values.add(classGen);
                      }
                      lDC.setIndex(classGen.getConstantPool().addString(text8));
                      instructionHandle = instructionHandle2;
                      instructionList.createInstructionHandle2(
                          instructionHandle2,
                          CustomStringEncryption.getInvokeInstruction(instructionFactory, value13));
                      break label156;
                    }
                    String text9 = texts[value7];
                    lDC.setIndex(
                        classGen.getConstantPool().addString(this.createText(bytes, text9)));
                    String text10 = text4;
                    ObjectType objectType2 = Type.STRING;
                    Type[] types2 = new Type[] {type};
                    InstructionFactory instructionFactory3 = instructionFactory;
                    InstructionHandle instructionHandle5 = instructionHandle2;
                    InstructionList instructionList3 = instructionList;
                    value7++;
                    instructionList3.createInstructionHandle2(
                        instructionHandle5,
                        instructionFactory3.createInvoke(
                            text9, text10, objectType2, types2, (short) 184));
                    if (value7 >= value3) {
                      value7 = 0;
                    }
                  }
                }
              }
              instructionHandle = instructionHandle2;
            }
            instructionHandle3 = instructionHandle2 = instructionHandle.getInstructionHandle2();
          }
          methodGen.update5();
          try {
            classGen.replaceMethod(method, methodGen.getMethod());
          } catch (Exception exception) {
            Log.error(
                "Error processing "
                    + classGen.getClassName()
                    + "."
                    + methodGen.getName()
                    + " "
                    + exception.getMessage());
          }
        }
      }
    }
  }

  public void transform3(ClassGen classGen) {
    InstructionFactory instructionFactory = new InstructionFactory(classGen);
    ConstantPoolGen constantPool = classGen.getConstantPool();
    InstructionList instructionList = new InstructionList();
    MethodGen methodGen =
        new MethodGen(
            9,
            Type.STRING,
            new Type[] {type},
            new String[] {"arg0"},
            text,
            classGen.getClassName(),
            instructionList,
            constantPool);
    instructionList.getInstructionHandle(instructionFactory.createNew("java.lang.StringBuffer"));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
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
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.AALOAD);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    int value = ((int[]) entries6.get(classGen.getClassName()))[2] == 0 ? 1 : 0;
    int value2 = RandomUtils.calculateValue() > 0.5 ? 1 : 0;
    InstructionList instructionList2;
    if (value2 != 0) {
      instructionList.getInstructionHandle(
          instructionFactory.createInvoke(
              "java.lang.StackTraceElement",
              value != 0 ? "getClassName" : "getMethodName",
              Type.STRING,
              Type.NO_ARGS,
              (short) 182));
      instructionList2 = instructionList;
    } else {
      instructionList.getInstructionHandle(
          instructionFactory.createInvoke(
              "java.lang.StackTraceElement",
              value != 0 ? "getMethodName" : "getClassName",
              Type.STRING,
              Type.NO_ARGS,
              (short) 182));
      instructionList2 = instructionList;
    }
    instructionList2.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.StringBuffer", "<init>", Type.VOID, new Type[] {Type.STRING}, (short) 183));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    if (value2 != 0) {
      instructionList.getInstructionHandle(
          instructionFactory.createInvoke(
              "java.lang.StackTraceElement",
              value != 0 ? "getMethodName" : "getClassName",
              Type.STRING,
              Type.NO_ARGS,
              (short) 182));
      instructionList.getInstructionHandle(
          instructionFactory.createInvoke(
              "java.lang.StringBuffer",
              "append",
              Type.STRINGBUFFER,
              new Type[] {Type.STRING},
              (short) 182));
      instructionList2 = instructionList;
    } else {
      instructionList.getInstructionHandle(
          instructionFactory.createInvoke(
              "java.lang.StackTraceElement",
              value != 0 ? "getClassName" : "getMethodName",
              Type.STRING,
              Type.NO_ARGS,
              (short) 182));
      instructionList.getInstructionHandle(InstructionConstants.ICONST_0);
      instructionList.getInstructionHandle(InstructionConstants.SWAP);
      instructionList.getInstructionHandle(
          instructionFactory.createInvoke(
              "java.lang.StringBuffer",
              "insert",
              Type.STRINGBUFFER,
              new Type[] {Type.INT, Type.STRING},
              (short) 182));
      instructionList2 = instructionList;
    }
    instructionList2.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.StringBuffer", "toString", Type.STRING, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.ISUB);
    int value3 = RandomUtils.calculateValue() <= 0.5 ? 1 : 0;
    Vector vector;
    Vector vector2;
    if (value3 != 0) {
      vector2 =
          vector =
              StringEncryptionUtils.createValues(
                  ((int[]) entries6.get(classGen.getClassName()))[0]);
      vector2.addAll(StringEncryptionUtils.createValues(RandomUtils.calculateCount(125) + 1));
    } else {
      (vector = StringEncryptionUtils.createValues(RandomUtils.calculateCount(125) + 1))
          .addAll(
              StringEncryptionUtils.createValues(
                  ((int[]) entries6.get(classGen.getClassName()))[0]));
      vector2 = vector;
    }
    vector2.addAll(
        StringEncryptionUtils.createValues(((int[]) entries6.get(classGen.getClassName()))[1]));
    instructionList.getInstructionHandle(InstructionConstants.FCONST_2);
    instructionList.getInstructionHandle(InstructionConstants.FCONST_1);
    instructionList.getInstructionHandle(InstructionConstants.POP);
    Iterator iterator2 = vector.iterator();
    Iterator iterator3 = iterator2;
    while (iterator3.hasNext()) {
      Instruction instruction = (Instruction) iterator2.next();
      iterator3 = iterator2;
      instructionList.getInstructionHandle(instruction);
    }
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(instructionFactory.createCheckCast(Type.STRING));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.OBJECT, 0));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "length", Type.INT, Type.NO_ARGS, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(instructionFactory.createNewArray(Type.CHAR, (short) 1));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(new PUSH(constantPool, 1));
    instructionList.getInstructionHandle(InstructionConstants.ISUB);
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.OBJECT, 3));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 7));
    if (value3 != 0) {
      instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
      instructionList.getInstructionHandle(InstructionConstants.POP2);
      instructionList.getInstructionHandle(
          InstructionFactory.createLocalVariableInstruction(Type.INT, 4));
      instructionList2 = instructionList;
    } else {
      instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
      instructionList.getInstructionHandle(InstructionConstants.POP);
      instructionList.getInstructionHandle(
          InstructionFactory.createLocalVariableInstruction(Type.INT, 4));
      instructionList.getInstructionHandle(InstructionConstants.POP);
      instructionList2 = instructionList;
    }
    instructionList2.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.POP);
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 5));
    instructionList.getInstructionHandle(InstructionConstants.SWAP);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.OBJECT, 6));
    BranchInstruction branchInstruction =
        InstructionFactory.createBranchInstruction((short) 167, null);
    instructionList.getBranchHandle(branchInstruction);
    InstructionHandle instructionHandle =
        instructionList.getInstructionHandle(
            InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 3));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 4));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 1));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "charAt", Type.CHAR, new Type[] {Type.INT}, (short) 182));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 6));
    instructionList.getInstructionHandle(new IINC(1, -1));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 2));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "charAt", Type.CHAR, new Type[] {Type.INT}, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(InstructionConstants.CASTORE);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 1));
    BranchInstruction branchInstruction2 =
        InstructionFactory.createBranchInstruction((short) 156, null);
    instructionList.getBranchHandle(branchInstruction2);
    BranchInstruction branchInstruction3 =
        InstructionFactory.createBranchInstruction((short) 167, null);
    instructionList.getBranchHandle(branchInstruction3);
    InstructionHandle instructionHandle2 =
        instructionList.getInstructionHandle(
            InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 3));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 7));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 0));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 1));
    instructionList.getInstructionHandle(InstructionConstants.DUP_X2);
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "charAt", Type.CHAR, new Type[] {Type.INT}, (short) 182));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 6));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 2));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String", "charAt", Type.CHAR, new Type[] {Type.INT}, (short) 182));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(new IINC(2, -1));
    instructionList.getInstructionHandle(InstructionConstants.IXOR);
    instructionList.getInstructionHandle(InstructionConstants.I2C);
    instructionList.getInstructionHandle(new IINC(1, -1));
    instructionList.getInstructionHandle(InstructionConstants.CASTORE);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 2));
    BranchInstruction branchInstruction4 =
        InstructionFactory.createBranchInstruction((short) 156, null);
    instructionList.getBranchHandle(branchInstruction4);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.INT, 5));
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction(Type.INT, 2));
    InstructionHandle instructionHandle3 =
        instructionList.getInstructionHandle(
            InstructionFactory.createLocalVariableInstruction2(Type.INT, 1));
    BranchHandle branchHandle =
        instructionList.getBranchHandle(
            InstructionFactory.createBranchInstruction((short) 156, instructionHandle));
    InstructionHandle instructionHandle4 =
        instructionList.getInstructionHandle(instructionFactory.createNew("java.lang.String"));
    instructionList.getInstructionHandle(InstructionConstants.DUP);
    instructionList.getInstructionHandle(
        InstructionFactory.createLocalVariableInstruction2(Type.OBJECT, 3));
    instructionList.getInstructionHandle(
        instructionFactory.createInvoke(
            "java.lang.String",
            "<init>",
            Type.VOID,
            new Type[] {new ArrayType(Type.CHAR, 1)},
            (short) 183));
    instructionList.getInstructionHandle(InstructionFactory.createReturn(Type.OBJECT));
    branchInstruction.setTarget(branchHandle);
    branchInstruction2.setTarget(instructionHandle2);
    branchInstruction3.setTarget(instructionHandle4);
    branchInstruction4.setTarget(instructionHandle3);
    methodGen.update5();
    methodGen.update4();
    classGen.processMethod2(methodGen.getMethod());
    instructionList.update3();
  }

  public boolean matchesBoolean(Boolean matches) {
    return matches == null ? false : matches;
  }

  public boolean matchesClassGen2(ClassGen classGen) {
    int value = Configuration.getObfuscationOptions().calculateCount(this.classStorage, classGen);
    Configuration.getObfuscationOptions();
    if (value == 0) {
      return false;
    }
    String text = this.getText(classGen);
    byte value2 = 0;
    Method[] methods;
    int value3 = (methods = classGen.getMethods()).length;
    int value4;
    for (value = value4 = 0; value < value3; value = ++value4) {
      Method method;
      if ((method = methods[value4]).getCode() != null) {
        InstructionHandle instructionHandle;
        MethodGen methodGen;
        InstructionHandle instructionHandle2 =
            instructionHandle =
                (methodGen =
                        MethodFactory.createMethodGen(
                            method,
                            classGen.getClassName(),
                            classGen.getConstantPool(),
                            classGen.getConstantPool().getConstantPool()))
                    .getInstructionList()
                    .getStart();
        while (instructionHandle2 != null) {
          if (instructionHandle.getInstruction() instanceof LDC) {
            LDC lDC = (LDC) instructionHandle.getInstruction();
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
                instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2();
                continue;
              }
              label56:
              {
                int value5 =
                    Configuration.getObfuscationOptions()
                        .calculateCount(this.classStorage, classGen, methodGen);
                String text2;
                if ((text2 = classGen.getClassName()).startsWith((String) "com.allatori")) {
                  if (text2.startsWith("com.allatori.obfuscate.opt")) {
                    Configuration.getObfuscationOptions();
                    value5 = 0;
                  }
                  if (text2.startsWith("com.allatori.obfuscate.ren")) {
                    Configuration.getObfuscationOptions();
                    value5 = 0;
                  }
                  if (text2.startsWith("com.allatori.io.re")) {
                    Configuration.getObfuscationOptions();
                    byte value6 = 0;
                    value = value6;
                    break label56;
                  }
                } else if (text2.startsWith((String) "org.apache.bcel")) {
                  Configuration.getObfuscationOptions();
                  value5 = 0;
                }
                value = value5;
              }
              Configuration.getObfuscationOptions();
              if (value == 1) {
                value2 = 1;
                this.entries2.put(text, true);
                if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
                  return true;
                }
              } else {
                value2 = 1;
                this.entries8.put(text, true);
                if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
                  return true;
                }
              }
            }
          }
          instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2();
        }
      }
    }
    return value2 != 0;
  }

  public void processValues2(Vector vector) {
    if (vector.size() != 0) {
      String text = this.getText((ClassGen) vector.get(0));
      int value =
          this.entries7.get(text) == null ? 0 : ((Vector) this.entries7.get(text)).size() / 8;
      if (value == 0) {
        value = 1;
      }
      int value2;
      if ((value2 = vector.size() / value) == 0) {
        value2 = 1;
      }
      Vector vector2;
      Vector vector3;
      DistributedStringEncryptionTransformer distributedStringEncryptionTransformer;
      label108:
      {
        vector3 = new Vector();
        vector2 = new Vector();
        if (vector.size() < value2 + 1) {
          if (vector.size() == 1) {
            if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
              vector3.add(vector.get(0));
            }
            if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
              distributedStringEncryptionTransformer = this;
              vector2.add(vector.get(0));
              break label108;
            }
          } else if (vector.size() == 2) {
            if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
              vector3.add(vector.get(0));
            }
            if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
              vector2.add(vector.get(0));
            }
            if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
              vector3.add(vector.get(1));
            }
            if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
              distributedStringEncryptionTransformer = this;
              vector2.add(vector.get(1));
              break label108;
            }
          } else if (vector.size() == 3) {
            if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
              vector3.add(vector.get(0));
            }
            if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
              vector2.add(vector.get(0));
            }
            if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
              vector3.add(vector.get(1));
            }
            if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
              distributedStringEncryptionTransformer = this;
              vector2.add(vector.get(2));
              break label108;
            }
          } else {
            if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
              vector3.add(vector.get(0));
            }
            if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
              vector2.add(vector.get(1));
            }
            if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
              vector3.add(vector.get(2));
            }
            if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
              distributedStringEncryptionTransformer = this;
              vector2.add(vector.get(3));
              break label108;
            }
          }
        } else {
          int value3;
          int value4 = value3 = 0;
          while (value4 < vector.size()) {
            int value5;
            if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
              value5 = value3;
              vector3.add(vector.get(value3));
            } else {
              if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
                vector2.add(vector.get(value3));
              }
              value5 = value3;
            }
            int value6;
            if ((value6 = value5 + value2) >= vector.size()) {
              distributedStringEncryptionTransformer = this;
              break label108;
            }
            int value7;
            if (this.matchesBoolean((Boolean) this.entries2.get(text))) {
              value7 = value6;
              vector2.add(vector.get(value6));
            } else {
              if (this.matchesBoolean((Boolean) this.entries8.get(text))) {
                vector3.add(vector.get(value6));
              }
              value7 = value6;
            }
            value4 = value3 = value7 + value2;
          }
        }
        distributedStringEncryptionTransformer = this;
      }
      distributedStringEncryptionTransformer.entries3.put(text, vector3);
      this.entries5.put(text, vector2);
    }
  }

  public void finish2() {
    Iterator classesByName2 = this.classStorage.getClassesByName2();
    label133:
    while (true) {
      for (Iterator iterator2 = classesByName2; iterator2.hasNext(); iterator2 = classesByName2) {
        ClassGen classGen = (ClassGen) classesByName2.next();
        if (!Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen)) {
          int value;
          int value2 =
              value =
                  Configuration.getObfuscationOptions().calculateCount(this.classStorage, classGen);
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
          byte value3 = 0;
          if (this.matchesClassGen2(classGen)) {
            Vector vector;
            if ((vector = (Vector) this.entries7.get(this.getText(classGen))) == null) {
              vector = new Vector();
              this.entries7.put(this.getText(classGen), vector);
              if (this.entries4.get(this.getText(classGen)) == null) {
                Vector vector2 = new Vector();
                if (this.classStorage.matchesClassGen(classGen)
                    && !classGen.isEnabled2()
                    && !classGen.isEnabled5()) {
                  vector2.add(classGen);
                  value3 = 1;
                }
                this.entries4.put(this.getText(classGen), vector2);
              }
            }
            if (!this.classStorage.matchesClassGen(classGen)
                || !classGen.isEnabled2() && !classGen.isEnabled5()) {
              vector.add(classGen);
            }
          }
          if (value3 == 0 && this.matchesClassGen(classGen)) {
            Vector vector3;
            if ((vector3 = (Vector) this.entries4.get(this.getText(classGen))) == null) {
              vector3 = new Vector();
              this.entries4.put(this.getText(classGen), vector3);
            }
            vector3.add(classGen);
          }
          continue label133;
        }
      }
      Iterator iterator3;
      Iterator iterator4 = iterator3 = this.entries4.values().iterator();
      while (iterator4.hasNext()) {
        Vector vector4 = (Vector) iterator3.next();
        iterator4 = iterator3;
        this.processValues(vector4);
      }
      Vector vector5 = new Vector();
      Iterator iterator5 = this.entries4.values().iterator();
      while (iterator5.hasNext()) {
        Vector vector6;
        if ((vector6 = (Vector) iterator5.next()).size() > vector5.size()) {
          vector5 = vector6;
        }
      }
      if (vector5.size() == 0) {
        this.enabled = false;
        return;
      }
      String text = this.getText((ClassGen) vector5.get(0));
      HashSet values2;
      (values2 = new HashSet()).addAll(this.entries4.keySet());
      for (String text2 : ((Iterable<String>) ((Iterable<?>) values2))) {
        if (((Vector) this.entries4.get(text2)).size() == 0) {
          if (this.matchesBoolean((Boolean) this.entries8.get(text2))) {
            this.entries8.put(text, true);
          }
          if (this.matchesBoolean((Boolean) this.entries2.get(text2))) {
            this.entries2.put(text, true);
          }
        }
      }
      Iterator iterator6;
      iterator4 = iterator6 = this.entries4.values().iterator();
      while (iterator4.hasNext()) {
        Vector vector7 = (Vector) iterator6.next();
        iterator4 = iterator6;
        this.processValues2(vector7);
      }
      values2.clear();
      values2.addAll(this.entries3.keySet());
      values2.addAll(this.entries5.keySet());
      Iterator iterator7 = values2.iterator();
      for (Iterator iterator8 = iterator7; iterator8.hasNext(); iterator8 = iterator7) {
        String text3 = (String) iterator7.next();
        this.processValues((Vector) this.entries3.get(text3), (Vector) this.entries5.get(text3));
      }
      values2.clear();
      values2.addAll(this.entries4.keySet());
      for (String text4 : ((Iterable<String>) ((Iterable<?>) values2))) {
        if (((Vector) this.entries4.get(text4)).size() == 0) {
          this.entries4.put(text4, vector5);
          this.entries3.put(text4, this.entries3.get(text));
          this.entries5.put(text4, this.entries5.get(text));
        }
      }
      return;
    }
  }

  public String getText(ClassGen classGen) {
    if (Configuration.getObfuscationOptions().isPackageStringEncryptionEnabled()) {
      String text;
      return (text = classGen.getClassName()).indexOf(46) > 0
          ? text.substring(0, text.lastIndexOf(46))
          : "";
    }
    if (classGen.getClassName().startsWith(this.text6)) {
      return "SPRINGBOOT";
    }
    if (Configuration.getObfuscationOptions().isGlobalStringEncryptionEnabled()) {
      return "GLOBAL";
    }
    if (!this.classStorage.matchesClassGen(classGen)) {
      return this.classStorage.getText(classGen);
    }
    String text2;
    if ((text2 = classGen.getClassName()).indexOf(36) > 0) {
      text2 = text2.substring(0, text2.indexOf(36));
    }
    return text2;
  }

  public String createText(String text, String text2) {
    char[] values = new char[text.length()];
    char value = (char) ((int[]) this.entries.get(text2))[0];
    char value2 = (char) ((int[]) this.entries.get(text2))[1];
    int value3;
    for (int value4 = value3 = values.length - 1; value4 >= 0; value4 = value3) {
      char value5 = (char) (text.charAt(value3) ^ value);
      int value6 = value3;
      value3--;
      values[value6] = value5;
      if (value3 < 0) {
        break;
      }
      char value7 = (char) (text.charAt(value3) ^ value2);
      int value8 = value3;
      value3--;
      values[value8] = value7;
    }
    return new String(values);
  }
}
