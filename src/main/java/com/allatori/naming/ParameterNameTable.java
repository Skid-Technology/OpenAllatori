package com.allatori.naming;

import com.allatori.config.Configuration;
import com.allatori.naming.generator.NameGenerator;
import com.allatori.util.LookupTable;

public class ParameterNameTable {

  public LookupTable lookupTable;

  public LookupTable lookupTable2;

  public LookupTable lookupTable3;

  public NameGenerator nameGenerator;

  public final NamingContext namingContext;

  public ParameterNameTable(NamingContext namingContext2) {
    super();
    this.namingContext = namingContext2;
    this.nameGenerator =
        Configuration.getRenamingOptions().getNameGenerationOptions().createNameGenerator6();
    this.lookupTable = new LookupTable();
    this.lookupTable3 = new LookupTable();
    this.lookupTable2 = new LookupTable();
  }

  public static LookupTable getLookupTable(ParameterNameTable parameterNameTable) {
    return parameterNameTable.lookupTable;
  }

  public static LookupTable getLookupTable2(ParameterNameTable parameterNameTable) {
    return parameterNameTable.lookupTable2;
  }

  public ParameterNameTable(
      NamingContext namingContext, NamingConstructorToken namingConstructorToken) {
    this(namingContext);
  }

  public static NameGenerator getNameGenerator(ParameterNameTable parameterNameTable) {
    return parameterNameTable.nameGenerator;
  }

  public static LookupTable getLookupTable3(ParameterNameTable parameterNameTable) {
    return parameterNameTable.lookupTable3;
  }
}
