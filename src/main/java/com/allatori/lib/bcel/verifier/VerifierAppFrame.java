package com.allatori.lib.bcel.verifier;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.verifier.ui.VerifierActionListener;
import com.allatori.lib.bcel.verifier.ui.VerifierActionListener2;
import com.allatori.lib.bcel.verifier.ui.VerifierActionListener3;
import com.allatori.lib.bcel.verifier.ui.VerifierSelectionListener;
import com.allatori.lib.bcel.verifier.ui.VerifierSelectionListener2;
import com.allatori.lib.bcel.verifier.ui.VerifierSelectionListener3;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;

public class VerifierAppFrame extends JFrame {

  public JScrollPane jScrollPane6;

  public JMenu jMenu1;

  public JTextPane messagesTextPane;

  public JScrollPane jScrollPane3;

  public JPanel jPanel;

  public JMenuItem aboutMenuItem;

  public String text;

  public JTextPane pass3aTextPane;

  public JScrollPane jScrollPane5;

  public JMenuItem newFileMenuItem;

  public JPanel jPanel3;

  public JScrollPane jScrollPane7;

  public JPanel jPanel2;

  public JScrollPane jScrollPane1;

  public CardLayout cardLayout1;

  public JList pass3aJList;

  public JPanel messagesPanel;

  public JScrollPane jScrollPane2;

  public JTextPane pass3bTextPane;

  public GridLayout gridLayout4;

  public JScrollPane messagesScrollPane;

  public JTextPane pass2TextPane;

  public GridLayout gridLayout1;

  public JPanel jPanel1;

  public JList classNamesJList;

  public JMenuBar jMenuBar1;

  public GridLayout gridLayout3;

  public JTextPane pass1TextPane;

  public JSplitPane jSplitPane1;

  public GridLayout gridLayout2;

  public String JUSTICE_VERSION;

  public JMenu jMenu2;

  public JMenuItem whatisMenuItem;

  public JSplitPane jSplitPane3;

  public JSplitPane jSplitPane2;

  public JList pass3bJList;

  public JSplitPane jSplitPane4;

  public JScrollPane jScrollPane4;

