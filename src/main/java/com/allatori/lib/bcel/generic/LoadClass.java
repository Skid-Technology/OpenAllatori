package com.allatori.lib.bcel.generic;

public interface LoadClass {

  ObjectType getLoadClassType(ConstantPoolGen constantPoolGen);

  Type getType(ConstantPoolGen constantPoolGen);
}
