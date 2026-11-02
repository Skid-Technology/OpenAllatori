package com.allatori.naming;

import com.allatori.VersionInfo;
import com.allatori.config.Configuration;
import com.allatori.model.NameMapping;
import com.allatori.optimizer.OptimizationStatistics;
import com.allatori.util.Log;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Vector;

public class MappingXmlWriter {

  public static StringBuilder stringBuilder = new StringBuilder(4096);

  public static void processPrintWriter(PrintWriter printWriter) {
    printWriter.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
    printWriter.println(
        "<!--\n   "
            + VersionInfo.getProductName()
            + " "
            + VersionInfo.getVersion()
            + "\n"
            + "   Web: "
            + VersionInfo.getWebsite()
            + "\n\n"
            + "   Log created: "
            + Calendar.getInstance().getTime()
            + "\n"
            + "-->\n");
    if (stringBuilder.length() > 0) {
      printWriter.println(
          new StringBuilder().insert(0, "<!--\n").append(stringBuilder).append("-->\n").toString());
    }
    printWriter.println(
        "<!--\n"
            + RenameStatistics.count3
            + " classes renamed\n"
            + RenameStatistics.count
            + " methods renamed\n"
            + RenameStatistics.count2
            + " fields renamed\n\n"
            + (OptimizationStatistics.count6
                + OptimizationStatistics.count4
                + OptimizationStatistics.count16
                + OptimizationStatistics.count24)
            + " local variables optimized:\n"
            + "   "
            + OptimizationStatistics.count4
            + " merged and removed\n"
            + "   "
            + OptimizationStatistics.count6
            + " removed (unused)\n"
            + "   "
            + OptimizationStatistics.count16
            + " removed (assigned a value but never used)\n"
            + "   "
            + OptimizationStatistics.count24
            + " usage optimized\n"
            + "-->\n");
    printWriter.println("<allatori>");
  }

  public static int calculateCount(Vector vector, String text) {
    int value = 0;
    int value2 = vector.size() - 1;
    while (value <= value2) {
      int value3 = value + value2 >> 1;
      int value4;
      if ((value4 = ((Comparable) vector.get(value3)).compareTo(text)) < 0) {
        value = value3 + 1;
      } else {
        if (value4 <= 0) {
          return value3;
        }
        value2 = value3 - 1;
      }
    }
    return value;
  }

