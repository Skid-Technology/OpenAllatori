package com.allatori.lib.asm;

public class ByteVector {

  public int length;

  public byte[] data = new byte[64];

  public final ByteVector encodeUtf8(String text, int value, int value2) {
    int size = text.length();
    int value3 = value;
    int value4;
    for (int value5 = value4 = value; value5 < size; value5 = ++value4) {
      char value6;
      if ((value6 = text.charAt(value4)) >= 1 && value6 <= 127) {
        value3++;
      } else if (value6 <= 2047) {
        value3 += 2;
      } else {
        value3 += 3;
      }
    }
    if (value3 > value2) {
      throw new IllegalArgumentException("UTF8 string too large");
    }
    int value7;
    if ((value7 = this.length - value - 2) >= 0) {
      this.data[value7] = (byte) (value3 >>> 8);
      this.data[value7 + 1] = (byte) value3;
    }
    if (this.length + value3 - value > this.data.length) {
      this.enlarge(value3 - value);
    }
    int value8 = this.length;
    int value9;
    for (int value10 = value9 = value; value10 < size; value10 = ++value9) {
      char value11;
      if ((value11 = text.charAt(value9)) >= 1 && value11 <= 127) {
        byte value12 = (byte) value11;
        int value13 = value8;
        byte[] values = this.data;
        value8++;
        values[value13] = value12;
      } else if (value11 <= 2047) {
        char value14 = value11;
        int value15 = value8;
        byte[] values2 = this.data;
        value8++;
        values2[value15] = (byte) ((short) 192 | value14 >> (byte) 6 & 31);
        char value16 = value11;
        int value17 = value8;
        byte[] values3 = this.data;
        value8++;
        values3[value17] = (byte) ((short) 128 | value16 & (byte) 63);
      } else {
        char value18 = value11;
        int value19 = value8;
        byte[] values4 = this.data;
        value8++;
        values4[value19] = (byte) ((short) 224 | value18 >> (byte) 12 & 15);
        char value20 = value11;
        int value21 = value8;
        byte[] values5 = this.data;
        value8++;
        values5[value21] = (byte) ((short) 128 | value20 >> (byte) 6 & 63);
        char value22 = value11;
        int value23 = value8;
        byte[] values6 = this.data;
        value8++;
        values6[value23] = (byte) ((short) 128 | value22 & (byte) 63);
      }
    }
    this.length = value8;
    return this;
  }

  public int size() {
    return this.length;
  }

  public ByteVector putInt(int value) {
    int value2 = this.length;
    if (this.length + 4 > this.data.length) {
      this.enlarge(4);
    }
    int value3 = value >>> 24;
    int value4 = value2;
    byte[] values = this.data;
    byte[] values2 = this.data;
    byte[] values3 = this.data;
    byte[] values4 = this.data;
    value2++;
    values[value4] = (byte) value3;
    int value5 = value >>> 16;
    int value6 = value2;
    value2++;
    values2[value6] = (byte) value5;
    int value7 = value >>> 8;
    int value8 = value2;
    value2++;
    values3[value8] = (byte) value7;
    byte value9 = (byte) value;
    int value10 = value2;
    value2++;
    values4[value10] = value9;
    this.length = value2;
    return this;
  }

  public final ByteVector put112(int value, int value2, int value3) {
    int value4 = this.length;
    if (this.length + 4 > this.data.length) {
      this.enlarge(4);
    }
    byte value5 = (byte) value;
    int value6 = value4;
    byte[] values = this.data;
    byte[] values2 = this.data;
    byte[] values3 = this.data;
    byte[] values4 = this.data;
    value4++;
    values[value6] = value5;
    byte value7 = (byte) value2;
    int value8 = value4;
    value4++;
    values2[value8] = value7;
    int value9 = value3 >>> 8;
    int value10 = value4;
    value4++;
    values3[value10] = (byte) value9;
    byte value11 = (byte) value3;
    int value12 = value4;
    value4++;
    values4[value12] = value11;
    this.length = value4;
    return this;
  }

  public ByteVector putByteArray(byte[] values, int value, int length) {
    if (this.length + length > this.data.length) {
      this.enlarge(length);
    }
    if (values != null) {
      System.arraycopy(values, value, this.data, this.length, length);
    }
    this.length += length;
    return this;
  }

  public final ByteVector put12(int value, int value2) {
    int value3 = this.length;
    if (this.length + 3 > this.data.length) {
      this.enlarge(3);
    }
    byte value4 = (byte) value;
    int value5 = value3;
    byte[] values = this.data;
    byte[] values2 = this.data;
    byte[] values3 = this.data;
    value3++;
    values[value5] = value4;
    int value6 = value2 >>> 8;
    int value7 = value3;
    value3++;
    values2[value7] = (byte) value6;
    byte value8 = (byte) value2;
    int value9 = value3;
    value3++;
    values3[value9] = value8;
    this.length = value3;
    return this;
  }

