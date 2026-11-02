package com.allatori.lib.asm;

public final class Handle {

  public final boolean isInterface;

  public final String name;

  public final int tag;

  public final String descriptor;

  public final String owner;

  public String getOwner() {
    return this.owner;
  }

  public int getTag() {
    return this.tag;
  }

  public Handle(int tag, String owner, String name, String descriptor, boolean isInterface) {
    super();
    this.tag = tag;
    this.owner = owner;
    this.name = name;
    this.descriptor = descriptor;
    this.isInterface = isInterface;
  }

  @Override
  public boolean equals(Object result) {
    if (result == this) {
      return true;
    }
    if (!(result instanceof Handle)) {
      return false;
    }
    Handle handle = (Handle) result;
    return this.tag == handle.tag
        && this.isInterface == handle.isInterface
        && this.owner.equals(handle.owner)
        && this.name.equals(handle.name)
        && this.descriptor.equals(handle.descriptor);
  }

  @Deprecated
  public Handle(int value, String text, String text2, String text3) {
    this(value, text, text2, text3, value == 9);
  }

  public String getName() {
    return this.name;
  }

  public String getDescriptor() {
    return this.descriptor;
  }

  public boolean isInterface() {
    return this.isInterface;
  }

  @Override
  public int hashCode() {
    return this.tag
        + (this.isInterface ? 64 : 0)
        + this.owner.hashCode() * this.name.hashCode() * this.descriptor.hashCode();
  }

  public static String stringConcat$0(
      String text, String text2, String text3, int value, String text4) {
    return "" + text + "." + text2 + text3 + " (" + value + text4 + ")";
  }
}
