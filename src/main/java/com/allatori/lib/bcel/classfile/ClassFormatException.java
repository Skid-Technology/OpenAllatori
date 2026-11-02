package com.allatori.lib.bcel.classfile;

public class ClassFormatException extends RuntimeException {

  public ClassFormatException() {}

  public ClassFormatException(String text, Throwable throwable) {
    super(text, throwable);
  }

  public ClassFormatException(String text) {
    super(text);
  }
}
