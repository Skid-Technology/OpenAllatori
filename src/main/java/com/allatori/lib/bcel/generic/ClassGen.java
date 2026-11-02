package com.allatori.lib.bcel.generic;

import com.allatori.internal.BytecodeSupport;
import com.allatori.lib.bcel.classfile.AccessFlags;
import com.allatori.lib.bcel.classfile.AnnotationEntry;
import com.allatori.lib.bcel.classfile.Annotations;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.RuntimeInvisibleAnnotations;
import com.allatori.lib.bcel.classfile.RuntimeVisibleAnnotations;
import com.allatori.lib.bcel.classfile.SourceFile;
import com.allatori.lib.bcel.classfile.Utility;
import com.allatori.lib.bcel.util.BCELComparator;
import com.allatori.lib.bcel.util.comparator.ClassGenComparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClassGen extends AccessFlags implements Cloneable {

  public int minor;

  public List attributes;

  public static BCELComparator _cmp = new ClassGenComparator();

  public List annotations;

  public List fields;

  public ArrayList observers;

  public ConstantPoolGen cp;

  public int superclassNameIndex;

  public String superclassName;

  public String className;

  public int major;

  public String fileName;

  public List methods;

  public int classNameIndex = -1;

  public List interfaceNames;

  public ClassGen(
      String className,
      String superclassName,
      String fileName,
      int access_flags,
      String[] texts,
      ConstantPoolGen constantPoolGen) {
    super();
    this.classNameIndex = -1;
    this.superclassNameIndex = -1;
    this.major = 45;
    this.minor = 3;
    this.fields = new ArrayList();
    this.methods = new ArrayList();
    this.attributes = new ArrayList();
    this.interfaceNames = new ArrayList();
    this.annotations = new ArrayList();
    this.className = className;
    this.superclassName = superclassName;
    this.fileName = fileName;
    this.access_flags = access_flags;
    this.cp = constantPoolGen;
    if (fileName != null) {
      this.processAttribute(
          new SourceFile(
              constantPoolGen.addUtf8("SourceFile"),
              2,
              constantPoolGen.addUtf8(fileName),
              constantPoolGen.getConstantPool()));
    }
    this.classNameIndex = constantPoolGen.addClass(className);
    this.superclassNameIndex = constantPoolGen.addClass(superclassName);
    int value2;
    if (texts != null) {
      for (int value3 = value2 = 0; value3 < texts.length; value3 = value2) {
        int value4 = value2;
        value2++;
        this.processText(texts[value4]);
      }
    }
  }

  public int getClassNameIndex() {
    return this.classNameIndex;
  }

  public ConstantPoolGen getConstantPool() {
    return this.cp;
  }

  public int getSuperclassNameIndex() {
    return this.superclassNameIndex;
  }

  public void processAttribute(Attribute attribute) {
    this.attributes.add(attribute);
  }

  public String getClassName() {
    return this.className;
  }

  public void setMethods(Method[] methods2) {
    int value = 0;
    this.methods.clear();
    for (int index = 0; value < methods2.length; value = index) {
      int value2 = index;
      index++;
      this.processMethod2(methods2[value2]);
    }
  }

  public Method containsMethod(String text, String text2) {
    Iterator iterator2 = this.methods.iterator();
    while (iterator2.hasNext()) {
      Method method;
      if ((method = (Method) iterator2.next()).getText().equals(text)
          && method.getText2().equals(text2)) {
        return method;
      }
    }
    return null;
  }

  public void addAnnotationEntry(AnnotationEntryGen annotationEntryGen) {
    this.annotations.add(annotationEntryGen);
  }

  public String[] getInterfaceNames() {
    String[] texts = new String[this.interfaceNames.size()];
    this.interfaceNames.toArray(texts);
    return texts;
  }

  public void processCount2(int classNameIndex) {
    this.classNameIndex = classNameIndex;
    this.className =
        this.cp.getConstantPool().createText(classNameIndex, (byte) 7).replace('/', '.');
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      System.err.println(cloneNotSupportedException);
      return null;
    }
  }

  public void addAnnotationEntry2(AnnotationEntryGen annotationEntryGen) {
    this.annotations.remove(annotationEntryGen);
  }

  public JavaClass getJavaClass() {
    int[] interfaces = this.getInterfaces();
    Field[] fields = this.getFields();
    Method[] methods = this.getMethods();
    Attribute[] attributes2;
    ClassGen classGen;
    if (this.annotations.size() == 0) {
      classGen = this;
      attributes2 = this.getAttributes();
    } else {
      Attribute[] attributes3 = Utility.createAttributes(this.cp, this.annotations);
      attributes2 = new Attribute[this.attributes.size() + attributes3.length];
      this.attributes.toArray(attributes2);
      System.arraycopy(attributes3, 0, attributes2, this.attributes.size(), attributes3.length);
      classGen = this;
    }
    ConstantPool finalConstantPool = classGen.cp.getFinalConstantPool();
    return new JavaClass(
        this.classNameIndex,
        this.superclassNameIndex,
        this.fileName,
        this.major,
        this.minor,
        this.access_flags,
        finalConstantPool,
        interfaces,
        fields,
        methods,
        attributes2);
  }

  public void processText(String text) {
    this.interfaceNames.add(text);
  }

  public void processField(Field field) {
    this.fields.add(field);
  }

  public String getFileName() {
    return this.fileName;
  }

  public Field containsField(String text) {
    Iterator iterator2 = this.fields.iterator();
    while (iterator2.hasNext()) {
      Field field;
      if ((field = (Field) iterator2.next()).getText().equals(text)) {
        return field;
      }
    }
    return null;
  }

  public void processCount3(int superclassNameIndex) {
    this.superclassNameIndex = superclassNameIndex;
    this.superclassName =
        this.cp.getConstantPool().createText(superclassNameIndex, (byte) 7).replace('/', '.');
  }

  public void update() {
    Iterator iterator2;
    if (this.observers != null) {
      for (Iterator iterator3 = iterator2 = this.observers.iterator();
          iterator3.hasNext();
          iterator3 = iterator2) {
        ((BytecodeSupport) iterator2.next()).processClassGen(this);
      }
    }
  }

  public void replaceMethod(Method method, Method method2) {
    if (method2 == null) {
      throw new ClassGenException("Replacement method must not be null");
    }
    int value;
    if ((value = this.methods.indexOf(method)) < 0) {
      this.methods.add(method2);
    } else {
      this.methods.set(value, method2);
    }
  }

  public static BCELComparator getComparator() {
    return _cmp;
  }

  public AnnotationEntryGen[] unpackAnnotations(Attribute[] attributes) {
    ArrayList values = new ArrayList();
    int value;
    for (int value2 = value = 0; value2 < attributes.length; value2 = ++value) {
      Attribute attribute;
      if ((attribute = attributes[value]) instanceof RuntimeVisibleAnnotations) {
        AnnotationEntry[] annotationEntries =
            ((RuntimeVisibleAnnotations) attribute).getAnnotationEntries();
        int value3;
        for (int value4 = value3 = 0; value4 < annotationEntries.length; value4 = value3) {
          AnnotationEntry annotationEntry = annotationEntries[value3];
          ConstantPoolGen constantPool = this.getConstantPool();
          value3++;
          values.add(new AnnotationEntryGen(annotationEntry, constantPool, false));
        }
      } else if (attribute instanceof RuntimeInvisibleAnnotations) {
        AnnotationEntry[] annotationEntries2 =
            ((RuntimeInvisibleAnnotations) attribute).getAnnotationEntries();
        int value5;
        for (int value6 = value5 = 0; value6 < annotationEntries2.length; value6 = value5) {
          AnnotationEntry annotationEntry2 = annotationEntries2[value5];
          ConstantPoolGen constantPool2 = this.getConstantPool();
          value5++;
          values.add(new AnnotationEntryGen(annotationEntry2, constantPool2, false));
        }
      }
    }
    return (AnnotationEntryGen[]) values.toArray(new AnnotationEntryGen[values.size()]);
  }

  public void setMethodAt(Method method, int value) {
    this.methods.set(value, method);
  }

  public Method[] getMethods() {
    return (Method[]) this.methods.toArray(new Method[this.methods.size()]);
  }

  public Attribute[] getAttributes() {
    return (Attribute[]) this.attributes.toArray(new Attribute[this.attributes.size()]);
  }

  public void processBytecodeSupport(BytecodeSupport bytecodeSupport) {
    if (this.observers == null) {
      this.observers = new ArrayList();
    }
    this.observers.add(bytecodeSupport);
  }

  @Override
  public boolean equals(Object result) {
    return _cmp.equals(this, result);
  }

  public void processText2(String text) {
    this.superclassName = text.replace('/', '.');
    this.superclassNameIndex = this.cp.addClass(text);
  }

  public Field[] getFields() {
    return (Field[]) this.fields.toArray(new Field[this.fields.size()]);
  }

  public int[] getInterfaces() {
    int value;
    int[] values = new int[value = this.interfaceNames.size()];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = this.cp.addClass((String) this.interfaceNames.get(value2));
      int value5 = value2;
      value2++;
      values[value5] = value4;
    }
    return values;
  }

  public ClassGen(String text, String text2, String text3, int value, String[] texts) {
    this(text, text2, text3, value, texts, new ConstantPoolGen());
  }

  public void processField2(Field field) {
    this.fields.remove(field);
  }

  public int getMajor() {
    return this.major;
  }

  public Method getMethodAt(int value) {
    return (Method) this.methods.get(value);
  }

  public AnnotationEntryGen[] getAnnotationEntries() {
    return (AnnotationEntryGen[])
        this.annotations.toArray(new AnnotationEntryGen[this.annotations.size()]);
  }

  public void processCount4(int value) {
    InstructionList instructionList;
    (instructionList = new InstructionList()).getInstructionHandle(InstructionConstants.ALOAD_0);
    instructionList.getInstructionHandle(
        new INVOKESPECIAL(this.cp.addMethodref(this.superclassName, "<init>", "()V")));
    instructionList.getInstructionHandle(InstructionConstants.RETURN);
    MethodGen methodGen =
        new MethodGen(
            value,
            Type.VOID,
            Type.NO_ARGS,
            null,
            "<init>",
            this.className,
            instructionList,
            this.cp);
    methodGen.setCount2(1);
    this.processMethod2(methodGen.getMethod());
  }

  public void processMethod(Method method) {
    this.methods.remove(method);
  }

  public boolean containsField(Field field) {
    return this.fields.contains(field);
  }

  public static void setComparator(BCELComparator bCELComparator) {
    _cmp = bCELComparator;
  }

  public void replaceField(Field field, Field field2) {
    if (field2 == null) {
      throw new ClassGenException("Replacement method must not be null");
    }
    int value;
    if ((value = this.fields.indexOf(field)) < 0) {
      this.fields.add(field2);
    } else {
      this.fields.set(value, field2);
    }
  }

  public ClassGen(JavaClass javaClass) {
    this.superclassNameIndex = -1;
    this.major = 45;
    this.minor = 3;
    this.fields = new ArrayList();
    this.methods = new ArrayList();
    this.attributes = new ArrayList();
    this.interfaceNames = new ArrayList();
    this.annotations = new ArrayList();
    this.classNameIndex = javaClass.getCount();
    this.superclassNameIndex = javaClass.getCount2();
    this.className = javaClass.getText2();
    this.superclassName = javaClass.getText5();
    this.fileName = javaClass.getText();
    this.access_flags = javaClass.getAccess_flags2();
    this.cp = new ConstantPoolGen(javaClass.getConstantPool());
    this.major = javaClass.getCount3();
    this.minor = javaClass.getCount4();
    Attribute[] attributes2;
    AnnotationEntryGen[] annotationEntryGens =
        this.unpackAnnotations(attributes2 = javaClass.getAttributes());
    Method[] methods2 = javaClass.getMethods();
    Field[] fields2 = javaClass.getFields();
    String[] interfaceNames2 = javaClass.getInterfaceNames();
    int value;
    for (int value2 = value = 0; value2 < interfaceNames2.length; value2 = value) {
      int value3 = value;
      value++;
      this.processText(interfaceNames2[value3]);
    }
    int value4;
    for (int value5 = value4 = 0; value5 < attributes2.length; value5 = ++value4) {
      if (!(attributes2[value4] instanceof Annotations)) {
        this.processAttribute(attributes2[value4]);
      }
    }
    int value6;
    for (int value7 = value6 = 0; value7 < annotationEntryGens.length; value7 = value6) {
      int value8 = value6;
      value6++;
      this.addAnnotationEntry(annotationEntryGens[value8]);
    }
    int value9;
    for (int value10 = value9 = 0; value10 < methods2.length; value10 = value9) {
      int value11 = value9;
      value9++;
      this.processMethod2(methods2[value11]);
    }
    int value12;
    for (int value13 = value12 = 0; value13 < fields2.length; value13 = value12) {
      int value14 = value12;
      value12++;
      this.processField(fields2[value14]);
    }
  }

  public String getSuperclassName() {
    return this.superclassName;
  }

  public void setConstantPool(ConstantPoolGen constantPoolGen) {
    this.cp = constantPoolGen;
  }

  public void processText3(String text) {
    this.interfaceNames.remove(text);
  }

  public void setMajor(int major) {
    this.major = major;
  }

  public void processText4(String text) {
    this.className = text.replace('/', '.');
    this.classNameIndex = this.cp.addClass(text);
  }

  public void setMinor(int minor) {
    this.minor = minor;
  }

  public void processMethod2(Method method) {
    this.methods.add(method);
  }

  @Override
  public int hashCode() {
    return _cmp.hashCode(this);
  }

  public int getMinor() {
    return this.minor;
  }

  public void processAttribute2(Attribute attribute) {
    this.attributes.remove(attribute);
  }

  public void processBytecodeSupport2(BytecodeSupport bytecodeSupport) {
    if (this.observers != null) {
      this.observers.remove(bytecodeSupport);
    }
  }
}
