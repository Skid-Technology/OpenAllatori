package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.classfile.AnnotationEntry;
import com.allatori.lib.bcel.classfile.Annotations;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.ConstantObject;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantValue;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.Utility;
import com.allatori.lib.bcel.util.BCELComparator;
import com.allatori.lib.bcel.util.comparator.FieldGenComparator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FieldGen extends FieldGenOrMethodGen {

  public static BCELComparator bcelComparator = new FieldGenComparator();

  public List observers;

  public Object value;

  public void processCount2(int value2) {
    this.checkType(Type.INT);
    if (value2 != 0) {
      this.value = new Integer(value2);
    }
  }

  public FieldGen(int value2, Type type, String text, ConstantPoolGen constantPoolGen) {
    super();
    this.value = null;
    this.setAccess_flags(value2);
    this.setType(type);
    this.setName(text);
    this.setConstantPool(constantPoolGen);
  }

  public void processCount3(int value2) {
    ConstantPool constantPool;
    this.value =
        ((ConstantObject) (constantPool = this.cp.getConstantPool()).getConstant(value2))
            .getConstantValue(constantPool);
  }

  public Field getField() {
    String text = this.getText();
    int value2 = this.cp.addUtf8(this.name);
    int value3 = this.cp.addUtf8(text);
    if (this.value != null) {
      this.checkType(this.type);
      int value4 = this.addConstant();
      this.addAttribute(
          new ConstantValue(
              this.cp.addUtf8("ConstantValue"), 2, value4, this.cp.getConstantPool()));
    }
    this.addAnnotationsAsAttribute(this.cp);
    return new Field(
        this.access_flags, value2, value3, this.getAttributes(), this.cp.getConstantPool());
  }

  public void setInitValue(long value2) {
    this.checkType(Type.LONG);
    if (value2 != 0L) {
      this.value = new Long(value2);
    }
  }

  public FieldGen copy(ConstantPoolGen constantPoolGen) {
    FieldGen fieldGen = (FieldGen) this.clone();
    fieldGen.setConstantPool(constantPoolGen);
    return fieldGen;
  }

  public static void setComparator(BCELComparator bCELComparator) {
    bcelComparator = bCELComparator;
  }

  @Override
  public boolean equals(Object result) {
    return bcelComparator.equals(this, result);
  }

  public String getInitValue() {
    return this.value != null ? this.value.toString() : null;
  }

  @Override
  public String getText() {
    return this.type.getSignature();
  }

  public void setInitValue(float value2) {
    this.checkType(Type.FLOAT);
    if (value2 != 0.0) {
      this.value = new Float(value2);
    }
  }

  public void checkType(Type type2) {
    if (this.type == null) {
      throw new ClassGenException("You haven't defined the type of the field yet");
    }
    if (!this.type.equals(type2)) {
      throw new ClassGenException(("Types are not compatible: " + this.type + " vs. " + type2));
    }
  }

  @Override
  public int hashCode() {
    return bcelComparator.hashCode(this);
  }

  public void setInitValue(String value) {
    this.checkType(new ObjectType("java.lang.String"));
    if (value != null) {
      this.value = value;
    }
  }

  public int addConstant() {
    switch (this.type.getType()) {
      case 4:
      case 5:
      case 8:
      case 9:
      case 10:
        return this.cp.addInteger((Integer) this.value);
      case 6:
        return this.cp.addFloat((Float) this.value);
      case 7:
        return this.cp.addDouble((Double) this.value);
      case 11:
        return this.cp.addLong((Long) this.value);
      case 12:
      case 13:
      default:
        throw new RuntimeException(("Oops: Unhandled : " + this.type.getType()));
      case 14:
        return this.cp.addString((String) this.value);
    }
  }

  public static BCELComparator getComparator() {
    return bcelComparator;
  }

  public void setInitValue(double value2) {
    this.checkType(Type.DOUBLE);
    if (value2 != 0.0) {
      this.value = new Double(value2);
    }
  }

  public void setInitValue(boolean matches) {
    this.checkType(Type.BOOLEAN);
    if (matches) {
      this.value = new Integer(1);
    }
  }

  public void cancelInitValue() {
    this.value = null;
  }

  public void removeObserver(FieldObserver fieldObserver) {
    if (this.observers != null) {
      this.observers.remove(fieldObserver);
    }
  }

  public void addObserver(FieldObserver fieldObserver) {
    if (this.observers == null) {
      this.observers = new ArrayList();
    }
    this.observers.add(fieldObserver);
  }

  public void setInitValue(char value2) {
    this.checkType(Type.CHAR);
    if (value2 != 0) {
      this.value = new Integer(value2);
    }
  }

  public void setInitValue(byte value2) {
    this.checkType(Type.BYTE);
    if (value2 != 0) {
      this.value = new Integer(value2);
    }
  }

  public void addAnnotationsAsAttribute(ConstantPoolGen constantPoolGen) {
    Attribute[] attributes;
    int value;
    if ((attributes = Utility.createAttributes(constantPoolGen, this.arrayList)) != null) {
      for (int value2 = value = 0; value2 < attributes.length; value2 = value) {
        int value3 = value;
        Attribute[] attributes2 = attributes;
        value++;
        this.addAttribute(attributes2[value3]);
      }
    }
  }

  public void setInitValue(short value2) {
    this.checkType(Type.SHORT);
    if (value2 != 0) {
      this.value = new Integer(value2);
    }
  }

  public void update() {
    Iterator iterator2;
    if (this.observers != null) {
      for (Iterator iterator3 = iterator2 = this.observers.iterator();
          iterator3.hasNext();
          iterator3 = iterator2) {
        ((FieldObserver) iterator2.next()).notify(this);
      }
    }
  }

  public FieldGen(Field field, ConstantPoolGen constantPoolGen) {
    this(
        field.getAccess_flags2(), Type.getType(field.getText2()), field.getText(), constantPoolGen);
    Attribute[] attributes = field.getAttributes();
    int value;
    for (int value2 = value = 0; value2 < attributes.length; value2 = ++value) {
      if (attributes[value] instanceof ConstantValue) {
        this.processCount3(((ConstantValue) attributes[value]).getConstantValueIndex());
      } else if (attributes[value] instanceof Annotations) {
        AnnotationEntry[] annotationEntries =
            ((Annotations) attributes[value]).getAnnotationEntries();
        int value3;
        for (int value4 = value3 = 0; value4 < annotationEntries.length; value4 = value3) {
          AnnotationEntry annotationEntry = annotationEntries[value3];
          value3++;
          this.processAnnotationEntryGen(
              new AnnotationEntryGen(annotationEntry, constantPoolGen, false));
        }
      } else {
        this.addAttribute(attributes[value]);
      }
    }
  }
}
