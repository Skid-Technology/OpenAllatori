package com.allatori.lib.bcel.generic;

public interface Visitor {

  void visitFLOAD(FLOAD fLOAD);

  void visitIOR(IOR oR);

  void visitIFGT(IFGT fGT);

  void visitStoreInstruction(StoreInstruction storeInstruction);

  void visitSASTORE(SASTORE sASTORE);

  void visitDDIV(DDIV dDIV);

  void visitDCMPG(DCMPG dCMPG);

  void visitCPInstruction(CPInstruction cPInstruction);

  void visitFASTORE(FASTORE fASTORE);

  void visitIF_ACMPNE(IF_ACMPNE f_ACMPNE);

  void visitFieldOrMethod(FieldOrMethod fieldOrMethod);

  void visitLCMP(LCMP lCMP);

  void visitCASTORE(CASTORE cASTORE);

  void visitIDIV(IDIV dIV);

  void visitDCONST(DCONST dCONST);

  void visitI2L(I2L i2L);

  void visitANEWARRAY(ANEWARRAY aNEWARRAY);

  void visitMONITORENTER(MONITORENTER mONITORENTER);

  void visitFCMPG(FCMPG fCMPG);

  void visitFRETURN(FRETURN fRETURN);

  void visitFALOAD(FALOAD fALOAD);

  void visitICONST(ICONST cONST);

  void visitLNEG(LNEG lNEG);

  void visitConversionInstruction(ConversionInstruction conversionInstruction);

  void visitPopInstruction(PopInstruction popInstruction);

  void visitI2F(I2F i2F);

  void visitBranchInstruction(BranchInstruction branchInstruction);

  void visitIFNE(IFNE fNE);

  void visitPushInstruction(PushInstruction pushInstruction);

  void visitIMPDEP1(IMPDEP1 mPDEP1);

  void visitIMPDEP2(IMPDEP2 mPDEP2);

  void visitF2D(F2D f2D);

  void visitLOOKUPSWITCH(LOOKUPSWITCH lOOKUPSWITCH);

  void visitIF_ICMPGT(IF_ICMPGT f_ICMPGT);

  void visitISHL(ISHL sHL);

  void visitDMUL(DMUL dMUL);

  void visitFSTORE(FSTORE fSTORE);

  void visitSelect(Select select);

  void visitIF_ICMPLE(IF_ICMPLE f_ICMPLE);

  void visitDUP_X1(DUP_X1 dUP_X1);

  void visitBALOAD(BALOAD bALOAD);

  void visitRETURN(RETURN rETURN);

  void visitACONST_NULL(ACONST_NULL aCONST_NULL);

  void visitIMUL(IMUL mUL);

  void visitIF_ICMPEQ(IF_ICMPEQ f_ICMPEQ);

  void visitDLOAD(DLOAD dLOAD);

  void visitALOAD(ALOAD aLOAD);

  void visitPUTSTATIC(PUTSTATIC pUTSTATIC);

  void visitINVOKEVIRTUAL(INVOKEVIRTUAL nVOKEVIRTUAL);

  void visitLoadInstruction(LoadInstruction loadInstruction);

  void visitISTORE(ISTORE sTORE);

  void visitNOP(NOP nOP);

  void visitGotoInstruction(GotoInstruction gotoInstruction);

  void visitSIPUSH(SIPUSH sIPUSH);

  void visitIXOR(IXOR xOR);

  void visitIF_ICMPNE(IF_ICMPNE f_ICMPNE);

  void visitI2D(I2D i2D);

  void visitLSHL(LSHL lSHL);

  void visitCHECKCAST(CHECKCAST cHECKCAST);

  void visitD2I(D2I d2I);

  void visitINVOKESPECIAL(INVOKESPECIAL nVOKESPECIAL);

  void visitIFLT(IFLT fLT);

  void visitJsrInstruction(JsrInstruction jsrInstruction);

  void visitGETFIELD(GETFIELD gETFIELD);

  void visitStackProducer(StackProducer stackProducer);

  void visitLSTORE(LSTORE lSTORE);

  void visitStackConsumer(StackConsumer stackConsumer);

  void visitMULTIANEWARRAY(MULTIANEWARRAY mULTIANEWARRAY);

  void visitASTORE(ASTORE aSTORE);

  void visitIFEQ(IFEQ fEQ);

  void visitINVOKEINTERFACE(INVOKEINTERFACE nVOKEINTERFACE);

  void visitArithmeticInstruction(ArithmeticInstruction arithmeticInstruction);

  void visitIFLE(IFLE fLE);

  void visitPUTFIELD(PUTFIELD pUTFIELD);

  void visitBREAKPOINT(BREAKPOINT bREAKPOINT);

  void visitLDC2_W(LDC2_W lDC2_W);

  void visitNEW(NEW nEW);

  void visitVariableLengthInstruction(VariableLengthInstruction variableLengthInstruction);

  void visitI2C(I2C i2C);

  void visitF2I(F2I f2I);

  void visitDUP_X2(DUP_X2 dUP_X2);

  void visitLOR(LOR lOR);

  void visitLCONST(LCONST lCONST);

  void visitConstantPushInstruction(ConstantPushInstruction constantPushInstruction);

