package com.allatori.lib.bcel.verifier;

import java.util.ArrayList;
import java.util.List;

public abstract class PassVerifier {

  public VerificationResult verificationResult;

  public List messages = new ArrayList();

  public void addMessage(String text) {
    this.messages.add(text);
  }

  public abstract VerificationResult getVerificationResult();

  public String[] getMessages() {
    this.getVerificationResult2();
    return (String[]) this.messages.toArray(new String[this.messages.size()]);
  }

  public VerificationResult getVerificationResult2() {
    if (this.verificationResult == null) {
      this.verificationResult = this.getVerificationResult();
    }
    return this.verificationResult;
  }

  public PassVerifier() {
    this.verificationResult = null;
  }
}
