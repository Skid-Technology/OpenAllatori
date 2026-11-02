package com.allatori.lib.asm.signature;

public abstract class SignatureVisitor {

  public static final char INSTANCEOF = '=';

  public static final char SUPER = '-';

  public static final char EXTENDS = '+';

  public final int api;

  public void update() {}

  public void visitBaseType(char value) {}

  public SignatureVisitor getSignatureVisitor() {
    return this;
  }

  public void processText(String text) {}

  public SignatureVisitor visitTypeArgument(char value) {
    return this;
  }

  public static String stringConcat$0(int value) {
    return "Unsupported api " + value;
  }

  public void processText2(String text) {}

  public SignatureVisitor getSignatureVisitor2() {
    return this;
  }

  public SignatureVisitor getSignatureVisitor3() {
    return this;
  }

  public SignatureVisitor getSignatureVisitor4() {
    return this;
  }

  public SignatureVisitor getSignatureVisitor5() {
    return this;
  }

  public SignatureVisitor getSignatureVisitor6() {
    return this;
  }

  public void update2() {}

  public SignatureVisitor getSignatureVisitor7() {
    return this;
  }

  public SignatureVisitor(int api) {
    super();
    if (api != 589824
        && api != 524288
        && api != 458752
        && api != 393216
        && api != 327680
        && api != 262144
        && api != 17432576) {
      throw new IllegalArgumentException(stringConcat$0(api));
    }
    this.api = api;
  }

  public SignatureVisitor getSignatureVisitor8() {
    return this;
  }

  public void processText3(String text) {}

  public void processText4(String text) {}
}
