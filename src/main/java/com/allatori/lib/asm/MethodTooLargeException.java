package com.allatori.lib.asm;

public final class MethodTooLargeException extends IndexOutOfBoundsException {

  public final int codeSize;

  public final String className;

  public static final long serialVersionUID = 6807380416709738314L;

  public final String descriptor;

  public final String methodName;

  public static String stringConcat$0(String text, String text2, String text3) {
    return "Method too large: " + text + "." + text2 + " " + text3;
  }

  public String getDescriptor() {
    return this.descriptor;
  }

  public int getCodeSize() {
    return this.codeSize;
  }

  public String getClassName() {
    return this.className;
  }

  public String getMethodName() {
    return this.methodName;
  }

  public MethodTooLargeException(
      String className, String methodName, String descriptor, int codeSize) {
    super(stringConcat$0(className, methodName, descriptor));
    this.className = className;
    this.methodName = methodName;
    this.descriptor = descriptor;
    this.codeSize = codeSize;
  }
}
