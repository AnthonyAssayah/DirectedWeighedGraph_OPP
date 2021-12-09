package testing;

import api.*;
import classes.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphAlgorithmsObjTest {

    private DirectedWeightedGraphAlgorithms graphAlgo = new DirectedWeightedGraphAlgorithmsObj();
    private DirectedWeightedGraph graph;
    //private HashMap<Integer, NodeData> nodes;
    private HashMap<Integer, HashMap<Integer, EdgeDataObj>> edges;

    private NodeData n1;
    private NodeData n2;
    private NodeData n3;
    private NodeData n4;
    private NodeData n5;
    private NodeData n6;
    private EdgeData e1;
    private EdgeData e2;

    public void new_DWGA() {

        graph = new DirectedWeightedGraphObj();

        n1 = new NodeDataObj( 1, new GeoLocationObj(10, 12.5, 22),10);
        n2 = new NodeDataObj( 2, new GeoLocationObj(5, 17, 7.5),15);
        n3 = new NodeDataObj( 3, new GeoLocationObj(4, 32, 6),22);
        n4 = new NodeDataObj( 4, new GeoLocationObj(7, 8, 9),30);
        n5 = new NodeDataObj( 5, new GeoLocationObj(14, 11, 21),8);
        n6 = new NodeDataObj( 6, new GeoLocationObj(11, 16, 21),5);

//        e1 = new EdgeDataObj(n1.getKey(), n2.getKey(),5);
//        e2 = new EdgeDataObj(n1.getKey(), n5.getKey(),2);
//        n1.setWeight(10);
//        n2.setWeight(15);
//        n3.setWeight(22);
//        n4.setWeight(30);
//        n5.setWeight(8);
//        n6.setWeight(5);

        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
        graph.addNode(n5);
        graph.addNode(n6);




        try {
            graph.connect(n1.getKey(), n2.getKey(), 3);
            graph.connect(n1.getKey(), n5.getKey(), 2);
            graph.connect(n2.getKey(), n4.getKey(), 5);
            graph.connect(n2.getKey(), n3.getKey(), 1);
            graph.connect(n2.getKey(), n5.getKey(), 8);
            graph.connect(n3.getKey(), n4.getKey(), 7);
            graph.connect(n4.getKey(), n6.getKey(), 6);
            graph.connect(n4.getKey(), n2.getKey(), 1);
            graph.connect(n5.getKey(), n4.getKey(), 4);
            graph.connect(n6.getKey(), n1.getKey(), 1);
            graph.connect(n2.getKey(), n1.getKey(), 3);

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.graphAlgo.init(this.graph);
    }



    @Test
    void getGraph() {
        new_DWGA();
        assertEquals(this.graphAlgo.getGraph(),this.graph);
    }

    @Test
    void copy() {
        new_DWGA();
        DirectedWeightedGraphAlgorithms new_graphAlgo = new DirectedWeightedGraphAlgorithmsObj();
        DirectedWeightedGraphObj new_graph = (DirectedWeightedGraphObj) new_graphAlgo.copy();
        new_graphAlgo.init(this.graph);
        assertEquals(new_graphAlgo.getGraph(),graphAlgo.getGraph());

    }

    @Test
    void isConnected()  {
        new_DWGA();
        assertTrue(graphAlgo.isConnected());

        graph.removeEdge(n4.getKey(),n6.getKey());
        assertFalse(graphAlgo.isConnected());

        DirectedWeightedGraph no_nodes = new DirectedWeightedGraphObj();
        DirectedWeightedGraphAlgorithms no_nodesAlgo = new DirectedWeightedGraphAlgorithmsObj();
        no_nodesAlgo.init(no_nodes);
        assertTrue(no_nodesAlgo.isConnected());

        DirectedWeightedGraph one_Node = new DirectedWeightedGraphObj();
        one_Node.addNode(new NodeDataObj(3,new GeoLocationObj(4, 5, 0)));
        DirectedWeightedGraphAlgorithms one_NodeAlgo = new DirectedWeightedGraphAlgorithmsObj();
        one_NodeAlgo.init(one_Node);
        assertTrue(one_NodeAlgo.isConnected());

    }

    @Test
    void shortestPathDist() {
        new_DWGA();
        double shortest1 = graphAlgo.shortestPathDist(n5.getKey(),n1.getKey());
        double shortest2 = graphAlgo.shortestPathDist(n1.getKey(),n4.getKey());
        double shortest3 = graphAlgo.shortestPathDist(n4.getKey(),n1.getKey());
        double shortest4 = graphAlgo.shortestPathDist(n2.getKey(),n6.getKey());

        assertEquals(8,shortest1);
        assertEquals(6,shortest2);
        assertEquals(4,shortest3);
        assertEquals(11,shortest4);

    }

    @Test
    void shortestPath() {
        new_DWGA();
        List<NodeData> myList = graphAlgo.shortestPath(6, 3);
        List<NodeData> myNewList = new LinkedList<>();
        System.out.println(myList);
        System.out.println(myNewList);
        myNewList.add(graph.getNode(6));
        myNewList.add(graph.getNode(1));
        myNewList.add(graph.getNode(2));
        myNewList.add(graph.getNode(3));
        assertEquals(myNewList, myList);

        graph.connect(6, 3, 2);
        myList = graphAlgo.shortestPath(6, 3);
        myNewList.clear();
        myNewList.add(graph.getNode(6));
        myNewList.add(graph.getNode(3));
        assertEquals(myNewList, myList);

        myList = graphAlgo.shortestPath(1, 4);
        myNewList.clear();
        myNewList.add(graph.getNode(1));
        myNewList.add(graph.getNode(5));
        myNewList.add(graph.getNode(4));
        assertEquals(myNewList, myList);

    }

    @Test
    void center() {
        DirectedWeightedGraphAlgorithmsObj ans = new DirectedWeightedGraphAlgorithmsObj();
        ans.load("C:/EX2_OOP/data/G1.json");
        assertEquals(ans.center(),ans.getGraph().getNode(8));

        DirectedWeightedGraphAlgorithmsObj ans2 = new DirectedWeightedGraphAlgorithmsObj();
        ans2.load("C:/EX2_OOP/data/G2.json");
        assertEquals(ans2.center(),ans2.getGraph().getNode(0));

        DirectedWeightedGraphAlgorithmsObj ans3 = new DirectedWeightedGraphAlgorithmsObj();
        ans3.load("C:/EX2_OOP/data/G3.json");
        assertEquals(ans3.center(),ans3.getGraph().getNode(40));

        new_DWGA();
        assertEquals(graphAlgo.center(), graphAlgo.getGraph().getNode(4));
    }

    @Test
    void tsp() {



    }

    @Test
    void save() throws IOException {

        new_DWGA();
        boolean saveGraph = graphAlgo.save("MyJsonGraph");
        boolean loadGraph = graphAlgo.load("MyJsonGraph");
        DirectedWeightedGraph new_graph = graphAlgo.getGraph();

        assertTrue(saveGraph);
        assertTrue(loadGraph);
        assertEquals(graph, new_graph);
//        new_graph.removeNode(2);
//        assertNotEquals(graph.nodeSize(), new_graph.nodeSize());

    }

    @Test
    void load() {
    }
}