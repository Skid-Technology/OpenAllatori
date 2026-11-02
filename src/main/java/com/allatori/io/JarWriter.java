package com.allatori.io;

import com.allatori.config.Configuration;
import com.allatori.config.InputJar;
import com.allatori.lib.asm.ClassReader;
import com.allatori.lib.bcel.classfile.ClassParser;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.util.Log;
import com.allatori.util.StreamUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.zip.CRC32;
import java.util.zip.ZipException;
import java.util.zip.ZipOutputStream;

public class JarWriter {

  public final String text;

  public Hashtable entries;

  public boolean enabled;

  public ClassStorage classStorage;

  public Vector values;

  public HashSet values2;

  public JarWriter() {
    super();
    this.text = ".tmp";
    this.values = new Vector();
    this.values2 = new HashSet();
    this.enabled = false;
  }

  public String createText(JarFile jarFile, JarEntry jarEntry) {
    try {
      InputStream inputStream = jarFile.getInputStream(jarEntry);
      ClassGen classGen = new ClassGen(new ClassParser(inputStream, jarEntry.getName()).parse());
      inputStream.close();
      return jarEntry
          .getName()
          .substring(0, jarEntry.getName().length() - classGen.getClassName().length() - 6);
    } catch (Exception exception) {
      return "";
    }
  }

  public void processText(String[] texts, String text) throws FileNotFoundException, IOException {
    byte[] values = new byte[262144];
    JarOutputStream jarOutputStream =
        new JarOutputStream(new FileOutputStream(("" + text + ".tmp")));
    if (Configuration.getOutputOptions().getCompressionLevel() != -1) {
      jarOutputStream.setLevel(Configuration.getOutputOptions().getCompressionLevel());
    }
    Hashtable hashtable = new Hashtable();
    int value;
    for (int value2 = value = 0; value2 < texts.length; value2 = value) {
      JarFile jarFile;
      Enumeration enumeration;
      Enumeration enumeration2 = enumeration = (jarFile = new JarFile(texts[value])).entries();
      while (enumeration2.hasMoreElements()) {
        JarEntry jarEntry = (JarEntry) enumeration.nextElement();
        if (hashtable.containsKey(jarEntry.getName())) {
          enumeration2 = enumeration;
        } else {
          hashtable.put(jarEntry.getName(), "");
          InputStream inputStream = jarFile.getInputStream(jarEntry);
          JarEntry jarEntry2 = new JarEntry(jarEntry.getName());
          jarOutputStream.putNextEntry(jarEntry2);
          InputStream inputStream2 = inputStream;
          int value3;
          while ((value3 = inputStream2.read(values)) > 0) {
            inputStream2 = inputStream;
            jarOutputStream.write(values, 0, value3);
          }
          jarOutputStream.closeEntry();
          enumeration2 = enumeration;
        }
      }
      JarFile jarFile2 = jarFile;
      value++;
      jarFile2.close();
    }
    if (Configuration.getObfuscationOptions().isOutputJarCommentEnabled()
        && !Configuration.getObfuscationOptions().isEnabled()) {
      this.processJarOutputStream(jarOutputStream);
    }
    jarOutputStream.finish();
    jarOutputStream.close();
    this.processText(text + ".tmp", text);
  }

  public static HashSet getValues(JarWriter jarWriter) {
    return jarWriter.values2;
  }

  public void processJarOutputStream(String text, JarOutputStream jarOutputStream)
      throws IOException {
    String text2 = "";
    label17:
    while (true) {
      String text3 = text;
      while (text3.indexOf(47) > 0) {
        text2 = "" + text2 + text.substring(0, text.indexOf(47) + 1);
        text = text.substring(text.indexOf(47) + 1);
        if (this.entries.containsKey(text2)) {
          continue label17;
        }
        text3 = text;
        this.entries.put(text2, ".tmp");
        jarOutputStream.putNextEntry(new JarEntry(text2));
        jarOutputStream.closeEntry();
      }
      return;
    }
  }

  public void processText(
      Vector vector, JarOutputStream jarOutputStream, ClassStorage classStorage, String text)
      throws IOException {
    int value;
    for (int value2 = value = vector.size() - 1; value2 >= 0; value2 = value) {
      ClassGen classGen = (ClassGen) vector.get(value);
      value--;
      this.processText(classGen, jarOutputStream, classStorage, text);
    }
  }

  public void processJarOutputStream(JarOutputStream jarOutputStream) {
    if (Configuration.getObfuscationOptions().getOutputJarComment() != null) {
      jarOutputStream.setComment(Configuration.getObfuscationOptions().getOutputJarComment());
    }
  }

