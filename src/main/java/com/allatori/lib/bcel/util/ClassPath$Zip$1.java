package com.allatori.lib.bcel.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

public class ClassPath$Zip$1 implements ClassPath$ClassFile {

  public final ZipEntry val$entry;

  public final ClassPath$Zip this$0;

  public ClassPath$Zip$1(ClassPath$Zip classPath$Zip, ZipEntry zipEntry) throws IOException {
    super();
    this.this$0 = classPath$Zip;
    this.val$entry = zipEntry;
  }

  @Override
  public String getText() {
    return ClassPath$Zip.access$200(this.this$0).getName();
  }

  @Override
  public String getText2() {
    return this.val$entry.toString();
  }

  @Override
  public long calculateSeed2() {
    return this.val$entry.getSize();
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return ClassPath$Zip.access$200(this.this$0).getInputStream(this.val$entry);
  }

  @Override
  public long calculateSeed() {
    return this.val$entry.getTime();
  }
}
