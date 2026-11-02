package com.allatori.lib.bcel.classfile;

import java.io.DataInput;
import java.io.DataOutputStream;
import java.io.IOException;

public class MethodParameters extends Attribute {

  public MethodParameter[] parameters;

  public static final MethodParameter[] METHOD_PARAMETERS = new MethodParameter[0];

  @Override
  public void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeByte(this.parameters.length);
    MethodParameter[] methodParameters = this.parameters;
    int value = this.parameters.length;
    int value2;
    for (int value3 = value2 = 0; value3 < value; value3 = value2) {
      int value4 = value2;
      value2++;
      methodParameters[value4].dump(dataOutputStream);
    }
  }

  public MethodParameter[] getParameters() {
    return this.parameters;
  }

  public void setParameters(MethodParameter[] methodParameters) {
    this.parameters = methodParameters != null ? methodParameters : METHOD_PARAMETERS;
  }

  @Override
  public void accept(Visitor visitor) {}

  @Override
  public Attribute copy(ConstantPool constantPool) {
    MethodParameters methodParameters;
    (methodParameters = (MethodParameters) this.clone()).parameters =
        new MethodParameter[this.parameters.length];
    int value;
    for (int value2 = value = 0; value2 < this.parameters.length; value2 = value) {
      MethodParameter[] methodParameters2 = methodParameters.parameters;
      MethodParameter methodParameter = this.parameters[value].copy();
      int value3 = value;
      value++;
      methodParameters2[value3] = methodParameter;
    }
    methodParameters.setConstantPool(constantPool);
    return methodParameters;
  }

  public MethodParameters(int value, int value2, DataInput dataInput, ConstantPool constantPool)
      throws IOException {
    super((byte) 21, value, value2, constantPool);
    this.parameters = METHOD_PARAMETERS;
    int value3;
    this.parameters = new MethodParameter[value3 = dataInput.readUnsignedByte()];
    int value4;
    for (int value5 = value4 = 0; value5 < value3; value5 = value4) {
      MethodParameter[] methodParameters = this.parameters;
      MethodParameter methodParameter = new MethodParameter(dataInput);
      int value6 = value4;
      value4++;
      methodParameters[value6] = methodParameter;
    }
  }
}
