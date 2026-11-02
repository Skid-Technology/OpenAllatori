package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.ClassFormatException;
import com.allatori.lib.bcel.classfile.Utility;
import com.allatori.lib.bcel.util.TypeParserThreadLocal;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class Type implements Serializable {

  public static final BasicType VOID = new BasicType((byte) 12);

  public static final BasicType BOOLEAN = new BasicType((byte) 4);

  public static final BasicType INT = new BasicType((byte) 10);

  public static final BasicType SHORT = new BasicType((byte) 9);

  public static final BasicType BYTE = new BasicType((byte) 8);

  public static final BasicType LONG = new BasicType((byte) 11);

  public static final BasicType DOUBLE = new BasicType((byte) 7);

  public static final BasicType FLOAT = new BasicType((byte) 6);

  public static final BasicType CHAR = new BasicType((byte) 5);

  public static final ObjectType OBJECT = new ObjectType("java.lang.Object");

  public static final ObjectType CLASS = new ObjectType("java.lang.Class");

  public static final ObjectType STRING = new ObjectType("java.lang.String");

  public static final ObjectType STRINGBUFFER = new ObjectType("java.lang.StringBuffer");

  public static final ObjectType THROWABLE = new ObjectType("java.lang.Throwable");

  public static final Type[] NO_ARGS = new Type[0];

  public static final ReferenceType NULL = new Type$1();

  public static final Type UNKNOWN = new Type$2((byte) 15, "<unknown object>");

  public static ThreadLocal consumed_chars = new TypeParserThreadLocal();

  public byte type;

  public String signature;

  public static int calculateCount(String text) {
    return calculateCount2(calculateCount3(text.substring(text.lastIndexOf(41) + 1)));
  }

  public static Type getType(Class classValue) {
    if (classValue == null) {
      throw new IllegalArgumentException("Class must not be null");
    }
    if (classValue.isArray()) {
      return getType(classValue.getName());
    }
    if (classValue.isPrimitive()) {
      if (classValue == int.class) {
        return INT;
      } else if (classValue == void.class) {
        return VOID;
      } else if (classValue == double.class) {
        return DOUBLE;
      } else if (classValue == float.class) {
        return FLOAT;
      } else if (classValue == boolean.class) {
        return BOOLEAN;
      } else if (classValue == byte.class) {
        return BYTE;
      } else if (classValue == short.class) {
        return SHORT;
      } else if (classValue == byte.class) {
        return BYTE;
      } else if (classValue == long.class) {
        return LONG;
      } else if (classValue == char.class) {
        return CHAR;
      } else {
        throw new IllegalStateException(("Ooops, what primitive type is " + classValue));
      }
    } else {
      return new ObjectType(classValue.getName());
    }
  }

  public static int encode(int value, int value2) {
    return value2 << 2 | value;
  }

  public static String getMethodSignature(Type type, Type[] types) {
    StringBuffer stringBuffer = new StringBuffer("(");
    int value = types == null ? 0 : types.length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = value2;
      value2++;
      stringBuffer.append(types[value4].getSignature());
    }
    stringBuffer.append(')');
    stringBuffer.append(type.getSignature());
    return stringBuffer.toString();
  }

  public static String getSignature(Method method) {
    StringBuffer stringBuffer = new StringBuffer("(");
    Class[] parameterTypes = method.getParameterTypes();
    int value;
    for (int value2 = value = 0; value2 < parameterTypes.length; value2 = value) {
      Class classValue = parameterTypes[value];
      value++;
      stringBuffer.append(getType(classValue).getSignature());
    }
    stringBuffer.append(")");
    stringBuffer.append(getType(method.getReturnType()).getSignature());
    return stringBuffer.toString();
  }

  public static int calculateCount2(String text) {
    int value = 0;
    try {
      if (text.charAt(0) != '(') {
        throw new ClassFormatException(("Invalid method signature: " + text));
      }
      int value2 = 1;
      while (text.charAt(value2) != ')') {
        int value3 = calculateCount3(text.substring(value2));
        value += calculateCount2(value3);
        value2 += calculateCount(value3);
      }
      return value;
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      throw new ClassFormatException(
          ("Invalid method signature: " + text), stringIndexOutOfBoundsException);
    }
  }

  public int getSize() {
    switch (this.type) {
      case 7:
      case 11:
        return 2;
      case 12:
        return 0;
      default:
        return 1;
    }
  }

  public static int calculateCount(int value) {
    return value >> 2;
  }

  public static Type getReturnType(String text) {
    try {
      return getType(text.substring(text.lastIndexOf(41) + 1));
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      throw new ClassFormatException(("Invalid method signature: " + text));
    }
  }

  public static void wrap(ThreadLocal threadLocal, int value) {
    threadLocal.set(new Integer(value));
  }

  public static int unwrap(ThreadLocal threadLocal) {
    return (Integer) threadLocal.get();
  }

  public byte getType() {
    return this.type;
  }

  public Type(byte type, String signature) {
    super();
    this.type = type;
    this.signature = signature;
  }

  public static int calculateCount3(String text) {
    byte value;
    if ((value = Utility.calculateValue2(text)) <= 12) {
      return encode(BasicType.getType(value).getSize(), 1);
    }
    if (value != 13) {
      int value2;
      if ((value2 = text.indexOf(59)) < 0) {
        throw new ClassFormatException(("Invalid signature: " + text));
      } else {
        return encode(1, value2 + 1);
      }
    } else {
      int value3 = 0;
      while (text.charAt(++value3) == '[') {}
      int value4 = calculateCount(calculateCount3(text.substring(value3)));
      return encode(1, value3 + value4);
    }
  }

  public static Type[] getArgumentTypes(String text) {
    ArrayList values = new ArrayList();
    try {
      if (text.charAt(0) != '(') {
        throw new ClassFormatException(("Invalid method signature: " + text));
      }
      for (int index = 1; text.charAt(index) != ')'; index += unwrap(consumed_chars)) {
        values.add(getType(text.substring(index)));
      }
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      throw new ClassFormatException(("Invalid method signature: " + text));
    }
    Type[] types = new Type[values.size()];
    values.toArray(types);
    return types;
  }

  public static int calculateCount2(int value) {
    return value & 3;
  }

  public static final Type getType(String text) throws StringIndexOutOfBoundsException {
    byte value;
    if ((value = Utility.calculateValue2(text)) <= 12) {
      wrap(consumed_chars, 1);
      return BasicType.getType(value);
    }
    if (value != 13) {
      int value2;
      if ((value2 = text.indexOf(59)) < 0) {
        throw new ClassFormatException(("Invalid signature: " + text));
      }
      wrap(consumed_chars, value2 + 1);
      return new ObjectType(text.substring(1, value2).replace('/', '.'));
    } else {
      int value3 = 0;
      while (text.charAt(++value3) == '[') {}
      Type type = getType(text.substring(value3));
      int value4 = unwrap(consumed_chars) + value3;
      wrap(consumed_chars, value4);
      return new ArrayType(type, value3);
    }
  }

  @Override
  public boolean equals(Object result) {
    if (result instanceof Type) {
      Type type2 = (Type) result;
      return this.type == type2.type && this.signature.equals(type2.signature);
    } else {
      return false;
    }
  }

  @Override
  public String toString() {
    return !this.equals(NULL) && this.type < 15
        ? Utility.signatureToString(this.signature, false)
        : this.signature;
  }

  @Override
  public int hashCode() {
    return this.type ^ this.signature.hashCode();
  }

  public String getSignature() {
    return this.signature;
  }

  public static Type[] getTypes(Class[] classs) {
    Type[] types = new Type[classs.length];
    int value;
    for (int value2 = value = 0; value2 < types.length; value2 = value) {
      Type type = getType(classs[value]);
      int value3 = value;
      value++;
      types[value3] = type;
    }
    return types;
  }
}
