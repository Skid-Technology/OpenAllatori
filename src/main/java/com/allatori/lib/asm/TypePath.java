package com.allatori.lib.asm;

public final class TypePath {

  public final int typePathOffset;

  public final byte[] typePathContainer;

  public static final int INNER_TYPE = 1;

  public static final int WILDCARD_BOUND = 2;

  public static final int TYPE_ARGUMENT = 3;

  public static final int ARRAY_ELEMENT = 0;

  public static TypePath fromString(String text) {
    if (text != null && text.length() != 0) {
      int size = text.length();
      ByteVector byteVector;
      (byteVector = new ByteVector(size)).putByte(0);
      int value = 0;
      while (value < size) {
        char value2;
        char value3 = value2 = text.charAt(value);
        value++;
        if (value3 == '[') {
          byteVector.put11(0, 0);
        } else if (value2 != '.') {
          if (value2 != '*') {
            if (value2 < '0' || value2 > '9') {
              throw new IllegalArgumentException();
            }
            int value4 = value2 - '0';
            int value5 = value;
            while (true) {
              ByteVector byteVector2;
              if (value5 < size) {
                char value6;
                char value7 = value6 = text.charAt(value);
                value++;
                if (value7 >= '0' && value6 <= '9') {
                  value4 = value4 * 10 + value6 - 48;
                  value5 = value;
                  continue;
                }
                if (value6 != ';') {
                  throw new IllegalArgumentException();
                }
                byteVector2 = byteVector;
              } else {
                byteVector2 = byteVector;
              }
              byteVector2.put11(3, value4);
              break;
            }
          } else {
            byteVector.put11(2, 0);
          }
        } else {
          byteVector.put11(1, 0);
        }
      }
      byteVector.data[0] = (byte) (byteVector.length / 2);
      return new TypePath(byteVector.data, 0);
    } else {
      return null;
    }
  }

  public int calculateCount(int value) {
    return this.typePathContainer[this.typePathOffset + 2 * value + 1];
  }

  public int getLength() {
    return this.typePathContainer[this.typePathOffset];
  }

  public int calculateCount2(int value) {
    return this.typePathContainer[this.typePathOffset + 2 * value + 2];
  }

  public static void put(TypePath typePath, ByteVector byteVector) {
    if (typePath == null) {
      byteVector.putByte(0);
    } else {
      int value = typePath.typePathContainer[typePath.typePathOffset] * 2 + 1;
      byteVector.putByteArray(typePath.typePathContainer, typePath.typePathOffset, value);
    }
  }

  public TypePath(byte[] typePathContainer, int typePathOffset) {
    super();
    this.typePathContainer = typePathContainer;
    this.typePathOffset = typePathOffset;
  }
}
