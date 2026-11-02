package com.allatori.lib.asm;

public abstract class RecordComponentVisitor {

  public RecordComponentVisitor delegate;

  public final int api;

  public RecordComponentVisitor(int api, RecordComponentVisitor recordComponentVisitor) {
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
    if (api == 17432576) {
      Constants.checkAsmExperimental(this);
    }
    this.api = api;
    this.delegate = recordComponentVisitor;
  }

  public AnnotationVisitor visitAnnotation(String text, boolean matches) {
    return this.delegate != null ? this.delegate.visitAnnotation(text, matches) : null;
  }

  public static String stringConcat$0(int value) {
    return "Unsupported api " + value;
  }

  public RecordComponentVisitor(int value) {
    this(value, null);
  }

  public AnnotationVisitor visitTypeAnnotation(
      int value, TypePath typePath, String text, boolean matches) {
    return this.delegate != null
        ? this.delegate.visitTypeAnnotation(value, typePath, text, matches)
        : null;
  }

  public RecordComponentVisitor getDelegate() {
    return this.delegate;
  }

  public void visitAttribute(Attribute attribute) {
    if (this.delegate != null) {
      this.delegate.visitAttribute(attribute);
    }
  }

  public void visitEnd() {
    if (this.delegate != null) {
      this.delegate.visitEnd();
    }
  }
}
