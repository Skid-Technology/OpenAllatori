package com.allatori.lib.asm;

public final class MethodWriter extends MethodVisitor {

  public Attribute attribute;

  public final SymbolTable symbolTable;

  public int count;

  public int count2;

  public AnnotationWriter[] annotationWriters;

  public int count3;

  public final int numberOfExceptions;

  public int[] counts;

  public final int[] exceptionIndexTable;

  public final int descriptorIndex;

  public int count4;

  public AnnotationWriter[] annotationWriters2;

  public int[] counts2;

  public static final int COMPUTE_INSERTED_FRAMES = 3;

  public int count5;

  public int count6;

  public ByteVector byteVector;

  public int count7;

  public final int nameIndex;

  public ByteVector byteVector2;

  public ByteVector byteVector3;

  public static final int[] STACK_SIZE_DELTA =
      new int[] {
        0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 1, 1, 1, 0, 0, 1, 2, 1, 2, 1, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, -1, 0, -1, -1, -1, -1, -1, -2, -1, -2,
        -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -3, -4, -3, -4, -3, -3, -3,
        -3, -1, -2, 1, 1, 1, 2, 2, 2, 0, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1, -2, -1,
        -2, -1, -2, -1, -2, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -2, -1, -2, -1, -2, 0, 1, 0, 1,
        -1, -1, 0, 0, 1, 1, -1, 0, -1, 0, 0, 0, -3, -1, -1, -3, -3, -1, -1, -1, -1, -1, -1, -2, -2,
        -2, -2, -2, -2, -2, -2, 0, 1, 0, -1, -1, -1, -2, -1, -2, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, -1, -1, 0, 0
      };

  public final ByteVector code;

  public int maxLocals;

  public static final int COMPUTE_MAX_STACK_AND_LOCAL = 1;

  public int count8;

  public final int signatureIndex;

  public static final int COUNT = 0;

  public Attribute attribute2;

  public boolean enabled;

  public ByteVector byteVector4;

  public Handler handler;

  public AnnotationWriter annotationWriter;

  public int count9;

  public final int accessFlags;

  public ByteVector byteVector5;

  public final String name;

  public Handler handler2;

  public AnnotationWriter annotationWriter2;

  public int count10;

  public boolean enabled2;

  public int count11;

  public Label label;

  public AnnotationWriter annotationWriter3;

  public AnnotationWriter annotationWriter4;

  public ByteVector byteVector6;

  public static final int COUNT2 = 0;

  public AnnotationWriter annotationWriter5;

  public final int compute;

  public Label firstBasicBlock;

  public int count12;

  public static final int COMPUTE_ALL_FRAMES = 4;

  public AnnotationWriter annotationWriter6;

  public int currentLocals;

  public int count13;

  public Label label2;

  public final String descriptor;

  public int count14;

  public static final int COMPUTE_MAX_STACK_AND_LOCAL_FROM_FRAMES = 2;

  @Override
  public void visitFrame(
      int value, int currentLocals, Object[] results, int count9, Object[] results2) {
    if (this.compute != 4) {
      MethodWriter methodWriter;
      if (this.compute == 3) {
        if (this.label.frame == null) {
          methodWriter = this;
          this.label.frame = new CurrentFrame(this.label);
          this.label.frame.setInputFrameFromDescriptor(
              this.symbolTable, this.accessFlags, this.descriptor, currentLocals);
          this.label.frame.accept(this);
        } else {
          if (value == -1) {
            this.label.frame.setInputFrameFromApiFormat(
                this.symbolTable, currentLocals, results, count9, results2);
          }
          methodWriter = this;
          this.label.frame.accept(this);
        }
      } else if (value == -1) {
        if (this.counts2 == null) {
          int value4 = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
          Frame frame2 = new Frame(new Label());
          frame2.setInputFrameFromDescriptor(
              this.symbolTable, this.accessFlags, this.descriptor, value4);
          frame2.accept(this);
        }
        this.currentLocals = currentLocals;
        int value5 = this.visitFrameStart(this.code.length, currentLocals, count9);
        int value6;
        for (int value7 = value6 = 0; value7 < currentLocals; value7 = value6) {
          SymbolTable symbolTable2 = this.symbolTable;
          int value8 = value5;
          int[] values = this.counts;
          value5++;
          int abstractTypeFromApiFormat =
              Frame.getAbstractTypeFromApiFormat(symbolTable2, results[value6]);
          value6++;
          values[value8] = abstractTypeFromApiFormat;
        }
        int value9;
        for (int value10 = value9 = 0; value10 < count9; value10 = value9) {
          SymbolTable symbolTable3 = this.symbolTable;
          int value11 = value5;
          int[] values2 = this.counts;
          value5++;
          int abstractTypeFromApiFormat2 =
              Frame.getAbstractTypeFromApiFormat(symbolTable3, results2[value9]);
          value9++;
          values2[value11] = abstractTypeFromApiFormat2;
        }
        methodWriter = this;
        this.update4();
      } else {
        if (this.symbolTable.getCount() < 50) {
          throw new IllegalArgumentException("Class versions V1_5 or less must use F_NEW frames.");
        }
        int value12;
        int value13;
        if (this.byteVector2 == null) {
          this.byteVector2 = new ByteVector();
          value12 = this.code.length;
          value13 = value;
        } else {
          if ((value12 = this.code.length - this.count3 - 1) < 0) {
            if (value == 3) {
              return;
            }
            throw new IllegalStateException();
          }
          value13 = value;
        }
        label130:
        {
          switch (value13) {
            case 0:
              this.currentLocals = currentLocals;
              this.byteVector2.putByte(255).putShort(value12).putShort(currentLocals);
              int value14 = 0;
              for (int value15 = value14; value15 < currentLocals; value15 = value14) {
                int value16 = value14;
                value14++;
                this.processValue(results[value16]);
              }
              this.byteVector2.putShort(count9);
              int value17 = 0;
              for (int value18 = value17; value18 < count9; value18 = value17) {
                int value19 = value17;
                value17++;
                this.processValue(results2[value19]);
              }
              break;
            case 1:
              this.currentLocals += currentLocals;
              this.byteVector2.putByte(251 + currentLocals).putShort(value12);
              int value20 = 0;
              for (int value21 = value20; value21 < currentLocals; value21 = value20) {
                int value22 = value20;
                value20++;
                this.processValue(results[value22]);
              }
              break;
            case 2:
              methodWriter = this;
              this.currentLocals -= currentLocals;
              this.byteVector2.putByte(251 - currentLocals).putShort(value12);
              break label130;
            case 3:
              if (value12 < 64) {
                methodWriter = this;
                this.byteVector2.putByte(value12);
              } else {
                methodWriter = this;
                this.byteVector2.putByte(251).putShort(value12);
              }
              break label130;
            case 4:
              if (value12 < 64) {
                methodWriter = this;
                this.byteVector2.putByte(64 + value12);
              } else {
                methodWriter = this;
                this.byteVector2.putByte(247).putShort(value12);
              }
              methodWriter.processValue(results2[0]);
              methodWriter = this;
              break label130;
            default:
              throw new IllegalArgumentException();
          }
          methodWriter = this;
        }
        methodWriter.count3 = this.code.length;
        methodWriter = this;
        this.count7++;
      }
      if (methodWriter.compute == 2) {
        int value23 = 0;
        this.count9 = count9;
        for (int index = 0; value23 < count9; value23 = ++index) {
          if (results2[index] == Opcodes.INTEGER5 || results2[index] == Opcodes.INTEGER) {
            this.count9++;
          }
        }
        if (this.count9 > this.count14) {
          this.count14 = this.count9;
        }
      }
      this.count = Math.max(this.count, count9);
      this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
    }
  }