  public final ByteVector put11(int value, int value2) {
    int value3 = this.length;
    if (this.length + 2 > this.data.length) {
      this.enlarge(2);
    }
    byte value4 = (byte) value;
    int value5 = value3;
    byte[] values = this.data;
    byte[] values2 = this.data;
    value3++;
    values[value5] = value4;
    byte value6 = (byte) value2;
    int value7 = value3;
    value3++;
    values2[value7] = value6;
    this.length = value3;
    return this;
  }

  public void enlarge(int value) {
    if (this.length > this.data.length) {
      throw new AssertionError("Internal error");
    }
    int value2 = 2 * this.data.length;
    int value3 = this.length + value;
    byte[] values;
    byte[] values2 = values = new byte[value2 > value3 ? value2 : value3];
    System.arraycopy(this.data, 0, values, 0, this.length);
    this.data = values2;
  }

  public ByteVector() {}

  public final ByteVector put122(int value, int value2, int value3) {
    int value4 = this.length;
    if (this.length + 5 > this.data.length) {
      this.enlarge(5);
    }
    byte value5 = (byte) value;
    int value6 = value4;
    byte[] values = this.data;
    byte[] values2 = this.data;
    byte[] values3 = this.data;
    byte[] values4 = this.data;
    byte[] values5 = this.data;
    value4++;
    values[value6] = value5;
    int value7 = value2 >>> 8;
    int value8 = value4;
    value4++;
    values2[value8] = (byte) value7;
    byte value9 = (byte) value2;
    int value10 = value4;
    value4++;
    values3[value10] = value9;
    int value11 = value3 >>> 8;
    int value12 = value4;
    value4++;
    values4[value12] = (byte) value11;
    byte value13 = (byte) value3;
    int value14 = value4;
    value4++;
    values5[value14] = value13;
    this.length = value4;
    return this;
  }

  public ByteVector(byte[] data) {
    super();
    this.data = data;
    this.length = data.length;
  }

  public ByteVector putShort(int value) {
    int value2 = this.length;
    if (this.length + 2 > this.data.length) {
      this.enlarge(2);
    }
    int value3 = value >>> 8;
    int value4 = value2;
    byte[] values = this.data;
    byte[] values2 = this.data;
    value2++;
    values[value4] = (byte) value3;
    byte value5 = (byte) value;
    int value6 = value2;
    value2++;
    values2[value6] = value5;
    this.length = value2;
    return this;
  }

  public ByteVector putLong(long value) {
    int value2 = this.length;
    if (this.length + 8 > this.data.length) {
      this.enlarge(8);
    }
    byte[] values = this.data;
    int value3 = (int) (value >>> 32);
    int value4 = value3 >>> 24;
    int value5 = value2;
    value2++;
    values[value5] = (byte) value4;
    int value6 = value3 >>> 16;
    int value7 = value2;
    value2++;
    values[value7] = (byte) value6;
    int value8 = value3 >>> 8;
    int value9 = value2;
    value2++;
    values[value9] = (byte) value8;
    byte value10 = (byte) value3;
    int value11 = value2;
    value2++;
    values[value11] = value10;
    int value12 = (int) value;
    int value13 = value12 >>> 24;
    int value14 = value2;
    value2++;
    values[value14] = (byte) value13;
    int value15 = value12 >>> 16;
    int value16 = value2;
    value2++;
    values[value16] = (byte) value15;
    int value17 = value12 >>> 8;
    int value18 = value2;
    value2++;
    values[value18] = (byte) value17;
    byte value19 = (byte) value12;
    int value20 = value2;
    value2++;
    values[value20] = value19;
    this.length = value2;
    return this;
  }

  public ByteVector putByte(int value) {
    int value2 = this.length;
    if (this.length + 1 > this.data.length) {
      this.enlarge(1);
    }
    byte value3 = (byte) value;
    int value4 = value2;
    byte[] values = this.data;
    value2++;
    values[value4] = value3;
    this.length = value2;
    return this;
  }

  public ByteVector(int value) {
    super();
    this.data = new byte[value];
  }

  public ByteVector putUTF8(String text) {
    int value;
    if ((value = text.length()) > 65535) {
      throw new IllegalArgumentException("UTF8 string too large");
    }
    int value2 = this.length;
    if (this.length + 2 + value > this.data.length) {
      this.enlarge(2 + value);
    }
    byte[] values = this.data;
    int value3 = value >>> 8;
    int value4 = value2;
    byte[] values2 = this.data;
    byte[] values3 = this.data;
    value2++;
    values2[value4] = (byte) value3;
    byte value5 = (byte) value;
    int value6 = value2;
    value2++;
    values3[value6] = value5;
    int value7;
    for (int value8 = value7 = 0; value8 < value; value8 = ++value7) {
      char value9;
      if ((value9 = text.charAt(value7)) < 1 || value9 > 127) {
        this.length = value2;
        return this.encodeUtf8(text, value7, 65535);
      }
      byte value10 = (byte) value9;
      int value11 = value2;
      value2++;
      values[value11] = value10;
    }
    this.length = value2;
    return this;
  }
}
