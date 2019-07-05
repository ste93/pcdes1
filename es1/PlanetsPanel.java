package es1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.JPanel;


public class PlanetsPanel extends JPanel {
    es1.PlanetManager planetManager;
    es1.SynchronizationManager synchronizationManager;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PlanetsPanel(es1.PlanetManager planetManager, es1.SynchronizationManager synchronizationManager) {
        this.planetManager = planetManager;
        this.synchronizationManager = synchronizationManager;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(es1.Constants.PANEL_SIZE_X, es1.Constants.PANEL_SIZE_Y);
    }


    protected void paintComponent(Graphics g) {
        synchronizationManager.acquireGraphicLock();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        for (es1.Planet p : this.planetManager.getPlanetsList()) {
            g2d.setColor(Color.RED);
            //TODO delete
            System.out.println(p.getPositionX() + " " + p.getPositionY());
            Ellipse2D.Double endCustomer = new Ellipse2D.Double(p.getPositionX(),///Constants.DRAWING_PANEL_SIZE_X, 
                    p.getPositionY(), // / Constants.DRAWING_PANEL_SIZE_Y,
                    10,
                    10);
            g2d.draw(endCustomer);
            g2d.fill(endCustomer);
            g2d.setColor(Color.BLACK);
        }
        this.synchronizationManager.releaseAcceleration();
    }
}
