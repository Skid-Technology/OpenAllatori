package com.allatori.lib.bcel.generic;

public abstract class EmptyVisitor implements Visitor {

  @Override
  public void visitACONST_NULL(ACONST_NULL aCONST_NULL) {}

  @Override
  public void visitNEW(NEW nEW) {}

  @Override
  public void visitFieldOrMethod(FieldOrMethod fieldOrMethod) {}

  @Override
  public void visitICONST(ICONST cONST) {}

  @Override
  public void visitDCMPG(DCMPG dCMPG) {}

  @Override
  public void visitLSTORE(LSTORE lSTORE) {}

  @Override
  public void visitLSHR(LSHR lSHR) {}

  @Override
  public void visitLALOAD(LALOAD lALOAD) {}

  @Override
  public void visitStoreInstruction(StoreInstruction storeInstruction) {}

  @Override
  public void visitArithmeticInstruction(ArithmeticInstruction arithmeticInstruction) {}

  @Override
  public void visitIF_ICMPEQ(IF_ICMPEQ f_ICMPEQ) {}

  @Override
  public void visitINVOKEVIRTUAL(INVOKEVIRTUAL nVOKEVIRTUAL) {}

  @Override
  public void visitLocalVariableInstruction(LocalVariableInstruction localVariableInstruction) {}

  @Override
  public void visitLRETURN(LRETURN lRETURN) {}

  @Override
  public void visitARRAYLENGTH(ARRAYLENGTH aRRAYLENGTH) {}

  @Override
  public void visitDRETURN(DRETURN dRETURN) {}

  @Override
  public void visitStackInstruction(StackInstruction stackInstruction) {}

  @Override
  public void visitD2L(D2L d2L) {}

  @Override
  public void visitDNEG(DNEG dNEG) {}

  @Override
  public void visitFCONST(FCONST fCONST) {}

  @Override
  public void visitLDIV(LDIV lDIV) {}

  @Override
  public void visitASTORE(ASTORE aSTORE) {}

  @Override
  public void visitFREM(FREM fREM) {}

  @Override
  public void visitDMUL(DMUL dMUL) {}

  @Override
  public void visitFMUL(FMUL fMUL) {}

  @Override
  public void visitJsrInstruction(JsrInstruction jsrInstruction) {}

  @Override
  public void visitFALOAD(FALOAD fALOAD) {}

  @Override
  public void visitIDIV(IDIV dIV) {}

  @Override
  public void visitMULTIANEWARRAY(MULTIANEWARRAY mULTIANEWARRAY) {}

  @Override
  public void visitFSTORE(FSTORE fSTORE) {}

  @Override
  public void visitIUSHR(IUSHR uSHR) {}

  @Override
  public void visitI2B(I2B i2B) {}

  @Override
  public void visitDASTORE(DASTORE dASTORE) {}

  @Override
  public void visitTypedInstruction(TypedInstruction typedInstruction) {}

  @Override
  public void visitANEWARRAY(ANEWARRAY aNEWARRAY) {}

  @Override
  public void visitIF_ACMPEQ(IF_ACMPEQ f_ACMPEQ) {}

  @Override
  public void visitALOAD(ALOAD aLOAD) {}

  @Override
  public void visitIFNE(IFNE fNE) {}

  @Override
  public void visitDUP2(DUP2 dUP2) {}

  @Override
  public void visitBALOAD(BALOAD bALOAD) {}

  @Override
  public void visitDUP(DUP dUP) {}

  @Override
  public void visitIF_ICMPLE(IF_ICMPLE f_ICMPLE) {}

  @Override
  public void visitD2I(D2I d2I) {}

  @Override
  public void visitARETURN(ARETURN aRETURN) {}

  @Override
  public void visitI2L(I2L i2L) {}

  @Override
  public void visitFADD(FADD fADD) {}

  @Override
  public void visitInvokeInstruction(InvokeInstruction invokeInstruction) {}

  @Override
  public void visitLCMP(LCMP lCMP) {}

  @Override
  public void visitConversionInstruction(ConversionInstruction conversionInstruction) {}

  @Override
  public void visitIF_ICMPLT(IF_ICMPLT f_ICMPLT) {}

  @Override
  public void visitPUTSTATIC(PUTSTATIC pUTSTATIC) {}

  @Override
  public void visitIFEQ(IFEQ fEQ) {}

  @Override
  public void visitBIPUSH(BIPUSH bIPUSH) {}

  @Override
  public void visitIFLE(IFLE fLE) {}

  @Override
  public void visitReturnInstruction(ReturnInstruction returnInstruction) {}

  @Override
  public void visitFRETURN(FRETURN fRETURN) {}

  @Override
  public void visitStackConsumer(StackConsumer stackConsumer) {}

