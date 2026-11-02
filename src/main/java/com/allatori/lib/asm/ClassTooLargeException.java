package com.allatori.lib.asm;

public final class ClassTooLargeException extends IndexOutOfBoundsException {

  public final int constantPoolCount;

  public final String className;

  public static final long serialVersionUID = 160715609518896765L;

  public int getConstantPoolCount() {
    return this.constantPoolCount;
  }

  public String getClassName() {
    return this.className;
  }

  public ClassTooLargeException(String className, int constantPoolCount) {
    super(stringConcat$0(className));
    this.className = className;
    this.constantPoolCount = constantPoolCount;
  }

  public static String stringConcat$0(String text) {
    return "Class too large: " + text;
  }
}
