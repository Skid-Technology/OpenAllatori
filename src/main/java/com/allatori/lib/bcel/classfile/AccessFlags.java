package com.allatori.lib.bcel.classfile;

import java.io.Serializable;

public abstract class AccessFlags implements Serializable {

  public int access_flags;

  public final void processEnabled(boolean matches) {
    this.setFlag(256, matches);
  }

  public final void processEnabled2(boolean matches) {
    this.setFlag(16, matches);
  }

  public final void processEnabled3(boolean matches) {
    this.setFlag(1024, matches);
  }

  public final boolean isEnabled() {
    return (this.access_flags & 64) != 0;
  }

  public final int getAccess_flags() {
    return this.access_flags;
  }

  public final boolean isEnabled2() {
    return (this.access_flags & 8192) != 0;
  }

  public final void setAccess_flags(int access_flags) {
    this.access_flags = access_flags;
  }

  public final boolean isEnabled3() {
    return (this.access_flags & 16) != 0;
  }

  public final void processCount(int value) {
    this.setAccess_flags(value);
  }

  public final void processEnabled4(boolean matches) {
    this.setFlag(8, matches);
  }

  public final void processEnabled5(boolean matches) {
    this.setFlag(2, matches);
  }

  public AccessFlags(int access_flags) {
    super();
    this.access_flags = access_flags;
  }

  public final void processEnabled6(boolean matches) {
    this.setFlag(128, matches);
  }

  public final void processEnabled7(boolean matches) {
    this.setFlag(4, matches);
  }

  public final void processEnabled8(boolean matches) {
    this.setFlag(2048, matches);
  }

  public final boolean isEnabled4() {
    return (this.access_flags & 128) != 0;
  }

  public final boolean isEnabled5() {
    return (this.access_flags & 512) != 0;
  }

  public final boolean isEnabled6() {
    return (this.access_flags & 1) != 0;
  }

  public final boolean isEnabled7() {
    return (this.access_flags & 2048) != 0;
  }

  public final int getAccess_flags2() {
    return this.access_flags;
  }

  public final void setFlag(int access_flags, boolean matches) {
    if ((this.access_flags & access_flags) != 0) {
      if (!matches) {
        this.access_flags ^= access_flags;
        return;
      }
    } else if (matches) {
      this.access_flags |= access_flags;
    }
  }

  public final boolean isEnabled8() {
    return (this.access_flags & 4096) != 0;
  }

  public final void processEnabled9(boolean matches) {
    this.setFlag(4096, matches);
  }

  public final void processEnabled10(boolean matches) {
    this.setFlag(8192, matches);
  }

  public final boolean isEnabled9() {
    return (this.access_flags & 2) != 0;
  }

  public final void processEnabled11(boolean matches) {
    this.setFlag(512, matches);
  }

  public final void processEnabled12(boolean matches) {
    this.setFlag(32, matches);
  }

  public final void processEnabled13(boolean matches) {
    this.setFlag(1, matches);
  }

  public final boolean isEnabled10() {
    return (this.access_flags & 16384) != 0;
  }

  public final void processEnabled14(boolean matches) {
    this.setFlag(16384, matches);
  }

  public final void processEnabled15(boolean matches) {
    this.setFlag(64, matches);
  }

  public final boolean isEnabled11() {
    return (this.access_flags & 8) != 0;
  }

  public AccessFlags() {}

  public final boolean isEnabled12() {
    return (this.access_flags & 32) != 0;
  }

  public final boolean isEnabled13() {
    return (this.access_flags & 4) != 0;
  }

  public final boolean isEnabled14() {
    return (this.access_flags & 256) != 0;
  }

  public final boolean isEnabled15() {
    return (this.access_flags & 1024) != 0;
  }
}
