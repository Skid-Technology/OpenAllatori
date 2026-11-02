package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.ExceptionConstants;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public final class INVOKEINTERFACE extends InvokeInstruction {

  public int nargs;

  @Override
  public String toString(ConstantPool constantPool) {
    return "" + super.toString(constantPool) + " " + this.nargs;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackConsumer(this);
    visitor.visitStackProducer(this);
    visitor.visitLoadClass(this);
    visitor.visitCPInstruction(this);
    visitor.visitFieldOrMethod(this);
    visitor.visitInvokeInstruction(this);
    visitor.visitINVOKEINTERFACE(this);
  }

  @Override
  public int consumeStack(ConstantPoolGen constantPoolGen) {
    return this.nargs;
  }

  public INVOKEINTERFACE() {}

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    super.initFromFile(byteSequence, matches);
    this.length = 5;
    this.nargs = byteSequence.readUnsignedByte();
    byteSequence.readByte();
  }

  @Override
  public Class[] getExceptions() {
    Class[] classs = new Class[4 + ExceptionConstants.TYPES4.length];
    System.arraycopy(ExceptionConstants.TYPES4, 0, classs, 0, ExceptionConstants.TYPES4.length);
    classs[ExceptionConstants.TYPES4.length + 3] = ExceptionConstants.TYPE9;
    classs[ExceptionConstants.TYPES4.length + 2] = ExceptionConstants.TYPE7;
    classs[ExceptionConstants.TYPES4.length + 1] = ExceptionConstants.TYPE14;
    classs[ExceptionConstants.TYPES4.length] = ExceptionConstants.TYPE6;
    return classs;
  }

  public INVOKEINTERFACE(int value, int nargs) {
    super((short) 185, value);
    this.length = 5;
    if (nargs < 1) {
      throw new ClassGenException(("Number of arguments must be > 0 " + nargs));
    }
    this.nargs = nargs;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.opcode);
    dataOutputStream.writeShort(this.index);
    dataOutputStream.writeByte(this.nargs);
    dataOutputStream.writeByte(0);
  }

  public int getCount() {
    return this.nargs;
  }
}
