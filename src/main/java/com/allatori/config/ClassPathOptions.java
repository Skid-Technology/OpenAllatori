package com.allatori.config;

import java.util.Vector;

public class ClassPathOptions {

  public final Configuration configuration;

  public Vector values;

  public void processText(String text) {
    this.values.add(text);
  }

  public Vector getValues() {
    return this.values;
  }

  public ClassPathOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.values = new Vector();
  }
}
