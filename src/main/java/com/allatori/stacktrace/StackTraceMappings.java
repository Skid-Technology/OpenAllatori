package com.allatori.stacktrace;

import com.allatori.util.LookupTable;
import java.util.Hashtable;
import java.util.Vector;

public class StackTraceMappings {

  public Vector values;

  public LookupTable lookupTable;

  public LookupTable lookupTable2;

  public Hashtable entries = new Hashtable();

  public boolean matchesText(String text) {
    return this.entries.containsKey(text);
  }

  public void processText(String text, String text2) {
    this.processCount(text, text2, -1, -1);
  }

  public void processCount(String text, String text2, int value, int value2) {
    if (!this.entries.containsKey(text)) {
      this.entries.put(text, new Vector());
    }
    ((Vector) this.entries.get(text)).add(new StackTraceMappingEntry(text2, value, value2, null));
  }

  public String getText(String text, int value) {
    Vector vector;
    for (StackTraceMappingEntry stackTraceMappingEntry :
        ((Iterable<StackTraceMappingEntry>)
            ((Iterable<?>) (vector = (Vector) this.entries.get(text))))) {
      if (value >= StackTraceMappingEntry.calculateCount(stackTraceMappingEntry)
          && value <= StackTraceMappingEntry.calculateCount2(stackTraceMappingEntry)) {
        return StackTraceMappingEntry.getText(stackTraceMappingEntry);
      }
    }
    return vector.size() > 0
        ? StackTraceMappingEntry.getText((StackTraceMappingEntry) vector.get(0))
        : null;
  }

  public String getText(String text) {
    Vector vector;
    return (vector = (Vector) this.entries.get(text)).size() > 0
        ? StackTraceMappingEntry.getText((StackTraceMappingEntry) vector.get(0))
        : null;
  }
}
