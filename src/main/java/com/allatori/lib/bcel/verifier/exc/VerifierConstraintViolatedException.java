package com.allatori.lib.bcel.verifier.exc;

public abstract class VerifierConstraintViolatedException extends RuntimeException {

  public String detailMessage;

  public VerifierConstraintViolatedException(String detailMessage) {
    super(detailMessage);
    this.detailMessage = detailMessage;
  }

  @Override
  public String getMessage() {
    return this.detailMessage;
  }

  public VerifierConstraintViolatedException() {}

  public void extendMessage(String text, String text2) {
    String text3 = text2;
    if (text == null) {
      text = "";
    }
    if (this.detailMessage == null) {
      this.detailMessage = "";
    }
    if (text3 == null) {
      text3 = "";
    }
    this.detailMessage = "" + text + this.detailMessage + text3;
  }
}
