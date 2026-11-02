package com.allatori.config;

import java.util.Vector;

public class InputOptions {

  public final Configuration configuration;

  public Vector values;

  public Vector values2;

  public Vector values3;

  public String text;

  public void setText(String text) {
    this.text = text;
  }

  public void processInputJar(InputJar inputJar) {
    this.values.add(inputJar);
  }

  public String getText() {
    return this.text;
  }

  public Vector getValues() {
    return this.values;
  }

  public Vector getValues3() {
    return this.values3;
  }

  public InputOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.values3 = new Vector();
    this.values = new Vector();
    this.values2 = new Vector();
    this.text = null;
  }

  public void processInputEntry(InputEntry inputEntry) {
    this.values3.add(inputEntry);
  }

  public void processClassPathEntry(ClassPathEntry classPathEntry) {
    this.values2.add(classPathEntry);
  }

  public Vector getValues2() {
    return this.values2;
  }
}
