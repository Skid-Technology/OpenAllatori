package com.allatori.lib.asm;

public abstract class ClassVisitor {

  public final int api;

  public ClassVisitor cv;

  public AnnotationVisitor visitAnnotation(String text, boolean matches) {
    return this.cv != null ? this.cv.visitAnnotation(text, matches) : null;
  }

  public void visitSource(String text, String text2) {
    if (this.cv != null) {
      this.cv.visitSource(text, text2);
    }
  }

  public void visitPermittedSubclass(String text) {
    if (this.api < 589824) {
      throw new UnsupportedOperationException("PermittedSubclasses requires ASM9");
    }
    if (this.cv != null) {
      this.cv.visitPermittedSubclass(text);
    }
  }

  public ClassVisitor(int value) {
    this(value, null);
  }

  public void visitOuterClass(String text, String text2, String text3) {
    if (this.cv != null) {
      this.cv.visitOuterClass(text, text2, text3);
    }
  }

  public void visit(
      int value, int value2, String text, String text2, String text3, String[] texts) {
    if (this.api < 524288 && (value2 & 65536) != 0) {
      throw new UnsupportedOperationException("Records requires ASM8");
    }
    if (this.cv != null) {
      this.cv.visit(value, value2, text, text2, text3, texts);
    }
  }

  public ClassVisitor getDelegate() {
    return this.cv;
  }

  public FieldVisitor visitField(
      int value, String text, String text2, String text3, Object result) {
    return this.cv != null ? this.cv.visitField(value, text, text2, text3, result) : null;
  }

  public ModuleVisitor visitModule(String text, int value, String text2) {
    if (this.api < 393216) {
      throw new UnsupportedOperationException("Module requires ASM6");
    } else {
      return this.cv != null ? this.cv.visitModule(text, value, text2) : null;
    }
  }

  public AnnotationVisitor visitTypeAnnotation(
      int value, TypePath typePath, String text, boolean matches) {
    if (this.api < 327680) {
      throw new UnsupportedOperationException("TypeAnnotation requires ASM5");
    } else {
      return this.cv != null ? this.cv.visitTypeAnnotation(value, typePath, text, matches) : null;
    }
  }

  public void visitInnerClass(String text, String text2, String text3, int value) {
    if (this.cv != null) {
      this.cv.visitInnerClass(text, text2, text3, value);
    }
  }

  public void visitEnd() {
    if (this.cv != null) {
      this.cv.visitEnd();
    }
  }

  public void visitNestMember(String text) {
    if (this.api < 458752) {
      throw new UnsupportedOperationException("NestMember requires ASM7");
    }
    if (this.cv != null) {
      this.cv.visitNestMember(text);
    }
  }

  public MethodVisitor visitMethod(
      int value, String text, String text2, String text3, String[] texts) {
    return this.cv != null ? this.cv.visitMethod(value, text, text2, text3, texts) : null;
  }

  public static String stringConcat$0(int value) {
    return "Unsupported api " + value;
  }

  public void visitNestHost(String text) {
    if (this.api < 458752) {
      throw new UnsupportedOperationException("NestHost requires ASM7");
    }
    if (this.cv != null) {
      this.cv.visitNestHost(text);
    }
  }

  public ClassVisitor(int api, ClassVisitor classVisitor) {
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
    this.cv = classVisitor;
  }

  public void visitAttribute(Attribute attribute) {
    if (this.cv != null) {
      this.cv.visitAttribute(attribute);
    }
  }

  public RecordComponentVisitor visitRecordComponent(String text, String text2, String text3) {
    if (this.api < 524288) {
      throw new UnsupportedOperationException("Record requires ASM8");
    } else {
      return this.cv != null ? this.cv.visitRecordComponent(text, text2, text3) : null;
    }
  }
}
