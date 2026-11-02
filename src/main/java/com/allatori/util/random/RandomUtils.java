package com.allatori.util.random;

import com.allatori.config.Configuration;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public class RandomUtils {

  public static boolean enabled = false;

  public static RandomGenerator randomGenerator;

  public static void processCount(List values, int value, int value2) {
    values.set(value, values.set(value2, values.get(value)));
  }

  public static double calculateValue() {
    if (!enabled) {
      update();
    }
    return randomGenerator.nextDouble();
  }

  public static void processCount(Object[] results, int value, int value2) {
    Object result = results[value];
    results[value] = results[value2];
    results[value2] = result;
  }

  public static int calculateCount(int value) {
    if (!enabled) {
      update();
    }
    int value2;
    return (value2 = randomGenerator.nextInt(0, value)) < value ? value2 : value2 - 1;
  }

  public static void update() {
    String text;
    if ((text = Configuration.getObfuscationOptions().getRandomSeed()) != null
        && text.length() != 0) {
      randomGenerator = new RanmarRandom(text.hashCode());
    } else {
      randomGenerator = new RanmarRandom(System.currentTimeMillis());
    }
    enabled = true;
  }

  public static void processValues(List values) {
    if (!enabled) {
      update();
    }
    int value;
    int value2;
    if ((value = values.size()) >= 5 && !(values instanceof RandomAccess)) {
      Object[] results = values.toArray();
      int value3;
      for (int value4 = value3 = value; value4 > 1; value4 = value3) {
        int value5 = value3 - 1;
        int value6 = value3;
        value3--;
        processCount(results, value5, calculateCount(value6));
      }
      ListIterator listIterator2 = values.listIterator();
      int value7;
      for (int value8 = value7 = 0; value8 < results.length; value8 = value7) {
        listIterator2.next();
        int value9 = value7;
        value7++;
        listIterator2.set(results[value9]);
      }
    } else {
      for (int value10 = value2 = value; value10 > 1; value10 = value2) {
        int value11 = value2 - 1;
        int value12 = value2;
        value2--;
        processCount(values, value11, calculateCount(value12));
      }
    }
  }
}