  @Override
  public void visitTryCatchBlock(Label label, Label label2, Label label3, String string) {
    MethodWriter methodWriter;
    String string2;
    int n;
    if (string != null) {
      n = this.symbolTable.getSymbol3((String) string).index;
      string2 = string;
    } else {
      n = 0;
      string2 = string;
    }
    Handler handler = new Handler(label, label2, label3, n, string2);
    if (this.handler2 == null) {
      methodWriter = this;
      this.handler2 = handler;
    } else {
      methodWriter = this;
      this.handler.nextHandler = handler;
    }
    methodWriter.handler = handler;
  }

  public void visitSwitchInsn(Label label2, Label[] labels) throws ClassNotFoundException {
    if (this.label != null) {
      if (this.compute == 4) {
        this.label.frame.execute(171, 0, null, null);
        this.processLabel3(0, label2);
        Label canonicalInstance = label2.getCanonicalInstance();
        canonicalInstance.value3 = (short) (canonicalInstance.value3 | 2);
        int value = labels.length;
        int value2;
        for (int value4 = value2 = 0; value4 < value; value4 = value2) {
          Label label3 = labels[value2];
          this.processLabel3(0, label3);
          Label canonicalInstance2 = label3.getCanonicalInstance();
          short value5 = canonicalInstance2.value3;
          value2++;
          canonicalInstance2.value3 = (short) (value5 | (byte) 2);
        }
      } else if (this.compute == 1) {
        this.count9--;
        this.processLabel3(this.count9, label2);
        int value6 = labels.length;
        int value7;
        for (int value8 = value7 = 0; value8 < value6; value8 = value7) {
          Label label4 = labels[value7];
          value7++;
          this.processLabel3(this.count9, label4);
        }
      }
      this.update3();
    }
  }

  @Override
  public void visitInsn(int value) throws ClassNotFoundException {
    this.count8 = this.code.length;
    this.code.putByte(value);
    if (this.label != null) {
      int value2;
      if (this.compute != 4 && this.compute != 3) {
        int value3;
        if ((value3 = this.count9 + STACK_SIZE_DELTA[value]) > this.count14) {
          this.count14 = value3;
        }
        this.count9 = value3;
        value2 = value;
      } else {
        this.label.frame.execute(value, 0, null, null);
        value2 = value;
      }
      if (value2 >= 172 && value <= 177 || value == 191) {
        this.update3();
      }
    }
  }

  @Override
  public void visitAttribute(Attribute attribute3) {
    if (attribute3.isEnabled2()) {
      attribute3.nextAttribute = this.attribute2;
      this.attribute2 = attribute3;
    } else {
      attribute3.nextAttribute = this.attribute;
      this.attribute = attribute3;
    }
  }

  @Override
  public void visitLocalVariable(
      String text, String text2, String text3, Label label, Label label2, int value) {
    if (text3 != null) {
      if (this.byteVector3 == null) {
        this.byteVector3 = new ByteVector();
      }
      this.count11++;
      this.byteVector3
          .putShort(label.bytecodeOffset)
          .putShort(label2.bytecodeOffset - label.bytecodeOffset)
          .putShort(this.symbolTable.addConstantUtf8(text))
          .putShort(this.symbolTable.addConstantUtf8(text3))
          .putShort(value);
    }
    if (this.byteVector5 == null) {
      this.byteVector5 = new ByteVector();
    }
    this.count13++;
    this.byteVector5
        .putShort(label.bytecodeOffset)
        .putShort(label2.bytecodeOffset - label.bytecodeOffset)
        .putShort(this.symbolTable.addConstantUtf8(text))
        .putShort(this.symbolTable.addConstantUtf8(text2))
        .putShort(value);
    if (this.compute != 0) {
      char value2 = text2.charAt(0);
      int value3;
      if ((value3 = value + (value2 != 'J' && value2 != 'D' ? 1 : 2)) > this.maxLocals) {
        this.maxLocals = value3;
      }
    }
  }

  @Override
  public void visitEnd() {}

  public boolean isCount7() {
    return this.count7 > 0;
  }

  @Override
  public void visitParameter(String text, int value) {
    if (this.byteVector6 == null) {
      this.byteVector6 = new ByteVector();
    }
    this.count6++;
    this.byteVector6
        .putShort(text == null ? 0 : this.symbolTable.addConstantUtf8(text))
        .putShort(value);
  }

  @Override
  public void visitAnnotableParameterCount(int value, boolean matches) {
    if (matches) {
      this.count4 = value;
    } else {
      this.count10 = value;
    }
  }

  @Override
  public void visitLabel(Label label3) {
    this.enabled2 =
        this.enabled2 | label3.resolve(this.code.data, this.byteVector2, this.code.length);
    if ((label3.value3 & 1) == 0) {
      if (this.compute == 4) {
        if (this.label != null) {
          if (label3.bytecodeOffset == this.label.bytecodeOffset) {
            this.label.value3 = (short) (this.label.value3 | label3.value3 & 2);
            label3.frame = this.label.frame;
            return;
          }
          this.processLabel3(0, label3);
        }
        if (this.label2 != null) {
          if (label3.bytecodeOffset == this.label2.bytecodeOffset) {
            this.label2.value3 = (short) (this.label2.value3 | label3.value3 & 2);
            label3.frame = this.label2.frame;
            this.label = this.label2;
            return;
          }
          this.label2.label2 = label3;
        }
        this.label = this.label2 = label3;
        label3.frame = new Frame(label3);
      } else if (this.compute == 3) {
        if (this.label == null) {
          this.label = label3;
        } else {
          this.label.frame.owner = label3;
        }
      } else if (this.compute == 1) {
        if (this.label != null) {
          this.label.value4 = (short) this.count14;
          this.processLabel3(this.count9, label3);
        }
        this.label = label3;
        this.count9 = 0;
        this.count14 = 0;
        if (this.label2 != null) {
          this.label2.label2 = label3;
        }
        this.label2 = label3;
      } else {
        if (this.compute == 2 && this.label == null) {
          this.label = label3;
        }
      }
    }
  }

