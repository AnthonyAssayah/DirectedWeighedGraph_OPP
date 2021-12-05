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


        this.nodes.put(4,n0);
        this.nodes.put(5,n1);

        //edges.put(e0.getSrc(), e0.getDest(),);

        graph1.addNode(n0);
        graph1.addNode(n1);
        graph1.addNode(n2);
        graph1.addNode(n3);
        graph1.addNode(n4);

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

       // EdgeData edge_1 = graph1.getEdge(n0.getKey(),n1.getKey());
        graph1.connect(0,1,3);
        EdgeData edge1 = (EdgeData) graph1.getEdge(0,1);
        EdgeData edge2 = (EdgeData) graph1.getEdge(1,0);
        assertNotEquals(edge1,edge2);
//        graph1.connect(0,1,3);
//        assertEquals(graph1.getEdge(25,26),e0);

    }

    @Test
    void addNode() {
        new_DWG1();
        //nodes.put(1,n0);
        int s = graph1.nodeSize();
        System.out.println(s);

    }

    @Test
    void connect() {
    }

    @Test
    void nodeIter() {
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
    }

    @Test
    void edgeSize() {
    }

    @Test
    void getMC() {
    }
}