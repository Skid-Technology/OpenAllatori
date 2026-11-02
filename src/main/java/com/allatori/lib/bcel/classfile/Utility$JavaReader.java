package com.allatori.lib.bcel.classfile;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class Utility$JavaReader extends FilterReader {

  public Utility$JavaReader(Reader reader) {
    super(reader);
  }

  @Override
  public int read(char[] values, int value, int value2) throws IOException {
    int value3;
    for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
      int value5 = value + value3;
      char value6 = (char) this.read();
      value3++;
      values[value5] = value6;
    }
    return value2;
  }

  @Override
  public int read() throws IOException {
    int value;
    if ((value = this.in.read()) != 36) {
      return value;
    }
    int value2;
    if ((value2 = this.in.read()) < 0) {
      return -1;
    }
    if (value2 >= 48 && value2 <= 57 || value2 >= 97 && value2 <= 102) {
      int value3;
      if ((value3 = this.in.read()) < 0) {
        return -1;
      }
      char[] values = new char[] {(char) value2, (char) value3};
      return Integer.parseInt(new String(values), 16);
    } else {
      return Utility.counts[value2];
    }
  }
}