  public void processClassStorage(String text3, String text4, ClassStorage classStorage2)
      throws IOException, FileNotFoundException, ZipException {
    byte value;
    this.classStorage = classStorage2;
    this.entries = new Hashtable();
    byte[] values2 = new byte[262144];
    String text5 = "";
    JarFile jarFile = new JarFile(text3);
    JarOutputStream jarOutputStream =
        new JarOutputStream(new FileOutputStream(("" + text4 + ".tmp")));
    HashSet values3 = new HashSet();
    if (Configuration.getOutputOptions().isKeepDuplicateEntries()) {
      try {
        Field field;
        if ((field = ZipOutputStream.class.getDeclaredField("names")) != null) {
          field.setAccessible(true);
          values3 = (HashSet) field.get(jarOutputStream);
        }
      } catch (RuntimeException runtimeException) {
        if (runtimeException.getClass().getName().endsWith("InaccessibleObjectException")) {
          Log.warn(
              "Use '--add-opens java.base/java.util.zip=ALL-UNNAMED' Java launcher option to keep"
                  + " output jar duplicate entries");
        }
      } catch (Exception exception) {
      }
    }
    if (Configuration.getOutputOptions().getCompressionLevel() != -1) {
      jarOutputStream.setLevel(Configuration.getOutputOptions().getCompressionLevel());
    }
    Vector vector = new Vector();
    Enumeration enumeration = jarFile.entries();
    while (true) {
      Enumeration enumeration2 = enumeration;
      JarEntry jarEntry;
      InputStream inputStream;
      JarEntry jarEntry2;
      while (true) {
        if (!enumeration2.hasMoreElements()) {
          this.processText(vector, jarOutputStream, classStorage2, text3);
          this.processText(classStorage2.getInputClasses(), jarOutputStream, classStorage2, text3);
          jarFile.close();
          if (Configuration.getObfuscationOptions().isOutputJarCommentEnabled()
              && !Configuration.getObfuscationOptions().isEnabled()) {
            this.processJarOutputStream(jarOutputStream);
          }
          jarOutputStream.finish();
          jarOutputStream.close();
          this.processText("" + text4 + ".tmp", text4);
          return;
        }
        if ((jarEntry = (JarEntry) enumeration.nextElement()).isDirectory()) {
          enumeration2 = enumeration;
        } else {
          String text6;
          if (jarEntry.getName().endsWith(".class")
              && (text6 = classStorage2.getText("" + text3 + '#' + jarEntry.getName())) != null) {
            text5 = classStorage2.getText2("" + text3 + '#' + text6);
            ClassGen classGen;
            if ((classGen = classStorage2.getClassGen(text6)) != null
                && !Configuration.getIgnoreClassesOptions()
                    .matchesClassGen(classStorage2, classGen)) {
              enumeration2 = enumeration;
              vector.add(classGen);
              continue;
            }
          }
          if (jarEntry.getName().equals("META-INF/INDEX.LIST")) {
            enumeration2 = enumeration;
            this.values.add(text4);
          } else {
            inputStream = jarFile.getInputStream(jarEntry);
            String name = jarEntry.getName();
            if (Configuration.getRenamingOptions()
                .getResourceRenamingOptions()
                .isUpdateResourceNamesEnabled()) {
              if (text5 == null && text3.endsWith(".war")) {
                text5 = "WEB-INF/classes/";
              }
              if (jarEntry.getName().startsWith("BOOT-INF/classes/")) {
                text5 = "BOOT-INF/classes/";
              }
              if (jarEntry.getName().startsWith("WEB-INF/classes/")) {
                text5 = "WEB-INF/classes/";
              }
              name = StreamUtils.createText(text5, jarEntry.getName());
            }
            ClassGen classGen2;
            if (Configuration.getLogOptions().isText()
                && "com/license4j/resources/d.bfi".equals(jarEntry.getName())
                && (classGen2 = classStorage2.getClassGen("com.license4j.HardwareID")) != null) {
              String text7;
              if ((text7 = classGen2.getClassName()).indexOf(46) > 0) {
                String text8 = text7.substring(0, text7.lastIndexOf(46) + 1).replace('.', '/');
                name = "" + text8 + Configuration.getLogOptions().getComLicense4jSupport();
              } else {
                name = Configuration.getLogOptions().getComLicense4jSupport();
              }
            }
            jarEntry2 = new JarEntry(name);
            if (!name.endsWith(".jar")) {
              break;
            }
            byte value2 = 0;
            Iterator iterator2 = Configuration.getInputOptions().getValues().iterator();
            while (true) {
              if (!iterator2.hasNext()) {
                value = value2;
                break;
              }
              InputJar inputJar;
              if ((inputJar = (InputJar) iterator2.next()).text.equals(text3)
                  && inputJar.text2.equals(jarEntry.getName())) {
                if (jarEntry.getMethod() == 0) {
                  jarEntry2.setMethod(0);
                }
                File file = new File(inputJar.text);
                jarEntry2.setSize(file.length());
                FileInputStream fileInputStream = new FileInputStream(file);
                CRC32 cRC32 = new CRC32();
                FileInputStream fileInputStream2 = fileInputStream;
                int value3;
                while ((value3 = fileInputStream2.read(values2)) > 0) {
                  fileInputStream2 = fileInputStream;
                  cRC32.update(values2, 0, value3);
                }
                jarEntry2.setCrc(cRC32.getValue());
                fileInputStream.close();
                FileInputStream fileInputStream3 = new FileInputStream(file);
                this.processJarOutputStream(jarEntry2.getName(), jarOutputStream);
                try {
                  jarOutputStream.putNextEntry(jarEntry2);
                  if (Configuration.getOutputOptions().isKeepDuplicateEntries()) {
                    values3.clear();
                  }
                  FileInputStream fileInputStream4 = fileInputStream3;
                  int value4;
                  while ((value4 = fileInputStream4.read(values2)) > 0) {
                    fileInputStream4 = fileInputStream3;
                    jarOutputStream.write(values2, 0, value4);
                  }
                  fileInputStream3.close();
                  jarOutputStream.closeEntry();
                } catch (ZipException zipException) {
                  if (!zipException.getMessage().contains("duplicate")) {
                    throw zipException;
                  }
                }
                value = value2 = 1;
                break;
              }
            }
            if (value == 0 && jarEntry.getMethod() == 0) {
              jarEntry2.setMethod(0);
              jarEntry2.setSize(jarEntry.getSize());
              jarEntry2.setCrc(jarEntry.getCrc());
            }
            if (value2 == 0) {
              break;
            }
            enumeration2 = enumeration;
          }
        }
      }
      this.processJarOutputStream(jarEntry2.getName(), jarOutputStream);
      try {
        jarOutputStream.putNextEntry(jarEntry2);
        if (Configuration.getOutputOptions().isKeepDuplicateEntries()) {
          values3.clear();
        }
        boolean matches =
            Configuration.getRenamingOptions()
                .getResourceRenamingOptions()
                .matchesText(jarEntry.getName());
        InputStream inputStream2 = inputStream;
        JarOutputStream jarOutputStream2;
        if (matches) {
          StreamUtils.processText(
              inputStream,
              jarOutputStream,
              Configuration.getRenamingOptions()
                  .getResourceRenamingOptions()
                  .getText(jarEntry.getName()));
          jarOutputStream2 = jarOutputStream;
        } else {
          int value5;
          while ((value5 = inputStream2.read(values2)) > 0) {
            inputStream2 = inputStream;
            jarOutputStream.write(values2, 0, value5);
          }
          jarOutputStream2 = jarOutputStream;
        }
        jarOutputStream2.closeEntry();
      } catch (ZipException zipException2) {
        if (!zipException2.getMessage().contains("duplicate")) {
          throw zipException2;
        }
      }
    }
  }

