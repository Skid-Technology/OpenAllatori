package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.Constants;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class CodeException implements Cloneable, Constants, Node, Serializable {

  public int handler_pc;

  public int end_pc;

  public int start_pc;

  public int catch_type;

  public CodeException(int start_pc, int end_pc, int handler_pc, int catch_type) {
    super();
    this.start_pc = start_pc;
    this.end_pc = end_pc;
    this.handler_pc = handler_pc;
    this.catch_type = catch_type;
  }

  public final int getStart_pc() {
    return this.start_pc;
  }

  public CodeException(CodeException codeException) {
    this(
        codeException.getStart_pc(),
        codeException.getEnd_pc(),
        codeException.getHandler_pc(),
        codeException.getCatch_type());
  }

  public CodeException copy() {
    try {
      return (CodeException) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }

  public final int getEnd_pc() {
    return this.end_pc;
  }

  public final int getCatch_type() {
    return this.catch_type;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitCodeException(this);
  }

  public final int getHandler_pc() {
    return this.handler_pc;
  }

  public final void setEnd_pc(int end_pc) {
    this.end_pc = end_pc;
  }

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(this.start_pc);
    dataOutputStream.writeShort(this.end_pc);
    dataOutputStream.writeShort(this.handler_pc);
    dataOutputStream.writeShort(this.catch_type);
  }

  public final void setStart_pc(int start_pc) {
    this.start_pc = start_pc;
  }

  public final String toString(ConstantPool constantPool, boolean matches) {
    String text;
    if (this.catch_type == 0) {
      text = "<Any exception>(0)";
    } else {
      text =
          ""
              + Utility.getText(constantPool.createText(this.catch_type, (byte) 7), false)
              + (matches
                  ? new StringBuilder()
                      .insert(0, "(")
                      .append(this.catch_type)
                      .append(")")
                      .toString()
                  : "");
    }
    return this.start_pc + "\t" + this.end_pc + "\t" + this.handler_pc + "\t" + text;
  }

  public final void setHandler_pc(int handler_pc) {
    this.handler_pc = handler_pc;
  }

  public final String toString(ConstantPool constantPool) {
    return this.toString(constantPool, true);
  }

  public final void setCatch_type(int catch_type) {
    this.catch_type = catch_type;
  }

  public CodeException(DataInputStream dataInputStream) throws IOException {
    this(
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort(),
        dataInputStream.readUnsignedShort());
  }
}
