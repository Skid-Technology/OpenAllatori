package com.allatori.config;

import com.allatori.config.rule.ClassRule;
import com.allatori.config.rule.FieldRule;
import com.allatori.config.rule.MethodRule;
import com.allatori.config.rule.RuleEntry;
import com.allatori.config.rule.TemplateUtils;
import com.allatori.naming.generator.LowercaseConfusableNameGenerator;
import com.allatori.naming.generator.MixedConfusableNameGenerator;
import com.allatori.util.FileUtils;
import com.allatori.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.helpers.DefaultHandler;

public class ConfigurationHandler extends DefaultHandler {

  public static final int COUNT = -1;

  public static final int COUNT2 = 1;

  public String text;

  public String text2;

  public static final int COUNT3 = 3;

  public Locator locator;

  public int count;

  public boolean enabled;

  public static final int COUNT4 = 2;

  public final XmlParsingContext xmlParsingContext;

  public boolean enabled2;

  public ClassRule classRule;

  @Override
  public void startElement(String text3, String text4, String text5, Attributes attributes)
      throws ConfigurationSaxException {
    if (!"config".equals(text5)) {
      if (!"keep-names".equals(text5)) {
        if ("ignore-classes".equals(text5)) {
          this.enabled2 = true;
        } else if ("jars".equals(text5) || "input".equals(text5)) {
          this.count = 1;
          this.text2 = this.createText(attributes, "basedir", false, "", true);
          this.text2 = this.createText(this.text2);
          if (!"".equals(this.text2) && !this.matchesText(this.text2)) {
            throw new ConfigurationSaxException(
                this.xmlParsingContext,
                new StringBuilder()
                    .insert(0, "Invalid base directory '")
                    .append(this.text2)
                    .append("'. ")
                    .append(this.createText())
                    .toString());
          }
          String text6;
          if ((text6 = this.createText(attributes, "single-jar", false, null, true)) != null
              && !new File(text6).isAbsolute()) {
            try {
              text6 = new File(("" + this.text2 + File.separator + text6)).getCanonicalPath();
            } catch (Exception exception) {
              text6 = new File(("" + this.text2 + File.separator + text6)).getAbsolutePath();
            }
          }
          Configuration.getInputOptions().setText(text6);
        } else if ("jar".equals(text5) && this.count == 1) {
          String text7 = this.createText(attributes, "in", true, "", true);
          String text8 =
              this.createText(
                  attributes, "out", !Configuration.getWatermarkOptions().isEnabled(), "", true);
          this.processText2(text7, text8);
        } else if ("nested".equals(text5) && this.count == 1) {
          this.processText4(this.createText(attributes, "in", true, "", true));
        } else if ("dir".equals(text5) && this.count == 1) {
          String text9 = this.createText(attributes, "in", true, "", true);
          String text10 =
              this.createText(
                  attributes, "out", !Configuration.getWatermarkOptions().isEnabled(), "", true);
          this.processText4(text9, text10);
        } else {
          if ("jar".equals(text5) && this.count == 2) {
            String[] texts =
                this.createText(attributes, "name", true, "", true).split(File.pathSeparator);
            int value;
            for (int value2 = value = 0; value2 < texts.length; value2 = value) {
              int value3 = value;
              value++;
              this.processText3(texts[value3]);
            }
          } else if ("classpath".equals(text5)) {
            this.count = 2;
            this.text = this.createText(attributes, "basedir", false, "", true);
            this.text = this.createText(this.text);
            if (!"".equals(this.text) && !this.matchesText(this.text)) {
              throw new ConfigurationSaxException(
                  this.xmlParsingContext,
                  new StringBuilder()
                      .insert(0, "Invalid base directory '")
                      .append(this.text)
                      .append("'. ")
                      .append(this.createText())
                      .toString());
            }
          } else {
            if ("class".equals(text5)) {
              this.count = 3;
              String text11 = this.createText(attributes, "template", false, null, true);
              String text12 = this.createText(attributes, "access", false, null, true);
              String text13 = this.createText(attributes, "ignore", false, null, true);
              String text14 = this.createText(attributes, "stop", false, null, true);
              this.classRule = this.createClassRule(text11, text12, text13, text14, "template");
              if (!this.enabled2) {
                Configuration.getRenamingOptions()
                    .getKeepNamesOptions()
                    .processClassRule(this.classRule);
                return;
              }
              Configuration.getIgnoreClassesOptions().processClassRule(this.classRule);
              return;
            }
            if ("field".equals(text5)) {
              String text15 = this.createText(attributes, "template", false, null, true);
              String text16 = this.createText(attributes, "access", false, null, true);
              String text17 = this.createText(attributes, "stop", false, null, true);
              FieldRule fieldRule = this.createFieldRule(text15, text16, text17);
              if (this.count == 3) {
                if (this.classRule == null) {
                  throw new ConfigurationSaxException(
                      this.xmlParsingContext,
                      new StringBuilder()
                          .insert(0, "Error in configuration. ")
                          .append(this.createText())
                          .toString());
                }
                this.classRule.processFieldRule(fieldRule);
                return;
              }
              Configuration.getRenamingOptions().getKeepNamesOptions().processFieldRule(fieldRule);
              return;
            }
            if ("method".equals(text5)) {
              String text18 = this.createText(attributes, "template", false, null, true);
              String text19 = this.createText(attributes, "access", false, null, true);
              String text20 = this.createText(attributes, "parameters", false, null, true);
              String text21 = this.createText(attributes, "stop", false, null, true);
              MethodRule methodRule = this.createMethodRule(text18, text19, "template", text21);
              if (this.count == 3) {
                if (this.classRule == null) {
                  throw new ConfigurationSaxException(
                      this.xmlParsingContext,
                      new StringBuilder()
                          .insert(0, "Error in configuration. ")
                          .append(this.createText())
                          .toString());
                }
                this.classRule.processMethodRule(methodRule);
                if ("keep".equals(text20)) {
                  ClassRule classRule2;
                  (classRule2 = this.classRule.createClassRule()).processMethodRule(methodRule);
                  LocalVariableOptions localVariableOptions =
                      Configuration.getRenamingOptions().getLocalVariableOptions();
                  Configuration.getRenamingOptions().getLocalVariableOptions();
                  localVariableOptions.processRuleEntry(new RuleEntry(5, classRule2));
                  return;
                }
              } else {
                Configuration.getRenamingOptions()
                    .getKeepNamesOptions()
                    .processMethodRule(methodRule);
                if ("keep".equals(text20)) {
                  try {
                    ClassRule classRule3;
                    (classRule3 = new ClassRule("class *", false, false))
                        .processMethodRule(methodRule);
                    LocalVariableOptions localVariableOptions2 =
                        Configuration.getRenamingOptions().getLocalVariableOptions();
                    Configuration.getRenamingOptions().getLocalVariableOptions();
                    localVariableOptions2.processRuleEntry(new RuleEntry(5, classRule3));
                    return;
                  } catch (ConfigurationException configurationException) {
                    return;
                  }
                }
              }
            } else {
              if ("property".equals(text5)) {
                this.processAttributes(attributes);
                return;
              }
              if ("trial".equals(text5)) {
                String text22 = this.createText(attributes, "class", true, null, true);
                String text23 = this.createText(attributes, "method", true, null, true);
                Configuration.getDebugInfoOptions().setText2(text22);
                Configuration.getDebugInfoOptions().setText(text23);
                ClassRule classRule4 =
                    this.createClassRule(
                        this.createText(attributes, "add2class", false, "private+ class *", true),
                        null,
                        null,
                        null,
                        "add2class");
                Configuration.getDebugInfoOptions()
                    .processRuleEntry(new RuleEntry(1, classRule4));
                MethodRule methodRule2 =
                    this.createMethodRule(
                        this.createText(
                            attributes, "add2method", false, "private+ <init>(**)", true),
                        null,
                        "add2method",
                        null);
                classRule4.processMethodRule(methodRule2);
                String text24 = this.createText(attributes, "passthis", false, "no", true);
                if (!"yes".equals(text24) && !"true".equals(text24)) {
                  Configuration.getDebugInfoOptions().setEnabled(false);
                  return;
                }
                Configuration.getDebugInfoOptions().setEnabled(true);
                return;
              }
              if ("watermark".equals(text5)) {
                String text28 = this.createText(attributes, "key", true, null, false);
                if ("".equals(text28)) {
                  this.processText2("key");
                }
                Configuration.getWatermarkOptions().setText2(text28);
                if (!Configuration.getWatermarkOptions().isEnabled()) {
                  String text29 = this.createText(attributes, "value", true, null, false);
                  if ("".equals(text29)) {
                    this.processText2("value");
                  }
                  Configuration.getWatermarkOptions().setText(text29);
                }
                return;
              }
              throw new ConfigurationSaxException(
                  this.xmlParsingContext,
                  new StringBuilder()
                      .insert(0, "Unknown tag '")
                      .append(text5)
                      .append("'. ")
                      .append(this.createText())
                      .toString());
            }
          }
        }
      }
    }
  }

  public void processText(String text, String text2) throws ConfigurationSaxException {
    if (text == null && text2 == null) {
      this.processText3("template", "access");
    }
  }

