package com.allatori.lib.asm;

public class Label {

  public static final int FLAG_DEBUG_ONLY = 1;

  public static final int FORWARD_REFERENCE_TYPE_MASK = -268435456;

  public static final int FLAG_JUMP_TARGET = 2;

  public static final int FORWARD_REFERENCES_CAPACITY_INCREMENT = 6;

  public Frame frame;

  public static final int FLAG_LINE_NUMBER = 128;

  public static final int FORWARD_REFERENCE_TYPE_SHORT = 268435456;

  public Edge outgoingEdges;

  public static final int FLAG_SUBROUTINE_START = 32;

  public short value;

  public short value2;

  public int[] counts;

  public static final int FORWARD_REFERENCE_TYPE_STACK_MAP = 805306368;

  public Label label;

  public int bytecodeOffset;

  public Object info;

  public static final Label EMPTY_LIST = new Label();

  public short value3;

  public short value4;

  public static final int FORWARD_REFERENCE_HANDLE_MASK = 268435455;

  public Label label2;

  public static final int COUNT = 4;

  public static final int FLAG_SUBROUTINE_END = 64;

  public short value5;

  public static final int COUNT2 = 4;

  public static final int FLAG_SUBROUTINE_CALLER = 16;

  public int[] counts2;

  public static final int FLAG_REACHABLE = 8;

  public short value6;

  public static final int FORWARD_REFERENCE_TYPE_WIDE = 536870912;

  public static String stringConcat$0(int value) {
    return "L" + value;
  }

  public final void put(ByteVector byteVector) {
    if ((this.value3 & 4) == 0) {
      this.addForwardReference(0, 805306368, byteVector.length);
    }
    byteVector.putShort(this.bytecodeOffset);
  }

  public int getOffset() {
    if ((this.value3 & 4) == 0) {
      throw new IllegalStateException("Label offset position has not been resolved yet");
    } else {
      return this.bytecodeOffset;
    }
  }

  public void addForwardReference(int value, int value2, int value3) {
    if (this.counts2 == null) {
      this.counts2 = new int[6];
    }
    int value4;
    if ((value4 = this.counts2[0]) + 2 >= this.counts2.length) {
      int[] values = new int[this.counts2.length + 6];
      System.arraycopy(this.counts2, 0, values, 0, this.counts2.length);
      this.counts2 = values;
    }
    value4++;
    int value5 = value4;
    int[] values2 = this.counts2;
    value4++;
    values2[value5] = value;
    this.counts2[value4] = value2 | value3;
    this.counts2[0] = value4;
  }

  public final void addSubroutineRetSuccessors(Label label2) {
    Label label3 = EMPTY_LIST;
    Label label4;
    Label label5 = label4 = this;
    this.label = EMPTY_LIST;
    while (label5 != EMPTY_LIST) {
      Label label6 = label4;
      Label label7 = label4.label;
      label4.label = label3;
      label3 = label4;
      if ((label4.value3 & 64) != 0 && label6.value2 != label2.value2) {
        label6.outgoingEdges =
            new Edge(label6.value5, label2.outgoingEdges.successor, label6.outgoingEdges);
      }
      label5 = label4 = label6.pushSuccessors(label7);
    }
    label5 = label3;
    while (label5 != EMPTY_LIST) {
      Label label8 = label3.label;
      label3.label = null;
      label5 = label3 = label8;
    }
  }

  public final void addLineNumber(int value2) {
    if ((this.value3 & 128) == 0) {
      this.value3 = (short) (this.value3 | 128);
      this.value = (short) value2;
    } else {
      if (this.counts == null) {
        this.counts = new int[4];
      }
      int value4;
      if ((value4 = ++this.counts[0]) >= this.counts.length) {
        int[] values = new int[this.counts.length + 4];
        System.arraycopy(this.counts, 0, values, 0, this.counts.length);
        this.counts = values;
      }
      this.counts[value4] = value2;
    }
  }

