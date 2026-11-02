package com.allatori.lib.bcel.verifier.exc;

public abstract class CodeConstraintException extends VerificationException {

  public CodeConstraintException(String text) {
    super(text);
  }

  public CodeConstraintException() {}
}
