package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.DescendingVisitor;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.Node;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.verifier.PassVerifier;
import com.allatori.lib.bcel.verifier.VerificationResult;
import com.allatori.lib.bcel.verifier.Verifier;
import com.allatori.lib.bcel.verifier.VerifierFactory;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.LoadingException;
import java.util.HashMap;
import java.util.HashSet;

public final class Pass2Verifier extends PassVerifier implements Constants {

  public Verifier myOwner;

  public LocalVariablesInfo[] localVariablesInfos;

  public static Verifier access$400(Pass2Verifier pass2Verifier) {
    return pass2Verifier.myOwner;
  }

  public Pass2Verifier(Verifier verifier) {
    super();
    this.myOwner = verifier;
  }

  public static boolean matchesText(String text) {
    return matchesText9(text);
  }

  public static boolean matchesText2(String text) {
    return matchesText8(text);
  }

  public void final_methods_are_not_overridden() {
    try {
      HashMap entries = new HashMap();
      JavaClass javaClass = Repository.lookupClass(this.myOwner.getClassName());
      for (int value = -1; value != 0; javaClass = Repository.lookupClass(javaClass.getText5())) {
        value = javaClass.getCount2();
        Method[] methods = javaClass.getMethods();
        int value2;
        for (int value3 = value2 = 0; value3 < methods.length; value3 = ++value2) {
          String text = "" + methods[value2].getText() + methods[value2].getText2();
          if (entries.containsKey(text)) {
            if (methods[value2].isEnabled3()) {
              if (!methods[value2].isEnabled9()) {
                throw new LoadingException(
                    ("Method '"
                        + text
                        + "' in class '"
                        + entries.get(text)
                        + "' overrides the final (not-overridable) definition in class '"
                        + javaClass.getText2()
                        + "'."));
              }
              this.addMessage(
                  "Method '"
                      + text
                      + "' in class '"
                      + entries.get(text)
                      + "' overrides the final (not-overridable) definition in class '"
                      + javaClass.getText2()
                      + "'. This is okay, as the original definition was private; however this"
                      + " constraint leverage was introduced by JLS 8.4.6 (not vmspec2) and the"
                      + " behaviour of the Sun verifiers.");
            } else if (!methods[value2].isEnabled11()) {
              entries.put(text, javaClass.getText2());
            }
          } else if (!methods[value2].isEnabled11()) {
            entries.put(text, javaClass.getText2());
          }
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  public static String getText(Node node) {
    return createText(node);
  }

  public static boolean matchesText3(String text) {
    return matchesText5(text);
  }

  public void every_class_has_an_accessible_superclass() {
    try {
      HashSet values = new HashSet();
      JavaClass javaClass = Repository.lookupClass(this.myOwner.getClassName());
      int value = -1;
      while (value != 0) {
        if ((value = javaClass.getCount2()) == 0) {
          if (javaClass != Repository.lookupClass(Type.OBJECT.getClassName())) {
            throw new LoadingException(
                ("Superclass of '"
                    + javaClass.getText2()
                    + "' missing but not "
                    + Type.OBJECT.getClassName()
                    + " itself!"));
          }
        } else {
          String text5 = javaClass.getText5();
          if (!values.add(text5)) {
            throw new LoadingException("Circular superclass hierarchy detected.");
          }
          if (VerifierFactory.getVerifier(text5).createVerificationResult()
              != VerificationResult.VERIFICATION_RESULT2) {
            throw new LoadingException(("Could not load in ancestor class '" + text5 + "'."));
          }
          if ((javaClass = Repository.lookupClass(text5)).isEnabled3()) {
            throw new LoadingException(
                ("Ancestor class '"
                    + text5
                    + "' has the FINAL access modifier and must therefore not be subclassed."));
          }
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  public static String createText(Node node) {
    return new StringRepresentation(node).toString();
  }

  public static boolean matchesText4(String text) {
    return matchesText6(text);
  }

  public static LocalVariablesInfo[] access$500(Pass2Verifier pass2Verifier) {
    return pass2Verifier.localVariablesInfos;
  }

  public static boolean matchesText5(String text) {
    return matchesEnabled(text, false);
  }

  public static boolean matchesText6(String text) {
    if (text.length() == 0) {
      return false;
    }
    if (!Character.isJavaIdentifierStart(text.charAt(0))) {
      return false;
    }
    int value;
    for (int value2 = value = 1; value2 < text.length(); value2 = ++value) {
      if (!Character.isJavaIdentifierPart(text.charAt(value))) {
        return false;
      }
    }
    return true;
  }

  public void constant_pool_entries_satisfy_static_constraints() {
    try {
      JavaClass javaClass = Repository.lookupClass(this.myOwner.getClassName());
      new Pass2Verifier$CPESSC_Visitor(this, javaClass, null);
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public VerificationResult getVerificationResult() {
    try {
      if (this.myOwner.createVerificationResult().equals(VerificationResult.VERIFICATION_RESULT2)) {
        this.localVariablesInfos =
            new LocalVariablesInfo
                [Repository.lookupClass(this.myOwner.getClassName()).getMethods().length];
        VerificationResult verificationResult = VerificationResult.VERIFICATION_RESULT2;
        try {
          this.constant_pool_entries_satisfy_static_constraints();
          this.field_and_method_refs_are_valid();
          this.every_class_has_an_accessible_superclass();
          this.final_methods_are_not_overridden();
          return verificationResult;
        } catch (LoadingException loadingException) {
          return new VerificationResult(2, loadingException.getMessage());
        }
      } else {
        return VerificationResult.VERIFICATION_RESULT;
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  public static boolean matchesEnabled(String text, boolean matches) {
    if (matchesText7(text)) {
      return true;
    } else {
      return matches ? text.equals("<init>") || text.equals("<clinit>") : text.equals("<init>");
    }
  }

  public static boolean matchesText7(String text) {
    if (!Character.isJavaIdentifierStart(text.charAt(0))) {
      return false;
    }
    int value;
    for (int value2 = value = 1; value2 < text.length(); value2 = ++value) {
      if (!Character.isJavaIdentifierPart(text.charAt(value))) {
        return false;
      }
    }
    return true;
  }

  public void field_and_method_refs_are_valid() {
    try {
      JavaClass javaClass = Repository.lookupClass(this.myOwner.getClassName());
      new DescendingVisitor(javaClass, new Pass2Verifier$FAMRAV_Visitor(this, javaClass, null))
          .visit();
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  public static final boolean matchesText8(String text) {
    return true;
  }

  public LocalVariablesInfo getLocalVariablesInfo(int value) {
    if (this.getVerificationResult2() != VerificationResult.VERIFICATION_RESULT2) {
      return null;
    } else if (value >= 0 && value < this.localVariablesInfos.length) {
      return this.localVariablesInfos[value];
    } else {
      throw new AssertionViolatedException("Method number out of range.");
    }
  }

  public static boolean matchesEnabled2(String text, boolean matches) {
    return matchesEnabled(text, matches);
  }

  public static boolean matchesText9(String text) {
    return matchesText6(text);
  }

  public static boolean matchesText10(String text) {
    return matchesText11(text);
  }

  public static boolean matchesText11(String text) {
    return text.startsWith("<") ? false : matchesText7(text);
  }
}
