package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.Repository;
import com.allatori.lib.bcel.classfile.Attribute;
import com.allatori.lib.bcel.classfile.ClassFormatException;
import com.allatori.lib.bcel.classfile.Code;
import com.allatori.lib.bcel.classfile.CodeException;
import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantDouble;
import com.allatori.lib.bcel.classfile.ConstantFieldref;
import com.allatori.lib.bcel.classfile.ConstantFloat;
import com.allatori.lib.bcel.classfile.ConstantInteger;
import com.allatori.lib.bcel.classfile.ConstantInterfaceMethodref;
import com.allatori.lib.bcel.classfile.ConstantLong;
import com.allatori.lib.bcel.classfile.ConstantMethodref;
import com.allatori.lib.bcel.classfile.ConstantNameAndType;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantString;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.ConstantValue;
import com.allatori.lib.bcel.classfile.Deprecated;
import com.allatori.lib.bcel.classfile.DescendingVisitor;
import com.allatori.lib.bcel.classfile.EmptyVisitor;
import com.allatori.lib.bcel.classfile.ExceptionTable;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.InnerClass;
import com.allatori.lib.bcel.classfile.InnerClasses;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.LineNumber;
import com.allatori.lib.bcel.classfile.LineNumberTable;
import com.allatori.lib.bcel.classfile.LocalVariable;
import com.allatori.lib.bcel.classfile.LocalVariableTable;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.Node;
import com.allatori.lib.bcel.classfile.SourceFile;
import com.allatori.lib.bcel.classfile.Synthetic;
import com.allatori.lib.bcel.classfile.Unknown;
import com.allatori.lib.bcel.classfile.Visitor;
import com.allatori.lib.bcel.generic.ArrayType;
import com.allatori.lib.bcel.generic.ObjectType;
import com.allatori.lib.bcel.generic.Type;
import com.allatori.lib.bcel.verifier.VerificationResult;
import com.allatori.lib.bcel.verifier.VerifierFactory;
import com.allatori.lib.bcel.verifier.exc.AssertionViolatedException;
import com.allatori.lib.bcel.verifier.exc.ClassConstraintException;
import com.allatori.lib.bcel.verifier.exc.LoadingException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Pass2Verifier$CPESSC_Visitor extends EmptyVisitor implements Visitor {

  public Class CONST_Float;

  public Set field_names;

  public Set method_names_and_desc;

  public Set field_names_and_desc;

  public DescendingVisitor carrier;

  public Class CONST_NameAndType;

  public Class CONST_Long;

  public Class CONST_Double;

  public final JavaClass jc;

  public final int cplen;

  public Class CONST_Integer;

  public Class CONST_Class;

  public Class CONST_String;

  public final ConstantPool cp;

  public final Pass2Verifier this$0;

  public Class CONST_Utf8;

  @Override
  public void visitField(Field field) {
    if (this.jc.isEnabled17()) {
      int value = 0;
      if (field.isEnabled9()) {
        value++;
      }
      if (field.isEnabled13()) {
        value++;
      }
      if (field.isEnabled6()) {
        value++;
      }
      if (value > 1) {
        throw new LoadingException(
            ("Field '"
                + Pass2Verifier.getText(field)
                + "' must only have at most one of its ACC_PRIVATE, ACC_PROTECTED, ACC_PUBLIC"
                + " modifiers set."));
      }
      if (field.isEnabled3() && field.isEnabled()) {
        throw new LoadingException(
            ("Field '"
                + Pass2Verifier.getText(field)
                + "' must only have at most one of its ACC_FINAL, ACC_VOLATILE modifiers set."));
      }
    } else {
      if (!field.isEnabled6()) {
        throw new LoadingException(
            ("Interface field '"
                + Pass2Verifier.getText(field)
                + "' must have the ACC_PUBLIC modifier set but hasn't!"));
      }
      if (!field.isEnabled11()) {
        throw new LoadingException(
            ("Interface field '"
                + Pass2Verifier.getText(field)
                + "' must have the ACC_STATIC modifier set but hasn't!"));
      }
      if (!field.isEnabled3()) {
        throw new LoadingException(
            ("Interface field '"
                + Pass2Verifier.getText(field)
                + "' must have the ACC_FINAL modifier set but hasn't!"));
      }
    }
    if ((field.getAccess_flags2() & -224) > 0) {
      this.this$0.addMessage(
          "Field '"
              + Pass2Verifier.getText(field)
              + "' has access flag(s) other than ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED,"
              + " ACC_STATIC, ACC_FINAL, ACC_VOLATILE, ACC_TRANSIENT set (ignored).");
    }
    this.checkIndex(field, field.getCount3(), this.CONST_Utf8);
    String text;
    if (!Pass2Verifier.matchesText(text = field.getText())) {
      throw new LoadingException(
          ("Field '"
              + Pass2Verifier.getText(field)
              + "' has illegal name '"
              + field.getText()
              + "'."));
    }
    this.checkIndex(field, field.getCount(), this.CONST_Utf8);
    String bytes = ((ConstantUtf8) this.cp.getConstant(field.getCount())).getBytes();
    try {
      Type.getType(bytes);
    } catch (ClassFormatException classFormatException) {
      throw new LoadingException(
          ("Illegal descriptor (==signature) '"
              + bytes
              + "' used by '"
              + Pass2Verifier.getText(field)
              + "'."));
    }
    String text2 = "" + text + bytes;
    if (this.field_names_and_desc.contains(text2)) {
      throw new LoadingException(
          ("No two fields (like '"
              + Pass2Verifier.getText(field)
              + "') are allowed have same names and descriptors!"));
    }
    if (this.field_names.contains(text)) {
      this.this$0.addMessage(
          "More than one field of name '"
              + text
              + "' detected (but with different type descriptors). This is very unusual.");
    }
    this.field_names_and_desc.add(text2);
    this.field_names.add(text);
    Attribute[] attributes = field.getAttributes();
    int value2 = 0;
    for (int value3 = value2; value3 < attributes.length; value3 = ++value2) {
      if (!(attributes[value2] instanceof ConstantValue)
          && !(attributes[value2] instanceof Synthetic)
          && !(attributes[value2] instanceof Deprecated)) {
        this.this$0.addMessage(
            "Attribute '"
                + Pass2Verifier.getText(attributes[value2])
                + "' as an attribute of Field '"
                + Pass2Verifier.getText(field)
                + "' is unknown and will therefore be ignored.");
      }
      if (!(attributes[value2] instanceof ConstantValue)) {
        this.this$0.addMessage(
            "Attribute '"
                + Pass2Verifier.getText(attributes[value2])
                + "' as an attribute of Field '"
                + Pass2Verifier.getText(field)
                + "' is not a ConstantValue and is therefore only of use for debuggers and such.");
      }
    }
  }

  @Override
  public void visitLocalVariable(LocalVariable localVariable) {}

  @Override
  public void visitUnknown(Unknown unknown) {
    this.checkIndex(unknown, unknown.getName_index(), this.CONST_Utf8);
    this.this$0.addMessage(
        "Unknown attribute '"
            + Pass2Verifier.getText(unknown)
            + "'. This attribute is not known in any context!");
  }

  @Override
  public void visitLineNumberTable(LineNumberTable lineNumberTable) {
    this.checkIndex(lineNumberTable, lineNumberTable.getName_index(), this.CONST_Utf8);
    String text;
    if (!(text = ((ConstantUtf8) this.cp.getConstant(lineNumberTable.getName_index())).getBytes())
        .equals("LineNumberTable")) {
      throw new LoadingException(
          ("The LineNumberTable attribute '"
              + Pass2Verifier.getText(lineNumberTable)
              + "' is not correctly named 'LineNumberTable' but '"
              + text
              + "'."));
    }
  }

  @Override
  public void visitCodeException(CodeException codeException) {}

  @Override
  public void visitConstantFloat(ConstantFloat constantFloat) {
    if (constantFloat.getTag() != 4) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantFloat) + "'."));
    }
  }

  @Override
  public void visitConstantFieldref(ConstantFieldref constantFieldref) {
    if (constantFieldref.getTag() != 9) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantFieldref) + "'."));
    }
    this.checkIndex(constantFieldref, constantFieldref.getClass_index(), this.CONST_Class);
    this.checkIndex(
        constantFieldref, constantFieldref.getName_and_type_index(), this.CONST_NameAndType);
  }

  @Override
  public void visitConstantString(ConstantString constantString) {
    if (constantString.getTag() != 8) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantString) + "'."));
    }
    this.checkIndex(constantString, constantString.getStringIndex(), this.CONST_Utf8);
  }

  @Override
  public void visitExceptionTable(ExceptionTable exceptionTable) {
    JavaClass javaClass;
    try {
      this.checkIndex(exceptionTable, exceptionTable.getName_index(), this.CONST_Utf8);
      String text;
      if (!(text = ((ConstantUtf8) this.cp.getConstant(exceptionTable.getName_index())).getBytes())
          .equals("Exceptions")) {
        throw new LoadingException(
            ("The Exceptions attribute '"
                + Pass2Verifier.getText(exceptionTable)
                + "' is not correctly named 'Exceptions' but '"
                + text
                + "'."));
      }
      int[] exceptionIndexTable = exceptionTable.getExceptionIndexTable();
      for (int index = 0; index < exceptionIndexTable.length; index++) {
        this.checkIndex(exceptionTable, exceptionIndexTable[index], this.CONST_Class);
        ConstantClass constantClass;
        ConstantClass constantClass2 =
            constantClass = (ConstantClass) this.cp.getConstant(exceptionIndexTable[index]);
        this.checkIndex(constantClass2, constantClass2.getNameIndex(), this.CONST_Utf8);
        String text2;
        VerificationResult verificationResult;
        if ((verificationResult =
                VerifierFactory.getVerifier(
                        text2 =
                            ((ConstantUtf8) this.cp.getConstant(constantClass.getNameIndex()))
                                .getBytes()
                                .replace('/', '.'))
                    .createVerificationResult())
            != VerificationResult.VERIFICATION_RESULT2) {
          throw new LoadingException(
              ("Exceptions attribute '"
                  + Pass2Verifier.getText(exceptionTable)
                  + "' references '"
                  + text2
                  + "' as an Exception but it does not pass verification pass 1: "
                  + verificationResult));
        }
        JavaClass javaClass2 = Repository.lookupClass(text2);
        JavaClass javaClass3 = Repository.lookupClass(Type.THROWABLE.getClassName());
        JavaClass javaClass4 = Repository.lookupClass(Type.OBJECT.getClassName());
        JavaClass javaClass5 = javaClass2;
        while (true) {
          if (javaClass5 == javaClass4) {
            javaClass = javaClass2;
            break;
          }
          if (javaClass2 == javaClass3) {
            javaClass = javaClass2;
            break;
          }
          VerificationResult verificationResult2;
          if ((verificationResult2 =
                  VerifierFactory.getVerifier(javaClass2.getText5()).createVerificationResult())
              != VerificationResult.VERIFICATION_RESULT2) {
            throw new LoadingException(
                ("Exceptions attribute '"
                    + Pass2Verifier.getText(exceptionTable)
                    + "' references '"
                    + text2
                    + "' as an Exception but '"
                    + javaClass2.getText5()
                    + "' in the ancestor hierachy does not pass verification pass 1: "
                    + verificationResult2));
          }
          javaClass5 = javaClass2 = Repository.lookupClass(javaClass2.getText5());
        }
        if (javaClass != javaClass3) {
          throw new LoadingException(
              ("Exceptions attribute '"
                  + Pass2Verifier.getText(exceptionTable)
                  + "' references '"
                  + text2
                  + "' as an Exception but it is not a subclass of '"
                  + javaClass3.getText2()
                  + "'."));
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitConstantValue(ConstantValue constantValue) {
    this.checkIndex(constantValue, constantValue.getName_index(), this.CONST_Utf8);
    String text;
    if (!(text = ((ConstantUtf8) this.cp.getConstant(constantValue.getName_index())).getBytes())
        .equals("ConstantValue")) {
      throw new LoadingException(
          ("The ConstantValue attribute '"
              + Pass2Verifier.getText(constantValue)
              + "' is not correctly named 'ConstantValue' but '"
              + text
              + "'."));
    }
    Object result;
    if ((result = this.carrier.predecessor()) instanceof Field) {
      Field field = (Field) result;
      Type type = Type.getType(((ConstantUtf8) this.cp.getConstant(field.getCount())).getBytes());
      int value;
      if ((value = constantValue.getConstantValueIndex()) >= 0 && value < this.cplen) {
        Constant constant = this.cp.getConstant(value);
        if (!this.CONST_Long.isInstance(constant) || !type.equals(Type.LONG)) {
          if (!this.CONST_Float.isInstance(constant) || !type.equals(Type.FLOAT)) {
            if (!this.CONST_Double.isInstance(constant) || !type.equals(Type.DOUBLE)) {
              if (!this.CONST_Integer.isInstance(constant)
                  || !type.equals(Type.INT)
                      && !type.equals(Type.SHORT)
                      && !type.equals(Type.CHAR)
                      && !type.equals(Type.BYTE)
                      && !type.equals(Type.BOOLEAN)) {
                if (!this.CONST_String.isInstance(constant) || !type.equals(Type.STRING)) {
                  throw new LoadingException(
                      ("Illegal type of ConstantValue '"
                          + constantValue
                          + "' embedding Constant '"
                          + constant
                          + "'. It is referenced by field '"
                          + Pass2Verifier.getText(field)
                          + "' expecting a different type: '"
                          + type
                          + "'."));
                }
              }
            }
          }
        }
      } else {
        throw new LoadingException(
            ("Invalid index '"
                + value
                + "' used by '"
                + Pass2Verifier.getText(constantValue)
                + "'."));
      }
    }
  }

  @Override
  public void visitInnerClass(InnerClass innerClass) {}

  public Pass2Verifier$CPESSC_Visitor(Pass2Verifier pass2Verifier, JavaClass javaClass) {
    super();
    this.this$0 = pass2Verifier;
    this.field_names = new HashSet();
    this.field_names_and_desc = new HashSet();
    this.method_names_and_desc = new HashSet();
    this.jc = javaClass;
    this.cp = javaClass.getConstantPool();
    this.cplen = this.cp.getLength();
    this.CONST_Class = ConstantClass.class;
    this.CONST_String = ConstantString.class;
    this.CONST_Integer = ConstantInteger.class;
    this.CONST_Float = ConstantFloat.class;
    this.CONST_Long = ConstantLong.class;
    this.CONST_Double = ConstantDouble.class;
    this.CONST_NameAndType = ConstantNameAndType.class;
    this.CONST_Utf8 = ConstantUtf8.class;
    this.carrier = new DescendingVisitor(javaClass, this);
    this.carrier.visit();
  }

  public Pass2Verifier$CPESSC_Visitor(
      Pass2Verifier pass2Verifier, JavaClass javaClass, Pass2Verifier$1 pass2Verifier$1) {
    this(pass2Verifier, javaClass);
  }

  @Override
  public void visitLineNumber(LineNumber lineNumber) {}

  @Override
  public void visitJavaClass(JavaClass javaClass) {
    Attribute[] attributes = javaClass.getAttributes();
    byte value = 0;
    byte value2 = 0;
    boolean matches = new Pass2Verifier$InnerClassDetector(this.jc).innerClassReferenced();
    int value3;
    for (int value4 = value3 = 0; value4 < attributes.length; value4 = ++value3) {
      if (!(attributes[value3] instanceof SourceFile)
          && !(attributes[value3] instanceof Deprecated)
          && !(attributes[value3] instanceof InnerClasses)
          && !(attributes[value3] instanceof Synthetic)) {
        this.this$0.addMessage(
            "Attribute '"
                + Pass2Verifier.getText(attributes[value3])
                + "' as an attribute of the ClassFile structure '"
                + Pass2Verifier.getText(javaClass)
                + "' is unknown and will therefore be ignored.");
      }
      Attribute[] attributes2;
      if (attributes[value3] instanceof SourceFile) {
        if (value != 0) {
          throw new LoadingException(
              ("A ClassFile structure (like '"
                  + Pass2Verifier.getText(javaClass)
                  + "') may have no more than one SourceFile attribute."));
        }
        value = 1;
        attributes2 = attributes;
      } else {
        attributes2 = attributes;
      }
      if (attributes2[value3] instanceof InnerClasses) {
        boolean matches2;
        if (value2 == 0) {
          value2 = 1;
          matches2 = matches;
        } else {
          if (matches) {
            throw new LoadingException(
                ("A Classfile structure (like '"
                    + Pass2Verifier.getText(javaClass)
                    + "') must have exactly one InnerClasses attribute if at least one Inner Class"
                    + " is referenced (which is the case). More than one InnerClasses attribute was"
                    + " found."));
          }
          matches2 = matches;
        }
        if (!matches2) {
          this.this$0.addMessage(
              "No referenced Inner Class found, but InnerClasses attribute '"
                  + Pass2Verifier.getText(attributes[value3])
                  + "' found. Strongly suggest removal of that attribute.");
        }
      }
    }
    if (matches && value2 == 0) {
      this.this$0.addMessage(
          "A Classfile structure (like '"
              + Pass2Verifier.getText(javaClass)
              + "') must have exactly one InnerClasses attribute if at least one Inner Class is"
              + " referenced (which is the case). No InnerClasses attribute was found.");
    }
  }

  @Override
  public void visitConstantDouble(ConstantDouble constantDouble) {
    if (constantDouble.getTag() != 6) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantDouble) + "'."));
    }
  }

  @Override
  public void visitSynthetic(Synthetic synthetic) {
    this.checkIndex(synthetic, synthetic.getName_index(), this.CONST_Utf8);
    String text;
    if (!(text = ((ConstantUtf8) this.cp.getConstant(synthetic.getName_index())).getBytes())
        .equals("Synthetic")) {
      throw new LoadingException(
          ("The Synthetic attribute '"
              + Pass2Verifier.getText(synthetic)
              + "' is not correctly named 'Synthetic' but '"
              + text
              + "'."));
    }
  }

  @Override
  public void visitConstantUtf8(ConstantUtf8 constantUtf8) {
    if (constantUtf8.getTag() != 1) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantUtf8) + "'."));
    }
  }

  @Override
  public void visitLocalVariableTable(LocalVariableTable localVariableTable) {}

  public void checkIndex(Node node, int value, Class classValue) {
    if (value >= 0 && value < this.cplen) {
      Constant constant = this.cp.getConstant(value);
      if (!classValue.isInstance(constant)) {
        throw new ClassCastException(
            ("Illegal constant '"
                + Pass2Verifier.getText(constant)
                + "' at index '"
                + value
                + "'. '"
                + Pass2Verifier.getText(node)
                + "' expects a '"
                + classValue
                + "'."));
      }
    } else {
      throw new LoadingException(
          ("Invalid index '" + value + "' used by '" + Pass2Verifier.getText(node) + "'."));
    }
  }

  @Override
  public void visitInnerClasses(InnerClasses innerClasses) {
    this.checkIndex(innerClasses, innerClasses.getName_index(), this.CONST_Utf8);
    String text;
    if (!(text = ((ConstantUtf8) this.cp.getConstant(innerClasses.getName_index())).getBytes())
        .equals("InnerClasses")) {
      throw new LoadingException(
          ("The InnerClasses attribute '"
              + Pass2Verifier.getText(innerClasses)
              + "' is not correctly named 'InnerClasses' but '"
              + text
              + "'."));
    }
    InnerClass[] innerClasses2 = innerClasses.getInnerClasses();
    int value;
    for (int value2 = value = 0; value2 < innerClasses2.length; value2 = ++value) {
      this.checkIndex(innerClasses, innerClasses2[value].getInner_class_index(), this.CONST_Class);
      int value3;
      if ((value3 = innerClasses2[value].getOuter_class_index()) != 0) {
        this.checkIndex(innerClasses, value3, this.CONST_Class);
      }
      int value4;
      if ((value4 = innerClasses2[value].getInner_name_index()) != 0) {
        this.checkIndex(innerClasses, value4, this.CONST_Utf8);
      }
      if ((innerClasses2[value].getInner_access_flags() & -1568) != 0) {
        this.this$0.addMessage(
            "Unknown access flag for inner class '"
                + Pass2Verifier.getText(innerClasses2[value])
                + "' set (InnerClasses attribute '"
                + Pass2Verifier.getText(innerClasses)
                + "').");
      }
    }
  }

  @Override
  public void visitCode(Code code) {
    JavaClass javaClass;
    try {
      this.checkIndex(code, code.getName_index(), this.CONST_Utf8);
      String text;
      if (!(text = ((ConstantUtf8) this.cp.getConstant(code.getName_index())).getBytes())
          .equals("Code")) {
        throw new LoadingException(
            ("The Code attribute '"
                + Pass2Verifier.getText(code)
                + "' is not correctly named 'Code' but '"
                + text
                + "'."));
      }
      if (!(this.carrier.predecessor() instanceof Method)) {
        this.this$0.addMessage(
            "Code attribute '"
                + Pass2Verifier.getText(code)
                + "' is not declared in a method_info structure but in '"
                + this.carrier.predecessor()
                + "'. Ignored.");
      } else {
        Method method = (Method) this.carrier.predecessor();
        if (code.getCode().length == 0) {
          throw new LoadingException(
              ("Code array of Code attribute '"
                  + Pass2Verifier.getText(code)
                  + "' (method '"
                  + method
                  + "') must not be empty."));
        }
        CodeException[] exceptionTable = code.getExceptionTable();
        int value;
        for (int value2 = value = 0; value2 < exceptionTable.length; value2 = ++value) {
          int value3;
          if ((value3 = exceptionTable[value].getCatch_type()) != 0) {
            this.checkIndex(code, value3, this.CONST_Class);
            ConstantClass constantClass;
            ConstantClass constantClass2 =
                constantClass = (ConstantClass) this.cp.getConstant(value3);
            this.checkIndex(constantClass2, constantClass2.getNameIndex(), this.CONST_Utf8);
            String text2;
            VerificationResult verificationResult;
            if ((verificationResult =
                    VerifierFactory.getVerifier(
                            text2 =
                                ((ConstantUtf8) this.cp.getConstant(constantClass.getNameIndex()))
                                    .getBytes()
                                    .replace('/', '.'))
                        .createVerificationResult())
                != VerificationResult.VERIFICATION_RESULT2) {
              throw new LoadingException(
                  ("Code attribute '"
                      + Pass2Verifier.getText(code)
                      + "' (method '"
                      + method
                      + "') has an exception_table entry '"
                      + Pass2Verifier.getText(exceptionTable[value])
                      + "' that references '"
                      + text2
                      + "' as an Exception but it does not pass verification pass 1: "
                      + verificationResult));
            }
            JavaClass javaClass2 = Repository.lookupClass(text2);
            JavaClass javaClass3 = Repository.lookupClass(Type.THROWABLE.getClassName());
            JavaClass javaClass4 = Repository.lookupClass(Type.OBJECT.getClassName());
            JavaClass javaClass5 = javaClass2;
            while (true) {
              if (javaClass5 == javaClass4) {
                javaClass = javaClass2;
                break;
              }
              if (javaClass2 == javaClass3) {
                javaClass = javaClass2;
                break;
              }
              VerificationResult verificationResult2;
              if ((verificationResult2 =
                      VerifierFactory.getVerifier(javaClass2.getText5()).createVerificationResult())
                  != VerificationResult.VERIFICATION_RESULT2) {
                throw new LoadingException(
                    ("Code attribute '"
                        + Pass2Verifier.getText(code)
                        + "' (method '"
                        + method
                        + "') has an exception_table entry '"
                        + Pass2Verifier.getText(exceptionTable[value])
                        + "' that references '"
                        + text2
                        + "' as an Exception but '"
                        + javaClass2.getText5()
                        + "' in the ancestor hierachy does not pass verification pass 1: "
                        + verificationResult2));
              }
              javaClass5 = javaClass2 = Repository.lookupClass(javaClass2.getText5());
            }
            if (javaClass != javaClass3) {
              throw new LoadingException(
                  ("Code attribute '"
                      + Pass2Verifier.getText(code)
                      + "' (method '"
                      + method
                      + "') has an exception_table entry '"
                      + Pass2Verifier.getText(exceptionTable[value])
                      + "' that references '"
                      + text2
                      + "' as an Exception but it is not a subclass of '"
                      + javaClass3.getText2()
                      + "'."));
            }
          }
        }
        int value4 = -1;
        Method[] methods =
            Repository.lookupClass(Pass2Verifier.access$400(this.this$0).getClassName())
                .getMethods();
        int value5;
        int value6 = value5 = 0;
        while (true) {
          if (value6 >= methods.length) {
            value6 = value4;
            break;
          }
          if (method == methods[value5]) {
            value6 = value4 = value5;
            break;
          }
          value6 = ++value5;
        }
        if (value6 < 0) {
          throw new AssertionViolatedException(
              "Could not find a known BCEL Method object in the corresponding BCEL JavaClass"
                  + " object.");
        }
        Pass2Verifier.access$500(this.this$0)[value4] =
            new LocalVariablesInfo(code.getMax_locals());
        int value7 = 0;
        Attribute[] attributes = code.getAttributes();
        for (int index = 0; index < attributes.length; index++) {
          Attribute[] attributes2;
          if (!(attributes[index] instanceof LineNumberTable)
              && !(attributes[index] instanceof LocalVariableTable)) {
            Pass2Verifier pass2Verifier = this.this$0;
            StringBuilder stringBuilder = new StringBuilder().insert(0, "Attribute '");
            attributes2 = attributes;
            pass2Verifier.addMessage(
                stringBuilder
                    .append(Pass2Verifier.getText(attributes[index]))
                    .append("' as an attribute of Code attribute '")
                    .append(Pass2Verifier.getText(code))
                    .append("' (method '")
                    .append(method)
                    .append("') is unknown and will therefore be ignored.")
                    .toString());
          } else {
            this.this$0.addMessage(
                "Attribute '"
                    + Pass2Verifier.getText(attributes[index])
                    + "' as an attribute of Code attribute '"
                    + Pass2Verifier.getText(code)
                    + "' (method '"
                    + method
                    + "') will effectively be ignored and is only useful for debuggers and such.");
            attributes2 = attributes;
          }
          if (attributes2[index] instanceof LocalVariableTable) {
            LocalVariableTable localVariableTable;
            LocalVariableTable localVariableTable2 =
                localVariableTable = (LocalVariableTable) attributes[index];
            this.checkIndex(
                localVariableTable2, localVariableTable2.getName_index(), this.CONST_Utf8);
            String text3;
            if (!(text3 =
                    ((ConstantUtf8) this.cp.getConstant(localVariableTable.getName_index()))
                        .getBytes())
                .equals("LocalVariableTable")) {
              throw new LoadingException(
                  ("The LocalVariableTable attribute '"
                      + Pass2Verifier.getText(localVariableTable)
                      + "' is not correctly named 'LocalVariableTable' but '"
                      + text3
                      + "'."));
            }
            LocalVariable[] localVariableTable3 = localVariableTable.getLocalVariableTable();
            int value8;
            for (value6 = value8 = 0; value6 < localVariableTable3.length; value6 = ++value8) {
              this.checkIndex(
                  localVariableTable, localVariableTable3[value8].getName_index(), this.CONST_Utf8);
              String text4;
              if (!Pass2Verifier.matchesText4(
                  text4 =
                      ((ConstantUtf8)
                              this.cp.getConstant(localVariableTable3[value8].getName_index()))
                          .getBytes())) {
                throw new LoadingException(
                    ("LocalVariableTable '"
                        + Pass2Verifier.getText(localVariableTable)
                        + "' references a local variable by the name '"
                        + text4
                        + "' which is not a legal Java simple name."));
              }
              this.checkIndex(
                  localVariableTable,
                  localVariableTable3[value8].getSignature_index(),
                  this.CONST_Utf8);
              String bytes =
                  ((ConstantUtf8)
                          this.cp.getConstant(localVariableTable3[value8].getSignature_index()))
                      .getBytes();
              Type type;
              try {
                type = Type.getType(bytes);
              } catch (ClassFormatException classFormatException) {
                throw new LoadingException(
                    ("Illegal descriptor (==signature) '"
                        + bytes
                        + "' used by LocalVariable '"
                        + Pass2Verifier.getText(localVariableTable3[value8])
                        + "' referenced by '"
                        + Pass2Verifier.getText(localVariableTable)
                        + "'."));
              }
              int index2 = localVariableTable3[value8].getIndex();
              Code code2;
              if (type != Type.LONG && type != Type.DOUBLE) {
                value6 = index2;
                code2 = code;
              } else {
                value6 = index2 + 1;
                code2 = code;
              }
              if (value6 >= code2.getMax_locals()) {
                throw new LoadingException(
                    ("LocalVariableTable attribute '"
                        + Pass2Verifier.getText(localVariableTable)
                        + "' references a LocalVariable '"
                        + Pass2Verifier.getText(localVariableTable3[value8])
                        + "' with an index that exceeds the surrounding Code attribute's max_locals"
                        + " value of '"
                        + code.getMax_locals()
                        + "'."));
              }
              try {
                Pass2Verifier.access$500(this.this$0)[value4].add(
                    index2,
                    text4,
                    localVariableTable3[value8].getStart_pc(),
                    localVariableTable3[value8].getLength(),
                    type);
              } catch (ClassConstraintException classConstraintException) {
                throw new LoadingException(
                    ("Conflicting information in LocalVariableTable '"
                        + Pass2Verifier.getText(localVariableTable)
                        + "' found in Code attribute '"
                        + Pass2Verifier.getText(code)
                        + "' (method '"
                        + Pass2Verifier.getText(method)
                        + "'). "
                        + classConstraintException.getMessage()));
              }
            }
            if (++value7 > code.getMax_locals()) {
              throw new LoadingException(
                  ("Number of LocalVariableTable attributes of Code attribute '"
                      + Pass2Verifier.getText(code)
                      + "' (method '"
                      + Pass2Verifier.getText(method)
                      + "') exceeds number of local variable slots '"
                      + code.getMax_locals()
                      + "' ('There may be no more than one LocalVariableTable attribute per local"
                      + " variable in the Code attribute.')."));
            }
          }
        }
      }
    } catch (ClassNotFoundException classNotFoundException) {
      throw new AssertionViolatedException(("Missing class: " + classNotFoundException.toString()));
    }
  }

  @Override
  public void visitConstantInterfaceMethodref(
      ConstantInterfaceMethodref constantInterfaceMethodref) {
    if (constantInterfaceMethodref.getTag() != 11) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantInterfaceMethodref) + "'."));
    }
    this.checkIndex(
        constantInterfaceMethodref, constantInterfaceMethodref.getClass_index(), this.CONST_Class);
    this.checkIndex(
        constantInterfaceMethodref,
        constantInterfaceMethodref.getName_and_type_index(),
        this.CONST_NameAndType);
  }

  @Override
  public void visitConstantMethodref(ConstantMethodref constantMethodref) {
    if (constantMethodref.getTag() != 10) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantMethodref) + "'."));
    }
    this.checkIndex(constantMethodref, constantMethodref.getClass_index(), this.CONST_Class);
    this.checkIndex(
        constantMethodref, constantMethodref.getName_and_type_index(), this.CONST_NameAndType);
  }

  @Override
  public void visitConstantClass(ConstantClass constantClass) {
    if (constantClass.getTag() != 7) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantClass) + "'."));
    }
    this.checkIndex(constantClass, constantClass.getNameIndex(), this.CONST_Utf8);
  }

  @Override
  public void visitConstantInteger(ConstantInteger constantInteger) {
    if (constantInteger.getTag() != 3) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantInteger) + "'."));
    }
  }

  @Override
  public void visitConstantNameAndType(ConstantNameAndType constantNameAndType) {
    if (constantNameAndType.getTag() != 12) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantNameAndType) + "'."));
    }
    this.checkIndex(constantNameAndType, constantNameAndType.getName_index(), this.CONST_Utf8);
    this.checkIndex(constantNameAndType, constantNameAndType.getSignature_index(), this.CONST_Utf8);
  }

  @Override
  public void visitDeprecated(Deprecated deprecated) {
    this.checkIndex(deprecated, deprecated.getName_index(), this.CONST_Utf8);
    String text;
    if (!(text = ((ConstantUtf8) this.cp.getConstant(deprecated.getName_index())).getBytes())
        .equals("Deprecated")) {
      throw new LoadingException(
          ("The Deprecated attribute '"
              + Pass2Verifier.getText(deprecated)
              + "' is not correctly named 'Deprecated' but '"
              + text
              + "'."));
    }
  }

  @Override
  public void visitConstantLong(ConstantLong constantLong) {
    if (constantLong.getTag() != 5) {
      throw new LoadingException(
          ("Wrong constant tag in '" + Pass2Verifier.getText(constantLong) + "'."));
    }
  }

  @Override
  public void visitConstantPool(ConstantPool constantPool) {}

  @Override
  public void visitMethod(Method method) {
    this.checkIndex(method, method.getCount3(), this.CONST_Utf8);
    String text;
    if (!Pass2Verifier.matchesEnabled2(text = method.getText(), true)) {
      throw new LoadingException(
          ("Method '" + Pass2Verifier.getText(method) + "' has illegal name '" + text + "'."));
    }
    this.checkIndex(method, method.getCount(), this.CONST_Utf8);
    String bytes = ((ConstantUtf8) this.cp.getConstant(method.getCount())).getBytes();
    Type[] types;
    Type type;
    try {
      type = Type.getReturnType(bytes);
      types = Type.getArgumentTypes(bytes);
    } catch (ClassFormatException classFormatException) {
      throw new LoadingException(
          ("Illegal descriptor (==signature) '"
              + bytes
              + "' used by Method '"
              + Pass2Verifier.getText(method)
              + "'."));
    }
    Type type2 = type;
    if (type2 instanceof ArrayType) {
      type2 = ((ArrayType) type2).getBasicType();
    }
    VerificationResult verificationResult;
    if (type2 instanceof ObjectType
        && (verificationResult =
                VerifierFactory.getVerifier(((ObjectType) type2).getClassName())
                    .createVerificationResult())
            != VerificationResult.VERIFICATION_RESULT2) {
      throw new LoadingException(
          ("Method '"
              + Pass2Verifier.getText(method)
              + "' has a return type that does not pass verification pass 1: '"
              + verificationResult
              + "'."));
    }
    int value;
    for (int value2 = value = 0; value2 < types.length; value2 = ++value) {
      Type type3;
      if ((type3 = types[value]) instanceof ArrayType) {
        type3 = ((ArrayType) type3).getBasicType();
      }
      VerificationResult verificationResult2;
      if (type3 instanceof ObjectType
          && (verificationResult2 =
                  VerifierFactory.getVerifier(((ObjectType) type3).getClassName())
                      .createVerificationResult())
              != VerificationResult.VERIFICATION_RESULT2) {
        throw new LoadingException(
            ("Method '"
                + Pass2Verifier.getText(method)
                + "' has an argument type that does not pass verification pass 1: '"
                + verificationResult2
                + "'."));
      }
    }
    if (text.equals("<clinit>") && types.length != 0) {
      throw new LoadingException(
          ("Method '"
              + Pass2Verifier.getText(method)
              + "' has illegal name '"
              + text
              + "'. It's name resembles the class or interface initialization method which it isn't"
              + " because of its arguments (==descriptor)."));
    }
    if (this.jc.isEnabled17()) {
      int value3 = 0;
      if (method.isEnabled9()) {
        value3++;
      }
      if (method.isEnabled13()) {
        value3++;
      }
      if (method.isEnabled6()) {
        value3++;
      }
      if (value3 > 1) {
        throw new LoadingException(
            ("Method '"
                + Pass2Verifier.getText(method)
                + "' must only have at most one of its ACC_PRIVATE, ACC_PROTECTED, ACC_PUBLIC"
                + " modifiers set."));
      }
      if (method.isEnabled15()) {
        if (method.isEnabled3()) {
          throw new LoadingException(
              ("Abstract method '"
                  + Pass2Verifier.getText(method)
                  + "' must not have the ACC_FINAL modifier set."));
        }
        if (method.isEnabled14()) {
          throw new LoadingException(
              ("Abstract method '"
                  + Pass2Verifier.getText(method)
                  + "' must not have the ACC_NATIVE modifier set."));
        }
        if (method.isEnabled9()) {
          throw new LoadingException(
              ("Abstract method '"
                  + Pass2Verifier.getText(method)
                  + "' must not have the ACC_PRIVATE modifier set."));
        }
        if (method.isEnabled11()) {
          throw new LoadingException(
              ("Abstract method '"
                  + Pass2Verifier.getText(method)
                  + "' must not have the ACC_STATIC modifier set."));
        }
        if (method.isEnabled7()) {
          throw new LoadingException(
              ("Abstract method '"
                  + Pass2Verifier.getText(method)
                  + "' must not have the ACC_STRICT modifier set."));
        }
        if (method.isEnabled12()) {
          throw new LoadingException(
              ("Abstract method '"
                  + Pass2Verifier.getText(method)
                  + "' must not have the ACC_SYNCHRONIZED modifier set."));
        }
      }
    } else if (!text.equals("<clinit>")) {
      if (!method.isEnabled6()) {
        throw new LoadingException(
            ("Interface method '"
                + Pass2Verifier.getText(method)
                + "' must have the ACC_PUBLIC modifier set but hasn't!"));
      }
      if (!method.isEnabled15()) {
        throw new LoadingException(
            ("Interface method '"
                + Pass2Verifier.getText(method)
                + "' must have the ACC_STATIC modifier set but hasn't!"));
      }
      if (method.isEnabled9()
          || method.isEnabled13()
          || method.isEnabled11()
          || method.isEnabled3()
          || method.isEnabled12()
          || method.isEnabled14()
          || method.isEnabled7()) {
        throw new LoadingException(
            ("Interface method '"
                + Pass2Verifier.getText(method)
                + "' must not have any of the ACC_PRIVATE, ACC_PROTECTED, ACC_STATIC, ACC_FINAL,"
                + " ACC_SYNCHRONIZED, ACC_NATIVE, ACC_ABSTRACT, ACC_STRICT modifiers set."));
      }
    }
    if (!text.equals("<init>")
        || !method.isEnabled11()
            && !method.isEnabled3()
            && !method.isEnabled12()
            && !method.isEnabled14()
            && !method.isEnabled15()) {
      if (text.equals("<clinit>")) {
        if ((method.getAccess_flags2() & -2049) > 0) {
          this.this$0.addMessage(
              "Class or interface initialization method '"
                  + Pass2Verifier.getText(method)
                  + "' has superfluous access modifier(s) set: everything but ACC_STRICT is"
                  + " ignored.");
        }
        if (method.isEnabled15()) {
          throw new LoadingException(
              ("Class or interface initialization method '"
                  + Pass2Verifier.getText(method)
                  + "' must not be abstract. This contradicts the Java Language Specification,"
                  + " Second Edition (which omits this constraint) but is common practice of"
                  + " existing verifiers."));
        }
      }
      if ((method.getAccess_flags2() & -3392) > 0) {
        this.this$0.addMessage(
            "Method '"
                + Pass2Verifier.getText(method)
                + "' has access flag(s) other than ACC_PUBLIC, ACC_PRIVATE, ACC_PROTECTED,"
                + " ACC_STATIC, ACC_FINAL, ACC_SYNCHRONIZED, ACC_NATIVE, ACC_ABSTRACT, ACC_STRICT"
                + " set (ignored).");
      }
      String text2 = "" + text + bytes;
      if (this.method_names_and_desc.contains(text2)) {
        throw new LoadingException(
            ("No two methods (like '"
                + Pass2Verifier.getText(method)
                + "') are allowed have same names and desciptors!"));
      }
      this.method_names_and_desc.add(text2);
      Attribute[] attributes = method.getAttributes();
      int value4 = 0;
      int value5 = 0;
      for (int value6 = value5; value6 < attributes.length; value6 = ++value5) {
        if (!(attributes[value5] instanceof Code)
            && !(attributes[value5] instanceof ExceptionTable)
            && !(attributes[value5] instanceof Synthetic)
            && !(attributes[value5] instanceof Deprecated)) {
          this.this$0.addMessage(
              "Attribute '"
                  + Pass2Verifier.getText(attributes[value5])
                  + "' as an attribute of Method '"
                  + Pass2Verifier.getText(method)
                  + "' is unknown and will therefore be ignored.");
        }
        if (!(attributes[value5] instanceof Code)
            && !(attributes[value5] instanceof ExceptionTable)) {
          this.this$0.addMessage(
              "Attribute '"
                  + Pass2Verifier.getText(attributes[value5])
                  + "' as an attribute of Method '"
                  + Pass2Verifier.getText(method)
                  + "' is neither Code nor Exceptions and is therefore only of use for debuggers"
                  + " and such.");
        }
        if (attributes[value5] instanceof Code && (method.isEnabled14() || method.isEnabled15())) {
          throw new LoadingException(
              ("Native or abstract methods like '"
                  + Pass2Verifier.getText(method)
                  + "' must not have a Code attribute like '"
                  + Pass2Verifier.getText(attributes[value5])
                  + "'."));
        }
        if (attributes[value5] instanceof Code) {
          value4++;
        }
      }
      if (!method.isEnabled14() && !method.isEnabled15() && value4 != 1) {
        throw new LoadingException(
            ("Non-native, non-abstract methods like '"
                + Pass2Verifier.getText(method)
                + "' must have exactly one Code attribute (found: "
                + value4
                + ")."));
      }
    } else {
      throw new LoadingException(
          ("Instance initialization method '"
              + Pass2Verifier.getText(method)
              + "' must not have any of the ACC_STATIC, ACC_FINAL, ACC_SYNCHRONIZED, ACC_NATIVE,"
              + " ACC_ABSTRACT modifiers set."));
    }
  }

  @Override
  public void visitSourceFile(SourceFile sourceFile) {
    this.checkIndex(sourceFile, sourceFile.getName_index(), this.CONST_Utf8);
    String text;
    if (!(text = ((ConstantUtf8) this.cp.getConstant(sourceFile.getName_index())).getBytes())
        .equals("SourceFile")) {
      throw new LoadingException(
          ("The SourceFile attribute '"
              + Pass2Verifier.getText(sourceFile)
              + "' is not correctly named 'SourceFile' but '"
              + text
              + "'."));
    }
    this.checkIndex(sourceFile, sourceFile.getSourceFileIndex(), this.CONST_Utf8);
    String text2;
    String text3 =
        text2 = ((ConstantUtf8) this.cp.getConstant(sourceFile.getSourceFileIndex())).getBytes();
    String text4 = text3.toLowerCase(Locale.ENGLISH);
    if (text3.indexOf(47) != -1
        || text2.indexOf(92) != -1
        || text2.indexOf(58) != -1
        || text4.lastIndexOf(".java") == -1) {
      this.this$0.addMessage(
          "SourceFile attribute '"
              + Pass2Verifier.getText(sourceFile)
              + "' has a funny name: remember not to confuse certain parsers working on javap's"
              + " output. Also, this name ('"
              + text2
              + "') is considered an unqualified (simple) file name only.");
    }
  }
}
