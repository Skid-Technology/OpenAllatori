package com.allatori.lib.bcel.verifier;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.verifier.statics.Pass1Verifier;
import com.allatori.lib.bcel.verifier.statics.Pass2Verifier;
import com.allatori.lib.bcel.verifier.statics.Pass3aVerifier;
import com.allatori.lib.bcel.verifier.structurals.Pass3bVerifier;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Verifier {

  public final String classname;

  public Map p3avs;

  public Pass2Verifier p2v;

  public Map p3bvs;

  public Pass1Verifier p1v;

  public void flush() {
    this.p1v = null;
    this.p2v = null;
    this.p3avs.clear();
    this.p3bvs.clear();
  }

  public final String getClassName() {
    return this.classname;
  }

  public VerificationResult createVerificationResult() {
    if (this.p1v == null) {
      this.p1v = new Pass1Verifier(this);
    }
    return this.p1v.getVerificationResult2();
  }

  public static void main(String[] texts) {
    System.out.println(
        "JustIce by Enver Haase, (C) 2001-2002.\n"
            + "<http://bcel.sourceforge.net>\n"
            + "<http://jakarta.apache.org/bcel>\n");
    int value;
    for (int value2 = value = 0; value2 < texts.length; value2 = ++value) {
      try {
        int value3;
        if (texts[value].endsWith(".class")
            && (value3 = texts[value].lastIndexOf(".class")) != -1) {
          texts[value] = texts[value].substring(0, value3);
        }
        texts[value] = texts[value].replace('/', '.');
        System.out.println("Now verifying: " + texts[value] + "\n");
        Verifier verifier;
        Verifier verifier2 = verifier = VerifierFactory.getVerifier(texts[value]);
        VerificationResult verificationResult = verifier2.createVerificationResult();
        System.out.println("Pass 1:\n" + verificationResult);
        VerificationResult verificationResult2 = verifier2.createVerificationResult2();
        System.out.println("Pass 2:\n" + verificationResult2);
        if (verificationResult2 == VerificationResult.VERIFICATION_RESULT2) {
          JavaClass javaClass = Repository.lookupClass(texts[value]);
          int value4;
          for (int value5 = value4 = 0; value5 < javaClass.getMethods().length; value5 = value4) {
            VerificationResult verificationResult3 = verifier.doPass3a(value4);
            System.out.println(
                "Pass 3a, method number "
                    + value4
                    + " ['"
                    + javaClass.getMethods()[value4]
                    + "']:\n"
                    + verificationResult3);
            VerificationResult verificationResult4 = verifier.doPass3b(value4);
            PrintStream printStream = System.out;
            StringBuilder stringBuilder =
                new StringBuilder()
                    .insert(0, "Pass 3b, method number ")
                    .append(value4)
                    .append(" ['")
                    .append(javaClass.getMethods()[value4])
                    .append("']:\n");
            value4++;
            printStream.println(stringBuilder.append(verificationResult4).toString());
          }
        }
        System.out.println("Warnings:");
        String[] texts2;
        if ((texts2 = verifier.getMessages()).length == 0) {
          System.out.println("<none>");
        }
        int value6;
        for (int value7 = value6 = 0; value7 < texts2.length; value7 = value6) {
          int value8 = value6;
          String[] texts3 = texts2;
          PrintStream printStream2 = System.out;
          value6++;
          printStream2.println(texts3[value8]);
        }
        System.out.println("\n");
        verifier.flush();
        Repository.clearCache();
        System.gc();
      } catch (ClassNotFoundException classNotFoundException) {
        classNotFoundException.printStackTrace();
      }
    }
  }

  public VerificationResult createVerificationResult2() {
    if (this.p2v == null) {
      this.p2v = new Pass2Verifier(this);
    }
    return this.p2v.getVerificationResult2();
  }

  public String[] getMessages() throws ClassNotFoundException {
    ArrayList values2 = new ArrayList();
    if (this.p1v != null) {
      String[] messages = this.p1v.getMessages();
      int value;
      for (int value2 = value = 0; value2 < messages.length; value2 = value) {
        StringBuilder stringBuilder = new StringBuilder().append("Pass 1: ");
        String text = messages[value];
        value++;
        values2.add(stringBuilder.append(text).toString());
      }
    }
    if (this.p2v != null) {
      String[] messages2 = this.p2v.getMessages();
      int value3;
      for (int value4 = value3 = 0; value4 < messages2.length; value4 = value3) {
        StringBuilder stringBuilder2 = new StringBuilder().insert(0, "Pass 2: ");
        String text2 = messages2[value3];
        value3++;
        values2.add(stringBuilder2.append(text2).toString());
      }
    }
    for (Pass3aVerifier pass3aVerifier :
        ((Iterable<Pass3aVerifier>) ((Iterable<?>) this.p3avs.values()))) {
      String[] messages3 = pass3aVerifier.getMessages();
      int methodNo = pass3aVerifier.getMethodNo();
      int value5;
      for (int value6 = value5 = 0; value6 < messages3.length; value6 = value5) {
        StringBuilder stringBuilder3 =
            new StringBuilder()
                .insert(0, "Pass 3a, method ")
                .append(methodNo)
                .append(" ('")
                .append(Repository.lookupClass(this.classname).getMethods()[methodNo])
                .append("'): ");
        String text3 = messages3[value5];
        value5++;
        values2.add(stringBuilder3.append(text3).toString());
      }
    }
    for (Pass3bVerifier pass3bVerifier :
        ((Iterable<Pass3bVerifier>) ((Iterable<?>) this.p3bvs.values()))) {
      String[] messages4 = pass3bVerifier.getMessages();
      int methodNo2 = pass3bVerifier.getMethodNo();
      int value7;
      for (int value8 = value7 = 0; value8 < messages4.length; value8 = value7) {
        StringBuilder stringBuilder4 =
            new StringBuilder()
                .insert(0, "Pass 3b, method ")
                .append(methodNo2)
                .append(" ('")
                .append(Repository.lookupClass(this.classname).getMethods()[methodNo2])
                .append("'): ");
        String text4 = messages4[value7];
        value7++;
        values2.add(stringBuilder4.append(text4).toString());
      }
    }
    String[] texts = new String[values2.size()];
    int value9;
    for (int value10 = value9 = 0; value10 < values2.size(); value10 = value9) {
      String text5 = (String) values2.get(value9);
      int value11 = value9;
      value9++;
      texts[value11] = text5;
    }
    return texts;
  }

  public VerificationResult doPass3a(int value) {
    String text = Integer.toString(value);
    Pass3aVerifier pass3aVerifier = (Pass3aVerifier) this.p3avs.get(text);
    if (this.p3avs.get(text) == null) {
      pass3aVerifier = new Pass3aVerifier(this, value);
      this.p3avs.put(text, pass3aVerifier);
    }
    return pass3aVerifier.getVerificationResult2();
  }

  public Verifier(String classname) {
    super();
    this.p3avs = new HashMap();
    this.p3bvs = new HashMap();
    this.classname = classname;
    this.flush();
  }

  public VerificationResult doPass3b(int value) {
    String text = Integer.toString(value);
    Pass3bVerifier pass3bVerifier = (Pass3bVerifier) this.p3bvs.get(text);
    if (this.p3bvs.get(text) == null) {
      pass3bVerifier = new Pass3bVerifier(this, value);
      this.p3bvs.put(text, pass3bVerifier);
    }
    return pass3bVerifier.getVerificationResult2();
  }
}
