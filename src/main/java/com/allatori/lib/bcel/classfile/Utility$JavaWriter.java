package com.allatori.lib.bcel.classfile;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public class Utility$JavaWriter extends FilterWriter {

  @Override
  public void write(String text, int value, int value2) throws IOException {
    this.write(text.toCharArray(), value, value2);
  }

  @Override
  public void write(char[] values, int value, int value2) throws IOException {
    int value3;
    for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
      int value5 = value + value3;
      value3++;
      this.write(values[value5]);
    }
  }

  @Override
  public void write(int value) throws IOException {
    if (Utility.isJavaIdentifierPart((char) value) && value != 36) {
      this.out.write(value);
    } else {
      this.out.write(36);
      if (value >= 0 && value < 48) {
        this.out.write(Utility.counts2[value]);
      } else {
        char[] values;
        if ((values = Integer.toHexString(value).toCharArray()).length == 1) {
          this.out.write(48);
          this.out.write(values[0]);
        } else {
          this.out.write(values[0]);
          this.out.write(values[1]);
        }
      }
    }
  }

  public Utility$JavaWriter(Writer writer) {
    super(writer);
  }
}