  public void processCount5(int value, int value2) {
    int value3;
    for (int value4 = value3 = value; value4 < value2; value4 = value3) {
      int value5 = this.counts[value3];
      SymbolTable symbolTable2 = this.symbolTable;
      value3++;
      Frame.putAbstractType(symbolTable2, value5, this.byteVector2);
    }
  }

  @Override
  public AnnotationVisitor createAnnotationVisitor3(
      int value, TypePath typePath, String text, boolean matches) {
    return matches
        ? (this.annotationWriter =
            AnnotationWriter.create(
                this.symbolTable,
                value & -16776961 | this.count8 << 8,
                typePath,
                text,
                this.annotationWriter))
        : (this.annotationWriter6 =
            AnnotationWriter.create(
                this.symbolTable,
                value & -16776961 | this.count8 << 8,
                typePath,
                text,
                this.annotationWriter6));
  }

  @Override
  public void visitMaxs(int count, int maxLocals) throws ClassNotFoundException {
    if (this.compute == 4) {
      this.computeAllFrames();
    } else if (this.compute == 1) {
      this.computeMaxStackAndLocal();
    } else if (this.compute == 2) {
      this.count = this.count14;
    } else {
      this.count = count;
      this.maxLocals = maxLocals;
    }
  }

  public void processCount6(int value, int value2) {
    this.counts[value] = value2;
  }

