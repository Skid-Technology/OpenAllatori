package com.allatori.lib.bcel.util;

import java.io.DataInputStream;

public final class ByteSequence extends DataInputStream {

  public ByteSequence$ByteArrayStream byte_stream;

  public final int getIndex() {
    return this.byte_stream.getPosition();
  }

  public ByteSequence(byte[] values) {
    super(new ByteSequence$ByteArrayStream(values));
    this.byte_stream = (ByteSequence$ByteArrayStream) this.in;
  }

  public final void unreadByte() {
    this.byte_stream.unreadByte();
  }
}
