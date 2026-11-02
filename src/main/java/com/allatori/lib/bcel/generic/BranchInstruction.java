package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class BranchInstruction extends Instruction implements InstructionTargeter {

  public InstructionHandle target;

  public int count;

  public int index;

  public int getTargetOffset() {
    return this.getTargetOffset(this.target);
  }

  @Override
  public void updateTarget(
      InstructionHandle instructionHandle, InstructionHandle instructionHandle2) {
    if (this.target == instructionHandle) {
      this.setTarget(instructionHandle2);
    } else {
      throw new ClassGenException(("Not targeting " + instructionHandle + ", but " + this.target));
    }
  }

  public static final void notifyTarget(
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2,
      InstructionTargeter instructionTargeter) {
    if (instructionHandle != null) {
      instructionHandle.removeTargeter(instructionTargeter);
    }
    if (instructionHandle2 != null) {
      instructionHandle2.addTargeter(instructionTargeter);
    }
  }

  public void setTarget(InstructionHandle instructionHandle) {
    notifyTarget(this.target, instructionHandle, this);
    this.target = instructionHandle;
  }

  public InstructionHandle getTarget() {
    return this.target;
  }

  @Override
  public boolean containsTarget(InstructionHandle instructionHandle) {
    return this.target == instructionHandle;
  }

  @Override
  public void dispose() {
    this.setTarget(null);
    this.index = -1;
    this.count = -1;
  }

  public int updatePosition(int count, int value2) {
    this.count += count;
    return 0;
  }

  @Override
  public String toString(boolean matches) {
    String text = super.toString(matches);
    String text2 = "null";
    if (matches) {
      if (this.target != null) {
        if (this.target.getInstruction() == this) {
          text2 = "<points to itself>";
        } else if (this.target.getInstruction() == null) {
          text2 = "<null instruction!!!?>";
        } else {
          text2 = this.target.getInstruction().toString(false);
        }
      }
    } else if (this.target != null) {
      this.index = this.getTargetOffset();
      text2 = "" + (this.index + this.count);
    }
    return "" + text + " -> " + text2;
  }

  public int getTargetOffset(InstructionHandle instructionHandle) {
    if (instructionHandle == null) {
      throw new ClassGenException(
          ("Target of " + super.toString(true) + " is invalid null handle"));
    } else {
      int value;
      if ((value = instructionHandle.getPosition()) < 0) {
        throw new ClassGenException(
            ("Invalid branch target position offset for "
                + super.toString(true)
                + ":"
                + value
                + ":"
                + instructionHandle));
      } else {
        return value - this.count;
      }
    }
  }

  public BranchInstruction() {}

  public BranchInstruction(short value, InstructionHandle instructionHandle) {
    super(value, (short) 3);
    this.setTarget(instructionHandle);
  }

  public final int getIndex() {
    return this.index;
  }

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    this.length = 3;
    this.index = byteSequence.readShort();
  }

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.opcode);
    this.index = this.getTargetOffset();
    if (Math.abs(this.index) >= 32767) {
      throw new ClassGenException("Branch target offset too large for short");
    }
    dataOutputStream.writeShort(this.index);
  }
}