  public boolean canCopyMethodAttributes(
      ClassReader classReader,
      boolean matches,
      boolean matches2,
      int value,
      int value2,
      int value3) {
    if (classReader == this.symbolTable.getSource()
        && value == this.descriptorIndex
        && value2 == this.signatureIndex
        && matches2 == ((this.accessFlags & 131072) != 0)) {
      boolean matches3 = this.symbolTable.getCount() < 49 && (this.accessFlags & 4096) != 0;
      if (matches != matches3) {
        return false;
      }
      if (value3 == 0) {
        if (this.numberOfExceptions != 0) {
          return false;
        }
      } else if (classReader.readUnsignedShort(value3) == this.numberOfExceptions) {
        int value4 = value3 + 2;
        int value5;
        for (int value6 = value5 = 0; value6 < this.numberOfExceptions; value6 = ++value5) {
          if (classReader.readUnsignedShort(value4) != this.exceptionIndexTable[value5]) {
            return false;
          }
          value4 += 2;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  public int visitFrameStart(int value, int value2, int value3) {
    int value4 = 3 + value2 + value3;
    if (this.counts == null || this.counts.length < value4) {
      this.counts = new int[value4];
    }
    this.counts[0] = value;
    this.counts[1] = value2;
    this.counts[2] = value3;
    return 3;
  }

  @Override
  public void visitIincInsn(int value, int value2) throws ClassNotFoundException {
    this.count8 = this.code.length;
    MethodWriter methodWriter;
    if (value <= 255 && value2 <= 127 && value2 >= -128) {
      methodWriter = this;
      this.code.putByte(132).put11(value, value2);
    } else {
      methodWriter = this;
      this.code.putByte(196).put12(132, value).putShort(value2);
    }
    if (methodWriter.label != null && (this.compute == 4 || this.compute == 3)) {
      this.label.frame.execute(132, value, null, null);
    }
    int value3;
    if (this.compute != 0 && (value3 = value + 1) > this.maxLocals) {
      this.maxLocals = value3;
    }
  }

  @Override
  public void visitMethodInsn(int value, String text, String text2, String text3, boolean matches)
      throws ClassNotFoundException {
    this.count8 = this.code.length;
    Symbol symbol = this.symbolTable.addConstantMethodref(text, text2, text3, matches);
    MethodWriter methodWriter;
    if (value == 185) {
      methodWriter = this;
      this.code.put12(185, symbol.index).put11(symbol.getArgumentsAndReturnSizes() >> 2, 0);
    } else {
      methodWriter = this;
      this.code.put12(value, symbol.index);
    }
    if (methodWriter.label != null) {
      if (this.compute == 4 || this.compute == 3) {
        this.label.frame.execute(value, 0, symbol, this.symbolTable);
        return;
      }
      int value2;
      int value3 = ((value2 = symbol.getArgumentsAndReturnSizes()) & 3) - (value2 >> 2);
      int value4;
      if ((value == 184 ? (value4 = this.count9 + value3 + 1) : (value4 = this.count9 + value3))
          > this.count14) {
        this.count14 = value4;
      }
      this.count9 = value4;
    }
  }

  @Override
  public void visitLineNumber(int value, Label label) {
    if (this.byteVector4 == null) {
      this.byteVector4 = new ByteVector();
    }
    this.count12++;
    this.byteVector4.putShort(label.bytecodeOffset);
    this.byteVector4.putShort(value);
  }

  public void computeMaxStackAndLocal() {
    Handler handler;
    for (Handler handler3 = handler = this.handler2;
        handler3 != null;
        handler3 = handler = handler.nextHandler) {
      Label label3 = handler.handlerPc;
      Label label4 = handler.startPc;
      Label label5 = handler.endPc;
      Label label6 = label4;
      while (label6 != label5) {
        Label label7;
        if ((label4.value3 & 16) == 0) {
          label7 = label4;
          label4.outgoingEdges = new Edge(Integer.MAX_VALUE, label3, label4.outgoingEdges);
        } else {
          label7 = label4;
          label4.outgoingEdges.nextEdge.nextEdge =
              new Edge(Integer.MAX_VALUE, label3, label4.outgoingEdges.nextEdge.nextEdge);
        }
        label6 = label4 = label7.label2;
      }
    }
    if (this.enabled) {
      int value = 1;
      int value5 = 1;
      this.firstBasicBlock.markSubroutine((short) value);
      Label label8;
      for (int index = 1; value5 <= value; value5 = ++index) {
        for (Label label9 = label8 = this.firstBasicBlock;
            label9 != null;
            label9 = label8 = label8.label2) {
          if ((label8.value3 & 16) != 0 && label8.value2 == index) {
            Label label10 = label8.outgoingEdges.nextEdge.successor;
            if (label8.outgoingEdges.nextEdge.successor.value2 == 0) {
              label10.markSubroutine((short) (++value));
            }
          }
        }
      }
      Label label11;
      for (Label label12 = label11 = this.firstBasicBlock;
          label12 != null;
          label12 = label11 = label11.label2) {
        if ((label11.value3 & 16) != 0) {
          label11.outgoingEdges.nextEdge.successor.addSubroutineRetSuccessors(label11);
        }
      }
    }
    Label label13 = this.firstBasicBlock;
    this.firstBasicBlock.label = Label.EMPTY_LIST;
    int value7 = this.count;
    while (label13 != Label.EMPTY_LIST) {
      Label label14 = label13;
      label13 = label13.label;
      short value8 = label14.value6;
      int value9;
      if ((value9 = label14.value6 + label14.value4) > value7) {
        value7 = value9;
      }
      Edge edge = label14.outgoingEdges;
      if ((label14.value3 & 16) != 0) {
        edge = edge.nextEdge;
      }
      for (Edge edge2 = edge; edge2 != null; edge2 = edge = edge.nextEdge) {
        Label label15 = edge.successor;
        if (edge.successor.label == null) {
          label15.value6 = (short) (edge.info == Integer.MAX_VALUE ? 1 : value8 + edge.info);
          label15.label = label13;
          label13 = label15;
        }
      }
    }
    this.count = value7;
  }

  @Override
  public void visitTypeInsn(int value, String text) throws ClassNotFoundException {
    this.count8 = this.code.length;
    Symbol symbol3 = this.symbolTable.getSymbol3(text);
    this.code.put12(value, symbol3.index);
    if (this.label != null) {
      if (this.compute == 4 || this.compute == 3) {
        this.label.frame.execute(value, this.count8, symbol3, this.symbolTable);
        return;
      }
      if (value == 187) {
        int value2;
        if ((value2 = this.count9 + 1) > this.count14) {
          this.count14 = value2;
        }
        this.count9 = value2;
      }
    }
  }

  @Override
  public void visitJumpInsn(int value, Label label2) throws ClassNotFoundException {
    Label label3;
    this.count8 = this.code.length;
    int value2 = value >= 200 ? value - 33 : value;
    boolean matches = false;
    MethodWriter methodWriter;
    if ((label2.value3 & 4) != 0 && label2.bytecodeOffset - this.code.length < -32768) {
      Label label4;
      if (value2 == 167) {
        label4 = label2;
        this.code.putByte(200);
      } else if (value2 == 168) {
        label4 = label2;
        this.code.putByte(201);
      } else {
        this.code.putByte(value2 >= 198 ? value2 ^ 1 : (value2 + 1 ^ 1) - 1);
        this.code.putShort(8);
        this.code.putByte(220);
        matches = this.enabled2 = true;
        label4 = label2;
      }
      label4.put(this.code, this.code.length - 1, true);
      methodWriter = this;
    } else if (value2 != value) {
      this.code.putByte(value);
      methodWriter = this;
      label2.put(this.code, this.code.length - 1, true);
    } else {
      methodWriter = this;
      this.code.putByte(value2);
      label2.put(this.code, this.code.length - 1, false);
    }
    if (methodWriter.label != null) {
      Label label5;
      label65:
      {
        label5 = null;
        if (this.compute == 4) {
          this.label.frame.execute(value2, 0, null, null);
          Label canonicalInstance = label2.getCanonicalInstance();
          canonicalInstance.value3 = (short) (canonicalInstance.value3 | 2);
          this.processLabel3(0, label2);
          if (value2 != 167) {
            label3 = label5 = new Label();
            break label65;
          }
        } else {
          if (this.compute == 3) {
            label3 = label5;
            this.label.frame.execute(value2, 0, null, null);
            break label65;
          }
          if (this.compute == 2) {
            label3 = label5;
            this.count9 = this.count9 + STACK_SIZE_DELTA[value2];
            break label65;
          }
          if (value2 == 168) {
            if ((label2.value3 & 32) == 0) {
              label2.value3 = (short) (label2.value3 | 32);
              this.enabled = true;
            }
            this.label.value3 = (short) (this.label.value3 | 16);
            this.processLabel3(this.count9 + 1, label2);
            label3 = label5 = new Label();
            break label65;
          }
          this.count9 = this.count9 + STACK_SIZE_DELTA[value2];
          this.processLabel3(this.count9, label2);
        }
        label3 = label5;
      }
      if (label3 != null) {
        if (matches) {
          label5.value3 = (short) (label5.value3 | 2);
        }
        this.visitLabel(label5);
      }
      if (value2 == 167) {
        this.update3();
      }
    }
  }

  public void update3() {
    if (this.compute == 4) {
      Label label3 = new Label();
      label3.frame = new Frame(label3);
      label3.resolve(this.code.data, this.byteVector2, this.code.length);
      this.label2 = this.label2.label2 = label3;
      this.label = null;
    } else {
      if (this.compute == 1) {
        this.label.value4 = (short) this.count14;
        this.label = null;
      }
    }
  }

  public void putMethodInfo(ByteVector byteVector7) {
    int value = this.symbolTable.getCount() < 49 ? 1 : 0;
    int value2 = value != 0 ? 4096 : 0;
    byteVector7
        .putShort(this.accessFlags & ~value2)
        .putShort(this.nameIndex)
        .putShort(this.descriptorIndex);
    if (this.count2 != 0) {
      byteVector7.putByteArray(
          this.symbolTable.getSource().classFileBuffer, this.count2, this.count5);
    } else {
      int value3 = 0;
      if (this.code.length > 0) {
        value3++;
      }
      if (this.numberOfExceptions > 0) {
        value3++;
      }
      if ((this.accessFlags & 4096) != 0 && value != 0) {
        value3++;
      }
      if (this.signatureIndex != 0) {
        value3++;
      }
      if ((this.accessFlags & 131072) != 0) {
        value3++;
      }
      if (this.annotationWriter5 != null) {
        value3++;
      }
      if (this.annotationWriter4 != null) {
        value3++;
      }
      if (this.annotationWriters != null) {
        value3++;
      }
      if (this.annotationWriters2 != null) {
        value3++;
      }
      if (this.annotationWriter3 != null) {
        value3++;
      }
      if (this.annotationWriter2 != null) {
        value3++;
      }
      if (this.byteVector != null) {
        value3++;
      }
      if (this.byteVector6 != null) {
        value3++;
      }
      if (this.attribute != null) {
        value3 += this.attribute.getAttributeCount();
      }
      byteVector7.putShort(value3);
      if (this.code.length > 0) {
        int value4 = 10 + this.code.length + Handler.calculateCount2(this.handler2);
        int value5 = 0;
        if (this.byteVector2 != null) {
          int value6 = this.byteVector2.length;
          int value7 = value4;
          value5++;
          value4 = value7 + (byte) 8 + value6;
        }
        if (this.byteVector4 != null) {
          int value8 = this.byteVector4.length;
          int value9 = value4;
          value5++;
          value4 = value9 + (byte) 8 + value8;
        }
        if (this.byteVector5 != null) {
          int value10 = this.byteVector5.length;
          int value11 = value4;
          value5++;
          value4 = value11 + (byte) 8 + value10;
        }
        if (this.byteVector3 != null) {
          int value12 = this.byteVector3.length;
          int value13 = value4;
          value5++;
          value4 = value13 + (byte) 8 + value12;
        }
        if (this.annotationWriter != null) {
          AnnotationWriter annotationWriter7 = this.annotationWriter;
          int value14 = value4;
          value5++;
          value4 =
              value14
                  + annotationWriter7.computeAnnotationsSize(
                      (String) "RuntimeVisibleTypeAnnotations");
        }
        if (this.annotationWriter6 != null) {
          AnnotationWriter annotationWriter8 = this.annotationWriter6;
          int value15 = value4;
          value5++;
          value4 =
              value15
                  + annotationWriter8.computeAnnotationsSize(
                      (String) "RuntimeInvisibleTypeAnnotations");
        }
        if (this.attribute2 != null) {
          value4 +=
              this.attribute2.computeAttributesSize(
                  this.symbolTable, this.code.data, this.code.length, this.count, this.maxLocals);
          value5 += this.attribute2.getAttributeCount();
        }
        byteVector7
            .putShort(this.symbolTable.addConstantUtf8("Code"))
            .putInt(value4)
            .putShort(this.count)
            .putShort(this.maxLocals)
            .putInt(this.code.length)
            .putByteArray(this.code.data, 0, this.code.length);
        Handler.putExceptionTable(this.handler2, byteVector7);
        byteVector7.putShort(value5);
        if (this.byteVector2 != null) {
          int value16 = this.symbolTable.getCount() >= 50 ? 1 : 0;
          byteVector7
              .putShort(
                  this.symbolTable.addConstantUtf8(value16 != 0 ? "StackMapTable" : "StackMap"))
              .putInt(2 + this.byteVector2.length)
              .putShort(this.count7)
              .putByteArray(this.byteVector2.data, 0, this.byteVector2.length);
        }
        if (this.byteVector4 != null) {
          byteVector7
              .putShort(this.symbolTable.addConstantUtf8("LineNumberTable"))
              .putInt(2 + this.byteVector4.length)
              .putShort(this.count12)
              .putByteArray(this.byteVector4.data, 0, this.byteVector4.length);
        }
        if (this.byteVector5 != null) {
          byteVector7
              .putShort(this.symbolTable.addConstantUtf8("LocalVariableTable"))
              .putInt(2 + this.byteVector5.length)
              .putShort(this.count13)
              .putByteArray(this.byteVector5.data, 0, this.byteVector5.length);
        }
        if (this.byteVector3 != null) {
          byteVector7
              .putShort(this.symbolTable.addConstantUtf8("LocalVariableTypeTable"))
              .putInt(2 + this.byteVector3.length)
              .putShort(this.count11)
              .putByteArray(this.byteVector3.data, 0, this.byteVector3.length);
        }
        if (this.annotationWriter != null) {
          this.annotationWriter.putAnnotations(
              this.symbolTable.addConstantUtf8("RuntimeVisibleTypeAnnotations"), byteVector7);
        }
        if (this.annotationWriter6 != null) {
          this.annotationWriter6.putAnnotations(
              this.symbolTable.addConstantUtf8("RuntimeInvisibleTypeAnnotations"), byteVector7);
        }
        if (this.attribute2 != null) {
          this.attribute2.putAttributes(
              this.symbolTable,
              this.code.data,
              this.code.length,
              this.count,
              this.maxLocals,
              byteVector7);
        }
      }
      if (this.numberOfExceptions > 0) {
        byteVector7
            .putShort(this.symbolTable.addConstantUtf8("Exceptions"))
            .putInt(2 + 2 * this.numberOfExceptions)
            .putShort(this.numberOfExceptions);
        int[] values = this.exceptionIndexTable;
        int value17 = values.length;
        int value18;
        for (int value19 = value18 = 0; value19 < value17; value19 = value18) {
          int value20 = value18;
          value18++;
          byteVector7.putShort(values[value20]);
        }
      }
      Attribute.putAttributes(this.symbolTable, this.accessFlags, this.signatureIndex, byteVector7);
      AnnotationWriter.putAnnotations(
          this.symbolTable,
          this.annotationWriter5,
          this.annotationWriter4,
          this.annotationWriter3,
          this.annotationWriter2,
          byteVector7);
      if (this.annotationWriters != null) {
        int value21 = this.symbolTable.addConstantUtf8("RuntimeVisibleParameterAnnotations");
        int value22;
        ByteVector byteVector8;
        if (this.count4 == 0) {
          value22 = this.annotationWriters.length;
          byteVector8 = byteVector7;
        } else {
          value22 = this.count4;
          byteVector8 = byteVector7;
        }
        AnnotationWriter.putParameterAnnotations(
            value21, this.annotationWriters, value22, byteVector8);
      }
      if (this.annotationWriters2 != null) {
        int value23 = this.symbolTable.addConstantUtf8("RuntimeInvisibleParameterAnnotations");
        int value24;
        ByteVector byteVector9;
        if (this.count10 == 0) {
          value24 = this.annotationWriters2.length;
          byteVector9 = byteVector7;
        } else {
          value24 = this.count10;
          byteVector9 = byteVector7;
        }
        AnnotationWriter.putParameterAnnotations(
            value23, this.annotationWriters2, value24, byteVector9);
      }
      if (this.byteVector != null) {
        byteVector7
            .putShort(this.symbolTable.addConstantUtf8("AnnotationDefault"))
            .putInt(this.byteVector.length)
            .putByteArray(this.byteVector.data, 0, this.byteVector.length);
      }
      if (this.byteVector6 != null) {
        byteVector7
            .putShort(this.symbolTable.addConstantUtf8("MethodParameters"))
            .putInt(1 + this.byteVector6.length)
            .putByte(this.count6)
            .putByteArray(this.byteVector6.data, 0, this.byteVector6.length);
      }
      if (this.attribute != null) {
        this.attribute.putAttributes(this.symbolTable, byteVector7);
      }
    }
  }

  @Override
  public AnnotationVisitor visitLocalVariableAnnotation(
      int value,
      TypePath typePath,
      Label[] labels,
      Label[] labels2,
      int[] values,
      String text,
      boolean matches) {
    ByteVector byteVector;
    (byteVector = new ByteVector()).putByte(value >>> 24).putShort(labels.length);
    int value2 = 0;
    for (int value3 = value2; value3 < labels.length; value3 = value2) {
      ByteVector byteVector2 =
          byteVector
              .putShort(labels[value2].bytecodeOffset)
              .putShort(labels2[value2].bytecodeOffset - labels[value2].bytecodeOffset);
      int value4 = value2;
      value2++;
      byteVector2.putShort(values[value4]);
    }
    TypePath.put(typePath, byteVector);
    byteVector.putShort(this.symbolTable.addConstantUtf8(text)).putShort(0);
    return matches
        ? (this.annotationWriter =
            new AnnotationWriter(this.symbolTable, true, byteVector, this.annotationWriter))
        : (this.annotationWriter6 =
            new AnnotationWriter(this.symbolTable, true, byteVector, this.annotationWriter6));
  }

  @Override
  public void visitCode() {}

  public void update4() {
    if (this.counts2 != null) {
      if (this.byteVector2 == null) {
        this.byteVector2 = new ByteVector();
      }
      this.update5();
      this.count7++;
    }
    this.counts2 = this.counts;
    this.counts = null;
  }

  @Override
  public void visitTableSwitchInsn(int value, int value2, Label label, Label... label2)
      throws ClassNotFoundException {
    this.count8 = this.code.length;
    this.code.putByte(170).putByteArray(null, 0, (4 - this.code.length % 4) % 4);
    label.put(this.code, this.count8, true);
    this.code.putInt(value).putInt(value2);
    int value3 = ((Label[]) label2).length;
    int value4;
    for (int value5 = value4 = 0; value5 < value3; value5 = value4) {
      Label label3 = ((Label[]) label2)[value4];
      ByteVector byteVector = this.code;
      value4++;
      label3.put(byteVector, this.count8, true);
    }
    this.visitSwitchInsn(label, label2);
  }

  @Override
  public void visitLdcInsn(Object result) throws ClassNotFoundException {
    this.count8 = this.code.length;
    Symbol symbol;
    Symbol symbol2 = symbol = this.symbolTable.addConstant(result);
    int value2 = symbol2.index;
    char value3;
    boolean matches =
        symbol2.tag == 5
            || symbol.tag == 6
            || symbol.tag == 17 && ((value3 = symbol.value.charAt(0)) == 'J' || value3 == 'D');
    MethodWriter methodWriter;
    if (matches) {
      methodWriter = this;
      this.code.put12(20, value2);
    } else if (value2 >= 256) {
      methodWriter = this;
      this.code.put12(19, value2);
    } else {
      methodWriter = this;
      this.code.put11(18, value2);
    }
    if (methodWriter.label != null) {
      if (this.compute == 4 || this.compute == 3) {
        this.label.frame.execute(18, 0, symbol, this.symbolTable);
        return;
      }
      int value4;
      if ((value4 = this.count9 + (matches ? 2 : 1)) > this.count14) {
        this.count14 = value4;
      }
      this.count9 = value4;
    }
  }

  @Override
  public void visitVarInsn(int value, int value2) throws ClassNotFoundException {
    this.count8 = this.code.length;
    MethodWriter methodWriter;
    if (value2 < 4 && value != 169) {
      int value4;
      if (value < 54) {
        value4 = 26 + (value - 21 << 2) + value2;
        methodWriter = this;
      } else {
        value4 = 59 + (value - 54 << 2) + value2;
        methodWriter = this;
      }
      methodWriter.code.putByte(value4);
      methodWriter = this;
    } else if (value2 >= 256) {
      methodWriter = this;
      this.code.putByte(196).put12(value, value2);
    } else {
      methodWriter = this;
      this.code.put11(value, value2);
    }
    label65:
    {
      if (methodWriter.label != null) {
        if (this.compute == 4 || this.compute == 3) {
          methodWriter = this;
          this.label.frame.execute(value, value2, null, null);
          break label65;
        }
        if (value == 169) {
          methodWriter = this;
          this.label.value3 = (short) (this.label.value3 | 64);
          this.label.value5 = (short) this.count9;
          this.update3();
          break label65;
        }
        int value6;
        if ((value6 = this.count9 + STACK_SIZE_DELTA[value]) > this.count14) {
          this.count14 = value6;
        }
        this.count9 = value6;
      }
      methodWriter = this;
    }
    int value7;
    if (methodWriter.compute != 0
        && (value != 22 && value != 24 && value != 55 && value != 57
                ? (value7 = value2 + 1)
                : (value7 = value2 + 2))
            > this.maxLocals) {
      this.maxLocals = value7;
    }
    if (value >= 54 && this.compute == 4 && this.handler2 != null) {
      this.visitLabel(new Label());
    }
  }

  public MethodWriter(
      SymbolTable symbolTable2,
      int value,
      String name,
      String descriptor,
      String text3,
      String[] texts,
      int compute) {
    super(589824);
    this.code = new ByteVector();
    this.symbolTable = symbolTable2;
    this.accessFlags = "<init>".equals(name) ? value | 262144 : value;
    this.nameIndex = symbolTable2.addConstantUtf8(name);
    this.name = name;
    this.descriptorIndex = symbolTable2.addConstantUtf8(descriptor);
    this.descriptor = descriptor;
    this.signatureIndex = text3 == null ? 0 : symbolTable2.addConstantUtf8(text3);
    if (texts != null && texts.length > 0) {
      this.numberOfExceptions = texts.length;
      int value3 = 0;
      this.exceptionIndexTable = new int[this.numberOfExceptions];
      for (int index2 = 0; value3 < this.numberOfExceptions; value3 = index2) {
        int[] values = this.exceptionIndexTable;
        int value4 = symbolTable2.getSymbol3(texts[index2]).index;
        int value5 = index2;
        index2++;
        values[value5] = value4;
      }
    } else {
      this.numberOfExceptions = 0;
      this.exceptionIndexTable = null;
    }
    this.compute = compute;
    if (compute != 0) {
      int value6 = Type.getArgumentsAndReturnSizes(descriptor) >> 2;
      if ((value & 8) != 0) {
        value6--;
      }
      this.maxLocals = value6;
      this.currentLocals = value6;
      this.firstBasicBlock = new Label();
      this.visitLabel(this.firstBasicBlock);
    }
  }

  @Override
  public AnnotationVisitor visitAnnotationDefault() {
    this.byteVector = new ByteVector();
    return new AnnotationWriter(this.symbolTable, false, this.byteVector, null);
  }

  @Override
  public void visitIntInsn(int value, int value2) throws ClassNotFoundException {
    this.count8 = this.code.length;
    MethodWriter methodWriter;
    if (value == 17) {
      methodWriter = this;
      this.code.put12(value, value2);
    } else {
      methodWriter = this;
      this.code.put11(value, value2);
    }
    if (methodWriter.label != null) {
      if (this.compute == 4 || this.compute == 3) {
        this.label.frame.execute(value, value2, null, null);
        return;
      }
      if (value != 188) {
        int value3;
        if ((value3 = this.count9 + 1) > this.count14) {
          this.count14 = value3;
        }
        this.count9 = value3;
      }
    }
  }

  public void update5() {
    int value = this.counts[1];
    int value2 = this.counts[2];
    if (this.symbolTable.getCount() < 50) {
      this.byteVector2.putShort(this.counts[0]).putShort(value);
      this.processCount5(3, 3 + value);
      this.byteVector2.putShort(value2);
      this.processCount5(3 + value, 3 + value + value2);
    } else {
      short value3;
      int value4;
      int value5;
      int value6;
      short value7;
      label66:
      {
        value4 = this.count7 == 0 ? this.counts[0] : this.counts[0] - this.counts2[0] - 1;
        value6 = this.counts2[1];
        value5 = value - value6;
        value3 = 255;
        if (value2 == 0) {
          switch (value5) {
            case -3:
            case -2:
            case -1:
              value7 = value3 = 248;
              break label66;
            case 0:
              value3 = (short) (value4 < 64 ? 0 : 251);
              value7 = value3;
              break label66;
            case 1:
            case 2:
            case 3:
              value7 = value3 = 252;
              break label66;
          }
        } else if (value5 == 0 && value2 == 1) {
          value3 = (short) (value4 < 63 ? 64 : 247);
        }
        value7 = value3;
      }
      label60:
      {
        if (value7 != 255) {
          int value8 = 3;
          int value9;
          for (int value10 = value9 = 0; value10 < value6 && value9 < value; value10 = ++value9) {
            if (this.counts[value8] != this.counts2[value8]) {
              value7 = 255;
              break label60;
            }
            value8++;
          }
        }
        value7 = value3;
      }
      switch (value7) {
        case 0:
          this.byteVector2.putByte(value4);
          return;
        case 64:
          this.byteVector2.putByte(64 + value4);
          this.processCount5(3 + value, 4 + value);
          return;
        case 247:
          this.byteVector2.putByte(247).putShort(value4);
          this.processCount5(3 + value, 4 + value);
          return;
        case 248:
          this.byteVector2.putByte(251 + value5).putShort(value4);
          return;
        case 251:
          this.byteVector2.putByte(251).putShort(value4);
          return;
        case 252:
          this.byteVector2.putByte(251 + value5).putShort(value4);
          this.processCount5(3 + value6, 3 + value);
          return;
        case 255:
        default:
          this.byteVector2.putByte(255).putShort(value4).putShort(value);
          this.processCount5(3, 3 + value);
          this.byteVector2.putShort(value2);
          this.processCount5(3 + value, 3 + value + value2);
      }
    }
  }

  public void computeAllFrames() throws ClassNotFoundException {
    Handler handler;
    for (Handler handler3 = handler = this.handler2;
        handler3 != null;
        handler3 = handler = handler.nextHandler) {
      String text =
          handler.catchTypeDescriptor == null ? "java/lang/Throwable" : handler.catchTypeDescriptor;
      int abstractTypeFromInternalName =
          Frame.getAbstractTypeFromInternalName(this.symbolTable, text);
      Label label3;
      Label label4 = label3 = handler.handlerPc.getCanonicalInstance();
      label4.value3 = (short) (label4.value3 | 2);
      Label canonicalInstance = handler.startPc.getCanonicalInstance();
      Label canonicalInstance2 = handler.endPc.getCanonicalInstance();
      for (Label label5 = canonicalInstance;
          label5 != canonicalInstance2;
          label5 = canonicalInstance = canonicalInstance.label2) {
        canonicalInstance.outgoingEdges =
            new Edge(abstractTypeFromInternalName, label3, canonicalInstance.outgoingEdges);
      }
    }
    Frame frame2 = this.firstBasicBlock.frame;
    frame2.setInputFrameFromDescriptor(
        this.symbolTable, this.accessFlags, this.descriptor, this.maxLocals);
    frame2.accept(this);
    Label label6 = this.firstBasicBlock;
    label6.label = Label.EMPTY_LIST;
    int value = 0;
    while (label6 != Label.EMPTY_LIST) {
      Label label7 = label6;
      label6 = label6.label;
      label7.label = null;
      label7.value3 = (short) (label7.value3 | 8);
      int value2;
      if ((value2 = label7.frame.getCounts5() + label7.value4) > value) {
        value = value2;
      }
      Edge edge;
      for (Edge edge2 = edge = label7.outgoingEdges; edge2 != null; edge2 = edge = edge.nextEdge) {
        Label canonicalInstance3 = edge.successor.getCanonicalInstance();
        if (label7.frame.merge(this.symbolTable, canonicalInstance3.frame, edge.info)
            && canonicalInstance3.label == null) {
          canonicalInstance3.label = label6;
          label6 = canonicalInstance3;
        }
      }
    }
    Label label8;
    for (Label label9 = label8 = this.firstBasicBlock;
        label9 != null;
        label9 = label8 = label8.label2) {
      if ((label8.value3 & 10) == 10) {
        label8.frame.accept(this);
      }
      if ((label8.value3 & 8) == 0) {
        Label label10 = label8.label2;
        int value5 = label8.bytecodeOffset;
        int value6;
        if ((value6 = (label10 == null ? this.code.length : label10.bytecodeOffset) - 1)
            >= value5) {
          int value7;
          for (int value8 = value7 = value5; value8 < value6; value8 = value7) {
            byte value9 = 0;
            int value10 = value7;
            byte[] values = this.code.data;
            value7++;
            values[value10] = value9;
          }
          this.code.data[value6] = -65;
          int value11 = this.visitFrameStart(value5, 0, 1);
          this.counts[value11] =
              Frame.getAbstractTypeFromInternalName(this.symbolTable, "java/lang/Throwable");
          this.update4();
          this.handler2 = Handler.removeRange(this.handler2, label8, label10);
          value = Math.max(value, 1);
        }
      }
    }
    this.count = value;
  }

  public void processValue(Object result) {
    if (result instanceof Integer) {
      this.byteVector2.putByte((Integer) result);
    } else if (result instanceof String) {
      this.byteVector2.putByte(7).putShort(this.symbolTable.getSymbol3((String) result).index);
    } else {
      this.byteVector2.putByte(8);
      ((Label) result).put(this.byteVector2);
    }
  }

  @Override
  public void visitFieldInsn(int value, String text, String text2, String text3)
      throws ClassNotFoundException {
    this.count8 = this.code.length;
    Symbol symbol = this.symbolTable.addConstantFieldref(text, text2, text3);
    this.code.put12(value, symbol.index);
    if (this.label != null) {
      if (this.compute == 4 || this.compute == 3) {
        this.label.frame.execute(value, 0, symbol, this.symbolTable);
        return;
      }
      int value2;
      int value3;
      label74:
      {
        char value4 = text3.charAt(0);
        switch (value) {
          case 178:
            value3 = value2 = this.count9 + (value4 != 'D' && value4 != 'J' ? 1 : 2);
            break label74;
          case 179:
            value3 = value2 = this.count9 + (value4 != 'D' && value4 != 'J' ? -1 : -2);
            break label74;
          case 180:
            value3 = value2 = this.count9 + (value4 != 'D' && value4 != 'J' ? 0 : 1);
            break label74;
          case 181:
        }
        value3 = value2 = this.count9 + (value4 != 'D' && value4 != 'J' ? -2 : -3);
      }
      if (value3 > this.count14) {
        this.count14 = value2;
      }
      this.count9 = value2;
    }
  }

  public int computeMethodInfoSize() {
    if (this.count2 != 0) {
      return 6 + this.count5;
    }
    int value = 8;
    if (this.code.length > 0) {
      if (this.code.length > 65535) {
        throw new MethodTooLargeException(
            this.symbolTable.getClassName(), this.name, this.descriptor, this.code.length);
      }
      this.symbolTable.addConstantUtf8("Code");
      value += 16 + this.code.length + Handler.calculateCount2(this.handler2);
      if (this.byteVector2 != null) {
        int value2 = this.symbolTable.getCount() >= 50 ? 1 : 0;
        this.symbolTable.addConstantUtf8(value2 != 0 ? "StackMapTable" : "StackMap");
        value += 8 + this.byteVector2.length;
      }
      if (this.byteVector4 != null) {
        this.symbolTable.addConstantUtf8("LineNumberTable");
        value += 8 + this.byteVector4.length;
      }
      if (this.byteVector5 != null) {
        this.symbolTable.addConstantUtf8("LocalVariableTable");
        value += 8 + this.byteVector5.length;
      }
      if (this.byteVector3 != null) {
        this.symbolTable.addConstantUtf8("LocalVariableTypeTable");
        value += 8 + this.byteVector3.length;
      }
      if (this.annotationWriter != null) {
        value += this.annotationWriter.computeAnnotationsSize("RuntimeVisibleTypeAnnotations");
      }
      if (this.annotationWriter6 != null) {
        value += this.annotationWriter6.computeAnnotationsSize("RuntimeInvisibleTypeAnnotations");
      }
      if (this.attribute2 != null) {
        value +=
            this.attribute2.computeAttributesSize(
                this.symbolTable, this.code.data, this.code.length, this.count, this.maxLocals);
      }
    }
    if (this.numberOfExceptions > 0) {
      this.symbolTable.addConstantUtf8("Exceptions");
      value += 8 + 2 * this.numberOfExceptions;
    }
    int value3 =
        value
            + Attribute.computeAttributesSize(
                this.symbolTable, this.accessFlags, this.signatureIndex)
            + AnnotationWriter.computeAnnotationsSize(
                this.annotationWriter5,
                this.annotationWriter4,
                this.annotationWriter3,
                this.annotationWriter2);
    if (this.annotationWriters != null) {
      value3 +=
          AnnotationWriter.computeParameterAnnotationsSize(
              "RuntimeVisibleParameterAnnotations",
              this.annotationWriters,
              this.count4 == 0 ? this.annotationWriters.length : this.count4);
    }
    if (this.annotationWriters2 != null) {
      value3 +=
          AnnotationWriter.computeParameterAnnotationsSize(
              "RuntimeInvisibleParameterAnnotations",
              this.annotationWriters2,
              this.count10 == 0 ? this.annotationWriters2.length : this.count10);
    }
    if (this.byteVector != null) {
      this.symbolTable.addConstantUtf8("AnnotationDefault");
      value3 += 6 + this.byteVector.length;
    }
    if (this.byteVector6 != null) {
      this.symbolTable.addConstantUtf8("MethodParameters");
      value3 += 7 + this.byteVector6.length;
    }
    if (this.attribute != null) {
      value3 += this.attribute.computeAttributesSize(this.symbolTable);
    }
    return value3;
  }

  @Override
  public AnnotationVisitor createAnnotationVisitor2(
      int value, TypePath typePath, String text, boolean matches) {
    return matches
        ? (this.annotationWriter3 =
            AnnotationWriter.create(
                this.symbolTable, value, typePath, text, this.annotationWriter3))
        : (this.annotationWriter2 =
            AnnotationWriter.create(
                this.symbolTable, value, typePath, text, this.annotationWriter2));
  }

  public void processLabel3(int value, Label label2) {
    this.label.outgoingEdges = new Edge(value, label2, this.label.outgoingEdges);
  }

  public boolean isEnabled2() {
    return this.enabled2;
  }

  public final void collectAttributePrototypes(Attribute$Set attribute$Set) {
    attribute$Set.addAttributes(this.attribute);
    attribute$Set.addAttributes(this.attribute2);
  }

  @Override
  public void visitLookupSwitchInsn(Label label, int[] values, Label[] labels)
      throws ClassNotFoundException {
    this.count8 = this.code.length;
    this.code.putByte(171).putByteArray(null, 0, (4 - this.code.length % 4) % 4);
    label.put(this.code, this.count8, true);
    this.code.putInt(labels.length);
    int value = 0;
    for (int value2 = value; value2 < labels.length; value2 = value) {
      this.code.putInt(values[value]);
      Label label2 = labels[value];
      ByteVector byteVector = this.code;
      value++;
      label2.put(byteVector, this.count8, true);
    }
    this.visitSwitchInsn(label, labels);
  }

  @Override
  public AnnotationVisitor createAnnotationVisitor(
      int value, TypePath typePath, String text, boolean matches) {
    return matches
        ? (this.annotationWriter =
            AnnotationWriter.create(this.symbolTable, value, typePath, text, this.annotationWriter))
        : (this.annotationWriter6 =
            AnnotationWriter.create(
                this.symbolTable, value, typePath, text, this.annotationWriter6));
  }

  @Override
  public AnnotationVisitor visitAnnotation(String text, boolean matches) {
    return matches
        ? (this.annotationWriter5 =
            AnnotationWriter.create(this.symbolTable, text, this.annotationWriter5))
        : (this.annotationWriter4 =
            AnnotationWriter.create(this.symbolTable, text, this.annotationWriter4));
  }

  @Override
  public void visitInvokeDynamicInsn(String text, String text2, Handle handle, Object... result)
      throws ClassNotFoundException {
    this.count8 = this.code.length;
    Symbol symbol = this.symbolTable.addConstantInvokeDynamic(text, text2, handle, result);
    this.code.put12(186, symbol.index);
    this.code.putShort(0);
    if (this.label != null) {
      if (this.compute == 4 || this.compute == 3) {
        this.label.frame.execute(186, 0, symbol, this.symbolTable);
        return;
      }
      int value;
      int value2 = ((value = symbol.getArgumentsAndReturnSizes()) & 3) - (value >> 2) + 1;
      int value3;
      if ((value3 = this.count9 + value2) > this.count14) {
        this.count14 = value3;
      }
      this.count9 = value3;
    }
  }

  @Override
  public AnnotationVisitor visitParameterAnnotation(int value, String text, boolean matches) {
    if (matches) {
      if (this.annotationWriters == null) {
        this.annotationWriters = new AnnotationWriter[Type.getArgumentCount(this.descriptor)];
      }
      return this.annotationWriters[value] =
          AnnotationWriter.create(this.symbolTable, text, this.annotationWriters[value]);
    } else {
      if (this.annotationWriters2 == null) {
        this.annotationWriters2 = new AnnotationWriter[Type.getArgumentCount(this.descriptor)];
      }
      return this.annotationWriters2[value] =
          AnnotationWriter.create(this.symbolTable, text, this.annotationWriters2[value]);
    }
  }

  @Override
  public void visitMultiANewArrayInsn(String text, int value) throws ClassNotFoundException {
    this.count8 = this.code.length;
    Symbol symbol3 = this.symbolTable.getSymbol3(text);
    this.code.put12(197, symbol3.index).putByte(value);
    if (this.label != null) {
      if (this.compute == 4 || this.compute == 3) {
        this.label.frame.execute(197, value, symbol3, this.symbolTable);
        return;
      }
      this.count9 += 1 - value;
    }
  }

  public void processCount7(int value, int value2) {
    this.count2 = value + 6;
    this.count5 = value2 - 6;
  }
}
