package gui;

import api.*;
import classes.DirectedWeightedGraphAlgorithmsObj;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Iterator;


public class gui {

    public static void main(String[] args){

        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        JMenuBar mb = new JMenuBar();

        JMenu m1 = new JMenu("File");
        mb.add(m1);
        JMenuItem m11 = new JMenuItem("Save");
        JMenuItem m12 = new JMenuItem("Load");
        m1.add(m11);
        m1.add(m12);

        JMenu m2 = new JMenu("Edit");
        mb.add(m2);
        JMenuItem m21 = new JMenuItem("Add Node");
        JMenuItem m22 = new JMenuItem("Add Edge");
        JMenuItem m23 = new JMenuItem("Remove Node");
        JMenuItem m24 = new JMenuItem("Remove Edge");
        m2.add(m21);
        m2.add(m22);
        m2.add(m23);
        m2.add(m24);

        JMenu m3 = new JMenu("Algorithms");
        mb.add(m3);
        JMenuItem m31 = new JMenuItem("Is Connected");
        JMenuItem m32 = new JMenuItem("Shortest Path");
        JMenuItem m33 = new JMenuItem("TSP");
        m3.add(m31);
        m3.add(m32);
        m3.add(m33);

        JMenu m4 = new JMenu("Test");
        mb.add(m4);

        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.add(new CustomPaintComponent());
        frame.setVisible(true);

    }
    static class CustomPaintComponent extends Component {

        private double min_x = Double.MAX_VALUE;
        private double min_y = Double.MAX_VALUE;
        private double max_x = 0;
        private double max_y = 0;

        public void paint(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(3, 60, 203));
            g2d.setStroke(new BasicStroke(2));

            DirectedWeightedGraphAlgorithms DWG = new DirectedWeightedGraphAlgorithmsObj();
            DWG.load("data/G1.json");

            updatePrivateValues(DWG.getGraph());
            drawNodes(g2d, DWG.getGraph());
            drawCities(g2d, DWG.getGraph());
            g2d.drawLine(0,487, 500, 487);
            g2d.drawLine(487,0, 487, 440);
        }
        public void drawNodes(Graphics2D g2d, DirectedWeightedGraph DWG) {

            Iterator<NodeData> it = DWG.nodeIter();
            while (it.hasNext()) {
                NodeData node = it.next();
                GeoLocation loc = node.getLocation();
                g2d.drawOval((int)algoX(loc.x()), (int)algoY(loc.y()), 5, 5);
            }
        }
        public void drawCities(Graphics2D g2d, DirectedWeightedGraph DWG) {

            Iterator<EdgeData> it = DWG.edgeIter();
            while (it.hasNext()) {
                EdgeData edge = it.next();
                GeoLocation src = DWG.getNode(edge.getSrc()).getLocation();
                GeoLocation dest = DWG.getNode(edge.getDest()).getLocation();
                g2d.drawLine((int)algoX(src.x()), (int)algoY(src.y()), (int)algoX(dest.x()), (int)algoY(dest.y()));
            }
        }
        public double algoX(double x) {
            x = x - this.min_x;
            x = (500 * 0.934) * (x / (this.max_x - this.min_x));
            return x;
        }
        public double algoY(double y) {
            y = y - this.min_y;
            y = (500 * 0.934) * (y / (this.max_y - this.min_y));
            return y;
        }
        public void updatePrivateValues(DirectedWeightedGraph DWG) {
            Iterator<NodeData> it = DWG.nodeIter();
            while (it.hasNext()) {
                NodeData node = it.next();
                GeoLocation loc = node.getLocation();
                if (this.min_x > loc.x())
                    this.min_x = loc.x();
                if (this.min_y > loc.y())
                    this.min_y = loc.y();
                if (this.max_x < loc.x())
                    this.max_x = loc.x();
                if (this.max_y < loc.y())
                    this.max_y = loc.y();
            }
        }
    }
}
