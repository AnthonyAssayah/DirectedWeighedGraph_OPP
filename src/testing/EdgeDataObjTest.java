package testing;

import api.GeoLocation;
import classes.EdgeDataObj;
import classes.GeoLocationObj;
import classes.NodeDataObj;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataObjTest {

    private EdgeDataObj edge1;
    private EdgeDataObj edge2;
    private NodeDataObj node1;
    private NodeDataObj node2;
    private NodeDataObj node3;
    private NodeDataObj node4;


    public void new_edge1() {
        GeoLocation g1 = new GeoLocationObj(7, -1, 3.5);
        GeoLocation g2 = new GeoLocationObj(-55, 88.8, 6);
        node1 = new NodeDataObj(4,g1);
        node2 = new NodeDataObj(-32,g2);
        edge1 = new EdgeDataObj(node1.getKey(),node2.getKey(), 6 );
    }

    public void new_edge2() {
        GeoLocation g3 = new GeoLocationObj(-21,45.4,10.09);
        GeoLocation g4 = new GeoLocationObj(0.03,555,1080);
        node3 = new NodeDataObj(47,g3);
        node4 = new NodeDataObj(33,g4);
        edge2 = new EdgeDataObj(node3.getKey(),node4.getKey(), 88.8 );
    }


    @Test
    void getSrc() {
        new_edge1();
        int src_1 = node1.getKey();
        assertEquals(src_1,4);
        new_edge2();
        int src_2 = node3.getKey();
        assertEquals(src_2,47);

    }

    @Test
    void getDest() {
        new_edge1();
        int dest_1 = node2.getKey();
        assertEquals(dest_1,-32);
        new_edge2();
        int dest_2 = node4.getKey();
        assertEquals(dest_2,33);
    }

    @Test
    void getWeight() {
        new_edge1();
        double weight_1 = edge1.getWeight();
        assertEquals(weight_1,6);
        new_edge2();
        double weight_2 = edge2.getWeight();
        assertEquals(weight_2,88.8);
    }

    @Test
    void getInfo() {
        new_edge1();
        String info_1 = edge1.getInfo();
        assertEquals(info_1,"White");
        new_edge2();
        String info_2 = edge2.getInfo();
        assertNotEquals(info_2,"Blue");
    }

    @Test
    void setInfo() {
        new_edge1();
        String info_1 = "Black";
        edge1.setInfo(info_1);
        assertEquals(edge1.getInfo(),info_1 );
        new_edge2();
        String info_2 = "Gray";
        edge2.setInfo(info_2);
        assertEquals(edge2.getInfo(),info_2 );
    }

    @Test
    void getTag() {
        new_edge1();
        int tag_1 = edge1.getTag();
        assertEquals(tag_1,0);
        new_edge2();
        int tag_2 = edge2.getTag();
        assertEquals(tag_2,0);
    }

    @Test
    void setTag() {
        new_edge1();
        int tag_1 = 7;
        edge1.setTag(tag_1);
        assertEquals(edge1.getTag(),tag_1 );
        new_edge2();
        int tag_2 = -91;
        edge2.setTag(tag_2);
        assertEquals(edge2.getTag(),tag_2 );
    }
}