Exemple 6.7 : InverseEditorPanel.java

package com.horstmann.corejava;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.lang.reflect.*;
import java.beans.*;
import javax.swing.*;

/**
   Le panneau permettant de définir la propriété inverse. Il contient
   des boutons permettant de basculer entre des couleurs normales et
   inversées.
*/
public class InverseEditorPanel extends JPanel
{
   public InverseEditorPanel(PropertyEditorSupport ed)
   {
      editor = ed;
      ButtonGroup g = new ButtonGroup();
      boolean isInverse = (Boolean) editor.getValue();
      normal = new JRadioButton("Normal", !isInverse);
      inverse = new JRadioButton("Inverse", isInverse);

      g.add(normal);
      g.add(inverse);
      add(normal);
      add(inverse);

      ActionListener buttonListener =
         new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               editor.setValue(
                 new Boolean(inverse.isSelected()));
               editor.firePropertyChange();
            }
         };
 
      normal.addActionListener(buttonListener);
      inverse.addActionListener(buttonListener);
   }

   private JRadioButton normal;
   private JRadioButton inverse;
   private PropertyEditorSupport editor;
}
