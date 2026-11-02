package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public final class StackMapTableEntry implements Cloneable, Serializable {

  public StackMapType[] typesOfStackItems;

  public int numberOfStackItems;

  public int numberOfLocals;

  public final int frameType;

  public StackMapType[] typesOfLocals;

  public int byteCodeOffset;

  public ConstantPool constantPool;

  public void setNumberOfStackItems(int numberOfStackItems) {
    this.numberOfStackItems = numberOfStackItems;
  }

  public StackMapTableEntry copy() {
    try {
      return (StackMapTableEntry) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }

  public StackMapTableEntry(
      int frameType,
      int byteCodeOffset,
      int numberOfLocals,
      StackMapType[] stackMapTypes,
      int numberOfStackItems,
      StackMapType[] stackMapTypes2,
      ConstantPool constantPool2) {
    super();
    this.frameType = frameType;
    this.byteCodeOffset = byteCodeOffset;
    this.numberOfLocals = numberOfLocals;
    this.typesOfLocals = stackMapTypes;
    this.numberOfStackItems = numberOfStackItems;
    this.typesOfStackItems = stackMapTypes2;
    this.constantPool = constantPool2;
  }

  public void setNumberOfLocals(int numberOfLocals) {
    this.numberOfLocals = numberOfLocals;
  }

  public StackMapType[] getTypesOfLocals() {
    return this.typesOfLocals;
  }

  public int getNumberOfLocals() {
    return this.numberOfLocals;
  }

  public int getNumberOfStackItems() {
    return this.numberOfStackItems;
  }

  public void setByteCodeOffset(int byteCodeOffset) {
    this.byteCodeOffset = byteCodeOffset;
  }

  public void accept(Visitor visitor) {}

  public StackMapTableEntry(DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(dataInputStream.read(), -1, -1, null, -1, null, constantPool);
    if (this.frameType >= 0 && this.frameType <= 63) {
      this.byteCodeOffset = this.frameType - 0;
    } else if (this.frameType >= 64 && this.frameType <= 127) {
      this.byteCodeOffset = this.frameType - 64;
      this.numberOfStackItems = 1;
      this.typesOfStackItems = new StackMapType[1];
      this.typesOfStackItems[0] = new StackMapType(dataInputStream, constantPool);
    } else if (this.frameType == 247) {
      this.byteCodeOffset = dataInputStream.readShort();
      this.numberOfStackItems = 1;
      this.typesOfStackItems = new StackMapType[1];
      this.typesOfStackItems[0] = new StackMapType(dataInputStream, constantPool);
    } else if (this.frameType >= 248 && this.frameType <= 250) {
      this.byteCodeOffset = dataInputStream.readShort();
    } else if (this.frameType == 251) {
      this.byteCodeOffset = dataInputStream.readShort();
    } else {
      if (this.frameType >= 252 && this.frameType <= 254) {
        this.byteCodeOffset = dataInputStream.readShort();
        this.numberOfLocals = this.frameType - 251;
        this.typesOfLocals = new StackMapType[this.numberOfLocals];
        int value;
        for (int value2 = value = 0; value2 < this.numberOfLocals; value2 = value) {
          StackMapType[] stackMapTypes = this.typesOfLocals;
          StackMapType stackMapType = new StackMapType(dataInputStream, constantPool);
          int value3 = value;
          value++;
          stackMapTypes[value3] = stackMapType;
        }
      } else {
        if (this.frameType != 255) {
          throw new ClassFormatException(
              ("Invalid frame type found while parsing stack map table: " + this.frameType));
        }
        this.byteCodeOffset = dataInputStream.readShort();
        this.numberOfLocals = dataInputStream.readShort();
        this.typesOfLocals = new StackMapType[this.numberOfLocals];
        int value4;
        for (int value5 = value4 = 0; value5 < this.numberOfLocals; value5 = value4) {
          StackMapType[] stackMapTypes2 = this.typesOfLocals;
          StackMapType stackMapType2 = new StackMapType(dataInputStream, constantPool);
          int value6 = value4;
          value4++;
          stackMapTypes2[value6] = stackMapType2;
        }
        this.numberOfStackItems = dataInputStream.readShort();
        this.typesOfStackItems = new StackMapType[this.numberOfStackItems];
        int value7;
        for (int value8 = value7 = 0; value8 < this.numberOfStackItems; value8 = value7) {
          StackMapType[] stackMapTypes3 = this.typesOfStackItems;
          StackMapType stackMapType3 = new StackMapType(dataInputStream, constantPool);
          int value9 = value7;
          value7++;
          stackMapTypes3[value9] = stackMapType3;
        }
      }
    }
  }

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.write(this.frameType);
    if (this.frameType < 0 || this.frameType > 63) {
      if (this.frameType >= 64 && this.frameType <= 127) {
        this.typesOfStackItems[0].dump(dataOutputStream);
      } else if (this.frameType == 247) {
        dataOutputStream.writeShort(this.byteCodeOffset);
        this.typesOfStackItems[0].dump(dataOutputStream);
      } else if (this.frameType >= 248 && this.frameType <= 250) {
        dataOutputStream.writeShort(this.byteCodeOffset);
      } else if (this.frameType == 251) {
        dataOutputStream.writeShort(this.byteCodeOffset);
      } else {
        if (this.frameType >= 252 && this.frameType <= 254) {
          int value = 0;
          dataOutputStream.writeShort(this.byteCodeOffset);
          for (int index = 0; value < this.numberOfLocals; value = index) {
            int value2 = index;
            StackMapType[] stackMapTypes = this.typesOfLocals;
            index++;
            stackMapTypes[value2].dump(dataOutputStream);
          }
        } else {
          if (this.frameType != 255) {
            throw new ClassFormatException(("Invalid Stack map table tag: " + this.frameType));
          }
          int value3 = 0;
          dataOutputStream.writeShort(this.byteCodeOffset);
          dataOutputStream.writeShort(this.numberOfLocals);
          for (int index2 = 0; value3 < this.numberOfLocals; value3 = index2) {
            int value4 = index2;
            StackMapType[] stackMapTypes2 = this.typesOfLocals;
            index2++;
            stackMapTypes2[value4].dump(dataOutputStream);
          }
          dataOutputStream.writeShort(this.numberOfStackItems);
          int value5;
          for (int value6 = value5 = 0; value6 < this.numberOfStackItems; value6 = value5) {
            int value7 = value5;
            StackMapType[] stackMapTypes3 = this.typesOfStackItems;
            value5++;
            stackMapTypes3[value7].dump(dataOutputStream);
          }
        }
      }
    }
  }

  public StackMapType[] getTypesOfStackItems() {
    return this.typesOfStackItems;
  }

  public final ConstantPool getConstantPool() {
    return this.constantPool;
  }

  public void setTypesOfStackItems(StackMapType[] stackMapTypes) {
    this.typesOfStackItems = stackMapTypes;
  }

  public int getByteCodeOffset() {
    return this.byteCodeOffset;
  }

  public final void setConstantPool(ConstantPool constantPool2) {
    this.constantPool = constantPool2;
  }

  public void setTypesOfLocals(StackMapType[] stackMapTypes) {
    this.typesOfLocals = stackMapTypes;
  }
}