  public void processText(String text) throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Invalid '")
            .append(text)
            .append("' attribute. ")
            .append(this.createText())
            .toString());
  }

  public String createText(String text, String text2, String text3)
      throws ConfigurationSaxException {
    String text4 = text;
    if (text == null) {
      if (!TemplateUtils.matches(text2)) {
        this.processText("access");
      }
      text4 = "" + text2 + text3;
    }
    return text4;
  }

  public void processText(String text, String text2, String text3, String text4)
      throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' should have one of the following values: '")
            .append(text2)
            .append("', '")
            .append(text3)
            .append("' or '")
            .append(text4)
            .append("'. ")
            .append(this.createText())
            .toString());
  }

  public void processText2(String text) throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' cannot have zero length. ")
            .append(this.createText())
            .toString());
  }

  public void processText(
      String text, String text2, String text3, String text4, String text5, String text6)
      throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' should have one of the following values: '")
            .append(text2)
            .append("', '")
            .append(text3)
            .append("', '")
            .append(text4)
            .append("', '")
            .append(text5)
            .append("' or '")
            .append(text6)
            .append("'. ")
            .append(this.createText())
            .toString());
  }

  public String createText(String text) {
    if ("".equals(text) || !new File(text).isAbsolute()) {
      try {
        String text2;
        if ((text2 =
                new File(XmlParsingContext.getText(this.xmlParsingContext))
                    .getCanonicalFile()
                    .getParent())
            != null) {
          return new File(("" + text2 + File.separator + text)).getCanonicalPath();
        }
      } catch (Exception exception) {
        String text3;
        if ((text3 =
                new File(XmlParsingContext.getText(this.xmlParsingContext))
                    .getAbsoluteFile()
                    .getParent())
            != null) {
          return new File(("" + text3 + File.separator + text)).getAbsolutePath();
        }
      }
    }
    return text;
  }

  public void processAttributes(Attributes attributes) throws ConfigurationSaxException {
    String text = this.createText(attributes, "name", true, null, true);
    String text2 = this.createText(attributes, "value", true, null, true);
    String text3 = this.createText(attributes, "apply2class", false, "private+ class *", true);
    String text4 = this.createText(attributes, "apply2method", false, "private+ *(**)", true);
    if ("string-encryption".equals(text)) {
      Configuration.getObfuscationOptions();
      byte value = 1;
      ConfigurationHandler configurationHandler;
      if ("enable".equals(text2)) {
        Configuration.getObfuscationOptions();
        value = 1;
        configurationHandler = this;
      } else if ("disable".equals(text2)) {
        Configuration.getObfuscationOptions();
        value = 0;
        configurationHandler = this;
      } else if ("maximum".equals(text2)) {
        Configuration.getObfuscationOptions();
        value = 2;
        configurationHandler = this;
      } else if ("maximum-with-warnings".equals(text2)) {
        Configuration.getObfuscationOptions();
        value = 3;
        configurationHandler = this;
      } else {
        configurationHandler = this;
        this.processText("value", "enable", "disable", "maximum");
      }
      ClassRule classRule;
      (classRule = configurationHandler.createClassRule(text3, null, null, null, "apply2class"))
          .processMethodRule(this.createMethodRule(text4, null, "apply2method", null));
      Configuration.getObfuscationOptions().processRuleEntry4(new RuleEntry(value, classRule));
    } else if ("member-reorder".equals(text)) {
      Configuration.getObfuscationOptions();
      byte value2 = 1;
      ConfigurationHandler configurationHandler2;
      if ("enable".equals(text2)) {
        Configuration.getObfuscationOptions();
        value2 = 1;
        configurationHandler2 = this;
      } else if ("disable".equals(text2)) {
        Configuration.getObfuscationOptions();
        value2 = 0;
        configurationHandler2 = this;
      } else if ("random".equals(text2)) {
        Configuration.getObfuscationOptions();
        value2 = 1;
        configurationHandler2 = this;
      } else if ("alphabetical".equals(text2)) {
        Configuration.getObfuscationOptions();
        value2 = 100;
        configurationHandler2 = this;
      } else if ("reverse-alphabetical".equals(text2)) {
        Configuration.getObfuscationOptions();
        value2 = 101;
        configurationHandler2 = this;
      } else {
        configurationHandler2 = this;
        this.processText(
            "value", "enable", "disable", "random", "alphabetical", "reverse-alphabetical");
      }
      ClassRule classRule2 =
          configurationHandler2.createClassRule(text3, null, null, null, "apply2class");
      Configuration.getObfuscationOptions().processRuleEntry6(new RuleEntry(value2, classRule2));
    } else if ("control-flow-obfuscation".equals(text)) {
      byte value3 = 1;
      ConfigurationHandler configurationHandler3;
      if ("enable".equals(text2)) {
        value3 = 1;
        configurationHandler3 = this;
      } else if ("disable".equals(text2)) {
        value3 = 0;
        configurationHandler3 = this;
      } else {
        configurationHandler3 = this;
        this.processText("value", "enable", "disable");
      }
      ClassRule classRule3;
      (classRule3 = configurationHandler3.createClassRule(text3, null, null, null, "apply2class"))
          .processMethodRule(this.createMethodRule(text4, null, "apply2method", null));
      Configuration.getObfuscationOptions().processRuleEntry3(new RuleEntry(value3, classRule3));
    } else if ("local-variables-naming".equals(text)) {
      Configuration.getRenamingOptions().getLocalVariableOptions();
      byte value4 = 6;
      if (text2.contains(":")) {
        Configuration.getRenamingOptions()
            .getLocalVariableOptions()
            .setLocalVariablesNaming(text2.substring(text2.indexOf(58) + 1));
        text2 = text2.substring(0, text2.indexOf(58));
      }
      if ("single-name".equals(text2)) {
        Configuration.getRenamingOptions().getLocalVariableOptions();
        value4 = 1;
      } else if ("abc".equals(text2)) {
        Configuration.getRenamingOptions().getLocalVariableOptions();
        value4 = 2;
      } else if ("keep".equals(text2)) {
        Configuration.getRenamingOptions().getLocalVariableOptions();
        value4 = 3;
      } else if ("remove".equals(text2)) {
        Configuration.getRenamingOptions().getLocalVariableOptions();
        value4 = 4;
      } else if ("keep-parameters".equals(text2)) {
        Configuration.getRenamingOptions().getLocalVariableOptions();
        value4 = 5;
      } else if ("optimize".equals(text2)) {
        Configuration.getRenamingOptions().getLocalVariableOptions();
        value4 = 6;
      } else {
        this.processText(
            "value", "single-name", "abc", "keep", "remove", "keep-parameters", "optimize");
      }
      if ("private+ class *".equals(text3) && "private+ *(**)".equals(text4)) {
        Configuration.getRenamingOptions()
            .getLocalVariableOptions()
            .setLocalVariablesNamingMode(value4);
      }
      ClassRule classRule4;
      (classRule4 = this.createClassRule(text3, null, null, null, "apply2class"))
          .processMethodRule(this.createMethodRule(text4, null, "apply2method", null));
      Configuration.getRenamingOptions()
          .getLocalVariableOptions()
          .processRuleEntry(new RuleEntry(value4, classRule4));
    } else if ("packages-naming".equals(text)) {
      if ("abc".equals(text2)) {
        NameGenerationOptions nameGenerationOptions =
            Configuration.getRenamingOptions().getNameGenerationOptions();
        Configuration.getRenamingOptions().getNameGenerationOptions();
        nameGenerationOptions.setPackagesNamingMode(1);
      } else if ("ABC".equals(text2)) {
        NameGenerationOptions nameGenerationOptions2 =
            Configuration.getRenamingOptions().getNameGenerationOptions();
        Configuration.getRenamingOptions().getNameGenerationOptions();
        nameGenerationOptions2.setPackagesNamingMode(8);
      } else if ("123".equals(text2)) {
        NameGenerationOptions nameGenerationOptions3 =
            Configuration.getRenamingOptions().getNameGenerationOptions();
        Configuration.getRenamingOptions().getNameGenerationOptions();
        nameGenerationOptions3.setPackagesNamingMode(6);
      } else if ("keep".equals(text2)) {
        Configuration.getRenamingOptions().setEnabled(true);
      } else if (text2.startsWith("custom(")) {
        NameGenerationOptions nameGenerationOptions4 =
            Configuration.getRenamingOptions().getNameGenerationOptions();
        Configuration.getRenamingOptions().getNameGenerationOptions();
        nameGenerationOptions4.setPackagesNamingMode(7);
        Configuration.getRenamingOptions()
            .getNameGenerationOptions()
            .setTexts4(this.createTexts(text2));
      } else {
        this.processText("value", "abc", "ABC", "123", "keep", "custom(filename.txt)");
      }
    } else {
      if ("classes-naming".equals(text)) {
        int value5 = -1;
        if (text2.indexOf(40) > 0 && !text2.startsWith("custom(")) {
          String text5;
          label867:
          {
            try {
              value5 = Integer.valueOf(text2.substring(text2.indexOf(40) + 1, text2.indexOf(41)));
            } catch (Exception exception) {
              text5 = text2;
              break label867;
            }
            text5 = text2;
          }
          text2 = text5.substring(0, text2.indexOf(40));
        }
        if ("abc".equals(text2)) {
          NameGenerationOptions nameGenerationOptions5 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions5.setClassesNamingMode(1);
          return;
        }
        if ("ABC".equals(text2)) {
          NameGenerationOptions nameGenerationOptions6 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions6.setClassesNamingMode(8);
          return;
        }
        if ("compact".equals(text2)) {
          NameGenerationOptions nameGenerationOptions7 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions7.setClassesNamingMode(2);
          return;
        }
        if ("windows".equals(text2)) {
          NameGenerationOptions nameGenerationOptions8 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions8.setClassesNamingMode(4);
          return;
        }
        if ("iii".equals(text2)) {
          NameGenerationOptions nameGenerationOptions9 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions9.setClassesNamingMode(5);
          if (value5 > 0) {
            MixedConfusableNameGenerator.processCount(value5);
            return;
          }
        } else {
          if (!"lll".equals(text2)
              && !"ll1".equals(text2)
              && !"l1l".equals(text2)
              && !"l11".equals(text2)
              && !"1ll".equals(text2)
              && !"1l1".equals(text2)
              && !"11l".equals(text2)
              && !"111".equals(text2)) {
            if ("123".equals(text2)) {
              NameGenerationOptions nameGenerationOptions10 =
                  Configuration.getRenamingOptions().getNameGenerationOptions();
              Configuration.getRenamingOptions().getNameGenerationOptions();
              nameGenerationOptions10.setClassesNamingMode(6);
              return;
            }
            if (text2.startsWith("custom(")) {
              NameGenerationOptions nameGenerationOptions11 =
                  Configuration.getRenamingOptions().getNameGenerationOptions();
              Configuration.getRenamingOptions().getNameGenerationOptions();
              nameGenerationOptions11.setClassesNamingMode(7);
              Configuration.getRenamingOptions()
                  .getNameGenerationOptions()
                  .setTexts3(this.createTexts(text2));
              return;
            }
            if ("unique".equals(text2)) {
              Configuration.getRenamingOptions().getNameGenerationOptions().setEnabled2(true);
              return;
            }
            if ("keep-$-sign".equals(text2)) {
              Configuration.getRenamingOptions().setEnabled4(true);
              return;
            }
            this.processText(
                "value",
                "abc",
                "ABC",
                "compact",
                "windows",
                "iii",
                "lll",
                "123",
                "unique",
                "keep-$-sign",
                "custom(filename.txt)");
            return;
          }
          NameGenerationOptions nameGenerationOptions12 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions12.setClassesNamingMode(9);
          if (value5 > 0) {
            LowercaseConfusableNameGenerator.processCount(value5);
            return;
          }
        }
      } else if ("fields-naming".equals(text)) {
        int value6 = -1;
        if (text2.indexOf(40) > 0 && !text2.startsWith("custom(")) {
          String text6;
          label877:
          {
            try {
              value6 = Integer.valueOf(text2.substring(text2.indexOf(40) + 1, text2.indexOf(41)));
            } catch (Exception exception2) {
              text6 = text2;
              break label877;
            }
            text6 = text2;
          }
          text2 = text6.substring(0, text2.indexOf(40));
        }
        if ("abc".equals(text2)) {
          NameGenerationOptions nameGenerationOptions13 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions13.setFieldsNamingMode(1);
          return;
        }
        if ("ABC".equals(text2)) {
          NameGenerationOptions nameGenerationOptions14 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions14.setFieldsNamingMode(8);
          return;
        }
        if ("compact".equals(text2)) {
          NameGenerationOptions nameGenerationOptions15 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions15.setFieldsNamingMode(2);
          return;
        }
        if ("keywords".equals(text2)) {
          NameGenerationOptions nameGenerationOptions16 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions16.setFieldsNamingMode(3);
          return;
        }
        if ("iii".equals(text2)) {
          NameGenerationOptions nameGenerationOptions17 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions17.setFieldsNamingMode(5);
          if (value6 > 0) {
            MixedConfusableNameGenerator.processCount(value6);
            return;
          }
        } else {
          if (!"lll".equals(text2)
              && !"ll1".equals(text2)
              && !"l1l".equals(text2)
              && !"l11".equals(text2)
              && !"1ll".equals(text2)
              && !"1l1".equals(text2)
              && !"11l".equals(text2)
              && !"111".equals(text2)) {
            if ("123".equals(text2)) {
              NameGenerationOptions nameGenerationOptions18 =
                  Configuration.getRenamingOptions().getNameGenerationOptions();
              Configuration.getRenamingOptions().getNameGenerationOptions();
              nameGenerationOptions18.setFieldsNamingMode(6);
              return;
            }
            if ("real".equals(text2)) {
              Configuration.getRenamingOptions().getNameGenerationOptions().setEnabled(true);
              return;
            }
            if ("unique".equals(text2)) {
              Configuration.getRenamingOptions().setEnabled6(true);
              return;
            }
            if (text2.startsWith("custom(")) {
              NameGenerationOptions nameGenerationOptions19 =
                  Configuration.getRenamingOptions().getNameGenerationOptions();
              Configuration.getRenamingOptions().getNameGenerationOptions();
              nameGenerationOptions19.setFieldsNamingMode(7);
              Configuration.getRenamingOptions()
                  .getNameGenerationOptions()
                  .setTexts2(this.createTexts(text2));
              return;
            }
            this.processText(
                "value",
                "abc",
                "ABC",
                "compact",
                "keywords",
                "iii",
                "lll",
                "123",
                "real",
                "unique",
                "custom(filename.txt)");
            return;
          }
          NameGenerationOptions nameGenerationOptions20 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions20.setFieldsNamingMode(9);
          if (value6 > 0) {
            LowercaseConfusableNameGenerator.processCount(value6);
            return;
          }
        }
      } else if ("methods-naming".equals(text)) {
        int value7 = -1;
        if (text2.indexOf(40) > 0 && !text2.startsWith("custom(")) {
          String text7;
          label887:
          {
            try {
              value7 = Integer.valueOf(text2.substring(text2.indexOf(40) + 1, text2.indexOf(41)));
            } catch (Exception exception3) {
              text7 = text2;
              break label887;
            }
            text7 = text2;
          }
          text2 = text7.substring(0, text2.indexOf(40));
        }
        if ("abc".equals(text2)) {
          NameGenerationOptions nameGenerationOptions21 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions21.setMethodsNamingMode(1);
          return;
        }
        if ("ABC".equals(text2)) {
          NameGenerationOptions nameGenerationOptions22 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions22.setMethodsNamingMode(8);
          return;
        }
        if ("compact".equals(text2)) {
          NameGenerationOptions nameGenerationOptions23 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions23.setMethodsNamingMode(2);
          return;
        }
        if ("keywords".equals(text2)) {
          NameGenerationOptions nameGenerationOptions24 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions24.setMethodsNamingMode(3);
          return;
        }
        if ("iii".equals(text2)) {
          NameGenerationOptions nameGenerationOptions25 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions25.setMethodsNamingMode(5);
          if (value7 > 0) {
            MixedConfusableNameGenerator.processCount(value7);
            return;
          }
        } else {
          if (!"lll".equals(text2)
              && !"ll1".equals(text2)
              && !"l1l".equals(text2)
              && !"l11".equals(text2)
              && !"1ll".equals(text2)
              && !"1l1".equals(text2)
              && !"11l".equals(text2)
              && !"111".equals(text2)) {
            if ("123".equals(text2)) {
              NameGenerationOptions nameGenerationOptions26 =
                  Configuration.getRenamingOptions().getNameGenerationOptions();
              Configuration.getRenamingOptions().getNameGenerationOptions();
              nameGenerationOptions26.setMethodsNamingMode(6);
              return;
            }
            if ("real".equals(text2)) {
              Configuration.getRenamingOptions().getNameGenerationOptions().setEnabled3(true);
              return;
            }
            if ("unique".equals(text2)) {
              Configuration.getRenamingOptions().setEnabled8(true);
              return;
            }
            if (text2.startsWith("custom(")) {
              NameGenerationOptions nameGenerationOptions27 =
                  Configuration.getRenamingOptions().getNameGenerationOptions();
              Configuration.getRenamingOptions().getNameGenerationOptions();
              nameGenerationOptions27.setMethodsNamingMode(7);
              Configuration.getRenamingOptions()
                  .getNameGenerationOptions()
                  .setTexts(this.createTexts(text2));
              return;
            }
            this.processText(
                "value",
                "abc",
                "ABC",
                "compact",
                "keywords",
                "iii",
                "lll",
                "123",
                "real",
                "unique",
                "custom(filename.txt)");
            return;
          }
          NameGenerationOptions nameGenerationOptions28 =
              Configuration.getRenamingOptions().getNameGenerationOptions();
          Configuration.getRenamingOptions().getNameGenerationOptions();
          nameGenerationOptions28.setMethodsNamingMode(9);
          if (value7 > 0) {
            LowercaseConfusableNameGenerator.processCount(value7);
            return;
          }
        }
      } else {
        if ("default-package".equals(text)) {
          String text8;
          String text9 = text8 = this.createText(attributes, "value", true, null, false);
          while (text9.endsWith(".")) {
            text9 = text8 = text8.substring(0, text8.length() - 1);
          }
          Configuration.getRenamingOptions().setDefaultPackage(text8);
          return;
        }
        if ("force-default-package".equals(text)) {
          if ("enable".equals(text2)) {
            Configuration.getRenamingOptions().setEnabled7(true);
            return;
          }
          if ("disable".equals(text2)) {
            Configuration.getRenamingOptions().setEnabled7(false);
            return;
          }
          this.processText("value", "enable", "disable");
          return;
        }
        if ("line-numbers".equals(text)) {
          if ("keep".equals(text2)) {
            LineNumberOptions lineNumberOptions =
                Configuration.getRenamingOptions().getLineNumberOptions();
            Configuration.getRenamingOptions().getLineNumberOptions();
            lineNumberOptions.setLineNumbersMode(2);
            return;
          }
          if ("remove".equals(text2)) {
            LineNumberOptions lineNumberOptions2 =
                Configuration.getRenamingOptions().getLineNumberOptions();
            Configuration.getRenamingOptions().getLineNumberOptions();
            lineNumberOptions2.setLineNumbersMode(3);
            return;
          }
          if ("obfuscate".equals(text2)) {
            LineNumberOptions lineNumberOptions3 =
                Configuration.getRenamingOptions().getLineNumberOptions();
            Configuration.getRenamingOptions().getLineNumberOptions();
            lineNumberOptions3.setLineNumbersMode(1);
            return;
          }
          this.processText("value", "keep", "remove", "obfuscate");
          return;
        }
        if ("log-file".equals(text)) {
          if (text2.length() == 0) {
            this.processText("value");
          }
          if (!new File(text2).isAbsolute()) {
            try {
              String text10;
              if ((text10 =
                      new File(XmlParsingContext.getText(this.xmlParsingContext))
                          .getCanonicalFile()
                          .getParent())
                  != null) {
                text2 = new File(("" + text10 + File.separator + text2)).getCanonicalPath();
              }
            } catch (Exception exception4) {
              String text11;
              if ((text11 =
                      new File(XmlParsingContext.getText(this.xmlParsingContext))
                          .getAbsoluteFile()
                          .getParent())
                  != null) {
                text2 = new File(("" + text11 + File.separator + text2)).getAbsolutePath();
              }
            }
          }
          Configuration.getOutputOptions().setLogFile(text2);
          return;
        }
        if ("random-seed".equals(text)) {
          Configuration.getObfuscationOptions().setRandomSeed(text2);
          return;
        }
        if ("incremental-obfuscation".equals(text)) {
          if (text2.length() == 0) {
            this.processText("value");
          }
          if (!new File(text2).isAbsolute()) {
            try {
              String text12;
              if ((text12 =
                      new File(XmlParsingContext.getText(this.xmlParsingContext))
                          .getCanonicalFile()
                          .getParent())
                  != null) {
                text2 = new File(("" + text12 + File.separator + text2)).getCanonicalPath();
              }
            } catch (Exception exception5) {
              String text13;
              if ((text13 =
                      new File(XmlParsingContext.getText(this.xmlParsingContext))
                          .getAbsoluteFile()
                          .getParent())
                  != null) {
                text2 = new File(("" + text13 + File.separator + text2)).getAbsolutePath();
              }
            }
          }
          if (!new File(text2).exists()) {
            throw new ConfigurationSaxException(
                this.xmlParsingContext,
                new StringBuilder()
                    .insert(0, "Cannot find file '")
                    .append(text2)
                    .append("'. ")
                    .append(this.createText())
                    .toString());
          }
          Configuration.getRenamingOptions().setIncrementalObfuscation(text2);
          return;
        }
        if ("string-encryption-type".equals(text)) {
          Configuration.getObfuscationOptions();
          int value8 = 0;
          ConfigurationHandler configurationHandler4;
          if ("fast".equals(text2)) {
            Configuration.getObfuscationOptions();
            value8 = 0;
            configurationHandler4 = this;
          } else if ("strong".equals(text2)) {
            Configuration.getObfuscationOptions();
            value8 = 1;
            configurationHandler4 = this;
          } else {
            label840:
            if (text2.startsWith("custom(")) {
              Configuration.getObfuscationOptions();
              value8 = 1000 + this.locator.getLineNumber();
              try {
                String text14;
                String text15 = text14 = text2.substring(7, text2.indexOf(44)).trim();
                String text16 = text15.substring(0, text14.lastIndexOf(46));
                String text17 = text15.substring(text14.lastIndexOf(46) + 1);
                String text18;
                String text19 =
                    text18 = text2.substring(text2.indexOf(44) + 1, text2.length() - 1).trim();
                String text20 = text19.substring(0, text18.lastIndexOf(46));
                String text21 = text19.substring(text18.lastIndexOf(46) + 1);
                Configuration.getObfuscationOptions()
                    .getEntries()
                    .put(value8, new String[] {text16, text17, text20, text21});
              } catch (Exception exception6) {
                configurationHandler4 = this;
                this.processText(
                    "value",
                    "fast",
                    "strong",
                    "custom(ClassName.methodName, ClassName2.methodName2)");
                break label840;
              }
              configurationHandler4 = this;
            } else {
              configurationHandler4 = this;
              this.processText(
                  "value",
                  "fast",
                  "strong",
                  "custom(ClassName.methodName, ClassName2.methodName2)");
            }
          }
          ClassRule classRule5;
          (classRule5 =
                  configurationHandler4.createClassRule(text3, null, null, null, "apply2class"))
              .processMethodRule(this.createMethodRule(text4, null, "apply2method", null));
          Configuration.getObfuscationOptions()
              .processRuleEntry5(new RuleEntry(value8, classRule5));
          return;
        }
        if ("finalize".equals(text)) {
          if ("enable".equals(text2)) {
            Configuration.getObfuscationOptions().setEnabled8(true);
            return;
          }
          if ("disable".equals(text2)) {
            Configuration.getObfuscationOptions().setEnabled8(false);
            return;
          }
          this.processText("value", "enable", "disable");
          return;
        }
        if ("anti-jd".equals(text) || "extensive-flow-obfuscation".equals(text)) {
          Configuration.getExtensiveFlowOptions();
          byte value9 = 1;
          ConfigurationHandler configurationHandler5;
          if ("disable".equals(text2)) {
            Configuration.getExtensiveFlowOptions();
            value9 = 0;
            configurationHandler5 = this;
          } else if ("normal".equals(text2)) {
            Configuration.getExtensiveFlowOptions();
            value9 = 1;
            configurationHandler5 = this;
          } else if ("maximum".equals(text2)) {
            Configuration.getExtensiveFlowOptions();
            value9 = 2;
            configurationHandler5 = this;
          } else {
            configurationHandler5 = this;
            this.processText("value", "disable", "normal", "maximum");
          }
          ClassRule classRule6;
          (classRule6 =
                  configurationHandler5.createClassRule(text3, null, null, null, "apply2class"))
              .processMethodRule(this.createMethodRule(text4, null, "apply2method", null));
          Configuration.getExtensiveFlowOptions()
              .processRuleEntry(new RuleEntry(value9, classRule6));
          return;
        }
        if ("unique-renaming".equals(text)) {
          ConfigurationHandler configurationHandler6;
          if ("enable".equals(text2)) {
            Configuration.getRenamingOptions().setEnabled8(true);
            configurationHandler6 = this;
          } else if ("disable".equals(text2)) {
            Configuration.getRenamingOptions().setEnabled8(false);
            configurationHandler6 = this;
          } else {
            configurationHandler6 = this;
            this.processText("value", "enable", "disable");
          }
          ClassRule classRule7;
          (classRule7 =
                  configurationHandler6.createClassRule(text3, null, null, null, "apply2class"))
              .processMethodRule(this.createMethodRule(text4, null, "apply2method", null));
          RenamingOptions renamingOptions = Configuration.getRenamingOptions();
          RuleEntry ruleEntry;
          byte value10;
          ClassRule classRule8;
          if (Configuration.getRenamingOptions().isEnabled8()) {
            value10 = 1;
            classRule8 = classRule7;
          } else {
            value10 = 0;
            classRule8 = classRule7;
          }
          ruleEntry = new RuleEntry(value10, classRule8);
          renamingOptions.processRuleEntry(ruleEntry);
          return;
        }
        if ("randomize-names".equals(text)) {
          if ("enable".equals(text2)) {
            Configuration.getRenamingOptions().setEnabled3(true);
            return;
          }
          if ("disable".equals(text2)) {
            Configuration.getRenamingOptions().setEnabled3(false);
            return;
          }
          this.processText("value", "enable", "disable");
          return;
        }
        if ("string-encryption-seed".equals(text)) {
          if (text2.length() == 0) {
            this.processText2("value");
            return;
          }
          Configuration.getObfuscationOptions().setCharacter(text2.charAt(0));
          return;
        }
        if ("string-encryption-version".equals(text)) {
          ConfigurationHandler configurationHandler7;
          if ("v3".equals(text2)) {
            ObfuscationOptions obfuscationOptions = Configuration.getObfuscationOptions();
            Configuration.getObfuscationOptions();
            obfuscationOptions.setStringEncryptionVersion(0);
            configurationHandler7 = this;
          } else if ("v4".equals(text2) || "v4-per-jar".equals(text2)) {
            ObfuscationOptions obfuscationOptions2 = Configuration.getObfuscationOptions();
            Configuration.getObfuscationOptions();
            obfuscationOptions2.setStringEncryptionVersion(1);
            Configuration.getObfuscationOptions().setEnabled7(false);
            Configuration.getObfuscationOptions().setEnabled5(false);
            configurationHandler7 = this;
          } else if ("v4-global".equals(text2)) {
            ObfuscationOptions obfuscationOptions3 = Configuration.getObfuscationOptions();
            Configuration.getObfuscationOptions();
            obfuscationOptions3.setStringEncryptionVersion(1);
            Configuration.getObfuscationOptions().setEnabled7(true);
            configurationHandler7 = this;
          } else if ("v4-per-package".equals(text2)) {
            ObfuscationOptions obfuscationOptions4 = Configuration.getObfuscationOptions();
            Configuration.getObfuscationOptions();
            obfuscationOptions4.setStringEncryptionVersion(1);
            Configuration.getObfuscationOptions().setEnabled5(true);
            configurationHandler7 = this;
          } else {
            configurationHandler7 = this;
            this.processText("value", "v3", "v4");
          }
          String text22 = configurationHandler7.createText(attributes, "add", false, "", true);
          String text23 = this.createText(attributes, "noadd", false, "", true);
          Configuration.getObfuscationOptions().setDecryptorIncludePattern(text22);
          Configuration.getObfuscationOptions().setDecryptorExcludePattern(text23);
          return;
        }
        if ("version-marker".equals(text)) {
          if (text2.length() == 0) {
            this.processText2("value");
            return;
          }
          Configuration.getRenamingOptions().getNameGenerationOptions().processText(text2);
          return;
        }
        if ("fields-naming-prefix".equals(text)) {
          Configuration.getRenamingOptions()
              .getNameGenerationOptions()
              .setFieldsNamingPrefix(text2);
          return;
        }
        if ("methods-naming-prefix".equals(text)) {
          Configuration.getRenamingOptions()
              .getNameGenerationOptions()
              .setMethodsNamingPrefix(text2);
          return;
        }
        if ("classes-naming-prefix".equals(text)) {
          Configuration.getRenamingOptions()
              .getNameGenerationOptions()
              .setClassesNamingPrefix(text2);
          return;
        }
        if ("string-encryption-ignored-strings".equals(text)) {
          if (text2.length() == 0) {
            this.processText("value");
          }
          if (!new File(text2).isAbsolute()) {
            try {
              String text24;
              if ((text24 =
                      new File(XmlParsingContext.getText(this.xmlParsingContext))
                          .getCanonicalFile()
                          .getParent())
                  != null) {
                text2 = new File(("" + text24 + File.separator + text2)).getCanonicalPath();
              }
            } catch (Exception exception7) {
              String text25;
              if ((text25 =
                      new File(XmlParsingContext.getText(this.xmlParsingContext))
                          .getAbsoluteFile()
                          .getParent())
                  != null) {
                text2 = new File(("" + text25 + File.separator + text2)).getAbsolutePath();
              }
            }
          }
          if (!new File(text2).exists()) {
            throw new ConfigurationSaxException(
                this.xmlParsingContext,
                new StringBuilder()
                    .insert(0, "Cannot find file '")
                    .append(text2)
                    .append("'. ")
                    .append(this.createText())
                    .toString());
          }
          Configuration.getObfuscationOptions().setStringEncryptionIgnoredStrings(text2);
          return;
        }
        if ("output-jar-comment".equals(text)) {
          if (!"disable".equals(text2) && !"false".equals(text2)) {
            if (!"enable".equals(text2) && !"true".equals(text2)) {
              Configuration.getObfuscationOptions().setOutputJarComment(text2);
              return;
            }
            Configuration.getObfuscationOptions().setEnabled2(true);
            return;
          }
          Configuration.getObfuscationOptions().setEnabled2(false);
          return;
        }
        if ("skip-renaming".equals(text)) {
          if ("enable".equals(text2)) {
            Configuration.getRenamingOptions().setEnabled5(true);
            return;
          }
          if ("disable".equals(text2)) {
            Configuration.getRenamingOptions().setEnabled5(false);
            return;
          }
          this.processText("value", "enable", "disable");
          return;
        }
        if (!"android-detection".equals(text)) {
          if ("generics".equals(text)) {
            byte value11 = 0;
            ConfigurationHandler configurationHandler8;
            if ("remove".equals(text2)) {
              value11 = 1;
              configurationHandler8 = this;
            } else if ("keep".equals(text2)) {
              value11 = 0;
              configurationHandler8 = this;
            } else {
              configurationHandler8 = this;
              this.processText("value", "remove", "keep");
            }
            ClassRule classRule9 =
                configurationHandler8.createClassRule(text3, null, null, null, "apply2class");
            Configuration.getObfuscationOptions()
                .processRuleEntry(new RuleEntry(value11, classRule9));
            return;
          }
          if ("inner-classes".equals(text)) {
            byte value12 = 0;
            ConfigurationHandler configurationHandler9;
            if ("remove".equals(text2)) {
              value12 = 1;
              configurationHandler9 = this;
            } else if ("keep".equals(text2)) {
              value12 = 0;
              configurationHandler9 = this;
            } else {
              configurationHandler9 = this;
              this.processText("value", "remove", "keep");
            }
            ClassRule classRule10 =
                configurationHandler9.createClassRule(text3, null, null, null, "apply2class");
            Configuration.getObfuscationOptions()
                .processRuleEntry7(new RuleEntry(value12, classRule10));
            return;
          }
          if ("throws-clause".equals(text)) {
            byte value13 = 0;
            ConfigurationHandler configurationHandler10;
            if ("remove".equals(text2)) {
              value13 = 1;
              configurationHandler10 = this;
            } else if ("keep".equals(text2)) {
              value13 = 0;
              configurationHandler10 = this;
            } else {
              configurationHandler10 = this;
              this.processText("value", "remove", "keep");
            }
            ClassRule classRule11;
            (classRule11 =
                    configurationHandler10.createClassRule(text3, null, null, null, "apply2class"))
                .processMethodRule(this.createMethodRule(text4, null, "apply2method", null));
            Configuration.getObfuscationOptions()
                .processRuleEntry2(new RuleEntry(value13, classRule11));
            return;
          }
          if ("synthetize-fields".equals(text)) {
            byte value14 = 1;
            if ("disable".equals(text2) || "none".equals(text2)) {
              value14 = 0;
              text2 = "private+";
            } else if ("enable".equals(text2) || "all".equals(text2)) {
              text2 = "private+";
            }
            if ("private".equals(text2)
                || "package".equals(text2)
                || "protected".equals(text2)
                || "public".equals(text2)) {
              text2 = "" + text2 + " *";
            }
            ClassRule classRule12 = this.createClassRule(text3, null, null, null, "apply2class");
            try {
              classRule12.processFieldRule(new FieldRule(text2, false, false));
            } catch (ConfigurationException configurationException) {
              throw new ConfigurationSaxException(
                  this.xmlParsingContext,
                  new StringBuilder()
                      .insert(0, "Invalid 'synthetize-fields' property. ")
                      .append(this.createText())
                      .toString());
            }
            Configuration.getSyntheticOptions()
                .processRuleEntry(new RuleEntry(value14, classRule12));
            return;
          }
          if ("synthetize-methods".equals(text)) {
            byte value15 = 1;
            String text26;
            if (!"disable".equals(text2) && !"none".equals(text2)) {
              if ("enable".equals(text2) || "all".equals(text2)) {
                text2 = "private+";
              }
              text26 = text2;
            } else {
              value15 = 0;
              text26 = text2 = "private+";
            }
            if (text26.indexOf(40) == -1) {
              text2 = "" + text2 + " *(**)";
            }
            ClassRule classRule13 = this.createClassRule(text3, null, null, null, "apply2class");
            try {
              classRule13.processMethodRule(new MethodRule(text2, false, false));
            } catch (ConfigurationException configurationException2) {
              throw new ConfigurationSaxException(
                  this.xmlParsingContext,
                  new StringBuilder()
                      .insert(0, "Invalid 'synthetize-methods' property. ")
                      .append(this.createText())
                      .toString());
            }
            Configuration.getSyntheticOptions()
                .processRuleEntry2(new RuleEntry(value15, classRule13));
            return;
          }
          if ("set-fields-to-public".equals(text)) {
            byte value16 = 1;
            String text27;
            if (!"disable".equals(text2) && !"none".equals(text2)) {
              if ("enable".equals(text2) || "all".equals(text2)) {
                text2 = "private+";
              }
              text27 = text2;
            } else {
              value16 = 0;
              text27 = text2 = "private+";
            }
            if (text27.indexOf(32) == -1) {
              text2 = "" + text2 + " *";
            }
            ClassRule classRule14 = this.createClassRule(text3, null, null, null, "apply2class");
            try {
              classRule14.processFieldRule(new FieldRule(text2, false, false));
            } catch (ConfigurationException configurationException3) {
              throw new ConfigurationSaxException(
                  this.xmlParsingContext,
                  new StringBuilder()
                      .insert(0, "Invalid 'set-fields-to-public' property. ")
                      .append(this.createText())
                      .toString());
            }
            Configuration.getMemberOrderOptions()
                .processRuleEntry(new RuleEntry(value16, classRule14));
            return;
          }
          if ("set-methods-to-public".equals(text)) {
            byte value17 = 1;
            String text28;
            if (!"disable".equals(text2) && !"none".equals(text2)) {
              if ("enable".equals(text2) || "all".equals(text2)) {
                text2 = "private+";
              }
              text28 = text2;
            } else {
              value17 = 0;
              text28 = text2 = "private+";
            }
            if (text28.indexOf(40) == -1) {
              text2 = "" + text2 + " *(**)";
            }
            ClassRule classRule15 = this.createClassRule(text3, null, null, null, "apply2class");
            try {
              classRule15.processMethodRule(new MethodRule(text2, false, false));
            } catch (ConfigurationException configurationException4) {
              throw new ConfigurationSaxException(
                  this.xmlParsingContext,
                  new StringBuilder()
                      .insert(0, "Invalid 'set-methods-to-public' property. ")
                      .append(this.createText())
                      .toString());
            }
            Configuration.getMemberOrderOptions()
                .processRuleEntry2(new RuleEntry(value17, classRule15));
            return;
          }
          if ("remove-calls".equals(text)) {
            if (text2.length() < 3
                || !text2.contains(".")
                || text2.startsWith(".")
                || text2.endsWith(".")) {
              this.processText("value");
            }
            String text29 = text2.substring(0, text2.lastIndexOf(46));
            String text30 = text2.substring(text2.lastIndexOf(46) + 1);
            ClassRule classRule16;
            (classRule16 = this.createClassRule(text3, null, null, null, "apply2class"))
                .processMethodRule(this.createMethodRule(text4, null, "apply2method", null));
            Configuration.getMethodCallRemovalOptions()
                .processText(new RuleEntry(555, classRule16), text29, text30);
            return;
          }
          if ("remove-methods".equals(text)) {
            if (text2.length() < 3
                || !text2.contains(".")
                || text2.startsWith(".")
                || text2.endsWith(".")) {
              this.processText("value");
            }
            int value18 = text2.lastIndexOf(40);
            String text31 = "(**)";
            if (value18 > 0) {
              text31 = text2.substring(value18);
              text2 = text2.substring(0, value18);
            }
            if (!text2.startsWith("class ")) {
              text2 = "class " + text2;
            }
            String text32 = text2.substring(0, text2.lastIndexOf(46));
            String text33 = text2.substring(text2.lastIndexOf(46) + 1);
            ClassRule classRule17;
            (classRule17 = this.createClassRule(text32, null, null, null, "value"))
                .processMethodRule(this.createMethodRule(text33 + text31, null, "value", null));
            Configuration.getOptimizationOptions().processRuleEntry2(new RuleEntry(1, classRule17));
            return;
          }
          if ("remove-fields".equals(text)) {
            if (text2.length() < 3
                || !text2.contains(".")
                || text2.startsWith(".")
                || text2.endsWith(".")) {
              this.processText("value");
            }
            if (!text2.startsWith("class ")) {
              text2 = "class " + text2;
            }
            String text34 = text2.substring(0, text2.lastIndexOf(46));
            String text35 = text2.substring(text2.lastIndexOf(46) + 1);
            ClassRule classRule18;
            (classRule18 = this.createClassRule(text34, null, null, null, "value"))
                .processFieldRule(this.createFieldRule(text35, null, null));
            Configuration.getFieldRemovalOptions().processRuleEntry(new RuleEntry(1, classRule18));
            return;
          }
          if (!"remove-annotations".equals(text)) {
            if ("remove-toString".equals(text)) {
              byte value19 = 0;
              ConfigurationHandler configurationHandler11;
              if ("enable".equals(text2)) {
                value19 = 1;
                configurationHandler11 = this;
              } else if ("disable".equals(text2)) {
                value19 = 0;
                configurationHandler11 = this;
              } else {
                configurationHandler11 = this;
                this.processText("value", "enable", "disable");
              }
              ClassRule classRule19 =
                  configurationHandler11.createClassRule(text3, null, null, null, "apply2class");
              Configuration.getOptimizationOptions()
                  .processRuleEntry(new RuleEntry(value19, classRule19));
              return;
            }
            if ("keep-package-names".equals(text)) {
              if ("enable".equals(text2)) {
                Configuration.getRenamingOptions().setEnabled(true);
                return;
              }
              if ("disable".equals(text2)) {
                Configuration.getRenamingOptions().setEnabled(false);
                return;
              }
              this.processText("value", "enable", "disable");
              return;
            }
            if ("compatible-renaming".equals(text)) {
              if ("enable".equals(text2)) {
                Configuration.getRenamingOptions().setEnabled2(true);
                return;
              }
              if ("disable".equals(text2)) {
                Configuration.getRenamingOptions().setEnabled2(false);
                return;
              }
              this.processText("value", "enable", "disable");
              return;
            }
            if ("java6".equals(text)) {
              if ("enable".equals(text2)) {
                Configuration.getObfuscationOptions().setEnabled4(true);
                return;
              }
              if ("disable".equals(text2)) {
                Configuration.getObfuscationOptions().setEnabled4(false);
                return;
              }
              this.processText("value", "enable", "disable");
              return;
            }
            if ("update-resource-names".equals(text)) {
              if ("enable".equals(text2)) {
                Configuration.getRenamingOptions().getResourceRenamingOptions().setEnabled(true);
                return;
              }
              if ("disable".equals(text2)) {
                Configuration.getRenamingOptions().getResourceRenamingOptions().setEnabled(false);
                return;
              }
              this.processText("value", "enable", "disable");
              return;
            }
            if ("update-resource-contents".equals(text)) {
              String text36 = this.createText(attributes, "apply2file", false, "*", true);
              if ("enable".equals(text2)) {
                Configuration.getRenamingOptions()
                    .getResourceRenamingOptions()
                    .processText(text36, true, "UTF-8");
                return;
              }
              if ("disable".equals(text2)) {
                Configuration.getRenamingOptions()
                    .getResourceRenamingOptions()
                    .processText(text36, false, "UTF-8");
                return;
              }
              if (text2.startsWith("enable:")) {
                String text37 = text2.substring("enable:".length());
                try {
                  Charset.forName(text37);
                } catch (Exception exception8) {
                  throw new ConfigurationSaxException(
                      this.xmlParsingContext,
                      new StringBuilder()
                          .insert(0, "Invalid encoding '")
                          .append(text37)
                          .append("'. ")
                          .append(this.createText())
                          .toString());
                }
                Configuration.getRenamingOptions()
                    .getResourceRenamingOptions()
                    .processText(text36, true, text37);
                return;
              }
              this.processText("value", "enable", "enable:ENCODING", "disable");
              return;
            }
            if ("com-license4j-support".equals(text)) {
              if (text2 != null && text2.length() > 0) {
                Configuration.getLogOptions().setComLicense4jSupport(text2);
                return;
              }
              this.processText("value");
              return;
            }
            if ("output-jar-compression-level".equals(text)) {
              try {
                int value20;
                if ((value20 = Integer.valueOf(text2)) >= -1 && value20 <= 9) {
                  Configuration.getOutputOptions().setCompressionLevel(value20);
                  return;
                }
                this.processText("value");
                return;
              } catch (Exception exception9) {
                this.processText("value");
                return;
              }
            }
            if ("output-jar-duplicate-name-entries".equals(text)) {
              if ("keep".equals(text2)) {
                Configuration.getOutputOptions().setEnabled(true);
                return;
              }
              if ("remove".equals(text2)) {
                Configuration.getOutputOptions().setEnabled(false);
                return;
              }
              this.processText("value", "remove", "keep");
              return;
            }
            if ("test".equals(text)) {
              byte value21 = 0;
              ConfigurationHandler configurationHandler12;
              if ("enable".equals(text2)) {
                value21 = 1;
                configurationHandler12 = this;
              } else if ("disable".equals(text2)) {
                value21 = 0;
                configurationHandler12 = this;
              } else {
                configurationHandler12 = this;
                this.processText("value", "enable", "disable");
              }
              ClassRule classRule20;
              (classRule20 =
                      configurationHandler12.createClassRule(
                          text3, null, null, null, "apply2class"))
                  .processMethodRule(this.createMethodRule(text4, null, "apply2method", null));
              Configuration.getMethodRemovalOptions()
                  .processRuleEntry(new RuleEntry(value21, classRule20));
              return;
            }
            if ("test-string".equals(text)) {
              if (text2 != null && text2.length() > 0) {
                Configuration.getMethodRemovalOptions().setTestString(text2);
                return;
              }
              this.processText("value");
              return;
            }
            throw new ConfigurationSaxException(
                this.xmlParsingContext,
                new StringBuilder()
                    .insert(0, "Unknown property '")
                    .append(text)
                    .append("'. ")
                    .append(this.createText())
                    .toString());
          }
          if (text2.length() < 1 || text2.startsWith(".") || text2.endsWith(".")) {
            this.processText("value");
          }
          ClassRule classRule21 = this.createClassRule(text3, null, null, null, "apply2class");
          Configuration.getPackageRenamingOptions()
              .processText(new RuleEntry(555, classRule21), text2);
          return;
        }
        if ("disable".equals(text2) || "false".equals(text2)) {
          Configuration.getObfuscationOptions().setEnabled6(false);
          return;
        }
      }
    }
  }

  public void processText2(String text, String text3) throws ConfigurationSaxException {
    String text4 = text;
    if (this.text2.length() > 0) {
      try {
        if (text4 != null && !new File(text4).isAbsolute()) {
          text4 = new File(("" + this.text2 + File.separator + text4)).getCanonicalPath();
        }
        if (text3 != null && !new File(text3).isAbsolute()) {
          text3 = new File(("" + this.text2 + File.separator + text3)).getCanonicalPath();
        }
      } catch (Exception exception) {
        if (text4 != null && !new File(text4).isAbsolute()) {
          text4 = new File(("" + this.text2 + File.separator + text4)).getAbsolutePath();
        }
        if (text3 != null && !new File(text3).isAbsolute()) {
          text3 = new File(("" + this.text2 + File.separator + text3)).getAbsolutePath();
        }
      }
    }
    if (text4 != null && text4.indexOf("*") != -1) {
      String text5 = ".";
      String text6 = text4;
      if (text4.indexOf(File.separator) >= 0 || text6.indexOf(47) >= 0) {
        int value = text6.lastIndexOf(File.separator);
        if (text6.lastIndexOf(47) > value) {
          value = text6.lastIndexOf(47);
        }
        text5 = text6.substring(0, value);
        text6 = text6.substring(value + 1);
      }
      File file;
      if (!(file = new File(text5)).isDirectory()) {
        throw new ConfigurationSaxException(
            this.xmlParsingContext,
            new StringBuilder()
                .insert(0, "Cannot find directory '")
                .append(text5)
                .append("'. ")
                .append(this.createText())
                .toString());
      }
      Pattern pattern = Pattern.compile(text6.replaceAll("\\.", "\\\\.").replaceAll("\\*", "(.*)"));
      File[] files;
      int value2;
      for (int value3 = value2 = (files = file.listFiles()).length - 1;
          value3 >= 0;
          value3 = --value2) {
        File file2;
        Matcher matcher2;
        if (!(file2 = files[value2]).isDirectory()
            && (matcher2 = pattern.matcher(file2.getName())).matches()) {
          String text7 = matcher2.group(1);
          String absolutePath = file2.getAbsolutePath();
          String text8 = null;
          if (text3 != null) {
            text8 = text3.replaceFirst("\\*", text7);
          }
          Configuration.getInputOptions().processInputEntry(new InputEntry(absolutePath, text8));
        }
      }
    } else {
      if (!new File(text4).exists()) {
        throw new ConfigurationSaxException(
            this.xmlParsingContext,
            new StringBuilder()
                .insert(0, "Cannot find file '")
                .append(text4)
                .append("'. ")
                .append(this.createText())
                .toString());
      }
      Configuration.getInputOptions().processInputEntry(new InputEntry(text4, text3));
    }
  }

  public void processEnabled(File file, String text, boolean matches2) {
    int value;
    File[] files;
    for (int value2 = value = (files = file.listFiles()).length - 1;
        value2 >= 0;
        value2 = --value) {
      File file2;
      if (!(file2 = files[value]).isDirectory() && file2.getName().matches(text)) {
        Configuration.getClassPathOptions().processText(file2.getAbsolutePath());
      }
      if (matches2 && file2.isDirectory()) {
        this.processEnabled(file2, text, matches2);
      }
    }
  }

  public ConfigurationHandler(
      XmlParsingContext xmlParsingContext,
      ConfigurationConstructorToken configurationConstructorToken) {
    this(xmlParsingContext);
  }

  public String createText(
      Attributes attributes, String text, boolean matches, String text2, boolean matches2)
      throws ConfigurationSaxException {
    String text3;
    if ((text3 = attributes.getValue(text)) == null && matches) {
      throw new ConfigurationSaxException(
          this.xmlParsingContext,
          new StringBuilder()
              .insert(0, "Missing '")
              .append(text)
              .append("' attribute. ")
              .append(this.createText())
              .toString());
    }
    if (text3 == null) {
      return text2;
    }
    while (text3.contains("${System.getProperty(")) {
      int value;
      if ((value = text3.indexOf("${System.getProperty(")) >= 0) {
        int value2;
        if ((value2 = text3.indexOf(")}", value)) < value) {
          throw new ConfigurationSaxException(
              this.xmlParsingContext,
              new StringBuilder()
                  .insert(0, "Invalid '")
                  .append(text)
                  .append("' attribute. ")
                  .append(this.createText())
                  .toString());
        }
        String text4;
        String text5;
        if ((text5 = System.getProperty(text4 = text3.substring(value + 21, value2))) == null) {
          throw new ConfigurationSaxException(
              this.xmlParsingContext,
              new StringBuilder()
                  .insert(0, "Missing system property '")
                  .append(text4)
                  .append("'. ")
                  .append(this.createText())
                  .toString());
        }
        text3 = "" + text3.substring(0, value) + text5 + text3.substring(value2 + 2);
      }
    }
    while (text3.contains("${System.getenv(")) {
      int value3;
      if ((value3 = text3.indexOf("${System.getenv(")) >= 0) {
        int value4;
        if ((value4 = text3.indexOf(")}", value3)) < value3) {
          throw new ConfigurationSaxException(
              this.xmlParsingContext,
              new StringBuilder()
                  .insert(0, "Invalid '")
                  .append(text)
                  .append("' attribute. ")
                  .append(this.createText())
                  .toString());
        }
        String text6;
        String text7;
        if ((text7 = System.getenv(text6 = text3.substring(value3 + 16, value4))) == null) {
          throw new ConfigurationSaxException(
              this.xmlParsingContext,
              new StringBuilder()
                  .insert(0, "Missing environment variable '")
                  .append(text6)
                  .append("'. ")
                  .append(this.createText())
                  .toString());
        }
        text3 = "" + text3.substring(0, value3) + text7 + text3.substring(value4 + 2);
      }
    }
    return matches2 ? text3.trim() : text3;
  }

  @Override
  public void endElement(String text, String text2, String text3) {
    if (!"jars".equals(text3)
        && !"input".equals(text3)
        && !"classpath".equals(text3)
        && !"class".equals(text3)) {
      if ("ignore-classes".equals(text3)) {
        this.enabled2 = false;
      }
    } else {
      this.count = -1;
    }
  }

  public void processText(String text, String text2, String text3, String text4, String text5)
      throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' should have one of the following values: '")
            .append(text2)
            .append("', '")
            .append(text3)
            .append("', '")
            .append(text4)
            .append("' or '")
            .append(text5)
            .append("'. ")
            .append(this.createText())
            .toString());
  }

  public MethodRule createMethodRule(String text, String text2, String text3, String text4)
      throws ConfigurationSaxException {
    String text5 = text;
    this.processText(text5, text2);
    byte value = 0;
    if ("yes".equals(text4) || "true".equals(text4)) {
      value = 1;
    }
    byte value2 = 0;
    if (text5 != null) {
      if (text5.startsWith("!")) {
        value2 = 1;
        text5 = text5.substring(1).trim();
      } else if (text5.startsWith("not")) {
        value2 = 1;
        text5 = text5.substring(3).trim();
      }
    }
    try {
      return new MethodRule(this.createText(text5, text2, " *(**)"), (value != 0), (value2 != 0));
    } catch (ConfigurationException configurationException) {
      if (text5 != null) {
        this.processText(text3);
      } else {
        this.processText("access");
      }
      return null;
    }
  }

  public void processText(
      String text,
      String text2,
      String text3,
      String text4,
      String text5,
      String text6,
      String text7,
      String text8,
      String text9,
      String text10)
      throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' should have one of the following values: '")
            .append(text2)
            .append("', '")
            .append(text3)
            .append("', '")
            .append(text4)
            .append("', '")
            .append(text5)
            .append("', '")
            .append(text6)
            .append("', '")
            .append(text7)
            .append("', '")
            .append(text8)
            .append("', '")
            .append(text9)
            .append("' or '")
            .append(text10)
            .append("'. ")
            .append(this.createText())
            .toString());
  }

  public FieldRule createFieldRule(String text, String text2, String text3)
      throws ConfigurationSaxException {
    String text4 = text;
    this.processText(text4, text2);
    byte value = 0;
    if ("yes".equals(text3) || "true".equals(text3)) {
      value = 1;
    }
    byte value2 = 0;
    if (text4 != null) {
      if (text4.startsWith("!")) {
        value2 = 1;
        text4 = text4.substring(1).trim();
      } else if (text4.startsWith("not")) {
        value2 = 1;
        text4 = text4.substring(3).trim();
      }
    }
    try {
      return new FieldRule(this.createText(text4, text2, " *"), (value != 0), (value2 != 0));
    } catch (ConfigurationException configurationException) {
      if (text4 != null) {
        this.processText("template");
      } else {
        this.processText("access");
      }
      return null;
    }
  }

  public void processText(
      String text,
      String text2,
      String text3,
      String text4,
      String text5,
      String text6,
      String text7,
      String text8,
      String text9,
      String text10,
      String text11)
      throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' should have one of the following values: '")
            .append(text2)
            .append("', '")
            .append(text3)
            .append("', '")
            .append(text4)
            .append("', '")
            .append(text5)
            .append("', '")
            .append(text6)
            .append("', '")
            .append(text7)
            .append("', '")
            .append(text8)
            .append("', '")
            .append(text9)
            .append("', '")
            .append(text10)
            .append("' or '")
            .append(text11)
            .append("'. ")
            .append(this.createText())
            .toString());
  }

  public void processText3(String text2) throws ConfigurationSaxException {
    String text3;
    String text4;
    label50:
    {
      text3 = text2;
      label49:
      if (this.text.length() > 0 && !new File(text3).isAbsolute()) {
        try {
          text3 = new File(("" + this.text + File.separator + text3)).getCanonicalPath();
        } catch (Exception exception) {
          text3 = new File(("" + this.text + File.separator + text3)).getAbsolutePath();
          break label49;
        }
        text4 = text3;
        break label50;
      }
      text4 = text3;
    }
    while (text4.endsWith(File.separator)) {
      text4 = text3 = text3.substring(0, text3.length() - File.separator.length());
    }
    if (text3.indexOf(42) == -1) {
      Configuration.getClassPathOptions().processText(text3);
    } else {
      String text5 = ".";
      String text6 = text3;
      if (text3.indexOf(File.separator) >= 0 || text3.indexOf(47) >= 0) {
        int value = text3.lastIndexOf(File.separator);
        if (text3.lastIndexOf(47) > value) {
          value = text3.lastIndexOf(47);
        }
        text5 = text3.substring(0, value);
        text6 = text3.substring(value + 1);
      }
      byte value2 = 0;
      if (text5.endsWith("**")) {
        text5 = text5.substring(0, text5.length() - 2);
        value2 = 1;
      }
      File file;
      if (!(file = new File(text5)).isDirectory()) {
        throw new ConfigurationSaxException(
            this.xmlParsingContext,
            new StringBuilder()
                .insert(0, "Cannot find directory '")
                .append(text5)
                .append("'. ")
                .append(this.createText())
                .toString());
      }
      String text7 = text6.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
      this.processEnabled(file, text7, value2 != 0);
    }
  }

  public void processText(
      String text,
      String text2,
      String text3,
      String text4,
      String text5,
      String text6,
      String text7,
      String text8)
      throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' should have one of the following values: '")
            .append(text2)
            .append("', '")
            .append(text3)
            .append("', '")
            .append(text4)
            .append("', '")
            .append(text5)
            .append("', '")
            .append(text6)
            .append("', '")
            .append(text7)
            .append("' or '")
            .append(text8)
            .append("'. ")
            .append(this.createText())
            .toString());
  }

  public void processText3(String text, String text2) throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Either '")
            .append(text)
            .append("' or '")
            .append(text2)
            .append("' attribute should be defined. ")
            .append(this.createText())
            .toString());
  }

  public void processText(
      String text,
      String text2,
      String text3,
      String text4,
      String text5,
      String text6,
      String text7)
      throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' should have one of the following values: '")
            .append(text2)
            .append("', '")
            .append(text3)
            .append("', '")
            .append(text4)
            .append("', '")
            .append(text5)
            .append("', '")
            .append(text6)
            .append("' or '")
            .append(text7)
            .append("'. ")
            .append(this.createText())
            .toString());
  }

  public void processText4(String text, String text3) throws ConfigurationSaxException {
    String text4 = text;
    if (this.text2.length() > 0) {
      try {
        if (text4 != null && !new File(text4).isAbsolute()) {
          text4 = new File(("" + this.text2 + File.separator + text4)).getCanonicalPath();
        }
        if (text3 != null && !new File(text3).isAbsolute()) {
          text3 = new File(("" + this.text2 + File.separator + text3)).getCanonicalPath();
        }
      } catch (Exception exception) {
        if (text4 != null && !new File(text4).isAbsolute()) {
          text4 = new File(("" + this.text2 + File.separator + text4)).getAbsolutePath();
        }
        if (text3 != null && !new File(text3).isAbsolute()) {
          text3 = new File(("" + this.text2 + File.separator + text3)).getAbsolutePath();
        }
      }
    }
    if (!new File(text4).exists()) {
      throw new ConfigurationSaxException(
          this.xmlParsingContext,
          new StringBuilder()
              .insert(0, "Cannot find directory '")
              .append(text4)
              .append("'. ")
              .append(this.createText())
              .toString());
    }
    if (!new File(text4).isDirectory()) {
      throw new ConfigurationSaxException(
          this.xmlParsingContext,
          new StringBuilder()
              .insert(0, "Cannot find directory '")
              .append(text4)
              .append("'. ")
              .append(this.createText())
              .toString());
    }
    Configuration.getInputOptions().processClassPathEntry(new ClassPathEntry(text4, text3));
  }

  public void processText5(String text, String text2) throws IOException, FileNotFoundException {
    Pattern pattern = Pattern.compile(text2.replaceAll("\\.", "\\\\.").replaceAll("\\*", "(.*)"));
    String text3 =
        new StringBuilder()
            .insert(0, this.createText("allatori-tmp-nested"))
            .append(File.separator)
            .toString();
    File file = new File(text3);
    if (this.enabled) {
      this.enabled = false;
      if (file.exists()) {
        File[] files;
        int value = (files = file.listFiles()).length;
        int value2;
        for (int value3 = value2 = 0; value3 < value; value3 = value2) {
          File file2 = files[value2];
          value2++;
          file2.delete();
        }
      } else {
        new File(text3).mkdirs();
      }
    }
    JarFile jarFile;
    Enumeration enumeration = (jarFile = new JarFile(text)).entries();
    while (enumeration.hasMoreElements()) {
      JarEntry jarEntry;
      if (!(jarEntry = (JarEntry) enumeration.nextElement()).isDirectory()
          && pattern.matcher(jarEntry.getName()).matches()) {
        String name = new File(jarEntry.getName()).getName();
        byte[] values = new byte[262144];
        FileOutputStream fileOutputStream = new FileOutputStream(("" + text3 + name));
        InputStream inputStream;
        InputStream inputStream2 = inputStream = jarFile.getInputStream(jarEntry);
        int value4;
        while ((value4 = inputStream2.read(values)) > 0) {
          inputStream2 = inputStream;
          fileOutputStream.write(values, 0, value4);
        }
        fileOutputStream.close();
        inputStream.close();
        Configuration.getInputOptions()
            .processInputJar(
                new InputJar(
                    ("" + text3 + name), ("" + text3 + name + ".obf"), text, jarEntry.getName()));
      }
    }
    jarFile.close();
  }

  public boolean matchesText(String text) {
    return text.length() > 0 && new File(text).isDirectory();
  }

  public String[] createTexts(String text) throws ConfigurationSaxException {
    String text2 = text.substring("custom(".length(), text.length() - 1);
    File file;
    if (!(file = new File(text2)).isAbsolute()) {
      try {
        String text3;
        if ((text3 =
                new File(XmlParsingContext.getText(this.xmlParsingContext))
                    .getCanonicalFile()
                    .getParent())
            != null) {
          text2 = new File(("" + text3 + File.separator + text2)).getCanonicalPath();
        }
      } catch (Exception exception) {
        String text4;
        if ((text4 =
                new File(XmlParsingContext.getText(this.xmlParsingContext))
                    .getAbsoluteFile()
                    .getParent())
            != null) {
          text2 = new File(("" + text4 + File.separator + text2)).getAbsolutePath();
        }
      }
      file = new File(text2);
    }
    if (!file.exists()) {
      throw new ConfigurationSaxException(
          this.xmlParsingContext,
          new StringBuilder()
              .insert(0, "Cannot find file '")
              .append(text2)
              .append("'. ")
              .append(this.createText())
              .toString());
    }
    String[] texts;
    try {
      texts = FileUtils.createTexts(file, "UTF-8", true);
    } catch (Exception exception2) {
      throw new ConfigurationSaxException(
          this.xmlParsingContext,
          new StringBuilder()
              .insert(0, exception2.getMessage())
              .append(" ")
              .append(this.createText())
              .toString());
    }
    if (texts != null && texts.length != 0) {
      return texts;
    } else {
      throw new ConfigurationSaxException(
          this.xmlParsingContext,
          new StringBuilder()
              .insert(0, "File '")
              .append(text2)
              .append("' is empty. ")
              .append(this.createText())
              .toString());
    }
  }

  public void processText4(String text) throws ConfigurationSaxException {
    String text3 = text;
    if (Configuration.getInputOptions().getValues3().size() == 0) {
      throw new ConfigurationSaxException(
          this.xmlParsingContext,
          new StringBuilder()
              .insert(0, "Cannot find parent jar. ")
              .append(this.createText())
              .toString());
    }
    InputEntry inputEntry = (InputEntry) Configuration.getInputOptions().getValues3().lastElement();
    if (text3.startsWith("/")) {
      text3 = text3.substring(1);
    }
    try {
      this.processText5(inputEntry.text2, text3);
    } catch (Exception exception) {
      throw new ConfigurationSaxException(
          this.xmlParsingContext,
          new StringBuilder()
              .insert(0, exception.getMessage())
              .append(" ")
              .append(this.createText())
              .toString());
    }
  }

  @Override
  public void setDocumentLocator(Locator locator2) {
    if (locator2 != null) {
      this.locator = locator2;
    }
  }

  public ConfigurationHandler(XmlParsingContext xmlParsingContext2) {
    super();
    this.xmlParsingContext = xmlParsingContext2;
    this.count = -1;
    this.enabled2 = false;
    this.text2 = "";
    this.text = "";
    this.locator = new ConfigurationLocator(this);
    this.enabled = true;
  }

  public ClassRule createClassRule(
      String text, String text2, String text3, String text4, String text5)
      throws ConfigurationSaxException {
    String text6 = text;
    this.processText(text6, text2);
    byte value = 0;
    byte value2 = 0;
    if (text3 != null) {
      if ("yes".equals(text3) || "true".equals(text3)) {
        value = 1;
      } else if ("keep-if-members-match".equals(text3)) {
        value2 = 1;
      } else if (!"no".equals(text3) && !"false".equals(text3)) {
        this.processText("ignore", "true", "false", "yes", "no", "keep-if-members-match");
      }
    }
    boolean matches = false;
    if ("yes".equals(text4) || "true".equals(text4)) {
      matches = true;
    }
    byte value3 = 0;
    if (text6 != null) {
      if (text6.startsWith("!")) {
        value3 = 1;
        text6 = text6.substring(1).trim();
      } else if (text6.startsWith("not")) {
        value3 = 1;
        text6 = text6.substring(3).trim();
      }
    }
    try {
      return new ClassRule(
          this.createText(text6, text2, " class *"),
          (value != 0),
          matches,
          (value2 != 0),
          (value3 != 0));
    } catch (ConfigurationException configurationException) {
      if (text6 != null) {
        this.processText(text5);
      } else {
        this.processText("access");
      }
      return null;
    }
  }

  public void processText(
      String text,
      String text2,
      String text3,
      String text4,
      String text5,
      String text6,
      String text7,
      String text8,
      String text9)
      throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' should have one of the following values: '")
            .append(text2)
            .append("', '")
            .append(text3)
            .append("', '")
            .append(text4)
            .append("', '")
            .append(text5)
            .append("', '")
            .append(text6)
            .append("', '")
            .append(text7)
            .append("', '")
            .append(text8)
            .append("' or '")
            .append(text9)
            .append("'. ")
            .append(this.createText())
            .toString());
  }

  public String createText() {
    return new StringBuilder()
        .insert(0, "[Line: ")
        .append(this.locator.getLineNumber())
        .append("]")
        .toString();
  }

  public void processText(String text, String text2, String text3)
      throws ConfigurationSaxException {
    throw new ConfigurationSaxException(
        this.xmlParsingContext,
        new StringBuilder()
            .insert(0, "Attribute '")
            .append(text)
            .append("' should have one of the following values: '")
            .append(text2)
            .append("' or '")
            .append(text3)
            .append("'. ")
            .append(this.createText())
            .toString());
  }
}
