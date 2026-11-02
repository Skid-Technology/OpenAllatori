package com.allatori.cli;

import com.allatori.VersionInfo;
import com.allatori.config.ClassPathEntry;
import com.allatori.config.Configuration;
import com.allatori.config.ConfigurationException;
import com.allatori.config.InputEntry;
import com.allatori.config.InputJar;
import com.allatori.engine.ObfuscationException;
import com.allatori.io.ClassStorageClassLoader;
import com.allatori.io.DirectoryClassReader;
import com.allatori.io.DirectoryWriter;
import com.allatori.io.JarClassReader;
import com.allatori.io.JarWriter;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;
import com.allatori.watermark.WatermarkProcessor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;

public class ObfuscationCommandSupport {

  public static void limitClassVersion(ClassStorage classStorage) {
    Iterator classesByName2 = classStorage.getClassesByName2();
    while (classesByName2.hasNext()) {
      ClassGen classGen;
      if ((classGen = (ClassGen) classesByName2.next()).getMajor() > 50) {
        classGen.setMajor(50);
      }
    }
  }

  public static void writeOutputs(ClassStorage classStorage)
      throws IOException, FileNotFoundException {
    JarWriter jarWriter = new JarWriter();
    Iterator iterator2;
    Iterator iterator3 = iterator2 = Configuration.getInputOptions().getValues().iterator();
    while (iterator3.hasNext()) {
      InputJar inputJar = (InputJar) iterator2.next();
      iterator3 = iterator2;
      jarWriter.processClassStorage(inputJar.text2, inputJar.text, classStorage);
    }
    Iterator iterator4;
    iterator3 = iterator4 = Configuration.getInputOptions().getValues3().iterator();
    while (iterator3.hasNext()) {
      InputEntry inputEntry = (InputEntry) iterator4.next();
      iterator3 = iterator4;
      jarWriter.processClassStorage(inputEntry.text2, inputEntry.text, classStorage);
    }
    if (Configuration.getInputOptions().getText() != null) {
      Vector vector;
      String[] texts = new String[(vector = Configuration.getInputOptions().getValues3()).size()];
      int value;
      for (int value2 = value = 0; value2 < vector.size(); value2 = value) {
        String text3 = ((InputEntry) vector.get(value)).text;
        int value3 = value;
        value++;
        texts[value3] = text3;
      }
      jarWriter.processText(texts, Configuration.getInputOptions().getText());
    }
    jarWriter.update();
  }

  public static void loadConfiguration(String text) throws Exception {
    try {
      Configuration.processText(text);
    } catch (ConfigurationException configurationException) {
      if (configurationException.getCause() == null) {
        throw new Exception(("Configuration error. " + configurationException.getMessage()));
      } else {
        throw new Exception(
            ("Error reading configuration. " + configurationException.getCause().getMessage()),
            configurationException.getCause());
      }
    }
  }

  public static void addWatermark(ClassStorage classStorage) throws ObfuscationException {
    new WatermarkProcessor(classStorage, Configuration.getWatermarkOptions().getText2())
        .processText(Configuration.getWatermarkOptions().getText());
  }

  public static String extractWatermark(ClassStorage classStorage) throws ObfuscationException {
    return new WatermarkProcessor(classStorage, Configuration.getWatermarkOptions().getText2())
        .createText();
  }

  public static void writeAllOutputs(ClassStorage classStorage)
      throws IOException, FileNotFoundException, ClassNotFoundException {
    writeOutputs(classStorage);
    writeDirectoryOutputs(classStorage);
  }

  public static StringBuffer getStringBuffer(StringBuffer stringBuffer, char value, int value2) {
    int value3;
    for (int value4 = value3 = 0; value4 < value2; value4 = value3) {
      value3++;
      stringBuffer.append(value);
    }
    return stringBuffer;
  }

  public static boolean loadConfigurationSafely(String text) {
    return loadConfigurationSafely(text, false);
  }

