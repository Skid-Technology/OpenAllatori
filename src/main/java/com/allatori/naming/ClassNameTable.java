package com.allatori.naming;

import com.allatori.config.Configuration;
import com.allatori.naming.generator.NameGenerator;
import com.allatori.util.LookupTable;

public class ClassNameTable {

  public NameGenerator nameGenerator;

  public LookupTable lookupTable;

  public final NamingContext namingContext;

  public LookupTable lookupTable2;

  public static NameGenerator getNameGenerator(ClassNameTable classNameTable) {
    return classNameTable.nameGenerator;
  }

  public ClassNameTable(NamingContext namingContext2) {
    super();
    this.namingContext = namingContext2;
    this.nameGenerator =
        Configuration.getRenamingOptions().getNameGenerationOptions().createNameGenerator4();
    this.lookupTable = new LookupTable();
    this.lookupTable2 = new LookupTable();
  }

  public static LookupTable getLookupTable(ClassNameTable classNameTable) {
    return classNameTable.lookupTable2;
  }

  public static LookupTable getLookupTable2(ClassNameTable classNameTable) {
    return classNameTable.lookupTable;
  }

  public ClassNameTable(
      NamingContext namingContext, NamingConstructorToken namingConstructorToken) {
    this(namingContext);
  }
}
