package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;

public class LocalVariablesInfo {

  public LocalVariableInfo[] localVariableInfos;

  public void add(int value, String text, int value2, int value3, Type type) {
    if (value >= 0 && value < this.localVariableInfos.length) {
      this.localVariableInfos[value].add(text, value2, value3, type);
      if (type == Type.LONG) {
        this.localVariableInfos[value + 1].add(
            text, value2, value3, UpperWordType.getUpperWordType());
      }
      if (type == Type.DOUBLE) {
        this.localVariableInfos[value + 1].add(
            text, value2, value3, UpperWordType2.getUpperWordType2());
      }
    } else {
      throw new AssertionViolatedException(
          "Slot number for local variable information out of range.");
    }
  }

  public LocalVariablesInfo(int value) {
    this.localVariableInfos = new LocalVariableInfo[value];
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      LocalVariableInfo[] localVariableInfos2 = this.localVariableInfos;
      LocalVariableInfo localVariableInfo = new LocalVariableInfo();
      int value4 = value2;
      value2++;
      localVariableInfos2[value4] = localVariableInfo;
    }
  }

  public LocalVariableInfo getLocalVariableInfo(int value) {
    if (value >= 0 && value < this.localVariableInfos.length) {
      return this.localVariableInfos[value];
    } else {
      throw new AssertionViolatedException(
          "Slot number for local variable information out of range.");
    }
  }
}
