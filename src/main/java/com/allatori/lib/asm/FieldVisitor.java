package com.allatori.lib.asm;

public abstract class FieldVisitor {

  public FieldVisitor fv;

  public final int api;

  public AnnotationVisitor visitAnnotation(String text, boolean matches) {
    return this.fv != null ? this.fv.visitAnnotation(text, matches) : null;
  }

  public void visitEnd() {
    if (this.fv != null) {
      this.fv.visitEnd();
    }
  }

  public static String stringConcat$0(int value) {
    return "Unsupported api " + value;
  }

  public AnnotationVisitor visitTypeAnnotation(
      int value, TypePath typePath, String text, boolean matches) {
    if (this.api < 327680) {
      throw new UnsupportedOperationException("This feature requires ASM5");
    } else {
      return this.fv != null ? this.fv.visitTypeAnnotation(value, typePath, text, matches) : null;
    }
  }

  public void visitAttribute(Attribute attribute) {
    if (this.fv != null) {
      this.fv.visitAttribute(attribute);
    }
  }

  public FieldVisitor getDelegate() {
    return this.fv;
  }

  public FieldVisitor(int value) {
    this(value, null);
  }

  public FieldVisitor(int api, FieldVisitor fieldVisitor) {
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
    this.fv = fieldVisitor;
  }
}
