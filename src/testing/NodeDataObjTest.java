package testing;
import api.GeoLocation;
import classes.GeoLocationObj;
import classes.NodeDataObj;
import static org.junit.jupiter.api.Assertions.*;

class NodeDataObjTest {

    private NodeDataObj node1;
    private NodeDataObj node2;


    public void new_Node1() {
        GeoLocation g1 = new GeoLocationObj(7, -1, 3.5);
        node1 = new NodeDataObj(4,g1);
    }
    public void new_Node2() {
        GeoLocation g2 = new GeoLocationObj(-55, 88.8, 6);
        node2 = new NodeDataObj(-32,g2);
    }


    @org.junit.jupiter.api.Test
    void getEdges() {                    /// test on hashmap ??

    }

    @org.junit.jupiter.api.Test
    void getKey() {
        new_Node1();
        int key_1 = node1.getKey();
        assertEquals(key_1,4 );
        new_Node2();
        int key_2 = node2.getKey();
        assertEquals(key_2,-32 );
    }

    @org.junit.jupiter.api.Test
    void getLocation() {
        new_Node1();
        GeoLocation G1 = new GeoLocationObj(7, -1, 3.5);
        assertEquals(node1.getLocation().toString(),G1.toString());
        new_Node2();
        GeoLocation G2 = new GeoLocationObj(-55, 88.8, 6);
        assertEquals(node2.getLocation().toString(),G2.toString());
    }

    @org.junit.jupiter.api.Test
    void setLocation() {
        new_Node1();
        GeoLocation G1 = new GeoLocationObj(5,4,3);
        node1.setLocation(G1);
        assertEquals(node1.getLocation().toString(),G1.toString());
        new_Node2();
        GeoLocation G2 = new GeoLocationObj(-96,7,66);
        node2.setLocation(G2);
        assertEquals(node2.getLocation().toString(),G2.toString());
    }

    @org.junit.jupiter.api.Test
    void getWeight() {
        new_Node1();
        double weight_1 = node1.getWeight();
        assertEquals(weight_1,0 );
        new_Node2();
        double weight_2 = node2.getWeight();
        assertNotEquals(weight_2,14 );
    }

    @org.junit.jupiter.api.Test
    void setWeight() {
        new_Node1();
        double weight_1 = 62.32;
        node1.setWeight(weight_1);
        assertEquals(node1.getWeight(),weight_1 );
        new_Node2();
        double weight_2 = 5.999;
        node2.setWeight(weight_2);
        assertEquals(node2.getWeight(),weight_2 );
    }

    @org.junit.jupiter.api.Test
    void getInfo() {
        new_Node1();
        String info_1 = node1.getInfo();
        assertEquals(info_1,"White" );
        new_Node2();
        String info_2 = node2.getInfo();
        assertEquals(info_2,"White" );
    }

    @org.junit.jupiter.api.Test
    void setInfo() {
        new_Node1();
        String info_1 = "Orange";
        node1.setInfo(info_1);
        assertEquals(node1.getInfo(),info_1 );
        new_Node2();
        String info_2 = "Red";
        node2.setInfo(info_2);
        assertEquals(node2.getInfo(),info_2 );
    }

    @org.junit.jupiter.api.Test
    void getTag() {
        new_Node1();
        int tag_1 = node1.getTag();
        assertEquals(tag_1,0 );
        new_Node2();
        int tag_2 = node2.getTag();
        assertNotEquals(tag_2,-9 );
    }

    @org.junit.jupiter.api.Test
    void setTag() {
        new_Node1();
        int tag_1 = -19;
        node1.setTag(tag_1);
        assertEquals(node1.getTag(),tag_1 );
        new_Node2();
        int tag_2 = 97;
        node2.setTag(tag_2);
        assertEquals(node2.getTag(),tag_2 );
    }
}
