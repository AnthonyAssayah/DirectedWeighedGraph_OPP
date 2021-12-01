package testing;

import api.DirectedWeightedGraph;
import api.GeoLocation;
import api.NodeData;
import classes.DirectedWeightedGraphObj;
import classes.GeoLocationObj;
import classes.NodeDataObj;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphObjTest {

    private static DirectedWeightedGraph graph1 = new DirectedWeightedGraphObj();
    private static DirectedWeightedGraph graph2 = new DirectedWeightedGraphObj();
    private static int numOfNodes_1;
    private static int numOfNodes_2;
    private static int numOfEdges_1;
    private static int numOfEdges_2;

    public static DirectedWeightedGraphObj new_DWG1(int numOfNodes_1, int numOfEdges_1) {
        for (int i = 0; i <= numOfNodes_1; i++){
        double x = 0,y = 11 ,z = 32;

           x = x + 7;
           y = y * 4;
           z = z * 3;

           GeoLocation g1 = new GeoLocationObj(x,y,z);
           NodeData n1 = new NodeDataObj(4,g1,5,"White", 6 );

        }

        return null;
    }

    @Test
    void getNode() {
    }

    @Test
    void getEdge() {
    }

    @Test
    void addNode() {
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