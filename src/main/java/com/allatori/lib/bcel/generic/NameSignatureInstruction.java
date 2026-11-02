package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.ConstantCP;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantUtf8;

public abstract class NameSignatureInstruction extends CPInstruction {

  public NameSignatureInstruction(short value, int value2) {
    super(value, value2);
  }

  public NameSignatureInstruction() {}

  public String getText(ConstantPoolGen constantPoolGen) {
    ConstantPool constantPool = constantPoolGen.getConstantPool();
    ConstantNameAndType nameAndType = this.getNameAndType(constantPoolGen);
    return ((ConstantUtf8) constantPool.getConstant(nameAndType.getSignature_index())).getBytes();
  }

  public String getText2(ConstantPoolGen constantPoolGen) {
    ConstantPool constantPool = constantPoolGen.getConstantPool();
    ConstantNameAndType nameAndType = this.getNameAndType(constantPoolGen);
    return ((ConstantUtf8) constantPool.getConstant(nameAndType.getName_index())).getBytes();
  }

  public ConstantNameAndType getNameAndType(ConstantPoolGen constantPoolGen) {
    ConstantPool constantPool;
    ConstantCP constantCP =
        (ConstantCP) (constantPool = constantPoolGen.getConstantPool()).getConstant(this.index);
    return (ConstantNameAndType) constantPool.getConstant(constantCP.getName_and_type_index());
  }
}
