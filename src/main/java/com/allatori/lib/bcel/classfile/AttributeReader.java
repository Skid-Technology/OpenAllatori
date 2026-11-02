package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;

public interface AttributeReader {

  Attribute createAttribute(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool);
}
