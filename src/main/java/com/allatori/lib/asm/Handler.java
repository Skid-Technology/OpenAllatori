package com.allatori.lib.asm;

public final class Handler {

  public final String catchTypeDescriptor;

  public final int catchType;

  public final Label handlerPc;

  public Handler nextHandler;

  public final Label startPc;

  public final Label endPc;

  public static Handler removeRange(Handler handler, Label label, Label label2) {
    if (handler == null) {
      return null;
    }
    handler.nextHandler = removeRange(handler.nextHandler, label, label2);
    int value = handler.startPc.bytecodeOffset;
    int value2 = handler.endPc.bytecodeOffset;
    int value3 = label.bytecodeOffset;
    int value4 = label2 == null ? Integer.MAX_VALUE : label2.bytecodeOffset;
    if (value3 >= value2 || value4 <= value) {
      return handler;
    }
    if (value3 <= value) {
      return value4 >= value2 ? handler.nextHandler : new Handler(handler, label2, handler.endPc);
    }
    if (value4 >= value2) {
      return new Handler(handler, handler.startPc, label);
    }
    handler.nextHandler = new Handler(handler, label2, handler.endPc);
    return new Handler(handler, handler.startPc, label);
  }

  public static void putExceptionTable(Handler handler, ByteVector byteVector) {
    byteVector.putShort(calculateCount(handler));
    Handler handler2 = handler;
    for (Handler handler3 = handler2; handler3 != null; handler3 = handler2) {
      byteVector
          .putShort(handler2.startPc.bytecodeOffset)
          .putShort(handler2.endPc.bytecodeOffset)
          .putShort(handler2.handlerPc.bytecodeOffset)
          .putShort(handler2.catchType);
      handler2 = handler2.nextHandler;
    }
  }

  public static int calculateCount(Handler handler) {
    int value = 0;
    Handler handler2;
    Handler handler3 = handler2 = handler;
    while (handler3 != null) {
      Handler handler4 = handler2.nextHandler;
      Handler handler5 = handler2.nextHandler;
      value++;
      handler3 = handler5;
      handler2 = handler4;
    }
    return value;
  }

  public Handler(
      Label label, Label label2, Label label3, int catchType, String catchTypeDescriptor) {
    super();
    this.startPc = label;
    this.endPc = label2;
    this.handlerPc = label3;
    this.catchType = catchType;
    this.catchTypeDescriptor = catchTypeDescriptor;
  }

  public static int calculateCount2(Handler handler) {
    return 2 + 8 * calculateCount(handler);
  }

  public Handler(Handler handler, Label label, Label label2) {
    this(label, label2, handler.handlerPc, handler.catchType, handler.catchTypeDescriptor);
    this.nextHandler = handler.nextHandler;
  }
}
