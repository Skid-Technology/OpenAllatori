package com.allatori.lib.bcel.util;

import com.allatori.lib.bcel.Constants;
import com.allatori.lib.bcel.classfile.ClassParser;
import com.allatori.lib.bcel.classfile.ConstantValue;
import com.allatori.lib.bcel.classfile.EmptyVisitor;
import com.allatori.lib.bcel.classfile.Field;
import com.allatori.lib.bcel.classfile.JavaClass;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.classfile.Utility;
import com.allatori.lib.bcel.generic.ArrayType;
import com.allatori.lib.bcel.generic.ConstantPoolGen;
import com.allatori.lib.bcel.generic.MethodGen;
import com.allatori.lib.bcel.generic.Type;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

public class BCELifier extends EmptyVisitor {

  public static final int FLAG_FOR_UNKNOWN = -1;

  public PrintWriter _out;

  public ConstantPoolGen _cp;

  public static final int FLAG_FOR_CLASS = 0;

  public JavaClass _clazz;

  public static final int FLAG_FOR_METHOD = 1;

  @Override
  public void visitField(Field field) {
    this._out.println();
    this._out.println(
        "    field = new FieldGen("
            + printFlags(field.getAccess_flags2())
            + ", "
            + printType(field.getText2())
            + ", \""
            + field.getText()
            + "\", _cp);");
    ConstantValue constantValue;
    if ((constantValue = field.getConstantValue()) != null) {
      String text = constantValue.toString();
      this._out.println("    field.setInitValue(" + text + ")");
    }
    this._out.println("    _cg.addField(field.getField());");
  }

  @Override
  public void visitMethod(Method method) {
    MethodGen methodGen;
    MethodGen methodGen2 = methodGen = new MethodGen(method, this._clazz.getText2(), this._cp);
    Type returnType = methodGen2.getReturnType();
    Type[] argumentTypes = methodGen2.getArgumentTypes();
    this._out.println("    InstructionList il = new InstructionList();");
    this._out.println(
        "    MethodGen method = new MethodGen("
            + printFlags(method.getAccess_flags2(), 1)
            + ", "
            + printType(returnType)
            + ", "
            + printArgumentTypes(argumentTypes)
            + ", "
            + "new String[] { "
            + Utility.printArray(methodGen.getArgumentNames(), false, true)
            + " }, \""
            + method.getText()
            + "\", \""
            + this._clazz.getText2()
            + "\", il, _cp);");
    this._out.println();
    new BCELFactory(methodGen, this._out).start();
    this._out.println("    method.setMaxStack();");
    this._out.println("    method.setMaxLocals();");
    this._out.println("    _cg.addMethod(method.getMethod());");
    this._out.println("    il.dispose();");
  }

  public BCELifier(JavaClass javaClass, OutputStream outputStream) {
    super();
    this._clazz = javaClass;
    this._out = new PrintWriter(outputStream);
    this._cp = new ConstantPoolGen(this._clazz.getConstantPool());
  }

  public static String printType(Type type) {
    return printType(type.getSignature());
  }

  public void start() {
    this.visitJavaClass(this._clazz);
    this._out.flush();
  }

  public static String printArgumentTypes(Type[] types) {
    if (types.length == 0) {
      return "Type.NO_ARGS";
    }
    StringBuffer stringBuffer = new StringBuffer();
    int value;
    for (int value2 = value = 0; value2 < types.length; value2 = ++value) {
      stringBuffer.append(printType(types[value]));
      if (value < types.length - 1) {
        stringBuffer.append(", ");
      }
    }
    return "new Type[] { " + stringBuffer.toString() + " }";
  }

