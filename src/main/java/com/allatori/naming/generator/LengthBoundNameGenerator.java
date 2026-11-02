package com.allatori.naming.generator;

import com.allatori.config.Configuration;
import com.allatori.util.random.RandomUtils;
import java.util.Vector;

public class LengthBoundNameGenerator implements NameGenerator {

  public int count;

  public int count2;

  public NameGenerator nameGenerator;

  public Vector values;

  public boolean enabled;

  public boolean enabled2;

  public Vector values2;

  @Override
  public void reset() {
    this.count = 0;
  }

  public LengthBoundNameGenerator(NameGenerator nameGenerator2) {
    super();
    this.values = new Vector();
    this.count = 0;
    this.values2 = new Vector();
    this.count2 = 26;
    this.enabled2 = false;
    this.enabled = false;
    this.nameGenerator = nameGenerator2;
    if (nameGenerator2 instanceof MixedConfusableNameGenerator) {
      this.enabled2 = true;
      this.count2 = 1024;
    }
    if (nameGenerator2 instanceof LowercaseConfusableNameGenerator) {
      this.enabled = true;
      this.count2 = 1024;
    }
  }

  @Override
  public String nextName() {
    if (this.count < this.values.size()) {
      return (String) this.values.get(this.count++);
    }
    if (!this.enabled2 && this.values.size() == 104) {
      this.count2 = 299;
    }
    int value;
    for (int value2 = value = 0; value2 < this.count2; value2 = value) {
      Vector vector = this.values2;
      value++;
      vector.add(this.nameGenerator.nextName());
    }
    if (Configuration.getRenamingOptions().isRandomizeNamesEnabled()) {
      RandomUtils.processValues(this.values2);
    }
    this.values.addAll(this.values2);
    this.values2.clear();
    if (this.enabled2) {
      MixedConfusableNameGenerator.setText((String) this.values.get(0));
    }
    if (this.enabled) {
      LowercaseConfusableNameGenerator.setText((String) this.values.get(0));
    }
    return (String) this.values.get(this.count++);
  }
}
