package classes;

import api.EdgeData;

public class EdgeDataObj implements EdgeData {

    private int src, dest;
    private double weight;
    private String info;
    private int tag;
    private int ID;

    public EdgeDataObj(int src, int dest, double weight)  {       /// Throw exception for neg weight ??

        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.ID = ID;
        this.info = "";
        this.tag = 0;
    }

    public EdgeDataObj(EdgeDataObj other){

        EdgeDataObj e = (EdgeDataObj)other;
        this.src = e.src;
        this.dest = e.dest;
        this.weight = other.getWeight();
        this.info = other.getInfo();
        this.tag = other.getTag();
    }
    /**
     * The id of the source node of this edge.
     *
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * The id of the destination node of this edge
     *
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.weight;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public String toString() {
        return "EdgeDataObj{" +
                "src=" + src +
                ", dest=" + dest +
                ", weight=" + weight +
                ", info='" + info + '\'' +
                ", tag=" + tag +
                "ID = "+ ID +
                '}';
    }
}
