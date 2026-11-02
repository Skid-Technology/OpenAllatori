package com.allatori.config;

public class ConfigurationException extends Exception {

  public ConfigurationException(Throwable throwable) {
    super(throwable);
  }

  public ConfigurationException(String text) {
    super(text);
  }
}
