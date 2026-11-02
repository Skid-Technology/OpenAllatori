package com.allatori.lib.asm;

public class TypeReference {

  public static final int METHOD_TYPE_PARAMETER = 1;

  public static final int METHOD_INVOCATION_TYPE_ARGUMENT = 73;

  public static final int RESOURCE_VARIABLE = 65;

  public static final int THROWS = 23;

  public static final int CONSTRUCTOR_REFERENCE = 69;

  public static final int METHOD_REFERENCE_TYPE_ARGUMENT = 75;

  public static final int CAST = 71;

  public static final int FIELD = 19;

  public final int targetTypeAndInfo;

  public static final int METHOD_FORMAL_PARAMETER = 22;

  public static final int CLASS_TYPE_PARAMETER_BOUND = 17;

  public static final int LOCAL_VARIABLE = 64;

  public static final int CLASS_TYPE_PARAMETER = 0;

  public static final int INSTANCEOF = 67;

  public static final int METHOD_REFERENCE = 70;

  public static final int CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT = 72;

  public static final int EXCEPTION_PARAMETER = 66;

  public static final int NEW = 68;

  public static final int METHOD_TYPE_PARAMETER_BOUND = 18;

  public static final int METHOD_RECEIVER = 21;

  public static final int CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT = 74;

  public static final int METHOD_RETURN = 20;

  public static final int CLASS_EXTENDS = 16;

  public static TypeReference createTypeReference(int value) {
    return new TypeReference(1107296256 | value << 8);
  }

  public static TypeReference createTypeReference2(int value) {
    return new TypeReference(385875968 | value << 8);
  }

  public static void putTarget(int value, ByteVector byteVector) {
    switch (value >>> 24) {
      case 0:
      case 1:
      case 22:
        byteVector.putShort(value >>> 16);
        return;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      case 64:
      case 65:
      default:
        throw new IllegalArgumentException();
      case 16:
      case 17:
      case 18:
      case 23:
      case 66:
      case 67:
      case 68:
      case 69:
      case 70:
        byteVector.put12(value >>> 24, (value & 16776960) >> 8);
        return;
      case 19:
      case 20:
      case 21:
        byteVector.putByte(value >>> 24);
        return;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
        byteVector.putInt(value);
    }
  }

  public int getTargetTypeAndInfo() {
    return this.targetTypeAndInfo >>> 24;
  }

  public int getTargetTypeAndInfo2() {
    return (this.targetTypeAndInfo & 16776960) >> 8;
  }

  public int getTargetTypeAndInfo3() {
    return this.targetTypeAndInfo;
  }

  public int getTargetTypeAndInfo4() {
    return (this.targetTypeAndInfo & 0xFF0000) >> 16;
  }

  public int getTargetTypeAndInfo5() {
    return (this.targetTypeAndInfo & 0xFF00) >> 8;
  }

  public static TypeReference newTypeParameterBoundReference(int value, int value2, int value3) {
    return new TypeReference(value << 24 | value2 << 16 | value3 << 8);
  }

  public TypeReference(int targetTypeAndInfo) {
    super();
    this.targetTypeAndInfo = targetTypeAndInfo;
  }

  public static TypeReference createTypeReference(int value, int value2) {
    return new TypeReference(value << 24 | value2);
  }

  public static TypeReference createTypeReference3(int value) {
    return new TypeReference(value << 24);
  }

  public static TypeReference createTypeReference2(int value, int value2) {
    return new TypeReference(value << 24 | value2 << 16);
  }

  public int getTargetTypeAndInfo6() {
    return (short) ((this.targetTypeAndInfo & 16776960) >> 8);
  }

  public int getTargetTypeAndInfo7() {
    return (this.targetTypeAndInfo & 0xFF0000) >> 16;
  }

  public int getTargetTypeAndInfo8() {
    return (this.targetTypeAndInfo & 16776960) >> 8;
  }

  public int getTargetTypeAndInfo9() {
    return this.targetTypeAndInfo & 0xFF;
  }

  public static TypeReference createTypeReference4(int value) {
    return new TypeReference(369098752 | value << 16);
  }

  public static TypeReference createTypeReference5(int value) {
    return new TypeReference(268435456 | (value & 65535) << 8);
  }
}
