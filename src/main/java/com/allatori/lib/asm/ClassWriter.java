package com.allatori.lib.asm;

public class ClassWriter extends ClassVisitor {

  public int count;

  public int count2;

  public int count3;

  public AnnotationWriter annotationWriter;

  public int count4;

  public AnnotationWriter annotationWriter2;

  public ByteVector byteVector;

  public RecordComponentWriter recordComponentWriter;

  public int count5;

  public AnnotationWriter annotationWriter3;

  public MethodWriter methodWriter;

  public int count6;

  public int count7;

  public int[] interfaces;

  public FieldWriter fieldWriter;

  public final int flags;

  public static final int COMPUTE_MAXS = 1;

  public AnnotationWriter annotationWriter4;

  public ModuleWriter moduleWriter;

  public int count8;

  public int count9;

  public int count10;

  public final SymbolTable symbolTable;

  public int count11;

  public int count12;

  public ByteVector byteVector2;

  public Attribute firstAttribute;

  public RecordComponentWriter recordComponentWriter2;

  public int count13;

  public MethodWriter methodWriter2;

  public static final int COMPUTE_FRAMES = 2;

  public int count14;

  public ByteVector byteVector3;

  public ByteVector byteVector4;

  public FieldWriter fieldWriter2;

  public String getCommonSuperClass(String text, String text2) throws ClassNotFoundException {
    ClassLoader classLoader = this.getClassLoader();
    Class classValue;
    try {
      classValue = Class.forName(text.replace('/', '.'), false, classLoader);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new TypeNotPresentException(text, classNotFoundException);
    }
    Class classValue2 = Class.forName(text2.replace('/', '.'), false, classLoader);
    if (classValue.isAssignableFrom(classValue2)) {
      return text;
    }
    if (classValue2.isAssignableFrom(classValue)) {
      return text2;
    }
    if (!classValue.isInterface() && !classValue2.isInterface()) {
      while (!(classValue = classValue.getSuperclass()).isAssignableFrom(classValue2)) {}
      return classValue.getName().replace('.', '/');
    } else {
      return "java/lang/Object";
    }
  }

  public int calculateCount(String text) {
    return this.symbolTable.getSymbol(text).index;
  }

  @Override
  public final void visitNestMember(String text) {
    if (this.byteVector4 == null) {
      this.byteVector4 = new ByteVector();
    }
    this.count7++;
    this.byteVector4.putShort(this.symbolTable.getSymbol3(text).index);
  }

  @Override
  public final void visitSource(String text, String text2) {
    if (text != null) {
      this.count4 = this.symbolTable.addConstantUtf8(text);
    }
    if (text2 != null) {
      this.byteVector2 = new ByteVector().encodeUtf8(text2, 0, Integer.MAX_VALUE);
    }
  }

  @Override
  public final void visitNestHost(String text) {
    this.count14 = this.symbolTable.getSymbol3(text).index;
  }

  @Override
  public final void visitEnd() {}

  @Override
  public final void visitAttribute(Attribute attribute) {
    attribute.nextAttribute = this.firstAttribute;
    this.firstAttribute = attribute;
  }

  @Override
  public final void visitPermittedSubclass(String text) {
    if (this.byteVector3 == null) {
      this.byteVector3 = new ByteVector();
    }
    this.count9++;
    this.byteVector3.putShort(this.symbolTable.getSymbol3(text).index);
  }

