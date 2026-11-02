package com.allatori.lib.bcel.verifier.structurals;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public final class Pass3bVerifier$InstructionContextQueue {

  public List ecs;

  public List ics = new Vector();

  public Pass3bVerifier$InstructionContextQueue(Pass3bVerifier$1 pass3bVerifier$1) {
    this();
  }

  public ArrayList getEC(int value) {
    return (ArrayList) this.ecs.get(value);
  }

  public int size() {
    return this.ics.size();
  }

  public InstructionContext getIC(int value) {
    return (InstructionContext) this.ics.get(value);
  }

  public Pass3bVerifier$InstructionContextQueue() {
    this.ecs = new Vector();
  }

  public boolean isEmpty() {
    return this.ics.isEmpty();
  }

  public void add(InstructionContext instructionContext, ArrayList values) {
    this.ics.add(instructionContext);
    this.ecs.add(values);
  }

  public void remove(int value) {
    this.ics.remove(value);
    this.ecs.remove(value);
  }

  public void remove() {
    this.remove(0);
  }
}