  public synchronized void pass3bJList_valueChanged(ListSelectionEvent listSelectionEvent) {
    if (!listSelectionEvent.getValueIsAdjusting()) {
      Verifier verifier = VerifierFactory.getVerifier(this.text);
      String text2 = "";
      byte value = 1;
      byte value2 = 0;
      int value3;
      for (int value4 = value3 = 0;
          value4 < this.pass3bJList.getModel().getSize();
          value4 = ++value3) {
        if (this.pass3bJList.isSelectedIndex(value3)) {
          VerificationResult verificationResult;
          if ((verificationResult = verifier.doPass3b(value3)).getStatus() == 2) {
            value = 0;
            value2 = 1;
          }
          try {
            JavaClass javaClass = Repository.lookupClass(verifier.getClassName());
            text2 =
                ""
                    + text2
                    + "Method '"
                    + javaClass.getMethods()[value3]
                    + "': "
                    + verificationResult.getDetailMessage().replace('\n', ' ')
                    + "\n\n";
          } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
          }
        }
      }
      this.pass3bTextPane.setText(text2);
      this.pass3bTextPane.setBackground(
          value != 0 ? Color.green : (value2 != 0 ? Color.red : Color.yellow));
    }
  }

  public synchronized void classNamesJList_valueChanged(ListSelectionEvent listSelectionEvent) {
    if (!listSelectionEvent.getValueIsAdjusting()) {
      this.text = this.classNamesJList.getSelectedValue().toString();
      VerifierAppFrame verifierAppFrame;
      label15:
      {
        try {
          this.verify();
        } catch (ClassNotFoundException classNotFoundException) {
          verifierAppFrame = this;
          classNotFoundException.printStackTrace();
          break label15;
        }
        verifierAppFrame = this;
      }
      verifierAppFrame.classNamesJList.setSelectedValue(this.text, true);
    }
  }

  public synchronized void pass3aJList_valueChanged(ListSelectionEvent listSelectionEvent) {
    if (!listSelectionEvent.getValueIsAdjusting()) {
      Verifier verifier = VerifierFactory.getVerifier(this.text);
      String text2 = "";
      byte value = 1;
      byte value2 = 0;
      int value3;
      for (int value4 = value3 = 0;
          value4 < this.pass3aJList.getModel().getSize();
          value4 = ++value3) {
        if (this.pass3aJList.isSelectedIndex(value3)) {
          VerificationResult verificationResult;
          if ((verificationResult = verifier.doPass3a(value3)).getStatus() == 2) {
            value = 0;
            value2 = 1;
          }
          try {
            JavaClass javaClass = Repository.lookupClass(verifier.getClassName());
            text2 =
                ""
                    + text2
                    + "Method '"
                    + javaClass.getMethods()[value3]
                    + "': "
                    + verificationResult.getDetailMessage().replace('\n', ' ')
                    + "\n\n";
          } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
          }
        }
      }
      this.pass3aTextPane.setText(text2);
      this.pass3aTextPane.setBackground(
          value != 0 ? Color.green : (value2 != 0 ? Color.red : Color.yellow));
    }
  }

  public void whatisMenuItem_actionPerformed(ActionEvent actionEvent) {
    JOptionPane.showMessageDialog(
        this,
        "The upper four boxes to the right reflect verification passes according to The Java"
            + " Virtual Machine Specification.\n"
            + "These are (in that order): Pass one, Pass two, Pass three (before data flow"
            + " analysis), Pass three (data flow analysis).\n"
            + "The bottom box to the right shows (warning) messages; warnings do not cause a class"
            + " to be rejected.",
        this.JUSTICE_VERSION,
        1);
  }

  public void jbInit() throws Exception {
    this.jPanel = (JPanel) this.getContentPane();
    this.jPanel.setLayout(this.cardLayout1);
    this.setJMenuBar(this.jMenuBar1);
    this.setSize(new Dimension(708, 451));
    this.setTitle("JustIce");
    this.jPanel1.setMinimumSize(new Dimension(100, 100));
    this.jPanel1.setPreferredSize(new Dimension(100, 100));
    this.jPanel1.setLayout(this.gridLayout1);
    this.jSplitPane2.setOrientation(0);
    this.jPanel2.setLayout(this.gridLayout2);
    this.jPanel3.setMinimumSize(new Dimension(200, 100));
    this.jPanel3.setPreferredSize(new Dimension(400, 400));
    this.jPanel3.setLayout(this.gridLayout4);
    this.messagesPanel.setMinimumSize(new Dimension(100, 100));
    this.messagesPanel.setLayout(this.gridLayout3);
    this.jPanel2.setMinimumSize(new Dimension(200, 100));
    this.jMenu1.setText("File");
    this.jScrollPane1.getViewport().setBackground(Color.red);
    this.messagesScrollPane.getViewport().setBackground(Color.red);
    this.messagesScrollPane.setPreferredSize(new Dimension(10, 10));
    this.classNamesJList.addListSelectionListener(new VerifierSelectionListener3(this));
    this.classNamesJList.setSelectionMode(0);
    this.jScrollPane3.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jScrollPane3.setPreferredSize(new Dimension(100, 100));
    this.gridLayout4.setRows(4);
    this.gridLayout4.setColumns(1);
    this.gridLayout4.setHgap(1);
    this.jScrollPane4.setBorder(BorderFactory.createLineBorder(Color.black));
    this.jScrollPane4.setPreferredSize(new Dimension(100, 100));
    this.pass1TextPane.setBorder(BorderFactory.createRaisedBevelBorder());
    this.pass1TextPane.setToolTipText("");
    this.pass1TextPane.setEditable(false);
    this.pass2TextPane.setBorder(BorderFactory.createRaisedBevelBorder());
    this.pass2TextPane.setEditable(false);
    this.messagesTextPane.setBorder(BorderFactory.createRaisedBevelBorder());
    this.messagesTextPane.setEditable(false);
    this.newFileMenuItem.setText("New...");
    this.newFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(78, 2, true));
    this.newFileMenuItem.addActionListener(new VerifierActionListener3(this));
    this.pass3aTextPane.setEditable(false);
    this.pass3bTextPane.setEditable(false);
    this.pass3aJList.addListSelectionListener(new VerifierSelectionListener2(this));
    this.pass3bJList.addListSelectionListener(new VerifierSelectionListener(this));
    this.jMenu2.setText("Help");
    this.whatisMenuItem.setText("What is...");
    this.whatisMenuItem.addActionListener(new VerifierActionListener2(this));
    this.aboutMenuItem.setText("About");
    this.aboutMenuItem.addActionListener(new VerifierActionListener(this));
    this.jSplitPane2.add(this.messagesPanel, "bottom");
    this.messagesPanel.add(this.messagesScrollPane, null);
    this.messagesScrollPane.getViewport().add(this.messagesTextPane, null);
    this.jSplitPane2.add(this.jPanel3, "top");
    this.jPanel3.add(this.jScrollPane3, null);
    this.jScrollPane3.getViewport().add(this.pass1TextPane, null);
    this.jPanel3.add(this.jScrollPane4, null);
    this.jPanel3.add(this.jSplitPane3, null);
    this.jSplitPane3.add(this.jScrollPane2, "left");
    this.jScrollPane2.getViewport().add(this.pass3aJList, null);
    this.jSplitPane3.add(this.jScrollPane5, "right");
    this.jScrollPane5.getViewport().add(this.pass3aTextPane, null);
    this.jPanel3.add(this.jSplitPane4, null);
    this.jSplitPane4.add(this.jScrollPane6, "left");
    this.jScrollPane6.getViewport().add(this.pass3bJList, null);
    this.jSplitPane4.add(this.jScrollPane7, "right");
    this.jScrollPane7.getViewport().add(this.pass3bTextPane, null);
    this.jScrollPane4.getViewport().add(this.pass2TextPane, null);
    this.jSplitPane1.add(this.jPanel2, "top");
    this.jPanel2.add(this.jScrollPane1, null);
    this.jSplitPane1.add(this.jPanel1, "bottom");
    this.jPanel1.add(this.jSplitPane2, null);
    this.jScrollPane1.getViewport().add(this.classNamesJList, null);
    this.jMenuBar1.add(this.jMenu1);
    this.jMenuBar1.add(this.jMenu2);
    this.jPanel.add(this.jSplitPane1, "jSplitPane1");
    this.jMenu1.add(this.newFileMenuItem);
    this.jMenu2.add(this.whatisMenuItem);
    this.jMenu2.add(this.aboutMenuItem);
    this.jSplitPane2.setDividerLocation(300);
    this.jSplitPane3.setDividerLocation(150);
    this.jSplitPane4.setDividerLocation(150);
  }

  public VerifierAppFrame() {
    super();
    this.jSplitPane1 = new JSplitPane();
    this.jPanel1 = new JPanel();
    this.jPanel2 = new JPanel();
    this.jSplitPane2 = new JSplitPane();
    this.jPanel3 = new JPanel();
    this.classNamesJList = new JList();
    this.gridLayout1 = new GridLayout();
    this.messagesPanel = new JPanel();
    this.gridLayout2 = new GridLayout();
    this.jMenuBar1 = new JMenuBar();
    this.jMenu1 = new JMenu();
    this.jScrollPane1 = new JScrollPane();
    this.messagesScrollPane = new JScrollPane();
    this.jScrollPane3 = new JScrollPane();
    this.gridLayout4 = new GridLayout();
    this.jScrollPane4 = new JScrollPane();
    this.cardLayout1 = new CardLayout();
    this.JUSTICE_VERSION = "JustIce by Enver Haase";
    this.gridLayout3 = new GridLayout();
    this.pass1TextPane = new JTextPane();
    this.pass2TextPane = new JTextPane();
    this.messagesTextPane = new JTextPane();
    this.newFileMenuItem = new JMenuItem();
    this.jSplitPane3 = new JSplitPane();
    this.jSplitPane4 = new JSplitPane();
    this.jScrollPane2 = new JScrollPane();
    this.jScrollPane5 = new JScrollPane();
    this.jScrollPane6 = new JScrollPane();
    this.jScrollPane7 = new JScrollPane();
    this.pass3aJList = new JList();
    this.pass3bJList = new JList();
    this.pass3aTextPane = new JTextPane();
    this.pass3bTextPane = new JTextPane();
    this.jMenu2 = new JMenu();
    this.whatisMenuItem = new JMenuItem();
    this.aboutMenuItem = new JMenuItem();
    this.enableEvents(64L);
    try {
      this.jbInit();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void processWindowEvent(WindowEvent windowEvent) {
    super.processWindowEvent(windowEvent);
    if (windowEvent.getID() == 201) {
      System.exit(0);
    }
  }

  public void verify() throws ClassNotFoundException {
    this.setTitle("PLEASE WAIT");
    Verifier verifier;
    Verifier verifier2 = verifier = VerifierFactory.getVerifier(this.text);
    verifier2.flush();
    VerificationResult verificationResult;
    if ((verificationResult = verifier2.createVerificationResult()).getStatus() == 2) {
      this.pass1TextPane.setText(verificationResult.getDetailMessage());
      this.pass1TextPane.setBackground(Color.red);
      this.pass2TextPane.setText("");
      this.pass2TextPane.setBackground(Color.yellow);
      this.pass3aTextPane.setText("");
      this.pass3aJList.setListData(new Object[0]);
      this.pass3aTextPane.setBackground(Color.yellow);
      this.pass3bTextPane.setText("");
      this.pass3bJList.setListData(new Object[0]);
      verifier2 = verifier;
      this.pass3bTextPane.setBackground(Color.yellow);
    } else {
      this.pass1TextPane.setBackground(Color.green);
      this.pass1TextPane.setText(verificationResult.getDetailMessage());
      VerificationResult verificationResult2;
      if ((verificationResult2 = verifier.createVerificationResult2()).getStatus() == 2) {
        this.pass2TextPane.setText(verificationResult2.getDetailMessage());
        this.pass2TextPane.setBackground(Color.red);
        this.pass3aTextPane.setText("");
        this.pass3aTextPane.setBackground(Color.yellow);
        this.pass3aJList.setListData(new Object[0]);
        this.pass3bTextPane.setText("");
        this.pass3bTextPane.setBackground(Color.yellow);
        this.pass3bJList.setListData(new Object[0]);
        verifier2 = verifier;
      } else {
        this.pass2TextPane.setText(verificationResult2.getDetailMessage());
        this.pass2TextPane.setBackground(Color.green);
        JavaClass javaClass;
        String[] texts =
            new String[(javaClass = Repository.lookupClass(this.text)).getMethods().length];
        int value;
        for (int value2 = value = 0; value2 < javaClass.getMethods().length; value2 = value) {
          String text2 =
              javaClass.getMethods()[value].toString().replace('\n', ' ').replace('\t', ' ');
          int value3 = value;
          value++;
          texts[value3] = text2;
        }
        this.pass3aJList.setListData(texts);
        this.pass3aJList.setSelectionInterval(0, javaClass.getMethods().length - 1);
        this.pass3bJList.setListData(texts);
        this.pass3bJList.setSelectionInterval(0, javaClass.getMethods().length - 1);
        verifier2 = verifier;
      }
    }
    String[] messages = verifier2.getMessages();
    this.messagesTextPane.setBackground(messages.length == 0 ? Color.green : Color.yellow);
    String text3 = "";
    int value4;
    for (int value5 = value4 = 0; value5 < messages.length; value5 = value4) {
      messages[value4] = messages[value4].replace('\n', ' ');
      StringBuilder stringBuilder = new StringBuilder().insert(0, text3).append(messages[value4]);
      value4++;
      text3 = stringBuilder.append((String) "\n\n").toString();
    }
    this.messagesTextPane.setText(text3);
    this.setTitle(this.text + " - " + this.JUSTICE_VERSION);
  }

  public void aboutMenuItem_actionPerformed(ActionEvent actionEvent) {
    JOptionPane.showMessageDialog(
        this,
        "JustIce is a Java class file verifier.\n"
            + "It was implemented by Enver Haase in 2001, 2002.\n"
            + "<http://jakarta.apache.org/bcel/index.html>",
        this.JUSTICE_VERSION,
        1);
  }

  public void newFileMenuItem_actionPerformed(ActionEvent actionEvent) {
    String text;
    if ((text =
                JOptionPane.showInputDialog(
                    "Please enter the fully qualified name of a class or interface to verify:"))
            != null
        && !text.equals("")) {
      VerifierFactory.getVerifier(text);
      this.classNamesJList.setSelectedValue(text, true);
    }
  }
}
