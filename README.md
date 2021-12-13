<img src="https://dist.neo4j.com/wp-content/uploads/graph_cluster_distant_wide.jpg" width="1000" height="260" />

# Directed & Weighted Graphs - EX2_OOP

## Presentation 📌

This project is based on ***Design and Implementation*** of directed and weighted graphs for an OOP assigment. This exercise is composed of two main parts, the first is to
implement the five different interfaces in order to create all the elements that form a graph. The second part is the GUI, which will allow us to really visualize 
the graph and all its parameters and options. 

<br />

## External Sources 🔎

- Explanation about the *Dijkstra Algorithm* on this [Youtube](https://www.youtube.com/watch?v=XB4MIexjvY0&list=PLDN4rrl48XKpZkf03iYFl-O29szjTrs_O&index=45) video, detailling
 all the process in examples with the drawbacks and analysing the time complexity.
 
 - Inofrmation about the famous problem of [Travelling salesman problem](https://en.wikipedia.org/wiki/Travelling_salesman_problem).This optimization problem which consists in determining, given a list of cities and the distances between all the pairs of cities, the shortest circuit that passes through every town once and only once.

- Tutorial on the [*BFS Algorithm*](https://www.programiz.com/dsa/graph-bfs).This algorithm is used to calculate the distances of all the nodes from a source node in an  graph directed or not directed.

## Interfaces & Classes 🎯

There are 5 classes defined in this project, the interfaces are in the ```api``` package and their implementations can be found in ```classes``` package. Their properties and 
methods are detailed below.

### <ins>***1 - NodeData***<ins> 


NodeDataObj is an implementation of a NodeData interface. This class is a simple representation of a vertex on a directed weighted graph and implement a set of different
operations.

Each ```NodeDataObj``` contains five attributes:

  - ```key```: This is the ID associated to each node.
  - ```location```: This is the position of the node in a 3D dimension with 3 coordinates(x,y,z).
  - ```weight```: This is a double variable used as node's weight initialized to 0. 
  - ```tag```: This is an Integer value used to calculate several algorithms initialized to 0.
  - ```info```: This is a string corresponding to node’s meta data which helps us to calculate various functions initialized to "White".
  
<br />
  
  | **Functions**      |    **Explanation**        |
|-----------------|-----------------------|
| `NodeDataObj ( NodeDataObj NewNode)` | A *copy constructor* to create a new NodeDataObj |
| `NodeDataObj (int key, GeoLocation g)` | A *constructor* taking two arguments |
| `getKey()` | Returns the "name" associated to a specific node |
| `getLocation()` | Returns the location (3D) of this node |
| `setLocation(Geolocation p)` | Changes the location of the node as *p*  |
| `getWeight()` | Returns the weight of this specific node |
| `setWeight(double w)` | Changes the weight of the node as *w* |
| `getInfo()` | Returns the meta data associated with this node |
| `setInfo(String s)` | Changes the meta data of the node as *s* |
| `getTag()` | Returns the tag value of the node |
| `setTag(int t)` | Changes the tag value for temporal marking an node to *t* |
| `toString()` | Returns a string of the node with all its parameters |

<br />

### <ins>***2 - EdgeData***<ins>
  
  EdgeDataObj is a representation of a EdgeData interface. This class designes all the characteristics and operations applicable on a directional edge defined by its source and   destination in a directional weighted graph.

  An ```EdgeDataObj``` is discribed by five fields:
  
  - ```src```: This integer represents the source node of the edge.
  - ```dest```: This integer represents the destination node of the edge.
  - ```weight```: This is a double variable used as edge's weight, must be positive. 
  - ```info```: This is a string corresponding to edge’s meta data. 
  - ```tag```: This is an Integer temporal value used in many fonctions initialized to 0.

<br />
  
  | **Functions**      |    **Explanation**        |
|-----------------|-----------------------|
| `EdgeDataObj(EdgeDataObj other)` | A *copy constructor* to create a new EdgeDataObj |
| `EdgeDataObj(int src, int dest, double weight)` | A *constructor* taking three arguments |
| `getSrc()` | Returns the id of the source node of this edge |
| `getDest()` | Returns the id of the destination node of this edge |
| `getWeight()` | Returns the weight of this specific edge |
| `getInfo()` | Returns the meta data associated with this edge |
| `setInfo(String s)` | Changes the meta data of the edge as *s* |
| `getTag()` | Returns the tag value of the edge |
| `setTag(int t)` | Changes the tag value for temporal marking an edge to *t* |
| `toString()` | Returns a string of the edge with all its parameters |

<br />

  ### <ins>***3 - GeoLocation***<ins>
  
 GeoLocation implements GeoLocationObj, this class contains all the elements needed for a point 3D visualization The interface geo_location is designed to have all the qualities  that are needed per a node in a directed weighted graph.
  
 Only three coordonates constitute a ```GeoLocationObj``` in a 3D space:
  
  - ```x```: the node’s value on the x axis.
  - ```y```: the node’s value on the y axis.
  - ```z```: the node’s value on the z axis.

    <br />

| **Methods**      |    **Explanation**        |
|-----------------|-----------------------|
| `GeoLocationObj(double x, double y, double z)` | A *constructor* taking the three coordonates |
| `public GeoLocationObj(GeoLocationObj other)` | A *copy constructor* to create a new GeoLocationObj |
| `x()` | Returns the x axis value |
| `y()` | Returns the y axis value |
| `z()` | Returns the z axis value |
| `distance(GeoLocation g)` | Returns the distance location between two nodes |
  
  <br />
  
  ### <ins>***4 - DirectedWeightedGraph***<ins>
  
  
  DirectedWeightedGraphObj implemented by DirectedWeightedGraph, this class gathers all the necessary components and features in order to build a DirectedWightedGraph.
  
  The essential elements for the configuration of the ```DirectedWeightedGraphObj``` are:
  
  - ```num_of_Nodes```: This Integer represents the amount of nodes in the graph.
  - ```num_of_Edges```: This Integer represents the amount of edges in the graph.
  - ```mc```: This Integer is a counter that increments by one for any change in the graph. 
  - ```nodes```:  HashMap<Integer, NodeData> containing all the nodes in the graph.
  - ```edges```: HashMap<Integer, HashMap<Integer, EdgeDataObj>> representing all `nodes` for each edges.
  
  <br />

  
| **Main methods**      |    **Explanation**        |
|-----------------|-----------------------|
| `DirectedWeightedGraphObj()` |A *default constructor*, initiates the default parameters values  |
| `DirectedWeightedGraphObj(DirectedWeightedGraph graph)` | A *copy constructor* to create a new GeoLocationObj |
| `getNode(int key)` | Returns the NodeData by the node's "name" (takes **O(1)** with the HashMap `get()` function) |
| `getEdge(int src, int dest)` | Returns the edge `src` and `dest` between two nodes (takes **O(1)** with the HashMap `get()` function) |
| `addNode(NodeData n)` | Adds a new node into the graph (takes **O(1)** with the HashMap `put()` function) |
| `connect(int src, int dest, double w)` | Connects an edge between a node src to a node dest (takes **O(1)** with the HashMap `put()` function)|
| `Iterator<NodeData> nodeIter()` | Returns an Iterator representing all the nodes in the graph |
| `Iterator<EdgeData> edgeIter()` | Returns an Iterator for all the edges in this graph |
| `Iterator<EdgeData> edgeIter(int node_id)` | Returns an Iterator for all the edges in this graph |
| `removeNode(int key)` | Removes the node and all the edges which are connected to this node from the graph (takes **O(k)** k=deg(V) with the HashMap `put()` and `remove()` functions) |
| `removeEdge(int src, int dest)` | Deletes an edge defined by its src and dest from the graph (takes **O(1)** with the HashMap `remove()` function) |
| `nodeSize()` | Returns the `num_of_Nodes` in the graph (takes **O(1)**) |
| `edgeSize()` | Returns the `num_of_Edges` in the graph (takes **O(1)**) |
| `getMC()` | Returns the `mc`, number of changes (additions/deletions of nodes/edges) in the graph (takes **O(1)**) |
  
   <br />
  
   ### <ins>***5 - DirectedWeightedGraphAlgorithms***<ins>
  
  Implemented by DirectedWeightedGraphAlgorithmsObj, DirectedWeightedGraphAlgorithms is the most important class that does the most "hardest job". Indeed, it will use certain algorithms and import external libraries which will allow us to perform several actions on the DirectedWeightedGraphObj.
  
  `DirectedWeightedGraphAlgorithmsObj` is characterized by only one object: a `DirectedWeightedGraphObj`.

  <br />

| **Main methods**      |    **Explanation**  | 
|-----------------|-----------------------|
| `DirectedWeightedGraphAlgorithmsObj()` | A *default constructor*, initiates the default parameters values|
| `init(DirectedWeightedGraph g)` | Inits the graph and sets algorithms operate on it |
| `getGraph()` | Returns the current graph |
| `copy()` | Returns a deep copy of this DirectedWeightedGraph |
| `isConnected()` | Check if there is a valid path from every node to each node, using `BFS_algo` (takes **O(n*(n+m))** deg(V)=n  deg(E)=m) |
| `shortestPathDist(int src, int dest)` | Returns the length value (weight) of the shortest path between src to dest, using `Dijkstra` |
| `shortestPath(int src, int dest)` | Returns the nodes in the shortest path between src to dest in a list, using `Dijkstra` |
| `center()` | Returns the node which minimizes the max distance to all the other nodes, that means the min of all max shortest path over all nodes |
| `tsp(List<NodeData> cities)` | Computes a list of consecutive nodes which go over all the nodes in cities |
| `save(String file)` | Saves this `DirectedWeightedGraphObj` to the given file name in a **JSON** format |
| `load(String file)` | Loads a `DirectedWeightedGraphObj` to this `DirectedWeightedGraphAlgorithmsObj` from a **JSON** format |

  <br /> 
  
* <ins>***Algorithm and other Methods***<ins>:
  
  **1 . ```BFS_Algo(NodeData node)```:**
  
    The BFS function based on Breadth First Search Traversal means visiting all the nodes of a graph searching all the vertices of a graph or tree data structure. It helps us 
  to verify if the graph is connected by passing over all the neighbors of the source and then explore them one by one. The algorithm stores the visited nodes in a queue and 
  increment a counter by one for every node connected to the source.
  The algorithm works as follows:
    - Start by putting the source node in the queue.
    - Remove the node from the beginning of the queue to process it.
    - Iterate over all the neighbors edges of this node.
    - Put all its unvisited neighbors in the queue.
    - Set its tag to 1 and increment the counter.
    - Repeat the process until the queue is empty.
  
      If the counter is equal to the `nodeSize()`, that means that the source node is connected.
      The time complexity of the BFS algorithm is **O(V+E)** where V is thenumber of nodes and E the number of edges.
  
    <br />
  
  **2. ```ppEdge(JSONObject edge)```:** 
  
     This private method is used in order to load the edges graph from a **Json** file, importing Json librairies like `JSONArray`, `JSONObject`or `JSONParser`.    
  
    <br />
  
  **3. ```ppNode(JSONObject node)```:**
  
     As the same, this private method is used in order to load the nodes graph from a **Json** file, importing the same librairies.
  
  <br />
 
 ### <ins>***6 - Ex2***<ins>
 
This is the main class, the "control pole". It will call on other interfaces and classes including the GUI in order to verify our implementation. `Ex2` includes three major functions detailled below:

- `DirectedWeightedGraph getGrapg (String json_file):` This method will create a new `DirectedWeightedGraphAlgorithmsObj`, load it form a Json file and return the graph which corresponds to it.

- `DirectedWeightedGraphAlgorithms getGrapgAlgo (String json_file)`: This function looks like the previous one, indeed it will also create a new `DirectedWeightedGraphAlgorithmsObj`, load it form a Json file and at the end return it.

- `void runGUI (String json_file)`: This static function will run our GUI using the Json file.
 
  <br />
 
 ### <ins>***7 - Tests***<ins>
 
 You can find our *Junit* tests in `testing` package in the `src` Directory.
 
   <br />
  
  ## GUI 🎨
 
 The `gui` class is the real visual representation, it kind of allows us to translate all our classes and algorithms on a window. The work on the Gui is divided into three main parts in which each has an essential role in the design of the graph.
 
 ***1.*** class `gui`:
  This class defines the size of the sreen window `int[] screenSize.The frame who is implemented as an instance of the JFrame class, is a window that has decorations such as a border, a title, the Toolbar and supports button components that close or iconify the window. 
 
     - public gui(DirectedWeightedGraphAlgorithms alg)
     - public static JMenuBar makeMenueBar(JFrame frame, CustomPaintComponent CPC)
 
   <br />
 
 ***2.*** class `CustomPaintComponent`: 
   The role of this class is to draw the graph on the screen, it is first necessary to subclass a `Component` and override its `paint()` method. The paint() method is automatically called 
   by the windowing system whenever component's area needs to be repainted.
 
     - public void paint(Graphics g) 
     - public void SetGraph(DirectedWeightedGraphAlgorithms DWG)
     - public DirectedWeightedGraphAlgorithms GetGraph() 
     - public void SetScreenSize(int[] ScreenSize)
     - public int[] GetScreenSize() 
     - public void SetGraphics2D(Graphics2D g2d)
     - public Graphics2D GetGraphics2D() 
     - public void drawNodes(Graphics2D g2d)
     - public void drawEdges(Graphics2D g2d)
     - public double algoX(double x)
     - public double algoY(double y) 
     - public void updatePrivateValues(DirectedWeightedGraph DWG)
     - public void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2)
     - public void highLightNode(NodeData node)
     - public void highLightPath(LinkedList<NodeData> nodes)
 
   <br />
 
  ***3.*** class `MenuDemo`:
 The class use `ActionListener` to notify when we click on the button or menu item, it is notified against `ActionEvent`. The ActionListener interface is found in `java.awt.event` package. The method `actionPerformed()` is invoked automatically when we click on the registered component. `ItemListener` notify us when we click on the checkbox against `ItemEvent`. The only method implemented is `itemStateChanged()`, when we click or unclick on the registered checkbox component.
 
     - public MenuDemo(JMenuItem[] JMI_Arr, JFrame frame, CustomPaintComponent CPC)
     - public void actionPerformed(ActionEvent e)
     - public void itemStateChanged(ItemEvent e) 
     - private void DrawGraph(DirectedWeightedGraphAlgorithms DWGA) 
 
 Apart from the possibility of retransmitting the graph on a window, the gui allows us to the user can to choose manually several functions and algorithms and compile them just by clicking on the function in the toolbar
 
 ***1.*** `Save` and `Load` the graph in `File` menu, by choosing the Json file in your Desktop. 
 
  <br />
 
 <p align="center">
   <img width="640" height="470" src="https://user-images.githubusercontent.com/92322613/145551275-bc8a3d77-4f39-4d7e-84fd-042f2ecd6396.png">
</p>
 
 
 ***2.*** You can also `Add Node`, `Remove Node`, `Add Edge` and `Remove Node` into/from the graph in `Edit` menu, by writting the coordinates and the ID of a node or the source, destination and weight for an edge. *(In this example, I just added a new node with ID: 32 and and connect it with a new edge to node 1)*.
 
   <br />
 
 <p align="center">
   <img width="1700" height="360" src="https://user-images.githubusercontent.com/92322613/145558728-0cd16d41-2523-4d58-974c-387df0476c5c.PNG">
</p>
 
  
 ***3.*** You can apply the algorithms `ShortestPath`, `isConnected`, `center` and `TSP` on the graph in `Algorithms` menu.
 
 - `isConnected` will return a window with a message if the graph is connected or not.
    <br />
 
 <p align="center">
   <img width="550" height="440" src="https://user-images.githubusercontent.com/92322613/145560100-6d229f57-5a33-429f-99ae-d77217d8b567.PNG">
</p>
  
  - `ShortestPath` will return graph with a coloring path from the node *source* to the node *dest* and `center` will color the center node.
    <br />

  <p align="center">
   <img width="550" height="440" src="https://user-images.githubusercontent.com/92322613/145561713-a1466d8b-703d-47ff-abe0-d7909680ec4a.PNG">
</p>
   <br />
 
 
  <br />
  
  ## UML Diagram 📊
 <br />
 
 UML Diagram of the interfaces and their implementations:
   <p align="center">
   <img width="870" height="640" src="https://user-images.githubusercontent.com/92322613/145563911-2fe01524-6288-40c9-9171-728e2877c1ec.png">
</p>
   <br />
  
 UML Diagram of the Gui:
   <p align="center">
   <img width="940" height="520" src="https://user-images.githubusercontent.com/92322613/145564582-8b957101-a88a-423b-a708-40ac69d5930b.PNG">
</p>
   <br />
  
 ## Analysis of Performance 📈
 
 
  <br />

| **Number of nodes in the graph**      |    **Loading time of the graph**  | 
|-----------------|-----------------------|
| *1000* | 262ms |
| *10.000* | 776ms |
| *100.000* | 3sec 545ms |
| *1.000.000* | heap |

 
 
  ## How to run 💻
  
 For running the program, you need to first go to the folder where the `jar` is stored and the command the command prompt.
 In the CMD, run the following command `java -jar EX2_OOP.jar <enter the file path of the initial graph>`. 
 *(For example, if you want to load G1.json graph you enter in the CMD: ` java -jar EX2_OOP.jar data/G1.json`)*.

 
