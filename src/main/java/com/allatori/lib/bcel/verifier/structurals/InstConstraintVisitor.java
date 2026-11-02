package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantDouble;
import com.allatori.lib.bcel.classfile.ConstantFieldref;
import com.allatori.lib.bcel.classfile.ConstantFloat;
import com.allatori.lib.bcel.classfile.ConstantInteger;
import com.allatori.lib.bcel.classfile.ConstantLong;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.generic.AALOAD;
import com.allatori.lib.bcel.generic.AASTORE;
import com.allatori.lib.bcel.generic.ACONST_NULL;
import com.allatori.lib.bcel.generic.ALOAD;
import com.allatori.lib.bcel.generic.ANEWARRAY;
import com.allatori.lib.bcel.generic.ARETURN;
import com.allatori.lib.bcel.generic.ARRAYLENGTH;
import com.allatori.lib.bcel.generic.ASTORE;
import com.allatori.lib.bcel.generic.ATHROW;
import com.allatori.lib.bcel.generic.ArrayType;
import com.allatori.lib.bcel.generic.BALOAD;
import com.allatori.lib.bcel.generic.BASTORE;
import com.allatori.lib.bcel.generic.BIPUSH;
import com.allatori.lib.bcel.generic.BREAKPOINT;
import com.allatori.lib.bcel.generic.CALOAD;
import com.allatori.lib.bcel.generic.CASTORE;
import com.allatori.lib.bcel.generic.CHECKCAST;
import com.allatori.lib.bcel.generic.CPInstruction;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.D2F;
import com.allatori.lib.bcel.generic.D2I;
import com.allatori.lib.bcel.generic.D2L;
import com.allatori.lib.bcel.generic.DADD;
import com.allatori.lib.bcel.generic.DALOAD;
import com.allatori.lib.bcel.generic.DASTORE;
import com.allatori.lib.bcel.generic.DCMPG;
import com.allatori.lib.bcel.generic.DCMPL;
import com.allatori.lib.bcel.generic.DCONST;
import com.allatori.lib.bcel.generic.DDIV;
import com.allatori.lib.bcel.generic.DLOAD;
import com.allatori.lib.bcel.generic.DMUL;
import com.allatori.lib.bcel.generic.DNEG;
import com.allatori.lib.bcel.generic.DREM;
import com.allatori.lib.bcel.generic.DRETURN;
import com.allatori.lib.bcel.generic.DSTORE;
import com.allatori.lib.bcel.generic.DSUB;
import com.allatori.lib.bcel.generic.DUP;
import com.allatori.lib.bcel.generic.DUP2;
import com.allatori.lib.bcel.generic.DUP2_X1;
import com.allatori.lib.bcel.generic.DUP2_X2;
import com.allatori.lib.bcel.generic.DUP_X1;
import com.allatori.lib.bcel.generic.DUP_X2;
import com.allatori.lib.bcel.generic.EmptyVisitor;
import com.allatori.lib.bcel.generic.F2D;
import com.allatori.lib.bcel.generic.F2I;
import com.allatori.lib.bcel.generic.F2L;
import com.allatori.lib.bcel.generic.FADD;
import com.allatori.lib.bcel.generic.FALOAD;
import com.allatori.lib.bcel.generic.FASTORE;
import com.allatori.lib.bcel.generic.FCMPG;
import com.allatori.lib.bcel.generic.FCMPL;
import com.allatori.lib.bcel.generic.FCONST;
import com.allatori.lib.bcel.generic.FDIV;
import com.allatori.lib.bcel.generic.FLOAD;
import com.allatori.lib.bcel.generic.FMUL;
import com.allatori.lib.bcel.generic.FNEG;
import com.allatori.lib.bcel.generic.FREM;
import com.allatori.lib.bcel.generic.FRETURN;
import com.allatori.lib.bcel.generic.FSTORE;
import com.allatori.lib.bcel.generic.FSUB;
import com.allatori.lib.bcel.generic.FieldInstruction;
import com.allatori.lib.bcel.generic.GETFIELD;
import com.allatori.lib.bcel.generic.GETSTATIC;
import com.allatori.lib.bcel.generic.GOTO;
import com.allatori.lib.bcel.generic.GOTO_W;
import com.allatori.lib.bcel.generic.I2B;
import com.allatori.lib.bcel.generic.I2C;
import com.allatori.lib.bcel.generic.I2D;
import com.allatori.lib.bcel.generic.I2F;
import com.allatori.lib.bcel.generic.I2L;
import com.allatori.lib.bcel.generic.I2S;
import com.allatori.lib.bcel.generic.IADD;
import com.allatori.lib.bcel.generic.IALOAD;
import com.allatori.lib.bcel.generic.IAND;
import com.allatori.lib.bcel.generic.IASTORE;
import com.allatori.lib.bcel.generic.ICONST;
import com.allatori.lib.bcel.generic.IDIV;
import com.allatori.lib.bcel.generic.IFEQ;
import com.allatori.lib.bcel.generic.IFGE;
import com.allatori.lib.bcel.generic.IFGT;
import com.allatori.lib.bcel.generic.IFLE;
import com.allatori.lib.bcel.generic.IFLT;
import com.allatori.lib.bcel.generic.IFNE;
import com.allatori.lib.bcel.generic.IFNONNULL;
import com.allatori.lib.bcel.generic.IFNULL;
import com.allatori.lib.bcel.generic.IF_ACMPEQ;
import com.allatori.lib.bcel.generic.IF_ACMPNE;
import com.allatori.lib.bcel.generic.IF_ICMPEQ;
import com.allatori.lib.bcel.generic.IF_ICMPGE;
import com.allatori.lib.bcel.generic.IF_ICMPGT;
import com.allatori.lib.bcel.generic.IF_ICMPLE;
import com.allatori.lib.bcel.generic.IF_ICMPLT;
import com.allatori.lib.bcel.generic.IF_ICMPNE;
import com.allatori.lib.bcel.generic.IINC;
import com.allatori.lib.bcel.generic.ILOAD;
import com.allatori.lib.bcel.generic.IMPDEP1;
import com.allatori.lib.bcel.generic.IMPDEP2;
import com.allatori.lib.bcel.generic.IMUL;
import com.allatori.lib.bcel.generic.INEG;
import com.allatori.lib.bcel.generic.INSTANCEOF;
import com.allatori.lib.bcel.generic.INVOKEINTERFACE;
import com.allatori.lib.bcel.generic.INVOKESPECIAL;
import com.allatori.lib.bcel.generic.INVOKESTATIC;
import com.allatori.lib.bcel.generic.INVOKEVIRTUAL;
import com.allatori.lib.bcel.generic.IOR;
import com.allatori.lib.bcel.generic.IREM;
import com.allatori.lib.bcel.generic.IRETURN;
import com.allatori.lib.bcel.generic.ISHL;
import com.allatori.lib.bcel.generic.ISHR;
import com.allatori.lib.bcel.generic.ISTORE;
import com.allatori.lib.bcel.generic.ISUB;
import com.allatori.lib.bcel.generic.IUSHR;
import com.allatori.lib.bcel.generic.IXOR;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InvokeInstruction;
import com.allatori.lib.bcel.generic.JSR;
import com.allatori.lib.bcel.generic.JSR_W;
import com.allatori.lib.bcel.generic.L2D;
import com.allatori.lib.bcel.generic.L2F;
import com.allatori.lib.bcel.generic.L2I;
import com.allatori.lib.bcel.generic.LADD;
import com.allatori.lib.bcel.generic.LALOAD;
import com.allatori.lib.bcel.generic.LAND;
import com.allatori.lib.bcel.generic.LASTORE;
import com.allatori.lib.bcel.generic.LCMP;
import com.allatori.lib.bcel.generic.LCONST;
import com.allatori.lib.bcel.generic.LDC;
import com.allatori.lib.bcel.generic.LDC2_W;
import com.allatori.lib.bcel.generic.LDC_W;
import com.allatori.lib.bcel.generic.LDIV;
import com.allatori.lib.bcel.generic.LLOAD;
import com.allatori.lib.bcel.generic.LMUL;
import com.allatori.lib.bcel.generic.LNEG;
import com.allatori.lib.bcel.generic.LOOKUPSWITCH;
import com.allatori.lib.bcel.generic.LOR;
import com.allatori.lib.bcel.generic.LREM;
import com.allatori.lib.bcel.generic.LRETURN;
import com.allatori.lib.bcel.generic.LSHL;
import com.allatori.lib.bcel.generic.LSHR;
import com.allatori.lib.bcel.generic.LSTORE;
import com.allatori.lib.bcel.generic.LSUB;
import com.allatori.lib.bcel.generic.LUSHR;
import com.allatori.lib.bcel.generic.LXOR;
import com.allatori.lib.bcel.generic.LoadClass;
import com.allatori.lib.bcel.generic.LoadInstruction;
import com.allatori.lib.bcel.generic.LocalVariableInstruction;
import com.allatori.lib.bcel.generic.MONITORENTER;
import com.allatori.lib.bcel.generic.MONITOREXIT;
import com.allatori.lib.bcel.generic.MULTIANEWARRAY;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.NEW;
import com.allatori.lib.bcel.generic.NEWARRAY;
import com.allatori.lib.bcel.generic.NOP;
import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.POP;
import com.allatori.lib.bcel.generic.POP2;
import com.allatori.lib.bcel.generic.PUTFIELD;
import com.allatori.lib.bcel.generic.PUTSTATIC;
import com.allatori.lib.bcel.generic.RET;
import com.allatori.lib.bcel.generic.RETURN;
import com.allatori.lib.bcel.generic.ReferenceType;
import com.allatori.lib.bcel.generic.ReturnInstruction;
import com.allatori.lib.bcel.generic.ReturnaddressType;
import com.allatori.lib.bcel.generic.SALOAD;
import com.allatori.lib.bcel.generic.SASTORE;
import com.allatori.lib.bcel.generic.SIPUSH;
import com.allatori.lib.bcel.generic.SWAP;
import com.allatori.lib.bcel.generic.StackConsumer;
import com.allatori.lib.bcel.generic.StackInstruction;
import com.allatori.lib.bcel.generic.StackProducer;
import com.allatori.lib.bcel.generic.StoreInstruction;
import com.allatori.lib.bcel.generic.TABLESWITCH;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.generic.Visitor;
import com.allatori.lib.bcel.verifier.VerificationResult;
import com.allatori.lib.bcel.verifier.VerifierFactory;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.StructuralCodeConstraintException;

