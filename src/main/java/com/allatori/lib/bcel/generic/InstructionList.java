package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.util.ByteSequence;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class InstructionList implements Serializable {

  public int length;

  public List observers;

  public int[] byte_positions;

  public InstructionHandle end;

  public InstructionHandle start = null;

  public InstructionHandle findHandle(int value) {
    return findHandle(this.getInstructionHandles(), this.byte_positions, this.length, value);
  }

  public void redirectLocalVariables(
      LocalVariableGen[] localVariableGens,
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2) {
    int value;
    for (int value2 = value = 0; value2 < localVariableGens.length; value2 = ++value) {
      InstructionHandle instructionHandle3 = localVariableGens[value].getInstructionHandle();
      InstructionHandle instructionHandle22 = localVariableGens[value].getInstructionHandle2();
      if (instructionHandle3 == instructionHandle) {
        localVariableGens[value].processInstructionHandle2(instructionHandle2);
      }
      if (instructionHandle22 == instructionHandle) {
        localVariableGens[value].processInstructionHandle(instructionHandle2);
      }
    }
  }

  public void processInstructionHandle(
      InstructionHandle instructionHandle3, InstructionHandle instructionHandle4)
      throws TargetLostException {
    this.processInstructionHandle4(
        instructionHandle3.instructionHandle, instructionHandle4.instructionHandle2);
  }

  public InstructionHandle createInstructionHandle(
      Instruction instruction, Instruction instruction2) {
    return this.createInstructionHandle(instruction, new InstructionList(instruction2));
  }

  public Instruction[] getInstructions() {
    ByteSequence byteSequence = new ByteSequence(this.getByteCode());
    ArrayList values = new ArrayList();
    try {
      while (byteSequence.available() > 0) {
        values.add(Instruction.readInstruction(byteSequence));
      }
    } catch (IOException oException) {
      throw new ClassGenException(oException.toString());
    }
    return (Instruction[]) values.toArray(new Instruction[values.size()]);
  }

  public InstructionHandle getInstructionHandle(Instruction instruction) {
    InstructionHandle instructionHandle = InstructionHandle.getInstructionHandle(instruction);
    this.processInstructionHandle(instructionHandle);
    return instructionHandle;
  }

  public InstructionHandle createInstructionHandle(
      InstructionHandle instructionHandle, Instruction instruction) {
    return this.createInstructionHandle(instructionHandle, new InstructionList(instruction));
  }

  public InstructionHandle getInstructionHandle2(Instruction instruction2) {
    InstructionHandle instructionHandle;
    for (InstructionHandle instructionHandle3 = instructionHandle = this.start;
        instructionHandle3 != null;
        instructionHandle3 = instructionHandle = instructionHandle.instructionHandle2) {
      if (instructionHandle.instruction == instruction2) {
        return instructionHandle;
      }
    }
    return null;
  }

  public int getLength() {
    return this.length;
  }

  public void addObserver(InstructionListObserver instructionListObserver) {
    if (this.observers == null) {
      this.observers = new ArrayList();
    }
    this.observers.add(instructionListObserver);
  }

  public InstructionHandle createInstructionHandle2(
      InstructionHandle instructionHandle, Instruction instruction) {
    return this.createInstructionHandle2(instructionHandle, new InstructionList(instruction));
  }

  public BranchHandle getBranchHandle(BranchInstruction branchInstruction) {
    BranchHandle branchHandle = BranchHandle.getBranchHandle(branchInstruction);
    this.processInstructionHandle(branchHandle);
    return branchHandle;
  }

  public InstructionHandle createInstructionHandle(
      InstructionHandle instructionHandle3, InstructionList instructionList) {
    if (instructionList == null) {
      throw new ClassGenException("Inserting null InstructionList");
    }
    if (instructionList.isEmpty()) {
      return instructionHandle3;
    }
    InstructionHandle instructionHandle4 = instructionHandle3.instructionHandle;
    InstructionHandle instructionHandle5 = instructionList.start;
    instructionHandle3.instructionHandle = instructionList.end;
    instructionList.end.instructionHandle2 = instructionHandle3;
    InstructionList instructionList2;
    if ((instructionList.start.instructionHandle = instructionHandle4) != null) {
      instructionList2 = this;
      instructionHandle4.instructionHandle2 = instructionList.start;
    } else {
      instructionList2 = this;
      this.start = instructionList.start;
    }
    instructionList2.length = instructionList2.length + instructionList.length;
    instructionList.update4();
    return instructionHandle5;
  }

  public void update() {
    this.setPositions(false);
  }

  public void removeObserver(InstructionListObserver instructionListObserver) {
    if (this.observers != null) {
      this.observers.remove(instructionListObserver);
    }
  }

  public BranchHandle getBranchHandle2(BranchInstruction branchInstruction) {
    BranchHandle branchHandle = BranchHandle.getBranchHandle(branchInstruction);
    this.processInstructionHandle3(branchHandle);
    return branchHandle;
  }

  public void update2() {
    Iterator iterator2;
    if (this.observers != null) {
      for (Iterator iterator3 = iterator2 = this.observers.iterator();
          iterator3.hasNext();
          iterator3 = iterator2) {
        ((InstructionListObserver) iterator2.next()).notify(this);
      }
    }
  }

  public Iterator iterator() {
    return new InstructionList$1(this);
  }

  public BranchHandle createBranchHandle(
      InstructionHandle instructionHandle, BranchInstruction branchInstruction) {
    BranchHandle branchHandle = BranchHandle.getBranchHandle(branchInstruction);
    InstructionList instructionList = new InstructionList();
    instructionList.processInstructionHandle(branchHandle);
    this.createInstructionHandle2(instructionHandle, instructionList);
    return branchHandle;
  }

  public void delete(Instruction instruction) throws TargetLostException {
    InstructionHandle instructionHandle;
    if ((instructionHandle = this.getInstructionHandle2(instruction)) == null) {
      throw new ClassGenException(
          ("Instruction " + instruction + " is not contained in this list."));
    }
    this.processInstructionHandle2(instructionHandle);
  }

  public void delete(Instruction instruction, Instruction instruction2) throws TargetLostException {
    InstructionHandle instructionHandle;
    if ((instructionHandle = this.getInstructionHandle2(instruction)) == null) {
      throw new ClassGenException(
          ("Instruction " + instruction + " is not contained in this list."));
    }
    InstructionHandle instructionHandle2;
    if ((instructionHandle2 = this.getInstructionHandle3(instruction2)) == null) {
      throw new ClassGenException(
          ("Instruction " + instruction2 + " is not contained in this list."));
    }
    this.processInstructionHandle(instructionHandle, instructionHandle2);
  }

  public InstructionHandle createInstructionHandle2(
      InstructionHandle instructionHandle3, InstructionList instructionList) {
    if (instructionList == null) {
      throw new ClassGenException("Appending null InstructionList");
    }
    if (instructionList.isEmpty()) {
      return instructionHandle3;
    }
    InstructionHandle instructionHandle4 = instructionHandle3.instructionHandle2;
    InstructionHandle instructionHandle5 = instructionList.start;
    instructionHandle3.instructionHandle2 = instructionList.start;
    instructionList.start.instructionHandle = instructionHandle3;
    InstructionList instructionList2;
    if ((instructionList.end.instructionHandle2 = instructionHandle4) != null) {
      instructionList2 = this;
      instructionHandle4.instructionHandle = instructionList.end;
    } else {
      instructionList2 = this;
      this.end = instructionList.end;
    }
    instructionList2.length = instructionList2.length + instructionList.length;
    instructionList.update4();
    return instructionHandle5;
  }

  public InstructionHandle getInstructionHandle(
      Instruction instruction, CompoundInstruction compoundInstruction) {
    return this.createInstructionHandle(instruction, compoundInstruction.getInstructionList());
  }

  public boolean contains(InstructionHandle instructionHandle) {
    if (instructionHandle == null) {
      return false;
    }
    InstructionHandle instructionHandle3;
    for (InstructionHandle instructionHandle4 = instructionHandle3 = this.start;
        instructionHandle4 != null;
        instructionHandle4 = instructionHandle3 = instructionHandle3.instructionHandle2) {
      if (instructionHandle3 == instructionHandle) {
        return true;
      }
    }
    return false;
  }

  public InstructionList(byte[] values) {
    this.end = null;
    this.length = 0;
    ByteSequence byteSequence = new ByteSequence(values);
    InstructionHandle[] instructionHandles = new InstructionHandle[values.length];
    int[] values2 = new int[values.length];
    int value = 0;
    try {
      while (byteSequence.available() > 0) {
        int index = byteSequence.getIndex();
        values2[value] = index;
        InstructionHandle instructionHandle;
        Instruction instruction2;
        ((instruction2 = Instruction.readInstruction(byteSequence)) instanceof BranchInstruction
                ? (instructionHandle = this.getBranchHandle((BranchInstruction) instruction2))
                : (instructionHandle = this.getInstructionHandle(instruction2)))
            .setPosition(index);
        InstructionHandle instructionHandle2 = instructionHandle;
        int value2 = value;
        value++;
        instructionHandles[value2] = instructionHandle2;
      }
    } catch (IOException oException) {
      throw new ClassGenException(oException.toString());
    }
    this.byte_positions = new int[value];
    int value3 = 0;
    System.arraycopy(values2, 0, this.byte_positions, 0, value);
    for (int index2 = 0; value3 < value; value3 = ++index2) {
      if (instructionHandles[index2] instanceof BranchHandle) {
        BranchInstruction branchInstruction =
            (BranchInstruction) instructionHandles[index2].instruction;
        int value4 = branchInstruction.count + branchInstruction.getIndex();
        InstructionHandle instructionHandle3;
        if ((instructionHandle3 = findHandle(instructionHandles, values2, value, value4)) == null) {
          throw new ClassGenException(("Couldn't find target for branch: " + branchInstruction));
        }
        branchInstruction.setTarget(instructionHandle3);
        if (branchInstruction instanceof Select) {
          Select select;
          int[] indices = (select = (Select) branchInstruction).getIndices();
          int value5;
          for (int value6 = value5 = 0; value6 < indices.length; value6 = value5) {
            int value7 = branchInstruction.count + indices[value5];
            InstructionHandle instructionHandle4;
            if ((instructionHandle4 = findHandle(instructionHandles, values2, value, value7))
                == null) {
              throw new ClassGenException(
                  ("Couldn't find target for switch: " + branchInstruction));
            }
            int value8 = value5;
            Select select2 = select;
            value5++;
            select2.setTarget(value8, instructionHandle4);
          }
        }
      }
    }
  }

  public InstructionHandle createInstructionHandle(
      Instruction instruction, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    if ((instructionHandle = this.getInstructionHandle3(instruction)) == null) {
      throw new ClassGenException(
          ("Instruction " + instruction + " is not contained in this list."));
    } else {
      return this.createInstructionHandle2(instructionHandle, instructionList);
    }
  }

  public InstructionHandle getInstructionHandle(CompoundInstruction compoundInstruction) {
    return this.append(compoundInstruction.getInstructionList());
  }

  public InstructionHandle getInstructionHandle3(Instruction instruction2) {
    InstructionHandle instructionHandle2;
    for (InstructionHandle instructionHandle3 = instructionHandle2 = this.end;
        instructionHandle3 != null;
        instructionHandle3 = instructionHandle2 = instructionHandle2.instructionHandle) {
      if (instructionHandle2.instruction == instruction2) {
        return instructionHandle2;
      }
    }
    return null;
  }

  public void move(
      InstructionHandle instructionHandle3,
      InstructionHandle instructionHandle4,
      InstructionHandle instructionHandle5) {
    if (instructionHandle3 != null && instructionHandle4 != null) {
      if (instructionHandle5 != instructionHandle3 && instructionHandle5 != instructionHandle4) {
        InstructionHandle instructionHandle6;
        for (InstructionHandle instructionHandle7 = instructionHandle6 = instructionHandle3;
            instructionHandle7 != instructionHandle4.instructionHandle2;
            instructionHandle7 = instructionHandle6 = instructionHandle6.instructionHandle2) {
          if (instructionHandle6 == null) {
            throw new ClassGenException(
                ("Invalid range: From " + instructionHandle3 + " to " + instructionHandle4));
          }
          if (instructionHandle6 == instructionHandle5) {
            throw new ClassGenException(
                ("Invalid range: From "
                    + instructionHandle3
                    + " to "
                    + instructionHandle4
                    + " contains target "
                    + instructionHandle5));
          }
        }
        InstructionHandle instructionHandle8 = instructionHandle3.instructionHandle;
        InstructionHandle instructionHandle9 = instructionHandle4.instructionHandle2;
        InstructionHandle instructionHandle10;
        if (instructionHandle8 != null) {
          instructionHandle10 = instructionHandle8.instructionHandle2 = instructionHandle9;
        } else {
          this.start = instructionHandle9;
          instructionHandle10 = instructionHandle9;
        }
        if (instructionHandle10 != null) {
          instructionHandle10 = instructionHandle3;
          instructionHandle9.instructionHandle = instructionHandle8;
        } else {
          this.end = instructionHandle8;
          instructionHandle10 = instructionHandle3;
        }
        instructionHandle10.instructionHandle = instructionHandle4.instructionHandle2 = null;
        if (instructionHandle5 == null) {
          if (this.start != null) {
            this.start.instructionHandle = instructionHandle4;
          }
          instructionHandle4.instructionHandle2 = this.start;
          this.start = instructionHandle3;
        } else {
          InstructionHandle instructionHandle11 = instructionHandle5.instructionHandle2;
          instructionHandle5.instructionHandle2 = instructionHandle3;
          instructionHandle3.instructionHandle = instructionHandle5;
          instructionHandle4.instructionHandle2 = instructionHandle11;
          if (instructionHandle11 != null) {
            instructionHandle11.instructionHandle = instructionHandle4;
          } else {
            this.end = instructionHandle4;
          }
        }
      } else {
        throw new ClassGenException(
            ("Invalid range: From "
                + instructionHandle3
                + " to "
                + instructionHandle4
                + " contains target "
                + instructionHandle5));
      }
    } else {
      throw new ClassGenException(
          ("Invalid null handle: From " + instructionHandle3 + " to " + instructionHandle4));
    }
  }

  public void replaceConstantPool(
      ConstantPoolGen constantPoolGen, ConstantPoolGen constantPoolGen2) {
    InstructionHandle instructionHandle;
    for (InstructionHandle instructionHandle3 = instructionHandle = this.start;
        instructionHandle3 != null;
        instructionHandle3 = instructionHandle = instructionHandle.instructionHandle2) {
      Instruction instruction2 = instructionHandle.instruction;
      if (instructionHandle.instruction instanceof CPInstruction) {
        CPInstruction cPInstruction = (CPInstruction) instruction2;
        Constant constant = constantPoolGen.getConstant(cPInstruction.getIndex());
        cPInstruction.setIndex(constantPoolGen2.addConstant(constant, constantPoolGen));
      }
    }
  }

  public InstructionHandle append(InstructionList instructionList) {
    if (instructionList == null) {
      throw new ClassGenException("Appending null InstructionList");
    } else if (instructionList.isEmpty()) {
      return null;
    } else if (this.isEmpty()) {
      this.start = instructionList.start;
      this.end = instructionList.end;
      this.length = instructionList.length;
      instructionList.update4();
      return this.start;
    } else {
      return this.createInstructionHandle2(this.end, instructionList);
    }
  }

  public static InstructionHandle access$000(InstructionList instructionList) {
    return instructionList.start;
  }

  public InstructionHandle getInstructionHandle(
      InstructionHandle instructionHandle, CompoundInstruction compoundInstruction) {
    return this.createInstructionHandle(
        instructionHandle, compoundInstruction.getInstructionList());
  }

  public InstructionHandle getStart() {
    return this.start;
  }

  public int getLength2() {
    return this.length;
  }

  public void processInstructionHandle2(
      InstructionHandle instructionHandle, InstructionHandle instructionHandle3) {
    InstructionHandle instructionHandle4;
    for (InstructionHandle instructionHandle5 = instructionHandle4 = this.start;
        instructionHandle5 != null;
        instructionHandle5 = instructionHandle4 = instructionHandle4.instructionHandle2) {
      Instruction instruction;
      if ((instruction = instructionHandle4.getInstruction()) instanceof BranchInstruction) {
        BranchInstruction branchInstruction;
        if ((branchInstruction = (BranchInstruction) instruction).getTarget()
            == instructionHandle) {
          branchInstruction.setTarget(instructionHandle3);
        }
        if (branchInstruction instanceof Select) {
          InstructionHandle[] targets = ((Select) branchInstruction).getTargets();
          int value;
          for (int value2 = value = 0; value2 < targets.length; value2 = ++value) {
            if (targets[value] == instructionHandle) {
              ((Select) branchInstruction).setTarget(value, instructionHandle3);
            }
          }
        }
      }
    }
  }

  public InstructionHandle getInstructionHandle2(
      Instruction instruction, CompoundInstruction compoundInstruction) {
    return this.createInstructionHandle2(instruction, compoundInstruction.getInstructionList());
  }

  public void update3() {
    InstructionHandle instructionHandle2;
    for (InstructionHandle instructionHandle3 = instructionHandle2 = this.end;
        instructionHandle3 != null;
        instructionHandle3 = instructionHandle2 = instructionHandle2.instructionHandle) {
      instructionHandle2.update();
    }
    this.update4();
  }

  public void processInstructionHandle3(
      InstructionHandle instructionHandle, InstructionHandle instructionHandle2) {
    this.move(instructionHandle, instructionHandle, instructionHandle2);
  }

  public void processInstructionHandle(InstructionHandle instructionHandle3) {
    InstructionList instructionList;
    if (this.isEmpty()) {
      this.start = this.end = instructionHandle3;
      instructionHandle3.instructionHandle2 = instructionHandle3.instructionHandle = null;
      instructionList = this;
    } else {
      instructionList = this;
      this.end.instructionHandle2 = instructionHandle3;
      instructionHandle3.instructionHandle = this.end;
      instructionHandle3.instructionHandle2 = null;
      this.end = instructionHandle3;
    }
    instructionList.length++;
  }

  public InstructionHandle createInstructionHandle2(
      Instruction instruction, InstructionList instructionList) {
    InstructionHandle instructionHandle;
    if ((instructionHandle = this.getInstructionHandle2(instruction)) == null) {
      throw new ClassGenException(
          ("Instruction " + instruction + " is not contained in this list."));
    } else {
      return this.createInstructionHandle(instructionHandle, instructionList);
    }
  }

  public byte[] getByteCode() {
    this.update();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    try {
      for (InstructionHandle instructionHandle = this.start;
          instructionHandle != null;
          instructionHandle = instructionHandle.instructionHandle2) {
        instructionHandle.instruction.dump(dataOutputStream);
      }
    } catch (IOException oException) {
      System.err.println(oException);
      return null;
    }
    return byteArrayOutputStream.toByteArray();
  }

  public String toString(boolean matches) {
    StringBuffer stringBuffer = new StringBuffer();
    InstructionHandle instructionHandle;
    for (InstructionHandle instructionHandle3 = instructionHandle = this.start;
        instructionHandle3 != null;
        instructionHandle3 = instructionHandle) {
      stringBuffer.append(instructionHandle.toString(matches)).append("\n");
      instructionHandle = instructionHandle.instructionHandle2;
    }
    return stringBuffer.toString();
  }

  public InstructionList(CompoundInstruction compoundInstruction) {
    super();
    this.start = null;
    this.end = null;
    this.length = 0;
    this.append(compoundInstruction.getInstructionList());
  }

  public InstructionHandle getEnd() {
    return this.end;
  }

  public InstructionHandle getInstructionHandle2(
      InstructionHandle instructionHandle, CompoundInstruction compoundInstruction) {
    return this.createInstructionHandle2(
        instructionHandle, compoundInstruction.getInstructionList());
  }

  public InstructionList(BranchInstruction branchInstruction) {
    super();
    this.start = null;
    this.end = null;
    this.length = 0;
    this.getBranchHandle(branchInstruction);
  }

  public void processInstructionHandle4(
      InstructionHandle instructionHandle3, InstructionHandle instructionHandle4)
      throws TargetLostException {
    InstructionHandle instructionHandle5;
    InstructionHandle instructionHandle6;
    InstructionHandle instructionHandle7;
    if (instructionHandle3 == null && instructionHandle4 == null) {
      instructionHandle6 = this.start;
      instructionHandle5 = this.start;
      this.start = this.end = null;
      instructionHandle7 = instructionHandle5;
    } else {
      if (instructionHandle3 == null) {
        instructionHandle5 = this.start;
        this.start = instructionHandle4;
        instructionHandle7 = instructionHandle4;
      } else {
        instructionHandle5 = instructionHandle3.instructionHandle2;
        instructionHandle3.instructionHandle2 = instructionHandle4;
        instructionHandle7 = instructionHandle4;
      }
      if (instructionHandle7 == null) {
        instructionHandle6 = this.end;
        this.end = instructionHandle3;
        instructionHandle7 = instructionHandle5;
      } else {
        instructionHandle6 = instructionHandle4.instructionHandle;
        instructionHandle4.instructionHandle = instructionHandle3;
        instructionHandle7 = instructionHandle5;
      }
    }
    instructionHandle7.instructionHandle = null;
    instructionHandle6.instructionHandle2 = null;
    ArrayList values = new ArrayList();
    InstructionHandle instructionHandle8;
    for (InstructionHandle instructionHandle9 = instructionHandle8 = instructionHandle5;
        instructionHandle9 != null;
        instructionHandle9 = instructionHandle8 = instructionHandle8.instructionHandle2) {
      instructionHandle8.getInstruction().dispose();
    }
    StringBuffer stringBuffer = new StringBuffer("{ ");
    InstructionHandle instructionHandle10;
    for (instructionHandle7 = instructionHandle10 = instructionHandle5;
        instructionHandle7 != null;
        instructionHandle7 = instructionHandle10) {
      InstructionHandle instructionHandle11 = instructionHandle10.instructionHandle2;
      this.length--;
      if (instructionHandle10.hasTargeters()) {
        values.add(instructionHandle10);
        stringBuffer.append(instructionHandle10.toString(true) + " ");
        instructionHandle10.instructionHandle2 = instructionHandle10.instructionHandle = null;
        instructionHandle7 = instructionHandle11;
      } else {
        instructionHandle10.update();
        instructionHandle7 = instructionHandle11;
      }
      instructionHandle10 = instructionHandle7;
    }
    stringBuffer.append("}");
    if (!values.isEmpty()) {
      InstructionHandle[] instructionHandles = new InstructionHandle[values.size()];
      values.toArray(instructionHandles);
      throw new TargetLostException(instructionHandles, stringBuffer.toString());
    }
  }

  public BranchHandle createBranchHandle2(
      InstructionHandle instructionHandle, BranchInstruction branchInstruction) {
    BranchHandle branchHandle = BranchHandle.getBranchHandle(branchInstruction);
    InstructionList instructionList = new InstructionList();
    instructionList.processInstructionHandle(branchHandle);
    this.createInstructionHandle(instructionHandle, instructionList);
    return branchHandle;
  }

  public void redirectExceptionHandlers(
      CodeExceptionGen[] codeExceptionGens,
      InstructionHandle instructionHandle,
      InstructionHandle instructionHandle2) {
    int value;
    for (int value2 = value = 0; value2 < codeExceptionGens.length; value2 = ++value) {
      if (codeExceptionGens[value].getInstructionHandle2() == instructionHandle) {
        codeExceptionGens[value].processInstructionHandle2(instructionHandle2);
      }
      if (codeExceptionGens[value].getInstructionHandle() == instructionHandle) {
        codeExceptionGens[value].processInstructionHandle(instructionHandle2);
      }
      if (codeExceptionGens[value].getInstructionHandle3() == instructionHandle) {
        codeExceptionGens[value].processInstructionHandle3(instructionHandle2);
      }
    }
  }

  public void processInstructionHandle2(InstructionHandle instructionHandle3)
      throws TargetLostException {
    this.processInstructionHandle4(
        instructionHandle3.instructionHandle, instructionHandle3.instructionHandle2);
  }

  public InstructionList(Instruction instruction) {
    super();
    this.start = null;
    this.end = null;
    this.length = 0;
    this.getInstructionHandle(instruction);
  }

  public InstructionHandle getInstructionHandle4(Instruction instruction) {
    InstructionHandle instructionHandle = InstructionHandle.getInstructionHandle(instruction);
    this.processInstructionHandle3(instructionHandle);
    return instructionHandle;
  }

  public int[] getInstructionPositions() {
    return this.byte_positions;
  }

  public boolean isEmpty() {
    return this.start == null;
  }

  public InstructionList() {
    this.end = null;
    this.length = 0;
  }

  public InstructionHandle insert(InstructionList instructionList) {
    if (this.isEmpty()) {
      this.append(instructionList);
      return this.start;
    } else {
      return this.createInstructionHandle(this.start, instructionList);
    }
  }

  public InstructionHandle getInstructionHandle2(CompoundInstruction compoundInstruction) {
    return this.insert(compoundInstruction.getInstructionList());
  }

  public void processInstructionHandle3(InstructionHandle instructionHandle3) {
    InstructionList instructionList;
    if (this.isEmpty()) {
      this.start = this.end = instructionHandle3;
      instructionHandle3.instructionHandle2 = instructionHandle3.instructionHandle = null;
      instructionList = this;
    } else {
      instructionList = this;
      this.start.instructionHandle = instructionHandle3;
      instructionHandle3.instructionHandle2 = this.start;
      instructionHandle3.instructionHandle = null;
      this.start = instructionHandle3;
    }
    instructionList.length++;
  }

  public InstructionList copy() {
    HashMap entries = new HashMap();
    InstructionList instructionList = new InstructionList();
    InstructionHandle instructionHandle;
    InstructionHandle instructionHandle3 = instructionHandle = this.start;
    while (instructionHandle3 != null) {
      Instruction instruction2;
      if ((instruction2 = instructionHandle.instruction.copy()) instanceof BranchInstruction) {
        entries.put(
            instructionHandle, instructionList.getBranchHandle((BranchInstruction) instruction2));
        instructionHandle3 = instructionHandle;
      } else {
        entries.put(instructionHandle, instructionList.getInstructionHandle(instruction2));
        instructionHandle3 = instructionHandle;
      }
      instructionHandle3 = instructionHandle = instructionHandle3.instructionHandle2;
    }
    InstructionHandle instructionHandle4 = this.start;
    InstructionHandle instructionHandle5 = instructionList.start;
    for (InstructionHandle instructionHandle6 = instructionHandle4;
        instructionHandle6 != null;
        instructionHandle6 = instructionHandle4) {
      Instruction instruction3 = instructionHandle4.instruction;
      Instruction instruction4 = instructionHandle5.instruction;
      if (instruction3 instanceof BranchInstruction) {
        BranchInstruction branchInstruction = (BranchInstruction) instruction3;
        BranchInstruction branchInstruction2 = (BranchInstruction) instruction4;
        InstructionHandle target = branchInstruction.getTarget();
        branchInstruction2.setTarget((InstructionHandle) entries.get(target));
        if (branchInstruction instanceof Select) {
          InstructionHandle[] targets = ((Select) branchInstruction).getTargets();
          InstructionHandle[] targets2 = ((Select) branchInstruction2).getTargets();
          int value;
          for (int value2 = value = 0; value2 < targets.length; value2 = value) {
            InstructionHandle instructionHandle7 = (InstructionHandle) entries.get(targets[value]);
            int value3 = value;
            value++;
            targets2[value3] = instructionHandle7;
          }
        }
      }
      instructionHandle4 = instructionHandle4.instructionHandle2;
      instructionHandle5 = instructionHandle5.instructionHandle2;
    }
    return instructionList;
  }

  public void setPositions(boolean matches) {
    int value = 0;
    int value2 = 0;
    int value3 = 0;
    int value4 = 0;
    int[] values = new int[this.length];
    InstructionHandle instructionHandle;
    if (matches) {
      for (InstructionHandle instructionHandle3 = instructionHandle = this.start;
          instructionHandle3 != null;
          instructionHandle3 = instructionHandle = instructionHandle.instructionHandle2) {
        Instruction instruction2 = instructionHandle.instruction;
        if (instructionHandle.instruction instanceof BranchInstruction) {
          Instruction instruction3 = ((BranchInstruction) instruction2).getTarget().instruction;
          if (!this.contains(instruction3)) {
            throw new ClassGenException(
                ("Branch target of "
                    + Constants.TEXTS8[instruction2.opcode]
                    + ":"
                    + instruction3
                    + " not in instruction list"));
          }
          if (instruction2 instanceof Select) {
            InstructionHandle[] targets = ((Select) instruction2).getTargets();
            int value5;
            for (int value6 = value5 = 0; value6 < targets.length; value6 = ++value5) {
              instruction3 = targets[value5].instruction;
              if (!this.contains(instruction3)) {
                throw new ClassGenException(
                    ("Branch target of "
                        + Constants.TEXTS8[instruction2.opcode]
                        + ":"
                        + instruction3
                        + " not in instruction list"));
              }
            }
          }
          if (!(instructionHandle instanceof BranchHandle)) {
            throw new ClassGenException(
                ("Branch instruction "
                    + Constants.TEXTS8[instruction2.opcode]
                    + ":"
                    + instruction3
                    + " not contained in BranchHandle."));
          }
        }
      }
    }
    InstructionHandle instructionHandle4;
    for (InstructionHandle instructionHandle5 = instructionHandle4 = this.start;
        instructionHandle5 != null;
        instructionHandle5 = instructionHandle4 = instructionHandle4.instructionHandle2) {
      Instruction instruction4 = instructionHandle4.instruction;
      instructionHandle4.setPosition(value3);
      int value7 = value3;
      int value8 = value4;
      value4++;
      values[value8] = value7;
      int value9;
      switch (instruction4.getOpcode()) {
        case 167:
        case 168:
          value += 2;
          value9 = value3;
          break;
        case 170:
        case 171:
          value += 3;
        case 169:
        default:
          value9 = value3;
      }
      value3 = value9 + instruction4.getLength();
    }
    InstructionHandle instructionHandle6;
    for (InstructionHandle instructionHandle7 = instructionHandle6 = this.start;
        instructionHandle7 != null;
        instructionHandle7 = instructionHandle6 = instructionHandle6.instructionHandle2) {
      value2 += instructionHandle6.updatePosition(value2, value);
    }
    int value10 = 0;
    int value11 = 0;
    InstructionHandle instructionHandle8;
    InstructionHandle instructionHandle9 = instructionHandle8 = this.start;
    while (instructionHandle9 != null) {
      Instruction instruction5 = instructionHandle8.instruction;
      instructionHandle8.setPosition(value11);
      int value12 = value11;
      int value13 = value10;
      InstructionHandle instructionHandle10 = instructionHandle8;
      value10++;
      values[value13] = value12;
      value11 += instruction5.getLength();
      instructionHandle9 = instructionHandle8 = instructionHandle10.instructionHandle2;
    }
    this.byte_positions = new int[value10];
    System.arraycopy(values, 0, this.byte_positions, 0, value10);
  }

  public void update4() {
    this.start = this.end = null;
    this.length = 0;
  }

  public static InstructionHandle findHandle(
      InstructionHandle[] instructionHandles, int[] values, int value, int value2) {
    int value3 = 0;
    int value4 = value - 1;
    int value5;
    do {
      int value6 = (value3 + value4) / 2;
      int value7;
      if ((value7 = values[value6]) == value2) {
        return instructionHandles[value6];
      }
      if (value2 < value7) {
        value4 = value6 - 1;
        value5 = value3;
      } else {
        value5 = value3 = value6 + 1;
      }
    } while (value5 <= value4);
    return null;
  }

  public InstructionHandle[] getInstructionHandles() {
    InstructionHandle[] instructionHandles = new InstructionHandle[this.length];
    InstructionHandle instructionHandle = this.start;
    int value;
    for (int value2 = value = 0; value2 < this.length; value2 = value) {
      InstructionHandle instructionHandle3;
      InstructionHandle instructionHandle4 = instructionHandle3 = instructionHandle;
      int value3 = value;
      value++;
      instructionHandles[value3] = instructionHandle3;
      instructionHandle = instructionHandle4.instructionHandle2;
    }
    return instructionHandles;
  }

  public boolean contains(Instruction instruction) {
    return this.getInstructionHandle2(instruction) != null;
  }

  public InstructionHandle createInstructionHandle2(
      Instruction instruction, Instruction instruction2) {
    return this.createInstructionHandle2(instruction, new InstructionList(instruction2));
  }
}
