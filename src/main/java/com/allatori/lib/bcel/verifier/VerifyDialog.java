package com.allatori.lib.bcel.verifier;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.verifier.ui.VerifierWindowListener;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class VerifyDialog extends JDialog {

  public JPanel ivjPass3Panel;

  public JButton ivjPass3Button;

  public JPanel ivjJDialogContentPane = null;

  public JButton ivjPass1Button;

  public static int classes_to_verify;

  public JPanel ivjPass1Panel = null;

  public String class_name;

  public JPanel ivjPass2Panel = null;

  public JButton ivjFlushButton;

  public JButton ivjPass2Button;

  public VerifyDialog$IvjEventHandler ivjEventHandler;

  public JButton getPass3Button() {
    if (this.ivjPass3Button == null) {
      try {
        this.ivjPass3Button = new JButton();
        this.ivjPass3Button.setName("Pass3Button");
        this.ivjPass3Button.setText("Passes 3a+3b: Verify code arrays");
        this.ivjPass3Button.setBackground(SystemColor.controlHighlight);
        this.ivjPass3Button.setBounds(100, 160, 300, 30);
        this.ivjPass3Button.setActionCommand("Button2");
      } catch (Throwable throwable) {
        this.handleException(throwable);
        return this.ivjPass3Button;
      }
      return this.ivjPass3Button;
    } else {
      return this.ivjPass3Button;
    }
  }

  public void processActionEvent(ActionEvent actionEvent) {
    try {
      this.flushButton_ActionPerformed(actionEvent);
    } catch (Throwable throwable) {
      this.handleException(throwable);
    }
  }

  public VerifyDialog(Frame frame, String text) {
    super(frame, text);
    this.ivjJDialogContentPane = null;
    this.ivjPass1Panel = null;
    this.ivjPass2Panel = null;
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
  }

  public VerifyDialog(Dialog dialog) {
    super(dialog);
    this.ivjJDialogContentPane = null;
    this.ivjPass1Panel = null;
    this.ivjPass2Panel = null;
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
  }

  public VerifyDialog(String text) {
    super();
    String text2 = text;
    this.ivjJDialogContentPane = null;
    this.ivjPass1Panel = null;
    this.ivjPass2Panel = null;
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
    int value;
    if ((value = text2.lastIndexOf(".class")) != -1) {
      text2 = text2.substring(0, value);
    }
    String text3 = text2.replace('/', '.');
    this.class_name = text3;
    this.initialize();
  }

  public static void processActionEvent(VerifyDialog verifyDialog, ActionEvent actionEvent) {
    verifyDialog.processActionEvent4(actionEvent);
  }

  public static JButton getJButton(VerifyDialog verifyDialog) {
    return verifyDialog.getPass3Button();
  }

  public void pass2Button_ActionPerformed(ActionEvent actionEvent) {
    this.pass1Button_ActionPerformed(actionEvent);
    VerificationResult verificationResult;
    if ((verificationResult =
                VerifierFactory.getVerifier(this.class_name).createVerificationResult2())
            .getStatus()
        == 1) {
      this.getPass2Panel().setBackground(Color.green);
      this.getPass2Panel().repaint();
    }
    if (verificationResult.getStatus() == 0) {
      this.getPass2Panel().setBackground(Color.yellow);
      this.getPass2Panel().repaint();
    }
    if (verificationResult.getStatus() == 2) {
      this.getPass2Panel().setBackground(Color.red);
      this.getPass2Panel().repaint();
    }
  }

  public void processActionEvent2(ActionEvent actionEvent) {
    try {
      this.pass4Button_ActionPerformed(actionEvent);
    } catch (Throwable throwable) {
      this.handleException(throwable);
    }
  }

  public void initialize() {
    VerifyDialog verifyDialog;
    label12:
    {
      try {
        this.setName("VerifyDialog");
        this.setDefaultCloseOperation(2);
        this.setSize(430, 280);
        this.setVisible(true);
        this.setModal(true);
        this.setResizable(false);
        this.setContentPane(this.getJDialogContentPane());
        this.initConnections();
      } catch (Throwable throwable) {
        verifyDialog = this;
        this.handleException(throwable);
        break label12;
      }
      verifyDialog = this;
    }
    verifyDialog.setTitle("'" + this.class_name + "' verification - JustIce / BCEL");
  }

  public VerifyDialog(Dialog dialog, String text, boolean matches) {
    super(dialog, text, matches);
    this.ivjJDialogContentPane = null;
    this.ivjPass1Panel = null;
    this.ivjPass2Panel = null;
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
  }

  public JButton getPass2Button() {
    if (this.ivjPass2Button == null) {
      try {
        this.ivjPass2Button = new JButton();
        this.ivjPass2Button.setName("Pass2Button");
        this.ivjPass2Button.setText("Pass 2: Verify static .class file constraints");
        this.ivjPass2Button.setBackground(SystemColor.controlHighlight);
        this.ivjPass2Button.setBounds(100, 100, 300, 30);
        this.ivjPass2Button.setActionCommand("Button2");
      } catch (Throwable throwable) {
        this.handleException(throwable);
        return this.ivjPass2Button;
      }
      return this.ivjPass2Button;
    } else {
      return this.ivjPass2Button;
    }
  }

  public VerifyDialog(Frame frame) {
    super(frame);
    this.ivjJDialogContentPane = null;
    this.ivjPass1Panel = null;
    this.ivjPass2Panel = null;
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
  }

  public void pass4Button_ActionPerformed(ActionEvent actionEvent) {
    this.pass2Button_ActionPerformed(actionEvent);
    Color color = Color.green;
    Verifier verifier;
    if ((verifier = VerifierFactory.getVerifier(this.class_name))
            .createVerificationResult2()
            .getStatus()
        == 1) {
      try {
        int value = Repository.lookupClass(this.class_name).getMethods().length;
        for (int index = 0; index < value; index++) {
          if (verifier.doPass3b(index).getStatus() != 1) {
            color = Color.red;
            break;
          }
        }
      } catch (ClassNotFoundException classNotFoundException) {
        classNotFoundException.printStackTrace();
      }
    } else {
      color = Color.yellow;
    }
    this.getPass3Panel().setBackground(color);
    this.getPass3Panel().repaint();
  }

  public JPanel getJDialogContentPane() {
    if (this.ivjJDialogContentPane == null) {
      try {
        this.ivjJDialogContentPane = new JPanel();
        this.ivjJDialogContentPane.setName("JDialogContentPane");
        this.ivjJDialogContentPane.setLayout(null);
        this.getJDialogContentPane().add(this.getPass1Panel(), this.getPass1Panel().getName());
        this.getJDialogContentPane().add(this.getPass3Panel(), this.getPass3Panel().getName());
        this.getJDialogContentPane().add(this.getPass2Panel(), this.getPass2Panel().getName());
        this.getJDialogContentPane().add(this.getPass1Button(), this.getPass1Button().getName());
        this.getJDialogContentPane().add(this.getPass2Button(), this.getPass2Button().getName());
        this.getJDialogContentPane().add(this.getPass3Button(), this.getPass3Button().getName());
        this.getJDialogContentPane().add(this.getFlushButton(), this.getFlushButton().getName());
      } catch (Throwable throwable) {
        this.handleException(throwable);
        return this.ivjJDialogContentPane;
      }
      return this.ivjJDialogContentPane;
    } else {
      return this.ivjJDialogContentPane;
    }
  }

  public VerifyDialog(Dialog dialog, boolean matches) {
    super(dialog, matches);
    this.ivjJDialogContentPane = null;
    this.ivjPass1Panel = null;
    this.ivjPass2Panel = null;
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
  }

  public static JButton getJButton2(VerifyDialog verifyDialog) {
    return verifyDialog.getPass2Button();
  }

  public void initConnections() throws Exception {
    this.getPass1Button().addActionListener(this.ivjEventHandler);
    this.getPass2Button().addActionListener(this.ivjEventHandler);
    this.getPass3Button().addActionListener(this.ivjEventHandler);
    this.getFlushButton().addActionListener(this.ivjEventHandler);
  }

  public void processActionEvent3(ActionEvent actionEvent) {
    try {
      this.pass2Button_ActionPerformed(actionEvent);
    } catch (Throwable throwable) {
      this.handleException(throwable);
    }
  }

  public static void processActionEvent2(VerifyDialog verifyDialog, ActionEvent actionEvent) {
    verifyDialog.processActionEvent3(actionEvent);
  }

  public VerifyDialog(Frame frame, String text, boolean matches) {
    super(frame, text, matches);
    this.ivjJDialogContentPane = null;
    this.ivjPass1Panel = null;
    this.ivjPass2Panel = null;
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
  }

  public VerifyDialog(Frame frame, boolean matches) {
    super(frame, matches);
    this.ivjJDialogContentPane = null;
    this.ivjPass1Panel = null;
    this.ivjPass2Panel = null;
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
  }

  public static void main(String[] texts) {
    classes_to_verify = texts.length;
    int value;
    for (int value2 = value = 0; value2 < texts.length; value2 = ++value) {
      try {
        VerifyDialog verifyDialog = new VerifyDialog(texts[value]);
        verifyDialog.setModal(true);
        verifyDialog.addWindowListener(new VerifierWindowListener());
        verifyDialog.setVisible(true);
      } catch (Throwable throwable) {
        System.err.println("Exception occurred in main() of javax.swing.JDialog");
        throwable.printStackTrace(System.out);
      }
    }
  }

  public void handleException(Throwable throwable) {
    System.out.println("--------- UNCAUGHT EXCEPTION ---------");
    throwable.printStackTrace(System.out);
  }

  public VerifyDialog() {
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
    this.initialize();
  }

  public static JButton getJButton3(VerifyDialog verifyDialog) {
    return verifyDialog.getPass1Button();
  }

  public VerifyDialog(Dialog dialog, String text) {
    super(dialog, text);
    this.ivjJDialogContentPane = null;
    this.ivjPass1Panel = null;
    this.ivjPass2Panel = null;
    this.ivjPass3Panel = null;
    this.ivjPass1Button = null;
    this.ivjPass2Button = null;
    this.ivjPass3Button = null;
    this.ivjEventHandler = new VerifyDialog$IvjEventHandler(this);
    this.class_name = "java.lang.Object";
    this.ivjFlushButton = null;
  }

  public static void processActionEvent3(VerifyDialog verifyDialog, ActionEvent actionEvent) {
    verifyDialog.processActionEvent(actionEvent);
  }

  public JButton getFlushButton() {
    if (this.ivjFlushButton == null) {
      try {
        this.ivjFlushButton = new JButton();
        this.ivjFlushButton.setName("FlushButton");
        this.ivjFlushButton.setText("Flush: Forget old verification results");
        this.ivjFlushButton.setBackground(SystemColor.controlHighlight);
        this.ivjFlushButton.setBounds(60, 215, 300, 30);
        this.ivjFlushButton.setForeground(Color.red);
        this.ivjFlushButton.setActionCommand("FlushButton");
      } catch (Throwable throwable) {
        this.handleException(throwable);
        return this.ivjFlushButton;
      }
      return this.ivjFlushButton;
    } else {
      return this.ivjFlushButton;
    }
  }

  public JPanel getPass2Panel() {
    if (this.ivjPass2Panel == null) {
      try {
        this.ivjPass2Panel = new JPanel();
        this.ivjPass2Panel.setName("Pass2Panel");
        this.ivjPass2Panel.setLayout(null);
        this.ivjPass2Panel.setBackground(SystemColor.controlShadow);
        this.ivjPass2Panel.setBounds(30, 90, 50, 50);
      } catch (Throwable throwable) {
        this.handleException(throwable);
        return this.ivjPass2Panel;
      }
      return this.ivjPass2Panel;
    } else {
      return this.ivjPass2Panel;
    }
  }

  public static void processActionEvent4(VerifyDialog verifyDialog, ActionEvent actionEvent) {
    verifyDialog.processActionEvent2(actionEvent);
  }

  public void flushButton_ActionPerformed(ActionEvent actionEvent) {
    VerifierFactory.getVerifier(this.class_name).flush();
    Repository.removeClass(this.class_name);
    this.getPass1Panel().setBackground(Color.gray);
    this.getPass1Panel().repaint();
    this.getPass2Panel().setBackground(Color.gray);
    this.getPass2Panel().repaint();
    this.getPass3Panel().setBackground(Color.gray);
    this.getPass3Panel().repaint();
  }

  public JButton getPass1Button() {
    if (this.ivjPass1Button == null) {
      try {
        this.ivjPass1Button = new JButton();
        this.ivjPass1Button.setName("Pass1Button");
        this.ivjPass1Button.setText("Pass1: Verify binary layout of .class file");
        this.ivjPass1Button.setBackground(SystemColor.controlHighlight);
        this.ivjPass1Button.setBounds(100, 40, 300, 30);
        this.ivjPass1Button.setActionCommand("Button1");
      } catch (Throwable throwable) {
        this.handleException(throwable);
        return this.ivjPass1Button;
      }
      return this.ivjPass1Button;
    } else {
      return this.ivjPass1Button;
    }
  }

  public static int getClasses_to_verify() {
    return classes_to_verify;
  }

  public JPanel getPass3Panel() {
    if (this.ivjPass3Panel == null) {
      try {
        this.ivjPass3Panel = new JPanel();
        this.ivjPass3Panel.setName("Pass3Panel");
        this.ivjPass3Panel.setLayout(null);
        this.ivjPass3Panel.setBackground(SystemColor.controlShadow);
        this.ivjPass3Panel.setBounds(30, 150, 50, 50);
      } catch (Throwable throwable) {
        this.handleException(throwable);
        return this.ivjPass3Panel;
      }
      return this.ivjPass3Panel;
    } else {
      return this.ivjPass3Panel;
    }
  }

  public void pass1Button_ActionPerformed(ActionEvent actionEvent) {
    VerificationResult verificationResult;
    if ((verificationResult =
                VerifierFactory.getVerifier(this.class_name).createVerificationResult())
            .getStatus()
        == 1) {
      this.getPass1Panel().setBackground(Color.green);
      this.getPass1Panel().repaint();
    }
    if (verificationResult.getStatus() == 2) {
      this.getPass1Panel().setBackground(Color.red);
      this.getPass1Panel().repaint();
    }
  }

  public static JButton getJButton4(VerifyDialog verifyDialog) {
    return verifyDialog.getFlushButton();
  }

  public JPanel getPass1Panel() {
    if (this.ivjPass1Panel == null) {
      try {
        this.ivjPass1Panel = new JPanel();
        this.ivjPass1Panel.setName("Pass1Panel");
        this.ivjPass1Panel.setLayout(null);
        this.ivjPass1Panel.setBackground(SystemColor.controlShadow);
        this.ivjPass1Panel.setBounds(30, 30, 50, 50);
      } catch (Throwable throwable) {
        this.handleException(throwable);
        return this.ivjPass1Panel;
      }
      return this.ivjPass1Panel;
    } else {
      return this.ivjPass1Panel;
    }
  }

  public static int calculateCount() {
    return classes_to_verify--;
  }

  public void processActionEvent4(ActionEvent actionEvent) {
    try {
      this.pass1Button_ActionPerformed(actionEvent);
    } catch (Throwable throwable) {
      this.handleException(throwable);
    }
  }
}
