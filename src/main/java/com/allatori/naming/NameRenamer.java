package com.allatori.naming;

import com.allatori.bytecode.ClassHierarchy;
import com.allatori.bytecode.MethodFactory;
import com.allatori.config.Configuration;
import com.allatori.engine.ObfuscationAbortException;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.BootstrapMethods;
import com.allatori.lib.bcel.classfile.Code;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantCP;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantFieldref;
import com.allatori.lib.bcel.classfile.ConstantInterfaceMethodref;
import com.allatori.lib.bcel.classfile.ConstantInvokeDynamic;
import com.allatori.lib.bcel.classfile.ConstantMethodType;
import com.allatori.lib.bcel.classfile.ConstantMethodref;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ConstantValue;
import com.allatori.lib.bcel.classfile.Deprecated;
import com.allatori.lib.bcel.classfile.EnclosingMethod;
import com.allatori.lib.bcel.classfile.ExceptionTable;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.InnerClass;
import com.allatori.lib.bcel.classfile.InnerClasses;
import com.allatori.lib.bcel.classfile.LineNumberTable;
import com.allatori.lib.bcel.classfile.LocalVariableTable;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.NestHost;
import com.allatori.lib.bcel.classfile.NestMembers;
import com.allatori.lib.bcel.classfile.PMGClass;
import com.allatori.lib.bcel.classfile.Signature;
import com.allatori.lib.bcel.classfile.SourceFile;
import com.allatori.lib.bcel.classfile.StackMap;
import com.allatori.lib.bcel.classfile.Synthetic;
import com.allatori.lib.bcel.classfile.Unknown;
import com.allatori.lib.bcel.generic.AnnotationElementValueGen;
import com.allatori.lib.bcel.generic.AnnotationEntryGen;
import com.allatori.lib.bcel.generic.ArrayElementValueGen;
import com.allatori.lib.bcel.generic.ClassElementValueGen;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.ElementValueGen;
import com.allatori.lib.bcel.generic.ElementValuePairGen;
import com.allatori.lib.bcel.generic.EnumElementValueGen;
import com.allatori.lib.bcel.generic.FieldGen;
import com.allatori.lib.bcel.generic.LocalVariableGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.SimpleElementValueGen;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.model.ClassStorage;
import com.allatori.naming.generator.LowercaseNameGenerator;
import com.allatori.naming.generator.MainNameGenerator;
import com.allatori.util.Log;
import com.allatori.util.LookupTable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

public class NameRenamer {

  public ClassHierarchy classHierarchy;

  public static final String TEXT = "&";

  public static boolean enabled = false;

  public static LookupTable lookupTable = new LookupTable();

  public NamingContext namingContext = new NamingContext(this, null);

  public ClassStorage classStorage;

  public NameMappingEntry nameMappingEntry = new NameMappingEntry();

  public static NameMappingEntry getNameMappingEntry(NameRenamer nameRenamer) {
    return nameRenamer.nameMappingEntry;
  }

