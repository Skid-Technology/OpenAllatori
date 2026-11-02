package com.allatori.transform;

import com.allatori.lib.bcel.generic.ClassGen;
import com.allatori.model.ClassStorage;
import com.allatori.optimizer.MethodOptimizer;

public class OptimizerTransformer implements ClassTransformer {

  public ClassStorage classStorage;

  @Override
  public void finish() {
    this.finish2();
  }

  public void finish2() {}

  public OptimizerTransformer(ClassStorage classStorage2) {
    super();
    this.classStorage = classStorage2;
  }

  @Override
  public void transform(ClassGen classGen) {
    new MethodOptimizer(this.classStorage).notify(classGen);
  }
}