  @Override
  public void visitGETFIELD(GETFIELD gETFIELD) {}

  @Override
  public void visitIOR(IOR oR) {}

  @Override
  public void visitSALOAD(SALOAD sALOAD) {}

  @Override
  public void visitIFGE(IFGE fGE) {}

  @Override
  public void visitIINC(IINC iNC) {}

  @Override
  public void visitISHL(ISHL sHL) {}

  @Override
  public void visitRET(RET rET) {}

  @Override
  public void visitINVOKESPECIAL(INVOKESPECIAL nVOKESPECIAL) {}

  @Override
  public void visitBASTORE(BASTORE bASTORE) {}

  @Override
  public void visitAASTORE(AASTORE aASTORE) {}

  @Override
  public void visitIfInstruction(IfInstruction ifInstruction) {}

  @Override
  public void visitIMPDEP2(IMPDEP2 mPDEP2) {}

  @Override
  public void visitPOP2(POP2 pOP2) {}

  @Override
  public void visitISUB(ISUB sUB) {}

  @Override
  public void visitLADD(LADD lADD) {}

  @Override
  public void visitINEG(INEG nEG) {}

  @Override
  public void visitINVOKESTATIC(INVOKESTATIC nVOKESTATIC) {}

  @Override
  public void visitSWAP(SWAP sWAP) {}

  @Override
  public void visitIFNULL(IFNULL fNULL) {}

  @Override
  public void visitLoadInstruction(LoadInstruction loadInstruction) {}

  @Override
  public void visitMONITORENTER(MONITORENTER mONITORENTER) {}

  @Override
  public void visitJSR_W(JSR_W jSR_W) {}

  @Override
  public void visitMONITOREXIT(MONITOREXIT mONITOREXIT) {}

  @Override
  public void visitIRETURN(IRETURN rETURN) {}

  @Override
  public void visitLCONST(LCONST lCONST) {}

  @Override
  public void visitDCMPL(DCMPL dCMPL) {}

  @Override
  public void visitIREM(IREM rEM) {}

  @Override
  public void visitPopInstruction(PopInstruction popInstruction) {}

  @Override
  public void visitDDIV(DDIV dDIV) {}

  @Override
  public void visitDSUB(DSUB dSUB) {}

  @Override
  public void visitDCONST(DCONST dCONST) {}

  @Override
  public void visitAllocationInstruction(AllocationInstruction allocationInstruction) {}

  @Override
  public void visitIALOAD(IALOAD aLOAD) {}

  @Override
  public void visitI2C(I2C i2C) {}

  @Override
  public void visitStackProducer(StackProducer stackProducer) {}

  @Override
  public void visitCASTORE(CASTORE cASTORE) {}

  @Override
  public void visitIF_ACMPNE(IF_ACMPNE f_ACMPNE) {}

  @Override
  public void visitISHR(ISHR sHR) {}

  @Override
  public void visitF2L(F2L f2L) {}

  @Override
  public void visitSASTORE(SASTORE sASTORE) {}

  @Override
  public void visitIADD(IADD aDD) {}

  @Override
  public void visitFNEG(FNEG fNEG) {}

  @Override
  public void visitIF_ICMPNE(IF_ICMPNE f_ICMPNE) {}

  @Override
  public void visitL2D(L2D l2D) {}

  @Override
  public void visitDALOAD(DALOAD dALOAD) {}

  @Override
  public void visitLDC2_W(LDC2_W lDC2_W) {}

  @Override
  public void visitUnconditionalBranch(UnconditionalBranch unconditionalBranch) {}

  @Override
  public void visitIFNONNULL(IFNONNULL fNONNULL) {}

  @Override
  public void visitL2I(L2I l2I) {}

  @Override
  public void visitI2F(I2F i2F) {}

  @Override
  public void visitConstantPushInstruction(ConstantPushInstruction constantPushInstruction) {}

  @Override
  public void visitFSUB(FSUB fSUB) {}

  @Override
  public void visitBREAKPOINT(BREAKPOINT bREAKPOINT) {}

  @Override
  public void visitLASTORE(LASTORE lASTORE) {}

  @Override
  public void visitLXOR(LXOR lXOR) {}

  @Override
  public void visitFCMPG(FCMPG fCMPG) {}

  @Override
  public void visitATHROW(ATHROW aTHROW) {}

  @Override
  public void visitI2D(I2D i2D) {}

  @Override
  public void visitBranchInstruction(BranchInstruction branchInstruction) {}

  @Override
  public void visitFLOAD(FLOAD fLOAD) {}

  @Override
  public void visitLAND(LAND lAND) {}

  @Override
  public void visitSIPUSH(SIPUSH sIPUSH) {}

