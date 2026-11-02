package com.allatori.wtk;

import com.allatori.Obfuscate;
import com.allatori.config.Configuration;
import com.allatori.config.ConfigurationException;
import com.allatori.engine.ObfuscationException;
import com.allatori.util.FileUtils;
import com.allatori.util.Log;
import com.sun.kvem.environment.Obfuscator;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

public class WTKObfuscator implements Obfuscator {

  public String configurationFileName;

  public WTKObfuscator() {
    super();
    this.configurationFileName = "allatori.xml";
  }

  public void createScriptFile(File file, File file2) {}

  public void run(
      File file, String text, String text2, String text3, String text4, String text5, String text6)
      throws IOException, ConfigurationException, ObfuscationException, ClassNotFoundException {
    String text7;
    if (!text4.endsWith("/") && !text4.endsWith("\\")) {
      text7 = "" + text4 + "/" + this.configurationFileName;
    } else {
      text7 = "" + text4 + this.configurationFileName;
    }
    File file2;
    File file3;
    if ((file2 = new File(text7)).exists() && !file2.canWrite()) {
      Log.info2("Using the existing configuration file.");
      file3 = file2;
    } else {
      String text8 = this.createText(file, text, text2, text3, text4, text5, text6);
      try {
        FileUtils.processText(file2, "UTF-8", text8);
      } catch (Exception exception) {
        exception.printStackTrace();
        throw new IOException();
      }
      file3 = file2;
    }
    Configuration.processText(file3.getPath());
    Obfuscate.runObfuscation();
    Configuration.update();
  }

  public String createText(
      File file,
      String text,
      String text2,
      String text3,
      String text4,
      String text5,
      String text6) {
    StringBuffer stringBuffer;
    (stringBuffer = new StringBuffer()).append("<config>\n");
    stringBuffer.append("    <jars>\n");
    stringBuffer.append("        <jar in=\"" + text3 + "\" out=\"" + file.getPath() + "\"/>\n");
    stringBuffer.append("    </jars>\n\n");
    stringBuffer.append("    <classpath>\n");
    StringTokenizer stringTokenizer =
        new StringTokenizer(text5, System.getProperty("path.separator"));
    StringTokenizer stringTokenizer2 = stringTokenizer;
    while (stringTokenizer2.hasMoreTokens()) {
      stringTokenizer2 = stringTokenizer;
      String text7 = stringTokenizer.nextToken();
      stringBuffer.append("        <jar name=\"" + text7 + "\"/>\n");
    }
    stringBuffer.append("    </classpath>\n\n");
    stringBuffer.append("    <keep-names>\n");
    stringBuffer.append(
        "        <class template=\"class * extends javax.microedition.midlet.MIDlet\"/>\n");
    stringBuffer.append("    </keep-names>\n\n");
    stringBuffer.append("    <property name=\"line-numbers\" value=\"remove\"/>\n");
    stringBuffer.append("    <property name=\"string-encryption\" value=\"disable\"/>\n");
    stringBuffer.append("    <property name=\"member-reorder\" value=\"enable\"/>\n");
    stringBuffer.append("    <property name=\"control-flow-obfuscation\" value=\"enable\"/>\n");
    stringBuffer.append("    <property name=\"local-variables-naming\" value=\"remove\"/>\n");
    stringBuffer.append("</config>\n");
    return stringBuffer.toString();
  }
}
