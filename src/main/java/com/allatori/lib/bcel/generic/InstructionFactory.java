package com.allatori.lib.bcel.generic;

import java.io.Serializable;

public class InstructionFactory implements InstructionConstants, Serializable {

  public ConstantPoolGen cp;

  public static final String[] short_names = new String[] {"C", "F", "D", "B", "S", "I", "L"};

  public ClassGen cg;

  public static InstructionFactory$MethodObject[] append_mos;

  public INSTANCEOF createInstanceOf(ReferenceType referenceType) {
    return referenceType instanceof ArrayType
        ? new INSTANCEOF(this.cp.addArrayClass((ArrayType) referenceType))
        : new INSTANCEOF(this.cp.addClass((ObjectType) referenceType));
  }

  public GETSTATIC createGetStatic(String text, String text2, Type type) {
    return new GETSTATIC(this.cp.addFieldref(text, text2, type.getSignature()));
  }

  public InstructionFactory(ClassGen classGen, ConstantPoolGen constantPoolGen) {
    super();
    this.cg = classGen;
    this.cp = constantPoolGen;
  }

  public InstructionFactory(ClassGen classGen) {
    this(classGen, classGen.getConstantPool());
  }

  public static final ArithmeticInstruction createArithmeticInstruction(char value, String text) {
    switch (value) {
      case '%':
        return LREM;
      case '&':
        return LAND;
      case '*':
        return LMUL;
      case '+':
        return LADD;
      case '-':
        return LSUB;
      case '/':
        return LDIV;
      case '<':
        return LSHL;
      case '>':
        if (text.equals(">>>")) {
          return LUSHR;
        }
        return LSHR;
      case '^':
        return LXOR;
      case '|':
        return LOR;
      default:
        throw new RuntimeException(("Invalid operand " + text));
    }
  }

  public PUTSTATIC createPutStatic(String text, String text2, Type type) {
    return new PUTSTATIC(this.cp.addFieldref(text, text2, type.getSignature()));
  }

  public static LocalVariableInstruction createLocalVariableInstruction(Type type, int value) {
    switch (type.getType()) {
      case 4:
      case 5:
      case 8:
      case 9:
      case 10:
        return new ISTORE(value);
      case 6:
        return new FSTORE(value);
      case 7:
        return new DSTORE(value);
      case 11:
        return new LSTORE(value);
      case 12:
      default:
        throw new RuntimeException(("Invalid type " + type));
      case 13:
      case 14:
        return new ASTORE(value);
    }
  }

  public static ArrayInstruction createArrayInstruction(Type type) {
    switch (type.getType()) {
      case 4:
      case 8:
        return BASTORE;
      case 5:
        return CASTORE;
      case 6:
        return FASTORE;
      case 7:
        return DASTORE;
      case 9:
        return SASTORE;
      case 10:
        return IASTORE;
      case 11:
        return LASTORE;
      case 12:
      default:
        throw new RuntimeException(("Invalid type " + type));
      case 13:
      case 14:
        return AASTORE;
    }
  }

  public void setClassGen(ClassGen classGen) {
    this.cg = classGen;
  }

  public static StackInstruction getStackInstruction(int value) {
    return value == 2 ? DUP2_X2 : DUP_X2;
  }

