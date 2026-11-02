package com.allatori.lib.bcel.verifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerifyDialog$IvjEventHandler implements ActionListener {

  public final VerifyDialog this$0;

  @Override
  public void actionPerformed(ActionEvent actionEvent) {
    if (actionEvent.getSource() == VerifyDialog.getJButton3(this.this$0)) {
      VerifyDialog.processActionEvent(this.this$0, actionEvent);
    }
    if (actionEvent.getSource() == VerifyDialog.getJButton2(this.this$0)) {
      VerifyDialog.processActionEvent2(this.this$0, actionEvent);
    }
    if (actionEvent.getSource() == VerifyDialog.getJButton(this.this$0)) {
      VerifyDialog.processActionEvent4(this.this$0, actionEvent);
    }
    if (actionEvent.getSource() == VerifyDialog.getJButton4(this.this$0)) {
      VerifyDialog.processActionEvent3(this.this$0, actionEvent);
    }
  }

  public VerifyDialog$IvjEventHandler(VerifyDialog verifyDialog) {
    super();
    this.this$0 = verifyDialog;
  }
}
