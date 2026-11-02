package com.allatori.lib.asm.signature;

public class SignatureReader {

  public final String signatureValue;

  public static int parseType(String text, int value, SignatureVisitor signatureVisitor) {
    char value2;
    int value3 = value;
    char value4;
    char value5 = value4 = text.charAt(value3);
    value3++;
    switch (value5) {
      case 'B':
      case 'C':
      case 'D':
      case 'F':
      case 'I':
      case 'J':
      case 'S':
      case 'V':
      case 'Z':
        signatureVisitor.visitBaseType(value4);
        return value3;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'U':
      case 'W':
      case 'X':
      case 'Y':
      default:
        throw new IllegalArgumentException();
      case 'L':
        int value6 = value3;
        byte value7 = 0;
        byte value8 = 0;
        while (true) {
          String text2 = text;
          while (true) {
            char value9;
            char value10 = value9 = text2.charAt(value3);
            value3++;
            if (value10 != '.' && value9 != ';') {
              if (value9 == '<') {
                String text3 = text.substring(value6, value3 - 1);
                if (value8 != 0) {
                  signatureVisitor.processText2(text3);
                } else {
                  signatureVisitor.processText(text3);
                }
                value7 = 1;
                text2 = text;
                char value11;
                while ((value11 = text2.charAt(value3)) != '>') {
                  switch (value11) {
                    case '*':
                      value3++;
                      text2 = text;
                      signatureVisitor.update2();
                      break;
                    case '+':
                    case '-':
                      text2 = text;
                      value3 =
                          parseType(text, value3 + 1, signatureVisitor.visitTypeArgument(value11));
                      break;
                    case ',':
                    default:
                      text2 = text;
                      value3 = parseType(text, value3, signatureVisitor.visitTypeArgument('='));
                  }
                }
              }
              break;
            }
            label45:
            {
              if (value7 == 0) {
                String text4 = text.substring(value6, value3 - 1);
                if (value8 != 0) {
                  signatureVisitor.processText2(text4);
                  value2 = value9;
                  break label45;
                }
                signatureVisitor.processText(text4);
              }
              value2 = value9;
            }
            if (value2 == ';') {
              signatureVisitor.update();
              return value3;
            }
            value6 = value3;
            value7 = 0;
            value8 = 1;
            text2 = text;
          }
        }
      case 'T':
        int value12;
        int value13 = value12 = text.indexOf(59, value3);
        signatureVisitor.processText3(text.substring(value3, value12));
        return value13 + 1;
      case '[':
        return parseType(text, value3, signatureVisitor.getSignatureVisitor7());
    }
  }

  public void acceptType(SignatureVisitor signatureVisitor) {
    parseType(this.signatureValue, 0, signatureVisitor);
  }

  public void accept(SignatureVisitor signatureVisitor) {
    String text;
    String text2 = text = this.signatureValue;
    int size = this.signatureValue.length();
    int value;
    if (text2.charAt(0) == '<') {
      value = 2;
      label59:
      while (true) {
        int value2 = text.indexOf(58, value);
        signatureVisitor.processText4(text.substring(value - 1, value2));
        char value3;
        if ((value3 = text.charAt(value = value2 + 1)) == 'L' || value3 == '[' || value3 == 'T') {
          value = parseType(text, value, signatureVisitor.getSignatureVisitor5());
        }
        text2 = text;
        while (true) {
          char value4;
          char value5 = value4 = text2.charAt(value);
          value++;
          if (value5 != ':') {
            if (value4 == '>') {
              text2 = text;
              break label59;
            }
            break;
          }
          text2 = text;
          value = parseType(text, value, signatureVisitor.getSignatureVisitor6());
        }
      }
    } else {
      value = 0;
      text2 = text;
    }
    if (text2.charAt(value) == '(') {
      String text3 = text;
      value++;
      for (String text4 = text3;
          text4.charAt(value) != ')';
          value = parseType(text, value, signatureVisitor.getSignatureVisitor())) {
        text4 = text;
      }
      int value6;
      int value7 = value6 = parseType(text, value + 1, signatureVisitor.getSignatureVisitor8());
      while (value7 < size) {
        value7 = value6 = parseType(text, value6 + 1, signatureVisitor.getSignatureVisitor4());
      }
    } else {
      int value8;
      int value9 = value8 = parseType(text, value, signatureVisitor.getSignatureVisitor3());
      while (value9 < size) {
        value9 = value8 = parseType(text, value8, signatureVisitor.getSignatureVisitor2());
      }
    }
  }

  public SignatureReader(String signatureValue) {
    super();
    this.signatureValue = signatureValue;
  }
}
