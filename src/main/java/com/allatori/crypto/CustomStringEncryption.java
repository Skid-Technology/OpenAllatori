package com.allatori.crypto;

import com.allatori.config.Configuration;
import com.allatori.lib.bcel.generic.InstructionFactory;
import com.allatori.lib.bcel.generic.InvokeInstruction;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.util.Log;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CustomStringEncryption {

  public static String text = "allatori-custom-12321-32123:";

  public static String createText(
      String text, ClassLoader classLoader, String text2, String text3) {
    String text4 = text;
    String[] texts = null;
    try {
      int value = Integer.parseInt(text4.substring(text4.indexOf(91) + 1, text4.indexOf(93)));
      text4 = text4.substring(text4.indexOf(93) + 1);
      String text5;
      if ((text5 =
              (String)
                  Class.forName(
                          (texts =
                                  (String[])
                                      Configuration.getObfuscationOptions().getEntries().get(value))
                              [0],
                          true,
                          classLoader)
                      .getDeclaredMethod(texts[1], String.class, String.class, String.class)
                      .invoke(null, text4, text2, text3))
          == null) {
        Log.error(
            "Custom string encryption error: null value is returned by "
                + texts[0]
                + "."
                + texts[1]
                + "()");
        throw new NullPointerException();
      } else {
        return text5;
      }
    } catch (ClassNotFoundException classNotFoundException) {
      Log.error("Cannot load custom string encryption class (class not found): " + texts[0]);
      return text4;
    } catch (NoSuchMethodException noSuchMethodException) {
      Log.error("Cannot find custom string encryption method: " + texts[0] + "." + texts[1] + "()");
      return text4;
    } catch (Exception exception) {
      Log.error("Custom string encryption error: " + exception.getMessage());
      return text4;
    }
  }

  public static InvokeInstruction getInvokeInstruction(
      InstructionFactory instructionFactory, int value) {
    String[] texts = (String[]) Configuration.getObfuscationOptions().getEntries().get(value);
    return instructionFactory.createInvoke(
        texts[2], texts[3], Type.STRING, new Type[] {Type.STRING}, (short) 184);
  }

  public static String createText(String text2, int value, ClassLoader classLoader) {
    String[] texts = (String[]) Configuration.getObfuscationOptions().getEntries().get(value);
    try {
      Class classValue = Class.forName(texts[0], true, classLoader);
      try {
        Method method;
        if (!Modifier.isStatic(
                (method = classValue.getDeclaredMethod(texts[1], String.class)).getModifiers())
            || !Modifier.isPublic(method.getModifiers())) {
          Log.error(
              "Custom string encryption method is not static and public: "
                  + texts[0]
                  + "."
                  + texts[1]
                  + "()");
        }
        String text3;
        if ((text3 = (String) method.invoke(null, text2)) == null) {
          Log.error(
              "Custom string encryption error: null value is returned by "
                  + texts[0]
                  + "."
                  + texts[1]
                  + "()");
          throw new NullPointerException();
        } else {
          return text3;
        }
      } catch (NoSuchMethodException noSuchMethodException) {
        Method method2;
        if (!Modifier.isStatic(
                (method2 =
                        classValue.getDeclaredMethod(
                            texts[1], String.class, String.class, String.class))
                    .getModifiers())
            || !Modifier.isPublic(method2.getModifiers())) {
          Log.error(
              "Custom string encryption method is not static and public: "
                  + texts[0]
                  + "."
                  + texts[1]
                  + "()");
        }
        return "" + text + '[' + value + ']' + text2;
      }
    } catch (ClassNotFoundException classNotFoundException) {
      Log.error("Cannot load custom string encryption class (class not found): " + texts[0]);
      return text2;
    } catch (NoSuchMethodException noSuchMethodException2) {
      Log.error("Cannot find custom string encryption method: " + texts[0] + "." + texts[1] + "()");
      return text2;
    } catch (Exception exception) {
      Log.error("Custom string encryption error: " + exception.getMessage());
      return text2;
    }
  }
}
