package com.allatori.lib.asm.signature;

public class SignatureWriter extends SignatureVisitor {

  public int argumentStack;

  public boolean enabled;

  public final StringBuilder stringBuilder;

  public boolean enabled2;

  @Override
  public SignatureVisitor getSignatureVisitor5() {
    return this;
  }

  @Override
  public void visitBaseType(char value) {
    this.stringBuilder.append(value);
  }

  public SignatureWriter(StringBuilder stringBuilder2) {
    super(589824);
    this.argumentStack = 1;
    this.stringBuilder = stringBuilder2;
  }

  @Override
  public SignatureVisitor getSignatureVisitor() {
    this.endFormals();
    if (!this.enabled) {
      this.enabled = true;
      this.stringBuilder.append('(');
    }
    return this;
  }

  @Override
  public void processText2(String text) {
    this.endArguments();
    this.stringBuilder.append('.').append(text);
    this.argumentStack <<= 1;
  }

  @Override
  public SignatureVisitor getSignatureVisitor8() {
    this.endFormals();
    if (!this.enabled) {
      this.stringBuilder.append('(');
    }
    this.stringBuilder.append(')');
    return this;
  }

  @Override
  public SignatureVisitor getSignatureVisitor6() {
    this.stringBuilder.append(':');
    return this;
  }

  @Override
  public SignatureVisitor getSignatureVisitor3() {
    this.endFormals();
    return this;
  }

  @Override
  public void processText(String text) {
    this.stringBuilder.append('L').append(text);
    this.argumentStack <<= 1;
  }

  @Override
  public void update2() {
    if ((this.argumentStack & 1) == 0) {
      this.argumentStack |= 1;
      this.stringBuilder.append('<');
    }
    this.stringBuilder.append('*');
  }

  @Override
  public void update() {
    this.endArguments();
    this.stringBuilder.append(';');
  }

  @Override
  public SignatureVisitor visitTypeArgument(char value) {
    if ((this.argumentStack & 1) == 0) {
      this.argumentStack |= 1;
      this.stringBuilder.append('<');
    }
    if (value != '=') {
      this.stringBuilder.append(value);
    }
    return (this.argumentStack & -2147483648) == 0 ? this : new SignatureWriter(this.stringBuilder);
  }

  public SignatureWriter() {
    this(new StringBuilder());
  }

  @Override
  public SignatureVisitor getSignatureVisitor2() {
    return this;
  }

  @Override
  public SignatureVisitor getSignatureVisitor7() {
    this.stringBuilder.append('[');
    return this;
  }

  @Override
  public void processText4(String text) {
    if (!this.enabled2) {
      this.enabled2 = true;
      this.stringBuilder.append('<');
    }
    this.stringBuilder.append(text).append(':');
  }

  public void endArguments() {
    if ((this.argumentStack & 1) == 1) {
      this.stringBuilder.append('>');
    }
    this.argumentStack >>>= 1;
  }

  public void endFormals() {
    if (this.enabled2) {
      this.enabled2 = false;
      this.stringBuilder.append('>');
    }
  }

  @Override
  public SignatureVisitor getSignatureVisitor4() {
    this.stringBuilder.append('^');
    return this;
  }

  @Override
  public void processText3(String text) {
    this.stringBuilder.append('T').append(text).append(';');
  }
}
