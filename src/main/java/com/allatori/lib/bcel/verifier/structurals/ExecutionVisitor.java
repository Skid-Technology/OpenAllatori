package com.allatori.lib.bcel.verifier.structurals;

import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantDouble;
import com.allatori.lib.bcel.classfile.ConstantFloat;
import com.allatori.lib.bcel.classfile.ConstantInteger;
import com.allatori.lib.bcel.classfile.ConstantLong;
import com.allatori.lib.bcel.classfile.ConstantString;
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
import com.allatori.lib.bcel.generic.CALOAD;
import com.allatori.lib.bcel.generic.CASTORE;
import com.allatori.lib.bcel.generic.CHECKCAST;
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
import com.allatori.lib.bcel.generic.MONITORENTER;
import com.allatori.lib.bcel.generic.MONITOREXIT;
import com.allatori.lib.bcel.generic.MULTIANEWARRAY;
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
import com.allatori.lib.bcel.generic.ReturnaddressType;
import com.allatori.lib.bcel.generic.SALOAD;
import com.allatori.lib.bcel.generic.SASTORE;
import com.allatori.lib.bcel.generic.SIPUSH;
import com.allatori.lib.bcel.generic.SWAP;
import com.allatori.lib.bcel.generic.TABLESWITCH;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.generic.Visitor;

public class ExecutionVisitor extends EmptyVisitor implements Visitor {

  public Frame frame = null;

  public ConstantPoolGen cpg = null;

  @Override
  public void visitLSTORE(LSTORE lSTORE) {
    this.locals().set(lSTORE.getIndex(), this.stack().pop());
    this.locals().set(lSTORE.getIndex() + 1, Type.UNKNOWN);
  }