  public final Label getCanonicalInstance() {
    return this.frame == null ? this : this.frame.owner;
  }

  public final boolean resolve(byte[] values, ByteVector byteVector, int bytecodeOffset) {
    this.value3 = (short) (this.value3 | 4);
    this.bytecodeOffset = bytecodeOffset;
    if (this.counts2 == null) {
      return false;
    }
    boolean matches = false;
    int value2;
    for (int value4 = value2 = this.counts2[0]; value4 > 0; value4 = value2) {
      int value5 = this.counts2[value2 - 1];
      int value6 = this.counts2[value2];
      int value7 = bytecodeOffset - value5;
      int value8 = value6 & 268435455;
      if ((value6 & -268435456) == 268435456) {
        if (value7 < -32768 || value7 > 32767) {
          int value9;
          if ((value9 = values[value5] & 255) < 198) {
            values[value5] = (byte) (value9 + 49);
          } else {
            values[value5] = (byte) (value9 + 20);
          }
          matches = true;
        }
        int value10 = value7 >>> 8;
        int value11 = value8;
        value8++;
        values[value11] = (byte) value10;
        values[value8] = (byte) value7;
      } else if ((value6 & -268435456) == 536870912) {
        int value12 = value7 >>> 24;
        int value13 = value8;
        value8++;
        values[value13] = (byte) value12;
        int value14 = value7 >>> 16;
        int value15 = value8;
        value8++;
        values[value15] = (byte) value14;
        int value16 = value7 >>> 8;
        int value17 = value8;
        value8++;
        values[value17] = (byte) value16;
        values[value8] = (byte) value7;
      } else {
        int value18 = bytecodeOffset >>> 8;
        int value19 = value8;
        byte[] values2 = byteVector.data;
        value8++;
        values2[value19] = (byte) value18;
        byteVector.data[value8] = (byte) bytecodeOffset;
      }
      value2 -= 2;
    }
    return matches;
  }

  public Label pushSuccessors(Label label2) {
    Label label3 = label2;
    Edge edge;
    for (Edge edge2 = edge = this.outgoingEdges; edge2 != null; edge2 = edge = edge.nextEdge) {
      int value = (this.value3 & 16) != 0 && edge == this.outgoingEdges.nextEdge ? 1 : 0;
      if (value == 0 && edge.successor.label == null) {
        edge.successor.label = label3;
        label3 = edge.successor;
      }
    }
    return label3;
  }

  public final void accept(MethodVisitor methodVisitor, boolean matches) {
    methodVisitor.visitLabel(this);
    if (matches && (this.value3 & 128) != 0) {
      methodVisitor.visitLineNumber(this.value & 65535, this);
      int value2;
      if (this.counts != null) {
        for (int value4 = value2 = 1; value4 <= this.counts[0]; value4 = value2) {
          int value5 = value2;
          int[] values = this.counts;
          value2++;
          methodVisitor.visitLineNumber(values[value5], this);
        }
      }
    }
  }

  public final void put(ByteVector byteVector, int value, boolean matches) {
    if ((this.value3 & 4) == 0) {
      if (matches) {
        this.addForwardReference(value, 536870912, byteVector.length);
        byteVector.putInt(-1);
      } else {
        this.addForwardReference(value, 268435456, byteVector.length);
        byteVector.putShort(-1);
      }
    } else if (matches) {
      byteVector.putInt(this.bytecodeOffset - value);
    } else {
      byteVector.putShort(this.bytecodeOffset - value);
    }
  }

  public final void markSubroutine(short value) {
    Label label2 = this;
    this.label = EMPTY_LIST;
    while (label2 != EMPTY_LIST) {
      Label label3 = label2;
      label2 = label2.label;
      label3.label = null;
      if (label3.value2 == 0) {
        label3.value2 = value;
        label2 = label3.pushSuccessors(label2);
      }
    }
  }
}
