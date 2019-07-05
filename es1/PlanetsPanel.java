package es1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.ExecutorService;
import javax.swing.JPanel;


public class PlanetsPanel extends JPanel {
    es1.PlanetManager planetManager;
    es1.SynchronizationManager synchronizationManager;
    ExecutorService exec;
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PlanetsPanel(es1.PlanetManager planetManager,
                        es1.SynchronizationManager synchronizationManager,
                        ExecutorService executorService) {
        this.planetManager = planetManager;
        this.synchronizationManager = synchronizationManager;
        this.exec = executorService;
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
        double minX = 0, minY = 0, maxX = 0, maxY = 0;
        boolean first = true;
        for (es1.Planet p : this.planetManager.getPlanetsList()) {
            if (first) {
                first = false;
                minX = p.getPositionX();
                maxX = minX;
                minY = p.getPositionY();
                maxY = minY;
            }
            else {
                if(p.getPositionX() > maxX) {
                    maxX = p.getPositionX();
                }else if(p.getPositionX() < minX) {
                    minX = p.getPositionX();
                }
                if(p.getPositionY() > maxY) {
                    maxY = p.getPositionY();
                }else if(p.getPositionY() < minY) {
                    minY = p.getPositionY();
                }
            }
        }
        minX -= 100;
        minY -=100;
        maxX+= 100;
        maxY +=100;
        double rescaleX = (maxX-minX) / Constants.PANEL_SIZE_X;
        double rescaleY = (maxY - minY ) / Constants.PANEL_SIZE_Y;
        for (es1.Planet p : this.planetManager.getPlanetsList()) {
            g2d.setColor(Color.RED);
            //TODO delete
            System.out.println(p.getPositionX() + " " + p.getPositionY());
            Ellipse2D.Double endCustomer = new Ellipse2D.Double((p.getPositionX() - 10D / 2 - minX)/ rescaleX,///Constants.DRAWING_PANEL_SIZE_X,
                    (p.getPositionY() - 10D / 2 - minY)/ rescaleY, // / Constants.DRAWING_PANEL_SIZE_Y,
                    10,
                    10);
            g2d.draw(endCustomer);
            g2d.fill(endCustomer);
            g2d.setColor(Color.BLACK);
        }
        this.synchronizationManager.releaseAcceleration();
    }
}
