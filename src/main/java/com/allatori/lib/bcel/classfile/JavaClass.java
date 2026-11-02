package com.allatori.lib.bcel.classfile;

import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.util.BCELComparator;
import com.allatori.lib.bcel.util.ClassQueue;
import com.allatori.lib.bcel.util.Repository;
import com.allatori.lib.bcel.util.SyntheticRepository;
import com.allatori.lib.bcel.util.comparator.JavaClassComparator;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class JavaClass extends AccessFlags implements Cloneable, Node, Comparable {

  public AnnotationEntry[] annotations;

  public String[] interface_names;

  public String text;

  public byte source;

  public String text2;

  public boolean enabled;

  public boolean enabled2;

  public String text3;

  public int count;

  public static boolean debug = false;

  static {
    debug = Boolean.getBoolean("JavaClass.debug");
    String text;
    if ((text = System.getProperty("file.separator")) != null) {
      try {
        character = text.charAt(0);
      } catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
      }
    }
  }

  public static char character = '/';

  public static BCELComparator bcelComparator = new JavaClassComparator();

  public int count2;

  public boolean enabled3;

  public int count3;

  public Method[] methods;

  public String text4;

  public Field[] fields;

  public boolean enabled4;

  public int count4;

  public String text5;

  public static final byte HEAP = 1;

  public ConstantPool constant_pool;

  public static final byte ZIP = 3;

  public Attribute[] attributes;

  public static final byte FILE = 2;

  public int[] interfaces;

  public transient Repository repository;

  @Override
  public boolean equals(Object result) {
    return bcelComparator.equals(this, result);
  }

  public ConstantPool getConstantPool() {
    return this.constant_pool;
  }

  public void setCount4(int count4) {
    this.count4 = count4;
  }

  public JavaClass getSuperClass() throws ClassNotFoundException {
    return "java.lang.Object".equals(this.getText2())
        ? null
        : this.repository.getJavaClass2(this.getText5());
  }

  public final boolean isEnabled16() {
    return (this.access_flags & 32) != 0;
  }

  public String[] getInterfaceNames() {
    return this.interface_names;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public void setText2(String text2) {
    this.text2 = text2;
  }

  public JavaClass(
      int count,
      int count2,
      String text3,
      int count3,
      int count4,
      int access_flags,
      ConstantPool constantPool,
      int[] interfaces,
      Field[] fields2,
      Method[] methods2,
      Attribute[] attributes2,
      byte source) {
    super();
    JavaClass javaClass;
    this.text = "<Unknown>";
    this.source = 1;
    this.enabled = false;
    this.enabled2 = false;
    this.enabled4 = false;
    this.enabled3 = true;
    this.repository = SyntheticRepository.getInstance();
    if (interfaces == null) {
      interfaces = new int[0];
    }
    if (attributes2 == null) {
      attributes2 = new Attribute[0];
    }
    if (fields2 == null) {
      fields2 = new Field[0];
    }
    if (methods2 == null) {
      methods2 = new Method[0];
    }
    this.count = count;
    this.count2 = count2;
    this.text3 = text3;
    this.count3 = count3;
    this.count4 = count4;
    this.access_flags = access_flags;
    this.constant_pool = constantPool;
    this.interfaces = interfaces;
    this.fields = fields2;
    this.methods = methods2;
    this.attributes = attributes2;
    this.enabled3 = true;
    this.source = source;
    int value7;
    int value8 = value7 = 0;
    while (true) {
      if (value8 >= attributes2.length) {
        javaClass = this;
        break;
      }
      if (attributes2[value7] instanceof SourceFile) {
        this.text = ((SourceFile) attributes2[value7]).getText();
        javaClass = this;
        break;
      }
      value8 = ++value7;
    }
    javaClass.text2 = constantPool.createText(count, (byte) 7);
    this.text2 = Utility.getText(this.text2, false);
    int value9;
    if ((value9 = this.text2.lastIndexOf(46)) < 0) {
      this.text4 = "";
      value8 = count2;
    } else {
      this.text4 = this.text2.substring(0, value9);
      value8 = count2;
    }
    JavaClass javaClass2;
    if (value8 > 0) {
      javaClass2 = this;
      this.text5 = constantPool.createText(count2, (byte) 7);
      this.text5 = Utility.getText(this.text5, false);
    } else {
      javaClass2 = this;
      this.text5 = "java.lang.Object";
    }
    javaClass2.interface_names = new String[interfaces.length];
    int value10;
    for (int value11 = value10 = 0; value11 < interfaces.length; value11 = value10) {
      String text7 = constantPool.createText(interfaces[value10], (byte) 7);
      String[] texts = this.interface_names;
      String text8 = Utility.getText(text7, false);
      int value12 = value10;
      value10++;
      texts[value12] = text8;
    }
  }

  public int getCount3() {
    return this.count3;
  }

  public void setCount2(int count2) {
    this.count2 = count2;
  }

  public JavaClass[] createJavaClasss() throws ClassNotFoundException {
    ArrayList values = new ArrayList();
    JavaClass javaClass;
    for (JavaClass javaClass2 = javaClass = this.getSuperClass();
        javaClass2 != null;
        javaClass2 = javaClass) {
      values.add(javaClass);
      javaClass = javaClass.getSuperClass();
    }
    return (JavaClass[]) values.toArray(new JavaClass[values.size()]);
  }

  @Override
  public int hashCode() {
    return bcelComparator.hashCode(this);
  }

  public void dump(File file) throws IOException {
    String text;
    if ((text = file.getParent()) != null) {
      new File(text).mkdirs();
    }
    DataOutputStream dataOutputStream = null;
    boolean matches = false;
    try {
      matches = true;
      dataOutputStream = new DataOutputStream(new FileOutputStream(file));
      this.dump(dataOutputStream);
      matches = false;
    } finally {
      if (matches) {
        if (dataOutputStream != null) {
          dataOutputStream.close();
        }
      }
    }
    dataOutputStream.close();
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitJavaClass(this);
  }

  public Method getMethod(java.lang.reflect.Method method) {
    int value;
    for (int value2 = value = 0; value2 < this.methods.length; value2 = ++value) {
      Method method2 = this.methods[value];
      if (method.getName().equals(method2.getText())
          && method.getModifiers() == method2.getAccess_flags()
          && Type.getSignature(method).equals(method2.getText2())) {
        return method2;
      }
    }
    return null;
  }

  public void setConstantPool(ConstantPool constantPool) {
    this.constant_pool = constantPool;
  }

  public String getText() {
    return this.text;
  }

  public final boolean isEnabled17() {
    return (this.access_flags & 512) == 0;
  }

  public void setRepository(Repository repository2) {
    this.repository = repository2;
  }

  public void setText5(String text5) {
    this.text5 = text5;
  }

  public JavaClass[] getJavaClasss() throws ClassNotFoundException {
    String[] texts;
    JavaClass[] javaClasss = new JavaClass[(texts = this.getInterfaceNames()).length];
    int value;
    for (int value2 = value = 0; value2 < texts.length; value2 = value) {
      JavaClass javaClass2 = this.repository.getJavaClass2(texts[value]);
      int value3 = value;
      value++;
      javaClasss[value3] = javaClass2;
    }
    return javaClasss;
  }

  public final void computeNestedTypeStatus() {
    if (!this.enabled4) {
      int value;
      for (int value2 = value = 0; value2 < this.attributes.length; value2 = ++value) {
        if (this.attributes[value] instanceof InnerClasses) {
          InnerClass[] innerClasses = ((InnerClasses) this.attributes[value]).getInnerClasses();
          int value3;
          for (int value4 = value3 = 0; value4 < innerClasses.length; value4 = ++value3) {
            byte value5 = 0;
            if (Utility.getText(
                    this.constant_pool.createText(
                        innerClasses[value3].getInner_class_index(), (byte) 7))
                .equals(this.getText2())) {
              value5 = 1;
            }
            if (value5 != 0) {
              this.enabled2 = true;
              if (innerClasses[value3].getInner_name_index() == 0) {
                this.enabled = true;
              }
            }
          }
        }
      }
      this.enabled4 = true;
    }
  }

  public boolean matchesJavaClass(JavaClass javaClass) throws ClassNotFoundException {
    if (!javaClass.isEnabled5()) {
      throw new IllegalArgumentException(("" + javaClass.getText2() + " is no interface"));
    }
    if (this.equals(javaClass)) {
      return true;
    }
    JavaClass[] javaClasss = this.createJavaClasss2();
    int value;
    for (int value2 = value = 0; value2 < javaClasss.length; value2 = ++value) {
      if (javaClasss[value].equals(javaClass)) {
        return true;
      }
    }
    return false;
  }

  public void setCount3(int count3) {
    this.count3 = count3;
  }

  public String getText3() {
    return this.text3;
  }

  public JavaClass(
      int value,
      int value2,
      String text,
      int value3,
      int value4,
      int value5,
      ConstantPool constantPool,
      int[] values,
      Field[] fields,
      Method[] methods,
      Attribute[] attributes) {
    this(
        value,
        value2,
        text,
        value3,
        value4,
        value5,
        constantPool,
        values,
        fields,
        methods,
        attributes,
        (byte) 1);
  }

  public static void setComparator(BCELComparator bCELComparator) {
    bcelComparator = bCELComparator;
  }

  public byte[] getBytes() {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    label86:
    {
      try {
        try {
          this.dump(dataOutputStream);
          break label86;
        } catch (IOException oException) {
          oException.printStackTrace();
        }
      } catch (Throwable throwable) {
        try {
          dataOutputStream.close();
        } catch (IOException oException2) {
          oException2.printStackTrace();
          throw throwable;
        }
        throw throwable;
      }
      try {
        dataOutputStream.close();
      } catch (IOException oException3) {
        oException3.printStackTrace();
        return byteArrayOutputStream.toByteArray();
      }
      return byteArrayOutputStream.toByteArray();
    }
    try {
      dataOutputStream.close();
    } catch (IOException oException4) {
      oException4.printStackTrace();
      return byteArrayOutputStream.toByteArray();
    }
    return byteArrayOutputStream.toByteArray();
  }

  public void setText(String text) {
    this.text = text;
  }

  public Repository getRepository() {
    return this.repository;
  }

  public void setInterfaces(int[] interfaces) {
    this.interfaces = interfaces;
  }

  public void setInterfaceNames(String[] texts) {
    this.interface_names = texts;
  }

  public void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeInt(-889275714);
    dataOutputStream.writeShort(this.count4);
    dataOutputStream.writeShort(this.count3);
    this.constant_pool.dump(dataOutputStream);
    dataOutputStream.writeShort(this.access_flags);
    dataOutputStream.writeShort(this.count);
    dataOutputStream.writeShort(this.count2);
    dataOutputStream.writeShort(this.interfaces.length);
    int value;
    for (int value2 = value = 0; value2 < this.interfaces.length; value2 = value) {
      int value3 = value;
      int[] values = this.interfaces;
      value++;
      dataOutputStream.writeShort(values[value3]);
    }
    dataOutputStream.writeShort(this.fields.length);
    int value4;
    for (int value5 = value4 = 0; value5 < this.fields.length; value5 = value4) {
      int value6 = value4;
      Field[] fields2 = this.fields;
      value4++;
      fields2[value6].dump(dataOutputStream);
    }
    dataOutputStream.writeShort(this.methods.length);
    int value7;
    for (int value8 = value7 = 0; value8 < this.methods.length; value8 = value7) {
      int value9 = value7;
      Method[] methods2 = this.methods;
      value7++;
      methods2[value9].dump(dataOutputStream);
    }
    if (this.attributes != null) {
      dataOutputStream.writeShort(this.attributes.length);
      int value10;
      for (int value11 = value10 = 0; value11 < this.attributes.length; value11 = value10) {
        int value12 = value10;
        Attribute[] attributes2 = this.attributes;
        value10++;
        attributes2[value12].dump(dataOutputStream);
      }
    } else {
      dataOutputStream.writeShort(0);
    }
    dataOutputStream.flush();
  }

  public int getCount() {
    return this.count;
  }

  public void setFields(Field[] fields2) {
    this.fields = fields2;
  }

  public Method[] getMethods() {
    return this.methods;
  }

  public int getCount4() {
    return this.count4;
  }

  public int[] getInterfaceIndices() {
    return this.interfaces;
  }

  public static final String indent(Object result) {
    StringTokenizer stringTokenizer = new StringTokenizer(result.toString(), "\n");
    StringBuffer stringBuffer = new StringBuffer();
    StringTokenizer stringTokenizer2 = stringTokenizer;
    while (stringTokenizer2.hasMoreTokens()) {
      StringBuffer stringBuffer2 = stringBuffer.append("\t");
      stringTokenizer2 = stringTokenizer;
      stringBuffer2.append(stringTokenizer.nextToken()).append("\n");
    }
    return stringBuffer.toString();
  }

  public String getText4() {
    return this.text4;
  }

  public final boolean isEnabled18() {
    this.computeNestedTypeStatus();
    return this.enabled;
  }

  public JavaClass[] createJavaClasss2() throws ClassNotFoundException {
    ClassQueue classQueue = new ClassQueue();
    TreeSet treeSet = new TreeSet();
    classQueue.enqueue(this);
    while (!classQueue.empty()) {
      JavaClass javaClass;
      JavaClass javaClass2 = javaClass = classQueue.dequeue();
      JavaClass superClass = javaClass2.getSuperClass();
      JavaClass[] javaClasss = javaClass2.getJavaClasss();
      if (javaClass2.isEnabled5()) {
        treeSet.add(javaClass);
      } else if (superClass != null) {
        classQueue.enqueue(superClass);
      }
      int value;
      for (int value2 = value = 0; value2 < javaClasss.length; value2 = value) {
        int value3 = value;
        value++;
        classQueue.enqueue(javaClasss[value3]);
      }
    }
    return (JavaClass[]) treeSet.toArray(new JavaClass[treeSet.size()]);
  }

  public static BCELComparator getComparator() {
    return bcelComparator;
  }

  public String getText5() {
    return this.text5;
  }

  public void processText(String text) throws IOException {
    this.dump(new File(text));
  }

  public static final void Debug(String text) {
    if (debug) {
      System.out.println(text);
    }
  }

  public final byte getSource() {
    return this.source;
  }

  public Field[] getFields() {
    return this.fields;
  }

  public JavaClass copy() {
    JavaClass javaClass = null;
    try {
      javaClass = (JavaClass) this.clone();
      javaClass.constant_pool = this.constant_pool.copy();
      javaClass.interfaces = (int[]) this.interfaces.clone();
      javaClass.interface_names = (String[]) this.interface_names.clone();
      javaClass.fields = new Field[this.fields.length];
      int value;
      for (int value2 = value = 0; value2 < this.fields.length; value2 = value) {
        Field[] fields2 = javaClass.fields;
        Field field = this.fields[value].copy(javaClass.constant_pool);
        int value3 = value;
        value++;
        fields2[value3] = field;
      }
      javaClass.methods = new Method[this.methods.length];
      int value4;
      for (int value5 = value4 = 0; value5 < this.methods.length; value5 = value4) {
        Method[] methods2 = javaClass.methods;
        Method method = this.methods[value4].copy(javaClass.constant_pool);
        int value6 = value4;
        value4++;
        methods2[value6] = method;
      }
      javaClass.attributes = new Attribute[this.attributes.length];
      int value7 = 0;
      while (value7 < this.attributes.length) {
        Attribute[] attributes2 = javaClass.attributes;
        Attribute attribute = this.attributes[value7].copy(javaClass.constant_pool);
        int value8 = value7;
        value7++;
        attributes2[value8] = attribute;
      }
    } catch (CloneNotSupportedException cloneNotSupportedException) {
    }
    return javaClass;
  }

  public Attribute[] getAttributes() {
    return this.attributes;
  }

  public void dump(OutputStream outputStream) throws IOException {
    this.dump(new DataOutputStream(outputStream));
  }

  public String getText2() {
    return this.text2;
  }

  @Override
  public int compareTo(Object result) {
    return this.getText2().compareTo(((JavaClass) result).getText2());
  }

  public void setMethods(Method[] methods2) {
    this.methods = methods2;
  }

  public int getCount2() {
    return this.count2;
  }

  public void setAttributes(Attribute[] attributes2) {
    this.attributes = attributes2;
  }

  public void setText3(String text3) {
    this.text3 = text3;
  }

  public final boolean isEnabled22() {
    this.computeNestedTypeStatus();
    return this.enabled2;
  }

  public AnnotationEntry[] getAnnotationEntries() {
    if (this.enabled3) {
      Attribute[] attributes = this.getAttributes();
      ArrayList values = new ArrayList();
      int value;
      for (int value2 = value = 0; value2 < attributes.length; value2 = ++value) {
        Attribute attribute;
        if ((attribute = attributes[value]) instanceof Annotations) {
          Annotations annotations2 = (Annotations) attribute;
          int value3;
          for (int value4 = value3 = 0;
              value4 < annotations2.getAnnotationEntries().length;
              value4 = value3) {
            AnnotationEntry[] annotationEntries = annotations2.getAnnotationEntries();
            int value5 = value3;
            value3++;
            values.add(annotationEntries[value5]);
          }
        }
      }
      this.annotations = (AnnotationEntry[]) values.toArray(new AnnotationEntry[values.size()]);
      this.enabled3 = false;
    }
    return this.annotations;
  }

  public final boolean matchesJavaClass2(JavaClass javaClass) throws ClassNotFoundException {
    if (this.equals(javaClass)) {
      return true;
    }
    JavaClass[] javaClasss = this.createJavaClasss();
    int value;
    for (int value2 = value = 0; value2 < javaClasss.length; value2 = ++value) {
      if (javaClasss[value].equals(javaClass)) {
        return true;
      }
    }
    return javaClass.isEnabled5() ? this.matchesJavaClass(javaClass) : false;
  }
}