  void visitIF_ICMPGE(IF_ICMPGE f_ICMPGE);

  void visitDRETURN(DRETURN dRETURN);

  void visitFMUL(FMUL fMUL);

  void visitDREM(DREM dREM);

  void visitLocalVariableInstruction(LocalVariableInstruction localVariableInstruction);

  void visitGETSTATIC(GETSTATIC gETSTATIC);

  void visitISHR(ISHR sHR);

  void visitInvokeInstruction(InvokeInstruction invokeInstruction);

  void visitFSUB(FSUB fSUB);

  void visitIINC(IINC iNC);

  void visitTypedInstruction(TypedInstruction typedInstruction);

  void visitDADD(DADD dADD);

  void visitIUSHR(IUSHR uSHR);

  void visitLASTORE(LASTORE lASTORE);

  void visitL2I(L2I l2I);

  void visitI2B(I2B i2B);

  void visitL2D(L2D l2D);

  void visitI2S(I2S i2S);

  void visitTABLESWITCH(TABLESWITCH tABLESWITCH);

  void visitINVOKESTATIC(INVOKESTATIC nVOKESTATIC);

  void visitUnconditionalBranch(UnconditionalBranch unconditionalBranch);

  void visitLADD(LADD lADD);

  void visitFREM(FREM fREM);

  void visitISUB(ISUB sUB);

  void visitIF_ICMPLT(IF_ICMPLT f_ICMPLT);

  void visitFNEG(FNEG fNEG);

  void visitD2L(D2L d2L);

  void visitAASTORE(AASTORE aASTORE);

  void visitSALOAD(SALOAD sALOAD);

  void visitGOTO(GOTO gOTO);

  void visitLMUL(LMUL lMUL);

  void visitStackInstruction(StackInstruction stackInstruction);

  void visitAllocationInstruction(AllocationInstruction allocationInstruction);

  void visitFCONST(FCONST fCONST);

  void visitDCMPL(DCMPL dCMPL);

  void visitL2F(L2F l2F);

  void visitLoadClass(LoadClass loadClass);

  void visitDUP2_X2(DUP2_X2 dUP2_X2);

  void visitFCMPL(FCMPL fCMPL);

  void visitDUP2(DUP2 dUP2);

  void visitJSR(JSR jSR);

  void visitIFNONNULL(IFNONNULL fNONNULL);

  void visitReturnInstruction(ReturnInstruction returnInstruction);

  void visitSWAP(SWAP sWAP);

  void visitLALOAD(LALOAD lALOAD);

  void visitLSHR(LSHR lSHR);

  void visitIfInstruction(IfInstruction ifInstruction);

  void visitIRETURN(IRETURN rETURN);

  void visitDUP2_X1(DUP2_X1 dUP2_X1);

  void visitIAND(IAND aND);

  void visitArrayInstruction(ArrayInstruction arrayInstruction);

  void visitLDIV(LDIV lDIV);

  void visitNEWARRAY(NEWARRAY nEWARRAY);

  void visitARRAYLENGTH(ARRAYLENGTH aRRAYLENGTH);

  void visitLREM(LREM lREM);

  void visitLLOAD(LLOAD lLOAD);

  void visitRET(RET rET);

  void visitIFGE(IFGE fGE);

  void visitPOP(POP pOP);

  void visitDSTORE(DSTORE dSTORE);

  void visitExceptionThrower(ExceptionThrower exceptionThrower);

  void visitFADD(FADD fADD);

  void visitLDC(LDC lDC);

  void visitD2F(D2F d2F);

  void visitLRETURN(LRETURN lRETURN);

  void visitLUSHR(LUSHR lUSHR);

  void visitJSR_W(JSR_W jSR_W);

  void visitBIPUSH(BIPUSH bIPUSH);

  void visitFieldInstruction(FieldInstruction fieldInstruction);

  void visitPOP2(POP2 pOP2);

  void visitFDIV(FDIV fDIV);

  void visitLXOR(LXOR lXOR);

  void visitATHROW(ATHROW aTHROW);

  void visitIADD(IADD aDD);

  void visitIREM(IREM rEM);

  void visitDUP(DUP dUP);

  void visitILOAD(ILOAD lOAD);

  void visitIASTORE(IASTORE aSTORE);

  void visitCALOAD(CALOAD cALOAD);

  void visitDSUB(DSUB dSUB);

  void visitDASTORE(DASTORE dASTORE);

  void visitINEG(INEG nEG);

  void visitDALOAD(DALOAD dALOAD);

  void visitMONITOREXIT(MONITOREXIT mONITOREXIT);

  void visitGOTO_W(GOTO_W gOTO_W);

  void visitINSTANCEOF(INSTANCEOF nSTANCEOF);

  void visitBASTORE(BASTORE bASTORE);

  void visitLSUB(LSUB lSUB);

  void visitDNEG(DNEG dNEG);

  void visitAALOAD(AALOAD aALOAD);

  void visitIALOAD(IALOAD aLOAD);

  void visitLAND(LAND lAND);

  void visitIFNULL(IFNULL fNULL);

  void visitARETURN(ARETURN aRETURN);

  void visitF2L(F2L f2L);

  void visitIF_ACMPEQ(IF_ACMPEQ f_ACMPEQ);
}
