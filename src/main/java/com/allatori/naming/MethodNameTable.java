package com.allatori.naming;

import com.allatori.config.Configuration;
import com.allatori.naming.generator.NameGenerator;
import com.allatori.util.LookupTable;

public class MethodNameTable {

  public LookupTable lookupTable;

  public LookupTable lookupTable2;

  public NameGenerator nameGenerator;

  public LookupTable lookupTable3;

  public final NamingContext namingContext;

  public LookupTable lookupTable4;

  public LookupTable lookupTable5;

  public static LookupTable getLookupTable(MethodNameTable methodNameTable) {
    return methodNameTable.lookupTable;
  }

  public static LookupTable getLookupTable2(MethodNameTable methodNameTable) {
    return methodNameTable.lookupTable3;
  }

  public MethodNameTable(NamingContext namingContext2) {
    super();
    this.namingContext = namingContext2;
    this.nameGenerator =
        Configuration.getRenamingOptions().getNameGenerationOptions().createNameGenerator2();
    this.lookupTable3 = new LookupTable();
    this.lookupTable4 = new LookupTable();
    this.lookupTable5 = new LookupTable();
    this.lookupTable = new LookupTable();
    this.lookupTable2 = new LookupTable();
  }

  public static LookupTable getLookupTable3(MethodNameTable methodNameTable) {
    return methodNameTable.lookupTable5;
  }

  public static NameGenerator getNameGenerator(MethodNameTable methodNameTable) {
    return methodNameTable.nameGenerator;
  }

  public static LookupTable getLookupTable4(MethodNameTable methodNameTable) {
    return methodNameTable.lookupTable2;
  }

  public MethodNameTable(
      NamingContext namingContext, NamingConstructorToken namingConstructorToken) {
    this(namingContext);
  }

  public static LookupTable getLookupTable5(MethodNameTable methodNameTable) {
    return methodNameTable.lookupTable4;
  }
}
