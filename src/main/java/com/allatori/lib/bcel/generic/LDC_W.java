package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.util.ByteSequence;
import java.io.IOException;

public class LDC_W extends LDC {

  @Override
  public void initFromFile(ByteSequence byteSequence, boolean matches) throws IOException {
    this.setIndex(byteSequence.readUnsignedShort());
    this.opcode = 19;
    this.length = 3;
  }

  public LDC_W() {}

  public LDC_W(int value) {
    super(value);
  }
}
