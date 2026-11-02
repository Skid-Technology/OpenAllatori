package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class BootstrapMethod implements Serializable, Cloneable {

  public int[] bootstrap_arguments;

  public int bootstrap_method_ref;

  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    dataOutputStream.writeShort(this.bootstrap_method_ref);
    dataOutputStream.writeShort(this.bootstrap_arguments.length);
    int[] values = this.bootstrap_arguments;
    int value = this.bootstrap_arguments.length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = value2;
      value2++;
      dataOutputStream.writeShort(values[value4]);
    }
  }

  public BootstrapMethod() {}

  public BootstrapMethod(DataInputStream dataInputStream) throws IOException {
    this.bootstrap_method_ref = dataInputStream.readUnsignedShort();
    int value;
    this.bootstrap_arguments = new int[value = dataInputStream.readUnsignedShort()];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int[] values = this.bootstrap_arguments;
      int value4 = dataInputStream.readUnsignedShort();
      int value5 = value2;
      value2++;
      values[value5] = value4;
    }
  }

  public int getBootstrap_method_ref() {
    return this.bootstrap_method_ref;
  }

  public void setBootstrapArguments(int[] bootstrap_arguments) {
    this.bootstrap_arguments = bootstrap_arguments;
  }

  public void setBootstrapMethodRef(int bootstrap_method_ref) {
    this.bootstrap_method_ref = bootstrap_method_ref;
  }

  public int[] getBootstrapArguments() {
    return this.bootstrap_arguments;
  }

  public BootstrapMethod copy() {
    try {
      return (BootstrapMethod) this.clone();
    } catch (CloneNotSupportedException cloneNotSupportedException) {
      return null;
    }
  }
}
