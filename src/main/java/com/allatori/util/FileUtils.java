package com.allatori.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Vector;

public class FileUtils {

  public static String createText(File file, String text)
      throws FileNotFoundException, UnsupportedEncodingException, IOException {
    StringBuffer stringBuffer = new StringBuffer((int) file.length());
    BufferedReader bufferedReader;
    BufferedReader bufferedReader2 =
        text != null
            ? (bufferedReader =
                new BufferedReader(new InputStreamReader(new FileInputStream(file), text)))
            : (bufferedReader = new BufferedReader(new FileReader(file)));
    int value;
    while ((value = bufferedReader2.read()) != -1) {
      bufferedReader2 = bufferedReader;
      stringBuffer.append((char) value);
    }
    bufferedReader.close();
    return stringBuffer.toString();
  }

  public static void processFile(File file, File file2) throws IOException, FileNotFoundException {
    FileChannel fileChannel = null;
    FileChannel fileChannel2 = null;
    try {
      fileChannel = new FileInputStream(file).getChannel();
      fileChannel2 = new FileOutputStream(file2).getChannel();
      MappedByteBuffer mappedByteBuffer =
          fileChannel.map(MapMode.READ_ONLY, 0L, fileChannel.size());
      fileChannel2.write(mappedByteBuffer);
    } finally {
      if (fileChannel != null) {
        fileChannel.close();
      }
      if (fileChannel2 != null) {
        fileChannel2.close();
      }
    }
  }

  public static String[] createTexts(File file, String text, boolean matches)
      throws FileNotFoundException, UnsupportedEncodingException, IOException {
    BufferedReader bufferedReader;
    if (text != null) {
      bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), text));
    } else {
      bufferedReader = new BufferedReader(new FileReader(file));
    }
    Vector vector = new Vector();
    label25:
    while (true) {
      BufferedReader bufferedReader2 = bufferedReader;
      String text2;
      while ((text2 = bufferedReader2.readLine()) != null) {
        if (matches) {
          if (text2.length() <= 0) {
            continue label25;
          }
          bufferedReader2 = bufferedReader;
          vector.add(text2);
        } else {
          vector.add(text2);
          bufferedReader2 = bufferedReader;
        }
      }
      bufferedReader.close();
      String[] texts = new String[vector.size()];
      return (String[]) vector.toArray(texts);
    }
  }

  public static void processText(File file, String text, String text2)
      throws FileNotFoundException, UnsupportedEncodingException, IOException {
    BufferedWriter bufferedWriter;
    (text != null
            ? (bufferedWriter =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), text)))
            : (bufferedWriter = new BufferedWriter(new FileWriter(file))))
        .write(text2);
    bufferedWriter.close();
  }
}
