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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedWeightedGraphObjTest {

    private DirectedWeightedGraph graph = new DirectedWeightedGraphObj();

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

    public void new_DWG1()  {
        graph = new DirectedWeightedGraphObj();

        n0 = new NodeDataObj(4,new GeoLocationObj(3, 10, 3));
        n1 = new NodeDataObj(5,new GeoLocationObj(5, 20.5, 9));
        n2 = new NodeDataObj(3,new GeoLocationObj(-12, 25, 6));
        n3 = new NodeDataObj(7,new GeoLocationObj(5, -1, 1));
        n4 = new NodeDataObj(9,new GeoLocationObj(0, 0, 0));

        e0 = new EdgeDataObj(n0.getKey(), n1.getKey(),3);
        e1 = new EdgeDataObj(n1.getKey(), n2.getKey(),5);
        e2 = new EdgeDataObj(n1.getKey(), n3.getKey(),2);
        e3 = new EdgeDataObj(n2.getKey(), n3.getKey(),7);
        e4 = new EdgeDataObj(n3.getKey(), n4.getKey(),1);
        e5 = new EdgeDataObj(n0.getKey(), n4.getKey(),2);

        graph.addNode(n0);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);


        try {
            graph.connect(n0.getKey(),n1.getKey(),e0.getWeight());
            graph.connect(n1.getKey(),n2.getKey(),e1.getWeight());
            graph.connect(n1.getKey(),n3.getKey(),e2.getWeight());
            graph.connect(n2.getKey(),n3.getKey(),e3.getWeight());
            graph.connect(n3.getKey(),n4.getKey(),e4.getWeight());
            graph.connect(n0.getKey(),n4.getKey(),e5.getWeight());
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Test
    void getNode() {
        new_DWG1();
        NodeData key_1 = graph.getNode(4);
        assertEquals(key_1,n0);
        NodeData key_2 = graph.getNode(7);
        assertEquals(key_2,n3);
        NodeData key_3 = graph.getNode(1);
        assertNotEquals(key_3,n4);
    }

    @Test
    void getEdge() {
        new_DWG1();
        graph.connect(n0.getKey(), n1.getKey(), e0.getWeight());
        EdgeData edge = new EdgeDataObj((EdgeDataObj) graph.getEdge(n0.getKey(), n1.getKey()));
        assertEquals(edge.getSrc(), e0.getSrc());
        assertEquals(edge.getDest(), e0.getDest());
        assertEquals(edge.getWeight(), e0.getWeight());

        graph.connect(n1.getKey(), n2.getKey(), e1.getWeight());
        EdgeData edge2 = new EdgeDataObj((EdgeDataObj) graph.getEdge(n1.getKey(), n2.getKey()));
        assertEquals(edge2.getWeight(), e1.getWeight());

    }

    @Test
    void addNode() {
        new_DWG1();
        GeoLocation g = new GeoLocationObj(11, 22, 6);
        NodeData new_node = new NodeDataObj(2,g);
        int s = graph.nodeSize();
        this.graph.addNode(new_node);
        int t = graph.nodeSize();
        assertEquals(s+1,t);
        new_DWG1();
        NodeData new_node2 = new NodeDataObj((NodeDataObj) n0);
        this.graph.addNode(new_node2);
        int w = graph.nodeSize();
        assertEquals(s,w);
    }

    @Test
    void connect() {
        new_DWG1();
        graph.connect(n0.getKey(), n1.getKey(), e0.getWeight()); // already exist in graph1
        graph.connect(n1.getKey(), n2.getKey(), e1.getWeight()); // already exist in graph1
        int s = graph.edgeSize();
        graph.connect(n1.getKey(), n3.getKey(), e2.getWeight()); // already exist in graph1
        int t = graph.edgeSize();
        assertEquals(s,t);

        new_DWG1();
        graph.connect(n4.getKey(), n2.getKey(), e0.getWeight());
        graph.connect(n1.getKey(), n3.getKey(), e1.getWeight());  // already exist in graph1
        graph.connect(n4.getKey(), n4.getKey(), e2.getWeight());  // can't exist
        int w = graph.edgeSize();
        assertEquals(w,t+1);


    }

    @Test
    void nodeIter() {
        new_DWG1();
        LinkedList<Integer> list = new LinkedList<>();
        Iterator<NodeData> iterNodes = this.graph.nodeIter();
        list.add(n0.getKey());
        list.add(n1.getKey());
        list.add(n2.getKey());
        list.add(n3.getKey());
        list.add(n4.getKey());
        int count = 0;
        while (iterNodes.hasNext()) {
            NodeData v = iterNodes.next();
            assertEquals(true, list.contains(v.getKey()));
            System.out.println(v.getKey());
            count++;
        }
        System.out.println(count);
        System.out.println(list.size());
        assertEquals(count, list.size());
    }

    @Test
    void edgeIter() {
        new_DWG1();
        Iterator<EdgeData> iterEdges = this.graph.edgeIter();
        int counter = 0;
        while (iterEdges.hasNext()) {

            EdgeData e = iterEdges.next();
            assertEquals(this.graph.getEdge(e.getSrc(),e.getDest()), e);
            counter++;
        }

        assertEquals(counter, this.graph.edgeSize());
        }

    @Test
    void testEdgeIter() {
        new_DWG1();
        Iterator<EdgeData> iterEdges = this.graph.edgeIter(n1.getKey());
        List<EdgeData> list = new LinkedList<>();
        list.add(e1);
        list.add(e2);
        int counter = 0;

        while (iterEdges.hasNext()) {
            EdgeData e = iterEdges.next();
            assertEquals(this.graph.getEdge(e.getSrc(),e.getDest()), e);
            counter++;

        }
        assertEquals(counter, list.size());
    }

    @Test
    void removeNode() {
        new_DWG1();
        int t = this.graph.nodeSize();
        this.graph.removeNode(n1.getKey());
        this.graph.removeNode(n2.getKey());
        int s = this.graph.nodeSize();

        assertEquals(t-2,s);


    }

    @Test
    void removeEdge() {
        new_DWG1();
        int s = this.graph.edgeSize();
        graph.removeEdge(e0.getSrc(),e0.getDest());
        int t = this.graph.edgeSize();
        assertEquals(s-1,t);
        graph.removeEdge(e0.getSrc(),e0.getDest()); // already removed
        assertEquals(s-1,t);

        graph.removeEdge(e1.getSrc(),e2.getDest());  // doesn't exist in graph1
        assertEquals(s-1,t);
    }

    @Test
    void nodeSize() {
        new_DWG1();
        assertEquals(5,graph.nodeSize());

        GeoLocation g = new GeoLocationObj(11, 22, 6);
        NodeData new_node = new NodeDataObj(2,g);
        this.graph.addNode(new_node);
        assertNotEquals(5,graph.nodeSize());

    }

    @Test
    void edgeSize()  {
        new_DWG1();
        assertEquals(6,graph.edgeSize());

        System.out.println(graph.edgeSize());
        graph.connect(n4.getKey(), n0.getKey(), 6);
        System.out.println(graph.edgeSize());
        assertNotEquals(5,graph.edgeSize());

        graph.connect(n2.getKey(), n4.getKey(), 4);
        System.out.println(graph.edgeSize());
        assertEquals(8,graph.edgeSize());

    }

    @Test
    void getMC() {
        new_DWG1();
        assertEquals(graph.getMC(),11);
        graph.removeEdge(e0.getSrc(),e0.getDest());
        graph.removeEdge(e1.getSrc(),e1.getDest());
        graph.removeEdge(e2.getSrc(),e2.getDest());
        assertEquals(graph.getMC(),14);

    }
}