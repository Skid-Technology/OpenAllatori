package com.allatori.lib.asm;

public abstract class ModuleVisitor {

  public ModuleVisitor mv;

  public final int api;

  public ModuleVisitor(int api, ModuleVisitor moduleVisitor) {
    super();
    if (api != 589824
        && api != 524288
        && api != 458752
        && api != 393216
        && api != 327680
        && api != 262144
        && api != 17432576) {
      throw new IllegalArgumentException(stringConcat$0(api));
    }
    if (api == 17432576) {
      Constants.checkAsmExperimental(this);
    }
    this.api = api;
    this.mv = moduleVisitor;
  }

  public ModuleVisitor(int value) {
    this(value, null);
  }

  public void visitEnd() {
    if (this.mv != null) {
      this.mv.visitEnd();
    }
  }

  public void processText(String text) {
    if (this.mv != null) {
      this.mv.processText(text);
    }
  }

  public void processText2(String text) {
    if (this.mv != null) {
      this.mv.processText2(text);
    }
  }

  public void processTexts(String text, int value, String... text2) {
    if (this.mv != null) {
      this.mv.processTexts(text, value, (String[]) text2);
    }
  }

  public void processText3(String text) {
    if (this.mv != null) {
      this.mv.processText3(text);
    }
  }

  public static String stringConcat$0(int value) {
    return "Unsupported api " + value;
  }

  public ModuleVisitor getDelegate() {
    return this.mv;
  }

  public void visitRequire(String text, int value, String text2) {
    if (this.mv != null) {
      this.mv.visitRequire(text, value, text2);
    }
  }

  public void visitProvide(String text, String... text2) {
    if (this.mv != null) {
      this.mv.visitProvide(text, (String[]) text2);
    }
  }

  public void processTexts2(String text, int value, String... text2) {
    if (this.mv != null) {
      this.mv.processTexts2(text, value, (String[]) text2);
    }
  }
}
