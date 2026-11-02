package com.allatori.lib.bcel.verifier.ui;

import com.allatori.lib.bcel.verifier.VerifierAppFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VerifierSelectionListener implements ListSelectionListener {

  public final VerifierAppFrame verifierAppFrame;

  public VerifierSelectionListener(VerifierAppFrame verifierAppFrame2) {
    super();
    this.verifierAppFrame = verifierAppFrame2;
  }

  @Override
  public void valueChanged(ListSelectionEvent listSelectionEvent) {
    this.verifierAppFrame.pass3bJList_valueChanged(listSelectionEvent);
  }
}
