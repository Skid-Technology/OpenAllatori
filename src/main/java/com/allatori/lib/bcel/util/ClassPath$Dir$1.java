package com.allatori.lib.bcel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ClassPath$Dir$1 implements ClassPath$ClassFile {

  public final File val$file;

  public final ClassPath$Dir this$0;

  @Override
  public String getText() {
    return ClassPath$Dir.access$100(this.this$0);
  }

  public ClassPath$Dir$1(ClassPath$Dir classPath$Dir, File file) throws FileNotFoundException {
    super();
    this.this$0 = classPath$Dir;
    this.val$file = file;
  }

  @Override
  public String getText2() {
    try {
      return this.val$file.getCanonicalPath();
    } catch (IOException oException) {
      return null;
    }
  }

  @Override
  public long calculateSeed2() {
    return this.val$file.length();
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return new FileInputStream(this.val$file);
  }

  @Override
  public long calculateSeed() {
    return this.val$file.lastModified();
  }
}
