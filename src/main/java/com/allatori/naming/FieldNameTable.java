package com.allatori.naming;

import com.allatori.config.Configuration;
import com.allatori.naming.generator.NameGenerator;
import com.allatori.util.LookupTable;

public class FieldNameTable {

  public final NamingContext namingContext;

  public LookupTable lookupTable;

  public NameGenerator nameGenerator;

  public LookupTable lookupTable2;

  public LookupTable lookupTable3;

  public static LookupTable getLookupTable(FieldNameTable fieldNameTable) {
    return fieldNameTable.lookupTable2;
  }

  public static LookupTable getLookupTable2(FieldNameTable fieldNameTable) {
    return fieldNameTable.lookupTable3;
  }

  public static NameGenerator getNameGenerator(FieldNameTable fieldNameTable) {
    return fieldNameTable.nameGenerator;
  }

  public FieldNameTable(NamingContext namingContext2) {
    super();
    this.namingContext = namingContext2;
    this.nameGenerator =
        Configuration.getRenamingOptions().getNameGenerationOptions().createNameGenerator5();
    this.lookupTable2 = new LookupTable();
    this.lookupTable = new LookupTable();
    this.lookupTable3 = new LookupTable();
  }

  public FieldNameTable(
      NamingContext namingContext, NamingConstructorToken namingConstructorToken) {
    this(namingContext);
  }

  public static LookupTable getLookupTable3(FieldNameTable fieldNameTable) {
    return fieldNameTable.lookupTable;
  }
}