  public Attribute[] getAttributePrototypes() {
    Attribute$Set attribute$Set;
    (attribute$Set = new Attribute$Set()).addAttributes(this.firstAttribute);
    FieldWriter fieldWriter2;
    for (FieldWriter fieldWriter3 = fieldWriter2 = this.fieldWriter;
        fieldWriter3 != null;
        fieldWriter3 = fieldWriter2 = (FieldWriter) fieldWriter2.fv) {
      fieldWriter2.collectAttributePrototypes(attribute$Set);
    }
    MethodWriter methodWriter2;
    for (MethodWriter methodWriter3 = methodWriter2 = this.methodWriter;
        methodWriter3 != null;
        methodWriter3 = methodWriter2 = (MethodWriter) methodWriter2.mv) {
      methodWriter2.collectAttributePrototypes(attribute$Set);
    }
    RecordComponentWriter recordComponentWriter;
    for (RecordComponentWriter recordComponentWriter3 =
            recordComponentWriter = this.recordComponentWriter2;
        recordComponentWriter3 != null;
        recordComponentWriter3 =
            recordComponentWriter = (RecordComponentWriter) recordComponentWriter.delegate) {
      recordComponentWriter.collectAttributePrototypes(attribute$Set);
    }
    return attribute$Set.toArray();
  }

  public int newConst(Object result) {
    return this.symbolTable.addConstant(result).index;
  }

  public int calculateCount2(String text) {
    return this.symbolTable.getSymbol3(text).index;
  }

  public int calculateCount3(String text) {
    return this.symbolTable.getSymbol2(text).index;
  }

  public ClassLoader getClassLoader() {
    return this.getClass().getClassLoader();
  }

  public int newHandle(int value, String text, String text2, String text3, boolean matches) {
    return this.symbolTable.addConstantMethodHandle(value, text, text2, text3, matches).index;
  }

  @Deprecated
  public int newHandle(int value, String text, String text2, String text3) {
    return this.newHandle(value, text, text2, text3, value == 9);
  }

  public int newConstantDynamic(String text, String text2, Handle handle, Object... result) {
    return this.symbolTable.addConstantDynamic(text, text2, handle, (Object[]) result).index;
  }

  @Override
  public final void visit(
      int count10, int count5, String text, String text2, String text3, String[] texts) {
    this.count10 = count10;
    this.count5 = count5;
    this.count2 = this.symbolTable.setMajorVersionAndClassName(count10 & 65535, text);
    if (text2 != null) {
      this.count3 = this.symbolTable.addConstantUtf8(text2);
    }
    this.count12 = text3 == null ? 0 : this.symbolTable.getSymbol3(text3).index;
    if (texts != null && texts.length > 0) {
      this.count6 = texts.length;
      int value3 = 0;
      this.interfaces = new int[this.count6];
      for (int index2 = 0; value3 < this.count6; value3 = index2) {
        int[] values = this.interfaces;
        int value4 = this.symbolTable.getSymbol3(texts[index2]).index;
        int value5 = index2;
        index2++;
        values[value5] = value4;
      }
    }
    if (this.count8 == 1 && (count10 & 65535) >= 51) {
      this.count8 = 2;
    }
  }

  public int newField(String text, String text2, String text3) {
    return this.symbolTable.addConstantFieldref(text, text2, text3).index;
  }

  public int newInvokeDynamic(String text, String text2, Handle handle, Object... result) {
    return this.symbolTable.addConstantInvokeDynamic(text, text2, handle, (Object[]) result).index;
  }

  public boolean hasFlags(int value) {
    return (this.flags & value) == value;
  }

  @Override
  public final RecordComponentVisitor visitRecordComponent(
      String text, String text2, String text3) {
    RecordComponentWriter recordComponentWriter3 =
        new RecordComponentWriter(this.symbolTable, text, text2, text3);
    ClassWriter classWriter;
    if (this.recordComponentWriter2 == null) {
      classWriter = this;
      this.recordComponentWriter2 = recordComponentWriter3;
    } else {
      classWriter = this;
      this.recordComponentWriter.delegate = recordComponentWriter3;
    }
    return classWriter.recordComponentWriter = recordComponentWriter3;
  }

