package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantPool;
import java.util.StringTokenizer;

public abstract class InvokeInstruction extends FieldOrMethod
    implements ExceptionThrower, TypedInstruction, StackConsumer, StackProducer {

  public Type getType2(ConstantPoolGen constantPoolGen) {
    return Type.getReturnType(this.getText3(constantPoolGen));
  }

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return this.getType2(constantPoolGen);
  }

  @Override
  public String toString(ConstantPool constantPool) {
    Constant constant = constantPool.getConstant(this.index);
    StringTokenizer stringTokenizer = new StringTokenizer(constantPool.constantToString(constant));
    return ""
        + Constants.TEXTS8[this.opcode]
        + " "
        + stringTokenizer.nextToken().replace('.', '/')
        + stringTokenizer.nextToken();
  }

  public InvokeInstruction() {}

  public String getText4(ConstantPoolGen constantPoolGen) {
    return this.getText(constantPoolGen);
  }

  public Type[] getArgumentTypes(ConstantPoolGen constantPoolGen) {
    return Type.getArgumentTypes(this.getText3(constantPoolGen));
  }

  public InvokeInstruction(short value, int value2) {
    super(value, value2);
  }

  @Override
  public int consumeStack(ConstantPoolGen constantPoolGen) {
    Type[] argumentTypes = Type.getArgumentTypes(this.getText3(constantPoolGen));
    int value;
    Type[] types;
    if (this.opcode == 184) {
      value = 0;
      types = argumentTypes;
    } else {
      value = 1;
      types = argumentTypes;
    }
    int value2 = types.length;
    int value3;
    for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
      Type type = argumentTypes[value3];
      int value5 = value;
      value3++;
      value = value5 + type.getSize();
    }
    return value;
  }

  @Override
  public int produceStack(ConstantPoolGen constantPoolGen) {
    return this.getType2(constantPoolGen).getSize();
  }
}
