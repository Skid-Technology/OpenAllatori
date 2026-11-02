package com.allatori.lib.bcel.verifier.exc;

public final class AssertionViolatedException extends RuntimeException {

  public String detailMessage;

  public AssertionViolatedException(String text) {
    super("INTERNAL ERROR: " + text);
    this.detailMessage = super.getMessage();
  }

  public static void main(String[] texts) {
    AssertionViolatedException assertionViolatedException = new AssertionViolatedException("Oops!");
    assertionViolatedException.extendMessage("\nFOUND:\n\t", "\nExiting!!\n");
    throw assertionViolatedException;
  }

  @Override
  public String getMessage() {
    return this.detailMessage;
  }

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

  public AssertionViolatedException() {}
}
