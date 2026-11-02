package com.allatori.config.rule;

import com.allatori.config.ConfigurationException;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.MethodGen;

public class MethodRule {

  public String text;

  public String text2;

  public String text3;

  public String text4;

  public boolean enabled;

  public boolean enabled2;

  public int count;

  public boolean matches(MethodGen methodGen) {
    if (!TemplateUtils.matches(methodGen, this.count)) {
      return this.enabled2;
    } else if (!methodGen.getName().matches(this.text3)) {
      return this.enabled2;
    } else if (!methodGen.getReturnType().toString().matches(this.text)) {
      return this.enabled2;
    } else if (!TemplateUtils.matches(methodGen.getArgumentTypes(), this.text2)) {
      return this.enabled2;
    } else {
      return this.text4 != null
              && !TemplateUtils.matches(methodGen.getAnnotationEntryGens(), this.text4)
          ? this.enabled2
          : !this.enabled2;
    }
  }

  public void processText(String text5) throws ConfigurationException {
    String[] texts;
    String[] texts2;
    if ((texts = text5.split("\\(|\\)")).length != 2) {
      if (texts.length == 1 && text5.endsWith("()")) {
        texts2 = texts;
      } else {
        if (texts.length <= 2 || !text5.contains("regex:")) {
          throw new ConfigurationException("Invalid template.");
        }
        String text6 = text5.substring(0, text5.lastIndexOf(40));
        String text7 = text5.substring(text5.lastIndexOf(40) + 1, text5.lastIndexOf(41));
        texts2 = texts = new String[] {text6, text7};
      }
    } else {
      texts2 = texts;
    }
    if (texts2.length == 1) {
      texts2 = texts;
      this.text2 = "";
    } else {
      texts2 = texts;
      String text8 = texts[1];
      this.text2 = TemplateUtils.getText4(text8);
    }
    String[] texts3;
    String text9;
    if ((texts3 = (text9 = texts2[0]).split("\\s+")) != null && texts3.length != 0) {
      this.text3 = TemplateUtils.getText(texts3[texts3.length - 1]);
      MethodRule methodRule;
      if (texts3.length > 1
          && !TemplateUtils.matches3(texts3[texts3.length - 2])
          && !TemplateUtils.matches2(texts3[texts3.length - 2])) {
        this.text = TemplateUtils.getText2(texts3[texts3.length - 2]);
        methodRule = this;
      } else {
        methodRule = this;
        this.text = ".*";
      }
      methodRule.count = TemplateUtils.calculateCount(text9);
      if (TemplateUtils.matches2(texts3[0])) {
        this.text4 = TemplateUtils.getText2(texts3[0].substring(1));
        this.text4 = 'L' + this.text4.replaceAll("\\\\\\.", "/") + ';';
      } else {
        this.text4 = null;
      }
    } else {
      throw new ConfigurationException("Invalid template.");
    }
  }

  public boolean matches() {
    return this.enabled;
  }

  public MethodRule(String text, boolean enabled, boolean enabled2) throws ConfigurationException {
    super();
    this.processText(text);
    this.enabled = enabled;
    this.enabled2 = enabled2;
  }

  public boolean matches(Method method) {
    if (!TemplateUtils.matches(method, this.count)) {
      return this.enabled2;
    } else if (!method.getText().matches(this.text3)) {
      return this.enabled2;
    } else if (!method.getReturnType().toString().matches(this.text)) {
      return this.enabled2;
    } else if (!TemplateUtils.matches(method.getArgumentTypes(), this.text2)) {
      return this.enabled2;
    } else {
      return this.text4 != null && !TemplateUtils.matches(method.getAnnotationEntries(), this.text4)
          ? this.enabled2
          : !this.enabled2;
    }
  }
}
