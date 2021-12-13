package testing;

import api.*;
import classes.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

        n1 = new NodeDataObj( 0, new GeoLocationObj(10, 12.5, 22),10);
        n2 = new NodeDataObj( 2, new GeoLocationObj(5, 17, 7.5),15);
        n3 = new NodeDataObj( 3, new GeoLocationObj(4, 32, 6),22);
        n4 = new NodeDataObj( 4, new GeoLocationObj(7, 8, 9),30);
        n5 = new NodeDataObj( 5, new GeoLocationObj(14, 11, 21),8);
        n6 = new NodeDataObj( 6, new GeoLocationObj(11, 16, 21),5);



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


        //////////////////G3NODES ///////////////////////////
        //106 ms
        DirectedWeightedGraphAlgorithmsObj ans3 = new DirectedWeightedGraphAlgorithmsObj();
        ans3.load("data/G3.json");
        ans3.init(ans3.getGraph());

        //////////////////1000 NODES ///////////////////////////
        // 325ms
        DirectedWeightedGraphAlgorithmsObj ans4 = new DirectedWeightedGraphAlgorithmsObj();
        ans4.load("output/1000NODES.json");
        assertTrue(ans4.isConnected());

        //////////////////10.000 NODES ///////////////////////////
        // 997ms
        DirectedWeightedGraphAlgorithmsObj ans5 = new DirectedWeightedGraphAlgorithmsObj();
        ans5.load("output/10000NODES.json");
        assertTrue(ans5.isConnected());

        //////////////////100.000 NODES ///////////////////////////
