package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.Code;
import com.allatori.lib.bcel.classfile.CodeException;
import com.allatori.lib.bcel.classfile.LineNumber;
import com.allatori.lib.bcel.classfile.LineNumberTable;
import com.allatori.lib.bcel.classfile.LocalVariable;
import com.allatori.lib.bcel.classfile.LocalVariableTable;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ASTORE;
import com.allatori.lib.bcel.generic.ATHROW;
import com.allatori.lib.bcel.generic.BREAKPOINT;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.GotoInstruction;
import com.allatori.lib.bcel.generic.IMPDEP1;
import com.allatori.lib.bcel.generic.IMPDEP2;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InstructionList;
import com.allatori.lib.bcel.generic.JsrInstruction;
import com.allatori.lib.bcel.generic.RET;
import com.allatori.lib.bcel.generic.ReturnInstruction;
import com.allatori.lib.bcel.verifier.PassVerifier;
import com.allatori.lib.bcel.verifier.VerificationResult;
import com.allatori.lib.bcel.verifier.Verifier;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.InvalidMethodException;
import com.allatori.lib.bcel.verifier.exc.LoadingException;
import com.allatori.lib.bcel.verifier.exc.StaticCodeInstructionConstraintException;
import com.allatori.lib.bcel.verifier.exc.StaticCodeInstructionOperandConstraintException;

public final class Pass3aVerifier extends PassVerifier {

  public int method_no;

  public InstructionList instructionList;

  public Verifier myOwner;

  public Code code;

  public void pass3StaticInstructionChecks() {
    if (this.code.getCode().length >= 65536) {
      throw new StaticCodeInstructionOperandConstraintException(
          ("Code array in code attribute '"
              + this.code
              + "' too big: must be smaller than 65536 bytes."));
    }
    InstructionHandle instructionHandle;
    for (InstructionHandle instructionHandle2 = instructionHandle = this.instructionList.getStart();
        instructionHandle2 != null;
        instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2()) {
      Instruction instruction;
      if ((instruction = instructionHandle.getInstruction()) instanceof IMPDEP1) {
        throw new StaticCodeInstructionOperandConstraintException(
            "IMPDEP1 must not be in the code, it is an illegal instruction for _internal_ JVM"
                + " use!");
      }
      if (instruction instanceof IMPDEP2) {
        throw new StaticCodeInstructionOperandConstraintException(
            "IMPDEP2 must not be in the code, it is an illegal instruction for _internal_ JVM"
                + " use!");
      }
      if (instruction instanceof BREAKPOINT) {
        throw new StaticCodeInstructionOperandConstraintException(
            "BREAKPOINT must not be in the code, it is an illegal instruction for _internal_ JVM"
                + " use!");
      }
    }
    Instruction instruction2;
    if (!((instruction2 = this.instructionList.getEnd().getInstruction())
            instanceof ReturnInstruction)
        && !(instruction2 instanceof RET)
        && !(instruction2 instanceof GotoInstruction)
        && !(instruction2 instanceof ATHROW)) {
      throw new StaticCodeInstructionOperandConstraintException(
          "Execution must not fall off the bottom of the code array. This constraint is enforced"
              + " statically as some existing verifiers do - so it may be a false alarm if the last"
              + " instruction is not reachable.");
    }
  }

