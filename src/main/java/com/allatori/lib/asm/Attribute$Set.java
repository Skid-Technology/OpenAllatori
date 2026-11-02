package com.allatori.lib.asm;

public final class Attribute$Set {

  public static final int SIZE_INCREMENT = 6;

  public int size;

  public Attribute[] data = new Attribute[6];

  public void addAttributes(Attribute attribute) {
    Attribute attribute2;
    for (Attribute attribute3 = attribute2 = attribute;
        attribute3 != null;
        attribute3 = attribute2 = attribute2.nextAttribute) {
      if (!this.contains(attribute2)) {
        this.add(attribute2);
      }
    }
  }

  public Attribute[] toArray() {
    Attribute[] attributes = new Attribute[this.size];
    System.arraycopy(this.data, 0, attributes, 0, this.size);
    return attributes;
  }

  public void add(Attribute attribute) {
    if (this.size >= this.data.length) {
      Attribute[] attributes;
      Attribute[] attributes2 = attributes = new Attribute[this.data.length + 6];
      System.arraycopy(this.data, 0, attributes, 0, this.size);
      this.data = attributes2;
    }
    this.data[this.size++] = attribute;
  }

  public boolean contains(Attribute attribute) {
    int value;
    for (int value2 = value = 0; value2 < this.size; value2 = ++value) {
      if (this.data[value].type.equals(attribute.type)) {
        return true;
      }
    }
    return false;
  }
}
