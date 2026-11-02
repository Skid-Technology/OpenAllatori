package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class CPInstruction extends Instruction
    implements TypedInstruction, IndexedInstruction {

  public int index;

  @Override
  public final int getIndex() {
    return this.index;
  }

  public CPInstruction() {}

  @Override
  public String toString(boolean matches) {
    return "" + super.toString(matches) + " " + this.index;
  }

  @Override
  public void setIndex(int index) {
    if (index < 0) {
      throw new ClassGenException(("Negative index value: " + index));
    }
    this.index = index;
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.opcode);
    dataOutputStream.writeShort(this.index);
  }

  public CPInstruction(short value, int value2) {
    super(value, (short) 3);
    this.setIndex(value2);
  }

  @Override
  public String toString(ConstantPool constantPool) {
    Constant constant;
    String text = constantPool.constantToString(constant = constantPool.getConstant(this.index));
    if (constant instanceof ConstantClass) {
      text = text.replace('.', '/');
    }
    return "" + Constants.TEXTS8[this.opcode] + " " + text;
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    this.setIndex(byteSequence.readUnsignedShort());
    this.length = 3;
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    String text;
    if (!(text = constantPoolGen.getConstantPool().createText(this.index, (byte) 7))
        .startsWith("[")) {
      text = "L" + text + ";";
    }
    return Type.getType(text);
  }
}
