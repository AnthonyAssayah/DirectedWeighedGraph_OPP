package gui;

import api.*;
import classes.DirectedWeightedGraphAlgorithmsObj;

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class gui {

    public static void main(String[] args) {

        final int[] screenSize = {750, 750};

        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenSize[0], screenSize[1]);

        JMenuBar mb =makeMenueBar(frame);

        frame.getContentPane().add(BorderLayout.NORTH, mb);

        /*CustomPaintComponent CPC = new CustomPaintComponent();
        DirectedWeightedGraphAlgorithms DWGA = new DirectedWeightedGraphAlgorithmsObj();
        DWGA.load("data/G1.json");
        CPC.SetScreenSize(screenSize);
        CPC.SetGraph(DWGA);*/

        //frame.add(CPC);

        frame.setVisible(true);

    }
    public static JMenuBar makeMenueBar(JFrame frame) {
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

        JMenuItem[] JMI_Arr = {m11, m12, m21, m22, m23, m24, m31, m32, m33};
        MenuDemo MD = new MenuDemo(JMI_Arr, frame);

        return mb;
    }
    static class CustomPaintComponent extends Component {

        private DirectedWeightedGraphAlgorithms DWGA;
        private int[] ScreenSize;
        private double min_x = Double.MAX_VALUE;
        private double min_y = Double.MAX_VALUE;
        private double max_x = 0;
        private double max_y = 0;
        private final int nodeSize = 7;
        private final int margin = 50;
        private final int indexOffset = 8;
        private final int arrowTipSize = 12;

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(2));

            if (this.DWGA == null) {
                DirectedWeightedGraphAlgorithms DWGA = new DirectedWeightedGraphAlgorithmsObj();
                DWGA.load("data/G3.json");
                SetGraph(DWGA);
            }

            updatePrivateValues(DWGA.getGraph());
            g2d.setColor(new Color(3, 60, 203));
            drawEdges(g2d);
            drawNodes(g2d);
        }
        public void SetGraph(DirectedWeightedGraphAlgorithms DWG) {

            this.DWGA = DWG;
        }
        public DirectedWeightedGraphAlgorithms GetGraph() {
            return this.DWGA;
        }
        public void SetScreenSize(int[] ScreenSize) {
            this.ScreenSize = ScreenSize;
        }
        public void drawNodes(Graphics2D g2d) {

            Iterator<NodeData> it = this.DWGA.getGraph().nodeIter();
            while (it.hasNext()) {
                NodeData node = it.next();
                GeoLocation loc = node.getLocation();
                g2d.setColor(new Color(229, 14, 14));
                g2d.fillOval((int)algoX(loc.x()) - (this.nodeSize/2), (int)algoY(loc.y()) - (this.nodeSize/2), this.nodeSize, this.nodeSize);
                g2d.setColor(new Color(0, 0, 0));
                g2d.drawString(node.getKey() + "", (int)algoX(loc.x()) - (this.nodeSize/2) + this.indexOffset, (int)algoY(loc.y()) - (this.nodeSize/2) + this.indexOffset + 5);
            }
        }
        public void drawEdges(Graphics2D g2d) {

            Iterator<EdgeData> it = this.DWGA.getGraph().edgeIter();
            while (it.hasNext()) {
                EdgeData edge = it.next();
                GeoLocation src = this.DWGA.getGraph().getNode(edge.getSrc()).getLocation();
                GeoLocation dest = this.DWGA.getGraph().getNode(edge.getDest()).getLocation();
                drawArrow(g2d, (int)algoX(src.x()), (int)algoY(src.y()), (int)algoX(dest.x()), (int)algoY(dest.y()));
            }
        }
        public double algoX(double x) {
            x = x - this.min_x;
            x = ((this.ScreenSize[0] - (2 * this.margin)) * 0.968) * (x / (this.max_x - this.min_x));
            return x + this.margin;
        }
        public double algoY(double y) {
            y = y - this.min_y;
            y = ((this.ScreenSize[1] - (2 * this.margin)) * 0.88) * (y / (this.max_y - this.min_y));
            return y + this.margin;
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
        public void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) {

            g2d.drawLine(x1,y1,x2,y2);

            double angel1 = Math.atan((double) (y1 - y2) / (x1 - x2)) * (180 / Math.PI) + 180 + 20;
            if (x2 < x1) {
                angel1 += 180 % 360;
            }
            int xx1 = x2 + (int) (this.arrowTipSize * Math.cos(angel1 * (Math.PI / 180)));
            int yy1 = y2 + (int) (this.arrowTipSize * Math.sin(angel1 * (Math.PI / 180)));

            double angel2 = Math.atan((double) (y1 - y2) / (x1 - x2)) * (180 / Math.PI) + 180 - 20;
            if (x2 < x1) {
                angel2 += 180 % 360;
            }

            int xx2 = x2 + (int) (this.arrowTipSize * Math.cos(angel2 * (Math.PI / 180)));
            int yy2 = y2 + (int) (this.arrowTipSize * Math.sin(angel2 * (Math.PI / 180)));

            g2d.fillPolygon(new int[] {x2, xx1, xx2}, new int[] {y2, yy1, yy2}, 3 );
        }
    }
    public static class MenuDemo implements ActionListener, ItemListener {

        private JFrame frame;
        private CustomPaintComponent CPC;

        public MenuDemo(JMenuItem[] JMI_Arr, JFrame frame) {
            for (JMenuItem JMI : JMI_Arr) {
                JMI.addActionListener(this);
            }
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(e.getActionCommand());
            switch (e.getActionCommand()) {
                case "Save":
                    if (this.CPC.GetGraph() == null) {
                        System.out.println("no graph to be saved");
                    }
                    else {
                        final JFileChooser fc = new JFileChooser();
                        File file1 = new File("C:/Users/edanp/IdeaProjects/EX2_OOP/data");
                        fc.setCurrentDirectory(file1);
                        int returnVal = fc.showOpenDialog(fc);
                        File file = fc.getSelectedFile();

                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            try {
                                this.CPC.GetGraph().save(file.getPath().toString());
                            } catch (IOException exception) {

                                exception.printStackTrace();
                            }
                        }
                    }
                    break;
                case "Load":
                    final JFileChooser fc = new JFileChooser();
                    File file1 = new File("C:/Users/edanp/IdeaProjects/EX2_OOP/data");
                    fc.setCurrentDirectory(file1);
                    int returnVal = fc.showOpenDialog(fc);
                    File file = fc.getSelectedFile();

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        if (this.CPC != null) {
                            this.frame.remove(this.CPC);
                        }
                        this.CPC = new CustomPaintComponent();
                        DirectedWeightedGraphAlgorithms DWGA = new DirectedWeightedGraphAlgorithmsObj();
                        DWGA.load(file.getPath());
                        this.CPC.SetGraph(DWGA);
                        int[] a = {750, 750};
                        this.CPC.SetScreenSize(a);
                        this.frame.add(this.CPC);
                        this.frame.setVisible(true);
                    }
                    else {
                        System.out.println("Load Command canceled by user");
                    }
                    break;
                case "Add Node":

                    break;
                case "Add Edge":

                    break;
                case "Remove Node":

                    break;
                case "Remove Edge":

                    break;
                case "Is Connected":

                    break;
                case "Shortest Path":

                    break;
                case "TSP":

                    break;
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            System.out.println(e.toString());
        }
    }
}
