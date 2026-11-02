package com.allatori.internal;

import com.allatori.lib.bcel.verifier.exc.VerifierConstraintViolatedException;

public class VerifierConstraintViolatedExceptionSupport
    extends VerifierConstraintViolatedException {

  public VerifierConstraintViolatedExceptionSupport(String text) {
    super(text);
  }

  public VerifierConstraintViolatedExceptionSupport() {}
}
