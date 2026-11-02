package com.allatori.lib.bcel;

public interface Constants {

  short ALOAD_1 = 43;

  short RETURN = 177;

  short FMUL = 106;

  short FADD = 98;

  short IFNE = 154;

  short PUTSTATIC_QUICK = 211;

  byte VALUE = 11;

  short I2F = 134;

  short DCMPL = 151;

  byte VALUE2 = 10;

  String TEXT4 = "<clinit>";

  String TEXT2 = "<init>";

  String TEXT3 = "<illegal opcode>";

  String TEXT = "<illegal type>";

  String[] TEXTS9 =
      new String[] {
        "public",
        "private",
        "protected",
        "static",
        "final",
        "synchronized",
        "volatile",
        "transient",
        "native",
        "interface",
        "abstract",
        "strictfp",
        "synthetic",
        "annotation",
        "enum"
      };

  String[] TEXTS7 =
      new String[] {
        "",
        "CONSTANT_Utf8",
        "",
        "CONSTANT_Integer",
        "CONSTANT_Float",
        "CONSTANT_Long",
        "CONSTANT_Double",
        "CONSTANT_Class",
        "CONSTANT_String",
        "CONSTANT_Fieldref",
        "CONSTANT_Methodref",
        "CONSTANT_InterfaceMethodref",
        "CONSTANT_NameAndType",
        "",
        "",
        "CONSTANT_MethodHandle",
        "CONSTANT_MethodType",
        "",
        "CONSTANT_InvokeDynamic"
      };

  String[] TEXTS5 = new String[] {"java.lang.Cloneable", "java.io.Serializable"};

  String[] TEXTS =
      new String[] {
        "<illegal type>",
        "<illegal type>",
        "<illegal type>",
        "<illegal type>",
        "boolean",
        "char",
        "float",
        "double",
        "byte",
        "short",
        "int",
        "long",
        "void",
        "array",
        "object",
        "unknown",
        "address"
      };

  String[] TEXTS6 =
      new String[] {
        "<illegal type>",
        "<illegal type>",
        "<illegal type>",
        "<illegal type>",
        "java.lang.Boolean",
        "java.lang.Character",
        "java.lang.Float",
        "java.lang.Double",
        "java.lang.Byte",
        "java.lang.Short",
        "java.lang.Integer",
        "java.lang.Long",
        "java.lang.Void",
        "<illegal type>",
        "<illegal type>",
        "<illegal type>",
        "<illegal type>"
      };

  String[] TEXTS3 =
      new String[] {
        "<illegal type>",
        "<illegal type>",
        "<illegal type>",
        "<illegal type>",
        "Z",
        "C",
        "F",
        "D",
        "B",
        "S",
        "I",
        "J",
        "V",
        "<illegal type>",
        "<illegal type>",
        "<illegal type>"
      };

  short[] NO_OF_OPERANDS =
      new short[] {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 1, 2, 2, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2,
        2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, -2, -2, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2,
        4, 5, 2, 1, 2, 0, 0, 2, 2, 0, 0, -2, 3, 2, 2, 4, 4, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -3, -3
      };