  public byte[] replaceAsmInstructions(byte[] values, boolean matches)
      throws ClassNotFoundException {
    Attribute[] attributePrototypes = this.getAttributePrototypes();
    this.fieldWriter = null;
    this.fieldWriter2 = null;
    this.methodWriter = null;
    this.methodWriter2 = null;
    this.annotationWriter = null;
    this.annotationWriter4 = null;
    this.annotationWriter3 = null;
    this.annotationWriter2 = null;
    this.moduleWriter = null;
    this.count14 = 0;
    this.count7 = 0;
    this.byteVector4 = null;
    this.count9 = 0;
    this.byteVector3 = null;
    this.recordComponentWriter2 = null;
    this.recordComponentWriter = null;
    this.firstAttribute = null;
    this.count8 = matches ? 3 : 0;
    new ClassReader(values, 0, false).accept(this, attributePrototypes, (matches ? 8 : 0) | 256);
    return this.toByteArray();
  }

  public int newNameType(String text, String text2) {
    return this.symbolTable.addConstantNameAndType(text, text2);
  }

  @Override
  public final ModuleVisitor visitModule(String text, int value, String text2) {
    return this.moduleWriter =
        new ModuleWriter(
            this.symbolTable,
            this.symbolTable.getSymbol4(text).index,
            value,
            text2 == null ? 0 : this.symbolTable.addConstantUtf8(text2));
  }

  @Override
  public final AnnotationVisitor visitAnnotation(String text, boolean matches) {
    return matches
        ? (this.annotationWriter =
            AnnotationWriter.create(this.symbolTable, text, this.annotationWriter))
        : (this.annotationWriter4 =
            AnnotationWriter.create(this.symbolTable, text, this.annotationWriter4));
  }

  public ClassWriter(int value) {
    this(null, value);
  }

