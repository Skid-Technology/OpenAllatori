package com.allatori.lib.bcel.verifier.ui;

import com.allatori.lib.bcel.verifier.VerifyDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class VerifierWindowListener extends WindowAdapter {

  @Override
  public void windowClosing(WindowEvent windowEvent) {
    VerifyDialog.calculateCount();
    if (VerifyDialog.getClasses_to_verify() == 0) {
      System.exit(0);
    }
  }
}
