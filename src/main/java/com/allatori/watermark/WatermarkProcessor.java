package com.allatori.watermark;

import com.allatori.VersionInfo;
import com.allatori.config.Configuration;
import com.allatori.engine.ObfuscationException;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.generic.ICONST;
import com.allatori.lib.bcel.generic.InstructionConstants;
import com.allatori.lib.bcel.generic.InstructionHandle;
import com.allatori.lib.bcel.generic.InstructionList;
import com.allatori.lib.bcel.generic.InstructionTargeter;
import com.allatori.lib.bcel.generic.LineNumberGen;
import com.allatori.lib.bcel.generic.LocalVariableGen;
import com.allatori.lib.bcel.generic.POP2;
import com.allatori.lib.bcel.generic.SIPUSH;
import com.allatori.lib.bcel.util.comparator.MethodComparator;
import com.allatori.model.ClassStorage;
import com.allatori.model.MethodContext;
import com.allatori.util.Log;
import com.allatori.util.random.RandomUtils;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class WatermarkProcessor {

  public ClassStorage classStorage;

  public String watermarkKey;

  public void processValues(Vector vector, short[] values) {
    InstructionHandle instructionHandle;
    int value = values.length / 4;
    int size2 = vector.size();
    int value2 = (value - 1) / size2 + 1;
    int value3 = 0;
    int value4;
    for (int value5 = value4 = vector.size() - 1; value5 >= 0; value5 = value4) {
      InstructionHandle instructionHandle2;
      MethodContext methodContext;
      InstructionList instructionList;
      InstructionHandle instructionHandle3 =
          instructionHandle2 =
              (instructionList =
                      MethodContext.getMethodGen(methodContext = (MethodContext) vector.get(value4))
                          .getInstructionList())
                  .getStart();
      while (true) {
        if (instructionHandle3 == null) {
          instructionHandle = instructionHandle2;
          break;
        }
        if (instructionHandle2.getInstruction() instanceof ICONST
            && this.matchesInstructionTargeters(instructionHandle2.getTargeters())
            && instructionHandle2.getInstructionHandle2() != null) {
          instructionHandle = instructionHandle2 = instructionHandle2.getInstructionHandle2();
          break;
        }
        instructionHandle3 = instructionHandle2 = instructionHandle2.getInstructionHandle2();
      }
      if (instructionHandle == null) {
        instructionHandle2 = instructionList.getStart();
      }
      if (Configuration.getObfuscationOptions().isEnabled()) {
        instructionHandle2 = instructionList.getEnd();
      }
      int value6 = 0;
      do {
        InstructionHandle instructionHandle4 =
            instructionList.createInstructionHandle(instructionHandle2, new SIPUSH(values[value3]));
        InstructionHandle instructionHandle5 =
            instructionList.createInstructionHandle(
                instructionHandle2, new SIPUSH(values[++value3]));
        InstructionHandle instructionHandle6 =
            instructionList.createInstructionHandle(
                instructionHandle2, new SIPUSH(values[++value3]));
        InstructionHandle instructionHandle7 =
            instructionList.createInstructionHandle(
                instructionHandle2, new SIPUSH(values[++value3]));
        value3++;
        InstructionHandle instructionHandle8;
        InstructionHandle instructionHandle9 =
            instructionHandle8 =
                instructionList.createInstructionHandle(
                    instructionHandle2, InstructionConstants.POP2);
        instructionList.createInstructionHandle(instructionHandle2, InstructionConstants.POP2);
        value6++;
        instructionHandle2 = instructionList.getStart();
      } while (value6 < value2 && value4 < value - value3 / 4);
      MethodContext.getMethodGen(methodContext).update5();
      ClassGen classGen = MethodContext.getClassGen(methodContext);
      Method method = MethodContext.getMethod(methodContext);
      MethodContext methodContext2 = methodContext;
      value4--;
      classGen.replaceMethod(method, MethodContext.getMethodGen(methodContext2).getMethod());
    }
  }

  public short[] createValues(Vector vector) {
    Vector vector2 = new Vector();
    int value;
    InstructionHandle instructionHandle;
    for (int value2 = value = vector.size() - 1; value2 >= 0; value2 = --value) {
      for (InstructionHandle instructionHandle2 =
              instructionHandle =
                  MethodContext.getMethodGen((MethodContext) vector.get(value))
                      .getInstructionList()
                      .getStart();
          instructionHandle2 != null;
          instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2()) {
        if (this.matchesInstructionHandle(instructionHandle)) {
          vector2.add(((SIPUSH) instructionHandle.getInstruction()).getValue());
          vector2.add(
              ((SIPUSH) instructionHandle.getInstructionHandle2().getInstruction()).getValue());
          vector2.add(
              ((SIPUSH)
                      instructionHandle
                          .getInstructionHandle2()
                          .getInstructionHandle2()
                          .getInstruction())
                  .getValue());
          vector2.add(
              ((SIPUSH)
                      instructionHandle
                          .getInstructionHandle2()
                          .getInstructionHandle2()
                          .getInstructionHandle2()
                          .getInstruction())
                  .getValue());
        }
      }
    }
    short[] values;
    int value3;
    for (int value4 = value3 = (values = new short[vector2.size()]).length - 1;
        value4 >= 0;
        value4 = value3) {
      short value5 = ((Number) vector2.get(value3)).shortValue();
      int value6 = value3;
      short[] values2 = values;
      value3--;
      values2[value6] = value5;
    }
    return values;
  }

  public boolean matchesValues(Vector vector) {
    InstructionHandle instructionHandle;
    int value;
    for (int value2 = value = vector.size() - 1; value2 >= 0; value2 = --value) {
      for (InstructionHandle instructionHandle2 =
              instructionHandle =
                  MethodContext.getMethodGen((MethodContext) vector.get(value))
                      .getInstructionList()
                      .getStart();
          instructionHandle2 != null;
          instructionHandle2 = instructionHandle = instructionHandle.getInstructionHandle2()) {
        if (this.matchesInstructionHandle(instructionHandle)) {
          return true;
        }
      }
    }
    return false;
  }

  public String createText(short[] values) throws Exception, UnsupportedEncodingException {
    int value;
    for (int value2 = value = 0; value2 < values.length; value2 = value) {
      short value3 = values[value];
      short value4 = values[value + 1];
      short value5 = values[value + 2];
      short value6 = values[value + 3];
      values[value] = values[value + 1] = values[value + 2] = values[value + 3] = 0;
      values[value] = (short) (values[value] | value3 ^ value5 ^ value6);
      short[] values2;
      if (values[value] % 2 == 0) {
        values2 = values;
        values[value + 1] = (short) (values[value + 1] | value3 ^ value4 ^ value6);
      } else {
        values2 = values;
        values[value + 1] = (short) (values[value + 1] | value4 ^ value5 ^ values[value]);
      }
      short[] values3;
      if (values2[value + 1] % 4 == 0) {
        values3 = values;
        values[value + 2] = (short) (values[value + 2] | value3 ^ value4 ^ value5);
      } else if (values[value + 1] % 4 == 1) {
        values3 = values;
        values[value + 2] =
            (short) (values[value + 2] | value3 ^ values[value] ^ values[value + 1]);
      } else if (values[value + 1] % 4 == 2) {
        values3 = values;
        values[value + 2] = (short) (values[value + 2] | value5 ^ value6 ^ values[value + 1]);
      } else {
        values3 = values;
        values[value + 2] = (short) (values[value + 2] | value4 ^ value6 ^ values[value]);
      }
      if (values3[value + 2] % 7 == 0) {
        values[value + 3] = (short) (values[value + 3] | value4 ^ value5 ^ value6);
      } else if (values[value + 2] % 7 == 1) {
        values[value + 3] =
            (short) (values[value + 3] | value6 ^ values[value] ^ values[value + 1]);
      } else if (values[value + 2] % 7 == 2) {
        values[value + 3] =
            (short) (values[value + 3] | value5 ^ values[value] ^ values[value + 2]);
      } else if (values[value + 2] % 7 == 3) {
        values[value + 3] =
            (short) (values[value + 3] | value4 ^ values[value + 1] ^ values[value + 2]);
      } else if (values[value + 2] % 7 == 4) {
        values[value + 3] = (short) (values[value + 3] | value3 ^ value4 ^ values[value]);
      } else if (values[value + 2] % 7 == 5) {
        values[value + 3] = (short) (values[value + 3] | value3 ^ value5 ^ values[value + 1]);
      } else {
        values[value + 3] = (short) (values[value + 3] | value3 ^ value6 ^ values[value + 2]);
      }
      value += 4;
    }
    int value7 = this.watermarkKey.hashCode();
    short value8 = (short) ((byte) 0 | value7);
    short value9 = (short) ((byte) 0 | value7 >> 16);
    int value10 = -1;
    Hashtable hashtable = new Hashtable();
    int value11;
    for (int value12 = value11 = 0; value12 < values.length; value12 = value11) {
      hashtable.put(new Short(values[value11]), new Integer(value11));
      if (values[value11] == value8 && values[value11 + 1] == value9) {
        if (value10 != -1) {
          throw new Exception();
        }
        value10 = value11;
      }
      value11 += 4;
    }
    if (value10 == -1) {
      throw new Exception();
    }
    int value13;
    int value14 = value13 = values[value10 + 3];
    while (value14 % 4 != 0) {
      value14 = ++value13;
    }
    byte[] values4;
    Arrays.fill(values4 = new byte[value13], (byte) 0);
    int value15 = 0;
    int value16 = value10 + 1;
    for (int value17 = value15; value17 < values4.length; value17 = value15) {
      value16 = ((Number) hashtable.get(new Short(values[value16 + 1]))).shortValue();
      int value18 = value15;
      byte[] values5 = values4;
      int value19 = value15;
      byte[] values6 = values4;
      byte[] values7 = values4;
      byte[] values8 = values4;
      byte[] values9 = values4;
      value15++;
      values6[value19] = (byte) (values5[value18] | values[value16 + 2] >> 8);
      int value20 = value15;
      int value21 = value15;
      value15++;
      values7[value21] = (byte) (values7[value20] | values[value16 + 3] >> 8);
      int value22 = value15;
      int value23 = value15;
      value15++;
      values8[value23] = (byte) (values8[value22] | values[value16 + 2]);
      int value24 = value15;
      int value25 = value15;
      value15++;
      values9[value25] = (byte) (values9[value24] | values[value16 + 3]);
    }
    byte[] bytes = this.watermarkKey.getBytes();
    int value26;
    for (int value27 = value26 = 0; value27 < values4.length; value27 = value26) {
      byte value28 = (byte) (values4[value26] ^ bytes[value26 % bytes.length]);
      int value29 = value26;
      byte[] values10 = values4;
      value26++;
      values10[value29] = value28;
    }
    if (values4.length > 1) {
      values4[1] ^= values4[0];
    }
    int value30;
    for (int value31 = value30 = 2; value31 < values4.length; value31 = value30) {
      byte value32 = (byte) (values4[value30] ^ values4[value30 - 1] ^ values4[value30 - 2]);
      int value33 = value30;
      byte[] values11 = values4;
      value30++;
      values11[value33] = value32;
    }
    return new String(values4, 0, values[value10 + 3], "UTF-8");
  }

  public Vector createValues() {
    Vector vector = new Vector();
    Iterator classesByName2 = this.classStorage.getClassesByName2();
    while (true) {
      Iterator iterator = classesByName2;
      ClassGen classGen;
      while (true) {
        if (!iterator.hasNext()) {
          return vector;
        }
        classGen = (ClassGen) classesByName2.next();
        try {
          if (!Configuration.getIgnoreClassesOptions()
              .matchesClassGen(this.classStorage, classGen)) {
            break;
          }
        } catch (ClassCastException classCastException) {
          Log.error("ALLATORI INTERNAL ERROR. Class " + classGen.getClassName());
          iterator = classesByName2;
          continue;
        }
        iterator = classesByName2;
      }
      int value;
      Method[] methods;
      for (int value2 = value = (methods = classGen.getMethods()).length - 1;
          value2 >= 0;
          value2 = --value) {
        Method method;
        if ((method = methods[value]).getCode() != null) {
          vector.add(new MethodContext(classGen, method));
        }
      }
    }
  }

  public void processText(String text) throws ObfuscationException {
    try {
      Vector vector;
      if (this.matchesValues(vector = this.createValues())) {
        throw new ObfuscationException("The jar file already contains watermark.");
      }
      RandomUtils.processValues(vector);
      short[] values = this.createValues(text);
      int value;
      for (int value2 = value = vector.size() - values.length / 4; value2 > 0; value2 = value) {
        value--;
        vector.remove(0);
      }
      this.processValues(vector, values);
    } catch (Exception exception) {
      System.out.println(
          "############### EXCEPTION (" + VersionInfo.getVersion() + ") ###############");
      exception.printStackTrace();
      System.out.println("#################################################");
      throw new ObfuscationException(("Watermarking error: " + exception.getMessage()));
    }
  }

  public boolean matchesInstructionTargeters(InstructionTargeter[] instructionTargeters) {
    if (instructionTargeters == null) {
      return true;
    }
    int value;
    for (int value2 = value = 0; value2 < instructionTargeters.length; value2 = ++value) {
      InstructionTargeter instructionTargeter;
      if (!((instructionTargeter = instructionTargeters[value]) instanceof LineNumberGen)
          && !(instructionTargeter instanceof LocalVariableGen)) {
        return false;
      }
    }
    return true;
  }

  public boolean matchesInstructionHandle(InstructionHandle instructionHandle) {
    return instructionHandle.getInstruction() instanceof SIPUSH
        && instructionHandle.getInstructionHandle2() != null
        && instructionHandle.getInstructionHandle2().getInstruction() instanceof SIPUSH
        && instructionHandle.getInstructionHandle2().getInstructionHandle2() != null
        && instructionHandle.getInstructionHandle2().getInstructionHandle2().getInstruction()
            instanceof SIPUSH
        && instructionHandle.getInstructionHandle2().getInstructionHandle2().getInstructionHandle2()
            != null
        && instructionHandle
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstruction()
            instanceof SIPUSH
        && instructionHandle
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
            != null
        && instructionHandle
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstruction()
            instanceof POP2
        && instructionHandle
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
            != null
        && instructionHandle
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstructionHandle2()
                .getInstruction()
            instanceof POP2;
  }

  public short[] createValues(String text) throws UnsupportedEncodingException {
    int value;
    byte[] values;
    int value2 = value = (values = text.getBytes("UTF-8")).length;
    while (value2 % 4 != 0) {
      value2 = ++value;
    }
    byte[] values2 = new byte[value];
    Arrays.fill(values2, (byte) 0);
    values2[0] = values[0];
    if (values.length > 1) {
      values2[1] = (byte) (values2[1] | values[1] ^ values[0]);
    }
    int value3;
    for (int value4 = value3 = 2; value4 < values.length; value4 = value3) {
      byte value5 =
          (byte) (values2[value3] | values[value3] ^ values[value3 - 1] ^ values[value3 - 2]);
      int value6 = value3;
      value3++;
      values2[value6] = value5;
    }
    int value7;
    for (int value8 = value7 = values.length; value8 < value; value8 = value7) {
      byte value9 = (byte) (values2[value7] | RandomUtils.calculateCount(256));
      int value10 = value7;
      value7++;
      values2[value10] = value9;
    }
    byte[] bytes = this.watermarkKey.getBytes();
    int value11;
    for (int value12 = value11 = 0; value12 < values2.length; value12 = value11) {
      byte value13 = (byte) (values2[value11] ^ bytes[value11 % bytes.length]);
      int value14 = value11;
      value11++;
      values2[value14] = value13;
    }
    int value15 = value;
    int value16;
    value2 = value16 = (int) (value * (0.666 + RandomUtils.calculateValue() / 3.0)) + 113;
    while (value2 % 4 != 0) {
      value2 = ++value16;
    }
    short[] values3;
    short[] values4 = values3 = new short[value15 + value16];
    int value17 = 0;
    Arrays.fill(values4, (short) 0);
    for (int index = 0; value17 < values3.length; value17 = index) {
      if (index < value15) {
        values3[index + 2] =
            (short) (values3[index + 2] | values2[index] << 8 | values2[index + 2] & 0xFF);
        values3[index + 3] =
            (short) (values3[index + 3] | values2[index + 1] << 8 | values2[index + 3] & 0xFF);
      } else {
        values3[index + 2] =
            (short) (values3[index + 2] | RandomUtils.calculateCount(Integer.MAX_VALUE));
        values3[index + 3] =
            (short) (values3[index + 3] | RandomUtils.calculateCount(Integer.MAX_VALUE));
      }
      index += 4;
    }
    Hashtable hashtable = new Hashtable();
    short value18 = (short) (0 | this.watermarkKey.hashCode());
    hashtable.put(new Short(value18), "");
    int value19 = 0;
    for (int value20 = value19; value20 < values3.length; value20 = value19) {
      int value21 = (short) (RandomUtils.calculateCount(65536) - 32768);
      for (Hashtable hashtable2 = hashtable;
          hashtable2.containsKey(new Short((short) value21));
          hashtable2 = hashtable) {
        value21++;
      }
      hashtable.put(new Short((short) value21), "");
      values3[value19] = (short) value21;
      if (value19 > 3) {
        values3[value19 - 3] = (short) value21;
      }
      value19 += 4;
    }
    int value22;
    for (int value23 = value22 = value15 + 4 * RandomUtils.calculateCount(20);
        value23 < values3.length;
        value23 = value22) {
      int value24 = value22 + 1;
      short value25 = values3[RandomUtils.calculateCount(value15 / 4) * 4];
      short[] values5 = values3;
      value22 += 4;
      values5[value24] = value25;
    }
    int value26 = this.watermarkKey.hashCode();
    values3[values3.length - 4] =
        values3[values3.length - 3] = values3[values3.length - 2] = values3[values3.length - 1] = 0;
    values3[values3.length - 4] = (short) (values3[values3.length - 4] | value26);
    values3[values3.length - 3] = (short) (values3[values3.length - 3] | value26 >> 16);
    values3[values3.length - 2] = (short) (values3[values3.length - 2] | values3[0]);
    values3[values3.length - 1] = (short) (values3[values3.length - 1] | values.length);
    int value27;
    for (int value28 = value27 = 0; value28 < values3.length; value28 = value27) {
      short value29 = values3[value27];
      short value30 = values3[value27 + 1];
      short value31 = values3[value27 + 2];
      short value32 = values3[value27 + 3];
      values3[value27] = values3[value27 + 1] = values3[value27 + 2] = values3[value27 + 3] = 0;
      values3[value27] = (short) (values3[value27] | value29 ^ value30 ^ value31);
      values3[value27 + 1] = (short) (values3[value27 + 1] | value30 ^ value31 ^ value32);
      values3[value27 + 2] = (short) (values3[value27 + 2] | value29 ^ value31 ^ value32);
      short value33 = (short) (values3[value27 + 3] | value29 ^ value30 ^ value32);
      int value34 = value27 + 3;
      short[] values6 = values3;
      value27 += 4;
      values6[value34] = value33;
    }
    return values3;
  }

  public String createText() throws ObfuscationException {
    try {
      return this.createText(this.createValues(this.createValues()));
    } catch (Exception exception) {
      throw new ObfuscationException("Cannot extract watermark.");
    }
  }

  public WatermarkProcessor(ClassStorage classStorage2, String watermarkKey) {
    super();
    this.classStorage = classStorage2;
    this.watermarkKey = watermarkKey;
    Method.setComparator(MethodComparator.getMethodComparator());
  }
}
