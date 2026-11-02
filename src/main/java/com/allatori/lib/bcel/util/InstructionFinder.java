package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.generic.ClassGenException;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InstructionList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionFinder {

  public static final int OFFSET = 32767;

  public static final int NO_OPCODES = 256;

  public String il_string;

  public static final Map map = new HashMap();

  public InstructionList il;

  public InstructionHandle[] handles;

  public final void reread() {
    int value;
    char[] values = new char[value = this.il.getLength2()];
    this.handles = this.il.getInstructionHandles();
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      char value4 = makeChar(this.handles[value2].getInstruction().getOpcode());
      int value5 = value2;
      value2++;
      values[value5] = value4;
    }
    this.il_string = new String(values);
  }

  public final InstructionList getInstructionList() {
    return this.il;
  }

  public final Iterator search(String text) {
    return this.search(text, this.il.getStart(), null);
  }

  public static final String getText(String text) {
    return pattern2string(text, true);
  }

  public static final String compilePattern(String text) {
    String text2 = text.toLowerCase(Locale.ENGLISH);
    StringBuffer stringBuffer = new StringBuffer();
    int size = text.length();
    int value;
    for (int value2 = value = 0; value2 < size; value2 = ++value) {
      char value3;
      if (!Character.isLetterOrDigit(value3 = text2.charAt(value))) {
        if (!Character.isWhitespace(value3)) {
          stringBuffer.append(value3);
        }
      } else {
        StringBuffer stringBuffer2 = new StringBuffer();
        for (char value4 = value3;
            (Character.isLetterOrDigit(value4) || value3 == '_') && value < size;
            value4 = value3 = text2.charAt(value)) {
          value++;
          stringBuffer2.append(value3);
          if (value >= size) {
            break;
          }
        }
        value--;
        stringBuffer.append(createText(stringBuffer2.toString()));
      }
    }
    return stringBuffer.toString();
  }

  static {
    map.put(
        "arithmeticinstruction",
        "(irem|lrem|iand|ior|ineg|isub|lneg|fneg|fmul|ldiv|fadd|lxor|frem|idiv|land|ixor|ishr|fsub|lshl|fdiv|iadd|lor|dmul|lsub|ishl|imul|lmul|lushr|dneg|iushr|lshr|ddiv|drem|dadd|ladd|dsub)");
    map.put("invokeinstruction", "(invokevirtual|invokeinterface|invokestatic|invokespecial)");
    map.put(
        "arrayinstruction",
        "(baload|aastore|saload|caload|fastore|lastore|iaload|castore|iastore|aaload|bastore|sastore|faload|laload|daload|dastore)");
    map.put("gotoinstruction", "(goto|goto_w)");
    map.put(
        "conversioninstruction", "(d2l|l2d|i2s|d2i|l2i|i2b|l2f|d2f|f2i|i2d|i2l|f2d|i2c|f2l|i2f)");
    map.put(
        "localvariableinstruction",
        "(fstore|iinc|lload|dstore|dload|iload|aload|astore|istore|fload|lstore)");
    map.put("loadinstruction", "(fload|dload|lload|iload|aload)");
    map.put("fieldinstruction", "(getfield|putstatic|getstatic|putfield)");
    map.put(
        "cpinstruction",
        "(ldc2_w|invokeinterface|multianewarray|putstatic|instanceof|getstatic|checkcast|getfield|invokespecial|ldc_w|invokestatic|invokevirtual|putfield|ldc|new|anewarray)");
    map.put("stackinstruction", "(dup2|swap|dup2_x2|pop|pop2|dup|dup2_x1|dup_x2|dup_x1)");
    map.put(
        "branchinstruction",
        "(ifle|if_acmpne|if_icmpeq|if_acmpeq|ifnonnull|goto_w|iflt|ifnull|if_icmpne|tableswitch|if_icmple|ifeq|if_icmplt|jsr_w|if_icmpgt|ifgt|jsr|goto|ifne|ifge|lookupswitch|if_icmpge)");
    map.put("returninstruction", "(lreturn|ireturn|freturn|dreturn|areturn|return)");
    map.put("storeinstruction", "(istore|fstore|dstore|astore|lstore)");
    map.put("select", "(tableswitch|lookupswitch)");
    map.put(
        "ifinstruction",
        "(ifeq|ifgt|if_icmpne|if_icmpeq|ifge|ifnull|ifne|if_icmple|if_icmpge|if_acmpeq|if_icmplt|if_acmpne|ifnonnull|iflt|if_icmpgt|ifle)");
    map.put("jsrinstruction", "(jsr|jsr_w)");
    map.put("variablelengthinstruction", "(tableswitch|jsr|goto|lookupswitch)");
    map.put("unconditionalbranch", "(goto|jsr|jsr_w|athrow|goto_w)");
    map.put("constantpushinstruction", "(dconst|bipush|sipush|fconst|iconst|lconst)");
    map.put(
        "typedinstruction",
        "(imul|lsub|aload|fload|lor|new|aaload|fcmpg|iand|iaload|lrem|idiv|d2l|isub|dcmpg|dastore|ret|f2d|f2i|drem|iinc|i2c|checkcast|frem|lreturn|astore|lushr|daload|dneg|fastore|istore|lshl|ldiv|lstore|areturn|ishr|ldc_w|invokeinterface|aastore|lxor|ishl|l2d|i2f|return|faload|sipush|iushr|caload|instanceof|invokespecial|putfield|fmul|ireturn|laload|d2f|lneg|ixor|i2l|fdiv|lastore|multianewarray|i2b|getstatic|i2d|putstatic|fcmpl|saload|ladd|irem|dload|jsr_w|dconst|dcmpl|fsub|freturn|ldc|aconst_null|castore|lmul|ldc2_w|dadd|iconst|f2l|ddiv|dstore|land|jsr|anewarray|dmul|bipush|dsub|sastore|d2i|i2s|lshr|iadd|l2i|lload|bastore|fstore|fneg|iload|fadd|baload|fconst|ior|ineg|dreturn|l2f|lconst|getfield|invokevirtual|invokestatic|iastore)");
    map.put("popinstruction", "(fstore|dstore|pop|pop2|astore|putstatic|istore|lstore)");
    map.put("allocationinstruction", "(multianewarray|new|anewarray|newarray)");
    map.put(
        "indexedinstruction",
        "(lload|lstore|fload|ldc2_w|invokeinterface|multianewarray|astore|dload|putstatic|instanceof|getstatic|checkcast|getfield|invokespecial|dstore|istore|iinc|ldc_w|ret|fstore|invokestatic|iload|putfield|invokevirtual|ldc|new|aload|anewarray)");
    map.put(
        "pushinstruction",
        "(dup|lload|dup2|bipush|fload|ldc2_w|sipush|lconst|fconst|dload|getstatic|ldc_w|aconst_null|dconst|iload|ldc|iconst|aload)");
    map.put(
        "stackproducer",
        "(imul|lsub|aload|fload|lor|new|aaload|fcmpg|iand|iaload|lrem|idiv|d2l|isub|dcmpg|dup|f2d|f2i|drem|i2c|checkcast|frem|lushr|daload|dneg|lshl|ldiv|ishr|ldc_w|invokeinterface|lxor|ishl|l2d|i2f|faload|sipush|iushr|caload|instanceof|invokespecial|fmul|laload|d2f|lneg|ixor|i2l|fdiv|getstatic|i2b|swap|i2d|dup2|fcmpl|saload|ladd|irem|dload|jsr_w|dconst|dcmpl|fsub|ldc|arraylength|aconst_null|tableswitch|lmul|ldc2_w|iconst|dadd|f2l|ddiv|land|jsr|anewarray|dmul|bipush|dsub|d2i|newarray|i2s|lshr|iadd|lload|l2i|fneg|iload|fadd|baload|fconst|lookupswitch|ior|ineg|lconst|l2f|getfield|invokevirtual|invokestatic)");
    map.put(
        "stackconsumer",
        "(imul|lsub|lor|iflt|fcmpg|if_icmpgt|iand|ifeq|if_icmplt|lrem|ifnonnull|idiv|d2l|isub|dcmpg|dastore|if_icmpeq|f2d|f2i|drem|i2c|checkcast|frem|lreturn|astore|lushr|pop2|monitorexit|dneg|fastore|istore|lshl|ldiv|lstore|areturn|if_icmpge|ishr|monitorenter|invokeinterface|aastore|lxor|ishl|l2d|i2f|return|iushr|instanceof|invokespecial|fmul|ireturn|d2f|lneg|ixor|pop|i2l|ifnull|fdiv|lastore|i2b|if_acmpeq|ifge|swap|i2d|putstatic|fcmpl|ladd|irem|dcmpl|fsub|freturn|ifgt|castore|lmul|dadd|f2l|ddiv|dstore|land|if_icmpne|if_acmpne|dmul|dsub|sastore|ifle|d2i|i2s|lshr|iadd|l2i|bastore|fstore|fneg|fadd|ior|ineg|ifne|dreturn|l2f|if_icmple|getfield|invokevirtual|invokestatic|iastore)");
    map.put(
        "exceptionthrower",
        "(irem|lrem|laload|putstatic|baload|dastore|areturn|getstatic|ldiv|anewarray|iastore|castore|idiv|saload|lastore|fastore|putfield|lreturn|caload|getfield|return|aastore|freturn|newarray|instanceof|multianewarray|athrow|faload|iaload|aaload|dreturn|monitorenter|checkcast|bastore|arraylength|new|invokevirtual|sastore|ldc_w|ireturn|invokespecial|monitorexit|invokeinterface|ldc|invokestatic|daload)");
    map.put(
        "loadclass",
        "(multianewarray|invokeinterface|instanceof|invokespecial|putfield|checkcast|putstatic|invokevirtual|new|getstatic|invokestatic|getfield|anewarray)");
    map.put(
        "instructiontargeter",
        "(ifle|if_acmpne|if_icmpeq|if_acmpeq|ifnonnull|goto_w|iflt|ifnull|if_icmpne|tableswitch|if_icmple|ifeq|if_icmplt|jsr_w|if_icmpgt|ifgt|jsr|goto|ifne|ifge|lookupswitch|if_icmpge)");
    map.put("if_icmp", "(if_icmpne|if_icmpeq|if_icmple|if_icmpge|if_icmplt|if_icmpgt)");
    map.put("if_acmp", "(if_acmpeq|if_acmpne)");
    map.put("if", "(ifeq|ifne|iflt|ifge|ifgt|ifle)");
    map.put("iconst", precompile((short) 3, (short) 8, (short) 2));
    map.put(
        "lconst",
        new String(new char[] {'(', makeChar((short) 9), '|', makeChar((short) 10), ')'}));
    map.put(
        "dconst",
        new String(new char[] {'(', makeChar((short) 14), '|', makeChar((short) 15), ')'}));
    map.put(
        "fconst",
        new String(new char[] {'(', makeChar((short) 11), '|', makeChar((short) 12), ')'}));
    map.put("iload", precompile((short) 26, (short) 29, (short) 21));
    map.put("dload", precompile((short) 38, (short) 41, (short) 24));
    map.put("fload", precompile((short) 34, (short) 37, (short) 23));
    map.put("aload", precompile((short) 42, (short) 45, (short) 25));
    map.put("istore", precompile((short) 59, (short) 62, (short) 54));
    map.put("dstore", precompile((short) 71, (short) 74, (short) 57));
    map.put("fstore", precompile((short) 67, (short) 70, (short) 56));
    map.put("astore", precompile((short) 75, (short) 78, (short) 58));
    for (String text : ((Iterable<String>) ((Iterable<?>) map.keySet()))) {
      String text2;
      if ((text2 = (String) map.get(text)).charAt(1) < 32767) {
        map.put(text, compilePattern(text2));
      }
    }
    StringBuffer stringBuffer = new StringBuffer("(");
    short value;
    for (short value2 = value = 0; value2 < 256; value2 = ++value) {
      if (Constants.NO_OF_OPERANDS[value] != -1) {
        stringBuffer.append(makeChar(value));
        if (value < 255) {
          stringBuffer.append('|');
        }
      }
    }
    stringBuffer.append(')');
    map.put("instruction", stringBuffer.toString());
  }

  public InstructionFinder(InstructionList instructionList) {
    super();
    this.il = instructionList;
    this.reread();
  }

  public final Iterator search(
      String text, InstructionFinder$CodeConstraint instructionFinder$CodeConstraint) {
    return this.search(text, this.il.getStart(), instructionFinder$CodeConstraint);
  }

  public final Iterator search(
      String text,
      InstructionHandle instructionHandle,
      InstructionFinder$CodeConstraint instructionFinder$CodeConstraint) {
    String text2 = compilePattern(text);
    int value = -1;
    int value2;
    int value3 = value2 = 0;
    while (true) {
      if (value3 >= this.handles.length) {
        value3 = value;
        break;
      }
      if (this.handles[value2] == instructionHandle) {
        value3 = value = value2;
        break;
      }
      value3 = ++value2;
    }
    if (value3 == -1) {
      throw new ClassGenException(
          ("Instruction handle " + instructionHandle + " not found in instruction list."));
    }
    Pattern pattern = Pattern.compile(text2);
    ArrayList values = new ArrayList();
    Matcher matcher2 = pattern.matcher(this.il_string);
    value3 = value;
    while (value3 < this.il_string.length() && matcher2.find(value)) {
      int value4 = matcher2.start();
      int value5;
      int value6 = (value5 = matcher2.end()) - value4 + 1;
      InstructionHandle[] match = this.getMatch(value4, value6);
      if (instructionFinder$CodeConstraint == null
          || instructionFinder$CodeConstraint.checkCode(match)) {
        values.add(match);
      }
      value3 = value = value5;
    }
    return values.iterator();
  }

  public static final String createText(String text) {
    String text2;
    if ((text2 = (String) map.get(text)) != null) {
      return text2;
    }
    int value;
    for (int value2 = value = 0; value2 < 256; value2 = ++value) {
      if (text.equals(Constants.TEXTS8[value])) {
        return "" + makeChar((short) value);
      }
    }
    throw new RuntimeException(("Instruction unknown: " + text));
  }

  public static final char makeChar(short value) {
    return (char) (value + 32767);
  }

  public static String precompile(short value, short value2, short value3) {
    StringBuffer stringBuffer = new StringBuffer("(");
    int value4;
    for (int value5 = value4 = value; value5 <= value2; value5 = ++value4) {
      stringBuffer.append(makeChar((short) value4));
      stringBuffer.append('|');
    }
    stringBuffer.append(makeChar(value3));
    stringBuffer.append(")");
    return stringBuffer.toString();
  }

  public final Iterator search(String text, InstructionHandle instructionHandle) {
    return this.search(text, instructionHandle, null);
  }

  public static final String pattern2string(String text, boolean matches) {
    StringBuffer stringBuffer = new StringBuffer();
    int value;
    for (int value2 = value = 0; value2 < text.length(); value2 = ++value) {
      char value3;
      if ((value3 = text.charAt(value)) >= 32767) {
        if (matches) {
          stringBuffer.append(Constants.TEXTS8[value3 - 32767]);
        } else {
          stringBuffer.append(value3 - 32767);
        }
      } else {
        stringBuffer.append(value3);
      }
    }
    return stringBuffer.toString();
  }

  public InstructionHandle[] getMatch(int value, int value2) {
    InstructionHandle[] instructionHandles = new InstructionHandle[value2];
    System.arraycopy(this.handles, value, instructionHandles, 0, value2);
    return instructionHandles;
  }
}