  @Override
  public void visitCPInstruction(CPInstruction cPInstruction) {}

  @Override
  public void visitLDC(LDC lDC) {}

  @Override
  public void visitINVOKEINTERFACE(INVOKEINTERFACE nVOKEINTERFACE) {}

  @Override
  public void visitDUP2_X2(DUP2_X2 dUP2_X2) {}

  @Override
  public void visitISTORE(ISTORE sTORE) {}

  @Override
  public void visitAALOAD(AALOAD aALOAD) {}

  @Override
  public void visitDREM(DREM dREM) {}

  @Override
  public void visitIXOR(IXOR xOR) {}

  @Override
  public void visitLNEG(LNEG lNEG) {}

  @Override
  public void visitDSTORE(DSTORE dSTORE) {}

  @Override
  public void visitFDIV(FDIV fDIV) {}

  @Override
  public void visitLREM(LREM lREM) {}

  @Override
  public void visitIMPDEP1(IMPDEP1 mPDEP1) {}

  @Override
  public void visitCHECKCAST(CHECKCAST cHECKCAST) {}

  @Override
  public void visitF2D(F2D f2D) {}

  @Override
  public void visitTABLESWITCH(TABLESWITCH tABLESWITCH) {}

  @Override
  public void visitLOOKUPSWITCH(LOOKUPSWITCH lOOKUPSWITCH) {}

  @Override
  public void visitDADD(DADD dADD) {}

  @Override
  public void visitIFLT(IFLT fLT) {}

  @Override
  public void visitDLOAD(DLOAD dLOAD) {}

  @Override
  public void visitNEWARRAY(NEWARRAY nEWARRAY) {}

  @Override
  public void visitIF_ICMPGT(IF_ICMPGT f_ICMPGT) {}

  @Override
  public void visitDUP2_X1(DUP2_X1 dUP2_X1) {}

  @Override
  public void visitIAND(IAND aND) {}

  @Override
  public void visitIF_ICMPGE(IF_ICMPGE f_ICMPGE) {}

  @Override
  public void visitGOTO_W(GOTO_W gOTO_W) {}

  @Override
  public void visitDUP_X1(DUP_X1 dUP_X1) {}

  @Override
  public void visitLSHL(LSHL lSHL) {}

  @Override
  public void visitGotoInstruction(GotoInstruction gotoInstruction) {}

  @Override
  public void visitFASTORE(FASTORE fASTORE) {}

  @Override
  public void visitILOAD(ILOAD lOAD) {}

  @Override
  public void visitF2I(F2I f2I) {}

  @Override
  public void visitI2S(I2S i2S) {}

  @Override
  public void visitPushInstruction(PushInstruction pushInstruction) {}

  @Override
  public void visitLUSHR(LUSHR lUSHR) {}

  @Override
  public void visitCALOAD(CALOAD cALOAD) {}

  @Override
  public void visitLOR(LOR lOR) {}

  @Override
  public void visitFCMPL(FCMPL fCMPL) {}

  @Override
  public void visitLoadClass(LoadClass loadClass) {}

  @Override
  public void visitRETURN(RETURN rETURN) {}

  @Override
  public void visitGETSTATIC(GETSTATIC gETSTATIC) {}

  @Override
  public void visitPOP(POP pOP) {}

  @Override
  public void visitJSR(JSR jSR) {}

  @Override
  public void visitLLOAD(LLOAD lLOAD) {}

  @Override
  public void visitLMUL(LMUL lMUL) {}

  @Override
  public void visitD2F(D2F d2F) {}

  @Override
  public void visitINSTANCEOF(INSTANCEOF nSTANCEOF) {}

  @Override
  public void visitFieldInstruction(FieldInstruction fieldInstruction) {}

  @Override
  public void visitNOP(NOP nOP) {}

  @Override
  public void visitIMUL(IMUL mUL) {}

  @Override
  public void visitExceptionThrower(ExceptionThrower exceptionThrower) {}

  @Override
  public void visitL2F(L2F l2F) {}

  @Override
  public void visitPUTFIELD(PUTFIELD pUTFIELD) {}

  @Override
  public void visitVariableLengthInstruction(VariableLengthInstruction variableLengthInstruction) {}

  @Override
  public void visitDUP_X2(DUP_X2 dUP_X2) {}

  @Override
  public void visitSelect(Select select) {}

  @Override
  public void visitIASTORE(IASTORE aSTORE) {}

  @Override
  public void visitLSUB(LSUB lSUB) {}

  @Override
  public void visitArrayInstruction(ArrayInstruction arrayInstruction) {}

  @Override
  public void visitGOTO(GOTO gOTO) {}

  @Override
  public void visitIFGT(IFGT fGT) {}
}
