package com.allatori.config;

public class LogOptions {

  public String comLicense4jSupport;

  public final Configuration configuration;

  public void setComLicense4jSupport(String comLicense4jSupport) {
    this.comLicense4jSupport = comLicense4jSupport;
  }

  public boolean isText() {
    return this.comLicense4jSupport != null;
  }

  public LogOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.comLicense4jSupport = null;
  }

  public String getComLicense4jSupport() {
    return this.comLicense4jSupport;
  }
}
