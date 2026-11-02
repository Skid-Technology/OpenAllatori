package com.allatori.lib.asm;

public abstract class Symbol {

  public static final int CONSTANT_NAME_AND_TYPE_TAG = 12;

  public static final int UNINITIALIZED_TYPE_TAG = 129;

  public final int tag;

  public final String value;

  public static final int TYPE_TAG = 128;

  public static final int MERGED_TYPE_TAG = 131;

  public static final int BOOTSTRAP_METHOD_TAG = 64;

  public static final int CONSTANT_INTERFACE_METHODREF_TAG = 11;

  public static final int CONSTANT_CLASS_TAG = 7;

  public final long data;

  public static final int CONSTANT_INTEGER_TAG = 3;

  public static final int CONSTANT_FLOAT_TAG = 4;

  public static final int CONSTANT_INVOKE_DYNAMIC_TAG = 18;

  public static final int CONSTANT_METHOD_TYPE_TAG = 16;

  public static final int CONSTANT_DYNAMIC_TAG = 17;

  public static final int CONSTANT_DOUBLE_TAG = 6;

  public static final int CONSTANT_METHOD_HANDLE_TAG = 15;

  public final String name;

  public static final int FORWARD_UNINITIALIZED_TYPE_TAG = 130;

  public static final int CONSTANT_LONG_TAG = 5;

  public static final int CONSTANT_STRING_TAG = 8;

  public static final int CONSTANT_PACKAGE_TAG = 20;

  public int count;

  public static final int CONSTANT_MODULE_TAG = 19;

  public static final int CONSTANT_UTF8_TAG = 1;

  public final String owner;

  public final int index;

  public static final int CONSTANT_FIELDREF_TAG = 9;

  public static final int CONSTANT_METHODREF_TAG = 10;

  public int getArgumentsAndReturnSizes() {
    if (this.count == 0) {
      this.count = Type.getArgumentsAndReturnSizes(this.value);
    }
    return this.count;
  }

  public Symbol(int index, int tag, String owner, String name, String value, long data) {
    super();
    this.index = index;
    this.tag = tag;
    this.owner = owner;
    this.name = name;
    this.value = value;
    this.data = data;
  }
}
