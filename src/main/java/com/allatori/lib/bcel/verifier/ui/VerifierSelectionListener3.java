package com.allatori.lib.bcel.verifier.ui;

import com.allatori.lib.bcel.verifier.VerifierAppFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VerifierSelectionListener3 implements ListSelectionListener {

  public final VerifierAppFrame verifierAppFrame;

  @Override
  public void valueChanged(ListSelectionEvent listSelectionEvent) {
    this.verifierAppFrame.classNamesJList_valueChanged(listSelectionEvent);
  }

  public VerifierSelectionListener3(VerifierAppFrame verifierAppFrame2) {
    super();
    this.verifierAppFrame = verifierAppFrame2;
  }
}
