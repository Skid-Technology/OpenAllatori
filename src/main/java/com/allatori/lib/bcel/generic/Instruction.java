package com.allatori.lib.bcel.generic;

import com.allatori.internal.NameSignatureInstructionSupport;
import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

public abstract class Instruction implements Cloneable, Serializable {

  public static InstructionComparator cmp = InstructionComparator.DEFAULT;

  public short length = 1;

  public short opcode = -1;

  public short getOpcode() {
    return this.opcode;
  }

  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {}

  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeByte(this.opcode);
  }

  public int produceStack(ConstantPoolGen constantPoolGen) {
    return Constants.COUNTS[this.opcode];
  }

  public void setOpcode(short opcode) {
    this.opcode = opcode;
  }

  public String toString(ConstantPool constantPool) {
    return this.toString(false);
  }

  public String getText() {
    return Constants.TEXTS8[this.opcode];
  }

  public Instruction copy() {
    if (InstructionConstants.INSTRUCTIONS[this.getOpcode()] != null) {
      return this;
    }
    try {
      return (Instruction) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      System.err.println(cloneNotSupportedException);
      return (Instruction) (Object) null;
    }
  }

  public int getLength() {
    return this.length;
  }

  public Instruction() {}

  public static final String className(short value) {
    String text = Constants.TEXTS8[value].toUpperCase(Locale.ENGLISH);
    try {
      int value2;
      char value3 = text.charAt((value2 = text.length()) - 2);
      char value4 = text.charAt(value2 - 1);
      if (value3 == '_' && value4 >= '0' && value4 <= '5') {
        text = text.substring(0, value2 - 2);
      }
      if (text.equals("ICONST_M1")) {
        text = "ICONST";
      }
    } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      System.err.println(stringIndexOutOfBoundsException);
    }
    return "org.apache.bcel.generic." + text;
  }

  public static InstructionComparator getComparator() {
    return cmp;
  }

  public String toString(boolean matches) {
    return matches
        ? ("" + this.getText() + "[" + this.opcode + "](" + this.length + ")")
        : this.getText();
  }

  @Override
  public boolean equals(Object result) {
    return result instanceof Instruction ? cmp.equals(this, (Instruction) result) : false;
  }

  public abstract void accept(Visitor visitor);

  public void dispose() {}

  public int consumeStack(ConstantPoolGen constantPoolGen) {
    return Constants.COUNTS2[this.opcode];
  }

  public static final Instruction readInstruction(ByteSequence byteSequence) throws IOException {
    byte value = 0;
    short value2 = (short) byteSequence.readUnsignedByte();
    if (value2 == 196) {
      value = 1;
      value2 = (short) byteSequence.readUnsignedByte();
    }
    if (InstructionConstants.INSTRUCTIONS[value2] != null) {
      return InstructionConstants.INSTRUCTIONS[value2];
    }
    Instruction instruction;
    try {
      switch (value2) {
        case 0:
          instruction = new NOP();
          break;
        case 1:
          instruction = new ACONST_NULL();
          break;
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
          instruction = new ICONST();
          break;
        case 9:
        case 10:
          instruction = new LCONST();
          break;
        case 11:
        case 12:
        case 13:
          instruction = new FCONST();
          break;
        case 14:
        case 15:
          instruction = new DCONST();
          break;
        case 16:
          instruction = new BIPUSH();
          break;
        case 17:
          instruction = new SIPUSH();
          break;
        case 18:
          instruction = new LDC();
          break;
        case 19:
          instruction = new LDC_W();
          break;
        case 20:
          instruction = new LDC2_W();
          break;
        case 21:
          instruction = new ILOAD();
          break;
        case 22:
          instruction = new LLOAD();
          break;
        case 23:
          instruction = new FLOAD();
          break;
        case 24:
          instruction = new DLOAD();
          break;
        case 25:
          instruction = new ALOAD();
          break;
        case 26:
        case 27:
        case 28:
        case 29:
          instruction = new ILOAD();
          break;
        case 30:
        case 31:
        case 32:
        case 33:
          instruction = new LLOAD();
          break;
        case 34:
        case 35:
        case 36:
        case 37:
          instruction = new FLOAD();
          break;
        case 38:
        case 39:
        case 40:
        case 41:
          instruction = new DLOAD();
          break;
        case 42:
        case 43:
        case 44:
        case 45:
          instruction = new ALOAD();
          break;
        case 46:
          instruction = new IALOAD();
          break;
        case 47:
          instruction = new LALOAD();
          break;
        case 48:
          instruction = new FALOAD();
          break;
        case 49:
          instruction = new DALOAD();
          break;
        case 50:
          instruction = new AALOAD();
          break;
        case 51:
          instruction = new BALOAD();
          break;
        case 52:
          instruction = new CALOAD();
          break;
        case 53:
          instruction = new SALOAD();
          break;
        case 54:
          instruction = new ISTORE();
          break;
        case 55:
          instruction = new LSTORE();
          break;
        case 56:
          instruction = new FSTORE();
          break;
        case 57:
          instruction = new DSTORE();
          break;
        case 58:
          instruction = new ASTORE();
          break;
        case 59:
        case 60:
        case 61:
        case 62:
          instruction = new ISTORE();
          break;
        case 63:
        case 64:
        case 65:
        case 66:
          instruction = new LSTORE();
          break;
        case 67:
        case 68:
        case 69:
        case 70:
          instruction = new FSTORE();
          break;
        case 71:
        case 72:
        case 73:
        case 74:
          instruction = new DSTORE();
          break;
        case 75:
        case 76:
        case 77:
        case 78:
          instruction = new ASTORE();
          break;
        case 79:
          instruction = new IASTORE();
          break;
        case 80:
          instruction = new LASTORE();
          break;
        case 81:
          instruction = new FASTORE();
          break;
        case 82:
          instruction = new DASTORE();
          break;
        case 83:
          instruction = new AASTORE();
          break;
        case 84:
          instruction = new BASTORE();
          break;
        case 85:
          instruction = new CASTORE();
          break;
        case 86:
          instruction = new SASTORE();
          break;
        case 87:
          instruction = new POP();
          break;
        case 88:
          instruction = new POP2();
          break;
        case 89:
          instruction = new DUP();
          break;
        case 90:
          instruction = new DUP_X1();
          break;
        case 91:
          instruction = new DUP_X2();
          break;
        case 92:
          instruction = new DUP2();
          break;
        case 93:
          instruction = new DUP2_X1();
          break;
        case 94:
          instruction = new DUP2_X2();
          break;
        case 95:
          instruction = new SWAP();
          break;
        case 96:
          instruction = new IADD();
          break;
        case 97:
          instruction = new LADD();
          break;
        case 98:
          instruction = new FADD();
          break;
        case 99:
          instruction = new DADD();
          break;
        case 100:
          instruction = new ISUB();
          break;
        case 101:
          instruction = new LSUB();
          break;
        case 102:
          instruction = new FSUB();
          break;
        case 103:
          instruction = new DSUB();
          break;
        case 104:
          instruction = new IMUL();
          break;
        case 105:
          instruction = new LMUL();
          break;
        case 106:
          instruction = new FMUL();
          break;
        case 107:
          instruction = new DMUL();
          break;
        case 108:
          instruction = new IDIV();
          break;
        case 109:
          instruction = new LDIV();
          break;
        case 110:
          instruction = new FDIV();
          break;
        case 111:
          instruction = new DDIV();
          break;
        case 112:
          instruction = new IREM();
          break;
        case 113:
          instruction = new LREM();
          break;
        case 114:
          instruction = new FREM();
          break;
        case 115:
          instruction = new DREM();
          break;
        case 116:
          instruction = new INEG();
          break;
        case 117:
          instruction = new LNEG();
          break;
        case 118:
          instruction = new FNEG();
          break;
        case 119:
          instruction = new DNEG();
          break;
        case 120:
          instruction = new ISHL();
          break;
        case 121:
          instruction = new LSHL();
          break;
        case 122:
          instruction = new ISHR();
          break;
        case 123:
          instruction = new LSHR();
          break;
        case 124:
          instruction = new IUSHR();
          break;
        case 125:
          instruction = new LUSHR();
          break;
        case 126:
          instruction = new IAND();
          break;
        case 127:
          instruction = new LAND();
          break;
        case 128:
          instruction = new IOR();
          break;
        case 129:
          instruction = new LOR();
          break;
        case 130:
          instruction = new IXOR();
          break;
        case 131:
          instruction = new LXOR();
          break;
        case 132:
          instruction = new IINC();
          break;
        case 133:
          instruction = new I2L();
          break;
        case 134:
          instruction = new I2F();
          break;
        case 135:
          instruction = new I2D();
          break;
        case 136:
          instruction = new L2I();
          break;
        case 137:
          instruction = new L2F();
          break;
        case 138:
          instruction = new L2D();
          break;
        case 139:
          instruction = new F2I();
          break;
        case 140:
          instruction = new F2L();
          break;
        case 141:
          instruction = new F2D();
          break;
        case 142:
          instruction = new D2I();
          break;
        case 143:
          instruction = new D2L();
          break;
        case 144:
          instruction = new D2F();
          break;
        case 145:
          instruction = new I2B();
          break;
        case 146:
          instruction = new I2C();
          break;
        case 147:
          instruction = new I2S();
          break;
        case 148:
          instruction = new LCMP();
          break;
        case 149:
          instruction = new FCMPL();
          break;
        case 150:
          instruction = new FCMPG();
          break;
        case 151:
          instruction = new DCMPL();
          break;
        case 152:
          instruction = new DCMPG();
          break;
        case 153:
          instruction = new IFEQ();
          break;
        case 154:
          instruction = new IFNE();
          break;
        case 155:
          instruction = new IFLT();
          break;
        case 156:
          instruction = new IFGE();
          break;
        case 157:
          instruction = new IFGT();
          break;
        case 158:
          instruction = new IFLE();
          break;
        case 159:
          instruction = new IF_ICMPEQ();
          break;
        case 160:
          instruction = new IF_ICMPNE();
          break;
        case 161:
          instruction = new IF_ICMPLT();
          break;
        case 162:
          instruction = new IF_ICMPGE();
          break;
        case 163:
          instruction = new IF_ICMPGT();
          break;
        case 164:
          instruction = new IF_ICMPLE();
          break;
        case 165:
          instruction = new IF_ACMPEQ();
          break;
        case 166:
          instruction = new IF_ACMPNE();
          break;
        case 167:
          instruction = new GOTO();
          break;
        case 168:
          instruction = new JSR();
          break;
        case 169:
          instruction = new RET();
          break;
        case 170:
          instruction = new TABLESWITCH();
          break;
        case 171:
          instruction = new LOOKUPSWITCH();
          break;
        case 172:
          instruction = new IRETURN();
          break;
        case 173:
          instruction = new LRETURN();
          break;
        case 174:
          instruction = new FRETURN();
          break;
        case 175:
          instruction = new DRETURN();
          break;
        case 176:
          instruction = new ARETURN();
          break;
        case 177:
          instruction = new RETURN();
          break;
        case 178:
          instruction = new GETSTATIC();
          break;
        case 179:
          instruction = new PUTSTATIC();
          break;
        case 180:
          instruction = new GETFIELD();
          break;
        case 181:
          instruction = new PUTFIELD();
          break;
        case 182:
          instruction = new INVOKEVIRTUAL();
          break;
        case 183:
          instruction = new INVOKESPECIAL();
          break;
        case 184:
          instruction = new INVOKESTATIC();
          break;
        case 185:
          instruction = new INVOKEINTERFACE();
          break;
        case 186:
          instruction = new NameSignatureInstructionSupport();
          break;
        case 187:
          instruction = new NEW();
          break;
        case 188:
          instruction = new NEWARRAY();
          break;
        case 189:
          instruction = new ANEWARRAY();
          break;
        case 190:
          instruction = new ARRAYLENGTH();
          break;
        case 191:
          instruction = new ATHROW();
          break;
        case 192:
          instruction = new CHECKCAST();
          break;
        case 193:
          instruction = new INSTANCEOF();
          break;
        case 194:
          instruction = new MONITORENTER();
          break;
        case 195:
          instruction = new MONITOREXIT();
          break;
        case 196:
        default:
          throw new ClassGenException("Illegal opcode detected");
        case 197:
          instruction = new MULTIANEWARRAY();
          break;
        case 198:
          instruction = new IFNULL();
          break;
        case 199:
          instruction = new IFNONNULL();
          break;
        case 200:
          instruction = new GOTO_W();
          break;
        case 201:
          instruction = new JSR_W();
      }
    } catch (ClassGenException classGenException) {
      throw classGenException;
    } catch (Exception exception) {
      throw new ClassGenException(exception.toString());
    }
    if (value != 0
        && !(instruction instanceof LocalVariableInstruction)
        && !(instruction instanceof IINC)
        && !(instruction instanceof RET)) {
      throw new ClassGenException(("Illegal opcode after wide: " + value2));
    }
    instruction.setOpcode(value2);
    instruction.initFromFile(byteSequence, value != 0);
    return instruction;
  }

  public Instruction(short opcode, short length) {
    super();
    this.length = 1;
    this.opcode = -1;
    this.length = length;
    this.opcode = opcode;
  }

  public static void setComparator(InstructionComparator instructionComparator) {
    cmp = instructionComparator;
  }
}
