package com.allatori.lib.asm;

public final class FieldWriter extends FieldVisitor {

  public final int accessFlags;

  public AnnotationWriter annotationWriter;

  public AnnotationWriter annotationWriter2;

  public AnnotationWriter annotationWriter3;

  public final int descriptorIndex;

  public final int nameIndex;

  public int signatureIndex;

  public int constantValueIndex;

  public Attribute firstAttribute;

  public final SymbolTable symbolTable;

  public AnnotationWriter annotationWriter4;

  @Override
  public AnnotationVisitor visitAnnotation(String text, boolean matches) {
    return matches
        ? (this.annotationWriter4 =
            AnnotationWriter.create(this.symbolTable, text, this.annotationWriter4))
        : (this.annotationWriter2 =
            AnnotationWriter.create(this.symbolTable, text, this.annotationWriter2));
  }

  @Override
  public void visitAttribute(Attribute attribute) {
    attribute.nextAttribute = this.firstAttribute;
    this.firstAttribute = attribute;
  }

  public FieldWriter(
      SymbolTable symbolTable2,
      int accessFlags,
      String text,
      String text2,
      String text3,
      Object result) {
    super(589824);
    this.symbolTable = symbolTable2;
    this.accessFlags = accessFlags;
    this.nameIndex = symbolTable2.addConstantUtf8(text);
    this.descriptorIndex = symbolTable2.addConstantUtf8(text2);
    if (text3 != null) {
      this.signatureIndex = symbolTable2.addConstantUtf8(text3);
    }
    if (result != null) {
      this.constantValueIndex = symbolTable2.addConstant(result).index;
    }
  }

  @Override
  public AnnotationVisitor visitTypeAnnotation(
      int value, TypePath typePath, String text, boolean matches) {
    return matches
        ? (this.annotationWriter =
            AnnotationWriter.create(this.symbolTable, value, typePath, text, this.annotationWriter))
        : (this.annotationWriter3 =
            AnnotationWriter.create(
                this.symbolTable, value, typePath, text, this.annotationWriter3));
  }

  public int computeFieldInfoSize() {
    int value = 8;
    if (this.constantValueIndex != 0) {
      SymbolTable symbolTable2 = this.symbolTable;
      value += 8;
      symbolTable2.addConstantUtf8("ConstantValue");
    }
    int value2 =
        value
            + Attribute.computeAttributesSize(
                this.symbolTable, this.accessFlags, this.signatureIndex)
            + AnnotationWriter.computeAnnotationsSize(
                this.annotationWriter4,
                this.annotationWriter2,
                this.annotationWriter,
                this.annotationWriter3);
    if (this.firstAttribute != null) {
      value2 += this.firstAttribute.computeAttributesSize(this.symbolTable);
    }
    return value2;
  }

  @Override
  public void visitEnd() {}

  public final void collectAttributePrototypes(Attribute$Set attribute$Set) {
    attribute$Set.addAttributes(this.firstAttribute);
  }

  public void putFieldInfo(ByteVector byteVector) {
    int value = this.symbolTable.getCount() < 49 ? 1 : 0;
    int value2 = value != 0 ? 4096 : 0;
    byteVector
        .putShort(this.accessFlags & ~value2)
        .putShort(this.nameIndex)
        .putShort(this.descriptorIndex);
    int value3 = 0;
    if (this.constantValueIndex != 0) {
      value3++;
    }
    if ((this.accessFlags & 4096) != 0 && value != 0) {
      value3++;
    }
    if (this.signatureIndex != 0) {
      value3++;
    }
    if ((this.accessFlags & 131072) != 0) {
      value3++;
    }
    if (this.annotationWriter4 != null) {
      value3++;
    }
    if (this.annotationWriter2 != null) {
      value3++;
    }
    if (this.annotationWriter != null) {
      value3++;
    }
    if (this.annotationWriter3 != null) {
      value3++;
    }
    if (this.firstAttribute != null) {
      value3 += this.firstAttribute.getAttributeCount();
    }
    byteVector.putShort(value3);
    if (this.constantValueIndex != 0) {
      byteVector
          .putShort(this.symbolTable.addConstantUtf8("ConstantValue"))
          .putInt(2)
          .putShort(this.constantValueIndex);
    }
    Attribute.putAttributes(this.symbolTable, this.accessFlags, this.signatureIndex, byteVector);
    AnnotationWriter.putAnnotations(
        this.symbolTable,
        this.annotationWriter4,
        this.annotationWriter2,
        this.annotationWriter,
        this.annotationWriter3,
        byteVector);
    if (this.firstAttribute != null) {
      this.firstAttribute.putAttributes(this.symbolTable, byteVector);
    }
  }
}
