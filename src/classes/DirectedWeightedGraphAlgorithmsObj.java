package classes;
import api.EdgeData;
import com.google.gson.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class DirectedWeightedGraphAlgorithmsObj implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph DWG;

    public DirectedWeightedGraphAlgorithmsObj(){
        this.DWG = new DirectedWeightedGraphObj();
    }

    /**
     * Inits the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(DirectedWeightedGraph g) {

        this.DWG = g;
    }

    /**
     * Returns the underlying graph of which this class works.
     *
     * @return
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return DWG;
    }

    /**
     * Computes a deep copy of this weighted graph.
     *
     * @return
     */
    @Override
    public DirectedWeightedGraph copy() {

        DirectedWeightedGraph DWG_copy = new DirectedWeightedGraphObj();
            return DWG_copy;
    }

    /**
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     *
     * @return
     */
    @Override
    public boolean isConnected() {
        return false;
    }

    /**
     * Computes the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    /**
     * Computes the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        return null;
    }

    /**
     * Computes a list of consecutive nodes which go over all the nodes in cities.
     * the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution -
     * the lower the better.
     * See: https://en.wikipedia.org/wiki/Travelling_salesman_problem
     *
     * @param cities
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) throws IOException {

        JSONObject Json = new JSONObject();
        JSONArray nodes_arr = new JSONArray();
        JSONArray edges_arr = new JSONArray();
        JSONObject My_nodes;
        JSONObject My_edges;

        Iterator<NodeData> iterNodes = getGraph().nodeIter();

        while (iterNodes.hasNext()) {

            NodeData v =  iterNodes.next();
            My_nodes = new JSONObject();
            My_nodes.put("pos", v.getLocation());
            My_nodes.put("id", v.getKey());

            nodes_arr.put(My_nodes);

            Iterator<EdgeData> iterEdges = getGraph().edgeIter(v.getKey());

            while ( iterEdges.hasNext()) {

                EdgeData e =  iterEdges.next();
                My_edges = new JSONObject();
                My_edges.put("src", e.getSrc());
                My_edges.put("w", e.getWeight());
                My_edges.put("dest", e.getDest());

                edges_arr.put(My_edges);
            }
        }
        Json.put("Nodes", nodes_arr);
        Json.put("Edges", edges_arr);

        try {
            FileWriter fw = new FileWriter(file);
            fw.write(Json.toString());
            fw.close();

        } catch (IOException e) { /// maybe add JsonException  ??
            e.printStackTrace();
            //return false           // ??
        }
        return true;
    }

    /**
     * This method loads a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        return false;
    }
}
