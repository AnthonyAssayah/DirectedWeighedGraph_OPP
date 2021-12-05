package testing;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.GeoLocation;
import api.NodeData;
import classes.DirectedWeightedGraphObj;
import classes.EdgeDataObj;
import classes.GeoLocationObj;
import classes.NodeDataObj;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphObjTest {

    private  DirectedWeightedGraph graph1 = new DirectedWeightedGraphObj();
    private  DirectedWeightedGraph graph2 = new DirectedWeightedGraphObj();
    private HashMap<Integer, NodeData> nodes;
    private HashMap<Integer, HashMap<Integer, EdgeDataObj>> edges;
    private  int numOfNodes_1;
    private  int numOfNodes_2;
    private  int numOfEdges_1;
    private  int numOfEdges_2;
    private  NodeData n0;
    private  NodeData n1;
    private  NodeData n2;
    private  NodeData n3;
    private  NodeData n4;
    private  EdgeData e0;
    private  EdgeData e1;
    private  EdgeData e2;
    private  EdgeData e3;
    private  EdgeData e4;
    private  EdgeData e5;

    public void new_DWG1() {
        graph1 = new DirectedWeightedGraphObj();
        GeoLocation g0 = new GeoLocationObj(3, 10, 3);
        GeoLocation g1 = new GeoLocationObj(5, 20.5, 9);
        GeoLocation g2 = new GeoLocationObj(-12, 25, 6);
        GeoLocation g3 = new GeoLocationObj(5, -1, 1);
        GeoLocation g4 = new GeoLocationObj(0, 0, 0);

        n0 = new NodeDataObj(4,g0,5,"White", -1 );
        n1 = new NodeDataObj(5,g1,10,"Black", 2 );
        n2 = new NodeDataObj(3,g2,2,"White", 6 );
        n3 = new NodeDataObj(7,g3,1,"Black", 1 );
        n4 = new NodeDataObj(9,g4,8,"White", 1 );

        e0 = new EdgeDataObj(n0.getKey(), n1.getKey(),3);
        e1 = new EdgeDataObj(n1.getKey(), n2.getKey(),5);
        e2 = new EdgeDataObj(n1.getKey(), n3.getKey(),2);
        e3 = new EdgeDataObj(n2.getKey(), n3.getKey(),7);
        e4 = new EdgeDataObj(n3.getKey(), n4.getKey(),1);
        e5 = new EdgeDataObj(n0.getKey(), n4.getKey(),2);

        nodes = new HashMap<Integer, NodeData>();
        edges = new HashMap<Integer, HashMap<Integer, EdgeDataObj>>();

        graph1.addNode(n0);
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addNode(n4);


        try {
            graph1.connect(n0.getKey(),n1.getKey(),e0.getWeight());
            graph1.connect(n1.getKey(),n2.getKey(),e1.getWeight());
            graph1.connect(n1.getKey(),n3.getKey(),e2.getWeight());
            graph1.connect(n2.getKey(),n3.getKey(),e3.getWeight());
            graph1.connect(n3.getKey(),n4.getKey(),e4.getWeight());
            graph1.connect(n0.getKey(),n4.getKey(),e5.getWeight());
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Test
    void getNode() {
        new_DWG1();
        NodeData key_1 = graph1.getNode(4);
        assertEquals(key_1,n0);
        NodeData key_2 = graph1.getNode(7);
        assertEquals(key_2,n3);
        NodeData key_3 = graph1.getNode(1);
        assertNotEquals(key_3,n4);
    }

    @Test
    void getEdge() throws Exception {
        new_DWG1();
        graph1.connect(n0.getKey(), n1.getKey(), e0.getWeight());
        EdgeData edge = new EdgeDataObj((EdgeDataObj) graph1.getEdge(n0.getKey(), n1.getKey()));
        assertEquals(edge.getSrc(), e0.getSrc());
        assertEquals(edge.getDest(), e0.getDest());
        assertEquals(edge.getWeight(), e0.getWeight());

        graph1.connect(n1.getKey(), n2.getKey(), e1.getWeight());
        EdgeData edge2 = new EdgeDataObj((EdgeDataObj) graph1.getEdge(n1.getKey(), n2.getKey()));
        assertNotEquals(edge2.getWeight(), e1.getWeight());

    }

    @Test
    void addNode() {
        new_DWG1();
        GeoLocation g = new GeoLocationObj(11, 22, 6);
        NodeData new_node = new NodeDataObj(2,g,1,"Black", 1);
        int s = graph1.nodeSize();
        this.graph1.addNode(new_node);
        int t = graph1.nodeSize();
        assertEquals(s+1,t);
        new_DWG1();
        NodeData new_node2 = new NodeDataObj((NodeDataObj) n0);
        this.graph1.addNode(new_node2);
        int w = graph1.nodeSize();
        assertEquals(s,w);
    }

    @Test
    void connect() throws Exception {
        new_DWG1();
        graph1.connect(n0.getKey(), n1.getKey(), e0.getWeight());
        graph1.connect(n1.getKey(), n2.getKey(), e1.getWeight());
        int s = graph1.edgeSize();
        graph1.connect(n1.getKey(), n3.getKey(), e2.getWeight());
        int t = graph1.edgeSize();
        assertEquals(s+1,t);

        new_DWG1();
        graph1.connect(n0.getKey(), n1.getKey(), e0.getWeight());
        graph1.connect(n1.getKey(), n2.getKey(), e1.getWeight());
        graph1.connect(n1.getKey(), n2.getKey(), e1.getWeight());
        int w = graph1.edgeSize();
        assertEquals(w,2);


    }

    @Test
    void nodeIter() {
        new_DWG1();
        LinkedList<Integer> list = new LinkedList<>();
        Iterator<NodeData> iterNodes = this.graph1.nodeIter();
        list.add(n0.getKey());
        list.add(n1.getKey());
        list.add(n2.getKey());
        list.add(n3.getKey());
        list.add(n4.getKey());
        int count = 0;
        while (iterNodes.hasNext()) {
            NodeData v = iterNodes.next();
            assertEquals(true, list.contains(v.getKey()));
            count++;
        }
        assertEquals(count, list.size());
    }

    @Test
    void edgeIter() {
    }

    @Test
    void testEdgeIter() {
    }

    @Test
    void removeNode() {
    }

    @Test
    void removeEdge() {
    }

    @Test
    void nodeSize() {
        new_DWG1();
        assertEquals(5,graph1.nodeSize());

        GeoLocation g = new GeoLocationObj(11, 22, 6);
        NodeData new_node = new NodeDataObj(2,g,1,"Black", 1);
        this.graph1.addNode(new_node);
        assertNotEquals(5,graph1.nodeSize());

    }

    @Test
    void edgeSize() throws Exception {
        new_DWG1();
        assertEquals(6,graph1.edgeSize());

        System.out.println(graph1.edgeSize());
        graph1.connect(n4.getKey(), n0.getKey(), 6);
        System.out.println(graph1.edgeSize());
        assertNotEquals(5,graph1.edgeSize());

        graph1.connect(n2.getKey(), n4.getKey(), 4);
        System.out.println(graph1.edgeSize());
        assertEquals(8,graph1.edgeSize());

    }

    @Test
    void getMC() {
    }
}