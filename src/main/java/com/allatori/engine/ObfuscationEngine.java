package com.allatori.engine;

import com.allatori.bytecode.ConstantPoolEditor;
import com.allatori.config.Configuration;
import com.allatori.lib.bcel.classfile.Method;
import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.lib.bcel.util.comparator.MethodComparator;
import com.allatori.model.ClassStorage;
import com.allatori.naming.NameRenamer;
import com.allatori.naming.generator.LowercaseConfusableNameGenerator;
import com.allatori.naming.generator.MixedConfusableNameGenerator;
import com.allatori.transform.ClassConstantPoolTransformer;
import com.allatori.transform.ClassTransformer;
import com.allatori.transform.ControlFlowTransformer;
import com.allatori.transform.DistributedStringEncryptionTransformer;
import com.allatori.transform.FieldAccessTransformer;
import com.allatori.transform.GlobalStringEncryptionTransformer;
import com.allatori.transform.HardwareIdTransformer;
import com.allatori.transform.MemberConstantPoolTransformer;
import com.allatori.transform.MemberReorderTransformer;
import com.allatori.transform.MemberVisibilityTransformer;
import com.allatori.transform.MethodAccessTransformer;
import com.allatori.transform.OptimizerTransformer;
import com.allatori.transform.RemoveFieldsTransformer;
import com.allatori.transform.RemoveMethodsTransformer;
import com.allatori.transform.StripAttributesTransformer;
import com.allatori.util.Log;
import java.util.Iterator;
import java.util.Vector;

public class ObfuscationEngine {

  public ClassStorage classStorage;

  public boolean enabled;

  public void update() {
    if (Configuration.getLogOptions().isText()) {
      new HardwareIdTransformer(this.classStorage).transform(null);
    }
    Vector vector = new Vector();
    vector.add(new MemberVisibilityTransformer(this.classStorage));
    vector.add(new RemoveMethodsTransformer(this.classStorage));
    vector.add(new RemoveFieldsTransformer(this.classStorage));
    if (Configuration.getDebugInfoOptions().getText2() != null
        && Configuration.getDebugInfoOptions().getText() != null) {
      vector.add(new FieldAccessTransformer(this.classStorage));
    }
    int stringEncryptionVersion =
        Configuration.getObfuscationOptions().getStringEncryptionVersion();
    Configuration.getObfuscationOptions();
    if (stringEncryptionVersion == 1) {
      DistributedStringEncryptionTransformer distributedStringEncryptionTransformer;
      if ((distributedStringEncryptionTransformer =
              new DistributedStringEncryptionTransformer(this.classStorage))
          .isEnabled()) {
        vector.add(distributedStringEncryptionTransformer);
        this.enabled = true;
      } else {
        vector.add(new GlobalStringEncryptionTransformer(this.classStorage));
      }
    } else {
      vector.add(new GlobalStringEncryptionTransformer(this.classStorage));
    }
    vector.add(new MethodAccessTransformer(this.classStorage));
    vector.add(new ControlFlowTransformer(this.classStorage));
    vector.add(new OptimizerTransformer(this.classStorage));
    Iterator classesByName2 = this.classStorage.getClassesByName2();
    while (classesByName2.hasNext()) {
      ClassGen classGen = (ClassGen) classesByName2.next();
      Iterator iterator2;
      if (!Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen)) {
        for (Iterator iterator3 = iterator2 = vector.iterator();
            iterator3.hasNext();
            iterator3 = iterator2) {
          ((ClassTransformer) iterator2.next()).transform(classGen);
        }
      }
    }
    Iterator iterator4;
    Iterator iterator5 = iterator4 = vector.iterator();
    while (iterator5.hasNext()) {
      ClassTransformer classTransformer = (ClassTransformer) iterator4.next();
      iterator5 = iterator4;
      classTransformer.finish();
    }
  }

  public ObfuscationEngine(ClassStorage classStorage2) {
    super();
    this.enabled = false;
    this.classStorage = classStorage2;
    Method.setComparator(MethodComparator.getMethodComparator());
    MixedConfusableNameGenerator.processCount(
        Integer.toBinaryString(classStorage2.getClassesByName()).length());
    LowercaseConfusableNameGenerator.processCount(
        Integer.toBinaryString(classStorage2.getClassesByName()).length() + 1);
  }

  public void update2() {
    ConstantPoolEditor constantPoolEditor = new ConstantPoolEditor(this.classStorage);
    Iterator classesByName2 = this.classStorage.getClassesByName2();
    while (classesByName2.hasNext()) {
      ClassGen classGen = (ClassGen) classesByName2.next();
      if (!Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen)) {
        constantPoolEditor.processClassGen(classGen);
      }
    }
  }

  public void obfuscate() {
    Iterator classesByName2 = this.classStorage.getClassesByName2();
    ObfuscationEngine obfuscationEngine;
    while (true) {
      if (classesByName2.hasNext()) {
        ClassGen classGen;
        if ((classGen = (ClassGen) classesByName2.next())
            .getSuperclassName()
            .startsWith("javax.microedition.")) {
          Configuration.getObfuscationOptions().setEnabled(true);
          obfuscationEngine = this;
          break;
        }
        if (!classGen.getSuperclassName().startsWith("android.")) {
          continue;
        }
        Configuration.getObfuscationOptions().setEnabled3(true);
        obfuscationEngine = this;
        break;
      }
      obfuscationEngine = this;
      break;
    }
    obfuscationEngine.update();
    MemberReorderTransformer memberReorderTransformer =
        new MemberReorderTransformer(this.classStorage);
    Iterator classesByName22 = this.classStorage.getClassesByName2();
    while (classesByName22.hasNext()) {
      ClassGen classGen2 = (ClassGen) classesByName22.next();
      if (!Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen2)) {
        memberReorderTransformer.transform(classGen2);
      }
    }
    this.renameMembers();
    memberReorderTransformer.finish();
    ClassTransformer classTransformer;
    if (this.enabled) {
      classTransformer = new ClassConstantPoolTransformer(this.classStorage);
      obfuscationEngine = this;
    } else {
      classTransformer = new MemberConstantPoolTransformer(this.classStorage);
      obfuscationEngine = this;
    }
    Iterator classesByName23 = obfuscationEngine.classStorage.getClassesByName2();
    while (classesByName23.hasNext()) {
      ClassGen classGen3 = (ClassGen) classesByName23.next();
      if (!Configuration.getIgnoreClassesOptions().matchesClassGen(this.classStorage, classGen3)) {
        classTransformer.transform(classGen3);
      }
    }
    classTransformer.finish();
    this.update2();
    new StripAttributesTransformer(this.classStorage).transform(null);
  }

  public void renameMembers() {
    try {
      new NameRenamer(this.classStorage).rename();
    } catch (ObfuscationAbortException obfuscationAbortException) {
      try {
        Log.warn("Rerunning obfuscation due to un-renamed packages.");
        new NameRenamer(this.classStorage).rename();
      } catch (ObfuscationAbortException obfuscationAbortException2) {
      }
    }
  }
}
