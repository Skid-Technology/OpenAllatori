package com.allatori.config;

public interface ConfigurationSource {

  void parse() throws ConfigurationException;
}
