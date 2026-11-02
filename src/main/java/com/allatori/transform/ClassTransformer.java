package com.allatori.transform;

import com.allatori.lib.bcel.generic.ClassGen;

public interface ClassTransformer {

  void transform(ClassGen classGen);

  void finish();
}