  short[][] TYPE_OF_OPERANDS =
      new short[][] {
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[] {8},
        new short[] {9},
        new short[] {8},
        new short[] {9},
        new short[] {9},
        new short[] {8},
        new short[] {8},
        new short[] {8},
        new short[] {8},
        new short[] {8},
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[] {8},
        new short[] {8},
        new short[] {8},
        new short[] {8},
        new short[] {8},
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[] {8, 8},
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {8},
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9},
        new short[] {9, 8, 8},
        new short[] {9, 8, 8},
        new short[] {9},
        new short[] {8},
        new short[] {9},
        new short[0],
        new short[0],
        new short[] {9},
        new short[] {9},
        new short[0],
        new short[0],
        new short[] {8},
        new short[] {9, 8},
        new short[] {9},
        new short[] {9},
        new short[] {10},
        new short[] {10},
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0],
        new short[0]
      };

  String[] TEXTS8 =
      new String[] {
        "nop",
        "aconst_null",
        "iconst_m1",
        "iconst_0",
        "iconst_1",
        "iconst_2",
        "iconst_3",
        "iconst_4",
        "iconst_5",
        "lconst_0",
        "lconst_1",
        "fconst_0",
        "fconst_1",
        "fconst_2",
        "dconst_0",
        "dconst_1",
        "bipush",
        "sipush",
        "ldc",
        "ldc_w",
        "ldc2_w",
        "iload",
        "lload",
        "fload",
        "dload",
        "aload",
        "iload_0",
        "iload_1",
        "iload_2",
        "iload_3",
        "lload_0",
        "lload_1",
        "lload_2",
        "lload_3",
        "fload_0",
        "fload_1",
        "fload_2",
        "fload_3",
        "dload_0",
        "dload_1",
        "dload_2",
        "dload_3",
        "aload_0",
        "aload_1",
        "aload_2",
        "aload_3",
        "iaload",
        "laload",
        "faload",
        "daload",
        "aaload",
        "baload",
        "caload",
        "saload",
        "istore",
        "lstore",
        "fstore",
        "dstore",
        "astore",
        "istore_0",
        "istore_1",
        "istore_2",
        "istore_3",
        "lstore_0",
        "lstore_1",
        "lstore_2",
        "lstore_3",
        "fstore_0",
        "fstore_1",
        "fstore_2",
        "fstore_3",
        "dstore_0",
        "dstore_1",
        "dstore_2",
        "dstore_3",
        "astore_0",
        "astore_1",
        "astore_2",
        "astore_3",
        "iastore",
        "lastore",
        "fastore",
        "dastore",
        "aastore",
        "bastore",
        "castore",
        "sastore",
        "pop",
        "pop2",
        "dup",
        "dup_x1",
        "dup_x2",
        "dup2",
        "dup2_x1",
        "dup2_x2",
        "swap",
        "iadd",
        "ladd",
        "fadd",
        "dadd",
        "isub",
        "lsub",
        "fsub",
        "dsub",
        "imul",
        "lmul",
        "fmul",
        "dmul",
        "idiv",
        "ldiv",
        "fdiv",
        "ddiv",
        "irem",
        "lrem",
        "frem",
        "drem",
        "ineg",
        "lneg",
        "fneg",
        "dneg",
        "ishl",
        "lshl",
        "ishr",
        "lshr",
        "iushr",
        "lushr",
        "iand",
        "land",
        "ior",
        "lor",
        "ixor",
        "lxor",
        "iinc",
        "i2l",
        "i2f",
        "i2d",
        "l2i",
        "l2f",
        "l2d",
        "f2i",
        "f2l",
        "f2d",
        "d2i",
        "d2l",
        "d2f",
        "i2b",
        "i2c",
        "i2s",
        "lcmp",
        "fcmpl",
        "fcmpg",
        "dcmpl",
        "dcmpg",
        "ifeq",
        "ifne",
        "iflt",
        "ifge",
        "ifgt",
        "ifle",
        "if_icmpeq",
        "if_icmpne",
        "if_icmplt",
        "if_icmpge",
        "if_icmpgt",
        "if_icmple",
        "if_acmpeq",
        "if_acmpne",
        "goto",
        "jsr",
        "ret",
        "tableswitch",
        "lookupswitch",
        "ireturn",
        "lreturn",
        "freturn",
        "dreturn",
        "areturn",
        "return",
        "getstatic",
        "putstatic",
        "getfield",
        "putfield",
        "invokevirtual",
        "invokespecial",
        "invokestatic",
        "invokeinterface",
        "invokedynamic",
        "new",
        "newarray",
        "anewarray",
        "arraylength",
        "athrow",
        "checkcast",
        "instanceof",
        "monitorenter",
        "monitorexit",
        "wide",
        "multianewarray",
        "ifnull",
        "ifnonnull",
        "goto_w",
        "jsr_w",
        "breakpoint",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "<illegal opcode>",
        "impdep1",
        "impdep2"
      };

  int[] COUNTS2 =
      new int[] {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 1, 2, 1, 2, 1, 1, 1, 1,
        1, 2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 3, 4, 3, 4, 3, 3, 3, 3, 1, 2, 1, 2, 3, 2,
        3, 4, 2, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 1, 2, 1, 2, 2, 3, 2, 3,
        2, 3, 2, 4, 2, 4, 2, 4, 0, 1, 1, 1, 2, 2, 2, 1, 1, 1, 2, 2, 2, 1, 1, 1, 4, 2, 2, 4, 4, 1, 1,
        1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 1, 1, 1, 2, 1, 2, 1, 0, 0, -2, 1, -2, -2, -2,
        -2, -2, -2, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, -2, 1, 1, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2
      };

  int[] COUNTS =
      new int[] {
        0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 2,
        2, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 3, 4, 4,
        5, 6, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2,
        1, 2, 1, 2, 1, 2, 1, 2, 0, 2, 1, 2, 1, 1, 2, 1, 2, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -2, 0, -2, 0, -2, -2,
        -2, -2, -2, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2, -2
      };

  String[] TEXTS4 =
      new String[] {
        "SourceFile",
        "ConstantValue",
        "Code",
        "Exceptions",
        "LineNumberTable",
        "LocalVariableTable",
        "InnerClasses",
        "Synthetic",
        "Deprecated",
        "PMGClass",
        "Signature",
        "StackMap",
        "RuntimeVisibleAnnotations",
        "RuntimeInvisibleAnnotations",
        "RuntimeVisibleParameterAnnotations",
        "RuntimeInvisibleParameterAnnotations",
        "AnnotationDefault",
        "LocalVariableTypeTable",
        "EnclosingMethod",
        "StackMapTable",
        "BootstrapMethods",
        "MethodParameters",
        "NestHost",
        "NestMembers"
      };

  String[] TEXTS2 =
      new String[] {
        "Bogus", "Integer", "Float", "Double", "Long", "Null", "InitObject", "Object", "NewObject"
      };

  short ILOAD_1 = 27;

  short LASTORE = 80;

  short VALUE3 = 128;

  short INSTANCEOF = 193;

  short INEG = 116;

  byte VALUE4 = 6;

  short ILOAD = 21;

  short VALUE5 = 16384;

  short PUSH = 4711;

  short F2D = 141;

  short DDIV = 111;

  short LSTORE = 55;

  byte VALUE6 = 3;

  short VALUE7 = 0;

  byte VALUE8 = 11;

  short VALUE9 = 0;

  short LDC_W = 19;

  short ICONST_3 = 6;

  byte VALUE10 = 1;

  short DCMPG = 152;

  short MONITOREXIT = 195;

  short FSUB = 102;

  short DLOAD_1 = 39;

  int COUNT = 247;

  short VALUE11 = 3;

  byte VALUE12 = 3;

  short VALUE13 = 45;

  short IADD = 96;

  byte VALUE14 = 9;

  short TABLESWITCH = 170;

  short IREM = 112;

  short LSTORE_2 = 65;

  short LDC_W_QUICK = 204;

  int COUNT2 = 255;

  byte VALUE15 = 8;

  short DUP2_X1 = 93;

  short GETFIELD = 180;

  short VALUE16 = 128;

  short ASTORE_3 = 78;

  short ACC_NATIVE = 256;

  short VALUE17 = 3;

  short ACC_ABSTRACT = 1024;

  short DUP_X1 = 90;

  short VALUE18 = 46;

  short ISUB = 100;

  short FSTORE_3 = 70;

  short VALUE19 = 0;

  byte VALUE20 = 14;

  short RESERVED = -3;

  short DADD = 99;

  short LCONST_1 = 10;

  int COUNT3 = 251;

  int COUNT4 = 63;

  short GETFIELD2_QUICK = 208;

  short DSTORE_2 = 73;

  short LMUL = 105;

  short FNEG = 118;

  short FSTORE_0 = 67;

  short ISTORE_1 = 60;

  int COUNT5 = 252;

  byte VALUE21 = 18;

  short VALUE22 = 2;

  short FSTORE_2 = 69;

  short FLOAD_2 = 36;

  short PUTSTATIC = 179;

  short VALUE23 = 32;

  short LUSHR = 125;

  short UNPREDICTABLE = -2;

  byte VALUE24 = 6;

  byte VALUE25 = 9;

  short VALUE26 = -32768;

  short ISTORE_0 = 59;

  short VALUE27 = 183;

  short PUTFIELD_QUICK_W = 228;

  short ICONST_4 = 7;

  short ACC_ANNOTATION = 8192;

  short ISHR = 122;

  short IRETURN = 172;

  int COUNT6 = 0;

  short VALUE28 = 45;

  short MULTIANEWARRAY = 197;

  short VALUE29 = 12;

  short GETFIELD_QUICK = 206;

  short LSHR = 123;

  short ASTORE_0 = 75;

  int COUNT7 = 254;

  int COUNT8 = 250;

  short PUTSTATIC2_QUICK = 213;

  short GETSTATIC = 178;

  short GETSTATIC_QUICK = 210;

  short L2D = 138;

  byte VALUE30 = 14;

  short SWITCH = 4712;

  short VALUE31 = 64;

  byte ATTR_UNKNOWN = -1;

  int COUNT9 = 64;

  byte VALUE32 = 23;

  short VALUE33 = 49;

  short PUTFIELD = 181;

  short GETSTATIC2_QUICK = 212;

  short VALUE34 = 2;

  byte VALUE35 = 12;

  short DASTORE = 82;

  short ALOAD_2 = 44;

  short INSTANCEOF_QUICK = 225;

  short DSUB = 103;

  short LLOAD = 22;

  byte VALUE36 = 1;

  short WIDE = 196;

  int COUNT10 = 65535;

  short LREM = 113;

  short VALUE37 = 8;

  short DSTORE_3 = 74;

  byte VALUE38 = 16;

  short NEWARRAY = 188;

  byte VALUE39 = 5;

  byte VALUE40 = 1;

  short ILOAD_0 = 26;

  short IF_ICMPGE = 162;

  short DCONST_1 = 15;

  short FDIV = 110;

  short DUP_X2 = 91;

  short LLOAD_0 = 30;

  byte VALUE41 = 15;

  short VALUE42 = 1;

  byte VALUE43 = 4;

  short ISTORE_2 = 61;

  short VALUE44 = 4;

  short BASTORE = 84;

  short ILOAD_3 = 29;

  short FLOAD_0 = 34;

  byte VALUE45 = 7;

  short VALUE46 = 146;

  short IF_ACMPNE = 166;

  byte VALUE47 = 7;

  int COUNT11 = 255;

  short VALUE48 = 52;

  short VALUE49 = 48;

  short DMUL = 107;

  short FLOAD_3 = 37;

  short IUSHR = 124;

  byte VALUE50 = 2;

  byte VALUE51 = 16;

  short IFLE = 158;

  byte VALUE52 = 9;

  short VALUE53 = 147;

  short ICONST_2 = 5;

  short ALOAD = 25;

  short FSTORE = 56;

  byte VALUE54 = 13;

  byte VALUE55 = 10;

  short ATHROW = 191;

  short FCMPL = 149;

  short DLOAD_2 = 40;

  short I2L = 133;

  short DUP = 89;

  byte VALUE56 = 13;

  short DUP2_X2 = 94;

  short CASTORE = 85;

  short LDC = 18;

  byte VALUE57 = 19;

  byte VALUE58 = 4;

  byte VALUE59 = 17;

  short INVOKESTATIC = 184;

  short ALOAD_0 = 42;

  short FCONST_2 = 13;

  short VALUE60 = 52;

  byte VALUE61 = 4;

  short SALOAD = 53;

  byte VALUE62 = 8;

  short POP2 = 88;

  short IF_ACMPEQ = 165;

  short IMPDEP2 = 255;

  byte VALUE63 = 21;

  short RET = 169;

  byte VALUE64 = 11;

  short IMPDEP1 = 254;

  short VALUE65 = 49;

  short INVOKEVIRTUALOBJECT_QUICK = 219;

  short BREAKPOINT = 202;

  short DREM = 115;

  short IF_ICMPNE = 160;

  byte VALUE66 = 16;

  short IFNULL = 198;

  short IFNONNULL = 199;

  short LDC_QUICK = 203;

  short DNEG = 119;

  short JSR = 168;

  short VALUE67 = 16384;

  short FASTORE = 81;

  short SWAP = 95;

  short VALUE68 = 16;

  short FCONST_0 = 11;

  short L2F = 137;

  byte VALUE69 = 18;

  byte VALUE70 = 12;

  short ANEWARRAY_QUICK = 222;

  short ACC_SYNTHETIC = 4096;

  short INVOKENONVIRTUAL_QUICK = 215;

  short D2I = 142;

  byte VALUE71 = 5;

  byte VALUE72 = 4;

  byte VALUE73 = 6;

  short NEW_QUICK = 221;

  short FLOAD = 23;

  short LSTORE_3 = 66;

  short PUTFIELD2_QUICK = 209;

  short DLOAD_0 = 38;

  byte VALUE74 = 5;

  short LDIV = 109;

  short INVOKESUPER_QUICK = 216;

  byte VALUE75 = 0;

  short ARETURN = 176;

  byte VALUE76 = 15;

  int COUNT12 = 248;

  short LSTORE_0 = 63;

  short IFGE = 156;

  short INVOKEVIRTUAL_QUICK_W = 226;

  short F2I = 139;

  short GOTO = 167;

  short LCMP = 148;

  short INVOKEINTERFACE = 185;

  short MULTIANEWARRAY_QUICK = 223;

  short VALUE77 = 47;

  short INVOKEVIRTUAL = 182;

  short VALUE78 = 32;

  short LOOKUPSWITCH = 171;

  short IFLT = 155;

  short SIPUSH = 17;

  byte VALUE79 = 20;

  short VALUE80 = 51;

  short VALUE81 = 128;

  byte VALUE82 = 8;

  short LLOAD_3 = 33;

  short LCONST_0 = 9;

  int COUNT13 = 127;

  short INVOKEDYNAMIC = 186;

  short LAND = 127;

  short VALUE83 = 50;

  short VALUE84 = 16;

  int MAX_CODE_SIZE = 65536;

  byte VALUE85 = 3;

  short LXOR = 131;

  short VALUE86 = 3;

  short LSUB = 101;

  short GETFIELD_QUICK_W = 227;

  short AASTORE = 83;

  byte VALUE87 = 2;

  short ANEWARRAY = 189;

  short LOR = 129;

  short VALUE88 = 24;

  short IF_ICMPLE = 164;

  short VALUE89 = 147;

  short IMUL = 104;

  byte VALUE90 = 14;

  short L2I = 136;

  short VALUE91 = 145;

  short VALUE92 = 0;

  short CHECKCAST_QUICK = 224;

  short I2D = 135;

  short ISTORE_3 = 62;

  byte VALUE93 = 0;

  short FCMPG = 150;

  short LSHL = 121;

  short GOTO_W = 200;

  int COUNT14 = 65535;

  short IAND = 126;

  short ACC_STRICT = 2048;

  short VALUE94 = 0;

  byte VALUE95 = 5;

  byte VALUE96 = 22;

  short DSTORE_1 = 72;

  byte VALUE97 = 7;

  short ISHL = 120;

  short VALUE98 = 64;

  short NEW = 187;

  short LLOAD_1 = 31;

  short DSTORE = 57;

  short IF_ICMPEQ = 159;

  short IFGT = 157;

  short DRETURN = 175;

  short INVOKEVIRTUAL_QUICK = 214;

  short IDIV = 108;

  short ASTORE_1 = 76;

  byte VALUE99 = 7;

  short MONITORENTER = 194;

  short FREM = 114;

  short VALUE100 = 8;

  short VALUE101 = 50;

  short LRETURN = 173;

  short FSTORE_1 = 68;

  short VALUE102 = 32;

  short VALUE103 = 48;

  short IASTORE = 79;

  short VALUE104 = 47;

  short LADD = 97;

  short ISTORE = 54;

  short ILOAD_2 = 28;

  short VALUE105 = 64;

  short D2L = 143;

  short VALUE106 = 1;

  byte VALUE107 = 6;

  short FLOAD_1 = 35;

  short VALUE108 = 145;

  byte VALUE109 = 15;

  short ACC_INTERFACE = 512;

  short ARRAYLENGTH = 190;

  short PUTFIELD_QUICK = 207;

  short IF_ICMPLT = 161;

  short FRETURN = 174;

  short VALUE110 = 45;

  short VALUE111 = 32;

  short VALUE112 = 46;

  short VALUE113 = 183;

  short DSTORE_0 = 71;

  short ASTORE_2 = 77;

  short JSR_W = 201;

  short INVOKESTATIC_QUICK = 217;

  short DCONST_0 = 14;

  short VALUE114 = 4;

  short IFEQ = 153;

  short ASTORE = 58;

  short VALUE115 = 0;

  short VALUE116 = 146;

  short LDC2_W = 20;

  short INVOKEINTERFACE_QUICK = 218;

  byte VALUE117 = 8;

  short DLOAD_3 = 41;

  short CHECKCAST = 192;

  short POP = 87;

  short F2L = 140;

  short DUP2 = 92;

  short IINC = 132;

  short D2F = 144;

  short UNDEFINED = -1;

  byte VALUE118 = 12;

  short LDC2_W_QUICK = 205;

  short VALUE119 = 0;

  short VALUE120 = 51;

  short IXOR = 130;

  short LNEG = 117;

  short VALUE121 = 0;

  byte VALUE122 = 10;

  short SASTORE = 86;

  short VALUE123 = 24;

  short IF_ICMPGT = 163;
}
