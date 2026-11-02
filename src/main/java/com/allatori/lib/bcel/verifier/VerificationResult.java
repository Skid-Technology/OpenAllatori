package com.allatori.lib.bcel.verifier;

public class VerificationResult {

  public String detailMessage;

  public static final String TEXT = "Not yet verified.";

  public static final int VERIFIED_NOTYET = 0;

  public static final String TEXT2 = "Passed verification.";

  public static final VerificationResult VERIFICATION_RESULT =
      new VerificationResult(0, "Not yet verified.");

  public static final int VERIFIED_REJECTED = 2;

  public static final VerificationResult VERIFICATION_RESULT2 =
      new VerificationResult(1, "Passed verification.");

  public int numeric;

  public static final int VERIFIED_OK = 1;

  public VerificationResult(int numeric, String detailMessage) {
    super();
    this.numeric = numeric;
    this.detailMessage = detailMessage;
  }

  @Override
  public boolean equals(Object result) {
    VerificationResult verificationResult;
    return !(result instanceof VerificationResult)
        ? false
        : (verificationResult = (VerificationResult) result).numeric == this.numeric
            && verificationResult.detailMessage.equals(this.detailMessage);
  }

  @Override
  public int hashCode() {
    return this.numeric ^ this.detailMessage.hashCode();
  }

  public int getStatus() {
    return this.numeric;
  }

  public String getDetailMessage() {
    return this.detailMessage;
  }
}
