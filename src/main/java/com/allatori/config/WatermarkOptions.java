package com.allatori.config;

public class WatermarkOptions {

  public boolean enabled;

  public String text;

  public final Configuration configuration;

  public String text2;

  public void setText(String text) {
    this.text = text;
  }

  public WatermarkOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.enabled = false;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return this.enabled;
  }

  public void setText2(String text2) {
    this.text2 = text2;
  }

  public String getText2() {
    return this.text2;
  }

  public String getText() {
    return this.text;
  }
}
