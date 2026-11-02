package com.allatori.lib.bcel.verifier.ui;

import com.allatori.lib.bcel.verifier.VerifierAppFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerifierActionListener implements ActionListener {

  public final VerifierAppFrame verifierAppFrame;

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    this.verifierAppFrame.aboutMenuItem_actionPerformed(actionEvent);
  }

  public VerifierActionListener(VerifierAppFrame verifierAppFrame2) {
    super();
    this.verifierAppFrame = verifierAppFrame2;
  }
}
