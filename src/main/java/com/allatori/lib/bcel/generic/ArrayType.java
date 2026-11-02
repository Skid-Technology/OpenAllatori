package com.allatori.lib.bcel.generic;

public final class ArrayType extends ReferenceType {

  public Type basic_type;

  public int dimensions;

  public Type getBasicType() {
    return this.basic_type;
  }

  public Type getElementType() {
    return this.dimensions == 1
        ? this.basic_type
        : new ArrayType(this.basic_type, this.dimensions - 1);
  }

  public ArrayType(String text, int value) {
    this(new ObjectType(text), value);
  }

  public ArrayType(Type type, int dimensions) {
    super((byte) 13, "<dummy>");
    if (dimensions >= 1 && dimensions <= 255) {
      switch (type.getType()) {
        case 12:
          throw new ClassGenException("Invalid type: void[]");
        case 13:
          ArrayType arrayType;
          ArrayType arrayType2 = arrayType = (ArrayType) type;
          this.dimensions = dimensions + arrayType.dimensions;
          this.basic_type = arrayType2.basic_type;
          break;
        default:
          this.dimensions = dimensions;
          this.basic_type = type;
      }
      StringBuffer stringBuffer = new StringBuffer();
      int value2;
      for (int value3 = value2 = 0; value3 < this.dimensions; value3 = value2) {
        value2++;
        stringBuffer.append('[');
      }
      stringBuffer.append(this.basic_type.getSignature());
      this.signature = stringBuffer.toString();
    } else {
      throw new ClassGenException(("Invalid number of dimensions: " + dimensions));
    }
  }

  @Override
  public boolean equals(Object result) {
    ArrayType arrayType;
    return result instanceof ArrayType
        ? (arrayType = (ArrayType) result).dimensions == this.dimensions
            && arrayType.basic_type.equals(this.basic_type)
        : false;
  }

  public ArrayType(byte value, int value2) {
    this(BasicType.getType(value), value2);
  }

  @Override
  public int hashCode() {
    return this.basic_type.hashCode() ^ this.dimensions;
  }

  public int getDimensions() {
    return this.dimensions;
  }
}
