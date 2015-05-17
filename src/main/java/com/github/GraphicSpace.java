package com.github;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>
 * </p>
 * @author kisel.nikolay@gmail.com
 * @since May 16, 2015.
 */
public class GraphicSpace
{

  /**
   * Create the GUI and show it. For thread safety, this method should be
   * invoked from the event-dispatching thread.
   */
  private static void createAndShowGUI()
  {
    // Create and set up the window.
    JFrame frame = new JFrame( "Space" );
    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    final ImagePanel imagePanel = new ImagePanel();
    frame.getContentPane().add( imagePanel );
    frame.setPreferredSize( new Dimension( 1024, 768 ) );
    frame.setResizable( false );

    // Display the window.
    frame.pack();
    frame.setVisible( true );

    int delay = 1000; // milliseconds
    ActionListener taskPerformer = new ActionListener() {
      public void actionPerformed( ActionEvent evt )
      {
        imagePanel.repaint();
      }
    };
    new Timer( delay, taskPerformer ).start();
  }

  public static void main( String[] args )
  {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater( new Runnable() {
      public void run()
      {
        createAndShowGUI();
      }
    } );
  }
}
