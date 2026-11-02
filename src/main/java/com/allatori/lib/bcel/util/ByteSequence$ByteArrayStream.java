package com.allatori.lib.bcel.util;

import java.io.ByteArrayInputStream;

public final class ByteSequence$ByteArrayStream extends ByteArrayInputStream {

  public final int getPosition() {
    return this.pos;
  }

  public ByteSequence$ByteArrayStream(byte[] values) {
    super(values);
  }

  public final void unreadByte() {
    if (this.pos > 0) {
      this.pos--;
    }
  }
}