  public byte[] toByteArray() throws ClassNotFoundException {
    int value = 24 + 2 * this.count6;
    int value2 = 0;
    FieldWriter fieldWriter2;
    for (FieldWriter fieldWriter3 = fieldWriter2 = this.fieldWriter;
        fieldWriter3 != null;
        fieldWriter3 = fieldWriter2 = (FieldWriter) fieldWriter2.fv) {
      int value3 = fieldWriter2.computeFieldInfoSize();
      int value4 = value;
      value2++;
      value = value4 + value3;
    }
    int value5 = 0;
    MethodWriter methodWriter2;
    for (MethodWriter methodWriter3 = methodWriter2 = this.methodWriter;
        methodWriter3 != null;
        methodWriter3 = methodWriter2 = (MethodWriter) methodWriter2.mv) {
      int value6 = methodWriter2.computeMethodInfoSize();
      int value7 = value;
      value5++;
      value = value7 + value6;
    }
    int value8 = 0;
    if (this.byteVector != null) {
      int value9 = value;
      value8++;
      value = value9 + (byte) 8 + this.byteVector.length;
      this.symbolTable.addConstantUtf8("InnerClasses");
    }
    if (this.count11 != 0) {
      value += 10;
      SymbolTable symbolTable2 = this.symbolTable;
      value8++;
      symbolTable2.addConstantUtf8("EnclosingMethod");
    }
    if ((this.count5 & 4096) != 0 && (this.count10 & 65535) < 49) {
      value += 6;
      SymbolTable symbolTable3 = this.symbolTable;
      value8++;
      symbolTable3.addConstantUtf8("Synthetic");
    }
    if (this.count3 != 0) {
      value += 8;
      SymbolTable symbolTable4 = this.symbolTable;
      value8++;
      symbolTable4.addConstantUtf8("Signature");
    }
    if (this.count4 != 0) {
      value += 8;
      SymbolTable symbolTable5 = this.symbolTable;
      value8++;
      symbolTable5.addConstantUtf8("SourceFile");
    }
    if (this.byteVector2 != null) {
      int value10 = value;
      value8++;
      value = value10 + (byte) 6 + this.byteVector2.length;
      this.symbolTable.addConstantUtf8("SourceDebugExtension");
    }
    if ((this.count5 & 131072) != 0) {
      value += 6;
      SymbolTable symbolTable6 = this.symbolTable;
      value8++;
      symbolTable6.addConstantUtf8("Deprecated");
    }
    if (this.annotationWriter != null) {
      AnnotationWriter annotationWriter5 = this.annotationWriter;
      int value11 = value;
      value8++;
      value = value11 + annotationWriter5.computeAnnotationsSize("RuntimeVisibleAnnotations");
    }
    if (this.annotationWriter4 != null) {
      AnnotationWriter annotationWriter6 = this.annotationWriter4;
      int value12 = value;
      value8++;
      value = value12 + annotationWriter6.computeAnnotationsSize("RuntimeInvisibleAnnotations");
    }
    if (this.annotationWriter3 != null) {
      AnnotationWriter annotationWriter7 = this.annotationWriter3;
      int value13 = value;
      value8++;
      value = value13 + annotationWriter7.computeAnnotationsSize("RuntimeVisibleTypeAnnotations");
    }
    if (this.annotationWriter2 != null) {
      AnnotationWriter annotationWriter8 = this.annotationWriter2;
      int value14 = value;
      value8++;
      value = value14 + annotationWriter8.computeAnnotationsSize("RuntimeInvisibleTypeAnnotations");
    }
    if (this.symbolTable.computeBootstrapMethodsSize() > 0) {
      SymbolTable symbolTable7 = this.symbolTable;
      int value15 = value;
      value8++;
      value = value15 + symbolTable7.computeBootstrapMethodsSize();
    }
    if (this.moduleWriter != null) {
      value8 += this.moduleWriter.getAttributeCount();
      value += this.moduleWriter.computeAttributesSize();
    }
    if (this.count14 != 0) {
      value += 8;
      SymbolTable symbolTable8 = this.symbolTable;
      value8++;
      symbolTable8.addConstantUtf8("NestHost");
    }
    if (this.byteVector4 != null) {
      int value16 = value;
      value8++;
      value = value16 + (byte) 8 + this.byteVector4.length;
      this.symbolTable.addConstantUtf8("NestMembers");
    }
    if (this.byteVector3 != null) {
      int value17 = value;
      value8++;
      value = value17 + (byte) 8 + this.byteVector3.length;
      this.symbolTable.addConstantUtf8("PermittedSubclasses");
    }
    int value18 = 0;
    int value19 = 0;
    if ((this.count5 & 65536) != 0 || this.recordComponentWriter2 != null) {
      RecordComponentWriter recordComponentWriter;
      for (RecordComponentWriter recordComponentWriter3 =
              recordComponentWriter = this.recordComponentWriter2;
          recordComponentWriter3 != null;
          recordComponentWriter3 =
              recordComponentWriter = (RecordComponentWriter) recordComponentWriter.delegate) {
        int value20 = recordComponentWriter.computeRecordComponentInfoSize();
        int value21 = value19;
        value18++;
        value19 = value21 + value20;
      }
      value8++;
      value += 8 + value19;
      this.symbolTable.addConstantUtf8("Record");
    }
    if (this.firstAttribute != null) {
      value8 += this.firstAttribute.getAttributeCount();
      value += this.firstAttribute.computeAttributesSize(this.symbolTable);
    }
    int value22 = value + this.symbolTable.calculateCount();
    int value23;
    if ((value23 = this.symbolTable.getConstantPoolCount()) > 65535) {
      throw new ClassTooLargeException(this.symbolTable.getClassName(), value23);
    }
    ByteVector byteVector5;
    (byteVector5 = new ByteVector(value22)).putInt(-889275714).putInt(this.count10);
    this.symbolTable.processByteVector(byteVector5);
    int value24 = (this.count10 & 65535) < 49 ? 4096 : 0;
    byteVector5.putShort(this.count5 & ~value24).putShort(this.count2).putShort(this.count12);
    byteVector5.putShort(this.count6);
    int value25 = 0;
    for (int value26 = value25; value26 < this.count6; value26 = value25) {
      int value27 = value25;
      int[] values = this.interfaces;
      ByteVector byteVector6 = byteVector5;
      value25++;
      byteVector6.putShort(values[value27]);
    }
    byteVector5.putShort(value2);
    FieldWriter fieldWriter4 = this.fieldWriter;
    for (FieldWriter fieldWriter5 = fieldWriter4;
        fieldWriter5 != null;
        fieldWriter5 = fieldWriter4 = (FieldWriter) fieldWriter4.fv) {
      fieldWriter4.putFieldInfo(byteVector5);
    }
    byteVector5.putShort(value5);
    boolean matches = false;
    boolean matches2 = false;
    MethodWriter methodWriter4 = this.methodWriter;
    for (MethodWriter methodWriter5 = methodWriter4;
        methodWriter5 != null;
        methodWriter5 = methodWriter4 = (MethodWriter) methodWriter4.mv) {
      matches |= methodWriter4.isCount7();
      matches2 |= methodWriter4.isEnabled2();
      methodWriter4.putMethodInfo(byteVector5);
    }
    byteVector5.putShort(value8);
    if (this.byteVector != null) {
      byteVector5
          .putShort(this.symbolTable.addConstantUtf8("InnerClasses"))
          .putInt(this.byteVector.length + 2)
          .putShort(this.count)
          .putByteArray(this.byteVector.data, 0, this.byteVector.length);
    }
    if (this.count11 != 0) {
      byteVector5
          .putShort(this.symbolTable.addConstantUtf8("EnclosingMethod"))
          .putInt(4)
          .putShort(this.count11)
          .putShort(this.count13);
    }
    if ((this.count5 & 4096) != 0 && (this.count10 & 65535) < 49) {
      byteVector5.putShort(this.symbolTable.addConstantUtf8("Synthetic")).putInt(0);
    }
    if (this.count3 != 0) {
      byteVector5
          .putShort(this.symbolTable.addConstantUtf8("Signature"))
          .putInt(2)
          .putShort(this.count3);
    }
    if (this.count4 != 0) {
      byteVector5
          .putShort(this.symbolTable.addConstantUtf8("SourceFile"))
          .putInt(2)
          .putShort(this.count4);
    }
    if (this.byteVector2 != null) {
      int value28 = this.byteVector2.length;
      byteVector5
          .putShort(this.symbolTable.addConstantUtf8("SourceDebugExtension"))
          .putInt(value28)
          .putByteArray(this.byteVector2.data, 0, value28);
    }
    if ((this.count5 & 131072) != 0) {
      byteVector5.putShort(this.symbolTable.addConstantUtf8("Deprecated")).putInt(0);
    }
    AnnotationWriter.putAnnotations(
        this.symbolTable,
        this.annotationWriter,
        this.annotationWriter4,
        this.annotationWriter3,
        this.annotationWriter2,
        byteVector5);
    this.symbolTable.processByteVector2(byteVector5);
    if (this.moduleWriter != null) {
      this.moduleWriter.putAttributes(byteVector5);
    }
    if (this.count14 != 0) {
      byteVector5
          .putShort(this.symbolTable.addConstantUtf8("NestHost"))
          .putInt(2)
          .putShort(this.count14);
    }
    if (this.byteVector4 != null) {
      byteVector5
          .putShort(this.symbolTable.addConstantUtf8("NestMembers"))
          .putInt(this.byteVector4.length + 2)
          .putShort(this.count7)
          .putByteArray(this.byteVector4.data, 0, this.byteVector4.length);
    }
    if (this.byteVector3 != null) {
      byteVector5
          .putShort(this.symbolTable.addConstantUtf8("PermittedSubclasses"))
          .putInt(this.byteVector3.length + 2)
          .putShort(this.count9)
          .putByteArray(this.byteVector3.data, 0, this.byteVector3.length);
    }
    if ((this.count5 & 65536) != 0 || this.recordComponentWriter2 != null) {
      byteVector5
          .putShort(this.symbolTable.addConstantUtf8("Record"))
          .putInt(value19 + 2)
          .putShort(value18);
      RecordComponentWriter recordComponentWriter4 = this.recordComponentWriter2;
      for (RecordComponentWriter recordComponentWriter5 = recordComponentWriter4;
          recordComponentWriter5 != null;
          recordComponentWriter5 =
              recordComponentWriter4 = (RecordComponentWriter) recordComponentWriter4.delegate) {
        recordComponentWriter4.putRecordComponentInfo(byteVector5);
      }
    }
    if (this.firstAttribute != null) {
      this.firstAttribute.putAttributes(this.symbolTable, byteVector5);
    }
    return matches2 ? this.replaceAsmInstructions(byteVector5.data, matches) : byteVector5.data;
  }

