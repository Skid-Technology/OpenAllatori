package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class SourceFile extends Attribute {

  public int sourcefile_index;

  public SourceFile(int value, int value2, int sourcefile_index, ConstantPool constantPool) {
    super((byte) 0, value, value2, constantPool);
    this.sourcefile_index = sourcefile_index;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    return (SourceFile) this.clone();
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitSourceFile(this);
  }

  public SourceFile(SourceFile sourceFile) {
    this(
        sourceFile.getName_index(),
        sourceFile.getLength(),
        sourceFile.getSourceFileIndex(),
        sourceFile.getConstantPool());
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.sourcefile_index);
  }

  public final String getText() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.sourcefile_index, (byte) 1))
        .getBytes();
  }

  public SourceFile(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, dataInputStream.readUnsignedShort(), constantPool);
  }

  public final void setSourceFileIndex(int sourcefile_index) {
    this.sourcefile_index = sourcefile_index;
  }

  public final int getSourceFileIndex() {
    return this.sourcefile_index;
  }
}
