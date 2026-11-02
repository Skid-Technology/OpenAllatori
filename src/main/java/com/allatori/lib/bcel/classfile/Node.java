package com.allatori.lib.bcel.classfile;

public interface Node {

  void accept(Visitor visitor);
}