  @Override
  public void visitJavaClass(JavaClass javaClass) {
    String text2 = javaClass.getText2();
    String text5 = javaClass.getText5();
    String text4 = javaClass.getText4();
    String text = Utility.printArray(javaClass.getInterfaceNames(), false, true);
    if (!"".equals(text4)) {
      text2 = text2.substring(text4.length() + 1);
      this._out.println("package " + text4 + ";");
      this._out.println();
    }
    this._out.println("import org.apache.bcel.generic.*;");
    this._out.println("import org.apache.bcel.classfile.*;");
    this._out.println("import org.apache.bcel.*;");
    this._out.println("import java.io.*;");
    this._out.println();
    this._out.println("public class " + text2 + "Creator implements Constants {");
    this._out.println("  private InstructionFactory _factory;");
    this._out.println("  private ConstantPoolGen    _cp;");
    this._out.println("  private ClassGen           _cg;");
    this._out.println();
    this._out.println("  public " + text2 + "Creator() {");
    this._out.println(
        "    _cg = new ClassGen(\""
            + ("".equals(text4)
                ? text2
                : new StringBuilder().insert(0, text4).append(".").append(text2).toString())
            + "\", \""
            + text5
            + "\", "
            + "\""
            + javaClass.getText()
            + "\", "
            + printFlags(javaClass.getAccess_flags2(), 0)
            + ", "
            + "new String[] { "
            + text
            + " });");
    this._out.println();
    this._out.println("    _cp = _cg.getConstantPool();");
    this._out.println("    _factory = new InstructionFactory(_cg, _cp);");
    this._out.println("  }");
    this._out.println();
    this.printCreate();
    Field[] fields;
    if ((fields = javaClass.getFields()).length > 0) {
      int value = 0;
      this._out.println("  private void createFields() {");
      this._out.println("    FieldGen field;");
      for (int index = 0; value < fields.length; value = index) {
        int value2 = index;
        Field[] fields2 = fields;
        index++;
        fields2[value2].accept(this);
      }
      this._out.println("  }");
      this._out.println();
    }
    Method[] methods = javaClass.getMethods();
    int value3;
    for (int value4 = value3 = 0; value4 < methods.length; value4 = value3) {
      this._out.println("  private void createMethod_" + value3 + "() {");
      methods[value3].accept(this);
      PrintWriter printWriter = this._out;
      value3++;
      printWriter.println((String) "  }");
      this._out.println();
    }
    this.printMain();
    this._out.println("}");
  }

  public static void main(String[] texts) throws Exception {
    JavaClass javaClass;
    String text;
    if ((javaClass = com.allatori.lib.bcel.Repository.lookupClass(text = texts[0])) == null) {
      javaClass = new ClassParser(text).parse();
    }
    new BCELifier(javaClass, System.out).start();
  }

  public static String printFlags(int value) {
    return printFlags(value, -1);
  }

  public void printCreate() {
    this._out.println("  public void create(OutputStream out) throws IOException {");
    if (this._clazz.getFields().length > 0) {
      this._out.println("    createFields();");
    }
    Method[] methods = this._clazz.getMethods();
    int value;
    for (int value2 = value = 0; value2 < methods.length; value2 = value) {
      PrintWriter printWriter = this._out;
      StringBuilder stringBuilder =
          new StringBuilder().insert(0, "    createMethod_").append(value);
      value++;
      printWriter.println(stringBuilder.append((String) "();").toString());
    }
    this._out.println("    _cg.getJavaClass().dump(out);");
    this._out.println("  }");
    this._out.println();
  }

  public static String printType(String text) {
    byte value;
    Type type;
    if ((value = (type = Type.getType(text)).getType()) <= 12) {
      return "Type." + Constants.TEXTS[value].toUpperCase(Locale.ENGLISH);
    } else if (type.toString().equals("java.lang.String")) {
      return "Type.STRING";
    } else if (type.toString().equals("java.lang.Object")) {
      return "Type.OBJECT";
    } else if (type.toString().equals("java.lang.StringBuffer")) {
      return "Type.STRINGBUFFER";
    } else if (type instanceof ArrayType) {
      ArrayType arrayType = (ArrayType) type;
      return "new ArrayType("
          + printType(arrayType.getBasicType())
          + ", "
          + arrayType.getDimensions()
          + ")";
    } else {
      return "new ObjectType(\"" + Utility.signatureToString(text, false) + "\")";
    }
  }

  public void printMain() {
    String text2 = this._clazz.getText2();
    this._out.println("  public static void main(String[] args) throws Exception {");
    this._out.println("    " + text2 + "Creator creator = new " + text2 + "Creator();");
    this._out.println("    creator.create(new FileOutputStream(\"" + text2 + ".class\"));");
    this._out.println("  }");
  }

  public static String printFlags(int value, int value2) {
    if (value == 0) {
      return "0";
    }
    StringBuffer stringBuffer = new StringBuffer();
    int value3 = 0;
    int value4;
    for (int value5 = value4 = 1; value5 <= 16384; value5 = value4) {
      label37:
      {
        if ((value & value4) != 0) {
          if (value4 == 32 && value2 == 0) {
            value5 = value4;
            stringBuffer.append("ACC_SUPER | ");
            break label37;
          }
          if (value4 == 64 && value2 == 1) {
            value5 = value4;
            stringBuffer.append("ACC_BRIDGE | ");
            break label37;
          }
          if (value4 == 128 && value2 == 1) {
            value5 = value4;
            stringBuffer.append("ACC_VARARGS | ");
            break label37;
          }
          stringBuffer
              .append("ACC_")
              .append(Constants.TEXTS9[value3].toUpperCase(Locale.ENGLISH))
              .append(" | ");
        }
        value5 = value4;
      }
      int value6 = value5;
      value3++;
      value4 = value6 << (byte) 1;
    }
    String text = stringBuffer.toString();
    return text.substring(0, text.length() - 3);
  }
}
