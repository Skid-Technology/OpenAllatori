package com.allatori.stacktrace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackTraceRestorer {

  public static String getText(String text, StackTraceMappings stackTraceMappings) {
    String text2 = text;
    Matcher matcher2 = Pattern.compile("L(.*?);").matcher(text2);
    while (matcher2.find()) {
      String text3;
      String text4 = (text3 = matcher2.group(1)).replace('/', '.');
      if (stackTraceMappings.lookupTable2.matchesText(text4)) {
        text2 =
            text2.replace(text3, stackTraceMappings.lookupTable2.getText(text4).replace('.', '/'));
      }
    }
    return text2;
  }

  public static void processPrintWriter(
      BufferedReader bufferedReader, StackTraceMappings stackTraceMappings, PrintWriter printWriter)
      throws IOException {
    PrintWriter printWriter2;
    PrintWriter printWriter3;
    Pattern pattern = Pattern.compile("(.*(?:at|Frame)\\s+)(.*)\\((.*)\\)(.*)");
    Pattern pattern2 = Pattern.compile("(.*syslog:\\s+)(.*?)\\((.*)\\)(.*)");
    Pattern pattern3 = Pattern.compile("(\\s*)(.*?)\\((.*)\\)(.*)");
    Pattern pattern4 =
        Pattern.compile(
            "(.*?\\s+)((?:\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*\\.)+\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*):(\\d+)(\\s+.*)");
    Pattern pattern5 = Pattern.compile("(.*lock.*a\\s+)(.*?)(\\).*)");
    String text = null;
    String text2 = null;
    String text3 = null;
    String text4 = null;
    BufferedReader bufferedReader2 = bufferedReader;
    String text5;
    while ((text5 = bufferedReader2.readLine()) != null) {
      byte value = 0;
      Matcher matcher2;
      if ((matcher2 = pattern.matcher(text5)).matches()) {
        value = 1;
        text = matcher2.group(1);
        if ((text2 = matcher2.group(2)).indexOf(40) > 0) {
          text2 = text2.substring(0, text2.indexOf(40));
        }
        text3 = matcher2.group(3);
        text4 = matcher2.group(4);
      }
      Matcher matcher3;
      if (value == 0 && (matcher3 = pattern2.matcher(text5)).matches()) {
        value = 1;
        text = matcher3.group(1);
        text2 = matcher3.group(2);
        text3 = matcher3.group(3);
        text4 = matcher3.group(4);
      }
      Matcher matcher4;
      if (value == 0 && (matcher4 = pattern3.matcher(text5)).matches()) {
        value = 1;
        text = matcher4.group(1);
        text2 = matcher4.group(2);
        text3 = matcher4.group(3);
        text4 = matcher4.group(4);
      }
      Matcher matcher5;
      if (value == 0 && (matcher5 = pattern4.matcher(text5)).matches()) {
        text = matcher5.group(1);
        String text6 = matcher5.group(2);
        text3 = matcher5.group(3);
        text4 = matcher5.group(4);
        if (stackTraceMappings.lookupTable2.matchesText(text6)) {
          label96:
          {
            try {
              text5 =
                  ""
                      + text
                      + stackTraceMappings.lookupTable2.getText(text6)
                      + ":"
                      + stackTraceMappings.values.indexOf(Integer.valueOf(text3))
                      + text4;
            } catch (Exception exception) {
              printWriter3 = printWriter;
              break label96;
            }
            printWriter3 = printWriter;
          }
          printWriter3.println(text5);
          bufferedReader2 = bufferedReader;
          continue;
        }
      }
      label89:
      {
        if (value != 0) {
          if (stackTraceMappings.matchesText(text2)) {
            byte value2 = 0;
            if (text3.startsWith("(Compiled Method)")) {
              value2 = 1;
              text3 = text3.substring("(Compiled Method)".length());
            }
            int value3 = -1;
            StackTraceMappings stackTraceMappings2;
            if (text3.indexOf(58) >= 0) {
              String text7 = text3.substring(0, text3.indexOf(58));
              String text8 = text3.substring(text3.indexOf(58) + 1);
              if (stackTraceMappings.lookupTable.matchesText(text7)) {
                text7 = stackTraceMappings.lookupTable.getText(text7);
              }
              try {
                if ((value3 = stackTraceMappings.values.indexOf(Integer.valueOf(text8))) >= 0) {
                  text8 = "" + value3;
                }
              } catch (NumberFormatException numberFormatException) {
              }
              text3 = "" + text7 + ":" + text8;
              stackTraceMappings2 = stackTraceMappings;
            } else {
              if (stackTraceMappings.lookupTable.matchesText(text3)) {
                text3 = stackTraceMappings.lookupTable.getText(text3);
              }
              stackTraceMappings2 = stackTraceMappings;
            }
            String text9;
            if ((text9 = stackTraceMappings2.getText(text2, value3)) == null) {
              text9 = stackTraceMappings.getText(text2);
            }
            text5 =
                ""
                    + text
                    + text9
                    + (value2 != 0 ? "(Compiled Method)" : "")
                    + "("
                    + text3
                    + ")"
                    + text4;
            printWriter2 = printWriter;
            break label89;
          }
        } else {
          Matcher matcher6;
          if ((matcher6 = pattern5.matcher(text5)).matches()
              && stackTraceMappings.lookupTable2.matchesText(matcher6.group(2))) {
            text5 =
                ""
                    + matcher6.group(1)
                    + stackTraceMappings.lookupTable2.getText(matcher6.group(2))
                    + matcher6.group(3);
          }
        }
        printWriter2 = printWriter;
      }
      printWriter2.println(text5);
      bufferedReader2 = bufferedReader;
    }
  }

  public static void processPrintWriter2(
      BufferedReader bufferedReader, StackTraceMappings stackTraceMappings, PrintWriter printWriter)
      throws IOException {
    StackTraceMappings stackTraceMappings2;
    int value;
    StringBuilder stringBuilder = new StringBuilder();
    BufferedReader bufferedReader2 = bufferedReader;
    String text;
    while ((text = bufferedReader2.readLine()) != null) {
      int value2 = 0;
      int size = text.length();
      stringBuilder.setLength(0);
      do {
        int value3 = -1;
        int value4 = -1;
        int value5 = -1;
        int value6 = -1;
        int value7 = value2;
        int value8 = value2;
        while (value8 < size && !Character.isJavaIdentifierStart(text.charAt(value2))) {
          value8 = ++value2;
        }
        int value9 = value2;
        int value10 = value2;
        while (value10 < size
            && (Character.isJavaIdentifierPart(text.charAt(value2))
                || text.charAt(value2) == '.'
                || text.charAt(value2) == '-'
                || text.charAt(value2) == '<'
                || text.charAt(value2) == '>')) {
          value10 = ++value2;
        }
        int value11 = value2;
        int value12 = value2;
        while (value12 < size && text.charAt(value2) == ' ') {
          value12 = ++value2;
        }
        label210:
        {
          if (value2 < size && text.charAt(value2) == '(') {
            value6 = ++value2;
            int value13 = value2;
            while (value13 < size && Character.isLetter(text.charAt(value2))) {
              value13 = ++value2;
            }
            value5 = value2;
            if (value2 < size && text.charAt(value2) == ':') {
              value4 = ++value2;
              int value14 = value2;
              while (value14 < size && Character.isDigit(text.charAt(value2))) {
                value14 = ++value2;
              }
              value3 = value2;
              value = value11;
              break label210;
            }
          } else if (value2 < size && text.charAt(value2) == ':') {
            value4 = ++value2;
            int value15 = value2;
            while (value15 < size && Character.isDigit(text.charAt(value2))) {
              value15 = ++value2;
            }
            value3 = value2;
          }
          value = value11;
        }
        if (value <= value9) {
          stringBuilder.append(text.substring(value7, value2));
        } else {
          String text2 = text.substring(value9, value11);
          if (stackTraceMappings.matchesText(text2)) {
            stringBuilder.append(text.substring(value7, value9));
            int value16;
            if (value3 > value4) {
              String text3 = text.substring(value4, value3);
              int value17 = -1;
              label174:
              {
                try {
                  value17 = stackTraceMappings.values.indexOf(Integer.valueOf(text3));
                } catch (NumberFormatException numberFormatException) {
                  stackTraceMappings2 = stackTraceMappings;
                  break label174;
                }
                stackTraceMappings2 = stackTraceMappings;
              }
              String text4;
              if ((text4 = stackTraceMappings2.getText(text2, value17)) == null) {
                text4 = stackTraceMappings.getText(text2);
              }
              stringBuilder.append(text4);
              value16 = value5;
            } else {
              stringBuilder.append(stackTraceMappings.getText(text2));
              value16 = value5;
            }
            if (value16 > value6) {
              String text5 = text.substring(value6, value5);
              stringBuilder.append(text.substring(value11, value6));
              if (stackTraceMappings.lookupTable.matchesText(text5)) {
                text5 = stackTraceMappings.lookupTable.getText(text5);
              }
              stringBuilder.append(text5);
              if (value3 <= value4) {
                stringBuilder.append(text.substring(value5, value2));
              }
            }
            if (value3 > value4) {
              String text6 = text.substring(value4, value3);
              try {
                int value18;
                if ((value18 = stackTraceMappings.values.indexOf(Integer.valueOf(text6))) >= 0) {
                  text6 = "" + value18;
                }
              } catch (NumberFormatException numberFormatException2) {
              }
              StringBuilder stringBuilder2;
              if (value5 > value6) {
                stringBuilder2 = stringBuilder;
                stringBuilder.append(text.substring(value5, value4));
              } else {
                stringBuilder2 = stringBuilder;
                stringBuilder.append(text.substring(value11, value4));
              }
              stringBuilder2.append(text6);
              stringBuilder.append(text.substring(value3, value2));
            }
            if (value5 <= value6 && value3 <= value4) {
              stringBuilder.append(text.substring(value11, value2));
            }
          } else if ((text2.indexOf(46) > 0 || text2.length() > 3)
              && stackTraceMappings.lookupTable2.matchesText(text2)) {
            stringBuilder.append(text.substring(value7, value9));
            stringBuilder.append(stackTraceMappings.lookupTable2.getText(text2));
            stringBuilder.append(text.substring(value11, value2));
          } else {
            stringBuilder.append(text.substring(value7, value2));
          }
        }
      } while (value2 < size);
      bufferedReader2 = bufferedReader;
      printWriter.println(getText(stringBuilder.toString(), stackTraceMappings));
    }
  }
}
