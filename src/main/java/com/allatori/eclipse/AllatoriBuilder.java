package com.allatori.eclipse;

import com.allatori.Obfuscate;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;

public class AllatoriBuilder extends IncrementalProjectBuilder {

  public static final String TEXT = "com.allatori.eclipse";

  public IProject[] build(int value, Map entries, IProgressMonitor progressMonitor)
      throws CoreException {
    this.getProject();
    switch (value) {
      case 6:
        this.processIProgressMonitor(progressMonitor);
      case 7:
      case 8:
      case 9:
      case 10:
      default:
        return null;
    }
  }

  public void processIProgressMonitor(IProgressMonitor progressMonitor) throws CoreException {
    try {
      String text = this.getProject().getLocation().toString();
      File file;
      File file2;
      if (!(file = new File(text + "/allatori.xml")).exists()) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        IClasspathEntry[] classpathEntrys;
        IJavaProject javaProject;
        int value =
            (classpathEntrys =
                    (javaProject = JavaCore.create(this.getProject())).getResolvedClasspath(true))
                .length;
        int value2;
        for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
          IClasspathEntry classpathEntry;
          if ((classpathEntry = classpathEntrys[value2]).getEntryKind() == 3) {
            if (classpathEntry.getOutputLocation() != null) {
              String text2 = classpathEntry.getOutputLocation().removeFirstSegments(1).toString();
              stringBuilder.append("        <dir in=\"" + text2 + "\" out=\"" + text2 + "\"/>\n");
            }
          } else if (classpathEntry.getEntryKind() == 1) {
            stringBuilder2.append(
                "        <jar name=\"" + classpathEntry.getPath().toString() + "\"/>\n");
          }
        }
        if (javaProject.getOutputLocation() != null) {
          String text3 = javaProject.getOutputLocation().removeFirstSegments(1).toString();
          stringBuilder.append("        <dir in=\"" + text3 + "\" out=\"" + text3 + "\"/>\n");
        }
        String text4 =
            "<config>\n    <input>\n"
                + stringBuilder
                + "    </input>\n\n"
                + "    <classpath>\n"
                + stringBuilder2
                + "    </classpath>\n\n"
                + "    <keep-names>\n"
                + "        <class access=\"protected+\">\n"
                + "            <field access=\"protected+\"/>\n"
                + "            <method access=\"protected+\"/>\n"
                + "        </class>\n"
                + "    </keep-names>\n\n"
                + "    <property name=\"log-file\" value=\"allatori-log.xml\"/>\n"
                + "</config>\n";
        BufferedWriter bufferedWriter =
            new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        file2 = file;
        bufferedWriter.write(text4);
        bufferedWriter.close();
      } else {
        StringBuffer stringBuffer = new StringBuffer((int) file.length());
        int value4;
        BufferedReader bufferedReader;
        for (BufferedReader bufferedReader2 =
                bufferedReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            (value4 = bufferedReader2.read()) != -1;
            bufferedReader2 = bufferedReader) {
          stringBuffer.append((char) value4);
        }
        bufferedReader.close();
        String text5;
        if ((text5 = stringBuffer.toString()).contains("${eclipse-")) {
          StringBuilder stringBuilder3 = new StringBuilder();
          StringBuilder stringBuilder4 = new StringBuilder();
          IJavaProject javaProject2;
          IClasspathEntry[] classpathEntrys2;
          int value5 =
              (classpathEntrys2 =
                      (javaProject2 = JavaCore.create(this.getProject()))
                          .getResolvedClasspath(true))
                  .length;
          int value6;
          for (int value7 = value6 = 0; value7 < value5; value7 = ++value6) {
            IClasspathEntry classpathEntry2;
            if ((classpathEntry2 = classpathEntrys2[value6]).getEntryKind() == 3) {
              if (classpathEntry2.getOutputLocation() != null) {
                String text6 =
                    classpathEntry2.getOutputLocation().removeFirstSegments(1).toString();
                stringBuilder3.append(
                    "        <dir in=\"" + text6 + "\" out=\"" + text6 + "\"/>\n");
              }
            } else if (classpathEntry2.getEntryKind() == 1) {
              stringBuilder4.append(
                  "        <jar name=\"" + classpathEntry2.getPath().toString() + "\"/>\n");
            }
          }
          if (javaProject2.getOutputLocation() != null) {
            String text7 = javaProject2.getOutputLocation().removeFirstSegments(1).toString();
            stringBuilder3.append("        <dir in=\"" + text7 + "\" out=\"" + text7 + "\"/>\n");
          }
          String text8 =
              text5
                  .replace("${eclipse-input}", stringBuilder3)
                  .replace("${eclipse-classpath}", stringBuilder4);
          file = new File(text + "/allatori-resolved.xml");
          BufferedWriter bufferedWriter2 =
              new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
          bufferedWriter2.write(text8);
          bufferedWriter2.close();
        }
        file2 = file;
      }
      Obfuscate.run(file2);
    } catch (Exception exception) {
      exception.printStackTrace();
      throw new CoreException(
          new Status(4, "com.allatori.eclipse", 0, exception.getMessage(), exception));
    }
  }
}