  public static boolean matchesEnabled(JarWriter jarWriter, boolean matches) {
    return jarWriter.enabled = matches;
  }

  public static boolean matchesJarWriter(JarWriter jarWriter) {
    return jarWriter.enabled;
  }

  public void processText(String text, String text2) {
    File file = new File(text);
    File file2;
    if ((file2 = new File(text2)).exists() && !file2.delete()) {
      Log.warn("Cannot delete '" + file2.getPath() + "'");
    }
    if (!file.renameTo(file2)) {
      Log.warn("Cannot rename '" + file.getPath() + "' to '" + file2.getPath() + "'");
      Log.warn("Resulting file is '" + file.getPath() + "'");
    }
  }

  public void update() {
    int value;
    for (int value2 = value = 0; value2 < this.values.size(); value2 = value) {
      String text = (String) this.values.get(value);
      value++;
      this.processText(text);
    }
  }

  public void processText(String text) {
    try {
      File file = new File(text);
      Runtime.getRuntime().exec("jar -i " + file.getName(), null, file.getParentFile());
    } catch (Exception exception) {
    }
  }

  public void processText(
      ClassGen classGen, JarOutputStream jarOutputStream, ClassStorage classStorage, String text)
      throws IOException {
    String text2;
    String text3 = text2 = classGen.getClassName();
    if (Configuration.getMappingOptions().lookupTable != null) {
      text3 = Configuration.getMappingOptions().lookupTable.getText(text2);
    }
    String text4;
    if ((text4 = classStorage.getText2("" + text + '#' + text3)) == null) {
      text4 = "";
    }
    String text5 = "" + text4 + text2.replace('.', '/') + ".class";
    JarEntry jarEntry = new JarEntry(text5);
    this.processJarOutputStream(text5, jarOutputStream);
    try {
      jarOutputStream.putNextEntry(jarEntry);
      JarOutputStream jarOutputStream2;
      if (classGen.getMajor() > 50) {
        ResolvingClassLoader resolvingClassLoader =
            new ResolvingClassLoader(classStorage, Configuration.getMappingOptions().lookupTable);
        ClassReader classReader = new ClassReader(classGen.getJavaClass().getBytes());
        JarClassWriter jarClassWriter = new JarClassWriter(this, 2, resolvingClassLoader);
        jarOutputStream2 = jarOutputStream;
        classReader.accept(jarClassWriter, 4);
        jarOutputStream.write(jarClassWriter.toByteArray());
      } else {
        jarOutputStream2 = jarOutputStream;
        jarOutputStream.write(classGen.getJavaClass().getBytes());
      }
      jarOutputStream2.closeEntry();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
