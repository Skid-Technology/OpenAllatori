package com.allatori.eclipse;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Platform;

public class AllatoriPropertyTester extends PropertyTester {

  public static final String TEXT = "isEnabled";

  public boolean test(Object result, String text, Object[] results, Object result2) {
    IProject project;
    return "isEnabled".equals(text)
            && (project =
                    (IProject) Platform.getAdapterManager().getAdapter(result, IProject.class))
                != null
        ? MenuAddBuilder.matchesIProject(project)
        : false;
  }
}
