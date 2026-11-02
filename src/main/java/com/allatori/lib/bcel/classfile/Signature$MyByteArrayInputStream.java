package com.allatori.lib.bcel.classfile;

import java.io.ByteArrayInputStream;

public final class Signature$MyByteArrayInputStream extends ByteArrayInputStream {

  public final int mark() {
    return this.pos;
  }

  public final void reset(int pos) {
    this.pos = pos;
  }

  public Signature$MyByteArrayInputStream(String text) {
    super(text.getBytes());
  }

  public final String getData() {
    return new String(this.buf);
  }

  public final void unread() {
    if (this.pos > 0) {
      this.pos--;
    }
  }
}
