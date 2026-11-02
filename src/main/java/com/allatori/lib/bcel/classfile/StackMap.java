package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class StackMap extends Attribute implements Node {

  public int map_length;

  public StackMapEntry[] map;

  public StackMap(int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (StackMapEntry[]) null, constantPool);
    this.map_length = dataInputStream.readUnsignedShort();
    this.map = new StackMapEntry[this.map_length];
    int value3;
    for (int value4 = value3 = 0; value4 < this.map_length; value4 = value3) {
      StackMapEntry[] stackMapEntrys = this.map;
      StackMapEntry stackMapEntry = new StackMapEntry(dataInputStream, constantPool);
      int value5 = value3;
      value3++;
      stackMapEntrys[value5] = stackMapEntry;
    }
  }

  public StackMap(
      int value, int value2, StackMapEntry[] stackMapEntrys, ConstantPool constantPool) {
    super((byte) 11, value, value2, constantPool);
    this.setStackMap(stackMapEntrys);
  }

  public final StackMapEntry[] getStackMap() {
    return this.map;
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    StackMap stackMap;
    (stackMap = (StackMap) this.clone()).map = new StackMapEntry[this.map_length];
    int value;
    for (int value2 = value = 0; value2 < this.map_length; value2 = value) {
      StackMapEntry[] stackMapEntrys = stackMap.map;
      StackMapEntry stackMapEntry = this.map[value].copy();
      int value3 = value;
      value++;
      stackMapEntrys[value3] = stackMapEntry;
    }
    stackMap.constant_pool = constantPool;
    return stackMap;
  }

  public final int getMapLength() {
    return this.map_length;
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitStackMap(this);
  }

  public final void setStackMap(StackMapEntry[] stackMapEntrys) {
    this.map = stackMapEntrys;
    this.map_length = stackMapEntrys == null ? 0 : stackMapEntrys.length;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    int value = 0;
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.map_length);
    for (int index = 0; value < this.map_length; value = index) {
      int value2 = index;
      StackMapEntry[] stackMapEntrys = this.map;
      index++;
      stackMapEntrys[value2].dump(dataOutputStream);
    }
  }
}
