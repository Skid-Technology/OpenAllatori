package com.allatori.lib.bcel.verifier;

import com.allatori.lib.bcel.generic.Type;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class GraphicalVerifier {

  public boolean packFrame = false;

  public GraphicalVerifier() {
    VerifierAppFrame verifierAppFrame = new VerifierAppFrame();
    if (this.packFrame) {
      verifierAppFrame.pack();
    } else {
      verifierAppFrame.validate();
    }
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dimension;
    if ((dimension = verifierAppFrame.getSize()).height > screenSize.height) {
      dimension.height = screenSize.height;
    }
    if (dimension.width > screenSize.width) {
      dimension.width = screenSize.width;
    }
    verifierAppFrame.setLocation(
        (screenSize.width - dimension.width) / 2, (screenSize.height - dimension.height) / 2);
    verifierAppFrame.setVisible(true);
    verifierAppFrame.classNamesJList.setModel(new VerifierFactoryListModel());
    VerifierFactory.getVerifier(Type.OBJECT.getClassName());
    verifierAppFrame.classNamesJList.setSelectedIndex(0);
  }

  public static void main(String[] texts) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    new GraphicalVerifier();
  }
}
