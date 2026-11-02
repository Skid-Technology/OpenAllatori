package com.allatori.lib.bcel.verifier.exc;

public abstract class VerificationException extends VerifierConstraintViolatedException {

  public VerificationException(String text) {
    super(text);
  }

  public VerificationException() {}
}
