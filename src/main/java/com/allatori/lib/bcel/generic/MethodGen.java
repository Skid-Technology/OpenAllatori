package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.AnnotationEntry;
import com.allatori.lib.bcel.classfile.Annotations;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.Code;
import com.allatori.lib.bcel.classfile.CodeException;
import com.allatori.lib.bcel.classfile.ExceptionTable;
import com.allatori.lib.bcel.classfile.LineNumber;
import com.allatori.lib.bcel.classfile.LineNumberTable;
import com.allatori.lib.bcel.classfile.LocalVariable;
import com.allatori.lib.bcel.classfile.LocalVariableTable;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.ParameterAnnotationEntry;
import com.allatori.lib.bcel.classfile.ParameterAnnotations;
import com.allatori.lib.bcel.classfile.RuntimeVisibleParameterAnnotations;
import com.allatori.lib.bcel.classfile.Utility;
import com.allatori.lib.bcel.util.BCELComparator;
import com.allatori.lib.bcel.util.comparator.MethodGenComparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MethodGen extends FieldGenOrMethodGen {

  public boolean strip_attributes;

  public List values;

  public List throws_vec;

  public InstructionList il;

  public List[] param_annotations;

  public int count;

  public String class_name;

  public List code_attrs_vec;

  public static BCELComparator bcelComparator = new MethodGenComparator();

  public List variable_vec;

  public List line_number_vec;

  public String[] arg_names;

  public List exception_vec;

  public int count2;

  public boolean hasParameterAnnotations;

  public boolean haveUnpackedParameterAnnotations;

  public Type[] arg_types;

  public Type getArgumentType(int value) {
    return this.arg_types[value];
  }

  public Type getReturnType() {
    return this.getType();
  }

  public void addCodeAttribute(Attribute attribute) {
    this.code_attrs_vec.add(attribute);
  }

  public CodeException[] getCodeExceptions() {
    int value;
    CodeException[] codeExceptions = new CodeException[value = this.exception_vec.size()];
    try {
      int value2 = 0;
      while (value2 < value) {
        CodeExceptionGen codeExceptionGen = (CodeExceptionGen) this.exception_vec.get(value2);
        CodeException codeException = codeExceptionGen.getCodeException(this.cp);
        int value3 = value2;
        value2++;
        codeExceptions[value3] = codeException;
      }
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
    }
    return codeExceptions;
  }

  public ExceptionTable getExceptionTable(ConstantPoolGen constantPoolGen) {
    int value;
    int[] values = new int[value = this.throws_vec.size()];
    try {
      int value2 = 0;
      while (value2 < value) {
        int value3 = constantPoolGen.addClass((String) this.throws_vec.get(value2));
        int value4 = value2;
        value2++;
        values[value4] = value3;
      }
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
    }
    return new ExceptionTable(
        constantPoolGen.addUtf8("Exceptions"),
        2 + 2 * value,
        values,
        constantPoolGen.getConstantPool());
  }

  public void setArgumentType(int value, Type type) {
    this.arg_types[value] = type;
  }

  public static int getMaxStack(
      ConstantPoolGen constantPoolGen,
      InstructionList instructionList,
      CodeExceptionGen[] codeExceptionGens) {
    MethodGen$BranchStack methodGen$BranchStack = new MethodGen$BranchStack();
    int value;
    for (int value2 = value = 0; value2 < codeExceptionGens.length; value2 = ++value) {
      InstructionHandle instructionHandle;
      if ((instructionHandle = codeExceptionGens[value].getInstructionHandle3()) != null) {
        methodGen$BranchStack.push(instructionHandle, 1);
      }
    }
    int value3 = 0;
    int value4 = 0;
    InstructionHandle start = instructionList.getStart();
    while (start != null) {
      Instruction instruction = start.getInstruction();
      short opcode = instruction.getOpcode();
      int value5 =
          instruction.produceStack(constantPoolGen) - instruction.consumeStack(constantPoolGen);
      if ((value3 += value5) > value4) {
        value4 = value3;
      }
      InstructionHandle instructionHandle2;
      if (instruction instanceof BranchInstruction) {
        BranchInstruction branchInstruction = (BranchInstruction) instruction;
        MethodGen$BranchStack methodGen$BranchStack2;
        if (instruction instanceof Select) {
          InstructionHandle[] targets = ((Select) branchInstruction).getTargets();
          int value6;
          for (int value7 = value6 = 0; value7 < targets.length; value7 = value6) {
            int value8 = value6;
            value6++;
            methodGen$BranchStack.push(targets[value8], value3);
          }
          start = null;
          methodGen$BranchStack2 = methodGen$BranchStack;
        } else {
          if (!(branchInstruction instanceof IfInstruction)) {
            if (opcode == 168 || opcode == 201) {
              methodGen$BranchStack.push(start.getInstructionHandle2(), value3 - 1);
            }
            start = null;
          }
          methodGen$BranchStack2 = methodGen$BranchStack;
        }
        methodGen$BranchStack2.push(branchInstruction.getTarget(), value3);
        instructionHandle2 = start;
      } else {
        if (opcode == 191 || opcode == 169 || opcode >= 172 && opcode <= 177) {
          start = null;
        }
        instructionHandle2 = start;
      }
      if (instructionHandle2 != null) {
        start = start.getInstructionHandle2();
      }
      MethodGen$BranchTarget methodGen$BranchTarget;
      if (start == null && (methodGen$BranchTarget = methodGen$BranchStack.pop()) != null) {
        start = methodGen$BranchTarget.target;
        value3 = methodGen$BranchTarget.stackDepth;
      }
    }
    return value4;
  }

  public MethodGen(
      int value,
      Type type,
      Type[] types,
      String[] texts,
      String text,
      String text2,
      InstructionList instructionList,
      ConstantPoolGen constantPoolGen) {
    super();
    this.variable_vec = new ArrayList();
    this.line_number_vec = new ArrayList();
    this.exception_vec = new ArrayList();
    this.throws_vec = new ArrayList();
    this.code_attrs_vec = new ArrayList();
    this.hasParameterAnnotations = false;
    this.haveUnpackedParameterAnnotations = false;
    this.setAccess_flags(value);
    this.setType(type);
    this.setArgumentTypes(types);
    this.setArgumentNames(texts);
    this.setName(text);
    this.setClassName(text2);
    this.setInstructionList(instructionList);
    this.setConstantPool(constantPoolGen);
    boolean matches = this.isEnabled15() || this.isEnabled14();
    InstructionHandle instructionHandle = null;
    InstructionHandle instructionHandle2 = null;
    if (!matches) {
      instructionHandle = instructionList.getStart();
      instructionHandle2 = instructionList.getEnd();
      if (!this.isEnabled11() && text2 != null) {
        this.addLocalVariable("this", new ObjectType(text2), instructionHandle, instructionHandle2);
      }
    }
    if (types != null) {
      int value2 = types.length;
      int value3;
      for (int value4 = value3 = 0; value4 < value2; value4 = ++value3) {
        if (Type.VOID == types[value3]) {
          throw new ClassGenException("'void' is an illegal argument type for a method");
        }
      }
      if (texts != null) {
        if (value2 != texts.length) {
          throw new ClassGenException(
              ("Mismatch in argument array lengths: " + value2 + " vs. " + texts.length));
        }
      } else {
        texts = new String[value2];
        int value5;
        for (int value6 = value5 = 0; value6 < value2; value6 = value5) {
          String text3 = "arg" + value5;
          int value7 = value5;
          String[] texts2 = texts;
          value5++;
          texts2[value7] = text3;
        }
        this.setArgumentNames(texts);
      }
      int value8;
      if (!matches) {
        for (int value9 = value8 = 0; value9 < value2; value9 = value8) {
          String text4 = texts[value8];
          Type type2 = types[value8];
          value8++;
          this.addLocalVariable(text4, type2, instructionHandle, instructionHandle2);
        }
      }
    }
  }

  public void stripAttributes(boolean strip_attributes) {
    this.strip_attributes = strip_attributes;
  }

  public static void setComparator(BCELComparator bCELComparator) {
    bcelComparator = bCELComparator;
  }

  public LineNumberGen addLineNumber(InstructionHandle instructionHandle, int value) {
    LineNumberGen lineNumberGen = new LineNumberGen(instructionHandle, value);
    this.line_number_vec.add(lineNumberGen);
    return lineNumberGen;
  }

  public List getAnnotationsOnParameter(int value) {
    this.update2();
    return this.hasParameterAnnotations && value <= this.arg_types.length
        ? this.param_annotations[value]
        : null;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public static BCELComparator getComparator() {
    return bcelComparator;
  }

  public String[] getArgumentNames() {
    return (String[]) this.arg_names.clone();
  }

  public void update() {
    Iterator iterator2;
    if (this.values != null) {
      for (Iterator iterator3 = iterator2 = this.values.iterator();
          iterator3.hasNext();
          iterator3 = iterator2) {
        ((MethodObserver) iterator2.next()).notify(this);
      }
    }
  }

  public void removeLocalVariable(LocalVariableGen localVariableGen) {
    this.variable_vec.remove(localVariableGen);
  }

  public void update2() {
    if (!this.haveUnpackedParameterAnnotations) {
      Attribute[] attributes = this.getAttributes();
      ParameterAnnotations parameterAnnotations = null;
      ParameterAnnotations parameterAnnotations2 = null;
      int value;
      for (int value2 = value = 0; value2 < attributes.length; value2 = ++value) {
        Attribute attribute;
        if ((attribute = attributes[value]) instanceof ParameterAnnotations) {
          if (!this.hasParameterAnnotations) {
            this.param_annotations = new List[this.arg_types.length];
            int value3;
            for (int value4 = value3 = 0; value4 < this.arg_types.length; value4 = value3) {
              List[] valuess = this.param_annotations;
              ArrayList values = new ArrayList();
              int value5 = value3;
              value3++;
              valuess[value5] = values;
            }
          }
          this.hasParameterAnnotations = true;
          ParameterAnnotations parameterAnnotations3;
          if ((parameterAnnotations3 = (ParameterAnnotations) attribute)
              instanceof RuntimeVisibleParameterAnnotations) {
            parameterAnnotations = parameterAnnotations3;
          } else {
            parameterAnnotations2 = parameterAnnotations3;
          }
          int value6;
          for (int value7 = value6 = 0; value7 < this.arg_types.length; value7 = ++value6) {
            try {
              ParameterAnnotationEntry parameterAnnotationEntry =
                  parameterAnnotations3.getParameter_annotation_table()[value6];
              List values2 =
                  this.makeMutableVersion(parameterAnnotationEntry.getAnnotationEntries());
              this.param_annotations[value6].addAll(values2);
            } catch (Exception exception) {
            }
          }
        }
      }
      if (parameterAnnotations != null) {
        this.removeAttribute(parameterAnnotations);
      }
      if (parameterAnnotations2 != null) {
        this.removeAttribute(parameterAnnotations2);
      }
      this.haveUnpackedParameterAnnotations = true;
    }
  }

  public void removeExceptionHandlers() {
    this.exception_vec.clear();
  }

  public int getCount() {
    return this.count;
  }

  public LocalVariableGen addLocalVariable(
      String text,
      Type type,
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2) {
    return this.addLocalVariable(text, type, this.count, instructionHandle, instructionHandle2);
  }

  public void setInstructionList(InstructionList instructionList) {
    this.il = instructionList;
  }

  public void setArgumentName(int value, String text) {
    this.arg_names[value] = text;
  }

  @Override
  public int hashCode() {
    return bcelComparator.hashCode(this);
  }

  public String[] getExceptions() {
    String[] texts = new String[this.throws_vec.size()];
    this.throws_vec.toArray(texts);
    return texts;
  }

  public MethodGen copy(String text, ConstantPoolGen constantPoolGen) {
    Method method = ((MethodGen) this.clone()).getMethod();
    MethodGen methodGen = new MethodGen(method, text, this.cp);
    if (this.cp != constantPoolGen) {
      methodGen.setConstantPool(constantPoolGen);
      methodGen.getInstructionList().replaceConstantPool(this.cp, constantPoolGen);
    }
    return methodGen;
  }

  public void removeLineNumbers() {
    this.line_number_vec.clear();
  }

  public CodeExceptionGen addExceptionHandler(
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2,
      InstructionHandle instructionHandle3,
      ObjectType objectType) {
    if (instructionHandle != null && instructionHandle2 != null && instructionHandle3 != null) {
      CodeExceptionGen codeExceptionGen =
          new CodeExceptionGen(
              instructionHandle, instructionHandle2, instructionHandle3, objectType);
      this.exception_vec.add(codeExceptionGen);
      return codeExceptionGen;
    } else {
      throw new ClassGenException("Exception handler target is null instruction");
    }
  }

  public LocalVariableGen addLocalVariable(
      String text,
      Type type,
      int value,
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2) {
    if (type.getType() != 16) {
      int size = type.getSize();
      if (value + size > this.count) {
        this.count = value + size;
      }
      LocalVariableGen localVariableGen =
          new LocalVariableGen(value, text, type, instructionHandle, instructionHandle2);
      int value2;
      if ((value2 = this.variable_vec.indexOf(localVariableGen)) >= 0) {
        this.variable_vec.set(value2, localVariableGen);
        return localVariableGen;
      } else {
        this.variable_vec.add(localVariableGen);
        return localVariableGen;
      }
    } else {
      throw new IllegalArgumentException(("Can not use " + type + " as type for local variable"));
    }
  }

  public void getVariable_vec() {
    this.variable_vec.clear();
  }

  public LocalVariableTable getLocalVariableTable(ConstantPoolGen constantPoolGen) {
    int value;
    LocalVariableGen[] localVariableGens;
    LocalVariable[] localVariables =
        new LocalVariable[value = (localVariableGens = this.getLocalVariables()).length];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      LocalVariable localVariable = localVariableGens[value2].getLocalVariable(constantPoolGen);
      int value4 = value2;
      value2++;
      localVariables[value4] = localVariable;
    }
    return new LocalVariableTable(
        constantPoolGen.addUtf8("LocalVariableTable"),
        2 + localVariables.length * 10,
        localVariables,
        constantPoolGen.getConstantPool());
  }

  public LineNumberTable getLineNumberTable(ConstantPoolGen constantPoolGen) {
    int value;
    LineNumber[] lineNumbers = new LineNumber[value = this.line_number_vec.size()];
    try {
      int value2 = 0;
      while (value2 < value) {
        LineNumber lineNumber = ((LineNumberGen) this.line_number_vec.get(value2)).getLineNumber();
        int value3 = value2;
        value2++;
        lineNumbers[value3] = lineNumber;
      }
    } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
    }
    return new LineNumberTable(
        constantPoolGen.addUtf8("LineNumberTable"),
        2 + lineNumbers.length * 4,
        lineNumbers,
        constantPoolGen.getConstantPool());
  }

  public InstructionList getInstructionList() {
    return this.il;
  }

  public void removeExceptions() {
    this.throws_vec.clear();
  }

  public void removeCodeAttributes() {
    this.code_attrs_vec.clear();
  }

  public void update3() {
    InstructionHandle instructionHandle;
    if (this.il != null) {
      for (InstructionHandle instructionHandle3 = instructionHandle = this.il.getStart();
          instructionHandle3 != null;
          instructionHandle3 = instructionHandle) {
        label42:
        {
          InstructionHandle instructionHandle4;
          instructionHandle4 = instructionHandle.instructionHandle2;
          label41:
          if (instructionHandle.instructionHandle2 != null
              && instructionHandle.getInstruction() instanceof NOP) {
            try {
              this.il.processInstructionHandle2(instructionHandle);
            } catch (TargetLostException targetLostException) {
              InstructionHandle[] targets = targetLostException.getTargets();
              int value;
              int value2 = value = 0;
              while (true) {
                if (value2 >= targets.length) {
                  break label41;
                }
                InstructionTargeter[] targeters = targets[value].getTargeters();
                int value3;
                for (int value4 = value3 = 0; value4 < targeters.length; value4 = value3) {
                  InstructionTargeter instructionTargeter = targeters[value3];
                  int value5 = value;
                  value3++;
                  instructionTargeter.updateTarget(targets[value5], instructionHandle4);
                }
                value2 = ++value;
              }
            }
            instructionHandle3 = instructionHandle4;
            break label42;
          }
          instructionHandle3 = instructionHandle4;
        }
        instructionHandle = instructionHandle3;
      }
    }
  }

  public void update4() {
    if (this.il != null) {
      int value = this.isEnabled11() ? 0 : 1;
      int value2;
      if (this.arg_types != null) {
        for (int value3 = value2 = 0; value3 < this.arg_types.length; value3 = value2) {
          Type type = this.arg_types[value2];
          int value4 = value;
          value2++;
          value = value4 + type.getSize();
        }
      }
      InstructionHandle instructionHandle;
      for (InstructionHandle instructionHandle2 = instructionHandle = this.il.getStart();
          instructionHandle2 != null;
          instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2()) {
        int value5;
        Instruction instruction;
        if (((instruction = instructionHandle.getInstruction()) instanceof LocalVariableInstruction
                || instruction instanceof RET
                || instruction instanceof IINC)
            && (value5 =
                    ((IndexedInstruction) instruction).getIndex()
                        + ((TypedInstruction) instruction).getType(this.cp).getSize())
                > value) {
          value = value5;
        }
      }
      this.count = value;
    } else {
      this.count = 0;
    }
  }

  public String getArgumentName(int value) {
    return this.arg_names[value];
  }

  public void removeCodeAttribute(Attribute attribute) {
    this.code_attrs_vec.remove(attribute);
  }

  public void addParameterAnnotation(int value, AnnotationEntryGen annotationEntryGen) {
    this.update2();
    if (!this.hasParameterAnnotations) {
      this.param_annotations = new List[this.arg_types.length];
      this.hasParameterAnnotations = true;
    }
    List values;
    if ((values = this.param_annotations[value]) != null) {
      values.add(annotationEntryGen);
    } else {
      ArrayList values2;
      (values2 = new ArrayList()).add(annotationEntryGen);
      this.param_annotations[value] = values2;
    }
  }

  public void setArgumentTypes(Type[] types) {
    this.arg_types = types;
  }

  public void addException(String text) {
    this.throws_vec.add(text);
  }

  public void processMethodObserver(MethodObserver methodObserver) {
    if (this.values == null) {
      this.values = new ArrayList();
    }
    this.values.add(methodObserver);
  }

  public String getClass_name() {
    return this.class_name;
  }

  public CodeExceptionGen[] getExceptionHandlers() {
    CodeExceptionGen[] codeExceptionGens = new CodeExceptionGen[this.exception_vec.size()];
    this.exception_vec.toArray(codeExceptionGens);
    return codeExceptionGens;
  }

  @Override
  public String getText() {
    return Type.getMethodSignature(this.type, this.arg_types);
  }

  public void setArgumentNames(String[] texts) {
    this.arg_names = texts;
  }

  public Attribute[] getCodeAttributes() {
    Attribute[] attributes = new Attribute[this.code_attrs_vec.size()];
    this.code_attrs_vec.toArray(attributes);
    return attributes;
  }

  public void setClassName(String class_name) {
    this.class_name = class_name;
  }

  public MethodGen(Method method, String text, ConstantPoolGen constantPoolGen) {
    this(
        method.getAccess_flags2(),
        Type.getReturnType(method.getText2()),
        Type.getArgumentTypes(method.getText2()),
        null,
        method.getText(),
        text,
        (method.getAccess_flags2() & 1280) == 0
            ? new InstructionList(method.getCode().getCode())
            : null,
        constantPoolGen);
    Type type;
    Attribute[] attributes = method.getAttributes();
    int value;
    for (int value2 = value = 0; value2 < attributes.length; value2 = ++value) {
      Attribute attribute;
      if (!((attribute = attributes[value]) instanceof Code)) {
        if (attribute instanceof ExceptionTable) {
          String[] exceptionNames = ((ExceptionTable) attribute).getExceptionNames();
          int value3;
          for (int value4 = value3 = 0; value4 < exceptionNames.length; value4 = value3) {
            int value5 = value3;
            value3++;
            this.addException(exceptionNames[value5]);
          }
        } else if (attribute instanceof Annotations) {
          AnnotationEntry[] annotationEntries = ((Annotations) attribute).getAnnotationEntries();
          int value6;
          for (int value7 = value6 = 0; value7 < annotationEntries.length; value7 = value6) {
            AnnotationEntry annotationEntry = annotationEntries[value6];
            value6++;
            this.processAnnotationEntryGen(
                new AnnotationEntryGen(annotationEntry, constantPoolGen, false));
          }
        } else {
          this.addAttribute(attribute);
        }
      } else {
        Code code = (Code) attribute;
        this.setCount2(code.getMax_stack());
        this.setCount(code.getMax_locals());
        int value8;
        CodeException[] codeExceptions;
        if ((codeExceptions = code.getExceptionTable()) != null) {
          for (int value9 = value8 = 0; value9 < codeExceptions.length; value9 = value8) {
            CodeException codeException;
            int catch_type = (codeException = codeExceptions[value8]).getCatch_type();
            ObjectType objectType = null;
            if (catch_type > 0) {
              String text2 = method.getConstantPool().createText(catch_type, (byte) 7);
              objectType = new ObjectType(text2);
            }
            int end_pc = codeException.getEnd_pc();
            InstructionHandle instructionHandle;
            MethodGen methodGen;
            if (method.getCode().getCode().length == end_pc) {
              methodGen = this;
              instructionHandle = this.il.getEnd();
            } else {
              methodGen = this;
              instructionHandle = this.il.findHandle(end_pc).getInstructionHandle();
            }
            InstructionHandle instructionHandle2 = this.il.findHandle(codeException.getStart_pc());
            InstructionList instructionList = this.il;
            int handler_pc = codeException.getHandler_pc();
            InstructionHandle instructionHandle3 = instructionHandle;
            MethodGen methodGen2 = methodGen;
            value8++;
            methodGen2.addExceptionHandler(
                instructionHandle2,
                instructionHandle3,
                instructionList.findHandle(handler_pc),
                objectType);
          }
        }
        Attribute[] attributes2 = code.getAttributes();
        int value10;
        for (value2 = value10 = 0; value2 < attributes2.length; value2 = ++value10) {
          Attribute attribute2;
          if ((attribute2 = attributes2[value10]) instanceof LineNumberTable) {
            LineNumber[] lineNumberTable = ((LineNumberTable) attribute2).getLineNumberTable();
            int value11;
            for (int value12 = value11 = 0; value12 < lineNumberTable.length; value12 = ++value11) {
              LineNumber lineNumber = lineNumberTable[value11];
              InstructionHandle instructionHandle4;
              if ((instructionHandle4 = this.il.findHandle(lineNumber.getStart_pc())) != null) {
                this.addLineNumber(instructionHandle4, lineNumber.getLine_number());
              }
            }
          } else if (attribute2 instanceof LocalVariableTable) {
            LocalVariable[] localVariableTable =
                ((LocalVariableTable) attribute2).getLocalVariableTable();
            value2 = 0;
            this.getVariable_vec();
            for (int index = 0; value2 < localVariableTable.length; value2 = index) {
              LocalVariable localVariable = localVariableTable[index];
              InstructionHandle instructionHandle5 =
                  this.il.findHandle(localVariable.getStart_pc());
              InstructionHandle instructionHandle6 =
                  this.il.findHandle(localVariable.getStart_pc() + localVariable.getLength());
              if (null == instructionHandle5) {
                instructionHandle5 = this.il.getStart();
              }
              if (null == instructionHandle6) {
                instructionHandle6 = this.il.getEnd();
              }
              String text3 = localVariable.getText();
              type = Type.getType(localVariable.getText2());
              int index2 = localVariable.getIndex();
              Type type2 = type;
              index++;
              this.addLocalVariable(text3, type2, index2, instructionHandle5, instructionHandle6);
            }
          } else {
            this.addCodeAttribute(attribute2);
          }
        }
      }
    }
  }

  public void removeExceptionHandler(CodeExceptionGen codeExceptionGen) {
    this.exception_vec.remove(codeExceptionGen);
  }

  public void processConstantPoolGen(ConstantPoolGen constantPoolGen) {
    if (this.hasParameterAnnotations) {
      int value;
      Attribute[] attributes;
      if ((attributes = Utility.createAttributes(constantPoolGen, this.param_annotations))
          != null) {
        for (int value2 = value = 0; value2 < attributes.length; value2 = value) {
          int value3 = value;
          Attribute[] attributes2 = attributes;
          value++;
          this.addAttribute(attributes2[value3]);
        }
      }
    }
  }

  public int getCount2() {
    return this.count2;
  }

  public Type[] getArgumentTypes() {
    return (Type[]) this.arg_types.clone();
  }

  public void update5() {
    if (this.il != null) {
      this.count2 = getMaxStack(this.cp, this.il, this.getExceptionHandlers());
      if (this.count2 == 0 && this.exception_vec != null && this.exception_vec.size() > 0) {
        this.count2 = 1;
        return;
      }
    } else {
      this.count2 = 0;
    }
  }

  public static final void processCount(
      LocalVariableGen[] localVariableGens, int value, int value2) {
    int value3 = value;
    int value4 = value2;
    int index = localVariableGens[(value + value2) / 2].getIndex();
    do {
      LocalVariableGen[] localVariableGens2 = localVariableGens;
      while (localVariableGens2[value3].getIndex() < index) {
        value3++;
        localVariableGens2 = localVariableGens;
      }
      int value5 = index;
      while (value5 < localVariableGens[value4].getIndex()) {
        value4--;
        value5 = index;
      }
      if (value3 <= value4) {
        LocalVariableGen localVariableGen = localVariableGens[value3];
        localVariableGens[value3] = localVariableGens[value4];
        int value6 = value4;
        value3++;
        localVariableGens[value6] = localVariableGen;
        value4--;
      }
    } while (value3 <= value4);
    if (value < value4) {
      processCount(localVariableGens, value, value4);
    }
    if (value3 < value2) {
      processCount(localVariableGens, value3, value2);
    }
  }

  public Method getMethod() {
    String text = this.getText();
    int value = this.cp.addUtf8(this.name);
    int value2 = this.cp.addUtf8(text);
    byte[] values = null;
    if (this.il != null) {
      values = this.il.getByteCode();
    }
    LineNumberTable lineNumberTable = null;
    LocalVariableTable localVariableTable = null;
    if (this.variable_vec.size() > 0 && !this.strip_attributes) {
      this.addCodeAttribute(localVariableTable = this.getLocalVariableTable(this.cp));
    }
    if (this.line_number_vec.size() > 0 && !this.strip_attributes) {
      this.addCodeAttribute(lineNumberTable = this.getLineNumberTable(this.cp));
    }
    Attribute[] codeAttributes = this.getCodeAttributes();
    int value3 = 0;
    int value4;
    for (int value5 = value4 = 0; value5 < codeAttributes.length; value5 = value4) {
      int length2 = codeAttributes[value4].getLength();
      int value6 = value3;
      value4++;
      value3 = value6 + length2 + (byte) 6;
    }
    CodeException[] codeExceptions;
    int value7 = (codeExceptions = this.getCodeExceptions()).length * 8;
    Code code = null;
    if (this.il != null && !this.isEnabled15() && !this.isEnabled14()) {
      Attribute[] attributes = this.getAttributes();
      int value8;
      for (int value9 = value8 = 0; value9 < attributes.length; value9 = ++value8) {
        Attribute attribute;
        if ((attribute = attributes[value8]) instanceof Code) {
          this.removeAttribute(attribute);
        }
      }
      code =
          new Code(
              this.cp.addUtf8("Code"),
              8 + values.length + 2 + value7 + 2 + value3,
              this.count2,
              this.count,
              values,
              codeExceptions,
              codeAttributes,
              this.cp.getConstantPool());
      this.addAttribute(code);
    }
    this.processConstantPoolGen2(this.cp);
    this.processConstantPoolGen(this.cp);
    ExceptionTable exceptionTable = null;
    if (this.throws_vec.size() > 0) {
      this.addAttribute(exceptionTable = this.getExceptionTable(this.cp));
    }
    Method method =
        new Method(
            this.access_flags, value, value2, this.getAttributes(), this.cp.getConstantPool());
    if (localVariableTable != null) {
      this.removeCodeAttribute(localVariableTable);
    }
    if (lineNumberTable != null) {
      this.removeCodeAttribute(lineNumberTable);
    }
    if (code != null) {
      this.removeAttribute(code);
    }
    if (exceptionTable != null) {
      this.removeAttribute(exceptionTable);
    }
    return method;
  }

  public void setReturnType(Type type) {
    this.setType(type);
  }

  public LocalVariableGen[] getLocalVariables() {
    int value;
    LocalVariableGen[] localVariableGens = new LocalVariableGen[value = this.variable_vec.size()];
    this.variable_vec.toArray(localVariableGens);
    int value2 = 0;
    for (int value3 = value2; value3 < value; value3 = ++value2) {
      if (localVariableGens[value2].getInstructionHandle() == null) {
        localVariableGens[value2].processInstructionHandle2(this.il.getStart());
      }
      if (localVariableGens[value2].getInstructionHandle2() == null) {
        localVariableGens[value2].processInstructionHandle(this.il.getEnd());
      }
    }
    if (value > 1) {
      processCount(localVariableGens, 0, value - 1);
    }
    return localVariableGens;
  }

  public LineNumberGen[] getLineNumbers() {
    LineNumberGen[] lineNumberGens = new LineNumberGen[this.line_number_vec.size()];
    this.line_number_vec.toArray(lineNumberGens);
    return lineNumberGens;
  }

  public List makeMutableVersion(AnnotationEntry[] annotationEntrys) {
    ArrayList values = new ArrayList();
    int value;
    for (int value2 = value = 0; value2 < annotationEntrys.length; value2 = value) {
      AnnotationEntry annotationEntry = annotationEntrys[value];
      ConstantPoolGen constantPool = this.getConstantPool();
      value++;
      values.add(new AnnotationEntryGen(annotationEntry, constantPool, false));
    }
    return values;
  }

  public void setCount2(int count2) {
    this.count2 = count2;
  }

  public void processConstantPoolGen2(ConstantPoolGen constantPoolGen) {
    int value;
    Attribute[] attributes;
    if ((attributes = Utility.createAttributes(constantPoolGen, this.arrayList)) != null) {
      for (int value2 = value = 0; value2 < attributes.length; value2 = value) {
        int value3 = value;
        Attribute[] attributes2 = attributes;
        value++;
        this.addAttribute(attributes2[value3]);
      }
    }
  }

  public void removeException(String text) {
    this.throws_vec.remove(text);
  }

  @Override
  public boolean equals(Object result) {
    return bcelComparator.equals(this, result);
  }

  public void removeLineNumber(LineNumberGen lineNumberGen) {
    this.line_number_vec.remove(lineNumberGen);
  }

  public void processMethodObserver2(MethodObserver methodObserver) {
    if (this.values != null) {
      this.values.remove(methodObserver);
    }
  }
}
