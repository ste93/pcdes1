package es1;


import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;  

public class GraphicSwing {
	/**
	 * 
	 */
	JFrame frame;
	SynchronizationManager synchronizationManager;	
	PlanetsPanel panel;

	public GraphicSwing(PlanetsPanel panel, SynchronizationManager synchronizationManager) {  
		this.panel = panel;
		this.synchronizationManager = synchronizationManager;
        panel.setLayout(null);
        panel.setSize(Constants.PANEL_SIZE_X + 20, Constants.PANEL_SIZE_Y + 20);
        JButton b1 = new JButton("start/stop"); 
        panel.add(b1);
        Insets insets = panel.getInsets();
        b1.setBounds(Constants.DRAWING_PANEL_SIZE_X + Constants.BUTTON_SPACING + insets.left, Constants.BUTTON_SPACING + insets.top,
                     Constants.BUTTON_SIZE_X, Constants.BUTTON_SIZE_Y);
        b1.addActionListener(new ClickListener(synchronizationManager));
    }
/*        //Create and set up the window.
        frame = new JFrame("Simulazione degli N-corpi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        //Size and display the window.
        //Insets insets = frame.getInsets();
        frame.setSize(Constants.PANEL_SIZE_X,
                      Constants.PANEL_SIZE_Y);
        frame.setLayout(null);//using no layout managers  
        frame.setVisible(true);//making the frame visible  
        frame.setVisible(true);
	}  
	
	
    public static void addComponentsToPane(Container pane) {
        pane.setLayout(null);
        pane.setSize(Constants.PANEL_SIZE_X + 20, Constants.PANEL_SIZE_Y + 20);
        JButton b1 = new JButton("start/stop"); 
        pane.add(b1);
        Insets insets = pane.getInsets();
        b1.setBounds(Constants.DRAWING_PANEL_SIZE_X + Constants.BUTTON_SPACING + insets.left, Constants.BUTTON_SPACING + insets.top,
                     Constants.BUTTON_SIZE_X, Constants.BUTTON_SIZE_Y);
    }
 */
	private static class ClickListener implements ActionListener {
		private SynchronizationManager synchronizationManager;
		
		public ClickListener(SynchronizationManager synchronizationManager) {
			this.synchronizationManager = synchronizationManager;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			this.synchronizationManager.buttonClick();
		}
		
	}

	public void updatePanel() {
        panel.repaint();
	}
	
    public void startGUI() {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
    	

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Simulazione degli N-corpi");
                //frame.setSize(Constants.PANEL_SIZE_X,
                //        Constants.PANEL_SIZE_Y);
                //frame.setLayout(null);//using no layout managers  
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}


