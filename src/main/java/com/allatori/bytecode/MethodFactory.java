package com.allatori.bytecode;

import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.MethodGen;

public class MethodFactory {

  public static MethodGen createMethodGen(
      Method method, String text, ConstantPoolGen constantPoolGen, ConstantPool constantPool) {
    method.setConstantPool(constantPool);
    return new MethodGen(method, text, constantPoolGen);
  }
}
