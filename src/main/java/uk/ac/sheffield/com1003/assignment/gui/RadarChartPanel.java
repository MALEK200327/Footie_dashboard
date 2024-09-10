package uk.ac.sheffield.com1003.assignment.gui;

import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractPlayerDashboardPanel;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChart;
import uk.ac.sheffield.com1003.assignment.codeprovided.gui.AbstractRadarChartPanel;

/**
 * SKELETON IMPLEMENTATION
 */

public class RadarChartPanel extends AbstractRadarChartPanel
{
    public RadarChartPanel(AbstractPlayerDashboardPanel parentPanel, AbstractRadarChart radarPlot) {
        super(parentPanel, radarPlot);
    }

    /* NOTE: your RadarChartPanel must override JPanel's `protected void paintComponent(Graphics g)`,
    in order to redraw your Radar Chart whenever it is updated.*/
    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        AbstractRadarChart radarChart = getRadarChart();
        RadarAxisValues[] axes = radarChart.
        Dimension size = getSize();
        int centerX = size.width / 2;
        int centerY = size.height / 2;
        int radius = Math.min(centerX, centerY) - 10;

        Graphics2D g2d = (Graphics2D) g;

        // Draw the axes
        for (int i = 0; i < axes.length; i++) {
            double angle = i * 2 * Math.PI / axes.length;
            double x = centerX + radius * Math.sin(angle);
            double y = centerY - radius * Math.cos(angle);
            g2d.draw(new Line2D.Double(centerX, centerY, x, y));
        }

        // Draw the data points for each axis
        for (int i = 0; i < axes.length; i++) {
            double value = radarChart.getAxisValue(i);
            double angle = i * 2 * Math.PI / axes.length;
            double x = centerX + radius * Math.sin(angle) * value / axes[i].getMax();
            double y = centerY - radius * Math.cos(angle) * value / axes[i].getMax();
            g2d.fill(new Ellipse2D.Double(x - 3, y - 3, 6, 6));
        }
    }*/

}


