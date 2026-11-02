package com.allatori.lib.bcel.verifier;

public abstract class NativeVerifier {

  public static void main(String[] texts) {
    if (texts.length != 1) {
      System.out.println("Verifier front-end: need exactly one argument.");
      System.exit(1);
    }
    int value;
    if ((value = texts[0].lastIndexOf(".class")) != -1) {
      texts[0] = texts[0].substring(0, value);
    }
    texts[0] = texts[0].replace('/', '.');
    try {
      Class.forName(texts[0]);
    } catch (ExceptionInInitializerError exceptionInInitializerError) {
      System.out.println(
          "NativeVerifier: ExceptionInInitializerError encountered on '" + texts[0] + "'.");
      System.out.println(exceptionInInitializerError);
      System.exit(1);
    } catch (LinkageError linkageError) {
      System.out.println("NativeVerifier: LinkageError encountered on '" + texts[0] + "'.");
      System.out.println(linkageError);
      System.exit(1);
    } catch (ClassNotFoundException classNotFoundException) {
      System.out.println("NativeVerifier: FILE NOT FOUND: '" + texts[0] + "'.");
      System.exit(1);
    } catch (Throwable throwable) {
      System.out.println("NativeVerifier: Unspecified verification error on'" + texts[0] + "'.");
      System.exit(1);
    }
    System.out.println("NativeVerifier: Class file '" + texts[0] + "' seems to be okay.");
    System.exit(0);
  }
}
