package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantDouble;
import com.allatori.lib.bcel.classfile.ConstantFieldref;
import com.allatori.lib.bcel.classfile.ConstantFloat;
import com.allatori.lib.bcel.classfile.ConstantInteger;
import com.allatori.lib.bcel.classfile.ConstantInterfaceMethodref;
import com.allatori.lib.bcel.classfile.ConstantLong;
import com.allatori.lib.bcel.classfile.ConstantMethodref;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ALOAD;
import com.allatori.lib.bcel.generic.ANEWARRAY;
import com.allatori.lib.bcel.generic.ASTORE;
import com.allatori.lib.bcel.generic.ArrayType;
import com.allatori.lib.bcel.generic.CHECKCAST;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.DLOAD;
import com.allatori.lib.bcel.generic.DSTORE;
import com.allatori.lib.bcel.generic.EmptyVisitor;
import com.allatori.lib.bcel.generic.FLOAD;
import com.allatori.lib.bcel.generic.FSTORE;
import com.allatori.lib.bcel.generic.FieldInstruction;
import com.allatori.lib.bcel.generic.GETSTATIC;
import com.allatori.lib.bcel.generic.IINC;
import com.allatori.lib.bcel.generic.ILOAD;
import com.allatori.lib.bcel.generic.INSTANCEOF;
import com.allatori.lib.bcel.generic.INVOKEINTERFACE;
import com.allatori.lib.bcel.generic.INVOKESPECIAL;
import com.allatori.lib.bcel.generic.INVOKESTATIC;
import com.allatori.lib.bcel.generic.INVOKEVIRTUAL;
import com.allatori.lib.bcel.generic.ISTORE;
import com.allatori.lib.bcel.generic.Instruction;
import com.allatori.lib.bcel.generic.InvokeInstruction;
import com.allatori.lib.bcel.generic.LDC;
import com.allatori.lib.bcel.generic.LDC2_W;
import com.allatori.lib.bcel.generic.LLOAD;
import com.allatori.lib.bcel.generic.LOOKUPSWITCH;
import com.allatori.lib.bcel.generic.LSTORE;
import com.allatori.lib.bcel.generic.LoadClass;
import com.allatori.lib.bcel.generic.MULTIANEWARRAY;
import com.allatori.lib.bcel.generic.NEW;
import com.allatori.lib.bcel.generic.NEWARRAY;
import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.PUTSTATIC;
import com.allatori.lib.bcel.generic.RET;
import com.allatori.lib.bcel.generic.TABLESWITCH;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.verifier.VerificationResult;
import com.allatori.lib.bcel.verifier.VerifierFactory;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.StaticCodeInstructionConstraintException;

public class Pass3aVerifier$InstOperandConstraintVisitor extends EmptyVisitor {

  public ConstantPoolGen constantPoolGen;

  public final Pass3aVerifier this$0;

  public Pass3aVerifier$InstOperandConstraintVisitor(
      Pass3aVerifier pass3aVerifier, ConstantPoolGen constantPoolGen2) {
    super();
    this.this$0 = pass3aVerifier;
    this.constantPoolGen = constantPoolGen2;
  }

