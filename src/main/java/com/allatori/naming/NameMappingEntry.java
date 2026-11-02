package com.allatori.naming;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.SourceFile;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.LineNumberGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.model.NameMapping;
import com.allatori.naming.generator.NameGenerator;
import com.allatori.util.LookupTable;
import com.allatori.util.random.RandomUtils;
import java.util.HashSet;
import java.util.Vector;

public class NameMappingEntry {

  public Vector values;

  public NameGenerator nameGenerator;

  public String text;

  public LookupTable lookupTable;

  public Vector values2;

  public LookupTable lookupTable2;

  public String text2;

  public HashSet values3;

  public Vector getValues2() {
    return this.values2;
  }

  public void processClassGen(ClassGen classGen) {
    int lineNumbersMode =
        Configuration.getRenamingOptions().getLineNumberOptions().getLineNumbersMode();
    Configuration.getRenamingOptions().getLineNumberOptions();
    if (lineNumbersMode != 2) {
      lineNumbersMode =
          Configuration.getRenamingOptions().getLineNumberOptions().getLineNumbersMode();
      Configuration.getRenamingOptions().getLineNumberOptions();
      int value;
      if (lineNumbersMode == 3) {
        Attribute[] attributes;
        for (int value2 = value = (attributes = classGen.getAttributes()).length - 1;
            value2 >= 0;
            value2 = --value) {
          Attribute attribute;
          if ((attribute = attributes[value]) instanceof SourceFile) {
            classGen.processAttribute2(attribute);
            return;
          }
        }
      } else {
        lineNumbersMode =
            Configuration.getRenamingOptions().getLineNumberOptions().getLineNumbersMode();
        Configuration.getRenamingOptions().getLineNumberOptions();
        int value3;
        if (lineNumbersMode == 1) {
          Attribute[] attributes2;
          for (int value4 = value3 = (attributes2 = classGen.getAttributes()).length - 1;
              value4 >= 0;
              value4 = --value3) {
            Attribute attribute2;
            if ((attribute2 = attributes2[value3]) instanceof SourceFile) {
              SourceFile sourceFile;
              String text = (sourceFile = (SourceFile) attribute2).getText();
              String text2;
              if ((text2 = this.lookupTable.getText(text)) == null) {
                do {
                  text2 = this.nameGenerator.nextName();
                } while (this.values3.contains(text2));
                this.values2.add(new NameMapping(text, text2));
                this.lookupTable.processText(text, text2);
                this.values3.add(text2);
              }
              sourceFile.setSourceFileIndex(classGen.getConstantPool().addUtf8(text2));
              return;
            }
          }
        }
      }
    }
  }

  public Vector getValues() {
    return this.values;
  }

  public NameMappingEntry() {
    super();
    this.nameGenerator =
        Configuration.getRenamingOptions().getNameGenerationOptions().createNameGenerator();
    this.values2 = new Vector();
    this.lookupTable = new LookupTable();
    this.values3 = new HashSet();
    this.values = new Vector();
    this.lookupTable2 = new LookupTable();
    this.text2 = "\" s=\"";
    this.text = "\" e=\"";
  }

  public LookupTable getLookupTable2() {
    return this.lookupTable2;
  }

  public void processMethodGen(String text3, MethodGen methodGen) {
    int lineNumbersMode =
        Configuration.getRenamingOptions().getLineNumberOptions().getLineNumbersMode();
    Configuration.getRenamingOptions().getLineNumberOptions();
    if (lineNumbersMode != 2) {
      lineNumbersMode =
          Configuration.getRenamingOptions().getLineNumberOptions().getLineNumbersMode();
      Configuration.getRenamingOptions().getLineNumberOptions();
      if (lineNumbersMode == 3) {
        methodGen.removeLineNumbers();
      } else {
        lineNumbersMode =
            Configuration.getRenamingOptions().getLineNumberOptions().getLineNumbersMode();
        Configuration.getRenamingOptions().getLineNumberOptions();
        if (lineNumbersMode == 1) {
          LineNumberGen[] lineNumbers = methodGen.getLineNumbers();
          int value = Integer.MAX_VALUE;
          int value2 = Integer.MIN_VALUE;
          int value3;
          for (int value4 = value3 = lineNumbers.length - 1; value4 >= 0; value4 = value3) {
            LineNumberGen lineNumberGen;
            int value5;
            if ((value5 = (lineNumberGen = lineNumbers[value3]).getSourceLine()) < value) {
              value = value5;
            }
            if (value5 > value2) {
              value2 = value5;
            }
            if (value5 > this.values.size() - 1) {
              int size2 = this.values.size();
              int value6 = value5 - size2 + 200;
              Vector vector = new Vector(value6);
              int value7;
              for (int value8 = value7 = 0; value8 < value6; value8 = value7) {
                vector.add(new Integer(size2 + value7++));
              }
              RandomUtils.processValues(vector);
              this.values.addAll(vector);
            }
            Object result = this.values.get(value5);
            LineNumberGen lineNumberGen2 = lineNumberGen;
            value3--;
            lineNumberGen2.setSourceLine((Integer) result);
          }
          if (value < Integer.MAX_VALUE) {
            this.lookupTable2.processText(
                "" + text3 + "&" + methodGen.getName() + "&" + methodGen.getText(),
                "" + this.text2 + value + this.text + value2);
          }
        }
      }
    }
  }

  public void processText(String text, String text2) {
    this.values2.add(new NameMapping(text, text2));
    this.lookupTable.processText(text, text2);
    this.values3.add(text2);
  }
}
