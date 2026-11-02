package com.allatori.config;

import com.allatori.naming.generator.AsciiNameGenerator;
import com.allatori.naming.generator.JavaKeywordNameGenerator;
import com.allatori.naming.generator.LengthBoundNameGenerator;
import com.allatori.naming.generator.LowercaseConfusableNameGenerator;
import com.allatori.naming.generator.LowercaseNameGenerator;
import com.allatori.naming.generator.MainNameGenerator;
import com.allatori.naming.generator.MethodNameGenerator;
import com.allatori.naming.generator.MixedConfusableNameGenerator;
import com.allatori.naming.generator.NameGenerator;
import com.allatori.naming.generator.NumericNameGenerator;
import com.allatori.naming.generator.PackageNameGenerator;
import com.allatori.naming.generator.PrefixedNameGenerator;
import com.allatori.naming.generator.RandomizedNameGenerator;
import com.allatori.naming.generator.TypeNameGenerator;
import com.allatori.naming.generator.UppercaseNameGenerator;
import com.allatori.naming.generator.WindowsDeviceNameGenerator;
import java.util.Locale;

public class NameGenerationOptions {

  public String[] texts;

  public static final int COUNT = 3;

  public static final int COUNT2 = 1;

  public static final int COUNT3 = 9;

  public String[] texts2;

  public static final int COUNT4 = 4;

  public String methodsNamingPrefix;

  public static final int COUNT5 = 8;

  public String text2;

  public String[] texts3;

  public int classesNamingMode;

  public final RenamingOptions renamingOptions;

  public static final int COUNT6 = 2;

  public String classesNamingPrefix;

  public String[] texts4;

  public static final int COUNT7 = 6;

  public String fieldsNamingPrefix;

  public int packagesNamingMode;

  public int methodsNamingMode;

  public int fieldsNamingMode;

  public boolean fieldsNamingEnabled;

  public boolean classesNamingEnabled;

  public static final int COUNT8 = 5;

  public boolean methodsNamingEnabled;

  public static final int COUNT9 = 7;

  public void setTexts3(String[] texts) {
    this.texts3 = texts;
  }

  public void setMethodsNamingPrefix(String methodsNamingPrefix) {
    this.methodsNamingPrefix = methodsNamingPrefix;
  }

  public NameGenerator createNameGenerator() {
    return new LengthBoundNameGenerator(new LowercaseNameGenerator());
  }

  public void setEnabled(boolean fieldsNamingEnabled) {
    this.fieldsNamingEnabled = fieldsNamingEnabled;
  }

  public void setTexts(String[] texts2) {
    this.texts = texts2;
  }

  public String getText() {
    if (this.methodsNamingMode == 5) {
      return MixedConfusableNameGenerator.nextName2();
    } else {
      return this.methodsNamingMode == 9
          ? LowercaseConfusableNameGenerator.nextName2()
          : RenamingOptions.getLocalVariableOptions(this.renamingOptions).getLocalVariablesNaming();
    }
  }

  public NameGenerator createNameGenerator2() {
    NameGenerator nameGenerator;
    NameGenerationOptions nameGenerationOptions;
    if (this.methodsNamingMode == 1) {
      nameGenerator = new LowercaseNameGenerator();
      nameGenerationOptions = this;
    } else if (this.methodsNamingMode == 8) {
      nameGenerator = new UppercaseNameGenerator();
      nameGenerationOptions = this;
    } else if (this.methodsNamingMode == 3) {
      nameGenerator = new JavaKeywordNameGenerator();
      nameGenerationOptions = this;
    } else if (this.methodsNamingMode == 5) {
      nameGenerator = new MixedConfusableNameGenerator();
      nameGenerationOptions = this;
    } else if (this.methodsNamingMode == 9) {
      nameGenerator = new LowercaseConfusableNameGenerator();
      nameGenerationOptions = this;
    } else if (this.methodsNamingMode == 6) {
      nameGenerator = new NumericNameGenerator();
      nameGenerationOptions = this;
    } else if (this.methodsNamingMode == 7) {
      nameGenerator = new PackageNameGenerator(this.texts);
      nameGenerationOptions = this;
    } else {
      nameGenerator = new AsciiNameGenerator();
      nameGenerationOptions = this;
    }
    if (nameGenerationOptions.methodsNamingEnabled) {
      nameGenerator = new MainNameGenerator(nameGenerator, false, true);
    }
    NameGenerator nameGenerator2 = new LengthBoundNameGenerator(nameGenerator);
    if (this.text2 != null) {
      nameGenerator2 = new MethodNameGenerator(this.text2, nameGenerator2);
    }
    if (this.methodsNamingPrefix != null) {
      nameGenerator2 = new TypeNameGenerator(this.methodsNamingPrefix, nameGenerator2);
    }
    return nameGenerator2;
  }

