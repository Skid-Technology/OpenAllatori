package com.allatori.config;

public class OutputOptions {

  public int compressionLevel;

  public String logFile;

  public final Configuration configuration;

  public boolean keepDuplicateEntries;

  public void setEnabled(boolean keepDuplicateEntries) {
    this.keepDuplicateEntries = keepDuplicateEntries;
  }

  public OutputOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
    this.compressionLevel = -1;
    this.keepDuplicateEntries = false;
  }

  public void setCompressionLevel(int compressionLevel) {
    this.compressionLevel = compressionLevel;
  }

  public boolean isKeepDuplicateEntries() {
    return this.keepDuplicateEntries;
  }

  public void setLogFile(String logFile) {
    this.logFile = logFile;
  }

  public String getLogFile() {
    return this.logFile;
  }

  public int getCompressionLevel() {
    return this.compressionLevel;
  }
}
