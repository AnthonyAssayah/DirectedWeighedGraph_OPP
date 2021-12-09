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



    public DirectedWeightedGraphAlgorithmsObj() {
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
        //  If there isn't vertices or just only one return true
        if (this.DWG.nodeSize() == 0 || this.DWG.nodeSize() == 1) {
            return true;
        }

        Iterator<NodeData> it = this.DWG.nodeIter();

        while (it.hasNext()) {

            boolean check = this.BFS_Algo(it.next());
            // reset tag
            Iterator<NodeData> it2 = this.DWG.nodeIter();
            while (it2.hasNext()) {
                NodeData v = it2.next();
                v.setTag(0);
            }

            if (check == false) {
                return false;
            }
        }
        return true;
    }

    public void resetTag() {
        Iterator<NodeData> it2 = this.DWG.nodeIter();
        while (it2.hasNext()) {
            NodeData v = it2.next();
            v.setTag(0);
        }
    }

    public void resetInfo() {
        Iterator<NodeData> it2 = this.DWG.nodeIter();
        while (it2.hasNext()) {
            NodeData v = it2.next();
            v.setInfo("White");
        }
    }

    public void resetWeight() {
        Iterator<NodeData> it2 = this.DWG.nodeIter();
        while (it2.hasNext()) {
            NodeData v = it2.next();
            v.setWeight(Double.MAX_VALUE);
        }
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

        NodeData source = DWG.getNode(src);
        NodeData destination = DWG.getNode(dest);

        if (source == null || destination == null) {

            return -1;
        }

        return Dijkstra(this.DWG.getNode(src), this.DWG.getNode(dest));


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

        // if the shortestPathDist between the src and dest is -1; it means that there isn't path between them
        if (shortestPathDist(src, dest) == -1) {

            return null;
        }
        System.out.println("hiiii");
        List<NodeData> Nodelist = new LinkedList<>();
        NodeData source = this.DWG.getNode(src);
        NodeData destination = this.DWG.getNode(dest);

        // if the vertices are null return null
        if (source == null || destination == null) {

            return null;
        }

        // if the src and the dest are equal; just add it and return the list
        if (src == dest) {
            System.out.println("hiiii");
            Nodelist.add(destination);
            return Nodelist;
        }

        Dijkstra(source, destination);

        List<NodeData> reverse = new LinkedList<>();
        NodeData curr = destination;
        System.out.println(curr.getKey());
        System.out.println(curr.getTag());
        while (curr.getTag() != 0) {// while dest != src
            System.out.println("enter while");
            reverse.add(curr);
            curr = this.DWG.getNode(curr.getTag());
        }


        for (int j = Nodelist.size() - 1; j >= 0; j--) {
            System.out.println("reverse");
            reverse.add(Nodelist.get(j));
        }

        List<NodeData> reverse2 = new LinkedList<>();
        for(int k = reverse.size()-1; k >=0; k--) {

            reverse2.add(reverse.get(k));
        }
        Nodelist.add(source);

        for (int j = reverse2.size() - 1; j >= 0; j--) {
            Nodelist.add(reverse.get(j));
        }

        return Nodelist;

    }

    /**
     * Finds the NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {

        if (!this.isConnected()) {
            return null;
        }

        Iterator<NodeData> it = this.DWG.nodeIter();
        Iterator<NodeData> it2;

        double max = 0;
        double min = Double.MAX_VALUE;
        NodeData ret = null;
        while (it.hasNext()) {
            max = 0;
            it2 = this.DWG.nodeIter();
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

        List<NodeData> path = new LinkedList<>();



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

    private double Dijkstra(NodeData src, NodeData dst) {


        resetInfo();
        resetTag();
        resetWeight();
      // System.out.println("djistra");
        double dist = Double.MAX_VALUE;
        PriorityQueue<NodeData> pq = new PriorityQueue<>((n1, n2) -> Double.compare(n1.getWeight(), n2.getWeight()));

        src.setWeight(0);
        pq.add(src);

        while (!pq.isEmpty()) {
            NodeData curr = pq.poll();

              Iterator<EdgeData> it = this.DWG.edgeIter(curr.getKey());
               while ( it.hasNext()) {
                EdgeData e = it.next();

                if (e != null) {
                    NodeData node = this.DWG.getNode(e.getDest());

                    if (node.getInfo() == "White") {

//                        System.out.println("node.getInfo() = "+node.getInfo());
//                        System.out.println("node.getWeight() = "+node.getWeight());
//                        System.out.println("curr.getWeight() = "+curr.getWeight());
//                        System.out.println("e.getWeight() = "+e.getWeight());

                        if (node.getWeight() > curr.getWeight() + e.getWeight()) {

                            node.setWeight(Math.min(node.getWeight(), curr.getWeight() + e.getWeight()));
                            node.setTag(curr.getKey());
                        }
                        pq.add(node);
                    }
                }
                curr.setInfo("Black");
                if (curr.getKey() == dst.getKey()) {
                    return curr.getWeight();
                }
            }

        }
        if (dist == Double.MAX_VALUE) {

            return -1;
        }
        return dist;
    }
}