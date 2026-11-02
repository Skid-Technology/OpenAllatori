package com.allatori.transform;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;

public class RemoveFieldsTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  @Override
  public void transform(ClassGen classGen) {
    int value;
    Field[] fields;
    for (int value2 = value = (fields = classGen.getFields()).length - 1;
        value2 >= 0;
        value2 = --value) {
      Field field = fields[value];
      if (Configuration.getFieldRemovalOptions().matchesField(this.classStorage, classGen, field)) {
        classGen.processField2(field);
        String text = "" + classGen.getClassName() + "." + field.getText() + field.getText2();
        Configuration.getFieldRemovalOptions().processText(text);
        Log.info(
            " [remove-fields] removed "
                + classGen.getClassName()
                + "."
                + field.getText()
                + " "
                + field.getText2());
      }
    }
  }

  @Override
  public void finish() {}

  public RemoveFieldsTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }
}
