package com.allatori.lib.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class Type {

  public static final String TEXT = "VZCBSIFJD";

  public static final Type VOID_TYPE = new Type(0, "VZCBSIFJD", 0, 1);

  public static final Type BOOLEAN_TYPE = new Type(1, "VZCBSIFJD", 1, 2);

  public static final Type CHAR_TYPE = new Type(2, "VZCBSIFJD", 2, 3);

  public static final Type BYTE_TYPE = new Type(3, "VZCBSIFJD", 3, 4);

  public static final Type SHORT_TYPE = new Type(4, "VZCBSIFJD", 4, 5);

  public static final Type INT_TYPE = new Type(5, "VZCBSIFJD", 5, 6);

  public static final Type FLOAT_TYPE = new Type(6, "VZCBSIFJD", 6, 7);

  public static final Type LONG_TYPE = new Type(7, "VZCBSIFJD", 7, 8);

  public static final Type DOUBLE_TYPE = new Type(8, "VZCBSIFJD", 8, 9);

  public static final int LONG = 7;

  public static final int OBJECT = 10;

  public static final int DOUBLE = 8;

  public static final int ARRAY = 9;

  public static final int BOOLEAN = 1;

  public final String valueBuffer;

  public static final int CHAR = 2;

  public static final int FLOAT = 6;

  public static final int INTERNAL = 12;

  public static final int METHOD = 11;

  public static final int BYTE = 3;

  public final int valueBegin;

  public static final int SHORT = 4;

  public static final int VOID = 0;

  public final int sort;

  public final int valueEnd;

  public static final int INT = 5;

  public static int getArgumentsAndReturnSizes(String text) {
    int value = 1;
    int value2 = 1;
    char value3;
    char value4 = value3 = text.charAt(value2);
    while (value4 != ')') {
      String text2;
      if (value3 != 'J' && value3 != 'D') {
        String text3 = text;
        while (text3.charAt(value2) == '[') {
          value2++;
          text3 = text;
        }
        value4 = text.charAt(value2);
        char value5 = value4;
        value2++;
        if (value5 == (byte) 76) {
          int value6 = text.indexOf(59, value2);
          value2 = Math.max(value2, value6 + 1);
        }
        value++;
        text2 = text;
      } else {
        value2++;
        value += 2;
        text2 = text;
      }
      value4 = value3 = text2.charAt(value2);
    }
    char value7;
    if ((value7 = text.charAt(value2 + 1)) == 'V') {
      return value << 2;
    }
    int value8 = value7 != 'J' && value7 != 'D' ? 1 : 2;
    return value << 2 | value8;
  }

  public void appendDescriptor(StringBuilder stringBuilder) {
    if (this.sort == 10) {
      stringBuilder.append(this.valueBuffer, this.valueBegin - 1, this.valueEnd + 1);
    } else if (this.sort == 12) {
      stringBuilder
          .append('L')
          .append(this.valueBuffer, this.valueBegin, this.valueEnd)
          .append(';');
    } else {
      stringBuilder.append(this.valueBuffer, this.valueBegin, this.valueEnd);
    }
  }

  public static int getReturnTypeOffset(String text) {
    int value = 1;
    label26:
    while (true) {
      String text2 = text;
      while (text2.charAt(value) != ')') {
        text2 = text;
        while (text2.charAt(value) == '[') {
          value++;
          text2 = text;
        }
        char value2 = text.charAt(value);
        value++;
        if (value2 != (byte) 76) {
          continue label26;
        }
        text2 = text;
        int value3 = text.indexOf(59, value);
        value = Math.max(value, value3 + 1);
      }
      return value + 1;
    }
  }

  public Type(int sort, String valueBuffer, int valueBegin, int valueEnd) {
    super();
    this.sort = sort;
    this.valueBuffer = valueBuffer;
    this.valueBegin = valueBegin;
    this.valueEnd = valueEnd;
  }

  public static void appendDescriptor(Class classValue, StringBuilder stringBuilder) {
    Class classValue2;
    for (Class classValue3 = classValue2 = classValue;
        classValue3.isArray();
        classValue3 = classValue2) {
      stringBuilder.append('[');
      classValue2 = classValue2.getComponentType();
    }
    if (classValue2.isPrimitive()) {
      char value;
      StringBuilder stringBuilder2;
      if (classValue2 == int.class) {
        value = 'I';
        stringBuilder2 = stringBuilder;
      } else if (classValue2 == void.class) {
        value = 'V';
        stringBuilder2 = stringBuilder;
      } else if (classValue2 == boolean.class) {
        value = 'Z';
        stringBuilder2 = stringBuilder;
      } else if (classValue2 == byte.class) {
        value = 'B';
        stringBuilder2 = stringBuilder;
      } else if (classValue2 == char.class) {
        value = 'C';
        stringBuilder2 = stringBuilder;
      } else if (classValue2 == short.class) {
        value = 'S';
        stringBuilder2 = stringBuilder;
      } else if (classValue2 == double.class) {
        value = 'D';
        stringBuilder2 = stringBuilder;
      } else if (classValue2 == float.class) {
        value = 'F';
        stringBuilder2 = stringBuilder;
      } else {
        if (classValue2 != long.class) {
          throw new AssertionError();
        }
        value = 'J';
        stringBuilder2 = stringBuilder;
      }
      stringBuilder2.append(value);
    } else {
      stringBuilder.append('L').append(getText(classValue2)).append(';');
    }
  }

  public int getSize() {
    switch (this.sort) {
      case 0:
        return 0;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 9:
      case 10:
      case 12:
        return 1;
      case 7:
      case 8:
        return 2;
      case 11:
      default:
        throw new AssertionError();
    }
  }

  public static String getText(Class classValue) {
    return classValue.getName().replace('.', '/');
  }

  public static String createText(String text) {
    return "Invalid descriptor: " + text;
  }

  public int getArgumentsAndReturnSizes() {
    return getArgumentsAndReturnSizes(this.getDescriptor());
  }

  public int getSort() {
    return this.sort == 12 ? 10 : this.sort;
  }

  public static Type getType(Class classValue) {
    if (classValue.isPrimitive()) {
      if (classValue == int.class) {
        return INT_TYPE;
      } else if (classValue == void.class) {
        return VOID_TYPE;
      } else if (classValue == boolean.class) {
        return BOOLEAN_TYPE;
      } else if (classValue == byte.class) {
        return BYTE_TYPE;
      } else if (classValue == char.class) {
        return CHAR_TYPE;
      } else if (classValue == short.class) {
        return SHORT_TYPE;
      } else if (classValue == double.class) {
        return DOUBLE_TYPE;
      } else if (classValue == float.class) {
        return FLOAT_TYPE;
      } else if (classValue == long.class) {
        return LONG_TYPE;
      } else {
        throw new AssertionError();
      }
    } else {
      return getType(createText(classValue));
    }
  }

  public static Type getType(Constructor constructor) {
    return getType(getConstructorDescriptor(constructor));
  }

  public static Type[] getArgumentTypes(Method method) {
    Class[] classs;
    Type[] types = new Type[(classs = method.getParameterTypes()).length];
    int value;
    for (int value2 = value = classs.length - 1; value2 >= 0; value2 = value) {
      Type type = getType(classs[value]);
      int value3 = value;
      value--;
      types[value3] = type;
    }
    return types;
  }

  public static Type getType(Method method) {
    return getType(method.getReturnType());
  }

  public static Type[] getArgumentTypes(String text) {
    Type[] types = new Type[getArgumentCount(text)];
    int value = 1;
    int value2 = 0;
    for (String text2 = text; text2.charAt(value) != ')'; text2 = text) {
      int value3 = value;
      text2 = text;
      while (text2.charAt(value) == '[') {
        value++;
        text2 = text;
      }
      char value4 = text.charAt(value);
      value++;
      if (value4 == (byte) 76) {
        int value5 = text.indexOf(59, value);
        value = Math.max(value, value5 + 1);
      }
      int value6 = value;
      int value7 = value2;
      value2++;
      types[value7] = getTypeInternal(text, value3, value6);
    }
    return types;
  }

  public static String getConstructorDescriptor(Constructor constructor) {
    StringBuilder stringBuilder;
    (stringBuilder = new StringBuilder()).append('(');
    Class[] parameterTypes = constructor.getParameterTypes();
    int value = parameterTypes.length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = value2;
      value2++;
      appendDescriptor(parameterTypes[value4], stringBuilder);
    }
    return stringBuilder.append(")V").toString();
  }

  public static Type getReturnType(Method method) {
    return getType(getMethodDescriptor(method));
  }

  public static Type getObjectType(String text) {
    return new Type(text.charAt(0) == '[' ? 9 : 12, text, 0, text.length());
  }

  public int getArgumentCount() {
    return getArgumentCount(this.getDescriptor());
  }

  public int getDimensions() {
    int value = 1;
    Type type = this;
    while (type.valueBuffer.charAt(this.valueBegin + value) == '[') {
      value++;
      type = this;
    }
    return value;
  }

  public Type[] getArgumentTypes() {
    return getArgumentTypes(this.getDescriptor());
  }

  public static Type getTypeInternal(String text, int value, int value2) {
    switch (text.charAt(value)) {
      case '(':
        return new Type(11, text, value, value2);
      case 'B':
        return BYTE_TYPE;
      case 'C':
        return CHAR_TYPE;
      case 'D':
        return DOUBLE_TYPE;
      case 'F':
        return FLOAT_TYPE;
      case 'I':
        return INT_TYPE;
      case 'J':
        return LONG_TYPE;
      case 'L':
        return new Type(10, text, value + 1, value2 - 1);
      case 'S':
        return SHORT_TYPE;
      case 'V':
        return VOID_TYPE;
      case 'Z':
        return BOOLEAN_TYPE;
      case '[':
        return new Type(9, text, value, value2);
      default:
        throw new IllegalArgumentException(createText(text));
    }
  }

  public String getClassName() {
    switch (this.sort) {
      case 0:
        return "void";
      case 1:
        return "boolean";
      case 2:
        return "char";
      case 3:
        return "byte";
      case 4:
        return "short";
      case 5:
        return "int";
      case 6:
        return "float";
      case 7:
        return "long";
      case 8:
        return "double";
      case 9:
        StringBuilder stringBuilder = new StringBuilder(this.getElementType().getClassName());
        int value;
        for (int value2 = value = this.getDimensions(); value2 > 0; value2 = value) {
          value--;
          stringBuilder.append("[]");
        }
        return stringBuilder.toString();
      case 10:
      case 12:
        return this.valueBuffer.substring(this.valueBegin, this.valueEnd).replace('/', '.');
      case 11:
      default:
        throw new AssertionError();
    }
  }

  public Type getElementType() {
    int dimensions = this.getDimensions();
    return getTypeInternal(this.valueBuffer, this.valueBegin + dimensions, this.valueEnd);
  }

  public static String getMethodDescriptor(Method method) {
    StringBuilder stringBuilder;
    (stringBuilder = new StringBuilder()).append('(');
    Class[] parameterTypes = method.getParameterTypes();
    int value = parameterTypes.length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = value2;
      value2++;
      appendDescriptor(parameterTypes[value4], stringBuilder);
    }
    stringBuilder.append(')');
    appendDescriptor(method.getReturnType(), stringBuilder);
    return stringBuilder.toString();
  }

  public static String createText2(String text) {
    return "L" + text + ";";
  }

  public static Type getType(String text) {
    return getTypeInternal(text, 0, text.length());
  }

  public static int getArgumentCount(String text) {
    int value = 0;
    int value2 = 1;
    for (String text2 = text; text2.charAt(value2) != ')'; text2 = text) {
      text2 = text;
      while (text2.charAt(value2) == '[') {
        value2++;
        text2 = text;
      }
      char value3 = text.charAt(value2);
      value2++;
      if (value3 == (byte) 76) {
        int value4 = text.indexOf(59, value2);
        value2 = Math.max(value2, value4 + 1);
      }
      value++;
    }
    return value;
  }

  public static Type getReturnType(String text) {
    return getTypeInternal(text, getReturnTypeOffset(text), text.length());
  }

  public Type getReturnType() {
    return getReturnType(this.getDescriptor());
  }

  public String getInternalName() {
    return this.valueBuffer.substring(this.valueBegin, this.valueEnd);
  }

  public int getOpcode(int value) {
    if (value != 46 && value != 79) {
      switch (this.sort) {
        case 0:
          if (value != 172) {
            throw new UnsupportedOperationException();
          }
          return 177;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
          return value;
        case 6:
          return value + 2;
        case 7:
          return value + 1;
        case 8:
          return value + 3;
        case 9:
        case 10:
        case 12:
          if (value != 21 && value != 54 && value != 172) {
            throw new UnsupportedOperationException();
          }
          return value + 4;
        case 11:
          throw new UnsupportedOperationException();
        default:
          throw new AssertionError();
      }
    } else {
      switch (this.sort) {
        case 0:
        case 11:
          throw new UnsupportedOperationException();
        case 1:
        case 3:
          return value + 5;
        case 2:
          return value + 6;
        case 4:
          return value + 7;
        case 5:
          return value;
        case 6:
          return value + 2;
        case 7:
          return value + 1;
        case 8:
          return value + 3;
        case 9:
        case 10:
        case 12:
          return value + 4;
        default:
          throw new AssertionError();
      }
    }
  }

  public String getDescriptor() {
    if (this.sort == 10) {
      return this.valueBuffer.substring(this.valueBegin - 1, this.valueEnd + 1);
    } else {
      return this.sort == 12
          ? createText2(this.valueBuffer.substring(this.valueBegin, this.valueEnd))
          : this.valueBuffer.substring(this.valueBegin, this.valueEnd);
    }
  }

  public static Type getMethodType(Type type, Type... type2) {
    return getType(getMethodDescriptor(type, (Type[]) type2));
  }

  @Override
  public boolean equals(Object result) {
    if (this == result) {
      return true;
    }
    if (!(result instanceof Type)) {
      return false;
    }
    Type type = (Type) result;
    int value;
    Type type2;
    if (this.sort == 12) {
      value = 10;
      type2 = type;
    } else {
      value = this.sort;
      type2 = type;
    }
    if (value != (type2.sort == 12 ? 10 : type.sort)) {
      return false;
    }
    int value2 = this.valueBegin;
    int value3 = this.valueEnd;
    int value4 = type.valueBegin;
    int value5 = type.valueEnd;
    if (value3 - value2 != value5 - value4) {
      return false;
    }
    int value6 = value2;
    int value7 = value4;
    value = value6;
    while (value < value3) {
      if (this.valueBuffer.charAt(value6) != type.valueBuffer.charAt(value7)) {
        return false;
      }
      int value8 = ++value6;
      value7++;
      value = value8;
    }
    return true;
  }

  public static String getMethodDescriptor(Type type, Type... type2) {
    StringBuilder stringBuilder;
    (stringBuilder = new StringBuilder()).append('(');
    int value = ((Type[]) type2).length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = value2;
      Type[] types = (Type[]) type2;
      value2++;
      types[value4].appendDescriptor(stringBuilder);
    }
    stringBuilder.append(')');
    type.appendDescriptor(stringBuilder);
    return stringBuilder.toString();
  }

  public static Type getMethodType(String text) {
    return new Type(11, text, 0, text.length());
  }

  @Override
  public int hashCode() {
    int value = 13 * (this.sort == 12 ? 10 : this.sort);
    if (this.sort >= 9) {
      int value2 = this.valueBegin;
      int value3 = this.valueEnd;
      for (int value4 = value2; value4 < value3; value4 = value2) {
        char value5 = this.valueBuffer.charAt(value2);
        int value6 = value;
        value2++;
        value = (byte) 17 * (value6 + value5);
      }
    }
    return value;
  }

  public static String createText(Class classValue) {
    StringBuilder stringBuilder = new StringBuilder();
    appendDescriptor(classValue, stringBuilder);
    return stringBuilder.toString();
  }
}
