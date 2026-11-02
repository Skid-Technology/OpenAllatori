package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantFloat;
import com.allatori.lib.bcel.classfile.ConstantInteger;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class LDC extends CPInstruction
    implements PushInstruction, ExceptionThrower, TypedInstruction {

  public LDC(int value) {
    super((short) 19, value);
    this.setSize();
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    this.length = 2;
    this.index = byteSequence.readUnsignedByte();
  }

  @Override
  public final void setIndex(int value) {
    super.setIndex(value);
    this.setSize();
  }

  @Override
  public Class[] getExceptions() {
    return ExceptionConstants.TYPES;
  }

  public final void setSize() {
    if (this.index <= 255) {
      this.opcode = 18;
      this.length = 2;
    } else {
      this.opcode = 19;
      this.length = 3;
    }
  }

  public LDC() {}

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackProducer(this);
    visitor.visitPushInstruction(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitCPInstruction(this);
    visitor.visitLDC(this);
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    switch (constantPoolGen.getConstantPool().getConstant(this.index).getTag()) {
      case 3:
        return Type.INT;
      case 4:
        return Type.FLOAT;
      case 5:
      case 6:
      default:
        throw new RuntimeException(("Unknown or invalid constant type at " + this.index));
      case 7:
        return Type.CLASS;
      case 8:
        return Type.STRING;
    }
  }

  public Object getValue(ConstantPoolGen constantPoolGen) {
    Constant constant;
    switch ((constant = constantPoolGen.getConstantPool().getConstant(this.index)).getTag()) {
      case 3:
        return new Integer(((ConstantInteger) constant).getBytes());
      case 4:
        return new Float(((ConstantFloat) constant).getBytes());
      case 5:
      case 6:
      default:
        throw new RuntimeException(("Unknown or invalid constant type at " + this.index));
      case 7:
        return constant;
      case 8:
        int stringIndex = ((ConstantString) constant).getStringIndex();
        return ((ConstantUtf8) constantPoolGen.getConstantPool().getConstant(stringIndex))
            .getBytes();
    }
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.opcode);
    if (this.length == 2) {
      dataOutputStream.writeByte(this.index);
    } else {
      dataOutputStream.writeShort(this.index);
    }
  }
}