  public static void processRenamingLog(PrintWriter printWriter, RenamingLog renamingLog) {
    printWriter.println(" <mapping>");
    Vector vector = new Vector(renamingLog.lookupTable4.getEntries2());
    Vector vector2 = new Vector(renamingLog.lookupTable5.getEntries2());
    Vector vector3 = new Vector(renamingLog.lookupTable2.getEntries2());
    Collections.sort(vector2);
    Collections.sort(vector3);
    String text3 = "\" new=\"";
    String text4 = "\"/>";
    String text5 = "<init>";
    String text6 = "<clinit>";
    String text7 = "   <method old=\"";
    int value;
    for (int value2 = value = vector.size() - 1; value2 >= 0; value2 = value) {
      String text8 = (String) vector.get(value);
      printWriter.println(
          (String) "  <class old=\""
              + text8
              + text3
              + renamingLog.lookupTable4.getText(text8)
              + (String) "\">");
      String text9 = "" + text8 + "&";
      int value3;
      value2 = value3 = calculateCount(vector2, text9);
      String text10;
      while (value2 < vector2.size() && (text10 = (String) vector2.get(value3)).startsWith(text9)) {
        String text11 = text10.substring(text10.indexOf("&") + 1, text10.lastIndexOf("&"));
        if (!text5.equals(text11) && !text6.equals(text11)) {
          String text12 = text10.substring(text10.lastIndexOf("&") + 1);
          Vector vector4;
          if (renamingLog.lookupTable3.matchesText(text10)) {
            vector4 = vector2;
            printWriter.println(
                ""
                    + text7
                    + text11
                    + text12
                    + text3
                    + renamingLog.lookupTable5.getText(text10)
                    + renamingLog.lookupTable3.getText(text10)
                    + text4);
            renamingLog.lookupTable3.processText(text10);
          } else {
            printWriter.println(
                ""
                    + text7
                    + text11
                    + text12
                    + text3
                    + renamingLog.lookupTable5.getText(text10)
                    + text4);
            vector4 = vector2;
          }
          vector4.remove(value3);
          value2 = value3;
        } else {
          vector2.remove(value3);
          value2 = value3;
        }
      }
      int value4;
      value2 = value4 = calculateCount(vector3, text9);
      String text13;
      while (value2 < vector3.size() && (text13 = (String) vector3.get(value4)).startsWith(text9)) {
        String text14 = text13.substring(text13.indexOf("&") + 1, text13.lastIndexOf("&"));
        String text15 = text13.substring(text13.lastIndexOf("&") + 1);
        value2 = value4;
        printWriter.println(
            ""
                + ((String) "   <field old=\"")
                + text14
                + ((String) " ")
                + text15
                + text3
                + renamingLog.lookupTable2.getText(text13)
                + text4);
        vector3.remove(value4);
      }
      value--;
      printWriter.println((String) "  </class>");
    }
    Vector vector5;
    if ((vector5 = new Vector(renamingLog.lookupTable.getEntries2())).size() > 0) {
      printWriter.println((String) "  <annotations>");
      int value5;
      for (int value6 = value5 = vector5.size() - 1; value6 >= 0; value6 = --value5) {
        String text16 = (String) vector5.get(value5);
        String text17 = renamingLog.lookupTable.getText(text16);
        String text18 = text16.substring(text16.indexOf("&") + 1);
        String text19 = text16.substring(0, text16.indexOf("&"));
        if (!text5.equals(text18) && !text6.equals(text18)) {
          printWriter.println(
              ""
                  + ((String) "   <annotation oldClassName=\"")
                  + text19
                  + ((String) "\" oldMethodName=\"")
                  + text18
                  + ((String) "\" newMethodName=\"")
                  + text17
                  + text4);
        }
      }
      printWriter.println((String) "  </annotations>");
    }
    printWriter.println("  <sources>");
    int value7;
    for (int value8 = value7 = renamingLog.values2.size() - 1; value8 >= 0; value8 = value7) {
      NameMapping nameMapping = (NameMapping) renamingLog.values2.get(value7);
      StringBuilder stringBuilder =
          new StringBuilder()
              .insert(0, (String) "   <source old=\"")
              .append(nameMapping.text)
              .append(text3)
              .append(nameMapping.text2);
      value7--;
      printWriter.println(stringBuilder.append(text4).toString());
    }
    printWriter.println("  </sources>");
    printWriter.println("  <lines>");
    int value9;
    for (int value10 = value9 = 0; value10 < renamingLog.values.size(); value10 = value9) {
      StringBuilder stringBuilder2 =
          new StringBuilder()
              .insert(0, (String) "   <line l=\"")
              .append(renamingLog.values.get(value9));
      value9++;
      printWriter.println(stringBuilder2.append(text4).toString());
    }
    printWriter.println("  </lines>");
    printWriter.println(" </mapping>");
  }

  public static void update() {
    String text;
    if ((text = Configuration.getOutputOptions().getLogFile()) == null) {
      Log.warn("Log file is not set.");
      stringBuilder.delete(0, stringBuilder.length());
    } else {
      PrintWriter printWriter = null;
      label90:
      {
        label91:
        {
          try {
            try {
              FileOutputStream fileOutputStream = new FileOutputStream(text);
              PrintWriter printWriter2 =
                  printWriter =
                      new PrintWriter(
                          new BufferedWriter(
                              new OutputStreamWriter(fileOutputStream, "UTF-8"), 524288));
              processPrintWriter(printWriter2);
              processRenamingLog(printWriter2, Configuration.getMappingOptions().renamingLog);
              processPrintWriter2(printWriter2);
              printWriter2.flush();
              break label90;
            } catch (FileNotFoundException fileNotFoundException) {
              Log.error("Cannot write log to '" + text + "' file.");
            } catch (Exception exception) {
              Log.error("Writing log file failed with exception: " + exception.getMessage());
              break label91;
            }
          } catch (Throwable throwable) {
            stringBuilder.delete(0, stringBuilder.length());
            try {
              printWriter.close();
            } catch (Exception exception2) {
              throw throwable;
            }
            throw throwable;
          }
          stringBuilder.delete(0, stringBuilder.length());
          try {
            printWriter.close();
            return;
          } catch (Exception exception3) {
            return;
          }
        }
        stringBuilder.delete(0, stringBuilder.length());
        try {
          printWriter.close();
          return;
        } catch (Exception exception4) {
          return;
        }
      }
      stringBuilder.delete(0, stringBuilder.length());
      try {
        printWriter.close();
      } catch (Exception exception5) {
      }
    }
  }

  public static void processPrintWriter2(PrintWriter printWriter) {
    printWriter.println("</allatori>");
  }
}
