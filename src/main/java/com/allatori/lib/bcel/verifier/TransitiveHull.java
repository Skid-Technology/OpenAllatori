package com.allatori.lib.bcel.verifier;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.JavaClass;
import java.io.PrintStream;

public class TransitiveHull implements VerifierFactoryObserver {

  public int indent = 0;

  public static void main(String[] texts) {
    if (texts.length != 1) {
      System.out.println("Need exactly one argument: The root class to verify.");
      System.exit(1);
    }
    int value;
    if ((value = texts[0].lastIndexOf(".class")) != -1) {
      texts[0] = texts[0].substring(0, value);
    }
    texts[0] = texts[0].replace('/', '.');
    TransitiveHull transitiveHull = new TransitiveHull();
    VerifierFactory.attach(transitiveHull);
    VerifierFactory.getVerifier(texts[0]);
    VerifierFactory.detach(transitiveHull);
  }

  @Override
  public void update(String text) {
    System.gc();
    int value;
    for (int value2 = value = 0; value2 < this.indent; value2 = value) {
      PrintStream printStream = System.out;
      value++;
      printStream.print(" ");
    }
    System.out.println(text);
    this.indent++;
    Verifier verifier;
    VerificationResult verificationResult;
    if ((verificationResult =
            (verifier = VerifierFactory.getVerifier(text)).createVerificationResult())
        != VerificationResult.VERIFICATION_RESULT2) {
      System.out.println("Pass 1:\n" + verificationResult);
    }
    VerificationResult verificationResult2;
    if ((verificationResult2 = verifier.createVerificationResult2())
        != VerificationResult.VERIFICATION_RESULT2) {
      System.out.println("Pass 2:\n" + verificationResult2);
    }
    if (verificationResult2 == VerificationResult.VERIFICATION_RESULT2) {
      try {
        JavaClass javaClass = Repository.lookupClass(verifier.getClassName());
        for (int index = 0; index < javaClass.getMethods().length; index++) {
          VerificationResult verificationResult3;
          if ((verificationResult3 = verifier.doPass3a(index))
              != VerificationResult.VERIFICATION_RESULT2) {
            System.out.println(
                ""
                    + verifier.getClassName()
                    + ", Pass 3a, method "
                    + index
                    + " ['"
                    + javaClass.getMethods()[index]
                    + "']:\n"
                    + verificationResult3);
          }
          VerificationResult verificationResult4;
          if ((verificationResult4 = verifier.doPass3b(index))
              != VerificationResult.VERIFICATION_RESULT2) {
            System.out.println(
                ""
                    + verifier.getClassName()
                    + ", Pass 3b, method "
                    + index
                    + " ['"
                    + javaClass.getMethods()[index]
                    + "']:\n"
                    + verificationResult4);
          }
        }
      } catch (ClassNotFoundException classNotFoundException) {
        System.err.println("Could not find class " + verifier.getClassName() + " in Repository");
      }
    }
    this.indent--;
  }
}
