package com.allatori.lib.asm;

import java.util.Arrays;

public final class ConstantDynamic {

  public final String name;

  public final String descriptor;

  public final Handle bootstrapMethod;

  public final Object[] bootstrapMethodArguments;

  public static String stringConcat$0(String text, String text2, String text3, String text4) {
    return "" + text + " : " + text2 + " " + text3 + " " + text4;
  }

  public ConstantDynamic(String name, String descriptor, Handle handle, Object... result) {
    super();
    this.name = name;
    this.descriptor = descriptor;
    this.bootstrapMethod = handle;
    this.bootstrapMethodArguments = (Object[]) result;
  }

  public int getBootstrapMethodArgumentCount() {
    return this.bootstrapMethodArguments.length;
  }

  public Handle getBootstrapMethod() {
    return this.bootstrapMethod;
  }

  @Override
  public boolean equals(Object result) {
    if (result == this) {
      return true;
    }
    if (!(result instanceof ConstantDynamic)) {
      return false;
    }
    ConstantDynamic constantDynamic = (ConstantDynamic) result;
    return this.name.equals(constantDynamic.name)
        && this.descriptor.equals(constantDynamic.descriptor)
        && this.bootstrapMethod.equals(constantDynamic.bootstrapMethod)
        && Arrays.equals(this.bootstrapMethodArguments, constantDynamic.bootstrapMethodArguments);
  }

  @Override
  public int hashCode() {
    return this.name.hashCode()
        ^ Integer.rotateLeft(this.descriptor.hashCode(), 8)
        ^ Integer.rotateLeft(this.bootstrapMethod.hashCode(), 16)
        ^ Integer.rotateLeft(Arrays.hashCode(this.bootstrapMethodArguments), 24);
  }

  public Object[] getBootstrapMethodArgumentsUnsafe() {
    return this.bootstrapMethodArguments;
  }

  public String getName() {
    return this.name;
  }

  public String getDescriptor() {
    return this.descriptor;
  }

  public Object getBootstrapMethodArgument(int value) {
    return this.bootstrapMethodArguments[value];
  }

  public int getSize() {
    char value;
    return (value = this.descriptor.charAt(0)) != 74 && value != 68 ? 1 : 2;
  }
}
