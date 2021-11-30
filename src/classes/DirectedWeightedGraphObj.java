package classes;

import java.util.*;

import api.DirectedWeightedGraph;
import api.EdgeData;
import api.NodeData;

import java.util.Iterator;
import java.util.LinkedList;

public class DirectedWeightedGraphObj implements DirectedWeightedGraph {

    private HashMap<Integer, NodeData> nodes;
    private HashMap<Integer, HashMap<Integer, EdgeDataObj>> edges;
    private int num_of_Nodes;
    private int num_of_Edges;
    private int mc;

    public DirectedWeightedGraphObj() {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.num_of_Nodes = 0;
        this.num_of_Edges = 0;
        this.mc = 0;
    }
    /**
     * returns the node_data by the node_id,
     *
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public NodeData getNode(int key) {
        return this.nodes.get(key);
    }

    /**
     * returns the data of the edge (src,dest), null if none.
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return
     */
    @Override
    public EdgeData getEdge(int src, int dest) {
        return (this.edges.get(src) == null) ? null : this.edges.get(src).get(dest);
    }

    /**
     * adds a new node to the graph with the given node_data.
     * Note: this method should run in O(1) time.
     *
     * @param n
     */
    @Override
    public void addNode(NodeData n) {
        if (nodes.containsKey(n.getKey())) {
            return;
        }
        nodes.put(n.getKey(), n);
        mc++;
        num_of_Nodes++;
    }

    /**
     * Connects an edge with weight w between node src to node dest.
     * * Note: this method should run in O(1) time.
     *
     * @param src  - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    @Override
    public void connect(int src, int dest, double w) throws Exception {
        // if weight is negative
        if ( w < 0) {
                throw new Exception("Invalid input: weight must be positive");
        }
        // if the src and the dest are the same node
        if (src == dest) {
            return;
        }
        // if there is already an edge between node src and dest src - update the new weight
        if (this.getEdge(src, dest) != null && this.getEdge(src,dest).getWeight() != w) {
            EdgeDataObj e = new EdgeDataObj(src, dest, w);
            mc++;
         // else if there isn't an edge between the src node and dest node
        }
        else if (nodes.containsKey(src) && nodes.containsKey(dest) && this.getEdge(src, dest) == null) {
            EdgeDataObj e = new EdgeDataObj(src, dest, w);
            this.edges.get(src).put(dest,e);
            mc++;
            num_of_Edges++;
        }
    }

    /**
     * This method returns an Iterator for the
     * collection representing all the nodes in the graph.
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<node_data>
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        return this.nodes.values().iterator();
        //Iterator itr_in = this.nodes.getEdges_in().entrySet().iterator();
    }

    /**
     * This method returns an Iterator for all the edges in this graph.
     * Note: if any of the edges going out of this node were changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<EdgeData>() {
            Iterator<HashMap<Integer, EdgeDataObj>> iter = edges.values().iterator();
            Iterator<EdgeDataObj> temp = iter.next().values().iterator();
            @Override
            public boolean hasNext() {
                if (!temp.hasNext()) {
                    if (iter.hasNext()) {
                        temp = iter.next().values().iterator();
                        return true;
                    }
                    return false;
                }
                return true;
            }

            @Override
            public EdgeData next() throws NoSuchElementException {
                if (!temp.hasNext()) {
                    if (iter.hasNext()) {
                        temp = iter.next().values().iterator();
                        return temp.next();
                    }
                    throw new NoSuchElementException();
                }
                return temp.next();
            }
        };
    }

    /**
     * This method returns an Iterator for edges getting out of the given node (all the edges starting (source) at the given node).
     * Note: if the graph was changed since the iterator was constructed - a RuntimeException should be thrown.
     *
     * @param node_id
     * @return Iterator<EdgeData>
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method should run in O(k), V.degree=k, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public NodeData removeNode(int key) {
        if (this.nodes.get(key) != null) {
            NodeDataObj temp = (NodeDataObj) this.nodes.get(key);
            this.nodes.remove(key);
            num_of_Nodes--;
            mc++;
            Iterator itr_out = edgeIter(key);
            EdgeDataObj a;
            while (itr_out.hasNext()) {
                a = (EdgeDataObj) itr_out.next();
                this.removeEdge(a.getSrc(), a.getDest());
                num_of_Edges--;
                mc++;
            }
            Iterator<HashMap<Integer, EdgeDataObj>> it = this.edges.values().iterator();
            //Iterator itr_in = this.edges.entrySet().iterator();
            while (it.hasNext()) {
                a = it.next().get(temp.getKey());
                this.removeEdge(a.getSrc(), a.getDest());
                num_of_Edges--;
                mc++;
            }
            return temp;
        }
        return null;
    }

    /**
     * Deletes the edge from the graph,
     * Note: this method should run in O(1) time.
     *
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public EdgeData removeEdge(int src, int dest) {
        if (this.edges.get(src) != null && this.edges.get(src).containsKey(dest)) {
            EdgeDataObj edge = this.edges.get(src).get(dest);
            this.edges.get(src).remove(dest);
            num_of_Edges--;
            mc++;
            return edge;
        }
        return null;
    }

    /**
     * Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int nodeSize() {
        return this.num_of_Nodes;
    }

    /**
     * Returns the number of edges (assume directional graph).
     * Note: this method should run in O(1) time.
     *
     * @return
     */
    @Override
    public int edgeSize() {
        return this.num_of_Edges;
    }

    /**
     * Returns the Mode Count - for testing changes in the graph.
     *
     * @return
     */
    @Override
    public int getMC() {
        return this.mc;
    }
}
