package testing;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.GeoLocation;
import api.NodeData;
import classes.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphAlgorithmsObjTest {

    private DirectedWeightedGraphAlgorithms graphAlgo = new DirectedWeightedGraphAlgorithmsObj();
    private DirectedWeightedGraph graph;

    private NodeData n1;
    private NodeData n2;
    private NodeData n3;
    private NodeData n4;
    private NodeData n5;
    private NodeData n6;

    public void new_DWGA() {

        graph = new DirectedWeightedGraphObj();

        n1 = new NodeDataObj( 1, new GeoLocationObj(10, 12.5, 22));
        n2 = new NodeDataObj( 2, new GeoLocationObj(5, 17, 7.5));
        n3 = new NodeDataObj( 3, new GeoLocationObj(4, 32, -9));
        n4 = new NodeDataObj( 4, new GeoLocationObj(7, 8, 9));
        n5 = new NodeDataObj( 5, new GeoLocationObj(14, 11, 21));
        n6 = new NodeDataObj( 6, new GeoLocationObj(11, 16, 21));

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.graphAlgo.init(this.graph);
    }

//    public void new_DWGA1() {
//
//        graph = new DirectedWeightedGraphObj();
//
//        for (int i = 0; i < 2; i++) {
//            NodeData n = new NodeDataObj( i, new GeoLocationObj(i, i, i));
//            graph.addNode(n);
//        }
//
//       try {
//            graph.connect(0, 1, 3);
//            graph.connect(1, 2, 2);
//            graph.connect(2, 3, 1);
//            graph.connect(3, 0, 6);
//            graph.connect(0, 2, 4);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        graphAlgo.init(graph);
//    }


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
        double shortest = graphAlgo.shortestPathDist(n5.getKey(),n1.getKey());
        assertEquals(8,shortest);



    }

    @Test
    void shortestPath() {
    }

    @Test
    void center() {
    }

    @Test
    void tsp() {
    }

    @Test
    void save() {
    }

    @Test
    void load() {
    }
}