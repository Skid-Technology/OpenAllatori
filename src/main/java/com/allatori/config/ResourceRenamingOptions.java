package com.allatori.config;

import java.util.Locale;
import java.util.Vector;

public class ResourceRenamingOptions {

  public boolean updateResourceNamesEnabled;

  public Vector values;

  public final RenamingOptions renamingOptions;

  public void processText(String text, boolean matches, String text2) {
    String text3 = text.replaceAll("\\.", "\\\\.").replaceAll("\\*", ".*");
    this.values.add(new ResourceNameRule(this, text3, matches, text2));
  }

  public boolean matchesText(String text) {
    String text2;
    if (!(text2 = text.toLowerCase(Locale.ENGLISH)).endsWith(".jar")
        && !text2.endsWith(".class")
        && !text2.endsWith(".jks")
        && !text2.endsWith(".ttf")
        && !text2.endsWith(".png")
        && !text2.endsWith(".jpg")
        && !text2.endsWith(".jpeg")
        && !text2.endsWith(".gif")
        && !text2.endsWith(".bmp")
        && !text2.endsWith(".svg")
        && !text2.endsWith(".ico")) {
      for (ResourceNameRule resourceNameRule :
          ((Iterable<ResourceNameRule>) ((Iterable<?>) this.values))) {
        if (text.matches(ResourceNameRule.getText(resourceNameRule))) {
          return ResourceNameRule.matchesResourceNameRule(resourceNameRule);
        }
      }
      return false;
    } else {
      return false;
    }
  }

  public String getText(String text) {
    text.toLowerCase(Locale.ENGLISH);
    for (ResourceNameRule resourceNameRule :
        ((Iterable<ResourceNameRule>) ((Iterable<?>) this.values))) {
      if (text.matches(ResourceNameRule.getText(resourceNameRule))) {
        return ResourceNameRule.getText2(resourceNameRule);
      }
    }
    return "UTF-8";
  }

  public void setEnabled(boolean updateResourceNamesEnabled) {
    this.updateResourceNamesEnabled = updateResourceNamesEnabled;
  }

  public ResourceRenamingOptions(RenamingOptions renamingOptions2) {
    super();
    this.renamingOptions = renamingOptions2;
    this.updateResourceNamesEnabled = false;
    this.values = new Vector();
  }

  public boolean isUpdateResourceNamesEnabled() {
    return this.updateResourceNamesEnabled;
  }
}
