package com.allatori.eclipse;

import java.util.ArrayList;
import java.util.Arrays;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;

public class MenuRemoveBuilder extends AbstractHandler implements IHandler {

  public Object execute(ExecutionEvent executionEvent) {
    IProject project;
    if ((project = MenuAddBuilder.getIProject(executionEvent)) != null) {
      try {
        IProjectDescription description = project.getDescription();
        ArrayList values;
        (values = new ArrayList()).addAll(Arrays.asList(description.getBuildSpec()));
        ICommand[] commands;
        int value = (commands = description.getBuildSpec()).length;
        int value2;
        for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
          ICommand command = commands[value2];
          if ("com.allatori.eclipse".equals(command.getBuilderName())) {
            values.remove(command);
          }
        }
        description.setBuildSpec((ICommand[]) values.toArray(new ICommand[values.size()]));
        project.setDescription(description, null);
      } catch (CoreException coreException) {
        coreException.printStackTrace();
      }
    }
    return null;
  }
}
