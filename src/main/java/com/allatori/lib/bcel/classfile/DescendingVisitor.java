package com.allatori.lib.bcel.classfile;

import java.util.Stack;

public class DescendingVisitor implements Visitor {

  public Stack stack;

  public Visitor visitor;

  public JavaClass clazz;

  public void visit() {
    this.clazz.accept(this);
  }

  @Override
  public void visitLocalVariableTypeTable(LocalVariableTypeTable localVariableTypeTable) {
    this.stack.push(localVariableTypeTable);
    localVariableTypeTable.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitUnknown(Unknown unknown) {
    this.stack.push(unknown);
    unknown.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitSignature(Signature signature) {
    this.stack.push(signature);
    signature.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitConstantInterfaceMethodref(
      ConstantInterfaceMethodref constantInterfaceMethodref) {
    this.stack.push(constantInterfaceMethodref);
    constantInterfaceMethodref.accept(this.visitor);
    this.stack.pop();
  }

  public Object predecessor() {
    return this.predecessor(0);
  }

  public DescendingVisitor(JavaClass javaClass, Visitor visitor2) {
    super();
    this.stack = new Stack();
    this.clazz = javaClass;
    this.visitor = visitor2;
  }

  @Override
  public void visitConstantString(ConstantString constantString) {
    this.stack.push(constantString);
    constantString.accept(this.visitor);
    this.stack.pop();
  }

  public Object current() {
    return this.stack.peek();
  }

  @Override
  public void visitLineNumberTable(LineNumberTable lineNumberTable) {
    this.stack.push(lineNumberTable);
    lineNumberTable.accept(this.visitor);
    LineNumber[] lineNumberTable2 = lineNumberTable.getLineNumberTable();
    int value = 0;
    for (int value2 = value; value2 < lineNumberTable2.length; value2 = value) {
      int value3 = value;
      value++;
      lineNumberTable2[value3].accept(this);
    }
    this.stack.pop();
  }

  @Override
  public void visitSynthetic(Synthetic synthetic) {
    this.stack.push(synthetic);
    synthetic.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitConstantFieldref(ConstantFieldref constantFieldref) {
    this.stack.push(constantFieldref);
    constantFieldref.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitConstantPool(ConstantPool constantPool) {
    this.stack.push(constantPool);
    constantPool.accept(this.visitor);
    Constant[] constantPool2 = constantPool.getConstantPool();
    int value = 1;
    for (int value2 = value; value2 < constantPool2.length; value2 = ++value) {
      if (constantPool2[value] != null) {
        constantPool2[value].accept(this);
      }
    }
    this.stack.pop();
  }

  @Override
  public void visitConstantClass(ConstantClass constantClass) {
    this.stack.push(constantClass);
    constantClass.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitLineNumber(LineNumber lineNumber) {
    this.stack.push(lineNumber);
    lineNumber.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitParameterAnnotation(ParameterAnnotations parameterAnnotations) {
    this.stack.push(parameterAnnotations);
    parameterAnnotations.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitStackMap(StackMap stackMap) {
    this.stack.push(stackMap);
    stackMap.accept(this.visitor);
    StackMapEntry[] stackMap2 = stackMap.getStackMap();
    int value = 0;
    for (int value2 = value; value2 < stackMap2.length; value2 = value) {
      int value3 = value;
      value++;
      stackMap2[value3].accept(this);
    }
    this.stack.pop();
  }

  @Override
  public void visitCodeException(CodeException codeException) {
    this.stack.push(codeException);
    codeException.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitAnnotation(Annotations annotations) {
    this.stack.push(annotations);
    annotations.accept(this.visitor);
    AnnotationEntry[] annotationEntries = annotations.getAnnotationEntries();
    int value = 0;
    for (int value2 = value; value2 < annotationEntries.length; value2 = value) {
      int value3 = value;
      value++;
      annotationEntries[value3].accept(this);
    }
    this.stack.pop();
  }

  @Override
  public void visitStackMapEntry(StackMapEntry stackMapEntry) {
    this.stack.push(stackMapEntry);
    stackMapEntry.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitConstantMethodref(ConstantMethodref constantMethodref) {
    this.stack.push(constantMethodref);
    constantMethodref.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitConstantFloat(ConstantFloat constantFloat) {
    this.stack.push(constantFloat);
    constantFloat.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitDeprecated(Deprecated deprecated) {
    this.stack.push(deprecated);
    deprecated.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitLocalVariableTable(LocalVariableTable localVariableTable) {
    this.stack.push(localVariableTable);
    localVariableTable.accept(this.visitor);
    LocalVariable[] localVariableTable2 = localVariableTable.getLocalVariableTable();
    int value = 0;
    for (int value2 = value; value2 < localVariableTable2.length; value2 = value) {
      int value3 = value;
      value++;
      localVariableTable2[value3].accept(this);
    }
    this.stack.pop();
  }

  @Override
  public void visitConstantNameAndType(ConstantNameAndType constantNameAndType) {
    this.stack.push(constantNameAndType);
    constantNameAndType.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitConstantDouble(ConstantDouble constantDouble) {
    this.stack.push(constantDouble);
    constantDouble.accept(this.visitor);
    this.stack.pop();
  }

  public Object predecessor(int value) {
    int value2;
    return (value2 = this.stack.size()) >= 2 && value >= 0
        ? this.stack.elementAt(value2 - (value + 2))
        : null;
  }

  @Override
  public void visitLocalVariable(LocalVariable localVariable) {
    this.stack.push(localVariable);
    localVariable.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitInnerClass(InnerClass innerClass) {
    this.stack.push(innerClass);
    innerClass.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitSourceFile(SourceFile sourceFile) {
    this.stack.push(sourceFile);
    sourceFile.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitCode(Code code) {
    this.stack.push(code);
    code.accept(this.visitor);
    CodeException[] exceptionTable = code.getExceptionTable();
    int value = 0;
    for (int value2 = value; value2 < exceptionTable.length; value2 = value) {
      int value3 = value;
      value++;
      exceptionTable[value3].accept(this);
    }
    Attribute[] attributes = code.getAttributes();
    int value4;
    for (int value5 = value4 = 0; value5 < attributes.length; value5 = value4) {
      int value6 = value4;
      value4++;
      attributes[value6].accept(this);
    }
    this.stack.pop();
  }

  @Override
  public void visitJavaClass(JavaClass javaClass) {
    this.stack.push(javaClass);
    javaClass.accept(this.visitor);
    Field[] fields = javaClass.getFields();
    int value = 0;
    for (int value2 = value; value2 < fields.length; value2 = value) {
      int value3 = value;
      value++;
      fields[value3].accept(this);
    }
    Method[] methods = javaClass.getMethods();
    int value4;
    for (int value5 = value4 = 0; value5 < methods.length; value5 = value4) {
      int value6 = value4;
      value4++;
      methods[value6].accept(this);
    }
    Attribute[] attributes = javaClass.getAttributes();
    int value7;
    for (int value8 = value7 = 0; value8 < attributes.length; value8 = value7) {
      int value9 = value7;
      value7++;
      attributes[value9].accept(this);
    }
    javaClass.getConstantPool().accept(this);
    this.stack.pop();
  }

  @Override
  public void visitInnerClasses(InnerClasses innerClasses) {
    this.stack.push(innerClasses);
    innerClasses.accept(this.visitor);
    InnerClass[] innerClasses2 = innerClasses.getInnerClasses();
    int value = 0;
    for (int value2 = value; value2 < innerClasses2.length; value2 = value) {
      int value3 = value;
      value++;
      innerClasses2[value3].accept(this);
    }
    this.stack.pop();
  }

  @Override
  public void visitConstantUtf8(ConstantUtf8 constantUtf8) {
    this.stack.push(constantUtf8);
    constantUtf8.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitConstantValue(ConstantValue constantValue) {
    this.stack.push(constantValue);
    constantValue.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitField(Field field) {
    this.stack.push(field);
    field.accept(this.visitor);
    Attribute[] attributes = field.getAttributes();
    int value = 0;
    for (int value2 = value; value2 < attributes.length; value2 = value) {
      int value3 = value;
      value++;
      attributes[value3].accept(this);
    }
    this.stack.pop();
  }

  @Override
  public void visitConstantInteger(ConstantInteger constantInteger) {
    this.stack.push(constantInteger);
    constantInteger.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitConstantLong(ConstantLong constantLong) {
    this.stack.push(constantLong);
    constantLong.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitEnclosingMethod(EnclosingMethod enclosingMethod) {
    this.stack.push(enclosingMethod);
    enclosingMethod.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitAnnotationEntry(AnnotationEntry annotationEntry) {
    this.stack.push(annotationEntry);
    annotationEntry.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitAnnotationDefault(AnnotationDefault annotationDefault) {
    this.stack.push(annotationDefault);
    annotationDefault.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitExceptionTable(ExceptionTable exceptionTable) {
    this.stack.push(exceptionTable);
    exceptionTable.accept(this.visitor);
    this.stack.pop();
  }

  @Override
  public void visitMethod(Method method) {
    this.stack.push(method);
    method.accept(this.visitor);
    Attribute[] attributes = method.getAttributes();
    int value = 0;
    for (int value2 = value; value2 < attributes.length; value2 = value) {
      int value3 = value;
      value++;
      attributes[value3].accept(this);
    }
    this.stack.pop();
  }
}
