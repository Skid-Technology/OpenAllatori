package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.classfile.ClassFormatException;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantFieldref;
import com.allatori.lib.bcel.classfile.ConstantInterfaceMethodref;
import com.allatori.lib.bcel.classfile.ConstantMethodref;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.EmptyVisitor;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.Visitor;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.verifier.exc.LoadingException;

public class Pass2Verifier$FAMRAV_Visitor extends EmptyVisitor implements Visitor {

  public final ConstantPool cp;

  public final Pass2Verifier this$0;

  public Pass2Verifier$FAMRAV_Visitor(
      Pass2Verifier pass2Verifier, JavaClass javaClass, Pass2Verifier$1 pass2Verifier$1) {
    this(pass2Verifier, javaClass);
  }

  @Override
  public void visitConstantFieldref(ConstantFieldref constantFieldref) {
    if (constantFieldref.getTag() != 9) {
      throw new LoadingException(
          ("ConstantFieldref '" + Pass2Verifier.getText(constantFieldref) + "' has wrong tag!"));
    }
    int name_and_type_index = constantFieldref.getName_and_type_index();
    ConstantNameAndType constantNameAndType =
        (ConstantNameAndType) this.cp.getConstant(name_and_type_index);
    String text;
    if (!Pass2Verifier.matchesText(
        text =
            ((ConstantUtf8) this.cp.getConstant(constantNameAndType.getName_index())).getBytes())) {
      throw new LoadingException(
          ("Invalid field name '"
              + text
              + "' referenced by '"
              + Pass2Verifier.getText(constantFieldref)
              + "'."));
    }
    int class_index = constantFieldref.getClass_index();
    ConstantClass constantClass = (ConstantClass) this.cp.getConstant(class_index);
    String text2;
    if (!Pass2Verifier.matchesText2(
        text2 = ((ConstantUtf8) this.cp.getConstant(constantClass.getNameIndex())).getBytes())) {
      throw new LoadingException(
          ("Illegal class name '"
              + text2
              + "' used by '"
              + Pass2Verifier.getText(constantFieldref)
              + "'."));
    }
    String bytes =
        ((ConstantUtf8) this.cp.getConstant(constantNameAndType.getSignature_index())).getBytes();
    try {
      Type.getType(bytes);
    } catch (ClassFormatException classFormatException) {
      throw new LoadingException(
          ("Illegal descriptor (==signature) '"
              + bytes
              + "' used by '"
              + Pass2Verifier.getText(constantFieldref)
              + "'."));
    }
  }

  @Override
  public void visitConstantMethodref(ConstantMethodref constantMethodref) {
    if (constantMethodref.getTag() != 10) {
      throw new LoadingException(
          ("ConstantMethodref '" + Pass2Verifier.getText(constantMethodref) + "' has wrong tag!"));
    }
    int name_and_type_index = constantMethodref.getName_and_type_index();
    ConstantNameAndType constantNameAndType =
        (ConstantNameAndType) this.cp.getConstant(name_and_type_index);
    String text;
    if (!Pass2Verifier.matchesText3(
        text =
            ((ConstantUtf8) this.cp.getConstant(constantNameAndType.getName_index())).getBytes())) {
      throw new LoadingException(
          ("Invalid (non-interface) method name '"
              + text
              + "' referenced by '"
              + Pass2Verifier.getText(constantMethodref)
              + "'."));
    }
    int class_index = constantMethodref.getClass_index();
    ConstantClass constantClass = (ConstantClass) this.cp.getConstant(class_index);
    String text2;
    if (!Pass2Verifier.matchesText2(
        text2 = ((ConstantUtf8) this.cp.getConstant(constantClass.getNameIndex())).getBytes())) {
      throw new LoadingException(
          ("Illegal class name '"
              + text2
              + "' used by '"
              + Pass2Verifier.getText(constantMethodref)
              + "'."));
    }
    String bytes =
        ((ConstantUtf8) this.cp.getConstant(constantNameAndType.getSignature_index())).getBytes();
    try {
      Type returnType = Type.getReturnType(bytes);
      if (text.equals("<init>") && returnType != Type.VOID) {
        throw new LoadingException("Instance initialization method must have VOID return type.");
      }
    } catch (ClassFormatException classFormatException) {
      throw new LoadingException(
          ("Illegal descriptor (==signature) '"
              + bytes
              + "' used by '"
              + Pass2Verifier.getText(constantMethodref)
              + "'."));
    }
  }

  @Override
  public void visitConstantInterfaceMethodref(
      ConstantInterfaceMethodref constantInterfaceMethodref) {
    if (constantInterfaceMethodref.getTag() != 11) {
      throw new LoadingException(
          ("ConstantInterfaceMethodref '"
              + Pass2Verifier.getText(constantInterfaceMethodref)
              + "' has wrong tag!"));
    }
    int name_and_type_index = constantInterfaceMethodref.getName_and_type_index();
    ConstantNameAndType constantNameAndType =
        (ConstantNameAndType) this.cp.getConstant(name_and_type_index);
    String text;
    if (!Pass2Verifier.matchesText10(
        text =
            ((ConstantUtf8) this.cp.getConstant(constantNameAndType.getName_index())).getBytes())) {
      throw new LoadingException(
          ("Invalid (interface) method name '"
              + text
              + "' referenced by '"
              + Pass2Verifier.getText(constantInterfaceMethodref)
              + "'."));
    }
    int class_index = constantInterfaceMethodref.getClass_index();
    ConstantClass constantClass = (ConstantClass) this.cp.getConstant(class_index);
    String text2;
    if (!Pass2Verifier.matchesText2(
        text2 = ((ConstantUtf8) this.cp.getConstant(constantClass.getNameIndex())).getBytes())) {
      throw new LoadingException(
          ("Illegal class name '"
              + text2
              + "' used by '"
              + Pass2Verifier.getText(constantInterfaceMethodref)
              + "'."));
    }
    String bytes =
        ((ConstantUtf8) this.cp.getConstant(constantNameAndType.getSignature_index())).getBytes();
    try {
      Type returnType = Type.getReturnType(bytes);
      if (text.equals("<clinit>") && returnType != Type.VOID) {
        this.this$0.addMessage(
            "Class or interface initialization method '<clinit>' usually has VOID return type"
                + " instead of '"
                + returnType
                + "'. Note this is really not a requirement of The Java Virtual Machine"
                + " Specification, Second Edition.");
      }
    } catch (ClassFormatException classFormatException) {
      throw new LoadingException(
          ("Illegal descriptor (==signature) '"
              + bytes
              + "' used by '"
              + Pass2Verifier.getText(constantInterfaceMethodref)
              + "'."));
    }
  }

  public Pass2Verifier$FAMRAV_Visitor(Pass2Verifier pass2Verifier, JavaClass javaClass) {
    super();
    this.this$0 = pass2Verifier;
    this.cp = javaClass.getConstantPool();
  }
}
