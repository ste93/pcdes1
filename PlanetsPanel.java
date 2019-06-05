package es1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


public class PlanetsPanel extends JPanel {
	PlanetManager planetManager;
	SynchronizationManager synchronizationManager;
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PlanetsPanel (PlanetManager planetManager, SynchronizationManager synchronizationManager) {
		this.planetManager = planetManager;
		this.synchronizationManager = synchronizationManager;
	}
	
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constants.PANEL_SIZE_X, Constants.PANEL_SIZE_Y);
    }

	
    //this need to be asynchronous
	protected void paintComponent(Graphics g) {
		//TODO
		System.out.println("paintComponent");
		synchronizationManager.acquireGraphicLock();
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        for(Planet p : this.planetManager.getPlanetsList()) {
            g2d.setColor(Color.RED);
            //TODO
            System.out.println(p.getPositionX() + " " + p.getPositionY());
            Ellipse2D.Double endCustomer = new Ellipse2D.Double(p.getPositionX(), p.getPositionY(), 10, 10);
            g2d.draw(endCustomer);
            g2d.fill(endCustomer);
            g2d.setColor(Color.BLACK);
        }
        this.synchronizationManager.releaseAcceleration();
    }
}
