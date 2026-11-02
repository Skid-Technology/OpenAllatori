package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.classfile.JavaClass;
import java.io.Serializable;
import java.util.Stack;

public class ClassStack implements Serializable {

  public Stack stack = new Stack();

  public JavaClass pop() {
    return (JavaClass) this.stack.pop();
  }

  public void push(JavaClass javaClass) {
    this.stack.push(javaClass);
  }

  public JavaClass top() {
    return (JavaClass) this.stack.peek();
  }

  public boolean empty() {
    return this.stack.empty();
  }
}
