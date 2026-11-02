package com.allatori.lib.bcel.verifier;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class VerifierFactory {

  public static Map hashMap = new HashMap();

  public static List observers = new Vector();

  public static void notify(String text) {
    Iterator iterator2;
    for (Iterator iterator3 = iterator2 = observers.iterator();
        iterator3.hasNext();
        iterator3 = iterator2) {
      ((VerifierFactoryObserver) iterator2.next()).update(text);
    }
  }

  public static void detach(VerifierFactoryObserver verifierFactoryObserver) {
    observers.remove(verifierFactoryObserver);
  }

  public static Verifier getVerifier(String text) {
    Verifier verifier;
    if ((verifier = (Verifier) hashMap.get(text)) == null) {
      verifier = new Verifier(text);
      hashMap.put(text, verifier);
      notify(text);
    }
    return verifier;
  }

  public static void attach(VerifierFactoryObserver verifierFactoryObserver) {
    observers.add(verifierFactoryObserver);
  }

  public static Verifier[] getVerifiers() {
    Verifier[] verifiers = new Verifier[hashMap.values().size()];
    return (Verifier[]) hashMap.values().toArray(verifiers);
  }
}