public class InstConstraintVisitor extends EmptyVisitor implements Visitor {

  public MethodGen mg;

  public ConstantPoolGen cpg;

  public static ObjectType GENERIC_ARRAY =
      new ObjectType("com.allatori.lib.bcel.verifier.structurals.GenericArray");

  public Frame frame = null;

  @Override
  public void visitIFGE(IFGE fGE) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          fGE,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitINSTANCEOF(INSTANCEOF nSTANCEOF) {
    Type type;
    if (!((type = this.stack().peek(0)) instanceof ReferenceType)) {
      this.constraintViolated(
          nSTANCEOF, "The 'objectref' is not of a ReferenceType but of type " + type + ".");
    }
    Constant constant;
    if (!((constant = this.cpg.getConstant(nSTANCEOF.getIndex())) instanceof ConstantClass)) {
      this.constraintViolated(
          nSTANCEOF, "The Constant at 'index' is not a ConstantClass, but '" + constant + "'.");
    }
  }

  @Override
  public void visitLCMP(LCMP lCMP) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lCMP,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lCMP,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitFNEG(FNEG fNEG) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fNEG,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitGETSTATIC(GETSTATIC gETSTATIC) {}

  @Override
  public void visitLASTORE(LASTORE lASTORE) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lASTORE,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    this.processType2(lASTORE, this.stack().peek(1));
    if (this.stack().peek(2) != Type.NULL) {
      if (!(this.stack().peek(2) instanceof ArrayType)) {
        this.constraintViolated(
            lASTORE,
            "Stack next-to-next-to-top must be of type long[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
      if (((ArrayType) this.stack().peek(2)).getBasicType() != Type.LONG) {
        this.constraintViolated(
            lASTORE,
            "Stack next-to-next-to-top must be of type long[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
    }
  }

  public void setFrame(Frame frame2) {
    this.frame = frame2;
  }

  @Override
  public void visitDUP2_X1(DUP2_X1 dUP2_X1) {
    if (this.stack().peek().getSize() == 2) {
      if (this.stack().peek(1).getSize() != 1) {
        this.constraintViolated(
            dUP2_X1,
            "If stack top's size is 2, then stack next-to-top's size must be 1. But it is '"
                + this.stack().peek(1)
                + "' of size '"
                + this.stack().peek(1).getSize()
                + "'.");
      }
    } else {
      if (this.stack().peek(1).getSize() != 1) {
        this.constraintViolated(
            dUP2_X1,
            "If stack top's size is 1, then stack next-to-top's size must also be 1. But it is '"
                + this.stack().peek(1)
                + "' of size '"
                + this.stack().peek(1).getSize()
                + "'.");
      }
      if (this.stack().peek(2).getSize() != 1) {
        this.constraintViolated(
            dUP2_X1,
            "If stack top's size is 1, then stack next-to-next-to-top's size must also be 1. But it"
                + " is '"
                + this.stack().peek(2)
                + "' of size '"
                + this.stack().peek(2).getSize()
                + "'.");
      }
    }
  }

  @Override
  public void visitDREM(DREM dREM) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dREM,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.DOUBLE) {
      this.constraintViolated(
          dREM,
          "The value at the stack next-to-top is not of type 'double', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  public void referenceTypeIsInitialized(Instruction instruction, ReferenceType referenceType) {
    if (referenceType instanceof UninitializedObjectType) {
      this.constraintViolated(
          instruction, "Working on an uninitialized object '" + referenceType + "'.");
    }
  }

  @Override
  public void visitLoadInstruction(LoadInstruction loadInstruction) {
    if (this.locals().get(loadInstruction.getIndex()) == Type.UNKNOWN) {
      this.constraintViolated(
          loadInstruction,
          "Read-Access on local variable " + loadInstruction.getIndex() + " with unknown content.");
    }
    if (loadInstruction.getType(this.cpg).getSize() == 2
        && this.locals().get(loadInstruction.getIndex() + 1) != Type.UNKNOWN) {
      this.constraintViolated(
          loadInstruction,
          "Reading a two-locals value from local variables "
              + loadInstruction.getIndex()
              + " and "
              + (loadInstruction.getIndex() + 1)
              + " where the latter one is destroyed.");
    }
    InstConstraintVisitor instConstraintVisitor;
    label26:
    {
      if (!(loadInstruction instanceof ALOAD)) {
        if (this.locals().get(loadInstruction.getIndex()) != loadInstruction.getType(this.cpg)) {
          StringBuilder stringBuilder =
              new StringBuilder()
                  .insert(
                      0,
                      "Local Variable type and LOADing Instruction type mismatch: Local Variable:"
                          + " '");
          instConstraintVisitor = this;
          this.constraintViolated(
              loadInstruction,
              stringBuilder
                  .append(this.locals().get(loadInstruction.getIndex()))
                  .append("'; Instruction type: '")
                  .append(loadInstruction.getType(this.cpg))
                  .append("'.")
                  .toString());
          break label26;
        }
      } else if (!(this.locals().get(loadInstruction.getIndex()) instanceof ReferenceType)) {
        this.constraintViolated(
            loadInstruction,
            "Local Variable type and LOADing Instruction type mismatch: Local Variable: '"
                + this.locals().get(loadInstruction.getIndex())
                + "'; Instruction expects a ReferenceType.");
      }
      instConstraintVisitor = this;
    }
    if (instConstraintVisitor.stack().maxStack() - this.stack().slotsUsed()
        < loadInstruction.getType(this.cpg).getSize()) {
      this.constraintViolated(
          loadInstruction,
          "Not enough free stack slots to load a '"
              + loadInstruction.getType(this.cpg)
              + "' onto the OperandStack.");
    }
  }

  @Override
  public void visitDNEG(DNEG dNEG) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dNEG,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitStackProducer(StackProducer stackProducer) {
    this._visitStackAccessor((Instruction) stackProducer);
  }

  @Override
  public void visitI2B(I2B i2B) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          i2B,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitL2I(L2I l2I) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          l2I,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitIALOAD(IALOAD aLOAD) {
    this.processType2(aLOAD, this.stack().peek());
    if (this.stack().peek(1) != Type.NULL) {
      if (!(this.stack().peek(1) instanceof ArrayType)) {
        this.constraintViolated(
            aLOAD,
            "Stack next-to-top must be of type int[] but is '" + this.stack().peek(1) + "'.");
      }
      if (((ArrayType) this.stack().peek(1)).getBasicType() != Type.INT) {
        this.constraintViolated(
            aLOAD,
            "Stack next-to-top must be of type int[] but is '" + this.stack().peek(1) + "'.");
      }
    }
  }

  @Override
  public void visitFMUL(FMUL fMUL) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fMUL,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.FLOAT) {
      this.constraintViolated(
          fMUL,
          "The value at the stack next-to-top is not of type 'float', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitLLOAD(LLOAD lLOAD) {}

  @Override
  public void visitDUP_X1(DUP_X1 dUP_X1) {
    if (this.stack().peek().getSize() != 1) {
      this.constraintViolated(
          dUP_X1,
          "Type on stack top '"
              + this.stack().peek()
              + "' should occupy exactly one slot, not '"
              + this.stack().peek().getSize()
              + "'.");
    }
    if (this.stack().peek(1).getSize() != 1) {
      this.constraintViolated(
          dUP_X1,
          "Type on stack next-to-top '"
              + this.stack().peek(1)
              + "' should occupy exactly one slot, not '"
              + this.stack().peek(1).getSize()
              + "'.");
    }
  }

  @Override
  public void visitPOP2(POP2 pOP2) {
    if (this.stack().peek().getSize() != 2) {
      this.constraintViolated(
          pOP2,
          "Stack top size should be 2 but stack top is '"
              + this.stack().peek()
              + "' of size '"
              + this.stack().peek().getSize()
              + "'.");
    }
  }

  @Override
  public void visitFCONST(FCONST fCONST) {}

  @Override
  public void visitLDC2_W(LDC2_W lDC2_W) {
    Constant constant;
    if (!((constant = this.cpg.getConstant(lDC2_W.getIndex())) instanceof ConstantLong)
        && !(constant instanceof ConstantDouble)) {
      this.constraintViolated(
          lDC2_W,
          "Referenced constant should be a CONSTANT_Integer, a CONSTANT_Float or a CONSTANT_String,"
              + " but is '"
              + constant
              + "'.");
    }
  }

  @Override
  public void visitPUTSTATIC(PUTSTATIC pUTSTATIC) {
    Field field;
    try {
      String fieldName = pUTSTATIC.getFieldName(this.cpg);
      Field[] fields =
          Repository.lookupClass(pUTSTATIC.createObjectType(this.cpg).getClassName()).getFields();
      Field field2 = null;
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= fields.length) {
          field = field2;
          break;
        }
        if (fields[value].getText().equals(fieldName)) {
          Type type = Type.getType(fields[value].getText2());
          Type type2 = pUTSTATIC.getType(this.cpg);
          if (type.equals(type2)) {
            field = field2 = fields[value];
            break;
          }
        }
        value2 = ++value;
      }
      if (field == null) {
        throw new AssertionViolatedException("Field not found?!?");
      }
      Type type3 = this.stack().peek();
      Type type4;
      Type type5 = type4 = Type.getType(field2.getText2());
      if (type5 == Type.BOOLEAN
          || type5 == Type.BYTE
          || type5 == Type.CHAR
          || type5 == Type.SHORT) {
        type5 = Type.INT;
      }
      if (type4 instanceof ReferenceType) {
        ReferenceType referenceType;
        if (type3 instanceof ReferenceType) {
          ReferenceType referenceType2 = (ReferenceType) type3;
          referenceType = referenceType2;
          this.referenceTypeIsInitialized(pUTSTATIC, referenceType2);
        } else {
          this.constraintViolated(
              pUTSTATIC,
              "The stack top type '" + type3 + "' is not of a reference type as expected.");
          referenceType = (ReferenceType) (Object) null;
        }
        if (!referenceType.matchesType2(type5)) {
          this.constraintViolated(
              pUTSTATIC,
              "The stack top type '"
                  + type3
                  + "' is not assignment compatible with '"
                  + type5
                  + "'.");
          return;
        }
      } else if (type5 != type3) {
        this.constraintViolated(
            pUTSTATIC,
            "The stack top type '" + type3 + "' is not of type '" + type5 + "' as expected.");
        return;
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  public void constraintViolated(Instruction instruction, String text) {
    String name = instruction.getClass().getName();
    throw new StructuralCodeConstraintException(
        ("Instruction "
            + name.substring(name.lastIndexOf(46) + 1)
            + " constraint violated: "
            + text));
  }

  @Override
  public void visitBASTORE(BASTORE bASTORE) {
    Type type = this.stack().peek(2);
    Type type2 = this.stack().peek(1);
    Type type3 = this.stack().peek(0);
    this.processType2(bASTORE, type2);
    this.processType(bASTORE, type3);
    if (this.arrayrefOfArrayType(bASTORE, type)
        && !((ArrayType) type).getElementType().equals(Type.BOOLEAN)
        && !((ArrayType) type).getElementType().equals(Type.BYTE)) {
      this.constraintViolated(
          bASTORE,
          "The 'arrayref' does not refer to an array with elements of a Type.BYTE or Type.BOOLEAN"
              + " but to an array of '"
              + ((ArrayType) type).getElementType()
              + "'.");
    }
  }

  @Override
  public void visitAALOAD(AALOAD aALOAD) {
    Type type = this.stack().peek(1);
    Type type2 = this.stack().peek(0);
    this.processType2(aALOAD, type2);
    if (this.arrayrefOfArrayType(aALOAD, type)
        && !(((ArrayType) type).getElementType() instanceof ReferenceType)) {
      this.constraintViolated(
          aALOAD,
          "The 'arrayref' does not refer to an array with elements of a ReferenceType but to an"
              + " array of "
              + ((ArrayType) type).getElementType()
              + ".");
    }
  }

  @Override
  public void visitARRAYLENGTH(ARRAYLENGTH aRRAYLENGTH) {
    Type type = this.stack().peek(0);
    this.arrayrefOfArrayType(aRRAYLENGTH, type);
  }

  @Override
  public void visitDSTORE(DSTORE dSTORE) {}

  @Override
  public void visitATHROW(ATHROW aTHROW) {
    try {
      if (!(this.stack().peek() instanceof ObjectType) && !this.stack().peek().equals(Type.NULL)) {
        this.constraintViolated(
            aTHROW,
            "The 'objectref' is not of an (initialized) ObjectType but of type "
                + this.stack().peek()
                + ".");
      }
      if (!this.stack().peek().equals(Type.NULL)) {
        ObjectType objectType = (ObjectType) this.stack().peek();
        ObjectType objectType2 = (ObjectType) Type.getType("Ljava/lang/Throwable;");
        if (!objectType.matchesObjectType(objectType2) && !objectType.equals(objectType2)) {
          this.constraintViolated(
              aTHROW,
              "The 'objectref' is not of class Throwable or of a subclass of Throwable, but of '"
                  + this.stack().peek()
                  + "'.");
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitI2S(I2S i2S) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          i2S,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitINVOKESTATIC(INVOKESTATIC nVOKESTATIC) {
    try {
      VerificationResult verificationResult;
      String text;
      Type type;
      if ((type = nVOKESTATIC.getType(this.cpg)) instanceof ObjectType
          && (verificationResult =
                      VerifierFactory.getVerifier(text = ((ObjectType) type).getClassName())
                          .createVerificationResult2())
                  .getStatus()
              != 1) {
        this.constraintViolated(
            nVOKESTATIC,
            "Class '"
                + text
                + "' is referenced, but cannot be loaded and resolved: '"
                + verificationResult
                + "'.");
      }
      int value;
      Type[] types;
      for (int value2 = (value = (types = nVOKESTATIC.getArgumentTypes(this.cpg)).length) - 1;
          value2 >= 0;
          value2--) {
        Type type2 = this.stack().peek(value - 1 - value2);
        Type type3;
        if ((type3 = types[value2]) == Type.BOOLEAN
            || type3 == Type.BYTE
            || type3 == Type.CHAR
            || type3 == Type.SHORT) {
          type3 = Type.INT;
        }
        if (!type2.equals(type3)) {
          if (type2 instanceof ReferenceType && type3 instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type2;
            ReferenceType referenceType2 = (ReferenceType) type3;
            if (!referenceType.matchesType2(referenceType2)) {
              this.constraintViolated(
                  nVOKESTATIC,
                  "Expecting a '"
                      + type3
                      + "' but found a '"
                      + type2
                      + "' on the stack (which is not assignment compatible).");
            }
            this.referenceTypeIsInitialized(nVOKESTATIC, referenceType);
          } else {
            this.constraintViolated(
                nVOKESTATIC,
                "Expecting a '" + type3 + "' but found a '" + type2 + "' on the stack.");
          }
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitIF_ICMPNE(IF_ICMPNE f_ICMPNE) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          f_ICMPNE,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          f_ICMPNE,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitFSUB(FSUB fSUB) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fSUB,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.FLOAT) {
      this.constraintViolated(
          fSUB,
          "The value at the stack next-to-top is not of type 'float', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitDCONST(DCONST dCONST) {}

  @Override
  public void visitLocalVariableInstruction(LocalVariableInstruction localVariableInstruction) {
    if (this.locals().getLocals()
        <= (localVariableInstruction.getType(this.cpg).getSize() == 1
            ? localVariableInstruction.getIndex()
            : localVariableInstruction.getIndex() + 1)) {
      this.constraintViolated(
          localVariableInstruction,
          "The 'index' is not a valid index into the local variable array.");
    }
  }

  @Override
  public void visitINVOKEVIRTUAL(INVOKEVIRTUAL nVOKEVIRTUAL) {
    try {
      String text;
      VerificationResult verificationResult;
      Type type;
      if ((type = nVOKEVIRTUAL.getType(this.cpg)) instanceof ObjectType
          && (verificationResult =
                      VerifierFactory.getVerifier(text = ((ObjectType) type).getClassName())
                          .createVerificationResult2())
                  .getStatus()
              != 1) {
        this.constraintViolated(
            nVOKEVIRTUAL,
            "Class '"
                + text
                + "' is referenced, but cannot be loaded and resolved: '"
                + verificationResult
                + "'.");
      }
      Type[] types;
      int value;
      int value2;
      for (int value3 =
              value = (value2 = (types = nVOKEVIRTUAL.getArgumentTypes(this.cpg)).length) - 1;
          value3 >= 0;
          value3 = --value) {
        Type type2 = this.stack().peek(value2 - 1 - value);
        Type type3;
        if ((type3 = types[value]) == Type.BOOLEAN
            || type3 == Type.BYTE
            || type3 == Type.CHAR
            || type3 == Type.SHORT) {
          type3 = Type.INT;
        }
        if (!type2.equals(type3)) {
          if (type2 instanceof ReferenceType && type3 instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type2;
            ReferenceType referenceType2 = (ReferenceType) type3;
            if (!referenceType.matchesType2(referenceType2)) {
              this.constraintViolated(
                  nVOKEVIRTUAL,
                  "Expecting a '"
                      + type3
                      + "' but found a '"
                      + type2
                      + "' on the stack (which is not assignment compatible).");
            }
            this.referenceTypeIsInitialized(nVOKEVIRTUAL, referenceType);
          } else {
            this.constraintViolated(
                nVOKEVIRTUAL,
                "Expecting a '" + type3 + "' but found a '" + type2 + "' on the stack.");
          }
        }
      }
      Type type4;
      if ((type4 = this.stack().peek(value2)) != Type.NULL) {
        if (!(type4 instanceof ReferenceType)) {
          this.constraintViolated(
              nVOKEVIRTUAL,
              "Expecting a reference type as 'objectref' on the stack, not a '" + type4 + "'.");
        }
        Type type5;
        label51:
        {
          this.referenceTypeIsInitialized(nVOKEVIRTUAL, (ReferenceType) type4);
          if (!(type4 instanceof ObjectType)) {
            if (!(type4 instanceof ArrayType)) {
              type5 = type4;
              this.constraintViolated(
                  nVOKEVIRTUAL,
                  "Expecting an ObjectType as 'objectref' on the stack, not a '" + type4 + "'.");
              break label51;
            }
            type4 = GENERIC_ARRAY;
          }
          type5 = type4;
        }
        String className = ((ObjectType) type5).getClassName();
        String text2 = nVOKEVIRTUAL.getText2(this.cpg);
        if (!Repository.matchesText(className, text2)) {
          this.constraintViolated(
              nVOKEVIRTUAL,
              "The 'objref' item '" + type4 + "' does not implement '" + text2 + "' as expected.");
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitD2I(D2I d2I) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          d2I,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitNOP(NOP nOP) {}

  public void visitLDC_W(LDC_W lDC_W) {
    Constant constant;
    if (!((constant = this.cpg.getConstant(lDC_W.getIndex())) instanceof ConstantInteger)
        && !(constant instanceof ConstantFloat)
        && !(constant instanceof ConstantString)) {
      this.constraintViolated(
          lDC_W,
          "Referenced constant should be a CONSTANT_Integer, a CONSTANT_Float or a CONSTANT_String,"
              + " but is '"
              + constant
              + "'.");
    }
  }

  @Override
  public void visitLXOR(LXOR lXOR) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lXOR,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lXOR,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitLCONST(LCONST lCONST) {}

  @Override
  public void visitIF_ACMPEQ(IF_ACMPEQ f_ACMPEQ) {
    if (!(this.stack().peek() instanceof ReferenceType)) {
      this.constraintViolated(
          f_ACMPEQ,
          "The value at the stack top is not of a ReferenceType, but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (!(this.stack().peek(1) instanceof ReferenceType)) {
      this.constraintViolated(
          f_ACMPEQ,
          "The value at the stack next-to-top is not of a ReferenceType, but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitI2L(I2L i2L) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          i2L,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitReturnInstruction(ReturnInstruction returnInstruction) {
    Type type;
    if ((type = this.mg.getType()) == Type.BOOLEAN
        || type == Type.BYTE
        || type == Type.SHORT
        || type == Type.CHAR) {
      type = Type.INT;
    }
    ReturnInstruction returnInstruction2;
    if (returnInstruction instanceof RETURN) {
      if (type == Type.VOID) {
        return;
      }
      returnInstruction2 = returnInstruction;
      this.constraintViolated(returnInstruction, "RETURN instruction in non-void method.");
    } else {
      returnInstruction2 = returnInstruction;
    }
    if (returnInstruction2 instanceof ARETURN) {
      if (this.stack().peek() != Type.NULL) {
        if (!(this.stack().peek() instanceof ReferenceType)) {
          this.constraintViolated(
              returnInstruction,
              "Reference type expected on top of stack, but is: '" + this.stack().peek() + "'.");
        }
        this.referenceTypeIsInitialized(returnInstruction, (ReferenceType) this.stack().peek());
      }
    } else {
      if (!type.equals(this.stack().peek())) {
        this.constraintViolated(
            returnInstruction,
            "Current method has return type of '"
                + this.mg.getType()
                + "' expecting a '"
                + type
                + "' on top of the stack. But stack top is a '"
                + this.stack().peek()
                + "'.");
      }
    }
  }

  @Override
  public void visitIFNE(IFNE fNE) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          fNE,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitDRETURN(DRETURN dRETURN) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dRETURN,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitD2L(D2L d2L) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          d2L,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitIREM(IREM rEM) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          rEM,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          rEM,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitJSR(JSR jSR) {}

  @Override
  public void visitIMPDEP2(IMPDEP2 mPDEP2) {
    throw new AssertionViolatedException(
        "In this JustIce verification pass there should not occur an illegal instruction such as"
            + " IMPDEP2.");
  }

  @Override
  public void visitSASTORE(SASTORE sASTORE) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          sASTORE,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    this.processType2(sASTORE, this.stack().peek(1));
    if (this.stack().peek(2) != Type.NULL) {
      if (!(this.stack().peek(2) instanceof ArrayType)) {
        this.constraintViolated(
            sASTORE,
            "Stack next-to-next-to-top must be of type short[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
      if (((ArrayType) this.stack().peek(2)).getBasicType() != Type.SHORT) {
        this.constraintViolated(
            sASTORE,
            "Stack next-to-next-to-top must be of type short[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
    }
  }

  @Override
  public void visitCALOAD(CALOAD cALOAD) {
    Type type = this.stack().peek(1);
    Type type2 = this.stack().peek(0);
    this.processType2(cALOAD, type2);
    this.arrayrefOfArrayType(cALOAD, type);
  }

  @Override
  public void visitGOTO_W(GOTO_W gOTO_W) {}

  @Override
  public void visitGOTO(GOTO gOTO) {}

  public OperandStack stack() {
    return this.frame.getStack();
  }

  @Override
  public void visitIASTORE(IASTORE aSTORE) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          aSTORE,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    this.processType2(aSTORE, this.stack().peek(1));
    if (this.stack().peek(2) != Type.NULL) {
      if (!(this.stack().peek(2) instanceof ArrayType)) {
        this.constraintViolated(
            aSTORE,
            "Stack next-to-next-to-top must be of type int[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
      if (((ArrayType) this.stack().peek(2)).getBasicType() != Type.INT) {
        this.constraintViolated(
            aSTORE,
            "Stack next-to-next-to-top must be of type int[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
    }
  }

  @Override
  public void visitSALOAD(SALOAD sALOAD) {
    this.processType2(sALOAD, this.stack().peek());
    if (this.stack().peek(1) != Type.NULL) {
      if (!(this.stack().peek(1) instanceof ArrayType)) {
        this.constraintViolated(
            sALOAD,
            "Stack next-to-top must be of type short[] but is '" + this.stack().peek(1) + "'.");
      }
      if (((ArrayType) this.stack().peek(1)).getBasicType() != Type.SHORT) {
        this.constraintViolated(
            sALOAD,
            "Stack next-to-top must be of type short[] but is '" + this.stack().peek(1) + "'.");
      }
    }
  }

  @Override
  public void visitIF_ICMPGT(IF_ICMPGT f_ICMPGT) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          f_ICMPGT,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          f_ICMPGT,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitDALOAD(DALOAD dALOAD) {
    this.processType2(dALOAD, this.stack().peek());
    if (this.stack().peek(1) != Type.NULL) {
      if (!(this.stack().peek(1) instanceof ArrayType)) {
        this.constraintViolated(
            dALOAD,
            "Stack next-to-top must be of type double[] but is '" + this.stack().peek(1) + "'.");
      }
      if (((ArrayType) this.stack().peek(1)).getBasicType() != Type.DOUBLE) {
        this.constraintViolated(
            dALOAD,
            "Stack next-to-top must be of type double[] but is '" + this.stack().peek(1) + "'.");
      }
    }
  }

  @Override
  public void visitL2F(L2F l2F) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          l2F,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitDDIV(DDIV dDIV) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dDIV,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.DOUBLE) {
      this.constraintViolated(
          dDIV,
          "The value at the stack next-to-top is not of type 'double', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitPOP(POP pOP) {
    if (this.stack().peek().getSize() != 1) {
      this.constraintViolated(
          pOP,
          "Stack top size should be 1 but stack top is '"
              + this.stack().peek()
              + "' of size '"
              + this.stack().peek().getSize()
              + "'.");
    }
  }

  @Override
  public void visitLSTORE(LSTORE lSTORE) {}

  @Override
  public void visitF2D(F2D f2D) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          f2D,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitNEWARRAY(NEWARRAY nEWARRAY) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          nEWARRAY,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitLAND(LAND lAND) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lAND,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lAND,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitDLOAD(DLOAD dLOAD) {}

  @Override
  public void visitStoreInstruction(StoreInstruction storeInstruction) {
    if (this.stack().isEmpty()) {
      this.constraintViolated(storeInstruction, "Cannot STORE: Stack to read from is empty.");
    }
    if (!(storeInstruction instanceof ASTORE)) {
      if (this.stack().peek() != storeInstruction.getType(this.cpg)) {
        this.constraintViolated(
            storeInstruction,
            "Stack top type and STOREing Instruction type mismatch: Stack top: '"
                + this.stack().peek()
                + "'; Instruction type: '"
                + storeInstruction.getType(this.cpg)
                + "'.");
        return;
      }
    } else {
      Type type;
      if (!((type = this.stack().peek()) instanceof ReferenceType)
          && !(type instanceof ReturnaddressType)) {
        this.constraintViolated(
            storeInstruction,
            "Stack top type and STOREing Instruction type mismatch: Stack top: '"
                + this.stack().peek()
                + "'; Instruction expects a ReferenceType or a ReturnadressType.");
      }
    }
  }

  @Override
  public void visitARETURN(ARETURN aRETURN) {
    if (!(this.stack().peek() instanceof ReferenceType)) {
      this.constraintViolated(
          aRETURN,
          "The 'objectref' at the stack top is not of a ReferenceType but of type '"
              + this.stack().peek()
              + "'.");
    }
    ReferenceType referenceType = (ReferenceType) this.stack().peek();
    this.referenceTypeIsInitialized(aRETURN, referenceType);
  }

  @Override
  public void visitLSHR(LSHR lSHR) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          lSHR,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lSHR,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitDASTORE(DASTORE dASTORE) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dASTORE,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
    this.processType2(dASTORE, this.stack().peek(1));
    if (this.stack().peek(2) != Type.NULL) {
      if (!(this.stack().peek(2) instanceof ArrayType)) {
        this.constraintViolated(
            dASTORE,
            "Stack next-to-next-to-top must be of type double[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
      if (((ArrayType) this.stack().peek(2)).getBasicType() != Type.DOUBLE) {
        this.constraintViolated(
            dASTORE,
            "Stack next-to-next-to-top must be of type double[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
    }
  }

  @Override
  public void visitFALOAD(FALOAD fALOAD) {
    this.processType2(fALOAD, this.stack().peek());
    if (this.stack().peek(1) != Type.NULL) {
      if (!(this.stack().peek(1) instanceof ArrayType)) {
        this.constraintViolated(
            fALOAD,
            "Stack next-to-top must be of type float[] but is '" + this.stack().peek(1) + "'.");
      }
      if (((ArrayType) this.stack().peek(1)).getBasicType() != Type.FLOAT) {
        this.constraintViolated(
            fALOAD,
            "Stack next-to-top must be of type float[] but is '" + this.stack().peek(1) + "'.");
      }
    }
  }

  @Override
  public void visitIFLT(IFLT fLT) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          fLT,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitLADD(LADD lADD) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lADD,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lADD,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitI2C(I2C i2C) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          i2C,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitFCMPG(FCMPG fCMPG) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fCMPG,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.FLOAT) {
      this.constraintViolated(
          fCMPG,
          "The value at the stack next-to-top is not of type 'float', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitLoadClass(LoadClass loadClass) {
    ObjectType objectType;
    VerificationResult verificationResult;
    if ((objectType = loadClass.getLoadClassType(this.cpg)) != null
        && (verificationResult =
                    VerifierFactory.getVerifier(objectType.getClassName())
                        .createVerificationResult2())
                .getStatus()
            != 1) {
      this.constraintViolated(
          (Instruction) loadClass,
          "Class '"
              + loadClass.getLoadClassType(this.cpg).getClassName()
              + "' is referenced, but cannot be loaded and resolved: '"
              + verificationResult
              + "'.");
    }
  }

  @Override
  public void visitFCMPL(FCMPL fCMPL) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fCMPL,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.FLOAT) {
      this.constraintViolated(
          fCMPL,
          "The value at the stack next-to-top is not of type 'float', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitTABLESWITCH(TABLESWITCH tABLESWITCH) {
    this.processType2(tABLESWITCH, this.stack().peek());
  }

  @Override
  public void visitDUP2(DUP2 dUP2) {
    if (this.stack().peek().getSize() != 2) {
      if (this.stack().peek(1).getSize() != 1) {
        this.constraintViolated(
            dUP2,
            "If stack top's size is 1, then stack next-to-top's size must also be 1. But it is '"
                + this.stack().peek(1)
                + "' of size '"
                + this.stack().peek(1).getSize()
                + "'.");
      }
    }
  }

  @Override
  public void visitInvokeInstruction(InvokeInstruction invokeInstruction) {}

  @Override
  public void visitI2D(I2D i2D) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          i2D,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitMONITORENTER(MONITORENTER mONITORENTER) {
    if (!(this.stack().peek() instanceof ReferenceType)) {
      this.constraintViolated(
          mONITORENTER,
          "The stack top should be of a ReferenceType, but is '" + this.stack().peek() + "'.");
    }
  }

  @Override
  public void visitIUSHR(IUSHR uSHR) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          uSHR,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          uSHR,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitIRETURN(IRETURN rETURN) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          rETURN,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitIXOR(IXOR xOR) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          xOR,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          xOR,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitLNEG(LNEG lNEG) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lNEG,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitFSTORE(FSTORE fSTORE) {}

  @Override
  public void visitILOAD(ILOAD lOAD) {}

  @Override
  public void visitFieldInstruction(FieldInstruction fieldInstruction) {
    Constant constant;
    if (!((constant = this.cpg.getConstant(fieldInstruction.getIndex()))
        instanceof ConstantFieldref)) {
      this.constraintViolated(
          fieldInstruction,
          "Index '"
              + fieldInstruction.getIndex()
              + "' should refer to a CONSTANT_Fieldref_info structure, but refers to '"
              + constant
              + "'.");
    }
    String text;
    Type type;
    VerificationResult verificationResult;
    if ((type = fieldInstruction.getType(this.cpg)) instanceof ObjectType
        && (verificationResult =
                    VerifierFactory.getVerifier(text = ((ObjectType) type).getClassName())
                        .createVerificationResult2())
                .getStatus()
            != 1) {
      this.constraintViolated(
          fieldInstruction,
          "Class '"
              + text
              + "' is referenced, but cannot be loaded and resolved: '"
              + verificationResult
              + "'.");
    }
  }

  @Override
  public void visitANEWARRAY(ANEWARRAY aNEWARRAY) {
    if (!this.stack().peek().equals(Type.INT)) {
      this.constraintViolated(
          aNEWARRAY,
          "The 'count' at the stack top is not of type '"
              + Type.INT
              + "' but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitCHECKCAST(CHECKCAST cHECKCAST) {
    Type type;
    if (!((type = this.stack().peek(0)) instanceof ReferenceType)) {
      this.constraintViolated(
          cHECKCAST, "The 'objectref' is not of a ReferenceType but of type " + type + ".");
    }
    Constant constant;
    if (!((constant = this.cpg.getConstant(cHECKCAST.getIndex())) instanceof ConstantClass)) {
      this.constraintViolated(
          cHECKCAST, "The Constant at 'index' is not a ConstantClass, but '" + constant + "'.");
    }
  }

  @Override
  public void visitLALOAD(LALOAD lALOAD) {
    this.processType2(lALOAD, this.stack().peek());
    if (this.stack().peek(1) != Type.NULL) {
      if (!(this.stack().peek(1) instanceof ArrayType)) {
        this.constraintViolated(
            lALOAD,
            "Stack next-to-top must be of type long[] but is '" + this.stack().peek(1) + "'.");
      }
      if (((ArrayType) this.stack().peek(1)).getBasicType() != Type.LONG) {
        this.constraintViolated(
            lALOAD,
            "Stack next-to-top must be of type long[] but is '" + this.stack().peek(1) + "'.");
      }
    }
  }

  @Override
  public void visitISUB(ISUB sUB) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          sUB,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          sUB,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitFREM(FREM fREM) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fREM,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.FLOAT) {
      this.constraintViolated(
          fREM,
          "The value at the stack next-to-top is not of type 'float', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitSIPUSH(SIPUSH sIPUSH) {}

  public boolean arrayrefOfArrayType(Instruction instruction, Type type) {
    if (!(type instanceof ArrayType) && !type.equals(Type.NULL)) {
      this.constraintViolated(
          instruction, "The 'arrayref' does not refer to an array but is of type " + type + ".");
    }
    return type instanceof ArrayType;
  }

  @Override
  public void visitDCMPL(DCMPL dCMPL) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dCMPL,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.DOUBLE) {
      this.constraintViolated(
          dCMPL,
          "The value at the stack next-to-top is not of type 'double', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitBIPUSH(BIPUSH bIPUSH) {}

  @Override
  public void visitIADD(IADD aDD) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          aDD,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          aDD,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitACONST_NULL(ACONST_NULL aCONST_NULL) {}

  @Override
  public void visitLDIV(LDIV lDIV) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lDIV,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lDIV,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitFRETURN(FRETURN fRETURN) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fRETURN,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  public void setConstantPoolGen(ConstantPoolGen constantPoolGen) {
    this.cpg = constantPoolGen;
  }

  @Override
  public void visitIINC(IINC iNC) {
    if (this.locals().getLocals()
        <= (iNC.getType(this.cpg).getSize() == 1 ? iNC.getIndex() : iNC.getIndex() + 1)) {
      this.constraintViolated(
          iNC, "The 'index' is not a valid index into the local variable array.");
    }
    this.processType2(iNC, this.locals().get(iNC.getIndex()));
  }

  @Override
  public void visitStackConsumer(StackConsumer stackConsumer) {
    this._visitStackAccessor((Instruction) stackConsumer);
  }

  public InstConstraintVisitor() {
    this.cpg = null;
    this.mg = null;
  }

  @Override
  public void visitIAND(IAND aND) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          aND,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          aND,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitALOAD(ALOAD aLOAD) {}

  public void _visitStackAccessor(Instruction instruction) {
    int value;
    if ((value = instruction.consumeStack(this.cpg)) > this.stack().slotsUsed()) {
      this.constraintViolated(
          instruction,
          "Cannot consume "
              + value
              + " stack slots: only "
              + this.stack().slotsUsed()
              + " slot(s) left on stack!\nStack:\n"
              + this.stack());
    }
    int value2;
    if ((value2 = instruction.produceStack(this.cpg) - instruction.consumeStack(this.cpg))
            + this.stack().slotsUsed()
        > this.stack().maxStack()) {
      this.constraintViolated(
          instruction,
          "Cannot produce "
              + value2
              + " stack slots: only "
              + (this.stack().maxStack() - this.stack().slotsUsed())
              + " free stack slot(s) left.\nStack:\n"
              + this.stack());
    }
  }

  @Override
  public void visitMULTIANEWARRAY(MULTIANEWARRAY mULTIANEWARRAY) {
    short dimensions = mULTIANEWARRAY.getDimensions();
    int value;
    for (int value2 = value = 0; value2 < dimensions; value2 = ++value) {
      if (this.stack().peek(value) != Type.INT) {
        this.constraintViolated(
            mULTIANEWARRAY,
            "The '" + dimensions + "' upper stack types should be 'int' but aren't.");
      }
    }
  }

  @Override
  public void visitLSHL(LSHL lSHL) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          lSHL,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lSHL,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitISTORE(ISTORE sTORE) {}

  @Override
  public void visitLDC(LDC lDC) {
    Constant constant;
    if (!((constant = this.cpg.getConstant(lDC.getIndex())) instanceof ConstantInteger)
        && !(constant instanceof ConstantFloat)
        && !(constant instanceof ConstantString)) {
      this.constraintViolated(
          lDC,
          "Referenced constant should be a CONSTANT_Integer, a CONSTANT_Float or a CONSTANT_String,"
              + " but is '"
              + constant
              + "'.");
    }
  }

  @Override
  public void visitBALOAD(BALOAD bALOAD) {
    Type type = this.stack().peek(1);
    Type type2 = this.stack().peek(0);
    this.processType2(bALOAD, type2);
    if (this.arrayrefOfArrayType(bALOAD, type)
        && !((ArrayType) type).getElementType().equals(Type.BOOLEAN)
        && !((ArrayType) type).getElementType().equals(Type.BYTE)) {
      this.constraintViolated(
          bALOAD,
          "The 'arrayref' does not refer to an array with elements of a Type.BYTE or Type.BOOLEAN"
              + " but to an array of '"
              + ((ArrayType) type).getElementType()
              + "'.");
    }
  }

  public void processType(Instruction instruction, Type type) {
    if (!type.equals(Type.INT)) {
      this.constraintViolated(
          instruction, "The 'value' is not of type int but of type " + type + ".");
    }
  }

  @Override
  public void visitSWAP(SWAP sWAP) {
    if (this.stack().peek().getSize() != 1) {
      this.constraintViolated(
          sWAP,
          "The value at the stack top is not of size '1', but of size '"
              + this.stack().peek().getSize()
              + "'.");
    }
    if (this.stack().peek(1).getSize() != 1) {
      this.constraintViolated(
          sWAP,
          "The value at the stack next-to-top is not of size '1', but of size '"
              + this.stack().peek(1).getSize()
              + "'.");
    }
  }

  @Override
  public void visitICONST(ICONST cONST) {}

  @Override
  public void visitGETFIELD(GETFIELD gETFIELD) {
    Field field;
    Field field2;
    try {
      Type type;
      if (!((type = this.stack().peek()) instanceof ObjectType) && type != Type.NULL) {
        this.constraintViolated(
            gETFIELD,
            "Stack top should be an object reference that's not an array reference, but is '"
                + type
                + "'.");
      }
      String fieldName = gETFIELD.getFieldName(this.cpg);
      JavaClass javaClass;
      Field[] fields =
          (javaClass = Repository.lookupClass(gETFIELD.createObjectType(this.cpg).getClassName()))
              .getFields();
      Field field3 = null;
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= fields.length) {
          field2 = field3;
          break;
        }
        if (fields[value].getText().equals(fieldName)) {
          Type type2 = Type.getType(fields[value].getText2());
          Type type3 = gETFIELD.getType(this.cpg);
          if (type2.equals(type3)) {
            field2 = field3 = fields[value];
            break;
          }
        }
        value2 = ++value;
      }
      if (field2 == null) {
        JavaClass[] javaClasss = javaClass.createJavaClasss();
        label76:
        {
          int value3;
          label75:
          for (int value4 = value3 = 0; value4 < javaClasss.length; value4 = ++value3) {
            Field[] fields2 = javaClasss[value3].getFields();
            int value5;
            for (int value6 = value5 = 0; value6 < fields2.length; value6 = ++value5) {
              if (fields2[value5].getText().equals(fieldName)) {
                Type type4 = Type.getType(fields2[value5].getText2());
                Type type5 = gETFIELD.getType(this.cpg);
                if (type4.equals(type5)) {
                  if (((field3 = fields2[value5]).getAccess_flags2() & 5) == 0) {
                    field = field3 = null;
                    break label76;
                  }
                  break label75;
                }
              }
            }
          }
          field = field3;
        }
        if (field == null) {
          throw new AssertionViolatedException(("Field '" + fieldName + "' not found?!?"));
        }
      }
      if (field3.isEnabled13()) {
        ObjectType objectType = gETFIELD.createObjectType(this.cpg);
        ObjectType objectType2 = new ObjectType(this.mg.getClass_name());
        if (objectType.equals(objectType2) || objectType2.matchesObjectType(objectType)) {
          Type type6;
          if ((type6 = this.stack().peek()) == Type.NULL) {
            return;
          }
          if (!(type6 instanceof ObjectType)) {
            this.constraintViolated(
                gETFIELD,
                "The 'objectref' must refer to an object that's not an array. Found instead: '"
                    + type6
                    + "'.");
          }
          ObjectType objectType3;
          if (!(objectType3 = (ObjectType) type6).equals(objectType2)
              && !objectType3.matchesObjectType(objectType2)) {}
        }
      }
      if (field3.isEnabled11()) {
        this.constraintViolated(
            gETFIELD, "Referenced field '" + field3 + "' is static which it shouldn't be.");
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitIFLE(IFLE fLE) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          fLE,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitL2D(L2D l2D) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          l2D,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitLOOKUPSWITCH(LOOKUPSWITCH lOOKUPSWITCH) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          lOOKUPSWITCH,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitMONITOREXIT(MONITOREXIT mONITOREXIT) {
    if (!(this.stack().peek() instanceof ReferenceType)) {
      this.constraintViolated(
          mONITOREXIT,
          "The stack top should be of a ReferenceType, but is '" + this.stack().peek() + "'.");
    }
  }

  @Override
  public void visitIF_ICMPEQ(IF_ICMPEQ f_ICMPEQ) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          f_ICMPEQ,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          f_ICMPEQ,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitIMUL(IMUL mUL) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          mUL,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          mUL,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  public LocalVariables locals() {
    return this.frame.getLocals();
  }

  @Override
  public void visitDUP_X2(DUP_X2 dUP_X2) {
    if (this.stack().peek().getSize() != 1) {
      this.constraintViolated(
          dUP_X2,
          "Stack top type must be of size 1, but is '"
              + this.stack().peek()
              + "' of size '"
              + this.stack().peek().getSize()
              + "'.");
    }
    if (this.stack().peek(1).getSize() != 2) {
      if (this.stack().peek(2).getSize() != 1) {
        this.constraintViolated(
            dUP_X2,
            "If stack top's size is 1 and stack next-to-top's size is 1, stack"
                + " next-to-next-to-top's size must also be 1, but is: '"
                + this.stack().peek(2)
                + "' of size '"
                + this.stack().peek(2).getSize()
                + "'.");
      }
    }
  }

  @Override
  public void visitIF_ICMPGE(IF_ICMPGE f_ICMPGE) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          f_ICMPGE,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          f_ICMPGE,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitIMPDEP1(IMPDEP1 mPDEP1) {
    throw new AssertionViolatedException(
        "In this JustIce verification pass there should not occur an illegal instruction such as"
            + " IMPDEP1.");
  }

  @Override
  public void visitBREAKPOINT(BREAKPOINT bREAKPOINT) {
    throw new AssertionViolatedException(
        "In this JustIce verification pass there should not occur an illegal instruction such as"
            + " BREAKPOINT.");
  }

  @Override
  public void visitAASTORE(AASTORE aASTORE) {
    try {
      Type type = this.stack().peek(2);
      Type type2 = this.stack().peek(1);
      Type type3 = this.stack().peek(0);
      this.processType2(aASTORE, type2);
      if (!(type3 instanceof ReferenceType)) {
        this.constraintViolated(
            aASTORE, "The 'value' is not of a ReferenceType but of type " + type3 + ".");
      }
      if (this.arrayrefOfArrayType(aASTORE, type)) {
        if (!(((ArrayType) type).getElementType() instanceof ReferenceType)) {
          this.constraintViolated(
              aASTORE,
              "The 'arrayref' does not refer to an array with elements of a ReferenceType but to an"
                  + " array of "
                  + ((ArrayType) type).getElementType()
                  + ".");
        }
        if (!((ReferenceType) type3)
            .matchesType2((ReferenceType) ((ArrayType) type).getElementType())) {
          this.constraintViolated(
              aASTORE,
              "The type of 'value' ('"
                  + type3
                  + "') is not assignment compatible to the components of the array 'arrayref'"
                  + " refers to. ('"
                  + ((ArrayType) type).getElementType()
                  + "')");
          return;
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitINVOKEINTERFACE(INVOKEINTERFACE nVOKEINTERFACE) {
    int value;
    if ((value = nVOKEINTERFACE.getCount()) == 0) {
      this.constraintViolated(nVOKEINTERFACE, "The 'count' argument must not be 0.");
    }
    Type type;
    VerificationResult verificationResult;
    String text;
    if ((type = nVOKEINTERFACE.getType(this.cpg)) instanceof ObjectType
        && (verificationResult =
                    VerifierFactory.getVerifier(text = ((ObjectType) type).getClassName())
                        .createVerificationResult2())
                .getStatus()
            != 1) {
      this.constraintViolated(
          nVOKEINTERFACE,
          "Class '"
              + text
              + "' is referenced, but cannot be loaded and resolved: '"
              + verificationResult
              + "'.");
    }
    int value2;
    int value3;
    Type[] types;
    for (int value4 =
            value2 = (value3 = (types = nVOKEINTERFACE.getArgumentTypes(this.cpg)).length) - 1;
        value4 >= 0;
        value4 = --value2) {
      Type type2 = this.stack().peek(value3 - 1 - value2);
      Type type3;
      if ((type3 = types[value2]) == Type.BOOLEAN
          || type3 == Type.BYTE
          || type3 == Type.CHAR
          || type3 == Type.SHORT) {
        type3 = Type.INT;
      }
      if (!type2.equals(type3)) {
        if (type2 instanceof ReferenceType && type3 instanceof ReferenceType) {
          ReferenceType referenceType = (ReferenceType) type2;
          this.referenceTypeIsInitialized(nVOKEINTERFACE, referenceType);
        } else {
          this.constraintViolated(
              nVOKEINTERFACE,
              "Expecting a '" + type3 + "' but found a '" + type2 + "' on the stack.");
        }
      }
    }
    Type type4;
    if ((type4 = this.stack().peek(value3)) != Type.NULL) {
      if (!(type4 instanceof ReferenceType)) {
        this.constraintViolated(
            nVOKEINTERFACE,
            "Expecting a reference type as 'objectref' on the stack, not a '" + type4 + "'.");
      }
      this.referenceTypeIsInitialized(nVOKEINTERFACE, (ReferenceType) type4);
      if (!(type4 instanceof ObjectType)) {
        if (!(type4 instanceof ArrayType)) {
          this.constraintViolated(
              nVOKEINTERFACE,
              "Expecting an ObjectType as 'objectref' on the stack, not a '" + type4 + "'.");
        }
      }
      int value5 = 1;
      int value6;
      for (int value7 = value6 = 0; value7 < value3; value7 = value6) {
        Type type5 = types[value6];
        int value8 = value5;
        value6++;
        value5 = value8 + type5.getSize();
      }
      if (value != value5) {
        this.constraintViolated(
            nVOKEINTERFACE,
            "The 'count' argument should probably read '" + value5 + "' but is '" + value + "'.");
      }
    }
  }

  @Override
  public void visitLMUL(LMUL lMUL) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lMUL,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lMUL,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitDSUB(DSUB dSUB) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dSUB,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.DOUBLE) {
      this.constraintViolated(
          dSUB,
          "The value at the stack next-to-top is not of type 'double', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitINVOKESPECIAL(INVOKESPECIAL nVOKESPECIAL) {
    Type type;
    try {
      if (nVOKESPECIAL.getText4(this.cpg).equals("<init>")
          && !(this.stack().peek(nVOKESPECIAL.getArgumentTypes(this.cpg).length)
              instanceof UninitializedObjectType)) {
        this.constraintViolated(
            nVOKESPECIAL,
            "Possibly initializing object twice. A valid instruction sequence must not have an"
                + " uninitialized object on the operand stack or in a local variable during a"
                + " backwards branch, or in a local variable in code protected by an exception"
                + " handler. Please see The Java Virtual Machine Specification, Second Edition,"
                + " 4.9.4 (pages 147 and 148) for details.");
      }
      String text;
      VerificationResult verificationResult;
      Type type2;
      if ((type2 = nVOKESPECIAL.getType(this.cpg)) instanceof ObjectType
          && (verificationResult =
                      VerifierFactory.getVerifier(text = ((ObjectType) type2).getClassName())
                          .createVerificationResult2())
                  .getStatus()
              != 1) {
        this.constraintViolated(
            nVOKESPECIAL,
            "Class '"
                + text
                + "' is referenced, but cannot be loaded and resolved: '"
                + verificationResult
                + "'.");
      }
      int value;
      int value2;
      Type[] types;
      for (int value3 =
              value2 = (value = (types = nVOKESPECIAL.getArgumentTypes(this.cpg)).length) - 1;
          value3 >= 0;
          value3 = --value2) {
        Type type3 = this.stack().peek(value - 1 - value2);
        Type type4;
        if ((type4 = types[value2]) == Type.BOOLEAN
            || type4 == Type.BYTE
            || type4 == Type.CHAR
            || type4 == Type.SHORT) {
          type4 = Type.INT;
        }
        if (!type3.equals(type4)) {
          if (type3 instanceof ReferenceType && type4 instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type3;
            ReferenceType referenceType2 = (ReferenceType) type4;
            if (!referenceType.matchesType2(referenceType2)) {
              this.constraintViolated(
                  nVOKESPECIAL,
                  "Expecting a '"
                      + type4
                      + "' but found a '"
                      + type3
                      + "' on the stack (which is not assignment compatible).");
            }
            this.referenceTypeIsInitialized(nVOKESPECIAL, referenceType);
          } else {
            this.constraintViolated(
                nVOKESPECIAL,
                "Expecting a '" + type4 + "' but found a '" + type3 + "' on the stack.");
          }
        }
      }
      Type type5;
      if ((type5 = this.stack().peek(value)) != Type.NULL) {
        if (!(type5 instanceof ReferenceType)) {
          this.constraintViolated(
              nVOKESPECIAL,
              "Expecting a reference type as 'objectref' on the stack, not a '" + type5 + "'.");
        }
        String text2;
        INVOKESPECIAL nVOKESPECIAL2;
        if (!nVOKESPECIAL.getText4(this.cpg).equals("<init>")) {
          label64:
          {
            this.referenceTypeIsInitialized(nVOKESPECIAL, (ReferenceType) type5);
            if (!(type5 instanceof ObjectType)) {
              if (!(type5 instanceof ArrayType)) {
                type = type5;
                this.constraintViolated(
                    nVOKESPECIAL,
                    "Expecting an ObjectType as 'objectref' on the stack, not a '" + type5 + "'.");
                break label64;
              }
              type5 = GENERIC_ARRAY;
            }
            type = type5;
          }
          text2 = ((ObjectType) type).getClassName();
          nVOKESPECIAL2 = nVOKESPECIAL;
        } else {
          if (!(type5 instanceof UninitializedObjectType)) {
            this.constraintViolated(
                nVOKESPECIAL,
                "Expecting an UninitializedObjectType as 'objectref' on the stack, not a '"
                    + type5
                    + "'. Otherwise, you couldn't invoke a method since an array has no methods"
                    + " (not to speak of a return address).");
          }
          text2 = ((UninitializedObjectType) type5).getInitialized().getClassName();
          nVOKESPECIAL2 = nVOKESPECIAL;
        }
        String text22 = nVOKESPECIAL2.getText2(this.cpg);
        if (!Repository.matchesText(text2, text22)) {
          this.constraintViolated(
              nVOKESPECIAL,
              "The 'objref' item '" + type5 + "' does not implement '" + text22 + "' as expected.");
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitFLOAD(FLOAD fLOAD) {}

  @Override
  public void visitPUTFIELD(PUTFIELD pUTFIELD) {
    Field field;
    try {
      Type type;
      if (!((type = this.stack().peek(1)) instanceof ObjectType) && type != Type.NULL) {
        this.constraintViolated(
            pUTFIELD,
            "Stack next-to-top should be an object reference that's not an array reference, but is"
                + " '"
                + type
                + "'.");
      }
      String fieldName = pUTFIELD.getFieldName(this.cpg);
      Field[] fields =
          Repository.lookupClass(pUTFIELD.createObjectType(this.cpg).getClassName()).getFields();
      Field field2 = null;
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= fields.length) {
          field = field2;
          break;
        }
        if (fields[value].getText().equals(fieldName)) {
          Type type2 = Type.getType(fields[value].getText2());
          Type type3 = pUTFIELD.getType(this.cpg);
          if (type2.equals(type3)) {
            field = field2 = fields[value];
            break;
          }
        }
        value2 = ++value;
      }
      if (field == null) {
        throw new AssertionViolatedException("Field not found?!?");
      }
      Type type4 = this.stack().peek();
      Type type5;
      Type type6 = type5 = Type.getType(field2.getText2());
      if (type6 == Type.BOOLEAN
          || type6 == Type.BYTE
          || type6 == Type.CHAR
          || type6 == Type.SHORT) {
        type6 = Type.INT;
      }
      if (type5 instanceof ReferenceType) {
        ReferenceType referenceType;
        if (type4 instanceof ReferenceType) {
          ReferenceType referenceType2 = (ReferenceType) type4;
          referenceType = referenceType2;
          this.referenceTypeIsInitialized(pUTFIELD, referenceType2);
        } else {
          this.constraintViolated(
              pUTFIELD,
              "The stack top type '" + type4 + "' is not of a reference type as expected.");
          referenceType = (ReferenceType) (Object) null;
        }
        if (!referenceType.matchesType2(type6)) {
          this.constraintViolated(
              pUTFIELD,
              "The stack top type '"
                  + type4
                  + "' is not assignment compatible with '"
                  + type6
                  + "'.");
        }
      } else if (type6 != type4) {
        this.constraintViolated(
            pUTFIELD,
            "The stack top type '" + type4 + "' is not of type '" + type6 + "' as expected.");
      }
      if (field2.isEnabled13()) {
        ObjectType objectType = pUTFIELD.createObjectType(this.cpg);
        ObjectType objectType2 = new ObjectType(this.mg.getClass_name());
        if (objectType.equals(objectType2) || objectType2.matchesObjectType(objectType)) {
          Type type7;
          if ((type7 = this.stack().peek(1)) == Type.NULL) {
            return;
          }
          if (!(type7 instanceof ObjectType)) {
            this.constraintViolated(
                pUTFIELD,
                "The 'objectref' must refer to an object that's not an array. Found instead: '"
                    + type7
                    + "'.");
          }
          ObjectType objectType3;
          if (!(objectType3 = (ObjectType) type7).equals(objectType2)
              && !objectType3.matchesObjectType(objectType2)) {
            this.constraintViolated(
                pUTFIELD,
                "The referenced field has the ACC_PROTECTED modifier, and it's a member of the"
                    + " current class or a superclass of the current class. However, the referenced"
                    + " object type '"
                    + this.stack().peek()
                    + "' is not the current class or a subclass of the current class.");
          }
        }
      }
      if (field2.isEnabled11()) {
        this.constraintViolated(
            pUTFIELD, "Referenced field '" + field2 + "' is static which it shouldn't be.");
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitCASTORE(CASTORE cASTORE) {
    Type type = this.stack().peek(2);
    Type type2 = this.stack().peek(1);
    Type type3 = this.stack().peek(0);
    this.processType2(cASTORE, type2);
    this.processType(cASTORE, type3);
    if (this.arrayrefOfArrayType(cASTORE, type)
        && !((ArrayType) type).getElementType().equals(Type.CHAR)) {
      this.constraintViolated(
          cASTORE,
          "The 'arrayref' does not refer to an array with elements of type char but to an array of"
              + " type "
              + ((ArrayType) type).getElementType()
              + ".");
    }
  }

  @Override
  public void visitLUSHR(LUSHR lUSHR) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          lUSHR,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lUSHR,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitF2L(F2L f2L) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          f2L,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitD2F(D2F d2F) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          d2F,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitIF_ACMPNE(IF_ACMPNE f_ACMPNE) {
    if (!(this.stack().peek() instanceof ReferenceType)) {
      this.constraintViolated(
          f_ACMPNE,
          "The value at the stack top is not of a ReferenceType, but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (!(this.stack().peek(1) instanceof ReferenceType)) {
      this.constraintViolated(
          f_ACMPNE,
          "The value at the stack next-to-top is not of a ReferenceType, but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitF2I(F2I f2I) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          f2I,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitDADD(DADD dADD) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dADD,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.DOUBLE) {
      this.constraintViolated(
          dADD,
          "The value at the stack next-to-top is not of type 'double', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitIFNULL(IFNULL fNULL) {
    if (!(this.stack().peek() instanceof ReferenceType)) {
      this.constraintViolated(
          fNULL,
          "The value at the stack top is not of a ReferenceType, but of type '"
              + this.stack().peek()
              + "'.");
    }
    this.referenceTypeIsInitialized(fNULL, (ReferenceType) this.stack().peek());
  }

  @Override
  public void visitIFNONNULL(IFNONNULL fNONNULL) {
    if (!(this.stack().peek() instanceof ReferenceType)) {
      this.constraintViolated(
          fNONNULL,
          "The value at the stack top is not of a ReferenceType, but of type '"
              + this.stack().peek()
              + "'.");
    }
    this.referenceTypeIsInitialized(fNONNULL, (ReferenceType) this.stack().peek());
  }

  @Override
  public void visitNEW(NEW nEW) {
    Type type;
    if (!((type = nEW.getType(this.cpg)) instanceof ReferenceType)) {
      throw new AssertionViolatedException("NEW.getType() returning a non-reference type?!");
    }
    if (!(type instanceof ObjectType)) {
      this.constraintViolated(
          nEW, "Expecting a class type (ObjectType) to work on. Found: '" + type + "'.");
    }
    ObjectType objectType;
    if (!(objectType = (ObjectType) type).isClass_name2()) {
      this.constraintViolated(
          nEW, "Expecting a class type (ObjectType) to work on. Found: '" + objectType + "'.");
    }
  }

  @Override
  public void visitFADD(FADD fADD) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fADD,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.FLOAT) {
      this.constraintViolated(
          fADD,
          "The value at the stack next-to-top is not of type 'float', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitIF_ICMPLE(IF_ICMPLE f_ICMPLE) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          f_ICMPLE,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          f_ICMPLE,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitJSR_W(JSR_W jSR_W) {}

  @Override
  public void visitCPInstruction(CPInstruction cPInstruction) {
    int value;
    if ((value = cPInstruction.getIndex()) < 0 || value >= this.cpg.getSize()) {
      throw new AssertionViolatedException(
          ("Huh?! Constant pool index of instruction '"
              + cPInstruction
              + "' illegal? Pass 3a should have checked this!"));
    }
  }

  @Override
  public void visitLOR(LOR lOR) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lOR,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lOR,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitLSUB(LSUB lSUB) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lSUB,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lSUB,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitFDIV(FDIV fDIV) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fDIV,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.FLOAT) {
      this.constraintViolated(
          fDIV,
          "The value at the stack next-to-top is not of type 'float', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitDUP2_X2(DUP2_X2 dUP2_X2) {
    InstConstraintVisitor instConstraintVisitor;
    if (this.stack().peek(0).getSize() == 2) {
      if (this.stack().peek(1).getSize() == 2) {
        return;
      }
      if (this.stack().peek(2).getSize() == 1) {
        return;
      }
      StringBuilder stringBuilder =
          new StringBuilder()
              .append(
                  "If stack top's size is 2 and stack-next-to-top's size is 1, then stack"
                      + " next-to-next-to-top's size must also be 1. But it is '");
      instConstraintVisitor = this;
      this.constraintViolated(
          dUP2_X2,
          stringBuilder
              .append(this.stack().peek(2))
              .append("' of size '")
              .append(this.stack().peek(2).getSize())
              .append("'.")
              .toString());
    } else {
      if (this.stack().peek(1).getSize() == 1) {
        if (this.stack().peek(2).getSize() == 2) {
          return;
        }
        if (this.stack().peek(3).getSize() == 1) {
          return;
        }
      }
      instConstraintVisitor = this;
    }
    instConstraintVisitor.constraintViolated(
        dUP2_X2,
        "The operand sizes on the stack do not match any of the four forms of usage of this"
            + " instruction.");
  }

  @Override
  public void visitLRETURN(LRETURN lRETURN) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lRETURN,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitFASTORE(FASTORE fASTORE) {
    if (this.stack().peek() != Type.FLOAT) {
      this.constraintViolated(
          fASTORE,
          "The value at the stack top is not of type 'float', but of type '"
              + this.stack().peek()
              + "'.");
    }
    this.processType2(fASTORE, this.stack().peek(1));
    if (this.stack().peek(2) != Type.NULL) {
      if (!(this.stack().peek(2) instanceof ArrayType)) {
        this.constraintViolated(
            fASTORE,
            "Stack next-to-next-to-top must be of type float[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
      if (((ArrayType) this.stack().peek(2)).getBasicType() != Type.FLOAT) {
        this.constraintViolated(
            fASTORE,
            "Stack next-to-next-to-top must be of type float[] but is '"
                + this.stack().peek(2)
                + "'.");
      }
    }
  }

  @Override
  public void visitStackInstruction(StackInstruction stackInstruction) {
    this._visitStackAccessor(stackInstruction);
  }

  @Override
  public void visitIDIV(IDIV dIV) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          dIV,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          dIV,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitDMUL(DMUL dMUL) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dMUL,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.DOUBLE) {
      this.constraintViolated(
          dMUL,
          "The value at the stack next-to-top is not of type 'double', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitLREM(LREM lREM) {
    if (this.stack().peek() != Type.LONG) {
      this.constraintViolated(
          lREM,
          "The value at the stack top is not of type 'long', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.LONG) {
      this.constraintViolated(
          lREM,
          "The value at the stack next-to-top is not of type 'long', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitIOR(IOR oR) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          oR,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          oR,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitISHR(ISHR sHR) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          sHR,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          sHR,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  public void processType2(Instruction instruction, Type type) {
    if (!type.equals(Type.INT)) {
      this.constraintViolated(
          instruction, "The 'index' is not of type int but of type " + type + ".");
    }
  }

  @Override
  public void visitDCMPG(DCMPG dCMPG) {
    if (this.stack().peek() != Type.DOUBLE) {
      this.constraintViolated(
          dCMPG,
          "The value at the stack top is not of type 'double', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.DOUBLE) {
      this.constraintViolated(
          dCMPG,
          "The value at the stack next-to-top is not of type 'double', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  public void setMethodGen(MethodGen methodGen) {
    this.mg = methodGen;
  }

  @Override
  public void visitISHL(ISHL sHL) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          sHL,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          sHL,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }

  @Override
  public void visitDUP(DUP dUP) {
    if (this.stack().peek().getSize() != 1) {
      this.constraintViolated(
          dUP,
          "Won't DUP type on stack top '"
              + this.stack().peek()
              + "' because it must occupy exactly one slot, not '"
              + this.stack().peek().getSize()
              + "'.");
    }
  }

  @Override
  public void visitASTORE(ASTORE aSTORE) {
    if (!(this.stack().peek() instanceof ReferenceType)
        && !(this.stack().peek() instanceof ReturnaddressType)) {
      this.constraintViolated(
          aSTORE,
          "The 'objectref' is not of a ReferenceType or of ReturnaddressType but of "
              + this.stack().peek()
              + ".");
    }
  }

  @Override
  public void visitRETURN(RETURN rETURN) {
    if (this.mg.getName().equals("<init>")
        && Frame._this != null
        && !this.mg.getClass_name().equals(Type.OBJECT.getClassName())) {
      this.constraintViolated(
          rETURN, "Leaving a constructor that itself did not call a constructor.");
    }
  }

  @Override
  public void visitINEG(INEG nEG) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          nEG,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitIFEQ(IFEQ fEQ) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          fEQ,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitIFGT(IFGT fGT) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          fGT,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitRET(RET rET) {
    if (!(this.locals().get(rET.getIndex()) instanceof ReturnaddressType)) {
      this.constraintViolated(
          rET, "Expecting a ReturnaddressType in local variable " + rET.getIndex() + ".");
    }
    if (this.locals().get(rET.getIndex()) == ReturnaddressType.NO_TARGET) {
      throw new AssertionViolatedException("Oops: RET expecting a target!");
    }
  }

  @Override
  public void visitI2F(I2F i2F) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          i2F,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
  }

  @Override
  public void visitIF_ICMPLT(IF_ICMPLT f_ICMPLT) {
    if (this.stack().peek() != Type.INT) {
      this.constraintViolated(
          f_ICMPLT,
          "The value at the stack top is not of type 'int', but of type '"
              + this.stack().peek()
              + "'.");
    }
    if (this.stack().peek(1) != Type.INT) {
      this.constraintViolated(
          f_ICMPLT,
          "The value at the stack next-to-top is not of type 'int', but of type '"
              + this.stack().peek(1)
              + "'.");
    }
  }
}
