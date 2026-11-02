package com.allatori.lib.bcel.classfile;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ClassParser {

  public int count;

  public int count2;

  public Method[] methods;

  public String zip_file;

  public int count3;

  public boolean fileOwned;

  public DataInputStream file;

  public Field[] fields;

  public String file_name;

  public int count4;

  public static final int BUFSIZE = 8192;

  public boolean is_zip;

  public Attribute[] attributes;

  public int[] interfaces;

  public int count5;

  public ConstantPool constant_pool;

  public final void readInterfaces() throws IOException, ClassFormatException {
    int value;
    this.interfaces = new int[value = this.file.readUnsignedShort()];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int[] values = this.interfaces;
      int value4 = this.file.readUnsignedShort();
      int value5 = value2;
      value2++;
      values[value5] = value4;
    }
  }

  public final void readID() throws IOException, ClassFormatException {
    int value = -889275714;
    if (this.file.readInt() != value) {
      throw new ClassFormatException(("" + this.file_name + " is not a Java .class file"));
    }
  }

  public final void readFields() throws IOException, ClassFormatException {
    int value;
    this.fields = new Field[value = this.file.readUnsignedShort()];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      Field[] fields2 = this.fields;
      Field field = new Field(this.file, this.constant_pool);
      int value4 = value2;
      value2++;
      fields2[value4] = field;
    }
  }

  public final void readClassInfo() throws IOException, ClassFormatException {
    this.count5 = this.file.readUnsignedShort();
    if ((this.count5 & 512) != 0) {
      this.count5 |= 1024;
    }
    if ((this.count5 & 1024) != 0 && (this.count5 & 16) != 0) {
      throw new ClassFormatException("Class can't be both final and abstract");
    }
    this.count = this.file.readUnsignedShort();
    this.count2 = this.file.readUnsignedShort();
  }

  public final void readConstantPool() throws IOException, ClassFormatException {
    this.constant_pool = new ConstantPool(this.file);
  }

  public JavaClass parse() throws IOException, ClassFormatException {
    ZipFile zipFile = null;
    try {
      ClassParser classParser;
      label59:
      {
        if (this.fileOwned) {
          if (this.is_zip) {
            ZipEntry entry = (zipFile = new ZipFile(this.zip_file)).getEntry(this.file_name);
            classParser = this;
            this.file =
                new DataInputStream(new BufferedInputStream(zipFile.getInputStream(entry), 8192));
            break label59;
          }
          this.file =
              new DataInputStream(
                  new BufferedInputStream(new FileInputStream(this.file_name), 8192));
        }
        classParser = this;
      }
      classParser.readID();
      this.readVersion();
      this.readConstantPool();
      this.readClassInfo();
      this.readInterfaces();
      this.readFields();
      this.readMethods();
      this.readAttributes();
    } finally {
      if (this.fileOwned) {
        this.file.close();
        if (zipFile != null) {
          zipFile.close();
        }
      }
    }
    return new JavaClass(
        this.count,
        this.count2,
        this.file_name,
        this.count3,
        this.count4,
        this.count5,
        this.constant_pool,
        this.interfaces,
        this.fields,
        this.methods,
        this.attributes,
        (byte) (this.is_zip ? 3 : 2));
  }

  public ClassParser(String file_name) throws IOException {
    super();
    this.is_zip = false;
    this.file_name = file_name;
    this.fileOwned = true;
  }

  public final void readVersion() throws IOException, ClassFormatException {
    this.count4 = this.file.readUnsignedShort();
    this.count3 = this.file.readUnsignedShort();
  }

  public final void readAttributes() throws IOException, ClassFormatException {
    int value;
    this.attributes = new Attribute[value = this.file.readUnsignedShort()];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      Attribute[] attributes2 = this.attributes;
      Attribute attribute = Attribute.readAttribute(this.file, this.constant_pool);
      int value4 = value2;
      value2++;
      attributes2[value4] = attribute;
    }
  }

  public ClassParser(InputStream inputStream, String file_name) {
    super();
    this.file_name = file_name;
    this.fileOwned = false;
    String text2;
    this.is_zip =
        (text2 = inputStream.getClass().getName()).startsWith("java.util.zip.")
            || text2.startsWith("java.util.jar.");
    if (inputStream instanceof DataInputStream) {
      this.file = (DataInputStream) inputStream;
    } else {
      this.file = new DataInputStream(new BufferedInputStream(inputStream, 8192));
    }
  }

  public final void readMethods() throws IOException {
    int value;
    this.methods = new Method[value = this.file.readUnsignedShort()];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      Method[] methods2 = this.methods;
      Method method = new Method(this.file, this.constant_pool);
      int value4 = value2;
      value2++;
      methods2[value4] = method;
    }
  }

  public ClassParser(String zip_file, String file_name) {
    super();
    this.is_zip = true;
    this.fileOwned = true;
    this.zip_file = zip_file;
    this.file_name = file_name;
  }
}
