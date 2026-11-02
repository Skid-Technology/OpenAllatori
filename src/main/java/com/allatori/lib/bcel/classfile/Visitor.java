package com.allatori.lib.bcel.classfile;

public interface Visitor {

  void visitEnclosingMethod(EnclosingMethod enclosingMethod);

  void visitStackMap(StackMap stackMap);

  void visitConstantValue(ConstantValue constantValue);

  void visitConstantNameAndType(ConstantNameAndType constantNameAndType);

  void visitInnerClasses(InnerClasses innerClasses);

  void visitInnerClass(InnerClass innerClass);

  void visitConstantPool(ConstantPool constantPool);

  void visitCode(Code code);

  void visitDeprecated(Deprecated deprecated);

  void visitConstantInterfaceMethodref(ConstantInterfaceMethodref constantInterfaceMethodref);

  void visitCodeException(CodeException codeException);

  void visitConstantClass(ConstantClass constantClass);

  void visitExceptionTable(ExceptionTable exceptionTable);

  void visitStackMapEntry(StackMapEntry stackMapEntry);

  void visitLineNumber(LineNumber lineNumber);

  void visitUnknown(Unknown unknown);

  void visitSourceFile(SourceFile sourceFile);

  void visitConstantMethodref(ConstantMethodref constantMethodref);

  void visitConstantString(ConstantString constantString);

  void visitConstantLong(ConstantLong constantLong);

  void visitAnnotationEntry(AnnotationEntry annotationEntry);

  void visitLineNumberTable(LineNumberTable lineNumberTable);

  void visitConstantUtf8(ConstantUtf8 constantUtf8);

  void visitLocalVariableTypeTable(LocalVariableTypeTable localVariableTypeTable);

  void visitSynthetic(Synthetic synthetic);

  void visitConstantFieldref(ConstantFieldref constantFieldref);

  void visitLocalVariableTable(LocalVariableTable localVariableTable);

  void visitAnnotation(Annotations annotations);

  void visitLocalVariable(LocalVariable localVariable);

  void visitAnnotationDefault(AnnotationDefault annotationDefault);

  void visitMethod(Method method);

  void visitJavaClass(JavaClass javaClass);

  void visitParameterAnnotation(ParameterAnnotations parameterAnnotations);

  void visitConstantInteger(ConstantInteger constantInteger);

  void visitConstantFloat(ConstantFloat constantFloat);

  void visitConstantDouble(ConstantDouble constantDouble);

  void visitSignature(Signature signature);

  void visitField(Field field);
}