  public NameGenerator createNameGenerator3() {
    if (RenamingOptions.getLocalVariableOptions(this.renamingOptions).calculateCount() == 2) {
      return new LowercaseNameGenerator();
    } else {
      return RenamingOptions.getLocalVariableOptions(this.renamingOptions).calculateCount() == 1
          ? new PrefixedNameGenerator(
              RenamingOptions.getLocalVariableOptions(this.renamingOptions)
                  .getLocalVariablesNaming())
          : new LowercaseNameGenerator();
    }
  }

  public NameGenerator createNameGenerator4() {
    NameGenerator nameGenerator;
    if (this.packagesNamingMode == 6) {
      nameGenerator = new NumericNameGenerator();
    } else if (this.packagesNamingMode == 7) {
      nameGenerator = new PackageNameGenerator(this.texts4);
    } else if (this.packagesNamingMode == 8) {
      nameGenerator = new UppercaseNameGenerator();
    } else {
      nameGenerator = new LowercaseNameGenerator();
    }
    return new LengthBoundNameGenerator(nameGenerator);
  }

  public NameGenerationOptions(RenamingOptions renamingOptions2) {
    super();
    this.renamingOptions = renamingOptions2;
    this.packagesNamingMode = 1;
    this.classesNamingMode = 2;
    this.classesNamingEnabled = false;
    this.fieldsNamingMode = 2;
    this.methodsNamingMode = 2;
    this.fieldsNamingEnabled = false;
    this.methodsNamingEnabled = false;
    this.text2 = null;
    this.fieldsNamingPrefix = null;
    this.methodsNamingPrefix = null;
    this.classesNamingPrefix = null;
    this.texts2 = null;
    this.texts = null;
    this.texts3 = null;
    this.texts4 = null;
  }

  public boolean isClassesNamingEnabled() {
    return this.classesNamingEnabled;
  }

  public void setMethodsNamingMode(int methodsNamingMode) {
    this.methodsNamingMode = methodsNamingMode;
  }

  public void setFieldsNamingMode(int fieldsNamingMode) {
    this.fieldsNamingMode = fieldsNamingMode;
  }

  public boolean isFieldsNamingEnabled() {
    return this.fieldsNamingEnabled;
  }

  public void setClassesNamingPrefix(String classesNamingPrefix) {
    this.classesNamingPrefix = classesNamingPrefix;
  }

  public NameGenerator createNameGenerator5() {
    NameGenerator nameGenerator;
    NameGenerationOptions nameGenerationOptions;
    if (this.fieldsNamingMode == 1) {
      nameGenerator = new LowercaseNameGenerator();
      nameGenerationOptions = this;
    } else if (this.fieldsNamingMode == 8) {
      nameGenerator = new UppercaseNameGenerator();
      nameGenerationOptions = this;
    } else if (this.fieldsNamingMode == 3) {
      nameGenerator = new JavaKeywordNameGenerator();
      nameGenerationOptions = this;
    } else if (this.fieldsNamingMode == 5) {
      nameGenerator = new MixedConfusableNameGenerator();
      nameGenerationOptions = this;
    } else if (this.fieldsNamingMode == 9) {
      nameGenerator = new LowercaseConfusableNameGenerator();
      nameGenerationOptions = this;
    } else if (this.fieldsNamingMode == 6) {
      nameGenerator = new NumericNameGenerator();
      nameGenerationOptions = this;
    } else if (this.fieldsNamingMode == 7) {
      nameGenerator = new PackageNameGenerator(this.texts2);
      nameGenerationOptions = this;
    } else {
      nameGenerator = new AsciiNameGenerator();
      nameGenerationOptions = this;
    }
    if (nameGenerationOptions.fieldsNamingEnabled) {
      nameGenerator = new MainNameGenerator(nameGenerator, true, false);
    }
    NameGenerator nameGenerator2 = new LengthBoundNameGenerator(nameGenerator);
    if (this.text2 != null) {
      nameGenerator2 = new MethodNameGenerator(this.text2, nameGenerator2);
    }
    if (this.fieldsNamingPrefix != null) {
      nameGenerator2 = new TypeNameGenerator(this.fieldsNamingPrefix, nameGenerator2);
    }
    return nameGenerator2;
  }

