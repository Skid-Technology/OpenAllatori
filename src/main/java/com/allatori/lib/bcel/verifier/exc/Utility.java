package com.allatori.lib.bcel.verifier.exc;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class Utility {

  public static String getStackTrace(Throwable throwable) {
    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    throwable.printStackTrace(printWriter);
    return stringWriter.toString();
  }
}
