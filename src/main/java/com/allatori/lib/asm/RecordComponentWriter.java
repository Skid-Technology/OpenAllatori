package com.allatori.lib.asm;

public final class RecordComponentWriter extends RecordComponentVisitor {

  public final int nameIndex;

  public int signatureIndex;

  public AnnotationWriter annotationWriter;

  public AnnotationWriter annotationWriter2;

  public AnnotationWriter annotationWriter3;

  public final SymbolTable symbolTable;

  public AnnotationWriter annotationWriter4;

  public Attribute firstAttribute;

  public final int descriptorIndex;

  public final void collectAttributePrototypes(Attribute$Set attribute$Set) {
    attribute$Set.addAttributes(this.firstAttribute);
  }

  @Override
  public void visitEnd() {}

  public int computeRecordComponentInfoSize() {
    int value =
        6
            + Attribute.computeAttributesSize(this.symbolTable, 0, this.signatureIndex)
            + AnnotationWriter.computeAnnotationsSize(
                this.annotationWriter4,
                this.annotationWriter2,
                this.annotationWriter,
                this.annotationWriter3);
    if (this.firstAttribute != null) {
      value += this.firstAttribute.computeAttributesSize(this.symbolTable);
    }
    return value;
  }

  @Override
  public AnnotationVisitor visitAnnotation(String text, boolean matches) {
    return matches
        ? (this.annotationWriter4 =
            AnnotationWriter.create(this.symbolTable, text, this.annotationWriter4))
        : (this.annotationWriter2 =
            AnnotationWriter.create(this.symbolTable, text, this.annotationWriter2));
  }

  public void putRecordComponentInfo(ByteVector byteVector) {
    byteVector.putShort(this.nameIndex).putShort(this.descriptorIndex);
    int value = 0;
    if (this.signatureIndex != 0) {
      value++;
    }
    if (this.annotationWriter4 != null) {
      value++;
    }
    if (this.annotationWriter2 != null) {
      value++;
    }
    if (this.annotationWriter != null) {
      value++;
    }
    if (this.annotationWriter3 != null) {
      value++;
    }
    if (this.firstAttribute != null) {
      value += this.firstAttribute.getAttributeCount();
    }
    byteVector.putShort(value);
    Attribute.putAttributes(this.symbolTable, 0, this.signatureIndex, byteVector);
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

  @Override
  public void visitAttribute(Attribute attribute) {
    attribute.nextAttribute = this.firstAttribute;
    this.firstAttribute = attribute;
  }

  public RecordComponentWriter(SymbolTable symbolTable2, String text, String text2, String text3) {
    super(589824);
    this.symbolTable = symbolTable2;
    this.nameIndex = symbolTable2.addConstantUtf8(text);
    this.descriptorIndex = symbolTable2.addConstantUtf8(text2);
    if (text3 != null) {
      this.signatureIndex = symbolTable2.addConstantUtf8(text3);
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
}
