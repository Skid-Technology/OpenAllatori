package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.ExceptionConstants;
import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public class NEWARRAY extends Instruction
    implements AllocationInstruction, ExceptionThrower, StackProducer {

  public byte type;

  public NEWARRAY(BasicType basicType) {
    this(basicType.getType());
  }

  public NEWARRAY(byte type) {
    super((short) 188, (short) 2);
    this.type = type;
  }

  @Override
  public Class[] getExceptions() {
    return new Class[] {ExceptionConstants.TYPE11};
  }

  @Override
  public String toString(boolean matches) {
    return "" + super.toString(matches) + " " + Constants.TEXTS[this.type];
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitAllocationInstruction(this);
    visitor.visitExceptionThrower(this);
    visitor.visitStackProducer(this);
    visitor.visitNEWARRAY(this);
  }

  public final byte getTypecode() {
    return this.type;
  }

  public final Type getType() {
    return new ArrayType(BasicType.getType(this.type), 1);
  }

  public NEWARRAY() {}

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.opcode);
    dataOutputStream.writeByte(this.type);
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    this.type = byteSequence.readByte();
    this.length = 2;
  }
}
