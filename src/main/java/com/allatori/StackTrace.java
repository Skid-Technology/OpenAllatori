package com.allatori;

import com.allatori.cli.ObfuscationCommandSupport;
import com.allatori.stacktrace.MappingXmlReader;
import com.allatori.stacktrace.StackTraceMappings;
import com.allatori.stacktrace.StackTraceRestorer;
import com.allatori.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class StackTrace {

  public static void printUsage() {
    System.out.println("Restore Stack Trace Utility");
    System.out.println("Usage:");
    System.out.println("com.allatori.StackTrace <log file> <input file> <output file>");
  }

  public static String restoreStackTrace(File file, String text)
      throws FileNotFoundException,
          UnsupportedEncodingException,
          IOException,
          ParserConfigurationException,
          SAXException {
    StackTraceMappings stackTraceMappings =
        MappingXmlReader.createStackTraceMappings(
            new InputStreamReader(new FileInputStream(file), "UTF-8"));
    BufferedReader bufferedReader = new BufferedReader(new StringReader(text));
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    StackTraceRestorer.processPrintWriter(bufferedReader, stackTraceMappings, printWriter);
    bufferedReader.close();
    printWriter.close();
    return stringWriter.toString();
  }

  public static void main(String[] texts) {
    BufferedReader bufferedReader;
    System.out.println(ObfuscationCommandSupport.getBanner());
    try {
      if (texts.length != 3) {
        printUsage();
        System.exit(0);
      }
      StackTraceMappings stackTraceMappings = null;
      String text = texts[0];
      String[] texts2;
      label39:
      {
        try {
          stackTraceMappings =
              MappingXmlReader.createStackTraceMappings(
                  new InputStreamReader(new FileInputStream(text), "UTF-8"));
        } catch (FileNotFoundException fileNotFoundException) {
          Log.error("Log file not found: " + fileNotFoundException.getMessage());
          System.exit(0);
          texts2 = texts;
          break label39;
        } catch (Exception exception) {
          Log.error("Error reading log file: " + exception.getMessage());
          System.exit(0);
          texts2 = texts;
          break label39;
        }
        texts2 = texts;
      }
      String text2 = texts2[1];
      String text3 = texts[2];
      BufferedReader bufferedReader2 = null;
      try {
        bufferedReader2 = new BufferedReader(new FileReader(text2));
      } catch (FileNotFoundException fileNotFoundException2) {
        Log.error("Input file not found: " + fileNotFoundException2.getMessage());
        System.exit(0);
      }
      PrintWriter printWriter = null;
      label34:
      {
        try {
          printWriter = new PrintWriter(new BufferedWriter(new FileWriter(text3)));
        } catch (IOException oException) {
          Log.error("Error writing output file: " + oException.getMessage());
          System.exit(0);
          bufferedReader = bufferedReader2;
          break label34;
        }
        bufferedReader = bufferedReader2;
      }
      StackTraceRestorer.processPrintWriter(bufferedReader, stackTraceMappings, printWriter);
      bufferedReader2.close();
      printWriter.close();
    } catch (Exception exception2) {
      System.out.println("Error: " + exception2.getMessage());
    }
  }
}
