package com.allatori.model;

import com.allatori.bytecode.MethodFactory;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.MethodGen;

public class MethodContext {

  public MethodGen methodGen;

  public Method method;

  public ClassGen classGen;

  public static ClassGen getClassGen(MethodContext methodContext) {
    return methodContext.classGen;
  }

  public MethodContext(ClassGen classGen2, Method method2) {
    super();
    this.classGen = classGen2;
    this.method = method2;
    this.methodGen =
        MethodFactory.createMethodGen(
            method2,
            classGen2.getClassName(),
            classGen2.getConstantPool(),
            classGen2.getConstantPool().getConstantPool());
  }

  public static Method getMethod(MethodContext methodContext) {
    return methodContext.method;
  }

  public static MethodGen getMethodGen(MethodContext methodContext) {
    return methodContext.methodGen;
  }
}
