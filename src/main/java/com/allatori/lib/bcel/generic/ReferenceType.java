package com.allatori.lib.bcel.generic;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.JavaClass;

public abstract class ReferenceType extends Type {

  public ReferenceType() {
    super((byte) 14, "<null object>");
  }

  public boolean matchesType(Type type) throws ClassNotFoundException {
    return this.equals(Type.NULL) ? true : this.matchesType2(type);
  }

  public ReferenceType createReferenceType(ReferenceType referenceType)
      throws ClassNotFoundException {
    if (this.equals(Type.NULL)) {
      return referenceType;
    }
    if (referenceType.equals(Type.NULL)) {
      return this;
    }
    if (this.equals(referenceType)) {
      return this;
    }
    if (this instanceof ArrayType && referenceType instanceof ArrayType) {
      ArrayType arrayType = (ArrayType) referenceType;
      if (((ArrayType) this).getDimensions() == arrayType.getDimensions()
          && ((ArrayType) this).getBasicType() instanceof ObjectType
          && arrayType.getBasicType() instanceof ObjectType) {
        return new ArrayType(
            ((ObjectType) ((ArrayType) this).getBasicType())
                .createReferenceType((ObjectType) arrayType.getBasicType()),
            ((ArrayType) this).getDimensions());
      }
    }
    if (!(this instanceof ArrayType) && !(referenceType instanceof ArrayType)) {
      if ((!(this instanceof ObjectType) || !((ObjectType) this).isEnabled2())
          && (!(referenceType instanceof ObjectType)
              || !((ObjectType) referenceType).isEnabled2())) {
        ObjectType objectType = (ObjectType) referenceType;
        JavaClass[] javaClasss = Repository.getJavaClasss(((ObjectType) this).getClassName());
        JavaClass[] javaClasss2 = Repository.getJavaClasss(objectType.getClassName());
        if (javaClasss != null && javaClasss2 != null) {
          JavaClass[] javaClasss3 = new JavaClass[javaClasss.length + 1];
          JavaClass[] javaClasss4 = new JavaClass[javaClasss2.length + 1];
          System.arraycopy(javaClasss, 0, javaClasss3, 1, javaClasss.length);
          System.arraycopy(javaClasss2, 0, javaClasss4, 1, javaClasss2.length);
          int value = 0;
          javaClasss3[0] = Repository.lookupClass(((ObjectType) this).getClassName());
          javaClasss4[0] = Repository.lookupClass(objectType.getClassName());
          int value2;
          for (int index = 0; value < javaClasss4.length; value = ++index) {
            for (int value3 = value2 = 0; value3 < javaClasss3.length; value3 = ++value2) {
              if (javaClasss3[value2].equals(javaClasss4[index])) {
                return new ObjectType(javaClasss3[value2].getText2());
              }
            }
          }
          return null;
        } else {
          return null;
        }
      } else {
        return Type.OBJECT;
      }
    } else {
      return Type.OBJECT;
    }
  }

  public boolean matchesType2(Type type) throws ClassNotFoundException {
    if (!(type instanceof ReferenceType)) {
      return false;
    }
    ReferenceType referenceType = (ReferenceType) type;
    if (this.equals(Type.NULL)) {
      return true;
    }
    if (this instanceof ObjectType && ((ObjectType) this).isClass_name()) {
      if (referenceType instanceof ObjectType && ((ObjectType) referenceType).isClass_name()) {
        if (this.equals(referenceType)) {
          return true;
        }
        if (Repository.matchesText(
            ((ObjectType) this).getClassName(), ((ObjectType) referenceType).getClassName())) {
          return true;
        }
      }
      if (referenceType instanceof ObjectType
          && ((ObjectType) referenceType).isEnabled()
          && Repository.matchesText2(
              ((ObjectType) this).getClassName(), ((ObjectType) referenceType).getClassName())) {
        return true;
      }
    }
    if (this instanceof ObjectType && ((ObjectType) this).isEnabled()) {
      if (referenceType instanceof ObjectType
          && ((ObjectType) referenceType).isClass_name()
          && referenceType.equals(Type.OBJECT)) {
        return true;
      }
      if (referenceType instanceof ObjectType && ((ObjectType) referenceType).isEnabled()) {
        if (this.equals(referenceType)) {
          return true;
        }
        if (Repository.matchesText2(
            ((ObjectType) this).getClassName(), ((ObjectType) referenceType).getClassName())) {
          return true;
        }
      }
    }
    if (this instanceof ArrayType) {
      if (referenceType instanceof ObjectType
          && ((ObjectType) referenceType).isClass_name()
          && referenceType.equals(Type.OBJECT)) {
        return true;
      }
      if (referenceType instanceof ArrayType) {
        Type elementType = ((ArrayType) this).getElementType();
        Type elementType2 = ((ArrayType) referenceType).getElementType();
        if (elementType instanceof BasicType
            && elementType2 instanceof BasicType
            && elementType.equals(elementType2)) {
          return true;
        }
        if (elementType2 instanceof ReferenceType
            && elementType instanceof ReferenceType
            && ((ReferenceType) elementType).matchesType2(elementType2)) {
          return true;
        }
      }
      int value;
      if (referenceType instanceof ObjectType && ((ObjectType) referenceType).isEnabled()) {
        for (int value2 = value = 0; value2 < Constants.TEXTS5.length; value2 = ++value) {
          if (referenceType.equals(new ObjectType(Constants.TEXTS5[value]))) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public ReferenceType(byte value, String text) {
    super(value, text);
  }

  /**
   * @deprecated
   */
  public ReferenceType createReferenceType2(ReferenceType referenceType)
      throws ClassNotFoundException {
    if (this.equals(Type.NULL)) {
      return referenceType;
    }
    if (referenceType.equals(Type.NULL)) {
      return this;
    }
    if (this.equals(referenceType)) {
      return this;
    }
    if (!(this instanceof ArrayType) && !(referenceType instanceof ArrayType)) {
      if ((!(this instanceof ObjectType) || !((ObjectType) this).isEnabled2())
          && (!(referenceType instanceof ObjectType)
              || !((ObjectType) referenceType).isEnabled2())) {
        ObjectType objectType = (ObjectType) referenceType;
        JavaClass[] javaClasss = Repository.getJavaClasss(((ObjectType) this).getClassName());
        JavaClass[] javaClasss2 = Repository.getJavaClasss(objectType.getClassName());
        if (javaClasss != null && javaClasss2 != null) {
          JavaClass[] javaClasss3 = new JavaClass[javaClasss.length + 1];
          JavaClass[] javaClasss4 = new JavaClass[javaClasss2.length + 1];
          System.arraycopy(javaClasss, 0, javaClasss3, 1, javaClasss.length);
          System.arraycopy(javaClasss2, 0, javaClasss4, 1, javaClasss2.length);
          int value = 0;
          javaClasss3[0] = Repository.lookupClass(((ObjectType) this).getClassName());
          javaClasss4[0] = Repository.lookupClass(objectType.getClassName());
          int value2;
          for (int index = 0; value < javaClasss4.length; value = ++index) {
            for (int value3 = value2 = 0; value3 < javaClasss3.length; value3 = ++value2) {
              if (javaClasss3[value2].equals(javaClasss4[index])) {
                return new ObjectType(javaClasss3[value2].getText2());
              }
            }
          }
          return null;
        } else {
          return null;
        }
      } else {
        return Type.OBJECT;
      }
    } else {
      return Type.OBJECT;
    }
  }
}
