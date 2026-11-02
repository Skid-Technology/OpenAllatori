package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.classfile.JavaClass;
import java.io.Serializable;
import java.util.LinkedList;

public class ClassQueue implements Serializable {

  public LinkedList vec = new LinkedList();

  public boolean empty() {
    return this.vec.isEmpty();
  }

  public JavaClass dequeue() {
    return (JavaClass) this.vec.removeFirst();
  }

  public void enqueue(JavaClass javaClass) {
    this.vec.addLast(javaClass);
  }
}
