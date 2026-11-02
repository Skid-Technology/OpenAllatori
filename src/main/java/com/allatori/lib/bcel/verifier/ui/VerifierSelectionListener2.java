package com.allatori.lib.bcel.verifier.ui;

import com.allatori.lib.bcel.verifier.VerifierAppFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VerifierSelectionListener2 implements ListSelectionListener {

  public final VerifierAppFrame verifierAppFrame;

  @Override
  public void valueChanged(ListSelectionEvent listSelectionEvent) {
    this.verifierAppFrame.pass3aJList_valueChanged(listSelectionEvent);
  }

  public VerifierSelectionListener2(VerifierAppFrame verifierAppFrame2) {
    super();
    this.verifierAppFrame = verifierAppFrame2;
  }
}
