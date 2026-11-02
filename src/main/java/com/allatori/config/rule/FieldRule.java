package com.allatori.config.rule;

import com.allatori.config.ConfigurationException;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.model.ClassStorage;

public class FieldRule {

  public String text;

  public boolean enabled;

  public String text2;

  public String text3;

  public String text4;

  public boolean enabled2;

  public int count;

  public boolean matches(ClassStorage classStorage, Field field) {
    if (!TemplateUtils.matches(field, this.count)) {
      return this.enabled;
    }
    if (!field.getText().matches(this.text4)) {
      return this.enabled;
    }
    if (!field.getType().toString().matches(this.text3)) {
      return this.enabled;
    }
    if (this.text != null && !TemplateUtils.matches(field.getAnnotationEntries(), this.text)) {
      return this.enabled;
    }
    if (this.text2 != null) {
      String text5;
      if ((text5 = field.getText2()).charAt(0) != 'L' || text5.charAt(text5.length() - 1) != ';') {
        return this.enabled;
      }
      String text6 = text5.substring(1, text5.length() - 1).replace('/', '.');
      if (!TemplateUtils.matches(classStorage, text6, this.text2)) {
        return this.enabled;
      }
    }
    return !this.enabled;
  }

  public void processText(String text5) throws ConfigurationException {
    String[] texts;
    if ((texts = text5.split("\\s+")) != null && texts.length != 0) {
      int value;
      FieldRule fieldRule;
      if ((value = texts.length) >= 3 && "instanceof".equals(texts[value - 2])) {
        int value2 = value - 1;
        String[] texts2 = texts;
        value -= 2;
        fieldRule = this;
        this.text2 = TemplateUtils.getText2(texts2[value2]);
      } else {
        fieldRule = this;
        this.text2 = null;
      }
      fieldRule.text4 = TemplateUtils.getText(texts[value - 1]);
      if (value > 1
          && !TemplateUtils.matches3(texts[value - 2])
          && !TemplateUtils.matches2(texts[value - 2])) {
        fieldRule = this;
        this.text3 = TemplateUtils.getText2(texts[value - 2]);
      } else {
        fieldRule = this;
        this.text3 = ".*";
      }
      fieldRule.count = TemplateUtils.calculateCount(text5);
      if (TemplateUtils.matches2(texts[0])) {
        this.text = TemplateUtils.getText2(texts[0].substring(1));
        this.text = 'L' + this.text.replaceAll("\\\\\\.", "/") + ';';
      } else {
        this.text = null;
      }
    } else {
      throw new ConfigurationException("Invalid template.");
    }
  }

  public FieldRule(String text, boolean enabled2, boolean enabled) throws ConfigurationException {
    super();
    this.processText(text);
    this.enabled2 = enabled2;
    this.enabled = enabled;
  }

  public boolean matches() {
    return this.enabled2;
  }
}
