package classes;

import api.GeoLocation;

public class GeoLocationObj implements GeoLocation {

    private double x, y, z;

    public GeoLocationObj(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public GeoLocationObj(GeoLocationObj other) {
        this.x = other.x();
        this.y = other.y();
        this.z = other.z();

    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        return Math.sqrt(Math.pow(this.x - g.x(), 2) + Math.pow(this.y - g.y(), 2));
    }
    @Override
    public String toString() {
        return "GeoLocationObj{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}
