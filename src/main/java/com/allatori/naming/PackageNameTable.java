package com.allatori.naming;

import com.allatori.config.Configuration;
import com.allatori.naming.generator.NameGenerator;

public class PackageNameTable {

  public final NamingContext namingContext;

  public NameGenerator nameGenerator;

  public static NameGenerator getNameGenerator(PackageNameTable packageNameTable) {
    return packageNameTable.nameGenerator;
  }

  public PackageNameTable(NamingContext namingContext2) {
    super();
    this.namingContext = namingContext2;
    this.nameGenerator =
        Configuration.getRenamingOptions().getNameGenerationOptions().createNameGenerator3();
  }

  public PackageNameTable(
      NamingContext namingContext, NamingConstructorToken namingConstructorToken) {
    this(namingContext);
  }
}
