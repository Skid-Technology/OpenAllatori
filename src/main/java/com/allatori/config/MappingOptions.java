package com.allatori.config;

import com.allatori.naming.RenamingLog;
import com.allatori.util.LookupTable;

public class MappingOptions {

  public LookupTable lookupTable;

  public RenamingLog renamingLog;

  public final Configuration configuration;

  public LookupTable lookupTable2;

  public MappingOptions(Configuration configuration2) {
    super();
    this.configuration = configuration2;
  }
}
