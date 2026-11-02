package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.classfile.ClassParser;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.Utility;
import java.io.ByteArrayInputStream;
import java.util.Hashtable;

public class ClassLoader extends java.lang.ClassLoader {

  public static final String[] DEFAULT_IGNORED_PACKAGES = new String[] {"java.", "javax.", "sun."};

  public String[] ignored_packages;

  public Hashtable classes;

  public Repository repository;

  public ClassLoader(java.lang.ClassLoader classLoader) {
    super(classLoader);
    this.classes = new Hashtable();
    this.repository = SyntheticRepository.getInstance();
    this.ignored_packages = DEFAULT_IGNORED_PACKAGES;
    this.repository = new ClassLoaderRepository(classLoader);
  }

  @Override
  public Class loadClass(String text, boolean matches) throws ClassNotFoundException {
    boolean matches2;
    Class classValue;
    Class classValue2;
    if ((classValue2 = (Class) this.classes.get(text)) == null) {
      int value;
      int value2 = value = 0;
      while (true) {
        if (value2 >= this.ignored_packages.length) {
          classValue = classValue2;
          break;
        }
        if (text.startsWith(this.ignored_packages[value])) {
          classValue = classValue2 = this.getParent().loadClass(text);
          break;
        }
        value2 = ++value;
      }
      label33:
      {
        if (classValue == null) {
          JavaClass javaClass;
          JavaClass javaClass2;
          if (text.indexOf("$$BCEL$$") >= 0) {
            javaClass2 = javaClass = this.createClass(text);
          } else {
            JavaClass javaClass3;
            if ((javaClass3 = this.repository.getJavaClass2(text)) == null) {
              throw new ClassNotFoundException(text);
            }
            javaClass2 = javaClass = this.modifyClass(javaClass3);
          }
          if (javaClass2 != null) {
            byte[] bytes = javaClass.getBytes();
            classValue2 = this.defineClass(text, bytes, 0, bytes.length);
            matches2 = matches;
            break label33;
          }
          classValue2 = Class.forName(text);
        }
        matches2 = matches;
      }
      if (matches2) {
        this.resolveClass(classValue2);
      }
    }
    this.classes.put(text, classValue2);
    return classValue2;
  }

  public JavaClass createClass(String text) {
    String text2 = text.substring(text.indexOf("$$BCEL$$") + 8);
    JavaClass javaClass;
    try {
      byte[] values = Utility.decode(text2, true);
      javaClass = new ClassParser(new ByteArrayInputStream(values), "foo").parse();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      return null;
    }
    ConstantPool constantPool;
    ConstantClass constantClass =
        (ConstantClass)
            (constantPool = javaClass.getConstantPool())
                .getConstant(javaClass.getCount(), (byte) 7);
    ((ConstantUtf8) constantPool.getConstant(constantClass.getNameIndex(), (byte) 1))
        .setBytes(text.replace('.', '/'));
    return javaClass;
  }

  public ClassLoader() {
    this(DEFAULT_IGNORED_PACKAGES);
  }

  public ClassLoader(String[] texts) {
    super();
    this.classes = new Hashtable();
    this.repository = SyntheticRepository.getInstance();
    this.ignored_packages = texts;
  }

  public JavaClass modifyClass(JavaClass javaClass) {
    return javaClass;
  }

  public ClassLoader(java.lang.ClassLoader classLoader, String[] texts) {
    this(texts);
    this.repository = new ClassLoaderRepository(classLoader);
  }
}
