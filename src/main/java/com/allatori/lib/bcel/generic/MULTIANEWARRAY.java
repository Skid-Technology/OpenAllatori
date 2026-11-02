package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class MULTIANEWARRAY extends CPInstruction
    implements LoadClass, AllocationInstruction, ExceptionThrower {

  public short dimensions;

  @Override
  public String toString(ConstantPool constantPool) {
    return "" + super.toString(constantPool) + " " + this.dimensions;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.opcode);
    dataOutputStream.writeShort(this.index);
    dataOutputStream.writeByte(this.dimensions);
  }

  @Override
  public ObjectType getLoadClassType(ConstantPoolGen constantPoolGen) {
    Type type;
    if ((type = this.getType(constantPoolGen)) instanceof ArrayType) {
      type = ((ArrayType) type).getBasicType();
    }
    return type instanceof ObjectType ? (ObjectType) type : null;
  }

  public final short getDimensions() {
    return this.dimensions;
  }

  @Override
  public int consumeStack(ConstantPoolGen constantPoolGen) {
    return this.dimensions;
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    super.initFromFile(byteSequence, matches);
    this.dimensions = byteSequence.readByte();
    this.length = 4;
  }

  public MULTIANEWARRAY() {}

  @Override
  public String toString(boolean matches) {
    return "" + super.toString(matches) + " " + this.index + " " + this.dimensions;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLoadClass(this);
    visitor.visitAllocationInstruction(this);
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitCPInstruction(this);
    visitor.visitMULTIANEWARRAY(this);
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[2 + ExceptionConstants.TYPES5.length];
    System.arraycopy(ExceptionConstants.TYPES5, 0, classs, 0, ExceptionConstants.TYPES5.length);
    classs[ExceptionConstants.TYPES5.length + 1] = ExceptionConstants.TYPE11;
    classs[ExceptionConstants.TYPES5.length] = ExceptionConstants.TYPE7;
    return classs;
  }

  public MULTIANEWARRAY(int value, short dimensions) {
    super((short) 197, value);
    if (dimensions < 1) {
      throw new ClassGenException(("Invalid dimensions value: " + dimensions));
    }
    this.dimensions = dimensions;
    this.length = 4;
  }
}
