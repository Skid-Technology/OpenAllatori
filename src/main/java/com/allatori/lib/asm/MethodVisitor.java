package com.allatori.lib.asm;

public abstract class MethodVisitor {

  public static final String TEXT = "This feature requires ASM5";

  public final int api;

  public MethodVisitor mv;

  public AnnotationVisitor createAnnotationVisitor(
      int value, TypePath typePath, String text, boolean matches) {
    if (this.api < 327680) {
      throw new UnsupportedOperationException("This feature requires ASM5");
    } else {
      return this.mv != null
          ? this.mv.createAnnotationVisitor(value, typePath, text, matches)
          : null;
    }
  }

  public void visitFieldInsn(int value, String text, String text2, String text3)
      throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitFieldInsn(value, text, text2, text3);
    }
  }

  public MethodVisitor getDelegate() {
    return this.mv;
  }

  public static String stringConcat$0(int value) {
    return "Unsupported api " + value;
  }

  public AnnotationVisitor visitParameterAnnotation(int value, String text, boolean matches) {
    return this.mv != null ? this.mv.visitParameterAnnotation(value, text, matches) : null;
  }

  public void visitTypeInsn(int value, String text) throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitTypeInsn(value, text);
    }
  }

  public void visitLabel(Label label) {
    if (this.mv != null) {
      this.mv.visitLabel(label);
    }
  }

  public void visitEnd() {
    if (this.mv != null) {
      this.mv.visitEnd();
    }
  }

  public void visitCode() {
    if (this.mv != null) {
      this.mv.visitCode();
    }
  }

  public void visitIntInsn(int value, int value2) throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitIntInsn(value, value2);
    }
  }

  public void visitMaxs(int value, int value2) throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitMaxs(value, value2);
    }
  }

  @Deprecated
  public void visitMethodInsn(int value, String text, String text2, String text3)
      throws ClassNotFoundException {
    int value2 = value | (this.api < 327680 ? 256 : 0);
    this.visitMethodInsn(value2, text, text2, text3, value == 185);
  }

  public MethodVisitor(int api, MethodVisitor methodVisitor) {
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
    this.mv = methodVisitor;
  }

  public AnnotationVisitor visitLocalVariableAnnotation(
      int value,
      TypePath typePath,
      Label[] labels,
      Label[] labels2,
      int[] values,
      String text,
      boolean matches) {
    if (this.api < 327680) {
      throw new UnsupportedOperationException("This feature requires ASM5");
    } else {
      return this.mv != null
          ? this.mv.visitLocalVariableAnnotation(
              value, typePath, labels, labels2, values, text, matches)
          : null;
    }
  }

  public void visitTableSwitchInsn(int value, int value2, Label label, Label... label2)
      throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitTableSwitchInsn(value, value2, label, (Label[]) label2);
    }
  }

  public void visitAttribute(Attribute attribute) {
    if (this.mv != null) {
      this.mv.visitAttribute(attribute);
    }
  }

  public AnnotationVisitor visitAnnotation(String text, boolean matches) {
    return this.mv != null ? this.mv.visitAnnotation(text, matches) : null;
  }

  public void visitLdcInsn(Object result) throws ClassNotFoundException {
    if (this.api >= 327680
        || !(result instanceof Handle)
            && (!(result instanceof Type) || ((Type) result).getSort() != 11)) {
      if (this.api < 458752 && result instanceof ConstantDynamic) {
        throw new UnsupportedOperationException("This feature requires ASM7");
      }
      if (this.mv != null) {
        this.mv.visitLdcInsn(result);
      }
    } else {
      throw new UnsupportedOperationException("This feature requires ASM5");
    }
  }

  public void visitLookupSwitchInsn(Label label, int[] values, Label[] labels)
      throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitLookupSwitchInsn(label, values, labels);
    }
  }

  public AnnotationVisitor visitAnnotationDefault() {
    return this.mv != null ? this.mv.visitAnnotationDefault() : null;
  }

  public void visitLineNumber(int value, Label label) {
    if (this.mv != null) {
      this.mv.visitLineNumber(value, label);
    }
  }

  public AnnotationVisitor createAnnotationVisitor2(
      int value, TypePath typePath, String text, boolean matches) {
    if (this.api < 327680) {
      throw new UnsupportedOperationException("This feature requires ASM5");
    } else {
      return this.mv != null
          ? this.mv.createAnnotationVisitor2(value, typePath, text, matches)
          : null;
    }
  }

  public void visitParameter(String text, int value) {
    if (this.api < 327680) {
      throw new UnsupportedOperationException("This feature requires ASM5");
    }
    if (this.mv != null) {
      this.mv.visitParameter(text, value);
    }
  }

  public void visitFrame(int value, int value2, Object[] results, int value3, Object[] results2) {
    if (this.mv != null) {
      this.mv.visitFrame(value, value2, results, value3, results2);
    }
  }

  public void visitMultiANewArrayInsn(String text, int value) throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitMultiANewArrayInsn(text, value);
    }
  }

  public AnnotationVisitor createAnnotationVisitor3(
      int value, TypePath typePath, String text, boolean matches) {
    if (this.api < 327680) {
      throw new UnsupportedOperationException("This feature requires ASM5");
    } else {
      return this.mv != null
          ? this.mv.createAnnotationVisitor3(value, typePath, text, matches)
          : null;
    }
  }

  public void visitTryCatchBlock(Label label, Label label2, Label label3, String text) {
    if (this.mv != null) {
      this.mv.visitTryCatchBlock(label, label2, label3, text);
    }
  }

  public MethodVisitor(int value) {
    this(value, null);
  }

  public void visitInvokeDynamicInsn(String text, String text2, Handle handle, Object... result)
      throws ClassNotFoundException {
    if (this.api < 327680) {
      throw new UnsupportedOperationException("This feature requires ASM5");
    }
    if (this.mv != null) {
      this.mv.visitInvokeDynamicInsn(text, text2, handle, (Object[]) result);
    }
  }

  public void visitIincInsn(int value, int value2) throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitIincInsn(value, value2);
    }
  }

  public void visitMethodInsn(int value, String text, String text2, String text3, boolean matches)
      throws ClassNotFoundException {
    if (this.api < 327680 && (value & 256) == 0) {
      if (matches != (value == 185)) {
        throw new UnsupportedOperationException("INVOKESPECIAL/STATIC on interfaces requires ASM5");
      }
      this.visitMethodInsn(value, text, text2, text3);
    } else {
      if (this.mv != null) {
        this.mv.visitMethodInsn(value & -257, text, text2, text3, (boolean) matches);
      }
    }
  }

  public void visitAnnotableParameterCount(int value, boolean matches) {
    if (this.mv != null) {
      this.mv.visitAnnotableParameterCount(value, matches);
    }
  }

  public void visitInsn(int value) throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitInsn(value);
    }
  }

  public void visitVarInsn(int value, int value2) throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitVarInsn(value, value2);
    }
  }

  public void visitJumpInsn(int value, Label label) throws ClassNotFoundException {
    if (this.mv != null) {
      this.mv.visitJumpInsn(value, label);
    }
  }

  public void visitLocalVariable(
      String text, String text2, String text3, Label label, Label label2, int value) {
    if (this.mv != null) {
      this.mv.visitLocalVariable(text, text2, text3, label, label2, value);
    }
  }
}