  @Override
  public final AnnotationVisitor visitTypeAnnotation(
      int value, TypePath typePath, String text, boolean matches) {
    return matches
        ? (this.annotationWriter3 =
            AnnotationWriter.create(
                this.symbolTable, value, typePath, text, this.annotationWriter3))
        : (this.annotationWriter2 =
            AnnotationWriter.create(
                this.symbolTable, value, typePath, text, this.annotationWriter2));
  }

  @Override
  public final void visitOuterClass(String text, String text2, String text3) {
    this.count11 = this.symbolTable.getSymbol3(text).index;
    if (text2 != null && text3 != null) {
      this.count13 = this.symbolTable.addConstantNameAndType(text2, text3);
    }
  }

  @Override
  public final void visitInnerClass(String text, String text2, String text3, int value) {
    if (this.byteVector == null) {
      this.byteVector = new ByteVector();
    }
    Symbol symbol;
    if ((symbol = this.symbolTable.getSymbol3(text)).count == 0) {
      this.count++;
      this.byteVector.putShort(symbol.index);
      this.byteVector.putShort(text2 == null ? 0 : this.symbolTable.getSymbol3(text2).index);
      this.byteVector.putShort(text3 == null ? 0 : this.symbolTable.addConstantUtf8(text3));
      this.byteVector.putShort(value);
      symbol.count = this.count;
    }
  }

