package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.JsrInstruction;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.RET;
import com.allatori.lib.bcel.generic.ReturnInstruction;
import com.allatori.lib.bcel.generic.ReturnaddressType;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.verifier.PassVerifier;
import com.allatori.lib.bcel.verifier.VerificationResult;
import com.allatori.lib.bcel.verifier.Verifier;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.VerifierConstraintViolatedException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Random;

public final class Pass3bVerifier extends PassVerifier {

  public Verifier myOwner;

  public static final boolean DEBUG = true;

  public int method_no;

  @Override
  public VerificationResult getVerificationResult() {
    if (!this.myOwner.doPass3a(this.method_no).equals(VerificationResult.VERIFICATION_RESULT2)) {
      return VerificationResult.VERIFICATION_RESULT;
    }
    JavaClass javaClass;
    try {
      javaClass = Repository.lookupClass(this.myOwner.getClassName());
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
    ConstantPoolGen constantPoolGen = new ConstantPoolGen(javaClass.getConstantPool());
    InstConstraintVisitor instConstraintVisitor;
    (instConstraintVisitor = new InstConstraintVisitor()).setConstantPoolGen(constantPoolGen);
    ExecutionVisitor executionVisitor = new ExecutionVisitor();
    executionVisitor.setConstantPoolGen(constantPoolGen);
    Method[] methods = javaClass.getMethods();
    try {
      MethodGen methodGen =
          new MethodGen(methods[this.method_no], this.myOwner.getClassName(), constantPoolGen);
      instConstraintVisitor.setMethodGen(methodGen);
      if (!methodGen.isEnabled15() && !methodGen.isEnabled14()) {
        ControlFlowGraph controlFlowGraph;
        Frame frame;
        MethodGen methodGen2;
        label68:
        {
          controlFlowGraph = new ControlFlowGraph(methodGen);
          frame = new Frame(methodGen.getCount(), methodGen.getCount2());
          if (!methodGen.isEnabled11()) {
            if (methodGen.getName().equals("<init>")) {
              Frame._this = new UninitializedObjectType(new ObjectType(javaClass.getText2()));
              methodGen2 = methodGen;
              frame.getLocals().set(0, Frame._this);
              break label68;
            }
            Frame._this = null;
            LocalVariables locals = frame.getLocals();
            locals.set(0, new ObjectType(javaClass.getText2()));
          }
          methodGen2 = methodGen;
        }
        Type[] argumentTypes = methodGen2.getArgumentTypes();
        int value = 0;
        int value2;
        for (int value3 = value2 = 0; value3 < argumentTypes.length; value3 = ++value2) {
          if (argumentTypes[value2] == Type.SHORT
              || argumentTypes[value2] == Type.BYTE
              || argumentTypes[value2] == Type.CHAR
              || argumentTypes[value2] == Type.BOOLEAN) {
            argumentTypes[value2] = Type.INT;
          }
          frame
              .getLocals()
              .set(value + value2 + (methodGen.isEnabled11() ? 0 : 1), argumentTypes[value2]);
          if (argumentTypes[value2].getSize() == 2) {
            LocalVariables locals2 = frame.getLocals();
            locals2.set(++value + value2 + (methodGen.isEnabled11() ? 0 : 1), Type.UNKNOWN);
          }
        }
        this.circulationPump(
            controlFlowGraph,
            controlFlowGraph.contextOf(methodGen.getInstructionList().getStart()),
            frame,
            instConstraintVisitor,
            executionVisitor);
      }
    } catch (VerifierConstraintViolatedException verifierConstraintViolatedException) {
      verifierConstraintViolatedException.extendMessage(
          "Constraint violated in method '" + methods[this.method_no] + "':\n", "");
      return new VerificationResult(2, verifierConstraintViolatedException.getMessage());
    } catch (RuntimeException runtimeException) {
      StringWriter stringWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(stringWriter);
      runtimeException.printStackTrace(printWriter);
      throw new AssertionViolatedException(
          ("Some RuntimeException occured while verify()ing class '"
              + javaClass.getText2()
              + "', method '"
              + methods[this.method_no]
              + "'. Original RuntimeException's stack trace:\n---\n"
              + stringWriter
              + "---\n"));
    }
    return VerificationResult.VERIFICATION_RESULT2;
  }

  public Pass3bVerifier(Verifier verifier, int method_no) {
    super();
    this.myOwner = verifier;
    this.method_no = method_no;
  }

  public int getMethodNo() {
    return this.method_no;
  }

  public void circulationPump(
      ControlFlowGraph controlFlowGraph,
      InstructionContext instructionContext,
      Frame frame,
      InstConstraintVisitor instConstraintVisitor,
      ExecutionVisitor executionVisitor) {
    InstructionContext instructionContext2;
    new Random();
    Pass3bVerifier$InstructionContextQueue pass3bVerifier$InstructionContextQueue =
        new Pass3bVerifier$InstructionContextQueue(null);
    instructionContext.execute(frame, new ArrayList(), instConstraintVisitor, executionVisitor);
    pass3bVerifier$InstructionContextQueue.add(instructionContext, new ArrayList());
    while (!pass3bVerifier$InstructionContextQueue.isEmpty()) {
      InstructionContext iC = pass3bVerifier$InstructionContextQueue.getIC(0);
      ArrayList eC = pass3bVerifier$InstructionContextQueue.getEC(0);
      pass3bVerifier$InstructionContextQueue.remove(0);
      ArrayList values = (ArrayList) eC.clone();
      ArrayList values2;
      (values2 = (ArrayList) eC.clone()).add(iC);
      if (!(iC.getInstruction().getInstruction() instanceof RET)) {
        InstructionContext[] successors = iC.getSuccessors();
        int value;
        for (int value2 = value = 0; value2 < successors.length; value2 = ++value) {
          InstructionContext instructionContext3;
          if ((instructionContext3 = successors[value])
              .execute(iC.getOutFrame(values), values2, instConstraintVisitor, executionVisitor)) {
            pass3bVerifier$InstructionContextQueue.add(
                instructionContext3, (ArrayList) values2.clone());
          }
        }
      } else {
        RET rET = (RET) iC.getInstruction().getInstruction();
        ReturnaddressType returnaddressType =
            (ReturnaddressType) iC.getOutFrame(values).getLocals().get(rET.getIndex());
        InstructionContext instructionContext4 =
            controlFlowGraph.contextOf(returnaddressType.getTarget());
        InstructionContext instructionContext5 = null;
        int value3 = 0;
        int value4;
        int value5 = value4 = values.size() - 1;
        while (true) {
          if (value5 < 0) {
            instructionContext2 = instructionContext5;
            break;
          }
          if (value3 < 0) {
            throw new AssertionViolatedException("More RET than JSR in execution chain?!");
          }
          if (((InstructionContext) values.get(value4)).getInstruction().getInstruction()
              instanceof JsrInstruction) {
            if (value3 == 0) {
              instructionContext2 = instructionContext5 = (InstructionContext) values.get(value4);
              break;
            }
            value3--;
          }
          if (((InstructionContext) values.get(value4)).getInstruction().getInstruction()
              instanceof RET) {
            value3++;
          }
          value5 = --value4;
        }
        if (instructionContext2 == null) {
          throw new AssertionViolatedException(
              ("RET without a JSR before in ExecutionChain?! EC: '" + values + "'."));
        }
        JsrInstruction jsrInstruction =
            (JsrInstruction) instructionContext5.getInstruction().getInstruction();
        if (instructionContext4 != controlFlowGraph.contextOf(jsrInstruction.physicalSuccessor())) {
          throw new AssertionViolatedException(
              ("RET '"
                  + iC.getInstruction()
                  + "' info inconsistent: jump back to '"
                  + instructionContext4
                  + "' or '"
                  + controlFlowGraph.contextOf(jsrInstruction.physicalSuccessor())
                  + "'?"));
        }
        if (instructionContext4.execute(
            iC.getOutFrame(values), values2, instConstraintVisitor, executionVisitor)) {
          pass3bVerifier$InstructionContextQueue.add(
              instructionContext4, (ArrayList) values2.clone());
        }
      }
      ExceptionHandler[] exceptionHandlers = iC.getExceptionHandlers();
      int value6;
      for (int value7 = value6 = 0; value7 < exceptionHandlers.length; value7 = ++value6) {
        InstructionContext instructionContext6;
        if ((instructionContext6 =
                controlFlowGraph.contextOf(exceptionHandlers[value6].getHandlerStart()))
            .execute(
                new Frame(
                    iC.getOutFrame(values).getLocals(),
                    new OperandStack(
                        iC.getOutFrame(values).getStack().maxStack(),
                        exceptionHandlers[value6].getExceptionType() == null
                            ? Type.THROWABLE
                            : exceptionHandlers[value6].getExceptionType())),
                new ArrayList(),
                instConstraintVisitor,
                executionVisitor)) {
          pass3bVerifier$InstructionContextQueue.add(instructionContext6, new ArrayList());
        }
      }
    }
    InstructionHandle instruction = instructionContext.getInstruction();
    do {
      if (instruction.getInstruction() instanceof ReturnInstruction
          && !controlFlowGraph.isDead(instruction)) {
        Frame frame2;
        InstructionContext instructionContext7;
        LocalVariables locals =
            (frame2 =
                    (instructionContext7 = controlFlowGraph.contextOf(instruction))
                        .getOutFrame(new ArrayList()))
                .getLocals();
        int value8;
        for (int value9 = value8 = 0; value9 < locals.getLocals(); value9 = ++value8) {
          if (locals.get(value8) instanceof UninitializedObjectType) {
            this.addMessage(
                "Warning: ReturnInstruction '"
                    + instructionContext7
                    + "' may leave method with an uninitialized object in the local variables array"
                    + " '"
                    + locals
                    + "'.");
          }
        }
        OperandStack stack = frame2.getStack();
        int value10;
        for (int value11 = value10 = 0; value11 < stack.size(); value11 = ++value10) {
          if (stack.peek(value10) instanceof UninitializedObjectType) {
            this.addMessage(
                "Warning: ReturnInstruction '"
                    + instructionContext7
                    + "' may leave method with an uninitialized object on the operand stack '"
                    + stack
                    + "'.");
          }
        }
      }
    } while ((instruction = instruction.getInstructionHandle2()) != null);
  }
}
