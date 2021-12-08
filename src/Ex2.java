import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import classes.DirectedWeightedGraphAlgorithmsObj;
import gui.gui;

/**
 * This class is the main class for Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraph getGrapg(String json_file) {
        DirectedWeightedGraphAlgorithmsObj ans = new DirectedWeightedGraphAlgorithmsObj();
        ans.load(json_file);
        return ans.getGraph();
    }
    /**
     * This static function will be used to test your implementation
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithmsObj ans = new DirectedWeightedGraphAlgorithmsObj();
        ans.load(json_file);
        return ans;
    }
    /**
     * This static function will run your GUI using the json fime.
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     *
     */
    public static void runGUI(String json_file) {
        DirectedWeightedGraphAlgorithms alg = getGrapgAlgo(json_file);
        // ****** Add your code here ******
        //
        // ********************************
    }
    public static void main(String[] args) {

        //gui GUI = new gui(args[0]);
        gui GUI = new gui("C:/Users/user/IdeaProjects/EX2_OOP/data/G1.json");
    }
}