  public void update() throws ObfuscationAbortException {
    MainNameGenerator.reset2();
    Iterator iterator2;
    Iterator iterator3 = iterator2 = this.classHierarchy.getValues().iterator();
    while (iterator3.hasNext()) {
      ClassGen classGen = (ClassGen) iterator2.next();
      if (Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen)) {
        iterator3 = iterator2;
      } else {
        this.processClassGen9(classGen);
        this.processClassGen8(classGen);
        this.processClassGen7(classGen);
        iterator3 = iterator2;
      }
    }
    if (!enabled && lookupTable.getEntries() > 0) {
      enabled = true;
      throw new ObfuscationAbortException();
    }
  }

  public void update2() {
    Iterator iterator2;
    Iterator iterator3 = iterator2 = this.classHierarchy.getValues().iterator();
    while (iterator3.hasNext()) {
      ClassGen classGen = (ClassGen) iterator2.next();
      if (Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen)) {
        iterator3 = iterator2;
      } else {
        this.processClassGen4(classGen);
        this.processClassGen5(classGen);
        iterator3 = iterator2;
      }
    }
  }

  public NameRenamer(ClassStorage classStorage2) {
    this.classStorage = classStorage2;
    NameRenamer nameRenamer;
    if (Configuration.getRenamingOptions().isText2()) {
      try {
        MappingImportHandler.processMappingImportHandler(new MappingImportHandler(this, null));
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      }
      nameRenamer = this;
    } else {
      nameRenamer = this;
    }
    nameRenamer.classHierarchy = new ClassHierarchy(classStorage2);
  }

  public boolean matchesEnabled(ClassGen classGen, String text, String text2, boolean matches) {
    if (FieldNameTable.getLookupTable3(NamingContext.getFieldNameTable(this.namingContext))
        .matchesText("" + classGen.getClassName() + "&" + text + "&" + text2)) {
      return true;
    }
    if (!matches) {
      return false;
    }
    int value;
    Vector vector;
    for (int value2 =
            value = (vector = this.classHierarchy.getValues(classGen.getClassName())).size() - 1;
        value2 >= 0;
        value2 = --value) {
      if (this.matchesEnabled((ClassGen) vector.get(value), text, text2, matches)) {
        return true;
      }
    }
    return false;
  }

  public void processClassGen(ClassGen classGen) {
    int value;
    ConstantPoolGen constantPoolGen;
    for (int value2 = value = (constantPoolGen = classGen.getConstantPool()).getSize() - 1;
        value2 >= 0;
        value2 = --value) {
      Constant constant;
      if (!((constant = constantPoolGen.getConstant(value)) instanceof ConstantClass)) {
        if (constant instanceof ConstantCP) {
          ConstantPool constantPool = constantPoolGen.getConstantPool();
          ConstantCP constantCP;
          String classValue = (constantCP = (ConstantCP) constant).getClass(constantPool);
          String text;
          if ((text =
                  ParameterNameTable.getLookupTable3(
                          NamingContext.getParameterNameTable(this.namingContext))
                      .getText(classValue))
              != null) {
            classValue = text;
          }
          ConstantNameAndType constantNameAndType =
              (ConstantNameAndType)
                  constantPoolGen.getConstant(constantCP.getName_and_type_index());
          String text2 = constantNameAndType.getText(constantPool);
          String text22 = constantNameAndType.getText2(constantPool);
          String text3 = null;
          String text4 = null;
          String text5;
          if (!(constantCP instanceof ConstantMethodref)
              && !(constantCP instanceof ConstantInterfaceMethodref)) {
            if (constantCP instanceof ConstantFieldref) {
              text3 =
                  FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
                      .getText("" + classValue + "&" + text2 + "&" + text22);
              text4 =
                  FieldNameTable.getLookupTable2(
                          NamingContext.getFieldNameTable(this.namingContext))
                      .getText(text22);
            }
            text5 = text3;
          } else {
            text3 =
                MethodNameTable.getLookupTable2(
                        NamingContext.getMethodNameTable(this.namingContext))
                    .getText("" + classValue + "&" + text2 + "&" + text22);
            text4 =
                MethodNameTable.getLookupTable(NamingContext.getMethodNameTable(this.namingContext))
                    .getText(text22);
            text5 = text3;
          }
          if (text5 != null && !text3.equals(text2) || text4 != null) {
            if (text3 == null) {
              text3 = text2;
            }
            if (text4 == null) {
              text4 = text22;
            }
            int value3 = constantPoolGen.addNameAndType(text3, text4);
            constantCP.setName_and_type_index(value3);
          }
        } else if (!(constant instanceof ConstantUtf8)
            && !(constant instanceof ConstantMethodType)
            && constant instanceof ConstantInvokeDynamic) {
          ConstantInvokeDynamic constantInvokeDynamic = (ConstantInvokeDynamic) constant;
          ConstantPool constantPool2 = constantPoolGen.getConstantPool();
          ConstantNameAndType constantNameAndType2;
          ConstantNameAndType constantNameAndType3 =
              constantNameAndType2 =
                  (ConstantNameAndType)
                      constantPoolGen.getConstant(
                          constantInvokeDynamic.getBootstrapMethodAttrIndex2());
          String text23 = constantNameAndType3.getText2(constantPool2);
          String text6 = constantNameAndType3.getText(constantPool2);
          if (text23.contains(")L")) {
            String text7 =
                text23
                    .substring(text23.lastIndexOf(")L") + 2, text23.length() - 1)
                    .replace('/', '.');
            if (ParameterNameTable.getLookupTable(
                        NamingContext.getParameterNameTable(this.namingContext))
                    .getText(text7)
                != null) {
              String text8 = null;
              if (classGen.getAttributes() != null) {
                Attribute[] attributes;
                int value4 = (attributes = classGen.getAttributes()).length;
                int value5;
                for (int value6 = value5 = 0; value6 < value4; value6 = ++value5) {
                  int[] values;
                  Attribute attribute;
                  if ((attribute = attributes[value5]) instanceof BootstrapMethods
                      && (values =
                              ((BootstrapMethods) attribute)
                                      .getBootstrapMethods()[
                                      constantInvokeDynamic.getBootstrapMethodAttrIndex()]
                                      .getBootstrapArguments())
                          != null) {
                    int[] values2 = values;
                    int value7 = values.length;
                    int value8;
                    for (int value9 = value8 = 0; value9 < value7; value9 = ++value8) {
                      int value10;
                      if (constantPoolGen.getConstant(value10 = values2[value8])
                          instanceof ConstantMethodType) {
                        ConstantMethodType constantMethodType =
                            (ConstantMethodType) constantPoolGen.getConstant(value10);
                        text8 =
                            ((ConstantUtf8)
                                    constantPoolGen.getConstant(
                                        constantMethodType.getDescriptorIndex()))
                                .getBytes();
                        break;
                      }
                    }
                  }
                }
              }
              String text9;
              if (text8 != null
                  && (text9 =
                          MethodNameTable.getLookupTable2(
                                  NamingContext.getMethodNameTable(this.namingContext))
                              .getText("" + text7 + "&" + text6 + "&" + text8))
                      != null) {
                constantNameAndType2.setName_index(constantPoolGen.addUtf8(text9));
              }
            }
          }
          String text24 = constantNameAndType2.getText2(constantPool2);
          String text10 = this.createText(text24);
          if (!text24.equals(text10)) {
            constantNameAndType2.setSignature_index(constantPoolGen.addUtf8(text10));
          }
        }
      }
    }
    int value11;
    for (int value12 = value11 = constantPoolGen.getSize() - 1; value12 >= 0; value12 = --value11) {
      Constant constant2;
      if ((constant2 = constantPoolGen.getConstant(value11)) instanceof ConstantClass) {
        ConstantPool constantPool3 = constantPoolGen.getConstantPool();
        ConstantClass constantClass;
        String text11 =
            ((String) (constantClass = (ConstantClass) constant2).getConstantValue(constantPool3))
                .replace('/', '.');
        byte value13 = 0;
        int value14;
        value12 = value14 = 0;
        while (value12 < text11.length() && text11.charAt(value14) == '[') {
          value12 = ++value14;
        }
        if (value14 > 0 && text11.endsWith(";") && text11.charAt(value14) == 'L') {
          text11 = text11.substring(value14 + 1, text11.length() - 1);
          value13 = 1;
        }
        String text12;
        if ((text12 =
                ParameterNameTable.getLookupTable(
                        NamingContext.getParameterNameTable(this.namingContext))
                    .getText(text11))
            != null) {
          String text13 = text12.replace('.', '/');
          if (value13 != 0) {
            text13 = "[L" + text13 + ";";
            for (int value15 = value14; value15 > 1; value15 = value14) {
              StringBuilder stringBuilder = new StringBuilder().insert(0, "[");
              String text14 = text13;
              value14--;
              text13 = stringBuilder.append(text14).toString();
            }
          }
          constantClass.setNameIndex(constantPoolGen.addUtf8(text13));
          Object result;
          Map entries;
          if ((result = (entries = constantPoolGen.class_table).get(text11.replace('.', '/')))
              != null) {
            entries.put(text13, result);
          }
        }
      } else if (!(constant2 instanceof ConstantCP)) {
        if (!(constant2 instanceof ConstantUtf8)) {
          if (constant2 instanceof ConstantMethodType) {
            ConstantMethodType constantMethodType2 = (ConstantMethodType) constant2;
            ConstantUtf8 constantUtf8 =
                (ConstantUtf8)
                    constantPoolGen.getConstant(constantMethodType2.getDescriptorIndex());
            constantUtf8.setBytes(this.createText(constantUtf8.getBytes()));
          }
        } else {
          ConstantUtf8 constantUtf82;
          String bytes = (constantUtf82 = (ConstantUtf8) constant2).getBytes();
          String text15;
          if ((text15 =
                  ParameterNameTable.getLookupTable(
                          NamingContext.getParameterNameTable(this.namingContext))
                      .getText(bytes))
              != null) {
            constantUtf82.setBytes(text15);
          } else {
            int value16;
            value12 = value16 = 0;
            while (value12 < bytes.length() && bytes.charAt(value16) == '[') {
              value12 = ++value16;
            }
            if (value16 > 0 && bytes.endsWith(";") && bytes.charAt(value16) == 'L') {
              bytes = bytes.substring(value16 + 1, bytes.length() - 1).replace('/', '.');
              String text16;
              if ((text16 =
                      ParameterNameTable.getLookupTable(
                              NamingContext.getParameterNameTable(this.namingContext))
                          .getText(bytes))
                  != null) {
                String text17 = "[L" + text16.replace('.', '/') + ";";
                for (int value17 = value16; value17 > 1; value17 = value16) {
                  StringBuilder stringBuilder2 = new StringBuilder().insert(0, "[");
                  String text18 = text17;
                  value16--;
                  text17 = stringBuilder2.append(text18).toString();
                }
                constantUtf82.setBytes(text17);
                continue;
              }
            }
            if (bytes.startsWith("L") && bytes.endsWith(";")) {
              String text19 = bytes.substring(1, bytes.length() - 1).replace('/', '.');
              String text20;
              if ((text20 =
                      ParameterNameTable.getLookupTable(
                              NamingContext.getParameterNameTable(this.namingContext))
                          .getText(text19))
                  != null) {
                String text21 = "L" + text20.replace('.', '/') + ";";
                constantUtf82.setBytes(text21);
              }
            }
          }
        }
      }
    }
  }

  public static boolean isEnabled() {
    return enabled;
  }

  public void processEnabled(
      ClassGen classGen,
      String text,
      String text2,
      String text3,
      boolean matches,
      boolean matches2) {
    if (matches) {
      ClassGen classGen2 = classGen;
      do {
        String superclassName = classGen2.getSuperclassName();
        if ((classGen2 = this.classStorage.getClassGen(superclassName)) != null) {
          MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
              .processText("" + classGen2.getClassName() + "&" + text + "&" + text3, text2);
          if (classGen2.isEnabled2()) {
            MethodNameTable.getLookupTable5(NamingContext.getMethodNameTable(this.namingContext))
                .processText("" + classGen2.getClassName() + "&" + text, text2);
          }
          MethodNameTable.getLookupTable3(NamingContext.getMethodNameTable(this.namingContext))
              .processText(
                  "" + classGen2.getClassName() + "&" + text2 + "&" + NameUtils.getText(text3),
                  "&");
        }
      } while (classGen2 != null);
    }
    int value;
    Vector vector;
    for (int value2 =
            value = (vector = this.classHierarchy.getValues(classGen.getClassName())).size() - 1;
        value2 >= 0;
        value2 = value) {
      ClassGen classGen3 = (ClassGen) vector.get(value);
      NameRenamer nameRenamer;
      if (matches2
          && Configuration.getRenamingOptions().isText()
          && !NameUtils.getText2(classGen.getClassName())
              .equals(NameUtils.getText2(classGen3.getClassName()))) {
        nameRenamer = this;
        MethodNameTable.getLookupTable3(NamingContext.getMethodNameTable(this.namingContext))
            .processText(
                "" + classGen3.getClassName() + "&" + text2 + "&" + NameUtils.getText(text3), "&");
      } else {
        MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
            .processText("" + classGen3.getClassName() + "&" + text + "&" + text3, text2);
        if (classGen3.isEnabled2()) {
          MethodNameTable.getLookupTable5(NamingContext.getMethodNameTable(this.namingContext))
              .processText("" + classGen3.getClassName() + "&" + text, text2);
        }
        nameRenamer = this;
        MethodNameTable.getLookupTable3(NamingContext.getMethodNameTable(this.namingContext))
            .processText(
                "" + classGen3.getClassName() + "&" + text2 + "&" + NameUtils.getText(text3), "&");
      }
      NameRenamer nameRenamer2 = nameRenamer;
      value--;
      nameRenamer2.processEnabled(classGen3, text, text2, text3, matches, matches2);
    }
  }

  public void processConstantPoolGen(List values, ConstantPoolGen constantPoolGen) {
    if (values != null) {
      int value;
      for (int value2 = value = values.size() - 1; value2 >= 0; value2 = value) {
        Object result = values.get(value);
        value--;
        this.processConstantPoolGen((AnnotationEntryGen) result, constantPoolGen);
      }
    }
  }

  public void processClassGen2(ClassGen classGen) {
    String className = classGen.getClassName();
    Field[] fields = classGen.getFields();
    if (!Configuration.getRenamingOptions().isFieldsNamingEnabled()) {
      FieldNameTable.getNameGenerator(NamingContext.getFieldNameTable(this.namingContext)).reset();
    }
    int value;
    for (int value2 = value = fields.length - 1; value2 >= 0; value2 = --value) {
      Field field;
      Field field2 = field = fields[value];
      String text = field2.getText();
      String text2 = field2.getText2();
      String text3;
      if ((text3 =
              FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
                  .getText("" + className + "&" + text + "&" + text2))
          == null) {
        NameRenamer nameRenamer = this;
        for (text3 =
                FieldNameTable.getNameGenerator(NamingContext.getFieldNameTable(this.namingContext))
                    .nextName();
            nameRenamer.matchesEnabled(classGen, text3, text2, !field.isEnabled9());
            text3 =
                FieldNameTable.getNameGenerator(NamingContext.getFieldNameTable(this.namingContext))
                    .nextName()) {
          nameRenamer = this;
        }
        FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
            .processText("" + className + "&" + text + "&" + text2, text3);
        FieldNameTable.getLookupTable3(NamingContext.getFieldNameTable(this.namingContext))
            .processText("" + className + "&" + text3 + "&" + text2, "&");
      }
      if (!field.isEnabled9()) {
        this.processText(classGen, text, text3, text2);
      }
    }
  }

  public void processConstantPoolGen(
      ElementValueGen elementValueGen, ConstantPoolGen constantPoolGen) {
    if (elementValueGen instanceof AnnotationElementValueGen) {
      this.processConstantPoolGen(
          ((AnnotationElementValueGen) elementValueGen).getAnnotation(), constantPoolGen);
    } else {
      int value;
      if (elementValueGen instanceof ArrayElementValueGen) {
        List values;
        for (int value2 =
                value =
                    (values = ((ArrayElementValueGen) elementValueGen).getElementValues()).size()
                        - 1;
            value2 >= 0;
            value2 = value) {
          Object result = values.get(value);
          value--;
          this.processConstantPoolGen((ElementValueGen) result, constantPoolGen);
        }
      } else {
        if (elementValueGen instanceof ClassElementValueGen) {
          return;
        }
        if (elementValueGen instanceof EnumElementValueGen) {
          EnumElementValueGen enumElementValueGen;
          String typeIdx2 =
              (enumElementValueGen = (EnumElementValueGen) elementValueGen).getTypeIdx2();
          String text = null;
          if (typeIdx2.startsWith("L") && typeIdx2.endsWith(";")) {
            String text2;
            text = text2 = typeIdx2.substring(1, typeIdx2.length() - 1).replace('/', '.');
            String text3;
            if ((text3 =
                    ParameterNameTable.getLookupTable(
                            NamingContext.getParameterNameTable(this.namingContext))
                        .getText(text2))
                != null) {
              String text4 = "L" + text3.replace('.', '/') + ";";
              enumElementValueGen.setTypeIdx(constantPoolGen.addUtf8(text4));
            }
          }
          if (text != null) {
            String valueIdx2 = enumElementValueGen.getValueIdx2();
            String text5 = "" + text + "&" + valueIdx2 + "&" + typeIdx2;
            if (FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
                .matchesText(text5)) {
              String text6 =
                  FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
                      .getText(text5);
              enumElementValueGen.setValueIdx(constantPoolGen.addUtf8(text6));
              return;
            }
          }
        } else if (elementValueGen instanceof SimpleElementValueGen) {
        }
      }
    }
  }

  public void processClassGen3(ClassGen classGen) {
    String className = classGen.getClassName();
    Method[] methods;
    int value;
    for (int value2 = value = (methods = classGen.getMethods()).length - 1;
        value2 >= 0;
        value2 = --value) {
      Method method;
      Method method2 = method = methods[value];
      String text = method2.getText();
      String text2 = method2.getText2();
      String text3;
      if (!method2.isEnabled9()) {
        text3 = this.createText(classGen, text, text2);
      } else {
        text3 =
            MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
                .getText("" + className + "&" + text + "&" + text2);
      }
      boolean matches =
          Configuration.getRenamingOptions().matchesClassGen(this.classStorage, classGen);
      if (text3 == null && matches) {
        text3 =
            MethodNameTable.getLookupTable4(NamingContext.getMethodNameTable(this.namingContext))
                .getText("" + text + "&" + NameUtils.getText(text2));
      }
      if (text3 == null) {
        if (!Configuration.getRenamingOptions().isEnabled8() && !classGen.isEnabled2()) {
          MethodNameTable.getNameGenerator(NamingContext.getMethodNameTable(this.namingContext))
              .reset();
        }
        String text4;
        byte value3;
        ClassGen classGen2;
        do {
          text3 =
              MethodNameTable.getNameGenerator(NamingContext.getMethodNameTable(this.namingContext))
                  .nextName();
          text4 = NameUtils.getText(text2);
          if (!method.isEnabled9()) {
            value3 = 1;
            classGen2 = classGen;
          } else {
            value3 = 0;
            classGen2 = classGen;
          }
        } while (this.matchesEnabled(classGen, text3, text4, value3 != 0, classGen2.isEnabled5()));
      }
      MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
          .processText("" + className + "&" + text + "&" + text2, text3);
      if (classGen.isEnabled2()) {
        MethodNameTable.getLookupTable5(NamingContext.getMethodNameTable(this.namingContext))
            .processText("" + className + "&" + text, text3);
      }
      MethodNameTable.getLookupTable3(NamingContext.getMethodNameTable(this.namingContext))
          .processText("" + className + "&" + text3 + "&" + NameUtils.getText(text2), "&");
      if (!method.isEnabled9()) {
        this.processEnabled(
            classGen,
            text,
            text3,
            text2,
            classGen.isEnabled5(),
            NameUtils.matchesAccessFlags2(method));
      }
      if (Configuration.getRenamingOptions().isEnabled8() && !text3.equals(text)) {
        MethodNameTable.getLookupTable4(NamingContext.getMethodNameTable(this.namingContext))
            .processText("" + text + "&" + NameUtils.getText(text2), text3);
      }
    }
  }

  public String createText(String text) {
    String text2;
    String text3 = text;
    int value;
    if (text3.contains(">.")) {
      for (int value2 = value = 0; value2 < text3.length(); value2 = ++value) {
        if (text3.charAt(value) == 'L') {
          int value3;
          int value4 = value3 = value + 1;
          int value5 = 0;
          int value6;
          value2 = value6 = value3;
          while (true) {
            if (value2 >= text3.length()) {
              text2 = text3;
              break;
            }
            if (text3.charAt(value6) == '<') {
              value5++;
            }
            if (text3.charAt(value6) == '>') {
              value5--;
            }
            if (text3.charAt(value6) == ';' && value5 == 0) {
              value4 = value6;
              text2 = text3;
              break;
            }
            value2 = ++value6;
          }
          String text4;
          if ((text4 = text2.substring(value3, value4)).indexOf(46) > 0) {
            String text5;
            String text6 = text5 = text4;
            while (text6.contains(">")) {
              int value7 = text5.indexOf(62);
              int value8 = text5.lastIndexOf(60, value7);
              text6 = text5 = "" + text5.substring(0, value8) + text5.substring(value7 + 1);
            }
            String text7 = text5.replace('.', '$').replace('/', '.');
            String text8;
            if ((text8 =
                        ParameterNameTable.getLookupTable(
                                NamingContext.getParameterNameTable(this.namingContext))
                            .getText(text7))
                    != null
                && !text8.equals(text7)) {
              text3 =
                  ""
                      + text3.substring(0, value3)
                      + text8.replace('.', '/')
                      + text3.substring(value3 + text4.length());
            }
          }
        }
      }
    }
    int value9;
    for (int value10 = value9 = text3.length() - 1; value10 >= 0; value10 = --value9) {
      if (text3.charAt(value9) == 'L') {
        int value11 = text3.indexOf(59, value9);
        int value12 = text3.indexOf(60, value9);
        if (value11 == -1) {
          value11 = value12;
        }
        if (value12 != -1 && value12 < value11) {
          value11 = value12;
        }
        if (value11 > value9) {
          String text9 = text3.substring(value9 + 1, value11).replace('/', '.');
          String text10;
          if ((text10 =
                      ParameterNameTable.getLookupTable(
                              NamingContext.getParameterNameTable(this.namingContext))
                          .getText(text9))
                  != null
              && !text10.equals(text9)) {
            text3 =
                ""
                    + text3.substring(0, value9 + 1)
                    + text10.replace('.', '/')
                    + text3.substring(value9 + text9.length() + 1);
          }
        }
      }
    }
    return text3;
  }

  public void processClassGen4(ClassGen classGen) {
    int value;
    Field[] fields;
    for (int value2 = value = (fields = classGen.getFields()).length - 1;
        value2 >= 0;
        value2 = --value) {
      Field field = fields[value];
      String text =
          FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
              .getText(
                  "" + classGen.getClassName() + "&" + field.getText() + "&" + field.getText2());
      Type type =
          NameUtils.getType(
              field.getType(),
              ParameterNameTable.getLookupTable(
                  NamingContext.getParameterNameTable(this.namingContext)));
      boolean matches;
      if (!(matches =
              Configuration.getMemberOrderOptions()
                  .matchesField(this.classStorage, classGen, field))
          && !field.isEnabled9()
          && (ParameterNameTable.getLookupTable2(
                      NamingContext.getParameterNameTable(this.namingContext))
                  .matchesText(classGen.getClassName())
              || Configuration.getRenamingOptions().isForceDefaultPackageEnabled())) {
        matches = true;
      }
      if (!text.equals(field.getText())
          || type != null
          || matches
          || field.getAnnotationEntries().length > 0) {
        FieldGen fieldGen = new FieldGen(field, classGen.getConstantPool());
        if (!text.equals(field.getText())) {
          fieldGen.setName(text);
          RenameStatistics.count2++;
        }
        if (type != null) {
          fieldGen.setType(type);
          FieldNameTable.getLookupTable2(NamingContext.getFieldNameTable(this.namingContext))
              .processText(field.getText2(), fieldGen.getText());
        }
        if (matches) {
          NameUtils.processAccessFlags(fieldGen);
        }
        this.processConstantPoolGen(fieldGen.getAnnotationEntryGens(), classGen.getConstantPool());
        classGen.replaceField(field, fieldGen.getField());
      }
    }
  }

  public void update3() {
    ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext))
        .processText("", "");
    Iterator iterator2;
    Iterator iterator3 = iterator2 = this.classHierarchy.getValues().iterator();
    while (iterator3.hasNext()) {
      ClassGen classGen = (ClassGen) iterator2.next();
      if (Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen)) {
        iterator3 = iterator2;
      } else {
        this.processClassGen6(classGen);
        this.processClassGen3(classGen);
        this.processClassGen2(classGen);
        iterator3 = iterator2;
      }
    }
    if (Configuration.getRenamingOptions().isClassesNamingEnabled()) {
      this.update4();
    }
  }

  public void processClassGen5(ClassGen classGen) {
    Method[] methods;
    int value;
    for (int value2 = value = (methods = classGen.getMethods()).length - 1;
        value2 >= 0;
        value2 = value) {
      Method method;
      MethodGen methodGen =
          MethodFactory.createMethodGen(
              method = methods[value],
              classGen.getClassName(),
              classGen.getConstantPool(),
              classGen.getConstantPool().getConstantPool());
      this.processMethodGen(classGen, methodGen);
      this.nameMappingEntry.processMethodGen(classGen.getClassName(), methodGen);
      String text =
          MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
              .getText(
                  "" + classGen.getClassName() + "&" + method.getText() + "&" + method.getText2());
      if (!methodGen.getName().equals(text)) {
        methodGen.setName(text);
        RenameStatistics.count++;
      }
      byte value3 = 0;
      Type[] types;
      int value4;
      for (int value5 = value4 = (types = methodGen.getArgumentTypes()).length - 1;
          value5 >= 0;
          value5 = --value4) {
        Type type;
        if ((type =
                NameUtils.getType(
                    types[value4],
                    ParameterNameTable.getLookupTable(
                        NamingContext.getParameterNameTable(this.namingContext))))
            != null) {
          methodGen.setArgumentType(value4, type);
          value3 = 1;
        }
      }
      Type type2;
      if ((type2 =
              NameUtils.getType(
                  methodGen.getReturnType(),
                  ParameterNameTable.getLookupTable(
                      NamingContext.getParameterNameTable(this.namingContext))))
          != null) {
        methodGen.setReturnType(type2);
        value3 = 1;
      }
      if (value3 != 0) {
        MethodNameTable.getLookupTable(NamingContext.getMethodNameTable(this.namingContext))
            .processText(method.getText2(), methodGen.getText());
      }
      if (Configuration.getMemberOrderOptions().matchesMethod(this.classStorage, classGen, method)
          || !method.isEnabled9()
              && (ParameterNameTable.getLookupTable2(
                          NamingContext.getParameterNameTable(this.namingContext))
                      .matchesText(classGen.getClassName())
                  || Configuration.getRenamingOptions().isForceDefaultPackageEnabled())) {
        NameUtils.processAccessFlags(methodGen);
      }
      this.processConstantPoolGen(methodGen.getAnnotationEntryGens(), classGen.getConstantPool());
      int value6;
      for (int value7 = value6 = methodGen.getArgumentTypes().length - 1;
          value7 >= 0;
          value7 = value6) {
        List annotationsOnParameter = methodGen.getAnnotationsOnParameter(value6);
        value6--;
        this.processConstantPoolGen(annotationsOnParameter, classGen.getConstantPool());
      }
      Method method2 = method;
      value--;
      classGen.replaceMethod(method2, methodGen.getMethod());
    }
  }

  public void processText(ClassGen classGen, String text, String text2, String text3) {
    Vector vector;
    int value;
    for (int value2 =
            value = (vector = this.classHierarchy.getValues(classGen.getClassName())).size() - 1;
        value2 >= 0;
        value2 = value) {
      ClassGen classGen2;
      ClassGen classGen3 = classGen2 = (ClassGen) vector.get(value);
      FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
          .processText("" + classGen2.getClassName() + "&" + text + "&" + text3, text2);
      FieldNameTable.getLookupTable3(NamingContext.getFieldNameTable(this.namingContext))
          .processText("" + classGen2.getClassName() + "&" + text2 + "&" + text3, "&");
      value--;
      this.processText(classGen3, text, text2, text3);
    }
  }

  public static NamingContext getNamingContext(NameRenamer nameRenamer) {
    return nameRenamer.namingContext;
  }

  public void processClassGen6(ClassGen classGen) {
    String className = classGen.getClassName();
    if (!ParameterNameTable.getLookupTable(NamingContext.getParameterNameTable(this.namingContext))
        .matchesText(className)) {
      String text2 = NameUtils.getText2(className);
      String text = this.createText2(text2);
      if (Configuration.getRenamingOptions().isText()
          && (!text2.equals(text)
              || Configuration.getRenamingOptions().isForceDefaultPackageEnabled())) {
        text = Configuration.getRenamingOptions().getDefaultPackage();
        ParameterNameTable.getLookupTable2(NamingContext.getParameterNameTable(this.namingContext))
            .processText(className, "&");
      }
      if (text.length() > 0) {
        text = "" + text + ".";
      }
      if (!Configuration.getRenamingOptions().getNameGenerationOptions().isClassesNamingEnabled()) {
        ParameterNameTable.getNameGenerator(NamingContext.getParameterNameTable(this.namingContext))
            .reset();
      }
      String text3;
      do {
        text3 =
            ""
                + text
                + ParameterNameTable.getNameGenerator(
                        NamingContext.getParameterNameTable(this.namingContext))
                    .nextName();
      } while (ParameterNameTable.getLookupTable3(
                  NamingContext.getParameterNameTable(this.namingContext))
              .matchesText(text3)
          || this.classStorage.getClassGen(text3) != null);
      ParameterNameTable.getLookupTable(NamingContext.getParameterNameTable(this.namingContext))
          .processText(className, text3);
      ParameterNameTable.getLookupTable3(NamingContext.getParameterNameTable(this.namingContext))
          .processText(text3, className);
    }
  }

  public void processClassGen7(ClassGen classGen) {
    String className = classGen.getClassName();
    Field[] fields;
    int value;
    for (int value2 = value = (fields = classGen.getFields()).length - 1;
        value2 >= 0;
        value2 = --value) {
      Field field = fields[value];
      if (!Configuration.getRenamingOptions()
          .getKeepNamesOptions()
          .matchesField(this.classStorage, classGen, field)) {
        String text = field.getText();
        String text2 = field.getText2();
        if (Configuration.getRenamingOptions().getNameGenerationOptions().isFieldsNamingEnabled()) {
          MainNameGenerator.processText2(text);
        }
        if (!FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
            .matchesText("" + className + "&" + text + "&" + text2)) {
          FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
              .processText("" + className + "&" + text + "&" + text2, text);
          FieldNameTable.getLookupTable3(NamingContext.getFieldNameTable(this.namingContext))
              .processText("" + className + "&" + text + "&" + text2, "&");
          if (!field.isEnabled9()) {
            this.processText(classGen, text, text, text2);
          }
        } else if (!field.isEnabled9()) {
          this.processText(
              classGen,
              text,
              FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
                  .getText("" + className + "&" + text + "&" + text2),
              text2);
        }
      }
    }
    ClassGen classGen2 = classGen;
    do {
      String superclassName = classGen2.getSuperclassName();
      int value3;
      if ((classGen2 = this.classStorage.getClassGen(superclassName)) != null) {
        Field[] fields2;
        for (int value4 = value3 = (fields2 = classGen2.getFields()).length - 1;
            value4 >= 0;
            value4 = --value3) {
          Field field2 = fields2[value3];
          if (!Configuration.getRenamingOptions()
              .getKeepNamesOptions()
              .matchesField(this.classStorage, classGen, field2)) {
            String text3 = field2.getText();
            String text22 = field2.getText2();
            if (Configuration.getRenamingOptions()
                .getNameGenerationOptions()
                .isFieldsNamingEnabled()) {
              MainNameGenerator.processText2(text3);
            }
            if (!FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
                .matchesText("" + superclassName + "&" + text3 + "&" + text22)) {
              FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
                  .processText("" + superclassName + "&" + text3 + "&" + text22, text3);
              FieldNameTable.getLookupTable3(NamingContext.getFieldNameTable(this.namingContext))
                  .processText("" + superclassName + "&" + text3 + "&" + text22, "&");
              if (!field2.isEnabled9()) {
                this.processText(classGen2, text3, text3, text22);
              }
            } else if (!field2.isEnabled9()) {
              this.processText(
                  classGen2,
                  text3,
                  FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext))
                      .getText("" + superclassName + "&" + text3 + "&" + text22),
                  text22);
            }
          }
        }
      }
    } while (classGen2 != null);
  }

  public String createText(ClassGen classGen, String text, String text2) {
    String text3;
    if ((text3 =
            MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
                .getText("" + classGen.getClassName() + "&" + text + "&" + text2))
        != null) {
      return text3;
    }
    Vector vector;
    int value;
    for (int value2 =
            value = (vector = this.classHierarchy.getValues(classGen.getClassName())).size() - 1;
        value2 >= 0;
        value2 = --value) {
      String text4;
      if ((text4 = this.createText((ClassGen) vector.get(value), text, text2)) != null) {
        return text4;
      }
    }
    return null;
  }

  public void update4() {
    for (String text :
        ((Iterable<String>)
            ((Iterable<?>)
                ParameterNameTable.getLookupTable(
                        NamingContext.getParameterNameTable(this.namingContext))
                    .getEntries2()))) {
      String text2 =
          ParameterNameTable.getLookupTable(NamingContext.getParameterNameTable(this.namingContext))
              .getText(text);
      String text3 = this.createText3(text);
      if (!text2.equals(text3)) {
        ParameterNameTable.getLookupTable(NamingContext.getParameterNameTable(this.namingContext))
            .processText(text, text3);
        ParameterNameTable.getLookupTable3(NamingContext.getParameterNameTable(this.namingContext))
            .processText(text2);
        ParameterNameTable.getLookupTable3(NamingContext.getParameterNameTable(this.namingContext))
            .processText(text3, text);
      }
    }
  }

  public void rename() throws ObfuscationAbortException {
    this.classHierarchy.update3();
    this.update();
    this.update3();
    this.update2();
    this.update6();
    this.update7();
    Configuration.getMappingOptions().renamingLog =
        new RenamingLog(
            ParameterNameTable.getLookupTable(
                NamingContext.getParameterNameTable(this.namingContext)),
            MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext)),
            FieldNameTable.getLookupTable(NamingContext.getFieldNameTable(this.namingContext)),
            this.nameMappingEntry.getValues2(),
            this.nameMappingEntry.getValues(),
            this.nameMappingEntry.getLookupTable2(),
            MethodNameTable.getLookupTable5(NamingContext.getMethodNameTable(this.namingContext)));
    Configuration.getMappingOptions().lookupTable =
        ParameterNameTable.getLookupTable3(NamingContext.getParameterNameTable(this.namingContext));
    Configuration.getMappingOptions().lookupTable2 =
        ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext));
  }

  public void processConstantPoolGen(
      AnnotationEntryGen[] annotationEntryGens, ConstantPoolGen constantPoolGen) {
    if (annotationEntryGens != null) {
      int value;
      for (int value2 = value = annotationEntryGens.length - 1; value2 >= 0; value2 = value) {
        int value3 = value;
        value--;
        this.processConstantPoolGen(annotationEntryGens[value3], constantPoolGen);
      }
    }
  }

  public void processMethodGen(ClassGen classGen, MethodGen methodGen) {
    int value;
    int value2 =
        value =
            Configuration.getRenamingOptions()
                .getLocalVariableOptions()
                .calculateCount(this.classStorage, classGen, methodGen);
    Configuration.getRenamingOptions().getLocalVariableOptions();
    if (value2 == 4) {
      methodGen.getVariable_vec();
    } else {
      Configuration.getRenamingOptions().getLocalVariableOptions();
      int value3 = value == 3 ? 1 : 0;
      LocalVariableGen[] localVariables = methodGen.getLocalVariables();
      int value4 = methodGen.getArgumentNames().length;
      if (!methodGen.isEnabled11()) {
        value4++;
      }
      Type[] types;
      int value5 = (types = methodGen.getArgumentTypes()).length;
      int value6;
      for (int value7 = value6 = 0; value7 < value5; value7 = ++value6) {
        Type type;
        if ((type = types[value6]) == Type.LONG || type == Type.DOUBLE) {
          value4++;
        }
      }
      PackageNameTable.getNameGenerator(NamingContext.getPackageNameTable(this.namingContext))
          .reset();
      LowercaseNameGenerator lowercaseNameGenerator = null;
      if (Configuration.getObfuscationOptions().isEnabled3()) {
        lowercaseNameGenerator = new LowercaseNameGenerator();
      }
      int value8;
      for (int value9 = value8 = localVariables.length - 1; value9 >= 0; value9 = --value8) {
        LocalVariableGen localVariableGen;
        Type type2;
        if ((type2 =
                NameUtils.getType(
                    (localVariableGen = localVariables[value8]).getType(),
                    ParameterNameTable.getLookupTable(
                        NamingContext.getParameterNameTable(this.namingContext))))
            != null) {
          localVariableGen.setType(type2);
        }
        try {
          if (methodGen.getAnnotationsOnParameter(localVariableGen.getIndex()) != null) {
            continue;
          }
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
        }
        Configuration.getRenamingOptions().getLocalVariableOptions();
        if (value == 5) {
          if (localVariableGen.getIndex() >= value4) {
            localVariableGen.setName(
                Configuration.getObfuscationOptions().isEnabled3()
                    ? lowercaseNameGenerator.nextName()
                    : Configuration.getRenamingOptions().getNameGenerationOptions().getText());
          }
        } else {
          Configuration.getRenamingOptions().getLocalVariableOptions();
          if (value == 6) {
            if (localVariableGen.getIndex() < value4) {
              localVariableGen.setName(
                  Configuration.getObfuscationOptions().isEnabled3()
                      ? lowercaseNameGenerator.nextName()
                      : Configuration.getRenamingOptions().getNameGenerationOptions().getText());
            } else {
              localVariableGen.setName(
                  Configuration.getObfuscationOptions().isEnabled3()
                      ? lowercaseNameGenerator.nextName()
                      : Configuration.getRenamingOptions().getNameGenerationOptions().getText());
            }
          } else {
            Configuration.getRenamingOptions().getLocalVariableOptions();
            if (value == 1) {
              localVariableGen.setName(
                  Configuration.getRenamingOptions().getNameGenerationOptions().getText());
            } else if (value3 == 0) {
              localVariableGen.setName(
                  PackageNameTable.getNameGenerator(
                          NamingContext.getPackageNameTable(this.namingContext))
                      .nextName());
            }
          }
        }
      }
    }
  }

  public void update6() {
    Iterator iterator2 = this.classHierarchy.getValues().iterator();
    label35:
    while (true) {
      for (Iterator iterator3 = iterator2; iterator3.hasNext(); iterator3 = iterator2) {
        ClassGen classGen = (ClassGen) iterator2.next();
        if (!Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen)) {
          this.processClassGen(classGen);
          ConstantPoolGen constantPool = classGen.getConstantPool();
          this.processConstantPoolGen(classGen.getAttributes(), constantPool);
          int value;
          Method[] methods;
          for (int value2 = value = (methods = classGen.getMethods()).length - 1;
              value2 >= 0;
              value2 = value) {
            Method method = methods[value];
            value--;
            this.processConstantPoolGen(method.getAttributes(), constantPool);
          }
          Field[] fields;
          int value3;
          for (int value4 = value3 = (fields = classGen.getFields()).length - 1;
              value4 >= 0;
              value4 = value3) {
            Field field = fields[value3];
            value3--;
            this.processConstantPoolGen(field.getAttributes(), constantPool);
          }
          continue label35;
        }
      }
      return;
    }
  }

  public static ClassStorage getClassStorage(NameRenamer nameRenamer) {
    return nameRenamer.classStorage;
  }

  public boolean matchesClassGen(ClassGen classGen) {
    int value;
    Method[] methods;
    for (int value2 = value = (methods = classGen.getMethods()).length - 1;
        value2 >= 0;
        value2 = --value) {
      if (methods[value].isEnabled14()) {
        return true;
      }
    }
    return false;
  }

  public void processConstantPoolGen(
      AnnotationEntryGen annotationEntryGen, ConstantPoolGen constantPoolGen) {
    String text;
    AnnotationEntryGen annotationEntryGen2;
    label29:
    {
      String text2 = annotationEntryGen.getText2();
      text = null;
      if (text2.startsWith("L") && text2.endsWith(";")) {
        String text3;
        text = text3 = text2.substring(1, text2.length() - 1).replace('/', '.');
        String text4;
        if ((text4 =
                ParameterNameTable.getLookupTable(
                        NamingContext.getParameterNameTable(this.namingContext))
                    .getText(text3))
            != null) {
          String text5 = "L" + text4.replace('.', '/') + ";";
          annotationEntryGen2 = annotationEntryGen;
          annotationEntryGen.setTypeIndex(constantPoolGen.addUtf8(text5));
          break label29;
        }
        text =
            ParameterNameTable.getLookupTable3(
                    NamingContext.getParameterNameTable(this.namingContext))
                .getText(text);
      }
      annotationEntryGen2 = annotationEntryGen;
    }
    List values;
    int value;
    for (int value2 = value = (values = annotationEntryGen2.getValues()).size() - 1;
        value2 >= 0;
        value2 = value) {
      ElementValuePairGen elementValuePairGen = (ElementValuePairGen) values.get(value);
      if (text != null) {
        String text6 = elementValuePairGen.getText();
        String text7 = "" + text + "&" + text6;
        if (MethodNameTable.getLookupTable5(NamingContext.getMethodNameTable(this.namingContext))
            .matchesText(text7)) {
          String text8 =
              MethodNameTable.getLookupTable5(NamingContext.getMethodNameTable(this.namingContext))
                  .getText(text7);
          elementValuePairGen.setNameIdx(constantPoolGen.addUtf8(text8));
        }
      }
      value--;
      this.processConstantPoolGen(elementValuePairGen.getValue(), constantPoolGen);
    }
  }

  public void processConstantPoolGen(Attribute[] attributes, ConstantPoolGen constantPoolGen) {
    int value;
    for (int value2 = value = attributes.length - 1; value2 >= 0; value2 = --value) {
      Attribute attribute;
      if ((attribute = attributes[value]) instanceof Code) {
        Code code = (Code) attribute;
        this.processConstantPoolGen(code.getAttributes(), constantPoolGen);
      } else if (!(attribute instanceof ConstantValue)
          && !(attribute instanceof Deprecated)
          && !(attribute instanceof ExceptionTable)) {
        if (attribute instanceof InnerClasses) {
          InnerClass[] innerClasss;
          if ((innerClasss = ((InnerClasses) attribute).getInnerClasses()).length > 0) {
            ConstantPool constantPool = constantPoolGen.getConstantPool();
            int value3;
            for (int value4 = value3 = innerClasss.length - 1; value4 >= 0; value4 = --value3) {
              InnerClass innerClass = innerClasss[value3];
              if (!constantPool
                  .createText(innerClass.getInner_class_index(), (byte) 7)
                  .contains("$")) {
                innerClass.setInner_name_index(0);
              }
            }
          }
        } else if (!(attribute instanceof LineNumberTable)
            && !(attribute instanceof LocalVariableTable)
            && !(attribute instanceof PMGClass)) {
          if (attribute instanceof Signature) {
            Signature signature = (Signature) attribute;
            String text = ((Signature) attribute).getText();
            String text2 = this.createText(text);
            signature.setSignatureIndex(constantPoolGen.addUtf8(text2));
          } else if (!(attribute instanceof SourceFile)
              && !(attribute instanceof StackMap)
              && !(attribute instanceof Synthetic)) {
            if (attribute instanceof EnclosingMethod) {
              EnclosingMethod enclosingMethod;
              if ((enclosingMethod = (EnclosingMethod) attribute).getMethodIndex() != 0) {
                ConstantPool constantPool2 = constantPoolGen.getConstantPool();
                String text3 =
                    enclosingMethod.getEnclosingClass().getBytes(constantPool2).replace('/', '.');
                String text4;
                if ((text4 =
                        ParameterNameTable.getLookupTable3(
                                NamingContext.getParameterNameTable(this.namingContext))
                            .getText(text3))
                    != null) {
                  text3 = text4;
                }
                ConstantNameAndType constantNameAndType;
                if ((constantNameAndType = enclosingMethod.getEnclosingMethod()) != null) {
                  String text5 = constantNameAndType.getText(constantPool2);
                  String text22 = constantNameAndType.getText2(constantPool2);
                  String text6 =
                      MethodNameTable.getLookupTable2(
                              NamingContext.getMethodNameTable(this.namingContext))
                          .getText("" + text3 + "&" + text5 + "&" + text22);
                  String text7 =
                      MethodNameTable.getLookupTable(
                              NamingContext.getMethodNameTable(this.namingContext))
                          .getText(text22);
                  if (text6 != null && !text6.equals(text5) || text7 != null) {
                    if (text6 == null) {
                      text6 = text5;
                    }
                    if (text7 == null) {
                      text7 = text22;
                    }
                    int value5 = constantPoolGen.addNameAndType(text6, text7);
                    enclosingMethod.setMethodIndex(value5);
                  }
                }
              }
            } else if (!(attribute instanceof NestHost)
                && !(attribute instanceof NestMembers)
                && attribute instanceof Unknown) {
            }
          }
        }
      }
    }
  }

  public static LookupTable getLookupTable() {
    return lookupTable;
  }

  public boolean matchesMethod(String text, Method method) {
    return method.isEnabled14()
            || this.classHierarchy.matchesText(text, method.getText(), method.getText2())
        ? true
        : "<init>".equals(method.getText())
            || "<clinit>".equals(method.getText())
            || "main".equals(method.getText())
                && "([Ljava/lang/String;)V".equals(method.getText2());
  }

  public void processClassGen8(ClassGen classGen) {
    String className = classGen.getClassName();
    int value;
    Method[] methods;
    for (int value2 = value = (methods = classGen.getMethods()).length - 1;
        value2 >= 0;
        value2 = --value) {
      Method method = methods[value];
      if (this.matchesMethod(className, method)
          || !Configuration.getRenamingOptions()
              .getKeepNamesOptions()
              .matchesMethod(this.classStorage, classGen, method)) {
        String text = method.getText();
        String text2 = method.getText2();
        if (Configuration.getRenamingOptions()
            .getNameGenerationOptions()
            .isMethodsNamingEnabled()) {
          MainNameGenerator.processText(text);
        }
        if (!MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
            .matchesText("" + className + "&" + text + "&" + text2)) {
          MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
              .processText("" + className + "&" + text + "&" + text2, text);
          MethodNameTable.getLookupTable3(NamingContext.getMethodNameTable(this.namingContext))
              .processText("" + className + "&" + text + "&" + NameUtils.getText(text2), "&");
          if (!method.isEnabled9()) {
            this.processEnabled(
                classGen,
                text,
                text,
                text2,
                classGen.isEnabled5(),
                NameUtils.matchesAccessFlags2(method));
          }
        } else if (!method.isEnabled9()) {
          this.processEnabled(
              classGen,
              text,
              MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
                  .getText("" + className + "&" + text + "&" + text2),
              text2,
              classGen.isEnabled5(),
              NameUtils.matchesAccessFlags2(method));
        }
      }
    }
    ClassGen classGen2 = classGen;
    do {
      String superclassName = classGen2.getSuperclassName();
      int value3;
      if ((classGen2 = this.classStorage.getClassGen(superclassName)) != null) {
        Method[] methods2;
        for (int value4 = value3 = (methods2 = classGen2.getMethods()).length - 1;
            value4 >= 0;
            value4 = --value3) {
          Method method2 = methods2[value3];
          if (!this.matchesMethod(superclassName, method2)
              && !Configuration.getRenamingOptions()
                  .getKeepNamesOptions()
                  .matchesMethod(this.classStorage, classGen, method2)) {
            String text3 = method2.getText();
            String text22 = method2.getText2();
            if (Configuration.getRenamingOptions()
                .getNameGenerationOptions()
                .isMethodsNamingEnabled()) {
              MainNameGenerator.processText(text3);
            }
            if (!MethodNameTable.getLookupTable2(
                    NamingContext.getMethodNameTable(this.namingContext))
                .matchesText("" + superclassName + "&" + text3 + "&" + text22)) {
              MethodNameTable.getLookupTable2(NamingContext.getMethodNameTable(this.namingContext))
                  .processText("" + superclassName + "&" + text3 + "&" + text22, text3);
              MethodNameTable.getLookupTable3(NamingContext.getMethodNameTable(this.namingContext))
                  .processText(
                      "" + superclassName + "&" + text3 + "&" + NameUtils.getText(text22), "&");
              if (!method2.isEnabled9()) {
                this.processEnabled(
                    classGen2,
                    text3,
                    text3,
                    text22,
                    classGen2.isEnabled5(),
                    NameUtils.matchesAccessFlags2(method2));
              }
            } else if (!method2.isEnabled9()) {
              this.processEnabled(
                  classGen2,
                  text3,
                  MethodNameTable.getLookupTable2(
                          NamingContext.getMethodNameTable(this.namingContext))
                      .getText("" + superclassName + "&" + text3 + "&" + text22),
                  text22,
                  classGen2.isEnabled5(),
                  NameUtils.matchesAccessFlags2(method2));
            }
          }
        }
      }
    } while (classGen2 != null);
  }

  public String createText2(String text) {
    String text2;
    if ((text2 =
            ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext))
                .getText(text))
        != null) {
      return text2;
    }
    if (Configuration.getRenamingOptions().isEnabled()) {
      String text3 = text;
      LookupTable lookupTable2 =
          ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext));
      String text4 = text3;
      lookupTable2.processText(text3, text3);
      ClassNameTable.getLookupTable(NamingContext.getClassNameTable(this.namingContext))
          .processText(text3, "&");
      while (text4.lastIndexOf(46) > 0) {
        text3 = text3.substring(0, text3.lastIndexOf(46));
        lookupTable2 =
            ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext));
        text4 = text3;
        lookupTable2.processText(text3, text3);
        ClassNameTable.getLookupTable(NamingContext.getClassNameTable(this.namingContext))
            .processText(text3, "&");
      }
      return ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext))
          .getText(text);
    } else {
      StringTokenizer stringTokenizer = new StringTokenizer(text, ".");
      String text5 = "";
      label38:
      while (true) {
        StringTokenizer stringTokenizer2 = stringTokenizer;
        while (stringTokenizer2.hasMoreTokens()) {
          if (text5.length() > 0) {
            text5 = "" + text5 + ".";
          }
          text5 = "" + text5 + stringTokenizer.nextToken();
          if (ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext))
              .matchesText(text5)) {
            continue label38;
          }
          ClassNameTable.getNameGenerator(NamingContext.getClassNameTable(this.namingContext))
              .reset();
          String text22 = NameUtils.getText2(text5);
          String text6;
          if ((text6 =
                      ClassNameTable.getLookupTable2(
                              NamingContext.getClassNameTable(this.namingContext))
                          .getText(text22))
                  .length()
              > 0) {
            text6 = "" + text6 + ".";
          }
          String text7;
          do {
            text7 =
                ""
                    + text6
                    + ClassNameTable.getNameGenerator(
                            NamingContext.getClassNameTable(this.namingContext))
                        .nextName();
          } while (ClassNameTable.getLookupTable(
                  NamingContext.getClassNameTable(this.namingContext))
              .matchesText(text7));
          stringTokenizer2 = stringTokenizer;
          ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext))
              .processText(text5, text7);
          ClassNameTable.getLookupTable(NamingContext.getClassNameTable(this.namingContext))
              .processText(text7, "&");
        }
        return ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext))
            .getText(text);
      }
    }
  }

  public boolean matchesEnabled2(ClassGen classGen, String text, String text2, boolean matches) {
    if (MethodNameTable.getLookupTable3(NamingContext.getMethodNameTable(this.namingContext))
        .matchesText("" + classGen.getClassName() + "&" + text + "&" + text2)) {
      return true;
    }
    if (!matches) {
      return false;
    }
    int value;
    Vector vector;
    for (int value2 =
            value = (vector = this.classHierarchy.getValues(classGen.getClassName())).size() - 1;
        value2 >= 0;
        value2 = --value) {
      if (this.matchesEnabled2((ClassGen) vector.get(value), text, text2, matches)) {
        return true;
      }
    }
    return false;
  }

  public String createText3(String text) {
    if (!text.contains("$")) {
      return ParameterNameTable.getLookupTable(
              NamingContext.getParameterNameTable(this.namingContext))
          .getText(text);
    }
    String text2;
    if ((text2 =
            ParameterNameTable.getLookupTable(
                    NamingContext.getParameterNameTable(this.namingContext))
                .getText(text))
        == null) {
      return this.createText3(text.substring(0, text.lastIndexOf(36)));
    }
    int value;
    if ((value = text2.lastIndexOf(46)) != -1) {
      text2 = text2.substring(value + 1);
    }
    int value2;
    if ((value2 = text2.lastIndexOf(36)) != -1) {
      text2 = text2.substring(value2 + 1);
    }
    return text2.charAt(0) >= '0' && text2.charAt(0) <= '9'
        ? ("" + this.createText3(text.substring(0, text.lastIndexOf(36))) + '$' + '0' + text2)
        : ("" + this.createText3(text.substring(0, text.lastIndexOf(36))) + '$' + text2);
  }

  public void processClassGen9(ClassGen classGen) {
    if (this.matchesClassGen(classGen)
        || !Configuration.getRenamingOptions()
            .getKeepNamesOptions()
            .matchesClassGen(this.classStorage, classGen)) {
      String className = classGen.getClassName();
      byte value = 0;
      if (ParameterNameTable.getLookupTable(NamingContext.getParameterNameTable(this.namingContext))
              .matchesText(className)
          && !className.equals(
              ParameterNameTable.getLookupTable(
                      NamingContext.getParameterNameTable(this.namingContext))
                  .getText(className))) {
        value = 1;
      }
      if (value == 0) {
        ParameterNameTable.getLookupTable(NamingContext.getParameterNameTable(this.namingContext))
            .processText(className, className);
        ParameterNameTable.getLookupTable3(NamingContext.getParameterNameTable(this.namingContext))
            .processText(className, className);
        String text2 = NameUtils.getText2(className);
        String text;
        if (Configuration.getRenamingOptions().isText2()
            && (text =
                    ClassNameTable.getLookupTable2(
                            NamingContext.getClassNameTable(this.namingContext))
                        .getText(text2))
                != null
            && !text.equals(text2)) {
          if (lookupTable.getText(text) == null) {
            Log.warn("Package '" + text + "' should be un-renamed.");
          }
          lookupTable.processText(text, text2);
        }
        ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext))
            .processText(text2, text2);
        ClassNameTable.getLookupTable(NamingContext.getClassNameTable(this.namingContext))
            .processText(text2, "&");
        for (String text3 = text2; text3.lastIndexOf(46) > 0; text3 = text2) {
          text2 = text2.substring(0, text2.lastIndexOf(46));
          String text4;
          if (Configuration.getRenamingOptions().isText2()
              && (text4 =
                      ClassNameTable.getLookupTable2(
                              NamingContext.getClassNameTable(this.namingContext))
                          .getText(text2))
                  != null
              && !text4.equals(text2)) {
            if (lookupTable.getText(text4) == null) {
              Log.warn("Package '" + text4 + "' should be un-renamed.");
            }
            lookupTable.processText(text4, text2);
          }
          ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext))
              .processText(text2, text2);
          ClassNameTable.getLookupTable(NamingContext.getClassNameTable(this.namingContext))
              .processText(text2, "&");
        }
      } else {
        String text22 = NameUtils.getText2(className);
        String text23 =
            NameUtils.getText2(
                ParameterNameTable.getLookupTable(
                        NamingContext.getParameterNameTable(this.namingContext))
                    .getText(className));
        if (!Configuration.getRenamingOptions().isText()
            || !text23.equals(Configuration.getRenamingOptions().getDefaultPackage())) {
          if (new StringTokenizer(text22, ".").countTokens()
              == new StringTokenizer(text23, ".").countTokens()) {
            ClassNameTable.getLookupTable2(NamingContext.getClassNameTable(this.namingContext))
                .processText(text22, text23);
            ClassNameTable.getLookupTable(NamingContext.getClassNameTable(this.namingContext))
                .processText(text23, "&");
            String text5 = text22;
            while (text5.lastIndexOf(46) > 0) {
              text22 = text22.substring(0, text22.lastIndexOf(46));
              text23 = text23.substring(0, text23.lastIndexOf(46));
              LookupTable lookupTable2 =
                  ClassNameTable.getLookupTable2(
                      NamingContext.getClassNameTable(this.namingContext));
              text5 = text22;
              lookupTable2.processText(text22, text23);
              ClassNameTable.getLookupTable(NamingContext.getClassNameTable(this.namingContext))
                  .processText(text23, "&");
            }
          }
        }
      }
    }
  }

  public void update7() {
    Iterator iterator2;
    Iterator iterator3 = iterator2 = this.classHierarchy.getValues().iterator();
    while (iterator3.hasNext()) {
      ClassGen classGen = (ClassGen) iterator2.next();
      if (Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen)) {
        iterator3 = iterator2;
      } else {
        this.processClassGen10(classGen);
        iterator3 = iterator2;
      }
    }
  }

  public void processClassGen10(ClassGen classGen) {
    if (ParameterNameTable.getLookupTable2(NamingContext.getParameterNameTable(this.namingContext))
            .matchesText(classGen.getClassName())
        || Configuration.getRenamingOptions().isForceDefaultPackageEnabled()) {
      NameUtils.processAccessFlags(classGen);
    }
    if (Configuration.getObfuscationOptions().isFinalizeEnabled()
        && !classGen.isEnabled15()
        && !classGen.isEnabled5()
        && !classGen.isEnabled2()
        && !classGen.isEnabled10()
        && this.classHierarchy.getValues(classGen.getClassName()).isEmpty()) {
      classGen.processEnabled2(true);
    }
    String text =
        ParameterNameTable.getLookupTable(NamingContext.getParameterNameTable(this.namingContext))
            .getText(classGen.getClassName());
    if (!classGen.getClassName().equals(text)) {
      classGen.processText4(text);
      RenameStatistics.count3++;
    }
    this.nameMappingEntry.processClassGen(classGen);
    String[] texts;
    int value = (texts = classGen.getInterfaceNames()).length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
      String text2 = texts[value2];
      String text3;
      if ((text3 =
              ParameterNameTable.getLookupTable(
                      NamingContext.getParameterNameTable(this.namingContext))
                  .getText(text2))
          != null) {
        classGen.processText3(text2);
        classGen.processText(text3);
        classGen.getConstantPool().addClass(text3);
      } else {
        classGen.processText3(text2);
        classGen.processText(text2);
      }
    }
    this.processConstantPoolGen(classGen.getAnnotationEntries(), classGen.getConstantPool());
  }

  public boolean matchesEnabled(
      ClassGen classGen, String text, String text2, boolean matches, boolean matches2) {
    if (matches2) {
      ClassGen classGen2 = classGen;
      do {
        String superclassName = classGen2.getSuperclassName();
        if ((classGen2 = this.classStorage.getClassGen(superclassName)) != null
            && MethodNameTable.getLookupTable3(NamingContext.getMethodNameTable(this.namingContext))
                .matchesText("" + classGen2.getClassName() + "&" + text + "&" + text2)) {
          return true;
        }
      } while (classGen2 != null);
    }
    if (MethodNameTable.getLookupTable3(NamingContext.getMethodNameTable(this.namingContext))
        .matchesText("" + classGen.getClassName() + "&" + text + "&" + text2)) {
      return true;
    }
    if (!matches) {
      return false;
    }
    Vector vector;
    int value;
    for (int value2 =
            value = (vector = this.classHierarchy.getValues(classGen.getClassName())).size() - 1;
        value2 >= 0;
        value2 = --value) {
      if (this.matchesEnabled((ClassGen) vector.get(value), text, text2, matches, matches2)) {
        return true;
      }
    }
    return false;
  }
}