  public void setTexts4(String[] texts) {
    this.texts4 = texts;
  }

  public void setEnabled3(boolean methodsNamingEnabled) {
    this.methodsNamingEnabled = methodsNamingEnabled;
  }

  public NameGenerator createNameGenerator6() {
    NameGenerator nameGenerator;
    NameGenerationOptions nameGenerationOptions;
    if (this.classesNamingMode == 1) {
      nameGenerator = new LowercaseNameGenerator();
      nameGenerationOptions = this;
    } else if (this.classesNamingMode == 8) {
      nameGenerator = new UppercaseNameGenerator();
      nameGenerationOptions = this;
    } else if (this.classesNamingMode == 4) {
      nameGenerator = new WindowsDeviceNameGenerator();
      nameGenerationOptions = this;
    } else if (this.classesNamingMode == 5) {
      nameGenerator = new MixedConfusableNameGenerator();
      nameGenerationOptions = this;
    } else if (this.classesNamingMode == 9) {
      nameGenerator = new LowercaseConfusableNameGenerator();
      nameGenerationOptions = this;
    } else if (this.classesNamingMode == 6) {
      nameGenerator = new NumericNameGenerator();
      nameGenerationOptions = this;
    } else if (this.classesNamingMode == 7) {
      nameGenerator = new PackageNameGenerator(this.texts3);
      nameGenerationOptions = this;
    } else {
      nameGenerator = new AsciiNameGenerator();
      nameGenerationOptions = this;
    }
    String text;
    if (nameGenerationOptions.classesNamingMode != 1
        && this.classesNamingMode != 8
        && this.classesNamingMode != 6
        && this.classesNamingMode != 9
        && this.classesNamingMode != 7
        && Configuration.getInputOptions().getValues2().size() > 0
        && ((text = System.getProperty("os.name").toLowerCase(Locale.ENGLISH)) == null
            || text.indexOf("win") >= 0
            || text.indexOf("mac") >= 0)) {
      nameGenerator = new LowercaseNameGenerator();
    }
    if (Configuration.getObfuscationOptions().isEnabled3()) {
      nameGenerator = new RandomizedNameGenerator(nameGenerator);
    }
    NameGenerator nameGenerator2 = new LengthBoundNameGenerator(nameGenerator);
    if (this.classesNamingPrefix != null) {
      nameGenerator2 = new TypeNameGenerator(this.classesNamingPrefix, nameGenerator2);
    }
    return nameGenerator2;
  }

  public void setEnabled2(boolean classesNamingEnabled) {
    this.classesNamingEnabled = classesNamingEnabled;
  }

  public NameGenerationOptions(
      RenamingOptions renamingOptions,
      NamingOptionsConstructorToken namingOptionsConstructorToken) {
    this(renamingOptions);
  }

  public void setTexts2(String[] texts) {
    this.texts2 = texts;
  }

  public void processText(String text2) {
    if (this.text2 == null) {
      this.text2 = text2;
    } else {
      this.text2 = "" + this.text2 + this.text2.substring(8, 9) + text2;
    }
  }

  public void setFieldsNamingPrefix(String fieldsNamingPrefix) {
    this.fieldsNamingPrefix = fieldsNamingPrefix;
  }

  public void setClassesNamingMode(int classesNamingMode) {
    this.classesNamingMode = classesNamingMode;
  }

  public boolean isMethodsNamingEnabled() {
    return this.methodsNamingEnabled;
  }

  public void setPackagesNamingMode(int packagesNamingMode) {
    this.packagesNamingMode = packagesNamingMode;
  }
}
