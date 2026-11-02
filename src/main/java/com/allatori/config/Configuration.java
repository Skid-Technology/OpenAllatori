package com.allatori.config;

import com.allatori.util.random.RandomUtils;

public class Configuration {

  public ObfuscationOptions obfuscationOptions;

  public OutputOptions outputOptions = new OutputOptions(this);

  public LogOptions logOptions;

  public MethodRemovalOptions methodRemovalOptions;

  public MemberOrderOptions memberOrderOptions;

  public WatermarkOptions watermarkOptions;

  public static Configuration configuration = null;

  public RenamingOptions renamingOptions;

  public DebugInfoOptions debugInfoOptions;

  public SyntheticOptions syntheticOptions;

  public IgnoreClassesOptions ignoreClassesOptions;

  public OptimizationOptions optimizationOptions;

  public FieldRemovalOptions fieldRemovalOptions;

  public MethodCallRemovalOptions methodCallRemovalOptions;

  public ClassPathOptions classPathOptions;

  public PackageRenamingOptions packageRenamingOptions;

  public InputOptions inputOptions = new InputOptions(this);

  public MappingOptions mappingOptions;

  public ExtensiveFlowOptions extensiveFlowOptions;

  public static MemberOrderOptions getMemberOrderOptions() {
    return configuration.memberOrderOptions;
  }

  public static void processText(String text) throws ConfigurationException {
    processEnabled(text, false);
  }

  public static InputOptions getInputOptions() {
    return configuration.inputOptions;
  }

  public static IgnoreClassesOptions getIgnoreClassesOptions() {
    return configuration.ignoreClassesOptions;
  }

  public static RenamingOptions getRenamingOptions() {
    return configuration.renamingOptions;
  }

  public static MethodCallRemovalOptions getMethodCallRemovalOptions() {
    return configuration.methodCallRemovalOptions;
  }

  public static ExtensiveFlowOptions getExtensiveFlowOptions() {
    return configuration.extensiveFlowOptions;
  }

  public static void processEnabled(String text, boolean matches) throws ConfigurationException {
    configuration = new Configuration();
    configuration.watermarkOptions.setEnabled(matches);
    new XmlParsingContext(text).parse();
    RandomUtils.update();
    if (getObfuscationOptions().getCharacter() == '\uffff') {
      getObfuscationOptions().setCharacter((char) (RandomUtils.calculateCount(125) + 1));
    }
  }

  public static MethodRemovalOptions getMethodRemovalOptions() {
    return configuration.methodRemovalOptions;
  }

  public static SyntheticOptions getSyntheticOptions() {
    return configuration.syntheticOptions;
  }

  public static ClassPathOptions getClassPathOptions() {
    return configuration.classPathOptions;
  }

  public static FieldRemovalOptions getFieldRemovalOptions() {
    return configuration.fieldRemovalOptions;
  }

  public Configuration() {
    this.classPathOptions = new ClassPathOptions(this);
    this.renamingOptions = new RenamingOptions(this);
    this.watermarkOptions = new WatermarkOptions(this);
    this.obfuscationOptions = new ObfuscationOptions(this);
    this.debugInfoOptions = new DebugInfoOptions(this);
    this.extensiveFlowOptions = new ExtensiveFlowOptions(this);
    this.syntheticOptions = new SyntheticOptions(this);
    this.memberOrderOptions = new MemberOrderOptions(this);
    this.methodCallRemovalOptions = new MethodCallRemovalOptions(this);
    this.optimizationOptions = new OptimizationOptions(this);
    this.fieldRemovalOptions = new FieldRemovalOptions(this);
    this.packageRenamingOptions = new PackageRenamingOptions(this);
    this.ignoreClassesOptions = new IgnoreClassesOptions(this);
    this.logOptions = new LogOptions(this);
    this.methodRemovalOptions = new MethodRemovalOptions(this);
    this.mappingOptions = new MappingOptions(this);
  }

  public static OptimizationOptions getOptimizationOptions() {
    return configuration.optimizationOptions;
  }

  public static void update() {
    configuration = null;
  }

  public static WatermarkOptions getWatermarkOptions() {
    return configuration.watermarkOptions;
  }

  public static LogOptions getLogOptions() {
    return configuration.logOptions;
  }

  public static DebugInfoOptions getDebugInfoOptions() {
    return configuration.debugInfoOptions;
  }

  public static MappingOptions getMappingOptions() {
    return configuration.mappingOptions;
  }

  public static PackageRenamingOptions getPackageRenamingOptions() {
    return configuration.packageRenamingOptions;
  }

  public static ObfuscationOptions getObfuscationOptions() {
    return configuration.obfuscationOptions;
  }

  public static OutputOptions getOutputOptions() {
    return configuration.outputOptions;
  }
}