  public static String getBanner() {
    String text = " ## #   #    ## ### ### ##  ###";
    String text2 = "Java bytecode obfuscator";
    String text3 = "" + VersionInfo.getProductName() + " " + VersionInfo.getVersion();
    String website = VersionInfo.getWebsite();
    int value =
        Math.max(
            text.length(), Math.max(text3.length(), Math.max(website.length(), text2.length())));
    value += 4;
    StringBuffer stringBuffer;
    getStringBuffer(stringBuffer = new StringBuffer("\n"), '#', value).append("\n#");
    getStringBuffer(stringBuffer, ' ', value - 2).append("#\n#");
    int value2 = (value - text.length() - 2) / 2;
    int value3 = value - value2 - text.length() - 2;
    if (value2 > value3) {
      value3++;
      value2--;
    }
    getStringBuffer(stringBuffer, ' ', value2).append(text);
    getStringBuffer(stringBuffer, ' ', value3).append("#\n#");
    getStringBuffer(stringBuffer, ' ', value2).append((String) "# # #   #   # #  #  # # # #  # ");
    getStringBuffer(stringBuffer, ' ', value3).append("#\n#");
    getStringBuffer(stringBuffer, ' ', value2).append((String) "### #   #   ###  #  # # ##   # ");
    getStringBuffer(stringBuffer, ' ', value3).append("#\n#");
    getStringBuffer(stringBuffer, ' ', value2).append((String) "# # ### ### # #  #  ### # # ###");
    getStringBuffer(stringBuffer, ' ', value3).append("#\n#");
    int value4 = (value - text2.length() - 2) / 2;
    int value5 = value - value4 - text2.length() - 2;
    if (value4 > value5) {
      value5++;
      value4--;
    }
    getStringBuffer(stringBuffer, ' ', value - 2).append("#\n#");
    getStringBuffer(stringBuffer, ' ', value4).append(text2);
    getStringBuffer(stringBuffer, ' ', value5).append("#\n#");
    getStringBuffer(stringBuffer, ' ', value - 2).append("#\n#");
    int value6 = (value - text3.length() - 2) / 2;
    int value7 = value - value6 - text3.length() - 2;
    if (value6 > value7) {
      value7++;
      value6--;
    }
    getStringBuffer(stringBuffer, ' ', value6).append(text3);
    getStringBuffer(stringBuffer, ' ', value7).append("#\n#");
    getStringBuffer(stringBuffer, ' ', value - 2).append("#\n#");
    int value8 = (value - website.length() - 2) / 2;
    int value9 = value - value8 - website.length() - 2;
    if (value8 > value9) {
      value9++;
      value8--;
    }
    getStringBuffer(stringBuffer, ' ', value8).append(website);
    getStringBuffer(stringBuffer, ' ', value9).append("#\n#");
    getStringBuffer(stringBuffer, ' ', value - 2).append("#\n");
    getStringBuffer(stringBuffer, '#', value).append("\n");
    return stringBuffer.toString();
  }

  public static boolean loadConfigurationSafely(String text, boolean matches) {
    try {
      Configuration.processEnabled(text, matches);
      return true;
    } catch (ConfigurationException configurationException) {
      if (configurationException.getCause() == null) {
        Log.error("Configuration error. " + configurationException.getMessage());
      } else {
        Log.error("Error reading configuration. " + configurationException.getCause().getMessage());
      }
      return false;
    }
  }

  public static ClassStorage createClassStorage() throws IOException {
    ClassStorage classStorage = new ClassStorage(createClassLoader());
    Iterator iterator2;
    Iterator iterator3 = iterator2 = Configuration.getInputOptions().getValues().iterator();
    while (iterator3.hasNext()) {
      InputJar inputJar = (InputJar) iterator2.next();
      iterator3 = iterator2;
      JarClassReader.processClassStorage(inputJar.text2, classStorage);
    }
    Iterator iterator4;
    iterator3 = iterator4 = Configuration.getInputOptions().getValues3().iterator();
    while (iterator3.hasNext()) {
      InputEntry inputEntry = (InputEntry) iterator4.next();
      iterator3 = iterator4;
      JarClassReader.processClassStorage(inputEntry.text2, classStorage);
    }
    Iterator iterator5;
    iterator3 = iterator5 = Configuration.getInputOptions().getValues2().iterator();
    while (iterator3.hasNext()) {
      ClassPathEntry classPathEntry = (ClassPathEntry) iterator5.next();
      iterator3 = iterator5;
      DirectoryClassReader.processText(classPathEntry.text, classStorage, classPathEntry.text);
    }
    return classStorage;
  }

  public static ClassStorageClassLoader createClassLoader() {
    Vector values = Configuration.getClassPathOptions().getValues();
    int value;
    for (int value2 = value = 0; value2 < values.size(); value2 = ++value) {
      try {
        values.set(value, new File((String) values.get(value)).toURL());
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    URL[] uRLs = (URL[]) values.toArray(new URL[0]);
    return new ClassStorageClassLoader(uRLs);
  }

  public static void writeDirectoryOutputs(ClassStorage classStorage)
      throws FileNotFoundException, IOException, ClassNotFoundException {
    DirectoryWriter directoryWriter = new DirectoryWriter();
    Iterator iterator2;
    Iterator iterator3 = iterator2 = Configuration.getInputOptions().getValues2().iterator();
    while (iterator3.hasNext()) {
      ClassPathEntry classPathEntry = (ClassPathEntry) iterator2.next();
      iterator3 = iterator2;
      directoryWriter.processClassStorage(classPathEntry.text, classPathEntry.text2, classStorage);
    }
  }
}