  @Override
  public void visitASTORE(ASTORE aSTORE) {
    int value;
    if ((value = aSTORE.getIndex()) < 0) {
      this.constraintViolated(aSTORE, "Index '" + value + "' must be non-negative.");
    } else {
      int value2 = this.maxLocals() - 1;
      if (value > value2) {
        this.constraintViolated(
            aSTORE,
            "Index '" + value + "' must not be greater than max_locals-1 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitLDC(LDC lDC) {
    this.indexValid(lDC, lDC.getIndex());
    Constant constant;
    if ((constant = this.constantPoolGen.getConstant(lDC.getIndex())) instanceof ConstantClass) {
      this.this$0.addMessage(
          "Operand of LDC or LDC_W is CONSTANT_Class '"
              + constant
              + "' - this is only supported in JDK 1.5 and higher.");
    } else {
      if (!(constant instanceof ConstantInteger)
          && !(constant instanceof ConstantFloat)
          && !(constant instanceof ConstantString)) {
        this.constraintViolated(
            lDC,
            "Operand of LDC or LDC_W must be one of CONSTANT_Integer, CONSTANT_Float or"
                + " CONSTANT_String, but is '"
                + constant
                + "'.");
      }
    }
  }

  @Override
  public void visitPUTSTATIC(PUTSTATIC pUTSTATIC) {
    Field field;
    try {
      String fieldName = pUTSTATIC.getFieldName(this.constantPoolGen);
      JavaClass javaClass;
      Field[] fields =
          (javaClass =
                  Repository.lookupClass(
                      pUTSTATIC.createObjectType(this.constantPoolGen).getClassName()))
              .getFields();
      Field field2 = null;
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= fields.length) {
          field = field2;
          break;
        }
        if (fields[value].getText().equals(fieldName)) {
          field = field2 = fields[value];
          break;
        }
        value2 = ++value;
      }
      if (field == null) {
        throw new AssertionViolatedException("Field not found?!?");
      }
      if (field2.isEnabled3()
          && !Pass3aVerifier.access$000(this.this$0)
              .getClassName()
              .equals(pUTSTATIC.createObjectType(this.constantPoolGen).getClassName())) {
        this.constraintViolated(
            pUTSTATIC,
            "Referenced field '"
                + field2
                + "' is final and must therefore be declared in the current class '"
                + Pass3aVerifier.access$000(this.this$0).getClassName()
                + "' which is not the case: it is declared in '"
                + pUTSTATIC.createObjectType(this.constantPoolGen).getClassName()
                + "'.");
      }
      if (!field2.isEnabled11()) {
        this.constraintViolated(
            pUTSTATIC, "Referenced field '" + field2 + "' is not static which it should be.");
      }
      String text =
          Repository.lookupClass(Pass3aVerifier.access$000(this.this$0).getClassName())
              .getMethods()[Pass3aVerifier.access$100(this.this$0)]
              .getText();
      if (!javaClass.isEnabled17() && !text.equals("<clinit>")) {
        this.constraintViolated(
            pUTSTATIC,
            "Interface field '" + field2 + "' must be set in a '" + "<clinit>" + "' method.");
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitTABLESWITCH(TABLESWITCH tABLESWITCH) {}

  @Override
  public void visitDLOAD(DLOAD dLOAD) {
    int value;
    if ((value = dLOAD.getIndex()) < 0) {
      this.constraintViolated(
          dLOAD,
          "Index '"
              + value
              + "' must be non-negative. [Constraint by JustIce as an analogon to the single-slot"
              + " xLOAD/xSTORE instructions; may not happen anyway.]");
    } else {
      int value2 = this.maxLocals() - 2;
      if (value > value2) {
        this.constraintViolated(
            dLOAD, "Index '" + value + "' must not be greater than max_locals-2 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitMULTIANEWARRAY(MULTIANEWARRAY mULTIANEWARRAY) {
    this.indexValid(mULTIANEWARRAY, mULTIANEWARRAY.getIndex());
    Constant constant;
    if (!((constant = this.constantPoolGen.getConstant(mULTIANEWARRAY.getIndex()))
        instanceof ConstantClass)) {
      this.constraintViolated(
          mULTIANEWARRAY, "Expecting a CONSTANT_Class operand, but found a '" + constant + "'.");
    }
    short value;
    if ((value = mULTIANEWARRAY.getDimensions()) < 1) {
      this.constraintViolated(
          mULTIANEWARRAY, "Number of dimensions to create must be greater than zero.");
    }
    Type type;
    if ((type = mULTIANEWARRAY.getType(this.constantPoolGen)) instanceof ArrayType) {
      if (((ArrayType) type).getDimensions() < value) {
        this.constraintViolated(
            mULTIANEWARRAY,
            "Not allowed to create array with more dimensions ('+dimensions2create+') than the one"
                + " referenced by the CONSTANT_Class '"
                + type
                + "'.");
        return;
      }
    } else {
      this.constraintViolated(
          mULTIANEWARRAY,
          "Expecting a CONSTANT_Class referencing an array type. [Constraint not found in The Java"
              + " Virtual Machine Specification, Second Edition, 4.8.1]");
    }
  }

  @Override
  public void visitInvokeInstruction(InvokeInstruction invokeInstruction) {
    this.indexValid(invokeInstruction, invokeInstruction.getIndex());
    if (!(invokeInstruction instanceof INVOKEVIRTUAL)
        && !(invokeInstruction instanceof INVOKESPECIAL)
        && !(invokeInstruction instanceof INVOKESTATIC)) {
      Constant constant;
      if (!((constant = this.constantPoolGen.getConstant(invokeInstruction.getIndex()))
          instanceof ConstantInterfaceMethodref)) {
        this.constraintViolated(
            invokeInstruction,
            "Indexing a constant that's not a CONSTANT_InterfaceMethodref but a '"
                + constant
                + "'.");
      }
      ConstantNameAndType constantNameAndType =
          (ConstantNameAndType)
              this.constantPoolGen.getConstant(
                  ((ConstantInterfaceMethodref) constant).getName_and_type_index());
      String text;
      if ((text =
              ((ConstantUtf8) this.constantPoolGen.getConstant(constantNameAndType.getName_index()))
                  .getBytes())
          .equals("<init>")) {
        this.constraintViolated(invokeInstruction, "Method to invoke must not be '<init>'.");
      }
      if (text.equals("<clinit>")) {
        this.constraintViolated(invokeInstruction, "Method to invoke must not be '<clinit>'.");
      }
    } else {
      Constant constant2;
      if (!((constant2 = this.constantPoolGen.getConstant(invokeInstruction.getIndex()))
          instanceof ConstantMethodref)) {
        this.constraintViolated(
            invokeInstruction,
            "Indexing a constant that's not a CONSTANT_Methodref but a '" + constant2 + "'.");
      } else {
        ConstantNameAndType constantNameAndType2 =
            (ConstantNameAndType)
                this.constantPoolGen.getConstant(
                    ((ConstantMethodref) constant2).getName_and_type_index());
        ConstantUtf8 constantUtf8;
        if ((constantUtf8 =
                    (ConstantUtf8)
                        this.constantPoolGen.getConstant(constantNameAndType2.getName_index()))
                .getBytes()
                .equals("<init>")
            && !(invokeInstruction instanceof INVOKESPECIAL)) {
          this.constraintViolated(
              invokeInstruction,
              "Only INVOKESPECIAL is allowed to invoke instance initialization methods.");
        }
        if (!constantUtf8.getBytes().equals("<init>") && constantUtf8.getBytes().startsWith("<")) {
          this.constraintViolated(
              invokeInstruction,
              "No method with a name beginning with '<' other than the instance initialization"
                  + " methods may be called by the method invocation instructions.");
        }
      }
    }
    Type type;
    if ((type = invokeInstruction.getType2(this.constantPoolGen)) instanceof ArrayType) {
      type = ((ArrayType) type).getBasicType();
    }
    VerificationResult verificationResult;
    if (type instanceof ObjectType
        && (verificationResult =
                    VerifierFactory.getVerifier(((ObjectType) type).getClassName())
                        .createVerificationResult2())
                .getStatus()
            != 1) {
      this.constraintViolated(
          invokeInstruction,
          "Return type class/interface could not be verified successfully: '"
              + verificationResult.getDetailMessage()
              + "'.");
    }
    Type[] argumentTypes = invokeInstruction.getArgumentTypes(this.constantPoolGen);
    int value;
    for (int value2 = value = 0; value2 < argumentTypes.length; value2 = ++value) {
      Type type2;
      if ((type2 = argumentTypes[value]) instanceof ArrayType) {
        type2 = ((ArrayType) type2).getBasicType();
      }
      VerificationResult verificationResult2;
      if (type2 instanceof ObjectType
          && (verificationResult2 =
                      VerifierFactory.getVerifier(((ObjectType) type2).getClassName())
                          .createVerificationResult2())
                  .getStatus()
              != 1) {
        this.constraintViolated(
            invokeInstruction,
            "Argument type class/interface could not be verified successfully: '"
                + verificationResult2.getDetailMessage()
                + "'.");
      }
    }
  }

  @Override
  public void visitFSTORE(FSTORE fSTORE) {
    int value;
    if ((value = fSTORE.getIndex()) < 0) {
      this.constraintViolated(fSTORE, "Index '" + value + "' must be non-negative.");
    } else {
      int value2 = this.maxLocals() - 1;
      if (value > value2) {
        this.constraintViolated(
            fSTORE,
            "Index '" + value + "' must not be greater than max_locals-1 '" + value2 + "'.");
      }
    }
  }

  public void constraintViolated(Instruction instruction, String text) {
    throw new StaticCodeInstructionConstraintException(
        ("Instruction " + instruction + " constraint violated: " + text));
  }

  @Override
  public void visitINSTANCEOF(INSTANCEOF nSTANCEOF) {
    this.indexValid(nSTANCEOF, nSTANCEOF.getIndex());
    Constant constant;
    if (!((constant = this.constantPoolGen.getConstant(nSTANCEOF.getIndex()))
        instanceof ConstantClass)) {
      this.constraintViolated(
          nSTANCEOF, "Expecting a CONSTANT_Class operand, but found a '" + constant + "'.");
    }
  }

  @Override
  public void visitFieldInstruction(FieldInstruction fieldInstruction) {
    Field field;
    Field field2;
    try {
      this.indexValid(fieldInstruction, fieldInstruction.getIndex());
      Constant constant;
      if (!((constant = this.constantPoolGen.getConstant(fieldInstruction.getIndex()))
          instanceof ConstantFieldref)) {
        this.constraintViolated(
            fieldInstruction,
            "Indexing a constant that's not a CONSTANT_Fieldref but a '" + constant + "'.");
      }
      String fieldName = fieldInstruction.getFieldName(this.constantPoolGen);
      JavaClass javaClass;
      Field[] fields =
          (javaClass =
                  Repository.lookupClass(
                      fieldInstruction.createObjectType(this.constantPoolGen).getClassName()))
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
          Type type = Type.getType(fields[value].getText2());
          Type type2 = fieldInstruction.getType(this.constantPoolGen);
          if (type.equals(type2)) {
            field2 = field3 = fields[value];
            break;
          }
        }
        value2 = ++value;
      }
      if (field2 != null) {
        Type.getType(field3.getText2());
        fieldInstruction.getType(this.constantPoolGen);
      } else {
        JavaClass[] javaClasss = javaClass.createJavaClasss();
        int value3;
        value2 = value3 = 0;
        label52:
        while (true) {
          if (value2 < javaClasss.length) {
            Field[] fields2 = javaClasss[value3].getFields();
            int value4;
            value2 = value4 = 0;
            while (true) {
              if (value2 >= fields2.length) {
                value2 = ++value3;
                continue label52;
              }
              if (fields2[value4].getText().equals(fieldName)) {
                Type type3 = Type.getType(fields2[value4].getText2());
                Type type4 = fieldInstruction.getType(this.constantPoolGen);
                if (type3.equals(type4)) {
                  if (((field3 = fields2[value4]).getAccess_flags2() & 5) == 0) {
                    field = null;
                    break label52;
                  }
                  break;
                }
              }
              value2 = ++value4;
            }
          }
          field = field3;
          break;
        }
        if (field == null) {
          this.constraintViolated(
              fieldInstruction,
              "Referenced field '"
                  + fieldName
                  + "' does not exist in class '"
                  + javaClass.getText2()
                  + "'.");
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitILOAD(ILOAD lOAD) {
    int value;
    if ((value = lOAD.getIndex()) < 0) {
      this.constraintViolated(lOAD, "Index '" + value + "' must be non-negative.");
    } else {
      int value2 = this.maxLocals() - 1;
      if (value > value2) {
        this.constraintViolated(
            lOAD, "Index '" + value + "' must not be greater than max_locals-1 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitLoadClass(LoadClass loadClass) {
    ObjectType objectType;
    VerificationResult verificationResult;
    if ((objectType = loadClass.getLoadClassType(this.constantPoolGen)) != null
        && (verificationResult =
                    VerifierFactory.getVerifier(objectType.getClassName())
                        .createVerificationResult())
                .getStatus()
            != 1) {
      this.constraintViolated(
          (Instruction) loadClass,
          "Class '"
              + loadClass.getLoadClassType(this.constantPoolGen).getClassName()
              + "' is referenced, but cannot be loaded: '"
              + verificationResult
              + "'.");
    }
  }

  public boolean matchesValues(Object[] results, Object[] results2) {
    if (results.length != results2.length) {
      return false;
    }
    int value;
    for (int value2 = value = 0; value2 < results.length; value2 = ++value) {
      if (!results[value].equals(results2[value])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void visitNEWARRAY(NEWARRAY nEWARRAY) {
    byte value;
    if ((value = nEWARRAY.getTypecode()) != 4
        && value != 5
        && value != 6
        && value != 7
        && value != 8
        && value != 9
        && value != 10
        && value != 11) {
      this.constraintViolated(nEWARRAY, "Illegal type code '+t+' for 'atype' operand.");
    }
  }

  @Override
  public void visitLDC2_W(LDC2_W lDC2_W) {
    this.indexValid(lDC2_W, lDC2_W.getIndex());
    Constant constant;
    if (!((constant = this.constantPoolGen.getConstant(lDC2_W.getIndex())) instanceof ConstantLong)
        && !(constant instanceof ConstantDouble)) {
      this.constraintViolated(
          lDC2_W,
          "Operand of LDC2_W must be CONSTANT_Long or CONSTANT_Double, but is '" + constant + "'.");
    }
    try {
      this.indexValid(lDC2_W, lDC2_W.getIndex() + 1);
    } catch (StaticCodeInstructionConstraintException staticCodeInstructionConstraintException) {
      throw new AssertionViolatedException(
          "OOPS: Does not BCEL handle that? LDC2_W operand has a problem.");
    }
  }

  @Override
  public void visitRET(RET rET) {
    int value;
    if ((value = rET.getIndex()) < 0) {
      this.constraintViolated(rET, "Index '" + value + "' must be non-negative.");
    } else {
      int value2 = this.maxLocals() - 1;
      if (value > value2) {
        this.constraintViolated(
            rET, "Index '" + value + "' must not be greater than max_locals-1 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitLLOAD(LLOAD lLOAD) {
    int value;
    if ((value = lLOAD.getIndex()) < 0) {
      this.constraintViolated(
          lLOAD,
          "Index '"
              + value
              + "' must be non-negative. [Constraint by JustIce as an analogon to the single-slot"
              + " xLOAD/xSTORE instructions; may not happen anyway.]");
    } else {
      int value2 = this.maxLocals() - 2;
      if (value > value2) {
        this.constraintViolated(
            lLOAD, "Index '" + value + "' must not be greater than max_locals-2 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitINVOKEVIRTUAL(INVOKEVIRTUAL nVOKEVIRTUAL) {
    Method method;
    try {
      JavaClass javaClass;
      Method[] methods =
          (javaClass = Repository.lookupClass(nVOKEVIRTUAL.getText2(this.constantPoolGen)))
              .getMethods();
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= methods.length) {
          method = (Method) (Object) null;
          break;
        }
        if (methods[value].getText().equals(nVOKEVIRTUAL.getText4(this.constantPoolGen))
            && Type.getReturnType(methods[value].getText2())
                .equals(nVOKEVIRTUAL.getType2(this.constantPoolGen))
            && this.matchesValues(
                Type.getArgumentTypes(methods[value].getText2()),
                nVOKEVIRTUAL.getArgumentTypes(this.constantPoolGen))) {
          method = methods[value];
          break;
        }
        value2 = ++value;
      }
      if (method == null) {
        this.constraintViolated(
            nVOKEVIRTUAL,
            "Referenced method '"
                + nVOKEVIRTUAL.getText4(this.constantPoolGen)
                + "' with expected signature '"
                + nVOKEVIRTUAL.getText3(this.constantPoolGen)
                + "' not found in class '"
                + javaClass.getText2()
                + "'. The native verifier does allow the method to be declared in some superclass"
                + " or implemented interface, which the Java Virtual Machine Specification, Second"
                + " Edition does not.");
      }
      if (!javaClass.isEnabled17()) {
        this.constraintViolated(
            nVOKEVIRTUAL,
            "Referenced class '"
                + javaClass.getText2()
                + "' is an interface, but not a class as expected.");
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitLSTORE(LSTORE lSTORE) {
    int value;
    if ((value = lSTORE.getIndex()) < 0) {
      this.constraintViolated(
          lSTORE,
          "Index '"
              + value
              + "' must be non-negative. [Constraint by JustIce as an analogon to the single-slot"
              + " xLOAD/xSTORE instructions; may not happen anyway.]");
    } else {
      int value2 = this.maxLocals() - 2;
      if (value > value2) {
        this.constraintViolated(
            lSTORE,
            "Index '" + value + "' must not be greater than max_locals-2 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitIINC(IINC iNC) {
    int value;
    if ((value = iNC.getIndex()) < 0) {
      this.constraintViolated(iNC, "Index '" + value + "' must be non-negative.");
    } else {
      int value2 = this.maxLocals() - 1;
      if (value > value2) {
        this.constraintViolated(
            iNC, "Index '" + value + "' must not be greater than max_locals-1 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitANEWARRAY(ANEWARRAY aNEWARRAY) {
    this.indexValid(aNEWARRAY, aNEWARRAY.getIndex());
    Constant constant;
    if (!((constant = this.constantPoolGen.getConstant(aNEWARRAY.getIndex()))
        instanceof ConstantClass)) {
      this.constraintViolated(
          aNEWARRAY, "Expecting a CONSTANT_Class operand, but found a '" + constant + "'.");
    }
    Type type;
    if ((type = aNEWARRAY.getType(this.constantPoolGen)) instanceof ArrayType
        && ((ArrayType) type).getDimensions() >= 255) {
      this.constraintViolated(
          aNEWARRAY, "Not allowed to create an array with more than 255 dimensions.");
    }
  }

  @Override
  public void visitNEW(NEW nEW) {
    this.indexValid(nEW, nEW.getIndex());
    Constant constant;
    if (!((constant = this.constantPoolGen.getConstant(nEW.getIndex())) instanceof ConstantClass)) {
      this.constraintViolated(
          nEW, "Expecting a CONSTANT_Class operand, but found a '" + constant + "'.");
    } else {
      ConstantUtf8 constantUtf8 =
          (ConstantUtf8)
              this.constantPoolGen.getConstant(((ConstantClass) constant).getNameIndex());
      if (Type.getType("L" + constantUtf8.getBytes() + ";") instanceof ArrayType) {
        this.constraintViolated(nEW, "NEW must not be used to create an array.");
      }
    }
  }

  @Override
  public void visitDSTORE(DSTORE dSTORE) {
    int value;
    if ((value = dSTORE.getIndex()) < 0) {
      this.constraintViolated(
          dSTORE,
          "Index '"
              + value
              + "' must be non-negative. [Constraint by JustIce as an analogon to the single-slot"
              + " xLOAD/xSTORE instructions; may not happen anyway.]");
    } else {
      int value2 = this.maxLocals() - 2;
      if (value > value2) {
        this.constraintViolated(
            dSTORE,
            "Index '" + value + "' must not be greater than max_locals-2 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitLOOKUPSWITCH(LOOKUPSWITCH lOOKUPSWITCH) {
    int[] match = lOOKUPSWITCH.getMatch();
    int value = Integer.MIN_VALUE;
    int value2;
    for (int value3 = value2 = 0; value3 < match.length; value3 = ++value2) {
      if (match[value2] == value && value2 != 0) {
        this.constraintViolated(
            lOOKUPSWITCH, "Match '" + match[value2] + "' occurs more than once.");
      }
      if (match[value2] < value) {
        this.constraintViolated(lOOKUPSWITCH, "Lookup table must be sorted but isn't.");
      } else {
        value = match[value2];
      }
    }
  }

  @Override
  public void visitCHECKCAST(CHECKCAST cHECKCAST) {
    this.indexValid(cHECKCAST, cHECKCAST.getIndex());
    Constant constant;
    if (!((constant = this.constantPoolGen.getConstant(cHECKCAST.getIndex()))
        instanceof ConstantClass)) {
      this.constraintViolated(
          cHECKCAST, "Expecting a CONSTANT_Class operand, but found a '" + constant + "'.");
    }
  }

  public void indexValid(Instruction instruction, int value) {
    if (value < 0 || value >= this.constantPoolGen.getSize()) {
      this.constraintViolated(instruction, "Illegal constant pool index '" + value + "'.");
    }
  }

  @Override
  public void visitINVOKESPECIAL(INVOKESPECIAL nVOKESPECIAL) {
    Method method;
    Method method2;
    Method method3;
    try {
      JavaClass javaClass;
      Method[] methods =
          (javaClass = Repository.lookupClass(nVOKESPECIAL.getText2(this.constantPoolGen)))
              .getMethods();
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= methods.length) {
          method3 = (Method) (Object) null;
          break;
        }
        if (methods[value].getText().equals(nVOKESPECIAL.getText4(this.constantPoolGen))
            && Type.getReturnType(methods[value].getText2())
                .equals(nVOKESPECIAL.getType2(this.constantPoolGen))
            && this.matchesValues(
                Type.getArgumentTypes(methods[value].getText2()),
                nVOKESPECIAL.getArgumentTypes(this.constantPoolGen))) {
          method3 = methods[value];
          break;
        }
        value2 = ++value;
      }
      if (method3 == null) {
        this.constraintViolated(
            nVOKESPECIAL,
            "Referenced method '"
                + nVOKESPECIAL.getText4(this.constantPoolGen)
                + "' with expected signature '"
                + nVOKESPECIAL.getText3(this.constantPoolGen)
                + "' not found in class '"
                + javaClass.getText2()
                + "'. The native verifier does allow the method to be declared in some superclass"
                + " or implemented interface, which the Java Virtual Machine Specification, Second"
                + " Edition does not.");
      }
      JavaClass javaClass2;
      if ((javaClass2 =
                  Repository.lookupClass(Pass3aVerifier.access$000(this.this$0).getClassName()))
              .isEnabled16()
          && Repository.matchesJavaClass(javaClass2, javaClass)
          && !javaClass2.equals(javaClass)
          && !nVOKESPECIAL.getText4(this.constantPoolGen).equals("<init>")) {
        int value3 = -1;
        Method method4 = null;
        while (true) {
          if (value3 == 0) {
            method2 = method4;
            break;
          }
          value3 = javaClass2.getCount2();
          Method[] methods2 =
              (javaClass2 = Repository.lookupClass(javaClass2.getText5())).getMethods();
          int value4;
          value2 = value4 = 0;
          while (true) {
            if (value2 >= methods2.length) {
              method = method4;
              break;
            }
            if (methods2[value4].getText().equals(nVOKESPECIAL.getText4(this.constantPoolGen))
                && Type.getReturnType(methods2[value4].getText2())
                    .equals(nVOKESPECIAL.getType2(this.constantPoolGen))
                && this.matchesValues(
                    Type.getArgumentTypes(methods2[value4].getText2()),
                    nVOKESPECIAL.getArgumentTypes(this.constantPoolGen))) {
              method = method4 = methods2[value4];
              break;
            }
            value2 = ++value4;
          }
          if (method != null) {
            method2 = method4;
            break;
          }
        }
        if (method2 == null) {
          this.constraintViolated(
              nVOKESPECIAL,
              "ACC_SUPER special lookup procedure not successful: method '"
                  + nVOKESPECIAL.getText4(this.constantPoolGen)
                  + "' with proper signature not declared in superclass hierarchy.");
          return;
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitFLOAD(FLOAD fLOAD) {
    int value;
    if ((value = fLOAD.getIndex()) < 0) {
      this.constraintViolated(fLOAD, "Index '" + value + "' must be non-negative.");
    } else {
      int value2 = this.maxLocals() - 1;
      if (value > value2) {
        this.constraintViolated(
            fLOAD, "Index '" + value + "' must not be greater than max_locals-1 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitISTORE(ISTORE sTORE) {
    int value;
    if ((value = sTORE.getIndex()) < 0) {
      this.constraintViolated(sTORE, "Index '" + value + "' must be non-negative.");
    } else {
      int value2 = this.maxLocals() - 1;
      if (value > value2) {
        this.constraintViolated(
            sTORE, "Index '" + value + "' must not be greater than max_locals-1 '" + value2 + "'.");
      }
    }
  }

  public int maxLocals() {
    try {
      return Repository.lookupClass(Pass3aVerifier.access$000(this.this$0).getClassName())
          .getMethods()[Pass3aVerifier.access$100(this.this$0)]
          .getCode()
          .getMax_locals();
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitGETSTATIC(GETSTATIC gETSTATIC) {
    Field field;
    try {
      String fieldName = gETSTATIC.getFieldName(this.constantPoolGen);
      Field[] fields =
          Repository.lookupClass(gETSTATIC.createObjectType(this.constantPoolGen).getClassName())
              .getFields();
      Field field2 = null;
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= fields.length) {
          field = field2;
          break;
        }
        if (fields[value].getText().equals(fieldName)) {
          field = field2 = fields[value];
          break;
        }
        value2 = ++value;
      }
      if (field == null) {
        throw new AssertionViolatedException("Field not found?!?");
      }
      if (!field2.isEnabled11()) {
        this.constraintViolated(
            gETSTATIC, "Referenced field '" + field2 + "' is not static which it should be.");
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitALOAD(ALOAD aLOAD) {
    int value;
    if ((value = aLOAD.getIndex()) < 0) {
      this.constraintViolated(aLOAD, "Index '" + value + "' must be non-negative.");
    } else {
      int value2 = this.maxLocals() - 1;
      if (value > value2) {
        this.constraintViolated(
            aLOAD, "Index '" + value + "' must not be greater than max_locals-1 '" + value2 + "'.");
      }
    }
  }

  @Override
  public void visitINVOKEINTERFACE(INVOKEINTERFACE nVOKEINTERFACE) {
    Method method;
    try {
      JavaClass javaClass;
      Method[] methods =
          (javaClass = Repository.lookupClass(nVOKEINTERFACE.getText2(this.constantPoolGen)))
              .getMethods();
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= methods.length) {
          method = (Method) (Object) null;
          break;
        }
        if (methods[value].getText().equals(nVOKEINTERFACE.getText4(this.constantPoolGen))
            && Type.getReturnType(methods[value].getText2())
                .equals(nVOKEINTERFACE.getType2(this.constantPoolGen))
            && this.matchesValues(
                Type.getArgumentTypes(methods[value].getText2()),
                nVOKEINTERFACE.getArgumentTypes(this.constantPoolGen))) {
          method = methods[value];
          break;
        }
        value2 = ++value;
      }
      if (method == null) {
        this.constraintViolated(
            nVOKEINTERFACE,
            "Referenced method '"
                + nVOKEINTERFACE.getText4(this.constantPoolGen)
                + "' with expected signature '"
                + nVOKEINTERFACE.getText3(this.constantPoolGen)
                + "' not found in class '"
                + javaClass.getText2()
                + "'. The native verifier does allow the method to be declared in some"
                + " superinterface, which the Java Virtual Machine Specification, Second Edition"
                + " does not.");
      }
      if (javaClass.isEnabled17()) {
        this.constraintViolated(
            nVOKEINTERFACE,
            "Referenced class '"
                + javaClass.getText2()
                + "' is a class, but not an interface as expected.");
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitINVOKESTATIC(INVOKESTATIC nVOKESTATIC) {
    Method method;
    try {
      JavaClass javaClass;
      Method[] methods =
          (javaClass = Repository.lookupClass(nVOKESTATIC.getText2(this.constantPoolGen)))
              .getMethods();
      Method method2 = null;
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= methods.length) {
          method = method2;
          break;
        }
        if (methods[value].getText().equals(nVOKESTATIC.getText4(this.constantPoolGen))
            && Type.getReturnType(methods[value].getText2())
                .equals(nVOKESTATIC.getType2(this.constantPoolGen))
            && this.matchesValues(
                Type.getArgumentTypes(methods[value].getText2()),
                nVOKESTATIC.getArgumentTypes(this.constantPoolGen))) {
          method = method2 = methods[value];
          break;
        }
        value2 = ++value;
      }
      if (method == null) {
        this.constraintViolated(
            nVOKESTATIC,
            "Referenced method '"
                + nVOKESTATIC.getText4(this.constantPoolGen)
                + "' with expected signature '"
                + nVOKESTATIC.getText3(this.constantPoolGen)
                + "' not found in class '"
                + javaClass.getText2()
                + "'. The native verifier possibly allows the method to be declared in some"
                + " superclass or implemented interface, which the Java Virtual Machine"
                + " Specification, Second Edition does not.");
      } else if (!method2.isEnabled11()) {
        this.constraintViolated(
            nVOKESTATIC,
            "Referenced method '"
                + nVOKESTATIC.getText4(this.constantPoolGen)
                + "' has ACC_STATIC unset.");
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }
}
