package com.allatori.lib.bcel.generic;

public interface ConstantPushInstruction extends PushInstruction, TypedInstruction {

  Number getValue();
}
