package testing;

import api.GeoLocation;
import classes.GeoLocationObj;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoLocationObjTest {

    private GeoLocationObj g1;
    private GeoLocationObj g2;
    private GeoLocationObj g3;
    private GeoLocationObj g4;


    public void new_geo() {

        g1 = new GeoLocationObj(10, 15, 3.5);
        g2 = new GeoLocationObj(-55, 25, 6);
        g3 = new GeoLocationObj(5, -1, 1);
        g4 = new GeoLocationObj(0, 0, 0);
    }

    @Test
    void x() {
        new_geo();
        assertEquals(g1.x(),10);
        assertEquals(g2.x(), -55);
        assertEquals(g3.x(),5);
        assertEquals(g4.x(),0);
    }

    @Test
    void y() {
        new_geo();
        assertEquals(g1.y(),15);
        assertEquals(g2.y(), 25);
        assertEquals(g3.y(),-1);
        assertEquals(g4.y(),0);
    }

    @Test
    void z() {
        new_geo();
        assertEquals(g1.z(),3.5);
        assertEquals(g2.z(), 6);
        assertEquals(g3.z(),1);
        assertEquals(g4.z(),0);
    }

    @Test
    void distance() { // String.format("%.3f",x) --> round a num x 3 digits after comma
       new_geo();
       double d1 = g1.distance(g2);
       double d_1 = Double.parseDouble(String.format("%.3f", d1));
       double d2 = g3.distance(g4);
       double d_2 = Double.parseDouble(String.format("%.3f", d2));
       double d3 = g1.distance(g4);
       double d_3 = Double.parseDouble(String.format("%.3f", d3));
       double d4 = g3.distance(g3);
       double d_4 = Double.parseDouble(String.format("%.3f", d4));
       assertEquals(d_1,65.812);
       assertEquals(d_2,5.196);
       assertEquals(d_3,18.364);
       assertEquals(d_4,0);
    }
}