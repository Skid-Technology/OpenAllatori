package com.allatori.config;

public class ResourceNameRule {

  public final ResourceRenamingOptions resourceRenamingOptions;

  public boolean enabled;

  public String text;

  public String text2;

  public static boolean matchesResourceNameRule(ResourceNameRule resourceNameRule) {
    return resourceNameRule.enabled;
  }

  public static String getText(ResourceNameRule resourceNameRule) {
    return resourceNameRule.text;
  }

  public static String getText2(ResourceNameRule resourceNameRule) {
    return resourceNameRule.text2;
  }

  public ResourceNameRule(
      ResourceRenamingOptions resourceRenamingOptions2,
      String text,
      boolean enabled,
      String text2) {
    super();
    this.resourceRenamingOptions = resourceRenamingOptions2;
    this.text = text;
    this.enabled = enabled;
    this.text2 = text2;
  }
}
