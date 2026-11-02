package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.classfile.Code;
import com.allatori.lib.bcel.classfile.CodeException;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantDouble;
import com.allatori.lib.bcel.classfile.ConstantFieldref;
import com.allatori.lib.bcel.classfile.ConstantFloat;
import com.allatori.lib.bcel.classfile.ConstantInteger;
import com.allatori.lib.bcel.classfile.ConstantInterfaceMethodref;
import com.allatori.lib.bcel.classfile.ConstantLong;
import com.allatori.lib.bcel.classfile.ConstantMethodref;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ConstantValue;
import com.allatori.lib.bcel.classfile.Deprecated;
import com.allatori.lib.bcel.classfile.EmptyVisitor;
import com.allatori.lib.bcel.classfile.ExceptionTable;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.InnerClass;
import com.allatori.lib.bcel.classfile.InnerClasses;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.LineNumber;
import com.allatori.lib.bcel.classfile.LineNumberTable;
import com.allatori.lib.bcel.classfile.LocalVariable;
import com.allatori.lib.bcel.classfile.LocalVariableTable;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.Node;
import com.allatori.lib.bcel.classfile.Signature;
import com.allatori.lib.bcel.classfile.SourceFile;
import com.allatori.lib.bcel.classfile.StackMap;
import com.allatori.lib.bcel.classfile.Synthetic;
import com.allatori.lib.bcel.classfile.Unknown;
import com.allatori.lib.bcel.classfile.Visitor;

public class StringRepresentation extends EmptyVisitor implements Visitor {

  public Node n;

  public String tostring;

  @Override
  public void visitJavaClass(JavaClass javaClass) {
    this.tostring = this.toString(javaClass);
  }

  @Override
  public void visitCode(Code code) {
    this.tostring = "<CODE>";
  }

  @Override
  public void visitLineNumber(LineNumber lineNumber) {
    this.tostring = this.toString(lineNumber);
  }

  @Override
  public void visitConstantValue(ConstantValue constantValue) {
    this.tostring = this.toString(constantValue);
  }

  @Override
  public void visitLocalVariableTable(LocalVariableTable localVariableTable) {
    this.tostring = "<LocalVariableTable: " + this.toString(localVariableTable) + ">";
  }

  @Override
  public void visitConstantLong(ConstantLong constantLong) {
    this.tostring = this.toString(constantLong);
  }

  @Override
  public void visitField(Field field) {
    this.tostring = this.toString(field);
  }

  @Override
  public void visitSourceFile(SourceFile sourceFile) {
    this.tostring = this.toString(sourceFile);
  }

  @Override
  public void visitConstantFieldref(ConstantFieldref constantFieldref) {
    this.tostring = this.toString(constantFieldref);
  }

  @Override
  public void visitConstantClass(ConstantClass constantClass) {
    this.tostring = this.toString(constantClass);
  }

  @Override
  public void visitConstantPool(ConstantPool constantPool) {
    this.tostring = this.toString(constantPool);
  }

  @Override
  public void visitConstantInteger(ConstantInteger constantInteger) {
    this.tostring = this.toString(constantInteger);
  }

  @Override
  public void visitStackMap(StackMap stackMap) {
    this.tostring = this.toString(stackMap);
  }

  @Override
  public void visitMethod(Method method) {
    this.tostring = this.toString(method);
  }

  @Override
  public void visitInnerClass(InnerClass innerClass) {
    this.tostring = this.toString(innerClass);
  }

  @Override
  public void visitConstantNameAndType(ConstantNameAndType constantNameAndType) {
    this.tostring = this.toString(constantNameAndType);
  }

  @Override
  public void visitConstantMethodref(ConstantMethodref constantMethodref) {
    this.tostring = this.toString(constantMethodref);
  }

  public String toString(Node node) {
    try {
      return node.toString();
    } catch (RuntimeException runtimeException) {
      String name = node.getClass().getName();
      String text = name.substring(name.lastIndexOf(".") + 1);
      return "<<" + text + ">>";
    }
  }

  @Override
  public void visitLocalVariable(LocalVariable localVariable) {
    this.tostring = this.toString(localVariable);
  }

  @Override
  public void visitConstantFloat(ConstantFloat constantFloat) {
    this.tostring = this.toString(constantFloat);
  }

  @Override
  public void visitConstantUtf8(ConstantUtf8 constantUtf8) {
    this.tostring = this.toString(constantUtf8);
  }

  @Override
  public void visitUnknown(Unknown unknown) {
    this.tostring = this.toString(unknown);
  }

  @Override
  public void visitExceptionTable(ExceptionTable exceptionTable) {
    this.tostring = this.toString(exceptionTable);
  }

  @Override
  public void visitConstantString(ConstantString constantString) {
    this.tostring = this.toString(constantString);
  }

  @Override
  public void visitDeprecated(Deprecated deprecated) {
    this.tostring = this.toString(deprecated);
  }

  public StringRepresentation(Node node) {
    super();
    this.n = node;
    node.accept(this);
  }

  @Override
  public void visitInnerClasses(InnerClasses innerClasses) {
    this.tostring = this.toString(innerClasses);
  }

  @Override
  public void visitCodeException(CodeException codeException) {
    this.tostring = this.toString(codeException);
  }

  @Override
  public void visitConstantDouble(ConstantDouble constantDouble) {
    this.tostring = this.toString(constantDouble);
  }

  @Override
  public void visitLineNumberTable(LineNumberTable lineNumberTable) {
    this.tostring = "<LineNumberTable: " + this.toString(lineNumberTable) + ">";
  }

  @Override
  public void visitSignature(Signature signature) {
    this.tostring = this.toString(signature);
  }

  @Override
  public void visitConstantInterfaceMethodref(
      ConstantInterfaceMethodref constantInterfaceMethodref) {
    this.tostring = this.toString(constantInterfaceMethodref);
  }

  @Override
  public void visitSynthetic(Synthetic synthetic) {
    this.tostring = this.toString(synthetic);
  }
}
