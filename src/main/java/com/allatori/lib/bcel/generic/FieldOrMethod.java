package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.ConstantCP;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantUtf8;

public abstract class FieldOrMethod extends CPInstruction implements LoadClass {

  public FieldOrMethod(short value, int value2) {
    super(value, value2);
  }

  public ReferenceType getReferenceType(ConstantPoolGen constantPoolGen) {
    ConstantPool constantPool;
    ConstantCP constantCP =
        (ConstantCP) (constantPool = constantPoolGen.getConstantPool()).getConstant(this.index);
    String text;
    if ((text = constantPool.createText(constantCP.getClass_index(), (byte) 7)).startsWith("[")) {
      return (ArrayType) Type.getType(text);
    }
    String text2 = text.replace('/', '.');
    return new ObjectType(text2);
  }

  /**
   * @deprecated
   */
  public ObjectType createObjectType(ConstantPoolGen constantPoolGen) {
    return new ObjectType(this.getText2(constantPoolGen));
  }

  @Override
  public ObjectType getLoadClassType(ConstantPoolGen constantPoolGen) {
    return this.createObjectType(constantPoolGen);
  }

  public FieldOrMethod() {}

  public String getText(ConstantPoolGen constantPoolGen) {
    ConstantPool constantPool;
    ConstantCP constantCP =
        (ConstantCP) (constantPool = constantPoolGen.getConstantPool()).getConstant(this.index);
    ConstantNameAndType constantNameAndType =
        (ConstantNameAndType) constantPool.getConstant(constantCP.getName_and_type_index());
    return ((ConstantUtf8) constantPool.getConstant(constantNameAndType.getName_index()))
        .getBytes();
  }

  /**
   * @deprecated
   */
  public String getText2(ConstantPoolGen constantPoolGen) {
    ConstantPool constantPool;
    ConstantCP constantCP =
        (ConstantCP) (constantPool = constantPoolGen.getConstantPool()).getConstant(this.index);
    String text;
    return (text = constantPool.createText(constantCP.getClass_index(), (byte) 7)).startsWith("[")
        ? "java.lang.Object"
        : text.replace('/', '.');
  }

  public String getText3(ConstantPoolGen constantPoolGen) {
    ConstantPool constantPool;
    ConstantCP constantCP =
        (ConstantCP) (constantPool = constantPoolGen.getConstantPool()).getConstant(this.index);
    ConstantNameAndType constantNameAndType =
        (ConstantNameAndType) constantPool.getConstant(constantCP.getName_and_type_index());
    return ((ConstantUtf8) constantPool.getConstant(constantNameAndType.getSignature_index()))
        .getBytes();
  }
}
