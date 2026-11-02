package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class LineNumber implements Cloneable, Node, Serializable {

  public int start_pc;

  public int line_number;

  public final void setLine_number(int line_number) {
    this.line_number = line_number;
  }

  public LineNumber(DataInputStream dataInputStream) throws IOException {
    this(dataInputStream.readUnsignedShort(), dataInputStream.readUnsignedShort());
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitLineNumber(this);
  }

  public final int getLine_number() {
    return this.line_number;
  }

  public LineNumber(int start_pc, int line_number) {
    super();
    this.start_pc = start_pc;
    this.line_number = line_number;
  }

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(this.start_pc);
    dataOutputStream.writeShort(this.line_number);
  }

  public LineNumber copy() {
    try {
      return (LineNumber) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }

  public LineNumber(LineNumber lineNumber) {
    this(lineNumber.getStart_pc(), lineNumber.getLine_number());
  }

  public final int getStart_pc() {
    return this.start_pc;
  }

  public final void setStart_pc(int start_pc) {
    this.start_pc = start_pc;
  }
}
