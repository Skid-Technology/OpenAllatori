package com.allatori.lib.bcel.generic;

public interface InstructionConstants {

  Instruction NOP = new NOP();

  Instruction ACONST_NULL = new ACONST_NULL();

  Instruction ICONST_M1 = new ICONST(-1);

  Instruction ICONST_0 = new ICONST(0);

  Instruction ICONST_1 = new ICONST(1);

  Instruction ICONST_2 = new ICONST(2);

  Instruction ICONST_3 = new ICONST(3);

  Instruction ICONST_4 = new ICONST(4);

  Instruction ICONST_5 = new ICONST(5);

  Instruction LCONST_0 = new LCONST(0L);

  Instruction LCONST_1 = new LCONST(1L);

  Instruction FCONST_0 = new FCONST(0.0F);

  Instruction FCONST_1 = new FCONST(1.0F);

  Instruction FCONST_2 = new FCONST(2.0F);

  Instruction DCONST_0 = new DCONST(0.0);

  Instruction DCONST_1 = new DCONST(1.0);

  ArrayInstruction IALOAD = new IALOAD();

  ArrayInstruction LALOAD = new LALOAD();

  ArrayInstruction FALOAD = new FALOAD();

  ArrayInstruction DALOAD = new DALOAD();

  ArrayInstruction AALOAD = new AALOAD();

  ArrayInstruction BALOAD = new BALOAD();

  ArrayInstruction CALOAD = new CALOAD();

  ArrayInstruction SALOAD = new SALOAD();

  ArrayInstruction IASTORE = new IASTORE();

  ArrayInstruction LASTORE = new LASTORE();

  ArrayInstruction FASTORE = new FASTORE();

  ArrayInstruction DASTORE = new DASTORE();

  ArrayInstruction AASTORE = new AASTORE();

  ArrayInstruction BASTORE = new BASTORE();

  ArrayInstruction CASTORE = new CASTORE();

  ArrayInstruction SASTORE = new SASTORE();

  StackInstruction POP = new POP();

  StackInstruction POP2 = new POP2();

  StackInstruction DUP = new DUP();

  StackInstruction DUP_X1 = new DUP_X1();

  StackInstruction DUP_X2 = new DUP_X2();

  StackInstruction DUP2 = new DUP2();

  StackInstruction DUP2_X1 = new DUP2_X1();

  StackInstruction DUP2_X2 = new DUP2_X2();

  StackInstruction SWAP = new SWAP();

  ArithmeticInstruction IADD = new IADD();

  ArithmeticInstruction LADD = new LADD();

  ArithmeticInstruction FADD = new FADD();

  ArithmeticInstruction DADD = new DADD();

  ArithmeticInstruction ISUB = new ISUB();

  ArithmeticInstruction LSUB = new LSUB();

  ArithmeticInstruction FSUB = new FSUB();

  ArithmeticInstruction DSUB = new DSUB();

  ArithmeticInstruction IMUL = new IMUL();

  ArithmeticInstruction LMUL = new LMUL();

  ArithmeticInstruction FMUL = new FMUL();

  ArithmeticInstruction DMUL = new DMUL();

  ArithmeticInstruction IDIV = new IDIV();

  ArithmeticInstruction LDIV = new LDIV();

  ArithmeticInstruction FDIV = new FDIV();

  ArithmeticInstruction DDIV = new DDIV();

  ArithmeticInstruction IREM = new IREM();

  ArithmeticInstruction LREM = new LREM();

  ArithmeticInstruction FREM = new FREM();

  ArithmeticInstruction DREM = new DREM();

  ArithmeticInstruction INEG = new INEG();

  ArithmeticInstruction LNEG = new LNEG();

  ArithmeticInstruction FNEG = new FNEG();

  ArithmeticInstruction DNEG = new DNEG();

  ArithmeticInstruction ISHL = new ISHL();

  ArithmeticInstruction LSHL = new LSHL();

  ArithmeticInstruction ISHR = new ISHR();

  ArithmeticInstruction LSHR = new LSHR();

  ArithmeticInstruction IUSHR = new IUSHR();

  ArithmeticInstruction LUSHR = new LUSHR();

  ArithmeticInstruction IAND = new IAND();

  ArithmeticInstruction LAND = new LAND();

  ArithmeticInstruction IOR = new IOR();

  ArithmeticInstruction LOR = new LOR();

  ArithmeticInstruction IXOR = new IXOR();

  ArithmeticInstruction LXOR = new LXOR();

  ConversionInstruction I2L = new I2L();

  ConversionInstruction I2F = new I2F();

  ConversionInstruction I2D = new I2D();

  ConversionInstruction L2I = new L2I();

  ConversionInstruction L2F = new L2F();

  ConversionInstruction L2D = new L2D();

  ConversionInstruction F2I = new F2I();

  ConversionInstruction F2L = new F2L();

  ConversionInstruction F2D = new F2D();

  ConversionInstruction D2I = new D2I();

  ConversionInstruction D2L = new D2L();

  ConversionInstruction D2F = new D2F();

  ConversionInstruction I2B = new I2B();

  ConversionInstruction I2C = new I2C();

  ConversionInstruction I2S = new I2S();

  Instruction LCMP = new LCMP();

  Instruction FCMPL = new FCMPL();

  Instruction FCMPG = new FCMPG();

  Instruction DCMPL = new DCMPL();

  Instruction DCMPG = new DCMPG();

  ReturnInstruction IRETURN = new IRETURN();

  ReturnInstruction LRETURN = new LRETURN();

  ReturnInstruction FRETURN = new FRETURN();

  ReturnInstruction DRETURN = new DRETURN();

  ReturnInstruction ARETURN = new ARETURN();

  ReturnInstruction RETURN = new RETURN();

  Instruction ARRAYLENGTH = new ARRAYLENGTH();

  Instruction ATHROW = new ATHROW();

  Instruction MONITORENTER = new MONITORENTER();

  Instruction MONITOREXIT = new MONITOREXIT();

  LocalVariableInstruction ALOAD_0 = new ALOAD(0);

  LocalVariableInstruction LOCAL_VARIABLE_INSTRUCTION6 = ALOAD_0;

  LocalVariableInstruction ALOAD_1 = new ALOAD(1);

  LocalVariableInstruction ALOAD_2 = new ALOAD(2);

  LocalVariableInstruction ILOAD_0 = new ILOAD(0);

  LocalVariableInstruction ILOAD_1 = new ILOAD(1);

  LocalVariableInstruction ILOAD_2 = new ILOAD(2);

  LocalVariableInstruction ASTORE_0 = new ASTORE(0);

  LocalVariableInstruction ASTORE_1 = new ASTORE(1);

  LocalVariableInstruction ASTORE_2 = new ASTORE(2);

  LocalVariableInstruction ISTORE_0 = new ISTORE(0);

  LocalVariableInstruction ISTORE_1 = new ISTORE(1);

  LocalVariableInstruction ISTORE_2 = new ISTORE(2);

  Instruction[] INSTRUCTIONS = new Instruction[256];

  InstructionConstants$Clinit bla = new InstructionConstants$Clinit();
}
