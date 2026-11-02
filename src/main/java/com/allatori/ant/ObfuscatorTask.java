package com.allatori.ant;

import com.allatori.Obfuscate;
import com.allatori.config.Configuration;
import com.allatori.util.FileUtils;
import java.io.File;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class ObfuscatorTask extends Task {

  public String config;

  public void execute() {
    if (this.config == null) {
      throw new BuildException("Missing 'config' attribute.");
    }
    try {
      File file;
      if (!(file = new File(this.config)).canRead()) {
        throw new BuildException(("Cannot find configuration file: '" + this.config + "'"));
      }
      File file2 = new File(("" + this.config + ".resolved"));
      file2.deleteOnExit();
      String text = FileUtils.createText(file, "UTF-8");
      String text2 = this.getProject().replaceProperties(text);
      FileUtils.processText(file2, "UTF-8", text2);
      Configuration.processText(file2.getPath());
      Obfuscate.runObfuscation();
      Configuration.update();
    } catch (Exception exception) {
      throw new BuildException(exception);
    }
  }

  public void setConfig(String config) {
    this.config = config;
  }
}
