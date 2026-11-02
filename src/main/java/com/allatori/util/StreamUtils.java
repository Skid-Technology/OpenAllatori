package com.allatori.util;

import com.allatori.config.Configuration;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class StreamUtils {

  public static void processWriter(Reader reader, Writer writer) throws IOException {
    StringBuffer stringBuffer = new StringBuffer();
    int value;
    while ((value = reader.read()) >= 0) {
      char value2 = (char) value;
      if (stringBuffer.length() == 0) {
        if (Character.isJavaIdentifierStart(value2)) {
          stringBuffer.append(value2);
        } else {
          writer.write(value2);
        }
      } else if (!Character.isJavaIdentifierPart(value2) && value2 != '.' && value2 != '-') {
        processText(writer, stringBuffer.toString());
        stringBuffer.setLength(0);
        writer.write(value2);
      } else {
        stringBuffer.append(value2);
      }
    }
    processText(writer, stringBuffer.toString());
  }

  public static void processText(InputStream inputStream, OutputStream outputStream, String text)
      throws UnsupportedEncodingException, IOException {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, text));
    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, text));
    processWriter(bufferedReader, bufferedWriter);
    bufferedWriter.flush();
    outputStream.flush();
  }

  public static void processText(Writer writer, String text) throws IOException {
    String text2;
    if ((text2 = Configuration.getMappingOptions().renamingLog.lookupTable4.getText(text))
        != null) {
      writer.write(text2);
    } else {
      writer.write(text);
    }
  }

  public static String createText(String text, String text2) {
    String text3 = text;
    if (text3 == null) {
      text3 = "";
    }
    String text4;
    if (text3.length() > 0) {
      if (!text2.startsWith(text3)) {
        return text2;
      }
      text4 = text2 = text2.substring(text3.length());
    } else {
      text4 = text2;
    }
    String[] texts;
    String[] texts2 = texts = text4.split("/");
    String text5 = texts2[texts2.length - 1];
    String text6 = "";
    if (text5.contains(".")) {
      text6 = text5.substring(text5.indexOf(46));
      text5 = text5.substring(0, text5.indexOf(46));
    }
    String text7 = "";
    int value;
    for (int value2 = value = 0; value2 < texts.length - 1; value2 = value) {
      StringBuilder stringBuilder = new StringBuilder().insert(0, text7).append(texts[value]);
      value++;
      text7 = stringBuilder.append((char) '.').toString();
    }
    String text8 = "" + text7 + text5;
    if (Configuration.getMappingOptions().renamingLog.lookupTable4.matchesText(text8)) {
      return ""
          + text3
          + Configuration.getMappingOptions()
              .renamingLog
              .lookupTable4
              .getText(text8)
              .replace('.', '/')
          + text6;
    }
    while (text5.contains("_")) {
      text6 = "" + text5.substring(text5.lastIndexOf(95)) + text6;
      text5 = text5.substring(0, text5.lastIndexOf(95));
      String text9 = "" + text7 + text5;
      if (Configuration.getMappingOptions().renamingLog.lookupTable4.matchesText(text9)) {
        return ""
            + text3
            + Configuration.getMappingOptions()
                .renamingLog
                .lookupTable4
                .getText(text9)
                .replace('.', '/')
            + text6;
      }
    }
    int value3;
    for (int value4 = value3 = texts.length - 2; value4 >= 0; value4 = --value3) {
      String text10 = "";
      int value5;
      for (int value6 = value5 = 0; value6 < value3; value6 = value5) {
        StringBuilder stringBuilder2 = new StringBuilder().insert(0, text10).append(texts[value5]);
        value5++;
        text10 = stringBuilder2.append((char) '.').toString();
      }
      String text11 = "" + text10 + texts[value3];
      if (Configuration.getMappingOptions().lookupTable2.matchesText(text11)
          && !text11.equals(Configuration.getMappingOptions().lookupTable2.getText(text11))) {
        String text12 =
            ""
                + text3
                + Configuration.getMappingOptions().lookupTable2.getText(text11).replace('.', '/');
        int value7;
        for (int value8 = value7 = value3 + 1; value8 <= texts.length - 1; value8 = value7) {
          StringBuilder stringBuilder3 = new StringBuilder().insert(0, text12).append('/');
          String text13 = texts[value7];
          value7++;
          text12 = stringBuilder3.append(text13).toString();
        }
        return text12;
      }
    }
    return "" + text3 + text2;
  }
}
