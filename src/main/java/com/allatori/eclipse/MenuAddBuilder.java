package com.allatori.eclipse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class MenuAddBuilder extends AbstractHandler implements IHandler {

  public static final boolean matchesIProject(IProject project) {
    try {
      ICommand[] commands;
      int value = (commands = project.getDescription().getBuildSpec()).length;
      int value2;
      for (int value3 = value2 = 0; value3 < value; value3 = ++value2) {
        ICommand command = commands[value2];
        if ("com.allatori.eclipse".equals(command.getBuilderName())) {
          return true;
        }
      }
    } catch (CoreException coreException) {
    }
    return false;
  }

  public static IProject getIProject(ExecutionEvent executionEvent) {
    ISelection selection;
    if ((selection = HandlerUtil.getCurrentSelection(executionEvent))
        instanceof IStructuredSelection) {
      Object firstElement = ((IStructuredSelection) selection).getFirstElement();
      return (IProject) Platform.getAdapterManager().getAdapter(firstElement, IProject.class);
    } else {
      return null;
    }
  }

  public Object execute(ExecutionEvent executionEvent) {
    IProject project;
    if ((project = getIProject(executionEvent)) != null) {
      try {
        if (matchesIProject(project)) {
          return null;
        }
        ICommand command;
        IProjectDescription projectDescription;
        (command = (projectDescription = project.getDescription()).newCommand())
            .setBuilderName("com.allatori.eclipse");
        ArrayList values;
        (values = new ArrayList()).addAll(Arrays.asList(projectDescription.getBuildSpec()));
        int value = 0;
        Iterator iterator2 = values.iterator();
        ArrayList values2;
        label29:
        {
          for (Iterator iterator3 = iterator2; iterator3.hasNext(); iterator3 = iterator2) {
            value++;
            ICommand command2 = (ICommand) iterator2.next();
            if ("org.eclipse.jdt.core.javabuilder".equals(command2.getBuilderName())) {
              values2 = values;
              break label29;
            }
          }
          values2 = values;
        }
        values2.add(value, command);
        projectDescription.setBuildSpec((ICommand[]) values.toArray(new ICommand[values.size()]));
        project.setDescription(projectDescription, null);
      } catch (CoreException coreException) {
        coreException.printStackTrace();
      }
    }
    return null;
  }
}
