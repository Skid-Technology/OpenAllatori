package com.allatori.transform;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.util.random.RandomUtils;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class MemberReorderTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  public Vector values;

  public Vector values2;

  @Override
  public void transform(ClassGen classGen) {
    int value;
    int value2 =
        value = Configuration.getObfuscationOptions().calculateCount2(this.classStorage, classGen);
    Configuration.getObfuscationOptions();
    if (value2 == 100) {
      this.values.add(classGen);
    } else {
      Configuration.getObfuscationOptions();
      if (value == 101) {
        this.values2.add(classGen);
      } else {
        this.processCount(classGen, value);
        this.processCount2(classGen, value);
      }
    }
  }

  @Override
  public void finish() {
    Iterator iterator2;
    Iterator iterator3 = iterator2 = this.values.iterator();
    while (iterator3.hasNext()) {
      ClassGen classGen = (ClassGen) iterator2.next();
      iterator3 = iterator2;
      Configuration.getObfuscationOptions();
      this.processCount(classGen, 100);
      Configuration.getObfuscationOptions();
      this.processCount2(classGen, 100);
    }
    Iterator iterator4;
    iterator3 = iterator4 = this.values2.iterator();
    while (iterator3.hasNext()) {
      ClassGen classGen2 = (ClassGen) iterator4.next();
      iterator3 = iterator4;
      Configuration.getObfuscationOptions();
      this.processCount(classGen2, 101);
      Configuration.getObfuscationOptions();
      this.processCount2(classGen2, 101);
    }
    this.values2 = this.values = null;
  }

  public MemberReorderTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
    this.values = new Vector();
    this.values2 = new Vector();
  }

  public void processCount(ClassGen classGen, int value) {
    Configuration.getObfuscationOptions();
    if (value == 1) {
      int value2;
      Field[] fields;
      for (int value3 = value2 = (fields = classGen.getFields()).length - 1;
          value3 >= 0;
          value3 = value2) {
        int value4 = value2;
        Field[] fields2 = fields;
        value2--;
        classGen.processField2(fields2[value4]);
      }
      List values;
      List values2 = values = Arrays.asList(fields);
      RandomUtils.processValues(values2);
      int value5;
      for (int value6 = value5 = values2.size() - 1; value6 >= 0; value6 = value5) {
        int value7 = value5;
        List values3 = values;
        value5--;
        classGen.processField((Field) values3.get(value7));
      }
    } else {
      Configuration.getObfuscationOptions();
      if (value >= 100) {
        int value8;
        Field[] fields3;
        for (int value9 = value8 = (fields3 = classGen.getFields()).length - 1;
            value9 >= 0;
            value9 = value8) {
          int value10 = value8;
          Field[] fields4 = fields3;
          value8--;
          classGen.processField2(fields4[value10]);
        }
        List values4 = Arrays.asList(fields3);
        Collections.sort(values4, new FieldOrderComparator(this, value));
        int value11;
        for (int value12 = value11 = values4.size() - 1; value12 >= 0; value12 = value11) {
          int value13 = value11;
          value11--;
          classGen.processField((Field) values4.get(value13));
        }
      }
    }
  }

  public void processCount2(ClassGen classGen, int value) {
    Configuration.getObfuscationOptions();
    if (value == 1) {
      Method[] methods;
      int value2;
      for (int value3 = value2 = (methods = classGen.getMethods()).length - 1;
          value3 >= 0;
          value3 = value2) {
        int value4 = value2;
        Method[] methods2 = methods;
        value2--;
        classGen.processMethod(methods2[value4]);
      }
      List values = Arrays.asList(methods);
      RandomUtils.processValues(values);
      int value5;
      for (int value6 = value5 = values.size() - 1; value6 >= 0; value6 = value5) {
        int value7 = value5;
        Method[] methods3 = methods;
        value5--;
        classGen.processMethod2(methods3[value7]);
      }
    } else {
      Configuration.getObfuscationOptions();
      if (value >= 100) {
        Method[] methods4;
        int value8;
        for (int value9 = value8 = (methods4 = classGen.getMethods()).length - 1;
            value9 >= 0;
            value9 = value8) {
          int value10 = value8;
          Method[] methods5 = methods4;
          value8--;
          classGen.processMethod(methods5[value10]);
        }
        List values2 = Arrays.asList(methods4);
        Collections.sort(values2, new MethodOrderComparator(this, value));
        int value11;
        for (int value12 = value11 = values2.size() - 1; value12 >= 0; value12 = value11) {
          int value13 = value11;
          Method[] methods6 = methods4;
          value11--;
          classGen.processMethod2(methods6[value13]);
        }
      }
    }
  }
}
