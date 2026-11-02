package com.allatori.lib.bcel.verifier.ui;

import com.allatori.lib.bcel.verifier.VerifierAppFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerifierActionListener3 implements ActionListener {

  public final VerifierAppFrame verifierAppFrame;

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    this.verifierAppFrame.newFileMenuItem_actionPerformed(actionEvent);
  }

  public VerifierActionListener3(VerifierAppFrame verifierAppFrame2) {
    super();
    this.verifierAppFrame = verifierAppFrame2;
  }
}