//        // 4sec 765ms
//        DirectedWeightedGraphAlgorithmsObj ans6 = new DirectedWeightedGraphAlgorithmsObj();
//        ans6.load("output/100000NODES.json");
//        ans6.init(ans6.getGraph());
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

        myNewList.add(graph.getNode(6));
        myNewList.add(graph.getNode(0));
        myNewList.add(graph.getNode(2));
        myNewList.add(graph.getNode(3));
        assertEquals(myNewList, myList);

        graph.connect(6, 3, 2);
        myList = graphAlgo.shortestPath(6, 3);
        myNewList.clear();
        myNewList.add(graph.getNode(6));
        myNewList.add(graph.getNode(3));
        assertEquals(myNewList, myList);

        myList = graphAlgo.shortestPath(0, 4);
        myNewList.clear();
        myNewList.add(graph.getNode(0));
        myNewList.add(graph.getNode(5));
        myNewList.add(graph.getNode(4));
        assertEquals(myNewList, myList);

        /////////////////////////G1//////////////////////////

        DirectedWeightedGraphAlgorithmsObj ans = new DirectedWeightedGraphAlgorithmsObj();
        ans.load("data/G1.json");
        ans.init(ans.getGraph());

        List<NodeData> myListJson = ans.shortestPath(15, 5);
        List<NodeData> myNewListJson = new LinkedList<>();

        myNewListJson.add(ans.getGraph().getNode(15));
        myNewListJson.add(ans.getGraph().getNode(16));
        myNewListJson.add(ans.getGraph().getNode(0));
        myNewListJson.add(ans.getGraph().getNode(1));
        myNewListJson.add(ans.getGraph().getNode(2));
        myNewListJson.add(ans.getGraph().getNode(6));
        myNewListJson.add(ans.getGraph().getNode(5));


        assertEquals(myNewListJson, myListJson);

        /////////////////////////G2//////////////////////////

        DirectedWeightedGraphAlgorithmsObj res = new DirectedWeightedGraphAlgorithmsObj();
        res.load("data/G2.json");
        res.init(res.getGraph());

        List<NodeData> myListJsonG2 = res.shortestPath(15, 8);
        List<NodeData> myNewListJsonG2 = new LinkedList<>();

        myNewListJsonG2.add(res.getGraph().getNode(15));
        myNewListJsonG2.add(res.getGraph().getNode(16));
        myNewListJsonG2.add(res.getGraph().getNode(0));
        myNewListJsonG2.add(res.getGraph().getNode(1));
        myNewListJsonG2.add(res.getGraph().getNode(26));
        myNewListJsonG2.add(res.getGraph().getNode(8));

        assertEquals(myNewListJsonG2, myListJsonG2);

        /////////////////////////G3//////////////////////////

        DirectedWeightedGraphAlgorithmsObj grap = new DirectedWeightedGraphAlgorithmsObj();
        grap.load("data/G3.json");
        grap.init(grap.getGraph());

        List<NodeData> myListJsonG3 = grap.shortestPath(4, 47);
        List<NodeData> myNewListJsonG3 = new LinkedList<>();

        myNewListJsonG3.add(grap.getGraph().getNode(4));
        myNewListJsonG3.add(grap.getGraph().getNode(13));
        myNewListJsonG3.add(grap.getGraph().getNode(14));
        myNewListJsonG3.add(grap.getGraph().getNode(15));
        myNewListJsonG3.add(grap.getGraph().getNode(39));
        myNewListJsonG3.add(grap.getGraph().getNode(40));
        myNewListJsonG3.add(grap.getGraph().getNode(41));
        myNewListJsonG3.add(grap.getGraph().getNode(42));
        myNewListJsonG3.add(grap.getGraph().getNode(43));
        myNewListJsonG3.add(grap.getGraph().getNode(44));
        myNewListJsonG3.add(grap.getGraph().getNode(46));
        myNewListJsonG3.add(grap.getGraph().getNode(47));

        assertEquals(myNewListJsonG3, myListJsonG3);

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

        new_DWGA();
        List<NodeData>  path = new LinkedList<>();
        path.add(graph.getNode(5));
        path.add(graph.getNode(2));
        path.add(graph.getNode(3));
        List<NodeData> myList = graphAlgo.tsp(path);
        List<NodeData> myNewList = new LinkedList<>();

        myNewList.add(graph.getNode(5));
        myNewList.add(graph.getNode(4));
        myNewList.add(graph.getNode(2));
        myNewList.add(graph.getNode(3));
        assertEquals(myNewList, myList);

        List<NodeData>  path2 = new LinkedList<>();
        path2.add(graph.getNode(6));
        path2.add(graph.getNode(3));
        List<NodeData> myList2 = graphAlgo.tsp(path2);
        List<NodeData> myNewList2 = new LinkedList<>();

        myNewList2.add(graph.getNode(6));
        myNewList2.add(graph.getNode(0));
        myNewList2.add(graph.getNode(2));
        myNewList2.add(graph.getNode(3));
        assertEquals(myNewList2, myList2);

        /////////////////////////G3//////////////////////////

        DirectedWeightedGraphAlgorithmsObj ans3 = new DirectedWeightedGraphAlgorithmsObj();
        ans3.load("data/G3.json");
        ans3.init(ans3.getGraph());

        List<NodeData>  path_G3 = new LinkedList<>();
        path_G3.add(ans3.getGraph().getNode(22));
        path_G3.add(ans3.getGraph().getNode(30));
        path_G3.add(ans3.getGraph().getNode(39));

        List<NodeData> myListJson3 = ans3.tsp(path_G3);
        List<NodeData> myNewListJson3 = new LinkedList<>();

        myNewListJson3.add(ans3.getGraph().getNode(22));
        myNewListJson3.add(ans3.getGraph().getNode(23));
        myNewListJson3.add(ans3.getGraph().getNode(31));
        myNewListJson3.add(ans3.getGraph().getNode(30));
        myNewListJson3.add(ans3.getGraph().getNode(29));
        myNewListJson3.add(ans3.getGraph().getNode(14));
        myNewListJson3.add(ans3.getGraph().getNode(15));
        myNewListJson3.add(ans3.getGraph().getNode(39));


       assertEquals(myNewListJson3, myListJson3);

        /////////////////////////G2//////////////////////////

        DirectedWeightedGraphAlgorithmsObj ans2 = new DirectedWeightedGraphAlgorithmsObj();
        ans2.load("data/G2.json");
        ans2.init(ans2.getGraph());

        List<NodeData>  path_G2 = new LinkedList<>();
        path_G2.add(ans2.getGraph().getNode(16));
        path_G2.add(ans2.getGraph().getNode(1));
        path_G2.add(ans2.getGraph().getNode(6));
        path_G2.add(ans2.getGraph().getNode(11));

        List<NodeData> myListJson2 = ans2.tsp(path_G2);
        List<NodeData> myNewListJson2 = new LinkedList<>();

        myNewListJson2.add(ans2.getGraph().getNode(16));
        myNewListJson2.add(ans2.getGraph().getNode(0));
        myNewListJson2.add(ans2.getGraph().getNode(1));
        myNewListJson2.add(ans2.getGraph().getNode(2));
        myNewListJson2.add(ans2.getGraph().getNode(6));
        myNewListJson2.add(ans2.getGraph().getNode(7));
        myNewListJson2.add(ans2.getGraph().getNode(8));
        myNewListJson2.add(ans2.getGraph().getNode(9));
        myNewListJson2.add(ans2.getGraph().getNode(10));
        myNewListJson2.add(ans2.getGraph().getNode(11));


        assertEquals(myNewListJson2, myListJson2);

    }


    @Test
    void saveLoad()  throws IOException {



        //////////////////10000 NODES ///////////////////////////
        DirectedWeightedGraphAlgorithmsObj ans1000 = new DirectedWeightedGraphAlgorithmsObj();
        DirectedWeightedGraph graph1000 = ans1000.getGraph();
        ans1000.init(graph1000);
        boolean loadGraph1000 = ans1000.load("data/1000Nodes.json");
        boolean saveGraph1000 = ans1000.save("output/1000NODES.json");
        DirectedWeightedGraph new_graph1000 = ans1000.getGraph();

        assertTrue(loadGraph1000);
        assertTrue(saveGraph1000);
        assertEquals(ans1000.getGraph(), new_graph1000);


        //////////////////10.000 NODES ///////////////////////////
        DirectedWeightedGraphAlgorithmsObj ans10000 = new DirectedWeightedGraphAlgorithmsObj();
        DirectedWeightedGraph graph10000 = ans10000.getGraph();
        ans10000.init(graph10000);
        boolean loadGraph100000 = ans10000.load("data/10000Nodes.json");
        boolean saveGraph100000 = ans10000.save("output/10000NODES.json");
        DirectedWeightedGraph new_graph100000 = ans10000.getGraph();

        assertTrue(loadGraph100000);
        assertTrue(saveGraph100000);
        assertEquals(ans10000.getGraph(), new_graph100000);


        //////////////////100.000 NODES ///////////////////////////
//        DirectedWeightedGraphAlgorithmsObj ans100000 = new DirectedWeightedGraphAlgorithmsObj();
//        DirectedWeightedGraph graph100000 = ans100000.getGraph();
//        ans100000.init(graph100000);
//        boolean loadGraph1000000 = ans100000.load("data/100000.json");
//        boolean saveGraph1000000 = ans100000.save("output/100000NODES.json");
//        DirectedWeightedGraph new_graph1000000 = ans100000.getGraph();
//
//        assertTrue(loadGraph1000000);
//        assertTrue(saveGraph1000000);
//        assertEquals(ans100000.getGraph(), new_graph1000000);


        ////////////////// G3NODES.json ///////////////////////////
        DirectedWeightedGraphAlgorithmsObj ans3 = new DirectedWeightedGraphAlgorithmsObj();
        DirectedWeightedGraph graph3 = ans3.getGraph();
        ans3.init(graph3);
        boolean loadGraph3 = ans3.load("data/G3.json");
        boolean saveGraph3 = ans3.save("output/G3NODES.json");
        DirectedWeightedGraph new_graph3 = ans3.getGraph();

        assertTrue(saveGraph3);
        assertTrue(loadGraph3);
        assertEquals(ans3.getGraph(), new_graph3);

        ////////////////// G2NODES.json ///////////////////////////
        DirectedWeightedGraphAlgorithmsObj ans2 = new DirectedWeightedGraphAlgorithmsObj();
        DirectedWeightedGraph graph2 = ans2.getGraph();
        ans2.init(graph2);
        boolean loadGraph2 = ans2.load("data/G2.json");
        boolean saveGraph2 = ans2.save("output/G2NODES.json");
        DirectedWeightedGraph new_graph2 = ans2.getGraph();

        assertTrue(saveGraph2);
        assertTrue(loadGraph2);
        assertEquals(ans2.getGraph(), new_graph2);

    }

    @Test
    void load() {
    }
}