  @Override
  public VerificationResult getVerificationResult() {
    Pass3aVerifier pass3aVerifier;
    try {
      if (!this.myOwner
          .createVerificationResult2()
          .equals(VerificationResult.VERIFICATION_RESULT2)) {
        return VerificationResult.VERIFICATION_RESULT;
      }
      Method[] methods = Repository.lookupClass(this.myOwner.getClassName()).getMethods();
      if (this.method_no >= methods.length) {
        throw new InvalidMethodException("METHOD DOES NOT EXIST!");
      }
      Method method = methods[this.method_no];
      this.code = method.getCode();
      if (method.isEnabled15() || method.isEnabled14()) {
        return VerificationResult.VERIFICATION_RESULT2;
      }
      try {
        this.instructionList = new InstructionList(method.getCode().getCode());
      } catch (RuntimeException runtimeException) {
        return new VerificationResult(
            2, "Bad bytecode in the code array of the Code attribute of method '" + method + "'.");
      }
      pass3aVerifier = this;
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
    pass3aVerifier.instructionList.setPositions(true);
    VerificationResult verificationResult = VerificationResult.VERIFICATION_RESULT2;
    try {
      this.delayedPass2Checks();
    } catch (LoadingException loadingException) {
      return new VerificationResult(2, loadingException.getMessage());
    }
    this.pass3StaticInstructionChecks();
    this.pass3StaticInstructionOperandsChecks();
    return verificationResult;
  }

  public int getMethodNo() {
    return this.method_no;
  }

  public void pass3StaticInstructionOperandsChecks() {
    try {
      ConstantPoolGen constantPoolGen =
          new ConstantPoolGen(
              Repository.lookupClass(this.myOwner.getClassName()).getConstantPool());
      Pass3aVerifier$InstOperandConstraintVisitor pass3aVerifier$InstOperandConstraintVisitor =
          new Pass3aVerifier$InstOperandConstraintVisitor(this, constantPoolGen);
      for (InstructionHandle start = this.instructionList.getStart();
          start != null;
          start = start.getInstructionHandle2()) {
        Instruction instruction;
        if ((instruction = start.getInstruction()) instanceof JsrInstruction) {
          InstructionHandle instructionHandle;
          if ((instructionHandle = ((JsrInstruction) instruction).getTarget())
              == this.instructionList.getStart()) {
            throw new StaticCodeInstructionConstraintException(
                ("Due to JustIce's clear definition of subroutines, no JSR or JSR_W may have a"
                     + " top-level instruction (such as the very first instruction, which is"
                     + " targeted by instruction '"
                    + start
                    + "' as its target."));
          }
          if (!(instructionHandle.getInstruction() instanceof ASTORE)) {
            throw new StaticCodeInstructionConstraintException(
                ("Due to JustIce's clear definition of subroutines, no JSR or JSR_W may target"
                     + " anything else than an ASTORE instruction. Instruction '"
                    + start
                    + "' targets '"
                    + instructionHandle
                    + "'."));
          }
        }
        start.accept(pass3aVerifier$InstOperandConstraintVisitor);
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  public static boolean contains(int[] values, int value) {
    int value2;
    for (int value3 = value2 = 0; value3 < values.length; value3 = ++value2) {
      if (values[value2] == value) {
        return true;
      }
    }
    return false;
  }

  public Pass3aVerifier(Verifier verifier, int method_no) {
    super();
    this.myOwner = verifier;
    this.method_no = method_no;
  }

  public static Verifier access$000(Pass3aVerifier pass3aVerifier) {
    return pass3aVerifier.myOwner;
  }

  public void delayedPass2Checks() {
    int[] instructionPositions = this.instructionList.getInstructionPositions();
    int value = this.code.getCode().length;
    LineNumberTable lineNumberTable;
    if ((lineNumberTable = this.code.getLineNumberTable()) != null) {
      LineNumber[] lineNumberTable2 = lineNumberTable.getLineNumberTable();
      IntList intList = new IntList();
      int value2;
      label87:
      for (int value3 = value2 = 0; value3 < lineNumberTable2.length; value3 = ++value2) {
        int value4;
        for (int value5 = value4 = 0; value5 < instructionPositions.length; value5 = ++value4) {
          int start_pc = lineNumberTable2[value2].getStart_pc();
          if (instructionPositions[value4] == start_pc) {
            if (intList.contains(start_pc)) {
              this.addMessage(
                  "LineNumberTable attribute '"
                      + this.code.getLineNumberTable()
                      + "' refers to the same code offset ('"
                      + start_pc
                      + "') more than once which is violating the semantics [but is sometimes"
                      + " produced by IBM's 'jikes' compiler].");
            } else {
              intList.add(start_pc);
            }
            continue label87;
          }
        }
        throw new LoadingException(
            ("Code attribute '"
                + this.code
                + "' has a LineNumberTable attribute '"
                + this.code.getLineNumberTable()
                + "' referring to a code offset ('"
                + lineNumberTable2[value2].getStart_pc()
                + "') that does not exist."));
      }
    }
    Attribute[] attributes = this.code.getAttributes();
    int value6;
    for (int value7 = value6 = 0; value7 < attributes.length; value7 = ++value6) {
      LocalVariableTable localVariableTable;
      if (attributes[value6] instanceof LocalVariableTable
          && (localVariableTable = (LocalVariableTable) attributes[value6]) != null) {
        LocalVariable[] localVariableTable2 = localVariableTable.getLocalVariableTable();
        int value8;
        for (int value9 = value8 = 0; value9 < localVariableTable2.length; value9 = ++value8) {
          int start_pc2 = localVariableTable2[value8].getStart_pc();
          int length2 = localVariableTable2[value8].getLength();
          if (!contains(instructionPositions, start_pc2)) {
            throw new LoadingException(
                ("Code attribute '"
                    + this.code
                    + "' has a LocalVariableTable attribute '"
                    + this.code.getLocalVariableTable()
                    + "' referring to a code offset ('"
                    + start_pc2
                    + "') that does not exist."));
          }
          if (!contains(instructionPositions, start_pc2 + length2)
              && start_pc2 + length2 != value) {
            throw new LoadingException(
                ("Code attribute '"
                    + this.code
                    + "' has a LocalVariableTable attribute '"
                    + this.code.getLocalVariableTable()
                    + "' referring to a code offset start_pc+length ('"
                    + (start_pc2 + length2)
                    + "') that does not exist."));
          }
        }
      }
    }
    CodeException[] exceptionTable = this.code.getExceptionTable();
    int value10;
    for (int value11 = value10 = 0; value11 < exceptionTable.length; value11 = ++value10) {
      int start_pc3 = exceptionTable[value10].getStart_pc();
      int end_pc = exceptionTable[value10].getEnd_pc();
      int handler_pc = exceptionTable[value10].getHandler_pc();
      if (start_pc3 >= end_pc) {
        throw new LoadingException(
            ("Code attribute '"
                + this.code
                + "' has an exception_table entry '"
                + exceptionTable[value10]
                + "' that has its start_pc ('"
                + start_pc3
                + "') not smaller than its end_pc ('"
                + end_pc
                + "')."));
      }
      if (!contains(instructionPositions, start_pc3)) {
        throw new LoadingException(
            ("Code attribute '"
                + this.code
                + "' has an exception_table entry '"
                + exceptionTable[value10]
                + "' that has a non-existant bytecode offset as its start_pc ('"
                + start_pc3
                + "')."));
      }
      if (!contains(instructionPositions, end_pc) && end_pc != value) {
        throw new LoadingException(
            ("Code attribute '"
                + this.code
                + "' has an exception_table entry '"
                + exceptionTable[value10]
                + "' that has a non-existant bytecode offset as its end_pc ('"
                + start_pc3
                + "') [that is also not equal to code_length ('"
                + value
                + "')]."));
      }
      if (!contains(instructionPositions, handler_pc)) {
        throw new LoadingException(
            ("Code attribute '"
                + this.code
                + "' has an exception_table entry '"
                + exceptionTable[value10]
                + "' that has a non-existant bytecode offset as its handler_pc ('"
                + handler_pc
                + "')."));
      }
    }
  }

  public static int access$100(Pass3aVerifier pass3aVerifier) {
    return pass3aVerifier.method_no;
  }
}