  @Override
  public void visitLASTORE(LASTORE lASTORE) {
    this.stack().pop();
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitIF_ICMPGE(IF_ICMPGE f_ICMPGE) {
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitBASTORE(BASTORE bASTORE) {
    this.stack().pop();
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitANEWARRAY(ANEWARRAY aNEWARRAY) {
    this.stack().pop();
    this.stack().push(new ArrayType(aNEWARRAY.getType(this.cpg), 1));
  }

  @Override
  public void visitINVOKEINTERFACE(INVOKEINTERFACE nVOKEINTERFACE) {
    this.stack().pop();
    int value = 0;
    for (int value2 = value;
        value2 < nVOKEINTERFACE.getArgumentTypes(this.cpg).length;
        value2 = value) {
      value++;
      this.stack().pop();
    }
    if (nVOKEINTERFACE.getType2(this.cpg) != Type.VOID) {
      Type type;
      if ((type = nVOKEINTERFACE.getType2(this.cpg)).equals(Type.BOOLEAN)
          || type.equals(Type.CHAR)
          || type.equals(Type.BYTE)
          || type.equals(Type.SHORT)) {
        type = Type.INT;
      }
      this.stack().push(type);
    }
  }

  @Override
  public void visitNEWARRAY(NEWARRAY nEWARRAY) {
    this.stack().pop();
    this.stack().push(nEWARRAY.getType());
  }

  @Override
  public void visitIRETURN(IRETURN rETURN) {
    this.stack().pop();
  }

  @Override
  public void visitFSTORE(FSTORE fSTORE) {
    this.locals().set(fSTORE.getIndex(), this.stack().pop());
  }

  @Override
  public void visitI2B(I2B i2B) {
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitFCMPL(FCMPL fCMPL) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitINSTANCEOF(INSTANCEOF nSTANCEOF) {
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitIFLE(IFLE fLE) {
    this.stack().pop();
  }

  @Override
  public void visitMONITORENTER(MONITORENTER mONITORENTER) {
    this.stack().pop();
  }

  @Override
  public void visitI2C(I2C i2C) {
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitIF_ICMPNE(IF_ICMPNE f_ICMPNE) {
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitISTORE(ISTORE sTORE) {
    this.locals().set(sTORE.getIndex(), this.stack().pop());
  }

  @Override
  public void visitIDIV(IDIV dIV) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitI2F(I2F i2F) {
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitLADD(LADD lADD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitLALOAD(LALOAD lALOAD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitDLOAD(DLOAD dLOAD) {
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitIMUL(IMUL mUL) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitFDIV(FDIV fDIV) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitBIPUSH(BIPUSH bIPUSH) {
    this.stack().push(Type.INT);
  }

  @Override
  public void visitSALOAD(SALOAD sALOAD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitLUSHR(LUSHR lUSHR) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitDSUB(DSUB dSUB) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitL2I(L2I l2I) {
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitPOP(POP pOP) {
    this.stack().pop();
  }

  @Override
  public void visitSWAP(SWAP sWAP) {
    Type type = this.stack().pop();
    Type type2 = this.stack().pop();
    this.stack().push(type);
    this.stack().push(type2);
  }

  @Override
  public void visitBALOAD(BALOAD bALOAD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitDREM(DREM dREM) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitLMUL(LMUL lMUL) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitJSR_W(JSR_W jSR_W) {
    this.stack().push(new ReturnaddressType(jSR_W.physicalSuccessor()));
  }

  @Override
  public void visitLDC2_W(LDC2_W lDC2_W) {
    Constant constant;
    if ((constant = this.cpg.getConstant(lDC2_W.getIndex())) instanceof ConstantLong) {
      this.stack().push(Type.LONG);
    }
    if (constant instanceof ConstantDouble) {
      this.stack().push(Type.DOUBLE);
    }
  }

  @Override
  public void visitAASTORE(AASTORE aASTORE) {
    this.stack().pop();
    this.stack().pop();
    this.stack().pop();
  }

  public void setConstantPoolGen(ConstantPoolGen constantPoolGen) {
    this.cpg = constantPoolGen;
  }

  public void visitLDC_W(LDC_W lDC_W) {
    Constant constant;
    if ((constant = this.cpg.getConstant(lDC_W.getIndex())) instanceof ConstantInteger) {
      this.stack().push(Type.INT);
    }
    if (constant instanceof ConstantFloat) {
      this.stack().push(Type.FLOAT);
    }
    if (constant instanceof ConstantString) {
      this.stack().push(Type.STRING);
    }
  }

  @Override
  public void visitRETURN(RETURN rETURN) {}

  @Override
  public void visitI2D(I2D i2D) {
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitIAND(IAND aND) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitGOTO(GOTO gOTO) {}

  @Override
  public void visitLSHL(LSHL lSHL) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitIASTORE(IASTORE aSTORE) {
    this.stack().pop();
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitIFGT(IFGT fGT) {
    this.stack().pop();
  }

  @Override
  public void visitISUB(ISUB sUB) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitIOR(IOR oR) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitFMUL(FMUL fMUL) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitRET(RET rET) {}

  @Override
  public void visitDCONST(DCONST dCONST) {
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitLRETURN(LRETURN lRETURN) {
    this.stack().pop();
  }

  @Override
  public void visitIF_ICMPEQ(IF_ICMPEQ f_ICMPEQ) {
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitINVOKESTATIC(INVOKESTATIC nVOKESTATIC) {
    int value;
    for (int value2 = value = 0;
        value2 < nVOKESTATIC.getArgumentTypes(this.cpg).length;
        value2 = value) {
      value++;
      this.stack().pop();
    }
    if (nVOKESTATIC.getType2(this.cpg) != Type.VOID) {
      Type type;
      if ((type = nVOKESTATIC.getType2(this.cpg)).equals(Type.BOOLEAN)
          || type.equals(Type.CHAR)
          || type.equals(Type.BYTE)
          || type.equals(Type.SHORT)) {
        type = Type.INT;
      }
      this.stack().push(type);
    }
  }

  @Override
  public void visitFNEG(FNEG fNEG) {
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitFADD(FADD fADD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitIADD(IADD aDD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitDUP_X2(DUP_X2 dUP_X2) {
    Type type = this.stack().pop();
    Type type2;
    if ((type2 = this.stack().pop()).getSize() == 2) {
      this.stack().push(type);
      this.stack().push(type2);
      this.stack().push(type);
    } else {
      Type type3 = this.stack().pop();
      this.stack().push(type);
      this.stack().push(type3);
      this.stack().push(type2);
      this.stack().push(type);
    }
  }

  @Override
  public void visitLNEG(LNEG lNEG) {
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitDUP2(DUP2 dUP2) {
    Type type;
    if ((type = this.stack().pop()).getSize() == 2) {
      this.stack().push(type);
      this.stack().push(type);
    } else {
      Type type2 = this.stack().pop();
      this.stack().push(type2);
      this.stack().push(type);
      this.stack().push(type2);
      this.stack().push(type);
    }
  }

  @Override
  public void visitI2L(I2L i2L) {
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitIALOAD(IALOAD aLOAD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitNOP(NOP nOP) {}

  @Override
  public void visitLOR(LOR lOR) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitIF_ACMPNE(IF_ACMPNE f_ACMPNE) {
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitICONST(ICONST cONST) {
    this.stack().push(Type.INT);
  }

  @Override
  public void visitGOTO_W(GOTO_W gOTO_W) {}

  @Override
  public void visitAALOAD(AALOAD aALOAD) {
    this.stack().pop();
    Type type = this.stack().pop();
    if (type == Type.NULL) {
      this.stack().push(Type.NULL);
    } else {
      ArrayType arrayType = (ArrayType) type;
      this.stack().push(arrayType.getElementType());
    }
  }

  @Override
  public void visitSIPUSH(SIPUSH sIPUSH) {
    this.stack().push(Type.INT);
  }

  @Override
  public void visitACONST_NULL(ACONST_NULL aCONST_NULL) {
    this.stack().push(Type.NULL);
  }

  @Override
  public void visitLLOAD(LLOAD lLOAD) {
    this.stack().push(this.locals().get(lLOAD.getIndex()));
  }

  @Override
  public void visitDMUL(DMUL dMUL) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitIFNONNULL(IFNONNULL fNONNULL) {
    this.stack().pop();
  }

  @Override
  public void visitFALOAD(FALOAD fALOAD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitDUP(DUP dUP) {
    Type type = this.stack().pop();
    this.stack().push(type);
    this.stack().push(type);
  }

  @Override
  public void visitIUSHR(IUSHR uSHR) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitDRETURN(DRETURN dRETURN) {
    this.stack().pop();
  }

  @Override
  public void visitF2I(F2I f2I) {
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitDALOAD(DALOAD dALOAD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitLAND(LAND lAND) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitIF_ICMPGT(IF_ICMPGT f_ICMPGT) {
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitL2F(L2F l2F) {
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitISHL(ISHL sHL) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  public void setFrame(Frame frame2) {
    this.frame = frame2;
  }

  @Override
  public void visitDUP2_X1(DUP2_X1 dUP2_X1) {
    Type type;
    if ((type = this.stack().pop()).getSize() == 2) {
      Type type2 = this.stack().pop();
      this.stack().push(type);
      this.stack().push(type2);
      this.stack().push(type);
    } else {
      Type type3 = this.stack().pop();
      Type type4 = this.stack().pop();
      this.stack().push(type3);
      this.stack().push(type);
      this.stack().push(type4);
      this.stack().push(type3);
      this.stack().push(type);
    }
  }

  @Override
  public void visitIF_ICMPLT(IF_ICMPLT f_ICMPLT) {
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitALOAD(ALOAD aLOAD) {
    this.stack().push(this.locals().get(aLOAD.getIndex()));
  }

  @Override
  public void visitLREM(LREM lREM) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitMONITOREXIT(MONITOREXIT mONITOREXIT) {
    this.stack().pop();
  }

  @Override
  public void visitDCMPG(DCMPG dCMPG) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  public OperandStack stack() {
    return this.frame.getStack();
  }

  @Override
  public void visitLOOKUPSWITCH(LOOKUPSWITCH lOOKUPSWITCH) {
    this.stack().pop();
  }

  @Override
  public void visitCALOAD(CALOAD cALOAD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitFLOAD(FLOAD fLOAD) {
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitFRETURN(FRETURN fRETURN) {
    this.stack().pop();
  }

  @Override
  public void visitIF_ICMPLE(IF_ICMPLE f_ICMPLE) {
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitDCMPL(DCMPL dCMPL) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitD2L(D2L d2L) {
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitDSTORE(DSTORE dSTORE) {
    this.locals().set(dSTORE.getIndex(), this.stack().pop());
    this.locals().set(dSTORE.getIndex() + 1, Type.UNKNOWN);
  }

  @Override
  public void visitL2D(L2D l2D) {
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitINVOKEVIRTUAL(INVOKEVIRTUAL nVOKEVIRTUAL) {
    this.stack().pop();
    int value = 0;
    for (int value2 = value;
        value2 < nVOKEVIRTUAL.getArgumentTypes(this.cpg).length;
        value2 = value) {
      value++;
      this.stack().pop();
    }
    if (nVOKEVIRTUAL.getType2(this.cpg) != Type.VOID) {
      Type type;
      if ((type = nVOKEVIRTUAL.getType2(this.cpg)).equals(Type.BOOLEAN)
          || type.equals(Type.CHAR)
          || type.equals(Type.BYTE)
          || type.equals(Type.SHORT)) {
        type = Type.INT;
      }
      this.stack().push(type);
    }
  }

  @Override
  public void visitIFLT(IFLT fLT) {
    this.stack().pop();
  }

  @Override
  public void visitIINC(IINC iNC) {}

  @Override
  public void visitDUP2_X2(DUP2_X2 dUP2_X2) {
    Type type;
    if ((type = this.stack().pop()).getSize() == 2) {
      Type type2;
      if ((type2 = this.stack().pop()).getSize() == 2) {
        this.stack().push(type);
        this.stack().push(type2);
        this.stack().push(type);
      } else {
        Type type3 = this.stack().pop();
        this.stack().push(type);
        this.stack().push(type3);
        this.stack().push(type2);
        this.stack().push(type);
      }
    } else {
      Type type4 = this.stack().pop();
      Type type5;
      if ((type5 = this.stack().pop()).getSize() == 2) {
        this.stack().push(type4);
        this.stack().push(type);
        this.stack().push(type5);
        this.stack().push(type4);
        this.stack().push(type);
      } else {
        Type type6 = this.stack().pop();
        this.stack().push(type4);
        this.stack().push(type);
        this.stack().push(type6);
        this.stack().push(type5);
        this.stack().push(type4);
        this.stack().push(type);
      }
    }
  }

  @Override
  public void visitDUP_X1(DUP_X1 dUP_X1) {
    Type type = this.stack().pop();
    Type type2 = this.stack().pop();
    this.stack().push(type);
    this.stack().push(type2);
    this.stack().push(type);
  }

  @Override
  public void visitFCONST(FCONST fCONST) {
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitMULTIANEWARRAY(MULTIANEWARRAY mULTIANEWARRAY) {
    int value;
    for (int value2 = value = 0; value2 < mULTIANEWARRAY.getDimensions(); value2 = value) {
      value++;
      this.stack().pop();
    }
    this.stack().push(mULTIANEWARRAY.getType(this.cpg));
  }

  @Override
  public void visitDNEG(DNEG dNEG) {
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitPUTSTATIC(PUTSTATIC pUTSTATIC) {
    this.stack().pop();
  }

  @Override
  public void visitIREM(IREM rEM) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitDASTORE(DASTORE dASTORE) {
    this.stack().pop();
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitLDC(LDC lDC) {
    Constant constant;
    if ((constant = this.cpg.getConstant(lDC.getIndex())) instanceof ConstantInteger) {
      this.stack().push(Type.INT);
    }
    if (constant instanceof ConstantFloat) {
      this.stack().push(Type.FLOAT);
    }
    if (constant instanceof ConstantString) {
      this.stack().push(Type.STRING);
    }
  }

  @Override
  public void visitF2D(F2D f2D) {
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitFSUB(FSUB fSUB) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitI2S(I2S i2S) {
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitFCMPG(FCMPG fCMPG) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitLDIV(LDIV lDIV) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitDADD(DADD dADD) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitD2I(D2I d2I) {
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitJSR(JSR jSR) {
    this.stack().push(new ReturnaddressType(jSR.physicalSuccessor()));
  }

  @Override
  public void visitARRAYLENGTH(ARRAYLENGTH aRRAYLENGTH) {
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitARETURN(ARETURN aRETURN) {
    this.stack().pop();
  }

  @Override
  public void visitIFNULL(IFNULL fNULL) {
    this.stack().pop();
  }

  @Override
  public void visitGETFIELD(GETFIELD gETFIELD) {
    this.stack().pop();
    Type type2 = gETFIELD.getType2(this.cpg);
    if (type2.equals(Type.BOOLEAN)
        || type2.equals(Type.CHAR)
        || type2.equals(Type.BYTE)
        || type2.equals(Type.SHORT)) {
      type2 = Type.INT;
    }
    this.stack().push(type2);
  }

  @Override
  public void visitGETSTATIC(GETSTATIC gETSTATIC) {
    Type type;
    if ((type = gETSTATIC.getType2(this.cpg)).equals(Type.BOOLEAN)
        || type.equals(Type.CHAR)
        || type.equals(Type.BYTE)
        || type.equals(Type.SHORT)) {
      type = Type.INT;
    }
    this.stack().push(type);
  }

  @Override
  public void visitCASTORE(CASTORE cASTORE) {
    this.stack().pop();
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitLSHR(LSHR lSHR) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitSASTORE(SASTORE sASTORE) {
    this.stack().pop();
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitISHR(ISHR sHR) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitPOP2(POP2 pOP2) {
    if (this.stack().pop().getSize() == 1) {
      this.stack().pop();
    }
  }

  @Override
  public void visitLXOR(LXOR lXOR) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitNEW(NEW nEW) {
    this.stack().push(new UninitializedObjectType((ObjectType) nEW.getType(this.cpg)));
  }

  @Override
  public void visitLCONST(LCONST lCONST) {
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitFREM(FREM fREM) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitIFGE(IFGE fGE) {
    this.stack().pop();
  }

  @Override
  public void visitILOAD(ILOAD lOAD) {
    this.stack().push(Type.INT);
  }

  @Override
  public void visitIFNE(IFNE fNE) {
    this.stack().pop();
  }

  @Override
  public void visitDDIV(DDIV dDIV) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.DOUBLE);
  }

  @Override
  public void visitIFEQ(IFEQ fEQ) {
    this.stack().pop();
  }

  @Override
  public void visitPUTFIELD(PUTFIELD pUTFIELD) {
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitF2L(F2L f2L) {
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitD2F(D2F d2F) {
    this.stack().pop();
    this.stack().push(Type.FLOAT);
  }

  @Override
  public void visitLSUB(LSUB lSUB) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.LONG);
  }

  @Override
  public void visitLCMP(LCMP lCMP) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitTABLESWITCH(TABLESWITCH tABLESWITCH) {
    this.stack().pop();
  }

  @Override
  public void visitCHECKCAST(CHECKCAST cHECKCAST) {
    this.stack().pop();
    this.stack().push(cHECKCAST.getType(this.cpg));
  }

  @Override
  public void visitATHROW(ATHROW aTHROW) {
    Type type = this.stack().pop();
    this.stack().clear();
    if (type.equals(Type.NULL)) {
      this.stack().push(Type.getType("Ljava/lang/NullPointerException;"));
    } else {
      this.stack().push(type);
    }
  }

  @Override
  public void visitFASTORE(FASTORE fASTORE) {
    this.stack().pop();
    this.stack().pop();
    this.stack().pop();
  }

  public LocalVariables locals() {
    return this.frame.getLocals();
  }

  @Override
  public void visitIXOR(IXOR xOR) {
    this.stack().pop();
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitIF_ACMPEQ(IF_ACMPEQ f_ACMPEQ) {
    this.stack().pop();
    this.stack().pop();
  }

  @Override
  public void visitINEG(INEG nEG) {
    this.stack().pop();
    this.stack().push(Type.INT);
  }

  @Override
  public void visitASTORE(ASTORE aSTORE) {
    this.locals().set(aSTORE.getIndex(), this.stack().pop());
  }

  @Override
  public void visitINVOKESPECIAL(INVOKESPECIAL nVOKESPECIAL) {
    if (nVOKESPECIAL.getText4(this.cpg).equals("<init>")) {
      UninitializedObjectType uninitializedObjectType;
      if ((uninitializedObjectType =
              (UninitializedObjectType)
                  this.stack().peek(nVOKESPECIAL.getArgumentTypes(this.cpg).length))
          == Frame._this) {
        Frame._this = null;
      }
      this.stack().initializeObject(uninitializedObjectType);
      this.locals().initializeObject(uninitializedObjectType);
    }
    this.stack().pop();
    int value = 0;
    for (int value2 = value;
        value2 < nVOKESPECIAL.getArgumentTypes(this.cpg).length;
        value2 = value) {
      value++;
      this.stack().pop();
    }
    if (nVOKESPECIAL.getType2(this.cpg) != Type.VOID) {
      Type type;
      if ((type = nVOKESPECIAL.getType2(this.cpg)).equals(Type.BOOLEAN)
          || type.equals(Type.CHAR)
          || type.equals(Type.BYTE)
          || type.equals(Type.SHORT)) {
        type = Type.INT;
      }
      this.stack().push(type);
    }
  }
}
