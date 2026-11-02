package com.allatori.lib.bcel.verifier.statics;

import com.allatori.internal.VerifierConstraintViolatedExceptionSupport;
import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.ClassFormatException;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.verifier.PassVerifier;
import com.allatori.lib.bcel.verifier.VerificationResult;
import com.allatori.lib.bcel.verifier.Verifier;
import com.allatori.lib.bcel.verifier.exc.Utility;

public final class Pass1Verifier extends PassVerifier {

  public JavaClass jc;

  public Verifier myOwner;

  public JavaClass getJavaClass() {
    if (this.jc == null) {
      try {
        this.jc = Repository.lookupClass(this.myOwner.getClassName());
      } catch (ClassNotFoundException classNotFoundException) {
        return this.jc;
      }
      return this.jc;
    } else {
      return this.jc;
    }
  }

  @Override
  public VerificationResult getVerificationResult() {
    JavaClass javaClass;
    try {
      if ((javaClass = this.getJavaClass()) != null
          && !this.myOwner.getClassName().equals(javaClass.getText2())) {
        throw new VerifierConstraintViolatedExceptionSupport(
            ("Wrong name: the internal name of the .class file '"
                + javaClass.getText2()
                + "' does not match the file's name '"
                + this.myOwner.getClassName()
                + "'."));
      }
    } catch (
        VerifierConstraintViolatedExceptionSupport verifierConstraintViolatedExceptionSupport) {
      return new VerificationResult(2, verifierConstraintViolatedExceptionSupport.getMessage());
    } catch (ClassFormatException classFormatException) {
      return new VerificationResult(2, classFormatException.getMessage());
    } catch (RuntimeException runtimeException) {
      return new VerificationResult(
          2,
          "Parsing via BCEL did not succeed. "
              + runtimeException.getClass().getName()
              + " occured:\n"
              + Utility.getStackTrace(runtimeException));
    }
    return javaClass != null
        ? VerificationResult.VERIFICATION_RESULT2
        : new VerificationResult(2, "Repository.lookup() failed. FILE NOT FOUND?");
  }

  public Pass1Verifier(Verifier verifier) {
    super();
    this.myOwner = verifier;
  }

  @Override
  public String[] getMessages() {
    return super.getMessages();
  }
}
