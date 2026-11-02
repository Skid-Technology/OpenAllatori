package com.allatori;

import com.allatori.cli.ObfuscationCommandSupport;
import com.allatori.config.Configuration;
import com.allatori.engine.ObfuscationEngine;
import com.allatori.engine.ObfuscationException;
import com.allatori.model.ClassStorage;
import com.allatori.naming.MappingXmlWriter;
import com.allatori.util.Log;
import java.io.File;
import java.io.IOException;

public class Obfuscate extends ObfuscationCommandSupport {

  public static void obfuscate(ClassStorage classStorage) {
    new ObfuscationEngine(classStorage).obfuscate();
  }

  public static void runObfuscation()
      throws IOException, ObfuscationException, ClassNotFoundException {
    ClassStorage classStorage;
    obfuscate(classStorage = createClassStorage());
    if (Configuration.getWatermarkOptions().getText2() != null
        && Configuration.getWatermarkOptions().getText() != null) {
      addWatermark(classStorage);
    }
    if (Configuration.getObfuscationOptions().isJava6Enabled()) {
      limitClassVersion(classStorage);
    }
    writeAllOutputs(classStorage);
    Log.info2("Obfuscation completed. Writing log file...");
    MappingXmlWriter.update();
  }

  public static void main(String[] texts) {
    String[] texts2;
    if (texts.length > 1 && "-silent".equals(texts[1])) {
      Log.setSilent(true);
      texts2 = texts;
    } else {
      System.out.println(getBanner());
      texts2 = texts;
    }
    if (texts2.length != 1 && !Log.isSilent()) {
      printUsage();
      System.exit(0);
    }
    if (!loadConfigurationSafely(texts[0])) {
      System.exit(-1);
    }
    try {
      runObfuscation();
    } catch (Exception exception) {
      Log.error(exception.getMessage());
      System.out.println(
          "############### EXCEPTION (" + VersionInfo.getVersion() + ") ###############");
      exception.printStackTrace();
      System.out.println("#################################################");
      System.exit(-1);
    }
    Configuration.update();
  }

  public static void printUsage() {
    System.out.println("" + VersionInfo.getProductName() + " " + VersionInfo.getVersion());
    System.out.println("Usage:");
    System.out.println("com.allatori.Obfuscate <config file>");
  }

  public static void run(File file) throws Exception {
    loadConfiguration(file.getPath());
    runObfuscation();
    Configuration.update();
  }
}
