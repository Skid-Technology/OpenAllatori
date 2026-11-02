package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.Utility;
import com.allatori.lib.bcel.generic.AllocationInstruction;
import com.allatori.lib.bcel.generic.ArrayInstruction;
import com.allatori.lib.bcel.generic.ArrayType;
import com.allatori.lib.bcel.generic.BranchHandle;
import com.allatori.lib.bcel.generic.BranchInstruction;
import com.allatori.lib.bcel.generic.CHECKCAST;
import com.allatori.lib.bcel.generic.CPInstruction;
import com.allatori.lib.bcel.generic.CodeExceptionGen;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.ConstantPushInstruction;
import com.allatori.lib.bcel.generic.EmptyVisitor;
import com.allatori.lib.bcel.generic.FieldInstruction;
import com.allatori.lib.bcel.generic.IINC;
import com.allatori.lib.bcel.generic.INSTANCEOF;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionConstants;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InvokeInstruction;
import com.allatori.lib.bcel.generic.LDC;
import com.allatori.lib.bcel.generic.LDC2_W;
import com.allatori.lib.bcel.generic.LocalVariableInstruction;
import com.allatori.lib.bcel.generic.MULTIANEWARRAY;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.NEWARRAY;
import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.RET;
import com.allatori.lib.bcel.generic.ReturnInstruction;
import com.allatori.lib.bcel.generic.Select;
import com.allatori.lib.bcel.generic.Type;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BCELFactory extends EmptyVisitor {

  public MethodGen _mg;

  public List branches;

  public Map branch_map;

  public ConstantPoolGen _cp;

  public PrintWriter _out;

  public BCELFactory(MethodGen methodGen, PrintWriter printWriter) {
    super();
    this.branch_map = new HashMap();
    this.branches = new ArrayList();
    this._mg = methodGen;
    this._cp = methodGen.getConstantPool();
    this._out = printWriter;
  }

  @Override
  public void visitAllocationInstruction(AllocationInstruction allocationInstruction) {
    Type type;
    AllocationInstruction allocationInstruction2;
    if (allocationInstruction instanceof CPInstruction) {
      type = ((CPInstruction) allocationInstruction).getType(this._cp);
      allocationInstruction2 = allocationInstruction;
    } else {
      type = ((NEWARRAY) allocationInstruction).getType();
      allocationInstruction2 = allocationInstruction;
    }
    short opcode = ((Instruction) allocationInstruction2).getOpcode();
    short value = 1;
    switch (opcode) {
      case 187:
        this._out.println(
            "il.append(_factory.createNew(\"" + ((ObjectType) type).getClassName() + "\"));");
        return;
      case 197:
        value = ((MULTIANEWARRAY) allocationInstruction).getDimensions();
      case 188:
      case 189:
        if (type instanceof ArrayType) {
          type = ((ArrayType) type).getBasicType();
        }
        this._out.println(
            "il.append(_factory.createNewArray("
                + BCELifier.printType(type)
                + ", (short) "
                + value
                + "));");
        return;
      default:
        throw new RuntimeException(("Oops: " + opcode));
    }
  }

  @Override
  public void visitFieldInstruction(FieldInstruction fieldInstruction) {
    short opcode = fieldInstruction.getOpcode();
    String text2 = fieldInstruction.getText2(this._cp);
    String fieldName = fieldInstruction.getFieldName(this._cp);
    Type type2 = fieldInstruction.getType2(this._cp);
    this._out.println(
        "il.append(_factory.createFieldAccess(\""
            + text2
            + "\", \""
            + fieldName
            + "\", "
            + BCELifier.printType(type2)
            + ", "
            + "Constants."
            + Constants.TEXTS8[opcode].toUpperCase(Locale.ENGLISH)
            + "));");
  }

  @Override
  public void visitConstantPushInstruction(ConstantPushInstruction constantPushInstruction) {
    this.createConstant(constantPushInstruction.getValue());
  }

  @Override
  public void visitReturnInstruction(ReturnInstruction returnInstruction) {
    Type type = returnInstruction.getType(this._cp);
    this._out.println("il.append(_factory.createReturn(" + BCELifier.printType(type) + "));");
  }

  @Override
  public void visitLDC(LDC lDC) {
    this.createConstant(lDC.getValue(this._cp));
  }

  public boolean visitInstruction(Instruction instruction) {
    short opcode = instruction.getOpcode();
    if (InstructionConstants.INSTRUCTIONS[opcode] != null
        && !(instruction instanceof ConstantPushInstruction)
        && !(instruction instanceof ReturnInstruction)) {
      this._out.println(
          "il.append(InstructionConstants."
              + instruction.getText().toUpperCase(Locale.ENGLISH)
              + ");");
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void visitInvokeInstruction(InvokeInstruction invokeInstruction) {
    short opcode = invokeInstruction.getOpcode();
    String text2 = invokeInstruction.getText2(this._cp);
    String text4 = invokeInstruction.getText4(this._cp);
    Type type2 = invokeInstruction.getType2(this._cp);
    Type[] argumentTypes = invokeInstruction.getArgumentTypes(this._cp);
    this._out.println(
        "il.append(_factory.createInvoke(\""
            + text2
            + "\", \""
            + text4
            + "\", "
            + BCELifier.printType(type2)
            + ", "
            + BCELifier.printArgumentTypes(argumentTypes)
            + ", "
            + "Constants."
            + Constants.TEXTS8[opcode].toUpperCase(Locale.ENGLISH)
            + "));");
  }

  @Override
  public void visitLocalVariableInstruction(LocalVariableInstruction localVariableInstruction) {
    short opcode = localVariableInstruction.getOpcode();
    Type type = localVariableInstruction.getType(this._cp);
    if (opcode == 132) {
      this._out.println(
          "il.append(new IINC("
              + localVariableInstruction.getIndex()
              + ", "
              + ((IINC) localVariableInstruction).getIncrement()
              + "));");
    } else {
      String text = opcode < 54 ? "Load" : "Store";
      this._out.println(
          "il.append(_factory.create"
              + text
              + "("
              + BCELifier.printType(type)
              + ", "
              + localVariableInstruction.getIndex()
              + "));");
    }
  }

  @Override
  public void visitCHECKCAST(CHECKCAST cHECKCAST) {
    Type type = cHECKCAST.getType(this._cp);
    this._out.println("il.append(_factory.createCheckCast(" + BCELifier.printType(type) + "));");
  }

  @Override
  public void visitLDC2_W(LDC2_W lDC2_W) {
    this.createConstant(lDC2_W.getValue(this._cp));
  }

  public void createConstant(Object result) {
    String text = result.toString();
    BCELFactory bCELFactory;
    if (result instanceof String) {
      text = '"' + Utility.createText(result.toString()) + '"';
      bCELFactory = this;
    } else {
      if (result instanceof Character) {
        text = "(char)0x" + Integer.toHexString((Character) result);
      }
      bCELFactory = this;
    }
    bCELFactory._out.println("il.append(new PUSH(_cp, " + text + "));");
  }

  public void start() {
    if (!this._mg.isEnabled15() && !this._mg.isEnabled14()) {
      InstructionHandle instructionHandle;
      for (InstructionHandle instructionHandle2 =
              instructionHandle = this._mg.getInstructionList().getStart();
          instructionHandle2 != null;
          instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2()) {
        Instruction instruction;
        if ((instruction = instructionHandle.getInstruction()) instanceof BranchInstruction) {
          this.branch_map.put(instruction, instructionHandle);
        }
        BCELFactory bCELFactory;
        if (instructionHandle.hasTargeters()) {
          if (instruction instanceof BranchInstruction) {
            bCELFactory = this;
            this._out.println("    InstructionHandle ih_" + instructionHandle.getPosition() + ";");
          } else {
            bCELFactory = this;
            this._out.print("    InstructionHandle ih_" + instructionHandle.getPosition() + " = ");
          }
        } else {
          bCELFactory = this;
          this._out.print("    ");
        }
        if (!bCELFactory.visitInstruction(instruction)) {
          instruction.accept(this);
        }
      }
      this.updateBranchTargets();
      this.updateExceptionHandlers();
    }
  }

  public void updateBranchTargets() {
    for (BranchInstruction branchInstruction :
        ((Iterable<BranchInstruction>) ((Iterable<?>) this.branches))) {
      BranchHandle branchHandle = (BranchHandle) this.branch_map.get(branchInstruction);
      int position = branchHandle.getPosition();
      String text = "" + branchInstruction.getText() + "_" + position;
      int position2 = branchHandle.getTarget().getPosition();
      this._out.println("    " + text + ".setTarget(ih_" + position2 + ");");
      if (branchInstruction instanceof Select) {
        InstructionHandle[] targets = ((Select) branchInstruction).getTargets();
        int value;
        for (int value2 = value = 0; value2 < targets.length; value2 = value) {
          int position3 = targets[value].getPosition();
          PrintWriter printWriter = this._out;
          StringBuilder stringBuilder =
              new StringBuilder()
                  .insert(0, "    ")
                  .append(text)
                  .append(".setTarget(")
                  .append(value)
                  .append(", ih_")
                  .append(position3);
          value++;
          printWriter.println(stringBuilder.append((String) ");").toString());
        }
      }
    }
  }

  @Override
  public void visitRET(RET rET) {
    this._out.println("il.append(new RET(" + rET.getIndex() + ")));");
  }

  public void updateExceptionHandlers() {
    CodeExceptionGen[] exceptionHandlers = this._mg.getExceptionHandlers();
    int value;
    for (int value2 = value = 0; value2 < exceptionHandlers.length; value2 = value) {
      CodeExceptionGen codeExceptionGen;
      String text =
          (codeExceptionGen = exceptionHandlers[value]).getCatchType() == null
              ? "null"
              : BCELifier.printType(codeExceptionGen.getCatchType());
      PrintWriter printWriter = this._out;
      StringBuilder stringBuilder =
          new StringBuilder()
              .insert(0, "    method.addExceptionHandler(ih_")
              .append(codeExceptionGen.getInstructionHandle2().getPosition())
              .append(", ")
              .append("ih_")
              .append(codeExceptionGen.getInstructionHandle().getPosition())
              .append(", ")
              .append("ih_")
              .append(codeExceptionGen.getInstructionHandle3().getPosition())
              .append(", ")
              .append(text);
      value++;
      printWriter.println(stringBuilder.append((String) ");").toString());
    }
  }

  @Override
  public void visitINSTANCEOF(INSTANCEOF nSTANCEOF) {
    Type type = nSTANCEOF.getType(this._cp);
    this._out.println(
        "il.append(new INSTANCEOF(_cp.addClass(" + BCELifier.printType(type) + ")));");
  }

  @Override
  public void visitArrayInstruction(ArrayInstruction arrayInstruction) {
    short opcode = arrayInstruction.getOpcode();
    Type type = arrayInstruction.getType(this._cp);
    String text = opcode < 79 ? "Load" : "Store";
    this._out.println(
        "il.append(_factory.createArray" + text + "(" + BCELifier.printType(type) + "));");
  }

  @Override
  public void visitBranchInstruction(BranchInstruction branchInstruction) {
    BranchHandle branchHandle;
    int position =
        (branchHandle = (BranchHandle) this.branch_map.get(branchInstruction)).getPosition();
    String text = "" + branchInstruction.getText() + "_" + position;
    BranchHandle branchHandle2;
    if (branchInstruction instanceof Select) {
      Select select = (Select) branchInstruction;
      this.branches.add(branchInstruction);
      StringBuffer stringBuffer = new StringBuffer("new int[] { ");
      int[] match = select.getMatch();
      int value = 0;
      for (int value2 = value; value2 < match.length; value2 = ++value) {
        stringBuffer.append(match[value]);
        if (value < match.length - 1) {
          stringBuffer.append(", ");
        }
      }
      stringBuffer.append(" }");
      this._out.print(
          "Select "
              + text
              + " = new "
              + branchInstruction.getText().toUpperCase(Locale.ENGLISH)
              + "("
              + ((Object) stringBuffer)
              + ", new InstructionHandle[] { ");
      int value3 = 0;
      for (int value4 = value3; value4 < match.length; value4 = ++value3) {
        this._out.print("null");
        if (value3 < match.length - 1) {
          this._out.print(", ");
        }
      }
      this._out.println(" }, null);");
      branchHandle2 = branchHandle;
    } else {
      int position2 = branchHandle.getTarget().getPosition();
      String text2;
      BCELFactory bCELFactory;
      if (position > position2) {
        text2 = "ih_" + position2;
        bCELFactory = this;
      } else {
        bCELFactory = this;
        this.branches.add(branchInstruction);
        text2 = "null";
      }
      bCELFactory._out.println(
          "    BranchInstruction "
              + text
              + " = _factory.createBranchInstruction("
              + "Constants."
              + branchInstruction.getText().toUpperCase(Locale.ENGLISH)
              + ", "
              + text2
              + ");");
      branchHandle2 = branchHandle;
    }
    if (branchHandle2.hasTargeters()) {
      this._out.println("    ih_" + position + " = il.append(" + text + ");");
    } else {
      this._out.println("    il.append(" + text + ");");
    }
  }
}