  public ClassWriter(ClassReader classReader, int flags) {
    super(589824);
    this.flags = flags;
    this.symbolTable =
        classReader == null ? new SymbolTable(this) : new SymbolTable(this, classReader);
    this.setFlags(flags);
  }

  @Override
  public final MethodVisitor visitMethod(
      int value, String text, String text2, String text3, String[] texts) {
    MethodWriter methodWriter3 =
        new MethodWriter(this.symbolTable, value, text, text2, text3, texts, this.count8);
    ClassWriter classWriter;
    if (this.methodWriter == null) {
      classWriter = this;
      this.methodWriter = methodWriter3;
    } else {
      classWriter = this;
      this.methodWriter2.mv = methodWriter3;
    }
    return classWriter.methodWriter2 = methodWriter3;
  }

  public int calculateCount4(String text) {
    return this.symbolTable.getSymbol4(text).index;
  }

  public int newUTF8(String text) {
    return this.symbolTable.addConstantUtf8(text);
  }

  public int newMethod(String text, String text2, String text3, boolean matches) {
    return this.symbolTable.addConstantMethodref(text, text2, text3, matches).index;
  }

  public final void setFlags(int value) {
    if ((value & 2) != 0) {
      this.count8 = 4;
    } else if ((value & 1) != 0) {
      this.count8 = 1;
    } else {
      this.count8 = 0;
    }
  }

  @Override
  public final FieldVisitor visitField(
      int value, String text, String text2, String text3, Object result) {
    FieldWriter fieldWriter3 = new FieldWriter(this.symbolTable, value, text, text2, text3, result);
    ClassWriter classWriter;
    if (this.fieldWriter == null) {
      classWriter = this;
      this.fieldWriter = fieldWriter3;
    } else {
      classWriter = this;
      this.fieldWriter2.fv = fieldWriter3;
    }
    return classWriter.fieldWriter2 = fieldWriter3;
  }
}
