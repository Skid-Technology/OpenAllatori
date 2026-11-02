package com.allatori;

import com.allatori.cli.ObfuscationCommandSupport;
import com.allatori.config.Configuration;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;

public class Watermark extends ObfuscationCommandSupport {

  public static void main(String[] texts) {
    System.out.println(getBanner());
    if (texts.length != 2) {
      printUsage();
      System.exit(0);
    }
    byte value = 0;
    String[] texts2;
    if ("-add".equals(texts[0])) {
      value = 0;
      texts2 = texts;
    } else if ("-extract".equals(texts[0])) {
      value = 1;
      texts2 = texts;
    } else {
      printUsage();
      System.exit(0);
      texts2 = texts;
    }
    if (!loadConfigurationSafely(texts2[1], value != 0)) {
      System.exit(-1);
    }
    try {
      ClassStorage classStorage = Obfuscate.createClassStorage();
      if (Configuration.getWatermarkOptions().isEnabled()) {
        if (Configuration.getWatermarkOptions().getText2() == null) {
          Log.error("Configuration error. Watermark key is not set.");
          System.exit(0);
        }
        System.out.println("Extracted watermark: \"" + extractWatermark(classStorage) + "\"");
      } else {
        if (Configuration.getWatermarkOptions().getText2() == null) {
          Log.error("Configuration error. Watermark key is not set.");
          System.exit(0);
        }
        if (Configuration.getWatermarkOptions().getText() == null) {
          Log.error("Configuration error. Watermark value is not set.");
          System.exit(0);
        }
        addWatermark(classStorage);
        writeAllOutputs(classStorage);
      }
    } catch (Exception exception) {
      Log.error(exception.getMessage());
    }
    Configuration.update();
  }

  public static void printUsage() {
    System.out.println("" + VersionInfo.getProductName() + " " + VersionInfo.getVersion());
    System.out.println("Usage:");
    System.out.println("com.allatori.Watermark -add <config file>");
    System.out.println("or");
    System.out.println("com.allatori.Watermark -extract <config file>");
  }
}
