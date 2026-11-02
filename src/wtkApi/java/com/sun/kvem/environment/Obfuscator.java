package com.sun.kvem.environment;

import java.io.File;
import java.io.IOException;

/** Compile-only contract of the legacy Sun Wireless Toolkit integration. */
public interface Obfuscator {

  void createScriptFile(File outputDirectory, File inputJar) throws IOException;

  void run(
      File outputJar,
      String projectName,
      String className,
      String inputJar,
      String projectDirectory,
      String classPath,
      String arguments)
      throws Exception;
}
