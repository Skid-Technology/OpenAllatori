package com.allatori.config.property;

import com.allatori.config.ConfigurationException;

public class PropertyRegistry {

  public static Object[][] values =
      new Object[][] {{"string-encryption", StringEncryptionProperty.class}, {"", ""}, {"", ""}};

  public static String text3 = "enable";

  public static String text = "disable";

  public static String text2 = "maximum";

  public static PropertyHandler createPropertyHandler(String text) throws ConfigurationException {
    int value;
    for (int value2 = value = values.length - 1; value2 >= 0; value2 = --value) {
      if (text.equals(values[value][0])) {
        Class classValue = (Class) values[value][1];
        try {
          return (PropertyHandler) classValue.newInstance();
        } catch (Exception exception) {
          throw new ConfigurationException("Cannot instantiate property class");
        }
      }
    }
    throw new ConfigurationException("Unknown property");
  }
}
