package com.allatori.lib.bcel.classfile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class Signature extends Attribute {

  public int signature_index;

  public final String getText() {
    return ((ConstantUtf8) this.constant_pool.getConstant(this.signature_index, (byte) 1))
        .getBytes();
  }

  public static final boolean isFormalParameterList(String text) {
    return text.startsWith("<") && text.indexOf(58) > 0;
  }

  public Signature(
      int value, int value2, DataInputStream dataInputStream, ConstantPool constantPool)
      throws IOException {
    this(value, value2, dataInputStream.readUnsignedShort(), constantPool);
  }

  public Signature(Signature signature) {
    this(
        signature.getName_index(),
        signature.getLength(),
        signature.getSignatureIndex(),
        signature.getConstantPool());
  }

  public static boolean identStart(int value) {
    return value == 84 || value == 76;
  }

  public static String translate(String text) {
    StringBuffer stringBuffer = new StringBuffer();
    processStringBuffer2(new Signature$MyByteArrayInputStream(text), stringBuffer);
    return stringBuffer.toString();
  }

  public static final boolean isActualParameterList(String text) {
    return text.startsWith("L") && text.endsWith(">;");
  }

  public final int getSignatureIndex() {
    return this.signature_index;
  }

  @Override
  public final void dump(DataOutputStream dataOutputStream) throws IOException {
    super.dump(dataOutputStream);
    dataOutputStream.writeShort(this.signature_index);
  }

  @Override
  public Attribute copy(ConstantPool constantPool) {
    return (Signature) this.clone();
  }

  public Signature(int value, int value2, int signature_index, ConstantPool constantPool) {
    super((byte) 10, value, value2, constantPool);
    this.signature_index = signature_index;
  }

  public static final void processStringBuffer(
      Signature$MyByteArrayInputStream signature$MyByteArrayInputStream,
      StringBuffer stringBuffer) {
    int value;
    if ((value = signature$MyByteArrayInputStream.read()) == -1) {
      throw new RuntimeException(
          ("Illegal signature: "
              + signature$MyByteArrayInputStream.getData()
              + " no ident, reaching EOF"));
    }
    if (!identStart(value)) {
      StringBuffer stringBuffer2 = new StringBuffer();
      int value2 = 1;
      for (int value3 = value; Character.isJavaIdentifierPart((char) value3); value3 = value) {
        value2++;
        stringBuffer2.append((char) value);
        value = signature$MyByteArrayInputStream.read();
      }
      if (value == 58) {
        "Ljava/lang/Object".length();
        signature$MyByteArrayInputStream.skip(17L);
        stringBuffer.append(stringBuffer2);
        signature$MyByteArrayInputStream.read();
        signature$MyByteArrayInputStream.unread();
      } else {
        int value4;
        for (int value5 = value4 = 0; value5 < value2; value5 = value4) {
          value4++;
          signature$MyByteArrayInputStream.unread();
        }
      }
    } else {
      StringBuffer stringBuffer3 = new StringBuffer();
      int value6 = signature$MyByteArrayInputStream.read();
      do {
        stringBuffer3.append((char) value6);
        value6 = signature$MyByteArrayInputStream.read();
      } while (value6 != -1 && (Character.isJavaIdentifierPart((char) value6) || value6 == 47));
      stringBuffer.append(stringBuffer3.toString().replace('/', '.'));
      if (value6 != -1) {
        signature$MyByteArrayInputStream.unread();
      }
    }
  }

  public static final void processStringBuffer2(
      Signature$MyByteArrayInputStream signature$MyByteArrayInputStream,
      StringBuffer stringBuffer) {
    processStringBuffer(signature$MyByteArrayInputStream, stringBuffer);
    int value;
    Signature$MyByteArrayInputStream signature$MyByteArrayInputStream2;
    if ((value = signature$MyByteArrayInputStream.read()) != 60 && value != 40) {
      signature$MyByteArrayInputStream2 = signature$MyByteArrayInputStream;
      signature$MyByteArrayInputStream.unread();
    } else {
      stringBuffer.append((char) value);
      processStringBuffer2(signature$MyByteArrayInputStream, stringBuffer);
      int value2;
      for (Signature$MyByteArrayInputStream signature$MyByteArrayInputStream3 =
              signature$MyByteArrayInputStream;
          (value2 = signature$MyByteArrayInputStream3.read()) != 62 && value2 != 41;
          signature$MyByteArrayInputStream3 = signature$MyByteArrayInputStream) {
        if (value2 == -1) {
          throw new RuntimeException(
              ("Illegal signature: "
                  + signature$MyByteArrayInputStream.getData()
                  + " reaching EOF"));
        }
        stringBuffer.append(", ");
        signature$MyByteArrayInputStream.unread();
        processStringBuffer2(signature$MyByteArrayInputStream, stringBuffer);
      }
      stringBuffer.append((char) value2);
      signature$MyByteArrayInputStream2 = signature$MyByteArrayInputStream;
    }
    int value3;
    if (identStart(value3 = signature$MyByteArrayInputStream2.read())) {
      signature$MyByteArrayInputStream.unread();
      processStringBuffer2(signature$MyByteArrayInputStream, stringBuffer);
    } else if (value3 == 41) {
      signature$MyByteArrayInputStream.unread();
    } else {
      if (value3 != 59) {
        throw new RuntimeException(
            ("Illegal signature: "
                + signature$MyByteArrayInputStream.getData()
                + " read "
                + ((char) value3)));
      }
    }
  }

  @Override
  public void accept(Visitor visitor) {
    visitor.visitSignature(this);
  }

  public final void setSignatureIndex(int signature_index) {
    this.signature_index = signature_index;
  }
}
