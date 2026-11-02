package com.allatori.bytecode;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.AnnotationDefault;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.BootstrapMethod;
import com.allatori.lib.bcel.classfile.BootstrapMethods;
import com.allatori.lib.bcel.classfile.Code;
import com.allatori.lib.bcel.classfile.CodeException;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantCP;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantInvokeDynamic;
import com.allatori.lib.bcel.classfile.ConstantMethodHandle;
import com.allatori.lib.bcel.classfile.ConstantMethodType;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ConstantValue;
import com.allatori.lib.bcel.classfile.Deprecated;
import com.allatori.lib.bcel.classfile.ElementValue;
import com.allatori.lib.bcel.classfile.EnclosingMethod;
import com.allatori.lib.bcel.classfile.ExceptionTable;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.InnerClass;
import com.allatori.lib.bcel.classfile.InnerClasses;
import com.allatori.lib.bcel.classfile.LineNumberTable;
import com.allatori.lib.bcel.classfile.LocalVariable;
import com.allatori.lib.bcel.classfile.LocalVariableTable;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.MethodParameter;
import com.allatori.lib.bcel.classfile.MethodParameters;
import com.allatori.lib.bcel.classfile.NestHost;
import com.allatori.lib.bcel.classfile.NestMembers;
import com.allatori.lib.bcel.classfile.PMGClass;
import com.allatori.lib.bcel.classfile.RuntimeInvisibleAnnotations;
import com.allatori.lib.bcel.classfile.Signature;
import com.allatori.lib.bcel.classfile.SourceFile;
import com.allatori.lib.bcel.classfile.StackMap;
import com.allatori.lib.bcel.classfile.StackMapEntry;
import com.allatori.lib.bcel.classfile.StackMapTable;
import com.allatori.lib.bcel.classfile.StackMapTableEntry;
import com.allatori.lib.bcel.classfile.StackMapType;
import com.allatori.lib.bcel.classfile.Synthetic;
import com.allatori.lib.bcel.classfile.Unknown;
import com.allatori.lib.bcel.generic.AnnotationElementValueGen;
import com.allatori.lib.bcel.generic.AnnotationEntryGen;
import com.allatori.lib.bcel.generic.ArrayElementValueGen;
import com.allatori.lib.bcel.generic.CPInstruction;
import com.allatori.lib.bcel.generic.ClassElementValueGen;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.ElementValueGen;
import com.allatori.lib.bcel.generic.ElementValuePairGen;
import com.allatori.lib.bcel.generic.EnumElementValueGen;
import com.allatori.lib.bcel.generic.FieldGen;
import com.allatori.lib.bcel.generic.FieldGenOrMethodGen;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InstructionList;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.SimpleElementValueGen;
import com.allatori.model.ClassStorage;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class ConstantPoolEditor {

  public ClassGen classGen;

  public TreeSet treeSet;

  public int[] counts;

  public ClassStorage classStorage;

  public boolean matchesAnnotationEntryGen(AnnotationEntryGen annotationEntryGen) {
    if (annotationEntryGen.getText2().startsWith("Lcom/allatori/annotations/")) {
      this.treeSet.add(annotationEntryGen.getTypeIndex());
      return true;
    }
    String text;
    if (Configuration.getPackageRenamingOptions().isEnabled()
        && (text = annotationEntryGen.getText2()).length() > 2
        && text.charAt(0) == 'L'
        && text.charAt(text.length() - 1) == ';') {
      String text2 = text.substring(1, text.length() - 1).replace('/', '.');
      if (Configuration.getPackageRenamingOptions()
          .matchesText(this.classStorage, this.classGen, text2)) {
        this.treeSet.add(annotationEntryGen.getTypeIndex());
        return true;
      }
      String text3 = Configuration.getMappingOptions().lookupTable.getText(text2);
      if (Configuration.getPackageRenamingOptions()
          .matchesText(this.classStorage, this.classGen, text3)) {
        this.treeSet.add(annotationEntryGen.getTypeIndex());
        return true;
      }
    }
    return false;
  }

  public void processValues(List values) {
    if (values != null) {
      int value;
      for (int value2 = value = values.size() - 1; value2 >= 0; value2 = value) {
        int value3 = value;
        value--;
        this.processAnnotationEntryGen((AnnotationEntryGen) values.get(value3));
      }
    }
  }

  public void processAnnotationEntryGens(AnnotationEntryGen[] annotationEntryGens) {
    if (annotationEntryGens != null) {
      int value;
      for (int value2 = value = annotationEntryGens.length - 1; value2 >= 0; value2 = value) {
        int value3 = value;
        value--;
        this.processAnnotationEntryGen(annotationEntryGens[value3]);
      }
    }
  }

  public void processConstantPool(Attribute[] attributes, ConstantPool constantPool) {
    int value;
    for (int value2 = value = attributes.length - 1; value2 >= 0; value2 = --value) {
      Attribute attribute;
      Attribute attribute2 = attribute = attributes[value];
      attribute2.setConstantPool(constantPool);
      if (!(attribute2 instanceof LocalVariableTable)) {
        attribute.setName_index(this.counts[attribute.getName_index()]);
      }
      if (attribute instanceof Code) {
        Code code = (Code) attribute;
        this.processConstantPool(code.getAttributes(), constantPool);
        CodeException[] codeExceptions;
        int value3;
        for (int value4 = value3 = (codeExceptions = code.getExceptionTable()).length - 1;
            value4 >= 0;
            value4 = --value3) {
          int value5;
          if ((value5 = codeExceptions[value3].getCatch_type()) != 0) {
            codeExceptions[value3].setCatch_type(this.counts[value5]);
          }
        }
      } else if (attribute instanceof ConstantValue) {
        ConstantValue constantValue = (ConstantValue) attribute;
        constantValue.setConstantValueIndex(this.counts[constantValue.getConstantValueIndex()]);
      } else if (!(attribute instanceof Deprecated)) {
        if (attribute instanceof ExceptionTable) {
          ExceptionTable exceptionTable;
          int[] values;
          int value6;
          for (int value7 =
                  value6 =
                      (values =
                                  (exceptionTable = (ExceptionTable) attribute)
                                      .getExceptionIndexTable())
                              .length
                          - 1;
              value7 >= 0;
              value7 = value6) {
            int value8 = this.counts[values[value6]];
            int value9 = value6;
            int[] values2 = values;
            value6--;
            values2[value9] = value8;
          }
          exceptionTable.setExceptionIndexTable(values);
        } else {
          int value10;
          if (attribute instanceof InnerClasses) {
            InnerClass[] innerClasss;
            for (int value11 =
                    value10 =
                        (innerClasss = ((InnerClasses) attribute).getInnerClasses()).length - 1;
                value11 >= 0;
                value11 = value10) {
              innerClasss[value10].setInner_name_index(
                  this.counts[innerClasss[value10].getInner_name_index()]);
              innerClasss[value10].setInner_class_index(
                  this.counts[innerClasss[value10].getInner_class_index()]);
              InnerClass innerClass = innerClasss[value10];
              int[] values3 = this.counts;
              int outer_class_index = innerClasss[value10].getOuter_class_index();
              value10--;
              innerClass.setOuter_class_index(values3[outer_class_index]);
            }
          } else if (!(attribute instanceof LineNumberTable)) {
            if (attribute instanceof LocalVariableTable) {
              try {
                LocalVariable[] localVariables;
                int value12 =
                    (localVariables = ((LocalVariableTable) attribute).getLocalVariableTable())
                            .length
                        - 1;
                while (value12 >= 0) {
                  localVariables[value12].setName_index(
                      this.counts[localVariables[value12].getName_index()]);
                  LocalVariable localVariable = localVariables[value12];
                  int[] values4 = this.counts;
                  int signature_index = localVariables[value12].getSignature_index();
                  value12--;
                  localVariable.setSignature_index(values4[signature_index]);
                }
              } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
              }
            } else if (attribute instanceof PMGClass) {
              PMGClass pMGClass = (PMGClass) attribute;
              pMGClass.setPmg_class_index(this.counts[pMGClass.getPmg_class_index()]);
              pMGClass.setPmg_index(this.counts[pMGClass.getPmg_index()]);
            } else if (attribute instanceof Signature) {
              Signature signature = (Signature) attribute;
              signature.setSignatureIndex(this.counts[signature.getSignatureIndex()]);
            } else if (attribute instanceof SourceFile) {
              SourceFile sourceFile = (SourceFile) attribute;
              sourceFile.setSourceFileIndex(this.counts[sourceFile.getSourceFileIndex()]);
            } else {
              int value13;
              if (attribute instanceof StackMap) {
                StackMapEntry[] stackMapEntrys;
                for (int value14 =
                        value13 =
                            (stackMapEntrys = ((StackMap) attribute).getStackMap()).length - 1;
                    value14 >= 0;
                    value14 = --value13) {
                  int value15;
                  StackMapType[] stackMapTypes;
                  StackMapEntry stackMapEntry;
                  for (int value16 =
                          value15 =
                              (stackMapTypes =
                                          (stackMapEntry = stackMapEntrys[value13])
                                              .getTypes_of_locals())
                                      .length
                                  - 1;
                      value16 >= 0;
                      value16 = --value15) {
                    StackMapType stackMapType;
                    if ((stackMapType = stackMapTypes[value15]).getType() == 7) {
                      stackMapType.setIndex(this.counts[stackMapType.getIndex()]);
                    }
                  }
                  StackMapType[] stackMapTypes2;
                  int value17;
                  for (int value18 =
                          value17 =
                              (stackMapTypes2 = stackMapEntry.getTypes_of_stack_items()).length - 1;
                      value18 >= 0;
                      value18 = --value17) {
                    StackMapType stackMapType2;
                    if ((stackMapType2 = stackMapTypes2[value17]).getType() == 7) {
                      stackMapType2.setIndex(this.counts[stackMapType2.getIndex()]);
                    }
                  }
                }
              } else if (!(attribute instanceof Synthetic)) {
                if (attribute instanceof EnclosingMethod) {
                  EnclosingMethod enclosingMethod = (EnclosingMethod) attribute;
                  enclosingMethod.setClassIndex(this.counts[enclosingMethod.getClassIndex()]);
                  if (enclosingMethod.getMethodIndex() != 0) {
                    enclosingMethod.setMethodIndex(this.counts[enclosingMethod.getMethodIndex()]);
                  }
                } else if (attribute instanceof AnnotationDefault) {
                  AnnotationDefault annotationDefault = (AnnotationDefault) attribute;
                  ElementValue defaultValue = ((AnnotationDefault) attribute).getDefaultValue();
                  ElementValueGen elementValueGen =
                      ElementValueGen.copy(defaultValue, new ConstantPoolGen(constantPool), false);
                  this.processElementValueGen(elementValueGen);
                  annotationDefault.setDefaultValue(elementValueGen.getElementValue());
                } else if (attribute instanceof BootstrapMethods) {
                  BootstrapMethod[] bootstrapMethods;
                  int value19 =
                      (bootstrapMethods = ((BootstrapMethods) attribute).getBootstrapMethods())
                          .length;
                  int value20;
                  for (int value21 = value20 = 0; value21 < value19; value21 = value20) {
                    BootstrapMethod bootstrapMethod = bootstrapMethods[value20];
                    bootstrapMethod.setBootstrapMethodRef(
                        this.counts[bootstrapMethod.getBootstrap_method_ref()]);
                    int[] values5;
                    int value22;
                    for (int value23 =
                            value22 =
                                (values5 = new int[bootstrapMethod.getBootstrapArguments().length])
                                        .length
                                    - 1;
                        value23 >= 0;
                        value23 = value22) {
                      int value24 = this.counts[bootstrapMethod.getBootstrapArguments()[value22]];
                      int value25 = value22;
                      int[] values6 = values5;
                      value22--;
                      values6[value25] = value24;
                    }
                    value20++;
                    bootstrapMethod.setBootstrapArguments(values5);
                  }
                } else if (!(attribute instanceof StackMapTable)) {
                  if (attribute instanceof NestHost) {
                    NestHost nestHost = (NestHost) attribute;
                    nestHost.setHostClassIndex(this.counts[nestHost.getHostClassIndex()]);
                  } else {
                    int value26;
                    if (attribute instanceof NestMembers) {
                      int[] values7;
                      for (int value27 =
                              value26 =
                                  (values7 = ((NestMembers) attribute).getClasses()).length - 1;
                          value27 >= 0;
                          value27 = value26) {
                        int value28 = this.counts[values7[value26]];
                        int value29 = value26;
                        int[] values8 = values7;
                        value26--;
                        values8[value29] = value28;
                      }
                    } else if (attribute instanceof MethodParameters) {
                      MethodParameter[] methodParameters;
                      int value30 =
                          (methodParameters = ((MethodParameters) attribute).getParameters())
                              .length;
                      int value31;
                      for (int value32 = value31 = 0; value32 < value30; value32 = value31) {
                        MethodParameter methodParameter = methodParameters[value31];
                        int[] values9 = this.counts;
                        int nameIndex = methodParameter.getNameIndex();
                        value31++;
                        methodParameter.setNameIndex(values9[nameIndex]);
                      }
                    } else if (attribute instanceof Unknown) {
                    }
                  }
                } else {
                  StackMapTableEntry[] stackMapTableEntrys;
                  int value33 =
                      (stackMapTableEntrys = ((StackMapTable) attribute).getStackMap()).length;
                  int value34;
                  for (int value35 = value34 = 0; value35 < value33; value35 = ++value34) {
                    StackMapTableEntry stackMapTableEntry;
                    if ((stackMapTableEntry = stackMapTableEntrys[value34]).getTypesOfLocals()
                        != null) {
                      StackMapType[] stackMapTypes3;
                      int value36 = (stackMapTypes3 = stackMapTableEntry.getTypesOfLocals()).length;
                      int value37;
                      for (int value38 = value37 = 0; value38 < value36; value38 = ++value37) {
                        StackMapType stackMapType3;
                        if ((stackMapType3 = stackMapTypes3[value37]).getType() == 7) {
                          stackMapType3.setIndex(this.counts[stackMapType3.getIndex()]);
                        }
                      }
                    }
                    if (stackMapTableEntry.getTypesOfStackItems() != null) {
                      StackMapType[] stackMapTypes4;
                      int value39 =
                          (stackMapTypes4 = stackMapTableEntry.getTypesOfStackItems()).length;
                      int value40;
                      for (int value41 = value40 = 0; value41 < value39; value41 = ++value40) {
                        StackMapType stackMapType4;
                        if ((stackMapType4 = stackMapTypes4[value40]).getType() == 7) {
                          stackMapType4.setIndex(this.counts[stackMapType4.getIndex()]);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
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

  public void processMethodGen(MethodGen methodGen) {
    InstructionList instructionList;
    if ((instructionList = methodGen.getInstructionList()) != null) {
      int value;
      InstructionHandle[] instructionHandles;
      for (int value2 =
              value = (instructionHandles = instructionList.getInstructionHandles()).length - 1;
          value2 >= 0;
          value2 = --value) {
        Instruction instruction;
        if ((instruction = instructionHandles[value].getInstruction()) instanceof CPInstruction) {
          CPInstruction cPInstruction = (CPInstruction) instruction;
          cPInstruction.setIndex(this.counts[cPInstruction.getIndex()]);
        }
      }
    }
  }

  public void processElementValueGen(ElementValueGen elementValueGen) {
    if (elementValueGen instanceof AnnotationElementValueGen) {
      this.processAnnotationEntryGen(((AnnotationElementValueGen) elementValueGen).getAnnotation());
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
          int value3 = value;
          List values2 = values;
          value--;
          this.processElementValueGen((ElementValueGen) values2.get(value3));
        }
      } else {
        if (elementValueGen instanceof ClassElementValueGen) {
          ClassElementValueGen classElementValueGen = (ClassElementValueGen) elementValueGen;
          classElementValueGen.setIdx(this.counts[classElementValueGen.getIndex()]);
          return;
        }
        if (elementValueGen instanceof EnumElementValueGen) {
          EnumElementValueGen enumElementValueGen = (EnumElementValueGen) elementValueGen;
          enumElementValueGen.setTypeIdx(this.counts[enumElementValueGen.getTypeIdx()]);
          enumElementValueGen.setValueIdx(this.counts[enumElementValueGen.getValueIdx()]);
          return;
        }
        if (elementValueGen instanceof SimpleElementValueGen) {
          SimpleElementValueGen simpleElementValueGen = (SimpleElementValueGen) elementValueGen;
          simpleElementValueGen.setIdx(this.counts[simpleElementValueGen.getIndex()]);
        }
      }
    }
  }

  public void processClassGen(ClassGen classGen2) {
    ClassGen classGen3 = this.classGen = classGen2;
    this.treeSet.clear();
    this.processClassGen2(classGen3);
    this.processClassGen3(classGen2);
  }

  public ConstantPoolEditor(ClassStorage classStorage2) {
    super();
    this.treeSet = new TreeSet();
    this.classStorage = classStorage2;
  }

  public void processConstantPoolGen(MethodGen methodGen, ConstantPoolGen constantPoolGen) {
    InstructionList instructionList;
    if ((instructionList = methodGen.getInstructionList()) != null) {
      int value;
      InstructionHandle[] instructionHandles;
      for (int value2 =
              value = (instructionHandles = instructionList.getInstructionHandles()).length - 1;
          value2 >= 0;
          value2 = --value) {
        Instruction instruction;
        if ((instruction = instructionHandles[value].getInstruction()) instanceof CPInstruction) {
          CPInstruction cPInstruction = (CPInstruction) instruction;
          this.processConstantPoolGen(cPInstruction.getIndex(), constantPoolGen);
        }
      }
    }
  }

  public void processFieldGen(ClassGen classGen, MethodGen methodGen, FieldGen fieldGen) {
    Attribute[] attributes = null;
    if (classGen != null) {
      attributes = classGen.getAttributes();
    }
    if (methodGen != null) {
      attributes = methodGen.getAttributes();
    }
    if (fieldGen != null) {
      attributes = fieldGen.getAttributes();
    }
    int value;
    if (methodGen != null) {
      Attribute[] attributes2;
      for (int value2 = value = (attributes2 = methodGen.getCodeAttributes()).length - 1;
          value2 >= 0;
          value2 = --value) {
        if (attributes2[value] instanceof Unknown) {
          methodGen.removeCodeAttribute(attributes2[value]);
        }
      }
    }
    if (attributes != null) {
      int value3;
      for (int value4 = value3 = attributes.length - 1; value4 >= 0; value4 = --value3) {
        if (attributes[value3] instanceof Unknown) {
          if (classGen != null) {
            classGen.processAttribute2(attributes[value3]);
          }
          if (methodGen != null) {
            methodGen.removeAttribute(attributes[value3]);
          }
          if (fieldGen != null) {
            fieldGen.removeAttribute(attributes[value3]);
          }
        }
      }
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

  public void processConstantPoolGen(
      AnnotationEntryGen annotationEntryGen, ConstantPoolGen constantPoolGen) {
    if (!this.matchesAnnotationEntryGen(annotationEntryGen)) {
      this.processConstantPoolGen(annotationEntryGen.getTypeIndex(), constantPoolGen);
      int value;
      List values;
      for (int value2 = value = (values = annotationEntryGen.getValues()).size() - 1;
          value2 >= 0;
          value2 = value) {
        ElementValuePairGen elementValuePairGen;
        ElementValuePairGen elementValuePairGen2 =
            elementValuePairGen = (ElementValuePairGen) values.get(value);
        this.processConstantPoolGen(elementValuePairGen.getNameIndex(), constantPoolGen);
        value--;
        this.processConstantPoolGen(elementValuePairGen2.getValue(), constantPoolGen);
      }
    }
  }

  public void processEnabled(
      Attribute[] attributes, ConstantPoolGen constantPoolGen, boolean matches, boolean matches2) {
    int value;
    for (int value2 = value = attributes.length - 1; value2 >= 0; value2 = --value) {
      Attribute attribute;
      Attribute attribute2 = attribute = attributes[value];
      attribute2.setConstantPool(constantPoolGen.getConstantPool());
      if (!(attribute2 instanceof Unknown)
          && !(attribute instanceof RuntimeInvisibleAnnotations)
          && (!matches || !(attribute instanceof Signature))
          && (!matches2
              || !(attribute instanceof InnerClasses) && !(attribute instanceof EnclosingMethod))) {
        this.processConstantPoolGen(attribute.getName_index(), constantPoolGen);
      }
      if (attribute instanceof Code) {
        Code code = (Code) attribute;
        this.processEnabled(code.getAttributes(), constantPoolGen, matches, matches2);
        CodeException[] codeExceptions;
        int value3;
        for (int value4 = value3 = (codeExceptions = code.getExceptionTable()).length - 1;
            value4 >= 0;
            value4 = value3) {
          CodeException codeException = codeExceptions[value3];
          value3--;
          this.processConstantPoolGen(codeException.getCatch_type(), constantPoolGen);
        }
      } else if (attribute instanceof ConstantValue) {
        ConstantValue constantValue = (ConstantValue) attribute;
        this.processConstantPoolGen(constantValue.getConstantValueIndex(), constantPoolGen);
      } else if (!(attribute instanceof Deprecated)) {
        int value5;
        if (attribute instanceof ExceptionTable) {
          int[] values;
          for (int value6 =
                  value5 =
                      (values = ((ExceptionTable) attribute).getExceptionIndexTable()).length - 1;
              value6 >= 0;
              value6 = value5) {
            int value7 = value5;
            int[] values2 = values;
            value5--;
            this.processConstantPoolGen(values2[value7], constantPoolGen);
          }
        } else if (attribute instanceof InnerClasses) {
          int value8;
          if (!matches2) {
            InnerClass[] innerClasss;
            for (int value9 =
                    value8 =
                        (innerClasss = ((InnerClasses) attribute).getInnerClasses()).length - 1;
                value9 >= 0;
                value9 = value8) {
              this.processConstantPoolGen(
                  innerClasss[value8].getInner_name_index(), constantPoolGen);
              this.processConstantPoolGen(
                  innerClasss[value8].getInner_class_index(), constantPoolGen);
              InnerClass innerClass = innerClasss[value8];
              value8--;
              this.processConstantPoolGen(innerClass.getOuter_class_index(), constantPoolGen);
            }
          }
        } else if (!(attribute instanceof LineNumberTable)) {
          int value10;
          if (attribute instanceof LocalVariableTable) {
            LocalVariable[] localVariables;
            for (int value11 =
                    value10 =
                        (localVariables = ((LocalVariableTable) attribute).getLocalVariableTable())
                                .length
                            - 1;
                value11 >= 0;
                value11 = value10) {
              this.processConstantPoolGen(localVariables[value10].getName_index(), constantPoolGen);
              LocalVariable localVariable = localVariables[value10];
              value10--;
              this.processConstantPoolGen(localVariable.getSignature_index(), constantPoolGen);
            }
          } else if (attribute instanceof PMGClass) {
            PMGClass pMGClass;
            PMGClass pMGClass2 = pMGClass = (PMGClass) attribute;
            this.processConstantPoolGen(pMGClass.getPmg_class_index(), constantPoolGen);
            this.processConstantPoolGen(pMGClass2.getPmg_index(), constantPoolGen);
          } else if (attribute instanceof Signature) {
            if (!matches) {
              Signature signature = (Signature) attribute;
              this.processConstantPoolGen(signature.getSignatureIndex(), constantPoolGen);
            }
          } else if (attribute instanceof SourceFile) {
            SourceFile sourceFile = (SourceFile) attribute;
            this.processConstantPoolGen(sourceFile.getSourceFileIndex(), constantPoolGen);
          } else {
            int value12;
            if (attribute instanceof StackMap) {
              StackMapEntry[] stackMapEntrys;
              for (int value13 =
                      value12 = (stackMapEntrys = ((StackMap) attribute).getStackMap()).length - 1;
                  value13 >= 0;
                  value13 = --value12) {
                StackMapEntry stackMapEntry;
                StackMapType[] stackMapTypes;
                int value14;
                for (int value15 =
                        value14 =
                            (stackMapTypes =
                                        (stackMapEntry = stackMapEntrys[value12])
                                            .getTypes_of_locals())
                                    .length
                                - 1;
                    value15 >= 0;
                    value15 = --value14) {
                  StackMapType stackMapType;
                  if ((stackMapType = stackMapTypes[value14]).getType() == 7) {
                    this.processConstantPoolGen(stackMapType.getIndex(), constantPoolGen);
                  }
                }
                StackMapType[] stackMapTypes2;
                int value16;
                for (int value17 =
                        value16 =
                            (stackMapTypes2 = stackMapEntry.getTypes_of_stack_items()).length - 1;
                    value17 >= 0;
                    value17 = --value16) {
                  StackMapType stackMapType2;
                  if ((stackMapType2 = stackMapTypes2[value16]).getType() == 7) {
                    this.processConstantPoolGen(stackMapType2.getIndex(), constantPoolGen);
                  }
                }
              }
            } else if (!(attribute instanceof Synthetic)) {
              if (attribute instanceof EnclosingMethod) {
                if (!matches2) {
                  EnclosingMethod enclosingMethod = (EnclosingMethod) attribute;
                  this.processConstantPoolGen(enclosingMethod.getClassIndex(), constantPoolGen);
                  if (enclosingMethod.getMethodIndex() != 0) {
                    this.processConstantPoolGen(enclosingMethod.getMethodIndex(), constantPoolGen);
                  }
                }
              } else if (attribute instanceof AnnotationDefault) {
                ElementValueGen elementValueGen =
                    ElementValueGen.copy(
                        ((AnnotationDefault) attribute).getDefaultValue(), constantPoolGen, false);
                this.processConstantPoolGen(elementValueGen, constantPoolGen);
              } else if (attribute instanceof BootstrapMethods) {
                BootstrapMethod[] bootstrapMethods;
                int value18 =
                    (bootstrapMethods = ((BootstrapMethods) attribute).getBootstrapMethods())
                        .length;
                int value19;
                for (int value20 = value19 = 0; value20 < value18; value20 = ++value19) {
                  BootstrapMethod bootstrapMethod = bootstrapMethods[value19];
                  this.processConstantPoolGen(
                      bootstrapMethod.getBootstrap_method_ref(), constantPoolGen);
                  int[] values3;
                  int value21 = (values3 = bootstrapMethod.getBootstrapArguments()).length;
                  int value22;
                  for (int value23 = value22 = 0; value23 < value21; value23 = value22) {
                    int value24;
                    int value25 = value24 = values3[value22];
                    value22++;
                    this.processConstantPoolGen(value25, constantPoolGen);
                  }
                }
              } else if (!(attribute instanceof StackMapTable)) {
                if (attribute instanceof NestHost) {
                  NestHost nestHost = (NestHost) attribute;
                  this.processConstantPoolGen(nestHost.getHostClassIndex(), constantPoolGen);
                } else {
                  int value26;
                  if (attribute instanceof NestMembers) {
                    int[] values4;
                    for (int value27 =
                            value26 = (values4 = ((NestMembers) attribute).getClasses()).length - 1;
                        value27 >= 0;
                        value27 = value26) {
                      int value28 = value26;
                      int[] values5 = values4;
                      value26--;
                      this.processConstantPoolGen(values5[value28], constantPoolGen);
                    }
                  } else if (attribute instanceof MethodParameters) {
                    MethodParameter[] methodParameters;
                    int value29 =
                        (methodParameters = ((MethodParameters) attribute).getParameters()).length;
                    int value30;
                    for (int value31 = value30 = 0; value31 < value29; value31 = value30) {
                      MethodParameter methodParameter = methodParameters[value30];
                      value30++;
                      this.processConstantPoolGen(methodParameter.getNameIndex(), constantPoolGen);
                    }
                  } else if (attribute instanceof Unknown) {
                  }
                }
              } else {
                StackMapTableEntry[] stackMapTableEntrys;
                int value32 =
                    (stackMapTableEntrys = ((StackMapTable) attribute).getStackMap()).length;
                int value33;
                for (int value34 = value33 = 0; value34 < value32; value34 = ++value33) {
                  StackMapTableEntry stackMapTableEntry;
                  if ((stackMapTableEntry = stackMapTableEntrys[value33]).getTypesOfLocals()
                      != null) {
                    StackMapType[] stackMapTypes3;
                    int value35 = (stackMapTypes3 = stackMapTableEntry.getTypesOfLocals()).length;
                    int value36;
                    for (int value37 = value36 = 0; value37 < value35; value37 = ++value36) {
                      StackMapType stackMapType3;
                      if ((stackMapType3 = stackMapTypes3[value36]).getType() == 7) {
                        this.processConstantPoolGen(stackMapType3.getIndex(), constantPoolGen);
                      }
                    }
                  }
                  if (stackMapTableEntry.getTypesOfStackItems() != null) {
                    StackMapType[] stackMapTypes4;
                    int value38 =
                        (stackMapTypes4 = stackMapTableEntry.getTypesOfStackItems()).length;
                    int value39;
                    for (int value40 = value39 = 0; value40 < value38; value40 = ++value39) {
                      StackMapType stackMapType4;
                      if ((stackMapType4 = stackMapTypes4[value39]).getType() == 7) {
                        this.processConstantPoolGen(stackMapType4.getIndex(), constantPoolGen);
                      }
                    }
                  }
                }
              }
            }
          }
        }
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
          this.processConstantPoolGen(
              ((ClassElementValueGen) elementValueGen).getIndex(), constantPoolGen);
          return;
        }
        if (elementValueGen instanceof EnumElementValueGen) {
          this.processConstantPoolGen(
              ((EnumElementValueGen) elementValueGen).getTypeIdx(), constantPoolGen);
          this.processConstantPoolGen(
              ((EnumElementValueGen) elementValueGen).getValueIdx(), constantPoolGen);
          return;
        }
        if (elementValueGen instanceof SimpleElementValueGen) {
          this.processConstantPoolGen(
              ((SimpleElementValueGen) elementValueGen).getIndex(), constantPoolGen);
        }
      }
    }
  }

  public void processConstantPoolGen(int value, ConstantPoolGen constantPoolGen) {
    if (value != -1) {
      this.counts[value] = 1;
      Constant constant;
      if ((constant = constantPoolGen.getConstant(value)) != null) {
        int size = constantPoolGen.getSize();
        int value2;
        for (int value3 = value2 = value + 1;
            value3 < size && constantPoolGen.getConstant(value2) == null;
            value3 = value2) {
          byte value4 = 1;
          int value5 = value2;
          int[] values = this.counts;
          value2++;
          values[value5] = value4;
        }
      }
      if (constant instanceof ConstantClass) {
        ConstantClass constantClass = (ConstantClass) constant;
        this.processConstantPoolGen(constantClass.getNameIndex(), constantPoolGen);
      } else if (constant instanceof ConstantCP) {
        ConstantCP constantCP;
        ConstantCP constantCP2 = constantCP = (ConstantCP) constant;
        this.processConstantPoolGen(constantCP.getClass_index(), constantPoolGen);
        this.processConstantPoolGen(constantCP2.getName_and_type_index(), constantPoolGen);
      } else if (constant instanceof ConstantNameAndType) {
        ConstantNameAndType constantNameAndType;
        ConstantNameAndType constantNameAndType2 =
            constantNameAndType = (ConstantNameAndType) constant;
        this.processConstantPoolGen(constantNameAndType.getName_index(), constantPoolGen);
        this.processConstantPoolGen(constantNameAndType2.getSignature_index(), constantPoolGen);
      } else if (constant instanceof ConstantString) {
        ConstantString constantString = (ConstantString) constant;
        this.processConstantPoolGen(constantString.getStringIndex(), constantPoolGen);
      } else if (constant instanceof ConstantInvokeDynamic) {
        ConstantInvokeDynamic constantInvokeDynamic = (ConstantInvokeDynamic) constant;
        this.processConstantPoolGen(
            constantInvokeDynamic.getBootstrapMethodAttrIndex2(), constantPoolGen);
      } else if (constant instanceof ConstantMethodHandle) {
        ConstantMethodHandle constantMethodHandle = (ConstantMethodHandle) constant;
        this.processConstantPoolGen(constantMethodHandle.getReference_index(), constantPoolGen);
      } else {
        if (constant instanceof ConstantMethodType) {
          ConstantMethodType constantMethodType = (ConstantMethodType) constant;
          this.processConstantPoolGen(constantMethodType.getDescriptorIndex(), constantPoolGen);
        }
      }
    }
  }

  public void processAnnotationEntryGen(AnnotationEntryGen annotationEntryGen) {
    annotationEntryGen.setTypeIndex(this.counts[annotationEntryGen.getTypeIndex()]);
    int value;
    List values;
    for (int value2 = value = (values = annotationEntryGen.getValues()).size() - 1;
        value2 >= 0;
        value2 = value) {
      ElementValuePairGen elementValuePairGen;
      ElementValuePairGen elementValuePairGen2 =
          elementValuePairGen = (ElementValuePairGen) values.get(value);
      int value3 = this.counts[elementValuePairGen.getNameIndex()];
      value--;
      elementValuePairGen2.setNameIdx(value3);
      this.processElementValueGen(elementValuePairGen2.getValue());
    }
  }

  public void processClassGen2(ClassGen classGen) {
    ConstantPoolGen constantPool = classGen.getConstantPool();
    int size = constantPool.getSize();
    this.counts = new int[size];
    Arrays.fill(this.counts, 0);
    this.processConstantPoolGen(0, constantPool);
    this.processConstantPoolGen(classGen.getClassNameIndex(), constantPool);
    this.processConstantPoolGen(classGen.getSuperclassNameIndex(), constantPool);
    int value;
    int[] values;
    for (int value2 = value = (values = classGen.getInterfaces()).length - 1;
        value2 >= 0;
        value2 = value) {
      int value3 = value;
      int[] values2 = values;
      value--;
      this.processConstantPoolGen(values2[value3], constantPool);
    }
    this.processEnabled(classGen.getAttributes(), constantPool, false, false);
    this.processConstantPoolGen(classGen.getAnnotationEntries(), constantPool);
    int value4;
    Method[] methods;
    for (int value5 = value4 = (methods = classGen.getMethods()).length - 1;
        value5 >= 0;
        value5 = --value4) {
      Method method;
      Method method2 = method = methods[value4];
      this.processConstantPoolGen(method.getCount3(), constantPool);
      this.processConstantPoolGen(method.getCount(), constantPool);
      this.processEnabled(method.getAttributes(), constantPool, false, false);
      MethodGen methodGen;
      this.processConstantPoolGen(
          methodGen =
              MethodFactory.createMethodGen(
                  method2, classGen.getClassName(), constantPool, constantPool.getConstantPool()),
          constantPool);
      this.processConstantPoolGen(methodGen.getAnnotationEntryGens(), constantPool);
      int value6;
      for (int value7 = value6 = methodGen.getArgumentTypes().length - 1;
          value7 >= 0;
          value7 = value6) {
        int value8 = value6;
        MethodGen methodGen2 = methodGen;
        value6--;
        this.processConstantPoolGen(methodGen2.getAnnotationsOnParameter(value8), constantPool);
      }
    }
    int value9;
    Field[] fields;
    for (int value10 = value9 = (fields = classGen.getFields()).length - 1;
        value10 >= 0;
        value10 = value9) {
      Field field;
      Field field2 = field = fields[value9];
      this.processConstantPoolGen(field.getCount3(), constantPool);
      this.processConstantPoolGen(field.getCount(), constantPool);
      this.processEnabled(field2.getAttributes(), constantPool, false, false);
      FieldGen fieldGen = new FieldGen(field, constantPool);
      value9--;
      this.processConstantPoolGen(fieldGen.getAnnotationEntryGens(), constantPool);
    }
    int value11;
    for (int value12 = value11 = constantPool.getSize() - 1; value12 >= 0; value12 = --value11) {
      Constant constant;
      if ((constant = constantPool.getConstant(value11)) instanceof ConstantUtf8) {
        ConstantUtf8 constantUtf8 = (ConstantUtf8) constant;
        if ("".equals(constantUtf8.getBytes())) {
          this.processConstantPoolGen(value11, constantPool);
        }
      }
    }
  }

  public void processFieldGenOrMethodGen(
      ClassGen classGen, FieldGenOrMethodGen fieldGenOrMethodGen) {
    AnnotationEntryGen[] annotationEntryGens = null;
    if (classGen != null) {
      annotationEntryGens = classGen.getAnnotationEntries();
    }
    if (fieldGenOrMethodGen != null) {
      annotationEntryGens = fieldGenOrMethodGen.getAnnotationEntryGens();
    }
    int value;
    for (int value2 = value = 0; value2 < annotationEntryGens.length; value2 = ++value) {
      AnnotationEntryGen annotationEntryGen = annotationEntryGens[value];
      if (this.treeSet.contains(annotationEntryGen.getTypeIndex())) {
        if (classGen != null) {
          classGen.addAnnotationEntry2(annotationEntryGen);
        }
        if (fieldGenOrMethodGen != null) {
          fieldGenOrMethodGen.processAnnotationEntryGen2(annotationEntryGen);
        }
      }
    }
  }

  public void processClassGen3(ClassGen classGen) {
    ConstantPoolGen constantPool = classGen.getConstantPool();
    int size = constantPool.getSize();
    int value = 0;
    int value2;
    for (int value3 = value2 = this.counts.length - 1; value3 >= 0; value3 = value2) {
      int value4 = value2;
      int[] values = this.counts;
      int value5 = value;
      value2--;
      value = value5 + values[value4];
    }
    Constant[] constants = new Constant[value];
    int value6 = 0;
    int value7;
    for (int value8 = value7 = 0; value8 < size; value8 = ++value7) {
      Constant constant = constantPool.getConstant(value7);
      if (this.counts[value7] == 1) {
        if (constant != null) {
          constant = constant.copy();
        }
        constants[value6] = constant;
        this.counts[value7] = value6++;
      }
    }
    int value9;
    for (int value10 = value9 = 0; value10 < value; value10 = ++value9) {
      Constant constant2;
      if ((constant2 = constants[value9]) instanceof ConstantClass) {
        ConstantClass constantClass = (ConstantClass) constant2;
        constantClass.setNameIndex(this.counts[constantClass.getNameIndex()]);
      } else if (constant2 instanceof ConstantCP) {
        ConstantCP constantCP = (ConstantCP) constant2;
        constantCP.setClass_index(this.counts[constantCP.getClass_index()]);
        constantCP.setName_and_type_index(this.counts[constantCP.getName_and_type_index()]);
      } else if (constant2 instanceof ConstantNameAndType) {
        ConstantNameAndType constantNameAndType = (ConstantNameAndType) constant2;
        constantNameAndType.setName_index(this.counts[constantNameAndType.getName_index()]);
        constantNameAndType.setSignature_index(
            this.counts[constantNameAndType.getSignature_index()]);
      } else if (constant2 instanceof ConstantString) {
        ConstantString constantString = (ConstantString) constant2;
        constantString.setStringIndex(this.counts[constantString.getStringIndex()]);
      } else if (constant2 instanceof ConstantInvokeDynamic) {
        ConstantInvokeDynamic constantInvokeDynamic = (ConstantInvokeDynamic) constant2;
        constantInvokeDynamic.setCount2(
            this.counts[constantInvokeDynamic.getBootstrapMethodAttrIndex2()]);
      } else if (constant2 instanceof ConstantMethodHandle) {
        ConstantMethodHandle constantMethodHandle = (ConstantMethodHandle) constant2;
        constantMethodHandle.setReference_index(
            this.counts[constantMethodHandle.getReference_index()]);
      } else if (constant2 instanceof ConstantMethodType) {
        ConstantMethodType constantMethodType = (ConstantMethodType) constant2;
        constantMethodType.setDescriptorIndex(this.counts[constantMethodType.getDescriptorIndex()]);
      }
    }
    ConstantPoolGen constantPoolGen;
    ConstantPool constantPool2 =
        (constantPoolGen = new ConstantPoolGen(constants)).getConstantPool();
    classGen.setConstantPool(constantPoolGen);
    classGen.processCount2(this.counts[classGen.getClassNameIndex()]);
    classGen.processCount3(this.counts[classGen.getSuperclassNameIndex()]);
    Attribute[] attributes;
    int value11 = (attributes = classGen.getAttributes()).length;
    int value12;
    for (int value13 = value12 = 0; value13 < value11; value13 = value12) {
      int value14 = value12;
      Attribute[] attributes2 = attributes;
      value12++;
      classGen.processAttribute2(attributes2[value14]);
    }
    this.processConstantPool(attributes, constantPool2);
    int value15;
    for (int value16 = value15 = 0; value16 < value11; value16 = value15) {
      int value17 = value15;
      Attribute[] attributes3 = attributes;
      value15++;
      classGen.processAttribute(attributes3[value17]);
    }
    this.processFieldGen(classGen, null, null);
    this.processFieldGenOrMethodGen(classGen, null);
    this.processAnnotationEntryGens(classGen.getAnnotationEntries());
    Method[] methods;
    int value18;
    for (int value19 = value18 = (methods = classGen.getMethods()).length - 1;
        value19 >= 0;
        value19 = value18) {
      Method method;
      int value20 = (method = methods[value18]).getLocalVariableTable() == null ? 1 : 0;
      MethodGen methodGen =
          MethodFactory.createMethodGen(
              method, classGen.getClassName(), constantPoolGen, constantPool.getConstantPool());
      this.processMethodGen(methodGen);
      this.processConstantPool(methodGen.getAttributes(), constantPool2);
      this.processConstantPool(
          new Attribute[] {methodGen.getLocalVariableTable(constantPoolGen)}, constantPool2);
      this.processConstantPool(methodGen.getCodeAttributes(), constantPool2);
      this.processFieldGen(null, methodGen, null);
      this.processFieldGenOrMethodGen(null, methodGen);
      this.processAnnotationEntryGens(methodGen.getAnnotationEntryGens());
      int value21;
      for (int value22 = value21 = methodGen.getArgumentTypes().length - 1;
          value22 >= 0;
          value22 = value21) {
        int value23 = value21;
        value21--;
        this.processValues(methodGen.getAnnotationsOnParameter(value23));
      }
      label84:
      {
        if (value20 == 0) {
          value19 =
              Configuration.getRenamingOptions()
                  .getLocalVariableOptions()
                  .calculateCount(this.classStorage, classGen, methodGen);
          Configuration.getRenamingOptions().getLocalVariableOptions();
          if (value19 != 4) {
            break label84;
          }
        }
        methodGen.getVariable_vec();
      }
      Method method2 = methodGen.getMethod();
      method2.setCount3(this.counts[method.getCount3()]);
      method2.setCount(this.counts[method.getCount()]);
      int value24 = value18;
      value18--;
      classGen.setMethodAt(method2, value24);
    }
    Field[] fields;
    int value25;
    for (int value26 = value25 = (fields = classGen.getFields()).length - 1;
        value26 >= 0;
        value26 = value25) {
      Field field = fields[value25];
      FieldGen fieldGen = new FieldGen(field, constantPool);
      fieldGen.setConstantPool(constantPoolGen);
      this.processConstantPool(fieldGen.getAttributes(), constantPool2);
      this.processFieldGen(null, null, fieldGen);
      this.processFieldGenOrMethodGen(null, fieldGen);
      this.processAnnotationEntryGens(fieldGen.getAnnotationEntryGens());
      Field field2 = fieldGen.getField();
      field2.setCount3(this.counts[field.getCount3()]);
      int value27 = this.counts[field.getCount()];
      value25--;
      field2.setCount(value27);
      classGen.replaceField(field, field2);
    }
  }
}
