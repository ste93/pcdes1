package es1;


import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GraphicSwing {
    /**
     *
     */
    JFrame frame;
    es1.SynchronizationManager synchronizationManager;
    es1.PlanetsPanel panel;

    public GraphicSwing(es1.PlanetsPanel panel, es1.SynchronizationManager synchronizationManager) {
        this.panel = panel;
        this.synchronizationManager = synchronizationManager;
        panel.setLayout(null);
        panel.setSize(es1.Constants.PANEL_SIZE_X + 20, es1.Constants.PANEL_SIZE_Y + 20);
        JButton b1 = new JButton("start/stop");
        panel.add(b1);
        Insets insets = panel.getInsets();
        b1.setBounds(es1.Constants.DRAWING_PANEL_SIZE_X + es1.Constants.BUTTON_SPACING + insets.left, es1.Constants.BUTTON_SPACING + insets.top,
                es1.Constants.BUTTON_SIZE_X, es1.Constants.BUTTON_SIZE_Y);
        b1.addActionListener(new ClickListener(synchronizationManager));
    }

    private static class ClickListener implements ActionListener {
        private es1.SynchronizationManager synchronizationManager;

        public ClickListener(es1.SynchronizationManager synchronizationManager) {
            this.synchronizationManager = synchronizationManager;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO delete
            System.out.println("acquired button lock before  click");

            this.synchronizationManager.buttonClick();
            //TODO delete
            System.out.println("acquired button lock after click");

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


