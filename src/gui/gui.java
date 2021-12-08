package gui;

import api.*;
import classes.DirectedWeightedGraphAlgorithmsObj;
import classes.GeoLocationObj;
import classes.NodeDataObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class gui {

    private final int[] screenSize = {750, 750};

    public gui(String path) {



        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(this.screenSize[0], this.screenSize[1]);

        CustomPaintComponent CPC = new CustomPaintComponent();
        File file = new File(path);
        DirectedWeightedGraphAlgorithms DWGA = new DirectedWeightedGraphAlgorithmsObj();
        DWGA.load(file.getPath());
        CPC.SetGraph(DWGA);
        CPC.SetScreenSize(this.screenSize);
        frame.add(CPC);
        frame.setVisible(true);

        JMenuBar mb = makeMenueBar(frame, CPC);

        frame.getContentPane().add(BorderLayout.NORTH, mb);

        frame.setVisible(true);

    }
    public static JMenuBar makeMenueBar(JFrame frame, CustomPaintComponent CPC) {
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
        JMenuItem m34 = new JMenuItem("Center");
        m3.add(m31);
        m3.add(m32);
        m3.add(m33);
        m3.add(m34);

        JMenu m4 = new JMenu("Test");
        mb.add(m4);

        JMenuItem[] JMI_Arr = {m11, m12, m21, m22, m23, m24, m31, m32, m33, m34};
        MenuDemo MD = new MenuDemo(JMI_Arr, frame, CPC);

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
        public Graphics2D g2d;

        public void paint(Graphics g) {
            this.g2d = (Graphics2D) g;
            this.g2d.setStroke(new BasicStroke(2));

            if (this.DWGA == null) {
                this.DWGA = new DirectedWeightedGraphAlgorithmsObj();
            }
            updatePrivateValues(this.DWGA.getGraph());
            drawEdges();
            drawNodes();
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
        public int[] GetScreenSize() {
            return this.ScreenSize;
        }
        public void SetGraphics2D(Graphics2D g2d) {
            this.g2d = g2d;
        }
        public Graphics2D GetGraphics2D() {
            return this.g2d;
        }
        public void drawNodes() {

            Iterator<NodeData> it = this.DWGA.getGraph().nodeIter();
            while (it.hasNext()) {
                NodeData node = it.next();
                GeoLocation loc = node.getLocation();
                this.g2d.setColor(new Color(229, 14, 14));
                this.g2d.fillOval((int)algoX(loc.x()) - (this.nodeSize/2), (int)algoY(loc.y()) - (this.nodeSize/2), this.nodeSize, this.nodeSize);
                this.g2d.setColor(new Color(0, 0, 0));
                this.g2d.drawString(node.getKey() + "", (int)algoX(loc.x()) - (this.nodeSize/2) + this.indexOffset, (int)algoY(loc.y()) - (this.nodeSize/2) + this.indexOffset + 5);
            }
        }
        public void highLightNode(NodeData node) {
            if (this.DWGA.getGraph().getNode(node.getKey()) != null) {
                this.g2d.setColor(new Color(14, 255, 0));
                this.g2d.drawOval((int)algoX(node.getLocation().x()) - (this.nodeSize/2), (int)algoY(node.getLocation().y()) - (this.nodeSize/2), this.nodeSize, this.nodeSize);
            }
        }
        public void highLightPath(LinkedList<NodeData> nodes) {
            if (nodes == null) return;
            this.g2d.setColor(new Color(152, 0, 99));
            //System.out.println("highlight: " + this.g2d);
            for (int i = 0 ; i < nodes.size() - 1 ; i++) {
                EdgeData edge = this.DWGA.getGraph().getEdge(nodes.get(i).getKey(), nodes.get(i + 1).getKey());
                GeoLocation src = this.DWGA.getGraph().getNode(edge.getSrc()).getLocation();
                GeoLocation dest = this.DWGA.getGraph().getNode(edge.getDest()).getLocation();
                drawArrow((int)algoX(src.x()), (int)algoY(src.y()), (int)algoX(dest.x()), (int)algoY(dest.y()));
                //System.out.println((int)algoX(src.x()) + " " + (int)algoY(src.y()) + " " + (int)algoX(dest.x()) + " " + (int)algoY(dest.y()));
            }
        }
        public void drawEdges() {
            this.g2d.setColor(new Color(3, 60, 203));
            Iterator<EdgeData> it = this.DWGA.getGraph().edgeIter();
            //System.out.println("drawEdges: " + this.g2d);
            while (it.hasNext()) {
                EdgeData edge = it.next();
                GeoLocation src = this.DWGA.getGraph().getNode(edge.getSrc()).getLocation();
                GeoLocation dest = this.DWGA.getGraph().getNode(edge.getDest()).getLocation();
                drawArrow((int)algoX(src.x()), (int)algoY(src.y()), (int)algoX(dest.x()), (int)algoY(dest.y()));
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
        public void drawArrow(int x1, int y1, int x2, int y2) {

            this.g2d.drawLine(x1,y1,x2,y2);

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

            this.g2d.fillPolygon(new int[] {x2, xx1, xx2}, new int[] {y2, yy1, yy2}, 3 );
        }
    }
    public static class MenuDemo implements ActionListener, ItemListener {

        private JFrame frame;
        private CustomPaintComponent CPC;

        public MenuDemo(JMenuItem[] JMI_Arr, JFrame frame, CustomPaintComponent CPC) {
            for (JMenuItem JMI : JMI_Arr) {
                JMI.addActionListener(this);
            }
            this.CPC = CPC;
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DirectedWeightedGraphAlgorithms DWGA = this.CPC.GetGraph();
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
                    File file1 = new File("C:/Users/user/IdeaProjects/EX2_OOP/data");
                    fc.setCurrentDirectory(file1);
                    int returnVal = fc.showOpenDialog(fc);
                    File file = fc.getSelectedFile();

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        DirectedWeightedGraphAlgorithms _DWGA = new DirectedWeightedGraphAlgorithmsObj();
                        _DWGA.load(file.getPath());
                        DrawGraph(_DWGA);
                    }
                    else {
                        System.out.println("Load Command canceled by user");
                    }
                    break;
                case "Add Node":
                    if (this.CPC == null) {
                        JOptionPane.showMessageDialog(frame, "Unable to add a node to an empty graph");
                        break;
                    }
                    double x = -1;
                    double y = -1;
                    int ID = -1;
                    while (x == -1) {
                        try {
                            x = Double.parseDouble(JOptionPane.showInputDialog("Enter an x coordinate for the new node"));
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    while (y == -1) {
                        try {
                            y = Double.parseDouble(JOptionPane.showInputDialog("Enter a y coordinate for the new node"));
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    while (ID == -1) {
                        try {
                            ID = Integer.parseInt(JOptionPane.showInputDialog("Enter an ID for the new node"));
                            while (ID <= 0) {
                                ID = Integer.parseInt(JOptionPane.showInputDialog("Enter an ID for the new node\nThe node's ID must be greater than 0"));
                            }
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    if (x == -1 || y == -1 || ID == -1) {
                        JOptionPane.showMessageDialog(frame, "Failed to add new node");
                        break;
                    }
                    if (DWGA.getGraph().getNode(ID) != null) {
                        JOptionPane.showMessageDialog(frame, "Failed to add new node, node already exists in the graph");
                    }
                    DWGA.getGraph().addNode(new NodeDataObj(ID, new GeoLocationObj(x, y, 0)));
                    JOptionPane.showMessageDialog(frame, "Node " + ID + ": " + x + ", " + y + "\nWas added to the graph");
                    DrawGraph(DWGA);
                    break;
                case "Add Edge":
                    int ID1 = -1;
                    int ID2 = -1;
                    double weight = -1;

                    while (ID1 == -1) {
                        try {
                            ID1 = Integer.parseInt(JOptionPane.showInputDialog("Enter the source node's ID"));
                            while (DWGA.getGraph().getNode(ID1) == null) {
                                ID1 = Integer.parseInt(JOptionPane.showInputDialog("Enter the source node's ID\nThis Node does not exist in the graph"));
                            }
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    while (ID2 == -1) {
                        try {
                            ID2 = Integer.parseInt(JOptionPane.showInputDialog("Enter the destination node's ID"));
                            while (DWGA.getGraph().getNode(ID2) == null) {
                                ID2 = Integer.parseInt(JOptionPane.showInputDialog("Enter the destination node's ID\nThis Node does not exist in the graph"));
                            }
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    while (weight == -1) {
                        try {
                            weight = Double.parseDouble(JOptionPane.showInputDialog("Enter the weight of the edge"));
                            while (weight < 0) {
                                weight = Double.parseDouble(JOptionPane.showInputDialog("Enter the weight of the edge\nWeight must be greater than 0"));
                            }
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    try {
                        DWGA.getGraph().connect(ID1, ID2, weight);
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(frame, "Failed to add new edge");
                        break;
                    }
                    DrawGraph(DWGA);
                    JOptionPane.showMessageDialog(frame, "The edge from " + ID1 + " to " + ID2 + ", with weight: " + weight + "\nWas added to the graph");
                    break;
                case "Remove Node":
                    ID = -1;
                    while (ID == -1) {
                        try {
                            ID = Integer.parseInt(JOptionPane.showInputDialog("Enter the ID of the node you want to remove"));
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    if (DWGA.getGraph().getNode(ID) != null) {
                        DWGA.getGraph().removeNode(ID);
                        DrawGraph(DWGA);
                        JOptionPane.showMessageDialog(frame, "Node " + ID + " was successfully removed from the graph");
                    }
                    break;
                case "Remove Edge":
                    ID1 = -1;
                    ID2 = -1;
                    while (ID1 == -1) {
                        try {
                            ID1 = Integer.parseInt(JOptionPane.showInputDialog("Enter the source ID of the edge you want to remove"));
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    while (ID2 == -1) {
                        try {
                            ID2 = Integer.parseInt(JOptionPane.showInputDialog("Enter the destination ID of the edge you want to remove"));
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    if (DWGA.getGraph().getEdge(ID1, ID2) != null) {
                        DWGA.getGraph().removeEdge(ID1, ID2);
                        DrawGraph(DWGA);
                        JOptionPane.showMessageDialog(frame, "The edge from " + ID1 + " to " + ID2 + " was successfully removed from the graph");
                    }
                    break;
                case "Is Connected":
                    JOptionPane.showMessageDialog(frame,"The graph is " + ((DWGA.isConnected()) ? "" : "not ") + "connected");
                    break;
                case "Shortest Path":
                    ID1 = -1;
                    ID2 = -1;
                    while (ID1 == -1) {
                        try {
                            ID1 = Integer.parseInt(JOptionPane.showInputDialog("Enter the source Node's ID"));
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    while (ID2 == -1) {
                        try {
                            ID2 = Integer.parseInt(JOptionPane.showInputDialog("Enter the destination node's ID"));
                        } catch (NumberFormatException ex) {
                            break;
                        }
                    }
                    LinkedList<NodeData> list = (LinkedList<NodeData>) DWGA.shortestPath(ID1, ID2);
                    this.CPC.highLightPath(list);
                    break;
                case "TSP":
                    LinkedList<NodeData> tsplist = null;
                    String IDs = JOptionPane.showInputDialog("Enter a set of Node ID as seen in the following example:\n1,2,14,12,13");
                    if (IDs.isEmpty()) {
                        break;
                    }
                    String[] strIDs = IDs.split(",");
                    tsplist = new LinkedList<>();
                    for (String str : strIDs) {
                        try {
                            Integer.parseInt(str);
                        }
                        catch (NumberFormatException nfe) {

                            JOptionPane.showMessageDialog(frame,"invalid input, found: " + str);
                            break;
                        }
                        if (DWGA.getGraph().getNode(Integer.parseInt(str)) != null) {

                            tsplist.add(DWGA.getGraph().getNode(Integer.parseInt(str)));
                        }
                        else {
                            JOptionPane.showMessageDialog(frame,"the node " + str + " does no exist in the graph");
                            continue;
                        }
                    }
                    DrawGraph(DWGA);
                    this.CPC.highLightPath(tsplist);
                    break;
                case "Center":
                    NodeData node = DWGA.center();
                    this.CPC.highLightNode(node);
                    break;
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            System.out.println(e.toString());
        }
        private void DrawGraph(DirectedWeightedGraphAlgorithms DWGA) {
            int[] screenSize = null;
            Graphics2D g2d = null;
            if (this.CPC != null) {
                this.frame.remove(this.CPC);
                screenSize = this.CPC.GetScreenSize();
                g2d = this.CPC.GetGraphics2D();
                this.CPC = null;
            }
            this.CPC = new CustomPaintComponent();
            this.CPC.SetGraph(DWGA);
            this.CPC.SetScreenSize(screenSize);
            this.CPC.SetGraphics2D(g2d);
            this.frame.add(this.CPC);
            this.frame.setVisible(true);
        }
    }
}
