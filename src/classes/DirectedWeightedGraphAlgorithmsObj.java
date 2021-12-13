package classes;
import api.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DirectedWeightedGraphAlgorithmsObj implements DirectedWeightedGraphAlgorithms {

    private DirectedWeightedGraph DWG;
    private HashMap<Integer, NodeData> previous;
    private HashMap<Integer, Double> distance;

    public DirectedWeightedGraphAlgorithmsObj() {
        this.DWG = new DirectedWeightedGraphObj();
        this.previous = new HashMap<Integer, NodeData>();
        this.distance = new HashMap<>();

        Iterator<NodeData> it3 = this.DWG.nodeIter();
        while (it3.hasNext()) {
            NodeData v = it3.next();
            v.setTag(0);
            previous.put(v.getKey(), null);
            v.setWeight(Double.MAX_VALUE);
            distance.put(v.getKey(), Double.MAX_VALUE);
        }

    }

    /**
     * Inits the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(DirectedWeightedGraph g) {

        this.DWG = g;
        this.previous = new HashMap<Integer, NodeData>();
        this.distance = new HashMap<>();

        Iterator<NodeData> it3 = this.DWG.nodeIter();
        while (it3.hasNext()) {
            NodeData v = it3.next();
            v.setTag(0);
            previous.put(v.getKey(), null);
            v.setWeight(Double.MAX_VALUE);
            distance.put(v.getKey(), Double.MAX_VALUE);
        }

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

        if (this.DWG.nodeSize() == 0 || this.DWG.nodeSize() == 1) {
            return true;
        }

        DirectedWeightedGraph flippedEdges = new DirectedWeightedGraphObj();

        Iterator<NodeData> NodeIter = this.DWG.nodeIter();

        while (NodeIter.hasNext()) {

            flippedEdges.addNode(NodeIter.next());
        }

        Iterator<EdgeData> EdgeIter = this.DWG.edgeIter();

        while (EdgeIter.hasNext()) {
            EdgeData e = EdgeIter.next();
            flippedEdges.connect(e.getDest(), e.getSrc(), e.getWeight());
        }

        boolean ThisGraphBFS = this.BFS_Algo(this.DWG.getNode(0));
        DirectedWeightedGraph temp = this.DWG;
        this.DWG = flippedEdges;
        boolean ThisGraphTransposeBFS = this.BFS_Algo(this.DWG.getNode(0));
        this.DWG = temp;
        return ThisGraphBFS && ThisGraphTransposeBFS;
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

        Iterator<NodeData> it2 = this.DWG.nodeIter();
        while (it2.hasNext()) {
            NodeData v = it2.next();
            v.setTag(0);
            previous.put(v.getKey(), null);
            v.setWeight(Double.MAX_VALUE);
            distance.put(v.getKey(), Double.MAX_VALUE);
        }

        NodeData source = DWG.getNode(src);
        NodeData destination = DWG.getNode(dest);

        if (source == null || destination == null) {
            return -1;
        }

        if (src == dest) {
            return 0;
        }

        Dijkstra(source);

        return distance.get(dest);
    }

    /**
     * Computes the shortest path between src to dest - as an ordered List of nodes:
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

        init(DWG);
        NodeData source = DWG.getNode(src);
        NodeData destination = DWG.getNode(dest);

        if (source == null || destination == null)
            return null;

        List <NodeData> list = new LinkedList <>();

        Dijkstra(source);

        if (src == dest) {
            list.add(source);
            return list;
        }

        int i = 0;
        while (source != destination) {
            list.add(i, destination);

            if (destination == null)
                return null;
            destination = previous.get(destination.getKey());
            i++;
        }

        list.add(i, source);

        List<NodeData> reverse_list = new LinkedList<>();

        for (int j = list.size() -1; j >= 0; j--){
            reverse_list.add(list.get(j));
        }

        return reverse_list;
    }

    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {

        init(DWG);

        if (!this.isConnected()) {
            return null;
        }

        Iterator<NodeData> it = this.DWG.nodeIter();

        double min = Double.MAX_VALUE;
        NodeData ret = null;

        while (it.hasNext()) {

            double max = 0;
            Iterator<NodeData> it2 = this.DWG.nodeIter();
            NodeData node1 = it.next();

            while (it2.hasNext()) {

                NodeData node2 = it2.next();

                if (shortestPathDist(node1.getKey(), node2.getKey()) > max) {
                    max = shortestPathDist(node1.getKey(), node2.getKey());

                }

            }
            if (max < min) {

                min = max;
                ret = node1;
            }
        }
        return ret;
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

        List<NodeData> tsp_path = new LinkedList<>();
        List<Integer> curr_list = new LinkedList<>();

        for(int i = 0; i < cities.size(); i++) {
            curr_list.add(cities.get(i).getKey());
        }

        NodeData tmp = cities.get(0);
        tsp_path.add(this.DWG.getNode(curr_list.get(0)));
        curr_list.remove(0);

        while (!curr_list.isEmpty()) {

            double shortest_path = Integer.MAX_VALUE;
            int node_id = -1;
            int index =-1;

            for (int i =0; i < curr_list.size(); i++) {

                int key = curr_list.get(i);
                if(shortestPathDist(tmp.getKey(), key) < shortest_path) {
                    shortest_path = shortestPathDist(tmp.getKey(), key);
                    node_id = key;
                    index = i;
                }
            }
            List<NodeData> short_list = shortestPath(tmp.getKey(), node_id);
            short_list.remove(0);

            while ( !short_list.isEmpty()) {
                tsp_path.add(short_list.get(0));
                short_list.remove(0);
            }
            int node = curr_list.get(index);
            tmp = this.DWG.getNode(node);
            curr_list.remove(curr_list.get(index));
        }

        return tsp_path;
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
            NodeData v = iterNodes.next();
            My_nodes = new JSONObject();
            My_nodes.put("pos", v.getLocation().x() + "," + v.getLocation().y() + "," + v.getLocation().z());
            My_nodes.put("id", v.getKey());
            nodes_arr.add(My_nodes);

            Iterator<EdgeData> iterEdges = getGraph().edgeIter(v.getKey());

            while (iterEdges.hasNext()) {
                EdgeData e = iterEdges.next();
                My_edges = new JSONObject();
                My_edges.put("src", e.getSrc());
                My_edges.put("w", e.getWeight());
                My_edges.put("dest", e.getDest());
                edges_arr.add(My_edges);
            }
        }
        Json.put("Nodes", nodes_arr);
        Json.put("Edges", edges_arr);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(Json.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

        JSONParser jsonParser = new JSONParser();
        File f = new File(file);      // should receive file path as input -> fileName
        try (FileReader reader = new FileReader(f)) {

            Object obj = jsonParser.parse(reader);
            JSONObject NodeObject = (JSONObject) obj;

            JSONArray NodeList = (JSONArray) NodeObject.get("Nodes");
            JSONArray EdgeList = (JSONArray) NodeObject.get("Edges");

            NodeList.forEach(node -> ppNode((JSONObject) node));
            EdgeList.forEach(edge -> ppEdge((JSONObject) edge));

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void ppEdge(JSONObject edge) {
        if (edge != null) {
            int src = ((Long) edge.get("src")).intValue();
            int dest = ((Long) edge.get("dest")).intValue();
            double w = (double) edge.get("w");
            try {

                this.DWG.connect(src, dest, w);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void ppNode(JSONObject node) {
        if (node != null) {
            int id = ((Long) node.get("id")).intValue();
            String pos = (String) node.get("pos");
            Double[] d = {Double.parseDouble(pos.split(",")[0]), Double.parseDouble(pos.split(",")[1])};
            GeoLocation g = new GeoLocationObj(d[0], d[1], 0);
            NodeData n = new NodeDataObj(id, g);
            this.DWG.addNode(n);
        }
    }

    private boolean BFS_Algo(NodeData node) {

        // reset tag
        Iterator<NodeData> it2 = this.DWG.nodeIter();
        while (it2.hasNext()) {
            NodeData v = it2.next();
            v.setTag(0);
        }

        Queue<NodeData> Queue = new LinkedList<>();
        node.setTag(1);
        int counter = 1;
        Queue.add(node);
        while (!Queue.isEmpty()) {

            NodeData curr = Queue.poll();

            Iterator<EdgeData> neighbours = this.DWG.edgeIter(curr.getKey());

            while (neighbours.hasNext()) {

                NodeData dst = this.DWG.getNode(neighbours.next().getDest());
                if (dst.getTag() == 0) {
                    dst.setTag(1);
                    Queue.add(dst);
                    counter++;
                }
            }
        }
        return (counter == this.DWG.nodeSize());
    }


    private void Dijkstra(NodeData src) {

        PriorityQueue<NodeData> pq = new PriorityQueue<>(Comparator.comparingDouble(NodeData::getWeight));

        distance.put(src.getKey(), 0.0);
        src.setWeight(0);
        pq.add(src);

        while (!pq.isEmpty()) {

            NodeData curr = pq.poll();

            Iterator<EdgeData> it = this.DWG.edgeIter(curr.getKey());
            while (it.hasNext()) {
                EdgeData e = it.next();
                if (e != null) {

                    NodeData node = DWG.getNode(e.getDest());

                    double dist = this.distance.get(curr.getKey()) + e.getWeight();

                    if (this.distance.get(node.getKey()) > dist) {
                        node.setWeight(dist);
                        previous.put(node.getKey(), curr);
                        distance.put(node.getKey(), dist);

                        pq.remove(node);
                        pq.add(node);
                    }
                }
            }
        }
    }
}