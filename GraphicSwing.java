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

	private static class ClickListener implements ActionListener {
		private SynchronizationManager synchronizationManager;
		
		public ClickListener(SynchronizationManager synchronizationManager) {
			this.synchronizationManager = synchronizationManager;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
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
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}


