package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class StackMapTable extends Attribute {

  public StackMapTableEntry[] stackMapTable;

  public int mapLength;

  public final int getMapLength() {
    return this.mapLength;
  }

  public StackMapTable(
      int value, int value2, StackMapTableEntry[] stackMapTableEntrys, ConstantPool constantPool) {
    super((byte) 19, value, value2, constantPool);
    this.setStackMap(stackMapTableEntrys);
  }

  public final StackMapTableEntry[] getStackMap() {
    return this.stackMapTable;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.mapLength);
    for (int index = 0; value < this.mapLength; value = index) {
      int value2 = index;
      StackMapTableEntry[] stackMapTableEntrys = this.stackMapTable;
      index++;
      stackMapTableEntrys[value2].dump(dataOutputStream);
    }
  }

  public final void setStackMap(StackMapTableEntry[] stackMapTableEntrys) {
    this.stackMapTable = stackMapTableEntrys;
    this.mapLength = stackMapTableEntrys == null ? 0 : stackMapTableEntrys.length;
  }

  @Override
  public void accept(Visitor visitor) {}

  @Override
  public Attribute copy(ConstantPool constantPool) {
    StackMapTable stackMapTable2;
    (stackMapTable2 = (StackMapTable) this.clone()).stackMapTable =
        new StackMapTableEntry[this.mapLength];
    int value;
    for (int value2 = value = 0; value2 < this.mapLength; value2 = value) {
      StackMapTableEntry[] stackMapTableEntrys = stackMapTable2.stackMapTable;
      StackMapTableEntry stackMapTableEntry = this.stackMapTable[value].copy();
      int value3 = value;
      value++;
      stackMapTableEntrys[value3] = stackMapTableEntry;
    }
    stackMapTable2.constant_pool = constantPool;
    return stackMapTable2;
  }

  public StackMapTable(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (StackMapTableEntry[]) null, constantPool);
    this.mapLength = dataInputStream.readUnsignedShort();
    this.stackMapTable = new StackMapTableEntry[this.mapLength];
    int value3;
    for (int value4 = value3 = 0; value4 < this.mapLength; value4 = value3) {
      StackMapTableEntry[] stackMapTableEntrys = this.stackMapTable;
      StackMapTableEntry stackMapTableEntry = new StackMapTableEntry(dataInputStream, constantPool);
      int value5 = value3;
      value3++;
      stackMapTableEntrys[value5] = stackMapTableEntry;
    }
  }
}
