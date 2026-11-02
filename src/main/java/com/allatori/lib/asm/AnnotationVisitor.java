package com.allatori.lib.asm;

public abstract class AnnotationVisitor {

  public final int api;

  public AnnotationVisitor av;

  public void visitEnum(String text, String text2, String text3) {
    if (this.av != null) {
      this.av.visitEnum(text, text2, text3);
    }
  }

  public AnnotationVisitor(int api, AnnotationVisitor annotationVisitor) {
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
    this.av = annotationVisitor;
  }

  public static String stringConcat$0(int value) {
    return "Unsupported api " + value;
  }

  public AnnotationVisitor visitAnnotation(String text, String text2) {
    return this.av != null ? this.av.visitAnnotation(text, text2) : null;
  }

  public AnnotationVisitor(int value) {
    this(value, null);
  }

  public AnnotationVisitor visitArray(String text) {
    return this.av != null ? this.av.visitArray(text) : null;
  }

  public void visitEnd() {
    if (this.av != null) {
      this.av.visitEnd();
    }
  }

  public AnnotationVisitor getDelegate() {
    return this.av;
  }

  public void visit(String text, Object result) {
    if (this.av != null) {
      this.av.visit(text, result);
    }
  }
}
