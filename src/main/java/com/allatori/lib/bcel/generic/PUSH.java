package com.allatori.lib.bcel.generic;

public final class PUSH
    implements CompoundInstruction, VariableLengthInstruction, InstructionConstants {

  public Instruction instruction;

  public PUSH(ConstantPoolGen constantPoolGen, String text) {
    super();
    if (text == null) {
      this.instruction = ACONST_NULL;
    } else {
      this.instruction = new LDC(constantPoolGen.addString(text));
    }
  }

  public PUSH(ConstantPoolGen constantPoolGen, Number number) {
    super();
    if (number instanceof Integer || number instanceof Short || number instanceof Byte) {
      this.instruction = new PUSH(constantPoolGen, number.intValue()).instruction;
    } else if (number instanceof Double) {
      this.instruction = new PUSH(constantPoolGen, number.doubleValue()).instruction;
    } else if (number instanceof Float) {
      this.instruction = new PUSH(constantPoolGen, number.floatValue()).instruction;
    } else if (number instanceof Long) {
      this.instruction = new PUSH(constantPoolGen, number.longValue()).instruction;
    } else {
      throw new ClassGenException(("What's this: " + number));
    }
  }

  public PUSH(ConstantPoolGen constantPoolGen, boolean matches) {
    super();
    this.instruction = INSTRUCTIONS[3 + (matches ? 1 : 0)];
  }

  public PUSH(ConstantPoolGen constantPoolGen, float value) {
    super();
    if (value == 0.0) {
      this.instruction = FCONST_0;
    } else if (value == 1.0) {
      this.instruction = FCONST_1;
    } else if (value == 2.0) {
      this.instruction = FCONST_2;
    } else {
      this.instruction = new LDC(constantPoolGen.addFloat(value));
    }
  }

  public PUSH(ConstantPoolGen constantPoolGen, int value) {
    super();
    if (value >= -1 && value <= 5) {
      this.instruction = INSTRUCTIONS[3 + value];
    } else if (value >= -128 && value <= 127) {
      this.instruction = new BIPUSH((byte) value);
    } else if (value >= -32768 && value <= 32767) {
      this.instruction = new SIPUSH((short) value);
    } else {
      this.instruction = new LDC(constantPoolGen.addInteger(value));
    }
  }

  @Override
  public final InstructionList getInstructionList() {
    return new InstructionList(this.instruction);
  }

  public final Instruction getInstruction() {
    return this.instruction;
  }

  public PUSH(ConstantPoolGen constantPoolGen, long value) {
    super();
    if (value == 0L) {
      this.instruction = LCONST_0;
    } else if (value == 1L) {
      this.instruction = LCONST_1;
    } else {
      this.instruction = new LDC2_W(constantPoolGen.addLong(value));
    }
  }

  public PUSH(ConstantPoolGen constantPoolGen, Character character) {
    this(constantPoolGen, character.charValue());
  }

  public PUSH(ConstantPoolGen constantPoolGen, Boolean matches) {
    this(constantPoolGen, matches.booleanValue());
  }

  public PUSH(ConstantPoolGen constantPoolGen, double value) {
    super();
    if (value == 0.0) {
      this.instruction = DCONST_0;
    } else if (value == 1.0) {
      this.instruction = DCONST_1;
    } else {
      this.instruction = new LDC2_W(constantPoolGen.addDouble(value));
    }
  }
}
