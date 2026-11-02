package com.allatori.lib.bcel.verifier.statics;

import com.allatori.lib.bcel.classfile.Constant;
import com.allatori.lib.bcel.classfile.ConstantClass;
import com.allatori.lib.bcel.classfile.ConstantPool;
import com.allatori.lib.bcel.classfile.ConstantUtf8;
import com.allatori.lib.bcel.classfile.DescendingVisitor;
import com.allatori.lib.bcel.classfile.EmptyVisitor;
import com.allatori.lib.bcel.classfile.JavaClass;

public class Pass2Verifier$InnerClassDetector extends EmptyVisitor {

  public ConstantPool cp;

  public boolean hasInnerClass;

  public JavaClass jc;

  public boolean innerClassReferenced() {
    return this.hasInnerClass;
  }

  @Override
  public void visitConstantClass(ConstantClass constantClass) {
    Constant constant;
    if ((constant = this.cp.getConstant(constantClass.getNameIndex())) instanceof ConstantUtf8) {
      String bytes = ((ConstantUtf8) constant).getBytes();
      if (bytes.startsWith(this.jc.getText2().replace('.', '/') + "$")) {
        this.hasInnerClass = true;
      }
    }
  }

  public Pass2Verifier$InnerClassDetector(JavaClass javaClass) {
    super();
    this.hasInnerClass = false;
    this.jc = javaClass;
    this.cp = this.jc.getConstantPool();
    new DescendingVisitor(this.jc, this).visit();
  }
}
