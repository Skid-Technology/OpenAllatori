package com.allatori.config;

import org.xml.sax.Locator;

public class ConfigurationLocator implements Locator {

  public final ConfigurationHandler configurationHandler;

  @Override
  public int getLineNumber() {
    return -1;
  }

  @Override
  public String getPublicId() {
    return null;
  }

  @Override
  public int getColumnNumber() {
    return -1;
  }

  public ConfigurationLocator(ConfigurationHandler configurationHandler2) {
    super();
    this.configurationHandler = configurationHandler2;
  }

  @Override
  public String getSystemId() {
    return null;
  }
}
