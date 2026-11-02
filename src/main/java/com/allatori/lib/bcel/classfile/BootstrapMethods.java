package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BootstrapMethods extends Attribute {

  public BootstrapMethod[] bootstrap_methods;

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.bootstrap_methods.length);
    BootstrapMethod[] bootstrapMethods = this.bootstrap_methods;
    int value = this.bootstrap_methods.length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = value2;
      value2++;
      bootstrapMethods[value4].dump(dataOutputStream);
    }
  }

  public final BootstrapMethod[] getBootstrapMethods() {
    return this.bootstrap_methods;
  }

  public BootstrapMethods(
      int value, int value2, BootstrapMethod[] bootstrapMethods, ConstantPool constantPool) {
    super((byte) 20, value, value2, constantPool);
    this.bootstrap_methods = bootstrapMethods;
  }

  @Override
  public void accept(Visitor visitor) {}

  public final void setBootstrapMethods(BootstrapMethod[] bootstrapMethods) {
    this.bootstrap_methods = bootstrapMethods;
  }

  public BootstrapMethods(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, (BootstrapMethod[]) null, constantPool);
    int value3;
    this.bootstrap_methods = new BootstrapMethod[value3 = dataInputStream.readUnsignedShort()];
    int value4;
    for (int value5 = value4 = 0; value5 < value3; value5 = value4) {
      BootstrapMethod[] bootstrapMethods = this.bootstrap_methods;
      BootstrapMethod bootstrapMethod = new BootstrapMethod(dataInputStream);
      int value6 = value4;
      value4++;
      bootstrapMethods[value6] = bootstrapMethod;
    }
  }

  public BootstrapMethods copy(ConstantPool constantPool) {
    BootstrapMethods bootstrapMethods;
    (bootstrapMethods = (BootstrapMethods) this.clone()).bootstrap_methods =
        new BootstrapMethod[this.bootstrap_methods.length];
    int value;
    for (int value2 = value = 0; value2 < this.bootstrap_methods.length; value2 = value) {
      BootstrapMethod[] bootstrapMethods2 = bootstrapMethods.bootstrap_methods;
      int value3 = value;
      BootstrapMethod bootstrapMethod = this.bootstrap_methods[value].copy();
      value++;
      bootstrapMethods2[value3] = bootstrapMethod;
    }
    bootstrapMethods.constant_pool = constantPool;
    return bootstrapMethods;
  }
}
