package com.allatori.internal;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.ExceptionConstants;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantInvokeDynamic;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.ExceptionThrower;
import com.allatori.lib.bcel.generic.NameSignatureInstruction;
import com.allatori.lib.bcel.generic.StackConsumer;
import com.allatori.lib.bcel.generic.StackProducer;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.generic.Visitor;
import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;

public class NameSignatureInstructionSupport extends NameSignatureInstruction
    implements ExceptionThrower, StackConsumer, StackProducer {

  public ConstantInvokeDynamic getConstantInvokeDynamic(ConstantPoolGen constantPoolGen) {
    return (ConstantInvokeDynamic) constantPoolGen.getConstantPool().getConstant(this.index);
  }

  public Type[] getTypes(ConstantPoolGen constantPoolGen) {
    return Type.getArgumentTypes(this.getText(constantPoolGen));
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitExceptionThrower(this);
    visitor.visitTypedInstruction(this);
    visitor.visitStackConsumer(this);
    visitor.visitStackProducer(this);
    visitor.visitCPInstruction(this);
  }

  @Override
  public int produceStack(ConstantPoolGen constantPoolGen) {
    return Type.calculateCount(this.getText(constantPoolGen));
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    super.initFromFile(byteSequence, matches);
    this.length = 5;
    byteSequence.readUnsignedShort();
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

  @Override
  public int consumeStack(ConstantPoolGen constantPoolGen) {
    return Type.calculateCount2(this.getText(constantPoolGen));
  }

  public Type getType2(ConstantPoolGen constantPoolGen) {
    return Type.getReturnType(this.getText(constantPoolGen));
  }

  public NameSignatureInstructionSupport() {}

  @Override
  public Type getType(ConstantPoolGen constantPoolGen) {
    return this.getType2(constantPoolGen);
  }

  public String getText3(ConstantPoolGen constantPoolGen) {
    return this.getText2(constantPoolGen);
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.opcode);
    dataOutputStream.writeShort(this.index);
    dataOutputStream.writeByte(0);
    dataOutputStream.writeByte(0);
  }

  @Override
  public ConstantNameAndType getNameAndType(ConstantPoolGen constantPoolGen) {
    ConstantPool constantPool = constantPoolGen.getConstantPool();
    ConstantInvokeDynamic constantInvokeDynamic = this.getConstantInvokeDynamic(constantPoolGen);
    return (ConstantNameAndType)
        constantPool.getConstant(constantInvokeDynamic.getBootstrapMethodAttrIndex2());
  }

  public NameSignatureInstructionSupport(short value, int value2) {
    super(value, value2);
  }
}
