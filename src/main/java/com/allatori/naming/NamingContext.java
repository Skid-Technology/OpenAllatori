package com.allatori.naming;

public class NamingContext {

  public PackageNameTable packageNameTable;

  public MethodNameTable methodNameTable;

  public ParameterNameTable parameterNameTable;

  public FieldNameTable fieldNameTable;

  public final NameRenamer nameRenamer;

  public ClassNameTable classNameTable;

  public static MethodNameTable getMethodNameTable(NamingContext namingContext) {
    return namingContext.methodNameTable;
  }

  public NamingContext(NameRenamer nameRenamer, NamingConstructorToken namingConstructorToken) {
    this(nameRenamer);
  }

  public static ParameterNameTable getParameterNameTable(NamingContext namingContext) {
    return namingContext.parameterNameTable;
  }

  public static ClassNameTable getClassNameTable(NamingContext namingContext) {
    return namingContext.classNameTable;
  }

  public static PackageNameTable getPackageNameTable(NamingContext namingContext) {
    return namingContext.packageNameTable;
  }

  public NamingContext(NameRenamer nameRenamer2) {
    super();
    this.nameRenamer = nameRenamer2;
    this.classNameTable = new ClassNameTable(this, null);
    this.parameterNameTable = new ParameterNameTable(this, null);
    this.methodNameTable = new MethodNameTable(this, null);
    this.fieldNameTable = new FieldNameTable(this, null);
    this.packageNameTable = new PackageNameTable(this, null);
  }

  public static FieldNameTable getFieldNameTable(NamingContext namingContext) {
    return namingContext.fieldNameTable;
  }
}
