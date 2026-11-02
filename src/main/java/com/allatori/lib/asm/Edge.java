package com.allatori.lib.asm;

public final class Edge {

  public final Label successor;

  public final int info;

  public static final int JUMP = 0;

  public static final int EXCEPTION = Integer.MAX_VALUE;

  public Edge nextEdge;

  public Edge(int info, Label label, Edge edge) {
    super();
    this.info = info;
    this.successor = label;
    this.nextEdge = edge;
  }
}