  static {
    InstructionFactory$MethodObject[] instructionFactory$MethodObjects =
        new InstructionFactory$MethodObject[12];
    instructionFactory$MethodObjects[0] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.STRING}, 1);
    instructionFactory$MethodObjects[1] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.OBJECT}, 1);
    instructionFactory$MethodObjects[2] = null;
    instructionFactory$MethodObjects[3] = null;
    instructionFactory$MethodObjects[4] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.BOOLEAN}, 1);
    instructionFactory$MethodObjects[5] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.CHAR}, 1);
    instructionFactory$MethodObjects[6] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.FLOAT}, 1);
    instructionFactory$MethodObjects[7] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.DOUBLE}, 1);
    instructionFactory$MethodObjects[8] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.INT}, 1);
    instructionFactory$MethodObjects[9] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.INT}, 1);
    instructionFactory$MethodObjects[10] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.INT}, 1);
    instructionFactory$MethodObjects[11] =
        new InstructionFactory$MethodObject(
            "java.lang.StringBuffer", "append", Type.STRINGBUFFER, new Type[] {Type.LONG}, 1);
    append_mos = instructionFactory$MethodObjects;
  }

  public static StackInstruction getStackInstruction2(int value) {
    return value == 2 ? DUP2 : DUP;
  }

  public ConstantPoolGen getConstantPool() {
    return this.cp;
  }

  public CHECKCAST createCheckCast(ReferenceType referenceType) {
    return referenceType instanceof ArrayType
        ? new CHECKCAST(this.cp.addArrayClass((ArrayType) referenceType))
        : new CHECKCAST(this.cp.addClass((ObjectType) referenceType));
  }

  public static StackInstruction getStackInstruction3(int value) {
    return value == 2 ? POP2 : POP;
  }

  public PUTFIELD createPutField(String text, String text2, Type type) {
    return new PUTFIELD(this.cp.addFieldref(text, text2, type.getSignature()));
  }

  public static StackInstruction getStackInstruction4(int value) {
    return value == 2 ? DUP2_X1 : DUP_X1;
  }

  public Instruction createAppend(Type type) {
    byte type2 = type.getType();
    if (isString(type)) {
      return this.createInvoke(append_mos[0], (short) 182);
    }
    switch (type2) {
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
        return this.createInvoke(append_mos[type2], (short) 182);
      case 12:
      default:
        throw new RuntimeException(("Oops: No append for this type? " + type));
      case 13:
      case 14:
        return this.createInvoke(append_mos[1], (short) 182);
    }
  }

  public void setConstantPool(ConstantPoolGen constantPoolGen) {
    this.cp = constantPoolGen;
  }

  public GETFIELD createGetField(String text, String text2, Type type) {
    return new GETFIELD(this.cp.addFieldref(text, text2, type.getSignature()));
  }

  public InvokeInstruction createInvoke(
      InstructionFactory$MethodObject instructionFactory$MethodObject, short value) {
    return this.createInvoke(
        instructionFactory$MethodObject.class_name,
        instructionFactory$MethodObject.name,
        instructionFactory$MethodObject.result_type,
        instructionFactory$MethodObject.arg_types,
        value);
  }

  public static final boolean isString(Type type) {
    return type instanceof ObjectType
        && ((ObjectType) type).getClassName().equals("java.lang.String");
  }

  public static LocalVariableInstruction createLocalVariableInstruction2(Type type, int value) {
    switch (type.getType()) {
      case 4:
      case 5:
      case 8:
      case 9:
      case 10:
        return new ILOAD(value);
      case 6:
        return new FLOAD(value);
      case 7:
        return new DLOAD(value);
      case 11:
        return new LLOAD(value);
      case 12:
      default:
        throw new RuntimeException(("Invalid type " + type));
      case 13:
      case 14:
        return new ALOAD(value);
    }
  }

  public static ArrayInstruction createArrayInstruction2(Type type) {
    switch (type.getType()) {
      case 4:
      case 8:
        return BALOAD;
      case 5:
        return CALOAD;
      case 6:
        return FALOAD;
      case 7:
        return DALOAD;
      case 9:
        return SALOAD;
      case 10:
        return IALOAD;
      case 11:
        return LALOAD;
      case 12:
      default:
        throw new RuntimeException(("Invalid type " + type));
      case 13:
      case 14:
        return AALOAD;
    }
  }

  public FieldInstruction createFieldAccess(String text, String text2, Type type, short value) {
    String signature = type.getSignature();
    int value2 = this.cp.addFieldref(text, text2, signature);
    switch (value) {
      case 178:
        return new GETSTATIC(value2);
      case 179:
        return new PUTSTATIC(value2);
      case 180:
        return new GETFIELD(value2);
      case 181:
        return new PUTFIELD(value2);
      default:
        throw new RuntimeException(("Oops: Unknown getfield kind:" + value));
    }
  }

  public static BranchInstruction createBranchInstruction(
      short value, InstructionHandle instructionHandle) {
    switch (value) {
      case 153:
        return new IFEQ(instructionHandle);
      case 154:
        return new IFNE(instructionHandle);
      case 155:
        return new IFLT(instructionHandle);
      case 156:
        return new IFGE(instructionHandle);
      case 157:
        return new IFGT(instructionHandle);
      case 158:
        return new IFLE(instructionHandle);
      case 159:
        return new IF_ICMPEQ(instructionHandle);
      case 160:
        return new IF_ICMPNE(instructionHandle);
      case 161:
        return new IF_ICMPLT(instructionHandle);
      case 162:
        return new IF_ICMPGE(instructionHandle);
      case 163:
        return new IF_ICMPGT(instructionHandle);
      case 164:
        return new IF_ICMPLE(instructionHandle);
      case 165:
        return new IF_ACMPEQ(instructionHandle);
      case 166:
        return new IF_ACMPNE(instructionHandle);
      case 167:
        return new GOTO(instructionHandle);
      case 168:
        return new JSR(instructionHandle);
      case 169:
      case 170:
      case 171:
      case 172:
      case 173:
      case 174:
      case 175:
      case 176:
      case 177:
      case 178:
      case 179:
      case 180:
      case 181:
      case 182:
      case 183:
      case 184:
      case 185:
      case 186:
      case 187:
      case 188:
      case 189:
      case 190:
      case 191:
      case 192:
      case 193:
      case 194:
      case 195:
      case 196:
      case 197:
      default:
        throw new RuntimeException(("Invalid opcode: " + value));
      case 198:
        return new IFNULL(instructionHandle);
      case 199:
        return new IFNONNULL(instructionHandle);
      case 200:
        return new GOTO_W(instructionHandle);
      case 201:
        return new JSR_W(instructionHandle);
    }
  }

  public Instruction createNewArray(Type type, short value) {
    if (value == 1) {
      if (type instanceof ObjectType) {
        return new ANEWARRAY(this.cp.addClass((ObjectType) type));
      } else {
        return type instanceof ArrayType
            ? new ANEWARRAY(this.cp.addArrayClass((ArrayType) type))
            : new NEWARRAY(((BasicType) type).getType());
      }
    } else {
      ArrayType arrayType;
      if (type instanceof ArrayType) {
        arrayType = (ArrayType) type;
      } else {
        arrayType = new ArrayType(type, value);
      }
      return new MULTIANEWARRAY(this.cp.addArrayClass(arrayType), value);
    }
  }

  public NEW createNew(ObjectType objectType) {
    return new NEW(this.cp.addClass(objectType));
  }

  public Instruction createCast(Type type, Type type2) {
    if (type instanceof BasicType && type2 instanceof BasicType) {
      byte type3 = type2.getType();
      byte type4 = type.getType();
      if (type3 == 11 && (type4 == 5 || type4 == 8 || type4 == 9)) {
        type4 = 10;
      }
      String text =
          "org.apache.bcel.generic." + short_names[type4 - 5] + "2" + short_names[type3 - 5];
      try {
        return (Instruction) Class.forName(text).newInstance();
      } catch (Exception exception) {
        throw new RuntimeException(("Could not find instruction: " + text));
      }
    } else if (!(type instanceof ReferenceType) || !(type2 instanceof ReferenceType)) {
      throw new RuntimeException(("Can not cast " + type + " to " + type2));
    } else {
      return type2 instanceof ArrayType
          ? new CHECKCAST(this.cp.addArrayClass((ArrayType) type2))
          : new CHECKCAST(this.cp.addClass(((ObjectType) type2).getClassName()));
    }
  }

  public static ArithmeticInstruction createBinaryOperation(String text, Type type) {
    char value = text.toCharArray()[0];
    switch (type.getType()) {
      case 5:
      case 8:
      case 9:
      case 10:
        return createArithmeticInstruction2(value, text);
      case 6:
        return createArithmeticInstruction(value);
      case 7:
        return createArithmeticInstruction2(value);
      case 11:
        return createArithmeticInstruction(value, text);
      default:
        throw new RuntimeException(("Invalid type " + type));
    }
  }

  public ClassGen getClassGen() {
    return this.cg;
  }

  public static ReturnInstruction createReturn(Type type) {
    switch (type.getType()) {
      case 4:
      case 5:
      case 8:
      case 9:
      case 10:
        return IRETURN;
      case 6:
        return FRETURN;
      case 7:
        return DRETURN;
      case 11:
        return LRETURN;
      case 12:
        return RETURN;
      case 13:
      case 14:
        return ARETURN;
      default:
        throw new RuntimeException(("Invalid type: " + type));
    }
  }

  public static final ArithmeticInstruction createArithmeticInstruction(char value) {
    switch (value) {
      case '*':
        return FMUL;
      case '+':
        return FADD;
      case ',':
      case '.':
      default:
        throw new RuntimeException(("Invalid operand " + value));
      case '-':
        return FSUB;
      case '/':
        return FDIV;
    }
  }

  public static Instruction createThis() {
    return new ALOAD(0);
  }

  public InstructionList createPrintln(String text) {
    InstructionList instructionList = new InstructionList();
    int value = this.cp.addFieldref("java.lang.System", "out", "Ljava/io/PrintStream;");
    int value2 = this.cp.addMethodref("java.io.PrintStream", "println", "(Ljava/lang/String;)V");
    instructionList.getInstructionHandle(new GETSTATIC(value));
    instructionList.getInstructionHandle(new PUSH(this.cp, text));
    instructionList.getInstructionHandle(new INVOKEVIRTUAL(value2));
    return instructionList;
  }

  public NEW createNew(String text) {
    return this.createNew(new ObjectType(text));
  }

  public static Instruction createNull(Type type) {
    switch (type.getType()) {
      case 4:
      case 5:
      case 8:
      case 9:
      case 10:
        return ICONST_0;
      case 6:
        return FCONST_0;
      case 7:
        return DCONST_0;
      case 11:
        return LCONST_0;
      case 12:
        return NOP;
      case 13:
      case 14:
        return ACONST_NULL;
      default:
        throw new RuntimeException(("Invalid type: " + type));
    }
  }

  public InvokeInstruction createInvoke(
      String text, String text2, Type type, Type[] types, short value) {
    int value2 = 0;
    String methodSignature = Type.getMethodSignature(type, types);
    int value3;
    for (int value4 = value3 = 0; value4 < types.length; value4 = value3) {
      Type type2 = types[value3];
      int value5 = value2;
      value3++;
      value2 = value5 + type2.getSize();
    }
    int value6;
    short value7;
    if (value == 185) {
      value6 = this.cp.addInterfaceMethodref(text, text2, methodSignature);
      value7 = value;
    } else {
      value6 = this.cp.addMethodref(text, text2, methodSignature);
      value7 = value;
    }
    switch (value7) {
      case 182:
        return new INVOKEVIRTUAL(value6);
      case 183:
        return new INVOKESPECIAL(value6);
      case 184:
        return new INVOKESTATIC(value6);
      case 185:
        return new INVOKEINTERFACE(value6, value2 + 1);
      default:
        throw new RuntimeException(("Oops: Unknown invoke kind:" + value));
    }
  }

  public InstructionFactory(ConstantPoolGen constantPoolGen) {
    this(null, constantPoolGen);
  }

  public static final ArithmeticInstruction createArithmeticInstruction2(char value) {
    switch (value) {
      case '*':
        return DMUL;
      case '+':
        return DADD;
      case ',':
      case '.':
      default:
        throw new RuntimeException(("Invalid operand " + value));
      case '-':
        return DSUB;
      case '/':
        return DDIV;
    }
  }

  public static final ArithmeticInstruction createArithmeticInstruction2(char value, String text) {
    switch (value) {
      case '%':
        return IREM;
      case '&':
        return IAND;
      case '*':
        return IMUL;
      case '+':
        return IADD;
      case '-':
        return ISUB;
      case '/':
        return IDIV;
      case '<':
        return ISHL;
      case '>':
        if (text.equals(">>>")) {
          return IUSHR;
        }
        return ISHR;
      case '^':
        return IXOR;
      case '|':
        return IOR;
      default:
        throw new RuntimeException(("Invalid operand " + text));
    }
  }

  public Instruction createConstant(Object result) {
    PUSH pUSH;
    if (result instanceof Number) {
      pUSH = new PUSH(this.cp, (Number) result);
    } else if (result instanceof String) {
      pUSH = new PUSH(this.cp, (String) result);
    } else if (result instanceof Boolean) {
      pUSH = new PUSH(this.cp, (Boolean) result);
    } else {
      if (!(result instanceof Character)) {
        throw new ClassGenException(("Illegal type: " + result.getClass()));
      }
      pUSH = new PUSH(this.cp, (Character) result);
    }
    return pUSH.getInstruction();
  }
}
